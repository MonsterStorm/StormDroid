package com.cst.stormdroid.ui.intercept;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Intercept LinearLayout, intercept touch event through override handleByChild function
 * @author MonsterStorm
 * @version 1.0
 */
public abstract class InterceptLinearLayout extends LinearLayout {
	// reference of
	private Activity mActivity;

	public InterceptLinearLayout(Context context) {
		super(context);
		this.mActivity = (Activity) context;
	}

	public InterceptLinearLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.mActivity = (Activity) context;
	}

	@TargetApi(11)
	public InterceptLinearLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.mActivity = (Activity) context;
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		if (handleByChild()) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * whether onTouchEvent should hanlde by child or only handle by Layout itself
	 * @return
	 */
	protected abstract boolean handleByChild();
}
