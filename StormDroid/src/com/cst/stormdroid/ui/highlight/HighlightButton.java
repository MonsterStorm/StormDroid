package com.cst.stormdroid.ui.highlight;

import android.content.Context;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Button;
/**
 * when click, then highlight it
 * @author MonsterStorm
 * @version 1.0
 */
public class HighlightButton extends Button {

	public HighlightButton(Context context) {
		super(context);
	}

	public HighlightButton(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public HighlightButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			if (this.getBackground() != null) {
				ColorFilter cf = new PorterDuffColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
				this.getBackground().setColorFilter(cf);
			}
		} else {
			if (this.getBackground() != null)
				this.getBackground().clearColorFilter();
		}

		return super.onTouchEvent(event);
	}
}
