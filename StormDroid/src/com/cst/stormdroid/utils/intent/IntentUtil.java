package com.cst.stormdroid.utils.intent;

import java.io.Serializable;

import android.content.Intent;
import android.os.Parcelable;

/**
 * class for intent process
 * @author MonsterStorm
 * @version 1.0
 */
public class IntentUtil {
	/**
	 * Prefix for all intents created
	 */
	public static final String INTENT_PREFIX = "com.cst.stormdroid.";

	/**
	 * Prefix for all extra data added to intents
	 */
	public static final String INTENT_EXTRA_PREFIX = INTENT_PREFIX + "extra.";

	/**
	 * class for build intent
	 * @author MonsterStorm
	 * @version 1.0
	 */
	public static class Builder {
		private final Intent intent;

		/**
		 * Create builder with suffix
		 * @param actionSuffix
		 */
		public Builder(String actionSuffix) {
			intent = new Intent(INTENT_PREFIX + actionSuffix);
		}

		/**
		 * Add extra field data value to intent being built up
		 * 
		 * @param fieldName
		 * @param value
		 * @return this builder
		 */
		public Builder add(String fieldName, String value) {
			intent.putExtra(fieldName, value);
			return this;
		}

		/**
		 * Add extra field data values to intent being built up
		 * 
		 * @param fieldName
		 * @param values
		 * @return this builder
		 */
		public Builder add(String fieldName, CharSequence[] values) {
			intent.putExtra(fieldName, values);
			return this;
		}

		/**
		 * Add extra field data value to intent being built up
		 * 
		 * @param fieldName
		 * @param value
		 * @return this builder
		 */
		public Builder add(String fieldName, int value) {
			intent.putExtra(fieldName, value);
			return this;
		}

		/**
		 * Add extra field data value to intent being built up
		 * 
		 * @param fieldName
		 * @param values
		 * @return this builder
		 */
		public Builder add(String fieldName, int[] values) {
			intent.putExtra(fieldName, values);
			return this;
		}

		/**
		 * Add extra field data value to intent being built up
		 * 
		 * @param fieldName
		 * @param values
		 * @return this builder
		 */
		public Builder add(String fieldName, boolean[] values) {
			intent.putExtra(fieldName, values);
			return this;
		}

		/**
		 * Add extra field data value to intent being built up
		 * 
		 * @param fieldName
		 * @param value
		 * @return this builder
		 */
		public Builder add(String fieldName, Serializable value) {
			intent.putExtra(fieldName, value);
			return this;
		}

		/**
		 * Add extra field data value to intent being built up
		 * 
		 * @param fieldName
		 * @param value
		 * @return this builder
		 */
		public Builder add(String fieldName, Parcelable value) {
			intent.putExtra(fieldName, value);
			return this;
		}

		/**
		 * Get built intent
		 * 
		 * @return intent
		 */
		public Intent toIntent() {
			return intent;
		}
	}
}
