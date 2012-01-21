package com.gittekat.tummyjournal.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class TummyJournalDB extends SQLiteOpenHelper {
	public final static String ID = "_id";

	// foodstuff table
	public final static String FOODSTUFF_TABLE = "foodstuff";
	public final static String FOODSTUFF_NAME = "name";

	// defecation table
	public final static String DEFECATION_TABLE = "defecation";
	public final static String DEFECATION_TYPE = "type";
	public final static String DEFECATION_DETAILS = "details";
	public final static String DEFECATION_DATETIME = "datetime";

	private static final String FOODSTUFF_TABLE_CREATE = "CREATE TABLE " + FOODSTUFF_TABLE + " (" + ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + FOODSTUFF_NAME + " TEXT NOT NULL);";

	private static final String DEFECATION_TABLE_CREATE = "CREATE TABLE " + DEFECATION_TABLE + " (" + ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + DEFECATION_TYPE + " TEXT NOT NULL, " + DEFECATION_DETAILS + " TEXT NOT NULL, "
			+ DEFECATION_DATETIME + " TIMESTAMP NOT NULL DEFAULT current_timestamp);";

	public final static String MAIN_DATABASE_CREATE = FOODSTUFF_TABLE_CREATE + DEFECATION_TABLE_CREATE;

	public TummyJournalDB(final Context context) {
		super(context, FOODSTUFF_TABLE, null, 1);
	}

	public TummyJournalDB(final Context context, final String name, final CursorFactory factory, final int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(final SQLiteDatabase db) {
		System.out.println(MAIN_DATABASE_CREATE);
		db.execSQL(MAIN_DATABASE_CREATE);
	}

	@Override
	public void onUpgrade(final SQLiteDatabase arg0, final int arg1, final int arg2) {
		// TODO Auto-generated method stub

	}

	/**
	 * Loading of a Foodstuff entries.
	 * 
	 * @param id
	 * @return foodstuff entries
	 */
	public FoodstuffWrapper getFoodstuff(final long id) {
		Cursor cursor = null;
		try {
			cursor = getReadableDatabase().query(FOODSTUFF_TABLE, new String[] { ID, FOODSTUFF_NAME }, ID + "=?",
					new String[] { String.valueOf(id) }, null, null, null);
			if (!cursor.moveToFirst()) {
				return null;
			}
			final FoodstuffWrapper foodstuff = new FoodstuffWrapper();
			foodstuff.id = id;
			foodstuff.name = cursor.getString(cursor.getColumnIndexOrThrow(FOODSTUFF_NAME));
			return foodstuff;
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
	}

	/**
	 * Saving of the foodstuff entries.
	 * 
	 * @param foodstuff
	 * @return id
	 */
	public long setFoodstuff(final FoodstuffWrapper foodstuff) {
		final ContentValues values = new ContentValues();

		if (foodstuff.id != 0) {
			values.put(ID, foodstuff.id);
		}
		values.put(FOODSTUFF_NAME, foodstuff.name);

		if (foodstuff.id == 0) {
			getWritableDatabase().insert(FOODSTUFF_TABLE, null, values);
		} else {
			getWritableDatabase().update(FOODSTUFF_TABLE, values, ID + "=?", new String[] { String.valueOf(foodstuff.id) });
		}

		return foodstuff.id;
	}

	public long setDefecation(final DefecationWrapper defecation) {
		final ContentValues values = new ContentValues();

		if (defecation.id != 0) {
			values.put(ID, defecation.id);
		}
		values.put(DEFECATION_TYPE, defecation.type);
		values.put(DEFECATION_DETAILS, defecation.details);

		if (defecation.id == 0) {
			getWritableDatabase().insert(DEFECATION_TABLE, null, values);
		} else {
			getWritableDatabase().update(DEFECATION_TABLE, values, ID + "=?", new String[] { String.valueOf(defecation.id) });
		}

		return defecation.id;
	}

	public void printDBContent() {
		System.out.println(FOODSTUFF_TABLE_CREATE);
		System.out.println(DEFECATION_TABLE_CREATE);
		Cursor cursor = null;
		try {
			cursor = getReadableDatabase().query(FOODSTUFF_TABLE, new String[] { ID, FOODSTUFF_NAME }, null, null, null, null,
					FOODSTUFF_NAME + " COLLATE LOCALIZED");
			System.err.println(cursor.getCount());
			cursor.moveToFirst();

			while (cursor.moveToNext()) {
				System.err.println(cursor.getString(cursor.getColumnIndexOrThrow(FOODSTUFF_NAME)));
			}
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
	}
}
