package com.cst.stormdroid.image;

import java.util.concurrent.Future;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.cst.stormdroid.image.interfaces.ImageLoaderCallback;
import com.cst.stormdroid.image.interfaces.ImageProcessor;
import com.cst.stormdroid.image.interfaces.ImageRequestCallback;

/**
 * request a image
 * @author MonsterStorm
 * @version 1.0
 */
public class ImageRequest {
	/**
	 * Image loader
	 */
	private static ImageLoader mImageLoader;
	
	/**
	 * loader future
	 */
	private Future<?> mFuture;
	private String mUrl;
	private ImageRequestCallback mCallback;
	private ImageProcessor mProcessor;
	private BitmapFactory.Options mOptions;
	
	public ImageRequest(final String url, final ImageRequestCallback callBack){
		this(url, callBack, null);
	}
	
	public ImageRequest(final String url, final ImageRequestCallback callBack, final ImageProcessor processor){
		this(url, callBack, processor, null);
	}
	
	public ImageRequest(final String url, final ImageRequestCallback callBack, final ImageProcessor processor, final BitmapFactory.Options options){
		mUrl = url;
		mCallback = callBack;
		mProcessor = processor;
		mOptions = options;
	}
	
	
	/**
	 * load a image
	 * @param context
	 */
	public void load(Context context){
		if(mFuture == null){
			if(mImageLoader == null){
				mImageLoader = new ImageLoader(context);
			}
			mFuture = mImageLoader.loadImage(mUrl, new InnerCallback(mCallback), mProcessor, mOptions);
		}
	}
	
	
	/**
	 * cancel a task
	 */
	public void cancel(){
		if(!isCancelled()){
			mFuture.cancel(false);//Here we do not want to force the task to be interrupted. Indeed, it may be useful to keep the result in a cache for a further use
			if(mCallback != null){
				mCallback.onImageRequestCanceled(this);
			}
		}
	}
	
	/**
	 * is the task is canceled
	 * @return
	 */
	public final boolean isCancelled(){
		return mFuture.isCancelled();
	}
	
	
	/**
	 * inner call back of image loader
	 * @author MonsterStorm
	 */
	private class InnerCallback implements ImageLoaderCallback {
		private ImageRequestCallback mCallback;
		public InnerCallback(ImageRequestCallback callBack){
			mCallback = callBack;
		}
		@Override
		public void onImageLoadingStarted(ImageLoader loader) {
			if(mCallback != null){
				mCallback.onImageRequestStart(ImageRequest.this);
			}
		}
		@Override
		public void onImageLoadingEnded(ImageLoader loader, Bitmap bitmap) {
			if(mCallback != null && !isCancelled()){
				mCallback.onImageRequestEnded(ImageRequest.this, bitmap);
			}
			mFuture = null;
		}
		@Override
		public void onImageLoadingFailed(ImageLoader loader, Throwable exception) {
			if(mCallback != null && !isCancelled()){
				mCallback.onImageRequestFailed(ImageRequest.this, exception);
			}
			mFuture = null;
		}
    }
}
