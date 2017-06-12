package com.campusstreet.model;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.campusstreet.api.BountyHallClient;
import com.campusstreet.api.CampusRecruitmentClient;
import com.campusstreet.api.ServiceGenerator;
import com.campusstreet.common.Const;
import com.campusstreet.entity.BountyHallInfo;
import com.campusstreet.entity.NewInfo;
import com.campusstreet.entity.RecruitInfo;
import com.campusstreet.entity.StudyWorkInfo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.R.attr.type;

/**
 * Created by Orange on 2017/4/18.
 */

public class CampusRecruitmentImpl implements ICampusRecruitmentBiz {

    private final String TAG = this.getClass().getSimpleName();
    private static CampusRecruitmentImpl sCampusRecruitmentImple;
    private CampusRecruitmentClient mCampusRecruitmentClient;


    private CampusRecruitmentImpl(Context context) {
        mCampusRecruitmentClient = ServiceGenerator.createService(context, CampusRecruitmentClient.class);
    }

    public static CampusRecruitmentImpl getInstance(Context context) {

        if (sCampusRecruitmentImple == null) {
            sCampusRecruitmentImple = new CampusRecruitmentImpl(context);
        }
        return sCampusRecruitmentImple;
    }

    @Override
    public void fetchCampusRecruitmentList(String key, int pi, @NonNull final LoadCampusRecruitmentListCallback callback) {
        Call<JsonObject> call;
        if (key != null) {
            call = mCampusRecruitmentClient.getRecruitList(key, pi);
        } else {
            call = mCampusRecruitmentClient.getRecruitList(pi);
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
                            List<RecruitInfo> recruitInfos = new ArrayList<>();
                            for (int i = 0; i < resultJsons.size(); i++) {
                                JsonObject json = resultJsons.get(i).getAsJsonObject();
                                RecruitInfo recruitInfo = gson.fromJson(json, RecruitInfo.class);
                                recruitInfos.add(recruitInfo);
                            }
                            callback.onCampusRecruitmentListLoaded(recruitInfos);
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
    public void fetchStudyWorkList(String key, int pi, @NonNull final LoadStudyWorkListCallback callback) {
        Call<JsonObject> call;
        if (key != null) {
            call = mCampusRecruitmentClient.getStudyWorkList(key, pi);
        } else {
            call = mCampusRecruitmentClient.getStudyWorkList(pi);
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
                            List<StudyWorkInfo> studyWorkInfos = new ArrayList<>();
                            for (int i = 0; i < resultJsons.size(); i++) {
                                JsonObject json = resultJsons.get(i).getAsJsonObject();
                                StudyWorkInfo studyWorkInfo = gson.fromJson(json, StudyWorkInfo.class);
                                studyWorkInfos.add(studyWorkInfo);
                            }
                            callback.onStudyWorkListLoaded(studyWorkInfos);
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
    public void fetchCampusRecruitmentDetail(int swid, @NonNull final LoadCampusRecruitmentDetailCallback callback) {
        Call<JsonObject> call = mCampusRecruitmentClient.getRecruitDetail(swid);
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
                            RecruitInfo recruitInfo = gson.fromJson(resultJsons.get(0).getAsJsonObject(), RecruitInfo.class);
                            callback.onCampusRecruitmentDetailLoaded(recruitInfo);
                        } else {
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
    public void fetchStudyWorkDetail(int rid, @NonNull final LoadStudyWorkDetailCallback callback) {
        Call<JsonObject> call = mCampusRecruitmentClient.getStudyWorkDetail(rid);
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
                            StudyWorkInfo studyWorkInfo = gson.fromJson(resultJsons.get(0).getAsJsonObject(), StudyWorkInfo.class);
                            callback.onStudyWorkDetailLoaded(studyWorkInfo);
                        } else {
                            callback.onDataNotAvailable("");
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
