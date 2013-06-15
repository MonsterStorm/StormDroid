package com.cst.stormdroid.app;
/**
 * handler trim memory operation
 * only added when sdk level up to 14
 * @author MonsterStorm
 * @version 1.0
 */
public interface OnTrimMemoryListener {
	/**
	 * trim memory with given level
	 * @param level
	 */
	public void onTrimMemory(int level);
}
