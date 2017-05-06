package com.campusstreet.api;

import com.google.gson.JsonObject;

import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;

/**
 * Created by Orange on 2017/4/24.
 */


public interface AssociationClient {

    @FormUrlEncoded
    @POST("get_assn")
    Call<JsonObject> getAssociation(@Field("pi") Integer pi);

    @Multipart
    @POST("get_assnpost")
    Call<JsonObject> getAssociationPost(@Field("aid") Integer aid,@Field("pi") Integer pi);

    @FormUrlEncoded
    @POST("get_assnpostreply")
    Call<JsonObject> getAssociationPostMessage(@Field("pid") Integer pid, @Field("pi") Integer pi);

    @FormUrlEncoded
    @POST("ins_assnpostreply")
    Call<JsonObject> leaveMessage(@Field("pid") Integer pid, @Field("uid") String uid ,@Field("con") String con);

    @FormUrlEncoded
    @POST("ins_assnpost")
    Call<JsonObject> addAssociationPost(@Field("aid") Integer aid, @Field("uid") String uid ,@Field("con") String con,@Field("title") String title);

    @FormUrlEncoded
    @POST("join_assn")
    Call<JsonObject> JoinAssociation(@Field("aid") Integer aid, @Field("uid") String uid ,@Field("con") String con);

    @FormUrlEncoded
    @POST("apply_joinassn")
    Call<JsonObject> applyJoinAssn(@Field("aid") Integer pid, @Field("uid") String uid ,@Field("st") int st,@Field("con") String con);

    @FormUrlEncoded
    @POST("get_assnmember")
    Call<JsonObject> getAssociationNumber(@Field("aid") Integer pid ,@Field("pi") Integer pi,@Field("key") String key);
}
