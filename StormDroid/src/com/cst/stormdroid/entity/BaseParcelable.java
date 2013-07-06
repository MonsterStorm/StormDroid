package com.cst.stormdroid.entity;

import android.os.Parcelable;
/**
 * Base Parcelable class, the writeToParcel and createFromParcel in Creator must has the same operation order.
 * @author MonsterStorm
 * @version 1.0
 */
public abstract class BaseParcelable<T> implements Parcelable{
	//creator
	protected Parcelable.Creator<T> mCreator;
	
	public BaseParcelable(){
		mCreator = createCreator();
	}
	
	@Override
	public int describeContents() {
		return 0;
	}
	
	protected abstract Creator<T> createCreator();
}
