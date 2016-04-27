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

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class ExpenseManagmentFragment extends Fragment implements
		OnClickListener {

	String TAG_FRAGMENT = "SubFragmentTag";
	TextView titleTxt, textView1, textView2, textView3, textView4, addTxt;
	LinearLayout bottom_ll;
	ListView ls_view;
	String[] expensesAry = { "US Trip - Octobar 2014", "UK Trip - August 2014",
			"UAE Trip - May 2014", "US Trip - Octobar 2014", };
	// Button addButton;
	ArrayList<Categories> list;
	Resources res;

	public ExpenseManagmentFragment() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_exp_mgmt, container,
				false);

		res = getActivity().getResources();
		titleTxt = (TextView) rootView.findViewById(R.id.tv_title);
		textView1 = (TextView) rootView.findViewById(R.id.txt1);
		textView2 = (TextView) rootView.findViewById(R.id.txt2);
		textView3 = (TextView) rootView.findViewById(R.id.txt3);
		textView4 = (TextView) rootView.findViewById(R.id.txt4);
		ls_view = (ListView) rootView.findViewById(R.id.lv_view);
		bottom_ll = (LinearLayout) rootView.findViewById(R.id.bottomLayout);
		addTxt = (TextView) rootView.findViewById(R.id.tv_add);

		titleTxt.setTypeface(Constants.getProximanova_bold(getActivity()));
		textView1.setTypeface(Constants.getProximanova_bold(getActivity()));
		textView2.setTypeface(Constants.getProximanova_bold(getActivity()));
		textView3.setTypeface(Constants.getProximanova_bold(getActivity()));
		textView4.setTypeface(Constants.getProximanova_bold(getActivity()));
		addTxt.setTypeface(Constants.getProximanova_bold(getActivity()));

		// rootView.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		// MainActivity.getActionBarIcons("");
		// MainActivity.setActionTitle("Las Vegas Trip - Nov 2013");
		// FragmentTransaction ft = getFragmentManager()
		// .beginTransaction();
		// ft.replace(R.id.frame_container, new TripExpensesFragment(),
		// TAG_FRAGMENT);
		// ft.commit();
		// }
		// });

		// ls_view.setOnItemClickListener(new OnItemClickListener() {
		//
		// @Override
		// public void onItemClick(AdapterView<?> parent, View view,
		// int position, long id) {
		// // TODO Auto-generated method stub
		//
		// MainActivity.getActionBarIcons("");
		//
		// FragmentTransaction ft = getFragmentManager()
		// .beginTransaction();
		//
		// Fragment fragment = new TripExpensesFragment();
		//
		// Bundle bundle = new Bundle();
		// bundle.putString("expId", "");
		// bundle.putBoolean("New", false);
		// bundle.putBoolean("NewExpense", false);
		// bundle.putString("tripId", "");
		// fragment.setArguments(bundle);
		// ft.replace(R.id.frame_container, fragment, TAG_FRAGMENT);
		// ft.commit();
		//
		// }
		// });

		MainActivity.tv_header.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

