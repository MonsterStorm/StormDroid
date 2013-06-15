package com.cst.stormdroid.utils.date;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Util for Handling Time or Date
 * @author MonsterStorm
 * @version 1.0
 */
public class DateUtil {
	/**
	 * default date time format "yyyy-MM-dd HH:mm:ss"
	 */
	public static final String DATE_FORMAT_DEFAULT = "yyyy-MM-dd HH:mm:ss";
	/**
	 * default Chinese date time format (in 24 hours format) "yyyy年MM月dd日 HH时mm分ss秒"
	 */
	public static final String DATE_FORMAT_CN_24 = "yyyy年MM月dd日 HH时mm分ss秒";
	/**
	 * default Chinese date time format (in 12 hours format) "yyyy年MM月dd日 hh时mm分ss秒"
	 */
	public static final String DATE_FORMAT_CN_12 = "yyyy年MM月dd日 hh时mm分ss秒";
	/**
	 * default Chinese date time format in short (and in 24 hours format ) "yy年MM月dd日 HH时mm分ss秒"
	 */
	public static final String DATE_FORMAT_CN_24_SHORT = "yy年MM月dd日 HH时mm分ss秒";
	/**
	 * default Chinese date time format in short (and in 12 hours format ) "yy年MM月dd日 hh时mm分ss秒"
	 */
	public static final String DATE_FORMAT_CN_12_SHORT = "yy年MM月dd日 hh时mm分ss秒";
	/**
	 * one second in milliseconds
	 */
	public static final long ONE_SECOND = 1000;
	/**
	 * one minute in milliseconds
	 */
	public static final long ONE_MINUTE = 60 * ONE_SECOND;
	/**
	 * one hour in milliseconds
	 */
	public static final long ONE_HOUR = 60 * ONE_MINUTE;
	/**
	 * one day in milliseconds
	 */
	public static final long ONE_DAY = 24 * ONE_HOUR;
	
	
	
	/**
	 * format a string, return default formatted string (yyyy-MM-dd HH:mm:ss)
	 * @param date
	 * @return
	 */
	public static String format(final Date date){
		return format(date, DATE_FORMAT_DEFAULT);
	}
	
	/**
	 * 格式化时间串，返回给定格式的串
	 * @param time
	 * @param format
	 * @return
	 */
	public static String format(final Date date, final String format){
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try{
			return sdf.format(date);
		} catch (Exception e){
			e.printStackTrace();
		}
		return date.toString();
	}
	
	/**
	 * build date time string according given date and default format
	 * @param date
	 * @return
	 */
	public static Date parse(final String date){
		return parse(date, DATE_FORMAT_DEFAULT);
	}
	
	/**
	 * build date time according given date and format
	 * @param date
	 * @param format
	 * @return
	 */
	public static Date parse(final String date, final String format){
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try{
			return sdf.parse(date);
		} catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * get current date in default format
	 * @return
	 */
	public static String getCurrent(){
		return getCurrent(DATE_FORMAT_DEFAULT);
	}
	/**
	 * get current date in given format
	 * @return
	 */
	public static String getCurrent(final String format){
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(new Date());
	}
	
	/**
	 * get current date in milliseconds
	 * @return
	 */
	public static Long getCurrentTime(){
		return System.currentTimeMillis();
	}
}
