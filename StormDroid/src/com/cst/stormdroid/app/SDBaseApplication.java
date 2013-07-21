package com.cst.stormdroid.app;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.res.Configuration;

import com.cst.stormdroid.image.ImageCache;
import com.cst.stormdroid.net.SDThreadPool;
import com.cst.stormdroid.utils.Config;
import com.cst.stormdroid.utils.CrashHandler;
import com.cst.stormdroid.utils.Globals;

/**
 * Main Application, used in AndroidManifest.xml
 * <application android:name="com.cst.stormdroid.app.SDApplication">
 * @author MonsterStorm
 * @version 1.0
 */
public class SDBaseApplication extends Application{
	/**
	 * Image cache
	 */
	private ImageCache mImageCache;
	/**
	 * Thread pool
	 */
	private SDThreadPool mThreadPool;
	/**
	 * on low memory listeners, use weak reference to refer all unnecessary listeners 
	 */
	private List<WeakReference<OnLowMemoryListener>> mOnLowMemoryListeners;
	
	/**
	 * singleton
	 */
	private static SDBaseApplication mInstance;
	
	
	public static SDBaseApplication getInstance(){
		return mInstance;
	}
	
	public SDBaseApplication(){
		mInstance = this;
		mOnLowMemoryListeners = new ArrayList<WeakReference<OnLowMemoryListener>>();
	}
		
	public void registerOnLowMemoryListener(OnLowMemoryListener listener){
		if(listener != null){
			mOnLowMemoryListeners.add(new WeakReference<OnLowMemoryListener>(listener));
		}
	}
	
	@Override
	public void onLowMemory() {
		super.onLowMemory();
		int i = 0;
        while (i < mOnLowMemoryListeners.size()) {
            final OnLowMemoryListener listener = mOnLowMemoryListeners.get(i).get();
            if (listener == null) {
            	mOnLowMemoryListeners.remove(i);
            } else {
                listener.onLowMemory();
                i++;
            }
        }
	}
	
	@Override
	@TargetApi(14)
	public void onTrimMemory(int level) {
		super.onTrimMemory(level);
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}
	//--------------------------life cycle functions-----------------------------
	@Override
	public void onCreate() {
		super.onCreate();
		
		//init crash handler
		if(Config.mHandleCrash){
			CrashHandler.getInstance().init(this);
		}
		
		//init globals
		Globals.init(this);
		
	}
	
	/**
	 * exit app
	 */
	public void exitApp(){
		
	}
	
	//----------------------------getter and setter------------------------------
	/**
	 * get thread pool
	 * @return
	 */
	public synchronized SDThreadPool getThreadPool(){
		if(mThreadPool == null){
			mThreadPool = new SDThreadPool();
		}
		return mThreadPool;
	}
	
	/**
	 * get Image Cache
	 */
	public synchronized ImageCache getImageCache(){
		if(mImageCache == null){
			mImageCache = new ImageCache(this);
		}
		return mImageCache;
	}
}
