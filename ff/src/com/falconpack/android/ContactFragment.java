package com.falconpack.android;

import com.falconpack.android.common.Constants;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

@SuppressLint("NewApi")
public class ContactFragment extends Fragment {
	TextView contactTxt, contact_titleTxt;

	public ContactFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_contact, container, false);

		contact_titleTxt = (TextView) rootView.findViewById(R.id.tv_con_title);
		contactTxt = (TextView) rootView.findViewById(R.id.tv_contact);

		contact_titleTxt.setTypeface(Constants.getProximanova_bold(getActivity()));
		contactTxt.setTypeface(Constants.getProximanova_regular(getActivity()));

		contactTxt.setMovementMethod(new ScrollingMovementMethod());
		contact_titleTxt.setText(Html.fromHtml("<font color=#1c7348><u><b>Reach Us</b></u></font>"));
		String text = "<u><b> HeadOffice.Sharjah UAE </b></u><br><br> Working House: 7:30-5:00 Sun-Thu <br> P.O Box 5852 Sharjah <br> <b>Tel:</b> +97165340340 <br> <b>Fax:</b> +97165340909 <br> <b>Mail:</b><font> marketing@falconpack.ae </font><br> <b>Web:</b> <font>www.falconpack.com </font><br><br> <b><u>Branches Of GCC</u></b> <br><br><b> KSA Jeddah </b><br><b> Tel:</b> +96626527388 <br> <b>Fax:</b> +96626521866 <br> <b>Address:</b> Jeddah St.60|BN ALALM <br> Tower,2nd floor, Office no:28 <br><br> <b>Oman</b><br><br> <b>Tel:</b> +96824503412 <br> <b>Fax:</b> +96824503795 <br> <b>Address: </b>Jeddah street60|BN ALALM <br> Tower,2nd floor,office#28 <br>";
		contactTxt.setText(Html.fromHtml(text));

		MainActivity.tv_header.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				MainActivity.getDashBoardFragment();
			}
		});

		return rootView;
	}
}