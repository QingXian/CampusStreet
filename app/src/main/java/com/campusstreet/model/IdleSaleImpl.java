package com.campusstreet.model;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.campusstreet.api.IdleSaleClient;
import com.campusstreet.api.ServiceGenerator;
import com.campusstreet.common.Const;
import com.campusstreet.entity.IdleSaleInfo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.R.attr.id;

/**
 * Created by Orange on 2017/4/19.
 */
public class IdleSaleImpl implements IdleSaleBiz {

    private final String TAG = this.getClass().getSimpleName();
    private static IdleSaleImpl sIdleSaleImple;
    private IdleSaleClient mIdleSaleClient;


    private IdleSaleImpl(Context context) {
        mIdleSaleClient = ServiceGenerator.createService(context, IdleSaleClient.class);
    }

    public static IdleSaleImpl getInstance(Context context) {

        if (sIdleSaleImple == null) {
            sIdleSaleImple = new IdleSaleImpl(context);
        }
        return sIdleSaleImple;
    }

    @Override
    public void fetchIdleSaleList(int type, int pi, @NonNull final LoadIdleSaleListCallback callback) {
        Call<JsonObject> call = mIdleSaleClient.getIdleSale(type, pi);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject bodyJson = response.body();
                if (bodyJson != null) {
                    int res = bodyJson.get(Const.RES_KEY).getAsInt();
                    if (res == 1) {
                        JsonArray resultJsons = bodyJson.get(Const.DATA_KEY).getAsJsonArray();
                        Gson gson = new GsonBuilder().setLenient().create();
                        List<IdleSaleInfo> IdleSaleInfoList = new ArrayList<>();
                        for (int i = 0; i < resultJsons.size(); i++) {
                            JsonObject json = resultJsons.get(i).getAsJsonObject();
                            IdleSaleInfo idleSaleInfo = gson.fromJson(json, IdleSaleInfo.class);
                            IdleSaleInfoList.add(idleSaleInfo);
                        }
                        callback.onIdleSaleListLoaded(IdleSaleInfoList);

                    } else if (res == 1) {
                        callback.onDataNotAvailable("暂时没有数据");
                    } else {
                        callback.onDataNotAvailable(bodyJson.get(Const.MESSAGE_KEY).getAsString());
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                callback.onDataNotAvailable("服务器异常");
                Log.d(TAG, "onFailure: "+t);
            }
        });
    }

    @Override
    public void searchIdleSale(String picType, @NonNull searchIdleSaleCallback callback) {

    }

    @Override
    public void addGoods(@NonNull addGoodsCallback callback) {

    }

    @Override
    public void leaveMessagae(@NonNull LeaveMessageCallback callback) {

    }
}
