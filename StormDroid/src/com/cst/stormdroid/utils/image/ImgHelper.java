package com.cst.stormdroid.utils.image;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.cst.stormdroid.app.SDBaseApplication;
import com.cst.stormdroid.utils.dimension.DimensionUtil;
import com.cst.stormdroid.utils.string.StringUtil;

/**
 * 
 * 1. 如果图片大小小于100Kb，那么不压缩直接上传。
 * 
 * 2. 图片压缩时的质量控制：
 * 图片压缩时的质量控制在0.95-0.8之间。
 * 当图片等于0.1Mb时，质量为0.95；当图片大于2Mb时，质量为0.8；当图片的大小（设为x）在0.1-2Mb之间时，质量为0.95-(x-0.1)*0.15/1.9。
 * y在(0.7, 0.95)的话，则质量为y = -3 / 38 * x + 91 / 95
 * 3.图片尺寸控制：
 * 当图片的最短边小于640时，不处理；否则，设短边为x，长边为y，压缩前尺寸(x，y)，压缩后的尺寸为（640，y*640/x）
 * 
 * 4. 上述规则只使用于聊天图片，个人头像还是采用原来的压缩到640*640的规则
 * 
 * @author cst
 */
public class ImgHelper {

	public static final int MAX_IMG_WIDTH_OF_SYSTEM = 4096;
	public static final int MAX_IMG_HEIGHT_OF_SYSTEM = 4096;

	public static final int DEFAULT_IMG_WIDTH = 960;//640, 95-80, 960, quality = 50
	public static final int DEFAULT_IMG_HEIGHT = 960;
	public static final int DEFAULT_IMG_WIDTH_MIN = 640;//长图保证质量的最小宽
	public static final int DEFAULT_IMG_HEIGHT_MIN = 640;//长图保证质量的最小高
	public static final int DEFAULT_LOGO_WIDTH = 640;
	public static final int DEFAULT_LOGO_HEIGHT = 640;

	public static final double DEFAULT_COMPRESS_FACTOR_MIN = 0.7;
	public static final double DEFAULT_COMPRESS_FACTOR_MAX = 0.95;
	public static final long ONE_KILOBYTES = 1024; // one kb
	public static final long ONE_MEGABYTES = 1024 * ONE_KILOBYTES;// one mb
	public static final long DEFAULT_COMPRESS_SIZE_LOW = 100 * ONE_KILOBYTES;
	public static final long DEFAULT_COMPRESS_SIZE_HIGH = 2 * ONE_MEGABYTES;

	private static final ScaleType DEFAULT_SCALE_TYPE = ScaleType.CENTER;

	/**
	 * calculate compress factor of image
	 * 
	 * @param sizeOfImgInBytes
	 * @return
	 */
	private static double calSendCompressFactor(long sizeOfImgInBytes) {
		if (sizeOfImgInBytes < DEFAULT_COMPRESS_SIZE_LOW) {
			return 1.0;
		} else if (sizeOfImgInBytes < DEFAULT_COMPRESS_SIZE_HIGH) {
			//			double minInMb = DEFAULT_COMPRESS_SIZE_LOW / (double) (ONE_MEGABYTES);
			double sizeInMb = sizeOfImgInBytes / (double) (ONE_MEGABYTES);
			return -25.0 / 190.0 * (sizeInMb) + 183.0 / 190.0;//(0.7-0.95)
			//			return DEFAULT_COMPRESS_FACTOR_MAX - (sizeInMb - minInMb) * 0.15 / 1.9;//(0.8-0.95)
		} else {
			return DEFAULT_COMPRESS_FACTOR_MIN;
		}
	}

