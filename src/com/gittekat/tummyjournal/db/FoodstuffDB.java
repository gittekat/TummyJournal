package com.gittekat.tummyjournal.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class FoodstuffDB extends SQLiteOpenHelper {
	public final static String MAIN_TABLE = "foodstuff"; // Tabellenname
	public final static String ID = "_id";
	public final static String NAME = "name";

	public final static String MAIN_DATABASE_CREATE = "CREATE TABLE " + MAIN_TABLE + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + NAME
			+ " TEXT NOT NULL);";

	public FoodstuffDB(final Context context) {
		super(context, MAIN_TABLE, null, 1);
	}

	public FoodstuffDB(final Context context, final String name, final CursorFactory factory, final int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(final SQLiteDatabase db) {
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
	public Foodstuff getFoodstuff(final long id) {
		Cursor cursor = null;
		try {
			cursor = getReadableDatabase().query(MAIN_TABLE, new String[] { ID, NAME }, ID + "=?", new String[] { String.valueOf(id) }, null, null,
					null);
			if (!cursor.moveToFirst()) {
				return null;
			}
			final Foodstuff foodstuff = new Foodstuff();
			foodstuff.id = id;
			foodstuff.name = cursor.getString(cursor.getColumnIndexOrThrow(NAME));
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
	public long setFoodstuff(final Foodstuff foodstuff) {
		final ContentValues values = new ContentValues();

		if (foodstuff.id != 0) {
			values.put(ID, foodstuff.id);
		}
		values.put(NAME, foodstuff.name);

		if (foodstuff.id == 0) {
			getWritableDatabase().insert(MAIN_TABLE, null, values);
		} else {
			getWritableDatabase().update(MAIN_TABLE, values, ID + "=?", new String[] { String.valueOf(foodstuff.id) });
		}

		return foodstuff.id;
	}

	public void printDBContent() {
		Cursor cursor = null;
		try {
			cursor = getReadableDatabase().query(MAIN_TABLE, new String[] { ID, NAME }, null, null, null, null, NAME + " COLLATE LOCALIZED");
			System.err.println(cursor.getCount());
			cursor.moveToFirst();

			while (cursor.moveToNext()) {
				System.err.println(cursor.getString(cursor.getColumnIndexOrThrow(NAME)));
			}
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
	}

}
