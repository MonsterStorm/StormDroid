package com.cst.stormdroid.net.interfaces;
/**
 * http call back for http
 * @author MonsterStorm
 * @version
 */
public interface SDOnHttpCallback {
	/**
	 * http start
	 * @param url
	 */
	public void onHttpStart(Integer requestCode, String url);
	
	/**
	 * http error
	 * @param url
	 * @param serverResponse
	 */
	public void onHttpError(Integer requestCode, String url, String serverResponse);
	
	/**
	 * http finish
	 * @param url
	 * @param serverResponse
	 */
	public void onHttpFinish(Integer requestCode, String url, String serverResponse);
	
	/**
	 * http progress update
	 * @param url
	 * @param progress
	 */
	public void onHttpProgress(Integer requestCode, String url, int progress);
}
