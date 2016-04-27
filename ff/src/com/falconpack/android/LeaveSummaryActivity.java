package com.falconpack.android;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import com.falconpack.android.common.CommonUtility;
import com.falconpack.android.common.Constants;
import com.falconpack.android.common.DialogUtility;
import com.falconpack.android.common.NetworkUtility;
import com.falconpack.android.common.PreferenceUtilities;
import com.falconpack.android.webservicecall.WebServiceCalls;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

public class LeaveSummaryActivity extends Activity implements OnClickListener {

	TextView other_leavesValTakenTxt, leavesTxt, leaves_valTxt, apply_leaveTxt;

	// Button apply_leaveButton;
	Context mContext;
	Activity activity;
	String total_leaves, total_leaves_used, total_leaves_remaining,
			extra_leaves_used, total_sick_leaves, total_sick_leaves_used,
			total_casual_leaves, total_casual_leaves_used, total_annual_leaves,
			total_annual_leaves_used, total_other_leaves,
			total_other_leaves_used;
	LinearLayout leavesLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_leave_summary);

		mContext = getApplicationContext();
		activity = LeaveSummaryActivity.this;

		apply_leaveTxt = (TextView) findViewById(R.id.apply_leave_btn);

		// sick_leavesValTakenTxt = (TextView)
		// findViewById(R.id.sick_leaves_taken_val_txt);

		// other_leavesValTakenTxt = (TextView)
		// findViewById(R.id.other_leaves_taken_val_txt);

		leavesLayout = (LinearLayout) findViewById(R.id.leavesLayout);

		apply_leaveTxt.setTypeface(Constants
				.getProximanova_bold(getApplicationContext()));

		apply_leaveTxt.setOnClickListener(this);

		CommonUtility
				.getHeaderTitle("Leave Summary", LeaveSummaryActivity.this);

		if (NetworkUtility.checkInternetConnection(activity)) {
			new LeavesSummaryAsyncTask().execute();
		} else {
			DialogUtility.ShowMessage(Constants.newtWorkMsg,activity);
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.apply_leave_btn:
			startActivity(new Intent(LeaveSummaryActivity.this,
					ApplyLeaveActivity.class));
			break;
		default:
			break;
		}
	}

	public class LeavesSummaryAsyncTask extends
			AsyncTask<String, String, String> {

		ProgressDialog pDialog;

		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(activity);
			pDialog.setMessage("Loading...");
			pDialog.setCancelable(false);
			pDialog.show();
		}

		protected String doInBackground(String... args) {

			List<NameValuePair> pairs = new ArrayList<NameValuePair>();

			// pairs.add(new BasicNameValuePair("apikey", "ETG123"));
			pairs.add(new BasicNameValuePair("deviceid", CommonUtility
					.getDeviceId(activity)));
			pairs.add(new BasicNameValuePair("id", PreferenceUtilities
					.getSavedUserData(activity).getId()));
			return WebServiceCalls.postValues(pairs, "leave_summary")
					.toString();
		}

		protected void onPostExecute(String jsonResult) {

			System.out.println(" result " + jsonResult);

			if (null != pDialog && pDialog.isShowing()) {

				pDialog.dismiss();

			}
			if (!TextUtils.isEmpty(jsonResult) || jsonResult.length() > 0) {

				try {

					JSONObject jsonObject = new JSONObject(jsonResult);
					if (jsonObject.getString("status").equalsIgnoreCase("1")) {

						jsonObject = jsonObject.getJSONObject("leave_details");

						for (Iterator<String> iter = jsonObject.keys(); iter
								.hasNext();) {
							String key = iter.next();

							Object value = jsonObject.get(key);

							System.out.println(" key " + key);
							System.out.println(" value " + value);

							// DialogUtility.ShowMessage(
							// jsonObject.getString("message"), activity);

							if (!key.equalsIgnoreCase("message")
									&& !key.equalsIgnoreCase("status")) {

								// Inflate the Hidden Layout Information View

								View hiddenInfo = getLayoutInflater().inflate(
										R.layout.row_leaves, leavesLayout,
										false);

								leavesTxt = (TextView) hiddenInfo
										.findViewById(R.id.leaves_txt);
								leaves_valTxt = (TextView) hiddenInfo
										.findViewById(R.id.numOf_leaves_txt);

								leavesTxt
										.setTypeface(Constants
												.getProximanova_semibold(getApplicationContext()));
								leaves_valTxt
										.setTypeface(Constants
												.getProximanova_semibold(getApplicationContext()));

								leavesTxt.setText(key);

								if (value.toString().trim()
										.equalsIgnoreCase("1")) {
									leaves_valTxt.setText(value + " Day");
								} else {
									leaves_valTxt.setText(value + " Days");
								}

								if (key.equalsIgnoreCase("Last Annual Vacation Taken")) {
									if (value.toString().contains("/")) {
										leaves_valTxt.setText("" + value);
									} else {
										leaves_valTxt.setText("-");
									}
								}

								leavesLayout.addView(hiddenInfo);
							}

						}
					} else {
						DialogUtility.ShowMessage(
								jsonObject.getString("message"), activity);
					}
					// if (jsonObject.getString("status").equalsIgnoreCase("1"))
					// {
					//
					// DialogUtility.ShowMessage(
					// jsonObject.getString("message"), activity);
					//
					// total_numOfTxt.setText(jsonObject
					// .getString("total_leaves") + " Days");
					//
					// total_leaves_used = jsonObject
					// .getString("total_leaves_used");
					//
					// leaves_availNumTxt.setText(jsonObject
					// .getString("total_leaves_used") + " Days");
					//
					// total_leaves_remaining = jsonObject
					// .getString("total_leaves_remaining");
					//
					// leaves_remNumTxt.setText(jsonObject
					// .getString("total_leaves_used") + " Days");
					//
					// extra_leaves_used = jsonObject
					// .getString("extra_leaves_used");
					//
					// extra_leaves_valTxt.setText(jsonObject
					// .getString("extra_leaves_used") + " Days");
					//
					// total_sick_leaves = jsonObject
					// .getString("total_sick_leaves");
					//
					// sick_leaves_valTxt.setText(jsonObject
					// .getString("total_sick_leaves") + " Days");
					//
					// total_sick_leaves_used = jsonObject
					// .getString("total_sick_leaves_used");
					//
					// sick_leavesValTakenTxt.setText(total_sick_leaves_used
					// + " Days");
					//
					// total_casual_leaves = jsonObject
					// .getString("total_casual_leaves");
					//
					// casual_leaves_valTxt.setText(total_casual_leaves
					// + " Days");
					//
					// total_casual_leaves_used = jsonObject
					// .getString("total_casual_leaves_used");
					//
					// casual_leaves_takenValTxt
					// .setText(total_casual_leaves_used + " Days");
					//
					// total_annual_leaves = jsonObject
					// .getString("total_annual_leaves");
					//
					// anual_vacAccValTxt.setText(total_annual_leaves
					// + " Days");
					//
					// total_annual_leaves_used = jsonObject
					// .getString("total_annual_leaves_used");
					//
					// last_anualValTxt.setText(total_annual_leaves_used
					// + " Days");
					//
					// total_other_leaves = jsonObject
					// .getString("total_other_leaves");
					//
					// other_leaves_valTxt.setText(jsonObject
					// .getString("total_other_leaves") + " Days");
					//
					// total_other_leaves_used = jsonObject
					// .getString("total_other_leaves_used");
					//
					// other_leavesValTakenTxt.setText(total_other_leaves_used
					// + " Days");
					// }
				} catch (Exception e) {
					e.printStackTrace();
					DialogUtility.ShowMessage(e.getMessage(), activity);
				}
			} else {
				DialogUtility.ShowMessage(
						"Unable to retrieve data from Server", activity);
			}
		}
	}
}
