package com.falconpack.android;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import com.falconpack.android.webservicecall.WebServiceCalls;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

public class ApplyLeaveActivity extends FragmentActivity implements OnClickListener {

	EditText reportEdt, choose_fromEdt, choose_toEdt, descEdt, subjectEdt, leaveTypeEdt;
	TextView apply_leaveTxt;
	String dateStr = "", toDate, fromDate;

	final int DATE_DIALOG_ID = 0;
	final int DATE_DIALOG_ID1 = 1;

	private int pYear, tYear;
	private int pMonth, tMonth;
	private int pDay, tDay;
	DatePickerFragment date1;
	DatePickerFragmentFrom date;
	FragmentManager fm;
	Context mContext;
	Activity activity;

	int count = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_apply_leave);

		mContext = getApplicationContext();
		activity = ApplyLeaveActivity.this;

		reportEdt = (EditText) findViewById(R.id.report_edt);
		choose_fromEdt = (EditText) findViewById(R.id.choose_from_edt);
		choose_toEdt = (EditText) findViewById(R.id.choose_to_edt);
		descEdt = (EditText) findViewById(R.id.desc_edt);
		subjectEdt = (EditText) findViewById(R.id.subject_edt);
		leaveTypeEdt = (EditText) findViewById(R.id.leaveType_edt);
		leaveTypeEdt.setTag(0);

		apply_leaveTxt = (TextView) findViewById(R.id.apply_leave_txt);

		reportEdt.setTypeface(Constants.getProximanova_semibold(getApplicationContext()));
		choose_fromEdt.setTypeface(Constants.getProximanova_semibold(getApplicationContext()));
		choose_toEdt.setTypeface(Constants.getProximanova_semibold(getApplicationContext()));

		subjectEdt.setTypeface(Constants.getProximanova_regular(getApplicationContext()));
		leaveTypeEdt.setTypeface(Constants.getProximanova_regular(getApplicationContext()));
		descEdt.setTypeface(Constants.getProximanova_semibold(getApplicationContext()));

		apply_leaveTxt.setTypeface(Constants.getProximanova_bold(getApplicationContext()));

		reportEdt.setText(Html.fromHtml("Reports to : <font  color=#5A5C5C> Sr. manager </font>"));

		reportEdt.setSelection(reportEdt.getText().toString().length());

		System.out.println("Converted date is : " + dateStr);

		final Calendar cal = Calendar.getInstance();

		pYear = cal.get(Calendar.YEAR);
		pMonth = cal.get(Calendar.MONTH);
		pDay = cal.get(Calendar.DAY_OF_MONTH);

		tYear = cal.get(Calendar.YEAR);
		tMonth = cal.get(Calendar.MONTH);
		tDay = cal.get(Calendar.DAY_OF_MONTH);

		fm = getSupportFragmentManager();
		date = new DatePickerFragmentFrom();
		date1 = new DatePickerFragment();

		/**
		 * Set Up Current Date Into dialog
		 */
		Calendar calender = Calendar.getInstance();
		Bundle args = new Bundle();
		args.putInt("year", calender.get(Calendar.YEAR));
		args.putInt("month", calender.get(Calendar.MONTH));
		args.putInt("day", calender.get(Calendar.DAY_OF_MONTH));
		args.putLong("millsec", calender.getTimeInMillis());
		date.setArguments(args);
		date1.setArguments(args);

		/**
		 * Set Call back to capture selected date
		 */
		updateDisplay();

		reportEdt.setText("Reports to: " + PreferenceUtilities.getSavedUserData(activity).getMname());

		choose_fromEdt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Bundle args;

				// if (count == 0) {
				System.out.println("dialg showing zero " + date.isVisible());
				count = 1;
				if (!date.isVisible()) {
					args = new Bundle();

					args.putInt("year", pYear);
					args.putInt("month", pMonth);
					args.putInt("day", pDay);

					date.setArguments(args);

					date.setCallBack(ondateFrom);
					date.show(getSupportFragmentManager(), "Date Picker");

				} else {
					// date.dismiss();
					Log.v("date dialogue tag ", "visible");
				}
				// } else {
				// System.out.println("dialg showing " + date.isVisible());
				//
				// }
			}
		});

		choose_toEdt.setOnClickListener(this);
		apply_leaveTxt.setOnClickListener(this);
		leaveTypeEdt.setOnClickListener(this);

		if (CommonUtility.leavTypesArrayList.size() == 0) {
			if (NetworkUtility.checkInternetConnection(activity)) {
				new AllLeaveTask().execute();
			} else {
				DialogUtility.ShowFinishDialoguMessage(Constants.newtWorkMsg, activity);
			}
		}

		CommonUtility.getHeaderTitle("Apply for Leave", ApplyLeaveActivity.this);

	}

	private void updateDisplay() {

		dateStr = (pMonth + 1) + "-" + pDay + "-" + pYear;
		String date;

		if (String.valueOf((pMonth + 1)).toString().trim().length() == 1) {

			date = "0" + String.valueOf(pMonth + 1) + "-" + String.valueOf(pDay) + "-" + String.valueOf(pYear);

		} else {

			date = String.valueOf(pMonth + 1) + "-" + String.valueOf(pDay) + "-" + String.valueOf(pYear);

		}

		fromDate = date;
		toDate = date;

	}

	DateDialogFragment frag;
	Button button;
	Calendar now = Calendar.getInstance();

	@SuppressLint("NewApi")
	public void showDialog() {

		FragmentTransaction ft = getFragmentManager().beginTransaction();
		frag = DateDialogFragment.newInstance(this, new DateDialogFragmentListener() {
			public void updateChangedDate(int year, int month, int day) {

				pYear = year;
				pMonth = month;
				pDay = day;
				choose_fromEdt.setText("From " + CommonUtility.getMonthValue(
						String.valueOf(month + 1) + "-" + String.valueOf(day) + "-" + String.valueOf(year)));
				now.set(year, month, day);
			}
		}, now);
		if (!frag.isVisible()) {
			frag.show(ft, "DateDialogFragment");
		}
	}

	@SuppressLint("NewApi")
	public void showDialog1() {

		FragmentTransaction ft = getFragmentManager().beginTransaction();
		frag = DateDialogFragment.newInstance(this, new DateDialogFragmentListener() {
			public void updateChangedDate(int year, int month, int day) {

				tYear = year;
				tMonth = month;
				tDay = day;
				choose_toEdt.setText("To " + CommonUtility.getMonthValue(
						String.valueOf(month + 1) + "-" + String.valueOf(day) + "-" + String.valueOf(year)));
				now.set(year, month, day);
			}
		}, now);

		frag.show(ft, "DateDialogFragment");

	}

	public interface DateDialogFragmentListener {
		// this interface is a listener between the Date Dialog fragment and the
		// activity to update the buttons date
		public void updateChangedDate(int year, int month, int day);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Bundle args;
		switch (v.getId()) {

		case R.id.choose_from_edt:

			// showDialog(DATE_DIALOG_ID);
			// showDatePicker();

			if (!date.isVisible()) {

				args = new Bundle();

				args.putInt("year", pYear);
				args.putInt("month", pMonth);
				args.putInt("day", pDay);

				date.setArguments(args);

				date.setCallBack(ondateFrom);
				date.show(fm, "Date Picker");

				// date.setMenuVisibility(menuVisible);

			} else {

				// date.dismiss();
				Log.v("date dialogue tag ", "visible");

			}

			break;

		case R.id.choose_to_edt:

			// showDialog(DATE_DIALOG_ID1);
			// showDatePicker();

			if (choose_fromEdt.getText().toString().trim().length() > 4) {

				if (!date1.isVisible()) {

					args = new Bundle();

					args.putInt("year", tYear);
					args.putInt("month", tMonth);
					args.putInt("day", tDay);

					SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");

					try {
						dateComFrom = sdf.parse(
								String.valueOf(pMonth + 1) + "-" + String.valueOf(pDay) + "-" + String.valueOf(pYear));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					args.putLong("millsec", dateComFrom.getTime());
					date1.setArguments(args);
					date1.setCallBack(ondateTo);

					date1.show(getSupportFragmentManager(), "Date Picker");

				} else {

					Log.v("date dialogue tag ", "visible");
				}
			} else {

				DialogUtility.ShowMessage("Please select From date", activity);

			}
			break;

		case R.id.leaveType_edt:

			if (CommonUtility.leavTypesArrayList.size() > 0) {

				DialogUtility.getChooseDialogue(activity,
						CommonUtility.leavTypesArrayList.toArray(new String[CommonUtility.leavTypesArrayList.size()]),
						CommonUtility.leaveTypeIdsArray, leaveTypeEdt);

			}

			break;

		case R.id.apply_leave_txt:

			CommonUtility.hideKeyBoard(activity, v);

			if (NetworkUtility.checkInternetConnection(activity)) {

				if (!leaveTypeEdt.getText().toString().trim().equalsIgnoreCase("Leave Type")) {

					if (choose_fromEdt.getText().toString().length() < 4
							&& choose_toEdt.getText().toString().length() < 3) {

						DialogUtility.ShowMessage("Please enter leave From and To dates ", activity);

					} else if (choose_fromEdt.getText().toString().length() < 5
							|| choose_toEdt.getText().toString().length() < 3) {

						if (choose_fromEdt.getText().toString().length() < 5) {
							DialogUtility.ShowMessage("Please enter From date ", activity);
						} else if (choose_toEdt.getText().toString().length() < 3) {
							DialogUtility.ShowMessage("Please enter To date ", activity);
						}
					} else if (descEdt.getText().toString().trim().length() != 0) {

						if (subjectEdt.getText().toString().trim().length() != 0) {

							new ApplyLeaveTask().execute();

						} else {

							AlertDialog.Builder builder = new AlertDialog.Builder(activity);
							builder.setMessage("Subject is empty do you want to proceed?").setCancelable(false)
									.setPositiveButton("OK", new DialogInterface.OnClickListener() {
										@Override
										public void onClick(DialogInterface dialog, int id) {
											dialog.dismiss();
											new ApplyLeaveTask().execute();
										}
									}).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
										@Override
										public void onClick(DialogInterface dialog, int id) {
											dialog.dismiss();
										}
									});
							AlertDialog alert = builder.create();
							alert.show();
						}

					} else {

						DialogUtility.ShowMessage("Please enter Description", activity);
					}

				} else {

					DialogUtility.ShowMessage("Please select Leave Type", activity);
				}
			} else {

				DialogUtility.ShowMessage(Constants.newtWorkMsg, activity);

			}
			break;

		default:
			break;

		}
	}

	OnDateSetListener ondateTo = new OnDateSetListener() {
		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

			SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");

			try {

				dateComFrom = sdf
						.parse(String.valueOf(pMonth) + "-" + String.valueOf(pDay) + "-" + String.valueOf(pYear));

				dateComTo = sdf.parse(monthOfYear + "-" + dayOfMonth + "-" + year);

			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			System.out.println(sdf.format(dateComFrom));
			System.out.println(sdf.format(dateComTo));

			if (dateComFrom.before(dateComTo) || dateComFrom.compareTo(dateComTo) == 0
					|| dateComFrom.compareTo(dateComTo) < 0) {

				System.out.println("Date1 is before Date2");

				tYear = year;
				tMonth = monthOfYear;
				tDay = dayOfMonth;

				String mon = String.valueOf(monthOfYear + 1);

				toDate = CommonUtility.getMonthValue(mon) + "-" + String.valueOf(dayOfMonth) + "-"
						+ String.valueOf(year);

				choose_toEdt.setText("To " + toDate);

			} else {

				DialogUtility.ShowMessage(" From  date is after To date, so please select proper date. ",
						activity);

			}

		}

	};

	Date dateComFrom, dateComTo;

	OnDateSetListener ondateFrom = new OnDateSetListener() {
		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
			SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
			if (choose_toEdt.getText().toString().trim().length() == 2) {

				pYear = year;
				pMonth = monthOfYear;
				pDay = dayOfMonth;

				String mon = String.valueOf(monthOfYear + 1);

				fromDate = CommonUtility.getMonthValue(mon) + "-" + String.valueOf(dayOfMonth) + "-"
						+ String.valueOf(year);

				choose_fromEdt.setText("From " + fromDate);
				try {
					dateComFrom = sdf.parse(String.valueOf(tMonth).trim() + "-" + String.valueOf(tDay).trim() + "-"
							+ String.valueOf(tYear).trim());
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {

				try {

					dateComFrom = sdf.parse(String.valueOf(tMonth).trim() + "-" + String.valueOf(tDay).trim() + "-"
							+ String.valueOf(tYear).trim());
					dateComTo = sdf.parse(String.valueOf(monthOfYear).trim() + "-" + String.valueOf(dayOfMonth).trim()
							+ "-" + String.valueOf(year).trim());

				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				System.out.println(sdf.format(dateComFrom));
				System.out.println(sdf.format(dateComTo));

				// if (dateComFrom.compareTo(dateComTo) > 0) {
				// System.out.println("Date1 is after Date2");
				// } else

				if (dateComFrom.after(dateComTo) || dateComFrom.compareTo(dateComTo) == 0
						|| dateComFrom.compareTo(dateComTo) > 0) {

					System.out.println("Date1 is before Date2");

					pYear = year;
					pMonth = monthOfYear;
					pDay = dayOfMonth;

					String mon = String.valueOf(monthOfYear + 1).trim();

					fromDate = CommonUtility.getMonthValue(mon).trim() + "-" + String.valueOf(dayOfMonth).trim() + "-"
							+ String.valueOf(year).trim();

					choose_fromEdt.setText("From " + fromDate);

				} else {

					DialogUtility.ShowMessage(" From  date is after To date, so please select proper date. ",
							activity);

				}

			}
			// else if (dateComFrom.compareTo(dateComTo) == 0) {
			// System.out.println("Date1 is equal to Date2");
			// } else {
			// System.out.println("How to get here?");
			// }

		}
	};

	public class ApplyLeaveTask extends AsyncTask<String, String, String> {

		ProgressDialog pDialog;

		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(activity);
			pDialog.setMessage("Applying for leave...");
			pDialog.setCancelable(false);
			pDialog.show();
		};

		protected String doInBackground(String... args) {
			List<NameValuePair> pairs = new ArrayList<NameValuePair>();

			String leaveType_id = leaveTypeEdt.getTag().toString().trim();
			String subject = subjectEdt.getText().toString().trim();
			String desc = descEdt.getText().toString().trim();

			pairs.add(new BasicNameValuePair("deviceid", CommonUtility.getDeviceId(activity)));
			pairs.add(new BasicNameValuePair("id", PreferenceUtilities.getSavedUserData(activity).getId()));
			pairs.add(new BasicNameValuePair("leavetype_id", leaveType_id));
			pairs.add(new BasicNameValuePair("subject", subject));
			pairs.add(new BasicNameValuePair("description", desc));
			pairs.add(new BasicNameValuePair("from_date",
					choose_fromEdt.getText().toString().substring(4).toString().trim()));
			pairs.add(new BasicNameValuePair("to_date",
					choose_toEdt.getText().toString().substring(2).toString().trim()));
			pairs.add(new BasicNameValuePair("report_to", PreferenceUtilities.getSavedUserData(activity).getMid()));

			return WebServiceCalls.postValues(pairs, "apply_leave").toString();
		}

		protected void onPostExecute(String jsonResult) {

			System.out.println(" result " + jsonResult);

			if (null != pDialog && pDialog.isShowing()) {
				pDialog.dismiss();
			}

			if (!TextUtils.isEmpty(jsonResult) || jsonResult.length() > 0) {

				try {

					// {"errorCode":2,"errorMessage":"Error occur while updating
					// venues"}

					JSONObject jsonObjMain = new JSONObject(jsonResult);

					DialogUtility.ShowResultMessage(activity,
							CommonUtility.getValueFromJsonObject(jsonObjMain, "status"),
							CommonUtility.getValueFromJsonObject(jsonObjMain, "message"));

				} catch (Exception e) {

					e.printStackTrace();

					// Toast.makeText(mContext, e.getMessage(),
					// Toast.LENGTH_SHORT)
					// .show();

				}

			} else {

				DialogUtility.ShowMessage("Unable to retrieve data from Server", activity);

			}
		}
	}

	public class AllLeaveTask extends AsyncTask<String, String, String> {

		ProgressDialog pDialog;

		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(activity);
			pDialog.setMessage("Applying for leave...");
			pDialog.setCancelable(false);
			pDialog.show();
		}

		protected String doInBackground(String... args) {
			
			List<NameValuePair> pairs = new ArrayList<NameValuePair>();
			pairs.add(new BasicNameValuePair("user_id", PreferenceUtilities.getSavedUserData(activity).getId()));

			return WebServiceCalls.postValues(pairs, "all_leavetypes").toString();
		}

		protected void onPostExecute(String jsonResult) {

			System.out.println(" result " + jsonResult);

			if (null != pDialog && pDialog.isShowing()) {
				pDialog.dismiss();
			}

			if (!TextUtils.isEmpty(jsonResult) || jsonResult.length() > 0) {
				try {
					// [{"id":"1","name":"Sick"},{"id":"2","name":"Casual"},{"id":"3","name":"Annual"},{"id":"4","name":"Others"}]
					JSONArray jsonArray = new JSONArray(jsonResult);
					CommonUtility.leaveTypeIdsArray = new String[jsonArray.length()];
					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject jsonObject = jsonArray.getJSONObject(i);
						System.out.println(
								" id and name " + jsonObject.getString("id") + " : " + jsonObject.getString("name"));
						CommonUtility.leaveTypeIdsArray[i] = jsonObject.getString("id");
						CommonUtility.leavTypesArrayList.add(jsonObject.getString("name"));
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				DialogUtility.ShowMessage("Unable to retrieve data from Server", activity);
			}
		}
	}
}
