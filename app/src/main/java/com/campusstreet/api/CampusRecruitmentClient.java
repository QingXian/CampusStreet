package com.campusstreet.api;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

import static android.R.attr.key;

/**
 * Created by Orange on 2017/5/3.
 */

public interface CampusRecruitmentClient {

    @FormUrlEncoded
    @POST("get_recruit")
    Call<JsonObject> getRecruitList(@Field("key") String key, @Field("pi") Integer pi);

    @FormUrlEncoded
    @POST("get_recruit")
    Call<JsonObject> getRecruitList(@Field("pi") Integer pi);

    @FormUrlEncoded
    @POST("get_studywork_info")
    Call<JsonObject> getRecruitDetail(@Field("swid") int swid);

    @FormUrlEncoded
    @POST("get_studywork")
    Call<JsonObject> getStudyWorkList(@Field("key") String key, @Field("pi") Integer pi);

    @FormUrlEncoded
    @POST("get_studywork")
    Call<JsonObject> getStudyWorkList(@Field("pi") Integer pi);

    @FormUrlEncoded
    @POST("get_recruit_info")
    Call<JsonObject> getStudyWorkDetail(@Field("rid") int rid);
}
