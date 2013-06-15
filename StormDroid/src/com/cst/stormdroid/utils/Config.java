package com.cst.stormdroid.utils;

import com.cst.stormdroid.utils.log.SDLog;
import com.cst.stormdroid.utils.log.SDLog.SDLogLevel;


/**
 * configuration of this application
 * @author MonsterStorm
 * @version 1.0
 */
public class Config {
	
	/**
	 * whether is in debug mode
	 */
	public static final boolean mDebug;
	
	static {
		mDebug = SDLog.SD_LOG_LEVEL == SDLogLevel.SD_LOG_LEVEL_DEBUG;
	}
}
