package com.inchat.ui;

import android.support.annotation.IdRes;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.view.View;

/**
 * Created by Dylan on 2017/11/23.
 */

public class ContentLoadingDelegate {

    private ContentLoadingProgressBar mProgressBar;

    public void setup(@IdRes int id, View view) {
        mProgressBar = view.findViewById(id);
        throwIfNull();
    }

    public void hide() {
        throwIfNull();
        mProgressBar.hide();
    }

    public void show() {
        throwIfNull();
        mProgressBar.show();
    }

    private void throwIfNull() {
        if (mProgressBar == null) {
            throw new IllegalStateException("use before must call setup.");
        }
    }
}
