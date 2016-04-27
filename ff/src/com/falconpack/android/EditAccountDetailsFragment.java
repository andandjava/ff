package com.falconpack.android;

import java.util.ArrayList;
import java.util.Arrays;
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
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class EditAccountDetailsFragment extends Fragment implements OnClickListener {

	LinearLayout dept_layout;
	Button signupButton;
	TextView txtName, txtGender, txtEmail, txtPwd, txtYou, txtConPwd, txtDepartment;
	EditText edtGender, edtchoose, edtName, edtEmail, edtPwd, edtConPwd, edtDepartment;
	Intent intent;

	Context mContext;

	String TAG_FRAGMENT = "SubFragmentTag";
	String name, emailId, pwd, conpwd, chooseYou;

	String[] userTypes, userIds, departments, deptIds;

	String[] allUserTypes, allUserIds, genderTypes;
	// HashMap<String, String> allUserTpeMap = new HashMap<String, String>();
	ArrayList<String> allUserTypesLsit = new ArrayList<String>();

	public EditAccountDetailsFragment() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_registration, container, false);

		mContext = getActivity();

		dept_layout = (LinearLayout) rootView.findViewById(R.id.department_layout);
		edtName = (EditText) rootView.findViewById(R.id.editName);
		edtGender = (EditText) rootView.findViewById(R.id.editGender);
		edtGender.setTag("0");

		edtEmail = (EditText) rootView.findViewById(R.id.editEmail);
		edtPwd = (EditText) rootView.findViewById(R.id.editPwd);
		edtConPwd = (EditText) rootView.findViewById(R.id.editConPwd);

		signupButton = (Button) rootView.findViewById(R.id.signup_btn);
		edtDepartment = (EditText) rootView.findViewById(R.id.choose_dept_edt);
		edtchoose = (EditText) rootView.findViewById(R.id.choose_edt);
		edtDepartment.setTag(0);
		edtchoose.setTag(0);

		txtName = (TextView) rootView.findViewById(R.id.textName);
		txtGender = (TextView) rootView.findViewById(R.id.textGender);
		txtEmail = (TextView) rootView.findViewById(R.id.textEmailId);
		txtDepartment = (TextView) rootView.findViewById(R.id.textDepartment);
		txtYou = (TextView) rootView.findViewById(R.id.textYou);
		txtPwd = (TextView) rootView.findViewById(R.id.textPwd);
		txtConPwd = (TextView) rootView.findViewById(R.id.textConPwd);

		signupButton = (Button) rootView.findViewById(R.id.signup_btn);

		txtName.setTypeface(Constants.getProximanova_semibold(mContext));
		txtGender.setTypeface(Constants.getProximanova_semibold(mContext));
		txtDepartment.setTypeface(Constants.getProximanova_semibold(mContext));
		txtEmail.setTypeface(Constants.getProximanova_semibold(mContext));
		txtPwd.setTypeface(Constants.getProximanova_semibold(mContext));
		txtConPwd.setTypeface(Constants.getProximanova_bold(mContext));
		txtYou.setTypeface(Constants.getProximanova_semibold(mContext));

		edtName.setTypeface(Constants.getProximanova_regular(mContext));
		edtGender.setTypeface(Constants.getProximanova_regular(mContext));
		edtEmail.setTypeface(Constants.getProximanova_regular(mContext));
		edtDepartment.setTypeface(Constants.getProximanova_regular(mContext));
		edtPwd.setTypeface(Constants.getProximanova_regular(mContext));
		edtConPwd.setTypeface(Constants.getProximanova_regular(mContext));
		edtchoose.setTypeface(Constants.getProximanova_regular(mContext));
		signupButton.setTypeface(Constants.getProximanova_regular(mContext));

		edtGender.setOnClickListener(this);
		signupButton.setOnClickListener(this);
		edtchoose.setOnClickListener(this);
		// edtDepartment.setOnClickListener(this);

		if (NetworkUtility.checkInternetConnection(getActivity())) {
			CommonUtility.hideKeyBoard(getActivity(), rootView);
			// InputMethodManager imm = (InputMethodManager) getActivity()
			// .getSystemService(Context.INPUT_METHOD_SERVICE);
			// imm.hideSoftInputFromWindow(edtPwd.getWindowToken(), 0);
			new UserTypeAsyncTask().execute();
			// RegistrationDetailsTask

		} else {
			DialogUtility.ShowMessage(Constants.newtWorkMsg,getActivity());
		}

		MainActivity.tv_header.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				FragmentTransaction ft = getFragmentManager().beginTransaction();
				MainActivity.getActionBarIcons("home");
				// ft = getFragmentManager().beginTransaction();
				ft.replace(R.id.frame_container, new DashBoardFragment(), TAG_FRAGMENT);
				ft.commit();
			}
		});
		return rootView;
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		// FragmentTransaction ft;
		switch (view.getId()) {

		case R.id.signup_btn:

			name = edtName.getText().toString().trim();
			chooseYou = edtchoose.getText().toString().trim();
			emailId = edtEmail.getText().toString().trim();
			pwd = edtPwd.getText().toString().trim();
			conpwd = edtConPwd.getText().toString().trim();

			if (name.length() <= 0 && chooseYou.length() <= 0 && pwd.length() <= 0 && emailId.length() <= 0
					&& conpwd.length() <= 0) {

				DialogUtility.ShowMessage("Please enter required fileds", getActivity());

			} else if (emailId.length() > 0) {

				if (CommonUtility.eMailValidation(emailId)) {

					if (name.length() > 0) {

						if (!pwd.equalsIgnoreCase(conpwd) || !conpwd.equalsIgnoreCase(pwd)) {

							DialogUtility.ShowMessage("Please enter New passord and Repeat password must be same",
									getActivity());
						} else {
							if (NetworkUtility.checkInternetConnection(getActivity())) {
								new UpdateUserDetailsTask().execute();
							} else {
								DialogUtility.ShowMessage(Constants.newtWorkMsg,getActivity());
							}
						}
					} else {
						DialogUtility.ShowMessage("Please enter name ", getActivity());
					}
				} else {
					DialogUtility.ShowMessage("Please enter valid email address", getActivity());
				}

			} else {

				DialogUtility.ShowMessage("Please enter email address", getActivity());

			}
			break;

		case R.id.editGender:

			DialogUtility.getGender(getActivity(), edtGender);

			break;

		case R.id.choose_edt:

			if (userTypes.length > 0) {

				AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
				builder.setTitle("Choose");

				builder.setSingleChoiceItems(userTypes, Arrays.asList(userIds).indexOf(edtchoose.getTag().toString()),
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int which) {

								edtchoose.setText(userTypes[which]);
								edtchoose.setTag(userIds[which]);

								if (edtchoose.getText().toString().equalsIgnoreCase("Employee")) {

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

		case R.id.choose_dept_edt:

			if (departments.length > 0) {

				DialogUtility.getChooseDialogue(getActivity(), departments, deptIds, edtDepartment);

				// AlertDialog.Builder builder = new AlertDialog.Builder(
				// getActivity());
				// builder.setTitle("Choose");
				// int pos=0;
				// if(!edtDepartment.getTag().toString().equalsIgnoreCase("0")){
				// pos= Arrays.asList(deptIds).indexOf(
				// edtDepartment.getTag().toString());
				// }else{
				// pos=0;
				// }
				//
				// builder.setSingleChoiceItems(
				// departments,
				// pos,
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

	ProgressDialog pDialog;

	class UserTypeAsyncTask extends AsyncTask<String, Void, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			pDialog = new ProgressDialog(getActivity());
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

			TelephonyManager telephonyManager = (TelephonyManager) getActivity()
					.getSystemService(Context.TELEPHONY_SERVICE);
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

				DialogUtility.ShowMessage("No data found from Server!!!", getActivity());

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
							Editor editor = PreferenceUtilities.saveUserTypeData(getActivity(),

									name, id, i);
							editor.clear();
							editor.commit();
						}
						PreferenceUtilities.saveUserTypeData(getActivity(), name, id, i);
					}

					jArray = jObject.getJSONArray("departments");

					for (int j = 0; j < jArray.length(); j++) {

						String id = jArray.getJSONObject(j).getString("id");
						String name = jArray.getJSONObject(j).getString("name");

						if (j == 0) {

							Editor editor = PreferenceUtilities.saveDepartMentData(getActivity(), name, id, j);

							editor.clear();
							editor.commit();

						}

						System.out.println(" name and id" + name + " and " + id);
						PreferenceUtilities.saveDepartMentData(getActivity(), name, id, j);
					}

					ArrayList<String[]> strArrayList = PreferenceUtilities.getSavedUserTypeData(getActivity());
					allUserTypes = strArrayList.get(0);
					allUserIds = strArrayList.get(1);
					strArrayList.clear();
					strArrayList = PreferenceUtilities.getSavedDepartmentData(getActivity());

					System.out.println(" size " + jArray.length());

					// if (null != pDialog && pDialog.isShowing()) {
					// pDialog.dismiss();
					// }

					new RegistrationDetailsTask().execute();

				} catch (Exception e) {
					e.printStackTrace();
					// TODO: handle exception

					if (null != pDialog && pDialog.isShowing()) {
						pDialog.dismiss();
					}

					DialogUtility.ShowFinishDialoguMessage("Unable to retrieve data from Server", getActivity());
				}
			}
		}
	}

	private class RegistrationDetailsTask extends AsyncTask<String, String, String> {
		// ProgressDialog pDialog;
		//
		// protected void onPreExecute() {
		// super.onPreExecute();
		// pDialog = new ProgressDialog(getActivity());
		// pDialog.setMessage("Loading ...");
		// pDialog.setCancelable(false);
		// pDialog.show();
		// }

		protected String doInBackground(String... args) {

			List<NameValuePair> pairs = new ArrayList<NameValuePair>();

			// pairs.add(new BasicNameValuePair("apikey", "ETG123"));
			pairs.add(new BasicNameValuePair("deviceid", CommonUtility.getDeviceId(getActivity())));
			pairs.add(new BasicNameValuePair("id", PreferenceUtilities.getSavedUserData(getActivity()).getId()));
			return WebServiceCalls.postValues(pairs, "userid_details").toString();
		}

		protected void onPostExecute(String jsonResult) {

			System.out.println(" result " + jsonResult);

			if (null != pDialog && pDialog.isShowing()) {

				pDialog.dismiss();
			}

			System.out.println(" result " + jsonResult);

			if (!TextUtils.isEmpty(jsonResult) || jsonResult.length() > 0) {

				try {

					JSONObject jsonObjMain = new JSONObject(jsonResult);

					if (jsonObjMain.getString("status").equalsIgnoreCase("1")) {

						edtName.setText(CommonUtility.getValueFromJsonObject(jsonObjMain, "name"));

						edtGender.setText(CommonUtility.getValueFromJsonObject(jsonObjMain, "gender"));

						if (CommonUtility.getValueFromJsonObject(jsonObjMain, "gender").equalsIgnoreCase("1")) {
							edtGender.setText("Male");
							edtGender.setTag("0");
						} else {
							edtGender.setText("Female");
							edtGender.setTag("1");
						}
						edtchoose.setText(CommonUtility.getValueFromJsonObject(jsonObjMain, "usertype"));

						edtEmail.setText(CommonUtility.getValueFromJsonObject(jsonObjMain, "email"));

						edtPwd.setText(PreferenceUtilities.getSavedUserData(getActivity()).getPassword());
						edtConPwd.setText(PreferenceUtilities.getSavedUserData(getActivity()).getPassword());

						ArrayList<String[]> strArrayList = PreferenceUtilities.getSavedUserTypeData(getActivity());

						userTypes = strArrayList.get(0);
						userIds = strArrayList.get(1);

						int pos = Arrays.asList(userTypes).indexOf(edtchoose.getText().toString().trim());

						edtchoose.setTag(userIds[pos]);

						System.out.println(" id tag " + edtchoose.getTag().toString());

						if (edtchoose.getText().toString().equalsIgnoreCase("Employee")) {

							dept_layout.setVisibility(View.VISIBLE);

							userTypes = new String[] { "Employee" };
							userIds = new String[] { userIds[0] };

						} else {

							dept_layout.setVisibility(View.GONE);

							// List<String> array = Arrays
							// .asList(new String[userTypes.length]);
							// ArrayList<String> arrayList = new
							// ArrayList<String>();
							// arrayList.addAll(array);
							// arrayList.remove(0);

							userTypes = new String[] { userTypes[1], userTypes[2] };

							// List<String> arrayIds = Arrays
							// .asList(new String[userIds.length]);
							// ArrayList<String> arrayListIds = new
							// ArrayList<String>();
							// arrayListIds.addAll(arrayIds);
							// arrayListIds.remove(0);

							userIds = new String[] { userIds[1], userIds[2] };

						}

						strArrayList.clear();
						strArrayList = PreferenceUtilities.getSavedDepartmentData(getActivity());

						departments = strArrayList.get(0);
						deptIds = strArrayList.get(1);

						// departments = strArrayList.get(0);
						// deptIds = strArrayList.get(1);

						String department = CommonUtility.getValueFromJsonObject(jsonObjMain, "department");

						if (!department.equalsIgnoreCase("0") && !(department.toString().trim().length() <= 0)) {
							pos = Arrays.asList(deptIds).indexOf(department);
							edtDepartment.setText(departments[pos]);
							edtDepartment.setTag(department);

						} else {

							edtDepartment.setTag(deptIds[0]);
							edtDepartment.setText(departments[0]);
							// edtDepartment.setTag(jsonObjMain.getString("department"));
						}

					} else {

						DialogUtility.ShowMessage(jsonObjMain.getString("message"), getActivity());

					}

				} catch (Exception e) {

					e.printStackTrace();

					// Toast.makeText(mContext, e.getMessage(),
					// Toast.LENGTH_SHORT)
					// .show();

					if (null != pDialog && pDialog.isShowing()) {
						pDialog.dismiss();
					}

				}
			} else {
				DialogUtility.ShowMessage("Unable to retrieve data from Server", getActivity());
				// LoginScreen.chkDialog(activity, MainScreenView.serverErrMsg);
			}
			// pDialog.dismiss();
		}
	}

	class UpdateUserDetailsTask extends AsyncTask<String, String, String> {

		ProgressDialog pDialog;

		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(getActivity());
			pDialog.setMessage("Updating ...");
			pDialog.setCancelable(false);
			pDialog.show();
		}

		protected String doInBackground(String... args) {

			// HttpClient client = new DefaultHttpClient();
			// HttpPost post = new HttpPost(Constants.MAIN_HOST
			// + "register_user");
			List<NameValuePair> pairs = new ArrayList<NameValuePair>();

			name = edtName.getText().toString().trim();
			chooseYou = edtchoose.getText().toString().trim();
			emailId = edtEmail.getText().toString().trim();
			pwd = edtPwd.getText().toString().trim();
			conpwd = edtConPwd.getText().toString().trim();

			// pairs.add(new BasicNameValuePair("apikey", "ETG123"));
			pairs.add(new BasicNameValuePair("deviceid", CommonUtility.getDeviceId(getActivity())));
			pairs.add(new BasicNameValuePair("register", "0"));
			pairs.add(new BasicNameValuePair("id", PreferenceUtilities.getSavedUserData(getActivity()).getId()));
			pairs.add(new BasicNameValuePair("type_id", edtchoose.getTag().toString()));
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

			if (!TextUtils.isEmpty(jsonResult) || jsonResult.length() > 0) {

				try {
					// {"errorCode":2,"errorMessage":"Error occur while
					// updating"}
					JSONObject jsonObjMain = new JSONObject(jsonResult);

					if (jsonObjMain.getString("status").equalsIgnoreCase("1")) {

						PreferenceUtilities.saveUserData(getActivity(), emailId, pwd, name,
								PreferenceUtilities.getSavedUserData(getActivity()).getId(), "false",
								PreferenceUtilities.getSavedUserData(getActivity()).getMname(),
								PreferenceUtilities.getSavedUserData(getActivity()).getMid(),
								edtDepartment.getTag().toString(), edtchoose.getTag().toString());

					} else {

					}

					// AlertDialog.Builder builder = new
					// AlertDialog.Builder(getActivity());
					// builder.setMessage(jsonObjMain.getString("message")).setCancelable(false).setPositiveButton("OK",
					// new DialogInterface.OnClickListener() {
					// @Override
					// public void onClick(DialogInterface dialog, int id) {
					// dialog.dismiss();
					// }
					// });
					// AlertDialog alert = builder.create();
					// alert.show();

					DialogUtility.ShowMessage(jsonObjMain.getString("message"), getActivity());

				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {

				DialogUtility.ShowMessage("Unable to retrieve data from Server", getActivity());
				// LoginScreen.chkDialog(activity, MainScreenView.serverErrMsg);
			}
			if (null != pDialog && pDialog.isShowing()) {
				pDialog.dismiss();
			}
		}
	}
}