package com.campusstreet.model;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.campusstreet.api.PartnerClient;
import com.campusstreet.api.ServiceGenerator;
import com.campusstreet.common.Const;
import com.campusstreet.entity.BountyHallInfo;
import com.campusstreet.entity.CategoriesInfo;
import com.campusstreet.entity.NewInfo;
import com.campusstreet.entity.PartnerInfo;
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

public class PartnerImpl implements IPartnerBiz {

    private final String TAG = this.getClass().getSimpleName();
    private static PartnerImpl sPartnerImple;
    private PartnerClient mPartnerClient;


    private PartnerImpl(Context context) {
        mPartnerClient = ServiceGenerator.createService(context, PartnerClient.class);
    }

    public static PartnerImpl getInstance(Context context) {

        if (sPartnerImple == null) {
            sPartnerImple = new PartnerImpl(context);
        }
        return sPartnerImple;
    }

    @Override
    public void fetchPartnerList(String key, int type, int pi, @NonNull final LoadPartnerListCallback callback) {
        Call<JsonObject> call;
        if (key != null) {
            call = mPartnerClient.getPartner(key, type, pi);
        } else {
            call = mPartnerClient.getPartner(type, pi);
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
                            List<PartnerInfo> partnerInfos = new ArrayList<>();
                            for (int i = 0; i < resultJsons.size(); i++) {
                                JsonObject json = resultJsons.get(i).getAsJsonObject();
                                PartnerInfo partnerInfo = gson.fromJson(json, PartnerInfo.class);
                                partnerInfos.add(partnerInfo);
                            }
                            callback.onPartnerListLoaded(partnerInfos);
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
    public void fetchPartnerCategories(@NonNull final LoadPartnerCategoriesCallback callback) {
        Call<JsonObject> call = mPartnerClient.getPartnerType();
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
                            callback.onPartnerCategoriesLoaded(categoriesInfos);
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
    public void fetchPartnerDetail(int pid, @NonNull final LoadPartnerDetailCallback callback) {
        Call<JsonObject> call = mPartnerClient.getPartnerDetail(pid);
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
                            PartnerInfo partnerInfo = gson.fromJson(resultJsons.get(0).getAsJsonObject(), PartnerInfo.class);
                            callback.onPartnerDetailLoaded(partnerInfo);
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
