package com.cst.stormdroid.utils;

/**
 * define constants in this class
 * @author MonsterStorm
 * @version 1.0
 */
public class Constants {
	/******************************** MESSAGE ******************************/
	public static interface MESSAGE_BASE {};
	/**
	 * Common Message: no data, an error occur
	 */
	public static enum MESSAGE_COMMON implements MESSAGE_BASE {
		MESSAGE_NO_DATA, MESSAGE_ERROR
	};

	/**
	 * Http Message : start, finish, error
	 */
	public static enum MESSAGE_HTTP implements MESSAGE_BASE {
		MESSAGE_HTTP_STATR, MESSAGE_HTTP_FINISH, MESSAGE_HTTP_ERROR
	};
	
	
	
	
	public static final int 
		MESSAGE_COMMON_NODATA = 1,
		MESSAGE_COMMON_ERROR = 2, 
		MESSAGE_HTTP_START = 3, 
		MESSAGE_HTTP_FINISH = 4, 
		MESSAGE_HTTP_ERROR = 5;
}
