package com.campusstreet.api;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Orange on 2017/6/1.
 */

public interface PayClient {

    @FormUrlEncoded
    @POST("get_alipaysign")
    Call<JsonObject> getalipaysign(@Field("uid") String uid, @Field("amount") String amount);

}
