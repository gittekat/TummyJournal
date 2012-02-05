package com.gittekat.tummyjournal.activities;

import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
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
	private int hour;
	private int minute;
	private int year;
	private int month;
	private int day;

	static final int DATE_DIALOG_ID = 0;
	static final int TIME_DIALOG_ID = 1;

	private OnDateSetListener dateSetListener = new DateListener();
	private OnTimeSetListener timeSetListener = new TimeListener();

	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		db = new TummyJournalDB(this);

		initLayout();

	}

	private void initLayout() {
		setContentView(R.layout.defecation);

		// date picker
		getDateDisplay().setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(final View v) {
				showDialog(DATE_DIALOG_ID);
			}
		});

		getTimeDisplay().setOnClickListener(new View.OnClickListener() {
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

		initDefecationSpinner();

		final Button button = getSubmitButton();
		if (button != null) {
			button.setOnClickListener(new SubmitListener());
		}

		updateDisplay();
	}

	private void initDefecationSpinner() {
		// defecation types spinner
		final ArrayAdapter<CharSequence> types_adapter = ArrayAdapter.createFromResource(this, R.array.defecation_types,
				R.layout.spinner_layout);
		types_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		getDefecationSpinner().setAdapter(types_adapter);
	}

	private Spinner getDefecationSpinner() {
		return (Spinner) findViewById(R.id.defecation_type);
	}

	private TextView getTimeDisplay() {
		return (TextView) findViewById(R.id.timeDisplay);
	}

	private TextView getDateDisplay() {
		return (TextView) findViewById(R.id.dateDisplay);
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
		final Spinner typeSpinner = getDefecationSpinner();
		if (typeSpinner != null) {
			return typeSpinner.getSelectedItem().toString();
		} else {
			return "";
		}
	}

	private void updateDisplay() {
		System.err.println(day);
		getDateDisplay().setText(
				new StringBuilder().append(getString(R.string.defecation_date)).append(": ").append(day).append(".").append(month + 1)
						.append(".").append(year).append(" "));
		String minuteText = "";
		if (minute < 10) {
			minuteText = "0" + minute;
		} else {
			minuteText = "" + minute;
		}
		getTimeDisplay().setText(
				new StringBuilder().append(getString(R.string.defecation_time)).append(": ").append(hour).append(":").append(minuteText));
	}

	private void resetView() {
		setContentView(R.layout.defecation);
		initDefecationSpinner();
		updateDisplay();
	}

	private final class TimeListener implements OnTimeSetListener {
		@Override
		public void onTimeSet(final TimePicker view, final int h, final int min) {
			hour = h;
			minute = min;
			updateDisplay();
		}
	}

	private final class DateListener implements OnDateSetListener {
		@Override
		public void onDateSet(final DatePicker view, final int y, final int monthOfYear, final int dayOfMonth) {
			year = y;
			month = monthOfYear;
			day = dayOfMonth;
			updateDisplay();
		}
	}

	private final class SubmitListener implements OnClickListener {
		@Override
		public void onClick(final View v) {
			final DefecationWrapper defecation = new DefecationWrapper();
			defecation.details = getDetailsString();
			defecation.type = getTypeString();
			final Calendar cal = Calendar.getInstance();
			cal.set(year, month, day, hour, minute);
			defecation.datetime = cal;

			db.setDefecation(defecation);
			db.printDBContent();

			resetView();
		}
	}
}
