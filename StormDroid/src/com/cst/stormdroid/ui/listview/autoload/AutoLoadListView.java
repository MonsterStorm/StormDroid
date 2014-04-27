package com.cst.stormdroid.ui.listview.autoload;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;

import com.cst.stormdroid.R;
import com.cst.stormdroid.ui.listview.autoload.AutoLoadInfoView.InfoViewState;

/**
 * 拉动加载的listview
 * 
 * @author Storm
 * 
 */
public class AutoLoadListView extends PullableListView {
	private static enum State {
		NORMAL, HEAD_LOADING, FOOT_LOADING, BOTH
	};

	public interface OnLoadingListener {
		public void onLoading(AutoLoadListView autoLoadListView);
	}

	private AutoLoadOnScrollListener mAutoLoadOnScrollListener;
	private OnSmoothScrollFinishedListener mOnSmoothScrollFinishedListener;
	private OnLoadingListener mHeadLoadingListener;
	private OnLoadingListener mFootLoadingListener;
	private State mState;

	private Context mContext;

	//----------------属性---------------------
	private boolean enableHeader;
	private boolean enableFooter;
	//header Layout
	private int headerLayout;
	private int footerLayout;
	//headerText
	private int headerText;
	private int footerText;
	//无加载动作时是否显示头部
	private boolean simpleHeader;
	private boolean simpleFooter;
	//是否允许同时加载头部跟底部
	private boolean enableBothLoad;
	//是否允许点击加载
	private boolean headClickLoad;
	private boolean footClickLoad;
	//是否允许下拉加载
	private boolean headPullLoad;
	private boolean footPullLoad;
	//是否允许滑动到顶部或底部自动加载
	private boolean headAutoLoad;
	private boolean footAutoLoad;
	//show divider
	private boolean showFooterDivider;
	//dummy
	private int dummyHeaderHeight;
	private int dummyFooterHeight;
	private View dummyHeader;
	private View dummyFooter;
	private boolean dummyHeaderAtTop;//是否在最顶部
	private boolean dummyFooterAtBottom;//是否在最底部
	//is header footer removed
	private boolean isHeaderRemoved = false;
	private boolean isFooterRemoved = false;

	public AutoLoadListView(Context context) {
		super(context);
		init(context, null);
	}

