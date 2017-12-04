package com.inchat.database;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Dylan on 2017/12/4.
 */

public class NoticeTableHelper extends SQLiteTableHelper {

	static final String TABLE_NAME = "notice";
	static final String COLUMN_ID = "_id";
	static final String COLUMN_FROM = "username";
	static final String COLUMN_GROUP_ID = "groupid";
	static final String COLUMN_GROUP_NAME = "groupname";

	static final String COLUMN_TIME = "time";
	static final String COLUMN_REASON = "reason";
	static final String COLUMN_STATUS = "status";
	static final String COLUMN_ISINVITEFROMME = "isInviteFromMe";
	static final String COLUMN_GROUPINVITER = "groupinviter";

	static final String COLUMN_UNREAD_MSG_COUNT = "unreadMsgCount";

	@Override
	public String getTableName() {
		return TABLE_NAME;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		StringBuilder sql = new StringBuilder();
		sql.append("CREATE TABLE IF NOT EXISTS ").append(TABLE_NAME);
		sql.append(" (").append(COLUMN_ID).append(" INTEGER PRIMARY KEY AUTOINCREMENT, ");
		sql.append(", ").append(COLUMN_FROM).append(" TEXT");
		sql.append(", ").append(COLUMN_GROUP_ID).append(" TEXT");
		sql.append(", ").append(COLUMN_GROUP_NAME).append(" TEXT");
		sql.append(", ").append(COLUMN_REASON).append(" TEXT");
		sql.append(", ").append(COLUMN_STATUS).append(" INTEGER");
		sql.append(", ").append(COLUMN_ISINVITEFROMME).append(" INTEGER");
		sql.append(", ").append(COLUMN_UNREAD_MSG_COUNT).append(" INTEGER");
		sql.append(", ").append(COLUMN_TIME).append(" TEXT");
		sql.append(", ").append(COLUMN_GROUPINVITER).append(" TEXT");
		sql.append(")");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}
}
