package com.campusstreet.api;


import com.google.gson.JsonObject;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

import static android.R.attr.type;

/**
 * Created by Orange on 2017/4/19.
 */

public interface IdleSaleClient {


    @POST("get_ewu_type")
    Call<JsonObject> getIdleSaleType();

    @FormUrlEncoded
    @POST("get_ewu_goods")
    Call<JsonObject> getIdleSale(@Field("type") Integer type, @Field("pi") Integer pi);

    @FormUrlEncoded
    @POST("get_my_ewugoods")
    Call<JsonObject> getUserIdleSale(@Field("uid") String uid,@Field("key") String key, @Field("pi") Integer pi);

    @Multipart
    @POST("ins_ewu_goods")
    Call<JsonObject> pushIdlegoods(@PartMap Map<String, RequestBody> Idlegoods,
                                   @Part MultipartBody.Part[] files);

//    @Multipart
//    @POST("ins_ewu_goods")
//    Call<JsonObject> pushIdlegoods(@PartMap Map<String, RequestBody> Idlegoods);

    @FormUrlEncoded
    @POST("get_ewu_comment")
    Call<JsonObject> getIdleSaleMessage(@Field("gid") Integer id, @Field("pi") Integer pi);

    @FormUrlEncoded
    @POST("get_ewu_goods_info")
    Call<JsonObject> getIdleSaleDetail(@Field("gid") Integer id);

    @FormUrlEncoded
    @POST("ins_ewu_comment")
    Call<JsonObject> LeaveMessage(@Field("gid") Integer gid, @Field("uid") String uid ,@Field("con") String con);

}
