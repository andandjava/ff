package com.falconpack.android;

import java.util.ArrayList;
import java.util.Locale;

import com.falconpack.android.common.CommonUtility;
import com.falconpack.android.common.Constants;
import com.falconpack.android.model.Categories;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class CatalogueFragment extends Fragment implements OnClickListener {

	RelativeLayout topLayout;
	GridView gridView;
	EditText searchEdt;
	TextView goText;

	String[] catalogueNamesArray = { "Foil and Cling Film", "Containers",
			"Boxes", "Table Ware", "Wooden Items", "Airline Products",
			"Baking and Cake Decoration", "Bags",
			"Hygiene and Production Ware", "Others" };

	String TAG_FRAGMENT = "CatalogueFragmentTag";
	String name = "";
	ArrayList<Categories> categoriesList;

	public CatalogueFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_catalogue,
				container, false);

		MainActivity.getActionBarIcons("catalogue");

		topLayout = (RelativeLayout) rootView.findViewById(R.id.toplayout);
		gridView = (GridView) rootView.findViewById(R.id.gridview);

		topLayout.setVisibility(View.GONE);

		searchEdt = (EditText) rootView.findViewById(R.id.search_edt);
		goText = (TextView) rootView.findViewById(R.id.go_txt);

		// final RelativeLayout searchLayout = (RelativeLayout) rootView
		// .findViewById(R.id.searchlayout);

		// MainActivity.searchImg.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		//
		// if (searchLayout.isShown()) {
		// searchLayout.setVisibility(View.GONE);
		//
		// // searchEdt.set
		// } else {
		// searchLayout.setVisibility(View.VISIBLE);
		// searchEdt.setFocusable(true);
		// searchEdt.requestFocus();
		// }
		// }
		// });

		searchEdt.addTextChangedListener(new TextWatcher() {

			public void afterTextChanged(Editable s) {
			}

			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			public void onTextChanged(CharSequence s, int start, int before,
					int count) {

				ArrayList<String> arraylist = new ArrayList<String>();

				for (int i = 0; i < catalogueNamesArray.length; i++) {

					if (catalogueNamesArray[i].toLowerCase(Locale.ENGLISH)
							.contains(s.toString())) {

						String str = catalogueNamesArray[i];
						arraylist.add(str);
					}

				}
				gridView.setAdapter(new GridviewAdapter(getActivity(),
						arraylist.toArray(new String[arraylist.size()])));
			}
		});

		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub

				MainActivity.getActionBarIcons("products");
				MainActivity.setActionTitle(catalogueNamesArray[position]);
				FragmentTransaction ft = getFragmentManager()
						.beginTransaction();
				Fragment fragment = new ProductsFragment();
				Bundle bundle = new Bundle();
				bundle.putInt("id", position);
				bundle.putString("name", catalogueNamesArray[position]);// .toLowerCase(Locale.ENGLISH));
				// set Fragment class Arguments
				fragment.setArguments(bundle);

				ft.replace(R.id.frame_container, fragment, TAG_FRAGMENT);
				ft.commit();

			}
		});

		MainActivity.tv_header.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				CommonUtility.hideKeyBoard(getActivity(), v);
				MainActivity.getDashBoardFragment();
			}
		});

		gridView.setAdapter(new GridviewAdapter(getActivity(),
				catalogueNamesArray));

		// if (NetworkUtility.checkInternetConnection(getActivity())) {
		//
		// new CatalogueASyncTask().execute();
		//
		// } else {
		//
		// DialogUtility.showCustomDialog(getActivity());
		//
		// }

		// MainActivity.filterImg.setOnClickListener(this);

		return rootView;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		// gridView.setAdapter(new GridviewAdapter(getActivity(), gal_images,
		// "filter"));
	}

	// private class CatalogueASyncTask extends AsyncTask<String, String,
	// String> {
	//
	// private ProgressDialog pDialog;
	//
	// protected void onPreExecute() {
	// super.onPreExecute();
	// pDialog = new ProgressDialog(getActivity());
	// pDialog.setMessage("Loading...");
	// pDialog.setCancelable(false);
	// pDialog.show();
	// }
	//
	// protected String doInBackground(String... args) {
	// // http://172.16.1.120/falcondevapp/services/get_products
	// List<NameValuePair> pairs = new ArrayList<NameValuePair>();
	//
	// pairs.add(new BasicNameValuePair("deviceid", CommonUtility
	// .getDeviceId(getActivity())));
	// pairs.add(new BasicNameValuePair("id", PreferenceUtilities
	// .getSavedUserData(getActivity()).getId()));
	// // pairs.add(new BasicNameValuePair("email", edtusername.getText()
	// // .toString().trim()));
	// // pairs.add(new BasicNameValuePair("password", edtPwd.getText()
	// // .toString().trim()));
	// return WebServiceCalls.postValues(pairs, "get_categories")
	// .toString();
	//
	// }
	//
	// protected void onPostExecute(String jsonResult) {
	//
	// System.out.println(" result " + jsonResult);
	//
	// if (!TextUtils.isEmpty(jsonResult) || jsonResult.length() > 0) {
	//
	// try {
	//
	// categoriesList = new ArrayList<Categories>();
	//
	// JSONObject jsonObjMain = new JSONObject(jsonResult);
	//
	// System.out.println(" message "
	// + jsonObjMain.getJSONArray("categories"));
	//
	// JSONArray jArray = jsonObjMain.getJSONArray("categories");
	//
	// for (int i = 0; i < jArray.length(); i++) {
	//
	// Categories categories = new Categories();
	//
	// categories.setId(jArray.getJSONObject(i)
	// .getString("id"));
	// categories.setName(jArray.getJSONObject(i).getString(
	// "name"));
	// categories.setImage(jArray.getJSONObject(i).getString(
	// "image"));
	//
	// categoriesList.add(categories);
	//
	// }
	//
	// gridView.setAdapter(new GridviewAdapter1(getActivity(),
	// categoriesList));
	//
	// } catch (Exception e) {
	// e.printStackTrace();
	// DialogUtility.ShowMessage(
	// "Unable to retrieve data from Server",
	// getActivity());
	// // Toast.makeText(getActivity(), e.getMessage(),
	// // Toast.LENGTH_SHORT).show();
	// }
	// } else {
	// DialogUtility.ShowMessage(
	// "Unable to retrieve data from Server", getActivity());
	// }
	//
	// if (null != pDialog && pDialog.isShowing()) {
	// pDialog.dismiss();
	// }
	// }
	// }
	//
	// public class GridviewAdapter1 extends BaseAdapter {
	//
	// String flag;
	// ArrayList<Categories> categoriesList;
	// Context context;
	// LayoutInflater inflater = null;
	//
	// public GridviewAdapter1(Context con,
	// ArrayList<Categories> categoriesList) {
	//
	// context = con;
	// this.categoriesList = categoriesList;
	// // this.flag = flag;
	// inflater = (LayoutInflater) context
	// .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	// }
	//
	// @Override
	// public int getCount() {
	// return categoriesList.size();
	// }
	//
	// @Override
	// public Categories getItem(int position) {
	// // TODO Auto-generated method stub
	// return categoriesList.get(position);
	// }
	//
	// @Override
	// public long getItemId(int position) {
	// // TODO Auto-generated method stub
	// return position;
	// }
	//
	// public class Holder {
	// public ImageView imgViewFlag;
	// public TextView catalogueTitleTxt;
	// }
	//
	// @SuppressLint("ViewHolder")
	// @Override
	// public View getView(final int position, View convertView,
	// ViewGroup parent) {
	//
	// Holder holder = new Holder();
	// View rowView;
	//
	// rowView = inflater.inflate(R.layout.row_catalogue, null);
	//
	// holder.imgViewFlag = (ImageView) rowView
	// .findViewById(R.id.container_image);
	// holder.catalogueTitleTxt = (TextView) rowView
	// .findViewById(R.id.title_txt);
	// holder.catalogueTitleTxt.setTypeface(Constants
	// .getProximanova_regular(context));
	//
	// holder.catalogueTitleTxt.setText(categoriesList.get(position)
	// .getName());
	//
	// Picasso picasso = Picasso.with(getActivity());
	// picasso.load(categoriesList.get(position).getImage())
	// .error(R.drawable.image1).placeholder(R.drawable.image1)
	// .into(holder.imgViewFlag);
	//
	// return rowView;
	// }
	// }

	public class GridviewAdapter extends BaseAdapter {

		String flag;
		String[] titles;
		Context context;
		LayoutInflater inflater = null;

		public GridviewAdapter(Context con, String[] titles) {

			context = con;
			this.titles = titles;
			// this.flag = flag;
			inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		@Override
		public int getCount() {
			return titles.length;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		public class Holder {
			public ImageView imgViewFlag;
			public TextView catalogueTitleTxt;
		}

		@SuppressLint("ViewHolder")
		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			Holder holder = new Holder();
			View rowView;

			rowView = inflater.inflate(R.layout.row_catalogue, null);

			holder.imgViewFlag = (ImageView) rowView
					.findViewById(R.id.container_image);
			holder.catalogueTitleTxt = (TextView) rowView
					.findViewById(R.id.title_txt);
			holder.catalogueTitleTxt.setTypeface(Constants
					.getProximanova_regular(context));

			holder.catalogueTitleTxt.setText(titles[position]);

			holder.imgViewFlag.setImageDrawable(CommonUtility
					.getBitmapFromAsset(
							getActivity(),
							"",
							catalogueNamesArray[position].toLowerCase(
									Locale.ENGLISH).replace(" ", "_")
									+ ".png"));

			return rowView;
		}
	}
}