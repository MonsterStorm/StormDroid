package com.cst.stormdroid.ui.listview.autoload;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ScrollView;

public class PullableScrollView extends PullableViewBase<ScrollView> {

	public PullableScrollView(Context context) {
		super(context);
	}

	public PullableScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected ScrollView createContentView(Context context, AttributeSet attrs) {
		ScrollView scrollView = new ScrollView(context, attrs);
		return scrollView;
	}

	@Override
	protected boolean isReadyForPullStart() {
		return mContentView.getScrollY() == 0;
	}

	@Override
	protected boolean isReadyForPullEnd() {
		View scrollViewChild = mContentView.getChildAt(0);
		if (null != scrollViewChild) {
			return mContentView.getScrollY() >= (scrollViewChild.getHeight() - getHeight());
		}
		return false;
	}

	@Override
	public View createHeaderView(Context context, Mode mode, TypedArray array) {
		return null;
	}

	@Override
	public View createFooterView(Context context, Mode mode, TypedArray array) {
		return null;
	}

	@Override
	public void handleStyledAttributes(TypedArray array) {
	}
}
