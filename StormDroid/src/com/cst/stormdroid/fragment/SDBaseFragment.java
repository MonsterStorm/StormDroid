package com.cst.stormdroid.fragment;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cst.stormdroid.utils.log.SDLog;
/**
 * base class for all fragments
 * @author MonsterStorm
 * @version 1.0
 */
@TargetApi(11)
public class SDBaseFragment extends Fragment {
	//tag for log
	private static final String TAG = SDBaseFragment.class.getSimpleName();
	
	// *********************************Lifecycle functions*********************************
	@Override
	public void onAttach(Activity activity) {
		SDLog.d(TAG, "onAttach");
		super.onAttach(activity);
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
