package com.cst.stormdroid.utils.string;

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
	public static boolean isValid(final String str){
		return str != null && !str.equals("");
	}
	
	/**
	 * return false when str is null, "", "null" or "NULL" 
	 * @param str
	 * @return
	 */
	public static boolean isStrongValid(final String str){
		return str != null && !str.equals("") && !str.equalsIgnoreCase("null");  
	}
}
