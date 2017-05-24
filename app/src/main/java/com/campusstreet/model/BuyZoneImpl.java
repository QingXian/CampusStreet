package com.campusstreet.model;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.campusstreet.api.BuyZoneClient;
import com.campusstreet.api.ServiceGenerator;
import com.campusstreet.common.Const;
import com.campusstreet.entity.BuyZoneInfo;
import com.campusstreet.entity.LeaveMessageInfo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.R.attr.id;
import static com.campusstreet.common.Const.MULTIPART_FORM_DATA;

/**
 * Created by Orange on 2017/4/24.
 */

public class BuyZoneImpl implements IBuyZoneBiz {

    private final String TAG = this.getClass().getSimpleName();
    private static BuyZoneImpl sBuyZoneImple;
    private BuyZoneClient mBuyZoneClient;


    private BuyZoneImpl(Context context) {
        mBuyZoneClient = ServiceGenerator.createService(context, BuyZoneClient.class);
    }

    public static BuyZoneImpl getInstance(Context context) {

        if (sBuyZoneImple == null) {
            sBuyZoneImple = new BuyZoneImpl(context);
        }
        return sBuyZoneImple;
    }

    @Override
    public void fetchBuyZoneList(int pi, @NonNull final LoadBuyZoneListCallback callback) {
        Call<JsonObject> call = mBuyZoneClient.getBuyZone(pi);
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
                            List<BuyZoneInfo> buyZoneInfoList = new ArrayList<>();
                            for (int i = 0; i < resultJsons.size(); i++) {
                                JsonObject json = resultJsons.get(i).getAsJsonObject();
                                BuyZoneInfo buyZoneInfo = gson.fromJson(json, BuyZoneInfo.class);
                                buyZoneInfoList.add(buyZoneInfo);
                            }
                            callback.onBuyZoneListLoaded(buyZoneInfoList);
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
    public void fetchUserBuyZoneList(String uid, String key, int pi, @NonNull final LoadUserBuyZoneListCallback callback) {
        Call<JsonObject> call = mBuyZoneClient.getUserBuyZone(uid, key, pi);
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
                            List<BuyZoneInfo> buyZoneInfoList = new ArrayList<>();
                            for (int i = 0; i < resultJsons.size(); i++) {
                                JsonObject json = resultJsons.get(i).getAsJsonObject();
                                BuyZoneInfo buyZoneInfo = gson.fromJson(json, BuyZoneInfo.class);
                                buyZoneInfoList.add(buyZoneInfo);
                            }
                            callback.onUserBuyZoneListLoaded(buyZoneInfoList);
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
    public void addBuy(BuyZoneInfo buyZoneInfo, @NonNull final addIdleGoodsCallback callback) {
        Map<String, RequestBody> requestBodyMap = new HashMap<>();
        requestBodyMap.put("uid", RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA), String.valueOf(buyZoneInfo.getUid())));
        requestBodyMap.put("name", RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA), buyZoneInfo.getName()));
        requestBodyMap.put("money", RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA), buyZoneInfo.getMoney()));
        requestBodyMap.put("mobile", RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA), buyZoneInfo.getMobile()));
        if (buyZoneInfo.getQq() != null) {
            requestBodyMap.put("qq", RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA), buyZoneInfo.getQq()));
        }
        requestBodyMap.put("content", RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA), buyZoneInfo.getCon()));
        Call<JsonObject> call = mBuyZoneClient.pushBuy(requestBodyMap);
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
    public void leaveMessagae(int wid, String uid, String con, @NonNull final LeaveMessageCallback callback) {
        Call<JsonObject> call = mBuyZoneClient.LeaveMessage(wid, uid, con);
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
    public void fetchBuyZoneMessageList(int id, int pi, @NonNull final LoadBuyZoneMessageListCallback callback) {
        Call<JsonObject> call = mBuyZoneClient.getBuyZoneMessage(id, pi);
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
                            List<LeaveMessageInfo> leaveMessageInfoList = new ArrayList<>();
                            for (int i = 0; i < resultJsons.size(); i++) {
                                JsonObject json = resultJsons.get(i).getAsJsonObject();
                                LeaveMessageInfo leaveMessageInfo = gson.fromJson(json, LeaveMessageInfo.class);
                                leaveMessageInfoList.add(leaveMessageInfo);
                            }
                            callback.onBuyZoneMessageListLoaded(leaveMessageInfoList);
                        } else {
                            callback.onDataNotAvailable("暂时没有人留言");
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
