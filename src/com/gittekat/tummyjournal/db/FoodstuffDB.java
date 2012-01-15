package com.gittekat.tummyjournal.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class FoodstuffDB extends SQLiteOpenHelper {
	public final String sqlEmptyDB = "CREATE TABLE foodstuff (_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL);";

	public FoodstuffDB(final Context context, final String name, final CursorFactory factory, final int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(final SQLiteDatabase db) {
		db.execSQL(sqlEmptyDB);
	}

	@Override
	public void onUpgrade(final SQLiteDatabase arg0, final int arg1, final int arg2) {
		// TODO Auto-generated method stub

	}

}
