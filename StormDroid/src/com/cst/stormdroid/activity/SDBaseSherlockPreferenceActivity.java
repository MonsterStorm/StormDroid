package com.cst.stormdroid.activity;

import android.annotation.TargetApi;
import android.preference.PreferenceFragment;

import com.actionbarsherlock.app.SherlockPreferenceActivity;

/**
 * base activity with sherlock preference
 * @author MonsterStorm
 * @version 1.0
 */
public class SDBaseSherlockPreferenceActivity extends SherlockPreferenceActivity{
	private static final String TAG = SDBaseSherlockPreferenceActivity.class.getSimpleName();
	
	/**
	 * add fragment
	 * @param frag
	 */
	@TargetApi(11)
	protected void setPreferenceFragment(PreferenceFragment fragment){
		getFragmentManager().beginTransaction().replace(android.R.id.content, fragment).commit();
	}
}
