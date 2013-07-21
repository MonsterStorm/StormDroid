package com.cst.stormdroid.image;

import java.io.InputStream;
import java.net.URL;
import java.util.concurrent.Future;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.os.Process;
import android.util.DisplayMetrics;

import com.cst.stormdroid.app.SDBaseApplication;
import com.cst.stormdroid.image.interfaces.ImageLoaderCallback;
import com.cst.stormdroid.image.interfaces.ImageProcessor;
import com.cst.stormdroid.net.SDThreadPool;
import com.cst.stormdroid.utils.log.SDLog;
import com.cst.stormdroid.utils.string.StringUtil;

/**
 * loader image from
 * @author MonsterStorm
 * @version 1.0
 */
public class ImageLoader {
	private static final String LOG_TAG = ImageLoader.class.getSimpleName();

	// image load status
	public static final int ON_IMAGE_LOAD_START = 1;
	public static final int ON_IMAGE_LOAD_ENDED = 2;
	public static final int ON_IMAGE_LOAD_FAILED = 3;

	/**
	 * image cache
	 */
	private ImageCache mImageCache;

	/**
	 * thread pool
	 */
	private SDThreadPool mThreadPool;

	/**
	 * default bitmap option
	 */
	private BitmapFactory.Options mDefaultOptions;

	private static AssetManager sAssetManager;

	public ImageLoader(Context context) {
		if (mImageCache == null) {
			mImageCache = SDBaseApplication.getInstance().getImageCache();
		}
		if (mThreadPool == null) {
			mThreadPool = SDBaseApplication.getInstance().getThreadPool();
		}
		if (mDefaultOptions == null) {
			mDefaultOptions = new BitmapFactory.Options();
			mDefaultOptions.inDither = true;
			mDefaultOptions.inScaled = true;
			mDefaultOptions.inDensity = DisplayMetrics.DENSITY_MEDIUM;
			mDefaultOptions.inTargetDensity = context.getResources().getDisplayMetrics().densityDpi;
		}
		sAssetManager = context.getAssets();
	}

	/**
	 * load image from url
	 * @param url
	 * @param callback
	 * @return
	 */
    public Future<?> loadImage(String url, ImageLoaderCallback callback) {
        return loadImage(url, callback, null);
    }

    /**
     * load image from url
     * @param url
     * @param callback
     * @param bitmapProcessor
     * @return
     */
    public Future<?> loadImage(String url, ImageLoaderCallback callback, ImageProcessor bitmapProcessor) {
        return loadImage(url, callback, bitmapProcessor, null);
    }
    
	/**
	 * load image
	 * @param url
	 * @param callBack
	 * @param processor
	 * @param options
	 * @return
	 */
	public Future<?> loadImage(final String url, final ImageLoaderCallback callBack, final ImageProcessor bitmapProcessor, final BitmapFactory.Options options) {
		return mThreadPool.submit(new ImageFetcher(url, callBack, bitmapProcessor, options));
	}

	/**
	 * Fetcher image from internet
	 * @author MonsterStorm
	 * 
	 */
	private class ImageFetcher implements Runnable {
		private String mUrl;
		private ImageHandler mHandler;
		private ImageProcessor mBitmapProcessor;
		private BitmapFactory.Options mOptions;

		public ImageFetcher(final String url, final ImageLoaderCallback callBack, final ImageProcessor bitmapProcessor, final BitmapFactory.Options options) {
			mUrl = url;
			mHandler = new ImageHandler(url, callBack);
			mBitmapProcessor = bitmapProcessor;
			mOptions = options;
		}

		public void run() {
			Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);

			final Handler h = mHandler;
			Bitmap bitmap = null;
			Throwable throwable = null;

			h.sendMessage(Message.obtain(h, ON_IMAGE_LOAD_START));

			try {

				if (!StringUtil.isValid(mUrl)) {// if url is valid
					throw new Exception("The given URL cannot be null or empty");
				}

				InputStream inputStream = null;

				if (mUrl.startsWith("file:///android_asset/")) {
					inputStream = sAssetManager.open(mUrl.replaceFirst("file:///android_asset/", ""));
				} else {
					inputStream = new URL(mUrl).openStream();
				}

				bitmap = BitmapFactory.decodeStream(inputStream, null, (mOptions == null) ? mDefaultOptions : mOptions);

				if (mBitmapProcessor != null && bitmap != null) {
					final Bitmap processedBitmap = mBitmapProcessor.processImage(bitmap);
					if (processedBitmap != null) {
						bitmap = processedBitmap;
					}
				}

			} catch (Exception e) {
				// An error occured while retrieving the image
				SDLog.e(LOG_TAG, "Error while fetching image", e);
				throwable = e;
			}

			if (bitmap == null) {
				if (throwable == null) {
					// Skia returned a null bitmap ... that's usually because
					// the given url wasn't pointing to a valid image
					throwable = new Exception("Skia image decoding failed");
				}
				h.sendMessage(Message.obtain(h, ON_IMAGE_LOAD_FAILED, throwable));
			} else {
				h.sendMessage(Message.obtain(h, ON_IMAGE_LOAD_ENDED, bitmap));
			}
		}
	}

	/**
	 * handler 
	 * @author MonsterStorm
	 */
	private class ImageHandler extends Handler {
		private String mUrl;
		private ImageLoaderCallback mCallback;

		public ImageHandler(final String url, final ImageLoaderCallback callback) {
			mUrl = url;
			mCallback = callback;
		}

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case ON_IMAGE_LOAD_START:
				if (mCallback != null)
					mCallback.onImageLoadingStarted(ImageLoader.this);
				break;
			case ON_IMAGE_LOAD_ENDED:
				final Bitmap bitmap = (Bitmap) msg.obj;
				mImageCache.pushToCache(mUrl, bitmap);
				if (mCallback != null)
					mCallback.onImageLoadingEnded(ImageLoader.this, bitmap);
				break;
			case ON_IMAGE_LOAD_FAILED:
				if (mCallback != null)
					mCallback.onImageLoadingFailed(ImageLoader.this, (Throwable) msg.obj);
				break;
			default:
				super.handleMessage(msg);
				break;
			}
		}
	}
}
