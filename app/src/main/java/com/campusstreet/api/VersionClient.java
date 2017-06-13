package com.campusstreet.api;

import com.campusstreet.common.AppConfig;
import com.google.gson.JsonObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;

/**
 *
 * Created by Orange on 2017/6/12.
 */

public interface VersionClient {

    @POST("get_android_update")
    Call<JsonObject> getVersion();
}
