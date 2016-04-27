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
import com.falconpack.android.model.Categories;
import com.falconpack.android.webservicecall.WebServiceCalls;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class HolidayCalendarActivity extends Activity {
	ListView holiday_listview;

	TextView headerTitleTxt, dayTxt, occasionTxt;

	public static String[] day_date = { "1st", "3rd", "20th", "17th", "19th",
			"20th" };

	public static String[] day = { "Thursday", "saturday", "Thursday",
			"Friday", "Sunday", "Thursday" };

	public static String[] month = { "January", "January", "March", "July",
			"July", "July" };

	public static String[] occasian = { "New Year Day",
			"Mouloud (The Prophet's Birthday)", "March equinox",
			"Leilat al-Meiraj", "Eid-al-Fitr Holiday 1",
			"Eid-al-Fitr Holiday 2" };
	ArrayList<Categories> list;
	Context mContext;
	Activity activity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_holiday);

		mContext = getApplicationContext();
		activity = HolidayCalendarActivity.this;

		headerTitleTxt = (TextView) findViewById(R.id.tv_list_header);
		dayTxt = (TextView) findViewById(R.id.tv_day);
		occasionTxt = (TextView) findViewById(R.id.tv_occasion);

		headerTitleTxt.setTypeface(Constants
				.getProximanova_bold(getApplicationContext()));
		dayTxt.setTypeface(Constants
				.getProximanova_bold(getApplicationContext()));
		occasionTxt.setTypeface(Constants
				.getProximanova_bold(getApplicationContext()));

		holiday_listview = (ListView) findViewById(R.id.holiday_listview);

		// Button backButton = (Button) findViewById(R.id.back_btn);
		// backButton.setText("Holiday Calendar");
		// backButton.setOnClickListener(new BackListener(this));

		CommonUtility.getHeaderTitle("Holiday Calendar",
				HolidayCalendarActivity.this);

		if (NetworkUtility.checkInternetConnection(activity)) {

			new HolidayCalendarASyncTask().execute();

		} else {
			DialogUtility.ShowMessage(Constants.newtWorkMsg,activity);
		}
	}

	private class HolidayCalendarASyncTask extends
			AsyncTask<String, String, String> {

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
			// pairs.add(new BasicNameValuePair("email", edtusername.getText()
			// .toString().trim()));
			// pairs.add(new BasicNameValuePair("password", edtPwd.getText()
			// .toString().trim()));
			return WebServiceCalls.postValues(pairs, "holiday_calender")
					.toString();

		}

		protected void onPostExecute(String jsonResult) {

			System.out.println(" result " + jsonResult);

			if (!TextUtils.isEmpty(jsonResult) || jsonResult.length() > 0) {

				try {

					list = new ArrayList<Categories>();

					JSONObject jsonObjMain = new JSONObject(jsonResult);

					System.out.println(" message "
							+ jsonObjMain.getJSONArray("holiday_calender"));

					JSONArray jArray = jsonObjMain
							.getJSONArray("holiday_calender");

					for (int i = 0; i < jArray.length(); i++) {

						Categories categories = new Categories();

						categories.setOccassion(jArray.getJSONObject(i)
								.getString("occassion"));
						categories.setDay(jArray.getJSONObject(i).getString(
								"day_numeric"));

						categories.setDayName(jArray.getJSONObject(i)
								.getString("day_name"));

						categories.setMonth(jArray.getJSONObject(i).getString(
								"month"));

						categories.setYear(jArray.getJSONObject(i).getString(
								"year"));

						list.add(categories);

					}

					holiday_listview.setAdapter(new HolidayAdapter(activity,
							list));

					if (null != pDialog && pDialog.isShowing()) {
						pDialog.dismiss();
					}

				} catch (Exception e) {
					e.printStackTrace();
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

	public class HolidayAdapter extends BaseAdapter {

		String[] day_date_1;
		String[] day_1;
		String[] month_1;
		String[] occasian_1;
		Context context;
		ArrayList<Categories> categoriesList;
		LayoutInflater inflater = null;

		// public HolidayAdapter(Context con, String[] day_date, String[] day,
		// String[] month, String[] occasian) {
		// // TODO Auto-generated constructor stub
		// day_date_1 = day_date;
		// context = con;
		// day_1 = day;
		// month_1 = month;
		// occasian_1 = occasian;
		// inflater = (LayoutInflater) context
		// .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		// }

		public HolidayAdapter(Context con, ArrayList<Categories> categoriesList) {
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
		public Categories getItem(int position) {
			// TODO Auto-generated method stub
			return categoriesList.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		public class Holder {
			TextView tv_month, tv_day, tv_time, tv_daydate;

		}

		@SuppressLint("ViewHolder")
		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			// TODO Auto-generated method stub
			Holder holder = new Holder();
			View rowView;
			rowView = inflater.inflate(R.layout.row_holiday, null);
			holder.tv_month = (TextView) rowView.findViewById(R.id.tv_month);
			holder.tv_day = (TextView) rowView.findViewById(R.id.tv_day);
			holder.tv_time = (TextView) rowView.findViewById(R.id.tv_time);
			holder.tv_daydate = (TextView) rowView
					.findViewById(R.id.tv_daydate);

			holder.tv_month.setTypeface(Constants
					.getProximanova_regular(context));
			holder.tv_day
					.setTypeface(Constants.getProximanova_regular(context));
			holder.tv_time.setTypeface(Constants
					.getProximanova_regular(context));
			holder.tv_daydate.setTypeface(Constants
					.getProximanova_regular(context));

			holder.tv_day.setText(getItem(position).getDayName());
			holder.tv_month.setText(getItem(position).getMonth());
			holder.tv_time.setText(getItem(position).getOccassion());
			holder.tv_daydate.setText(getItem(position).getDay()
					+ CommonUtility.getDayNumberSuffix(Integer
							.parseInt(getItem(position).getDay())) + ",");

			return rowView;
		}

	}
}
