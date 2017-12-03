package com.inchat;

import android.app.Application;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.EaseUI;
import com.inchat.content.AppPreferences;

/**
 * Created by Dylan on 2017/12/2.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        AppPreferences.init(this);

        initIM();
    }

    private void initIM() {
        EMOptions options = new EMOptions();
// 默认添加好友时，是不需要验证的，改成需要验证
        options.setAcceptInvitationAlways(false);
        EaseUI.getInstance().init(this, options);
    }
}
