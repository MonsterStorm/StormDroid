package com.cst.stormdroid.ui.listview.autoload;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.cst.stormdroid.R;
import com.cst.stormdroid.utils.dimension.DimensionUtil;

/**
 * pullable view
 * 
 * @author Storm
 * 
 * @param <T>
 */
public abstract class PullableViewBase<T extends View> extends RelativeLayout {
	private static final String STATE_MODE = "state_mode";
	private static final String STATE_CURRENT_MODE = "state_current_mode";
	private static final String STATE_SUPER = "state_super";
	// pullable friction
	private float FRICTION = 2.0f;
	// scroll duration
	public static final int SMOOTH_SCROLL_DURATION_MS = 200;
	// minimum touch distance
	private int mTouchSlop;
	// touch position
	private float mInitialMotionX, mInitialMotionY;
	private float mLastMotionX, mLastMotionY;

	// indicate whether we are dragging
	private boolean mIsBeingDragged = false;

	// pullable mode
	private Mode mMode = Mode.getDefault();

	private View mBackgroundView;

	// current pullable mode
	private Mode mCurrentMode;

	// scroll interpolator
	private Interpolator mScrollAnimationInterpolator;

	// scroll runnable
	private SmoothScrollRunnable mCurrentSmoothScrollRunnable;

	// smooth scroll listener
	private OnSmoothScrollFinishedListener mOnSmoothScrollFinishedListener;

	// content view
	protected T mContentView;
	private FrameLayout mContentViewWrapper;

	private View mHeaderLayout;
	private View mFooterLayout;

	// max background pull, max pull distance of background
	private int maxBackgroundPull;

	public PullableViewBase(Context context) {
		super(context);
		init(context, null);
	}

