package com.cst.stormdroid.app;
/**
 * handler on Low Memory event
 * @author MonsterStorm
 * @version 1.0
 */
public interface OnLowMemoryListener {

	/**
	 * handler on low memory event, mainly clear all cache in this method
	 */
	public void onLowMemory();
	
}
