package com.falconpack.android;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
import com.falconpack.android.common.CommonUtility;
import com.falconpack.android.common.Constants;
import com.falconpack.android.common.DialogUtility;
import com.falconpack.android.common.NetworkUtility;
import com.falconpack.android.model.Categories;
import com.falconpack.android.webservicecall.WebServiceCalls;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

public class DocumentManagmentFragment extends Fragment implements
		OnClickListener {

	Button hrButton, financeButton, mrktgButton, deptButton;
	String TAG_FRAGMENT = "SubFragmentTag";
	LinearLayout deptsLayout;

	public DocumentManagmentFragment() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_doc_mgmt, container,
				false);

		hrButton = (Button) rootView.findViewById(R.id.hr_btn);
		financeButton = (Button) rootView.findViewById(R.id.finance_btn);
		mrktgButton = (Button) rootView.findViewById(R.id.mrktg_btn);
		deptsLayout = (LinearLayout) rootView.findViewById(R.id.deptsLayout);
		hrButton.setTypeface(Constants.getProximanova_semibold(getActivity()));
		financeButton.setTypeface(Constants
				.getProximanova_semibold(getActivity()));
		mrktgButton.setTypeface(Constants
				.getProximanova_semibold(getActivity()));

		hrButton.setOnClickListener(this);
		financeButton.setOnClickListener(this);
		mrktgButton.setOnClickListener(this);

		MainActivity.tv_header.setOnClickListener(this);
		if (NetworkUtility.checkInternetConnection(getActivity())) {
			new getDepartmentsAsyncTask().execute();
		} else {
			DialogUtility.ShowMessage(Constants.newtWorkMsg,getActivity());
		}
		return rootView;
	}

	public class getDepartmentsAsyncTask extends
			AsyncTask<String, String, String> {

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

			// pairs.add(new BasicNameValuePair("apikey", "ETG123"));
			pairs.add(new BasicNameValuePair("deviceid", CommonUtility
					.getDeviceId(getActivity())));

			return WebServiceCalls.postValues(pairs, "getDepartments")
					.toString();
		}

		protected void onPostExecute(String jsonResult) {

			System.out.println(" result " + jsonResult);

			if (null != pDialog && pDialog.isShowing()) {

				pDialog.dismiss();

			}
			if (!TextUtils.isEmpty(jsonResult) || jsonResult.length() > 0) {

				try {

					JSONObject jsonObject = new JSONObject(jsonResult);
					if (jsonObject.getString("status").equalsIgnoreCase("1")) {

						jsonObject = jsonObject.getJSONObject("departments");

						System.out.println(" departments "
								+ jsonObject.toString());
						ArrayList<Categories> deptarray = new ArrayList<Categories>();

						for (Iterator<String> iter = jsonObject.keys(); iter
								.hasNext();) {
							String key = iter.next();

							Object value = jsonObject.get(key);
							Categories categories = new Categories();
							System.out.println(" key " + key);
							System.out.println(" value " + value.toString());
							categories.setName(key);
							categories.setDept_no(value.toString());
							deptarray.add(categories);

							View hiddenInfo = getActivity().getLayoutInflater()
									.inflate(R.layout.row_btn, deptsLayout,
											false);

							deptButton = (Button) hiddenInfo
									.findViewById(R.id.dept_btn);
							deptButton.setTypeface(Constants
									.getProximanova_semibold(getActivity()));
							deptButton.setText(key);
							deptButton.setTag(key + "," + value.toString());

							deptButton
									.setOnClickListener(new OnClickListener() {

										@Override
										public void onClick(View v) {
											// TODO Auto-generated method stub
											getFragment(v.getTag().toString()
													.split(",")[1], v.getTag()
													.toString().split(",")[0]);
										}
									});
							deptsLayout.addView(hiddenInfo);

						}

					} else {

					}
				} catch (Exception e) {
					e.printStackTrace();
					DialogUtility.ShowMessage(
							"Unable to retrieve data from Server",
							getActivity());
					// DialogUtility.ShowMessage(e.getMessage(), getActivity());
				}
			} else {
				DialogUtility.ShowMessage(
						"Unable to retrieve data from Server", getActivity());
			}
		}
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.tv_header:

			MainActivity.getDashBoardFragment();
			break;
		case R.id.hr_btn:
			getFragment("0", "HR");
			break;
		case R.id.finance_btn:
			getFragment("1", "Finance");
			break;
		case R.id.mrktg_btn:
			getFragment("2", "Marketing");
			break;
		default:
			break;
		}
	}

	private void getFragment(String id, String title) {
		MainActivity.getActionBarIcons("");
		CommonUtility.docId = id;
		CommonUtility.docName = title;
		MainActivity.tv_header.setText(title);
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		ft.replace(R.id.frame_container, new MarketingFragment(), TAG_FRAGMENT);
		ft.commit();
	}
}