	public PullableViewBase(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs);
	}

	/**
	 * override add view method to add view to it's wrapped view
	 */
	@Override
	public void addView(View child, int index, ViewGroup.LayoutParams params) {

		final T mContentView = getContentView();

		if (mContentView instanceof ViewGroup) {
			((ViewGroup) mContentView).addView(child, index, params);
		} else {
			throw new UnsupportedOperationException("ContentView View is not a ViewGroup so can't addView");
		}
	}

	/**
	 * Used internally for adding view. Need because we override addView to
	 * pass-through to the Refreshable View
	 */
	protected final void addViewInternal(View child, int index, ViewGroup.LayoutParams params) {
		super.addView(child, index, params);
	}

	/**
	 * Used internally for adding view. Need because we override addView to
	 * pass-through to the Refreshable View
	 */
	protected final void addViewInternal(View child, ViewGroup.LayoutParams params) {
		super.addView(child, -1, params);
	}

	/**
	 * init view
	 * 
	 * @param context
	 * @param attrs
	 */
	private void init(Context context, AttributeSet attrs) {
		// setOrientation(LinearLayout.VERTICAL);
		setGravity(Gravity.CENTER);

		ViewConfiguration config = ViewConfiguration.get(context);
		mTouchSlop = config.getScaledTouchSlop();

		TypedArray pullableTypedArray = context.obtainStyledAttributes(attrs, R.styleable.PullableViewBase);
		TypedArray autoLoadTypedArray = context.obtainStyledAttributes(attrs, R.styleable.AutoLoadListView);

		// 拉动比例
		if (pullableTypedArray.hasValue(R.styleable.PullableViewBase_friction)) {
			FRICTION = pullableTypedArray.getFloat(R.styleable.PullableViewBase_friction, 2.0f);
		}

		// 背景图
		addBackgroundView(context, pullableTypedArray);

		// create content view
		mContentView = createContentView(context, attrs);

		// add content view
		addContentView(context, mContentView);

		// We need to create now layouts now
		mHeaderLayout = createHeaderView(context, Mode.PULL_FROM_START, autoLoadTypedArray);
		mFooterLayout = createFooterView(context, Mode.PULL_FROM_END, autoLoadTypedArray);
		autoLoadTypedArray.recycle();

		// recycle them...
		handleStyledAttributes(pullableTypedArray);
		pullableTypedArray.recycle();

		updateUIForMode();
	}

	private void addBackgroundView(Context context, TypedArray array) {
		if (array.hasValue(R.styleable.PullableViewBase_bgView)) {
			mBackgroundView = new ImageView(context);

			// addView
			RelativeLayout.LayoutParams lp = getLoadingLayoutLayoutParams();
			DisplayMetrics displaymetrics = new DisplayMetrics();
			((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);

			maxBackgroundPull = (int) (displaymetrics.heightPixels / FRICTION + DimensionUtil.dipToPixel(context, 50));
			lp.setMargins(0, -maxBackgroundPull, 0, 0);

			lp.height = maxBackgroundPull + DimensionUtil.dipToPixel(context, 150) * 2;// 最大宽度为图片的两倍高度+maxPull

			int bgViewId = array.getResourceId(R.styleable.PullableViewBase_bgView, R.drawable.ic_launcher);
			// mBackgroundView.setBackgroundResource(bgViewId);
			((ImageView) mBackgroundView).setScaleType(ScaleType.FIT_XY);
			((ImageView) mBackgroundView).setImageResource(bgViewId);// 如果用bg的话就不能scrollTo了

			addViewInternal(mBackgroundView, -1, lp);
		}
	}

	/**
	 * add content view to layout
	 * 
	 * @param context
	 * @param refreshableView
	 */
	private void addContentView(Context context, T contentView) {
		mContentViewWrapper = new FrameLayout(context);

		mContentViewWrapper.addView(contentView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

		addViewInternal(mContentViewWrapper, new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
	}

	/**
	 * create content view
	 * 
	 * @param context
	 * @param attrs
	 * @return
	 */
	protected abstract T createContentView(Context context, AttributeSet attrs);

	protected abstract View createHeaderView(Context context, Mode mode, TypedArray array);

	protected abstract View createFooterView(Context context, Mode mode, TypedArray array);

	protected abstract boolean isReadyForPullStart();

	protected abstract boolean isReadyForPullEnd();

	protected abstract void handleStyledAttributes(TypedArray array);

	/**
	 * touch event intercepter
	 */
	@Override
	public final boolean onInterceptTouchEvent(MotionEvent event) {
		final int action = event.getAction();

		// ToastHelper.showToast(getContext(), "onInterceptTouchEvent: " + action);

		if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
			mIsBeingDragged = false;
			return false;
		}

		if (action != MotionEvent.ACTION_DOWN && mIsBeingDragged) {
			return true;
		}

		switch (action) {
		case MotionEvent.ACTION_MOVE: {
			if (isReadyForPull()) {
				final float y = event.getY(), x = event.getX();
				final float diff, oppositeDiff, absDiff;

				diff = y - mLastMotionY;
				oppositeDiff = x - mLastMotionX;
				absDiff = Math.abs(diff);

				if (absDiff > mTouchSlop && absDiff > Math.abs(oppositeDiff)) {
					if (mMode.showHeaderLoadingLayout() && diff >= 1f && isReadyForPullStart()) {
						mLastMotionY = y;
						mLastMotionX = x;
						mIsBeingDragged = true;
						if (mMode == Mode.BOTH) {
							mCurrentMode = Mode.PULL_FROM_START;
						}
					} else if (mMode.showFooterLoadingLayout() && diff <= -1f && isReadyForPullEnd()) {
						mLastMotionY = y;
						mLastMotionX = x;
						mIsBeingDragged = true;
						if (mMode == Mode.BOTH) {
							mCurrentMode = Mode.PULL_FROM_END;
						}
					}
				}
			}
			break;
		}
		case MotionEvent.ACTION_DOWN: {
			if (isReadyForPull()) {
				mLastMotionY = mInitialMotionY = event.getY();
				mLastMotionX = mInitialMotionX = event.getX();
				mIsBeingDragged = false;
			}
			break;
		}
		}

		return mIsBeingDragged;
	}

	/**
	 * on touch event
	 */
	@Override
	public final boolean onTouchEvent(MotionEvent event) {
		// ToastHelper.showToast(getContext(), "onTouchEvent: " + event.getAction());

		if (event.getAction() == MotionEvent.ACTION_DOWN && event.getEdgeFlags() != 0) {
			return false;
		}

		switch (event.getAction()) {
		case MotionEvent.ACTION_MOVE: {
			if (mIsBeingDragged) {
				mLastMotionY = event.getY();
				mLastMotionX = event.getX();
				pullEvent();
				return true;
			}
			break;
		}

		case MotionEvent.ACTION_DOWN: {
			if (isReadyForPull()) {
				mLastMotionY = mInitialMotionY = event.getY();
				mLastMotionX = mInitialMotionX = event.getX();
				return true;
			}
			break;
		}

		case MotionEvent.ACTION_CANCEL:
		case MotionEvent.ACTION_UP: {
			if (mIsBeingDragged) {
				mIsBeingDragged = false;
				onReset();
				return true;
			}
			break;
		}
		}
		return false;
	}

	// -----------------------------------pullable ----------------------------
	private boolean isReadyForPull() {
		switch (mMode) {
		case PULL_FROM_START:
			return isReadyForPullStart();
		case PULL_FROM_END:
			return isReadyForPullEnd();
		case BOTH:
			return isReadyForPullEnd() || isReadyForPullStart();
		default:
			return false;
		}
	}

	protected void onReset() {
		mIsBeingDragged = false;
		smoothScrollTo(0, mOnSmoothScrollFinishedListener);
	}

	protected void updateUIForMode() {
		// final LinearLayout.LayoutParams lp = getLoadingLayoutLayoutParams();
		final LayoutParams lp = getLoadingLayoutLayoutParams();

		// Remove Header, and then add Header Loading View again if needed
		if (mHeaderLayout != null) {
			if (this == mHeaderLayout.getParent()) {
				removeHeaderLayout();
			}
			if (mMode.showHeaderLoadingLayout()) {
				addHeaderLayout(lp);
			}
		}

		// Remove Footer, and then add Footer Loading View again if needed
		if (mFooterLayout != null) {
			if (this == mFooterLayout.getParent()) {
				removeFooterLayout();
			}
			if (mMode.showFooterLoadingLayout()) {
				addFooterLayout(lp);
			}
		}

		refreshLoadingViewsSize();
		mCurrentMode = (mMode != Mode.BOTH) ? mMode : Mode.PULL_FROM_END;
	}

	private LayoutParams getLoadingLayoutLayoutParams() {
		return new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
	}

	public void removeHeaderLayout() {
		removeView(mHeaderLayout);
	}

	public void removeFooterLayout() {
		removeView(mFooterLayout);
	}

	public void addHeaderLayout(LayoutParams lp) {
		addViewInternal(mHeaderLayout, 0, lp);
	}

	public void addFooterLayout(LayoutParams lp) {
		addViewInternal(mFooterLayout, lp);
	}

	public void onPtrRestoreInstanceState(Bundle bundle) {
	};

	public void onPtrSaveInstanceState(Bundle bundle) {
	};

	@Override
	protected final void onRestoreInstanceState(Parcelable state) {
		if (state instanceof Bundle) {
			Bundle bundle = (Bundle) state;
			mCurrentMode = Mode.mapIntToValue(bundle.getInt(STATE_CURRENT_MODE, 0));
			// Let super Restore Itself
			super.onRestoreInstanceState(bundle.getParcelable(STATE_SUPER));
			// Now let derivative classes restore their state
			onPtrRestoreInstanceState(bundle);
			return;
		}

		if (state instanceof View.BaseSavedState)// fixed by cst
			super.onRestoreInstanceState(state);
	}

	@Override
	protected final Parcelable onSaveInstanceState() {
		Bundle bundle = new Bundle();
		// Let derivative classes get a chance to save state first, that way we
		// can make sure they don't overrite any of our values
		onPtrSaveInstanceState(bundle);
		bundle.putInt(STATE_CURRENT_MODE, mCurrentMode.getIntValue());
		bundle.putParcelable(STATE_SUPER, super.onSaveInstanceState());
		return bundle;
	}

	/**
	 * refresh size
	 */
	@Override
	protected final void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);

		// We need to update the header/footer when our size changes
		// refreshLoadingViewsSize();

		// Update the Refreshable View layout
		// refreshContentViewSize(w, h);//会造成显示抖动的问题

		post(new Runnable() {// 这个不能删，否则布局变动不刷新
			@Override
			public void run() {
				requestLayout();
			}
		});
	}

	protected final void refreshLoadingViewsSize() {
		final int maximumPullScroll = (int) (getMaximumPullScroll() * 1.2f);

		int pLeft = getPaddingLeft();
		int pTop = getPaddingTop();
		int pRight = getPaddingRight();
		int pBottom = getPaddingBottom();

		pTop = 0;
		pBottom = 0;
		/*
		 * if (mHeaderLayout != null && mMode.showHeaderLoadingLayout()) {
		 * ViewGroup.LayoutParams lp = (ViewGroup.LayoutParams) mHeaderLayout.getLayoutParams();
		 * lp.height = maximumPullScroll;
		 * mHeaderLayout.requestLayout();
		 * pTop = -maximumPullScroll;
		 * pTop = 0;
		 * } else {
		 * pTop = 0;
		 * }
		 * if (mFooterLayout != null && mMode.showFooterLoadingLayout()) {
		 * ViewGroup.LayoutParams lp = (ViewGroup.LayoutParams) mFooterLayout.getLayoutParams();
		 * lp.height = maximumPullScroll;
		 * mFooterLayout.requestLayout();
		 * pBottom = -maximumPullScroll;
		 * pBottom = 0;
		 * } else {
		 * pBottom = 0;
		 * }
		 */

		setPadding(pLeft, pTop, pRight, pBottom);
	}

	/**
	 * refresh content view size
	 * 
	 * @param width
	 * @param height
	 */
	private final void refreshContentViewSize(int width, int height) {
		// We need to set the Height of the content View to the same as this layout
		LayoutParams lp = (RelativeLayout.LayoutParams) mContentViewWrapper.getLayoutParams();

		if (lp.height != height) {
			lp.height = height;
			mContentViewWrapper.requestLayout();
		}
	}

	// -------------------------------scroll---------------------------------------
	private void pullEvent() {
		final int newScrollValue;
		final int itemDimension;
		final float initialMotionValue, lastMotionValue;

		initialMotionValue = mInitialMotionY;
		lastMotionValue = mLastMotionY;

		switch (mCurrentMode) {
		case PULL_FROM_END:
			newScrollValue = Math.round(Math.max(initialMotionValue - lastMotionValue, 0) / FRICTION);
			itemDimension = getFooterSize();
			break;
		case PULL_FROM_START:
		default:
			newScrollValue = Math.round(Math.min(initialMotionValue - lastMotionValue, 0) / FRICTION);
			itemDimension = getHeaderSize();
			break;
		}

		setHeaderScroll(newScrollValue);
	}

	private long timeStart = 0;

	protected final void setHeaderScroll(int value) {
		final int maximumPullScroll = getMaximumPullScroll();
		value = Math.min(maximumPullScroll, Math.max(-maximumPullScroll, value));

		this.scrollTo(0, value);

		if (mBackgroundView != null) {
			mBackgroundView.scrollTo(0, value / 4);
			// mBackgroundView.scrollBy(0, distance / 2);//scroll By 的话就用距离
		}

		/*
		 * if(mBackgroundView != null && (System.currentTimeMillis() - timeStart) > 20){
		 * timeStart = System.currentTimeMillis();
		 * RelativeLayout.LayoutParams lp = (LayoutParams) mBackgroundView.getLayoutParams();
		 * lp.topMargin = -maxBackgroundPull + value / 2;
		 * mBackgroundView.setLayoutParams(lp);
		 * mBackgroundView.invalidate(mBackgroundView.getLeft(), mBackgroundView.getTop(), mBackgroundView.getRight(), mBackgroundView.getBottom());
		 * // mBackgroundView.startAnimation(new TranslateAnimation(0, 0, value/2, 0));
		 * }
		 */
	}

	private int getMaximumPullScroll() {
		return Math.round(getHeight() / FRICTION);
	}

	/**
	 * mode
	 * 
	 * @author Storm
	 * 
	 */
	public static enum Mode {
		DISABLED(0x0), PULL_FROM_START(0x1), PULL_FROM_END(0x2), BOTH(0x3);
		static Mode mapIntToValue(final int modeInt) {
			for (Mode value : Mode.values()) {
				if (modeInt == value.getIntValue()) {
					return value;
				}
			}

			return getDefault();
		}

		static Mode getDefault() {
			return BOTH;
		}

		private int mIntValue;

		Mode(int modeInt) {
			mIntValue = modeInt;
		}

		boolean permitsPullToRefresh() {
			return !(this == DISABLED);
		}

		public boolean showHeaderLoadingLayout() {
			return this == PULL_FROM_START || this == BOTH;
		}

		public boolean showFooterLoadingLayout() {
			return this == PULL_FROM_END || this == BOTH;
		}

		int getIntValue() {
			return mIntValue;
		}

	}

	// ----------------------smooth scroll to position------------------------------
	private final void smoothScrollTo(int scrollValue) {
		smoothScrollTo(scrollValue, SMOOTH_SCROLL_DURATION_MS);
	}

	private final void smoothScrollTo(int scrollValue, OnSmoothScrollFinishedListener listener) {
		smoothScrollTo(scrollValue, SMOOTH_SCROLL_DURATION_MS, 0, listener);
	}

	private final void smoothScrollTo(int scrollValue, long duration) {
		smoothScrollTo(scrollValue, duration, 0, null);
	}

	private final void smoothScrollTo(int newScrollValue, long duration, long delayMillis, OnSmoothScrollFinishedListener listener) {
		if (null != mCurrentSmoothScrollRunnable) {
			mCurrentSmoothScrollRunnable.stop();
		}

		final int oldScrollValue = getScrollY();

		if (oldScrollValue != newScrollValue) {
			if (null == mScrollAnimationInterpolator) {
				mScrollAnimationInterpolator = new DecelerateInterpolator();
			}
			mCurrentSmoothScrollRunnable = new SmoothScrollRunnable(oldScrollValue, newScrollValue, duration, listener);

			if (delayMillis > 0) {
				postDelayed(mCurrentSmoothScrollRunnable, delayMillis);
			} else {
				post(mCurrentSmoothScrollRunnable);
			}
		}
	}

	/**
	 * smooth scroll runnable
	 * 
	 * @author Storm
	 * 
	 */
	final class SmoothScrollRunnable implements Runnable {
		private final Interpolator mInterpolator;
		private final int mScrollToY;
		private final int mScrollFromY;
		private final long mDuration;
		private OnSmoothScrollFinishedListener mListener;

		private boolean mContinueRunning = true;
		private long mStartTime = -1;
		private int mCurrentY = -1;

		public SmoothScrollRunnable(int fromY, int toY, long duration, OnSmoothScrollFinishedListener listener) {
			mScrollFromY = fromY;
			mScrollToY = toY;
			mInterpolator = mScrollAnimationInterpolator;
			mDuration = duration;
			mListener = listener;
		}

		@Override
		public void run() {
			if (mStartTime == -1) {
				mStartTime = System.currentTimeMillis();
			} else {
				long normalizedTime = (1000 * (System.currentTimeMillis() - mStartTime)) / mDuration;
				normalizedTime = Math.max(Math.min(normalizedTime, 1000), 0);

				final int deltaY = Math.round((mScrollFromY - mScrollToY) * mInterpolator.getInterpolation(normalizedTime / 1000f));
				mCurrentY = mScrollFromY - deltaY;
				setHeaderScroll(mCurrentY);
			}

			// If we're not at the target Y, keep going...
			if (mContinueRunning && mScrollToY != mCurrentY) {
				ViewCompat.postOnAnimation(PullableViewBase.this, this);
			} else {
				if (null != mListener) {
					mListener.onSmoothScrollFinished(mCurrentMode);
				}
			}
		}

		public void stop() {
			mContinueRunning = false;
			removeCallbacks(this);
		}
	}

	/**
	 * smooth scroll listener
	 * 
	 * @author Storm
	 * 
	 */
	static interface OnSmoothScrollFinishedListener {
		void onSmoothScrollFinished(Mode mCurrentMode);
	}

	public void setOnSmoothScrollFinishedListener(OnSmoothScrollFinishedListener listener) {
		mOnSmoothScrollFinishedListener = listener;
	}

	// --------------------------------getter and setter-----------------------
	public void setContentBackground(int resId) {
		getContentView().setBackgroundResource(resId);
	}

	public T getContentView() {
		return mContentView;
	}

	public FrameLayout getContentViewWrapper() {
		return mContentViewWrapper;
	}

	public View getHeaderLayout() {
		return mHeaderLayout;
	}

	public View getFooterLayout() {
		return mFooterLayout;
	}

	public int getHeaderSize() {
		if (mHeaderLayout != null) {
			return mHeaderLayout.getHeight();
		}
		return 0;
	}

	public int getFooterSize() {
		if (mFooterLayout != null) {
			return mFooterLayout.getHeight();
		}
		return 0;
	}

	public Mode getCurrentMode() {
		return mCurrentMode;
	}
}
