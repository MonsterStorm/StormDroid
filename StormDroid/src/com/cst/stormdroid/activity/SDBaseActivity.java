package com.cst.stormdroid.activity;

import java.io.Serializable;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;

import com.cst.stormdroid.utils.Constants;
import com.cst.stormdroid.utils.log.SDLog;

/**
 * base activity for all activities
 * @author MonsterStorm
 * @version 1.0
 */
public class SDBaseActivity extends Activity {
	private static final String TAG = SDBaseActivity.class.getSimpleName();

	// base handle message
	public Handler mTipHandler = new Handler(){
		public void handleMessage(Message msg) {
			SDBaseActivity.this.handleMessage(msg);
		};
	};

	// toast a message
	protected void tip(int msgType) {
		mTipHandler.sendEmptyMessage(msgType);
	}
	
	/**
	 * handler message
	 * @param msg
	 */
	protected void handleMessage(Message msg){
		switch(msg.what){
		case Constants.MESSAGE_COMMON_NODATA :
			break;
		case Constants.MESSAGE_COMMON_ERROR : 
			break;
		case Constants.MESSAGE_HTTP_START:
			break;
		case Constants.MESSAGE_HTTP_FINISH:
			break;
		case Constants.MESSAGE_HTTP_ERROR:
			break;
		default :
			break;
		}
	}
	
	/**
	 * get int extra with default -1
	 * @param fieldName
	 * @return
	 */
	protected int getIntExtra(final String name) {
		return getIntent().getIntExtra(name, -1);
	}

	/**
	 * get String exta
	 * @param name
	 * @return
	 */
	protected String getStringExtra(final String name) {
		return getIntent().getStringExtra(name);
	}

	/**
	 * Get string array extra
	 * @param name
	 * @return string array
	 */
	protected String[] getStringArrayExtra(final String name) {
		return getIntent().getStringArrayExtra(name);
	}

	/**
	 * get Serializable extra
	 * @param name
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected <V extends Serializable> V getSerializableExtra(final String name) {
		return (V) getIntent().getSerializableExtra(name);
	}

	/**
	 * get Parcelable extra
	 * @param name
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected <V extends Parcelable> V getParcelableExtra(final String name) {
		return (V) getIntent().getParcelableExtra(name);
	}
	
	
	/**
	 * redirect to other activity
	 * @param activity
	 */
	protected void redirctTo(Activity activity){
	}
	

	/**
	 * register Broadcast Receiver
	 */
	protected void registerBroadcastReceiver(){};
	/**
	 * unregister Broadcast Receiver
	 */
	protected void unregisterBroadcastReceiver(){};
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
		//register broadcast receiver
		registerBroadcastReceiver();
	}

	@Override
	protected void onPause() {
		SDLog.d(TAG, "onPause");
		super.onPause();
		//unregister broadcast receiver
		unregisterBroadcastReceiver();
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
