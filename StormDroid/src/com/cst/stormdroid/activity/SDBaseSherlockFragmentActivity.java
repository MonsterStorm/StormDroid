package com.cst.stormdroid.activity;

import android.annotation.TargetApi;
import android.support.v4.app.Fragment;

import com.actionbarsherlock.app.SherlockFragmentActivity;

/**
 * base activity with sherlock preference
 * @author MonsterStorm
 * @version 1.0
 */
public class SDBaseSherlockFragmentActivity extends SherlockFragmentActivity{
	private static final String TAG = SDBaseSherlockFragmentActivity.class.getSimpleName();
	
	/**
	 * add fragment
	 * @param frag
	 */
	@TargetApi(11)
	protected void setPreferenceFragment(Fragment fragment){
		getSupportFragmentManager().beginTransaction().replace(android.R.id.content, fragment).commit();
	}
}
