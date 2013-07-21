package com.cst.stormdroid.utils;

import com.cst.stormdroid.app.SDBaseApplication;

import android.os.Environment;

/**
 * Global variables
 * @author MonsterStorm
 * @version 1.0
 */
public class Globals {
	
	/**
	 * sdcard base path
	 */
	public static String SD_BASE_PATH;
	
	/**
	 * whether has sdcard
	 */
	public static boolean HAS_SDCARD;
	
	/**
	 * init globals variable
	 * @param app
	 */
	public static void init(SDBaseApplication app){
		if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)){
			Globals.SD_BASE_PATH = Environment.getExternalStorageDirectory().toString();
			Globals.HAS_SDCARD = true;
		}else{
			Globals.SD_BASE_PATH = app.getCacheDir().toString();
			Globals.HAS_SDCARD = false;
		}
	}
	
}
