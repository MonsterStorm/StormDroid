package com.cst.stormdroid.image.interfaces;

import android.graphics.Bitmap;

import com.cst.stormdroid.ui.AsyncImageView;

/**
 * ImageView load listener
 * @author MonsterStorm
 * @version 1.0
 */
public interface OnImageViewLoadListener {

    /**
     * Called when the image started to load
     * @param imageView The AsyncImageView that started loading
     */
    void onLoadingStarted(AsyncImageView imageView);

    /**
     * Called when the image ended to load that is when the image has been downloaded and is ready to be displayed on screen
     * @param imageView The AsyncImageView that ended loading
     */
    void onLoadingEnded(AsyncImageView imageView, Bitmap image);

    /**
     * Called when the image loading failed
     * @param imageView The AsyncImageView that failed to load
     */
    void onLoadingFailed(AsyncImageView imageView, Throwable throwable);
}
