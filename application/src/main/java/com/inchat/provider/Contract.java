package com.inchat.provider;

import android.net.Uri;

import java.util.Locale;

/**
 * Created by Dylan on 2017/12/4.
 */
public class Contract {
	private final int codeBase;

	final String name;
	final int codeAll;
	final int codeById;

	final Uri contentUri;
	final String mimeTypeForOne;
	final String mimeTypeForMany;

	public Contract(int ordinal, String name, String authority) {
		this.name = name.toLowerCase(Locale.ENGLISH);
		this.codeBase = ordinal << 3;
		this.codeAll = this.codeBase + 1;
		this.codeById = this.codeBase + 2;
		this.contentUri = Uri.parse("content://" + authority + "/" + this.name);
		this.mimeTypeForOne = "vnd.android.cursor.item/vnd.inchat." + this.name;
		this.mimeTypeForMany = "vnd.android.cursor.dir/vnd.inchat." + this.name;
	}
}
