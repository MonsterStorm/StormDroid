package com.cst.stormdroid.utils.log;

import android.util.Log;

/**
 * log utils
 * @author MonsterStorm
 * @version 1.0
 */
public class SDLog {
	private SDLog(){}
	/**
	 * different logs level
	 */
	public static enum SDLogLevel{
		SD_LOG_LEVEL_INFO, 
		SD_LOG_LEVEL_WARNING, 
		SD_LOG_LEVEL_ERROR, 
		SD_LOG_LEVEL_NONE
	};
    
    /**
     * current logs level, set to SD_LOG_LEVEL_NONE when application is release
     */
    private static final SDLogLevel SD_LOG_LEVEL = SDLogLevel.SD_LOG_LEVEL_NONE;
    
    /**
     * is info logs enabled
     */
    public static final boolean SD_INFO_LOGS_ENABLED = (SD_LOG_LEVEL == SDLogLevel.SD_LOG_LEVEL_INFO);
    
    /**
     * is warning logs enabled
     */
    public static final boolean SD_WARNING_LOGS_ENABLED = SD_INFO_LOGS_ENABLED || (SD_LOG_LEVEL == SDLogLevel.SD_LOG_LEVEL_WARNING);
	
    /**
     * is error logs enabled
     */
    public static final boolean SD_ERROR_LOGS_ENABLED = SD_WARNING_LOGS_ENABLED || (SD_LOG_LEVEL == SDLogLevel.SD_LOG_LEVEL_ERROR);
    
    /**
     * log info
     * @param tag
     * @param msg
     */
	public static void i(final String tag, final String msg){
		if(SD_INFO_LOGS_ENABLED){
			Log.i(tag, msg);
		}
	}
	
	/**
	 * log warning
	 * @param tag
	 * @param msg
	 */
	public static void w(final String tag, final String msg){
		if(SD_WARNING_LOGS_ENABLED){
			Log.w(tag, msg);
		}
	}
	
	/**
	 * log error
	 * @param tag
	 * @param msg
	 */
	public static void e(final String tag, final String msg){
		if(SD_ERROR_LOGS_ENABLED){
			Log.e(tag, msg);
		}
	}
	
	/**
	 * log error
	 * @param tag
	 * @param msg
	 * @param exception
	 */
	public static void e(final String tag, final String msg, final Throwable exception){
		if(SD_ERROR_LOGS_ENABLED){
			Log.e(tag, msg, exception);
		}
	}
}
