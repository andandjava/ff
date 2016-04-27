package com.falconpack.android;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import com.falconpack.android.common.CommonUtility;
import com.falconpack.android.common.Constants;
import com.falconpack.android.common.DialogUtility;
import com.falconpack.android.model.Categories;
import com.falconpack.android.webservicecall.WebServiceCalls;

import android.annotation.SuppressLint;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


public class NotificationsFragment extends Fragment {

	public static String[] document1 = { "Documents Uploaded",
			"Enquiry Received", "Enquiry Received", "1 New Event",
			"2 New Leaves Pending for approval" };

	public static String[] document2 = { "Document1", "Document1", "", "", "", };

	public static String[] time = { "9:05 AM", "9:10 AM", "10:30 AM",
			"9:05 AM", "10:15 AM", };

	ListView notification_listview;
	String TAG_FRAGMENT = NotificationsFragment.class.getName();
	ArrayList<Categories> categoriesList;

	TextView enameTxt, ename_valueTxt, subTxt, sub_val_Txt, appliedTxt,
			applied_dateTxt, fromTxt, from_dateTxt, toTxt, to_dateTxt,
			no_ofTxt, no_of_daysTxt, descTxt, desc_valTxt, accept_leaveTxt,
			reject_leaveTxt, noTxt;
	String leave_status = "", numOfLeave = "";
	Categories category;
	Dialog dialog;

	Resources res;

