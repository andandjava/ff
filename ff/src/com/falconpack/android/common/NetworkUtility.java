package com.falconpack.android.common;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkUtility {
	public static Context mContext;

	public NetworkUtility(Context context) {
		mContext = context;
	}

	public static boolean checkInternetConnection(Context mContext) {

		ConnectivityManager con_manager = (ConnectivityManager) mContext
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		if (con_manager == null) {
			return false;
		} else {
			NetworkInfo[] info = con_manager.getAllNetworkInfo();
			if (info != null) {
				for (int i = 0; i < info.length; i++) {
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
				}
			}
		}
		return false;
	}

	

	public static String getAllUserTypes() {
		return Constants.MAIN_HOST + "all_user_types";
	}
}