package com.cst.stormdroid.ui.imageview.touch;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ScaleGestureDetector.OnScaleGestureListener;
import android.view.ViewConfiguration;
import android.view.animation.DecelerateInterpolator;
import android.widget.Scroller;

public class ImageViewTouch extends ImageViewTouchBase {
	private static final boolean DBG = false;
	static final float SCROLL_DELTA_THRESHOLD = 1.0f;
	protected ScaleGestureDetector mScaleDetector;
	protected GestureDetector mGestureDetector;
	protected int mTouchSlop;
	protected float mScaleFactor;
	protected int mDoubleTapDirection;
	protected OnGestureListener mGestureListener;
	protected OnScaleGestureListener mScaleListener;
	protected boolean mDoubleTapEnabled = true;
	protected boolean mScaleEnabled = true;
	protected boolean mScrollEnabled = true;
	private OnImageViewTouchDoubleTapListener mDoubleTapListener;
	private OnImageViewTouchSingleTapListener mSingleTapListener;
	private Context mContext;
	private Float mWidthScale;
	private Float mHeightScale;

	private Scroller mScroller;

	public ImageViewTouch(Context context) {
		super(context);
		this.mContext = context;
		initScroller(context);
	}

	public ImageViewTouch(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.mContext = context;
		initScroller(context);
	}

	// init scroller, add by cst
	private void initScroller(Context context) {
		mScroller = new Scroller(context, new DecelerateInterpolator());
	}

	@Override
	public void computeScroll() {
		if (mScroller.computeScrollOffset()) {
			scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
			postInvalidate();
		}
	}

	@Override
	protected void init() {
		super.init();
		mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
		mGestureListener = getGestureListener();
		mScaleListener = getScaleListener();

		mScaleDetector = new ScaleGestureDetector(getContext(), mScaleListener);
		mGestureDetector = new GestureDetector(getContext(), mGestureListener, null, true);

		mDoubleTapDirection = 1;
	}

	public void setDoubleTapListener(OnImageViewTouchDoubleTapListener listener) {
		mDoubleTapListener = listener;
	}

	public void setSingleTapListener(OnImageViewTouchSingleTapListener listener) {
		mSingleTapListener = listener;
	}

	public void setDoubleTapEnabled(boolean value) {
		mDoubleTapEnabled = value;
	}

	public void setScaleEnabled(boolean value) {
		mScaleEnabled = value;
	}

	public void setScrollEnabled(boolean value) {
		mScrollEnabled = value;
	}

	public boolean getDoubleTapEnabled() {
		return mDoubleTapEnabled;
	}

	protected OnGestureListener getGestureListener() {
		return new GestureListener();
	}

	protected OnScaleGestureListener getScaleListener() {
		return new ScaleListener();
	}

	@Override
	protected void _setImageDrawable(final Drawable drawable, final Matrix initial_matrix, float min_zoom, float max_zoom) {
		super._setImageDrawable(drawable, initial_matrix, min_zoom, max_zoom);
		mScaleFactor = getMaxScale() / 3;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		mScaleDetector.onTouchEvent(event);

		if (!mScaleDetector.isInProgress()) {
			mGestureDetector.onTouchEvent(event);
		}

		int action = event.getAction();
		switch (action & MotionEvent.ACTION_MASK) {
		case MotionEvent.ACTION_UP:
			if (getScale() < getMinScale()) {
				zoomTo(getMinScale(), 50);

				postDelayed(new Runnable() {// add by cst
							@Override
							public void run() {
								scrollToCenter(false);
							}
						}, 70);
			} else {// add by cst
				scrollToCenter(false);
			}

			break;
		}
		return true;
	}

	@Override
	protected void onZoomAnimationCompleted(float scale) {

		if (LOG_ENABLED) {
			Log.d(LOG_TAG, "onZoomAnimationCompleted. scale: " + scale + ", minZoom: " + getMinScale());
		}

		if (scale < getMinScale()) {
			zoomTo(getMinScale(), 50);

			postDelayed(new Runnable() {// add by cst
						@Override
						public void run() {
							scrollToCenter(true);
						}
					}, 70);
		} else {// add by cst
			scrollToCenter(true);
		}
	}

