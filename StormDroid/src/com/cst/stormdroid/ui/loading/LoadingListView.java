package com.cst.stormdroid.ui.loading;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

public class LoadingListView extends BaseLoadingLayout<ListView> {

	public LoadingListView(Context context) {
		super(context);
	}

	public LoadingListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public LoadingListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	public ListView createContentView(Context context, AttributeSet attrs) {
		ListView lv = new ListView(context, attrs);
		return lv;
	}

}
