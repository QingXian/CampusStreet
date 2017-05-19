package com.campusstreet.api;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Orange on 2017/4/24.
 */

public interface CampusInformationClient {

    @FormUrlEncoded
    @POST("get_news")
    Call<JsonObject> getNews(@Field("key") String key, @Field("pi") Integer pi);

    @FormUrlEncoded
    @POST("get_news")
    Call<JsonObject> getNews(@Field("pi") Integer pi);

    @FormUrlEncoded
    @POST("get_news_info")
    Call<JsonObject> getNewDetail(@Field("nid") Integer nid);
}
