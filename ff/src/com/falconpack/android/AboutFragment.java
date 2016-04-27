package com.falconpack.android;

import com.falconpack.android.common.CommonUtility;
import com.falconpack.android.common.Constants;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

@SuppressLint("NewApi")
public class AboutFragment extends Fragment {

	TextView summaryTxt;

	public AboutFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_about, container,
				false);

		summaryTxt = (TextView) rootView.findViewById(R.id.tv_summary);

		summaryTxt.setMovementMethod(new ScrollingMovementMethod());

		summaryTxt.setTypeface(Constants.getProximanova_regular(getActivity()));

		MainActivity.tv_header.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				CommonUtility.hideKeyBoard(getActivity(), v);

				MainActivity.getDashBoardFragment();

			}
		});

		return rootView;
	}
}