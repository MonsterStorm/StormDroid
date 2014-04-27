package com.cst.stormdroid.ui.listview.autoload;

import android.content.Context;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
/**
 * on scroll listener that wrapped
 * @author Storm
 *
 */
public class AutoLoadOnScrollListener implements OnScrollListener{
	private AutoLoadListView mAutoLoadListView;
	private OnScrollListener mOnScrollListener;
	private Context mContext;
	
	public AutoLoadOnScrollListener(Context context, AutoLoadListView autoLoadListView){
		this.mContext = context;
		this.mAutoLoadListView = autoLoadListView;
	}
	
	
	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		if(scrollState == OnScrollListener.SCROLL_STATE_IDLE){
			if(mAutoLoadListView.isHeaderLayoutVisible()){
				if(mAutoLoadListView.isHeadAutoLoad() == true){
					mAutoLoadListView.mContentView.setSelection(0);//选中第一个，解决现实一半的时候加载问题
					mAutoLoadListView.startHeadLoading();
				}
			}
			
			if(mAutoLoadListView.isFooterLayoutVisible()){
				if(mAutoLoadListView.isFootAutoLoad() == true){
					mAutoLoadListView.mContentView.setSelection(view.getCount() - 1);
					mAutoLoadListView.startFootLoading();
				}
			}
		}
		
		if(mOnScrollListener != null){
			mOnScrollListener.onScrollStateChanged(view, scrollState);
		}
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		if(mOnScrollListener != null){
			mOnScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
		}
	}

	public void setOnScrollListener(OnScrollListener mOnScrollListener) {
		this.mOnScrollListener = mOnScrollListener;
	}
}
