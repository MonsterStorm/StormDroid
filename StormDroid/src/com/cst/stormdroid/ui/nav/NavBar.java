package com.cst.stormdroid.ui.nav;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cst.stormdroid.R;

/**
 * nav bar, with left btn, center(image or text), right btn
 * 
 * @author Storm
 * 
 */
public class NavBar extends LinearLayout {
	private static final Long DEFAULT_DURATION = 1000L;
	private static final Long DEFAULT_START_OFFSET = 1500L;
	private ImageView mCenterImg;
	private TextView mCenterTxt;
	private CharSequence mOrignalTxt;
	private View mBtnLeft;
	private View mBtnRight;
	private View mBtnMore;
	private Animation mBreathAnim;
	private Animation mFadeoutAnim;
	private boolean isShow = true;
	private boolean isFadeOutEnd = true;
	private boolean isFirstFadeout = true;

	private OnClickListener mRightOnClickListener;

	public NavBar(Context context) {
		super(context);
		init(context, null);
	}

	public NavBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs);
	}

	private void init(Context context, AttributeSet attrs) {
		TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.NavBar);
		try {

			int layout = array.getResourceId(R.styleable.NavBar_layout, R.layout.navbar_default);

			View contentView = LayoutInflater.from(context).inflate(layout, this, true);
			mBtnLeft = contentView.findViewById(R.id.btnLeft);
			mBtnRight = contentView.findViewById(R.id.btnRight);
			mBtnMore = contentView.findViewById(R.id.ivMore);
			mCenterImg = (ImageView) contentView.findViewById(R.id.ivCenter);
			mCenterTxt = (TextView) contentView.findViewById(R.id.tvCenter);

			//extend views
			getExtendedViews(contentView);

			if (array.hasValue(R.styleable.NavBar_bgLeft)) {
				int bgLeft = array.getResourceId(R.styleable.NavBar_bgLeft, R.color.darkgray);
				setLeftBackground(bgLeft);
			}

			if (array.hasValue(R.styleable.NavBar_srcLeft)) {
				int srcLeft = array.getResourceId(R.styleable.NavBar_srcLeft, R.color.darkgray);
				setLeftSrc(srcLeft);
			}
			if (array.hasValue(R.styleable.NavBar_btnLeftVisible)) {
				boolean enable = array.getBoolean(R.styleable.NavBar_btnLeftVisible, false);
				if (enable) {
					setBtnLeftVisibility(View.VISIBLE);
				} else {
					setBtnLeftVisibility(View.INVISIBLE);
				}
			}

			if (array.hasValue(R.styleable.NavBar_txtRight)) {//右边文字
				int txtRight = array.getResourceId(R.styleable.NavBar_txtRight, 0);
				mBtnRight.setVisibility(View.INVISIBLE);
				mBtnRight = contentView.findViewById(R.id.txtRight);
				setRightTxt(txtRight);
				mBtnRight.setVisibility(View.VISIBLE);
			}

			if (array.hasValue(R.styleable.NavBar_txtRightVisible)) {//右边文字
				boolean txtRightVisible = array.getBoolean(R.styleable.NavBar_txtRightVisible, false);
				if (txtRightVisible) {
					mBtnRight.setVisibility(View.INVISIBLE);
					mBtnRight = contentView.findViewById(R.id.txtRight);
					mBtnRight.setVisibility(View.VISIBLE);
				} else {
					View view = contentView.findViewById(R.id.txtRight);
					view.setVisibility(View.INVISIBLE);
				}
			}

			if (array.hasValue(R.styleable.NavBar_bgRight)) {
				int bgRight = array.getResourceId(R.styleable.NavBar_bgRight, R.color.darkgray);
				setRightBackground(bgRight);
			}

			if (array.hasValue(R.styleable.NavBar_srcRight)) {
				int srcRight = array.getResourceId(R.styleable.NavBar_srcRight, 0);
				setRightSrc(srcRight);
			}

			if (array.hasValue(R.styleable.NavBar_btnRightVisible)) {
				boolean enable = array.getBoolean(R.styleable.NavBar_btnRightVisible, false);
				if (enable) {
					setBtnRightVisibility(View.VISIBLE);
				} else {
					setBtnRightVisibility(View.INVISIBLE);
				}
			}

			//center img & txt
			if (array.hasValue(R.styleable.NavBar_centerImg)) {
				int imgCenter = array.getResourceId(R.styleable.NavBar_centerImg, 0);
				setCenterImg(imgCenter);
				setCenterImgVisibility(View.VISIBLE);
			} else {
				setCenterImgVisibility(View.INVISIBLE);
			}

			if (array.hasValue(R.styleable.NavBar_centerImgVisible)) {
				boolean enable = array.getBoolean(R.styleable.NavBar_centerImgVisible, false);
				if (enable) {
					setCenterImgVisibility(View.VISIBLE);
				} else {
					setCenterImgVisibility(View.INVISIBLE);
				}
			}

			if (array.hasValue(R.styleable.NavBar_centerTxtStyle)) {
				int style = array.getResourceId(R.styleable.NavBar_centerTxtStyle, 0);
				setCenterTxtStyle(style);
			}

			if (array.hasValue(R.styleable.NavBar_centerTxt)) {
				int txtCenter = array.getResourceId(R.styleable.NavBar_centerTxt, 0);
				setCenterTxt(txtCenter);
				setCenterTxtVisibility(View.VISIBLE);
			} else {
				setCenterTxtVisibility(View.INVISIBLE);
			}

			if (array.hasValue(R.styleable.NavBar_centerTxtVisible)) {
				boolean enable = array.getBoolean(R.styleable.NavBar_centerTxtVisible, false);
				if (enable) {
					setCenterTxtVisibility(View.VISIBLE);
				} else {
					setCenterTxtVisibility(View.INVISIBLE);
				}
			}

			if (array.hasValue(R.styleable.NavBar_srcMore)) {
				int srcMore = array.getResourceId(R.styleable.NavBar_srcMore, 0);
				setBtnMoreSrc(srcMore);
				setBtnMoreVisibility(View.VISIBLE);
			} else {
				setBtnMoreVisibility(View.GONE);
			}

			if (array.hasValue(R.styleable.NavBar_btnMoreVisible)) {
				boolean enable = array.getBoolean(R.styleable.NavBar_centerTxtVisible, false);
				if (enable) {
					setBtnMoreVisibility(View.VISIBLE);
				} else {
					setBtnMoreVisibility(View.GONE);
				}
			}

			//extended styles
			handleExtendedStyles(context, attrs);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			array.recycle();
		}

	}

	//method extend by it's child
	public void getExtendedViews(View contentView) {
	}

	public void handleExtendedStyles(Context context, AttributeSet attrs) {
	}

	private void initOrignalTxt() {
		if (mOrignalTxt == null) {
			mOrignalTxt = getCenterTxtString();
		}
	}

	/**
	 * append text add to orignal text
	 * 
	 * @param appendResText
	 */
	public void startBreath(int appendResText) {
		startBreath(getContext().getResources().getString(appendResText));
	}

	/**
	 * appendText add to orignal text
	 * 
	 * @param breathText
	 */
	public void startBreath(String breathText) {
		if (mBreathAnim == null) {
			mBreathAnim = AnimationUtils.loadAnimation(getContext(), R.anim.anim_navbar_breath);
			mBreathAnim.setAnimationListener(new AnimationListener() {
				@Override
				public void onAnimationStart(Animation animation) {
				}

				@Override
				public void onAnimationRepeat(Animation animation) {
				}

				@Override
				public void onAnimationEnd(Animation animation) {//在动画结束的时恢复信息
					if (mOrignalTxt != null)
						setCenterTxt(mOrignalTxt);
				}
			});
		}
		initOrignalTxt();
		this.setCenterTxt(mOrignalTxt + breathText);
		this.startAnimation(mBreathAnim);
	}

	/**
	 * stop breath
	 * 
	 * @param appendResText
	 */
	public void stopBreath(int appendResText) {
		stopBreath(getContext().getResources().getString(appendResText));
	}

	/**
	 * stop animation
	 */
	public void stopBreath(String breathText) {
		initOrignalTxt();

		if (breathText == null) {
			this.setCenterTxt(mOrignalTxt);
			mOrignalTxt = null;
		} else {
			this.setCenterTxt(mOrignalTxt + breathText);
		}
		if (mBreathAnim != null) {
			this.clearAnimation();
			mBreathAnim = null;
		}
	}

	/**
	 * fade out this view
	 */
	public void fadeOut(Long offset, Long duration) {
		if (isShow == false || isFadeOutEnd == false) {//快速切换时，不多次fadeout
			return;
		}
		if (mFadeoutAnim == null) {
			mFadeoutAnim = new AlphaAnimation(1.0f, 0.0f);
			mFadeoutAnim.setAnimationListener(new AnimationListener() {
				@Override
				public void onAnimationStart(Animation animation) {
					isFadeOutEnd = false;
				}

				@Override
				public void onAnimationRepeat(Animation animation) {
				}

				@Override
				public void onAnimationEnd(Animation animation) {
					NavBar.this.setVisibility(View.INVISIBLE);
					isFadeOutEnd = true;
					isFirstFadeout = false;
				}
			});
		}
		if (offset != null) {
			mFadeoutAnim.setStartOffset(offset);
		} else {
			mFadeoutAnim.setStartOffset(DEFAULT_START_OFFSET);
		}
		if (duration != null) {
			mFadeoutAnim.setDuration(duration);
		} else {
			mFadeoutAnim.setDuration(DEFAULT_DURATION);
		}

		this.startAnimation(mFadeoutAnim);
		isShow = false;
	}

	/**
	 * show this view
	 */
	public void show() {
		if (isFirstFadeout == false) {//快速操作时不屏蔽第一次fadeout
			if (mFadeoutAnim != null) {
				mFadeoutAnim.cancel();
				isFadeOutEnd = true;
			}
			this.setVisibility(View.VISIBLE);
			isShow = true;
		}
	}

	/**
	 * toggle show
	 */
	public void toggle() {
		if (isShow) {
			fadeOut(0L, DEFAULT_DURATION);
		} else {
			show();
		}
	}

	//----------------------getters and setters------------------------
	public NavBar setLeftSrc(int resId) {
		if (mBtnLeft != null) {
			if (mBtnLeft instanceof ImageButton) {
				((ImageButton) mBtnLeft).setImageResource(resId);
			}
		}
		return this;
	}

	public NavBar setRightTxt(int resId) {
		if (mBtnRight != null) {
			if (mBtnRight instanceof TextView) {
				((TextView) mBtnRight).setText(resId);
			}
		}
		return this;
	}

	/**
	 * 
	 * @param resId
	 * @param forceChange
	 *            force change right (img or txt)
	 * @return
	 */
	public NavBar setRightTxt(int resId, boolean forceChange) {
		if (forceChange) {
			if (mBtnRight != null) {
				if (mBtnRight instanceof TextView) {
					((TextView) mBtnRight).setText(resId);
					if (mBtnRight.getVisibility() != View.VISIBLE) {
						mBtnRight.setVisibility(View.VISIBLE);
					}
				} else {
					mBtnRight.setVisibility(View.GONE);
					mBtnRight = this.findViewById(R.id.txtRight);
					((TextView) mBtnRight).setText(resId);
					if (mBtnRight.getVisibility() != View.VISIBLE) {
						mBtnRight.setVisibility(View.VISIBLE);
					}
				}
			}
		} else {
			setRightTxt(resId);
		}
		return this;
	}

	public NavBar setLeftBackground(int resId) {
		if (mBtnLeft != null) {
			mBtnLeft.setBackgroundResource(resId);
		}
		return this;
	}

	public NavBar setRightSrc(int resId) {
		if (mBtnRight != null) {
			if (mBtnRight instanceof ImageButton) {
				((ImageButton) mBtnRight).setImageResource(resId);
			}
		}
		return this;
	}

	public NavBar setRightBackground(int resId) {
		if (mBtnRight != null) {
			mBtnRight.setBackgroundResource(resId);
		}
		return this;
	}

	public NavBar setLeftOnClickListener(OnClickListener listener) {
		if (mBtnLeft != null) {
			mBtnLeft.setOnClickListener(listener);
		}
		return this;
	}

	public NavBar setRightOnClickListener(OnClickListener listener) {
		if (mBtnRight != null) {
			mRightOnClickListener = listener;
			mBtnRight.setOnClickListener(mRightOnClickListener);
		}
		return this;
	}

	public NavBar setCenterImg(int resId) {
		if (mCenterImg != null) {
			mCenterImg.setImageResource(resId);
		}
		return this;
	}

	public NavBar setCenterTxt(int resId) {
		if (mCenterTxt != null) {
			mCenterTxt.setText(resId);
		}
		return this;
	}

	public NavBar setCenterTxt(CharSequence text) {
		if (mCenterTxt != null) {
			mCenterTxt.setText(text);
		}
		return this;
	}

	public NavBar setBtnLeftVisibility(int visibility) {
		if (mBtnLeft != null) {
			mBtnLeft.setVisibility(visibility);
		}
		return this;
	}

	public NavBar setBtnRightVisibility(int visibility) {
		if (mBtnRight != null) {
			mBtnRight.setVisibility(visibility);
		}
		return this;
	}

	public NavBar setCenterImgVisibility(int visibility) {
		if (mCenterImg != null) {
			mCenterImg.setVisibility(visibility);
		}
		return this;
	}

	public NavBar setCenterTxtVisibility(int visibility) {
		if (mCenterTxt != null) {
			mCenterTxt.setVisibility(visibility);
		}
		return this;
	}

	public NavBar setBtnMoreVisibility(int visibility) {
		if (mBtnMore != null) {
			mBtnMore.setVisibility(visibility);
		}
		return this;
	}

	public NavBar setBtnMoreSrc(int resId) {
		if (mBtnMore != null) {
			if (mBtnMore instanceof ImageView) {
				((ImageView) mBtnMore).setImageResource(resId);
			}
		}
		return this;
	}

	public NavBar setBtnMoreOnClickListener(OnClickListener listener) {
		if (mBtnMore != null) {
			mBtnMore.setOnClickListener(listener);
		}
		return this;
	}

	public NavBar setCenterTxtOnClickListener(OnClickListener listener) {
		if (mCenterTxt != null) {
			mCenterTxt.setOnClickListener(listener);
		}
		return this;
	}

	public NavBar setCenterTxtStyle(int style) {
		if (mCenterTxt != null) {
			mCenterTxt.setTextAppearance(getContext(), style);
		}
		return this;
	}

	public NavBar setLeftEnabled(boolean enabled) {
		if (mBtnLeft != null) {
			mBtnLeft.setEnabled(enabled);
		}
		return this;
	}

	public NavBar setRightEnabled(boolean enabled) {
		if (mBtnRight != null) {
			mBtnRight.setEnabled(enabled);
		}
		return this;
	}

	public View getBtnLeft() {
		return mBtnLeft;
	}

	public View getBtnRight() {
		return mBtnRight;
	}

	public ImageView getCenterImg() {
		return mCenterImg;
	}

	public TextView getCenterTxt() {
		return mCenterTxt;
	}

	public String getCenterTxtString() {
		if (mCenterTxt != null && mCenterTxt.getText() != null) {
			return mCenterTxt.getText().toString();
		}
		return null;
	}
}