	public NotificationsFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_notification,
				container, false);

		// ImageView img_animation =
		// (ImageView)rootView.findViewById(R.id.iv_menily_home);

		res = getActivity().getResources();

		notification_listview = (ListView) rootView
				.findViewById(R.id.notification_listview);
		noTxt = (TextView) rootView.findViewById(android.R.id.empty);
		noTxt.setTypeface(Constants.getProximanova_bold(getActivity()));
		notification_listview.setEmptyView(noTxt);

		// notification_listview.setAdapter(new CustomAdapter(getActivity(),
		// document1, document2, time));

		System.out.println(" active fragment "
				+ MainActivity.getActiveFragment());

		MainActivity.tv_header.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				// MainActivity.getActionBarIcons("home");
				//
				// FragmentTransaction ft = getFragmentManager()
				// .beginTransaction();
				// ft.replace(R.id.frame_container, new DashBoardFragment(),
				// TAG_FRAGMENT);
				// ft.commit();

				MainActivity.getDashBoardFragment();

			}
		});

		notification_listview.setAdapter(new CustomAdapter(getActivity(),
				CommonUtility.notificationList));

		// notification_listview.setOnItemClickListener(new
		// OnItemClickListener() {
		//
		// @Override
		// public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
		// long arg3) {
		// // TODO Auto-generated method stub
		//
		// category = CommonUtility.notificationList.get(arg2);
		//
		// System.out.println(" get position "
		// + category.getLeaveType_id() + " user name "
		// + category.getUser_name());
		//
		// getLeaveStatusDialogue();
		//
		// //
		// // Intent i=new
		// // Intent(getActivity(),LeaveDetailsActivity.class);
		// // i.putExtra("pos", arg2);
		// // startActivity(i);
		// }
		// });

		return rootView;
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

			// pairs.add(new BasicNameValuePair("apikey", "ETG123"));
			// $recieved_data = array
			// (
			// 'apikey' => 'ETG123',
			// 'deviceid' => '1234',
			// 'user_id' => '48', // currently logged in user id
			// 'leave_id' => '1',
			// 'status' => '2', // 1: leave approved 2: leave rejected
			// 'mgr_nf_status' => '1' //0: didn't see notification 1: manager
			// saw the notification by clicking the bubble
			// );
			String ids = "";
			if (CommonUtility.generalNotificationList.size() > 0) {

				for (int i = 0; i < CommonUtility.generalNotificationList
						.size(); i++) {

					CommonUtility.notificationFlag_ids = CommonUtility.notificationFlag_ids
							+ ","
							+ CommonUtility.generalNotificationList.get(i)
									.getId();
				}

				ids = CommonUtility.notificationFlag_ids;

				// System.out.println( " content " +ids);
				System.out
						.println(" content " + ids.substring(1, ids.length()));

			}
			// if(CommonUtility.notificationFlag_ids.equalsIgnoreCase(CommonUtility.notificationFlag_ids.substring(0,
			// 2));

			// CommonUtility.documentFlag_ids;

			pairs.add(new BasicNameValuePair("deviceid", CommonUtility
					.getDeviceId(getActivity())));
			// pairs.add(new BasicNameValuePair("user_id", PreferenceUtilities
			// .getSavedUserData(getActivity()).getId()));
			pairs.add(new BasicNameValuePair("notification_flag_id", ids));// for
																			// turning
																			// off
																			// general
																			// notifications
			pairs.add(new BasicNameValuePair("document_flag_id",
					CommonUtility.documentFlag_ids));// for
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

					if (jsonObject.getString("status").equalsIgnoreCase("1")) {

						builder.setPositiveButton("OK",
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,
											int id) {
										// finish();
										dialog.dismiss();
										MainActivity.getActionBarIcons("home");
										FragmentTransaction ft = getFragmentManager()
												.beginTransaction();
										ft.replace(R.id.frame_container,
												new DashBoardFragment(),
												TAG_FRAGMENT);
										ft.commit();
									}
								});
					} else {
						builder.setPositiveButton("OK",
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,
											int id) {
										dialog.cancel();
									}
								});
					}

					AlertDialog alert = builder.create();
					alert.show();

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

	public class CustomAdapter extends BaseAdapter {

		Context context;
		LayoutInflater inflater = null;
		ArrayList<String> categoriesList;

		public CustomAdapter(Context con, ArrayList<String> categoriesList) {
			// TODO Auto-generated constructor stub
			this.categoriesList = categoriesList;
			context = con;
			inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return categoriesList.size();
		}

		@Override
		public String getItem(int position) {
			// TODO Auto-generated method stub
			return categoriesList.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		public class ViewHolder {
			TextView tv_name, tv_desc, tv_time, tv_click;
			int pos = 0;
			ImageView arrowImg;

		}

		@SuppressLint("ViewHolder")
		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			// TODO Auto-generated method stub

			ViewHolder holder = new ViewHolder();
			String categories = categoriesList.get(position);

			if (convertView == null) {
				convertView = inflater.inflate(R.layout.notification_listrow,
						null);
				holder.tv_name = (TextView) convertView
						.findViewById(R.id.tv_text1);
				holder.tv_desc = (TextView) convertView
						.findViewById(R.id.tv_text2);
				holder.tv_time = (TextView) convertView
						.findViewById(R.id.tv_time);
				holder.tv_click = (TextView) convertView
						.findViewById(R.id.tv_click);

				holder.arrowImg = (ImageView) convertView
						.findViewById(R.id.iv_arrow);
				holder.pos = position;
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			holder.tv_name.setTypeface(Constants
					.getProximanova_regular(getActivity()));
			holder.tv_desc.setTypeface(Constants
					.getProximanova_regular(getActivity()));
			holder.tv_time.setTypeface(Constants
					.getProximanova_regular(getActivity()));

			// holder.tv_click.setVisibility(View.GONE);
			// holder.tv_desc.setVisibility(View.VISIBLE);
			holder.arrowImg.setVisibility(View.VISIBLE);

			// if (categories.getDescription().equalsIgnoreCase("Leaves")
			// || categories.getDescription()
			// .equalsIgnoreCase("Documents")) {

			holder.tv_click.setVisibility(View.GONE);
			holder.tv_desc.setVisibility(View.GONE);
			holder.tv_time.setVisibility(View.GONE);
			// }

			holder.tv_name.setText(categories);
			// holder.tv_desc.setText(categories.getDescription());

			// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd h:mmaa");
			//
			// // "2015-09-02 08:02:00
			// SimpleDateFormat format = new SimpleDateFormat(
			// "yyyy-MM-dd HH:mm:ss");
			//
			// try {
			//
			// System.out.println("Date "
			// + categoriesList.get(position).getApplied_date());
			//
			// Date dat = format.parse(categoriesList.get(position)
			// .getApplied_date());
			//
			// holder.tv_time.setText(sdf.format(dat));
			//
			// } catch (ParseException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }

			convertView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					// numOfLeave = getItem(position).getNum_leaves();
					FragmentTransaction ft = getFragmentManager()
							.beginTransaction();
					Fragment fragment;
					// new LeaveNotificationsAsyncTask().execute();
					Bundle bundle = new Bundle();

					if (getItem(position).contains("General")) {

						MainActivity.getActionBarIcons("");
						MainActivity.setActionTitle("General Notifications");
						fragment = new NotificationsSubFragment();
						bundle.putString("key", "general");
						fragment.setArguments(bundle);
						ft.replace(R.id.frame_container, fragment)
								.addToBackStack(
										res.getString(R.string.leavenotificationfrag));

					} else if (getItem(position).contains("Leave")) {

						MainActivity.getActionBarIcons("");
						MainActivity.setActionTitle("Leave Notifications");
						fragment = new NotificationsSubFragment();
						bundle.putString("key", "leaves");
						fragment.setArguments(bundle);
						ft.replace(R.id.frame_container, fragment)
								.addToBackStack(
										res.getString(R.string.leavenotificationfrag));

					} else {

						MainActivity.getActionBarIcons("");
						MainActivity.tv_header.setText("Marketing");

						bundle = new Bundle();
						bundle.putString("noti", "doc");
						fragment = new MarketingFragment();
						fragment.setArguments(bundle);
						ft.replace(R.id.frame_container, fragment)
								.addToBackStack(
										res.getString(R.string.marketingfrag));

					}
					ft.commit();
				}
			});
			return convertView;
		}
		//
		// OnClickListener onBtncllick = new OnClickListener() {
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		// getItem(position).get;
		// }
		// };
	}

}