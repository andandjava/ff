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
import com.falconpack.android.customuiadapters.PayslipsAdapter;
import com.falconpack.android.model.Categories;
import com.falconpack.android.webservicecall.WebServiceCalls;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

public class CorporatePoliciesActivity extends Activity {

	PayslipsAdapter mPayslipsAdapter;
	ListView policiesList;
	FrameLayout searchLayout;
	EditText searchEdt;
	TextView noTxt;
	Activity activity;
	public static String[] monthsList = { "HR Policy", "Leave Policy",
			"Employee Policy" };
	ArrayList<Categories> policiesArrayList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_payslips);

		activity = CorporatePoliciesActivity.this;

		searchLayout = (FrameLayout) findViewById(R.id.search_layout);
		searchLayout.setVisibility(View.GONE);

		searchEdt = (EditText) findViewById(R.id.search_edt);
		searchEdt.setVisibility(View.GONE);

		noTxt = (TextView) findViewById(android.R.id.empty);
		noTxt.setText("No Policies Found");
		noTxt.setTypeface(Constants.getProximanova_bold(activity));

		policiesList = (ListView) findViewById(R.id.payslip_list);
		policiesList.setEmptyView(noTxt);

		CommonUtility.getHeaderTitle("Corporate Policies",
				CorporatePoliciesActivity.this);

		if (NetworkUtility.checkInternetConnection(activity)) {

			new PolicesAsyncTask().execute();

		} else {
			DialogUtility.ShowMessage(Constants.newtWorkMsg,activity);
		}
	}

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

			return WebServiceCalls.postValues(pairs, "policies").toString();

		}

		protected void onPostExecute(String jsonResult) {

			System.out.println(" result " + jsonResult);

			if (!TextUtils.isEmpty(jsonResult) || jsonResult.length() > 0) {

				try {

					policiesArrayList = new ArrayList<Categories>();

					JSONObject jsonObjMain = new JSONObject(jsonResult);

					if (!CommonUtility.getValueFromJsonObject(jsonObjMain,
							"status").equalsIgnoreCase("0")) {

						JSONArray jArray = jsonObjMain.getJSONArray("policies");

						for (int i = 0; i < jArray.length(); i++) {

							Categories categories = new Categories();

							categories.setId(jArray.getJSONObject(i).getString(
									"id"));

							categories.setName(jArray.getJSONObject(i)
									.getString("name"));

							categories.setDownload(jArray.getJSONObject(i)
									.getString("doc_path"));

							policiesArrayList.add(categories);

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

					// gridView.setAdapter(new GridviewAdapter(activity(),
					// list));

					mPayslipsAdapter = new PayslipsAdapter(activity,
							policiesArrayList, "");
					policiesList.setAdapter(mPayslipsAdapter);

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
					if (null != pDialog && pDialog.isShowing()) {
						pDialog.dismiss();
					}
				}
			} else {
				DialogUtility.ShowMessage(
						"Unable to retrieve data from Server", activity);
				if (null != pDialog && pDialog.isShowing()) {
					pDialog.dismiss();
				}
			}
		}
	}
}
