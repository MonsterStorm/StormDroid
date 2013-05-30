package com.cst.stormdroid.image.interfaces;

import android.graphics.Bitmap;

import com.cst.stormdroid.image.ImageLoader;

/**
 * Image Loader callback
 * @author MonsterStorm
 * @version 1.0
 */
public interface ImageLoaderCallback {
	void onImageLoadingStarted(ImageLoader loader);

	void onImageLoadingEnded(ImageLoader loader, Bitmap bitmap);

	void onImageLoadingFailed(ImageLoader loader, Throwable exception);
}