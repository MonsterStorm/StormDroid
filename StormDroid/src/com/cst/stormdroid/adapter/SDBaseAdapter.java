package com.cst.stormdroid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.cst.stormdroid.adapter.interfaces.IBaseAdapter;
/**
 * base adapter
 * @author MonsterStorm
 * @version 1.0
 */
public abstract class SDBaseAdapter<T extends BaseViewHolder> extends BaseAdapter implements IBaseAdapter<T>{
	protected Context ctx;
	protected LayoutInflater inflator;
	
	public SDBaseAdapter(Context ctx){
		this.ctx = ctx;
		this.inflator = LayoutInflater.from(ctx);
	}
	
	@Override
	public long getItemId(int position) {
		return position;
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
		
		setViewContents(position, viewHolder);
		
		return convertView;
	}
}
