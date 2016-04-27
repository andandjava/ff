package com.falconpack.android;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.http.HttpResponse;
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
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class RegistrationActivity extends Activity implements OnClickListener {

	Button signupButton;
	TextView txtName, txtGender, txtEmail, txtPwd, txtYou, txtConPwd, txtDepartment;
	EditText edtGender, edtChoose, edtName, edtEmail, edtPwd, edtConPwd, edtDepartment;
	LinearLayout dept_layout;

	Intent intent;

	Context mContext;

	Activity activity;
	String TAG_FRAGMENT = "SubFragmentTag";
	String name, emailId, pwd, conpwd, chooseYou, chooseDepartment, strIMEINo, id;
	String[] allUserTypes, allUserIds, departments, deptIds, genderTypes;
	// HashMap<String, String> allUserTpeMap = new HashMap<String, String>();
	ArrayList<String> allUserTypesLsit = new ArrayList<String>();
	HttpResponse response1;
	protected int responseCode = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registration);
		mContext = getApplicationContext();
		activity = RegistrationActivity.this;

		dept_layout = (LinearLayout) findViewById(R.id.department_layout);
		edtName = (EditText) findViewById(R.id.editName);
		edtGender = (EditText) findViewById(R.id.editGender);
		edtGender.setTag("0");
		edtEmail = (EditText) findViewById(R.id.editEmail);
		edtPwd = (EditText) findViewById(R.id.editPwd);
		edtConPwd = (EditText) findViewById(R.id.editConPwd);

		signupButton = (Button) findViewById(R.id.signup_btn);
		edtDepartment = (EditText) findViewById(R.id.choose_dept_edt);
		edtChoose = (EditText) findViewById(R.id.choose_edt);

		txtName = (TextView) findViewById(R.id.textName);
		txtGender = (TextView) findViewById(R.id.textGender);
		txtEmail = (TextView) findViewById(R.id.textEmailId);
		txtDepartment = (TextView) findViewById(R.id.textDepartment);
		txtYou = (TextView) findViewById(R.id.textYou);
		txtPwd = (TextView) findViewById(R.id.textPwd);
		txtConPwd = (TextView) findViewById(R.id.textConPwd);

		signupButton = (Button) findViewById(R.id.signup_btn);

		txtName.setTypeface(Constants.getProximanova_semibold(mContext));
		txtGender.setTypeface(Constants.getProximanova_semibold(mContext));
		txtEmail.setTypeface(Constants.getProximanova_semibold(mContext));
		txtDepartment.setTypeface(Constants.getProximanova_semibold(mContext));
		txtPwd.setTypeface(Constants.getProximanova_semibold(mContext));
		txtConPwd.setTypeface(Constants.getProximanova_semibold(mContext));
		txtYou.setTypeface(Constants.getProximanova_semibold(mContext));

		edtName.setTypeface(Constants.getProximanova_regular(mContext));
		edtGender.setTypeface(Constants.getProximanova_regular(mContext));
		edtEmail.setTypeface(Constants.getProximanova_semibold(mContext));
		edtDepartment.setTypeface(Constants.getProximanova_regular(mContext));
		edtPwd.setTypeface(Constants.getProximanova_regular(mContext));
		edtConPwd.setTypeface(Constants.getProximanova_regular(mContext));
		edtChoose.setTypeface(Constants.getProximanova_regular(mContext));
		signupButton.setTypeface(Constants.getProximanova_bold(mContext));

		if (NetworkUtility.checkInternetConnection(mContext)) {
			new UserTypeAsyncTask().execute();
		} else {
			DialogUtility.ShowMessage(Constants.newtWorkMsg, activity);
		}

		edtGender.setOnClickListener(this);
		signupButton.setOnClickListener(this);
		// edtChoose.setOnClickListener(this);
		edtDepartment.setOnClickListener(this);

		CommonUtility.getHeaderTitle("Registration", RegistrationActivity.this);
	}

	@SuppressLint("NewApi")
	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub

		switch (view.getId()) {

		case R.id.signup_btn:

			name = edtName.getText().toString().trim();
			chooseDepartment = edtDepartment.getText().toString().trim();
			emailId = edtEmail.getText().toString().trim();
			pwd = edtPwd.getText().toString().trim();
			conpwd = edtConPwd.getText().toString().trim();

			System.out.println(" id " + edtChoose.getTag().toString());

			if (name.length() <= 0 && chooseDepartment.length() <= 0 && pwd.length() <= 0 && emailId.length() <= 0
					&& conpwd.length() <= 0) {

				DialogUtility.ShowMessage("Please enter required fileds", activity);

			} else if (emailId.length() > 0) {

				if (CommonUtility.eMailValidation(emailId)) {

					if (name.length() > 0) {

						if (chooseDepartment.length() > 0) {

							if (pwd.toString().trim().length() > 0) {

								if (!pwd.equalsIgnoreCase(conpwd) || !conpwd.equalsIgnoreCase(pwd)) {

									DialogUtility.ShowMessage(
											"Please enter New passord and Repeat password must be same", activity);

								} else {

									TelephonyManager telephonyManager = (TelephonyManager) getSystemService(
											Context.TELEPHONY_SERVICE);
									strIMEINo = telephonyManager.getDeviceId();

									if (NetworkUtility.checkInternetConnection(activity)) {
										new RegistrationTask().execute();
									} else {
										DialogUtility.ShowMessage(Constants.newtWorkMsg, activity);
									}

								}
							} else {
								DialogUtility.ShowMessage("Please enter password ", activity);
							}
						} else {

							DialogUtility.ShowMessage("Please select Department ", activity);
						}
					} else {
						DialogUtility.ShowMessage("Please enter name ", activity);
					}
				} else {
					DialogUtility.ShowMessage("Please enter valid email address", activity);
				}
			} else {
				DialogUtility.ShowMessage("Please enter email address", activity);
			}

			break;

		case R.id.choose_edt:

			if (allUserTypes.length > 0) {

				AlertDialog.Builder builder = new AlertDialog.Builder(RegistrationActivity.this);
				builder.setTitle("Choose User Type");

				List<String> idsarray = Arrays.asList(allUserIds);

				builder.setSingleChoiceItems(allUserTypes,
						idsarray.indexOf(edtChoose.getTag().toString().toString().trim()),
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								edtChoose.setText(allUserTypes[which]);
								edtChoose.setTag(allUserIds[which]);
								if (edtChoose.getText().toString().equalsIgnoreCase("Employee")) {

									dept_layout.setVisibility(View.VISIBLE);

								} else {
									dept_layout.setVisibility(View.GONE);

								}

								dialog.dismiss();
							}
						});
				AlertDialog alert = builder.create();
				alert.show();
			}

			break;

		case R.id.editGender:
			DialogUtility.getGender(activity, edtGender);
			break;
		case R.id.choose_dept_edt:

			if (departments.length > 0) {

				DialogUtility.getChooseDialogue(activity, departments, deptIds, edtDepartment);

				// AlertDialog.Builder builder = new AlertDialog.Builder(
				// RegistrationActivity.this);
				// builder.setTitle("Choose");
				//
				// builder.setSingleChoiceItems(
				// departments,
				// Arrays.asList(deptIds).indexOf(
				// edtDepartment.getTag().toString()),
				// new DialogInterface.OnClickListener() {
				//
				// @Override
				// public void onClick(DialogInterface dialog,
				// int which) {
				//
				// edtDepartment.setText(departments[which]);
				// edtDepartment.setTag(deptIds[which]);
				//
				// dialog.dismiss();
				// }
				// });
				//
				// AlertDialog alert = builder.create();
				// alert.show();

			}

			break;
		default:
			break;
		}
	}

	class UserTypeAsyncTask extends AsyncTask<String, Void, String> {

		ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			pDialog = new ProgressDialog(RegistrationActivity.this);
			pDialog.setMessage("Loading ...");
			pDialog.setCancelable(false);
			pDialog.show();
		}

		protected String doInBackground(String... args) {

			List<NameValuePair> pairs = new ArrayList<NameValuePair>();

			// order_id
			// user_id
			// session_id
			// products

			TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
			// pairs.add(new BasicNameValuePair("apikey", "ETG123"));
			pairs.add(new BasicNameValuePair("deviceid", telephonyManager.getDeviceId()));

			return WebServiceCalls.postValues(pairs, CommonUtility.ALL_USER_TYPES).toString();
		}

		@Override
		protected void onPostExecute(String result) {

			super.onPostExecute(result);

			// Toast.makeText(getApplicationContext(), result, 200).show();

			System.out.println(" response " + result);

			if (null == result || result.length() == 0) {

				if (null != pDialog && pDialog.isShowing()) {
					pDialog.dismiss();
				}

				DialogUtility.ShowMessage("No data found from Server!!!", activity);

			} else {

				try {

					// JSONArray jMainObject1 = new JSONArray(result);
					JSONObject jMainObject = new JSONObject(result);

					JSONObject jObject = jMainObject.getJSONObject("signup_register");

					JSONArray jArray = jObject.getJSONArray("usertypes");

					System.out.println(result);

					System.out.println(" name " + jArray.getJSONObject(0).getString("name"));

					for (int i = 0; i < jArray.length(); i++) {

						String id = jArray.getJSONObject(i).getString("id");
						String name = jArray.getJSONObject(i).getString("name");

						System.out.println(" name and id" + name + " and " + id);

						if (i == 0) {
							Editor editor = PreferenceUtilities.saveUserTypeData(activity, name, id, i);
							editor.clear();
							editor.commit();
						}
						PreferenceUtilities.saveUserTypeData(activity, name, id, i);
					}

					jArray = jObject.getJSONArray("departments");

					for (int j = 0; j < jArray.length(); j++) {

						String id = jArray.getJSONObject(j).getString("id");
						String name = jArray.getJSONObject(j).getString("name");

						if (j == 0) {

							Editor editor = PreferenceUtilities.saveDepartMentData(activity, name, id, j);
							editor.clear();
							editor.commit();
						}
						System.out.println(" name and id" + name + " and " + id);
						PreferenceUtilities.saveDepartMentData(activity, name, id, j);
					}

					ArrayList<String[]> strArrayList = PreferenceUtilities.getSavedUserTypeData(activity);
					allUserTypes = strArrayList.get(0);
					allUserIds = strArrayList.get(1);
					strArrayList.clear();
					strArrayList = PreferenceUtilities.getSavedDepartmentData(activity);

					departments = strArrayList.get(0);
					deptIds = strArrayList.get(1);

					edtDepartment.setTag(deptIds[0]);
					edtChoose.setTag(allUserIds[0]);

					System.out.println(" size " + jArray.length());
					if (null != pDialog && pDialog.isShowing()) {
						pDialog.dismiss();
					}
				} catch (Exception e) {
					e.printStackTrace();
					// TODO: handle exception
					if (null != pDialog && pDialog.isShowing()) {
						pDialog.dismiss();
					}
					DialogUtility.ShowFinishDialoguMessage("Unable to retrieve data from Server", activity);
				}
			}
		}
	}

	public class RegistrationTask extends AsyncTask<String, String, String> {

		ProgressDialog pDialog;

		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(RegistrationActivity.this);
			pDialog.setMessage("Signing up ...");
			pDialog.setCancelable(false);
			pDialog.show();
		}

		protected String doInBackground(String... args) {

			List<NameValuePair> pairs = new ArrayList<NameValuePair>();

			name = edtName.getText().toString().trim();
			chooseYou = edtChoose.getText().toString().trim();
			emailId = edtEmail.getText().toString().trim();
			pwd = edtPwd.getText().toString().trim();
			conpwd = edtConPwd.getText().toString().trim();

			// pairs.add(new BasicNameValuePair("apikey", "ETG123"));
			pairs.add(new BasicNameValuePair("deviceid", CommonUtility.getDeviceId(activity)));
			pairs.add(new BasicNameValuePair("register", "1"));

			pairs.add(new BasicNameValuePair("type_id", edtChoose.getTag().toString()));
			if (dept_layout.isShown()) {
				pairs.add(new BasicNameValuePair("department", edtDepartment.getTag().toString()));
			} else {
				pairs.add(new BasicNameValuePair("department", "0"));
			}

			if (edtGender.getText().toString().trim().equalsIgnoreCase("male")) {
				pairs.add(new BasicNameValuePair("gender", "1"));
			} else {
				pairs.add(new BasicNameValuePair("gender", "2"));
			}

			pairs.add(new BasicNameValuePair("name", name));
			pairs.add(new BasicNameValuePair("email", emailId));
			pairs.add(new BasicNameValuePair("password", pwd));
			return WebServiceCalls.postValues(pairs, "register_user").toString();
		}

		protected void onPostExecute(String jsonResult) {

			System.out.println(" result " + jsonResult);

			if (null != pDialog && pDialog.isShowing()) {
				pDialog.dismiss();
			}

			if (!TextUtils.isEmpty(jsonResult) || jsonResult.length() > 0) {

				try {

					JSONObject jsonObjMain = new JSONObject(jsonResult);
					//
					// AlertDialog.Builder builder = new
					// AlertDialog.Builder(activity);
					// builder.setMessage(jsonObjMain.getString("message")).setCancelable(false);
					//
					// if
					// (jsonObjMain.getString("status").equalsIgnoreCase("1")) {
					//
					// builder.setPositiveButton("OK", new
					// DialogInterface.OnClickListener() {
					// @Override
					// public void onClick(DialogInterface dialog, int id) {
					// // dialog.cancel();
					// finish();
					// Intent intent = new Intent(activity,
					// LoginActivity.class);
					// intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					// startActivity(intent);
					// }
					// });
					//
					// } else {
					//
					// builder.setPositiveButton("OK", new
					// DialogInterface.OnClickListener() {
					// @Override
					// public void onClick(DialogInterface dialog, int id) {
					// dialog.cancel();
					// }
					// });
					// }
					// AlertDialog alert = builder.create();
					// alert.show();

					final Dialog offerdialog1 = new Dialog(activity);
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
					message1.setText(jsonObjMain.getString("message"));

					if (jsonObjMain.getString("status").equalsIgnoreCase("1")) {

						okTxt.setOnClickListener(new View.OnClickListener() {

							@Override
							public void onClick(View v) {
								finish();
								Intent intent = new Intent(activity, LoginActivity.class);
								intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
								startActivity(intent);
							}
						});

					} else {

						okTxt.setOnClickListener(new View.OnClickListener() {

							@Override
							public void onClick(View v) {
								offerdialog1.cancel();
							}
						});
					}
					offerdialog1.show();
					// if (!((Activity) activity).isFinishing()) {
					// // show dialog
					// offerdialog1.show();
					// } else {
					// Toast.makeText(activity,
					// jsonObjMain.getString("message"),
					// Toast.LENGTH_LONG).show();
					// }

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

	// static public <T> void executeAsyncTask(AsyncTask<T, ?, ?> task,
	// T... params) {
	// task.execute(params);
	// }

}
