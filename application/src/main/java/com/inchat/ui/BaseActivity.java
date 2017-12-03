package com.inchat.ui;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.inchat.ui.LifecycleLogDelegate;

/**
 * Created by Dylan on 2017/11/23.
 */

public class BaseActivity extends AppCompatActivity {
    protected LifecycleLogDelegate mLifecycleLogDelegate;

    public BaseActivity() {
        mLifecycleLogDelegate = new LifecycleLogDelegate("BaseActivity");
    }

    protected void setupLifecycleLog(boolean enable, String tag) {
        mLifecycleLogDelegate.setup(enable, tag);
    }

    @Override
    public void onAttachFragment(Fragment childFragment) {
        super.onAttachFragment(childFragment);
        mLifecycleLogDelegate.onAttachFragment(childFragment);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLifecycleLogDelegate.onCreate(savedInstanceState);
    }

    @Override
    public void onRestart() {
        super.onRestart();
        mLifecycleLogDelegate.onRestart();
    }

    @Override
    public void onStart() {
        super.onStart();
        mLifecycleLogDelegate.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mLifecycleLogDelegate.onResume();
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mLifecycleLogDelegate.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mLifecycleLogDelegate.onSaveInstanceState(outState);
    }

    @Override
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        mLifecycleLogDelegate.onNewIntent(intent);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mLifecycleLogDelegate.onConfigurationChanged(newConfig);
    }

    @Override
    public void onPause() {
        super.onPause();
        mLifecycleLogDelegate.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mLifecycleLogDelegate.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mLifecycleLogDelegate.onDestroy();
    }

}
