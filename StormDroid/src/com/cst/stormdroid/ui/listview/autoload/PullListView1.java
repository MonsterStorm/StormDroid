package com.cst.stormdroid.ui.listview.autoload;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.Adapter;
import android.widget.ListView;

public class PullListView1 extends ListView {
	static final float FRICTION = 2.0f;
	public static final int SMOOTH_SCROLL_DURATION_MS = 200;
	private int mTouchSlop;
	private float mLastMotionX, mLastMotionY;
	private float mInitialMotionX, mInitialMotionY;
	private boolean mIsBeingDragged = false;
	private Mode mMode = Mode.getDefault();
	private Mode mCurrentMode;
	private Interpolator mScrollAnimationInterpolator;
	private SmoothScrollRunnable mCurrentSmoothScrollRunnable;

	public PullListView1(Context context) {
		super(context);
		init(context, null);
	}

	public PullListView1(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs);
	}

	private void init(Context context, AttributeSet attrs) {
		ViewConfiguration config = ViewConfiguration.get(context);
		mTouchSlop = config.getScaledTouchSlop();
		updateUIForMode();
	}

	@Override
	public final boolean onInterceptTouchEvent(MotionEvent event) {
		final int action = event.getAction();

		/*if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
			mIsBeingDragged = false;
			return false;
		}

		if (action != MotionEvent.ACTION_DOWN && mIsBeingDragged) {
			return true;
		}*/

		switch (action) {
		/*case MotionEvent.ACTION_MOVE: {

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
		}*/
		case MotionEvent.ACTION_DOWN: {
			if (isReadyForPull()) {
				mLastMotionY = mInitialMotionY = event.getY();
				mLastMotionX = mInitialMotionX = event.getX();
				mIsBeingDragged = false;
			}
			break;
		}
		}

		return super.onInterceptTouchEvent(event);
		/*final int action = event.getAction();

		if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
			mIsBeingDragged = false;
			Log.e("XXXXXXXX", "onInterceptTouchEvent111");
			return false;
		}

		if (action != MotionEvent.ACTION_DOWN && mIsBeingDragged) {
			Log.e("XXXXXXXX", "onInterceptTouchEvent222");
			return true;
		}

		switch (action) {
		case MotionEvent.ACTION_MOVE: {
			Log.e("XXXXXXXXXXXXXXXX", "isReadyForPull" + isReadyForPull());
			if (isReadyForPull()) {
				final float y = event.getY(), x = event.getX();
				final float distanceY = y - mLastMotionY;
				final float distanceX = x - mLastMotionX;
				final float absDistanceY = Math.abs(distanceY);

				if (absDistanceY > mTouchSlop && (absDistanceY > Math.abs(distanceX))) {//如果移动距离超过最小移动距离，且y移动比x移动大，则表示是上下移动
					if (mMode.showHeaderLoadingLayout() && distanceY >= 1f && isReadyForPullStart()) {//下滑动,y>lastY
						mLastMotionY = y;
						mLastMotionX = x;
						mIsBeingDragged = true;
						if (mMode == Mode.BOTH) {
							mCurrentMode = Mode.PULL_FROM_START;
						}
					} else if (mMode.showFooterLoadingLayout() && distanceY <= -1f && isReadyForPullEnd()) {//上滑动,y<lastY
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
		}
		}

		return mIsBeingDragged;*/

	}

	@Override
	public final boolean onTouchEvent(MotionEvent event) {
		/*if (event.getAction() == MotionEvent.ACTION_DOWN && event.getEdgeFlags() != 0) {
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
		return false;*/
		if (event.getAction() == MotionEvent.ACTION_DOWN && event.getEdgeFlags() != 0) {
			Log.e("XXXXXXXXXX", "1111");
			return super.onTouchEvent(event);
		}

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN: {
			Log.e("XXXXXXXXXX", "2222" + isReadyForPull());
			if (isReadyForPull()) {
				mLastMotionY = mInitialMotionY = event.getY();
				mLastMotionX = mInitialMotionX = event.getX();
				mIsBeingDragged = false;
				return true;
			}
			break;
		}
		case MotionEvent.ACTION_MOVE: {
			Log.e("XXXXXXXXXX", "55555" + mIsBeingDragged + "," + isReadyForPull());
			if (mIsBeingDragged == false && isReadyForPull()) {
				final float y = event.getY(), x = event.getX();
				final float distanceY = y - mLastMotionY;
				final float distanceX = x - mLastMotionX;
				final float absDistanceY = Math.abs(distanceY);
				Log.e("XXXXXXXXX", y + "," + mLastMotionY + "," + x + "," + mLastMotionX);
				Log.e("XXXXXXXXXX", absDistanceY + "," + mTouchSlop + "," + Math.abs(distanceX) + "," + mMode.showHeaderLoadingLayout() + "," + mMode.showFooterLoadingLayout() +","+ distanceY + "," + isReadyForPullStart() + "," + isReadyForPullEnd());

				if (absDistanceY > mTouchSlop && (absDistanceY > Math.abs(distanceX))) {//如果移动距离超过最小移动距离，且y移动比x移动大，则表示是上下移动
					if (mMode.showHeaderLoadingLayout() && distanceY >= 1f && isReadyForPullStart()) {//下滑动,y>lastY
						mLastMotionY = y;
						mLastMotionX = x;
						mIsBeingDragged = true;
						if (mMode == Mode.BOTH) {
							mCurrentMode = Mode.PULL_FROM_START;
						}
					} else if (mMode.showFooterLoadingLayout() && distanceY <= -1f && isReadyForPullEnd()) {//上滑动,y<lastY
						mLastMotionY = y;
						mLastMotionX = x;
						mIsBeingDragged = true;
						if (mMode == Mode.BOTH) {
							mCurrentMode = Mode.PULL_FROM_END;
						}
					}
				}
			}
			if (mIsBeingDragged) {
				mLastMotionY = event.getY();
				mLastMotionX = event.getX();
				pullEvent();
				Log.e("XXXXXXXXXX", "3333");
				return true;
			}
			break;
		}
		case MotionEvent.ACTION_CANCEL:
		case MotionEvent.ACTION_UP: {
			Log.e("XXXXXXXXXX", "4444" + mIsBeingDragged + "," + mLastMotionY);
			if (mIsBeingDragged) {
				mIsBeingDragged = false;
				onReset();
				return true;
			}
			break;
		}
		}
		Log.e("XXXXXXXXXX", "5555");
		return super.onTouchEvent(event);
	}

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

	protected boolean isReadyForPullStart() {
		return isFirstItemVisible();
	}

	protected boolean isReadyForPullEnd() {
		return isLastItemVisible();
	}

	private boolean isFirstItemVisible() {
		final Adapter adapter = this.getAdapter();

		if (null == adapter || adapter.isEmpty()) {
			return true;
		} else {
			int headerViewCount = this.getHeaderViewsCount();
			if (this.getFirstVisiblePosition() <= headerViewCount) {
				final View firstVisibleChild = this.getChildAt(0);
				if (firstVisibleChild != null) {
					int top1 = firstVisibleChild.getTop();
					int top2 = this.getTop();
//					Log.e("isFirstItemVisible", "XXXXXXXXXXXXXXXXXX" + top1 + "," + top2);
					return top1 <= top2;
				}
			}
		}
		return false;
	}

	private boolean isLastItemVisible() {
		final Adapter adapter = this.getAdapter();

		if (null == adapter || adapter.isEmpty()) {
			return true;
		} else {
			int footerViewCount = this.getFooterViewsCount();
			final int lastItemPosition = this.getCount() - 1;
			if (this.getLastVisiblePosition() >= lastItemPosition - footerViewCount) {
				final int childIndex = this.getLastVisiblePosition() - this.getFirstVisiblePosition();
				final View lastVisibleChild = this.getChildAt(childIndex);
				if (lastVisibleChild != null) {
					int bottom1 = lastVisibleChild.getBottom();
					int bottom2 = this.getBottom();
//					Log.e("isLastItemVisible", "XXXXXXXXXXXXXXXXXX" + bottom1 + "," + bottom2);
					return bottom1 >= bottom2;
				}
			}
		}

		return false;
	}

	protected final int getFooterSize() {
		return 0;
	}

	protected final int getHeaderSize() {
		return 0;
	}

	protected void onReset() {////////////////////
		mIsBeingDragged = false;
		smoothScrollTo(0);
	}

	@Override
	protected final void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);

		// We need to update the header/footer when our size changes
		refreshLoadingViewsSize();

		post(new Runnable() {
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
		//		if (mMode.showHeaderLoadingLayout()) {
		//			pTop = -maximumPullScroll;
		//		} else {
		pTop = 0;
		//		}

		//		if (mMode.showFooterLoadingLayout()) {
		//			pBottom = -maximumPullScroll;
		//		} else {
		pBottom = 0;
		//		}

		setPadding(pLeft, pTop, pRight, pBottom);
	}

	protected final void setHeaderScroll(int value) {
		final int maximumPullScroll = getMaximumPullScroll();
		value = Math.min(maximumPullScroll, Math.max(-maximumPullScroll, value));
		scrollTo(0, value);
	}

	protected final void smoothScrollTo(int scrollValue) {
		smoothScrollTo(scrollValue, 200);
	}

	protected final void smoothScrollTo(int scrollValue, OnSmoothScrollFinishedListener listener) {
		smoothScrollTo(scrollValue, 200, 0, listener);
	}

	protected void updateUIForMode() {
		refreshLoadingViewsSize();
		mCurrentMode = (mMode != Mode.BOTH) ? mMode : Mode.PULL_FROM_END;
	}

	private void pullEvent() {
		final int newScrollValue;
		final float initialMotionValue, lastMotionValue;

		initialMotionValue = mInitialMotionY;
		lastMotionValue = mLastMotionY;

		switch (mCurrentMode) {
		case PULL_FROM_END:
			newScrollValue = Math.round(Math.max(initialMotionValue - lastMotionValue, 0) / FRICTION);
			break;
		case PULL_FROM_START:
		default:
			newScrollValue = Math.round(Math.min(initialMotionValue - lastMotionValue, 0) / FRICTION);
			break;
		}

		setHeaderScroll(newScrollValue);
	}

	private int getMaximumPullScroll() {
		return Math.round(getHeight() / FRICTION);
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
				ViewCompat.postOnAnimation(PullListView1.this, this);
			} else {
				if (null != mListener) {
					mListener.onSmoothScrollFinished();
				}
			}
		}

		public void stop() {
			mContinueRunning = false;
			removeCallbacks(this);
		}
	}

	static interface OnSmoothScrollFinishedListener {
		void onSmoothScrollFinished();
	}

}
