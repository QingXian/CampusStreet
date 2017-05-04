package com.campusstreet.model;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.campusstreet.api.CampusInformationClient;
import com.campusstreet.api.ServiceGenerator;
import com.campusstreet.common.Const;
import com.campusstreet.entity.NewInfo;
import com.campusstreet.entity.RecruitInfo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Orange on 2017/4/24.
 */

public class CampusInformationImpl implements ICampusInformationBiz {

    private final String TAG = this.getClass().getSimpleName();
    private static CampusInformationImpl sCampusInformationImple;
    private CampusInformationClient mCampusInformationClient;


    private CampusInformationImpl(Context context) {
        mCampusInformationClient = ServiceGenerator.createService(context, CampusInformationClient.class);
    }

    public static CampusInformationImpl getInstance(Context context) {

        if (sCampusInformationImple == null) {
            sCampusInformationImple = new CampusInformationImpl(context);
        }
        return sCampusInformationImple;
    }

    @Override
    public void fetchCampusInformationList(String key, int pi, @NonNull final LoadCampusInformationListCallback callback) {
        Call<JsonObject> call;
        if (key != null) {
            call = mCampusInformationClient.getNews(key, pi);
        } else {
            call = mCampusInformationClient.getNews(pi);
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
                            List<NewInfo> newInfos = new ArrayList<>();
                            for (int i = 0; i < resultJsons.size(); i++) {
                                JsonObject json = resultJsons.get(i).getAsJsonObject();
                                NewInfo newInfo = gson.fromJson(json, NewInfo.class);
                                newInfos.add(newInfo);
                            }
                            callback.onCampusInformationListLoaded(newInfos);
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
                callback.onDataNotAvailable("服务器异常");
                Log.d(TAG, "onFailure: " + t);
            }
        });
    }

    @Override
    public void fetchCampusInformationDetail(int nid, @NonNull final LoadCampusInformationDetailCallback callback) {
        Call<JsonObject> call = mCampusInformationClient.getNewDetail(nid);
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
                            NewInfo newInfo = gson.fromJson(resultJsons.get(0).getAsJsonObject(), NewInfo.class);
                            callback.onCampusInformationDetailLoaded(newInfo);
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
                callback.onDataNotAvailable("服务器异常");
                Log.d(TAG, "onFailure: " + t);
            }
        });
    }
}