	/**
	 * calculate compress height of image, x(0-960-4096),y(0-960-4096), x,y split the plane to several part
	 * case1: x(>960),y(>960) or x(>960-<4096),y(>640-<960) or x(>640-<960),y(>960-<4096): max(x,y) compressed to 960
	 * case2: x(>=4096),y(0-<=960), or x(0-<=960),y(>=4096): max(x,y) compressed to 4096
	 * case3: x(0-960),y(0-960) or x(0-<=640),y(960-4096) or x(960-4096),y(0-<=640):no compress
	 * 
	 * @param width
	 * @param height
	 * @return
	 */
	private static ImageSize calSendCompressSize(int width, int height) {

		ImageSize imageSize = null;

		boolean case1 = (width > DEFAULT_IMG_WIDTH && height > DEFAULT_IMG_HEIGHT) || (width > DEFAULT_IMG_WIDTH && width < MAX_IMG_WIDTH_OF_SYSTEM && height > DEFAULT_IMG_HEIGHT_MIN && height < DEFAULT_IMG_HEIGHT) || (width > DEFAULT_IMG_WIDTH_MIN && width < DEFAULT_IMG_WIDTH && height > DEFAULT_IMG_WIDTH && height < MAX_IMG_HEIGHT_OF_SYSTEM);

		boolean case2 = (width >= MAX_IMG_WIDTH_OF_SYSTEM && height <= DEFAULT_IMG_HEIGHT) || (height >= MAX_IMG_HEIGHT_OF_SYSTEM && width <= DEFAULT_IMG_WIDTH);

		if (case1) {
			if (width > height) {
				height = (int) (DEFAULT_IMG_WIDTH * Double.valueOf(height) / Double.valueOf(width));
				width = DEFAULT_IMG_WIDTH;
			} else {//width <= height
				width = (int) (DEFAULT_IMG_HEIGHT * Double.valueOf(width) / Double.valueOf(height));
				height = DEFAULT_IMG_HEIGHT;
			}
		} else if (case2) {
			if (width > height) {//horizontal long image
				height = (int) (MAX_IMG_WIDTH_OF_SYSTEM * Double.valueOf(height) / Double.valueOf(width));
				width = MAX_IMG_WIDTH_OF_SYSTEM;
			} else {//width <= height
				width = (int) (MAX_IMG_HEIGHT_OF_SYSTEM * Double.valueOf(width) / Double.valueOf(height));
				height = MAX_IMG_HEIGHT_OF_SYSTEM;
			}
		}

		imageSize = new ImageSize(width, height);
		return imageSize;
	}

	/**
	 * calculate in sample size
	 * 
	 * @return
	 */
	private static int calInSampleSize(BitmapFactory.Options opts, int targetWidth, int targetHeight, ScaleType viewScaleType, boolean powerOf2Scale) {
		int inSampleSize = 1;
		if (opts != null) {
			int srcWidth = opts.outWidth;
			int srcHeight = opts.outHeight;

			targetWidth = Math.min(targetWidth, MAX_IMG_WIDTH_OF_SYSTEM);
			targetHeight = Math.min(targetHeight, MAX_IMG_HEIGHT_OF_SYSTEM);

			int widthScale = 1 + srcWidth / targetWidth;
			int heightScale = 1 + srcHeight / targetHeight;

			switch (viewScaleType) {
			case FIT_CENTER:
			case FIT_XY:
			case FIT_START:
			case FIT_END:
			case CENTER_INSIDE: {
				if (powerOf2Scale) {
					while (srcWidth / 2 >= targetWidth || srcHeight / 2 >= targetHeight) { // ||
						srcWidth /= 2;
						srcHeight /= 2;
						inSampleSize *= 2;
					}
				} else {
					inSampleSize = Math.max(widthScale, heightScale); // max
				}
				break;
			}
			case MATRIX:
			case CENTER:
			case CENTER_CROP: {
				if (powerOf2Scale) {
					while (srcWidth / 2 >= targetWidth && srcHeight / 2 >= targetHeight) { // &&
						srcWidth /= 2;
						srcHeight /= 2;
						inSampleSize *= 2;
					}
				} else {
					inSampleSize = Math.min(widthScale, heightScale); // min
				}
				break;
			}
			}
		}

		if (inSampleSize < 1) {
			inSampleSize = 1;
		}

		//		Log.e("XXXXX", "XX" + targetWidth + "," + targetHeight + "," + inSampleSize + "," + powerOf2Scale);

		return inSampleSize;
	}

