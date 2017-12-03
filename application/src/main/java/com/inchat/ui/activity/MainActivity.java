package com.inchat.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.Toast;

import com.hyphenate.chat.EMClient;
import com.inchat.R;
import com.inchat.ui.BaseActivity;
import com.inchat.ui.fragment.LoginFragment;
import com.inchat.ui.fragment.MainFragment;

/**
 * Created by Dylan on 2017/11/23.
 */

public class MainActivity extends BaseActivity implements LoginFragment.OnLoginListener {

    public final static String TAG = "MainActivity";

    private FragmentManager mFM = getSupportFragmentManager();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFM = getSupportFragmentManager();

        Fragment show;
        String tag;
        if (EMClient.getInstance().isLoggedInBefore()) {
            show = MainFragment.newInstance();
            tag = MainFragment.TAG;
        } else {
            show = LoginFragment.newInstance();
            tag = LoginFragment.TAG;
        }
        mFM.beginTransaction()
                .add(R.id.fragment_container, show, tag).commitNow();
    }

    @Override
    public void onLoginSuccess(Object o) {
        mFM.beginTransaction()
                .remove(mFM.findFragmentByTag(LoginFragment.TAG))
                .add(R.id.fragment_container, MainFragment.newInstance(), MainFragment.TAG)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commitNow();
    }

    @Override
    public void onLoginFailed(int errcode, String reason) {
        Log.d(TAG, "onLoginFailed: " + Thread.currentThread().getName());
        Toast.makeText(this, reason, Toast.LENGTH_LONG).show();
    }
}
