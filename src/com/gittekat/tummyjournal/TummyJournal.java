package com.gittekat.tummyjournal;

import android.app.Activity;
import android.os.Bundle;

public class TummyJournal extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
	}

	@Override
	protected void onDestroy() {
		// TODO close database
		super.onDestroy();
	}
}