package com.cst.stormdroid.utils.toast;

import android.content.Context;
import android.os.Looper;
import android.widget.Toast;

import com.cst.stormdroid.app.SDBaseApplication;
import com.cst.stormdroid.utils.Config;
import com.cst.stormdroid.utils.string.StringUtil;

/**
 * Util for quick mToast, can only show mToast info by restId
 * @author MonsterStorm
 * @version 1.0
 */
public class ToastUtil {
	/**
	 * old msg of mToast
	 */
	private static CharSequence mOldMsg;
	/**
	 * mToast reference
	 */
    private static Toast mToast = null;
    /**
     * first time of the msg
     */
    private static long mOneTime = 0;
    /**
     * second time of the msg
     */
    private static long mTwoTime = 0;
    /**
     * mToast mLength
     */
    private static int mLength = Toast.LENGTH_SHORT;
	
	/**
	 * show a mToast by a given resId
	 * @param context
	 * @param resId
	 */
	public static void showToast(final int resId){
		showToast(resId, false);
	}
	
	/**
	 * show mToast, whether is Toast.LENGTH_SHORT or Toast.LENGTH_LONG, default is LENGTH_SHORT
	 * @param resId
	 * @param isLong
	 */
	public static void showToast(final int resId, final boolean isLong){
		setLength(isLong);
		showToast(SDBaseApplication.getInstance(), SDBaseApplication.getInstance().getText(resId));
	}
	
	/**
	 * show mToast only in debug mode
	 * @param context
	 * @param resId
	 */
	public static void debugToast(final int resId){
		if(Config.mDebug){
			showToast(SDBaseApplication.getInstance(), SDBaseApplication.getInstance().getText(resId));
		}
	}
	
	/**
	 * show mToast only in debug mode
	 * @param context
	 * @param msg
	 */
	public static void debugToast(final CharSequence msg){
		if(Config.mDebug){
			showToast(SDBaseApplication.getInstance(), msg);
		}
	}
	
	/**
	 * show mToast by a given string, set it to private to let providers use resource, not hard code string in developing.
	 * @param context
	 * @param msg
	 */
	private static void showToast(final Context context, final CharSequence msg){
		Looper.prepare();//to ensure handler is binded to Main Thread
		if(StringUtil.isEmpty(msg)) return;
		if(mToast == null){
			mToast = Toast.makeText(context, msg, mLength);
			mOneTime = System.currentTimeMillis();
			mToast.show();
		} else {
			mTwoTime = System.currentTimeMillis();
			if(msg.equals(mOldMsg)){//only show msg when time elapse or show a different msg
				if(mTwoTime - mOneTime > mLength){
					mToast.show();
				}
			} else {
				mOldMsg = msg;
				mToast.setText(msg);
				mToast.show();
			}
			mOneTime = mTwoTime;
		}
		Looper.loop();
	}
	
	/**
	 * set time of mToast mLength
	 * @param isLong
	 */
	private static void setLength(final boolean isLong){
		if(isLong){
			mLength = Toast.LENGTH_LONG;
		} else {
			mLength = Toast.LENGTH_SHORT;
		}
	}
}
