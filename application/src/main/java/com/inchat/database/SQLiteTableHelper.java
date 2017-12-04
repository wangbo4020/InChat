package com.inchat.database;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Dylan on 2017/12/4.
 */
public abstract class SQLiteTableHelper {

	public SQLiteTableHelper() {
	}

	public abstract String getTableName();

	public abstract void onCreate(SQLiteDatabase db);

	public abstract void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion);

	public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		throw new SQLiteException("Can't downgrade database from version " +
				oldVersion + " to " + newVersion);
	}
}
