package com.cst.stormdroid.utils.sys;

import android.content.Context;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

/**
 * System information of this phone, including software and hardware information.
 * @author MonsterStorm
 * @version 1.0
 */
public class SysUtil {
	/**
	 * Sdk level of this system
	 */
	public static final int SDK = Build.VERSION.SDK_INT;

	/**
	 * MAC address
	 */
	
	/**
	 * IP address
	 */
	
	/**
	 * Screen Dimension
	 */
	public static DisplayMetrics getWindowMetrics(Context ctx){
		WindowManager wm = (WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		DisplayMetrics outMetrics = new DisplayMetrics();
		display.getMetrics(outMetrics);
		return outMetrics;
	}
}
