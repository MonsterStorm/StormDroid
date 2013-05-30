package com.cst.stormdroid.image.interfaces;

import android.graphics.Bitmap;

/**
 * base interface for image cache
 * @author MonsterStorm
 * @version 1.0
 */
public interface BaseImageCacheInterface {
	
	/**
	 * get Image by url
	 * @param url
	 * @return
	 */
	public Bitmap getImageByUrl(final String url);
	
	
	/**
	 * put bmp to cache
	 * @param url
	 * @param bmp
	 * @return
	 */
	public void pushToCache(final String url, final Bitmap bmp);
	
}
