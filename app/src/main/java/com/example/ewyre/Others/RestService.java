package com.example.ewyre.Others;

import com.squareup.okhttp.OkHttpClient;

import java.util.concurrent.TimeUnit;

import retrofit.client.OkClient;


public class RestService {

    private static final String URL = "http://192.168.1.146/ewyreapi/api";

    private retrofit.RestAdapter restAdapter;
    private UserService apiService;

    public RestService() {
        final OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setReadTimeout(1800, TimeUnit.SECONDS);
        okHttpClient.setConnectTimeout(1800, TimeUnit.SECONDS);
        restAdapter = new retrofit.RestAdapter.Builder()

                .setEndpoint(URL)
                .setLogLevel(retrofit.RestAdapter.LogLevel.FULL)
                .setClient(new OkClient(okHttpClient))
                .build();
        apiService = restAdapter.create(UserService.class);
    }

    public UserService getService() {
        return apiService;
    }
}
