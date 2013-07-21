package com.cst.stormdroid.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.cst.stormdroid.R;
import com.cst.stormdroid.app.SDBaseApplication;
import com.cst.stormdroid.image.ImageRequest;
import com.cst.stormdroid.image.interfaces.ImageProcessor;
import com.cst.stormdroid.image.interfaces.ImageRequestCallback;
import com.cst.stormdroid.image.interfaces.OnImageViewLoadListener;
import com.cst.stormdroid.utils.log.SDLog;
import com.cst.stormdroid.utils.string.StringUtil;

/**
 * Async ImageView, load image from a given url
 * @author MonsterStorm
 * @version 1.0
 */
public class AsyncImageView extends ImageView implements ImageRequestCallback {
	// log tag
	private static final String LOG_TAG = AsyncImageView.class.getSimpleName();

	/**
	 * Image source
	 */
	public enum ImageSource {
		IMAGE_SOURCE_BITMAP, IMAGE_SOURCE_DRAWABLE, IMAGE_SOURCE_RESOURCE, IMAGE_SOURCE_UNKNOWN
	};

	/**
	 * type of image source, and default
	 */
	private ImageSource mImageSource;
	private Bitmap mDefaultBitmap;
	private Drawable mDefaultDrawable;
	private int mDefaultResource;

	/**
	 * url of this imageview
	 */
	private String mUrl;

	/**
	 * is paused
	 */
	private boolean mPaused;

	/**
	 * bitmap
	 */
	private Bitmap mBitmap;

	/**
	 * options
	 */
	private BitmapFactory.Options mOptions;

	/**
	 * image processor
	 */
	private ImageProcessor mImageProcessor;

	/**
	 * mImageRequest
	 */
	private ImageRequest mImageRequest;

	/**
	 * ImageLoad listener
	 */
	private OnImageViewLoadListener mOnImageViewLoadListener;

	public AsyncImageView(Context context) {
		this(context, null);
	}

