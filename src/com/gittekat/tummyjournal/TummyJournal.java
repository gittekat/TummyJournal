package com.gittekat.tummyjournal;

import android.app.Activity;
import android.os.Bundle;

import com.gittekat.tummyjournal.db.FoodstuffDB;

public class TummyJournal extends Activity {
	private FoodstuffDB db;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		db = new FoodstuffDB(this);
	}

	@Override
	protected void onDestroy() {
		db.close();
		super.onDestroy();
	}
}