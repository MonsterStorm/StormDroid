package com.cst.stormdroid.ui.listview.autoload;

import android.content.Context;

import com.cst.stormdroid.R;

/**
 * auto load header
 * 
 * @author Storm
 * 
 */
public class AutoLoadHeader extends AutoLoadInfoView {

	public AutoLoadHeader(Context context, boolean isSimple) {
		super(context, R.layout.listtop_autoload_default, isSimple);
	}
	
	public AutoLoadHeader(Context context, int layout, boolean isSimple){
		super(context, layout, isSimple);
	}

}
