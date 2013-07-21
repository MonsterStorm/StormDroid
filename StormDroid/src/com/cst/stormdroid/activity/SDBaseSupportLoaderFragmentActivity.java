package com.cst.stormdroid.activity;

import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;

/**
 * base fragment activity for all fragment activities
 * @author MonsterStorm
 * @version 1.0
 */
public abstract class SDBaseSupportLoaderFragmentActivity<T> extends SDBaseSupportFragmentActivity implements LoaderManager.LoaderCallbacks<T> {
	private static final String TAG = SDBaseSupportLoaderFragmentActivity.class.getSimpleName();

	/**
	 * init loader
	 * @param which
	 */
	protected void initLoaders(Integer... whichs) {
		for(int i = 0; i < whichs.length; i++){
			getSupportLoaderManager().initLoader(whichs[i], null, this);
		}
	}

	/**
	 * restart loaders
	 * @param whichs
	 */
	protected void restartLoaders(Integer... whichs){
		for(int i = 0; i < whichs.length; i++){
			getSupportLoaderManager().restartLoader(0, null, (LoaderCallbacks<T>) this);
		}
	}
}