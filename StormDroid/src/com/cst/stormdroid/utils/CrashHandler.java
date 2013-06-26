package com.cst.stormdroid.utils;
import java.lang.Thread.UncaughtExceptionHandler;

import android.content.Context;
import android.util.Log;
/**
 * crash handler, handler uncaught exception
 * @author MonsterStorm
 * @version 1.0
 */
public class CrashHandler implements UncaughtExceptionHandler {
	public static final String TAG = CrashHandler.class.getSimpleName();
	
	private static CrashHandler instance = new CrashHandler();
	private Context mContext;
	private Thread.UncaughtExceptionHandler mDefaultHandler;

	private CrashHandler() {
	}

	public static CrashHandler getInstance() {
		return instance;
	}

	/**
	 * init crash handler
	 * @param ctx
	 */
	public void init(Context ctx) {
		mContext = ctx;
		mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
		Thread.setDefaultUncaughtExceptionHandler(this);
	}

	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		ex.printStackTrace();
		Log.e(TAG, ex.getLocalizedMessage());
//		mSubExceptionHandler.uncaughtException(thread, ex);
	}
	
	/**
	 * handle exception by user, this can be override
	 * @param ex
	 */
	protected void handleException(Throwable ex){
	}
}
