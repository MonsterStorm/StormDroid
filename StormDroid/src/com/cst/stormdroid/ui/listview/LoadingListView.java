package com.cst.stormdroid.ui.listview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;
/**
 * add loading effect to listview
 * @author MonsterStorm
 * @version 1.0
 */
public class LoadingListView extends ListView{
	//context
	private Context mContext;

	public LoadingListView(Context context) {
		this(context, null);
	}
	
	public LoadingListView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}
	
	public LoadingListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.mContext = context;
	}

}
