package com.inchat.content;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Dylan on 2017/12/3.
 */

public class AppPreferences {

    private final static String KEY_ID = "id";
    private final static String KEY_UID = "uid";
    private final static String KEY_TOKEN = "rtoken";
    private final static String KEY_USERNAME = "user_name";
    private final static String KEY_PASSWORD = "password";
    private final static String KEY_NICKNAME = "nick_name";
    private final static String KEY_DISPLAY_NAME = "display_name";
    private final static String KEY_PORTRAIT_URL = "portraitUri";
    private final static String KEY_TELEPHONE = "phone";
    private final static String KEY_ZONE = "zone";
    private final static String KEY_STATUS = "status";
    private final static String KEY_LEVEL = "level";
    private final static String KEY_IS_VIP = "is_vip";
    private final static String KEY_VIP_START = "vip_start";
    private final static String KEY_VIP_END = "vip_end";
    private final static String KEY_IS_EM = "is_em";

    private static AppPreferences sInstance;

    public static AppPreferences getInstance() {
        return sInstance;
    }

    public static void init(Context context) {
        sInstance = new AppPreferences(context.getSharedPreferences(context.getPackageName() + "_pref", Context.MODE_PRIVATE));
    }

    private SharedPreferences mProfile;

    public AppPreferences(SharedPreferences profile) {
        mProfile = profile;
    }

    public void setId(int id) {
        SharedPreferences.Editor edit = mProfile.edit();
        if (id == 0) {
            edit.remove(KEY_ID);
        } else {
            edit.putInt(KEY_ID, id);
        }
        edit.commit();
    }

    public int getId() {
        return mProfile.getInt(KEY_ID, 0);
    }

    public void setUid(String uid) {
        SharedPreferences.Editor edit = mProfile.edit();
        if (uid == null) {
            edit.remove(KEY_UID);
        } else {
            edit.putString(KEY_UID, uid);
        }
        edit.commit();
    }

    public String getUid() {
        return mProfile.getString(KEY_UID, null);
    }

    public void setToken(String token) {
        SharedPreferences.Editor edit = mProfile.edit();
        if (token == null) {
            edit.remove(KEY_TOKEN);
        } else {
            edit.putString(KEY_TOKEN, token);
        }
        edit.commit();
    }

    public String getToken() {
        return mProfile.getString(KEY_TOKEN, null);
    }

    public void setUserName(String username) {
        SharedPreferences.Editor edit = mProfile.edit();
        if (username == null) {
            edit.remove(KEY_USERNAME);
        } else {
            edit.putString(KEY_USERNAME, username);
        }
        edit.commit();
    }

    public String getUserName() {
        return mProfile.getString(KEY_USERNAME, null);
    }

    public void setNickame(String nickname) {
        SharedPreferences.Editor edit = mProfile.edit();
        if (nickname == null) {
            edit.remove(KEY_NICKNAME);
        } else {
            edit.putString(KEY_NICKNAME, nickname);
        }
        edit.commit();
    }

    public String getNickame() {
        return mProfile.getString(KEY_NICKNAME, null);
    }

    public void setPortraitUrl(String portraitUrl) {
        SharedPreferences.Editor edit = mProfile.edit();
        if (portraitUrl == null) {
            edit.remove(KEY_PORTRAIT_URL);
        } else {
            edit.putString(KEY_PORTRAIT_URL, portraitUrl);
        }
        edit.commit();
    }

    public String getPortraitUrl() {
        return mProfile.getString(KEY_PORTRAIT_URL, null);
    }

    public void setTelephone(String telephone) {
        SharedPreferences.Editor edit = mProfile.edit();
        if (telephone == null) {
            edit.remove(KEY_TELEPHONE);
        } else {
            edit.putString(KEY_TELEPHONE, telephone);
        }
        edit.commit();
    }

    public String getTelephone() {
        return mProfile.getString(KEY_TELEPHONE, null);
    }

}
