package com.cst.stormdroid.activity.interfaces;

import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * interface that handler fragments communication
 * @author MonsterStorm
 *
 */
public interface OnFragmentsCommunicate {
	/**
	 * fragment sended date received by FragmentActivity
	 * @param fragment
	 * @param data
	 */
	public void onDataRecevied(Fragment fragment, Bundle data);
}
