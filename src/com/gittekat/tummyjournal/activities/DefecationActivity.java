package com.gittekat.tummyjournal.activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.gittekat.tummyjournal.R;

public class DefecationActivity extends Activity {
	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.defecation);

		// defecation types spinner
		final Spinner defecationTypeSpinner = (Spinner) findViewById(R.id.spinner1);
		final ArrayAdapter types_adapter = ArrayAdapter.createFromResource(this, R.array.defecation_types, android.R.layout.simple_spinner_item);
		types_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		defecationTypeSpinner.setAdapter(types_adapter);
	}
}
