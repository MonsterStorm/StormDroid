package com.cst.stormdroid.utils.bundle;

import java.io.Serializable;
import java.security.InvalidParameterException;

import android.os.Bundle;
import android.os.Parcelable;

/**
 * util for bundle operations
 * @author MonsterStorm
 * @version 1.0
 */
public class BundleUtil {

	/**
	 * create a new bundle with one key-value parameter
	 * @param key
	 * @param value
	 * @return
	 */
	public static Bundle newBundle(String key, Object value) {
		Bundle bundle = new Bundle();
		if (value instanceof Byte) {
			bundle.putByte(key, (Byte) value);
		} else if (value instanceof Short) {
			bundle.putShort(key, (Short) value);
		} else if (value instanceof Integer) {
			bundle.putInt(key, (Integer) value);
		} else if (value instanceof Long) {
			bundle.putLong(key, (Long) value);
		} else if (value instanceof Double) {
			bundle.putDouble(key, (Double) value);
		} else if (value instanceof Float) {
			bundle.putFloat(key, (Float) value);
		} else if (value instanceof Boolean) {
			bundle.putBoolean(key, (Boolean) value);
		} else if (value instanceof String) {
			bundle.putString(key, (String) value);
		} else if (value instanceof Parcelable) {
			bundle.putParcelable(key, (Parcelable) value);
		} else if (value instanceof Serializable) {
			bundle.putSerializable(key, (Serializable) value);
		} else if (value instanceof Bundle) {
			bundle.putBundle(key, (Bundle) value);
		} else {
			throw new InvalidParameterException("Value Type Error: type of value must be Byte, Short, Integer, Long, Double, Float, Boolean, String, Parcelabel, Serializable, Bundle!");
		}
		return bundle;
	}

	/**
	 * check whether a key is empty
	 * @param bundle
	 * @param key
	 * @return
	 */
	public static boolean isEmpty(Bundle bundle, String key) {
		return bundle == null || !bundle.containsKey(key);
	}

	/**
	 * get int
	 * @param bundle
	 * @param key
	 * @return
	 */
	public static Integer getInt(Bundle bundle, String key) {
		if (isEmpty(bundle, key)) {
			return null;
		} else {
			return bundle.getInt(key);
		}
	}

	/**
	 * get Long
	 * @param bundle
	 * @param key
	 * @return
	 */
	public static Long getLong(Bundle bundle, String key) {
		if (isEmpty(bundle, key)) {
			return null;
		} else {
			return bundle.getLong(key);
		}
	}

	/**
	 * get data of given key and given class
	 * @param bundle
	 * @param key
	 * @param clazz
	 * @return
	 *//*
	public static <T> T getData(Bundle bundle, String key, Class<T> clazz) {
		try {
			if (clazz.isAssignableFrom(Byte.class)) {
				bundle.getByte(key);
			} else if (clazz.isAssignableFrom(Short.class)) {
				bundle.getShort(key);
			} else if (clazz.isAssignableFrom(Integer.class)) {
				bundle.getInt(key);
			} else if (clazz.isAssignableFrom(Long.class)) {
				bundle.getLong(key);
			} else if (clazz.isAssignableFrom(Double.class)) {
				bundle.getDouble(key);
			} else if (clazz.isAssignableFrom(Float.class)) {
				bundle.getFloat(key);
			} else if (clazz.isAssignableFrom(Boolean.class)) {
				bundle.getBoolean(key);
			} else if (clazz.isAssignableFrom(String.class)) {
				bundle.getString(key);
			} else if (clazz.isAssignableFrom(Parcelable.class)) {
				bundle.getParcelable(key);
			} else if (clazz.isAssignableFrom(Serializable.class)) {
				bundle.getSerializable(key);
			} else if (clazz.isAssignableFrom(Bundle.class)) {
				bundle.getBundle(key);
			} else {
				throw new InvalidParameterException("Class Type Error: type of class must be Byte, Short, Integer, Long, Double, Float, Boolean, String, Parcelabel, Serializable, Bundle!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}*/
}