	/**
	 * is long image for given imageview
	 * 
	 * @param bitmap
	 * @return
	 */
	@Deprecated
	public static boolean isLongImage(Bitmap bitmap, ImageView imageView) {
		if (bitmap != null && imageView != null) {
			int bmpW = bitmap.getWidth();
			int bmpH = bitmap.getHeight();
			int viewW = imageView.getWidth();
			int viewH = imageView.getHeight();
			
			if((bmpW < viewW / 2.0 && bmpH > viewH * 2) || (bmpW > viewW * 2 && bmpH < viewH / 2.0)){
				return true;
			}
		}
		return false;
	}

	/**
	 * create bitmap options with
	 * 
	 * @param array
	 * @return
	 */
	public static BitmapFactory.Options createBitmapOptions(byte[] array) {
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inJustDecodeBounds = true;
		BitmapFactory.decodeByteArray(array, 0, array.length, opts);
		opts.inSampleSize = calInSampleSize(opts, DimensionUtil.SCREEN_WIDTH, DimensionUtil.SCREEN_HEIGHT, ScaleType.CENTER, false);
		opts.inDither = true;
		opts.inJustDecodeBounds = false;
		opts.inPurgeable = true;
		opts.inInputShareable = true;
		opts.inTempStorage = new byte[32 * 1024];
		return opts;
	}

	/**
	 * get display image
	 * 
	 * @param filePath
	 * @return
	 */
	public static Bitmap getDisplayImg(String filePath) {
		return getDisplayImg(filePath, null, true);
	}

	/**
	 * get display image
	 * 
	 * @param filePath
	 * @param imageView
	 * @return
	 */
	public static Bitmap getDisplayImg(String filePath, boolean powerOf2Scale) {
		return getDisplayImg(filePath, null, powerOf2Scale);
	}

	/**
	 * get display image
	 * 
	 * @param filePath
	 * @param imageView
	 * @return
	 */
	public static Bitmap getDisplayImg(String filePath, ImageView imageView) {
		return getDisplayImg(filePath, imageView, true);
	}

	/**
	 * get display image
	 * 
	 * @param filePath
	 * @param imageView
	 * @param powerOf2Scale
	 * @return
	 */
	public static Bitmap getDisplayImg(String filePath, ImageView imageView, boolean powerOf2Scale) {
		//		Log.d("XXX", "XXX22" + filePath + "," + imageView);
		if (imageView != null) {
			//			Log.d("XXX", "XXX22" + filePath + "," + imageView + "," + imageView.getWidth() + "," + imageView.getHeight());
			if (imageView.getWidth() > 0 && imageView.getHeight() > 0) {
				return getDisplayImg(filePath, imageView.getWidth(), imageView.getHeight(), imageView.getScaleType(), powerOf2Scale);
			} else {
				return getDisplayImg(filePath, DimensionUtil.SCREEN_WIDTH, DimensionUtil.SCREEN_HEIGHT, imageView.getScaleType(), powerOf2Scale);
			}
		} else {
			return getDisplayImg(filePath, DimensionUtil.SCREEN_WIDTH, DimensionUtil.SCREEN_HEIGHT, DEFAULT_SCALE_TYPE, powerOf2Scale);
		}
	}

