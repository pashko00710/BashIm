package com.example.bashim.rest;

import com.example.bashim.rest.model.BashImModel;

import java.util.ArrayList;

public class RestService {
    private RestClient restClient;

    public RestService() {
        restClient = new RestClient();
    }

    public ArrayList<BashImModel> getRecordings() {
        return restClient.getBashImApi().getRecordings();
    }
}
