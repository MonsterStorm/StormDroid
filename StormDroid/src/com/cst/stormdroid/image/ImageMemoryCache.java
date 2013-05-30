package com.cst.stormdroid.image;

import java.lang.ref.SoftReference;
import java.util.LinkedHashMap;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.cst.stormdroid.app.OnLowMemoryListener;
import com.cst.stormdroid.image.interfaces.BaseImageCacheInterface;

/**
 * Cache image in memory
 * Use double level cache, a hard cache and a soft cache.
 * The hard cache store images that used frequently and the soft cache store images used not frequently. 
 * @author MonsterStorm
 * @version 1.0
 */
public class ImageMemoryCache implements BaseImageCacheInterface, OnLowMemoryListener{
	/**
	 * soft cache size
	 */
	private static final int SOFT_CACHE_SIZE = 10;
	/**
	 * hard reference cache
	 */
	private LruCache<String, Bitmap> mLruCache;
	/**
	 * soft reference cache, which is also a lru
	 */
	private LinkedHashMap<String, SoftReference<Bitmap>> mSoftCache; 
	
	
	public ImageMemoryCache(Context context){
		final int memClass = ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE)).getMemoryClass(); 
	    // Use 1/8th of the available memory for this memory cache. 
	    final int cacheSize = 1024 * 1024 * memClass / 8; 
	    
		mLruCache = new LruCache<String, Bitmap>(cacheSize){
			@Override
            protected int sizeOf(String key, Bitmap bmp) {
                if (bmp != null)
//                	return bmp.getByteCount();//api level > 11
                    return bmp.getRowBytes() * bmp.getHeight();
                else
                    return 0;
            }
                     
			//Called for entries that have been evicted or removed
            @Override
            protected void entryRemoved(boolean evicted, String key, Bitmap oldValue, Bitmap newValue) {
                if (oldValue != null && mSoftCache.get(key) == null)//When the hard cache is full, put the image not used frequently in the soft cache according to the LRU method
                    mSoftCache.put(key, new SoftReference<Bitmap>(oldValue));
            }
		};
		//set accessOrder to ture, then the list is order from least-recently accessed to most-recently accessed
		mSoftCache = new LinkedHashMap<String, SoftReference<Bitmap>>(SOFT_CACHE_SIZE, 0.75f, true) {
            private static final long serialVersionUID = 6040103833179403725L;
            @Override
            protected boolean removeEldestEntry(Entry<String, SoftReference<Bitmap>> eldest) {
                if (size() > SOFT_CACHE_SIZE){   
                    return true; 
                } 
                return false;
            }
        };
	}
	
	/**
	 * get a image from cache by url
	 */
	@Override
	public Bitmap getImageByUrl(final String url) {
		Bitmap bitmap;
        synchronized (mLruCache) {
            bitmap = mLruCache.get(url);
            if (bitmap != null) {//remove from the LRU cache and put it back
                mLruCache.remove(url);
                mLruCache.put(url, bitmap);
                return bitmap;
            } else {
            	mLruCache.remove(url);
            }
        }
        synchronized (mSoftCache) {
            SoftReference<Bitmap> bitmapReference = mSoftCache.get(url);
            if (bitmapReference != null) {
                bitmap = bitmapReference.get();
                if (bitmap != null) {//if is in the soft cache, then put it back to hard cache
                	mLruCache.put(url, bitmap);
                	mSoftCache.remove(url);
                    return bitmap;
                } else {
                	mSoftCache.remove(url);
                }
            }
        }
        return null;
	}

	/**
	 * put a bitmap to cache
	 */
	@Override
	public void pushToCache(final String url, final Bitmap bmp) {
		if(bmp != null && getImageByUrl(url) == null){
			mLruCache.put(url, bmp);
		}
	}
	
	/**
	 * clear cache
	 */
	public void clear(){
		mLruCache.evictAll();
		mSoftCache.clear();
	}
	
	
	@Override
	public void onLowMemory() {
		clear();
	}
}
