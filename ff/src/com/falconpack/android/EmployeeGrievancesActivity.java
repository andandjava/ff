package com.falconpack.android;

import java.util.ArrayList;
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
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

public class EmployeeGrievancesActivity extends Activity {

	EditText subjectEdt, descriptionEdt;
	TextView submitTxt;

	Context mContext;
	Activity activity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_emp_grievance);

		mContext = getApplicationContext();
		activity = EmployeeGrievancesActivity.this;

		subjectEdt = (EditText) findViewById(R.id.sub_edt);
		descriptionEdt = (EditText) findViewById(R.id.desc_edt);
		descriptionEdt = (EditText) findViewById(R.id.desc_edt);
		submitTxt = (TextView) findViewById(R.id.submit_txt);

		subjectEdt.setTypeface(Constants
				.getProximanova_regular(getApplicationContext()));
		descriptionEdt.setTypeface(Constants
				.getProximanova_regular(getApplicationContext()));
		submitTxt.setTypeface(Constants
				.getProximanova_bold(getApplicationContext()));

		CommonUtility.getHeaderTitle("Employee Grievances",
				EmployeeGrievancesActivity.this);

		submitTxt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (NetworkUtility.checkInternetConnection(activity)) {

					if (subjectEdt.getText().toString().trim().length() <= 0
							&& descriptionEdt.getText().toString().trim()
									.length() <= 0) {

						DialogUtility.ShowMessage(
								"please enter all the fields", activity);
					} else if (subjectEdt.getText().toString().trim().length() <= 0) {

						DialogUtility.ShowMessage("please enter Subject",
								activity);
					} else if (descriptionEdt.getText().toString().trim()
							.length() <= 0) {
						DialogUtility.ShowMessage("please enter Description",
								activity);
					} else {
						InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
						imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
						new EmpGrievanceASyncTask().execute();
					}

				} else {

					DialogUtility.ShowMessage(Constants.newtWorkMsg,activity);

				}
			}
		});

	}

	private class EmpGrievanceASyncTask extends
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
			pairs.add(new BasicNameValuePair("user_id", PreferenceUtilities
					.getSavedUserData(activity).getId()));

			// 'user_id' => '45',
			// 'subject'=> 'some random subject',
			// 'description' => 'some random description for grievance'

			pairs.add(new BasicNameValuePair("subject", subjectEdt.getText()
					.toString().trim()));
			pairs.add(new BasicNameValuePair("description", descriptionEdt
					.getText().toString().trim()));
			return WebServiceCalls.postValues(pairs, "get_employee_grievance")
					.toString();
		}

		protected void onPostExecute(String jsonResult) {

			System.out.println(" result " + jsonResult);

			if (!TextUtils.isEmpty(jsonResult) || jsonResult.length() > 0) {

				try {

					JSONObject jsonObjMain = new JSONObject(jsonResult);

					// gridView.setAdapter(new GridviewAdapter(getActivity(),
					// list));

					DialogUtility.ShowResultMessage(activity, CommonUtility
							.getValueFromJsonObject(jsonObjMain, "status"),
							CommonUtility.getValueFromJsonObject(jsonObjMain,
									"message"));

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
}
