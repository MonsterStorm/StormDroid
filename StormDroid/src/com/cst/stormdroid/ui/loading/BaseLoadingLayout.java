package com.cst.stormdroid.ui.loading;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.cst.stormdroid.R;

/**
 * a loading layout
 * @author MonsterStorm
 * @param <T> view that u want using loading
 */
public abstract class BaseLoadingLayout<T extends View> extends LinearLayout {

	/**
	 * layout with data
	 */
	private T mContentView;

	/**
	 * view for no data
	 */
	private View mEmptyView;

	/**
	 * content view wrapper
	 */
	private FrameLayout mContentViewWarpper;

	/**
	 * loading indicator view
	 */
	private LoadingIndicator mLoadingIndicator;

	/**
	 * state of this view
	 */
	public enum STATE {
		STATE_IDLE, STATE_NO_DATA, STATE_WITH_DATA, STATE_LOADING
	};

	/**
	 * current state of layout
	 */
	private STATE mState = STATE.STATE_IDLE;

	/**
	 * context
	 */
	private Context mContext;

	public BaseLoadingLayout(Context context) {
		super(context);
		init(context, null);
	}

	public BaseLoadingLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs);
	}

	@TargetApi(11)
	public BaseLoadingLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context, attrs);
	}

	/**
	 * init loading layout
	 * @param contxt
	 * @param attrs
	 */
	private void init(Context context, AttributeSet attrs) {
		this.mContext = context;

		setGravity(Gravity.CENTER);

		mContentView = createContentView(context, attrs);
		addContentView(context, mContentView);

		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.BaseLoadingLayout);

		mEmptyView = createEmptyLayout(context, a);

		// whether show indicator
		if (a.getBoolean(R.styleable.BaseLoadingLayout_showLoadingIndicator, true)) {
			mLoadingIndicator = createLoadingIndicator(context, a);
		}

		handleStyledAttributes(a);

		a.recycle();
	}

	/**
	 * add content view to our content view wrapper
	 * @param context
	 * @param contentView
	 */
	private void addContentView(Context context, T contentView) {
		mContentViewWarpper = new FrameLayout(context);
		mContentViewWarpper.addView(contentView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
		addViewInternal(mContentViewWarpper, new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
	}

	/**
	 * Used internally for adding view. Need because we override addView to
	 * pass-through to the content View
	 */
	protected final void addViewInternal(View child, ViewGroup.LayoutParams params) {
		super.addView(child, -1, params);
	}

	/**
	 * override the addView of Linear Layout
	 */
	@Override
	public void addView(View child, int index, ViewGroup.LayoutParams params) {
		final T contentView = getContentView();
		if (contentView instanceof ViewGroup) {
			((ViewGroup) contentView).addView(child, index, params);
		} else {
			throw new UnsupportedOperationException("ContentView View is not a ViewGroup so can't addView");
		}
	}

	/**
	 * handle styled attributes, which can be specified be child class
	 * @param array
	 */
	public void handleStyledAttributes(TypedArray array) {
	}

	/**
	 * create content view
	 * @param context
	 * @param attrs
	 * @return
	 */
	public abstract T createContentView(Context context, AttributeSet attrs);

	/**
	 * create loading indicator
	 * @param context
	 * @param attrs
	 * @return
	 */
	protected LoadingIndicator createLoadingIndicator(Context context, TypedArray array) {
		if (array.hasValue(R.styleable.BaseLoadingLayout_loadingIndicatorLayout)) {
			return new LoadingIndicator(context, array.getResourceId(R.styleable.BaseLoadingLayout_loadingIndicatorLayout, R.layout.ui_loadingindicator));
		} else {
			return new LoadingIndicator(context);
		}
	}

	/**
	 * set progress of loading indicator
	 * @param progress
	 */
	public void setProgress(int progress) {
		if (mLoadingIndicator != null) {
			mLoadingIndicator.setProgress(progress);
		}
	}

	/**
	 * 
	 * @param context
	 * @param array
	 * @return
	 */
	public View createEmptyLayout(Context context, TypedArray array) {
		LayoutInflater inflater = LayoutInflater.from(mContext);
		int layout = R.layout.ui_emptylayout;
		if (array.hasValue(R.styleable.BaseLoadingLayout_emptyLayout)) {
			layout = array.getResourceId(R.styleable.BaseLoadingLayout_emptyLayout, R.layout.ui_emptylayout);
		}
		return inflater.inflate(layout, null);
	}

	public final void setEmptyView(View newEmptyView) {
		FrameLayout contentViewWrapper = getContentViewWrapper();

		if (null != newEmptyView) {
			// New view needs to be clickable so that Android recognizes it as a
			// target for Touch Events
			newEmptyView.setClickable(true);

			ViewParent newEmptyViewParent = newEmptyView.getParent();
			if (null != newEmptyViewParent && newEmptyViewParent instanceof ViewGroup) {
				((ViewGroup) newEmptyViewParent).removeView(newEmptyView);
			}

			// We need to convert any LayoutParams so that it works in our
			// FrameLayout
			FrameLayout.LayoutParams lp = convertEmptyViewLayoutParams(newEmptyView.getLayoutParams());
			if (null != lp) {
				contentViewWrapper.addView(newEmptyView, lp);
			} else {
				contentViewWrapper.addView(newEmptyView);
			}
		}

		if (mContentView instanceof EmptyViewMethodAccessor) {
			((EmptyViewMethodAccessor) mContentView).setEmptyViewInternal(newEmptyView);
		} else if (mContentView instanceof AbsListView){
			((AbsListView)mContentView).setEmptyView(newEmptyView);
		}
		mEmptyView = newEmptyView;
	}

	/**
	 * convert empty view layout params
	 * @param lp
	 * @return
	 */
	private static FrameLayout.LayoutParams convertEmptyViewLayoutParams(ViewGroup.LayoutParams lp) {
		FrameLayout.LayoutParams newLp = null;

		if (null != lp) {
			newLp = new FrameLayout.LayoutParams(lp);

			if (lp instanceof LinearLayout.LayoutParams) {
				newLp.gravity = ((LinearLayout.LayoutParams) lp).gravity;
			} else {
				newLp.gravity = Gravity.CENTER;
			}
		}

		return newLp;
	}

	// ************************setters and getters************************

	public void setState(STATE state) {
		this.mState = state;
		switch (mState) {
		case STATE_NO_DATA:
			mEmptyView.setVisibility(View.VISIBLE);
			mContentView.setVisibility(View.GONE);
			if (mLoadingIndicator != null)
				mLoadingIndicator.setVisibility(View.GONE);
			break;
		case STATE_WITH_DATA:
			mEmptyView.setVisibility(View.GONE);
			mContentView.setVisibility(View.VISIBLE);
			if (mLoadingIndicator != null)
				mLoadingIndicator.setVisibility(View.GONE);
			break;
		case STATE_LOADING:
			mEmptyView.setVisibility(View.GONE);
			mContentView.setVisibility(View.GONE);
			if (mLoadingIndicator != null)
				mLoadingIndicator.setVisibility(View.VISIBLE);
			break;
		}
	}

	public STATE getState() {
		return mState;
	}

	public T getContentView() {
		return mContentView;
	}

	public FrameLayout getContentViewWrapper() {
		return mContentViewWarpper;
	}
}
