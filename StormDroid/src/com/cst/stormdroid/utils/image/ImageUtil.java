package com.cst.stormdroid.utils.image;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * a util class for decode image
 * @author MonsterStorm
 * @version 1.0
 */
public class ImageUtil {

	/**
	 * decode bitmap form resource
	 * @param res
	 * @param resId
	 * @param reqWidth
	 * @param reqHeight
	 * @return
	 */
	public static Bitmap decode(Resources res, int resId, int reqWidth, int reqHeight) {
		final BitmapFactory.Options options = decodeBounds(res, resId);

		options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeResource(res, resId, options);
	}

	/**
	 * calculate in sample size
	 * @param options
	 * @param reqWidth
	 * @param reqHeight
	 * @return
	 */
	public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
		return calculateInSampleSize(options, reqWidth, reqHeight, false);
	}

	/**
	 * calculate in sample size
	 * @param options the options that is already get the dimensions
	 * @param reqWidth
	 * @param reqHeight
	 * @param isPowOf2 ,cause the decoder of image is more efficient when inSampleSize is power of 2, 
	 * 			this is set to true when u want decode a file more quickly, and the image will not used to save in cache or disk,
	 * 			otherwise it's better to decode to a bitmap more close to the true dimensions.
	 * @return
	 */
	public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight, boolean isPowOf2) {
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {

			int heightRatio = Math.round((float) height / (float) reqHeight);
			int widthRatio = Math.round((float) width / (float) reqWidth);

			if (isPowOf2) {
				int h = 1, w = 1;
				while (h < heightRatio || w < widthRatio) {
					if (h < heightRatio)
						h *= 2;
					if (w < widthRatio)
						w *= 2;
				}
				heightRatio = h;
				widthRatio = w;
			}

			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
		}

		return inSampleSize;
	}

	/**
	 * decode Bounds of a res
	 * @param res
	 * @param resid
	 * @return
	 */
	public static BitmapFactory.Options decodeBounds(Resources res, int resid) {
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeResource(res, resid, options);
		return options;
	}
}