	@Deprecated
	protected float onDoubleTapPost(float scale, float maxZoom) {
		if (mDoubleTapDirection == 1) {
			if ((scale + (mScaleFactor * 2)) <= maxZoom) {
				return scale + mScaleFactor;
			} else {
				mDoubleTapDirection = -1;
				return maxZoom;
			}
		} else {
			mDoubleTapDirection = 1;
			return 1f;
		}
	}

	/**
	 * scroll back add by cst
	 * 
	 * @param e1
	 * @param e2
	 * @param distanceX
	 * @param distanceY
	 * @return
	 */
	public void scrollToCenter(boolean forseFinish) {
		if (mScroller.isFinished() == false) {
			if (forseFinish) {
				mScroller.abortAnimation();
				mScroller.forceFinished(true);
			} else {
				return;
			}
		}

		float[] imageMatrix = new float[9];
		getImageMatrix().getValues(imageMatrix);
		float transX = imageMatrix[Matrix.MTRANS_X];
		float transY = imageMatrix[Matrix.MTRANS_Y];

		Rect rect = new Rect();
		getGlobalVisibleRect(rect);

		final int screenLeft = rect.left;
		final int screenTop = getTop();
		final int screenRight = rect.right;
		final int screenBottom = getBottom();

		final int screenHeight = screenBottom - screenTop;
		final int screenWidth = screenRight - screenLeft;

		// current width and height of bitmap
		final float bHeight = mBitmapRect.bottom - mBitmapRect.top;
		final float bWidth = mBitmapRect.right - mBitmapRect.left;

		// current position of the bitmap
		final float left = transX - getScrollX();
		final float top = transY - getScrollY();

		final float oldLeft = left;
		final float oldRight = (left + bWidth);
		final float oldTop = top;
		final float oldBottom = (top + bHeight);

		float dx = 0;
		float dy = 0;

		if (bWidth < screenWidth) {// back to center
			dx = (0 - getScrollX());
		} else {
			if (oldLeft > screenLeft) {
				dx = (oldLeft - screenLeft);// to left
			}

			if (oldRight < screenRight) {// to right
				dx = -(screenRight - oldRight);
			}
		}

		if (bHeight < screenHeight) {// back to center
			dy = (0 - getScrollY());
		} else {
			if (oldTop > screenTop) {// to top
				dy = (oldTop - screenTop);
			}
			if (oldBottom < screenBottom) {// to bottom
				dy = -(screenBottom - oldBottom);
			}
		}

		if (DBG) {
			Log.i("XXXXXXXXXX", "XX: transX: " + transX + ", transY: " + transY + ", scrollX: " + getScrollX() + ", scrollY: " + getScrollY());
			Log.i("XXXXXXXXXX", "XX: bW: " + bWidth + ", bH " + bHeight + ", left: " + left + ", top: " + top);
			Log.i("XXXXXXXXXX", "XX: sL: " + screenLeft + ", sT: " + screenTop + ", sR: " + screenRight + ", sB:" + screenBottom);
			Log.i("XXXXXXXXXX", "XX: oL:" + oldLeft + ", oT: " + oldTop + ", oR: " + oldRight + ", oB: " + oldBottom + ", dx: " + dx + ", dy: " + dy);
			Log.i("XXXXXXXXXX", "XX:-----------------------------------------------------");
		}

		startSmoothScroll((int) dx, (int) dy, 500, false);
	}

	public void startSmoothScroll(int dx, int dy, int time, boolean restrict) {
		if (dx != 0 || dy != 0) {

			if (restrict) {
				dx = (int) restrictWidth(dx);
				dy = (int) restrictHeight(dy);
			}

			mScroller.startScroll(getScrollX(), getScrollY(), dx, dy, time);
			invalidate();
		}
	}

	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
		if (!mScrollEnabled)
			return false;

		if (e1 == null || e2 == null)
			return false;
		if (e1.getPointerCount() > 1 || e2.getPointerCount() > 1)
			return false;
		if (mScaleDetector.isInProgress())
			return false;
		// if (getScale() == 1f)
		// return false;

