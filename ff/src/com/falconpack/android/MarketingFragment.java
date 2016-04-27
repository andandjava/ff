package com.falconpack.android;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.falconpack.android.common.CommonUtility;
import com.falconpack.android.common.Constants;
import com.falconpack.android.common.DialogUtility;
import com.falconpack.android.common.NetworkUtility;
import com.falconpack.android.model.Categories;
import com.falconpack.android.webservicecall.WebServiceCalls;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MarketingFragment extends Fragment implements OnClickListener {

	Button button1, button2, button3;
	String TAG_FRAGMENT = "SubFragmentTag";

	// private String[] monthsList = { "", "February", "March",
	// "April", "May", "June", "July", "August", "September", "October",
	// "November", "December" };

	ListView documentsList;
	ArrayList<Categories> categoriesList;
	String doc = "";
	Dialog customdialog;

	String redDocId = "";

	public MarketingFragment() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_marketing,
				container, false);

		// button1 = (Button) rootView.findViewById(R.id.btn1);
		// button2 = (Button) rootView.findViewById(R.id.btn2);
		// button3 = (Button) rootView.findViewById(R.id.btn3);
		//
		// button1.setTypeface(Constants.getProximanova_semibold(getActivity()));
		// button2.setTypeface(Constants.getProximanova_semibold(getActivity()));
		// button3.setTypeface(Constants.getProximanova_semibold(getActivity()));

		// button = (Button) rootView.findViewById(R.id.btn);

		rootView.setOnClickListener(this);

		// button1.setOnClickListener(this);
		// button2.setOnClickListener(this);
		// button3.setOnClickListener(this);

		documentsList = (ListView) rootView.findViewById(R.id.documents_list);

		MainActivity.tv_header.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				// FragmentTransaction ft = getFragmentManager()
				// .beginTransaction();
				// MainActivity.getActionBarIcons("");
				if (!doc.equalsIgnoreCase("doc")) {

					// MainActivity.setActionTitle("View Documents");
					// ft.replace(R.id.frame_container,
					// new DocumentManagmentFragment(), TAG_FRAGMENT);

					MainActivity.getDocMangFragment();

				} else {

					MainActivity.getNotificationFragment();

					// MainActivity.setActionTitle("Notifications");
					// ft.replace(R.id.frame_container,
					// new NotificationsFragment(), TAG_FRAGMENT);
				}

				// ft.commit();
			}
		});

		Bundle bundle = this.getArguments();

		if (bundle != null) {
			if (!bundle.get("noti").toString().equals(null)) {
				doc = (String) bundle.get("noti");
			}
		}

		if (!doc.equalsIgnoreCase("doc")) {
			if (NetworkUtility.checkInternetConnection(getActivity())) {

				new MarketingASyncTask().execute();

			} else {

				DialogUtility.ShowMessage(Constants.newtWorkMsg,getActivity());

			}
		} else {
			MainActivity.getActionBarIcons("");
			MainActivity.tv_header.setText("Documents List");
			DocumentsAdapter mDocumentsAdapter = new DocumentsAdapter(
					getActivity(), CommonUtility.documentNotificatonArrayList);

			documentsList.setAdapter(mDocumentsAdapter);

		}
		return rootView;
	}

	private class MarketingASyncTask extends AsyncTask<String, String, String> {
		private ProgressDialog pDialog;

		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(getActivity());
			pDialog.setMessage("Loading ...");
			pDialog.setCancelable(false);
			pDialog.show();
		}

		protected String doInBackground(String... args) {
			// http://172.16.1.120/falcondevapp/services/get_products
			List<NameValuePair> pairs = new ArrayList<NameValuePair>();

			// pairs.add(new BasicNameValuePair("apikey", "ETG123"));
			pairs.add(new BasicNameValuePair("deviceid", CommonUtility
					.getDeviceId(getActivity())));
			pairs.add(new BasicNameValuePair("department_id",
					CommonUtility.docId));

			return WebServiceCalls.postValues(pairs, "getDocuments2").toString();
		}

		protected void onPostExecute(String jsonResult) {

			System.out.println(" result " + jsonResult);

			if (!TextUtils.isEmpty(jsonResult) || jsonResult.length() > 0) {

				try {

					categoriesList = new ArrayList<Categories>();

					JSONObject jsonObjMain = new JSONObject(jsonResult);
					JSONArray jArray;

					if (jsonObjMain.getString("status").equalsIgnoreCase("1")) {

						jArray = jsonObjMain.getJSONArray("data");

//						for (Iterator<String> iter = jObject.keys(); iter
//								.hasNext();) {
//
//							String key = iter.next();
//
//							Object value = jObject.get(key);
						 for (int i = 0; i < jArray.length(); i++) {
							 
							JSONObject jObj = jArray.getJSONObject(i);

							Categories categories = new Categories();

							categories.setId(""+i);

							categories.setName(CommonUtility
									.getValueFromJsonObject(jObj,
											"document name"));

							categories.setDownload(CommonUtility
									.getValueFromJsonObject(jObj,
											"document path"));

							System.out.println(" created "
									+ CommonUtility.getValueFromJsonObject(
											jObj, "created"));

							System.out.println(" modified "
									+ CommonUtility.getValueFromJsonObject(
											jObj, "modified"));

							categoriesList.add(categories);
						 }
//						}

						// for (int i = 0; i < jArray.length(); i++) {
						//
						// Categories categories = new Categories();
						//
						// categories.setId(CommonUtility
						// .getValueFromJsonObject(
						// jArray.getJSONObject(i), "id"));
						// categories.setName(CommonUtility
						// .getValueFromJsonObject(
						// jArray.getJSONObject(i),
						// "nameofdoc"));
						// categories
						// .setDownload(CommonUtility
						// .getValueFromJsonObject(
						// jArray.getJSONObject(i),
						// "path_doc"));
						// categoriesList.add(categories);
						//
						// }

						DocumentsAdapter mDocumentsAdapter = new DocumentsAdapter(
								getActivity(), categoriesList);

						documentsList.setAdapter(mDocumentsAdapter);
					} else {

						DialogUtility
								.ShowMessage(jsonObjMain.getString("message"),
										getActivity());
					}
					// gridView.setAdapter(new GridviewAdapter(getActivity(),
					// list));

				} catch (JSONException e) {
					e.printStackTrace();
					// Toast.makeText(getActivity(), e.getMessage(),
					// Toast.LENGTH_SHORT).show();
				} catch (Exception e) {
					e.printStackTrace();
					// Toast.makeText(getActivity(), e.getMessage(),
					// Toast.LENGTH_SHORT).show();
				}
			} else {
				DialogUtility.ShowMessage(
						"Unable to retrieve data from Server", getActivity());
			}
			if (null != pDialog && pDialog.isShowing()) {
				pDialog.dismiss();
			}
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

	public class DocumentsAdapter extends BaseAdapter {
		private Context mContext;
		// private String[] monthsList;
		String flag = "";
		ArrayList<Categories> categoriesList;

		public DocumentsAdapter(Context context,
				ArrayList<Categories> categoriesList) {
			// TODO Auto-generated constructor stub
			this.mContext = context;
			this.categoriesList = categoriesList;
		}

		@Override
		public int getCount() {
			return categoriesList.size();
		}

		@Override
		public Categories getItem(int position) {
			return categoriesList.get(position);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			Categories category = (Categories) this.getItem(position);

			ViewHolder holder;

			if (convertView == null) {
				holder = new ViewHolder();
				convertView = LayoutInflater.from(mContext).inflate(
						R.layout.row_marketing, parent, false);
				holder.docBtn = (Button) convertView.findViewById(R.id.doc_btn);
				convertView.setTag(holder);
			} else
				holder = (ViewHolder) convertView.getTag();

			holder.docBtn.setTypeface(Constants.getProximanova_bold(mContext));
			holder.docBtn.setText(category.getName());
			holder.docBtn.setTag(position);
			System.out.println(" message string "
					+ category.getName().toString());

			holder.docBtn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					if (doc.equalsIgnoreCase("")) {

						// MainActivity.getActionBarIcons("");
						//
						// MainActivity.tv_header.setText(categoriesList.get(
						// Integer.parseInt(v.getTag().toString()))
						// .getName());
						//
						// FragmentTransaction ft = getFragmentManager()
						// .beginTransaction();

						// DocumentsViewActivity fragment = new
						// DocumentsFragment();

						// hrd navigation to Document managment

						Intent intent = new Intent(getActivity(),
								DocumentsViewActivity.class);

						intent.putExtra(
								"name",
								categoriesList
										.get(Integer.parseInt(v.getTag()
												.toString())).getName());
						intent.putExtra(
								"url",
								categoriesList
										.get(Integer.parseInt(v.getTag()
												.toString())).getDownload());

						startActivity(intent);

						// fragment.setArguments(bundle);
						//
						// ft.replace(R.id.frame_container, fragment,
						// TAG_FRAGMENT);
						// ft.commit();

					} else {

						// from notificatoin navigation
						customdialog = new Dialog(getActivity(),
								android.R.style.Theme_Translucent_NoTitleBar);
						customdialog.setCancelable(true);
						customdialog
								.requestWindowFeature(Window.FEATURE_NO_TITLE);
						customdialog.setContentView(R.layout.dialog_view_payslip);

//						ImageView bill_img = (ImageView) customdialog
//								.findViewById(R.id.iv_bill);
//						bill_img.setVisibility(View.GONE);
						TextView tv_ok = (TextView) customdialog
								.findViewById(R.id.tv_ok);
						tv_ok.setTypeface(Constants
								.getProximanova_semibold(getActivity()));

						WebView theWebPage = (WebView) customdialog
								.findViewById(R.id.webView);
						// theWebPage.getSettings().setJavaScriptEnabled(true);
						// theWebPage.getSettings().setPluginState(PluginState.ON);
						//
						Categories category = getItem(Integer.parseInt(v
								.getTag().toString()));
						//
						// // String url =
						// category.getDownload().replace("http",
						// // "");
						if (NetworkUtility
								.checkInternetConnection(getActivity())) {

							// if (category.getDownload().contains(".pdf")) {
							// String url =
							// "http://docs.google.com/gview?embedded=true&url="
							// + category.getDownload();
							// theWebPage.loadUrl(url);
							// } else {
							// theWebPage.loadUrl(category.getDownload());
							// }
							redDocId = category.getId();
							CommonUtility.startWebView(getActivity(),
									category.getDownload(), theWebPage);

						} else {
							DialogUtility.ShowMessage(Constants.newtWorkMsg,getActivity());
						}

						TextView backButton = (TextView) customdialog
								.findViewById(R.id.back_btn);
						backButton.setText(category.getName());
						backButton.setTypeface(Constants
								.getProximanova_semibold(getActivity()));

						backButton.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								// dialog.dismiss();

								new updateNotificationAsyncTask().execute();

							}
						});

						tv_ok.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								customdialog.cancel();
							}
						});

						customdialog.show();
					}
				}
			});
			return convertView;
		}

		private class ViewHolder {
			Button docBtn;
		}

		@Override
		public long getItemId(int position) {
			// Unimplemented, because we aren't using Sqlite.
			return position;
		}
	}

	class updateNotificationAsyncTask extends AsyncTask<String, String, String> {

		ProgressDialog pDialog;

		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(getActivity());
			pDialog.setMessage("Loading...");
			pDialog.setCancelable(false);
			pDialog.show();
		}

		protected String doInBackground(String... args) {

			List<NameValuePair> pairs = new ArrayList<NameValuePair>();

			pairs.add(new BasicNameValuePair("deviceid", CommonUtility
					.getDeviceId(getActivity())));
			pairs.add(new BasicNameValuePair("notification_flag_id", "0"));// for
																			// turning
																			// off
																			// general
																			// notifications
			pairs.add(new BasicNameValuePair("document_flag_id", redDocId));// for

			// turning
			// notifications

			return WebServiceCalls
					.postValues(pairs, "update_notification_flag").toString();
		}

		protected void onPostExecute(String jsonResult) {

			System.out.println(" result " + jsonResult);

			if (null != pDialog && pDialog.isShowing()) {
				pDialog.dismiss();
			}

			if (!TextUtils.isEmpty(jsonResult) || jsonResult.length() > 0) {

				try {

					JSONObject jsonObject = new JSONObject(jsonResult);
					System.out.println("status "
							+ jsonObject.getString("status"));

					AlertDialog.Builder builder = new AlertDialog.Builder(
							getActivity());

					builder.setMessage(jsonObject.getString("message"))
							.setCancelable(false);

					customdialog.dismiss();

					// if (jsonObject.getString("status").equalsIgnoreCase("1"))
					// {
					//
					// builder.setPositiveButton("OK",
					// new DialogInterface.OnClickListener() {
					// @Override
					// public void onClick(DialogInterface dialog,
					// int id) {
					// // finish();
					// dialog.dismiss();
					// // getBackFragment();
					// customdialog.dismiss();
					// }
					// });
					// } else {
					// builder.setPositiveButton("OK",
					// new DialogInterface.OnClickListener() {
					// @Override
					// public void onClick(DialogInterface dialog,
					// int id) {
					// dialog.cancel();
					// }
					// });
					// }

					// AlertDialog alert = builder.create();
					// alert.show();

				} catch (Exception e) {
					e.printStackTrace();
					DialogUtility.ShowMessage(e.getMessage(), getActivity());
				}
			} else {
				DialogUtility.ShowMessage(
						"Unable to retrieve data from Server", getActivity());
			}
		}
	}
}
