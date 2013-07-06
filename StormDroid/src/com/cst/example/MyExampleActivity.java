package com.cst.example;

import android.os.Bundle;

import com.cst.stormdroid.activity.SDBaseActivity;

/**
 * simple example activity, only show the function of some life cycle functions.
 * @author MonsterStorm
 *
 */
public class MyExampleActivity extends SDBaseActivity{

	/**
	 * called when the life cycle start, should init ui there
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}


	/**
	 * called after onCreate, used to resume the ui, the bundle savedInstanceState is from function onSavedInstanceState
	 * the bundle savedInstanceState is also being passed to onCreate function, this method only called since Activity being destroied last time.
	 * Called before the activity becomes foreground
	 */
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
	}
	

	/**
	 * Called when configuration is changed, and this activity is becoming visible
	 */
	@Override
	protected void onRestart() {
		super.onRestart();
	}
	
	/**
	 * Called when the activity is in visible life cycle.
	 */
	@Override
	protected void onStart() {
		super.onStart();
	}

	/**
	 * Called when the activity is in lifetime.
	 * Called when resume the activity, when inactive UI needs update, thread or process needs update.
	 */
	@Override
	protected void onResume() {
		super.onResume();
	}

	/**
	 * save UI status to savedInstanceState, when the thread paused, then the bundle will passed to onCreate and onRestoreInstanceState
	 */
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

	/**
	 * Called when activity is at the end of lift cycle
	 * hang on unused UI thread, cpu intensive thread
	 */
	@Override
	protected void onPause() {
		super.onPause();
	}

	/**
	 * Called at the end of life cycle
	 * hang on unused UI update, thread. save all edited status and value, cause the process would be killed after this method called
	 */
	@Override
	protected void onStop() {
		super.onStop();
	}

	/**
	 * Called at he entire life cycle
	 * clear all resources, including threads, close the connection and etc.
	 */
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}
