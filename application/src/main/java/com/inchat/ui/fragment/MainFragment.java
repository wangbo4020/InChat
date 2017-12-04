package com.inchat.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.inchat.R;
import com.inchat.ui.BaseFragment;
import com.inchat.ui.BottomNavigationViewHelper;

/**
 * Created by Dylan on 2017/11/23.
 */

public class MainFragment extends BaseFragment implements BottomNavigationView.OnNavigationItemSelectedListener {

    public final static String TAG = "MainFragment";

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    private Fragment mFrgmtMessage;
    private Fragment mFrgmtChat;
    private Fragment mFrgmtWords;
    private Fragment mFrgmtSetting;
    private BottomNavigationView mNaviBottom;

    @Override
    public void onAttachFragment(Fragment childFragment) {
        super.onAttachFragment(childFragment);
        // Activity在后台被回收后，再次进入现场恢复的Fragment
        if (childFragment instanceof MessageFragment) {
            mFrgmtMessage = childFragment;
        } else if (childFragment instanceof ChatFragment) {
            mFrgmtChat = childFragment;
        } else if (childFragment instanceof WordsFragment) {
            mFrgmtWords = childFragment;
        } else if (childFragment instanceof SettingFragment) {
            mFrgmtSetting = childFragment;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 初次进入，Fragment实例化
        mFrgmtMessage = mFrgmtMessage == null ? MessageFragment.newInstance() : mFrgmtMessage;
        mFrgmtChat = mFrgmtChat == null ? ChatFragment.newInstance() : mFrgmtChat;
        mFrgmtWords = mFrgmtWords == null ? WordsFragment.newInstance() : mFrgmtWords;
        mFrgmtSetting = mFrgmtSetting == null ? SettingFragment.newInstance() : mFrgmtSetting;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mNaviBottom = view.findViewById(R.id.navi_bottom);
        BottomNavigationViewHelper.disableShiftMode(mNaviBottom);
        mNaviBottom.setOnNavigationItemSelectedListener(this);

        // 现场恢复上次显示的Fragment
        int selectedId = R.id.navigation_message;
        if (savedInstanceState != null) {
            selectedId = savedInstanceState.getInt("SelectedId");
        }
        mNaviBottom.setSelectedItemId(selectedId);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("SelectedId", mNaviBottom.getSelectedItemId());
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
        case R.id.navigation_message:
            return showFragment(mFrgmtMessage, mFrgmtChat, mFrgmtWords, mFrgmtSetting);
        case R.id.navigation_chat:
            return showFragment(mFrgmtChat, mFrgmtMessage, mFrgmtWords, mFrgmtSetting);
        case R.id.navigation_words:
            return showFragment(mFrgmtWords, mFrgmtMessage, mFrgmtChat, mFrgmtSetting);
        case R.id.navigation_setting:
            return showFragment(mFrgmtSetting, mFrgmtMessage, mFrgmtChat, mFrgmtWords);
        }
        return false;
    }

    /**
     * 显示一个Fragment，隐藏多个Fragment
     * @param frgmt
     * @param hides
     * @return
     */
    public boolean showFragment(Fragment frgmt, Fragment... hides) {
        if (frgmt.isVisible()) return false;
        FragmentManager fm = getChildFragmentManager();
        FragmentTransaction trans = fm.beginTransaction();
        if (!frgmt.isAdded()) {
            trans.add(R.id.main_content, frgmt);
        }
        for (Fragment f : hides) if (f != frgmt && f != null && f.isAdded()) trans.hide(f);
        trans.show(frgmt).commit();
        return true;
    }
}
