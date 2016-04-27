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
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class ProductsFragment extends Fragment implements OnClickListener {

	TextView containerTxt;
	RelativeLayout topLayout;
	GridView gridView;
	EditText searchEdt;
	TextView goText;

	String[] FoilandClingfilm = { "Aluminium Foils", "Foil Wraps",
			"Cling Films" };
	String[] Containers = { "Aluminium Containers", "Clear Containers",
			"Cake Containers", "Deli Containers", "Salad Containers",
			"Microwave Containers" };
	String[] Boxes = { "Foam Boxs", "Paper Boxs" };
	String[] TableWare = { "Cutlerys", "Plates", "Trays", "Cups",
			"Disposable Accessoriess" };
	String[] WoodenItems = { "toothpicks", "Skewers", "Bamboo Picks",
			"Doyleys", "Place Mats", "Napkins" };
	String[] Airlineproducts = { "Lunch Boxs", "Plastic Coffee Cups" };
	String[] BakingandCakeDecoration = { "Baking Moulds", "Cake Boards",
			"Muffin Trays", "Cake Cups", "Piping Bags",
			"Glassine and Sandwich Papers", "Baking Papers" };
	String[] Bags = { "Freezer Zipper Bags", "Roasting Bags",
			"Table Cover Sofras", "Paper Bags" };
	String[] Plates = { "Plastic Plate and Bowl", "Foam Plate and Bowl",
			"Paper Plate", "Bio-Degradable Products" };
	String[] Trays = { "crystal and Plastic Tray", "Foam Tray", "Carton Tray" };
	String[] Cups = { "Plastic Cup", "Dessert Cup", "Foam Cup", "Paper Cup",
			"Paper Soup Cup" };
	String[] DisposableAccessories = { "Plastic Straw", "Stirrer",
			"Cup Holder", "Glass Cover", "Coaster" };
	String[] HygieneandProtectionWare = { "Gloves", "Masks", "Head Gears",
			"Tissue Papers", "Dispensers" };
	String[] Others = { "Handy Wrap Cutters", "Impulse Sealers", "Handy Fuels",
			"Bag sealers" };

	String TAG_FRAGMENT = "SubFragmentTag";
	int categoryId = 0;
	String categoryName = "";
	ArrayList<Categories> productsList;
	ArrayList<String[]> productsArrayList;
	GridviewAdapter productAdapter;

	public ProductsFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_catalogue,
				container, false);

		categoryId = getArguments().getInt("id", 0);
		categoryName = getArguments().getString("name");

		// Boxes = String[]{"Foam Boxs", "Paper Boxs"};

		productsArrayList = new ArrayList<String[]>();

		productsArrayList.add(FoilandClingfilm);
		productsArrayList.add(Containers);
		productsArrayList.add(Boxes);
		productsArrayList.add(TableWare);
		productsArrayList.add(WoodenItems);
		productsArrayList.add(Airlineproducts);
		productsArrayList.add(BakingandCakeDecoration);
		productsArrayList.add(Bags);
		productsArrayList.add(HygieneandProtectionWare);
		productsArrayList.add(Others);

		// productsArrayList.add(Plates);
		// productsArrayList.add(Trays);
		// productsArrayList.add(Cups);
		// productsArrayList.add(DisposableAccessories);

		topLayout = (RelativeLayout) rootView.findViewById(R.id.toplayout);
		containerTxt = (TextView) rootView.findViewById(R.id.tv_container);

		containerTxt.setTypeface(Constants
				.getProximanova_regular(getActivity()));
		gridView = (GridView) rootView.findViewById(R.id.gridview);

		searchEdt = (EditText) rootView.findViewById(R.id.search_edt);
		searchEdt.setTypeface(Constants.getProximanova_regular(getActivity()));
		// goText = (TextView) rootView.findViewById(R.id.go_txt);

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
		// } else {
		// searchLayout.setVisibility(View.VISIBLE);
		// }
		//
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

				for (int i = 0; i < productsArrayList.get(categoryId).length; i++) {

					if (productsArrayList.get(categoryId)[i].toLowerCase(
							Locale.ENGLISH).contains(s.toString())) {

						String str = productsArrayList.get(categoryId)[i];
						arraylist.add(str);
					}
				}
				gridView.setAdapter(new GridviewAdapter(getActivity(),
						arraylist.toArray(new String[arraylist.size()])));

				// mPayslipsAdapter.getFilter().filter(s.toString());
				// System.out.println(" count "+mPayslipsAdapter.getCount());
			}
		});

		productAdapter = new GridviewAdapter(getActivity(),
				productsArrayList.get(categoryId));
		gridView.setAdapter(productAdapter);

		// MainActivity.filterImg.setOnClickListener(this);
		MainActivity.tv_header.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				CommonUtility.hideKeyBoard(getActivity(), v);
				MainActivity.getActionBarIcons("");
				MainActivity.tv_header.setText("Catalogue");
				FragmentTransaction ft = getFragmentManager()
						.beginTransaction();
				ft.replace(R.id.frame_container, new CatalogueFragment(),
						TAG_FRAGMENT);
				ft.commit();

			}
		});

		// if (NetworkUtility.checkInternetConnection(getActivity())) {
		//
		// new ProductsASyncTask().execute();
		// } else {
		// DialogUtility.showCustomDialog(getActivity());
		// }

		return rootView;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		// gridView.setAdapter(new GridviewAdapter(getActivity(), gal_images,
		// "filter"));
	}

	// private class ProductsASyncTask extends AsyncTask<String, String, String>
	// {
	//
	// private ProgressDialog pDialog;
	//
	// protected void onPreExecute() {
	// super.onPreExecute();
	//
	// pDialog = new ProgressDialog(getActivity());
	// pDialog.setMessage("Signing ...");
	// pDialog.setCancelable(false);
	// pDialog.show();
	//
	// }
	//
	// protected String doInBackground(String... args) {
	//
	// // http://172.16.1.120/falcondevapp/services/get_products
	//
	// List<NameValuePair> pairs = new ArrayList<NameValuePair>();
	//
	// // pairs.add(new BasicNameValuePair("apikey", "ETG123"));
	// pairs.add(new BasicNameValuePair("deviceid", CommonUtility
	// .getDeviceId(getActivity())));
	// pairs.add(new BasicNameValuePair("id", PreferenceUtilities
	// .getSavedUserData(getActivity()).getId()));
	//
	// pairs.add(new BasicNameValuePair("category_id", String
	// .valueOf(categoryId)));
	// // pairs.add(new BasicNameValuePair("password", edtPwd.getText()
	// // .toString().trim()));
	// return WebServiceCalls.postValues(pairs, "get_products").toString();
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
	// productsList = new ArrayList<Categories>();
	//
	// JSONObject jsonObjMain = new JSONObject(jsonResult);
	//
	// if (jsonObjMain.getString("status").equalsIgnoreCase("1")) {
	//
	// System.out.println(" message "
	// + jsonObjMain.getJSONArray("products"));
	//
	// JSONArray jArray = jsonObjMain.getJSONArray("products");
	//
	// for (int i = 0; i < jArray.length(); i++) {
	//
	// Categories categories = new Categories();
	//
	// categories.setId(jArray.getJSONObject(i).getString(
	// "id"));
	// categories.setName(jArray.getJSONObject(i)
	// .getString("name"));
	// categories.setImage(jArray.getJSONObject(i)
	// .getString("image"));
	// categories.setImage(jArray.getJSONObject(i)
	// .getString("description"));
	//
	// productsList.add(categories);
	//
	// }
	// } else {
	// DialogUtility
	// .ShowMessage(jsonObjMain.getString("message"),
	// getActivity());
	// }
	// gridView.setAdapter(new GridviewAdapter1(getActivity(),
	// productsList));
	//
	// if (null != pDialog && pDialog.isShowing()) {
	// pDialog.dismiss();
	// }
	//
	// } catch (Exception e) {
	// e.printStackTrace();
	// // DialogUtility.ShowMessage(e.getMessage(), getActivity());
	// }
	// } else {
	// DialogUtility.ShowMessage(
	// "Unable to retrieve data from Server", getActivity());
	// }
	// }
	// }
	//
	// public class GridviewAdapter1 extends BaseAdapter {
	//
	// String flag;
	// ArrayList<Categories> productsList;
	// Context context;
	// LayoutInflater inflater = null;
	//
	// public GridviewAdapter1(Context con, ArrayList<Categories> productsList)
	// {
	//
	// context = con;
	// this.productsList = productsList;
	// // this.flag = flag;
	// inflater = (LayoutInflater) context
	// .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	// }
	//
	// @Override
	// public int getCount() {
	// return productsList.size();
	// }
	//
	// @Override
	// public Categories getItem(int position) {
	// // TODO Auto-generated method stub
	// return productsList.get(position);
	// }
	//
	// @Override
	// public long getItemId(int position) {
	// // TODO Auto-generated method stub
	// return position;
	// }
	//
	// public class Holder {
	// public ImageView imgViewContainer, imgFavorite, imgCart;
	// public TextView catalogueTitleTxt;
	// }
	//
	// Holder holder;
	//
	// @SuppressLint("ViewHolder")
	// @Override
	// public View getView(final int position, View convertView,
	// ViewGroup parent) {
	// /*
	// * Holder holder = new Holder(); View rowView; rowView =
	// * inflater.inflate(R.layout.gallery_list, null);
	// */
	// holder = new Holder();
	// View rowView;
	//
	// // if (!flag.equalsIgnoreCase("filter")) {
	// // rowView = inflater.inflate(R.layout.row_catalogue, null);
	// // } else {
	//
	// rowView = inflater.inflate(R.layout.row_cataloguefilter, null);
	//
	// // }
	//
	// holder.imgViewContainer = (ImageView) rowView
	// .findViewById(R.id.container_image);
	// holder.imgFavorite = (ImageView) rowView
	// .findViewById(R.id.fav_image);
	// holder.catalogueTitleTxt = (TextView) rowView
	// .findViewById(R.id.title_txt);
	// holder.imgCart = (ImageView) rowView.findViewById(R.id.cart_image);
	//
	// holder.catalogueTitleTxt.setTypeface(Constants
	// .getProximanova_regular(context));
	//
	// if (position % 2 == 0) {
	// holder.imgFavorite.setImageResource(R.drawable.favorite);
	// holder.imgFavorite.setTag(position + "," + position % 2);
	// } else {
	// holder.imgFavorite.setImageResource(R.drawable.favorite_holo);
	// holder.imgFavorite.setTag(position + "," + position % 2);
	// }
	//
	// holder.catalogueTitleTxt.setText(productsList.get(position)
	// .getName());
	//
	// // holder.imgViewContainer.setImageResource(productsList.get(position));
	//
	// Picasso picasso = Picasso.with(getActivity());
	// picasso.load(productsList.get(position).getImage())
	// .error(R.drawable.image1).placeholder(R.drawable.image1)
	// .into(holder.imgViewContainer);
	//
	// holder.imgCart.setOnClickListener(onclickListener);
	// holder.imgFavorite.setOnClickListener(onclickListener);
	//
	// return rowView;
	// }
	//
	// OnClickListener onclickListener = new OnClickListener() {
	// @Override
	// public void onClick(View v) {
	// // TODO Auto-generated method stub
	//
	// switch (v.getId()) {
	// case R.id.cart_image:
	//
	// int pos = (int) v.getTag();
	// System.out.println(" cart clicked " + pos);
	//
	// break;
	// case R.id.fav_image:
	//
	// String str_pos = v.getTag().toString().trim();
	// if (str_pos.split(",")[1].equalsIgnoreCase("1")) {
	// ((ImageView) v).setImageResource(R.drawable.favorite);
	// v.setTag(v
	// .getTag()
	// .toString()
	// .replace(
	// String.valueOf(str_pos.charAt(str_pos
	// .length() - 1)), "0"));
	// } else {
	// ((ImageView) v)
	// .setImageResource(R.drawable.favorite_holo);
	// v.setTag(v
	// .getTag()
	// .toString()
	// .replace(
	// String.valueOf(str_pos.charAt(str_pos
	// .length() - 1)), "1"));
	// }
	// break;
	//
	// default:
	// break;
	// }
	// }
	// };
	// }

	public class GridviewAdapter extends BaseAdapter {

		String flag;
		String[] titles;
		Context context;
		LayoutInflater inflater = null;

		public GridviewAdapter(Context con, String[] titles) {

			context = con;
			this.titles = titles;

			inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		@Override
		public int getCount() {
			return titles.length;
		}

		@Override
		public String getItem(int position) {
			// TODO Auto-generated method stub
			return titles[position];
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		public class Holder {
			public ImageView imgViewFlag, imgFavorite, imgCart;
			private EditText catalogueTitleTxt;
		}

		Holder holder;

		@SuppressLint("ViewHolder")
		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			/*
			 * Holder holder = new Holder(); View rowView; rowView =
			 * inflater.inflate(R.layout.gallery_list, null);
			 */
			holder = new Holder();
			View rowView;
			// if (!flag.equalsIgnoreCase("filter")) {
			// rowView = inflater.inflate(R.layout.row_catalogue, null);
			// } else {
			rowView = inflater.inflate(R.layout.row_cataloguefilter, null);
			// }
			holder.imgViewFlag = (ImageView) rowView
					.findViewById(R.id.container_image);
			holder.imgFavorite = (ImageView) rowView
					.findViewById(R.id.fav_image);
			holder.catalogueTitleTxt = (EditText) rowView
					.findViewById(R.id.title_txt);
			holder.imgCart = (ImageView) rowView.findViewById(R.id.cart_image);

			holder.catalogueTitleTxt.setTypeface(Constants
					.getProximanova_regular(context));
			holder.imgCart.setTag(position);

			if (position % 2 == 0) {
				holder.imgFavorite.setImageResource(R.drawable.favorite);
				holder.imgFavorite.setTag(position + "," + position % 2);
			} else {
				holder.imgFavorite.setImageResource(R.drawable.favorite_holo);
				holder.imgFavorite.setTag(position + "," + position % 2);
			}

			holder.catalogueTitleTxt.setText(titles[position]);

			holder.imgCart.setOnClickListener(onclickListener);

			holder.imgFavorite.setOnClickListener(onclickListener);

			rowView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					String productName = productAdapter.getItem(position);
					String str = categoryName
							+ "/"
							+ productName.toLowerCase(Locale.ENGLISH).replace(
									" ", "_");
					// System.out.println(" product details path " + str);
					Intent intent = new Intent(getActivity(),
							ProductDetailsActivity.class);

					intent.putExtra("catId", categoryId);
					intent.putExtra("path", str);
					intent.putExtra("pos", position);
					intent.putExtra("Title", productName);

					startActivity(intent);

				}
			});

			holder.imgViewFlag.setImageDrawable(CommonUtility
					.getBitmapFromAsset(
							getActivity(),
							categoryName,
							CommonUtility.removeLastChar(
									titles[position]
											.toLowerCase(Locale.ENGLISH))
									.replace(" ", "_")
									+ ".png"));

			return rowView;
		}

		OnClickListener onclickListener = new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				switch (v.getId()) {

				case R.id.cart_image:

					String pos = v.getTag().toString().trim();
					System.out.println(" cart clicked " + pos);

					break;
				case R.id.fav_image:

					String str_pos = v.getTag().toString().trim();
					if (str_pos.split(",")[1].equalsIgnoreCase("1")) {
						((ImageView) v).setImageResource(R.drawable.favorite);
						v.setTag(v
								.getTag()
								.toString()
								.replace(
										String.valueOf(str_pos.charAt(str_pos
												.length() - 1)), "0"));
					} else {
						((ImageView) v)
								.setImageResource(R.drawable.favorite_holo);

						v.setTag(v
								.getTag()
								.toString()
								.replace(
										String.valueOf(str_pos.charAt(str_pos
												.length() - 1)), "1"));
					}
					break;

				default:
					break;
				}
			}
		};
	}

}