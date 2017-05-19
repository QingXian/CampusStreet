package com.campusstreet.api;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Orange on 2017/4/24.
 */

public interface PartnerClient {

    @POST("get_partnertype")
    Call<JsonObject> getPartnerType();

    @FormUrlEncoded
    @POST("get_partner")
    Call<JsonObject> getPartner(@Field("key") String key, @Field("type") Integer type, @Field("pi") Integer pi);

    @FormUrlEncoded
    @POST("get_partner")
    Call<JsonObject> getPartner(@Field("type") Integer type, @Field("pi") Integer pi);

    @FormUrlEncoded
    @POST("get_partner_info")
    Call<JsonObject> getPartnerDetail(@Field("pid") Integer pid);
}
