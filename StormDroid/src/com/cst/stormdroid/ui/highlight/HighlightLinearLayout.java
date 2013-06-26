package com.cst.stormdroid.ui.highlight;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * when click, then highlight it
 * @author MonsterStorm
 * @version 1.0
 */
public class HighlightLinearLayout extends LinearLayout {
	
	public HighlightLinearLayout(Context context) {
		super(context);
	}

	public HighlightLinearLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	@TargetApi(11)
	public HighlightLinearLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			if (this.getBackground() != null) {
				ColorFilter cf = new PorterDuffColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
				this.getBackground().setColorFilter(cf);
				int count = this.getChildCount();
				for (int i = 0; i < count; i++) {
					View v = this.getChildAt(i);
					if (v instanceof ImageView || v instanceof ImageButton) {
						((ImageView) v).setColorFilter(cf);
					} else {
						if (v.getBackground() != null)
							v.getBackground().setColorFilter(cf);
					}
				}
			}
		} else {
			if (this.getBackground() != null)
				this.getBackground().clearColorFilter();
			int count = this.getChildCount();
			for (int i = 0; i < count; i++) {
				View v = this.getChildAt(i);
				if (v instanceof ImageView || v instanceof ImageButton) {
					((ImageView) v).clearColorFilter();
				} else {
					if (v.getBackground() != null)
						v.getBackground().clearColorFilter();
				}
			}
		}
		return super.onTouchEvent(event);
	}
}
