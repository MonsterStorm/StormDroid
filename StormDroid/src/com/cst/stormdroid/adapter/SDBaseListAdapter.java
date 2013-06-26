package com.cst.stormdroid.adapter;

import java.util.List;

import android.content.Context;
/**
 * base adapter with list values, T is subclass of BaseViewHolder and V is the item type of List
 * @author MonsterStorm
 * @version 1.0
 */
public abstract class SDBaseListAdapter<T extends BaseViewHolder, V> extends SDBaseAdapter<T>{
	/**
	 * list that store values
	 */
	protected List<V> items;
	
	public SDBaseListAdapter(Context ctx, List<V> items){
		super(ctx);
		this.items = items;
	}
	
	@Override
	public int getCount() {
		if(items != null){
			return items.size();
		} else {
			return 0;
		}
	}
	
	@Override
	public V getItem(int position) {
		if(items != null){
			return items.get(position);
		} else {
			return null;
		}
	}
	
	/**
	 * set items to list
	 * @param items
	 */
	protected void setItems(List<V> v){
		this.items = v;
	}
	
	/**
	 * add items to list
	 * @param items
	 */
	protected void addItems(List<V> v){
		if(this.items != null){
			this.items.addAll(v);
		}
	}
	
	/**
	 * add an item to list
	 * @param v
	 */
	protected void addItem(V v){
		if(items != null && v != null){
			items.add(v);
		}
	}
	
	/**
	 * set an item to a position
	 * @param v
	 */
	protected void setItem(V v, int pos){
		if(items != null && v != null && pos < items.size()){
			items.set(pos, v);
		}
	}
}
