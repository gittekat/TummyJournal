package com.gittekat.tummyjournal.activities;

import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.gittekat.tummyjournal.R;
import com.gittekat.tummyjournal.db.DefecationWrapper;
import com.gittekat.tummyjournal.db.TummyJournalDB;

public class DefecationActivity extends Activity {
	private TummyJournalDB db;
	private TextView dateDisplay;
	private TextView timeDisplay;
	private int hour;
	private int minute;
	private int year;
	private int month;
	private int day;

	static final int DATE_DIALOG_ID = 0;
	static final int TIME_DIALOG_ID = 1;

	private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {

		@Override
		public void onDateSet(final DatePicker view, final int y, final int monthOfYear, final int dayOfMonth) {
			year = y;
			month = monthOfYear;
			day = dayOfMonth;
			updateDisplay();
		}
	};

	private TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
		@Override
		public void onTimeSet(final TimePicker view, final int h, final int min) {
			hour = h;
			minute = min;
			updateDisplay();
		}
	};

	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.defecation);

		db = new TummyJournalDB(this);

		// date picker
		dateDisplay = (TextView) findViewById(R.id.dateDisplay);
		dateDisplay.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(final View v) {
				showDialog(DATE_DIALOG_ID);
			}
		});

		timeDisplay = (TextView) findViewById(R.id.timeDisplay);
		timeDisplay.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(final View v) {
				showDialog(TIME_DIALOG_ID);
			}
		});

		final Calendar c = Calendar.getInstance();
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);
		hour = c.get(Calendar.HOUR_OF_DAY);
		minute = c.get(Calendar.MINUTE);

		// defecation types spinner
		final Spinner defecationTypeSpinner = (Spinner) findViewById(R.id.defecation_type);
		final ArrayAdapter<CharSequence> types_adapter = ArrayAdapter.createFromResource(this, R.array.defecation_types,
				R.layout.spinner_layout);
		types_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		defecationTypeSpinner.setAdapter(types_adapter);

		final Button button = getSubmitButton();
		if (button != null) {
			button.setOnClickListener(new SubmitListener());
		}

		updateDisplay();
	}

	@Override
	protected Dialog onCreateDialog(final int id) {
		switch (id) {
		case DATE_DIALOG_ID:
			return new DatePickerDialog(this, dateSetListener, year, month, day);
		case TIME_DIALOG_ID:
			return new TimePickerDialog(this, timeSetListener, hour, minute, true);
		}
		return null;
	}

	@Override
	protected void onDestroy() {
		db.close();
		super.onDestroy();
	}

	private Button getSubmitButton() {
		return (Button) findViewById(R.id.defecation_submit_button);
	}

	private String getDetailsString() {
		final TextView details = (TextView) findViewById(R.id.defecation_details);
		if (details != null) {
			return details.getText().toString();
		} else {
			return "";
		}
	}

	private String getTypeString() {
		final Spinner typeSpinner = (Spinner) findViewById(R.id.defecation_type);
		if (typeSpinner != null) {
			return typeSpinner.getSelectedItem().toString();
		} else {
			return "";
		}
	}

	private void updateDisplay() {
		dateDisplay.setText(new StringBuilder().append(getString(R.string.defecation_date)).append(": ").append(day).append(".")
				.append(month + 1).append(".").append(year).append(" "));
		String minuteText = "";
		if (minute < 10) {
			minuteText = "0" + minute;
		} else {
			minuteText = "" + minute;
		}
		timeDisplay.setText(new StringBuilder().append(getString(R.string.defecation_time)).append(": ").append(hour).append(":")
				.append(minuteText));
	}

	private final class SubmitListener implements View.OnClickListener {
		@Override
		public void onClick(final View v) {
			final DefecationWrapper defecation = new DefecationWrapper();
			defecation.details = getDetailsString();
			defecation.type = getTypeString();
			final Calendar cal = Calendar.getInstance();
			cal.set(year, month, day, hour, minute);
			defecation.datetime = cal;

			db.setDefecation(defecation);
		}
	}
}
