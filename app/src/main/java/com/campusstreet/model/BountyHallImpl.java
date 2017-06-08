package com.campusstreet.model;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.campusstreet.api.BountyHallClient;
import com.campusstreet.api.ServiceGenerator;
import com.campusstreet.common.Const;
import com.campusstreet.entity.BountyHallInfo;
import com.campusstreet.entity.BuyZoneInfo;
import com.campusstreet.entity.CategoriesInfo;
import com.campusstreet.entity.JoinInfo;
import com.campusstreet.entity.UserJoinTaskInfo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.R.attr.type;
import static com.campusstreet.common.Const.MULTIPART_FORM_DATA;

/**
 * Created by Orange on 2017/4/27.
 */

public class BountyHallImpl implements IBountyHallBiz {

    private final String TAG = this.getClass().getSimpleName();
    private static BountyHallImpl sBountyHallImple;
    private BountyHallClient mBountyHallClient;


    private BountyHallImpl(Context context) {
        mBountyHallClient = ServiceGenerator.createService(context, BountyHallClient.class);
    }

    public static BountyHallImpl getInstance(Context context) {

        if (sBountyHallImple == null) {
            sBountyHallImple = new BountyHallImpl(context);
        }
        return sBountyHallImple;
    }

    @Override
    public void fetchTaskList(int type, int pi, String key, @NonNull final LoadTaskListCallback callback) {
        Call<JsonObject> call;
        if (key != null) {
            call = mBountyHallClient.getTasks(type, key, pi);
        } else {
            call = mBountyHallClient.getTasks(type, pi);
        }
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject bodyJson = response.body();
                if (bodyJson != null) {
                    int res = bodyJson.get(Const.RES_KEY).getAsInt();
                    if (res == 1) {
                        if (bodyJson.get(Const.TOTAL_KEY).getAsInt() != 0) {
                            JsonArray resultJsons = bodyJson.get(Const.DATA_KEY).getAsJsonArray();
                            Gson gson = new GsonBuilder().setLenient().create();
                            List<BountyHallInfo> bountyHallInfos = new ArrayList<>();
                            for (int i = 0; i < resultJsons.size(); i++) {
                                JsonObject json = resultJsons.get(i).getAsJsonObject();
                                BountyHallInfo bountyHallInfo = gson.fromJson(json, BountyHallInfo.class);
                                bountyHallInfos.add(bountyHallInfo);
                            }
                            callback.onTaskListLoaded(bountyHallInfos);
                        } else {
                            callback.onDataNotAvailable("暂时没有数据");
                        }

                    } else {
                        callback.onDataNotAvailable(bodyJson.get(Const.MESSAGE_KEY).getAsString());
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                callback.onDataNotAvailable("网络异常");
                Log.d(TAG, "onFailure: " + t);
            }
        });
    }

    @Override
    public void fetchBountyHallCategories(@NonNull final LoadBountyHallCategoriesCallback callback) {
        Call<JsonObject> call = mBountyHallClient.getTaskType();
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject bodyJson = response.body();
                if (bodyJson != null) {
                    int res = bodyJson.get(Const.RES_KEY).getAsInt();
                    if (res == 1) {
                        if (bodyJson.get(Const.TOTAL_KEY).getAsInt() != 0) {
                            JsonArray resultJsons = bodyJson.get(Const.DATA_KEY).getAsJsonArray();
                            Gson gson = new GsonBuilder().setLenient().create();
                            List<CategoriesInfo> categoriesInfos = new ArrayList<>();
                            for (int i = 0; i < resultJsons.size(); i++) {
                                JsonObject json = resultJsons.get(i).getAsJsonObject();
                                CategoriesInfo categoriesInfo = gson.fromJson(json, CategoriesInfo.class);
                                categoriesInfos.add(categoriesInfo);
                            }
                            callback.onBountyHallCategoriesLoaded(categoriesInfos);
                        } else {
                            callback.onDataNotAvailable("暂时没有数据");
                        }

                    } else {
                        callback.onDataNotAvailable(bodyJson.get(Const.MESSAGE_KEY).getAsString());
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                callback.onDataNotAvailable("网络异常");
                Log.d(TAG, "onFailure: " + t);
            }
        });
    }

    @Override
    public void fetchjoinTaskList(int tp, int state, int pi, @NonNull final LoadJoinTaskListCallback callback) {
        Call<JsonObject> call = mBountyHallClient.getJoinTask(tp, state, pi);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject bodyJson = response.body();
                if (bodyJson != null) {
                    int res = bodyJson.get(Const.RES_KEY).getAsInt();
                    if (res == 1) {
                        if (bodyJson.get(Const.TOTAL_KEY).getAsInt() != 0) {
                            JsonArray resultJsons = bodyJson.get(Const.DATA_KEY).getAsJsonArray();
                            Gson gson = new GsonBuilder().setLenient().create();
                            List<JoinInfo> joinInfos = new ArrayList<>();
                            for (int i = 0; i < resultJsons.size(); i++) {
                                JsonObject json = resultJsons.get(i).getAsJsonObject();
                                JoinInfo joinInfo = gson.fromJson(json, JoinInfo.class);
                                joinInfos.add(joinInfo);
                            }
                            callback.onJoinTaskListLoaded(joinInfos);
                        } else {
                            callback.onDataNotAvailable("暂时没有数据");
                        }
                    }

                } else {
                    callback.onDataNotAvailable(bodyJson.get(Const.MESSAGE_KEY).getAsString());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                callback.onDataNotAvailable("网络异常");
                Log.d(TAG, "onFailure: " + t);
            }
        });
    }

    @Override
    public void fetchTaskDetail(int tid, @NonNull final LoadTaskDetailCallback callback) {
        Call<JsonObject> call = mBountyHallClient.getTasksDetail(tid);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject bodyJson = response.body();
                if (bodyJson != null) {
                    int res = bodyJson.get(Const.RES_KEY).getAsInt();
                    if (res == 1) {
                        if (bodyJson.get(Const.TOTAL_KEY).getAsInt() != 0) {
                            JsonArray resultJsons = bodyJson.get(Const.DATA_KEY).getAsJsonArray();
                            Gson gson = new GsonBuilder().setLenient().create();
                            BountyHallInfo bountyHallInfo = gson.fromJson(resultJsons.get(0).getAsJsonObject(), BountyHallInfo.class);
                            callback.onUserTaskDetailLoaded(bountyHallInfo);
                        } else {
                            callback.onDataNotAvailable("暂时没有数据");
                        }

                    } else {
                        callback.onDataNotAvailable(bodyJson.get(Const.MESSAGE_KEY).getAsString());
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                callback.onDataNotAvailable("网络异常");
                Log.d(TAG, "onFailure: " + t);
            }
        });
    }


    @Override
    public void onJoinTask(JoinInfo joinInfo, @NonNull final onJoinTaskCallback callback) {
        Map<String, RequestBody> requestBodyMap = new HashMap<>();
        requestBodyMap.put("tid", RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA), String.valueOf(joinInfo.getId())));
        requestBodyMap.put("uid", RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA), joinInfo.getUid()));
        requestBodyMap.put("fee", RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA), joinInfo.getFee()));
        requestBodyMap.put("mobile", RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA), joinInfo.getPhone()));
        requestBodyMap.put("con", RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA), joinInfo.getSummary()));
        Call<JsonObject> call = mBountyHallClient.joinTask(requestBodyMap);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject bodyJson = response.body();
                if (bodyJson != null) {
                    int res = bodyJson.get(Const.RES_KEY).getAsInt();
                    if (res == 1) {

                        callback.onJoinTaskSuccess();
                    } else {
                        callback.onJoinTaskFailure(bodyJson.get(Const.MESSAGE_KEY).getAsString());
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                callback.onJoinTaskFailure("网络异常");
                Log.d(TAG, "onFailure: " + t);
            }
        });
    }

    @Override
    public void onPassJoinTask(String uid, int tid, int tpid, int st, @NonNull final onPassJoinTaskCallback callback) {
        Call<JsonObject> call = mBountyHallClient.passJoinTask(uid, tid, tpid, st);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject bodyJson = response.body();
                if (bodyJson != null) {
                    int res = bodyJson.get(Const.RES_KEY).getAsInt();
                    if (res == 1) {

                        callback.onPassJoinTaskSuccess();
                    } else {
                        callback.onPassJoinTaskFailure(bodyJson.get(Const.MESSAGE_KEY).getAsString());
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                callback.onPassJoinTaskFailure("网络异常");
                Log.d(TAG, "onFailure: " + t);
            }
        });
    }

    @Override
    public void onPublisherOpTask(String uid, int tpid, int taskid, int state, @NonNull final onPublisherOpTaskCallback callback) {
        Call<JsonObject> call = mBountyHallClient.PublisherOpTask(uid, tpid, taskid, state);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject bodyJson = response.body();
                if (bodyJson != null) {
                    int res = bodyJson.get(Const.RES_KEY).getAsInt();
                    if (res == 1) {

                        callback.onPublisherOpTaskSuccess();
                    } else {
                        callback.onPublisherOpTaskFailure(bodyJson.get(Const.MESSAGE_KEY).getAsString());
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                callback.onPublisherOpTaskFailure("网络异常");
                Log.d(TAG, "onFailure: " + t);
            }
        });
    }

    @Override
    public void onCompleteTask(String uid, int tpid, int taskid, @NonNull final onCompleteTaskCallback callback) {
        Call<JsonObject> call = mBountyHallClient.CompleteTask(uid, tpid, taskid);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject bodyJson = response.body();
                if (bodyJson != null) {
                    int res = bodyJson.get(Const.RES_KEY).getAsInt();
                    if (res == 1) {
                        callback.onCompleteTaskSuccess();
                    } else {
                        callback.onCompleteTaskFailure(bodyJson.get(Const.MESSAGE_KEY).getAsString());
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                callback.onCompleteTaskFailure("网络异常");
                Log.d(TAG, "onFailure: " + t);
            }
        });
    }

    @Override
    public void onGiveUpTask(String uid, int tpid, int taskid, @NonNull final onGiveUpTaskCallback callback) {
        Call<JsonObject> call = mBountyHallClient.GiveUpTask(uid, tpid, taskid);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject bodyJson = response.body();
                if (bodyJson != null) {
                    int res = bodyJson.get(Const.RES_KEY).getAsInt();
                    if (res == 1) {

                        callback.onGiveUpTaskSuccess();
                    } else {
                        callback.onGiveUpTaskFailure(bodyJson.get(Const.MESSAGE_KEY).getAsString());
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                callback.onGiveUpTaskFailure("网络异常");
                Log.d(TAG, "onFailure: " + t);
            }
        });
    }

    @Override
    public void onStartTask(String uid, int tid, String state, @NonNull final onStartTaskCallback callback) {
        Call<JsonObject> call = mBountyHallClient.StartTask(uid, tid, state);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject bodyJson = response.body();
                if (bodyJson != null) {
                    int res = bodyJson.get(Const.RES_KEY).getAsInt();
                    if (res == 1) {

                        callback.onStartTaskSuccess();
                    } else {
                        callback.onStartTaskFailure(bodyJson.get(Const.MESSAGE_KEY).getAsString());
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                callback.onStartTaskFailure("网络异常");
                Log.d(TAG, "onFailure: " + t);
            }
        });
    }

    @Override
    public void addTask(BountyHallInfo bountyHallInfo, @NonNull final addTaskCallback callback) {
        Map<String, RequestBody> requestBodyMap = new HashMap<>();
        requestBodyMap.put("uid", RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA), bountyHallInfo.getUid()));
        if (bountyHallInfo.getId() != null) {
            requestBodyMap.put("tid", RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA), String.valueOf(bountyHallInfo.getId())));
        }
        requestBodyMap.put("title", RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA), bountyHallInfo.getTitle()));
        requestBodyMap.put("con", RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA), bountyHallInfo.getCon()));
        requestBodyMap.put("taskfee", RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA), bountyHallInfo.getFee()));
        requestBodyMap.put("tasktype", RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA), bountyHallInfo.getType()));
        requestBodyMap.put("person", RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA), String.valueOf(bountyHallInfo.getPerson())));
        requestBodyMap.put("endtime", RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA), bountyHallInfo.getEndtime()));
        requestBodyMap.put("linkman", RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA), bountyHallInfo.getLinkman()));
        requestBodyMap.put("mobile", RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA), bountyHallInfo.getMobile()));
        requestBodyMap.put("keys", RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA), bountyHallInfo.getKey()));
        Call<JsonObject> call = mBountyHallClient.addTask(requestBodyMap);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject bodyJson = response.body();
                if (bodyJson != null) {
                    int res = bodyJson.get(Const.RES_KEY).getAsInt();
                    if (res == 1) {

                        callback.onAddSuccess();
                    } else {
                        callback.onAddFailure(bodyJson.get(Const.MESSAGE_KEY).getAsString());
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                callback.onAddFailure("网络异常");
                Log.d(TAG, "onFailure: " + t);
            }
        });
    }

    @Override
    public void fetchUserTaskList(String uid, int tp, String key, int pi, final LoadUserTaskListCallback callback) {
        Call<JsonObject> call;
        if (key != null) {
            call = mBountyHallClient.getUserTasks(uid, tp, key, pi);
        } else {
            call = mBountyHallClient.getUserTasks(uid, tp, pi);
        }
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject bodyJson = response.body();
                if (bodyJson != null) {
                    int res = bodyJson.get(Const.RES_KEY).getAsInt();
                    if (res == 1) {
                        if (bodyJson.get(Const.TOTAL_KEY).getAsInt() != 0) {
                            JsonArray resultJsons = bodyJson.get(Const.DATA_KEY).getAsJsonArray();
                            Gson gson = new GsonBuilder().setLenient().create();
                            List<BountyHallInfo> bountyHallInfos = new ArrayList<>();
                            for (int i = 0; i < resultJsons.size(); i++) {
                                JsonObject json = resultJsons.get(i).getAsJsonObject();
                                BountyHallInfo bountyHallInfo = gson.fromJson(json, BountyHallInfo.class);
                                bountyHallInfos.add(bountyHallInfo);
                            }
                            callback.onUserTaskListLoaded(bountyHallInfos);
                        } else {
                            callback.onDataNotAvailable("暂时没有数据");
                        }

                    } else {
                        callback.onDataNotAvailable(bodyJson.get(Const.MESSAGE_KEY).getAsString());
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                callback.onDataNotAvailable("网络异常");
                Log.d(TAG, "onFailure: " + t);
            }
        });
    }

    @Override
    public void fetchUserJoinTaskList(String uid, int pi, @NonNull final LoadUserJoinTaskListCallback callback) {
        Call<JsonObject> call = mBountyHallClient.getUserJoinTasks(uid, pi);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject bodyJson = response.body();
                if (bodyJson != null) {
                    int res = bodyJson.get(Const.RES_KEY).getAsInt();
                    if (res == 1) {
                        if (bodyJson.get(Const.TOTAL_KEY).getAsInt() != 0) {
                            JsonArray resultJsons = bodyJson.get(Const.DATA_KEY).getAsJsonArray();
                            Gson gson = new GsonBuilder().setLenient().create();
                            List<UserJoinTaskInfo> userJoinTaskInfoss = new ArrayList<>();
                            for (int i = 0; i < resultJsons.size(); i++) {
                                JsonObject json = resultJsons.get(i).getAsJsonObject();
                                UserJoinTaskInfo userJoinTaskInfo = gson.fromJson(json, UserJoinTaskInfo.class);
                                userJoinTaskInfoss.add(userJoinTaskInfo);
                            }
                            callback.onUserJoinTaskListLoaded(userJoinTaskInfoss);
                        } else {
                            callback.onDataNotAvailable("暂时没有数据");
                        }

                    } else {
                        callback.onDataNotAvailable(bodyJson.get(Const.MESSAGE_KEY).getAsString());
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                callback.onDataNotAvailable("网络异常");
                Log.d(TAG, "onFailure: " + t);
            }
        });
    }
}
