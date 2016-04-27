package com.falconpack.android;

import com.falconpack.android.common.Constants;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;


public class LeaveManagmentFragment extends Fragment implements OnClickListener {

	Button leave_sumButton, apply_leaveButton;
	Intent intent;
	String TAG_FRAGMENT = "SubFragmentTag";

	public LeaveManagmentFragment() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_leave_mgmt,
				container, false);

		leave_sumButton = (Button) rootView.findViewById(R.id.leave_sum_btn);
		apply_leaveButton = (Button) rootView
				.findViewById(R.id.apply_leave_btn);

		leave_sumButton.setTypeface(Constants
				.getProximanova_bold(getActivity()));
		apply_leaveButton.setTypeface(Constants
				.getProximanova_bold(getActivity()));

		leave_sumButton.setOnClickListener(this);
		apply_leaveButton.setOnClickListener(this);

		MainActivity.tv_header.setOnClickListener(this);

		return rootView;
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub

		switch (view.getId()) {

		case R.id.tv_header:
			//
			// MainActivity.getActionBarIcons("home");
			//
			// FragmentTransaction ft = getFragmentManager().beginTransaction();
			// ft.replace(R.id.frame_container, new DashBoardFragment(),
			// TAG_FRAGMENT);
			// ft.commit();

			MainActivity.getDashBoardFragment();

			break;

		case R.id.leave_sum_btn:

			startActivity(new Intent(getActivity(), LeaveSummaryActivity.class));

			break;

		case R.id.apply_leave_btn:

			startActivity(new Intent(getActivity(), ApplyLeaveActivity.class));

			break;

		default:
			break;
		}
	}

}
