package com.inchat.database;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Dylan on 2017/12/4.
 */
public class AppDBHelper extends SQLiteOpenHelper {

	private final static String TAG = "AppDBHelper";
	// 初始版本
	public final static int VERSION_CODE_INIT = 1;

	private SQLiteTableHelper[] mTables;

	/**
	 *
	 * @param context
	 * @param tablesHelper override的方法不需要开始事务
	 */
	public AppDBHelper(Context context, SQLiteTableHelper... tablesHelper) {
		this(context, null, null, tablesHelper);
	}

	public AppDBHelper(Context context, SQLiteDatabase.CursorFactory factory, DatabaseErrorHandler errorHandler, SQLiteTableHelper... tableHelpers) {
		super(context, "application.db", factory, VERSION_CODE_INIT, errorHandler);
		this.mTables = tableHelpers;
	}

	public <T extends SQLiteTableHelper> T getTableHelper(Class<T> clazz) {

		if (mTables == null || mTables.length == 0) {
			Log.i(TAG, "getTableHelper: " + getDatabaseName() + " no tables.");
			return null;
		}

		for (SQLiteTableHelper table : mTables) {
			if (table.getClass() == clazz) {
				return (T) table;
			}
		}
		return null;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		if (mTables == null || mTables.length == 0) {
			Log.i(TAG, "onCreate: " + getDatabaseName() + " no tables.");
			return;
		}

		db.beginTransaction();
		for (SQLiteTableHelper table : mTables) {
			table.onCreate(db);
		}
		db.setTransactionSuccessful();
		db.endTransaction();
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		if (mTables == null || mTables.length == 0) {
			Log.i(TAG, "onUpgrade: " + getDatabaseName() + " no tables.");
			return;
		}

		db.beginTransaction();
		for (SQLiteTableHelper table : mTables) {
			table.onUpgrade(db, oldVersion, newVersion);
		}
		db.setTransactionSuccessful();
		db.endTransaction();
	}

	@Override
	public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		if (mTables == null || mTables.length == 0) {
			Log.i(TAG, "onDowngrade: " + getDatabaseName() + " no tables.");
			return;
		}

		db.beginTransaction();
		for (SQLiteTableHelper table : mTables) {
			table.onDowngrade(db, oldVersion, newVersion);
		}
		db.setTransactionSuccessful();
		db.endTransaction();
	}

	@Override
	public void onOpen(SQLiteDatabase db) {
		super.onOpen(db);
	}

	@Override
	public void onConfigure(SQLiteDatabase db) {
		super.onConfigure(db);
	}

}