package com.cst.stormdroid.image;

import android.content.Context;
import android.graphics.Bitmap;

import com.cst.stormdroid.app.OnLowMemoryListener;
import com.cst.stormdroid.image.interfaces.BaseImageCacheInterface;
import com.cst.stormdroid.utils.string.StringUtil;

/**
 * Image Cache
 * @author MonsterStorm
 * @version 1.0
 */
public class ImageCache implements BaseImageCacheInterface, OnLowMemoryListener {
	/**
	 * memory cache
	 */
	private ImageMemoryCache memoryCache;
	/**
	 * file cache
	 */
	private ImageFileCache fileCache;
	
	
	public ImageCache(Context context){
		memoryCache = new ImageMemoryCache(context);
		fileCache = new ImageFileCache(context);
	}

	/**
	 * get image from cache:
	 * 1. first get directly from memory, if not in memory then
	 * 2. get directly from filesystem, if is exists then return, and put it into memroy cache else then
	 * 3. download from network, if download successful, then return and put it into memory and filesystem else return null 
	 * @param url image url
	 */
	@Override
	public Bitmap getImageByUrl(final String url) {
		Bitmap bmp = null;
		if(StringUtil.isStrongValid(url)){//is valid url
			if(memoryCache != null)
				bmp = memoryCache.getImageByUrl(url);
			
			if(bmp == null){//not in memory
				if(fileCache != null)
					bmp = fileCache.getImageByUrl(url);
				
				if(bmp != null){
					memoryCache.pushToCache(url, bmp);
				}
				/*if(bmp == null){//not in memory and not in filesystem, then get from network
					bmp = getImageFromNetwork(url);
					if(bmp != null){
						if(memoryCache != null)
							memoryCache.pushToCache(url, bmp);
						if(fileCache != null)
							fileCache.pushToCache(url, bmp);
					}
				} else {
					memoryCache.pushToCache(url, bmp);
				}*/
			}
		}
		return bmp;
	}

	/**
	 * push to memory cache and to file cache
	 */
	@Override
	public void pushToCache(final String url, final Bitmap bmp) {
		if(StringUtil.isValid(url) && bmp != null){
			if(memoryCache != null && memoryCache.getImageByUrl(url) == null){
				memoryCache.pushToCache(url, bmp);
			}
			if(fileCache != null && fileCache.getImageByUrl(url) == null){
				fileCache.pushToCache(url, bmp);
			}
		}
	}
	
	@Override
	public void onLowMemory() {
		memoryCache.onLowMemory();
		fileCache.onLowMemory();
	}
}
