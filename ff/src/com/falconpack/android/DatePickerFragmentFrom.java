package com.falconpack.android;

import java.util.Calendar;
import java.util.Date;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class DatePickerFragmentFrom extends DialogFragment {
	OnDateSetListener ondateSet;

	public DatePickerFragmentFrom() {
		setArguments(new Bundle());
	}

	public void setCallBack(OnDateSetListener ondate) {
		ondateSet = ondate;
	}

	private int year, month, day;

	@Override
	public void setArguments(Bundle args) {
		super.setArguments(args);
		year = args.getInt("year");
		month = args.getInt("month");
		day = args.getInt("day");
	}

	DatePickerDialog dialog;
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		
		Calendar c = Calendar.getInstance();

		 dialog = new DatePickerDialog(getActivity(),
				ondateSet, year, month, day);
		dialog.getDatePicker().setMinDate(c.getTimeInMillis());
		 setCancelable(false);
		return dialog;
		
	}
	

}
