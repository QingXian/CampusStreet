package com.campusstreet.api;

import com.google.gson.JsonObject;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

/**
 * Created by Orange on 2017/4/24.
 */

public interface BuyZoneClient {

    @FormUrlEncoded
    @POST("get_wishs")
    Call<JsonObject> getBuyZone(@Field("pi") Integer pi);

    @FormUrlEncoded
    @POST("get_mywishs")
    Call<JsonObject> getUserBuyZone(@Field("uid") String uid, @Field("key") String key, @Field("pi") Integer pi);

    @Multipart
    @POST("ins_wishs")
    Call<JsonObject> pushBuy(@PartMap Map<String, RequestBody> buyZone);


    @FormUrlEncoded
    @POST("get_wish_comment")
    Call<JsonObject> getBuyZoneMessage(@Field("wid") Integer id, @Field("pi") Integer pi);

    @FormUrlEncoded
    @POST("ins_wish_comment")
    Call<JsonObject> LeaveMessage(@Field("wid") Integer wid, @Field("uid") String uid, @Field("con") String con);
}

