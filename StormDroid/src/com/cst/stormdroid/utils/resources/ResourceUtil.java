package com.cst.stormdroid.utils.resources;

import android.content.Context;
import android.content.res.TypedArray;

/**
 * Get Typed Array As int array
 * @author MonsterStorm
 * @version 1.0
 */
public class ResourceUtil {
	
	/**
	 * get Typed Array as int array
	 * @param ctx
	 * @param arrayId
	 * @return
	 */
	public static int[] getTypedArray(Context ctx, int arrayId){
		TypedArray ar = ctx.getResources().obtainTypedArray(arrayId);
		int len = ar.length();
		int[] array = new int[len];
		for (int i = 0; i < len; i++)
			array[i] = ar.getResourceId(i, 0);
		ar.recycle();
		return array;
	}
}
