package com.cst.stormdroid.activity;

import android.annotation.TargetApi;
import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;

/**
 * base activity for all activities
 * @author MonsterStorm
 * @version 1.0
 */
@TargetApi(11)
public abstract class SDBaseLoaderActivity<T> extends SDBaseActivity implements LoaderManager.LoaderCallbacks<T> {
	private static final String TAG = SDBaseLoaderActivity.class.getSimpleName();

	/**
	 * init loader
	 * @param which
	 */
	protected void initLoaders(Integer... whichs) {
		for(int i = 0; i < whichs.length; i++){
			getLoaderManager().initLoader(whichs[i], null, this);
		}
	}

	/**
	 * restart loaders
	 * @param whichs
	 */
	protected void restartLoaders(Integer... whichs){
		for(int i = 0; i < whichs.length; i++){
			getLoaderManager().restartLoader(0, null, (LoaderCallbacks<T>) this);
		}
	}
}
