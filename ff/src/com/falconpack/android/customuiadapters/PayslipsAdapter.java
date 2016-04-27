package com.falconpack.android.customuiadapters;

import java.util.ArrayList;
import com.falconpack.android.R;
import com.falconpack.android.common.CommonUtility;
import com.falconpack.android.common.Constants;
import com.falconpack.android.common.DialogUtility;
import com.falconpack.android.common.NetworkUtility;
import com.falconpack.android.model.Categories;
import android.app.Activity;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class PayslipsAdapter extends ArrayAdapter<Categories> {
	private Activity mContext;
	String flag = "";
	ArrayList<Categories> policiesList;

	public PayslipsAdapter(Activity context, ArrayList<Categories> policiesList, String flag) {
		super(context, R.layout.row_payslips, policiesList);
		this.mContext = context;
		this.policiesList = policiesList;
		this.flag = flag;
	}

	@Override
	public int getCount() {
		return policiesList.size();
	}

	@Override
	public Categories getItem(int position) {
		return policiesList.get(position);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		Categories categories = getItem(position);

		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(mContext).inflate(R.layout.row_payslips, parent, false);
			holder.monthTxt = (TextView) convertView.findViewById(R.id.tv_month);
			holder.viewTxt = (TextView) convertView.findViewById(R.id.tv_view);
			convertView.setTag(holder);
		} else
			holder = (ViewHolder) convertView.getTag();

		// LayoutParams lp = (LayoutParams) holder.message.getLayoutParams();
		// check if it is a status message then remove background, and change
		// text color.

		holder.viewTxt.setTypeface(Constants.getProximanova_semibold(mContext));
		holder.monthTxt.setTypeface(Constants.getProximanova_regular(mContext));
		holder.viewTxt.setText("View");
		if (flag.equalsIgnoreCase("pay")) {
			// String message = categories.getMonth() + " " +
			// categories.getYear();
			holder.monthTxt.setText("Payslips for the month of " + categories.getText_comb().toString());
		} else {
			String message = categories.getName();
			// holder.viewTxt.setText("VIEWDOWNLOAD");
			holder.monthTxt.setText(message.toString());
		}

		holder.viewTxt.setTag(position);

		holder.viewTxt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final Dialog customdialog = new Dialog(mContext, android.R.style.Theme_Translucent_NoTitleBar);
				customdialog.setCancelable(true);
				customdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
				customdialog.setContentView(R.layout.dialog_view_payslip);
				//
				// ImageView bill_img = (ImageView) customdialog
				// .findViewById(R.id.iv_bill);
				// bill_img.setVisibility(View.GONE);
				TextView tv_ok = (TextView) customdialog.findViewById(R.id.tv_ok);
				tv_ok.setTypeface(Constants.getProximanova_bold(mContext));

				WebView theWebPage = (WebView) customdialog.findViewById(R.id.webView);
				// theWebPage.getSettings().setJavaScriptEnabled(true);
				// theWebPage.getSettings().setPluginState(PluginState.ON);
				//
				Categories category = getItem(Integer.parseInt(v.getTag().toString()));
				//
				// // String url =
				// category.getDownload().replace("http",
				// // "");

				if (NetworkUtility.checkInternetConnection(mContext)) {

					// if (category.getDownload().contains(".pdf")) {
					// String url =
					// "http://docs.google.com/gview?embedded=true&url="
					// + category.getDownload();
					// theWebPage.loadUrl(url);
					// } else {
					// theWebPage.loadUrl(category.getDownload());
					// }
					// redDocId=category.getId();
					CommonUtility.startWebView(mContext, category.getDownload(), theWebPage);

				} else {
					DialogUtility.ShowMessage(Constants.newtWorkMsg,mContext);
				}

				TextView backButton = (TextView) customdialog.findViewById(R.id.back_btn);

				if (flag.equalsIgnoreCase("pay")) {
					backButton.setText("View Payslip");
				} else {
					backButton.setText(category.getName());
				}
				backButton.setTypeface(Constants.getProximanova_semibold(mContext));

				backButton.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						customdialog.dismiss();

						// new updateNotificationAsyncTask().execute();

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
		});

		// System.out.println(" message string " + message.toString());

		// // Check whether message is mine to show green background and align
		// // to right
		// if (userid.equalsIgnoreCase(message.toString().trim().split(":")[0]))
		// {
		// holder.message1.setBackgroundResource(R.drawable.orange);
		// holder.message1.setText(message.toString());
		// // lp.gravity = Gravity.RIGHT;
		// holder.message.setVisibility(View.GONE);
		// holder.message1.setVisibility(View.VISIBLE);
		// }
		// // If not mine then it is from sender to show orange background and
		// // align to left
		// else {
		// if
		// (StartChatActivity.newusers.contains(message.toString().trim().split(":")[0]))
		// {
		// holder.message.setBackgroundResource(R.drawable.blue);
		// holder.message.setText(message.toString());
		// // lp.gravity = Gravity.RIGHT;
		// holder.message.setVisibility(View.VISIBLE);
		// holder.message1.setVisibility(View.GONE);
		//
		// } else {
		//
		// // lp.gravity = Gravity.RIGHT;
		// holder.message.setVisibility(View.GONE);
		// holder.message1.setVisibility(View.GONE);
		// }
		// }
		// // holder.message.setLayoutParams(lp);
		// holder.message.setTextColor(R.color.white);
		// }

		return convertView;
	}

	private class ViewHolder {
		TextView monthTxt, viewTxt;
	}

	@Override
	public long getItemId(int position) {
		// Unimplemented, because we aren't using Sqlite.
		return position;
	}
}
