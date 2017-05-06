package com.campusstreet.api;

import com.google.gson.JsonObject;

import java.util.Map;
import java.util.Objects;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;

/**
 * Created by Orange on 2017/5/3.
 */

public interface UserClient {

    @FormUrlEncoded
    @POST("user_login")
    Call<JsonObject> login(@Field("mobile") String phone, @Field("pwd") String passwrod);

    @FormUrlEncoded
    @POST("get_mobilecode")
    Call<JsonObject> fetchCaptcha(@Field("mobile") String phone,@Field("mc") String mc);

    @Multipart
    @POST("ins_register")
    Call<JsonObject> resgister(@PartMap Map<String, Object> params);

    @Multipart
    @POST("upd_userpwd")
    Call<JsonObject> changePassword(@PartMap Map<String, Object> params);

    @Multipart
    @POST("upd_forgotpwd")
    Call<JsonObject> forgetPassword(@PartMap Map<String, Object> params);

    @FormUrlEncoded
    @POST("get_regmc")
    Call<JsonObject> getRegMc(@Field("mobile") String phone);

    @FormUrlEncoded
    @POST("get_forgotmc")
    Call<JsonObject> getForgetmc(@Field("mobile") String phone);

}

