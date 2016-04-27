package com.falconpack.android.common;

import android.content.Context;
import android.graphics.Typeface;

public class Constants {
//	http://172.16.1.120/falcondevapp/services/
	public static String MAIN_HOST = "http://202.65.147.156/falconstgapp/services/";
	public static String newtWorkMsg = "Sorry, Network is not available. Please try again later";
	public static Typeface proximanova_bold, proximanova_regular,
			proximanova_semibold;

	public static Typeface getProximanova_bold(Context context) {
		return Typeface.createFromAsset(context.getAssets(),
				"fonts/proximanova_bold.otf");
	}

	public void setProximanova_bold(Typeface proximanova_bold) {
		Constants.proximanova_bold = proximanova_bold;
	}

	public static Typeface getProximanova_regular(Context context) {
		return Typeface.createFromAsset(context.getAssets(),
				"fonts/proximanova_regular.otf");
	}

	public void setProximanova_regular(Typeface proximanova_regular) {
		Constants.proximanova_regular = proximanova_regular;
	}

	public static Typeface getProximanova_semibold(Context context) {
		return Typeface.createFromAsset(context.getAssets(),
				"fonts/proximanova_semibold.otf");
	}

	public void setProximanova_semibold(Typeface proximanova_semibold) {
		Constants.proximanova_semibold = proximanova_semibold;
	}
}
