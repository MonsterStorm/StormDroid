package com.cst.stormdroid.adapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;

/**
 * base adapter for view pager
 * @author MonsterStorm
 * @version 1.0
 */
public class SDBasePagerAdapter extends PagerAdapter {
	/**
	 * views of this adapter
	 */
	private List<View> mViews;
	/**
	 * view ids of this adapter
	 */
	private List<Integer> mViewIds;
	protected Context mContext;
	protected LayoutInflater mInflater;

	/**
	 * init page data from resources, if is set true, then view will be created from resources
	 */
	protected boolean mFromIds = false;

	public SDBasePagerAdapter(Context context) {
		this.mFromIds = true;
		this.mViews = new ArrayList<View>();
		this.mContext = context;
		this.mInflater = LayoutInflater.from(context);
	}

	public SDBasePagerAdapter(List<View> views, Context context) {
		this.mFromIds = false;
		this.mViews = views;
		this.mContext = context;
		this.mInflater = LayoutInflater.from(context);
	}

	public SDBasePagerAdapter(Context context, Integer... viewIds) {
		this(context, false, viewIds);
	}

	public SDBasePagerAdapter(Context context, boolean lazy, Integer... viewIds) {
		this.mContext = context;
		this.mFromIds = lazy;
		this.mViews = new ArrayList<View>();
		this.mInflater = LayoutInflater.from(context);
		this.mViewIds = Arrays.asList(viewIds);
	}

	public SDBasePagerAdapter(Context context, List<Integer> viewIds) {
		this(context, false, viewIds);
	}

	public SDBasePagerAdapter(Context context, boolean lazy, List<Integer> viewIds) {
		this.mContext = context;
		this.mFromIds = lazy;
		this.mViews = new ArrayList<View>();
		this.mInflater = LayoutInflater.from(context);
		this.mViewIds = viewIds;
	}

	/**
	 * create page view
	 * @param pageIndex
	 * @param pageViewId
	 * @return
	 */
	protected View createPageView(int pageIndex, int pageViewId) {
		return this.mInflater.inflate(pageViewId, null);
	}

	/**
	 * init page data
	 * @param v
	 * @param pageIndex
	 * @param pageViewId
	 */
	protected void initPageData(View v, int pageIndex, int pageViewId) {
	}

	/**
	 * bind action for page
	 * @param v
	 * @param pageNo
	 * @param pageViewId
	 */
	protected void bindPageAction(View v, int pageIndex, int pageViewId) {
	}

	/**
	 * set views of this adapter
	 * @param views
	 */
	public void setViews(List<View> views) {
		this.mFromIds = false;
		this.mViews = views;
		this.notifyDataSetChanged();
	}

	/**
	 * get views
	 * @return
	 */
	public List<View> getViews() {
		return this.mViews;
	}

	/**
	 * get view
	 * @param pos
	 * @return
	 */
	public View getView(int pos) {
		if (mViews != null && mViews.size() > pos) {
			return mViews.get(pos);
		}
		return null;
	}

	/**
	 * set view ids
	 * @param viewIds
	 */
	public void setViewIds(Integer... viewIds) {
		this.mFromIds = true;
		this.mViewIds = Arrays.asList(viewIds);
		this.notifyDataSetChanged();
	}

	/**
	 * set view ids
	 * @param viewIds
	 */
	public void setViewIds(List<Integer> viewIds) {
		this.mFromIds = true;
		this.mViewIds = viewIds;
		this.notifyDataSetChanged();
	}

	/**
	 * get inflater
	 * @return
	 */
	public LayoutInflater getInflater() {
		return this.mInflater;
	}

	// *************************************life cycle functions*************************************
	/**
	 * init the item view
	 */
	@Override
	public Object instantiateItem(View pager, int pos) {
		if (mFromIds == true) {// load view from ids
			View v = createPageView(pos, mViewIds.get(pos));
			initPageData(v, pos, mViewIds.get(pos));
			bindPageAction(v, pos, mViewIds.get(pos));
			mViews.add(v);
		}
		((ViewPager) pager).addView(mViews.get(pos), 0);
		return mViews.get(pos);
	}

	/**
	 * destory item
	 */
	@Override
	public void destroyItem(View pager, int position, Object obj) {
		((ViewPager) pager).removeView(mViews.get(position));
	}

	@Override
	public int getCount() {
		if (mFromIds == false && mViews != null) {
			return mViews.size();
		}
		if (mFromIds == true && mViewIds != null) {
			return mViewIds.size();
		}
		return 0;
	}

	/**
	 * decide whether is build from object
	 */
	@Override
	public boolean isViewFromObject(View view, Object obj) {
		return (view == obj);
	}

	@Override
	public Parcelable saveState() {
		return null;
	}

	@Override
	public void restoreState(Parcelable parcelable, ClassLoader loader) {
	}

	@Override
	public void startUpdate(View view) {
	}

	@Override
	public void finishUpdate(View view) {
	}
}
