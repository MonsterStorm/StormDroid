package com.cst.stormdroid.utils.connection;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.cst.stormdroid.R;
import com.cst.stormdroid.app.SDBaseApplication;
import com.cst.stormdroid.utils.toast.ToastUtil;

/**
 * Connection util to phone connection
 * @author MonsterStorm
 * @version 1.0
 */
public class ConnectionUtil {
	/**
	 * context 
	 */
	private static final Context mCtx;
	
	static {
		mCtx = SDBaseApplication.getInstance();
	}
	
	/**
	 * is wifi Connected
	 */
	public static boolean isWifiConnected(){
		ConnectivityManager connMgr = (ConnectivityManager)mCtx.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI); 
		return networkInfo.isConnected();
	}
	/**
	 * is mobile connected
	 */
	public static boolean isMobileConnected(){
		ConnectivityManager connMgr = (ConnectivityManager)mCtx.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE); 
		return networkInfo.isConnected();
	}
	
	/**
	 * is network connected
	 */
	public static boolean isNetworkConnected(){
		ConnectivityManager connMgr = (ConnectivityManager)mCtx.getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
	    return (networkInfo != null && networkInfo.isConnected());
	}
	
	/**
	 * check the network and toast when it has not connected
	 */
	public static boolean checkAndToast(){
		boolean isConnected = isNetworkConnected();
		if(!isConnected){
			ToastUtil.showToast(R.string.info_network_not_connected);
		}
		return isConnected;
	}
}
