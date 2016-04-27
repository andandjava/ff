package com.falconpack.android;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;
import com.falconpack.android.common.CommonUtility;
import com.falconpack.android.common.Constants;
import com.falconpack.android.common.DialogUtility;
import com.falconpack.android.common.NetworkUtility;
import com.falconpack.android.common.PreferenceUtilities;
import com.falconpack.android.customuiadapters.CropOption;
import com.falconpack.android.customuiadapters.CropOptionAdapter;
import com.falconpack.android.model.Categories;
import com.falconpack.android.webservicecall.WebServiceCalls;
import com.squareup.picasso.Picasso;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TripExpensesDetailsFragment extends Fragment implements OnClickListener {

	private Uri mImageCaptureUri;

	private static final int PICK_FROM_CAMERA = 1;
	private static final int CROP_FROM_CAMERA = 2;
	private static final int PICK_FROM_FILE = 3;

	String uploadFilePath = "";
	String uploadFileName = "";

	DatePickerFragment date;
	Calendar calender;

	int mYear, mMonth, mDay, cYear, cMonth, cDay;

	TextView tripTxt, expTxt, descTxt, dateTxt, amtTxt, currencyTxt, payTxt, uploadTxt, uploadBillTxt, viewBillTxt,
			updateTxt, sendTxt, clearTxt;
	EditText tripNameEdt, choose_expEdt, dateEdt, descEdt, amtEdt, currencyEdt, cardEdt;
	ImageView uploadImg;
	LinearLayout send_ll, clear_ll, update_ll;
	String[] currencyArray = new String[] { "ARS", "Rupee", "Dollar", "Dinar", "Euro", "Yen", "Shilling", "Pound",
			"USD", "AED", "SR", "KO", "OMR", "QR" };
	String[] cardArray = new String[] { "Credit Card", "Debit Card", "Cash" };
	AlertDialog.Builder builder;
	AlertDialog alert;

	String TAG_FRAGMENT = "SubFragmentTag";

	ByteArrayOutputStream stream;
	Bitmap uploadBitmap;
	byte[] byteArray;

	AlertDialog imgDialog;
	Boolean isNewTrip = false, isNewExpense = false;
	ArrayList<Categories> categoriesList;
	String[] ids, names;
	private ProgressDialog pDialog;
	String Currency = "", expId = "", tripId = "", encodeStr = "";
	Resources res;
	String bill_path = "";
	String dateOutput = "";

	// Bitmap bitmap

	// String
	public TripExpensesDetailsFragment() {

	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);

		Bundle bundle = this.getArguments();
		if (bundle != null) {

			if (!(Boolean) bundle.get("New").equals(null)) {

				isNewTrip = (Boolean) bundle.get("New");

			}

			if (!(Boolean) bundle.get("NewExpense").equals(null)) {
				isNewExpense = (Boolean) bundle.get("NewExpense");
			}
			try {
				if (!bundle.get("Currency").equals(null)) {
					Currency = (String) bundle.get("Currency");
				}
			} catch (Exception e) {
				e.printStackTrace();
				Currency = "";
			}
			if (!bundle.get("tripId").equals(null)) {
				tripId = (String) bundle.get("tripId");
			}

			if (!bundle.get("expId").equals(null)) {
				expId = (String) bundle.get("expId");
			}

			// System.out.println("string value" + isNewTrip);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_trip_exps_details, container, false);

		res = getActivity().getResources();

		// uploadBitmap = BitmapFactory.decodeResource(getResources(),
		// R.drawable.noimage);

		tripTxt = (TextView) rootView.findViewById(R.id.tv_trip);
		tripNameEdt = (EditText) rootView.findViewById(R.id.trip_name_edt);

		expTxt = (TextView) rootView.findViewById(R.id.tv_exp);
		choose_expEdt = (EditText) rootView.findViewById(R.id.choose_exp_edt);

		descTxt = (TextView) rootView.findViewById(R.id.tv_desc);
		descEdt = (EditText) rootView.findViewById(R.id.desc_edt);

		dateTxt = (TextView) rootView.findViewById(R.id.tv_date);
		dateEdt = (EditText) rootView.findViewById(R.id.date_edt);

		amtTxt = (TextView) rootView.findViewById(R.id.tv_amt);
		amtEdt = (EditText) rootView.findViewById(R.id.amt_edt);

		currencyTxt = (TextView) rootView.findViewById(R.id.tv_cur);
		currencyEdt = (EditText) rootView.findViewById(R.id.cur_edt);
		currencyEdt.setTag(0);

		payTxt = (TextView) rootView.findViewById(R.id.tv_pay);
		cardEdt = (EditText) rootView.findViewById(R.id.card_edt);
		cardEdt.setTag(0);

		uploadTxt = (TextView) rootView.findViewById(R.id.upload_txt);
		uploadImg = (ImageView) rootView.findViewById(R.id.upload_img);
		uploadBillTxt = (TextView) rootView.findViewById(R.id.tv_upload);
		viewBillTxt = (TextView) rootView.findViewById(R.id.tv_viewbill);

		updateTxt = (TextView) rootView.findViewById(R.id.tv_update);
		sendTxt = (TextView) rootView.findViewById(R.id.tv_send);
		clearTxt = (TextView) rootView.findViewById(R.id.tv_clear);

		update_ll = (LinearLayout) rootView.findViewById(R.id.update_layout);
		clear_ll = (LinearLayout) rootView.findViewById(R.id.clear_layout);
		send_ll = (LinearLayout) rootView.findViewById(R.id.sendLayout);

		expTxt.setTypeface(Constants.getProximanova_bold(getActivity()));

		choose_expEdt.setTypeface(Constants.getProximanova_regular(getActivity()));

		tripTxt.setTypeface(Constants.getProximanova_bold(getActivity()));
		tripNameEdt.setTypeface(Constants.getProximanova_regular(getActivity()));

		descTxt.setTypeface(Constants.getProximanova_bold(getActivity()));
		descEdt.setTypeface(Constants.getProximanova_regular(getActivity()));

		dateTxt.setTypeface(Constants.getProximanova_bold(getActivity()));
		dateEdt.setTypeface(Constants.getProximanova_regular(getActivity()));

		amtTxt.setTypeface(Constants.getProximanova_bold(getActivity()));
		amtEdt.setTypeface(Constants.getProximanova_regular(getActivity()));

		currencyTxt.setTypeface(Constants.getProximanova_bold(getActivity()));
		currencyEdt.setTypeface(Constants.getProximanova_regular(getActivity()));

		payTxt.setTypeface(Constants.getProximanova_bold(getActivity()));
		cardEdt.setTypeface(Constants.getProximanova_regular(getActivity()));

		uploadTxt.setTypeface(Constants.getProximanova_bold(getActivity()));
		uploadBillTxt.setTypeface(Constants.getProximanova_bold(getActivity()));

		viewBillTxt.setTypeface(Constants.getProximanova_bold(getActivity()));
		choose_expEdt.setTypeface(Constants.getProximanova_regular(getActivity()));

		updateTxt.setTypeface(Constants.getProximanova_regular(getActivity()));
		sendTxt.setTypeface(Constants.getProximanova_regular(getActivity()));
		clearTxt.setTypeface(Constants.getProximanova_regular(getActivity()));

		date = new DatePickerFragment();

		/**
		 * Set Up Current Date Into dialog
		 */
		calender = Calendar.getInstance();

		// Bundle args = new Bundle();
		// args.putInt("year", calender.get(Calendar.YEAR));
		// args.putInt("month", calender.get(Calendar.MONTH));
		// args.putInt("day", calender.get(Calendar.DAY_OF_MONTH));

		mYear = calender.get(Calendar.YEAR);
		mMonth = calender.get(Calendar.MONTH);
		mDay = calender.get(Calendar.DAY_OF_MONTH);

		cYear = mYear;
		cMonth = mMonth;
		cDay = mDay;

		dateEdt.setText(CommonUtility.getMonthValue(String.valueOf(calender.get(Calendar.MONTH) + 1)) + "-"
				+ calender.get(Calendar.DAY_OF_MONTH) + "-" + +calender.get(Calendar.YEAR));

		MainActivity.tv_header.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (MainActivity.mDrawerLayout.isDrawerOpen(MainActivity.mDrawerList)) {
					MainActivity.mDrawerLayout.closeDrawer(MainActivity.mDrawerList);
				}

				MainActivity.getActionBarIcons("");
				FragmentTransaction ft = getFragmentManager().beginTransaction();
				try {
					Fragment fragment = new TripExpensesFragment();
					Bundle bundle = new Bundle();
					bundle.putString("expId", "");
					bundle.putBoolean("New", false);
					bundle.putBoolean("NewExpense", false);
					bundle.putString("tripId", CommonUtility.tripId);
					if (!isNewTrip.equals(null)) {

						if (isNewTrip) {

							// MainActivity.setActionTitle("Expense
							// Management");
							ft.replace(R.id.frame_container, new ExpenseManagmentFragment())
									.addToBackStack(res.getString(R.string.expsmangfrag));

						} else {

							// MainActivity
							// .setActionTitle("Las Vegas Trip - Nov 2013");

							fragment.setArguments(bundle);
							ft.replace(R.id.frame_container, fragment)
									.addToBackStack(res.getString(R.string.tripexpfrag));
						}

					} else {
						fragment.setArguments(bundle);
						ft.replace(R.id.frame_container, fragment).addToBackStack(res.getString(R.string.tripexpfrag));
					}

				} catch (NullPointerException ne) {
					ne.printStackTrace();
					ft.replace(R.id.frame_container, new TripExpensesFragment())
							.addToBackStack(res.getString(R.string.tripexpfrag));
				}

				ft.commit();
			}
		});

		String[] items = new String[] { "Camera Snapshot", "Pick From Gallery" };

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.select_dialog_item,
				items);

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

		builder.setTitle("Choose Attachment");

		builder.setAdapter(adapter, new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int item) {

				// pick from camera
				if (item == 0) {

					Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

					try {
						// intent.putExtra("return-data", true);
						File f = new File(android.os.Environment.getExternalStorageDirectory(), "uploadImg.jpg");
						intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
						startActivityForResult(intent, PICK_FROM_CAMERA);
					} catch (ActivityNotFoundException e) {
						e.printStackTrace();
					}

				} else {

					// pick from file
					Intent intent = new Intent();
					intent.setType("image/*");
					intent.setAction(Intent.ACTION_GET_CONTENT);

					startActivityForResult(Intent.createChooser(intent, "Complete action using"), PICK_FROM_FILE);
				}

				// if (item == 0) {
				// Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				//
				// mImageCaptureUri = Uri.fromFile(new File(Environment
				// .getExternalStorageDirectory(), "uploadImg"
				// + String.valueOf(System.currentTimeMillis())
				// + ".jpg"));
				// intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT,
				// mImageCaptureUri);
				//
				// try {
				// intent.putExtra("return-data", true);
				//
				// startActivityForResult(intent, PICK_FROM_CAMERA);
				// } catch (ActivityNotFoundException e) {
				// e.printStackTrace();
				// }
				// } else { // pick from file
				// Intent intent = new Intent();
				//
				// intent.setType("image/*");
				// intent.setAction(Intent.ACTION_GET_CONTENT);
				//
				// startActivityForResult(Intent.createChooser(intent,
				// "Complete action using"), PICK_FROM_FILE);
				// }

			}
		});

		imgDialog = builder.create();

		if (NetworkUtility.checkInternetConnection(getActivity())) {

			new ExpensesTypeASyncTask().execute();

		} else {

			DialogUtility.ShowMessage(Constants.newtWorkMsg,getActivity());

		}

		if (Currency.toString().trim().length() > 0) {
			currencyEdt.setText(Currency);
			currencyEdt.setClickable(false);
			currencyEdt.setEnabled(false);
			currencyEdt.setFocusable(false);

			currencyEdt.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
		}

		viewBillTxt.setOnClickListener(this);
		clear_ll.setOnClickListener(this);
		update_ll.setOnClickListener(this);
		uploadBillTxt.setOnClickListener(this);
		choose_expEdt.setOnClickListener(this);
		dateEdt.setOnClickListener(this);
		currencyEdt.setOnClickListener(this);
		cardEdt.setOnClickListener(this);

		return rootView;
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()) {

		case R.id.choose_exp_edt:

			DialogUtility.getChooseDialogue(getActivity(), names, ids, choose_expEdt);

			break;

		case R.id.cur_edt:

			if (Currency.toString().trim().length() > 0) {
				currencyEdt.setText(Currency);
				currencyEdt.setClickable(false);
				currencyEdt.setEnabled(false);
				currencyEdt.setFocusable(false);

				currencyEdt.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
			} else {

				builder = new AlertDialog.Builder(getActivity());
				builder.setTitle("Choose Currencey");
				// Integer.parseInt(currencyEdt.getTag().toString())
				builder.setSingleChoiceItems(currencyArray,
						Arrays.asList(currencyArray).indexOf(currencyEdt.getText().toString()),
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								currencyEdt.setText(currencyArray[which]);
								currencyEdt.setTag(which);
								dialog.dismiss();
							}
						});

				AlertDialog alert = builder.create();
				alert.show();
			}
			break;

		case R.id.card_edt:

			cardArray = new String[] { "Credit Card", "Debit Card", "Cash" };

			builder = new AlertDialog.Builder(getActivity());
			builder.setTitle("Choose Payment mode");

			builder.setSingleChoiceItems(cardArray, Arrays.asList(cardArray).indexOf(cardEdt.getText().toString()),
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							cardEdt.setText(cardArray[which]);
							cardEdt.setTag(which);
							dialog.dismiss();
						}
					});

			alert = builder.create();
			alert.show();

			break;

		case R.id.date_edt:

			DialogFragment fragment = new SelectDateFragment();
			fragment.show(getFragmentManager(), "Date Picker");

			// final Calendar c = Calendar.getInstance();
			//
			// final int maxYear = c.get(Calendar.YEAR); // this year ( 2011 ) -
			// 20
			// // =
			// // 1991
			// final int maxMonth = c.get(Calendar.MONTH);
			// final int maxDay = c.get(Calendar.DAY_OF_MONTH);
			//
			// final int minYear = mYear;
			// final int minMonth = mMonth; // january
			// final int minDay = mDay;
			//
			// Dialog customdialog = new Dialog(getActivity());
			// customdialog.setCancelable(true);
			// customdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			// customdialog.setContentView(R.layout.dialog_date_picker);
			// customdialog.show();
			// // dialogDetails = dialogbuilder.create();
			// final Button date_time_setBtn = (Button) customdialog
			// .findViewById(R.id.date_time_set);
			// final DatePicker BirthDateDP = (DatePicker) customdialog
			// .findViewById(R.id.date_picker);
			// BirthDateDP.init(minYear, minMonth, minDay,
			// new OnDateChangedListener() {
			//
			// public void onDateChanged(DatePicker view, int year,
			// int monthOfYear, int dayOfMonth) {
			//
			// if (year > maxYear || monthOfYear > maxMonth
			// && year == maxYear || dayOfMonth > maxDay
			// && year == maxYear
			// && monthOfYear == maxMonth) {
			//
			// // Toast.makeText(DatePickerActivity.this,"max year",
			// // Toast.LENGTH_SHORT).show();
			//
			// view.updateDate(maxYear, maxMonth, maxDay);
			//
			// dateOutput = String.format("%02d-%02d-%04d",
			// maxYear, maxMonth + 1, maxDay);
			// Toast.makeText(getActivity(),
			// "maxYear " + dateOutput,
			// Toast.LENGTH_SHORT).show();
			//
			// } else if (year < minYear || monthOfYear < minMonth
			// && year == minYear || dayOfMonth < minDay
			// && year == minYear
			// && monthOfYear == minMonth) {
			//
			// // Toast.makeText(DatePickerActivity.this,"min year",
			// // Toast.LENGTH_SHORT).show();
			//
			// view.updateDate(minYear, minMonth, minDay);
			//
			// dateOutput = String.format("%02d-%02d-%04d",
			// minYear, minMonth + 1, minDay);
			//
			// // Toast.makeText(ManageShowing.this,dateOutput,
			// // Toast.LENGTH_SHORT).show();
			//
			// } else {
			//
			// // Toast.makeText(ManageShowing.this,"else",
			// // Toast.LENGTH_SHORT).show();
			//
			// dateOutput = String.format("%02d-%02d-%04d",
			// year, monthOfYear + 1, dayOfMonth);
			//
			// // Toast.makeText(ManageShowing.this,dateOutput,
			// // Toast.LENGTH_SHORT).show();
			//
			// }
			//
			// // BirthDateDP.setText(dateOutput);
			//
			// System.out.println("date output " + dateOutput);
			//
			// }
			// }); // DateDP.init()
			//
			// date_time_setBtn.setOnClickListener(new OnClickListener() {
			//
			// @Override
			// public void onClick(View v) {
			// // TODO Auto-generated method stub
			// dateEdt.setText(dateOutput);
			// }
			// });

			break;

		case R.id.tv_upload:

			imgDialog.show();

			break;

		case R.id.tv_viewbill:

			final Dialog dialog = new Dialog(getActivity(), android.R.style.Theme_Translucent_NoTitleBar);
			dialog.setCancelable(true);
			dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			dialog.setContentView(R.layout.dialog_viewbill);

			ImageView bill_img = (ImageView) dialog.findViewById(R.id.iv_bill);

			TextView tv_ok = (TextView) dialog.findViewById(R.id.tv_ok);
			tv_ok.setTypeface(Constants.getProximanova_semibold(getActivity()));
			bill_img.setImageBitmap(uploadBitmap);

			TextView backButton = (TextView) dialog.findViewById(R.id.back_btn);
			backButton.setText("View Bill");

			backButton.setTypeface(Constants.getProximanova_semibold(getActivity()));

			if (!bill_path.equalsIgnoreCase("")) {
				Picasso picasso = Picasso.with(getActivity());
				picasso.load(bill_path).error(R.drawable.noimage).placeholder(R.drawable.noimage).into(bill_img);
			}

			backButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					dialog.dismiss();
				}
			});

			tv_ok.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					dialog.cancel();
				}
			});

			dialog.show();

			break;

		case R.id.update_layout:

			String tripName = "";
			if (tripNameEdt.isShown()) {
				tripName = tripNameEdt.getText().toString().trim();
			} else {
				tripName = CommonUtility.trip_name;
			}
			String desc = descEdt.getText().toString().trim();
			String amt = amtEdt.getText().toString().trim();

			if (tripName.length() > 0 && amt.length() > 0) {

				if (uploadBitmap != null || !bill_path.equals("")) {
				
				if (desc.length() <= 0) {

					
					final Dialog offerdialog1 = new Dialog(getActivity());
					offerdialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
					offerdialog1.setCanceledOnTouchOutside(false);
					offerdialog1.setContentView(R.layout.alert_custom_dialog);

					TextView okTxt = (TextView) offerdialog1.findViewById(R.id.ok);
					TextView cancelTxt = (TextView) offerdialog1.findViewById(R.id.cancel);
					TextView message1 = (TextView) offerdialog1.findViewById(R.id.message);
					okTxt.setTypeface(Constants.getProximanova_regular(getActivity()));
					cancelTxt.setTypeface(Constants.getProximanova_regular(getActivity()));
					message1.setTypeface(Constants.getProximanova_regular(getActivity()));
					message1.setText("Description is empty do you want to proceed? ");
					okTxt.setOnClickListener(new View.OnClickListener() {

						@Override
						public void onClick(View v) {
							offerdialog1.dismiss();
							new ExpensesDetailsUploadingASyncTask().execute();
						}
					});

					cancelTxt.setOnClickListener(new View.OnClickListener() {

						@Override
						public void onClick(View v) {
							offerdialog1.cancel();
						}
					});

					// show dialog
					offerdialog1.show();

				} else {

					new ExpensesDetailsUploadingASyncTask().execute();
				}

				}else{
					DialogUtility.ShowMessage("Please upload bill ", getActivity());
				}
			} else

			if (tripName.length() <= 0 || amt.length() <= 0) {

				// new ExpensesDetailsUploadingASyncTask().execute();

				if (tripName.length() <= 0) {

					DialogUtility.ShowMessage("Please enter trip name", getActivity());

				} else if (amt.length() <= 0) {

					DialogUtility.ShowMessage("Please enter amount", getActivity());
				}
			}

			break;

		case R.id.clear_layout:

			tripNameEdt.setText("");
			choose_expEdt.setTag(ids[0]);
			choose_expEdt.setText(names[0]);
			currencyEdt.setText(currencyArray[0]);
			currencyEdt.setTag(0);
			descEdt.setText("");
			dateEdt.setText(cDay + "-" + CommonUtility.getMonthValue(String.valueOf(cMonth + 1)) + "-" + cYear);
			amtEdt.setText("");

			cardEdt.setText(cardArray[0]);
			cardEdt.setTag(0);
			bill_path="";
			if (uploadBitmap != null) {
				uploadBitmap.recycle();
				uploadBitmap = null;
				bill_path="";
			}

