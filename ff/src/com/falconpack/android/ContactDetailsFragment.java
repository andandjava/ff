package com.falconpack.android;

import com.falconpack.android.common.BackListener;
import com.falconpack.android.common.CommonUtility;
import com.falconpack.android.common.Constants;

import android.app.Dialog;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ContactDetailsFragment extends Fragment implements OnClickListener {

	EditText mobile_valueEdt, address_valueEdt;
	TextView mobileTxt, addressTxt, mobileNumTxt, addressDetailsTxt, editTxt;

	EditText dlg_mobile_valueEdt, dlg_address_valueEdt;
	TextView dlg_mobileTxt, dlg_addressTxt, dlg_updateTxt;

	String mobileNum, address;

	// @Override
	// protected void onCreate(Bundle savedInstanceState) {
	// super.onCreate(savedInstanceState);
	// setContentView(R.layout.contact_details);
	//
	// editTxt = (TextView) findViewById(R.id.tv_edit);
	// mobileTxt = (TextView) findViewById(R.id.tv_mobile);
	// addressTxt = (TextView) findViewById(R.id.tv_address);
	// mobileNumTxt = (EditText) findViewById(R.id.tv_mobile_value);
	// addressDetailsTxt = (EditText) findViewById(R.id.tv_address_value);
	//
	// mobileTxt.setTypeface(Constants.getProximanova_semibold(getApplicationContext()));
	// addressTxt.setTypeface(Constants.getProximanova_semibold(getApplicationContext()));
	// mobileNumTxt.setTypeface(Constants.getProximanova_semibold(getApplicationContext()));
	// addressDetailsTxt.setTypeface(Constants.getProximanova_semibold(getApplicationContext()));
	//
	// editTxt.setTypeface(Constants.getProximanova_semibold(getApplicationContext()));
	//
	// editTxt.setOnClickListener(this);
	//
	// Button backButton = (Button) findViewById(R.id.back_btn);
	// backButton.setText("Contact Details");
	// backButton.setOnClickListener(new BackListener(this));
	// }

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.contact_details, container,
				false);

		editTxt = (TextView) rootView.findViewById(R.id.tv_edit);
		mobileTxt = (TextView) rootView.findViewById(R.id.tv_mobile);
		addressTxt = (TextView) rootView.findViewById(R.id.tv_address);
		mobileNumTxt = (TextView) rootView.findViewById(R.id.tv_mobile_value);
		addressDetailsTxt = (TextView) rootView
				.findViewById(R.id.tv_address_value);

		mobileTxt.setTypeface(Constants.getProximanova_semibold(getActivity()));
		addressTxt
				.setTypeface(Constants.getProximanova_semibold(getActivity()));
		mobileNumTxt.setTypeface(Constants
				.getProximanova_semibold(getActivity()));
		addressDetailsTxt.setTypeface(Constants
				.getProximanova_semibold(getActivity()));

		editTxt.setTypeface(Constants.getProximanova_semibold(getActivity()));

		editTxt.setOnClickListener(this);
		
//
//		Button backButton = (Button) rootView.findViewById(R.id.back_btn);
//		backButton.setText("Contact Details");
//		backButton.setOnClickListener(new BackListener(getActivity()));

		CommonUtility.getHeaderTitle("Contact Details",
				getActivity());
		
		return rootView;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		// startActivity(new Intent(getActivity(),
		// EditContactDetailsActivity.class));

		final Dialog dialog = new Dialog(getActivity(),
				android.R.style.Theme_Translucent_NoTitleBar);
		dialog.setCancelable(true);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.dialogue_edit);

		dlg_mobile_valueEdt = (EditText) dialog
				.findViewById(R.id.mobile_value_edt);
		dlg_address_valueEdt = (EditText) dialog
				.findViewById(R.id.address_value_edt);

		dlg_mobileTxt = (TextView) dialog.findViewById(R.id.tv_mobile);
		dlg_addressTxt = (TextView) dialog.findViewById(R.id.tv_address);
		dlg_updateTxt = (TextView) dialog.findViewById(R.id.tv_update);

		dlg_mobileTxt.setTypeface(Constants
				.getProximanova_semibold(getActivity()));
		dlg_addressTxt.setTypeface(Constants
				.getProximanova_semibold(getActivity()));
		dlg_updateTxt.setTypeface(Constants
				.getProximanova_semibold(getActivity()));

		dlg_mobile_valueEdt.setTypeface(Constants
				.getProximanova_regular(getActivity()));
		dlg_address_valueEdt.setTypeface(Constants
				.getProximanova_regular(getActivity()));

		dlg_mobile_valueEdt.setText(mobileNumTxt.getText().toString());

		dlg_address_valueEdt.setText(addressDetailsTxt.getText().toString());

		dlg_mobile_valueEdt.setSelection(dlg_mobile_valueEdt.getText()
				.toString().length());
		dlg_address_valueEdt.setSelection(dlg_address_valueEdt.getText()
				.toString().length());
		
//		ConnectivityReceiver.getHeaderTitle("Edit Contact Details",
//				getActivity());
		
		Button backButton = (Button) dialog.findViewById(R.id.back_btn);
		backButton.setText("Edit Contact Details");
		backButton.setOnClickListener(new BackListener(getActivity()));
		
		backButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				getActivity().finish();
				dialog.cancel();
			}
		});
		
		dlg_updateTxt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mobileNumTxt.setText(dlg_mobile_valueEdt.getText().toString()
						.trim());
				addressDetailsTxt.setText(dlg_address_valueEdt.getText()
						.toString().trim());
				dialog.dismiss();
			}
		});
		dialog.show();
	}
}
