package com.gittekat.tummyjournal.activities;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.gittekat.tummyjournal.R;
import com.gittekat.tummyjournal.db.FoodstuffWrapper;
import com.gittekat.tummyjournal.db.TummyJournalDB;

public class FoodStuffActivity extends Activity {
	private static final int MORE_BUTTON_SIZE = 90;
	private TummyJournalDB db;
	private List<EditText> ingredients;
	private int idCounter;
	private LinearLayout layout;

	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ingredients = new ArrayList<EditText>();
		setContentView(initLayout());

		db = new TummyJournalDB(this);
	}

	// TODO add possibility to change date and time
	private View initLayout() {
		final ScrollView scrollView = new ScrollView(this);
		layout = new LinearLayout(this);
		layout.setOrientation(LinearLayout.VERTICAL);
		scrollView.addView(layout);

		final TextView label = new TextView(this);
		label.setText(R.string.food1);
		layout.addView(label);

		ingredients.clear();
		idCounter = 0;
		final ViewGroup ingredientView = createIngredientGrid();
		layout.addView(ingredientView);

		final Button button = new Button(this);
		button.setText(R.string.submit);
		button.setOnClickListener(new SubmitListener());
		layout.addView(button, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.FILL_PARENT));

		return scrollView;
	}

	private ViewGroup createIngredientGrid() {
		final RelativeLayout container = new RelativeLayout(this);

		final EditText textField = new EditText(this);
		textField.setId(++idCounter);
		ingredients.add(textField);

		container.addView(textField, new RelativeLayout.LayoutParams(getWindowManager().getDefaultDisplay().getWidth() - MORE_BUTTON_SIZE,
				RelativeLayout.LayoutParams.FILL_PARENT));

		final Button moreButton = new Button(this);
		moreButton.setId(++idCounter);
		moreButton.setText("+");
		moreButton.setOnClickListener(new MoreIngredientsListener());

		final RelativeLayout.LayoutParams parameters = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.FILL_PARENT);
		parameters.addRule(RelativeLayout.RIGHT_OF, textField.getId());
		parameters.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		container.addView(moreButton, parameters);

		return container;
	}

	private List<String> getFoodTextStrings() {
		final List<String> ingredientTexts = new ArrayList<String>();
		for (final EditText text : ingredients) {
			ingredientTexts.add(text.getText().toString());
		}
		return ingredientTexts;
	}

	// TODO remove and save single entries with getFoodTextStrings()
	private String getFoodTextString() {
		final StringBuilder fullText = new StringBuilder();
		for (final String text : getFoodTextStrings()) {
			if (!text.isEmpty()) {
				fullText.append(text);
				fullText.append(",");
			}
		}

		fullText.delete(fullText.length() - 1, fullText.length());
		return fullText.toString();
	}

	@Override
	protected void onDestroy() {
		db.close();
		super.onDestroy();
	}

	private final class MoreIngredientsListener implements OnClickListener {
		@Override
		public void onClick(final View v) {
			layout.addView(createIngredientGrid(), v.getId() / 2 + 1);
			layout.invalidate();
		}
	}

	private final class SubmitListener implements OnClickListener {
		@Override
		public void onClick(final View v) {
			final String textEntry = getFoodTextString();
			System.err.println(textEntry);

			final FoodstuffWrapper foodstuff = new FoodstuffWrapper();
			foodstuff.name = textEntry;
			db.setFoodstuff(foodstuff);
			db.printDBContent();

			setContentView(initLayout());
		}

	}
}
