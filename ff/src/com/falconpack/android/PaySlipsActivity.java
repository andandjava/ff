package com.falconpack.android;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
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
import com.falconpack.android.customuiadapters.PayslipsAdapter;
import com.falconpack.android.model.Categories;
import com.falconpack.android.webservicecall.WebServiceCalls;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


public class PaySlipsActivity extends FragmentActivity {

	PayslipsAdapter mPayslipsAdapter;
	ListView pay_slipsListView;
	TextView noTxt;
	EditText searchEdt;
	ImageView search_calImg;
	static final int DATE_DIALOG_ID = 1;
	private int mYear;
	private int mMonth;
	private int mDay;
	ArrayList<Categories> payslipsList;

	private String[] monthsList = { "January", "February", "March", "April",
			"May", "June", "July", "August", "September", "October",
			"November", "December" };

	String[] monthvalues = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul",
			"Aug", "Sep", "Oct", "Nov", "Dec" };
	private int min_year;
	private int max_year;
	private int cur_mon, cur_year;
	private int pre_mon;
	private int next_mon;

	DatePickerFragment date;
	Context mContext;
	Activity activity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_payslips);

		mContext = getApplicationContext();
		activity = PaySlipsActivity.this;

		pay_slipsListView = (ListView) findViewById(R.id.payslip_list);

		noTxt = (TextView) findViewById(android.R.id.empty);

		noTxt.setTypeface(Constants.getProximanova_bold(activity));

		pay_slipsListView.setEmptyView(noTxt);

		searchEdt = (EditText) findViewById(R.id.search_edt);
		searchEdt.setTypeface(Constants
				.getProximanova_regular(getApplicationContext()));

		search_calImg = (ImageView) findViewById(R.id.search_cal_img);

		final Calendar cal = Calendar.getInstance();
		max_year = cal.get(Calendar.YEAR);
		min_year = max_year - 5;
		cur_mon = cal.get(Calendar.MONTH);
		cur_year = cal.get(Calendar.YEAR);
		searchEdt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				// date = new DatePickerFragment();
				// /**
				// * Set Up Current Date Into dialog
				// */
				// Calendar calender = Calendar.getInstance();
				// mYear = calender.get(Calendar.YEAR);
				// mMonth = calender.get(Calendar.MONTH);
				// Bundle args = new Bundle();
				// args.putInt("year", calender.get(Calendar.YEAR));
				// args.putInt("month", calender.get(Calendar.MONTH));
				// args.putInt("day", calender.get(Calendar.DAY_OF_MONTH));
				// date.setArguments(args);
				// date.setCallBack(ondateMonth);
				// date.show(getSupportFragmentManager(), "Date Picker");

				// showDialog(DATE_DIALOG_ID);

				final Dialog dialog = new Dialog(activity);
				dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
				dialog.setContentView(R.layout.custom);

				// Toast.makeText(activity, "maximum year" + max_year,
				// 1).show();
				// Toast.makeText(activity, "minimum year" + min_year,
				// 1).show();
				// Toast.makeText(activity,
				// "current year" + cal.get(Calendar.YEAR), 1).show();
				// Toast.makeText(activity,
				// "current month" + cal.get(Calendar.MONTH), 1).show();

				final TextView dialogTitle = (TextView) dialog
						.findViewById(R.id.dialog_title);
				final TextView subyear = (TextView) dialog
						.findViewById(R.id.subyear);
				final TextView addyear = (TextView) dialog
						.findViewById(R.id.addyear);
				final TextView submonth = (TextView) dialog
						.findViewById(R.id.submonth);
				final TextView addmonth = (TextView) dialog
						.findViewById(R.id.addmonth);
				final TextView month = (TextView) dialog
						.findViewById(R.id.month);
				final TextView year = (TextView) dialog.findViewById(R.id.year);

				Button dialogButton = (Button) dialog
						.findViewById(R.id.dialogButtonOK);
				Button dialogCancel = (Button) dialog
						.findViewById(R.id.dialogButtonCancel);

				dialogTitle.setTypeface(Constants
						.getProximanova_semibold(mContext));
				subyear.setTypeface(Constants.getProximanova_regular(mContext));
				addyear.setTypeface(Constants.getProximanova_regular(mContext));
				submonth.setTypeface(Constants.getProximanova_regular(mContext));
				addmonth.setTypeface(Constants.getProximanova_regular(mContext));
				month.setTypeface(Constants.getProximanova_semibold(mContext));
				year.setTypeface(Constants.getProximanova_semibold(mContext));

				dialogButton.setTypeface(Constants
						.getProximanova_regular(mContext));
				dialogCancel.setTypeface(Constants
						.getProximanova_regular(mContext));

				// int n = cal.get(Calendar.YEAR);

				year.setText("" + cur_year);
				cur_year = cur_year - 1;
				subyear.setText("" + cur_year);
				addyear.setText("" + min_year);

				// month.setText(""+cal.get(Calendar.MONTH));
				// int m = cal.get(Calendar.MONTH);

				month.setText("" + monthvalues[cur_mon]);
				next_mon = cur_mon + 1;

				if (next_mon > 11) {
					next_mon = next_mon - 12;
					addmonth.setText("" + monthvalues[next_mon]);
				} else {
					addmonth.setText("" + monthvalues[next_mon]);
				}

				pre_mon = cur_mon - 1;
				if (pre_mon < 0) {
					pre_mon = pre_mon + 12;
					submonth.setText("" + monthvalues[pre_mon]);
				} else {

					submonth.setText("" + monthvalues[pre_mon]);
				}
				// cur_mon = cal.get(Calendar.MONTH);
				// addyear.setVisibility(View.INVISIBLE);

				subyear.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						String s1 = year.getText().toString();
						addyear.setText(s1);
						String s = subyear.getText().toString();
						year.setText(s);
						int n = Integer.parseInt(s);
						int k = n - 1;
						// addyear.setVisibility(View.VISIBLE);
						if (k >= min_year) {
							n = k;
							subyear.setText("" + k);
							// Toast.makeText(MainActivity.this, "int value"+k,
							// 1).show();
						} else {

							subyear.setText("" + max_year);
							// subyear.setVisibility(View.INVISIBLE);
							// Toast.makeText(activity,
							// "It reached the minimum value", 1).show();
						}
					}
				});

				addyear.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						String s1 = year.getText().toString();
						subyear.setText(s1);
						String s = addyear.getText().toString();
						year.setText(s);
						int n = Integer.parseInt(s);
						int k = n + 1;
						// subyear.setVisibility(View.VISIBLE);
						if (k <= max_year) {
							n = k;
							addyear.setText("" + k);

							// Toast.makeText(MainActivity.this,
							// "incremented year value"+k, 1).show();
						} else {
							n = min_year;
							addyear.setText("" + n);
							// addyear.setVisibility(View.INVISIBLE);
							// Toast.makeText(activity,
							// "It reached the maximum value", 1).show();
						}
					}
				});

				submonth.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						String s = month.getText().toString();
						addmonth.setText("" + s);
						String s1 = submonth.getText().toString();
						month.setText("" + s1);
						// int m=Integer.parseInt(s1);
						// int m=cur_mon+1;
						// int k=next_mon+1;
						int k = pre_mon - 1;
						next_mon = pre_mon + 1;
						if (k < 0) {
							pre_mon = k + 12;
							// month.setText(""+m);
							submonth.setText("" + monthvalues[pre_mon]);
							// Toast.makeText(activity,
							// "decremented  month value" + pre_mon, 1)
							// .show();
						} else {
							pre_mon = k;
							// cur_mon=m;
							// month.setText(""+m);
							submonth.setText("" + monthvalues[pre_mon]);
						}
					}
				});

				addmonth.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						String s = month.getText().toString();
						submonth.setText("" + s);
						String s1 = addmonth.getText().toString();
						month.setText("" + s1);
						// int m=Integer.parseInt(s1);
						// int m=cur_mon+1;
						int k = next_mon + 1;
						pre_mon = next_mon - 1;
						if (k > 11) {
							next_mon = k - 12;
							// month.setText(""+m);
							addmonth.setText("" + monthvalues[next_mon]);
							// Toast.makeText(activity,
							// "incremented  month value" + next_mon, 1)
							// .show();
						} else {
							next_mon = k;
							// cur_mon=m;
							// month.setText(""+m);
							addmonth.setText("" + monthvalues[next_mon]);
						}
					}
				});

				// if button is clicked, close the custom dialog
				dialogButton.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						// Toast.makeText(activity, "yes", 1).show();
						// Toast.makeText(MainActivity.this, "year", 1).show();

						// monthsList

						searchEdt.setText(monthsList[Arrays.asList(monthvalues)
								.indexOf(month.getText().toString())]
								+ " "
								+ year.getText().toString());

						cur_year = Integer.parseInt(year.getText().toString());
						cur_mon = Arrays.asList(monthvalues).indexOf(
								month.getText().toString());

						// monthval.setText("month is:"
						// + month.getText().toString());
						dialog.dismiss();
					}
				});

				// if button is clicked, close the custom dialog
				dialogCancel.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						// Toast.makeText(activity, "no", 1).show();
						dialog.dismiss();
					}
				});
				dialog.show();
			}
		});

		searchEdt.addTextChangedListener(new TextWatcher() {

			public void afterTextChanged(Editable s) {
			}

			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			public void onTextChanged(CharSequence s, int start, int before,
					int count) {

				ArrayList<Categories> cat = new ArrayList<Categories>();

				for (int i = 0; i < payslipsList.size(); i++) {

					if (payslipsList.get(i).getText_comb()
							.contains(s.toString())) {

						cat.add(payslipsList.get(i));
					}
				}

				mPayslipsAdapter = new PayslipsAdapter(activity, cat, "pay");
				pay_slipsListView.setAdapter(mPayslipsAdapter);

				// mPayslipsAdapter.getFilter().filter(s.toString());
				// System.out.println(" count "+mPayslipsAdapter.getCount());

			}
		});

		if (NetworkUtility.checkInternetConnection(activity)) {

			// new PaySlipSyncTask().execute();

			new PolicesAsyncTask().execute();

		} else {

			DialogUtility.ShowMessage(Constants.newtWorkMsg,activity);

		}

		// Button backButton = (Button) findViewById(R.id.back_btn);
		// backButton.setText("Payslips");
		// backButton.setOnClickListener(new BackListener(this));
		CommonUtility.getHeaderTitle("Payslips", PaySlipsActivity.this);
	}

	DatePickerDialog.OnDateSetListener mDateSetListner = new OnDateSetListener() {

		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			mYear = year;
			mMonth = monthOfYear;
			mDay = dayOfMonth;
			updateDate();
		}
	};

	@Override
	protected Dialog onCreateDialog(int id) {

		switch (id) {

		case DATE_DIALOG_ID:

			/*
			 * return new DatePickerDialog(this, mDateSetListner, mYear, mMonth,
			 * mDay);
			 */

			DatePickerDialog datePickerDialog = this.customDatePicker();
			// android:calendarViewShown="false"
			datePickerDialog.getDatePicker().setSpinnersShown(true);
			datePickerDialog.getDatePicker().setCalendarViewShown(false);

			return datePickerDialog;
		}
		return null;
	}

	protected void updateDate() {
//		int localMonth = (mMonth + 1);
		Integer.toString(mYear).substring(2);
		searchEdt.setText(monthsList[mMonth] + " " + mYear);
		showDialog(DATE_DIALOG_ID);
	}

	private DatePickerDialog customDatePicker() {
		DatePickerDialog dpd = new DatePickerDialog(this, mDateSetListner,
				mYear, mMonth, mDay);
		try {

			Calendar c = Calendar.getInstance();
			dpd.getDatePicker().setMaxDate(c.getTimeInMillis());
			c.add(Calendar.YEAR, -4); // to get previous year add -1
			c.getTime();
			dpd.getDatePicker().setMinDate(c.getTimeInMillis());

			Field[] datePickerDialogFields = dpd.getClass().getDeclaredFields();

			for (java.lang.reflect.Field datePickerDialogField : datePickerDialogFields) {

				if (datePickerDialogField.getName().equals("mDatePicker")) {

					datePickerDialogField.setAccessible(true);

					DatePicker datePicker = (DatePicker) datePickerDialogField
							.get(dpd);

					Field datePickerFields[] = datePickerDialogField.getType()
							.getDeclaredFields();

					for (Field datePickerField : datePickerFields) {

						if ("mDayPicker".equals(datePickerField.getName())
								|| "mDaySpinner".equals(datePickerField
										.getName())) {
							datePickerField.setAccessible(true);
							Object dayPicker = new Object();
							dayPicker = datePickerField.get(datePicker);
							((View) dayPicker).setVisibility(View.GONE);
						}
					}
				}
			}
		} catch (Exception ex) {
		}
		return dpd;
	}

	// OnDateSetListener ondateMonth = new OnDateSetListener() {
	// @Override
	// public void onDateSet(DatePicker view, int year, int monthOfYear,
	// int dayOfMonth) {
	//
	// searchEdt.setText("" + monthsList[monthOfYear] + " "+year);
	//
	// // Toast.makeText(
	// // ApplyLeaveActivity.this,
	// // String.valueOf(dayOfMonth) + "-"
	// // + String.valueOf(monthOfYear) + "-"
	// // + String.valueOf(year), Toast.LENGTH_LONG).show();
	// }
	// };

	private class PolicesAsyncTask extends AsyncTask<String, String, String> {

		private ProgressDialog pDialog;

		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(activity);
			pDialog.setMessage("Loading ...");
			pDialog.setCancelable(false);
			pDialog.show();
		}

		protected String doInBackground(String... args) {
			// http://172.16.1.120/falcondevapp/services/get_products
			List<NameValuePair> pairs = new ArrayList<NameValuePair>();

			// pairs.add(new BasicNameValuePair("apikey", "ETG123"));
			pairs.add(new BasicNameValuePair("deviceid", CommonUtility
					.getDeviceId(activity)));
			pairs.add(new BasicNameValuePair("user_id", PreferenceUtilities
					.getSavedUserData(activity).getId()));

			return WebServiceCalls.postValues(pairs, "payslips").toString();

		}

		protected void onPostExecute(String jsonResult) {

			System.out.println(" result " + jsonResult);

			if (!TextUtils.isEmpty(jsonResult) || jsonResult.length() > 0) {

				try {

					payslipsList = new ArrayList<Categories>();

					JSONObject jsonObjMain = new JSONObject(jsonResult);
					if (CommonUtility.getValueFromJsonObject(jsonObjMain,
							"status").equalsIgnoreCase("1")) {
						System.out.println(" message "
								+ jsonObjMain.getJSONArray("payslips"));

						JSONArray jArray = jsonObjMain.getJSONArray("payslips");

						for (int i = 0; i < jArray.length(); i++) {

							Categories categories = new Categories();

							categories.setId(jArray.getJSONObject(i).getString(
									"id"));
							// categories.setMonth(jArray.getJSONObject(i)
							// .getString("month"));
							//
							// categories.setYear(jArray.getJSONObject(i)
							// .getString("year"));
							categories.setName(jArray.getJSONObject(i)
									.getString("user_name"));

							categories.setDownload(jArray.getJSONObject(i)
									.getString("doc_path"));

							categories
									.setText_comb(jArray.getJSONObject(i)
											.getString("month")
											+ " "
											+ jArray.getJSONObject(i)
													.getString("year"));

							payslipsList.add(categories);

						}
					} else if (CommonUtility.getValueFromJsonObject(
							jsonObjMain, "status").equalsIgnoreCase("0")) {

						DialogUtility.ShowMessage(
								jsonObjMain.getString("message"), activity);

					} else {
						DialogUtility
								.ShowMessage(
										"Unable to retrieve data from Server",
										activity);

					}

					mPayslipsAdapter = new PayslipsAdapter(activity,
							payslipsList, "pay");
					pay_slipsListView.setAdapter(mPayslipsAdapter);

					if (null != pDialog && pDialog.isShowing()) {
						pDialog.dismiss();
					}
				} catch (Exception e) {
					e.printStackTrace();
					DialogUtility.ShowMessage(
							"Unable to retrieve data from Server", activity);
					// Toast.makeText(activity, e.getMessage(),
					// Toast.LENGTH_SHORT)
					// .show();
				}
			} else {
				DialogUtility.ShowMessage(
						"Unable to retrieve data from Server", activity);
			}
		}
	}
}
