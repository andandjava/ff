package com.falconpack.android;

import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
import com.falconpack.android.RegistrationActivity.UserTypeAsyncTask;
import com.falconpack.android.common.CommonUtility;
import com.falconpack.android.common.Constants;
import com.falconpack.android.common.DialogUtility;
import com.falconpack.android.common.NetworkUtility;
import com.falconpack.android.common.PreferenceUtilities;
import com.falconpack.android.webservicecall.WebServiceCalls;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends Activity implements OnClickListener {

	Button signinButton;
	TextView signupTxt, forgotPwdTxt;
	EditText edtusername, edtPwd;
	EditText edtEmail;
	CheckBox rememberChk;
	String username, password;
	Intent intent;

	Context mContext;
	Activity activity;
	String userTypeName, count = "0";
	private ProgressDialog pDialog;
	Dialog customDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		mContext = LoginActivity.this;
		activity = LoginActivity.this;
		edtusername = (EditText) findViewById(R.id.editEmail);
		edtPwd = (EditText) findViewById(R.id.editPassword);

		signinButton = (Button) findViewById(R.id.signin_btn);
		signupTxt = (TextView) findViewById(R.id.textSignup);

		rememberChk = (CheckBox) findViewById(R.id.rememberChk);
		forgotPwdTxt = (TextView) findViewById(R.id.textForgotPassword);

		if (PreferenceUtilities.getSavedUserData(activity) != null) {

			if (Boolean.valueOf(PreferenceUtilities.getSavedUserData(activity).getIsRemember()) == true) {
				edtusername.setText(PreferenceUtilities.getSavedUserData(activity).getUsername());
				edtPwd.setText(PreferenceUtilities.getSavedUserData(activity).getPassword());
				rememberChk.setChecked(Boolean.valueOf(PreferenceUtilities.getSavedUserData(activity).getIsRemember()));

				edtusername.setSelection(edtusername.getText().toString().length());
			}
		}

		edtusername.setTypeface(Constants.getProximanova_regular(mContext));
		edtPwd.setTypeface(Constants.getProximanova_regular(mContext));
		signinButton.setTypeface(Constants.getProximanova_bold(mContext));
		signupTxt.setTypeface(Constants.getProximanova_semibold(mContext));
		rememberChk.setTypeface(Constants.getProximanova_regular(mContext));
		forgotPwdTxt.setTypeface(Constants.getProximanova_regular(mContext));

		forgotPwdTxt.setOnClickListener(this);
		signinButton.setOnClickListener(this);
		signupTxt.setOnClickListener(this);

	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub

		switch (view.getId()) {

		case R.id.signin_btn:

			username = edtusername.getText().toString().trim().replace(" ", "");
			password = edtPwd.getText().toString().trim();

			if (TextUtils.isEmpty(username)) {
				DialogUtility.ShowMessage("Please enter email address", activity);
			} else if (TextUtils.isEmpty(password)) {
				DialogUtility.ShowMessage("Please enter password", activity);
			} else {
				if (CommonUtility.eMailValidation(username)) {
					if (NetworkUtility.checkInternetConnection(activity)) {
						InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
						imm.hideSoftInputFromWindow(edtPwd.getWindowToken(), 0);
						new LoginASyncTask().execute();
					} else {
						DialogUtility.ShowMessage(Constants.newtWorkMsg, activity);
					}
				} else {
					DialogUtility.ShowMessage("Please enter valid email address", activity);
				}
			}

			break;

		case R.id.textSignup:

			if (NetworkUtility.checkInternetConnection(mContext)) {
				intent = new Intent(this, RegistrationActivity.class);
				intent.putExtra("reg", "reg");
				startActivity(intent);
			} else {
				DialogUtility.ShowMessage(Constants.newtWorkMsg, activity);
			}

			break;

		case R.id.textForgotPassword:

			customDialog = new Dialog(activity, android.R.style.Theme_Translucent_NoTitleBar);
			customDialog.setCancelable(true);
			customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			customDialog.setContentView(R.layout.dialog_forgotpwd);

			edtEmail = (EditText) customDialog.findViewById(R.id.editEmail);
			TextView tv_head = (TextView) customDialog.findViewById(R.id.tv_head);
			Button submit_btn = (Button) customDialog.findViewById(R.id.submit_btn);

			tv_head.setTypeface(Constants.getProximanova_regular(activity));
			submit_btn.setTypeface(Constants.getProximanova_bold(activity));
			edtEmail.setTypeface(Constants.getProximanova_regular(activity));

			TextView backButton = (TextView) customDialog.findViewById(R.id.back_btn);
			backButton.setText("Forgot Password");
			backButton.setTypeface(Constants.getProximanova_bold(activity));
			backButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					customDialog.dismiss();
				}
			});

			submit_btn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					String email = edtEmail.getText().toString().trim();
					if (TextUtils.isEmpty(email)) {
						DialogUtility.ShowMessage("Please enter email address", activity);
					} else {
						if (CommonUtility.eMailValidation(email)) {
							if (NetworkUtility.checkInternetConnection(activity)) {
								InputMethodManager imm = (InputMethodManager) getSystemService(
										Context.INPUT_METHOD_SERVICE);
								imm.hideSoftInputFromWindow(edtPwd.getWindowToken(), 0);
								new ForgotASyncTask().execute();
							} else {
								DialogUtility.ShowMessage(Constants.newtWorkMsg, activity);
							}
						} else {
							DialogUtility.ShowMessage("Please enter valid email address", activity);
						}
					}

				}
			});

			customDialog.show();

			break;

		default:
			break;
		}
	}

	private class LoginASyncTask extends AsyncTask<String, String, String> {

		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(mContext);
			pDialog.setMessage("Signin ...");
			pDialog.setCancelable(false);
			pDialog.show();
		}

		protected String doInBackground(String... args) {

			List<NameValuePair> pairs = new ArrayList<NameValuePair>();

			// pairs.add(new BasicNameValuePair("apikey", "ETG123"));
			pairs.add(new BasicNameValuePair("deviceid", CommonUtility.getDeviceId(activity)));
			pairs.add(new BasicNameValuePair("email", edtusername.getText().toString().trim()));
			pairs.add(new BasicNameValuePair("password", edtPwd.getText().toString().trim()));
			return WebServiceCalls.postValues(pairs, CommonUtility.LOGIN_METHOD).toString();

		}

		protected void onPostExecute(String jsonResult) {

			System.out.println(" result " + jsonResult);

			// if (null != pDialog && pDialog.isShowing()) {
			// pDialog.dismiss();
			// }

			if (!TextUtils.isEmpty(jsonResult) || jsonResult.length() > 0) {

				try {

					JSONObject jsonObjMain = new JSONObject(jsonResult);

					System.out.println(" message " + jsonObjMain.getString("message"));

					if (jsonObjMain.getString("status").equalsIgnoreCase("1")) {

						String usertype_id = CommonUtility.getValueFromJsonObject(jsonObjMain, "usertype id");

						PreferenceUtilities.saveUserData(activity, username, password,
								CommonUtility.getValueFromJsonObject(jsonObjMain, "name"),
								CommonUtility.getValueFromJsonObject(jsonObjMain, "id"),
								String.valueOf(rememberChk.isChecked()),
								CommonUtility.getValueFromJsonObject(jsonObjMain, "manager_name"),
								CommonUtility.getValueFromJsonObject(jsonObjMain, "manager_id"),
								CommonUtility.getValueFromJsonObject(jsonObjMain, "department_id"), usertype_id);
						CommonUtility.getValueFromJsonObject(jsonObjMain, "manager_id");

						// finish();

						// userTypeName = PreferenceUtilities
						// .getSavedUserTypeData(activity).get(0)[Arrays
						// .asList(PreferenceUtilities
						// .getSavedUserTypeData(activity).get(1))
						// .indexOf(usertype_id)];

						intent = new Intent(activity, MainActivity.class);
						intent.putExtra("reg", "");
						// intent.putExtra("NAME",
						// CommonUtility.getValueFromJsonObject(
						// jsonObjMain, "name"));

						if (CommonUtility.getValueFromJsonObject(jsonObjMain, "department_id").equalsIgnoreCase("")
								|| CommonUtility.getValueFromJsonObject(jsonObjMain, "department_id")
										.equalsIgnoreCase("0")) {
							intent.putExtra("userType", "");
						} else {
							intent.putExtra("userType", "emp");
						}

						intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(intent);

						if (null != pDialog && pDialog.isShowing()) {
							pDialog.dismiss();
						}

					} else {
						if (null != pDialog && pDialog.isShowing()) {
							pDialog.dismiss();
						}
						DialogUtility.ShowMessage(jsonObjMain.getString("message"), activity);
					}

				} catch (Exception e) {
					e.printStackTrace();
					if (null != pDialog && pDialog.isShowing()) {
						pDialog.dismiss();
					}
					// Toast.makeText(mContext, e.getMessage(),
					// Toast.LENGTH_SHORT)
					// .show();
				}

			} else {
				if (null != pDialog && pDialog.isShowing()) {
					pDialog.dismiss();
				}
				DialogUtility.ShowMessage("Unable to retrieve data from Server", activity);

			}

		}

	}

	private class ForgotASyncTask extends AsyncTask<String, String, String> {

		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(mContext);
			pDialog.setMessage("Loading ...");
			pDialog.setCancelable(false);
			pDialog.show();
		}

		protected String doInBackground(String... args) {

			List<NameValuePair> pairs = new ArrayList<NameValuePair>();

			// pairs.add(new BasicNameValuePair("apikey", "ETG123"));
			pairs.add(new BasicNameValuePair("deviceid", CommonUtility.getDeviceId(activity)));
			pairs.add(new BasicNameValuePair("email", edtEmail.getText().toString().trim()));

			return WebServiceCalls.postValues(pairs, "forgot_password").toString();

		}

		protected void onPostExecute(String jsonResult) {

			System.out.println(" result " + jsonResult);

			// if (null != pDialog && pDialog.isShowing()) {
			// pDialog.dismiss();
			// }

			if (!TextUtils.isEmpty(jsonResult) || jsonResult.length() > 0) {

				try {

					JSONObject jsonObjMain = new JSONObject(jsonResult);

					System.out.println(" message " + jsonObjMain.getString("message"));

					if (jsonObjMain.getString("status").equalsIgnoreCase("1")) {

						if (null != pDialog && pDialog.isShowing()) {
							pDialog.dismiss();
						}

						DialogUtility.ShowMessage(jsonObjMain.getString("message"), activity);
						customDialog.dismiss();

					} else {
						if (null != pDialog && pDialog.isShowing()) {
							pDialog.dismiss();
						}
						DialogUtility.ShowMessage(jsonObjMain.getString("message"), activity);
					}

				} catch (Exception e) {

					e.printStackTrace();

					if (null != pDialog && pDialog.isShowing()) {
						pDialog.dismiss();
					}

					// Toast.makeText(mContext, e.getMessage(),
					// Toast.LENGTH_SHORT)
					// .show();
				}

			} else {
				if (null != pDialog && pDialog.isShowing()) {
					pDialog.dismiss();
				}
				DialogUtility.ShowMessage("Unable to retrieve data from Server", activity);
			}
		}
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		System.exit(0);
	}
}
