package com.inchat.database;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Dylan on 2017/12/4.
 */

public class UserTableHelper extends SQLiteTableHelper {

	static final String TABLE_NAME = "user";
	static final String COLUMN_ID = "_id";
	static final String COLUMN_USERNAME = "username";
	static final String COLUMN_NICKNAME = "nickname";
	static final String COLUMN_AVATAR = "avatar";
	static final String COLUMN_UPDATE_TIME = "update_time";
	static final String COLUMN_CREATE_TIME = "create_time";

	public UserTableHelper() {
	}

	@Override
	public String getTableName() {
		return TABLE_NAME;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		StringBuilder sql = new StringBuilder();
		sql.append("CREATE TABLE IF NOT EXISTS ").append(TABLE_NAME);
		sql.append(" (").append(COLUMN_ID).append(" INTEGER PRIMARY KEY AUTOINCREMENT");
		sql.append(", ").append(COLUMN_USERNAME).append(" TEXT UNIQUE");
		sql.append(", ").append(COLUMN_NICKNAME).append(" TEXT");
		sql.append(", ").append(COLUMN_AVATAR).append(" TEXT");
		sql.append(", ").append(COLUMN_UPDATE_TIME).append(" TIMESTAMP");
		sql.append(", ").append(COLUMN_CREATE_TIME).append(" TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP");
		sql.append(");");

		db.execSQL(sql.toString());
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}

	@Override
	public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		super.onDowngrade(db, oldVersion, newVersion);// throw exception
	}
}
