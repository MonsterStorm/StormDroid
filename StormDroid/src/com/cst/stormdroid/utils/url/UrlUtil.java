package com.cst.stormdroid.utils.url;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.cst.stormdroid.utils.string.StringUtil;

/**
 * utils for build url
 * @author MonsterStorm
 * @version 1.0
 */
public class UrlUtil {
	/**
	 * url use http
	 */
	public static final String PREFIX_HTTP = "http";
	/**
	 * url use https
	 */
	public static final String PREFIX_HTTPS = "https";

	/**
	 * build url
	 * eg: http://www.google.com/search?a=1&b=2
	 * @param baseUrl eg: www.google.com
	 * @param extUrl eg: search
	 * @param params eg: HashMap{a=1;b=2}
	 * @return
	 */
	public static String buildUrl(final String baseUrl, final String extUrl, Map<String, Object> params) {
		return buildUrl(PREFIX_HTTP, baseUrl, extUrl, params);
	}

	/**
	 * build url
	 * eg: http://www.google.com/search?a=1&b=2
	 * @param prefix eg: http
	 * @param baseUrl eg: www.google.com
	 * @param extUrl eg: search
	 * @param params eg: HashMap{a=1;b=2}
	 * @return
	 */
	public static String buildUrl(final String prefix, final String baseUrl, final String extUrl, Map<String, Object> params) {
		StringBuilder sb = new StringBuilder();
		sb.append(prefix);
		sb.append("://");
		sb.append(baseUrl);
		if(StringUtil.isValid(extUrl) || params != null){
			sb.append("/");
		}
		if (StringUtil.isValid(extUrl)) {
			sb.append(extUrl);
		}
		if (params != null) {
			sb.append("?");
			Iterator<Entry<String, Object>> iter = params.entrySet().iterator();
			while (iter.hasNext()) {
				Entry<String, Object> entry = (Entry<String, Object>) iter.next();
				String key = entry.getKey();
				Object value = entry.getValue();
				sb.append(key);
				sb.append("=");
				sb.append(value);
				if (iter.hasNext()) {
					sb.append("&");
				}
			}
		}
		return sb.toString();
	}
}
