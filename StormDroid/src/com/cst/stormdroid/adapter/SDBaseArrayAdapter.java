package com.cst.stormdroid.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.cst.stormdroid.adapter.interfaces.IBaseAdapter;
/**
 * base adapter, where T is the view holder, and V is the item of list
 * @author MonsterStorm
 * @version 1.0
 */
public abstract class SDBaseArrayAdapter<T extends BaseViewHolder, V> extends ArrayAdapter<V> implements IBaseAdapter<T>{
	protected Context mCtx;
	protected LayoutInflater mInflator;
	
	public SDBaseArrayAdapter(Context ctx){
		super(ctx, 0);
		this.mCtx = ctx;
		this.mInflator = LayoutInflater.from(ctx);
	}
	
	public SDBaseArrayAdapter(Context ctx, List<V> list){
		super(ctx, 0, list);
		this.mCtx = ctx;
		this.mInflator = LayoutInflater.from(ctx);
	}
	
	@Override
	public long getItemId(int position) {
		return position;
	}
	
	@Override
	public V getItem(int position) {
		return super.getItem(position);
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
