package com.campusstreet.model;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.campusstreet.api.BountyHallClient;
import com.campusstreet.api.LiveClient;
import com.campusstreet.api.ServiceGenerator;
import com.campusstreet.common.Const;
import com.campusstreet.entity.BannerInfo;
import com.campusstreet.entity.HomeDynamicInfo;
import com.campusstreet.entity.LiveInfo;
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

public class FindImpl implements IFindBiz {

    private final String TAG = this.getClass().getSimpleName();
    private static FindImpl sFindImple;
    private LiveClient mLiveClient;


    private FindImpl(Context context) {
        mLiveClient = ServiceGenerator.createService(context, LiveClient.class);
    }

    public static FindImpl getInstance(Context context) {

        if (sFindImple == null) {
            sFindImple = new FindImpl(context);
        }
        return sFindImple;
    }


    @Override
    public void fetchFindList(int pi, @NonNull final LoadFindListCallback callback) {
        Call<JsonObject> call = mLiveClient.getLive(pi);
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
                            List<LiveInfo> liveInfos = new ArrayList<>();
                            for (int i = 0; i < resultJsons.size(); i++) {
                                JsonObject json = resultJsons.get(i).getAsJsonObject();
                                LiveInfo liveInfo = gson.fromJson(json, LiveInfo.class);
                                liveInfos.add(liveInfo);
                            }
                            callback.onFindListLoaded(liveInfos);
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
    public void fetchTopImage(String picType, @NonNull GetTopImageCallback callback) {

    }

    @Override
    public void addLive(LiveInfo liveInfo, @NonNull AddLiveCallback callback) {

    }
}
