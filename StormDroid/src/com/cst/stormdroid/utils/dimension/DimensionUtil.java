package com.cst.stormdroid.utils.dimension;

import com.cst.stormdroid.app.SDApplication;

/**
 * util for dimension process such as dp to xp and xp to dp convert
 * @author MonsterStorm
 * @version 1.0
 */
public class DimensionUtil {
	
	/**
	 * convert dp to px
	 */
	public static int dpToPx(int dp) {
		final float SCALE = SDApplication.getInstance().getResources().getDisplayMetrics().density;
		int px = (int) (dp * SCALE + 0.5f);
		return px;
	}

	/**
	 * convert px to dp
	 */
	public static float pxToDp(int px) {
		final float SCALE = SDApplication.getInstance().getResources().getDisplayMetrics().density;
		float dp = px / SCALE;
		return dp;
	}
}
