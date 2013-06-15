package com.cst.stormdroid.app;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import android.app.Application;
import android.content.res.Configuration;

import com.cst.stormdroid.image.ImageCache;
import com.cst.stormdroid.net.SDThreadPool;

/**
 * Main Application, used in AndroidManifest.xml
 * <application android:name="com.cst.stormdroid.app.SDApplication">
 * @author MonsterStorm
 * @version 1.0
 */
public class SDApplication extends Application{
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
	private static SDApplication mInstance;
	
	
	public static SDApplication getInstance(){
		return mInstance;
	}
	
	public SDApplication(){
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
	
	/*
	 * must up api_level 14
	@Override
	public void onTrimMemory(int level) {
		super.onTrimMemory(level);
	}*/
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
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