	/**
	 * get image from path
	 * 
	 * @param srcPath
	 * @return
	 */
	public static Bitmap getDisplayImg(String filePath, int targetWidth, int targetHeight, ScaleType viewScaleType, boolean powerOf2Scale) {
		if (StringUtil.isValid(filePath) == false) {
			return null;
		}
		try {
			BitmapFactory.Options opts = new BitmapFactory.Options();
			opts.inJustDecodeBounds = true;

			BitmapFactory.decodeFile(filePath, opts);

			if (opts.outWidth < 0 || opts.outHeight < 0) {//decode error
				return null;
			}

			opts.inSampleSize = calInSampleSize(opts, targetWidth, targetHeight, viewScaleType, powerOf2Scale);
			opts.inPreferredConfig = Config.ARGB_8888;
			opts.inTempStorage = new byte[32 * 1024];
			opts.inDither = true;
			opts.inJustDecodeBounds = false;
			opts.inPurgeable = true;
			opts.inInputShareable = true;

			Bitmap bitmap = BitmapFactory.decodeFile(filePath, opts);
			if (bitmap == null) {
				return bitmap;
			}
			bitmap = rotateImage(bitmap, getImageOrientation(filePath));
			return bitmap;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * read bitmap from old path, compress and save it to new path, the old file is very large in this case
	 * 
	 * @param oldPath
	 * @param newPath
	 * @return
	 */
	public static String saveSendedImage(String oldPath, String newPath, boolean highQuality) {
		try {
			BitmapFactory.Options opts = new BitmapFactory.Options();
			opts.inJustDecodeBounds = true;

			BitmapFactory.decodeFile(oldPath, opts);

			if (opts.outWidth < 0 || opts.outHeight < 0) {//decode error
				return null;
			}

			ImageSize targetSize = calSendCompressSize(opts.outWidth, opts.outHeight);

			opts.inSampleSize = calInSampleSize(opts, targetSize.width * 2, targetSize.height * 2, ScaleType.FIT_XY, false);
			opts.inPreferredConfig = Config.ARGB_8888;
			opts.inTempStorage = new byte[32 * 1024];
			opts.inDither = true;
			opts.inJustDecodeBounds = false;
			opts.inPurgeable = true;
			opts.inInputShareable = true;

			Bitmap bitmap = BitmapFactory.decodeFile(oldPath, opts);//may out of memory
			Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, targetSize.width, targetSize.height, true);
			recycleOldIfNeed(bitmap, scaledBitmap);

			int quality = 100;
			if (highQuality) {//high quality
				quality = (int) (DEFAULT_COMPRESS_FACTOR_MAX * 100);
			} else {//normal quality
				long sizeOfBitmap = getBitmapSize(targetSize.width, targetSize.height, scaledBitmap.getConfig());
				quality = Math.min((int) (calSendCompressFactor(sizeOfBitmap) * 100), (int) (DEFAULT_COMPRESS_FACTOR_MAX * 100));
			}

			String result = saveImage(scaledBitmap, newPath, getImageOrientation(oldPath), quality);
			recycleOldIfNeed(scaledBitmap, null);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * recycle oldBitmap
	 * 
	 * @param oldBitmap
	 *            bitmap to be recycled
	 * @param newBitmap
	 *            bitmap created from old ( or may equal to old)
	 */
	private static void recycleOldIfNeed(Bitmap oldBitmap, Bitmap newBitmap) {
		if (oldBitmap != newBitmap && oldBitmap != null && oldBitmap.isRecycled() == false) {
			oldBitmap.recycle();
		}
	}

	/**
	 * save sended bitmap
	 * 
	 * @param bitmap
	 * @param highQuailty
	 * @return
	 */
	public static String saveSendedBitmap(Bitmap bitmap, String newPath, boolean highQuality) {
		if (bitmap == null) {
			return null;
		}

		try {
			ImageSize targetSize = calSendCompressSize(bitmap.getWidth(), bitmap.getHeight());
			Bitmap scaledBitmap = bitmap;

			if (targetSize.width != bitmap.getWidth() || targetSize.height != bitmap.getHeight()) {
				scaledBitmap = Bitmap.createScaledBitmap(bitmap, targetSize.width, targetSize.height, true);
				recycleOldIfNeed(bitmap, scaledBitmap);
			}

			int quality = 100;
			if (highQuality) {//high quality
				quality = (int) (DEFAULT_COMPRESS_FACTOR_MAX * 100);
			} else {//normal quality
				long sizeOfBitmap = getBitmapSize(targetSize.width, targetSize.height, scaledBitmap.getConfig());
				quality = Math.min((int) (calSendCompressFactor(sizeOfBitmap) * 100), (int) (DEFAULT_COMPRESS_FACTOR_MAX * 100));
			}

			String result = saveImage(scaledBitmap, newPath, quality);
			recycleOldIfNeed(scaledBitmap, null);

			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * save a input stream to output stream
	 * 
	 * @param inputStream
	 * @param outputStream
	 */
	public static void streamToFile(InputStream inputStream, OutputStream outputStream) {
		try {
			int read = 0;
			byte[] bytes = new byte[1024];

			while ((read = inputStream.read(bytes)) != -1) {
				outputStream.write(bytes, 0, read);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (outputStream != null) {
				try {
					outputStream.flush();
					outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		}
	}

	/**
	 * applay water mark to image
	 * 
	 * @param image
	 * @param watermark
	 * @return
	 */
	public static Bitmap applyWaterMark(Bitmap image, Bitmap watermark, Integer width, Integer height) {
		try {
			if (width == null || width < 1) {
				width = DEFAULT_LOGO_WIDTH;
			}

			if (height == null || height < 1) {
				height = DEFAULT_LOGO_HEIGHT;
			}

			Bitmap result = Bitmap.createBitmap(width, height, Config.ARGB_8888);
			Bitmap scaledSrc = Bitmap.createScaledBitmap(image, width, height, true);
			recycleOldIfNeed(image, scaledSrc);

			Bitmap scaledWaterMark = Bitmap.createScaledBitmap(watermark, width, height, true);
			recycleOldIfNeed(watermark, scaledWaterMark);

			Canvas canvas = new Canvas(result);
			canvas.drawBitmap(scaledSrc, 0, 0, null);
			recycleOldIfNeed(scaledSrc, null);

			canvas.drawBitmap(scaledWaterMark, 0, 0, null);
			recycleOldIfNeed(scaledWaterMark, null);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * save bitmap directly with given quality
	 * 
	 * @param bitmap
	 * @param filePath
	 */
	public static void bitmapToFile(Bitmap bitmap, String filePath, int quality) {
		ByteArrayOutputStream bos = null;
		FileOutputStream fos = null;
		try {
			bos = new ByteArrayOutputStream();
			fos = new FileOutputStream(new File(filePath));
			bitmap.compress(CompressFormat.JPEG, quality, bos);
			byte[] bitmapdata = bos.toByteArray();
			fos.write(bitmapdata);
			fos.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (bos != null)
					bos.close();
				if (fos != null)
					fos.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 
	 * @param filePath
	 * @param context
	 */
	public static void notifyPhotoScans(String filePath, Context context) {
		try {
			Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
			Uri uri = Uri.fromFile(new File(filePath));
			intent.setData(uri);
			context.sendBroadcast(intent);
		} catch (Exception e) {
		}
	}

	/**
	 * convert a drawable to bitmap
	 * 
	 * @param drawable
	 * @return
	 */
	public static Bitmap drawableToBitmap(Drawable drawable) {
		if (drawable instanceof BitmapDrawable) {
			return ((BitmapDrawable) drawable).getBitmap();
		}
		Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
		drawable.draw(canvas);
		return bitmap;
	}

	/**
	 * get rotation degree of file
	 * 
	 * @param file
	 * @return
	 */
	public static int getImageOrientation(String file) {
		return getImageOrientation(new File(file));
	}

	/**
	 * 
	 * @param uri
	 * @return
	 * @throws IOException
	 */
	public static int getImageOrientation(Uri uri) {
		Cursor cursor = SDBaseApplication.getInstance().getContentResolver().query(uri, new String[] { MediaStore.Images.ImageColumns.ORIENTATION }, null, null, null);

		if (cursor == null || cursor.getCount() != 1) {
			return 0;
		}

		cursor.moveToFirst();
		return cursor.getInt(0);
	}

	/**
	 * get orientataion
	 * 
	 * @param bitmapFile
	 * @return
	 * @throws IOException
	 */
	public static int getImageOrientation(File bitmapFile) {
		ExifInterface exif = null;
		try {
			if (bitmapFile != null) {
				exif = new ExifInterface(bitmapFile.getAbsolutePath());
			} else {
				return 0;
			}
		} catch (IOException e) {
			e.printStackTrace();
			return 0;
		}
		int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);
		switch (orientation) {
		case ExifInterface.ORIENTATION_NORMAL:
			return 0;
		case ExifInterface.ORIENTATION_ROTATE_90:
			return 90;
		case ExifInterface.ORIENTATION_ROTATE_180:
			return 180;
		case ExifInterface.ORIENTATION_ROTATE_270:
			return 270;
		default:
			return 0;
		}
	}

	/**
	 * save orientation of image
	 * 
	 * @param bitmapFile
	 * @param orientation
	 */
	public static void saveImgOrientation(File bitmapFile, int orientation) {
		if (orientation <= 0) {
			return;
		}

		ExifInterface exif = null;
		try {
			if (bitmapFile != null) {
				exif = new ExifInterface(bitmapFile.getAbsolutePath());
				switch (orientation) {
				case 0:
					exif.setAttribute(ExifInterface.TAG_ORIENTATION, String.valueOf(ExifInterface.ORIENTATION_NORMAL));
					break;
				case 90:
					exif.setAttribute(ExifInterface.TAG_ORIENTATION, String.valueOf(ExifInterface.ORIENTATION_ROTATE_90));
					break;
				case 180:
					exif.setAttribute(ExifInterface.TAG_ORIENTATION, String.valueOf(ExifInterface.ORIENTATION_ROTATE_180));
					break;
				case 270:
					exif.setAttribute(ExifInterface.TAG_ORIENTATION, String.valueOf(ExifInterface.ORIENTATION_ROTATE_270));
					break;
				}

				exif.saveAttributes();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * rotate bitmap
	 * 
	 * @param bitmap
	 * @param degree
	 * @return
	 */
	public static Bitmap rotateImage(final Bitmap bitmap, int orientation) {
		if (bitmap == null) {
			return null;
		}
		Bitmap rotatedBitmap = bitmap;
		if (orientation != 0) {
			Matrix matrix = new Matrix();
			matrix.postRotate(orientation);
			rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
			recycleOldIfNeed(bitmap, rotatedBitmap);
		}
		return rotatedBitmap;
	}

	/**
	 * save image
	 * 
	 * @param bitmap
	 * @param filePath
	 * @param quality
	 * @return
	 */
	public static String saveImage(Bitmap bitmap, String filePath, int quality) {
		return saveImage(bitmap, filePath, 0, quality);
	}

	/**
	 * save bitmap
	 * 
	 * @param bitmap
	 * @param quality
	 *            bitmap quality(1-100)
	 * @return
	 */
	public static String saveImage(Bitmap bitmap, String filePath, int orientation, int quality) {
		try {
			File file = new File(filePath);
			FileOutputStream out = new FileOutputStream(file);
			if (bitmap.compress(Bitmap.CompressFormat.JPEG, quality, out)) {
				out.flush();
				out.close();

				saveImgOrientation(file, orientation);

				return filePath;
			} else {
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * compress bitmap to target size, and return a byte[] array
	 * 
	 * @param bmp
	 * @param targetSizeInKiloBytes
	 * @return
	 */
	public static byte[] compressBitmap(Bitmap bmp, int targetSizeInKiloBytes) {
		if(bmp == null){
			return null;
		}
		int targetSize = targetSizeInKiloBytes * 1024;
		int bitmapSize = sizeOfBitmap(bmp);

		byte[] byteArray = null;
		if (targetSize > bitmapSize) {//return directly
			ByteBuffer buffer = ByteBuffer.allocate(bitmapSize);
			bmp.copyPixelsToBuffer(buffer);
			try {
				return buffer.array();
			} catch (Exception e) {
			}
		} else {
			int quality = Math.min((targetSize * 100) / bitmapSize, 100);
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			bmp.compress(Bitmap.CompressFormat.JPEG, quality, stream);
			try {
				byteArray = stream.toByteArray();
				stream.close();
			} catch (Exception e) {
			}
		}

		recycleOldIfNeed(bmp, null);
		return byteArray;
	}

	/**
	 * calculate size of bitmap
	 */
	public static long getBitmapSize(long width, long height, Bitmap.Config config) {
		return width * height * getBytesOfPixel(config);
	}

	/**
	 * calculate size of bitmap
	 */
	@SuppressLint("NewApi")
	public static int sizeOfBitmap(Bitmap bitmap) {
		int size = 0;
		if (bitmap != null) {
			Bitmap.Config config = bitmap.getConfig();
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
				size = bitmap.getByteCount() * getBytesOfPixel(config);
			} else {
				size = bitmap.getRowBytes() * bitmap.getHeight() * getBytesOfPixel(config);
			}
		}
		return size;
	}

	/**
	 * get bytes of one pixel according bitmap format
	 */
	public static int getBytesOfPixel(Bitmap.Config config) {
		int bytesOfPixel = 1;
		switch (config) {
		case RGB_565:
		case ARGB_4444:
			bytesOfPixel = 2;
			break;
		case ALPHA_8:
			bytesOfPixel = 1;
			break;
		case ARGB_8888:
			bytesOfPixel = 4;
			break;
		}
		return bytesOfPixel;
	}

	/**
	 * round corners of given image
	 * 
	 * @param path
	 * @param imageView
	 * @param roundPixels
	 * @return
	 */
	public static Bitmap roundCorners(String path, ImageView imageView, int roundPixels) {
		Bitmap bitmap = getDisplayImg(path, imageView);
		return roundCorners(bitmap, imageView, roundPixels);
	}

	/**
	 * roundcorners of given image
	 * 
	 * @param path
	 * @param width
	 * @param height
	 * @param roundPixels
	 * @return
	 */
	public static Bitmap roundCorners(String path, int width, int height, int roundPixels) {
		Bitmap bitmap = getDisplayImg(path, width, height, DEFAULT_SCALE_TYPE, true);
		return roundCorners(bitmap, width, height, DEFAULT_SCALE_TYPE, roundPixels);
	}

	/**
	 * get round corners
	 * 
	 * @param bitmap
	 *            Incoming Bitmap to process
	 * @param imageview
	 *            view that to display bitmap
	 * @param roundPixels
	 *            Rounded pixels of corner
	 * @return Result bitmap with rounded corners
	 */
	public static Bitmap roundCorners(Bitmap bitmap, ImageView imageView, int roundPixels) {
		if (imageView == null) {
			return bitmap;
		}
		return roundCorners(bitmap, imageView.getWidth(), imageView.getHeight(), imageView.getScaleType(), roundPixels);
	}

	/**
	 * round corners of given bitmap
	 * 
	 * @param bitmap
	 * @param vw
	 * @param vh
	 * @param scaleType
	 * @param roundPixels
	 * @return
	 */
	public static Bitmap roundCorners(Bitmap bitmap, int vw, int vh, ScaleType scaleType, int roundPixels) {
		if (bitmap == null || bitmap.isRecycled()) {
			return null;
		}

		int bw = bitmap.getWidth();
		int bh = bitmap.getHeight();
		if (vw <= 0)
			vw = bw;
		if (vh <= 0)
			vh = bh;

		if (scaleType == null) {
			return bitmap;
		}

		int width, height;
		Rect srcRect;
		Rect destRect;
		switch (scaleType) {
		case CENTER_INSIDE:
			float vRation = (float) vw / vh;
			float bRation = (float) bw / bh;
			int destWidth;
			int destHeight;
			if (vRation > bRation) {
				destHeight = Math.min(vh, bh);
				destWidth = (int) (bw / ((float) bh / destHeight));
			} else {
				destWidth = Math.min(vw, bw);
				destHeight = (int) (bh / ((float) bw / destWidth));
			}
			int x = (vw - destWidth) / 2;
			int y = (vh - destHeight) / 2;
			srcRect = new Rect(0, 0, bw, bh);
			destRect = new Rect(x, y, x + destWidth, y + destHeight);
			width = vw;
			height = vh;
			break;
		case FIT_CENTER:
		case FIT_START:
		case FIT_END:
		default:
			vRation = (float) vw / vh;
			bRation = (float) bw / bh;
			if (vRation > bRation) {
				width = (int) (bw / ((float) bh / vh));
				height = vh;
			} else {
				width = vw;
				height = (int) (bh / ((float) bw / vw));
			}
			srcRect = new Rect(0, 0, bw, bh);
			destRect = new Rect(0, 0, width, height);
			break;
		case CENTER_CROP:
			vRation = (float) vw / vh;
			bRation = (float) bw / bh;
			int srcWidth;
			int srcHeight;
			if (vRation > bRation) {
				srcWidth = bw;
				srcHeight = (int) (vh * ((float) bw / vw));
				x = 0;
				y = (bh - srcHeight) / 2;
			} else {
				srcWidth = (int) (vw * ((float) bh / vh));
				srcHeight = bh;
				x = (bw - srcWidth) / 2;
				y = 0;
			}
			width = srcWidth;// Math.min(vw, bw);
			height = srcHeight;//Math.min(vh, bh);
			srcRect = new Rect(x, y, x + srcWidth, y + srcHeight);
			destRect = new Rect(0, 0, width, height);
			break;
		case FIT_XY:
			width = vw;
			height = vh;
			srcRect = new Rect(0, 0, bw, bh);
			destRect = new Rect(0, 0, width, height);
			break;
		case CENTER:
		case MATRIX:
			width = Math.min(vw, bw);
			height = Math.min(vh, bh);
			x = (bw - width) / 2;
			y = (bh - height) / 2;
			srcRect = new Rect(x, y, x + width, y + height);
			destRect = new Rect(0, 0, width, height);
			break;
		}

		return getRoundedCornerBitmap(bitmap, roundPixels, srcRect, destRect, width, height);
	}

	/**
	 * get rounded corner bitmap
	 * 
	 * @param bitmap
	 * @param roundPixels
	 * @param srcRect
	 * @param destRect
	 * @param width
	 * @param height
	 * @return
	 */
	private static Bitmap getRoundedCornerBitmap(Bitmap bitmap, int roundPixels, Rect srcRect, Rect destRect, int width, int height) {
		if (bitmap == null || bitmap.isRecycled()) {
			return null;
		}

		try {
			Bitmap output = Bitmap.createBitmap(width, height, Config.ARGB_8888);
			Canvas canvas = new Canvas(output);

			final Paint paint = new Paint();
			final RectF destRectF = new RectF(destRect);

			paint.setAntiAlias(true);
			canvas.drawARGB(0, 0, 0, 0);
			paint.setColor(0xFF000000);
			canvas.drawRoundRect(destRectF, roundPixels, roundPixels, paint);

			paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

			if (bitmap.isRecycled() == false)
				canvas.drawBitmap(bitmap, srcRect, destRectF, paint);

			recycleOldIfNeed(bitmap, null);

			return output;
		} catch (OutOfMemoryError e) {
			return bitmap;
		}
	}

	/**
	 * resize a image to target size
	 * 
	 * @param fromFile
	 * @param toFile
	 * @param width
	 * @param height
	 * @param quality
	 * @return
	 */
	public static String resizeImage(String fromFile, String toFile, int width, int height, int quality) {
		try {
			Bitmap bitmap = getDisplayImg(fromFile);
			int bitmapWidth = bitmap.getWidth();
			int bitmapHeight = bitmap.getHeight();
			// 缩放图片的尺寸
			float scaleW = (float) width / bitmapWidth;
			float scaleH = (float) height / bitmapHeight;
			float scale = Math.min(scaleW, scaleH);
			Matrix matrix = new Matrix();
			matrix.postScale(scale, scale);
			// 产生缩放后的Bitmap对象
			Bitmap resizeBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmapWidth, bitmapHeight, matrix, false);
			recycleOldIfNeed(bitmap, resizeBitmap);

			// save file
			File myCaptureFile = new File(toFile);
			FileOutputStream out = new FileOutputStream(myCaptureFile);
			if (resizeBitmap.compress(Bitmap.CompressFormat.JPEG, quality, out)) {
				out.flush();
				out.close();
			}
			recycleOldIfNeed(resizeBitmap, null);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return toFile;
	}

	//image size
	static class ImageSize {
		int width;
		int height;

		public ImageSize(int width, int height) {
			this.width = width;
			this.height = height;
		}
	}
}
