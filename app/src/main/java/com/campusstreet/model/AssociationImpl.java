package com.campusstreet.model;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.campusstreet.api.AssociationClient;
import com.campusstreet.api.ServiceGenerator;
import com.campusstreet.common.Const;
import com.campusstreet.entity.AssociationInfo;
import com.campusstreet.entity.AssociationNumInfo;
import com.campusstreet.entity.AssociationPostInfo;
import com.campusstreet.entity.AssociationPostMessageInfo;
import com.campusstreet.entity.BountyHallInfo;
import com.campusstreet.entity.AssociationPostInfo;
import com.campusstreet.entity.UserAssociationInfo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.R.attr.key;

/**
 * Created by Orange on 2017/5/7.
 */

public class AssociationImpl implements IAssociationBiz {
    private final String TAG = this.getClass().getSimpleName();
    private static AssociationImpl sAssociationImple;
    private AssociationClient mAssociationClient;


    private AssociationImpl(Context context) {
        mAssociationClient = ServiceGenerator.createService(context, AssociationClient.class);
    }

    public static AssociationImpl getInstance(Context context) {

        if (sAssociationImple == null) {
            sAssociationImple = new AssociationImpl(context);
        }
        return sAssociationImple;
    }

    @Override
    public void fetchAssociationList(int pi, @NonNull final LoadAssociationListCallback callback) {
        Call<JsonObject> call = mAssociationClient.getAssociation(pi);
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
                            List<AssociationInfo> associationInfos = new ArrayList<>();
                            for (int i = 0; i < resultJsons.size(); i++) {
                                JsonObject json = resultJsons.get(i).getAsJsonObject();
                                AssociationInfo associationInfo = gson.fromJson(json, AssociationInfo.class);
                                associationInfos.add(associationInfo);
                            }
                            callback.onAssociationListLoaded(associationInfos);
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
    public void fetchAssociationPostList(int aid, int pi, @NonNull final LoadAssociationPostCallback callback) {
        Call<JsonObject> call = mAssociationClient.getAssociationPost(aid, pi);
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
                            List<AssociationPostInfo> associationPostInfos = new ArrayList<>();
                            for (int i = 0; i < resultJsons.size(); i++) {
                                JsonObject json = resultJsons.get(i).getAsJsonObject();
                                AssociationPostInfo associationInfo = gson.fromJson(json, AssociationPostInfo.class);
                                associationPostInfos.add(associationInfo);
                            }
                            callback.onAssociationPostLoaded(associationPostInfos);
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
    public void onLeaveMessage(int pid, String uid, String con, @NonNull final onLeaveMessageCallback callback) {
        Call<JsonObject> call = mAssociationClient.leaveMessage(pid, uid, con);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject bodyJson = response.body();
                if (bodyJson != null) {
                    int res = bodyJson.get(Const.RES_KEY).getAsInt();
                    if (res == 1) {

                        callback.onLeaveMessageSuccess();
                    } else {
                        callback.onLeaveMessageFailure(bodyJson.get(Const.MESSAGE_KEY).getAsString());
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                callback.onLeaveMessageFailure("网络异常");
                Log.d(TAG, "onFailure: " + t);
            }
        });
    }

    @Override
    public void onJoinAssociation(int aid, String uid, String con, @NonNull final onJoinAssociationCallback callback) {
        Call<JsonObject> call = mAssociationClient.JoinAssociation(aid, uid, con);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject bodyJson = response.body();
                if (bodyJson != null) {
                    int res = bodyJson.get(Const.RES_KEY).getAsInt();
                    if (res == 1) {

                        callback.onJoinAssociationSuccess();
                    } else {
                        callback.onJoinAssociationFailure(bodyJson.get(Const.MESSAGE_KEY).getAsString());
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                callback.onJoinAssociationFailure("网络异常");
                Log.d(TAG, "onFailure: " + t);
            }
        });
    }

    @Override
    public void onApplyJoinAssn(int pid, String uid, int st, String con, @NonNull final onApplyJoinAssnCallback callback) {
        Call<JsonObject> call = mAssociationClient.leaveMessage(pid, uid, con);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject bodyJson = response.body();
                if (bodyJson != null) {
                    int res = bodyJson.get(Const.RES_KEY).getAsInt();
                    if (res == 1) {

                        callback.onApplyJoinAssnSuccess();
                    } else {
                        callback.onApplyJoinAssnFailure(bodyJson.get(Const.MESSAGE_KEY).getAsString());
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                callback.onApplyJoinAssnFailure("网络异常");
                Log.d(TAG, "onFailure: " + t);
            }
        });
    }


    @Override
    public void fetchAssociationNumList(int aid, int pi, int ps, @NonNull final LoadAssociationNumListCallback callback) {
        Call<JsonObject> call = mAssociationClient.getAssociationNumber(aid, pi, ps);
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
                            List<AssociationNumInfo> associationNumInfos = new ArrayList<>();
                            for (int i = 0; i < resultJsons.size(); i++) {
                                JsonObject json = resultJsons.get(i).getAsJsonObject();
                                AssociationNumInfo associationNumInfo = gson.fromJson(json, AssociationNumInfo.class);
                                associationNumInfos.add(associationNumInfo);
                            }
                            callback.onAssociationNumListLoaded(associationNumInfos);
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
    public void addAssociationPost(int aid, String uid, String con, String title, @NonNull final addAssociationPostCallback callback) {
        Call<JsonObject> call = mAssociationClient.addAssociationPost(aid, uid, con, title);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject bodyJson = response.body();
                if (bodyJson != null) {
                    int res = bodyJson.get(Const.RES_KEY).getAsInt();
                    if (res == 0) {
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
    public void fetchAssociationPostMessageList(int pid, int pi, final LoadAssociationPostMessageCallback callback) {
        Call<JsonObject> call = mAssociationClient.getAssociationPostMessage(pid, pi);
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
                            List<AssociationPostMessageInfo> associationPostMessageInfos = new ArrayList<>();
                            for (int i = 0; i < resultJsons.size(); i++) {
                                JsonObject json = resultJsons.get(i).getAsJsonObject();
                                AssociationPostMessageInfo associationPostMessageInfo = gson.fromJson(json, AssociationPostMessageInfo.class);
                                associationPostMessageInfos.add(associationPostMessageInfo);
                            }
                            callback.onAssociationPostMessageLoaded(associationPostMessageInfos);
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
    public void fetchAssociationPostDetail(int pid, @NonNull final LoadAssociationPostDetailCallback callback) {
        Call<JsonObject> call = mAssociationClient.getAssociationPostDetail(pid);
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
                            AssociationPostInfo associationPostInfo = gson.fromJson(resultJsons.get(0).getAsJsonObject(), AssociationPostInfo.class);
                            callback.onAssociationPostDetailLoaded(associationPostInfo);
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
    public void fetchUserAssociationList(int pi, String uid, @NonNull final LoadUserAssociationListCallback callback) {
        Call<JsonObject> call = mAssociationClient.getUserAssociation(pi, uid);
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
                            List<UserAssociationInfo> userAssociationInfos = new ArrayList<>();
                            for (int i = 0; i < resultJsons.size(); i++) {
                                JsonObject json = resultJsons.get(i).getAsJsonObject();
                                UserAssociationInfo userAssociationInfo = gson.fromJson(json, UserAssociationInfo.class);
                                userAssociationInfos.add(userAssociationInfo);
                            }
                            callback.onUserAssociationListLoaded(userAssociationInfos);
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

