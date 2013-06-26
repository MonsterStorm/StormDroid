package com.cst.stormdroid.adapter.interfaces;

import android.view.View;

import com.cst.stormdroid.adapter.BaseViewHolder;

/**
 * interface for base adapter
 * @author MonsterStorm
 * @version 1.0
 */
public interface IBaseAdapter<T extends BaseViewHolder> {

	/**
	 * create convert view for base adapter
	 * @param context
	 * @position position of the item is adapter
	 * @param inflater
	 */
	public View createConvertView(int position);
	
	/**
	 * bind view for view holder, eg: viewHolder.xxx = (XXX)convertView.findViewById("xxx");
	 * @param adapter
	 * @param position
	 * @param convertView
	 */
	public T createViewHolder(int position, View convertView);
	
	/**
	 * bind action for view holder
	 * @param adapter
	 * @param position
	 * @param viewHolder
	 */
	public void bindViewActions(int position, T viewHolder);
	
	/**
	 * set data to view holder
	 * @param adapter
	 * @param position
	 * @param viewHolder
	 */
	public void setViewContents(int position, T viewHolder);
}
