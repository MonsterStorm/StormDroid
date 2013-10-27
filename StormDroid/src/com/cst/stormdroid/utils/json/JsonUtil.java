package com.cst.stormdroid.utils.json;

import java.lang.reflect.Field;

import org.json.JSONObject;

import com.cst.stormdroid.utils.log.SDLog;

/**
 * Help easily parse and encode json string
 * @author MonsterStorm
 * @version 1.0
 */
public class JsonUtil {
	//tag for log
	private static final String TAG = JsonUtil.class.getSimpleName();
	
	/**
	 * get string named with param from a json object 
	 * @param obj
	 * @param param
	 * @return
	 */
	public static String getString(final JSONObject obj, final String param) {
		try {
			if (obj != null && obj.has(param)) {
				return obj.getString(param);
			}
		} catch (Exception e) {
			SDLog.e(TAG, "getString error");
			e.printStackTrace();
		}
		return null;
	}

	
	/**
	 * get integer named with param from a json object
	 * @param obj
	 * @param param
	 * @return
	 */
	public static Integer getInt(final JSONObject obj, final String param) {
		try {
			if (obj != null && obj.has(param)) {
				return obj.getInt(param);
			}
		} catch (Exception e) {
			SDLog.e(TAG, "getInt error");
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * use reflect to build object from a json string
	 * @param json
	 * @param clazz
	 * @return
	 */
	public static <T> T get(final String json, final Class<T> clazz){
		try{
			T t = clazz.newInstance();
			Field[] fields = clazz.getFields();
			for(int i = 0; i < fields.length; i++){
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}
}
