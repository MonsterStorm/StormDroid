package com.cst.stormdroid.app;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import android.app.Application;

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
	private ImageCache imageCache;
	/**
	 * Thread pool
	 */
	private SDThreadPool threadPool;
	/**
	 * on low memory listeners, use weak reference to refer all unnecessary listeners 
	 */
	private List<WeakReference<OnLowMemoryListener>> onLowMemoryListeners;
	
	/**
	 * singleton
	 */
	public static SDApplication instance;
	
	
	public static SDApplication getInstance(){
		return instance;
	}
	
	public SDApplication(){
		instance = this;
		onLowMemoryListeners = new ArrayList<WeakReference<OnLowMemoryListener>>();
	}
	
	/**
	 * run a task on background
	 * @param runnale
	 */
	public void submit(Runnable runnable){
		threadPool.submit(runnable);
	}
	
	public void registerOnLowMemoryListener(OnLowMemoryListener listener){
		if(listener != null){
			onLowMemoryListeners.add(new WeakReference<OnLowMemoryListener>(listener));
		}
	}
	
	@Override
	public void onLowMemory() {
		super.onLowMemory();
		int i = 0;
        while (i < onLowMemoryListeners.size()) {
            final OnLowMemoryListener listener = onLowMemoryListeners.get(i).get();
            if (listener == null) {
            	onLowMemoryListeners.remove(i);
            } else {
                listener.onLowMemory();
                i++;
            }
        }
	}
	
	//----------------------------getter and setter------------------------------
	/**
	 * get thread pool
	 * @return
	 */
	public synchronized SDThreadPool getThreadPool(){
		if(threadPool == null){
			threadPool = new SDThreadPool();
		}
		return threadPool;
	}
	
	/**
	 * get Image Cache
	 */
	public synchronized ImageCache getImageCache(){
		if(imageCache == null){
			imageCache = new ImageCache(this);
		}
		return imageCache;
	}
}