//			uploadBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.noimage);
			uploadImg.setImageResource(R.drawable.noimage);
			// viewBillTxt.setVisibility(View.GONE);
			viewBillTxt.setTextColor(res.getColor(R.color.light_hash));
			viewBillTxt.setEnabled(false);
			break;

		default:
			break;

		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);

		getActivity();

		if (resultCode != Activity.RESULT_OK)
			return;

		switch (requestCode) {

		case PICK_FROM_CAMERA:
			// doCrop();

			File f = new File(Environment.getExternalStorageDirectory(), "uploadImg.jpg");
			BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();

			if (uploadBitmap != null) {
				uploadBitmap.recycle();
				uploadBitmap = null;
			}

			//
			// ExifInterface ei = new
			// ExifInterface(Environment.getExternalStorageDirectory()+"/uploadImg.jpg");
			// int orientation =
			// ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
			// ExifInterface.ORIENTATION_NORMAL);
			//
			// switch(orientation) {
			// case ExifInterface.ORIENTATION_ROTATE_90:
			// rotateImage(uploadBitmap, 90);
			// break;
			// case ExifInterface.ORIENTATION_ROTATE_180:
			// rotateImage(uploadBitmap, 180);
			// break;
			// // etc.
			// }
			//
			// uploadBitmap = ;

			uploadBitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeFile(f.getAbsolutePath(), bitmapOptions), 240,
					240, false);
			stream = new ByteArrayOutputStream();
			uploadBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
			uploadImg.setImageBitmap(uploadBitmap);
			byteArray = stream.toByteArray();

			break;

		case PICK_FROM_FILE:

			// mImageCaptureUri = data.getData();
			// doCrop();
			//
			// uploadFilePath = mImageCaptureUri.getPath();

			Uri pickedImage = data.getData();

			try {

				stream = new ByteArrayOutputStream();
				
				if (uploadBitmap != null) {
					uploadBitmap.recycle();
					uploadBitmap = null;
				}
				
				// uploadBitmap =
				// MediaStore.Images.Media.getBitmap(getActivity()
				// .getContentResolver(), pickedImage);

				uploadBitmap = Bitmap.createScaledBitmap(
						MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), pickedImage), 240, 240,
						false);

				uploadImg.setImageBitmap(uploadBitmap);

				uploadBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);

				byteArray = stream.toByteArray();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;

		case CROP_FROM_CAMERA:
			
			Bundle extras = data.getExtras();

			// if (extras != null) {
			//
			// if (uploadBitmap != null) {
			// uploadBitmap.recycle();
			// uploadBitmap = null;
			// }
			// uploadBitmap = extras.getParcelable("data");
			//
			// ByteArrayOutputStream stream = new ByteArrayOutputStream();
			//
			// uploadBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
			// byteArray = stream.toByteArray();
			// uploadImg.setImageBitmap(uploadBitmap);
			// // byteStr = Base64.encodeToString(byteArray, Base64.DEFAULT);
			// }
			//
			// File f = new File(mImageCaptureUri.getPath());
			//
			// uploadFilePath = mImageCaptureUri.getPath();

			// uploadFileName = "a.jpg";

			// Toast.makeText(getActivity(),
			// uploadFilePath + "/" + uploadFileName, Toast.LENGTH_LONG)
			// .show();

			// if (f.exists())
			// f.delete();

			break;
		}

		viewBillTxt.setTextColor(Color.WHITE);
		viewBillTxt.setEnabled(true);
	}

	private void doCrop() {
		final ArrayList<CropOption> cropOptions = new ArrayList<CropOption>();

		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setType("image/*");

		List<ResolveInfo> list = getActivity().getPackageManager().queryIntentActivities(intent, 0);

		int size = list.size();

		if (size == 0) {

			String dialogmsg = "Can not find image crop app";
			// showDialog();
			// showCustomDialog(dialogmsg);

			DialogUtility.ShowMessage(dialogmsg, getActivity());

			return;
		} else {
			intent.setData(mImageCaptureUri);

			intent.putExtra("outputX", 200);
			intent.putExtra("outputY", 200);
			intent.putExtra("aspectX", 1);
			intent.putExtra("aspectY", 1);
			intent.putExtra("scale", true);
			intent.putExtra("return-data", true);

			if (size == 1) {
				Intent i = new Intent(intent);
				ResolveInfo res = list.get(0);

				i.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));

				startActivityForResult(i, CROP_FROM_CAMERA);
			} else {
				for (ResolveInfo res : list) {
					final CropOption co = new CropOption();

					co.title = getActivity().getPackageManager().getApplicationLabel(res.activityInfo.applicationInfo);
					co.icon = getActivity().getPackageManager().getApplicationIcon(res.activityInfo.applicationInfo);
					co.appIntent = new Intent(intent);

					co.appIntent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));

					cropOptions.add(co);
				}

				CropOptionAdapter adapter = new CropOptionAdapter(getActivity(), cropOptions);

				AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
				builder.setTitle("Choose Crop App");
				builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int item) {
						startActivityForResult(cropOptions.get(item).appIntent, CROP_FROM_CAMERA);
					}
				});

				builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
					public void onCancel(DialogInterface dialog) {

						try {
							if (mImageCaptureUri != null) {

								getActivity().getContentResolver().delete(mImageCaptureUri, null, null);
								mImageCaptureUri = null;
							}
						} catch (Exception e) {

							e.printStackTrace();
						}
					}
				});

				AlertDialog alert = builder.create();

				alert.show();
			}
		}
	}

	class SelectDateFragment extends DialogFragment implements

			DatePickerDialog.OnDateSetListener {

		public Dialog onCreateDialog(Bundle savedInstanceState) {

			System.out.println("entrering on create dialog");

			Calendar c = Calendar.getInstance();

			System.out.println(" date on click " + mYear + "-" + mMonth + "-" + mDay);

			DatePickerDialog dPickerDialog = new DatePickerDialog(getActivity(), this, mYear, mMonth, mDay);

			dPickerDialog.getDatePicker().setMaxDate(c.getTimeInMillis());

			// Date today = c.getTime();
			// c.add(Calendar.YEAR, -4); // to get previous year add -1
			// Date nextYear = c.getTime();
			// dPickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());

			return dPickerDialog;
			// it will return dialog setting date with mYera,MMonth and MDay
		}

		@Override
		public void onDateSet(android.widget.DatePicker view, int year, int monthOfYear, int dayOfMonth) {

			System.out.println("ondate set year=" + year + "day=" + dayOfMonth + "month=" + monthOfYear);

			mYear = year;
			mMonth = monthOfYear;
			mDay = dayOfMonth;

			onPopulateSet(year, monthOfYear + 1, dayOfMonth);
		}

		protected void onPrepareDialog(int id, Dialog dialog) {
			((DatePickerDialog) dialog).updateDate(mYear, mMonth - 1, mDay);

			System.out.println("ondate set year...............");
		}

		// set the selected date in the edit text
		private void onPopulateSet(int year, int i, int dayOfMonth) {

			dateEdt.setText(CommonUtility.getMonthValue(String.valueOf(i)) + "-" + dayOfMonth + "-" + year);
			System.out.println("enetring on populate Set");
		}
	}

	private class ExpensesTypeASyncTask extends AsyncTask<String, String, String> {

		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(getActivity());
			pDialog.setMessage("Loading ...");
			pDialog.setCancelable(false);
			pDialog.show();
		}

		protected String doInBackground(String... args) {

			List<NameValuePair> pairs = new ArrayList<NameValuePair>();

			// pairs.add(new BasicNameValuePair("apikey", "ETG123"));
			pairs.add(new BasicNameValuePair("deviceid", CommonUtility.getDeviceId(getActivity())));
			pairs.add(new BasicNameValuePair("id", PreferenceUtilities.getSavedUserData(getActivity()).getId()));

			return WebServiceCalls.postValues(pairs, "expensetypes").toString();

		}

		protected void onPostExecute(String jsonResult) {

			System.out.println(" result " + jsonResult);

			if (!TextUtils.isEmpty(jsonResult) || jsonResult.length() > 0) {

				try {

					categoriesList = new ArrayList<Categories>();

					JSONObject jsonObjMain = new JSONObject(jsonResult);

					System.out.println(" message " + jsonObjMain.getJSONArray("expensetypes"));

					JSONArray jArray = jsonObjMain.getJSONArray("expensetypes");

					ids = new String[jArray.length()];
					names = new String[jArray.length()];

					for (int i = 0; i < jArray.length(); i++) {

						ids[i] = CommonUtility.getValueFromJsonObject(jArray.getJSONObject(i), "id");
						names[i] = CommonUtility.getValueFromJsonObject(jArray.getJSONObject(i), "name");
					}

					choose_expEdt.setTag(ids[0]);
					choose_expEdt.setText(names[0]);

					try {
						if (isNewTrip) {

							MainActivity.setActionTitle("New Trip");

							updateTxt.setText("Add");

							tripTxt.setVisibility(View.VISIBLE);
							tripNameEdt.setVisibility(View.VISIBLE);
							viewBillTxt.setTextColor(res.getColor(R.color.light_hash));
							viewBillTxt.setEnabled(false);

						} else if (isNewExpense) {
							updateTxt.setText("Add");
							if (Currency.toString().trim().length() > 0) {
								currencyEdt.setText(Currency);
								currencyEdt.setClickable(false);
							}

							viewBillTxt.setTextColor(res.getColor(R.color.light_hash));
							viewBillTxt.setEnabled(false);
						} else {
							new ExpensesDetailsASyncTask().execute();

						}
					} catch (NullPointerException ne) {
						new ExpensesDetailsASyncTask().execute();
					}

				} catch (Exception e) {

					e.printStackTrace();

					// if (null != pDialog && pDialog.isShowing()) {
					// pDialog.dismiss();
					// }

					DialogUtility.ShowMessage("Unable to retrieve data from Server", getActivity());

					// Toast.makeText(getActivity(), e.getMessage(),
					// Toast.LENGTH_SHORT).show();
				}
			} else {

				// if (null != pDialog && pDialog.isShowing()) {
				// pDialog.dismiss();
				// }

				DialogUtility.ShowMessage("Unable to retrieve data from Server", getActivity());

			}

			if (null != pDialog && pDialog.isShowing()) {
				pDialog.dismiss();
			}
		}
	}

	private class ExpensesDetailsASyncTask extends AsyncTask<String, String, String> {

		// private ProgressDialog pDialog;

		protected void onPreExecute() {
			super.onPreExecute();
			// pDialog = new ProgressDialog(getActivity());
			// pDialog.setMessage("Loading ...");
			// pDialog.setCancelable(false);
			// pDialog.show();
		}

		protected String doInBackground(String... args) {

			List<NameValuePair> pairs = new ArrayList<NameValuePair>();

			// pairs.add(new BasicNameValuePair("apikey", "ETG123"));
			pairs.add(new BasicNameValuePair("deviceid", CommonUtility.getDeviceId(getActivity())));
			pairs.add(new BasicNameValuePair("id", PreferenceUtilities.getSavedUserData(getActivity()).getId()));
			pairs.add(new BasicNameValuePair("expense_id", CommonUtility.expId));

			return WebServiceCalls.postValues(pairs, "expense_id").toString();

		}

		protected void onPostExecute(String jsonResult) {

			System.out.println(" result " + jsonResult);

			// {"status":"1","message":"Expense Details Retrived",
			// "tripid":"1","expensetype_id":"2","user_id":"49","trip_name":"UK
			// Trip",
			// "journey_date":"2015-08-27","amount":"1000.00","currency":"USD",
			// "payment_mode":"Cash","bill_path":"Testpath"}

			if (!TextUtils.isEmpty(jsonResult) || jsonResult.length() > 0) {

				try {

					categoriesList = new ArrayList<Categories>();

					JSONObject jsonObjMain = new JSONObject(jsonResult);

					if (CommonUtility.getValueFromJsonObject(jsonObjMain, "status").equalsIgnoreCase("1")) {
						// String message =
						// CommonUtility.getValueFromJsonObject(
						// jsonObjMain, "message");
						//
						// String tripid = CommonUtility.getValueFromJsonObject(
						// jsonObjMain, "tripid");

						String expensetype_id = CommonUtility.getValueFromJsonObject(jsonObjMain, "expensetype_id");

						// String user_id =
						// CommonUtility.getValueFromJsonObject(
						// jsonObjMain, "user_id");

						String trip_name = CommonUtility.getValueFromJsonObject(jsonObjMain, "trip_name");// .replace("%20",
																											// "
																											// ");

						String description = CommonUtility.getValueFromJsonObject(jsonObjMain, "description");// .replace("%20",
																												// "
																												// ");

						String journey_date = CommonUtility.getValueFromJsonObject(jsonObjMain, "journey_date");

						String amount = CommonUtility.getValueFromJsonObject(jsonObjMain, "amount");

						String currency = CommonUtility.getValueFromJsonObject(jsonObjMain, "currency");

						String payment_mode = CommonUtility.getValueFromJsonObject(jsonObjMain, "payment_mode");

						bill_path = CommonUtility.getValueFromJsonObject(jsonObjMain, "bill_path");

						Picasso picasso = Picasso.with(getActivity());
						picasso.load(bill_path).error(R.drawable.noimage).into(uploadImg);

						
						MainActivity.setActionTitle(trip_name);

						choose_expEdt.setTag(expensetype_id);
						choose_expEdt.setText(names[Arrays.asList(ids).indexOf(expensetype_id)]);

						Calendar mCalendar = CommonUtility.getStringToCalendarFormat(journey_date);

						mMonth = mCalendar.get(Calendar.MONTH);
						mDay = mCalendar.get(Calendar.DAY_OF_MONTH);
						mYear = mCalendar.get(Calendar.YEAR);

						System.out.println("mYear " + mMonth + "-" + mDay + "-" + mYear);

						journey_date = CommonUtility.getMonthValue(String.valueOf(mMonth + 1)) + "-" + mDay + "-"
								+ mYear;

						System.out.println(" retrieved date " + journey_date);

						dateEdt.setText(CommonUtility.setTextEdt(journey_date));
						descEdt.setText(CommonUtility.setTextEdt(description));
						amtEdt.setText(CommonUtility.setTextEdt(amount));
						currencyEdt.setText(CommonUtility.setTextEdt(currency));
						currencyEdt.setClickable(false);
						// currencyEdt.setEd
						cardEdt.setText(CommonUtility.setTextEdt(payment_mode));
					} else {
						DialogUtility.ShowMessage(CommonUtility.getValueFromJsonObject(jsonObjMain, "message"),
								getActivity());
					}

				} catch (Exception e) {
					e.printStackTrace();
					if (null != pDialog && pDialog.isShowing()) {
						pDialog.dismiss();
					}
					DialogUtility.ShowMessage("Unable to retrieve data from Server", getActivity());
					// Toast.makeText(getActivity(), e.getMessage(),
					// Toast.LENGTH_SHORT).show();
				}
			} else {
				if (null != pDialog && pDialog.isShowing()) {
					pDialog.dismiss();
				}
				DialogUtility.ShowMessage("Unable to retrieve data from Server", getActivity());
			}

			if (null != pDialog && pDialog.isShowing()) {
				pDialog.dismiss();
			}
		}
	}

	private class ExpensesDetailsUploadingASyncTask extends AsyncTask<String, String, String> {

		private ProgressDialog pDialog1;

		protected void onPreExecute() {
			super.onPreExecute();
			pDialog1 = new ProgressDialog(getActivity());
			pDialog1.setMessage("Loading ...");
			pDialog1.setCancelable(false);
			pDialog1.show();
		}

		protected String doInBackground(String... args) {

			// 'apikey' => 'ETG123',
			// 'deviceid' => '1234',
			// 'user_id' => '41', // only use id for update purpose not for
			// adding
			// 'expensetype' => '2',
			// 'expense_id' => '1',
			// 'tripid' => '410859',
			// 'description' => 'Mysore Trip bankock changed',
			// 'journey_date' => '26-09-2015',
			// 'amount' => '1014.00',
			// 'currency' => 'USD',
			// 'payment_mode' => 'Bank 2 changed',
			// 'bill_path' => 'image encoded text',
			// 'add' => '0', // edit expense

			String tripName = "", desc = "", datStr = "", amt = "", curency = "", cardtype = "";

			tripName = CommonUtility.getTextFromEdt(tripNameEdt);// tripNameEdt.getText().toString().trim();

			// .replace(" ", "%20");

			// desc = descEdt.getText().toString().trim();// .replace(" ",
			// "%20");
			desc = CommonUtility.getTextFromEdt(descEdt);

			datStr = CommonUtility.getTextFromEdt(dateEdt);

			amt = CommonUtility.getTextFromEdt(amtEdt);

			curency = CommonUtility.getTextFromEdt(currencyEdt);// currencyEdt.getText().toString().trim();

			cardtype = CommonUtility.getTextFromEdt(cardEdt);// cardEdt.getText().toString().trim();

			
			if (uploadBitmap != null) {
				encodeStr = encodeTobase64(uploadBitmap);
			} else {
				if (!bill_path.equalsIgnoreCase("")) {
					encodeStr = bill_path;
				}
			}

			List<NameValuePair> pairs = new ArrayList<NameValuePair>();

			// pairs.add(new BasicNameValuePair("apikey", "ETG123"));
			pairs.add(new BasicNameValuePair("deviceid", CommonUtility.getDeviceId(getActivity())));
			pairs.add(new BasicNameValuePair("user_id", PreferenceUtilities.getSavedUserData(getActivity()).getId()));

			if (isNewTrip) {

				pairs.add(new BasicNameValuePair("trip_name", tripName));
				pairs.add(new BasicNameValuePair("add", "1"));

			} else {

				System.out.println("isnewexpense " + isNewExpense);

				if (isNewExpense) {
					pairs.add(new BasicNameValuePair("trip_name", CommonUtility.trip_name));
					pairs.add(new BasicNameValuePair("add", "1"));
					pairs.add(new BasicNameValuePair("tripid", CommonUtility.tripId));
				} else {
					pairs.add(new BasicNameValuePair("tripid", CommonUtility.tripId));
					pairs.add(new BasicNameValuePair("add", "0"));
					pairs.add(new BasicNameValuePair("expense_id", CommonUtility.expId));
				}
			}

			pairs.add(new BasicNameValuePair("expensetype", choose_expEdt.getTag().toString()));
			pairs.add(new BasicNameValuePair("description", desc));
			pairs.add(new BasicNameValuePair("journey_date", datStr));
			pairs.add(new BasicNameValuePair("amount", amt));
			pairs.add(new BasicNameValuePair("currency", curency));
			pairs.add(new BasicNameValuePair("payment_mode", cardtype));
			pairs.add(new BasicNameValuePair("bill_path", encodeStr));

			return WebServiceCalls.postValues(pairs, "add_expense").toString();

		}

		protected void onPostExecute(String jsonResult) {

			System.out.println(" result " + jsonResult);

			// {"status":"1","message":"Expense Details Retrived",
			// "tripid":"1","expensetype_id":"2","user_id":"49","trip_name":"UK
			// Trip",
			// "journey_date":"2015-08-27","amount":"1000.00","currency":"USD",
			// "payment_mode":"Cash","bill_path":"Testpath"}

			if (!TextUtils.isEmpty(jsonResult) || jsonResult.length() > 0) {

				try {

					// categoriesList = new ArrayList<Categories>();
					JSONObject jsonObjMain = new JSONObject(jsonResult);

					if (CommonUtility.getValueFromJsonObject(jsonObjMain, "status").equalsIgnoreCase("1")) {

						if (MainActivity.mDrawerLayout.isDrawerOpen(MainActivity.mDrawerList)) {
							MainActivity.mDrawerLayout.closeDrawer(MainActivity.mDrawerList);
						}
						MainActivity.getActionBarIcons("");

						FragmentTransaction ft = getFragmentManager().beginTransaction();

						if (isNewTrip) {

							ft.replace(R.id.frame_container, new ExpenseManagmentFragment())
									.addToBackStack(res.getString(R.string.expsmangfrag));
						} else {

							ft.replace(R.id.frame_container, new TripExpensesFragment())
									.addToBackStack(res.getString(R.string.tripexpfrag));

						}

						ft.commit();

					} else {

						DialogUtility.ShowMessage(CommonUtility.getValueFromJsonObject(jsonObjMain, "message"),
								getActivity());
					}

				} catch (Exception e) {

					e.printStackTrace();
					if (null != pDialog1 && pDialog1.isShowing()) {
						pDialog1.dismiss();
					}
					DialogUtility.ShowMessage("Unable to updata data on Server", getActivity());
					// Toast.makeText(getActivity(), e.getMessage(),
					// Toast.LENGTH_SHORT).show();
				}

			} else {

				if (null != pDialog1 && pDialog1.isShowing()) {
					pDialog1.dismiss();
				}

				DialogUtility.ShowMessage("Unable to updata data on Server", getActivity());
			}

			if (null != pDialog1 && pDialog1.isShowing()) {
				pDialog1.dismiss();
			}
		}
	}

	public String encodeTobase64(Bitmap image) {
		Bitmap immagex = image;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		immagex.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		byte[] b = baos.toByteArray();
		String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);

		Log.e("LOOK", imageEncoded);
		return imageEncoded;
	}

	public Bitmap decodeBase64(String input) {
		byte[] decodedByte = Base64.decode(input, 0);
		return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
	}
}