package com.cst.example;

import com.cst.stormdroid.adapter.BaseViewHolder;
import com.cst.stormdroid.adapter.SDBaseDynamicAdapter;

import android.content.Context;
import android.view.View;
import android.widget.AbsListView;

public class ExampleBaseAdapter extends SDBaseDynamicAdapter {

	public ExampleBaseAdapter(Context ctx, AbsListView lv) {
		super(ctx, lv);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View createConvertView(int position) {
		return null;
	}

	@Override
	public BaseViewHolder createViewHolder(int position, View convertView) {
//		BaseViewHolder viewHolder = new XXX();
//		viewHolder.xxx = (XXX)convertView.findViewById(R.id.xxx);
		return null;
	}

	@Override
	public void bindViewActions(int position, BaseViewHolder viewHolder) {
	}

	@Override
	public void setViewContents(int position, BaseViewHolder viewHolder) {
	}
}