//				MainActivity.getActionBarIcons("home");
//
//				FragmentTransaction ft = getFragmentManager()
//						.beginTransaction();
//				ft.replace(R.id.frame_container, new DashBoardFragment())
//						.addToBackStack(res.getString(R.string.dashboadfrag));
//
//				ft.commit();
				
				MainActivity.getDashBoardFragment();
			}
		});

		bottom_ll.setOnClickListener(this);
		// addTxt.setOnClickListener(this);

		if (NetworkUtility.checkInternetConnection(getActivity())) {

			new ExpensesSyncTask().execute();

		} else {

			DialogUtility.ShowMessage(Constants.newtWorkMsg,getActivity());

		}

		return rootView;
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		MainActivity.getActionBarIcons("");
		// MainActivity.setActionTitle("Expense Management");
		FragmentTransaction ft = getFragmentManager().beginTransaction();

		Fragment fragment = new TripExpensesDetailsFragment();

		ft.replace(R.id.frame_container, fragment);

		Bundle bundle = new Bundle();
		bundle.putBoolean("New", true);
		bundle.putBoolean("NewExpense", false);
		bundle.putString("tripId", "");
		bundle.putString("expId", "");

		fragment.setArguments(bundle);
		ft.addToBackStack(res.getString(R.string.tripexpdetailsfrag));
		ft.commit();

	}

	private class ExpensesSyncTask extends AsyncTask<String, String, String> {

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
			pairs.add(new BasicNameValuePair("user_id", PreferenceUtilities
					.getSavedUserData(getActivity()).getId()));
			// pairs.add(new BasicNameValuePair("email", edtusername.getText()
			// .toString().trim()));
			// pairs.add(new BasicNameValuePair("password", edtPwd.getText()
			// .toString().trim()));
			return WebServiceCalls.postValues(pairs, "all_trips").toString();

		}

		protected void onPostExecute(String jsonResult) {

			System.out.println(" result " + jsonResult);

			if (!TextUtils.isEmpty(jsonResult) || jsonResult.length() > 0) {

				try {

					list = new ArrayList<Categories>();

					JSONObject jsonObjMain = new JSONObject(jsonResult);

					if (jsonObjMain.has("all_trips")) {
						
						System.out.println(" message "
								+ jsonObjMain.getJSONArray("all_trips"));

						JSONArray jArray = jsonObjMain
								.getJSONArray("all_trips");

						for (int i = 0; i < jArray.length(); i++) {

							Categories categories = new Categories();

							categories.setId(jArray.getJSONObject(i).getString(
									"trip_id"));
							categories.setName(jArray.getJSONObject(i)
									.getString("trip_name"));

							CommonUtility.trip_name = jArray.getJSONObject(i)
									.getString("trip_name");

							list.add(categories);
						}

					} else {

						DialogUtility.ShowMessage(CommonUtility
								.getValueFromJsonObject(jsonObjMain, "message"),
								getActivity());

						// ExpensesAdapter expensesAdapter = new
						// ExpensesAdapter(getActivity(),
						// expensesAry);
						//
						// ls_view.setAdapter(expensesAdapter);
					}

					ls_view.setAdapter(new ExpensesAdapter(getActivity(), list));

					if (null != pDialog && pDialog.isShowing()) {
						pDialog.dismiss();
					}

				} catch (Exception e) {
					e.printStackTrace();
//					Toast.makeText(getActivity(), e.getMessage(),
//							Toast.LENGTH_SHORT).show();
				}
			} else {
				DialogUtility.ShowMessage(
						"Unable to retrieve data from Server", getActivity());
			}
		}
	}

	public class ExpensesAdapter extends BaseAdapter {
		String[] expensesAry;
		Context context;
		ArrayList<Categories> list;
		LayoutInflater inflater = null;

		public ExpensesAdapter(Context con, ArrayList<Categories> list) {
			// TODO Auto-generated constructor stub
			this.list = list;
			context = con;
			inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list.size();
		}

		@Override
		public Categories getItem(int position) {
			// TODO Auto-generated method stub
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		public class Holder {
			TextView tv_text1, tv_text2, tv_time;
		}

		@SuppressLint("ViewHolder")
		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			// TODO Auto-generated method stub
			Holder holder = new Holder();
			View rowView;
//			Categories category = list.get(position);

			rowView = inflater.inflate(R.layout.row_exps_mgt, null);
			holder.tv_text1 = (TextView) rowView.findViewById(R.id.txt1);

			holder.tv_text1.setTypeface(Constants
					.getProximanova_bold(getActivity()));
			holder.tv_text1.setText(getItem(position).getName());
			holder.tv_text1.setTag(position);

			holder.tv_text1.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					// System.out.println(" position " + v.getTag().toString());

					CommonUtility.tripId = getItem(
							Integer.parseInt(v.getTag().toString())).getId();

					MainActivity.getActionBarIcons("");
					//
					FragmentTransaction ft = getFragmentManager()
							.beginTransaction();

					Fragment fragment = new TripExpensesFragment();

					Bundle bundle = new Bundle();
					bundle.putString("expId", "");
					bundle.putBoolean("New", false);
					bundle.putBoolean("NewExpense", false);
					bundle.putString("tripId", CommonUtility.tripId);

					fragment.setArguments(bundle);
					ft.replace(R.id.frame_container, fragment);
					ft.addToBackStack(res.getString(R.string.tripexpfrag));
					ft.commit();

				}
			});

			return rowView;
		}
	}
}
