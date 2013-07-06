package com.cst.stormdroid.utils.intent;

import java.util.Iterator;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;

import com.cst.stormdroid.utils.string.StringUtil;

/**
 * intent helper to create intent
 * @author MonsterStorm
 * @version 1.0
 */
public class IntentHelper {
	/**
	 * create new intent
	 * @param from
	 * @param to
	 * @return
	 */
	public static Intent newIntent(final Context from, final Class<?> to){
		return newIntentWithParamsAndActionAndFlags(from, to, null, null, null);
	}
	
	/**
	 * create new intent
	 * @param from
	 * @param to
	 * @return
	 */
	public static Intent newIntentWithAction(final Context from, final Class<?> to, final String action){
		return newIntentWithParamsAndActionAndFlags(from, to, null, action, null);
	}
	
	/**
	 * create new intent with flag
	 * @param from
	 * @param to
	 * @param flag
	 * @return
	 */
	public static Intent newIntentWithFlags(final Context from, final Class<?> to, final Integer flags){
		return newIntentWithParamsAndActionAndFlags(from, to, null, null, flags);
	}
	
	/**
	 * create new intent with params
	 * @param from
	 * @param to
	 * @param params
	 * @return
	 */
	public static Intent newIntentWithParams(final Context from, final Class<?> to, final Map<String, Object> params){
		return newIntentWithParamsAndActionAndFlags(from, to, params, null, null);
	}
	
	
	/**
	 * create new intent with params and flags
	 * @param from
	 * @param to
	 * @param params
	 * @param flags
	 * @return
	 */
	public static Intent newIntentWithParamsAndFlags(final Context from, final Class<?> to, final Map<String, Object> params, final Integer flags){
		return newIntentWithParamsAndActionAndFlags(from, to, params, null, flags);
	}
	
	/**
	 * create new intent with params
	 * @param from
	 * @param to
	 * @param params
	 * @return
	 */
	public static Intent newIntentWithParamsAndAction(final Context from, final Class<?> to, final Map<String, Object> params, final String action){
		return newIntentWithParamsAndActionAndFlags(from, to, params, action, null);
	}
	
	/**
	 * create new intent with params
	 * @param from
	 * @param to
	 * @param params
	 * @return
	 */
	public static Intent newIntentWithParamsAndActionAndFlags(final Context from, final Class<?> to, final Map<String, Object> params, final String action, final Integer flags){
		Intent intent = null;
		if(from != null && to != null)
			intent = new Intent(from, to);
		else
			intent = new Intent();
		
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
		
		if(StringUtil.isValid(action))
			intent.setAction(action);
		if(flags != null)
			intent.addFlags(flags);
		if(params != null){
			for (Iterator<String> iterator = params.keySet().iterator(); iterator.hasNext();) {
				String key = iterator.next();
				Object value = params.get(key);
				if(value instanceof Integer){
					intent.putExtra(key, (Integer)value);
				} else if (value instanceof String){
					intent.putExtra(key, (String)value);
				} else if (value instanceof Boolean){
					intent.putExtra(key, (Boolean)value);
				} else if (value instanceof Parcelable){
					intent.putExtra(key, (Parcelable)value);
				} else if (value instanceof Object){
					///
				}
			}
		}
		return intent;
	}
}
