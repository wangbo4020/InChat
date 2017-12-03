package com.inchat.ui;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Dylan on 2017/11/23.
 */

public class BaseFragment extends Fragment {

    protected LifecycleLogDelegate mLifecycleLogDelegate;
    protected ContentLoadingDelegate mContentLoadingDelegate;

    public BaseFragment() {
        mLifecycleLogDelegate = new LifecycleLogDelegate("BaseFragment");
    }

    protected void setupLifecycleLog(boolean enable, @NonNull String tag) {
        mLifecycleLogDelegate.setup(enable, tag);
    }

    protected void setSupportActionBar(@Nullable Toolbar toolbar) {
        ((BaseActivity) getActivity()).setSupportActionBar(toolbar);
    }

    protected void runOnUiThread(Runnable action) {
        getActivity().runOnUiThread(action);
    }

    /* - - - - - - - - - - - - - - - ContentLoadingDelegate Start - - - - - - - - - - - - - - - - */
    protected void setupContentLoading(@IdRes int id, @NonNull View view) {
        if (mContentLoadingDelegate == null) {
            mContentLoadingDelegate = new ContentLoadingDelegate();
        }
        mContentLoadingDelegate.setup(id, view);
    }

    protected void showContentLoading() {
        mContentLoadingDelegate.show();
    }

    protected void hideContentLoading() {
        mContentLoadingDelegate.hide();
    }

    /* * * * * * * * * * * * * * * * * ContentLoadingDelegate End * * * * * * * * * * * * * * * * */

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        mLifecycleLogDelegate.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mLifecycleLogDelegate.onConfigurationChanged(newConfig);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mLifecycleLogDelegate.onAttach(context);
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mLifecycleLogDelegate.onCreateView(savedInstanceState);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mLifecycleLogDelegate.onViewCreated(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mLifecycleLogDelegate.onActivityCreated(savedInstanceState);
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
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mLifecycleLogDelegate.onSaveInstanceState(outState);
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
    public void onDestroyView() {
        super.onDestroyView();
        mLifecycleLogDelegate.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mLifecycleLogDelegate.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mLifecycleLogDelegate.onDetach();
    }
}
