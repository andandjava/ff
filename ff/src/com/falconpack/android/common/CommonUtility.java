package com.falconpack.android.common;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONException;
import org.json.JSONObject;

import com.falconpack.android.R;
import com.falconpack.android.model.Categories;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.TextView;

public class CommonUtility {

	public static String[] leaveTypeIdsArray;
	public static ArrayList<String> leavTypesArrayList = new ArrayList<String>();
	public static ArrayList<String> notificationList = new ArrayList<String>();
	public static ArrayList<Categories> categoriesList = new ArrayList<Categories>();

	// public static ArrayList<Categories> notificationList = new
	// ArrayList<Categories>();
	public static ArrayList<Categories> generalNotificationList = new ArrayList<Categories>();
	public static ArrayList<Categories> documentNotificatonArrayList = new ArrayList<Categories>();

	public static String ALL_USER_TYPES = "all_user_types";
	public static String LOGIN_METHOD = "user_validate";
	public static String tripId = "", expId = "", trip_name = "",
			notificationFlag_ids = "", documentFlag_ids = "", docId = "0",
			docName = "";

	public static String isManager = "";

	// public static ArrayList<Categories> categoriesList = new
	// ArrayList<Categories>();

	public static boolean eMailValidation(String emailstring) {
		Pattern emailPattern = Pattern.compile(".+@.+\\.[a-z]+");
		Matcher emailMatcher = emailPattern.matcher(emailstring);
		return emailMatcher.matches();
	}

	public static String ShowMeridian(String input) {
		String type = "";
		DateFormat inputFormat = new SimpleDateFormat("HH:mm");
		DateFormat outputFormat = new SimpleDateFormat("a");
		try {
			type = outputFormat.format(inputFormat.parse(input));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return type;
	}

	public static String getDeviceId(Activity activity) {

		TelephonyManager telephonyManager = (TelephonyManager) activity
				.getSystemService(Context.TELEPHONY_SERVICE);

		// String string = telephonyManager.getDeviceId();

		return telephonyManager.getDeviceId();
	}

	public static void getHeaderTitle(String title, Activity activity) {

		TextView backButton = (TextView) activity.findViewById(R.id.back_btn);
		backButton.setText(title);
		backButton.setOnClickListener(new BackListener(activity));

		backButton.setTypeface(Constants.getProximanova_semibold(activity));
		hideKeyBoard(activity, backButton);
	}

	public static String getDayNumberSuffix(int day) {

		if (day >= 11 && day <= 13) {
			return "th";
		}

		switch (day % 10) {
		case 1:
			return "st";
		case 2:
			return "nd";
		case 3:
			return "rd";
		default:
			return "th";
		}
	}

	public static String getMonthValue(String mon) {
		if (mon.toString().trim().length() == 1) {
			mon = "0" + mon;
		}
		return mon;
	}

	public static String getValueFromJsonObject(JSONObject lJsonObject,
			String pKey) {
		String lValue = "";
		try {

			if (lJsonObject.has(pKey)) {

				if (!lJsonObject.getString(pKey).equalsIgnoreCase("null")) {

					lValue = lJsonObject.getString(pKey);

					if (TextUtils.isEmpty(lValue)
							|| lValue.equalsIgnoreCase("null")) {
						lValue = "";
					}
				}
			} else {
				lValue = "";
			}
		} catch (JSONException e) {
			e.printStackTrace();
			lValue = "";
		}
		return lValue;
	}

	public static JSONObject getJsonObject(String pKey, String pValue) {
		JSONObject lJsonObj = new JSONObject();
		try {
			lJsonObj.put(pKey, pValue);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return lJsonObj;
	}

	public static void startWebView(final Activity activity, String url,
			WebView webView) {

		// Create new webview Client to show progress dialog
		// When opening a url or click on link

		webView.setWebViewClient(new WebViewClient() {
			ProgressDialog progressDialog;

			// If you will not use this method url links are opeen in new brower
			// not in webview
			public boolean shouldOverrideUrlLoading(WebView view, String url) {

				if (url.contains(".pdf")) {
					String pdfurl = "http://docs.google.com/gview?embedded=true&url="
							+ url;
					view.loadUrl(pdfurl);
				} else {
					view.loadUrl(url);
				}

				return true;
			}

			// Show loader on url load
			public void onLoadResource(WebView view, String url) {
				if (progressDialog == null) {
					// in standard case YourActivity.this
					progressDialog = new ProgressDialog(activity);
					progressDialog.setMessage("Loading...");
					if (!((Activity) activity).isFinishing()) {
						// show dialog
						progressDialog.show();
					} else {
						// progressDialog.show();
						// Toast.makeText(mContext, message,
						// Toast.LENGTH_LONG).show();
					}
					// progressDialog
					// progressDialog.show();
				}
			}

			public void onPageFinished(WebView view, String url) {
				try {
					if (null != progressDialog && progressDialog.isShowing()) {
						progressDialog.dismiss();
					}
				} catch (Exception exception) {
					exception.printStackTrace();
				}
			}
		});

		// Javascript inabled on webview
		webView.getSettings().setJavaScriptEnabled(true);

		// Load url in webview
		webView.loadUrl(url);

	}

	public static String[] getImagesNames(Context conetx, String folderName)
			throws IOException {
		AssetManager assetManager = conetx.getAssets();
		System.out.println(" images folder name " + folderName);

		String[] files = assetManager.list(folderName);
		System.out.println(" files length " + files.length);
		// List<String> it = Arrays.asList(files);
		return files;
	}

	public static Drawable getBitmapFromAsset(Activity activity, String path,
			String strName) {
		AssetManager assetManager = activity.getAssets();
		InputStream istr = null;
		try {
			if (path.equalsIgnoreCase("")) {
				path = strName;
			} else {
				path = path.trim() + "/" + strName;
			}
			System.out.println("image path " + path);
			istr = assetManager.open(path);
		} catch (IOException e) {
			e.printStackTrace();
		}

		Drawable drawable = Drawable.createFromStream(istr, null);
		// Bitmap bitmap = BitmapFactory.decodeStream(istr);
		return drawable;
	}

	public static String removeLastChar(String str) {
		return str.substring(0, str.length() - 1);
	}

	public static void hideKeyBoard(Activity activitiy, View v) {

		// View view = activitiy.getCurrentFocus();
		InputMethodManager imm = (InputMethodManager) activitiy
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
	}

	public static String getTextFromEdt(EditText descEdt) {
		// TODO Auto-generated method stub
		String text = descEdt.getText().toString().trim();
		if (text.length() <= 0 || text.equalsIgnoreCase("null")
				|| TextUtils.isEmpty(text)) {
			return "";
		} else {
			return text;
		}
	}

	public static CharSequence setTextEdt(String text) {
		// TODO Auto-generated method stub

		if (text.length() <= 0 || text.equalsIgnoreCase("(null)")
				|| text.equalsIgnoreCase("null") || TextUtils.isEmpty(text)) {
			return "";
		} else {
			return text;
		}
	}

	public static Calendar getStringToCalendarFormat(String str_date) {
		DateFormat formatter = null;
		Date date = null;
		// str_date is in this format --> 1987-08-11
		try {
			formatter = new SimpleDateFormat("yyyy-MM-dd");
			// formatter = new SimpleDateFormat("MM-dd-yyyy");
			date = (Date) formatter.parse(str_date);
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			return cal;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return Calendar.getInstance();
	}
}
