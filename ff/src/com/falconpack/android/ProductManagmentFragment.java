package com.falconpack.android;

import com.falconpack.android.common.Constants;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;


public class ProductManagmentFragment extends Fragment implements
		OnClickListener {

	Button viewButton;
	String TAG_FRAGMENT = "SubFragmentTag";

	public ProductManagmentFragment() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_product_mgmt,
				container, false);
		viewButton = (Button) rootView.findViewById(R.id.view_btn);

		viewButton.setTypeface(Constants.getProximanova_bold(getActivity()));

		viewButton.setOnClickListener(this);

		MainActivity.tv_header.setOnClickListener(this);

		return rootView;
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub

		FragmentTransaction ft;

		switch (view.getId()) {

		case R.id.tv_header:

			// MainActivity.getActionBarIcons("home");
			// ft = getFragmentManager().beginTransaction();
			// ft.replace(R.id.frame_container, new DashBoardFragment(),
			// TAG_FRAGMENT);
			// ft.commit();

			MainActivity.getDashBoardFragment();

			break;

		case R.id.view_btn:
			// startActivity(new Intent(getActivity(),
			// CatalogueFragment.class));
			MainActivity.getActionBarIcons("");
			MainActivity.tv_header.setText("Catalogue");
			ft = getFragmentManager().beginTransaction();
			ft.replace(R.id.frame_container, new CatalogueFragment(),
					TAG_FRAGMENT);
			ft.commit();
			break;

		default:
			break;
		}
	}
}