	public AutoLoadListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs);
	}

	public void init(Context context, AttributeSet attrs) {
		mContext = context;

		//create on scorll listener
		mAutoLoadOnScrollListener = new AutoLoadOnScrollListener(context, this);
		setOnScrollListenerInternal(mAutoLoadOnScrollListener);

		mOnSmoothScrollFinishedListener = new MyOnSmoothScrollListener();
		setOnSmoothScrollFinishedListener(mOnSmoothScrollFinishedListener);

		mState = State.NORMAL;
	}

	/**
	 * set state of this view
	 * 
	 * @param state
	 */
	public void setState(State state) {
		if (mState == state)
			return;

		if (enableBothLoad == true && isLoading() && state != State.NORMAL) {//如果允许加载，且当前正在加载，且state不是Normal
			state = State.BOTH;
		}

		AutoLoadInfoView mHeader = (AutoLoadInfoView) getHeaderLayout();
		AutoLoadInfoView mFooter = (AutoLoadInfoView) getFooterLayout();
		this.mState = state;

		switch (state) {
		case NORMAL:
			mHeader.setState(InfoViewState.NORMAL);
			mFooter.setState(InfoViewState.NORMAL);
			break;
		case HEAD_LOADING:
			mHeader.setState(InfoViewState.LOADING);
			mFooter.setState(InfoViewState.NORMAL);
			break;
		case FOOT_LOADING:
			mHeader.setState(InfoViewState.NORMAL);
			mFooter.setState(InfoViewState.LOADING);
			break;
		case BOTH:
			mHeader.setState(InfoViewState.LOADING);
			mFooter.setState(InfoViewState.LOADING);
			break;
		}
	}
	
	@Override
	public int getHeaderSize() {
		int height = super.getHeaderSize();
		if(dummyHeader != null){
			height += dummyHeader.getHeight();
		}
		return height;
	}
	
	@Override
	public int getFooterSize() {
		int height = super.getFooterSize();
		if(dummyFooter != null){
			height += dummyFooter.getHeight();
		}
		return height;
	}

	/**
	 * loading finish
	 */
	public void onLoadingFinish() {
		//		onReset();//回退回去
		setState(State.NORMAL);
	}

	/**
	 * loading finish
	 * 
	 * @param remove
	 */
	public void onLoadingFinish(boolean remove) {
		onLoadingFinish();
		if (remove) {
			removeHeaderLayout();
			removeFooterLayout();
		}
	}

	/**
	 * head loading finish
	 */
	public void onHeadLoadingFinish() {
		if (mState == State.BOTH) {
			setState(State.FOOT_LOADING);
		} else {
			setState(State.NORMAL);
		}
	}

	/**
	 * head loading finish
	 * 
	 * @param remove
	 */
	public void onHeadLoadingFinish(boolean remove) {
		onHeadLoadingFinish();
		if (remove) {
			removeHeaderLayout();
		}
	}

	/**
	 * foot loading finish
	 */
	public void onFootLoadingFinish() {
		if (mState == State.BOTH) {
			setState(State.HEAD_LOADING);
		} else {
			setState(State.NORMAL);
		}
	}

	/**
	 * head loading finish
	 * 
	 * @param remove
	 */
	public void onFootLoadingFinish(boolean remove) {
		onFootLoadingFinish();
		if (remove) {
			removeFooterLayout();
		}
	}

	/**
	 * start head loading
	 */
	public void startHeadLoading() {
		if (enableHeader && mHeadLoadingListener != null && isHeadLoading() == false) {
			setState(State.HEAD_LOADING);
			mHeadLoadingListener.onLoading(this);
		}
	}

	/**
	 * start foot loading
	 */
	public void startFootLoading() {
		if (enableFooter && mFootLoadingListener != null && isFootLoading() == false) {
			setState(State.FOOT_LOADING);
			mFootLoadingListener.onLoading(this);
		}
	}

	@Override
	public View createHeaderView(Context context, Mode mode, TypedArray array) {
		try {
			headerLayout = array.getResourceId(R.styleable.AutoLoadListView_autoLoadHeaderLayout, R.layout.listtop_autoload_default);
			enableHeader = array.getBoolean(R.styleable.AutoLoadListView_enableHeader, false);
			headAutoLoad = array.getBoolean(R.styleable.AutoLoadListView_headAutoLoad, true);
			headClickLoad = array.getBoolean(R.styleable.AutoLoadListView_headClickLoad, true);
			headPullLoad = array.getBoolean(R.styleable.AutoLoadListView_headPullLoad, true);
			headerText = array.getResourceId(R.styleable.AutoLoadListView_headerText, R.string.info_autoload_listview_header_default);
			simpleHeader = array.getBoolean(R.styleable.AutoLoadListView_simpleHeader, false);

			if (array.hasValue(R.styleable.AutoLoadListView_dummyHeaderHeight)) {
				dummyHeaderHeight = array.getDimensionPixelSize(R.styleable.AutoLoadListView_dummyHeaderHeight, 0);
				dummyHeaderAtTop = array.getBoolean(R.styleable.AutoLoadListView_dummyHeaderAtTop, false);//默认在头部第一个
				dummyHeader = new View(getContext());
				AbsListView.LayoutParams lp = new AbsListView.LayoutParams(LayoutParams.MATCH_PARENT, dummyHeaderHeight);
				dummyHeader.setLayoutParams(lp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		AutoLoadHeader header = new AutoLoadHeader(context, headerLayout, simpleHeader);
		header.setInfoText(getResources().getString(headerText));
		return header;
	}

	@Override
	public View createFooterView(Context context, Mode mode, TypedArray array) {
		try {
			footerLayout = array.getResourceId(R.styleable.AutoLoadListView_autoLoadFooterLayout, R.layout.listbottom_autoload_default);
			enableFooter = array.getBoolean(R.styleable.AutoLoadListView_enableFooter, false);
			footAutoLoad = array.getBoolean(R.styleable.AutoLoadListView_footAutoLoad, false);
			footClickLoad = array.getBoolean(R.styleable.AutoLoadListView_footClickLoad, true);
			footPullLoad = array.getBoolean(R.styleable.AutoLoadListView_footPullLoad, true);
			footerText = array.getResourceId(R.styleable.AutoLoadListView_footerText, R.string.info_autoload_listview_footer_default);
			simpleFooter = array.getBoolean(R.styleable.AutoLoadListView_simpleFooter, false);
			showFooterDivider = array.getBoolean(R.styleable.AutoLoadListView_showFooterDivider, false);

			if (array.hasValue(R.styleable.AutoLoadListView_dummyFooterHeight)) {
				dummyFooterHeight = array.getDimensionPixelSize(R.styleable.AutoLoadListView_dummyFooterHeight, 0);
				dummyFooterAtBottom = array.getBoolean(R.styleable.AutoLoadListView_dummyFooterAtBottom, false);//默认在底部第一个
				dummyFooter = new View(getContext());
				AbsListView.LayoutParams lp = new AbsListView.LayoutParams(LayoutParams.MATCH_PARENT, dummyFooterHeight);
				dummyFooter.setLayoutParams(lp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		AutoLoadFooter footer = new AutoLoadFooter(context, footerLayout, simpleFooter);
		footer.setInfoText(getResources().getString(footerText));

		if (showFooterDivider == true) {
			footer.setDividerVisibility(View.VISIBLE);
		} else {
			footer.setDividerVisibility(View.GONE);
		}

		return footer;
	}

	//-----------------------------------getter and setter------------------------------------
	public View getDummyHeader() {
		return dummyHeader;
	}

	public View getDummyFooter() {
		return dummyFooter;
	}

	public void hideHeader() {
		View v = getHeaderLayout();
		if (v != null) {
			enableHeader = false;
			super.removeHeaderLayout();//不移出dummy Header
		}
	}

	public void hideFooter() {
		View v = getFooterLayout();
		if (v != null) {
			enableFooter = false;
			super.removeFooterLayout();//不移除dummy Footer
		}
	}

	public void setHeaderText(CharSequence txt) {
		View v = getHeaderLayout();
		if (v != null) {
			AutoLoadInfoView ativ = (AutoLoadInfoView) v;
			ativ.setInfoText(txt);
		}
	}

	public void setFooterText(CharSequence txt) {
		View v = getFooterLayout();
		if (v != null) {
			AutoLoadInfoView ativ = (AutoLoadInfoView) v;
			ativ.setInfoText(txt);
		}
	}

	@Override
	public void removeHeaderLayout() {
		isHeaderRemoved = true;
		super.removeHeaderLayout();

		/*if (dummyHeader != null) {//do not remove dummy
			mContentView.removeHeaderView(dummyHeader);
		}*/
	}

	@Override
	public void removeFooterLayout() {
		isFooterRemoved = true;
		super.removeFooterLayout();
		/*if (dummyFooter != null) {//do not remove dummy
			mContentView.removeHeaderView(dummyFooter);
		}*/
	}

	@Override
	public void addHeaderLayout(LayoutParams lp) {
		if (dummyHeaderAtTop == false) {//先header，后dummy
			if (enableHeader) {
				super.addHeaderLayout(lp);
				isHeaderRemoved = false;
			}
			if (dummyHeader != null) {
				mContentView.addHeaderView(dummyHeader);
			}
		} else {//先dummy，后header
			if (dummyHeader != null) {
				mContentView.addHeaderView(dummyHeader);
			}
			if (enableHeader) {
				super.addHeaderLayout(lp);
				isHeaderRemoved = false;
			}
		}
	}

	@Override
	public void addFooterLayout(LayoutParams lp) {
		if (dummyFooterAtBottom == false) {//先dummy，再footer
			if (dummyFooter != null) {//先添加dummyFooter，再添加真正的footer
				mContentView.addFooterView(dummyFooter);
			}
			if (enableFooter) {
				super.addFooterLayout(lp);
				isFooterRemoved = false;
			}
		} else {//先footer，再dummy
			if (enableFooter) {
				super.addFooterLayout(lp);
				isFooterRemoved = false;
			}
			if (dummyFooter != null) {//先添加dummyFooter，再添加真正的footer
				mContentView.addFooterView(dummyFooter);
			}
		}
	}

	public void setEnableBothLoad(boolean enableBothLoad) {
		this.enableBothLoad = enableBothLoad;
	}

	public void setOnScrollListener(OnScrollListener l) {
		mAutoLoadOnScrollListener.setOnScrollListener(l);
	}

	public void setOnHeadLoadingListener(OnLoadingListener onLoadingListener) {
		mHeadLoadingListener = onLoadingListener;
	}

	public void setOnFootLoadingListener(OnLoadingListener onLoadingListener) {
		mFootLoadingListener = onLoadingListener;
	}

	public OnLoadingListener getHeadLoadingListener() {
		return mHeadLoadingListener;
	}

	public OnLoadingListener getFootLoadingListener() {
		return mFootLoadingListener;
	}

	/**
	 * set header click to load
	 * 
	 * @param flag
	 */
	public void setHeaderClickToLoad(boolean flag) {
		if (flag) {
			setHeaderOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					startHeadLoading();
				}
			});
		} else {
			setHeaderOnClickListener(null);
		}
	}

	public void setHeaderOnClickListener(OnClickListener headerOnClickListener) {
		View mHeader = getHeaderLayout();
		if (mHeader != null) {
			mHeader.setOnClickListener(headerOnClickListener);
		}
	}

	/**
	 * set footer on click to load
	 * 
	 * @param flag
	 */
	public void setFooterOnClickToLoad(boolean flag) {
		if (flag) {
			setFooterOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					startFootLoading();
				}
			});
		} else {
			setFooterOnClickListener(null);
		}
	}

	public void setFooterOnClickListener(OnClickListener footerOnClickListener) {
		View mFooter = getFooterLayout();
		if (mFooter != null) {
			mFooter.setOnClickListener(footerOnClickListener);
		}
	}

	/**
	 * is this listview can load right now
	 * 
	 * @return
	 */
	public boolean canLoad() {
		if (enableBothLoad == false) {
			return mState == State.NORMAL;
		} else {
			return mState != State.BOTH;
		}
	}

	/**
	 * is loading
	 * 
	 * @return
	 */
	public boolean isLoading() {
		return mState == State.HEAD_LOADING || mState == State.FOOT_LOADING || mState == State.BOTH;
	}

	/**
	 * is head loading right now
	 * 
	 * @return
	 */
	public boolean isHeadLoading() {
		return mState == State.HEAD_LOADING || mState == State.BOTH;
	}

	/**
	 * is foot loading right now
	 * 
	 * @return
	 */
	public boolean isFootLoading() {
		return mState == State.FOOT_LOADING || mState == State.BOTH;
	}

	public boolean isHeadClickLoad() {
		return headClickLoad;
	}

	public void setHeadClickLoad(boolean headClickLoad) {
		this.headClickLoad = headClickLoad;
	}

	public boolean isFootClickLoad() {
		return footClickLoad;
	}

	public void setFootClickLoad(boolean footClickLoad) {
		this.footClickLoad = footClickLoad;
	}

	public boolean isHeadPullLoad() {
		return headPullLoad;
	}

	public void setHeadPullLoad(boolean headPullLoad) {
		this.headPullLoad = headPullLoad;
	}

	public boolean isFootPullLoad() {
		return footPullLoad;
	}

	public void setFootPullLoad(boolean footPullLoad) {
		this.footPullLoad = footPullLoad;
	}

	public boolean isHeadAutoLoad() {
		return headAutoLoad;
	}

	public void setHeadAutoLoad(boolean headAutoLoad) {
		this.headAutoLoad = headAutoLoad;
	}

	public boolean isFootAutoLoad() {
		return footAutoLoad;
	}

	public void setFootAutoLoad(boolean footAutoLoad) {
		this.footAutoLoad = footAutoLoad;
	}

	class MyOnSmoothScrollListener implements OnSmoothScrollFinishedListener {
		@Override
		public void onSmoothScrollFinished(Mode currentMode) {
			switch (currentMode) {
			case PULL_FROM_START:
				if (isHeaderRemoved == false && headPullLoad == true && isFirstItemFullVisible())
					startHeadLoading();
				break;
			case PULL_FROM_END:
				if (isFooterRemoved == false && footPullLoad == true && isLastItemFullVisible())
					startFootLoading();
				break;
			}
		}
	}
	
	/*@Override
	public void handleStyledAttributes(TypedArray array) {
		super.handleStyledAttributes(array);
		if(array.hasValue(R.styleable.AutoLoadListView_contentBackground)){
			int resId = array.getResourceId(R.styleable.AutoLoadListView_contentBackground, -1);
			if(resId != -1)
				this.setBackgroundResource(resId);	
		}
	}*/
}
