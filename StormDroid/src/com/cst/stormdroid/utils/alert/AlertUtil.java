package com.cst.stormdroid.utils.alert;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;

import com.cst.stormdroid.R;

/**
 * util class for alert dialog
 * @author MonsterStorm
 * @version 1.0
 */
public final class AlertUtil {
	
	public interface OnAlertSelectId {
		void onClick(int whichButton);
	}
	
	/**
	 * show an notify alert with title and msg
	 * @param context
	 * @param title
	 * @param msg
	 * @return
	 */
	public static AlertDialog showAlert(final Context context, final String title, final String msg) {
		if (context instanceof Activity && ((Activity) context).isFinishing()) {
			return null;
		}

		final Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(title);
		builder.setMessage(msg);
		builder.setPositiveButton(R.string.alert_btn_ok, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(final DialogInterface dialog, final int which) {
				dialog.cancel();
			}
		});
		final AlertDialog alert = builder.create();
		alert.show();
		return alert;
	}
	
	/**
	 * show an notify alert with title and msg
	 * @param context
	 * @param msgId
	 * @param titleId
	 * @return
	 */
	public static AlertDialog showAlert(final Context context, final int titleId,  final int msgId) {
		if (context instanceof Activity && ((Activity) context).isFinishing()) {
			return null;
		}

		final Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(titleId);
		builder.setMessage(msgId);
		builder.setPositiveButton(R.string.alert_btn_ok, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(final DialogInterface dialog, final int which) {
				dialog.cancel();
			}
		});
		final AlertDialog alert = builder.create();
		alert.show();
		return alert;
	}

	/**
	 * show alert with customized action
	 * @param context
	 * @param msgId
	 * @param titleId
	 * @param l
	 * @return
	 */
	public static AlertDialog showAlert(final Context context, final int titleId, final int msgId, final DialogInterface.OnClickListener l) {
		if (context instanceof Activity && ((Activity) context).isFinishing()) {
			return null;
		}

		final Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(titleId);
		builder.setMessage(msgId);
		builder.setPositiveButton(R.string.alert_btn_ok, l);
		// builder.setCancelable(false);
		final AlertDialog alert = builder.create();
		alert.show();
		return alert;
	}
	
	/**
	 * show alert with customized action
	 * @param context
	 * @param msgId
	 * @param titleId
	 * @param l
	 * @return
	 */
	public static AlertDialog showAlert(final Context context, final String title, final String msg, final DialogInterface.OnClickListener l) {
		if (context instanceof Activity && ((Activity) context).isFinishing()) {
			return null;
		}

		final Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(title);
		builder.setMessage(msg);
		builder.setPositiveButton(R.string.alert_btn_ok, l);
		// builder.setCancelable(false);
		final AlertDialog alert = builder.create();
		alert.show();
		return alert;
	}
	
	/**
	 * show alert with customized action
	 * @param context
	 * @param msgId
	 * @param titleId
	 * @param l
	 * @return
	 */
	public static AlertDialog showAlert(final Context context, final int titleId, final int msgId, final DialogInterface.OnClickListener lOk, final DialogInterface.OnClickListener lCancel) {
		if (context instanceof Activity && ((Activity) context).isFinishing()) {
			return null;
		}

		final Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(titleId);
		builder.setMessage(msgId);
		builder.setPositiveButton(R.string.alert_btn_ok, lOk);
		builder.setNegativeButton(R.string.alert_btn_cancel, lCancel);
		// builder.setCancelable(false);
		final AlertDialog alert = builder.create();
		alert.show();
		return alert;
	}
	
	/**
	 * show alert with customized title, msg, string_ok, string_cancel, ok_action, cancel_action
	 * @param context
	 * @param title
	 * @param msg
	 * @param yes
	 * @param no
	 * @param lOk
	 * @param lCancel
	 * @return
	 */
	public static AlertDialog showAlert(final Context context, final int title, final int msg, final int yes, final int no, final DialogInterface.OnClickListener lOk,
			final DialogInterface.OnClickListener lCancel) {
		if (context instanceof Activity && ((Activity) context).isFinishing()) {
			return null;
		}

		final Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(title);
		builder.setMessage(msg);
		builder.setPositiveButton(yes, lOk);
		builder.setNegativeButton(no, lCancel);
		// builder.setCancelable(false);
		final AlertDialog alert = builder.create();
		alert.show();
		return alert;
	}
	
	/**
	 * show alert with customized title, msg, string_ok, string_cancel, ok_action, cancel_action
	 * @param context
	 * @param title
	 * @param msg
	 * @param yes
	 * @param no
	 * @param lOk
	 * @param lCancel
	 * @return
	 */
	public static AlertDialog showAlert(final Context context, final String title, final String msg, final String yes, final String no, final DialogInterface.OnClickListener lOk,
			final DialogInterface.OnClickListener lCancel) {
		if (context instanceof Activity && ((Activity) context).isFinishing()) {
			return null;
		}

		final Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(title);
		builder.setMessage(msg);
		builder.setPositiveButton(yes, lOk);
		builder.setNegativeButton(no, lCancel);
		// builder.setCancelable(false);
		final AlertDialog alert = builder.create();
		alert.show();
		return alert;
	}
	
	/**
	 * show alert with customized title and content_view
	 * @param context
	 * @param title
	 * @param msg
	 * @param yes
	 * @param no
	 * @param lOk
	 * @param lCancel
	 * @return
	 */
	public static AlertDialog showAlert(final Context context, final String title, final View view, final DialogInterface.OnClickListener lOk) {
		if (context instanceof Activity && ((Activity) context).isFinishing()) {
			return null;
		}

		final Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(title);
		builder.setView(view);
		builder.setPositiveButton(R.string.alert_btn_ok, lOk);
		// builder.setCancelable(true);
		final AlertDialog alert = builder.create();
		alert.show();
		return alert;
	}
	
	/**
	 * show alert with customized title and content_view
	 * @param context
	 * @param title
	 * @param msg
	 * @param yes
	 * @param no
	 * @param lOk
	 * @param lCancel
	 * @return
	 */
	public static AlertDialog showAlert(final Context context, final String title, final View view, final String ok, final String cancel, final DialogInterface.OnClickListener lOk,
			final DialogInterface.OnClickListener lCancel) {
		if (context instanceof Activity && ((Activity) context).isFinishing()) {
			return null;
		}

		final Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(title);
		builder.setView(view);
		builder.setPositiveButton(ok, lOk);
		builder.setNegativeButton(cancel, lCancel);
		// builder.setCancelable(false);
		final AlertDialog alert = builder.create();
		alert.show();
		return alert;
	}
	
	/**
	 * show alert with customized title , string_ok and content_view
	 * @param context
	 * @param title
	 * @param msg
	 * @param yes
	 * @param no
	 * @param lOk
	 * @param lCancel
	 * @return
	 */
	public static AlertDialog showAlert(final Context context, final String title, final String ok, final View view, final DialogInterface.OnClickListener lOk) {
		if (context instanceof Activity && ((Activity) context).isFinishing()) {
			return null;
		}

		final Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(title);
		builder.setView(view);
		builder.setPositiveButton(ok, lOk);
		// builder.setCancelable(true);
		final AlertDialog alert = builder.create();
		alert.show();
		return alert;
	}
	
	public static AlertDialog showAlert(final Context context, final String title, final String msg, final View view, final DialogInterface.OnClickListener lOk,
			final DialogInterface.OnClickListener lCancel) {
		if (context instanceof Activity && ((Activity) context).isFinishing()) {
			return null;
		}

		final Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(title);
		builder.setMessage(msg);
		builder.setView(view);
		builder.setPositiveButton(R.string.alert_btn_ok, lOk);
		builder.setNegativeButton(R.string.alert_btn_cancel, lCancel);
		// builder.setCancelable(true);
		builder.setOnCancelListener(new DialogInterface.OnCancelListener() {

			@Override
			public void onCancel(DialogInterface dialog) {
				if (lCancel != null) {
					lCancel.onClick(dialog, 0);
				}
			}
		});
		final AlertDialog alert = builder.create();
		alert.show();
		return alert;
	}
}
