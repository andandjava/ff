package com.falconpack.android.common;

import java.util.ArrayList;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class PreferenceUtilities {

	private final static String TYPE = "user_type";
	private final static String ID = "id";
	private final static String PREFS_NAME = "Prefs";
	private final static String ALL_USER_PREFS_NAME = "UserTypePrefs";

	private final static String USERNAME = "user_name";
	private final static String PASSWORD = "pass_word";
	private final static String NAME = "name";
	private final static String ISREMEMBER = "isRemember";

	private final static String M_NAME = "manager_name";
	private final static String M_ID = "manager_id";
	private final static String D_ID = "department_id";
	private final static String U_ID = "usertype_id";
	public static SharedPreferences prefs;

	public static void saveUserData(Context cont, String username,
			String password, String name, String id, String isRemember,
			String mname, String mid, String d_id, String u_id) {
		prefs = cont.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
		SharedPreferences.Editor prefsEditor = prefs.edit();
		// if (LoginActivity.rememberChk.isChecked()) {
		prefsEditor.putString(USERNAME, "" + username);
		prefsEditor.putString(PASSWORD, "" + password);
		prefsEditor.putString(NAME, "" + name);
		prefsEditor.putString(ID, "" + id);
		prefsEditor.putString(ISREMEMBER, isRemember);
		prefsEditor.putString(M_NAME, "" + mname);
		prefsEditor.putString(M_ID, "" + mid);
		prefsEditor.putString(D_ID, d_id);
		prefsEditor.putString(U_ID, u_id);
		prefsEditor.commit();
		// }
	}

	/**
	 * Getting the User name/password of a particular user.
	 * 
	 * @return user name,password,city and location
	 */
	public static UserDetails getSavedUserData(Context con) {

		prefs = con.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

		UserDetails userDetails = new UserDetails();

		if (prefs.getAll().size() > 0) {

			// String[] value = new String[prefs.getAll().size()];
			// value[0] = prefs.getString(USERNAME, "");
			// value[1] = prefs.getString(PASSWORD, "");
			// value[2] = prefs.getString(ID, "");
			// value[3] = prefs.getString(NAME, "");
			// value[4] = prefs.getString(ISREMEMBER, "false");
			// value[5] = prefs.getString(M_NAME, "");
			// value[6] = prefs.getString(M_ID, "");
			// value[7] = prefs.getString(D_ID, "");
			// value[8] = prefs.getString(U_ID,"");

			userDetails.setUsername(prefs.getString(USERNAME, ""));
			userDetails.setPassword(prefs.getString(PASSWORD, ""));
			userDetails.setId(prefs.getString(ID, ""));
			userDetails.setName(prefs.getString(NAME, ""));
			userDetails.setIsRemember(prefs.getString(ISREMEMBER, "false"));
			userDetails.setMname(prefs.getString(M_NAME, ""));
			userDetails.setMid(prefs.getString(M_ID, ""));
			userDetails.setDid(prefs.getString(D_ID, ""));
			userDetails.setUid(prefs.getString(U_ID, ""));

			return userDetails;
		} else {

			userDetails.setUsername("");
			userDetails.setPassword("");
			userDetails.setId("");
			userDetails.setName("");
			userDetails.setIsRemember("false");
			userDetails.setMname("");
			userDetails.setMid("");
			userDetails.setDid("");
			userDetails.setUid("");
		}
		return userDetails;
	}

	/**
	 * Getting the User name/password of a particular user.
	 * 
	 * @return user name,password,city and location
	 */
	public static ArrayList<String[]> getSavedUserTypeData(Context con) {
		ArrayList<String[]> array = new ArrayList<String[]>();
		prefs = con.getSharedPreferences(ALL_USER_PREFS_NAME,
				Context.MODE_PRIVATE);
		if (prefs.getAll().size() > 0) {
			String[] values = new String[prefs.getAll().size() / 2];
			String[] ids = new String[prefs.getAll().size() / 2];

			for (int i = 0; i < values.length; i++) {

				values[i] = prefs.getString(TYPE + "_" + i, "");
				ids[i] = prefs.getString(ID + "_" + i, "");

			}
			array.add(values);
			array.add(ids);
		}
		return array;
	}

	/**
	 * Getting the User name/password of a particular user.
	 * 
	 * @return user name,password,city and location
	 */
	private final static String DEPARTMENT_PREFS_NAME = "deptPrefs";

	public static ArrayList<String[]> getSavedDepartmentData(Context con) {
		ArrayList<String[]> array = new ArrayList<String[]>();
		prefs = con.getSharedPreferences(DEPARTMENT_PREFS_NAME,
				Context.MODE_PRIVATE);
		// prefs.getAll().size();
		if (prefs.getAll().size() > 0) {
			String[] values = new String[prefs.getAll().size() / 2];
			String[] ids = new String[prefs.getAll().size() / 2];

			for (int i = 0; i < values.length; i++) {

				values[i] = prefs.getString(TYPE + "_" + i, "");
				ids[i] = prefs.getString(ID + "_" + i, "");

			}

			array.add(values);
			array.add(ids);
		}
		return array;
	}

	public static Editor saveUserTypeData(Context cont, String username,
			String id, int pos) {
		prefs = cont.getSharedPreferences(ALL_USER_PREFS_NAME,
				Context.MODE_PRIVATE);

		SharedPreferences.Editor prefsEditor = prefs.edit();
		System.out.println(" UserType Size " + prefs.getAll().size());
		prefsEditor.putString(TYPE + "_" + pos, "" + username);
		prefsEditor.putString(ID + "_" + pos, "" + id);
		prefsEditor.commit();

		return prefsEditor;
	}

	public static Editor saveDepartMentData(Context cont, String username,
			String id, int pos) {
		prefs = cont.getSharedPreferences(DEPARTMENT_PREFS_NAME,
				Context.MODE_PRIVATE);
		SharedPreferences.Editor prefsEditor = prefs.edit();

		System.out.println("DepartMent Size " + prefs.getAll().size());
		
		prefsEditor.putString(TYPE + "_" + pos, "" + username);
		prefsEditor.putString(ID + "_" + pos, "" + id);
		prefsEditor.commit();

		return prefsEditor;
	}
}
