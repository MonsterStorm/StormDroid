package com.cst.stormdroid.utils.string;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * String 
 * @author MonsterStorm
 * @version 1.0
 */
public class StringUtil {
	
	/**
	 * return false when str is null, ""
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(final CharSequence str){
		return str == null || str.equals("");
	}
	
	/**
	 * return false when str is null, "", "null" or "NULL" 
	 * @param str
	 * @return
	 */
	public static boolean isValid(final String str){
		return str != null && !str.equals("") && !str.equalsIgnoreCase("null");  
	}

	/**
	 * get String from map, does not return a null
	 * @param obj
	 * @return
	 */
	public static <T> String getString(Map<T, Object> map, T key){
		if(key != null){
			Object obj = map.get(key);
			return String.valueOf(obj);//if obj is null, then ruturn "null"
		} else {
			return "";
		}
	}
	
	/**
	 * convert string array to array list
	 * @param items
	 * @return
	 */
	public static List<String> stringsToList(String[] items){
		List<String> list = new ArrayList<String>();
		for(int i = 0; i < items.length; i++){
			list.add(items[i]);
		}
		return list;
	}
}
