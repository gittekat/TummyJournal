package com.gittekat.tummyjournal.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.gittekat.tummyjournal.R;

public class FoodStuffActivity extends Activity {

	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.foodstuff);

		final Button button = getSubmitButton();
		if (button != null) {
			button.setOnClickListener(new SubmitListener());
		}
	}

	private Button getSubmitButton() {
		return (Button) findViewById(R.id.foodstuff_submit_button);
	}

	private String getFoodText1String() {
		final TextView text = (TextView) findViewById(R.id.foodstuff_text1);
		if (text != null) {
			return text.getText().toString();
		} else {
			return "";
		}
	}

	private final class SubmitListener implements View.OnClickListener {
		public void onClick(final View v) {
			final String textEntry = getFoodText1String();
			System.err.println(textEntry);
		}
	}

}
