package com.falconpack.android;

import com.falconpack.android.common.CommonUtility;
import com.falconpack.android.common.Constants;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

public class ContactDetailsActivity extends Activity implements OnClickListener {

	EditText mobile_valueEdt, address_valueEdt;
	TextView mobileTxt, addressTxt, mobileNumTxt, addressDetailsTxt, editTxt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contact_details);

		editTxt = (TextView) findViewById(R.id.tv_edit);
		mobileTxt = (TextView) findViewById(R.id.tv_mobile);
		addressTxt = (TextView) findViewById(R.id.tv_address);
		mobileNumTxt = (EditText) findViewById(R.id.tv_mobile_value);
		addressDetailsTxt = (EditText) findViewById(R.id.tv_address_value);

		mobileTxt.setTypeface(Constants
				.getProximanova_semibold(getApplicationContext()));
		addressTxt.setTypeface(Constants
				.getProximanova_semibold(getApplicationContext()));
		mobileNumTxt.setTypeface(Constants
				.getProximanova_semibold(getApplicationContext()));
		addressDetailsTxt.setTypeface(Constants
				.getProximanova_semibold(getApplicationContext()));

		editTxt.setTypeface(Constants
				.getProximanova_semibold(getApplicationContext()));

		editTxt.setOnClickListener(this);

		// Button backButton = (Button) findViewById(R.id.back_btn);
		// backButton.setText("Contact Details");
		// backButton.setOnClickListener(new BackListener(this));

		CommonUtility.getHeaderTitle("Contact Details",
				ContactDetailsActivity.this);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		startActivity(new Intent(ContactDetailsActivity.this,
				EditContactDetailsActivity.class));
	}
}
