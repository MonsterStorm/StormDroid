package com.cst.stormdroid.image.interfaces;

import android.graphics.Bitmap;

import com.cst.stormdroid.image.ImageRequest;

/**
 * interface
 * @author MonsterStorm
 */
public interface ImageRequestCallback{
	public void onImageRequestStart(ImageRequest imageRequest);
	public void onImageRequestEnded(ImageRequest imageRequest, Bitmap bm);
	public void onImageRequestFailed(ImageRequest imageRequest, Throwable exception);
	public void onImageRequestCanceled(ImageRequest imageRequest);
}