package com.cst.stormdroid.ui.loading;

import android.content.Context;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.cst.stormdroid.R;

/**
 * loading indicator for base loading layout
 * @author MonsterStorm
 */
public class LoadingIndicator extends FrameLayout {
	private Context mContext;
	// progress bar
	private ProgressBar mProgressBar;

	public LoadingIndicator(Context context) {
		super(context);
		init(context, null);
	}

	public LoadingIndicator(Context context, int progressLayout) {
		super(context);
		init(context, progressLayout);
	}

	/**
	 * init view
	 */
	private void init(Context context, Integer progressLayout) {
		this.mContext = context;

		LayoutInflater inflater = LayoutInflater.from(mContext);
		if (progressLayout != null) {
			inflater.inflate(progressLayout, this);
		} else {
			inflater.inflate(R.layout.ui_loadingindicator, this);
		}
		mProgressBar = (ProgressBar) this.findViewById(android.R.id.progress);

		if (mProgressBar == null) {
			throw new InflateException("LoadingIndicator layout must have a progress bar with id \"android.R.id.progress\"");
		}
	}

	// ****************************getter and setters*******************************
	public void setProgress(int progress) {
		mProgressBar.setProgress(progress);
	}
}
