package com.inchat;

import com.inchat.server.ServerClient;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * Created by Dylan on 2017/12/3.
 */

public class Constants {

    public static final String SERVER_BASE = "http://www.aafvv.cn:8080";

    public static final Retrofit RETROFIT = new Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//新的配置
            .baseUrl(SERVER_BASE)
            .build();

    public static final ServerClient SERVER_CLIENT = RETROFIT.create(ServerClient.class);
}
