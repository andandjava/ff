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


public class HRDManagmentFragment extends Fragment implements OnClickListener {

	Button holiday_calButton, pay_slipsButton, cor_policButton,
			emp_grievanceButton;
	String TAG_FRAGMENT = "SubFragmentTag";

	public HRDManagmentFragment() {

		// TODO Auto-generated constructor stub
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_hrd_mgmt, container,
				false);

		holiday_calButton = (Button) rootView.findViewById(R.id.holi_cal_btn);
		cor_policButton = (Button) rootView.findViewById(R.id.cor_polic_btn);
		pay_slipsButton = (Button) rootView.findViewById(R.id.pay_slips_btn);
		emp_grievanceButton = (Button) rootView
				.findViewById(R.id.emp_grievance_btn);

		holiday_calButton.setTypeface(Constants
				.getProximanova_semibold(getActivity()));
		cor_policButton.setTypeface(Constants
				.getProximanova_semibold(getActivity()));
		pay_slipsButton.setTypeface(Constants
				.getProximanova_semibold(getActivity()));
		emp_grievanceButton.setTypeface(Constants
				.getProximanova_semibold(getActivity()));

		
		holiday_calButton.setOnClickListener(this);
		cor_policButton.setOnClickListener(this);
		pay_slipsButton.setOnClickListener(this);
		emp_grievanceButton.setOnClickListener(this);

		MainActivity.tv_header.setOnClickListener(this);

		return rootView;
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
//		FragmentTransaction ft;
		switch (view.getId()) {

		case R.id.tv_header:

//			MainActivity.getActionBarIcons("home");
//
//			ft = getFragmentManager().beginTransaction();
//			ft.replace(R.id.frame_container, new DashBoardFragment(),
//					TAG_FRAGMENT);
//			ft.commit();
			
			MainActivity.getDashBoardFragment();

			break;

		case R.id.holi_cal_btn:

			startActivity(new Intent(getActivity(),
					HolidayCalendarActivity.class));

			break;
		case R.id.pay_slips_btn:

			startActivity(new Intent(getActivity(), PaySlipsActivity.class));

			break;

		case R.id.cor_polic_btn:

			startActivity(new Intent(getActivity(),
					CorporatePoliciesActivity.class));

			break;
			

		case R.id.emp_grievance_btn:

			startActivity(new Intent(getActivity(),
					EmployeeGrievancesActivity.class));

			break;
			
		default:
			break;
		}

	}

}