		mUserScaled = true;

		distanceX = restrictWidth(distanceX);
		distanceY = restrictHeight(distanceY);

		scrollBy(-distanceX, -distanceY);
		invalidate();
		return true;
	}

	/**
	 * restrict scroll x
	 * @param dx
	 * @return
	 */
	private float restrictWidth(float dx) {
		int width = getWidth();
		int height = getHeight();
		RectF rect = getBitmapRect();

		if (rect != null) {

			float bitmapWidth = rect.right - rect.left;
			float bitmapHeight = rect.bottom - rect.top;

			if (bitmapHeight > height + 1 && bitmapWidth < width + 1) {
				dx = 0;
			}

			return dx;
		}
		return 0;
	}

	/**
	 * restrict scroll y
	 * @param dy
	 * @return
	 */
	private float restrictHeight(float dy) {
		int width = getWidth();
		int height = getHeight();
		RectF rect = getBitmapRect();

		if (rect != null) {
			float bitmapWidth = rect.right - rect.left;
			float bitmapHeight = rect.bottom - rect.top;

			if (bitmapWidth > width + 1 && bitmapHeight < height + 1) {
				dy = 0;
			}

			return dy;
		}
		return 0;
	}

	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
		if (!mScrollEnabled)
			return false;

		if (e1.getPointerCount() > 1 || e2.getPointerCount() > 1)
			return false;
		if (mScaleDetector.isInProgress())
			return false;
		// if (getScale() == 1f)
		// return false;

		float diffX = e2.getX() - e1.getX();
		float diffY = e2.getY() - e1.getY();

		if (Math.abs(velocityX) > 800 || Math.abs(velocityY) > 800) {
			mUserScaled = true;

			if ((diffX != 0 || diffY != 0)) {

				if (!mScroller.isFinished()) {// 停止了动画，我们马上滑倒目标位置 并开始fling的动画
					mScroller.abortAnimation();
				}

				startSmoothScroll(-(int) diffX, -(int) diffY, 300, true);
				// mScroller.startScroll(getScrollX(), getScrollY(), -(int) diffX, -(int) diffY, 300);
				// scrollBy(diffX, diffY, 300);//原始值diffX/2, diffY/2, 300，cst修改
				invalidate();

				postDelayed(new Runnable() {// 上面的滑动有可能超出屏幕，回到中心， add by cst
							@Override
							public void run() {
								scrollToCenter(false);
							}
						}, 325);

				return true;
			}

		}
		return false;
	}

	/**
	 * Determines whether this ImageViewTouch can be scrolled.
	 * 
	 * @param direction
	 *            - positive direction value means scroll from right to left,
	 *            negative value means scroll from left to right
	 * 
	 * @return true if there is some more place to scroll, false - otherwise.
	 */
	public boolean canScroll(int direction, boolean isFirst, boolean isLast) {
		RectF bitmapRect = getBitmapRect();
		updateRect(bitmapRect, mScrollRect);
		Rect imageViewRect = new Rect();
		getGlobalVisibleRect(imageViewRect);

		if (null == bitmapRect) {
			return false;
		}

		if (isFirst && direction > 0) {// first, and scroll to left
			return true;
		}

		if (isLast && direction < 0) {// last and scroll to right
			return true;
		}

		if (bitmapRect.right >= imageViewRect.right) {
			if (direction < 0) {
				return Math.abs(bitmapRect.right - imageViewRect.right) > SCROLL_DELTA_THRESHOLD;
			}
		}

		double bitmapScrollRectDelta = Math.abs(bitmapRect.left - mScrollRect.left);
		return bitmapScrollRectDelta > SCROLL_DELTA_THRESHOLD;
	}

	public class GestureListener extends GestureDetector.SimpleOnGestureListener {
		@Override
		public boolean onSingleTapConfirmed(MotionEvent e) {

			if (null != mSingleTapListener) {
				mSingleTapListener.onSingleTapConfirmed();
			}

			return super.onSingleTapConfirmed(e);
		}

		@Override
		public boolean onDoubleTap(MotionEvent e) {
			Log.i(LOG_TAG, "onDoubleTap. double tap enabled? " + mDoubleTapEnabled);
			if (mDoubleTapEnabled) {
				mUserScaled = true;
				float scale = getScale();
				float targetScale = scale;
				// targetScale = onDoubleTapPost( scale, getMaxScale() );
				targetScale = calScale(scale, getMaxScale());
				targetScale = Math.min(getMaxScale(), Math.max(targetScale, getMinScale()));
				zoomTo(targetScale, e.getX(), e.getY(), DEFAULT_ANIMATION_DURATION);
				invalidate();
			}

			if (null != mDoubleTapListener) {
				mDoubleTapListener.onDoubleTap();
			}

			return super.onDoubleTap(e);
		}

		@Override
		public void onLongPress(MotionEvent e) {
			if (isLongClickable()) {
				if (!mScaleDetector.isInProgress()) {
					setPressed(true);
					performLongClick();
				}
			}
		}

		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
			return ImageViewTouch.this.onScroll(e1, e2, distanceX, distanceY);
		}

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
			return ImageViewTouch.this.onFling(e1, e2, velocityX, velocityY);
		}
	}

	// 计算图片正好铺满的scala
	private float calScale(float scale, float maxZoom) {
		if (mDoubleTapDirection == 1) {
			if (mWidthScale == null)
				mWidthScale = getWidthScale();
			if (mHeightScale == null)
				mHeightScale = getHeightScale();
			// Log.e("XXXXXXXXXXXX", scale + "," + mWidthScale + "," + mHeightScale + "," + maxZoom);

			float nScale = Math.max(mWidthScale, mHeightScale);

			if (nScale < maxZoom && scale < nScale) {
				return nScale;
			}

			mDoubleTapDirection = -1;
			return maxZoom;
		} else {
			mDoubleTapDirection = 1;
			return 1f;
		}

	}

	/**
	 * 宽度填满的scale
	 * 
	 * @return
	 */
	private float getWidthScale() {
		int width = getWidth();

		RectF rect = getBitmapRect();
		float bitmapWidth = rect.right - rect.left;

		// Log.e("XXXXXXXXXXXX", "width:" + width + ", bWidth:" + bitmapWidth);

		float fw = 0;

		if (width < bitmapWidth) {// 比屏幕大
			fw = bitmapWidth / width;
		} else {// 原图比屏幕小
			fw = width / bitmapWidth;
		}

		return fw;
	}

	/**
	 * 宽度填满的scale
	 * 
	 * @return
	 */
	private float getHeightScale() {
		int height = getHeight();

		RectF rect = getBitmapRect();
		float bitmapHeight = rect.bottom - rect.top;

		// Log.e("XXXXXXXXXXXX", "height:" + height + ", bHeight:" + bitmapHeight);

		float fh = 0;

		if (height < bitmapHeight) {// 原图比屏幕大
			fh = bitmapHeight / height;
		} else {// 原图比图片小
			fh = height / bitmapHeight;
		}

		return fh;
	}

	public class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {

		protected boolean mScaled = false;

		@Override
		public boolean onScale(ScaleGestureDetector detector) {
			float span = detector.getCurrentSpan() - detector.getPreviousSpan();
			float targetScale = getScale() * detector.getScaleFactor();

			if (mScaleEnabled) {
				if (mScaled && span != 0) {
					mUserScaled = true;
					targetScale = Math.min(getMaxScale(), Math.max(targetScale, getMinScale() - 0.1f));
					zoomTo(targetScale, detector.getFocusX(), detector.getFocusY());
					mDoubleTapDirection = 1;
					invalidate();
					return true;
				}

				// This is to prevent a glitch the first time
				// image is scaled.
				if (!mScaled)
					mScaled = true;
			}
			return true;
		}
	}

	public interface OnImageViewTouchDoubleTapListener {

		void onDoubleTap();
	}

	public interface OnImageViewTouchSingleTapListener {

		void onSingleTapConfirmed();
	}
}
