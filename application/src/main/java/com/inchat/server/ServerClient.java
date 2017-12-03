package com.inchat.server;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Dylan on 2017/12/3.
 */

public interface ServerClient {

    // common
    String KEY_RESULT_CODE = "code";
    String KEY_RESULT_MSG = "msg";
    String KEY_RESULT_MODEL = "result";

    String KEY_ID = "id";
    String KEY_UID = "uid";
    String KEY_TOKEN = "rtoken";
    String KEY_USERNAME = "user_name";
    String KEY_PASSWORD = "password";
    String KEY_NICKNAME = "nick_name";
    String KEY_DISPLAY_NAME = "display_name";
    String KEY_PORTRAIT_URL = "portraitUri";
    String KEY_TELEPHONE = "phone";
    String KEY_ZONE = "zone";
    String KEY_TUS = "tus";
    String KEY_LEVEL = "level";
    String KEY_VIP = "vip";
    String KEY_VIP_START = "vip_start";
    String KEY_VIP_END = "vip_end";
    String KEY_EM = "em";
    String KEY_UPDATED_AT = "updated_at";
    String KEY_CREATED_AT = "created_at";

    @POST("api/user/login" )
    Observable<ResponseBody> login(@Query("zone") String zone, @Query("phone") String telephone);
}
