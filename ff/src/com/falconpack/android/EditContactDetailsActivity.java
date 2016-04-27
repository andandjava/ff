package com.falconpack.android;

import com.falconpack.android.common.CommonUtility;
import com.falconpack.android.common.Constants;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

public class EditContactDetailsActivity extends Activity implements
		OnClickListener {

	EditText mobile_valueEdt, address_valueEdt;
	TextView mobileTxt, addressTxt, updateTxt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_contact_details);

		mobile_valueEdt = (EditText) findViewById(R.id.mobile_value_edt);
		address_valueEdt = (EditText) findViewById(R.id.address_value_edt);

		mobileTxt = (TextView) findViewById(R.id.tv_mobile);
		addressTxt = (TextView) findViewById(R.id.tv_address);
		updateTxt = (TextView) findViewById(R.id.tv_update);

		mobile_valueEdt.setSelection(mobile_valueEdt.getText().toString()
				.length());
		address_valueEdt.setSelection(address_valueEdt.getText().toString()
				.length());

		mobileTxt.setTypeface(Constants
				.getProximanova_semibold(getApplicationContext()));
		addressTxt.setTypeface(Constants
				.getProximanova_semibold(getApplicationContext()));
		updateTxt.setTypeface(Constants
				.getProximanova_semibold(getApplicationContext()));

		mobile_valueEdt.setTypeface(Constants
				.getProximanova_regular(getApplicationContext()));
		address_valueEdt.setTypeface(Constants
				.getProximanova_regular(getApplicationContext()));

		// Button backButton = (Button) findViewById(R.id.back_btn);
		// backButton.setText("Edit Contact Details");
		// backButton.setOnClickListener(new BackListener(this));

		CommonUtility.getHeaderTitle("Edit Contact Details",
				EditContactDetailsActivity.this);
		
		updateTxt.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		finish();
	}
}
