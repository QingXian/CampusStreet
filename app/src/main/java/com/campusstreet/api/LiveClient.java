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
 * Created by Orange on 2017/5/31.
 */

public interface LiveClient {

    @FormUrlEncoded
    @POST("get_disinfo")
    Call<JsonObject> getLive(@Field("pi") Integer pi);

    @Multipart
    @POST("ins_disinfo")
    Call<JsonObject> pushLive(@PartMap Map<String, RequestBody> liveInfo,
                                   @Part MultipartBody.Part[] files);
    @FormUrlEncoded
    @POST("del_disinfo")
    Call<JsonObject> deleteLive(@Field("uid") String uid,@Field("did") Integer did);

    @FormUrlEncoded
    @POST("reply_disinfo")
    Call<JsonObject> replyLive(@Field("uid") String uid,@Field("did") Integer did,@Field("con") String con);

    @FormUrlEncoded
    @POST("get_disinfo_reply")
    Call<JsonObject> getLivereply(@Field("did") Integer did,@Field("pi") Integer pi);
}
