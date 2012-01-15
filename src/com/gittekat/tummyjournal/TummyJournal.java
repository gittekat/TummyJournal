package com.gittekat.tummyjournal;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;

import com.gittekat.tummyjournal.activities.DefecationActivity;
import com.gittekat.tummyjournal.activities.FoodStuffActivity;

public class TummyJournal extends TabActivity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		final Resources res = getResources(); // Resource object to get
												// Drawables
		final TabHost tabHost = getTabHost(); // The activity TabHost
		TabHost.TabSpec spec; // Resusable TabSpec for each tab
		Intent intent; // Reusable Intent for each tab

		// Create an Intent to launch an Activity for the tab (to be reused)
		intent = new Intent().setClass(this, FoodStuffActivity.class);

		// Initialize a TabSpec for each tab and add it to the TabHost
		spec = tabHost.newTabSpec("foodStuff").setIndicator(getString(R.string.food_title), res.getDrawable(R.drawable.ic_tab_foodstuff))
				.setContent(intent);
		tabHost.addTab(spec);

		// Do the same for the other tabs
		intent = new Intent().setClass(this, DefecationActivity.class);
		spec = tabHost.newTabSpec("defecation").setIndicator(getString(R.string.defecation_title), res.getDrawable(R.drawable.ic_tab_foodstuff))
				.setContent(intent);
		tabHost.addTab(spec);

		tabHost.setCurrentTab(0);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}