package com.inchat.ui.fragment;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.inchat.R;
import com.inchat.ui.BaseFragment;
import com.inchat.ui.BottomNavigationViewHelper;

/**
 * Created by Dylan on 2017/11/23.
 */

public class MainFragment extends BaseFragment {

    public final static String TAG = "MainFragment";

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    private BottomNavigationView mNaviBottom;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mNaviBottom = view.findViewById(R.id.navi_bottom);
        BottomNavigationViewHelper.disableShiftMode(mNaviBottom);
    }
}
