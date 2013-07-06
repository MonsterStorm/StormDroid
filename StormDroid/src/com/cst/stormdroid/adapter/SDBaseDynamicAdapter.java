package com.cst.stormdroid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.cst.stormdroid.adapter.interfaces.IBaseAdapter;
/**
 * base adapter that handle listview that has image in it's item, download images when the listview is in idle state
 * @author MonsterStorm
 * @version 1.0
 */
public abstract class SDBaseDynamicAdapter<T extends BaseViewHolder> extends BaseAdapter implements IBaseAdapter<T>{
	protected AbsListView mLv;
	protected Context mCtx;
	protected LayoutInflater mInflator;
	//is load first time
	protected boolean mIsFirstTime = true;
	
	public SDBaseDynamicAdapter(Context ctx, AbsListView mLv){
		this.mCtx = ctx;
		this.mInflator = LayoutInflater.from(ctx);
		this.mLv = mLv;
		bindScrollListener();
	}
	
	/**
	 * bind scroll listener for listview
	 */
	protected void bindScrollListener(){
		this.mLv.setOnScrollListener(new OnScrollListener() {
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				if(scrollState == OnScrollListener.SCROLL_STATE_IDLE){
					//change is first time
					mIsFirstTime = false;
					
					int headCount = 0;
					if(mLv instanceof ListView) headCount = ((ListView)mLv).getHeaderViewsCount();
					int first = view.getFirstVisiblePosition() - headCount;
					int last = view.getLastVisiblePosition() - headCount;
					for(int i = first; i <= last; i++){
						setViewContentWhenIdle(i);
					}
				}
			}
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
			}
		});
	}
	
	@Override
	public void notifyDataSetChanged() {
		mIsFirstTime = true;
		super.notifyDataSetChanged();
	}
	
	@Override
	public void notifyDataSetInvalidated() {
		mIsFirstTime = true;
		super.notifyDataSetInvalidated();
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		T viewHolder;
		if(convertView == null){
			//create convertview
			convertView = createConvertView(position);
			//create view holder
			viewHolder = createViewHolder(position, convertView);
			//store view holder to tag
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (T)convertView.getTag();
		}
		
		//bind actions
		bindViewActions(position, viewHolder);
		
		//set view content
		if(mIsFirstTime){
			setViewContentWhenGetView(position, viewHolder);
		}
		
		setViewContents(position, viewHolder);
		
		return convertView;
	}

	/**
	 * setView
	 * @param pos
	 * @param viewHolder
	 */
	protected void setViewContentWhenGetView(int pos, T viewHolder){};
	
	/**
	 * set view content when AbsListView's Scroll state is idle
	 * override this method to set view
	 * @param pos position in AbsListView
	 */
	protected void setViewContentWhenIdle(int pos){};
}
