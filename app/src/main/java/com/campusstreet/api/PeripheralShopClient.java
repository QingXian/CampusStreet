package com.campusstreet.api;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Orange on 2017/4/24.
 */

public interface PeripheralShopClient {

    @POST("get_shoptype")
    Call<JsonObject> getShopType();

    @FormUrlEncoded
    @POST("get_shop")
    Call<JsonObject> getShop(@Field("key") String key, @Field("type") Integer type, @Field("pi") Integer pi);

    @FormUrlEncoded
    @POST("get_shop")
    Call<JsonObject> getShop(@Field("type") Integer type, @Field("pi") Integer pi);

    @FormUrlEncoded
    @POST("get_shopgoods")
    Call<JsonObject> getShopGood(@Field("sid") Integer sid, @Field("type") Integer type, @Field("key") String key, @Field("pi") Integer pi);

    @FormUrlEncoded
    @POST("get_shopgoods")
    Call<JsonObject> getShopGood(@Field("sid") Integer sid, @Field("type") Integer type, @Field("pi") Integer pi);
}
