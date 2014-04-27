package com.cst.stormdroid.ui.listview.autoload;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.Adapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;

public abstract class PullableAbsListView<T extends AbsListView> extends PullableViewBase<T> {
	//empty view
	private View mEmptyView;

	public PullableAbsListView(Context context) {
		super(context);
	}

	public PullableAbsListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public boolean isReadyForPullStart() {
		return isFirstItemFullVisible();
	}

	public boolean isReadyForPullEnd() {
		return isLastItemFullVisible();
	}

	/**
	 * is header Layout visible
	 * @return
	 */
	public boolean isHeaderLayoutVisible(){
		final Adapter adapter = getContentView().getAdapter();

		if (null == adapter || adapter.isEmpty()) {
			return true;
		} else {
			int headerViewCount = getHeaderViewsCount();
			if (mContentView.getFirstVisiblePosition() < headerViewCount) {
				final View firstVisibleChild = getHeaderLayout();
				if (firstVisibleChild != null) {
					int bottom1 = firstVisibleChild.getBottom();
					return bottom1 > 0;
				}
			}
		}
		return false;
	}
	
	/**
	 * is footer layout visible
	 * @return
	 */
	public boolean isFooterLayoutVisible(){
		final Adapter adapter = getContentView().getAdapter();

		if (null == adapter || adapter.isEmpty()) {
			return true;
		} else {
			int footerViewCount = getFooterViewsCount();
			final int lastItemPosition = mContentView.getCount() - 1;
			if (mContentView.getLastVisiblePosition() > lastItemPosition - footerViewCount) {//不用>=
				final View lastVisibleChild = getFooterLayout();
				if (lastVisibleChild != null) {
					int top1 = lastVisibleChild.getTop();
					int bottom2 = mContentView.getBottom() - mContentView.getTop();//normallize to zero in y axe
					return top1 < bottom2;
				}
			}
		}

		return false;
	}
	
	/**
	 * 是否第一个元素完全可见，部分可见返回false
	 * @return
	 */
	public boolean isFirstItemFullVisible() {
		final Adapter adapter = getContentView().getAdapter();
		int headerCount = 0;
		if(mContentView instanceof ListView){
			ListView lv = ((ListView)mContentView);
			headerCount = lv.getHeaderViewsCount();
		}
		
		if ((null == adapter || adapter.isEmpty()) && headerCount == 0){
			return true;
		} else {
			
			/**
			 * PullToRefresh中：
			 * This check should really just be:
			 * mRefreshableView.getFirstVisiblePosition() == 0, but PtRListView
			 * internally use a HeaderView which messes the positions up. For
			 * now we'll just add one to account for it and rely on the inner
			 * condition which checks getTop().
			 */
			if (mContentView.getFirstVisiblePosition() == 0) {//第一个漏出来了才能拖动,不同于PullToRefresh的设置（mRefreshableView.getFirstVisiblePosition() <= 1）
				final View firstVisibleChild = mContentView.getChildAt(0);
				if (firstVisibleChild != null) {
					int top1 = firstVisibleChild.getTop();
					int top2 = mContentView.getTop();//normally is zero
//					Log.e("XXXXXXXXXXXXXXXisFirstItemFullVisible", mContentView.getFirstVisiblePosition() + "," + getHeaderViewsCount() + "," + top1 + "," + top2);
					//return top1 >= 0;//top1 >= top2
					return top1 >= top2;
				}
			}
		}
		return false;
	}
	
	/**
	 * 是否最后一个元素完全可见，部分可见返回false
	 * @return
	 */
	public boolean isLastItemFullVisible() {
		final Adapter adapter = getContentView().getAdapter();
		int footerCount = 0;
		if(mContentView instanceof ListView){
			ListView lv = ((ListView)mContentView);
			footerCount = lv.getFooterViewsCount();
		}

		if ((null == adapter || adapter.isEmpty()) && footerCount == 0) {
			return true;
		} else {
			/**
			 * PullToRefresh中：
			 * This check should really just be: lastVisiblePosition ==
			 * lastItemPosition, but PtRListView internally uses a FooterView
			 * which messes the positions up. For me we'll just subtract one to
			 * account for it and rely on the inner condition which checks
			 * getBottom().
			 */
			final int lastItemPosition = mContentView.getCount() - 1;
//			Log.e("XXXXXXXXXXXXXXXisLastItemFullVisible", mContentView.getLastVisiblePosition() + "," + lastItemPosition);
			if (mContentView.getLastVisiblePosition() == lastItemPosition) {//不同于PullToRefresh的设置（lastVisiblePosition >= lastItemPosition - 1）
				final int childIndex = mContentView.getLastVisiblePosition() - mContentView.getFirstVisiblePosition();
				final View lastVisibleChild = mContentView.getChildAt(childIndex);
				if (lastVisibleChild != null) {
					int bottom1 = lastVisibleChild.getBottom();
					int bottom2 = mContentView.getBottom() - mContentView.getTop();//normallize to zero in y axe
					
//					Log.e("XXXXXXXXXXXXXXXisLastItemFullVisible", mContentView.getLastVisiblePosition() + "," + getFooterViewsCount() + "," + bottom1 + "," + bottom2);
					return bottom1 <= bottom2;
				}
			}
		}

		return false;
	}

	private static FrameLayout.LayoutParams convertEmptyViewLayoutParams(ViewGroup.LayoutParams lp) {
		FrameLayout.LayoutParams newLp = null;

		if (null != lp) {
			newLp = new FrameLayout.LayoutParams(lp);

			if (lp instanceof LinearLayout.LayoutParams) {
				newLp.gravity = ((LinearLayout.LayoutParams) lp).gravity;
			} else {
				newLp.gravity = Gravity.CENTER;
			}
		}

		return newLp;
	}

	public final void setEmptyView(View newEmptyView) {
		FrameLayout refreshableViewWrapper = getContentViewWrapper();

		if (null != newEmptyView) {
			// New view needs to be clickable so that Android recognizes it as a
			// target for Touch Events
			newEmptyView.setClickable(true);

			ViewParent newEmptyViewParent = newEmptyView.getParent();
			if (null != newEmptyViewParent && newEmptyViewParent instanceof ViewGroup) {
				((ViewGroup) newEmptyViewParent).removeView(newEmptyView);
			}

			// We need to convert any LayoutParams so that it works in our
			// FrameLayout
			FrameLayout.LayoutParams lp = convertEmptyViewLayoutParams(newEmptyView.getLayoutParams());
			if (null != lp) {
				refreshableViewWrapper.addView(newEmptyView, lp);
			} else {
				refreshableViewWrapper.addView(newEmptyView);
			}
		}

		if (mContentView instanceof EmptyViewMethodAccessor) {
			((EmptyViewMethodAccessor) mContentView).setEmptyViewInternal(newEmptyView);
		} else {
			mContentView.setEmptyView(newEmptyView);
		}
		mEmptyView = newEmptyView;
	}

	protected void setOnScrollListenerInternal(OnScrollListener l) {
		mContentView.setOnScrollListener(l);
	}

	//get header count of this AbsListView
	public abstract int getHeaderViewsCount();

	//get footer count of this AbsListView	
	public abstract int getFooterViewsCount();

}
