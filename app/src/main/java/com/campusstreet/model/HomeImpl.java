package com.campusstreet.model;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.campusstreet.api.AdvertisementClient;
import com.campusstreet.api.CampusRecruitmentClient;
import com.campusstreet.api.HomeClient;
import com.campusstreet.api.ServiceGenerator;
import com.campusstreet.common.Const;
import com.campusstreet.entity.BannerInfo;
import com.campusstreet.entity.HomeDynamicInfo;
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

/**
 * Created by Orange on 2017/4/16.
 */

public class HomeImpl implements IHomeBiz {

    private final String TAG = this.getClass().getSimpleName();
    private static HomeImpl sHomeImple;
    private AdvertisementClient mAdvertisementClient;
    private HomeClient mHomeClient;


    private HomeImpl(Context context) {
        mAdvertisementClient = ServiceGenerator.createService(context, AdvertisementClient.class);
        mHomeClient = ServiceGenerator.createService(context, HomeClient.class);
    }

    public static HomeImpl getInstance(Context context) {

        if (sHomeImple == null) {
            sHomeImple = new HomeImpl(context);
        }
        return sHomeImple;
    }

    @Override
    public void fetchBannerImage(@NonNull final GetBannerCallback callback) {
        Call<JsonObject> call = mAdvertisementClient.getHomeBanner();
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
                            List<BannerInfo> bannerInfos = new ArrayList<>();
                            for (int i = 0; i < resultJsons.size(); i++) {
                                JsonObject json = resultJsons.get(i).getAsJsonObject();
                                BannerInfo bannerInfo = gson.fromJson(json, BannerInfo.class);
                                bannerInfos.add(bannerInfo);
                            }
                            callback.onFetchSuccess(bannerInfos);
                        } else {
                            callback.onFetchFailure("暂时没有数据");
                        }

                    } else {
                        callback.onFetchFailure(bodyJson.get(Const.MESSAGE_KEY).getAsString());
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                callback.onFetchFailure("网络异常");
                Log.d(TAG, "onFailure: " + t);
            }
        });

    }

    @Override
    public void fetchAdImage(String picType, @NonNull GetAdImageCallback callback) {

    }

    @Override
    public void getdynamicList(final String uid, @NonNull final LoaddynamicListCallback callback) {
        Call<JsonObject> call = mHomeClient.GetIndextrend(uid);
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
                            List<HomeDynamicInfo> homeDynamicInfos = new ArrayList<>();
                            for (int i = 0; i < resultJsons.size(); i++) {
                                JsonObject json = resultJsons.get(i).getAsJsonObject();
                                HomeDynamicInfo homeDynamicInfo = gson.fromJson(json, HomeDynamicInfo.class);
                                homeDynamicInfos.add(homeDynamicInfo);
                            }
                            callback.ondynamicListLoaded(homeDynamicInfos);
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
