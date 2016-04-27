package com.falconpack.android;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import com.falconpack.android.common.CommonUtility;
import com.falconpack.android.common.Constants;
import com.falconpack.android.common.DialogUtility;
import com.falconpack.android.common.NetworkUtility;
import com.falconpack.android.common.PreferenceUtilities;
import com.falconpack.android.model.Categories;
import com.falconpack.android.webservicecall.WebServiceCalls;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class NotificationsSubFragment extends Fragment {

	public static String[] document1 = { "Documents Uploaded",
			"Enquiry Received", "Enquiry Received", "1 New Event",
			"2 New Leaves Pending for approval" };

	public static String[] document2 = { "Document1", "Document1", "", "", "", };

	public static String[] time = { "9:05 AM", "9:10 AM", "10:30 AM",
			"9:05 AM", "10:15 AM", };

	ListView notification_listview;
	String TAG_FRAGMENT = NotificationsSubFragment.class.getName();
	ArrayList<Categories> categoriesList;

	TextView statusTxt, status_valTxt, enameTxt, ename_valueTxt, subTxt,
			sub_val_Txt, appliedTxt, applied_dateTxt, fromTxt, from_dateTxt,
			toTxt, to_dateTxt, no_ofTxt, no_of_daysTxt, descTxt, desc_valTxt,
			accept_leaveTxt, reject_leaveTxt, noTxt, approvedTxt,
			approved_valTxt;
	String leave_status = "", value = "";
	Categories category;
	Dialog dialog;

	// Activity activity;

	public NotificationsSubFragment() {
	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);

		Bundle bundle = this.getArguments();
		if (bundle != null) {

			if (!bundle.get("key").toString().equals(null)) {
				value = (String) bundle.get("key");
			}
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_notification,
				container, false);
		// ImageView img_animation =
		// (ImageView)rootView.findViewById(R.id.iv_menily_home);

		notification_listview = (ListView) rootView
				.findViewById(R.id.notification_listview);
		noTxt = (TextView) rootView.findViewById(android.R.id.empty);

		noTxt.setTypeface(Constants.getProximanova_bold(getActivity()));

		notification_listview.setEmptyView(noTxt);

		if (value.equalsIgnoreCase("General")) {

			notification_listview.setAdapter(new CustomAdapter(getActivity(),
					CommonUtility.generalNotificationList));

		} else {

			if (NetworkUtility.checkInternetConnection(getActivity())) {
				new LeaveNotificationsAsyncTask().execute();
			} else {
				DialogUtility.ShowMessage(Constants.newtWorkMsg,getActivity());
			}
		}

		// notification_listview.setAdapter(new CustomAdapter(getActivity(),
		// document1, document2, time));

		MainActivity.tv_header.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (value.equalsIgnoreCase("Leaves")) {

					getBackFragment();

				} else {
					if (NetworkUtility.checkInternetConnection(getActivity())) {
						new updateNotificationAsyncTask().execute();
					} else {
						DialogUtility.ShowMessage(Constants.newtWorkMsg,getActivity());
					}
				}
			}
		});

		// notification_listview.setAdapter(new CustomAdapter(getActivity(),
		// CommonUtility.categoriesList));

		notification_listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				if (value.equalsIgnoreCase("Leaves")) {
					category = CommonUtility.categoriesList.get(arg2);

					System.out.println(" get position "
							+ category.getLeaveType_id() + " user name "
							+ category.getUser_name());

					getLeaveStatusDialogue();
				}
			}
		});

		return rootView;
	}

	class updateNotificationAsyncTask extends AsyncTask<String, String, String> {

		ProgressDialog pDialog;

		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(getActivity());
			pDialog.setMessage("Loading...");
			pDialog.setCancelable(false);
			pDialog.show();
		}

		protected String doInBackground(String... args) {

			List<NameValuePair> pairs = new ArrayList<NameValuePair>();

			String ids = "";
			if (CommonUtility.generalNotificationList.size() > 0) {

				for (int i = 0; i < CommonUtility.generalNotificationList
						.size(); i++) {

					CommonUtility.notificationFlag_ids = CommonUtility.notificationFlag_ids
							+ ","
							+ CommonUtility.generalNotificationList.get(i)
									.getId();
				}

				ids = CommonUtility.notificationFlag_ids;

				// System.out.println( " content " +ids);
				System.out
						.println(" content " + ids.substring(1, ids.length()));

			}

			pairs.add(new BasicNameValuePair("deviceid", CommonUtility
					.getDeviceId(getActivity())));
			pairs.add(new BasicNameValuePair("notification_flag_id", ids));// for
																			// turning
																			// off
																			// general
																			// notifications
			pairs.add(new BasicNameValuePair("document_flag_id",
					CommonUtility.documentFlag_ids));// for

			// turning
			// notifications

			return WebServiceCalls
					.postValues(pairs, "update_notification_flag").toString();
		}

		protected void onPostExecute(String jsonResult) {

			System.out.println(" result " + jsonResult);

			if (null != pDialog && pDialog.isShowing()) {
				pDialog.dismiss();
			}

			if (!TextUtils.isEmpty(jsonResult) || jsonResult.length() > 0) {

				try {

					JSONObject jsonObject = new JSONObject(jsonResult);
					System.out.println("status "
							+ jsonObject.getString("status"));

					// AlertDialog.Builder builder = new AlertDialog.Builder(
					// getActivity());
					//
					// builder.setMessage(jsonObject.getString("message"))
					// .setCancelable(false);

					// dialog.dismiss();
					getBackFragment();

					// if (jsonObject.getString("status").equalsIgnoreCase("1"))
					// {
					//
					// builder.setPositiveButton("OK",
					// new DialogInterface.OnClickListener() {
					// @Override
					// public void onClick(DialogInterface dialog,
					// int id) {
					// // finish();
					// dialog.dismiss();
					// getBackFragment();
					// }
					// });
					// } else {
					// builder.setPositiveButton("OK",
					// new DialogInterface.OnClickListener() {
					// @Override
					// public void onClick(DialogInterface dialog,
					// int id) {
					// dialog.cancel();
					// }
					// });
					// }

					// AlertDialog alert = builder.create();
					// alert.show();

				} catch (Exception e) {
					e.printStackTrace();
					DialogUtility.ShowMessage(e.getMessage(), getActivity());
				}
			} else {
				DialogUtility.ShowMessage(
						"Unable to retrieve data from Server", getActivity());
			}
		}
	}

	private void getLeaveStatusDialogue() {
		// TODO Auto-generated method stub
		dialog = new Dialog(getActivity(),
				android.R.style.Theme_Translucent_NoTitleBar);
		dialog.setCancelable(true);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.activity_leave_details);

		accept_leaveTxt = (TextView) dialog.findViewById(R.id.accept_leave_txt);
		reject_leaveTxt = (TextView) dialog.findViewById(R.id.reject_leave_txt);

		statusTxt = (TextView) dialog.findViewById(R.id.status_txt);
		status_valTxt = (TextView) dialog.findViewById(R.id.status_value_txt);
		enameTxt = (TextView) dialog.findViewById(R.id.ename_txt);
		ename_valueTxt = (TextView) dialog.findViewById(R.id.ename_value_txt);
		subTxt = (TextView) dialog.findViewById(R.id.sub_txt);
		sub_val_Txt = (TextView) dialog.findViewById(R.id.sub_val_txt);
		appliedTxt = (TextView) dialog.findViewById(R.id.applied_txt);
		applied_dateTxt = (TextView) dialog.findViewById(R.id.applied_date_txt);
		fromTxt = (TextView) dialog.findViewById(R.id.from_txt);
		from_dateTxt = (TextView) dialog.findViewById(R.id.from_date_txt);
		toTxt = (TextView) dialog.findViewById(R.id.to_txt);
		to_dateTxt = (TextView) dialog.findViewById(R.id.to_date_txt);
		no_ofTxt = (TextView) dialog.findViewById(R.id.no_of_txt);
		no_of_daysTxt = (TextView) dialog.findViewById(R.id.no_of_days_txt);
		descTxt = (TextView) dialog.findViewById(R.id.desc_txt);
		desc_valTxt = (TextView) dialog.findViewById(R.id.desc_val_txt);

		approvedTxt = (TextView) dialog.findViewById(R.id.approved_txt);
		approved_valTxt = (TextView) dialog.findViewById(R.id.approved_val_txt);

		statusTxt.setTypeface(Constants.getProximanova_semibold(getActivity()));
		enameTxt.setTypeface(Constants.getProximanova_semibold(getActivity()));
		subTxt.setTypeface(Constants.getProximanova_semibold(getActivity()));
		appliedTxt
				.setTypeface(Constants.getProximanova_semibold(getActivity()));
		fromTxt.setTypeface(Constants.getProximanova_semibold(getActivity()));
		toTxt.setTypeface(Constants.getProximanova_semibold(getActivity()));
		no_ofTxt.setTypeface(Constants.getProximanova_semibold(getActivity()));

		descTxt.setTypeface(Constants.getProximanova_semibold(getActivity()));

		status_valTxt.setTypeface(Constants
				.getProximanova_regular(getActivity()));
		ename_valueTxt.setTypeface(Constants
				.getProximanova_regular(getActivity()));
		sub_val_Txt
				.setTypeface(Constants.getProximanova_regular(getActivity()));
		applied_dateTxt.setTypeface(Constants
				.getProximanova_regular(getActivity()));

		from_dateTxt.setTypeface(Constants
				.getProximanova_regular(getActivity()));
		to_dateTxt.setTypeface(Constants.getProximanova_regular(getActivity()));
		no_of_daysTxt.setTypeface(Constants
				.getProximanova_regular(getActivity()));
		desc_valTxt
				.setTypeface(Constants.getProximanova_regular(getActivity()));

		accept_leaveTxt.setTypeface(Constants
				.getProximanova_bold(getActivity()));

		reject_leaveTxt.setTypeface(Constants
				.getProximanova_bold(getActivity()));

		ename_valueTxt.setText(category.getUser_name());
		sub_val_Txt.setText(category.getSubject());
		applied_dateTxt.setText(category.getApplied_date());
		from_dateTxt.setText(category.getFrom_date());
		to_dateTxt.setText(category.getTo_date());
		no_of_daysTxt.setText(category.getNum_days());
		desc_valTxt.setText(category.getDescription());
		no_of_daysTxt.setText(category.getNum_days());

		TextView backButton = (TextView) dialog.findViewById(R.id.back_btn);
		backButton.setTypeface(Constants.getProximanova_bold(getActivity()));

		backButton.setText("Leave Details");

		backButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (!category.getType().equalsIgnoreCase("pending")) {
					// leave_status = "0";
					sendLeaveStatus();
				}

				dialog.dismiss();

			}
		});

		// if (!category.getType().equalsIgnoreCase("pending")) {
		//
		// ename_valueTxt.setText(PreferenceUtilities.getSavedUserData(
		// getActivity()).getName());
		// accept_leaveTxt.setVisibility(View.GONE);
		// reject_leaveTxt.setVisibility(View.GONE);
		//
		// appliedTxt.setText("Modified Date");
		// applied_dateTxt.setText(category.getModified());

		if (!category.getType().equalsIgnoreCase("pending")) {

			ename_valueTxt.setText(PreferenceUtilities.getSavedUserData(
					getActivity()).getName());
			accept_leaveTxt.setVisibility(View.GONE);
			reject_leaveTxt.setVisibility(View.GONE);

			appliedTxt.setText("Applied Date");
			applied_dateTxt.setText(category.getApplied_date());

			approved_valTxt.setText(category.getApproved_by_name());

			if (category.getStatus().equalsIgnoreCase("1")) {
				// accept_leaveTxt.setVisibility(View.VISIBLE);
				status_valTxt.setText("Leave Accepted");
				status_valTxt.setTextColor(getActivity().getResources()
						.getColor(R.color.light_green));
				appliedTxt.setText("Applied On");
				applied_dateTxt.setText(category.getApplied_on());
				// GradientDrawable gd = (GradientDrawable) accept_leaveTxt
				// .getBackground().getCurrent();
				// gd.setColor(getActivity().getResources().getColor(
				// R.color.light_green));

			} else if (category.getStatus().equalsIgnoreCase("2")) {

				// accept_leaveTxt.setVisibility(View.VISIBLE);
				// GradientDrawable gd = (GradientDrawable) accept_leaveTxt
				// .getBackground().getCurrent();
				// gd.setColor(Color.RED);
				status_valTxt.setTextColor(Color.RED);
				// gd.setStroke(2, Color.GREEN, 0, 0);
				appliedTxt.setText("Applied On");
				applied_dateTxt.setText(category.getApplied_on());
				status_valTxt.setText("Leave Rejected");
				approvedTxt.setText("Rejected by");

			} else {

				// accept_leaveTxt.setVisibility(View.VISIBLE);
				// GradientDrawable gd = (GradientDrawable) accept_leaveTxt
				// .getBackground().getCurrent();
				// gd.setColor(Color.BLUE);

				// GradientDrawable gd = (GradientDrawable) accept_leaveTxt
				// .getBackground().getCurrent();
				// gd.setColor(getActivity().getResources().getColor(
				// R.color.light_green));
				// gd = (GradientDrawable) reject_leaveTxt.getBackground()
				// .getCurrent();
				// gd.setColor(getActivity().getResources().getColor(
				// R.color.light_green));

				status_valTxt.setText("Not Responded");
				status_valTxt.setTextColor(Color.BLUE);

			}

			// } else {
			//
			// statusTxt.setVisibility(View.GONE);
			// status_valTxt.setVisibility(View.GONE);
			//
			// }
		} else {

			statusTxt.setVisibility(View.GONE);
			status_valTxt.setVisibility(View.GONE);

			// appliedTxt.setVisibility(View.GONE);
			// applied_dateTxt.setVisibility(View.GONE);
			approvedTxt.setVisibility(View.GONE);

		}

		accept_leaveTxt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				leave_status = "1";
				sendLeaveStatus();
			}

		});
		reject_leaveTxt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				leave_status = "2";
				sendLeaveStatus();
			}
		});

		dialog.show();
	}

	private void sendLeaveStatus() {
		// TODO Auto-generated method stub
		if (NetworkUtility.checkInternetConnection(getActivity())) {

			new LeavesStatusAsyncTask().execute();

		} else {

			DialogUtility.ShowMessage(Constants.newtWorkMsg,getActivity());

		}
	}

	public class LeavesStatusAsyncTask extends
			AsyncTask<String, String, String> {

		ProgressDialog pDialog;

		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(getActivity());
			pDialog.setMessage("Loading...");
			pDialog.setCancelable(false);
			pDialog.show();
		}

		protected String doInBackground(String... args) {

			List<NameValuePair> pairs = new ArrayList<NameValuePair>();

			// pairs.add(new BasicNameValuePair("apikey", "ETG123"));

			// $recieved_data = array
			// (
			// 'apikey' => 'ETG123',
			// 'deviceid' => '1234',
			// 'user_id' => '48', // currently logged in user id
			// 'leave_id' => '1',
			// 'status' => '2', // 1: leave approved 2: leave rejected
			// 'mgr_nf_status' => '1' //0: didn't see notification 1: manager
			// saw the notification by clicking the bubble
			//
			// );

			pairs.add(new BasicNameValuePair("deviceid", CommonUtility
					.getDeviceId(getActivity())));
			pairs.add(new BasicNameValuePair("user_id", PreferenceUtilities
					.getSavedUserData(getActivity()).getId()));
			pairs.add(new BasicNameValuePair("leave_id", category.getId()));

			if (CommonUtility.isManager.equalsIgnoreCase("1")) {
				pairs.add(new BasicNameValuePair("mgr_nf_status", "1"));
				if (!category.getType().equalsIgnoreCase("pending")) {
					pairs.add(new BasicNameValuePair("status", "0"));
				} else {
					pairs.add(new BasicNameValuePair("status", leave_status));
				}
			}

			if (!category.getApproved_by_name().equalsIgnoreCase("")) {
				pairs.add(new BasicNameValuePair("notification_status", "1"));
			}

			return WebServiceCalls.postValues(pairs, "get_leave_status")
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
						dialog.cancel();
						
						MainActivity.getDashBoardFragment();
						
						
					} else {

						DialogUtility.ShowMessage(
								jsonObject.getString("message"), getActivity());
					}

				} catch (Exception e) {
					e.printStackTrace();
					DialogUtility.ShowMessage(e.getMessage(), getActivity());
				}
			} else {
				DialogUtility.ShowMessage(
						"Unable to retrieve data from Server", getActivity());
			}
		}
	}

	/**
	 * Async task class to get json by making HTTP call
	 * */
	// private class NotificationAsyncTask extends
	// AsyncTask<String, String, String> {
	// ProgressDialog pDialog;
	//
	// @Override
	// protected void onPreExecute() {
	// super.onPreExecute();
	// // Showing progress dialog
	// pDialog = new ProgressDialog(getActivity());
	// pDialog.setMessage("Please wait...");
	// pDialog.setCancelable(false);
	// pDialog.show();
	//
	// }
	//
	// @Override
	// protected String doInBackground(String... args) {
	// // http://172.16.1.120/falcondevapp/services/get_products
	// List<NameValuePair> pairs = new ArrayList<NameValuePair>();
	//
	// pairs.add(new BasicNameValuePair("deviceid", CommonUtility
	// .getDeviceId(getActivity())));
	// // pairs.add(new BasicNameValuePair("id", PreferenceUtilities
	// // .getSavedUserData(getActivity()).getId()));
	// // pairs.add(new BasicNameValuePair("email", edtusername.getText()
	// // .toString().trim()));
	// // pairs.add(new BasicNameValuePair("password", edtPwd.getText()
	// // .toString().trim()));
	// return WebServiceCalls.postValues(pairs, "notification_types")
	// .toString();
	// }
	//
	// protected void onPostExecute(String jsonResult) {
	//
	// System.out.println(" result " + jsonResult);
	//
	// if (!TextUtils.isEmpty(jsonResult) || jsonResult.length() > 0) {
	//
	// try {
	// // JSON Node names
	// // private static final String TAG_DOCUMENTTYPE =
	// // "documenttype";
	// // private static final String TAG_ID = "id";
	// // private static final String TAG_NAME = "name";
	// // private static final String TAG_DESCRIPTION =
	// // "description";
	// categoriesList = new ArrayList<Categories>();
	//
	// JSONObject jsonObjMain = new JSONObject(jsonResult);
	//
	// System.out.println(" message "
	// + jsonObjMain.getJSONArray("notificationtype"));
	//
	// JSONArray jArray = jsonObjMain
	// .getJSONArray("notificationtype");
	//
	// for (int i = 0; i < jArray.length(); i++) {
	//
	// Categories categories = new Categories();
	//
	// categories.setId(jArray.getJSONObject(i)
	// .getString("id"));
	// categories.setName(jArray.getJSONObject(i).getString(
	// "name"));
	// categories.setDescription(jArray.getJSONObject(i)
	// .getString("description"));
	//
	// categoriesList.add(categories);
	//
	// }
	// notification_listview.setAdapter(new CustomAdapter(
	// getActivity(), categoriesList));
	// // gridView.setAdapter(new GridviewAdapter(getActivity(),
	// // categoriesList));
	//
	// } catch (Exception e) {
	// e.printStackTrace();
	// DialogUtility.ShowMessage(
	// "Unable to retrieve data from Server",
	// getActivity());
	// // Toast.makeText(getActivity(), e.getMessage(),
	// // Toast.LENGTH_SHORT).show();
	// }
	// } else {
	// DialogUtility.ShowMessage(
	// "Unable to retrieve data from Server", getActivity());
	// }
	//
	// if (null != pDialog && pDialog.isShowing()) {
	// pDialog.dismiss();
	// }
	// }
	// }

	JSONArray jArray;

	private class LeaveNotificationsAsyncTask extends
			AsyncTask<String, String, String> {
		private ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// Showing progress dialog
			pDialog = new ProgressDialog(getActivity());
			pDialog.setMessage("Please wait...");
			pDialog.setCancelable(false);
			pDialog.show();

		}

		@Override
		protected String doInBackground(String... args) {
			// http://172.16.1.120/falcondevapp/services/get_products
			List<NameValuePair> pairs = new ArrayList<NameValuePair>();

			pairs.add(new BasicNameValuePair("deviceid", CommonUtility
					.getDeviceId(getActivity())));
			pairs.add(new BasicNameValuePair("user_id", PreferenceUtilities
					.getSavedUserData(getActivity()).getId()));

			// pairs.add(new BasicNameValuePair("email", edtusername.getText()
			// .toString().trim()));
			// pairs.add(new BasicNameValuePair("password", edtPwd.getText()
			// .toString().trim()));
			// return WebServiceCalls.postValues(pairs, "notification_types")
			// .toString();

			return WebServiceCalls.postValues(pairs, "leave_notifications")
					.toString();

		}

		protected void onPostExecute(String jsonResult) {

			System.out.println(" result " + jsonResult);

			if (!TextUtils.isEmpty(jsonResult) || jsonResult.length() > 0) {

				try {

					CommonUtility.categoriesList.clear();

					// JSON Node names
					// private static final String TAG_DOCUMENTTYPE =
					// "documenttype";
					// private static final String TAG_ID = "id";
					// private static final String TAG_NAME = "name";
					// private static final String TAG_DESCRIPTION =
					// "description";

					JSONObject jsonObjMain = new JSONObject(jsonResult);

					if (jsonObjMain.getString("is_manager").equalsIgnoreCase(
							"1")) {

						System.out.println(" message "
								+ CommonUtility.getValueFromJsonObject(
										jsonObjMain,
										"total_leave_notifications"));

						String count = CommonUtility.getValueFromJsonObject(
								jsonObjMain, "total_leave_notifications");

						if (Integer.parseInt(count) != 0) {

							// = jsonObjMain
							// .getJSONArray("manager_leaves_summary");

							String mcount = CommonUtility
									.getValueFromJsonObject(jsonObjMain,
											"manager_leave_count");

							if (Integer.parseInt(mcount) != 0) {
								jArray = jsonObjMain
										.getJSONArray("manager_leaves_summary");
								for (int i = 0; i < jArray.length(); i++) {

									Categories categories = new Categories();

									JSONObject jObj = jArray.getJSONObject(i);

									categories
											.setId(CommonUtility
													.getValueFromJsonObject(
															jObj, "id"));
									categories.setUser_id(CommonUtility
											.getValueFromJsonObject(jObj,
													"user"));
									categories.setLeaveType_id(CommonUtility
											.getValueFromJsonObject(jObj,
													"leavetype_id"));
									categories.setName(CommonUtility
											.getValueFromJsonObject(jObj,
													"name"));
									categories.setUser_name(CommonUtility
											.getValueFromJsonObject(jObj,
													"user_name"));
									categories.setSubject(CommonUtility
											.getValueFromJsonObject(jObj,
													"subject"));
									categories.setDescription(CommonUtility
											.getValueFromJsonObject(jObj,
													"description"));
									categories.setApplied_date(CommonUtility
											.getValueFromJsonObject(jObj,
													"applied_date"));
									categories.setFrom_date(CommonUtility
											.getValueFromJsonObject(jObj,
													"from_date"));
									categories.setTo_date(CommonUtility
											.getValueFromJsonObject(jObj,
													"to_date"));
									categories.setNum_days(CommonUtility
											.getValueFromJsonObject(jObj,
													"num_days"));
									categories.setApplied_date(CommonUtility
											.getValueFromJsonObject(jObj,
													"applied_date"));
									categories.setApproved_by(CommonUtility
											.getValueFromJsonObject(jObj,
													"approved_by"));
									categories
											.setApproved_by_name(CommonUtility
													.getValueFromJsonObject(
															jObj,
															"approved_by_name"));
									categories.setStatus(CommonUtility
											.getValueFromJsonObject(jObj,
													"status"));
									// categories.setType("");
									CommonUtility.categoriesList
											.add(categories);
								}
							}
						}
					} else {

						// count = CommonUtility
						// .getValueFromJsonObject(jsonObjMain,
						// "leave_notifications");

						jArray = jsonObjMain
								.getJSONArray("employee_leaves_summary");
						for (int i = 0; i < jArray.length(); i++) {

							Categories categories = new Categories();

							JSONObject jObj = jArray.getJSONObject(i);

							categories.setId(CommonUtility
									.getValueFromJsonObject(jObj, "id"));
							categories.setUser_id(CommonUtility
									.getValueFromJsonObject(jObj, "user"));
							categories.setLeaveType_id(CommonUtility
									.getValueFromJsonObject(jObj,
											"leavetype_id"));
							categories.setName(CommonUtility
									.getValueFromJsonObject(jObj, "name"));
							categories.setUser_name(CommonUtility
									.getValueFromJsonObject(jObj, "user_name"));
							categories.setSubject(CommonUtility
									.getValueFromJsonObject(jObj, "subject"));
							categories
									.setDescription(CommonUtility
											.getValueFromJsonObject(jObj,
													"description"));

							categories.setFrom_date(CommonUtility
									.getValueFromJsonObject(jObj, "from_date"));
							categories.setTo_date(CommonUtility
									.getValueFromJsonObject(jObj, "to_date"));
							categories.setNum_days(CommonUtility
									.getValueFromJsonObject(jObj, "num_days"));
							categories.setApplied_date(CommonUtility
									.getValueFromJsonObject(jObj,
											"applied_date"));
							categories
									.setApplied_on(CommonUtility
											.getValueFromJsonObject(jObj,
													"applied_on"));
							categories
									.setApproved_by(CommonUtility
											.getValueFromJsonObject(jObj,
													"approved_by"));

							categories.setApproved_by_name(CommonUtility
									.getValueFromJsonObject(jObj,
											"approved_by_name"));
							categories.setStatus(CommonUtility
									.getValueFromJsonObject(jObj, "status"));
							// categories.setType("");
							CommonUtility.categoriesList.add(categories);
						}
					}

					// {
					// "id": "25",
					// "user": "37",
					// "leavetype_id": "1",
					// "name": "Sick",
					// "subject": "subject sick",
					// "from_date": "2015-09-02",
					// "to_date": "2015-09-04",
					// "num_days": "5",
					// "applied_date": "2015-09-02 08:02:00",
					// "approved_by": "1"
					// }

					// "id": "13",
					// "subject":
					// "Some random subject applied by mohan bane",
					// "description": "sick leave description",
					// "from_date": "2015-02-12",
					// "to_date": "2015-02-15",
					// "num_days": "4",
					// "status": "1",
					// "approved_by": "1",
					// "approved_by_name": "Admin",
					// "modified": "2015-09-11 14:44:23"

					jArray = jsonObjMain.getJSONArray("pending_leaves_summary");
					for (int i = 0; i < jArray.length(); i++) {

						Categories categories = new Categories();

						JSONObject jObj = jArray.getJSONObject(i);

						categories.setId(CommonUtility.getValueFromJsonObject(
								jObj, "id"));
						categories.setUser_id(CommonUtility
								.getValueFromJsonObject(jObj, "user"));
						categories.setLeaveType_id(CommonUtility
								.getValueFromJsonObject(jObj, "leavetype_id"));
						categories.setName(CommonUtility
								.getValueFromJsonObject(jObj, "name"));
						categories.setUser_name(CommonUtility
								.getValueFromJsonObject(jObj, "user_name"));
						categories.setSubject(CommonUtility
								.getValueFromJsonObject(jObj, "subject"));
						categories.setDescription(CommonUtility
								.getValueFromJsonObject(jObj, "description"));
						categories.setFrom_date(CommonUtility
								.getValueFromJsonObject(jObj, "from_date"));
						categories.setTo_date(CommonUtility
								.getValueFromJsonObject(jObj, "to_date"));
						categories.setNum_days(CommonUtility
								.getValueFromJsonObject(jObj, "num_days"));
						categories.setApplied_date(CommonUtility
								.getValueFromJsonObject(jObj, "applied_date"));
						categories.setApproved_by(CommonUtility
								.getValueFromJsonObject(jObj, "approved_by"));
						categories.setApproved_by_name(CommonUtility
								.getValueFromJsonObject(jObj,
										"approved_by_name"));
						categories.setType("pending");

						CommonUtility.categoriesList.add(categories);

					}
					// }

					notification_listview.setAdapter(new CustomAdapter(
							getActivity(), CommonUtility.categoriesList));
					// gridView.setAdapter(new GridviewAdapter(getActivity(),
					// categoriesList));

				} catch (Exception e) {
					e.printStackTrace();
					DialogUtility.ShowMessage(
							"Unable to retrieve data from Server",
							getActivity());
					// Toast.makeText(getActivity(), e.getMessage(),
					// Toast.LENGTH_SHORT).show();
				}
			} else {
				// DialogUtility.ShowMessage(
				// "Unable to retrieve data from Server", activity);
			}
			// intent = new Intent(activity, MainActivity.class);
			// intent.putExtra("reg", "");
			// intent.putExtra("notiftn_count", count);
			//
			// if (userTypeName.equalsIgnoreCase("employee")) {
			// intent.putExtra("userType", "emp");
			// } else {
			// intent.putExtra("userType", "");
			// }
			//
			// intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			// startActivity(intent);
			if (null != pDialog && pDialog.isShowing()) {
				pDialog.dismiss();
			}
		}
	}

	public class CustomAdapter extends BaseAdapter {

		Context context;
		LayoutInflater inflater = null;
		ArrayList<Categories> categoriesList;

		public CustomAdapter(Context con, ArrayList<Categories> categoriesList) {
			// TODO Auto-generated constructor stub
			this.categoriesList = categoriesList;
			context = con;
			inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return categoriesList.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		public class ViewHolder {
			TextView tv_name, tv_desc, tv_time, tv_click;
		}

		@SuppressLint("ViewHolder")
		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			// TODO Auto-generated method stub

			ViewHolder holder = new ViewHolder();

			if (convertView == null) {
				convertView = inflater.inflate(R.layout.notification_listrow,
						null);
				holder.tv_name = (TextView) convertView
						.findViewById(R.id.tv_text1);
				holder.tv_desc = (TextView) convertView
						.findViewById(R.id.tv_text2);
				holder.tv_time = (TextView) convertView
						.findViewById(R.id.tv_time);

				holder.tv_click = (TextView) convertView
						.findViewById(R.id.tv_click);

				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			holder.tv_click.setVisibility(View.GONE);
			holder.tv_time.setVisibility(View.GONE);

			holder.tv_name.setText(categoriesList.get(position).getUser_name());
			holder.tv_desc.setText(categoriesList.get(position).getSubject());

			holder.tv_name.setTypeface(Constants
					.getProximanova_regular(context));
			holder.tv_desc.setTypeface(Constants
					.getProximanova_semibold(context));

			if (!categoriesList.get(position).getType()
					.equalsIgnoreCase("Pending")) {
				holder.tv_name.setText(categoriesList.get(position)
						.getSubject());
				holder.tv_desc.setText(categoriesList.get(position)
						.getDescription());
			}

			if (value.equalsIgnoreCase("General")) {
				holder.tv_name.setText(categoriesList.get(position)
						.getUser_name());
				holder.tv_desc.setText(categoriesList.get(position)
						.getSubject());
			}

			if (value.equalsIgnoreCase("Leaves")) {

				holder.tv_time.setText(categoriesList.get(position)
						.getApplied_date());

				// holder.tv_time.setVisibility(View.VISIBLE);
				// SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
				//
				// // "2015-09-02 08:02:00
				// SimpleDateFormat format = new SimpleDateFormat(
				// "yyyy-MM-dd");
				//
				// try {
				//
				// System.out.println("Date "
				// + categoriesList.get(position).getApplied_date());
				//
				// Date dat = format.parse(categoriesList.get(position)
				// .getApplied_date());
				//
				// holder.tv_time.setText(categoriesList.get(position).getApplied_date());
				//
				// } catch (ParseException e) {
				// // TODO Auto-generated catch block
				// e.printStackTrace();
				// }
			}

			// holder.tv_time.setOnClickListener(new OnClickListener() {
			// @Override
			// public void onClick(View v) {
			// // TODO Auto-generated method stub
			// System.out
			// .println(" get position " + v.getTag().toString());
			// }
			// });

			return convertView;
		}

		OnClickListener onBtncllick = new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				System.out.println(" get position " + v.getTag().toString());
			}
		};
	}

	private void getBackFragment() {
		MainActivity.getActionBarIcons("");
		MainActivity.setActionTitle("Notifications");
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		ft.replace(R.id.frame_container, new NotificationsFragment(),
				TAG_FRAGMENT);
		ft.commit();
	}
}
