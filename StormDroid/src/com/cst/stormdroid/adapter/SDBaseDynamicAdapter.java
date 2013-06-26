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
	protected AbsListView lv;
	protected Context ctx;
	protected LayoutInflater inflator;
	//is load first time
	protected boolean isFirstTime = true;
	
	public SDBaseDynamicAdapter(Context ctx, AbsListView lv){
		this.ctx = ctx;
		this.inflator = LayoutInflater.from(ctx);
		this.lv = lv;
		bindScrollListener();
	}
	
	/**
	 * bind scroll listener for listview
	 */
	protected void bindScrollListener(){
		this.lv.setOnScrollListener(new OnScrollListener() {
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				if(scrollState == OnScrollListener.SCROLL_STATE_IDLE){
					//change is first time
					isFirstTime = false;
					
					int headCount = 0;
					if(lv instanceof ListView) headCount = ((ListView)lv).getHeaderViewsCount();
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
		isFirstTime = true;
		super.notifyDataSetChanged();
	}
	
	@Override
	public void notifyDataSetInvalidated() {
		isFirstTime = true;
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
		if(isFirstTime){
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
