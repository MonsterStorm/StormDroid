package com.cst.stormdroid.utils.dimension;

import android.content.Context;
import android.util.DisplayMetrics;

import com.cst.stormdroid.app.SDBaseApplication;

/**
 * util for dimension process such as dp to xp and xp to dp convert
 * @author MonsterStorm
 * @version 1.0
 */

/**
 * dimension helper
 * 
 * @author Storm
 * 
 */
public class DimensionUtil {
	public static final int SCREEN_WIDTH;
	public static final int SCREEN_HEIGHT;

	static {
		DisplayMetrics display = SDBaseApplication.getInstance().getResources().getDisplayMetrics();
		SCREEN_WIDTH = display.widthPixels;
		SCREEN_HEIGHT = display.heightPixels;
	}

	/**
	 * one dip's count of pixel
	 */
	public static final int ONE_DIP_OF_PIXEL;
	static {
		ONE_DIP_OF_PIXEL = dipToPixel(SDBaseApplication.getInstance(), 1);
	}

	/**
	 * get screen width
	 * @param applicaion
	 * @return
	 */
	public static int getScreenWidth(Context ctx) {
		if (ctx != null) {
			DisplayMetrics display = ctx.getResources().getDisplayMetrics();
			return display.widthPixels;
		}
		return -1;
	}

	/**
	 * get screen height
	 * @return
	 */
	public static int getScreenHeight(Context ctx) {
		if (ctx != null) {
			DisplayMetrics display = ctx.getResources().getDisplayMetrics();
			return display.heightPixels;
		}
		return -1;
	}

	/**
	 * dip to pixel
	 */
	public static int dipToPixel(Context context, int dip) {
		final float SCALE = context.getResources().getDisplayMetrics().density;
		return (int) (dip * SCALE + 0.5f);
	}

	/**
	 * pixel to dip
	 */
	public static float pixelToDip(Context context, int pixel) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return pixel / scale;
	}
}
