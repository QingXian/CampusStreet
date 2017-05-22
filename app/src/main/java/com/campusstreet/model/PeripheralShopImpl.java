package com.campusstreet.model;

import android.content.Context;
import android.renderscript.Matrix2f;
import android.support.annotation.NonNull;
import android.util.Log;

import com.campusstreet.api.PeripheralShopClient;
import com.campusstreet.api.ServiceGenerator;
import com.campusstreet.common.Const;
import com.campusstreet.entity.PartnerInfo;
import com.campusstreet.entity.PeripheralShopGoodInfo;
import com.campusstreet.entity.PeripheralShopInfo;
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

public class PeripheralShopImpl implements IPeripheralShopBiz {

    private final String TAG = this.getClass().getSimpleName();
    private static PeripheralShopImpl sPeripheralShopImple;
    private PeripheralShopClient mPeripheralShopClient;


    private PeripheralShopImpl(Context context) {
        mPeripheralShopClient = ServiceGenerator.createService(context, PeripheralShopClient.class);
    }

    public static PeripheralShopImpl getInstance(Context context) {

        if (sPeripheralShopImple == null) {
            sPeripheralShopImple = new PeripheralShopImpl(context);
        }
        return sPeripheralShopImple;
    }

    @Override
    public void fetchPeripheralShopList(int type, int pi, String key, @NonNull final LoadPeripheralShopListCallback callback) {
        Call<JsonObject> call;
        if (key != null) {
            call = mPeripheralShopClient.getShop(key, type, pi);
        } else {
            call = mPeripheralShopClient.getShop(type, pi);
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
                            List<PeripheralShopInfo> peripheralShopInfos = new ArrayList<>();
                            for (int i = 0; i < resultJsons.size(); i++) {
                                JsonObject json = resultJsons.get(i).getAsJsonObject();
                                PeripheralShopInfo peripheralShopInfo = gson.fromJson(json, PeripheralShopInfo.class);
                                peripheralShopInfos.add(peripheralShopInfo);
                            }
                            callback.onPeripheralShopListLoaded(peripheralShopInfos);
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
    public void fetchPeripheralCategories(@NonNull final LoadPeripheralCategoriesCallback callback) {
        Call<JsonObject> call = mPeripheralShopClient.getShopType();
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject bodyJson = response.body();
                if (bodyJson != null) {
                    int res = bodyJson.get(Const.RES_KEY).getAsInt();
                    if (res == 1) {
                        if (bodyJson.get(Const.TOTAL_KEY).getAsInt() != 0) {
                            JsonArray resultJsons = bodyJson.get(Const.DATA_KEY).getAsJsonArray();
                            String[] type = new String[resultJsons.size()];
                            for (int i = 0; i < resultJsons.size(); i++) {
                                JsonObject json = resultJsons.get(i).getAsJsonObject();
                                type[i] = json.get("name").getAsString();
                            }
                            callback.onPeripheralCategoriesLoad(type);
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
    public void fetchShopGoodList(int sid, int type, String key, int pi, @NonNull final LoadShopCommodityListCallback callback) {
        Call<JsonObject> call;
        if (key != null) {
            call = mPeripheralShopClient.getShopGood(sid, type, key, pi);
        } else {
            call = mPeripheralShopClient.getShopGood(sid, type, pi);
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
                            List<PeripheralShopGoodInfo> peripheralShopInfos = new ArrayList<>();
                            for (int i = 0; i < resultJsons.size(); i++) {
                                JsonObject json = resultJsons.get(i).getAsJsonObject();
                                PeripheralShopGoodInfo peripheralShopGoodInfo = gson.fromJson(json, PeripheralShopGoodInfo.class);
                                peripheralShopInfos.add(peripheralShopGoodInfo);
                            }
                            callback.onShopCommodityListLoaded(peripheralShopInfos);
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
