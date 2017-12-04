package com.inchat.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.inchat.BuildConfig;
import com.inchat.database.AppDBHelper;
import com.inchat.database.NoticeTableHelper;
import com.inchat.database.SQLiteTableHelper;

import java.util.List;

/**
 * Created by Dylan on 2017/12/4.
 */

public class NoticeProvider extends ContentProvider {

	private String sAuthority;
	private UriMatcher sUriMatcher;

	private SQLiteTableHelper[] sTablesHelper;
	private Contract[] sContract;

	private AppDBHelper mOpenHelper;

	@Override
	public boolean onCreate() {
		this.sAuthority = getContext().getPackageName() + ".noticeprovider";
		this.sUriMatcher = new UriMatcher(-1);
		this.sTablesHelper = new SQLiteTableHelper[]{new NoticeTableHelper()};
		this.sContract = new Contract[this.sTablesHelper.length];

		for (int i = 0; i < this.sTablesHelper.length; i++) {

			final String name = this.sTablesHelper[i].getTableName();
			final Contract contract = new Contract(i, name, sAuthority);

			this.sUriMatcher.addURI(sAuthority, name, contract.codeAll);
			this.sUriMatcher.addURI(sAuthority, name + "/#", contract.codeById);

			this.sContract[i] = contract;
		}

		this.mOpenHelper = new AppDBHelper(getContext(), sTablesHelper);
		this.mOpenHelper.getWritableDatabase();
		return true;
	}

	@Override
	public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs,
						String sortOrder) {
		this.checkUri(uri);
		SQLiteDatabase db = null;
		Cursor c = null;

		try {
			db = this.mOpenHelper.getReadableDatabase();
			c = db.query(uri.getPathSegments().get(0),
					projection,
					this.appendSelection(uri, selection),
					this.appendSelectionArgs(uri, selectionArgs),
					null,
					null,
					sortOrder);
			c.setNotificationUri(this.getContext().getContentResolver(), uri);
			return c;
		} catch (SQLiteException e) {
			e.printStackTrace();
			if (c != null && !c.isClosed()) {
				c.close();
			} else if (db != null && db.isOpen()) {
				db.close();
			}

			return null;
		} catch (Exception e) {
			if (c != null) {
				c.close();
			}

			return null;
		}
	}

	@Override
	public Uri insert(@NonNull Uri uri, ContentValues values) {
		this.checkUri(uri);
		SQLiteDatabase db = null;
		try {
			db = this.mOpenHelper.getWritableDatabase();
			long rowId = db.insertOrThrow(uri.getPathSegments().get(0), null, values);
			Uri returnUri = ContentUris.withAppendedId(uri, rowId);
			this.getContext().getContentResolver().notifyChange(returnUri, null);
			return returnUri;
		} catch (SQLiteException e) {
			e.printStackTrace();
			if (db != null && db.isOpen()) {
				db.close();
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public int update(@NonNull Uri uri, ContentValues values, String selection, String[] selectionArgs) {
		this.checkUri(uri);
		SQLiteDatabase db = null;
		try {
			db = this.mOpenHelper.getWritableDatabase();
			int count = db.update(uri.getPathSegments().get(0),
					values,
					this.appendSelection(uri, selection),
					this.appendSelectionArgs(uri, selectionArgs));
			this.getContext().getContentResolver().notifyChange(uri, null);
			return count;
		} catch (SQLiteException e) {
			e.printStackTrace();
			if (db != null && db.isOpen()) {
				db.close();
			}
			return 0;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
		this.checkUri(uri);
		SQLiteDatabase db = null;
		try {
			db = this.mOpenHelper.getWritableDatabase();
			int count = db.delete(uri.getPathSegments().get(0),
					this.appendSelection(uri, selection),
					this.appendSelectionArgs(uri, selectionArgs));
			this.getContext().getContentResolver().notifyChange(uri, null);
			return count;
		} catch (SQLiteException e) {
			e.printStackTrace();
			if (db != null && db.isOpen()) {
				db.close();
			}
			return 0;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public String getType(@NonNull Uri uri) {
		int code = sUriMatcher.match(uri);

		int length = sContract.length;

		for (int i = 0; i < length; ++i) {
			Contract contract = sContract[i];
			if (code == contract.codeAll) {
				return contract.mimeTypeForMany;
			}

			if (code == contract.codeById) {
				return contract.mimeTypeForOne;
			}
		}

		throw new IllegalArgumentException("unknown uri : " + uri);
	}

	private void checkUri(Uri uri) {
		int code = sUriMatcher.match(uri);

		int length = sContract.length;

		for (int i = 0; i < length; ++i) {
			Contract contract = sContract[i];
			if (code == contract.codeAll) {
				return;
			}

			if (code == contract.codeById) {
				return;
			}
		}

		throw new IllegalArgumentException("unknown uri : " + uri);
	}

	private String appendSelection(Uri uri, String selection) {
		List pathSegments = uri.getPathSegments();
		return pathSegments.size() == 1 ? selection
				: "_id = ?" + (selection == null ? "" : " AND (" + selection + ")");
	}

	private String[] appendSelectionArgs(Uri uri, String[] selectionArgs) {
		List pathSegments = uri.getPathSegments();
		if (pathSegments.size() == 1) {
			return selectionArgs;
		} else if (selectionArgs != null && selectionArgs.length != 0) {
			String[] returnArgs = new String[selectionArgs.length + 1];
			returnArgs[0] = (String) pathSegments.get(1);
			System.arraycopy(selectionArgs, 0, returnArgs, 1, selectionArgs.length);
			return returnArgs;
		} else {
			return new String[]{(String) pathSegments.get(1)};
		}
	}
}
