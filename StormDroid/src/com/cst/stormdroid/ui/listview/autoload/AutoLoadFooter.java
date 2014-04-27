package com.cst.stormdroid.ui.listview.autoload;

import android.content.Context;

import com.cst.stormdroid.R;

/**
 * auto load header
 * 
 * @author Storm
 * 
 */
public class AutoLoadFooter extends AutoLoadInfoView {

	public AutoLoadFooter(Context context, boolean isSimple) {
		super(context, R.layout.listbottom_autoload_default, isSimple);
	}
	
	public AutoLoadFooter(Context context, int layout, boolean isSimple){
		super(context, layout, isSimple);
	}

}
