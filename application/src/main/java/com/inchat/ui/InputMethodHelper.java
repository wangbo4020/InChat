package com.inchat.ui;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import static android.view.inputmethod.InputMethodManager.HIDE_NOT_ALWAYS;

/**
 * Created by Dylan on 2017/12/4.
 */

public class InputMethodHelper {

	public static void hideSoftInputFromWindow(Activity activity) {
		InputMethodManager imm = (InputMethodManager) activity.getApplicationContext()
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		View focused = activity.getCurrentFocus();
		if (focused != null) {
			imm.hideSoftInputFromWindow(focused.getWindowToken(), HIDE_NOT_ALWAYS);
		}
	}
}
