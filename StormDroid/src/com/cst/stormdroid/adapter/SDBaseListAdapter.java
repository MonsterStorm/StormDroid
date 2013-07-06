package com.cst.stormdroid.adapter;

import java.util.List;

import android.content.Context;
/**
 * Cause Android has a default @ArrayAdapter, so this is deprecated, just use the @SDBaseArrayAdapter.
 * base adapter with list values, T is subclass of BaseViewHolder and V is the item type of List
 * @author MonsterStorm
 * @version 1.0
 */
@Deprecated
public abstract class SDBaseListAdapter<T extends BaseViewHolder, V> extends SDBaseAdapter<T>{
	/**
	 * list that store values
	 */
	protected List<V> mItems;
	
	public SDBaseListAdapter(Context ctx, List<V> items){
		super(ctx);
		this.mItems = items;
	}
	
	@Override
	public int getCount() {
		if(mItems != null){
			return mItems.size();
		} else {
			return 0;
		}
	}
	
	@Override
	public V getItem(int position) {
		if(mItems != null){
			return mItems.get(position);
		} else {
			return null;
		}
	}
	
	/**
	 * set mItems to list
	 * @param mItems
	 */
	protected void setItems(List<V> v){
		this.mItems = v;
	}
	
	/**
	 * add mItems to list
	 * @param mItems
	 */
	protected void addItems(List<V> v){
		if(this.mItems != null){
			this.mItems.addAll(v);
		}
	}
	
	/**
	 * add an item to list
	 * @param v
	 */
	protected void addItem(V v){
		if(mItems != null && v != null){
			mItems.add(v);
		}
	}
	
	/**
	 * set an item to a position
	 * @param v
	 */
	protected void setItem(V v, int pos){
		if(mItems != null && v != null && pos < mItems.size()){
			mItems.set(pos, v);
		}
	}
}
