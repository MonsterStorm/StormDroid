package com.cst.stormdroid.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.actionbarsherlock.app.SherlockListFragment;
import com.cst.stormdroid.adapter.SDBaseArrayAdapter;
import com.cst.stormdroid.fragment.interfaces.SDBaseCallback;
import com.cst.stormdroid.utils.log.SDLog;

/**
 * base fragment extends sherlock fragment, to support actionbar in android 2.x
 * @author MonsterStorm
 * @version 1.0
 */
public class SDBaseSherlockListFragment<T extends SDBaseArrayAdapter> extends SherlockListFragment {
	// tag for log
	private static final String TAG = SDBaseSherlockListFragment.class.getSimpleName();

	/**
	 * adapter
	 */
	protected T mAdapter;
	
	/**
	 * callback for communication with activity and other fragments
	 */
	public SDBaseCallback mCallback;

	/**
	 * init data for this fragment
	 */
	protected void initData() {
	};

	/**
	 * bind actions for this fragment, Note that action in list item will be defined in Adapter, actions defined there are actions on group view, such as onListItemClick
	 */
	protected void bindActions() {
	};

	/**
	 * init callback
	 */
	protected void initCallback(Activity activity) {
		try {
			mCallback = (SDBaseCallback) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString() + " must implement SDBaseCallback if you want communication with activity or other fragments");
		}
	}

	// *********************************Lifecycle functions*********************************
	@Override
	public void onAttach(Activity activity) {
		SDLog.d(TAG, "onAttach");
		super.onAttach(activity);
		initCallback(activity);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		SDLog.d(TAG, "onCreate");
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		SDLog.d(TAG, "onCreateView");
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		SDLog.d(TAG, "onActivityCreated");
		super.onActivityCreated(savedInstanceState);
		initData();
		bindActions();
	}

	@Override
	public void onStart() {
		SDLog.d(TAG, "onStart");
		super.onStart();
	}

	@Override
	public void onResume() {
		SDLog.d(TAG, "onResume");
		super.onResume();
	}

	@Override
	public void onPause() {
		SDLog.d(TAG, "onPause");
		super.onPause();
	}

	@Override
	public void onStop() {
		SDLog.d(TAG, "onStop");
		super.onStop();
	}

	@Override
	public void onDestroyView() {
		SDLog.d(TAG, "onDestroyView");
		super.onDestroyView();
	}

	@Override
	public void onDestroy() {
		SDLog.d(TAG, "onDestroy");
		super.onDestroy();
	}

	@Override
	public void onDetach() {
		SDLog.d(TAG, "onDetach");
		super.onDetach();
	}
}
