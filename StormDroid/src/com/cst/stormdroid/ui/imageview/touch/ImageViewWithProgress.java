package com.cst.stormdroid.ui.imageview.touch;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.cst.stormdroid.R;

/**
 * image view touch with progress display
 * 
 * @author Storm
 * 
 */
public class ImageViewWithProgress<T> extends FrameLayout {
	private int mProgress;
	// private ImageViewTouch mImageViewTouch;
	private T mImageView;
	private View mLayout;
	private TextView mProgressView;
	private TextView mImageSize;

	public ImageViewWithProgress(Context context) {
		super(context);
		init(context, null, null);
	}

	public ImageViewWithProgress(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs, null);
	}

	public ImageViewWithProgress(Context context, Integer layout) {
		super(context);
		init(context, null, layout);
	}

	public ImageViewWithProgress(Context context, AttributeSet attrs, Integer layout) {
		super(context, attrs);
		init(context, attrs, layout);
	}

	private void init(Context context, AttributeSet attrs, Integer layout) {
		mProgress = 0;

		if (layout == null)
			LayoutInflater.from(context).inflate(R.layout.dlg_imageviewtouch_progress, this, true);
		else {
			LayoutInflater.from(context).inflate(layout, this, true);
		}

		mImageView = (T) this.findViewById(R.id.ivImg);
		mLayout = this.findViewById(R.id.layout);
		mProgressView = (TextView) this.findViewById(R.id.tvProgress);
		mImageSize = (TextView) this.findViewById(R.id.tvSize);

		mLayout.setVisibility(View.GONE);
		mImageSize.setVisibility(View.GONE);
	}
	
	public void doInit(int layout){
		init(getContext(), null, layout);
	}

	public void startLoading() {
		mProgressView.setText("0%");
		mLayout.setVisibility(View.VISIBLE);
	}

	public void stopLoading() {
		mProgressView.setText(null);
		mLayout.setVisibility(View.GONE);
	}

	public void setProgress(int progress, int totalLength) {
		if (mProgress != progress) {
			mProgress = progress;

			if (mProgress > 0 && mProgress < 100) {
				mProgressView.setText(mProgress + "%");

				if (mImageSize.getVisibility() == View.GONE) {
					mImageSize.setText((Math.round(totalLength / 100.0) / 10.0) + " K");// 2131.4k
					mImageSize.setVisibility(View.VISIBLE);
				}
			} else if (mProgress == 100) {
				mProgressView.setText("100%");
				Animation anim = new AlphaAnimation(1.0f, 0.0f);
				anim.setDuration(500);
				anim.setFillAfter(true);
				mLayout.startAnimation(anim);
			}
		}
	}

	public T getImageView() {
		return mImageView;
	}
}
