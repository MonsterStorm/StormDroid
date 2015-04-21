package com.cst.stormdroid.activity;

import android.annotation.TargetApi;
import android.app.Fragment;

import com.actionbarsherlock.app.SherlockActivity;

/**
 * base activity with sherlock preference
 * @author MonsterStorm
 * @version 1.0
 */
public class SDBaseSherlockActivity extends SherlockActivity{
	private static final String TAG = SDBaseSherlockActivity.class.getSimpleName();
	
	/**
	 * add fragment
	 * @param frag
	 */
	@TargetApi(11)
	protected void setPreferenceFragment(Fragment fragment){
		getFragmentManager().beginTransaction().replace(android.R.id.content, fragment).commit();
	}
}
