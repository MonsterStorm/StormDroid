package com.cst.stormdroid.activity;

import android.app.ListActivity;
import android.os.Bundle;
import android.os.Handler;

import com.cst.stormdroid.adapter.SDBaseAdapter;
import com.cst.stormdroid.utils.log.SDLog;

public class BaseListActivity extends ListActivity{
	private static final String TAG = BaseListActivity.class.getSimpleName();

	private Handler handler;
	// adapter 
	private SDBaseAdapter mAdapter;
	
	
	// *********************************Lifecycle functions*********************************
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		SDLog.d(TAG, "onCreate");
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void onStart() {
		SDLog.d(TAG, "onStart");
		super.onStart();
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		SDLog.d(TAG, "onRestoreInstanceState");
		super.onRestoreInstanceState(savedInstanceState);
	}

	@Override
	protected void onResume() {
		SDLog.d(TAG, "onResume");
		super.onResume();
	}

	@Override
	protected void onPause() {
		SDLog.d(TAG, "onPause");
		super.onPause();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		SDLog.d(TAG, "onSaveInstanceState");
		super.onSaveInstanceState(outState);
	}

	@Override
	protected void onStop() {
		SDLog.d(TAG, "onStop");
		super.onStop();
	}

	@Override
	protected void onRestart() {
		SDLog.d(TAG, "onRestart");
		super.onRestart();
	}

	@Override
	protected void onDestroy() {
		SDLog.d(TAG, "onDestroy");
		super.onDestroy();
	}
}
