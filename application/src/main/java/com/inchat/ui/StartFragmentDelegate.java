package com.inchat.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * Created by Dylan on 2017/12/2.
 */

public class StartFragmentDelegate implements StartFragment {

    private FragmentManager mFM;
    private int mContainerId;

    public void setup(FragmentManager fm, int containerId) {
        mFM = fm;
        mContainerId = containerId;
    }

    @Override
    public void startFragment(Fragment fragment, String tag) {
        mFM.beginTransaction().addToBackStack(tag + "_back")
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .add(mContainerId, fragment, tag).commit();
    }
}
