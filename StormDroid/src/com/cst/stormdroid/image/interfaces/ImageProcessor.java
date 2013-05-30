package com.cst.stormdroid.image.interfaces;

import android.graphics.Bitmap;

/**
 * image processor to handler image process
 * @author MonsterStorm
 * @version 1.0
 */
public interface ImageProcessor{
	/**
	 * process image
	 * @param bitmap
	 * @return
	 */
	public Bitmap processImage(Bitmap bitmap);
}
