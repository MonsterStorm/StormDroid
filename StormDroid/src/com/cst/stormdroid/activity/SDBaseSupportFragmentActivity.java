package com.cst.stormdroid.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;

import com.cst.stormdroid.R;
import com.cst.stormdroid.utils.Constants;
import com.cst.stormdroid.utils.log.SDLog;

/**
 * base fragment activity for all fragment activities
 * @author MonsterStorm
 * @version 1.0
 */
public class SDBaseSupportFragmentActivity extends FragmentActivity {
	private static final String TAG = SDBaseSupportFragmentActivity.class.getSimpleName();

	/**
	 * handler
	 */
	public Handler mHandler = new Handler();

	/**
	 * default Enter animation
	 */
	private int mEnterAnimDefault = R.anim.anim_slide_right_in;
	/**
	 * default exit animation
	 */
	private int mExitAnimDefault = R.anim.anim_slide_left_out;

	// base handle message
	public Handler mTipHandler = new Handler() {
		public void handleMessage(Message msg) {
			SDBaseSupportFragmentActivity.this.handleMessage(msg);
		};
	};

	// base post handler for post some runnable
	public Handler mPostHandler = new Handler();

	// toast a message
	protected void tip(int msgType) {
		mTipHandler.sendEmptyMessage(msgType);
	}

	/**
	 * handler message
	 * @param msg
	 */
	protected void handleMessage(Message msg) {
		switch (msg.what) {
		case Constants.MESSAGE_COMMON_NODATA:
			break;
		case Constants.MESSAGE_COMMON_ERROR:
			break;
		case Constants.MESSAGE_HTTP_START:
			break;
		case Constants.MESSAGE_HTTP_FINISH:
			break;
		case Constants.MESSAGE_HTTP_ERROR:
			break;
		default:
			break;
		}
	}

	/**
	 * start Activity With Animation
	 * @param intent
	 * @param enterAnim
	 * @param exitAnim
	 */
	public void startActivityWithAnim(Intent intent) {
		super.startActivity(intent);
		this.overridePendingTransition(mEnterAnimDefault, mExitAnimDefault);
	}

	/**
	 * start Activity With Animation
	 * @param intent
	 * @param enterAnim
	 * @param exitAnim
	 */
	public void startActivityWithAnim(Intent intent, final int enterAnim, final int exitAnim) {
		super.startActivity(intent);
		this.overridePendingTransition(enterAnim, exitAnim);
	}

	/**
	 * set override pending anim
	 * @param enterAnim
	 * @param exitAnim
	 */
	public void setDefaultPendingAnim(final int enterAnim, final int exitAnim) {
		this.mEnterAnimDefault = enterAnim;
		this.mExitAnimDefault = exitAnim;
	}

	/**
	 * register Broadcast Receiver
	 */
	protected void registerBroadcastReceiver() {
	};

	/**
	 * unregister Broadcast Receiver
	 */
	protected void unregisterBroadcastReceiver() {
	};

	/**
	 * show softkeyboard
	 */
	protected void showSoftKeyboard() {
		InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
	}

	/**
	 * hide softKeyboard
	 */
	protected void hideSoftKeyboard() {
		Window window = getWindow();
		if (window != null) {
			View view = window.getDecorView();
			if (view != null) {
				IBinder binder = view.getWindowToken();
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				if (imm.isActive()) {
					imm.hideSoftInputFromWindow(binder, 0);
				}
			}
		}
	}

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
		// register broadcast receiver
		registerBroadcastReceiver();
	}

	@Override
	protected void onPause() {
		SDLog.d(TAG, "onPause");
		super.onPause();
		// unregister broadcast receiver
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
