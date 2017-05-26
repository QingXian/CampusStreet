package com.campusstreet.api;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Orange on 2017/5/25.
 */

public interface HomeClient {

    @FormUrlEncoded
    @POST("get_indextrends")
    Call<JsonObject> GetIndextrend(@Field("uid") String uid);
}
