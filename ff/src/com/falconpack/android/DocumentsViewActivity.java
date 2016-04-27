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
import com.falconpack.android.webservicecall.WebServiceCalls;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class DocumentsViewActivity extends Activity {
	Button downloadButton;
	TextView summaryTxt;
	String TAG_FRAGMENT = "SubFragmentTag";
	String docUrl = "", docName = "", emailIds = "";
	Context mContext;
	Activity activity;

	EditText edtEmailId;
	Dialog customdialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.fragment_document);

		mContext = getApplicationContext();
		activity = DocumentsViewActivity.this;

		summaryTxt = (TextView) findViewById(R.id.tv_summary);
		downloadButton = (Button) findViewById(R.id.download_btn);

		summaryTxt.setTypeface(Constants.getProximanova_regular(activity));
		downloadButton.setTypeface(Constants.getProximanova_semibold(activity));

		WebView webView = (WebView) findViewById(R.id.webView);

		Bundle bundle = getIntent().getExtras();

		if (bundle != null) {

			docUrl = bundle.getString("url");
			docName = bundle.getString("name");
		}

		if (NetworkUtility.checkInternetConnection(activity)) {

			CommonUtility.startWebView(activity, docUrl, webView);

		} else {

			DialogUtility.ShowMessage(Constants.newtWorkMsg,activity);

		}

		downloadButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				// LayoutInflater inflater = LayoutInflater.from(activity);

				// AlertDialog.Builder dialogbuilder = new AlertDialog.Builder(
				// activity);
				// dialogbuilder.setTitle("Share Document");
				// dialogbuilder.setView(dialogview);

				customdialog = new Dialog(activity);
				customdialog.setCancelable(true);
				customdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
				customdialog.setContentView(R.layout.dialog_share);

				// dialogDetails = dialogbuilder.create();
				TextView titleTxt = (TextView) customdialog.findViewById(R.id.title_txt);
				TextView shareTxt = (TextView) customdialog.findViewById(R.id.txt_share);
				TextView cancelTxt = (TextView) customdialog.findViewById(R.id.txt_cancel);
				TextView noteTxt = (TextView) customdialog.findViewById(R.id.txt_note);
				edtEmailId = (EditText) customdialog.findViewById(R.id.edit_email);

				noteTxt.setTypeface(Constants.getProximanova_semibold(activity));
				shareTxt.setTypeface(Constants.getProximanova_regular(activity));
				titleTxt.setTypeface(Constants.getProximanova_semibold(activity));
				shareTxt.setTypeface(Constants.getProximanova_regular(activity));
				cancelTxt.setTypeface(Constants.getProximanova_regular(activity));
				edtEmailId.setTypeface(Constants.getProximanova_regular(activity));

				shareTxt.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {

						emailIds = edtEmailId.getText().toString().trim();

						if (emailIds.length() <= 0) {
							DialogUtility.ShowMessage("Please enter email address", activity);
						} else if (getLastChar(emailIds).equalsIgnoreCase(",")) {
							DialogUtility.ShowMessage("Please enter valid email address", activity);
						} else {

							if (NetworkUtility.checkInternetConnection(activity)) {
								new SharingAsyncTask().execute();
							} else {
								DialogUtility.ShowMessage(Constants.newtWorkMsg, activity);
							}

						}

						// new SharingAsyncTask().execute();
						//
						// customdialog.dismiss();
						// Toast.makeText(
						// activity,
						// "User Name : " + edtEmailId.getText().toString()
						// + " Password : "
						// + edtEmailId.getText().toString(),
						// Toast.LENGTH_LONG).show();

					}
				});

				cancelTxt.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						customdialog.dismiss();
					}
				});
				customdialog.show();
			}
		});

		CommonUtility.getHeaderTitle(docName, DocumentsViewActivity.this);
	}

	private String getLastChar(String str) {
		return str.substring(str.length() - 1, str.length());
	}

	public class SharingAsyncTask extends AsyncTask<String, String, String> {

		ProgressDialog pDialog;

		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(activity);
			pDialog.setMessage("Sharing document....");
			pDialog.setCancelable(false);
			pDialog.show();
		}

		protected String doInBackground(String... args) {

			List<NameValuePair> pairs = new ArrayList<NameValuePair>();
			pairs.add(new BasicNameValuePair("deviceid", CommonUtility.getDeviceId(activity)));
			pairs.add(new BasicNameValuePair("from_id", PreferenceUtilities.getSavedUserData(activity).getId()));
			pairs.add(new BasicNameValuePair("link", docUrl));
			pairs.add(new BasicNameValuePair("to_email", edtEmailId.getText().toString().trim()));

			return WebServiceCalls.postValues(pairs, "share_document").toString();
		}

		protected void onPostExecute(String jsonResult) {

			System.out.println(" result " + jsonResult);

			if (null != pDialog && pDialog.isShowing()) {
				pDialog.dismiss();
			}

			if (!TextUtils.isEmpty(jsonResult) || jsonResult.length() > 0) {
				try {
					// [{"id":"1","name":"Sick"},{"id":"2","name":"Casual"},{"id":"3","name":"Annual"},{"id":"4","name":"Others"}]
					JSONObject jsonObjMain = new JSONObject(jsonResult);

					if (jsonObjMain.getString("status").equalsIgnoreCase("0")) {

						// PreferenceUtilities.saveUserData(getActivity(),
						// emailId, pwd, name, PreferenceUtilities
						// .getSavedUserData(getActivity())
						// .getId(), "false", PreferenceUtilities
						// .getSavedUserData(getActivity())
						// .getMname(), PreferenceUtilities
						// .getSavedUserData(getActivity())
						// .getMid(), edtDepartment.getTag()
						// .toString(), edtchoose.getTag()
						// .toString());

						// AlertDialog.Builder builder = new
						// AlertDialog.Builder(activity);
						// builder.setMessage(jsonObjMain.getString("message")).setCancelable(false)
						// .setPositiveButton("OK", new
						// DialogInterface.OnClickListener() {
						// @Override
						// public void onClick(DialogInterface dialog, int id) {
						// dialog.dismiss();
						// }
						// });
						// AlertDialog alert = builder.create();
						// alert.show();.

						DialogUtility.ShowMessage(jsonObjMain.getString("message"), activity);

					} else {

						JSONArray jsonArray = jsonObjMain.getJSONArray("unregistered_email");

						String unEmail = "", reEmail = "";

						if (jsonArray.length() > 0) {
							for (int i = 0; i < jsonArray.length(); i++) {
								unEmail = jsonArray.get(i).toString().trim() + ",";
							}
						}
						jsonArray = jsonObjMain.getJSONArray("registered_email");

						if (jsonArray.length() > 0) {
							for (int i = 0; i < jsonArray.length(); i++) {
								reEmail = reEmail + jsonArray.get(i).toString().trim() + ",";
							}
						}

						AlertDialog.Builder builder = new AlertDialog.Builder(activity);

						String message = "";

						if (unEmail.toString().trim().length() > 0) {

							message = jsonObjMain.getString("message") + "\n" + CommonUtility.removeLastChar(reEmail);

							message = message
									+ "\n \nUnable to share Document to below Email ids. Hence they are not registered with falconpack : \n"
									+ CommonUtility.removeLastChar(unEmail);

							// builder.setPositiveButton("OK", new
							// DialogInterface.OnClickListener() {
							// @Override
							// public void onClick(DialogInterface dialog, int
							// id) {
							// dialog.dismiss();
							//
							// }
							// });
							//
							
							DialogUtility.ShowResultMessage(activity, "0",
									CommonUtility.getValueFromJsonObject(jsonObjMain, "message"));

						} else {

							message = jsonObjMain.getString("message") + "\n" + CommonUtility.removeLastChar(reEmail);

							DialogUtility.ShowResultMessage(activity, "1",
									CommonUtility.getValueFromJsonObject(jsonObjMain, "message"));

						}

						builder.setMessage(message).setCancelable(false);

						AlertDialog alert = builder.create();

						// Must call show() prior to fetching text view
						TextView messageView = (TextView) alert.findViewById(android.R.id.message);
						messageView.setGravity(Gravity.CENTER);

						alert.show();
					}

					// CommonUtility.leaveTypeIdsArray = new String[jsonArray
					// .length()];
					//
					// for (int i = 0; i < jsonArray.length(); i++) {
					// JSONObject jsonObject = jsonArray.getJSONObject(i);
					// System.out.println(" id and name "
					// + jsonObject.getString("id") + " : "
					// + jsonObject.getString("name"));
					// CommonUtility.leaveTypeIdsArray[i] = jsonObject
					// .getString("id");
					// CommonUtility.leavTypesArrayList.add(jsonObject
					// .getString("name"));
					// }

				} catch (Exception e) {
					e.printStackTrace();
					// Toast.makeText(mContext, e.getMessage(),
					// Toast.LENGTH_SHORT)
					// .show();
				}
			} else {
				DialogUtility.ShowMessage("Unable to retrieve data from Server", activity);
				// LoginScreen.chkDialog(activity, MainScreenView.serverErrMsg);
			}
			// pDialog.dismiss();
		}
	}
}