	public AsyncImageView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public AsyncImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initDefaultValues();
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.AsyncImageView, defStyle, 0);

		Drawable d = a.getDrawable(R.styleable.AsyncImageView_defaultSrc);
		if (d != null) {
			setDefaultImageDrawable(d);
		}

		final int inDensity = a.getInt(R.styleable.AsyncImageView_inDensity, -1);
		if (inDensity != -1) {
			setInDensity(inDensity);
		}

		setUrl(a.getString(R.styleable.AsyncImageView_url));

		a.recycle();
	}

	/**
	 * set density
	 * @param inDensity
	 */
	private void setInDensity(final int inDensity) {
		if (mOptions == null) {
			mOptions = new BitmapFactory.Options();
			mOptions.inDither = true;
			mOptions.inScaled = true;
			mOptions.inTargetDensity = getContext().getResources().getDisplayMetrics().densityDpi;
		}
		mOptions.inDensity = inDensity;
	}

	/**
	 * init default values
	 */
	private void initDefaultValues() {
		mImageSource = ImageSource.IMAGE_SOURCE_UNKNOWN;
		mPaused = false;
	}

	/**
	 * set Image load listener
	 * @param listener
	 */
	public void setOnImageViewLoadListener(OnImageViewLoadListener listener) {
		mOnImageViewLoadListener = listener;
	}
	
	/**
	 * set options
	 * @param options
	 */
	public void setOptions(BitmapFactory.Options options) {
        mOptions = options;
    }

	/**
	 * set Image processor
	 * @param imageProcessor
	 */
	public void setImageProcessor(ImageProcessor imageProcessor) {
		mImageProcessor = imageProcessor;
	}

	/**
	 * set url
	 * @param url
	 */
	public void setUrl(final String url) {
		if (mBitmap != null && mUrl != null && mUrl.equals(url)) {// the same url
			return;
		}

		stopLoading();
		mUrl = url;

		if (!StringUtil.isValid(mUrl)) {// if url is invalid, then load the default image
			mBitmap = null;
			setDefaultImage();
		} else {
			if (!mPaused) {
				reload();
			} else {
				// check the cache
				mBitmap = SDBaseApplication.getInstance().getImageCache().getImageByUrl(mUrl);
				if (mBitmap != null) {
					setImageBitmap(mBitmap);
					return;
				} else {
					setDefaultImage();
				}
			}
		}
	}

	/**
	 * stop current loading
	 */
	public void stopLoading() {
		if (mImageRequest != null) {
			mImageRequest.cancel();
			mImageRequest = null;
		}
	}

	/**
	 * set pause
	 */
	public void setPaused(boolean paused) {
		if (mPaused != paused) {
			mPaused = paused;
			if (!paused) {
				reload();
			}
		}
	}

	/**
	 * Reload the image pointed by the given URL
	 */
	public void reload() {
		reload(false);
	}

	/**
	 * reload image
	 * @param force whether force load and don't look the cache
	 */
	private void reload(boolean force) {
		if (mImageRequest == null && StringUtil.isValid(mUrl)) {
			mBitmap = null;
			if (!force) {
				mBitmap = SDBaseApplication.getInstance().getImageCache().getImageByUrl(mUrl);
			}

			if (mBitmap != null) {
				setImageBitmap(mBitmap);
				return;
			}

			SDLog.i(LOG_TAG, "Cache miss. Starting to load the image at the given URL");

			setDefaultImage();
			mImageRequest = new ImageRequest(mUrl, this, mImageProcessor, mOptions);
			mImageRequest.load(getContext());
		}
	}

	//---------------------default image-------------------------
	/**
	 * set default bitmap
	 * @param bitmap
	 */
	public void setDefaultImageBitmap(final Bitmap bm) {
		if (bm != null) {
			mImageSource = ImageSource.IMAGE_SOURCE_BITMAP;
			mDefaultBitmap = bm;
			setDefaultImage();
		}
	}

	/**
	 * set default drawable
	 * @param drawable
	 */
	public void setDefaultImageDrawable(final Drawable drawable) {
		if (drawable != null) {
			mImageSource = ImageSource.IMAGE_SOURCE_DRAWABLE;
			mDefaultDrawable = drawable;
			setDefaultImage();
		}
	}

	/**
	 * set default resource
	 * @param resId
	 */
	public void setDefaultImageResource(final int resId) {
		mImageSource = ImageSource.IMAGE_SOURCE_RESOURCE;
		mDefaultResource = resId;
		setDefaultImage();
	}

	/**
	 * set default image
	 */
	private void setDefaultImage() {
		if (mBitmap == null) {
			switch (mImageSource) {
			case IMAGE_SOURCE_BITMAP:
				setImageBitmap(mDefaultBitmap);
				break;
			case IMAGE_SOURCE_DRAWABLE:
				setImageDrawable(mDefaultDrawable);
				break;
			case IMAGE_SOURCE_RESOURCE:
				setImageResource(mDefaultResource);
				break;
			default:
				setImageDrawable(null);
				break;
			}
		}
	}
	
	//-----------------------image loader callback---------------------------
	@Override
	public void onImageRequestStart(ImageRequest imageRequest) {
		if (mOnImageViewLoadListener != null) {
			mOnImageViewLoadListener.onLoadingStarted(this);
		}
	}

	@Override
	public void onImageRequestEnded(ImageRequest imageRequest, Bitmap bm) {
		mBitmap = bm;
		setImageBitmap(bm);
		if (mOnImageViewLoadListener != null) {
			mOnImageViewLoadListener.onLoadingEnded(this, bm);
		}
		mImageRequest = null;
	}

	@Override
	public void onImageRequestFailed(ImageRequest imageRequest, Throwable exception) {
		mImageRequest = null;
		if (mOnImageViewLoadListener != null) {
			mOnImageViewLoadListener.onLoadingFailed(this, exception);
		}
	}

	@Override
	public void onImageRequestCanceled(ImageRequest imageRequest) {
		mImageRequest = null;
		if (mOnImageViewLoadListener != null) {
			mOnImageViewLoadListener.onLoadingFailed(this, null);
		}
	}
	
	//-----------------------saved state-------------------------
	protected static class SavedState extends BaseSavedState {
        private String url;

        protected SavedState(Parcelable superState) {
            super(superState);
        }

        private SavedState(Parcel in) {
            super(in);
            url = in.readString();
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeString(url);
        }

        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() {
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
    }

    @Override
    public Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SavedState ss = new SavedState(superState);

        ss.url = mUrl;

        return ss;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());

        setUrl(ss.url);
    }
}
