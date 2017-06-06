package com.campusstreet.api;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Orange on 2017/5/31.
 */

public interface MessageClient {

    @FormUrlEncoded
    @POST("get_mysms")
    Call<JsonObject> getMessage(@Field("uid") String uid, @Field("pi") Integer pi);

    @FormUrlEncoded
    @POST("read_mysms")
    Call<JsonObject> readMessage(@Field("uid") String uid, @Field("smsids") Integer pi);

}
