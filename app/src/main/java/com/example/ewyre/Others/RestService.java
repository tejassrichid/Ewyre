package com.example.ewyre.Others;

import java.util.concurrent.TimeUnit;

import retrofit.client.OkClient;


public class RestService {

    private static final String URL = "https://rentorhire.in/rentorhireapi/api";

    private retrofit.RestAdapter restAdapter;
    private UserService apiService;

    public RestService() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(100000, TimeUnit.SECONDS)
                .readTimeout(100000, TimeUnit.SECONDS).build();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(UserService.class);
    }

    public UserService getService() {
        return apiService;
    }
}
