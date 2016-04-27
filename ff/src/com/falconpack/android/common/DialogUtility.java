package com.falconpack.android.common;

import java.util.Arrays;

import com.falconpack.android.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class DialogUtility {

//	public static void ShowDialogMessage(String message, Activity activity) {
//
//		// AlertDialog.Builder builder = new AlertDialog.Builder(activity);
//		//
//		// builder.setMessage(message).setCancelable(false).setPositiveButton("OK",
//		// new DialogInterface.OnClickListener() {
//		// @Override
//		// public void onClick(DialogInterface dialog, int id) {
//		// dialog.cancel();
//		// }
//		// });
//		//
//		// builder.setPositiveButton("Cancel", new
//		// DialogInterface.OnClickListener() {
//		// @Override
//		// public void onClick(DialogInterface dialog, int id) {
//		// dialog.dismiss();
//		// }
//		// });
//		// AlertDialog alert = builder.create();
//		// alert.show();
//		//
//		// // You Can Customise your Message here
//		// TextView messageView = (TextView)
//		// alert.findViewById(android.R.id.message);
//		// messageView.setGravity(Gravity.CENTER);
//		//
//		// alert.getButton(DialogInterface.BUTTON_NEGATIVE).setTypeface(Constants.getProximanova_regular(activity));
//		// alert.getButton(DialogInterface.BUTTON_POSITIVE).setTypeface(Constants.getProximanova_regular(activity));
//		// messageView.setTypeface(Constants.getProximanova_regular(activity));
//		//
//		//
//		//
//		//
//
//		final Dialog offerdialog1 = new Dialog(activity);
//		offerdialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
//		offerdialog1.setCanceledOnTouchOutside(false);
//		offerdialog1.setContentView(R.layout.alert_custom_dialog);
//
//		TextView okTxt = (TextView) offerdialog1.findViewById(R.id.ok);
//		TextView cancelTxt = (TextView) offerdialog1.findViewById(R.id.cancel);
//		TextView message1 = (TextView) offerdialog1.findViewById(R.id.message);
//		okTxt.setTypeface(Constants.getProximanova_regular(activity));
//		cancelTxt.setTypeface(Constants.getProximanova_regular(activity));
//		cancelTxt.setVisibility(View.GONE);
//		message1.setTypeface(Constants.getProximanova_regular(activity));
//		message1.setText(message);
//		okTxt.setOnClickListener(new View.OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				offerdialog1.cancel();
//			}
//		});
//
//		cancelTxt.setOnClickListener(new View.OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				offerdialog1.dismiss();
//			}
//		});
//
//		if (!((Activity) activity).isFinishing()) {
//			// show dialog
//			offerdialog1.show();
//		} else {
//			Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
//		}
//
//	}

	public static void ShowMessage(String message, Activity mContext) {

		final Dialog offerdialog1 = new Dialog(mContext);
		offerdialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
		offerdialog1.setCanceledOnTouchOutside(false);
		offerdialog1.setContentView(R.layout.alert_custom_dialog);

		TextView okTxt = (TextView) offerdialog1.findViewById(R.id.ok);
		TextView cancelTxt = (TextView) offerdialog1.findViewById(R.id.cancel);
		TextView message1 = (TextView) offerdialog1.findViewById(R.id.message);
		okTxt.setTypeface(Constants.getProximanova_regular(mContext));
		cancelTxt.setTypeface(Constants.getProximanova_regular(mContext));
		cancelTxt.setVisibility(View.GONE);
		message1.setTypeface(Constants.getProximanova_regular(mContext));
		message1.setText(message);
		okTxt.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				offerdialog1.dismiss();
			}
		});
		if (!((Activity) mContext).isFinishing()) {
			// show dialog
			offerdialog1.show();
		} else {
			Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
		}
	}

	public static void ShowFinishDialoguMessage(String message, final Activity mContext) {

		final Dialog offerdialog1 = new Dialog(mContext);
		offerdialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
		offerdialog1.setCanceledOnTouchOutside(false);
		offerdialog1.setContentView(R.layout.alert_custom_dialog);

		TextView submit = (TextView) offerdialog1.findViewById(R.id.ok);
		TextView message1 = (TextView) offerdialog1.findViewById(R.id.message);
		TextView cancelTxt = (TextView) offerdialog1.findViewById(R.id.cancel);
		cancelTxt.setTypeface(Constants.getProximanova_regular(mContext));
		cancelTxt.setVisibility(View.GONE);
		submit.setTypeface(Constants.getProximanova_regular(mContext));
		message1.setTypeface(Constants.getProximanova_regular(mContext));
		message1.setText(message);
		submit.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				offerdialog1.dismiss();
				mContext.finish();
			}
		});
		if (!((Activity) mContext).isFinishing()) {
			// show dialog
			offerdialog1.show();
		} else {
			Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
		}
	}

	public static void getChooseDialogue(Activity activity, final String[] types, final String[] ids,
			final EditText edt) {

		System.out.println(" ids " + ids.length);

		AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		builder.setTitle("Choose");
		builder.setSingleChoiceItems(types, Arrays.asList(ids).indexOf(edt.getTag().toString()),
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						edt.setText(types[which]);
						edt.setTag(ids[which]);
						dialog.dismiss();
					}
				});

		AlertDialog alert = builder.create();
		alert.show();

		return;
	}

	public static void getGender(Activity activity, final EditText edt) {
		final String[] genderTypes = new String[] { "Male", "Female" };
		AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		builder.setTitle("Choose");

		builder.setSingleChoiceItems(genderTypes, Integer.parseInt(edt.getTag().toString()),
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						edt.setText(genderTypes[which]);
						edt.setTag(which);
						dialog.dismiss();
					}
				});

		AlertDialog alert = builder.create();
		alert.show();
	}

	public static void ShowResultMessage(final Activity activity, final String status, String message) {

		final Dialog offerdialog1 = new Dialog(activity);
		offerdialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
		offerdialog1.setCanceledOnTouchOutside(false);
		offerdialog1.setContentView(R.layout.alert_custom_dialog);

		TextView submit = (TextView) offerdialog1.findViewById(R.id.ok);
		TextView message1 = (TextView) offerdialog1.findViewById(R.id.message);

		TextView cancelTxt = (TextView) offerdialog1.findViewById(R.id.cancel);
		cancelTxt.setTypeface(Constants.getProximanova_regular(activity));
		cancelTxt.setVisibility(View.GONE);

		submit.setTypeface(Constants.getProximanova_regular(activity));
		message1.setTypeface(Constants.getProximanova_regular(activity));
		message1.setText(message);
		submit.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (status.equalsIgnoreCase("1")) {
					activity.finish();
				} else {
					offerdialog1.dismiss();
				}
			}
		});
		if (!((Activity) activity).isFinishing()) {
			// show dialog
			offerdialog1.show();
		} else {
			Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
		}

	}

	// public static void showConfirmMessage(Activity mContext, String msg) {
	// AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
	// // builder.setMessage("Network is not available. Please check network
	// // connection and try again.").setCancelable(false)
	// builder.setMessage(msg).setCancelable(false).setPositiveButton("OK", new
	// DialogInterface.OnClickListener() {
	// @Override
	// public void onClick(DialogInterface dialog, int id) {
	// dialog.cancel();
	// }
	// });
	// AlertDialog alert = builder.create();
	// alert.show();
	// // You Can Customise your Message here
	// TextView messageView = (TextView)
	// alert.findViewById(android.R.id.message);
	// messageView.setGravity(Gravity.CENTER);
	//
	// }
}
