package com.example.bashim.rest;

import com.example.bashim.rest.api.BashImApi;
import com.example.bashim.util.ConstantManager;

import retrofit.RestAdapter;

public class RestClient {

    private BashImApi bashImApi;
    public RestClient() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(ConstantManager.BASE_URL)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();

        bashImApi = restAdapter.create(BashImApi.class);

    }

    public BashImApi getBashImApi() {
        return bashImApi;
    }
}
