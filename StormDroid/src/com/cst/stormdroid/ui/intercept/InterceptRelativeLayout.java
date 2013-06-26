package com.cst.stormdroid.ui.intercept;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

/**
 * Intercept RelativeLayout, intercept touch event through override handleByChild function
 * @author MonsterStorm
 * @version 1.0
 */
public abstract class InterceptRelativeLayout extends RelativeLayout {
	// reference of
	private Activity activity;

	public InterceptRelativeLayout(Context context) {
		super(context);
		this.activity = (Activity) context;
	}

	public InterceptRelativeLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.activity = (Activity) context;
	}

	@TargetApi(11)
	public InterceptRelativeLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.activity = (Activity) context;
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
