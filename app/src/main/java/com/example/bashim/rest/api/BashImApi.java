package com.example.bashim.rest.api;

import com.example.bashim.rest.model.BashImModel;
import com.example.bashim.util.ConstantManager;

import java.util.ArrayList;

import retrofit.http.GET;

public interface BashImApi {
    @GET("/api/get?site=bash.im&name=bash&num=" + ConstantManager.RECORDINGS_LIMIT)
    public ArrayList<BashImModel> getRecordings();
}
