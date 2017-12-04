package com.inchat.content;

import android.content.ContentResolver;
import android.content.Context;

import com.inchat.database.AppDBHelper;
import com.inchat.database.NoticeTableHelper;

/**
 * Created by Dylan on 2017/12/4.
 */
public class NoticeManager {

	private final Context mContext;
	private final ContentResolver mResolver;

	public NoticeManager(Context context) {
		this.mContext = context;
		this.mResolver = context.getContentResolver();
	}

	public void removeNotice(String from) {
	}
}
