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
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("NewApi")
public class TripExpensesFragment extends Fragment implements OnClickListener {

	// public static String[] document1 = { "Air Tickets", "Dinner at KFC",
	// "Lunch at IMAC2", "Return Air Tickets" };

	TextView titleTxt, totalTxt, totalValueTxt, addTxt, sendTxt, editTxt, deleteTxt;
	ListView lv_view;
	RelativeLayout rr_add, rr_send, rr_edit, rr_delete;
	String TAG_FRAGMENT = "SubFragmentTag";
	ArrayList<Categories> tripExpsList;
	ExpensesByTripAdapter expensesByTripAdapter;
	String tripId = "", delTripId = "";
	int delPos = 0;
	Resources res;

	public TripExpensesFragment() {

	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);

		Bundle bundle = this.getArguments();
		if (bundle != null) {

			if (!bundle.get("tripId").toString().equals(null)) {
				tripId = (String) bundle.get("tripId");

				CommonUtility.tripId = tripId;
			}

			// System.out.println("string value" + isNewTrip);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_trip_exps, container, false);

		res = getActivity().getResources();
		titleTxt = (TextView) rootView.findViewById(R.id.tv_title);
		totalTxt = (TextView) rootView.findViewById(R.id.tv_total);
		totalValueTxt = (TextView) rootView.findViewById(R.id.tv_total_value);

		addTxt = (TextView) rootView.findViewById(R.id.tv_add);
		sendTxt = (TextView) rootView.findViewById(R.id.tv_send);
		editTxt = (TextView) rootView.findViewById(R.id.tv_edit);
		deleteTxt = (TextView) rootView.findViewById(R.id.tv_delete);

		titleTxt.setTypeface(Constants.getProximanova_bold(getActivity()));
		totalTxt.setTypeface(Constants.getProximanova_bold(getActivity()));
		totalValueTxt.setTypeface(Constants.getProximanova_bold(getActivity()));
		addTxt.setTypeface(Constants.getProximanova_regular(getActivity()));
		sendTxt.setTypeface(Constants.getProximanova_regular(getActivity()));
		editTxt.setTypeface(Constants.getProximanova_regular(getActivity()));
		deleteTxt.setTypeface(Constants.getProximanova_regular(getActivity()));

		lv_view = (ListView) rootView.findViewById(R.id.lv_view);

		if (NetworkUtility.checkInternetConnection(getActivity())) {

			new ExpensesByTripSyncTask().execute();

		} else {

			DialogUtility.ShowMessage(Constants.newtWorkMsg, getActivity());

		}

		// lv_view.setOnItemClickListener(new OnItemClickListener() {
		//
		// @Override
		// public void onItemClick(AdapterView<?> parent, View view,
		// int position, long id) {
		// // TODO Auto-generated method stub
		//
		// MainActivity.getActionBarIcons("");
		//
		// System.out
		// .println(" expensesByTripAdapter.getExpTypeId(position) "
		// + expensesByTripAdapter.getItem(position)
		// .getExp_id());
		//
		// CommonUtility.expId = expensesByTripAdapter.getItem(position)
		// .getExp_id();
		//
		// Fragment fragment = new TripExpensesDetailsFragment();
		//
		// Bundle bundle = new Bundle();
		// bundle.putString("expId",
		// expensesByTripAdapter.getItem(position).getExp_id());
		// bundle.putBoolean("New", false);
		// bundle.putBoolean("NewExpense", false);
		// bundle.putString("tripId", CommonUtility.tripId);
		// fragment.setArguments(bundle);
		// FragmentTransaction ft = getFragmentManager()
		// .beginTransaction();
		// ft.replace(R.id.frame_container, fragment, TAG_FRAGMENT);
		// ft.commit();
		// }
		// });

		MainActivity.setActionTitle("");

		MainActivity.tv_header.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				backButtonFunction();

			}
		});

		sendTxt.setOnClickListener(this);
		addTxt.setOnClickListener(this);
		editTxt.setOnClickListener(this);

		return rootView;
	}

	private class ExpensesByTripSyncTask extends AsyncTask<String, String, String> {

		private ProgressDialog pDialog;

		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(getActivity());
			pDialog.setMessage("Loading ...");
			pDialog.setCancelable(false);
			pDialog.show();
		}

		protected String doInBackground(String... args) {
			List<NameValuePair> pairs = new ArrayList<NameValuePair>();

			// pairs.add(new BasicNameValuePair("apikey", "ETG123"));
			pairs.add(new BasicNameValuePair("deviceid", CommonUtility.getDeviceId(getActivity())));
			pairs.add(new BasicNameValuePair("user_id", PreferenceUtilities.getSavedUserData(getActivity()).getId()));
			pairs.add(new BasicNameValuePair("tripid", CommonUtility.tripId));
			// pairs.add(new BasicNameValuePair("trip_name",
			// CommonUtility.trip_name));
			return WebServiceCalls.postValues(pairs, "expense_by_trip").toString();

		}

		protected void onPostExecute(String jsonResult) {

			System.out.println(" result " + jsonResult);

			if (!TextUtils.isEmpty(jsonResult) || jsonResult.length() > 0) {

				try {

					tripExpsList = new ArrayList<Categories>();

					JSONObject jsonObjMain = new JSONObject(jsonResult);

					if (CommonUtility.getValueFromJsonObject(jsonObjMain, "status").equalsIgnoreCase("1")) {

						String trip_name = CommonUtility.getValueFromJsonObject(jsonObjMain, "trip_name").replace("%20",
								" ");
						String total = CommonUtility.getValueFromJsonObject(jsonObjMain, "Total");
						String currency = CommonUtility.getValueFromJsonObject(jsonObjMain, "currency");
						MainActivity.setActionTitle(trip_name);

						CommonUtility.trip_name = trip_name;
						totalValueTxt.setText(currency + " " + total);

						if (jsonObjMain.has("expensetype")) {

							JSONArray jArray = jsonObjMain.getJSONArray("expensetype");

							// "expense_id": "7",
							// "expensetype_id": "2",
							// "name": "Food",
							// "description": "Food Expenses",
							// "ammount": "1000.00",
							// "currency": "USD"

							for (int i = 0; i < jArray.length(); i++) {

								Categories categories = new Categories();

								JSONObject jObject = jArray.getJSONObject(i);

								categories.setExp_id(CommonUtility.getValueFromJsonObject(jObject, "expense_id"));
								categories
										.setExpType_id(CommonUtility.getValueFromJsonObject(jObject, "expensetype_id"));

								categories.setName(CommonUtility.getValueFromJsonObject(jObject, "name"));

								categories.setId(CommonUtility.getValueFromJsonObject(jObject, "description"));
								categories.setAmt(CommonUtility.getValueFromJsonObject(jObject, "amount"));

								categories.setCurrency(CommonUtility.getValueFromJsonObject(jObject, "currency"));
								tripExpsList.add(categories);
							}

							expensesByTripAdapter = new ExpensesByTripAdapter(getActivity(), tripExpsList);

							lv_view.setAdapter(expensesByTripAdapter);
						} else {

							backButtonFunction();
						}
					}

					if (null != pDialog && pDialog.isShowing()) {
						pDialog.dismiss();
					}
				} catch (Exception e) {
					e.printStackTrace();

					if (null != pDialog && pDialog.isShowing()) {
						pDialog.dismiss();
					}
					// Toast.makeText(getActivity(), e.getMessage(),
					// Toast.LENGTH_SHORT).show();
				}
			} else {
				DialogUtility.ShowMessage("Unable to retrieve data from Server", getActivity());
			}
		}
	}

	public class ExpensesByTripAdapter extends BaseAdapter {

		String[] document_1;
		Context context;
		ArrayList<Categories> categoriesList;
		LayoutInflater inflater = null;

		public ExpensesByTripAdapter(Context con, ArrayList<Categories> categoriesList) {

			context = con;
			this.categoriesList = categoriesList;
			// this.flag = flag;
			inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return categoriesList.size();
		}

		@Override
		public Categories getItem(int position) {
			// TODO Auto-generated method stub
			return categoriesList.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		public String getExpId(int position) {

			return categoriesList.get(position).getExp_id();
		}

		public class Holder {
			TextView tv_text1, tv_text2, tv_amount;
			ImageView iv_edit, iv_del;

		}

		@SuppressLint("ViewHolder")
		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub

			Holder holder = new Holder();
			View rowView;

			rowView = inflater.inflate(R.layout.row_expense, null);
			holder.tv_text1 = (TextView) rowView.findViewById(R.id.tv_text1);
			holder.tv_text2 = (TextView) rowView.findViewById(R.id.tv_text2);
			holder.tv_amount = (TextView) rowView.findViewById(R.id.tv_amount);

			holder.iv_edit = (ImageView) rowView.findViewById(R.id.iv_edit);
			holder.iv_del = (ImageView) rowView.findViewById(R.id.iv_del);

			holder.tv_text1.setTypeface(Constants.getProximanova_bold(getActivity()));
			holder.tv_text2.setTypeface(Constants.getProximanova_regular(getActivity()));
			holder.tv_amount.setTypeface(Constants.getProximanova_semibold(getActivity()));

			holder.tv_text1.setTextColor(getResources().getColor(R.color.hash_btn));
			holder.tv_amount
					.setText(categoriesList.get(position).getCurrency() + " " + categoriesList.get(position).getAmt());

			holder.tv_text1.setText(categoriesList.get(position).getName());
			holder.tv_text2.setText("(" + categoriesList.get(position).getCurrency() + "100.00 @ INR 10.90)");

			holder.iv_edit.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					MainActivity.getActionBarIcons("");

					System.out.println(" expensesByTripAdapter.getExpTypeId(position) "
							+ expensesByTripAdapter.getItem(position).getExp_id());

					CommonUtility.expId = getItem(position).getExp_id();

					Fragment fragment = new TripExpensesDetailsFragment();

					Bundle bundle = new Bundle();
					bundle.putString("expId", CommonUtility.expId);
					bundle.putBoolean("New", false);
					bundle.putBoolean("NewExpense", false);
					bundle.putString("tripId", CommonUtility.tripId);
					bundle.putString("Currency", getItem(position).getCurrency());
					fragment.setArguments(bundle);
					FragmentTransaction ft = getFragmentManager().beginTransaction();
					ft.replace(R.id.frame_container, fragment)
							.addToBackStack(res.getString(R.string.tripexpdetailsfrag));
					ft.commit();

				}
			});

			holder.iv_del.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					delPos = position;
					delTripId = getItem(position).getExp_id();

					// AlertDialog.Builder builder = new
					// AlertDialog.Builder(getActivity());
					// builder.setMessage("Are you sure, Do you want to delete
					// Expense ?").setCancelable(false)
					// .setPositiveButton("OK", new
					// DialogInterface.OnClickListener() {
					// @Override
					// public void onClick(DialogInterface dialog, int id) {
					// dialog.dismiss();
					//
					// new ExpensesDeleteASyncTask().execute();
					// }
					// }).setNegativeButton("Cancel", new
					// DialogInterface.OnClickListener() {
					// @Override
					// public void onClick(DialogInterface dialog, int id) {
					// dialog.dismiss();
					// }
					// });
					// AlertDialog alert = builder.create();
					// alert.show();

					// You Can Customise your Message here
					// TextView messageView = (TextView)
					// alert.findViewById(android.R.id.message);
					// messageView.setGravity(Gravity.CENTER);
					//
					// alert.getButton(DialogInterface.BUTTON_NEGATIVE)
					// .setTypeface(Constants.getProximanova_regular(getActivity()));
					// alert.getButton(DialogInterface.BUTTON_POSITIVE)
					// .setTypeface(Constants.getProximanova_regular(getActivity()));
					// messageView.setTypeface(Constants.getProximanova_regular(getActivity()));

					final Dialog offerdialog1 = new Dialog(getActivity());
					offerdialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
					offerdialog1.setCanceledOnTouchOutside(false);
					offerdialog1.setContentView(R.layout.alert_custom_dialog);

					TextView okTxt = (TextView) offerdialog1.findViewById(R.id.ok);
					TextView cancelTxt = (TextView) offerdialog1.findViewById(R.id.cancel);
					TextView message1 = (TextView) offerdialog1.findViewById(R.id.message);
					okTxt.setTypeface(Constants.getProximanova_regular(getActivity()));
					cancelTxt.setTypeface(Constants.getProximanova_regular(getActivity()));
					message1.setTypeface(Constants.getProximanova_regular(getActivity()));
					message1.setText("Are you sure, Do you want to delete Expense ?");
					okTxt.setOnClickListener(new View.OnClickListener() {

						@Override
						public void onClick(View v) {
							offerdialog1.dismiss();
							new ExpensesDeleteASyncTask().execute();
						}
					});

					cancelTxt.setOnClickListener(new View.OnClickListener() {

						@Override
						public void onClick(View v) {
							offerdialog1.cancel();
						}
					});

					// show dialog
					offerdialog1.show();

				}
			});

			return rowView;
		}
	}

	private class ExpensesDeleteASyncTask extends AsyncTask<String, String, String> {

		private ProgressDialog pDialog1;

		protected void onPreExecute() {
			super.onPreExecute();
			pDialog1 = new ProgressDialog(getActivity());
			pDialog1.setMessage("Loading ...");
			pDialog1.setCancelable(false);
			pDialog1.show();
		}

		protected String doInBackground(String... args) {

			// 'apikey' => 'ETG123',
			// 'deviceid' => '1234',
			// 'user_id' => '41', // only use id for update purpose not for
			// adding
			// 'expensetype' => '2',
			// 'expense_id' => '1',
			// 'tripid' => '410859',
			// 'description' => 'Mysore Trip bankock changed',
			// 'journey_date' => '26-09-2015',
			// 'amount' => '1014.00',
			// 'currency' => 'USD',
			// 'payment_mode' => 'Bank 2 changed',
			// 'bill_path' => 'image encoded text',
			// 'add' => '0', // edit expense
			//

			List<NameValuePair> pairs = new ArrayList<NameValuePair>();

			// pairs.add(new BasicNameValuePair("apikey", "ETG123"));
			pairs.add(new BasicNameValuePair("deviceid", CommonUtility.getDeviceId(getActivity())));
			pairs.add(new BasicNameValuePair("user_id", PreferenceUtilities.getSavedUserData(getActivity()).getId()));

			pairs.add(new BasicNameValuePair("expense_id", delTripId));

			return WebServiceCalls.postValues(pairs, "delete_expense").toString();

		}

		protected void onPostExecute(String jsonResult) {

			System.out.println(" result " + jsonResult);

			// {"status":"1","message":"Expense Details Retrived",
			// "tripid":"1","expensetype_id":"2","user_id":"49","trip_name":"UK
			// Trip",
			// "journey_date":"2015-08-27","amount":"1000.00","currency":"USD",
			// "payment_mode":"Cash","bill_path":"Testpath"}

			if (!TextUtils.isEmpty(jsonResult) || jsonResult.length() > 0) {

				try {

					// categoriesList = new ArrayList<Categories>();
					JSONObject jsonObjMain = new JSONObject(jsonResult);

					if (CommonUtility.getValueFromJsonObject(jsonObjMain, "status").equalsIgnoreCase("1")) {

						tripExpsList.remove(delPos);

						expensesByTripAdapter.notifyDataSetChanged();

						// DialogUtility.ShowMessage(
						// CommonUtility.getValueFromJsonObject(
						// jsonObjMain, "message"), getActivity());

						String message = CommonUtility.getValueFromJsonObject(jsonObjMain, "message");

						final Dialog offerdialog1 = new Dialog(getActivity());
						offerdialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
						offerdialog1.setCanceledOnTouchOutside(false);
						offerdialog1.setContentView(R.layout.alert_custom_dialog);

						TextView submit = (TextView) offerdialog1.findViewById(R.id.ok);

						TextView message1 = (TextView) offerdialog1.findViewById(R.id.message);
						TextView cancelTxt = (TextView) offerdialog1.findViewById(R.id.cancel);
						cancelTxt.setTypeface(Constants.getProximanova_regular(getActivity()));
						cancelTxt.setVisibility(View.GONE);
						submit.setTypeface(Constants.getProximanova_regular(getActivity()));
						message1.setTypeface(Constants.getProximanova_regular(getActivity()));
						message1.setText(message);

						submit.setOnClickListener(new View.OnClickListener() {

							@Override
							public void onClick(View v) {
								offerdialog1.dismiss();
								if (null != pDialog1 && pDialog1.isShowing()) {
									pDialog1.dismiss();
								}
								new ExpensesByTripSyncTask().execute();
							}
						});
						if (!((Activity) getActivity()).isFinishing()) {
							// show dialog
							offerdialog1.show();
						} else {
							Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
						}

					} else {

						DialogUtility.ShowMessage(CommonUtility.getValueFromJsonObject(jsonObjMain, "message"),
								getActivity());
					}

				} catch (Exception e) {

					e.printStackTrace();

					if (null != pDialog1 && pDialog1.isShowing()) {
						pDialog1.dismiss();
					}

					DialogUtility.ShowMessage("Unable to updata data on Server", getActivity());

					// Toast.makeText(getActivity(), e.getMessage(),
					// Toast.LENGTH_SHORT).show();

				}

			} else {

				if (null != pDialog1 && pDialog1.isShowing()) {
					pDialog1.dismiss();
				}

				DialogUtility.ShowMessage("Unable to updata data on Server", getActivity());
			}

			if (null != pDialog1 && pDialog1.isShowing()) {
				pDialog1.dismiss();
			}
		}
	}

	private class ExpensesSendASyncTask extends AsyncTask<String, String, String> {

		private ProgressDialog pDialog1;

		protected void onPreExecute() {
			super.onPreExecute();
			pDialog1 = new ProgressDialog(getActivity());
			pDialog1.setMessage("Loading ...");
			pDialog1.setCancelable(false);
			pDialog1.show();
		}

		protected String doInBackground(String... args) {

			// $recieved_data = array
			// (
			// 'apikey' => 'ETG123',
			// 'deviceid' => '1234',
			// 'user_id' => '11',
			// 'tripid' => '8520942'
			//
			// );

			List<NameValuePair> pairs = new ArrayList<NameValuePair>();

			// pairs.add(new BasicNameValuePair("apikey", "ETG123"));
			pairs.add(new BasicNameValuePair("deviceid", CommonUtility.getDeviceId(getActivity())));
			pairs.add(new BasicNameValuePair("user_id", PreferenceUtilities.getSavedUserData(getActivity()).getId()));

			System.out.println(" trip id " + CommonUtility.tripId + " id " + tripId);
			pairs.add(new BasicNameValuePair("tripid", CommonUtility.tripId));

			return WebServiceCalls.postValues(pairs, "send_expense").toString();

		}

		protected void onPostExecute(String jsonResult) {

			System.out.println(" result " + jsonResult);

			if (!TextUtils.isEmpty(jsonResult) || jsonResult.length() > 0) {

				try {

					JSONObject jsonObjMain = new JSONObject(jsonResult);

					if (CommonUtility.getValueFromJsonObject(jsonObjMain, "status").equalsIgnoreCase("1")) {

						DialogUtility.ShowMessage(CommonUtility.getValueFromJsonObject(jsonObjMain, "message"),
								getActivity());

					} else {

						DialogUtility.ShowMessage(CommonUtility.getValueFromJsonObject(jsonObjMain, "message"),
								getActivity());
					}

				} catch (Exception e) {

					e.printStackTrace();

					if (null != pDialog1 && pDialog1.isShowing()) {
						pDialog1.dismiss();
					}

					DialogUtility.ShowMessage("Unable to updata data on Server", getActivity());

					// Toast.makeText(getActivity(), e.getMessage(),
					// Toast.LENGTH_SHORT).show();

				}

			} else {

				if (null != pDialog1 && pDialog1.isShowing()) {
					pDialog1.dismiss();
				}

				DialogUtility.ShowMessage("Unable to updata data on Server", getActivity());
			}

			if (null != pDialog1 && pDialog1.isShowing()) {
				pDialog1.dismiss();
			}
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()) {

		case R.id.tv_send:

			new ExpensesSendASyncTask().execute();

			break;

		case R.id.tv_add:

			MainActivity.getActionBarIcons("");
			// MainActivity.setActionTitle("Las Vegas Trip - Nov 2013");
			FragmentTransaction ft = getFragmentManager().beginTransaction();
			Fragment fragment = new TripExpensesDetailsFragment();
			Bundle bundle = new Bundle();
			bundle.putBoolean("New", false);

			bundle.putString("tripId", CommonUtility.tripId);
			bundle.putString("expId", "");
			bundle.putBoolean("NewExpense", true);

			if (tripExpsList.size() > 0) {
				bundle.putString("Currency", tripExpsList.get(0).getCurrency());
			} else {
				bundle.putString("Currency", "");
			}
			fragment.setArguments(bundle);
			ft.replace(R.id.frame_container, fragment).addToBackStack(res.getString(R.string.tripexpdetailsfrag));
			ft.commit();

			break;
		default:
			break;
		}
	}

	private void backButtonFunction() {

		if (MainActivity.mDrawerLayout.isDrawerOpen(MainActivity.mDrawerList)) {
			MainActivity.mDrawerLayout.closeDrawer(MainActivity.mDrawerList);
		}

		MainActivity.getActionBarIcons("");
		MainActivity.setActionTitle(res.getString(R.string.exp_mgmt));

		FragmentTransaction ft = getFragmentManager().beginTransaction();
		ft.replace(R.id.frame_container, new ExpenseManagmentFragment())
				.addToBackStack(res.getString(R.string.expsmangfrag));
		ft.commit();
	}
}