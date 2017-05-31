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
import retrofit2.http.QueryMap;

/**
 * Created by Orange on 2017/4/24.
 */

public interface ModifyInfoClient {

    @FormUrlEncoded
    @POST("upd_user")
    Call<JsonObject> ModifyInfo(@FieldMap Map<String, Object> params);

    @Multipart
    @POST("upd_user")
    Call<JsonObject> ModifyAvatar(@PartMap Map<String, RequestBody> partMap,
                                  @Part MultipartBody.Part file);
}
