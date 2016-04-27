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
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DashBoardFragment extends Fragment implements OnClickListener {

	LinearLayout leave_mngLayout, prd_mngLayout, doc_mngLayout, hrdLayout, exp_mngLayout;
	TextView leave_mngTxt, prd_mngTxt, doc_mngTxt, hrdTxt, exp_mngTxt;
	Intent intent;
	String TAG_FRAGMENT = "SubFragmentTag";

	TextView usernameTxt, empcodeTxt;

	Resources res;
	private ProgressDialog pDialog;
	String userTypeName, count = "0";

	public DashBoardFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView;

		PreferenceUtilities.getSavedUserData(getActivity()).getUid();
		
		// ArrayList<String[]> usertypes = PreferenceUtilities
		// .getSavedUserTypeData(getActivity());

		// int pos = Arrays.asList(usertypes.get(1)).indexOf(
		// PreferenceUtilities.getSavedUserData(getActivity()).getUid());

		System.out.println(" position " + MainActivity.getUserType());

		//if (MainActivity.getUserType().equalsIgnoreCase("emp")) {
			rootView = inflater.inflate(R.layout.fragment_dashboard, container, false);
//		} else {
//			rootView = inflater.inflate(R.layout.fragment_dashboard_other, container, false);
//		}

			
//		CommonUtility.hideKeyBoard(getActivity(), v);
		if (NetworkUtility.checkInternetConnection(getActivity())) {

			CommonUtility.hideKeyBoard(getActivity(), rootView);

			new NotificationAsyncTask().execute();

		} else {
			
			//DialogUtility.showCustomDialog(getActivity());
			
		}
		
		CommonUtility.hideKeyBoard(getActivity(), rootView);
		
		res = getActivity().getResources();

		usernameTxt = (TextView) rootView.findViewById(R.id.username_txt);
		empcodeTxt = (TextView) rootView.findViewById(R.id.empcode_txt);

		leave_mngLayout = (LinearLayout) rootView.findViewById(R.id.leave_mng_layout);
		prd_mngLayout = (LinearLayout) rootView.findViewById(R.id.prd_mng_layout);
		doc_mngLayout = (LinearLayout) rootView.findViewById(R.id.doc_mng_layout);
		hrdLayout = (LinearLayout) rootView.findViewById(R.id.hrd_layout);
		exp_mngLayout = (LinearLayout) rootView.findViewById(R.id.exp_mng_layout);

		leave_mngTxt = (TextView) rootView.findViewById(R.id.leave_mng_txt);
		prd_mngTxt = (TextView) rootView.findViewById(R.id.prd_mng_txt);
		doc_mngTxt = (TextView) rootView.findViewById(R.id.doc_mng_txt);
		hrdTxt = (TextView) rootView.findViewById(R.id.hrd_mgmt_txt);
		exp_mngTxt = (TextView) rootView.findViewById(R.id.exp_mng_txt);

		leave_mngLayout.setOnClickListener(this);
		prd_mngLayout.setOnClickListener(this);
		doc_mngLayout.setOnClickListener(this);
		hrdLayout.setOnClickListener(this);
		exp_mngLayout.setOnClickListener(this);

		if (PreferenceUtilities.getSavedUserData(getActivity()).getName() != null) {

			usernameTxt.setText(Html.fromHtml("<html><body>Welcome" + " <b> "
					+ PreferenceUtilities.getSavedUserData(getActivity()).getName() + " </b></body><html>"));

//			if (MainActivity.getUserType().equalsIgnoreCase("emp")) {

				empcodeTxt.setText(Html.fromHtml("<html><body>Empolyee Code <b>"
						+ PreferenceUtilities.getSavedUserData(getActivity()).getId() + "</b> </body><html>"));

//			} else {
//
//				empcodeTxt.setText(Html.fromHtml("<html><body>Code <b>"
//						+ PreferenceUtilities.getSavedUserData(getActivity()).getId() + "</b> </body><html>"));
//			}

		}

		leave_mngTxt.setTypeface(Constants.getProximanova_bold(getActivity()));
		prd_mngTxt.setTypeface(Constants.getProximanova_semibold(getActivity()));
		doc_mngTxt.setTypeface(Constants.getProximanova_semibold(getActivity()));
		hrdTxt.setTypeface(Constants.getProximanova_semibold(getActivity()));
		exp_mngTxt.setTypeface(Constants.getProximanova_semibold(getActivity()));
		usernameTxt.setTypeface(Constants.getProximanova_semibold(getActivity()));
		empcodeTxt.setTypeface(Constants.getProximanova_semibold(getActivity()));


		return rootView;
	}

	private class NotificationAsyncTask extends AsyncTask<String, String, String> {

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

			pairs.add(new BasicNameValuePair("deviceid", CommonUtility.getDeviceId(getActivity())));
			pairs.add(new BasicNameValuePair("user_id", PreferenceUtilities.getSavedUserData(getActivity()).getId()));

			return WebServiceCalls.postValues(pairs, "notifications").toString();

		}

		protected void onPostExecute(String jsonResult) {

			System.out.println(" result " + jsonResult);

			if (!TextUtils.isEmpty(jsonResult) || jsonResult.length() > 0) {

				try {

					// JSON Node names
					// private static final String TAG_DOCUMENTTYPE =
					// "documenttype";
					// private static final String TAG_ID = "id";
					// private static final String TAG_NAME = "name";
					// private static final String TAG_DESCRIPTION =
					// "description";

					CommonUtility.documentNotificatonArrayList.clear();
					CommonUtility.generalNotificationList.clear();
					CommonUtility.notificationList.clear();

					JSONObject jsonObjMain = new JSONObject(jsonResult);

					System.out.println(
							" message " + CommonUtility.getValueFromJsonObject(jsonObjMain, "total_notifications"));

					count = CommonUtility.getValueFromJsonObject(jsonObjMain, "total_notifications");
					CommonUtility.isManager = CommonUtility.getValueFromJsonObject(jsonObjMain, "is_manager");
					String general_count = CommonUtility.getValueFromJsonObject(jsonObjMain,
							"general_notifications_count");
					String doc_count = CommonUtility.getValueFromJsonObject(jsonObjMain,
							"document_notifications_count");
					String leave_count = CommonUtility.getValueFromJsonObject(jsonObjMain, "leave_notifications");

					if (Integer.parseInt(count) != 0) {

						if (Integer.parseInt(general_count) != 0) {

							if (Integer.parseInt(general_count) == 1) {

								CommonUtility.notificationList.add(general_count + " General Notification Received");

							} else {

								CommonUtility.notificationList.add(general_count + " General Notifications Received");

							}

							JSONArray jArray = jsonObjMain.getJSONArray("general_notifications");

							for (int i = 0; i < jArray.length(); i++) {

								Categories categories = new Categories();

								JSONObject jObj = jArray.getJSONObject(i);

								categories.setId(CommonUtility.getValueFromJsonObject(jObj, "id"));
								categories.setIsManager(CommonUtility.isManager);
								categories.setUser_name(CommonUtility.getValueFromJsonObject(jObj, "name"));
								categories.setSubject(CommonUtility.getValueFromJsonObject(jObj, "description"));
								CommonUtility.generalNotificationList.add(categories);
								// CommonUtility.notificationList.add(categories);
							}
						}

						if (Integer.parseInt(doc_count) != 0) {

							Categories categories = new Categories();

							if (Integer.parseInt(doc_count) == 1) {

								categories.setName(doc_count + " Document Uploaded.");
								CommonUtility.notificationList.add(doc_count + " Document Uploaded.");

							} else {
								CommonUtility.notificationList.add(doc_count + " Documents Uploaded.");
								categories.setName(doc_count + " Documents Uploaded.");

							}

							categories.setDescription("Documents");
							// CommonUtility.notificationList.add(categories);

							JSONArray jArray = jsonObjMain.getJSONArray("document_notifications");

							for (int i = 0; i < jArray.length(); i++) {

								categories = new Categories();

								JSONObject jObj = jArray.getJSONObject(i);
								categories.setIsManager(CommonUtility.isManager);
								categories.setId(CommonUtility.getValueFromJsonObject(jObj, "id"));

								categories.setName(CommonUtility.getValueFromJsonObject(jObj, "name"));
								categories.setDownload(CommonUtility.getValueFromJsonObject(jObj, "download"));
								categories.setModified(CommonUtility.getValueFromJsonObject(jObj, "modified"));
								categories.setDescription("Documents");

								CommonUtility.documentNotificatonArrayList.add(categories);
							}
						}

						if (Integer.parseInt(leave_count) != 0) {
							Categories categories = new Categories();

							categories.setIsManager(CommonUtility.isManager);
							if (Integer.parseInt(leave_count) == 1) {
								categories.setName(leave_count + " New Leave Received.");// Pending
																							// for
																							// approval.");
								CommonUtility.notificationList.add(leave_count + " New Leave Received.");// Pending
																											// for
																											// approval.");
							} else {
								categories.setName(leave_count + " New Leaves Received.");// Pending
																							// for
																							// approval.");
								CommonUtility.notificationList.add(leave_count + " New Leaves Received.");// Pending
																											// for
																											// approval.");
							}
							categories.setNum_leaves("" + leave_count);
							categories.setDescription("Leaves");
							// CommonUtility.notificationList.add(categories);
						}

						MainActivity.tv_NotificationCount.setText(count);
						MainActivity.tv_NotificationCount.setVisibility(View.VISIBLE);
						MainActivity.notificationImg.setVisibility(View.VISIBLE);
					} else {
						MainActivity.tv_NotificationCount.setVisibility(View.GONE);
						MainActivity.notificationImg.setVisibility(View.GONE);
					}

					// notification_listview.setAdapter(new CustomAdapter(
					// activity, categoriesList));
					// gridView.setAdapter(new GridviewAdapter(getActivity(),
					// categoriesList));

				} catch (Exception e) {
					e.printStackTrace();
					DialogUtility.ShowMessage("Unable to retrieve data from Server", getActivity());
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
			// intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			// startActivity(intent);
		
			if (null != pDialog && pDialog.isShowing()) {
				pDialog.dismiss();
			}
			
		}
		
	}

	public void onClick(View view) {
		// TODO Auto-generated method stub
		
		switch (view.getId()) {
		
		case R.id.leave_mng_layout:
			MainActivity.getLeaveMangFragment();
			break;
		
		case R.id.prd_mng_layout:
			
			// MainActivity.getActionBarIcons("");
			// MainActivity.setActionTitle("Product Managment");
			// ft = getFragmentManager().beginTransaction();
			// ft.replace(R.id.frame_container, new ProductManagmentFragment())
			// .addToBackStack(res.getString(R.string.productmangfrag));
			// ft.commit();
			
		//	DialogUtility.showConfirmMessage(getActivity(), " ");
					
			MainActivity.getProductMangFragment();
			
			break;
		case R.id.doc_mng_layout:
			MainActivity.getDocMangFragment();
			break;
		case R.id.hrd_layout:
			MainActivity.getHRDMangFragment();
			break;
		case R.id.exp_mng_layout:
			MainActivity.getExpensMangFragment();
			break;
		default:
			break;
		}
	}
}