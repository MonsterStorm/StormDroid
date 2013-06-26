package com.cst.stormdroid.ui.highlight;

import android.content.Context;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

/**
 * when click, then highlight it
 * @author MonsterStorm
 * @version 1.0
 */
public class HighlightImageView extends ImageView {
	
	public HighlightImageView(Context context) {
		super(context);
	}
	public HighlightImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	public HighlightImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if(event.getAction() == MotionEvent.ACTION_DOWN){
			ColorFilter cf = new PorterDuffColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
			this.setColorFilter(cf);
		} else {
			this.clearColorFilter();
		}
		return super.onTouchEvent(event);
	}
	

}
