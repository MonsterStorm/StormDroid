package com.cst.stormdroid.ui.listview.autoload;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cst.stormdroid.R;

/**
 * auto load header
 * 
 * @author Storm
 * 
 */
public abstract class AutoLoadInfoView extends RelativeLayout {
	public static enum InfoViewState{NORMAL, LOADING};
	private ViewGroup mInnerLayout;
	private TextView mInfoText;
	private ProgressBar mProgressBar;
	private LayoutInflater mInflater;
	private boolean isSimple = false;//是否简单显示
	private InfoViewState mState;

	private boolean mUseIntrinsicAnimation = false;
	
	public AutoLoadInfoView(Context context, int resId) {
		this(context, resId, false);
	}
	
	public AutoLoadInfoView(Context context, int resId, boolean isSimple) {
		super(context);
		this.isSimple = isSimple;
		
		mInflater = LayoutInflater.from(context);
		mInflater.inflate(resId, this);

		mInnerLayout = (ViewGroup) this.findViewById(R.id.fl_inner);
		mInfoText = (TextView) mInnerLayout.findViewById(R.id.tvInfo);
		mProgressBar = (ProgressBar) mInnerLayout.findViewById(R.id.pbLoading);
		
		setState(InfoViewState.NORMAL);
	}

	public void setInfoText(CharSequence content){
		mInfoText.setText(content);
	}
	
	public void setState(InfoViewState state){
		mState = state;
		switch(mState){
		case NORMAL:
			showInfoText();
			hideLoadingProgress();
			break;
		case LOADING:
			hideInfoText();
			showLoadingProgress();
			break;
		}
	}
	
	public int getContentSize(){
		return mInnerLayout.getHeight();
	}
	
	public void showInfoText(){
		if(isSimple == false && mInfoText != null)
			mInfoText.setVisibility(View.VISIBLE);
	}
	
	public void hideInfoText(){
		if(mInfoText != null)
			mInfoText.setVisibility(View.GONE);
	}
	
	public void showLoadingProgress(){
		if(mProgressBar != null)
			mProgressBar.setVisibility(View.VISIBLE);
	}
	
	public void hideLoadingProgress(){
		if(mProgressBar != null)
			mProgressBar.setVisibility(View.GONE);
	}
	
	public void setSimple(boolean isSimple) {
		if(this.isSimple == isSimple)
			return;
		this.isSimple = isSimple;
		setState(mState);//重新更新显示
	}
	
	public void setDividerVisibility(int visible){
		View v = findViewById(R.id.ivTemp);
		if(v != null){
			v.setVisibility(visible);
		}
	}
}
