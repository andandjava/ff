package com.falconpack.android;

import java.util.Calendar;
import java.util.Date;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class DatePickerFragment extends DialogFragment {
	OnDateSetListener ondateSet;

	public DatePickerFragment() {
		setArguments(new Bundle());
	}

	public void setCallBack(OnDateSetListener ondate) {
		ondateSet = ondate;
	}

	private int year, month, day;
	long millsec;
	Calendar c;
	@Override
	public void setArguments(Bundle args) {
		super.setArguments(args);
		year = args.getInt("year");
		month = args.getInt("month");
		day = args.getInt("day");
		millsec = args.getLong("millsec");
		
//		Calendar c = Calendar.getInstance();
	}

	DatePickerDialog dialog;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		

		dialog = new DatePickerDialog(getActivity(), ondateSet, year, month, day);
		dialog.getDatePicker().setMinDate(millsec);
		setCancelable(false);
		return dialog;

	}

	public Dialog setMinDateMethod(Date date) {

		dialog.getDatePicker().setMinDate(date.getTime());
		return dialog;
	}
}
