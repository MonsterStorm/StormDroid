package com.cst.stormdroid.ui.listview.autoload;
//package cn.dface.ui.listview.autoload;
//
//import android.content.Context;
//import android.content.res.TypedArray;
//import android.graphics.Rect;
//import android.util.AttributeSet;
//import android.view.GestureDetector;
//import android.view.GestureDetector.OnGestureListener;
//import android.view.LayoutInflater;
//import android.view.MotionEvent;
//import android.view.View;
//import android.view.animation.DecelerateInterpolator;
//import android.view.animation.Interpolator;
//import android.view.animation.TranslateAnimation;
//import android.widget.ListView;
//import android.widget.Scroller;
//import cn.dface.R;
//import cn.dface.ui.listview.autoload.AutoLoadInfoView.InfoViewState;
//
///**
// * list view that auto load when scroll
// * 
// * @author Storm
// * 
// */
//public class AutoLoadListView1 extends ListView{
//	public static enum State {
//		NORMAL, HEAD_LOADING, FOOT_LOADING
//	};
//
//	private Context mContext;
//	private AutoLoadHeader mHeader;
//	private AutoLoadFooter mFooter;
//	private LayoutInflater mInflater;
//	private AutoLoadOnScrollListener mAutoLoadOnScrollListener;
//	private State mState;
//
//	//scroll
//	private GestureDetector mGestureDetector;
//	private Scroller mScroller;
//	private Interpolator mScrollInterpolator = new DecelerateInterpolator();
//
//	//------------------styleable-----------------------
//	//是否初始化时显示头部
//	private boolean enableHeader = true;
//	private boolean enableFooter = true;
//	//headerText
//	private int headerText = 0;
//	private int footerText = 0;
//	//无加载动作时是否显示头部
//	private boolean simpleHeader = false;
//	private boolean simpleFooter = false;
//
//	//---------------------------------------------------
//
//	public interface OnLoadingListener {
//		public void onLoading(AutoLoadListView1 autoLoadListView);
//	}
//
//	public AutoLoadListView1(Context context) {
//		super(context);
//		init(context, null);
//	}
//
//	public AutoLoadListView1(Context context, AttributeSet attrs) {
//		super(context, attrs);
//		init(context, null);
//	}
//
//	public AutoLoadListView1(Context context, AttributeSet attrs, int defStyle) {
//		super(context, attrs, defStyle);
//		init(context, null);
//	}
//
//	/**
//	 * init listview
//	 * 
//	 * @param context
//	 * @param attrs
//	 */
//	private void init(Context context, AttributeSet attrs) {
//		this.mContext = context;
//		this.mInflater = LayoutInflater.from(context);
//
//		mHeader = createHeader(attrs, R.layout.listtop_autoload_default);
//
//		mFooter = createFooter(attrs, R.layout.listbottom_autoload_default);
//
//		addHeaderView(mHeader);
//		addFooterView(mFooter);
//
//		initVisibility(attrs);
//
//		//create on scorll listener
//		mAutoLoadOnScrollListener = new AutoLoadOnScrollListener(context, this);
//		setOnScrollListenerInternal();
//
//		mState = State.NORMAL;
//
//		mScroller = new Scroller(mContext, mScrollInterpolator);
//		mGestureDetector = new GestureDetector(mContext, new MyOnGestureListener());
//	}
//
//	public void setState(State state) {
//		this.mState = state;
//		switch (state) {
//		case NORMAL:
//			mHeader.setState(InfoViewState.NORMAL);
//			mFooter.setState(InfoViewState.NORMAL);
//			break;
//		case HEAD_LOADING:
//			mHeader.setState(InfoViewState.LOADING);
//			mFooter.setState(InfoViewState.NORMAL);
//			break;
//		case FOOT_LOADING:
//			mHeader.setState(InfoViewState.NORMAL);
//			mFooter.setState(InfoViewState.LOADING);
//			break;
//		}
//	}
//
//	public void showHeader() {
//		mHeader.setVisibility(View.VISIBLE);
//	}
//
//	public void hideHeader() {
//		mHeader.setVisibility(View.GONE);
//	}
//
//	public void showFooter() {
//		mFooter.setVisibility(View.VISIBLE);
//	}
//
//	public void hideFooter() {
//		mFooter.setVisibility(View.GONE);
//	}
//
//	public void onLoadingFinish() {
//		setState(State.NORMAL);
//	}
//
//	/**
//	 * create header
//	 * 
//	 * @param attrs
//	 * @param resLayout
//	 * @return
//	 */
//	protected AutoLoadHeader createHeader(AttributeSet attrs, int resLayout) {
//		try {
//			TypedArray array = mContext.obtainStyledAttributes(attrs, R.styleable.AutoLoadListView);
//			headerText = array.getResourceId(R.styleable.AutoLoadListView_headerText, R.string.info_autoload_listview_header_default);
//			simpleHeader = array.getBoolean(R.styleable.AutoLoadListView_simpleHeader, false);
//			array.recycle();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		AutoLoadHeader header = new AutoLoadHeader(mContext, simpleHeader);
//		header.setInfoText(getResources().getString(headerText));
//		return header;
//	}
//
//	/**
//	 * create footer
//	 * 
//	 * @param attrs
//	 * @param resLayout
//	 * @return
//	 */
//	protected AutoLoadFooter createFooter(AttributeSet attrs, int resLayout) {
//		try {
//			TypedArray array = mContext.obtainStyledAttributes(attrs, R.styleable.AutoLoadListView);
//			footerText = array.getResourceId(R.styleable.AutoLoadListView_footerText, R.string.info_autoload_listview_footer_default);
//			simpleFooter = array.getBoolean(R.styleable.AutoLoadListView_simpleFooter, false);
//			array.recycle();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		AutoLoadFooter footer = new AutoLoadFooter(mContext, simpleFooter);
//		footer.setInfoText(getResources().getString(footerText));
//		return footer;
//	}
//
//	/**
//	 * is this listview can load right now
//	 * 
//	 * @return
//	 */
//	protected boolean canLoad() {
//		return mState == State.NORMAL;
//	}
//
//	/**
//	 * is loading
//	 * 
//	 * @return
//	 */
//	protected boolean isLoading() {
//		return mState == State.HEAD_LOADING || mState == State.FOOT_LOADING;
//	}
//
//	/**
//	 * is head loading right now
//	 * 
//	 * @return
//	 */
//	protected boolean isHeadLoading() {
//		return mState == State.HEAD_LOADING;
//	}
//
//	/**
//	 * is foot loading right now
//	 * 
//	 * @return
//	 */
//	protected boolean isFootLoading() {
//		return mState == State.FOOT_LOADING;
//	}
//
//	public void startHeadLoading() {
//		setState(State.HEAD_LOADING);
//	}
//
//	public void startFootLoading() {
//		setState(State.FOOT_LOADING);
//	}
//
//	/**
//	 * init visibility of views
//	 * 
//	 * @param attrs
//	 */
//	private void initVisibility(AttributeSet attrs) {
//		try {
//			TypedArray array = mContext.obtainStyledAttributes(attrs, R.styleable.AutoLoadListView);
//			enableHeader = array.getBoolean(R.styleable.AutoLoadListView_enableHeader, true);
//			enableFooter = array.getBoolean(R.styleable.AutoLoadListView_enableFooter, true);
//			array.recycle();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		if (enableHeader) {
//			mHeader.setVisibility(View.VISIBLE);
//		} else {
//			mHeader.setVisibility(View.GONE);
//		}
//		if (enableFooter) {
//			mFooter.setVisibility(View.VISIBLE);
//		} else {
//			mFooter.setVisibility(View.GONE);
//		}
//	}
//
//	private int getHeaderSize() {
//		return mHeader.getContentSize();
//	}
//
//	private int getFooterSize() {
//		return mFooter.getContentSize();
//	}
//
//	//----------------------------getters and setters---------------------------------
//	@Override
//	public void setOnScrollListener(OnScrollListener l) {
//		mAutoLoadOnScrollListener.setOnScrollListener(l);
//	}
//
//	private void setOnScrollListenerInternal() {
//		super.setOnScrollListener(mAutoLoadOnScrollListener);
//	}
//
//	public void setOnHeadLoadingListener(OnLoadingListener onLoadingListener) {
//		mAutoLoadOnScrollListener.setOnHeadLoadingListener(onLoadingListener);
//	}
//
//	public void setOnFootLoadingListener(OnLoadingListener onLoadingListener) {
//		mAutoLoadOnScrollListener.setOnFootLoadingListener(onLoadingListener);
//	}
//
//	public void setHeaderOnClickListener(OnClickListener headerOnClickListener) {
//		if (mHeader != null) {
//			mHeader.setOnClickListener(headerOnClickListener);
//		}
//	}
//
//	public void setFooterOnClickListener(OnClickListener footerOnClickListener) {
//		if (mFooter != null) {
//			mFooter.setOnClickListener(footerOnClickListener);
//		}
//	}
//
//	private float mDownX;
//	private float mDownY;
//	private float mLastX;
//	private float mLastY;
//	private boolean isPullDown;
//	private boolean isPullUp;
//	private boolean isScrollToTop;
//	private boolean isScrollFarTop;
//
//
//	/*@Override
//	public boolean onTouchEvent(MotionEvent ev) {
//		if (mScroller.computeScrollOffset()) {//we are scrolling
//			return true;
//		}
//
//		switch (ev.getAction()) {
//		case MotionEvent.ACTION_DOWN:
//			mDownX = ev.getX();
//			mDownY = ev.getY();
//			return true;
//		case MotionEvent.ACTION_MOVE:
//			mLastX = ev.getX();
//			mLastY = ev.getY();
//			//pullEvent();
//			return true;
//		case MotionEvent.ACTION_CANCEL:
//		case MotionEvent.ACTION_UP:
//			float mUpX = ev.getX();
//			float mUpY = ev.getY();
//			//			mScroller.startScroll((int)mDownX, (int)mDownY, (int)mUpX, (int)mUpY, 200);
//			return true;
//		}
//
//		return false;
//	}*/
//
//	private void pullEvent() {
//		final int newScrollValue;
//		final int itemDimension;
//		final float initialMotionValue, lastMotionValue;
//
//		initialMotionValue = mDownX;
//		lastMotionValue = mDownY;
//
//		newScrollValue = Math.round(Math.min(initialMotionValue - lastMotionValue, 0) / 2.0f);
//		itemDimension = getHeaderSize();
//
//		this.scrollTo(0, newScrollValue);
//
//		if (newScrollValue != 0 && !isLoading()) {
//			float scale = Math.abs(newScrollValue) / (float) itemDimension;
//
//		}
//	}
//
//	
//	private boolean outBound = false;
//	private int distance;
//	private int firstOut;
//	class MyOnGestureListener implements OnGestureListener{
//		@Override
//		public boolean onSingleTapUp(MotionEvent e) {
//			return false;
//		}
//
//		@Override
//		public void onShowPress(MotionEvent e) {
//		}
//		
//		/**
//		 * 手势滑动的时候触发
//		 */
//		@Override
//		public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
//			int firstPos = getFirstVisiblePosition();
//			int lastPos = getLastVisiblePosition();
//			int itemCount = getCount();
//			// outbound Top   
//			if (outBound && firstPos != 0 && lastPos != (itemCount - 1)) {
//				scrollTo(0, 0);
//				return false;
//			}
//			View firstView = getChildAt(firstPos);
//			if (!outBound)
//				firstOut = (int) e2.getRawY();
//			if (firstView != null && (outBound || (firstPos == 0 && firstView.getTop() == 0 && distanceY < 0))) {
//				// Record the length of each slide   
//				distance = firstOut - (int) e2.getRawY();
//				scrollTo(0, distance / 2);
//				return true;
//			}
//			// outbound Bottom   
//			return false;
//		}
//
//		@Override
//		public void onLongPress(MotionEvent e) {
//		}
//
//		@Override
//		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
//			return false;
//		}
//
//		@Override
//		public boolean onDown(MotionEvent e) {
//			return false;
//		}
//	}
//
//	public boolean dispatchTouchEvent(MotionEvent event) {
//		int act = event.getAction();
//		if ((act == MotionEvent.ACTION_UP || act == MotionEvent.ACTION_CANCEL) && outBound) {
//			outBound = false;
//			// scroll back   
//		}
//		if (!mGestureDetector.onTouchEvent(event)) {//return if gestureDetector consume the event
//			outBound = false;
//		} else {
//			outBound = true;
//		}
//		Rect rect = new Rect();
//		getLocalVisibleRect(rect);
//		TranslateAnimation am = new TranslateAnimation(0, 0, -rect.top, 0);
//		am.setDuration(300);
//		startAnimation(am);
//		scrollTo(0, 0);
//		return super.dispatchTouchEvent(event);
//	}
//
//}
