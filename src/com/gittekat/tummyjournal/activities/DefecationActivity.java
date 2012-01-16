package com.gittekat.tummyjournal.activities;

import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import com.gittekat.tummyjournal.R;

public class DefecationActivity extends Activity {
	private TextView dateDisplay;
	private Button pickDate;
	private int year;
	private int month;
	private int day;

	static final int DATE_DIALOG_ID = 0;

	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.defecation);

		// date picker
		dateDisplay = (TextView) findViewById(R.id.dateDisplay);
		pickDate = (Button) findViewById(R.id.pickDate);

		pickDate.setOnClickListener(new View.OnClickListener() {
			public void onClick(final View v) {
				showDialog(DATE_DIALOG_ID);
			}
		});

		final Calendar c = Calendar.getInstance();
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);

		updateDisplay();

		// defecation types spinner
		final Spinner defecationTypeSpinner = (Spinner) findViewById(R.id.spinner1);
		final ArrayAdapter types_adapter = ArrayAdapter.createFromResource(this, R.array.defecation_types, android.R.layout.simple_spinner_item);
		types_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		defecationTypeSpinner.setAdapter(types_adapter);
	}

	@Override
	protected Dialog onCreateDialog(final int id) {
		switch (id) {
		case DATE_DIALOG_ID:
			return new DatePickerDialog(this, mDateSetListener, year, month, day);
		}
		return null;
	}

	private void updateDisplay() {
		dateDisplay.setText(new StringBuilder()
		// Month is 0 based so add 1
				.append(day).append("-").append(month + 1).append("-").append(year).append(" "));
	}

	private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {

		public void onDateSet(final DatePicker view, final int y, final int monthOfYear, final int dayOfMonth) {
			year = y;
			month = monthOfYear;
			day = dayOfMonth;
			updateDisplay();
		}
	};
}
