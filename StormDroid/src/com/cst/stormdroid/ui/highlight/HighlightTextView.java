package com.cst.stormdroid.ui.highlight;

import android.content.Context;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.TextView;

/**
 * when click, then highlight it
 * @author MonsterStorm
 * @version 1.0
 */
public class HighlightTextView extends TextView implements OnTouchListener{
	private OnTouchListener mOnTouchListener;

	public HighlightTextView(Context context) {
		super(context);
		init();
	}

	public HighlightTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public HighlightTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}
	
	private void init() {
		this.setClickable(true);
		this.setLongClickable(true);
		this.setOnTouchListenerInternal(this);
	}

	@Override
	public void setOnTouchListener(OnTouchListener l) {
		this.mOnTouchListener = l;
	}

	private void setOnTouchListenerInternal(OnTouchListener l) {
		super.setOnTouchListener(l);
	}

	@Override
	public boolean onTouch(View v, MotionEvent e) {
		if (e.getAction() == MotionEvent.ACTION_DOWN) {
			PorterDuffColorFilter greyFilter = new PorterDuffColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
			Drawable bg = this.getBackground();
			if (bg != null) {
				bg.setColorFilter(greyFilter);
			}
		} else if (e.getAction() == MotionEvent.ACTION_UP || e.getAction() == MotionEvent.ACTION_CANCEL || e.getAction() == MotionEvent.ACTION_OUTSIDE) {
			Drawable bg = this.getBackground();
			if (bg != null) {
				bg.clearColorFilter();
			}
		}

		if (mOnTouchListener != null) {
			return mOnTouchListener.onTouch(v, e);
		} else {
			return false;
		}
	}
}
