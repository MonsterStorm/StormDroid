package com.cst.stormdroid.ui.listview.autoload;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * pullable listview
 * 
 * @author Storm
 * 
 */
public class PullableListView extends PullableAbsListView<ListView> {

	interface OnAdaperSetListener {
		public void onSetAdaper();
	}

	public PullableListView(Context context) {
		super(context);
	}

	public PullableListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public int getHeaderViewsCount() {
		return mContentView.getHeaderViewsCount();
	}

	@Override
	public int getFooterViewsCount() {
		return mContentView.getFooterViewsCount();
	}

	@Override
	public ListView createContentView(final Context context, AttributeSet attrs) {
		ListView lv = new InternalListView(context, attrs);
		return lv;
	}

	protected class InternalListView extends ListView implements EmptyViewMethodAccessor {
		
		
		public InternalListView(Context context, AttributeSet attrs) {
			super(context, attrs);
		}

		@Override
		protected void dispatchDraw(Canvas canvas) {
			try {
				super.dispatchDraw(canvas);
			} catch (IndexOutOfBoundsException e) {
				e.printStackTrace();
			}
		}

		@Override
		public boolean dispatchTouchEvent(MotionEvent ev) {
			try {
				return super.dispatchTouchEvent(ev);
			} catch (IndexOutOfBoundsException e) {
				e.printStackTrace();
				return false;
			}
		}

		@Override
		public void setEmptyView(View emptyView) {
			PullableListView.this.setEmptyView(emptyView);
		}

		@Override
		public void setEmptyViewInternal(View emptyView) {
			super.setEmptyView(emptyView);
		}

		@Override
		public void setAdapter(ListAdapter adapter) {
			super.setAdapter(adapter);

			if (adapter != null && adapter.getCount() > 0) {
				updateUIForMode();
			}
		}
	}

	@Override
	public void handleStyledAttributes(TypedArray array) {
	}

	@Override
	public void addHeaderLayout(LayoutParams lp) {
		mContentView.addHeaderView(getHeaderLayout());
	}

	@Override
	public void addFooterLayout(LayoutParams lp) {
		mContentView.addFooterView(getFooterLayout());
	}

	@Override
	public void removeHeaderLayout() {
		mContentView.removeHeaderView(getHeaderLayout());
	}

	@Override
	public void removeFooterLayout() {
		mContentView.removeFooterView(getFooterLayout());
	}

	@Override
	public View createHeaderView(Context context, Mode mode, TypedArray array) {
		return null;
	}

	@Override
	public View createFooterView(Context context, Mode mode, TypedArray array) {
		return null;
	}
}
