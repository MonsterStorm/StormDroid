package com.cst.stormdroid.image;

import java.io.File;

import com.cst.stormdroid.app.OnLowMemoryListener;
import com.cst.stormdroid.image.interfaces.BaseImageCacheInterface;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;

/**
 * Cache image in file system
 * @author MonsterStorm
 * @version 1.0
 */
public class ImageFileCache implements BaseImageCacheInterface, OnLowMemoryListener {
	/**
	 * file cache size
	 */
	private static final int DISK_CACHE_SIZE = 1024 * 1024 * 10; // 10MB
	/**
	 * cache sub dir
	 */
	private static final String DISK_CACHE_SUBDIR = "cache";
	
	//android 4.1+
//	private DiskLruCache mDiskCache;
	
	public ImageFileCache(Context context){
		File cacheDir = getCacheDir(context, DISK_CACHE_SUBDIR);
//		mDiskCache = DiskLruCache.openCache(this, cacheDir, DISK_CACHE_SIZE);
	}
	
	/**
	 * Check if media is mounted or storage is built-in, if so, try and use external cache dir otherwise use internal cache dir
	 * @param context
	 * @param uniqueName
	 * @return
	 */
	private File getCacheDir(Context context, String uniqueName) {
		StringBuilder cachePath = new StringBuilder();
		if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) || !Environment.isExternalStorageRemovable()){//API9
			cachePath.append(context.getExternalCacheDir().getPath());
		} else {
			cachePath.append(context.getCacheDir().getPath());
		}
	    return new File(cachePath.toString() + File.separator + uniqueName);
	}
	
	
	@Override
	public Bitmap getImageByUrl(final String url) {
		return null;
	}

	@Override
	public void pushToCache(final String url, final Bitmap bmp) {
	}

	@Override
	public void onLowMemory() {
	}
}
