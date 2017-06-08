package com.campusstreet.api;

import com.campusstreet.contract.BasePresenter;
import com.campusstreet.contract.BaseView;
import com.campusstreet.entity.BountyHallInfo;
import com.campusstreet.entity.IdleSaleInfo;
import com.campusstreet.entity.JoinInfo;
import com.campusstreet.entity.LeaveMessageInfo;
import com.google.gson.JsonObject;

import java.util.List;
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

import static android.R.attr.type;

/**
 * Created by Orange on 2017/4/27.
 */

public interface BountyHallClient {

    @FormUrlEncoded
    @POST("get_tasks")
    Call<JsonObject> getTasks(@Field("tp") Integer type, @Field("key") String key, @Field("pi") Integer pi);

    @FormUrlEncoded
    @POST("get_tasks")
    Call<JsonObject> getTasks(@Field("tp") Integer type, @Field("pi") Integer pi);

    @FormUrlEncoded
    @POST("get_tasks_info")
    Call<JsonObject> getTasksDetail(@Field("tid") Integer tid);

    @FormUrlEncoded
    @POST("get_jointask")
    Call<JsonObject> getJoinTask(@Field("tid") Integer tid, @Field("state") Integer state, @Field("pi") Integer pi);


    @Multipart
    @POST("ins_jointask")
    Call<JsonObject> joinTask(@PartMap Map<String, RequestBody> joinInfo);

    @FormUrlEncoded
    @POST("ins_tasktakeperson")
    Call<JsonObject> passJoinTask(@Field("uid") String uid, @Field("tid") Integer tid, @Field("tpid") Integer tpid, @Field("st") Integer st);


    @FormUrlEncoded
    @POST("publisher_op_task")
    Call<JsonObject> StartTask(@Field("uid") String uid, @Field("taskid") Integer tid, @Field("state") String state);

    @FormUrlEncoded
    @POST("publisher_op_taskperson")
    Call<JsonObject> PublisherOpTask(@Field("uid") String uid, @Field("tpid") Integer tpid, @Field("taskid") Integer tid, @Field("state") Integer state);

    @FormUrlEncoded
    @POST("taskperson_done")
    Call<JsonObject> CompleteTask(@Field("uid") String uid, @Field("tpid") Integer tpid, @Field("taskid") Integer tid);

    @FormUrlEncoded
    @POST("taskperson_abandon")
    Call<JsonObject> GiveUpTask(@Field("uid") String uid, @Field("tpid") Integer tpid, @Field("taskid") Integer tid);

    @Multipart
    @POST("ins_task")
    Call<JsonObject> addTask(@PartMap Map<String, RequestBody> bountyHallInfo);

    @POST("get_tasktype")
    Call<JsonObject> getTaskType();

    @FormUrlEncoded
    @POST("get_mytasks")
    Call<JsonObject> getUserTasks(@Field("uid") String uid, @Field("tp") Integer type, @Field("key") String key, @Field("pi") Integer pi);

    @FormUrlEncoded
    @POST("get_mytasks")
    Call<JsonObject> getUserTasks(@Field("uid") String uid, @Field("tp") Integer type, @Field("pi") Integer pi);

    @FormUrlEncoded
    @POST("get_myjointasks")
    Call<JsonObject> getUserJoinTasks(@Field("uid") String uid, @Field("pi") Integer pi);
}
