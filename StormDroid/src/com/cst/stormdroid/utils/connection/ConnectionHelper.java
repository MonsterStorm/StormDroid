package com.cst.stormdroid.utils.connection;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import com.cst.stormdroid.R;
import com.cst.stormdroid.app.SDBaseApplication;
import com.cst.stormdroid.utils.string.StringUtil;
import com.cst.stormdroid.utils.toast.ToastUtil;

/**
 * network status manager
 * @author cst
 */
public class ConnectionHelper {
	private static final String DEFAULT_HOST_ADDRESS = "www.baidu.com";
	private static Integer DEFAULT_HOST = null;
	private static final Context mCtx;

	static {
		mCtx = SDBaseApplication.getInstance();
		new Thread() {
			public void run() {
				DEFAULT_HOST = lookupHost(DEFAULT_HOST_ADDRESS);
			};
		}.start();
	}

	/**
	 * is wifi Connected
	 */
	public static boolean isWifiConnected() {
		ConnectivityManager connMgr = (ConnectivityManager) mCtx.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		return networkInfo.isConnected();
	}

	/**
	 * is mobile connected
	 */
	public static boolean isMobileConnected() {
		ConnectivityManager connMgr = (ConnectivityManager) mCtx.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		return networkInfo.isConnected();
	}

	/**
	 * is network connected
	 */
	public static boolean isNetworkConnected() {
		ConnectivityManager connMgr = (ConnectivityManager) mCtx.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		return (networkInfo != null && networkInfo.isConnected());
	}

	/**
	 * get network type
	 * 
	 * @param intent
	 * @return
	 */
	public static Integer getType(Intent intent) {
		NetworkInfo networkInfo = (NetworkInfo) intent.getParcelableExtra(ConnectivityManager.EXTRA_NETWORK_INFO);
		if (networkInfo != null && (networkInfo.getState() == State.CONNECTED || networkInfo.getState() == State.SUSPENDED))
			return networkInfo.getType();
		return null;
	}

	/**
	 * get Network type
	 * 
	 * @param context
	 * @return
	 */
	public static Integer getType() {
		ConnectivityManager connMgr = (ConnectivityManager) mCtx.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		if (networkInfo != null) {
			return networkInfo.getType();
		} else {
			return null;
		}
	}

	/**
	 * get wifi info
	 * 
	 * @param ctx
	 * @return
	 */
	public static String getWifiSSID() {
		final WifiManager manager = (WifiManager) mCtx.getSystemService(Context.WIFI_SERVICE);
		if (manager != null) {
			WifiInfo wifiInfo = manager.getConnectionInfo();
			if (wifiInfo != null && !StringUtil.isEmpty(wifiInfo.getSSID())) {
				return wifiInfo.getSSID();
			}
		}
		return null;
	}
	
	/**
	 * get wifi info
	 * @param ctx
	 * @return
	 */
	public static int getWifiState() {
		final WifiManager manager = (WifiManager) mCtx.getSystemService(Context.WIFI_SERVICE);
		if (manager != null) {
			return manager.getWifiState();
		}
		return WifiManager.WIFI_STATE_DISABLED;
	}
	
	/**
	 * get wifi info
	 * @param ctx
	 * @return
	 */
	public static WifiInfo getWifiInfo() {
		final WifiManager manager = (WifiManager) mCtx.getSystemService(Context.WIFI_SERVICE);
		if (manager != null) {
			return manager.getConnectionInfo();
		}
		return null;
	}

	/**
	 * check the network and toast when it has not connected
	 */
	public static boolean checkAndToast() {
		boolean isConnected = isNetworkConnected();
		if (!isConnected) {
			ToastUtil.showToast(R.string.info_network_not_connected);
		}
		return isConnected;
	}

	// ***********************************methods must called in a thread**************************************
	/**
	 * whether current connected network is active, and can connected to internet
	 * this method must called in a thread
	 * 
	 * @param ctx
	 * @return
	 */
	public static boolean isNetworkActive(Context ctx) {
		if (DEFAULT_HOST != null) {
			return isNetworkActive(ctx, DEFAULT_HOST);
		} else {
			return isNetworkActive(ctx, DEFAULT_HOST_ADDRESS);
		}
	}

	/**
	 * whether current connected network is active, and can connected to internet
	 * this method must called in a thread
	 * 
	 * @return
	 */
	public static boolean isNetworkActive(Context ctx, String hostAddress) {
		int host = lookupHost(hostAddress);
		if (DEFAULT_HOST == null) {
			DEFAULT_HOST = host;
		}
		return isNetworkActive(ctx, host);
	}

	/**
	 * whether current connected network is active, and can connected to internet
	 * this method must called in a thread
	 * 
	 * @param ctx
	 * @param host
	 * @return
	 */
	public static boolean isNetworkActive(Context ctx, int host) {
		ConnectivityManager connMgr = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isConnected()) {
			int type = networkInfo.getType();
			return connMgr.requestRouteToHost(type, host);
		} else {
			return false;
		}
	}

	/**
	 * convert IPV4 to int
	 * this method must called in a thread
	 * 
	 * @param hostname
	 * @return
	 */
	private static int lookupHost(String hostname) {
		InetAddress inetAddress;
		try {
			inetAddress = InetAddress.getByName(hostname);
		} catch (UnknownHostException e) {
			return -1;
		}
		byte[] addrBytes;
		int addr = 0;
		addrBytes = inetAddress.getAddress();
		for (int i = addrBytes.length - 1; i >= 0; i--) {
			addr = addr | ((addrBytes[i] & 0xff) << 8 * i);
		}
		return addr;
	}

	public static int lookupHost2(String hostname) {
		hostname = hostname.substring(0, hostname.indexOf(":") > 0 ? hostname.indexOf(":") : hostname.length());
		String result = "";
		String[] array = hostname.split("\\.");
		if (array.length != 4)
			return -1;

		int[] hexArray = new int[] { 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0 };
		hexArray[0] = Integer.parseInt(array[0]) / 16;
		hexArray[1] = Integer.parseInt(array[0]) % 16;
		hexArray[2] = Integer.parseInt(array[1]) / 16;
		hexArray[3] = Integer.parseInt(array[1]) % 16;
		hexArray[4] = Integer.parseInt(array[2]) / 16;
		hexArray[5] = Integer.parseInt(array[2]) % 16;
		hexArray[6] = Integer.parseInt(array[3]) / 16;
		hexArray[7] = Integer.parseInt(array[3]) % 16;

		for (int i = 0; i < 8; i++) {
			result += Integer.toHexString(hexArray[i]);
		}

		return Long.valueOf(Long.parseLong(result, 16)).intValue();
	}

	/**
	 * whether has an active connection which can connection to the internect
	 * this method must called in a thread
	 * 
	 * @param context
	 * @return
	 */
	public static boolean hasActivieInternetConnection(Context context) {
		return hasActiveInternetConnection(context, DEFAULT_HOST_ADDRESS);
	}

	/**
	 * whether has an active connection which can connection to the internect
	 * this method must called in a thread
	 * 
	 * @param context
	 * @return
	 */
	public static boolean hasActiveInternetConnection(Context context, String hostAddress) {
		if (isNetworkConnected()) {
			try {
				HttpURLConnection urlc = (HttpURLConnection) (new URL("http://" + hostAddress).openConnection());
				urlc.setRequestProperty("User-Agent", "Test");
				urlc.setRequestProperty("Connection", "close");
				urlc.setConnectTimeout(1500);
				urlc.connect();
				return (urlc.getResponseCode() == 200);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
		}
		return false;
	}
}
