package com.campusstreet.model;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.campusstreet.api.IdleSaleClient;
import com.campusstreet.api.ServiceGenerator;
import com.campusstreet.common.Const;
import com.campusstreet.entity.IdleSaleInfo;
import com.campusstreet.entity.LeaveMessageInfo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.R.attr.id;
import static android.R.attr.type;
import static com.campusstreet.common.Const.MULTIPART_FORM_DATA;

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
    public void addIdleGoods(IdleSaleInfo idleSaleInfo ,@NonNull final addIdleGoodsCallback callback) {
        Map<String, RequestBody> requestBodyMap = new HashMap<>();
        RequestBody body = null;
        MultipartBody.Part part = null;
        if (idleSaleInfo.getImage()!=null){
        body = RequestBody.create(MediaType.parse("application/otcet-stream"), idleSaleInfo.getImage());
        part = MultipartBody.Part.createFormData("image", idleSaleInfo.getImage().getName(), body);
        }


        //添加请求参数
        requestBodyMap.put("uid", RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA), String.valueOf(idleSaleInfo.getUid())));
        requestBodyMap.put("name", RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA),
                idleSaleInfo.getName()));
        requestBodyMap.put("gmoney", RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA), idleSaleInfo.getMoney()));
        requestBodyMap.put("goodstype", RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA), idleSaleInfo.getGoodstype()));
        requestBodyMap.put("tradeplace", RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA), idleSaleInfo.getTradeplace()));
        requestBodyMap.put("tradetype", RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA), idleSaleInfo.getTradetype()));
        requestBodyMap.put("selltype", RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA), idleSaleInfo.getSelltype()));
        requestBodyMap.put("mobile", RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA), idleSaleInfo.getMobile()));
        requestBodyMap.put("qq", RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA), idleSaleInfo.getQq()));
        requestBodyMap.put("bewrite", RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA), idleSaleInfo.getBewrite()));
        requestBodyMap.put("gcontent", RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA), idleSaleInfo.getContent()));
        Call<JsonObject> call = mIdleSaleClient.pushIdlegoods(requestBodyMap,part);
//        Call<JsonObject> call = mIdleSaleClient.pushIdlegoods(requestBodyMap);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject bodyJson = response.body();
                if (bodyJson != null) {
                    int res = bodyJson.get(Const.RES_KEY).getAsInt();
                    if (res == 1) {
                        callback.onAddSuccess();

                    }
                    else {
                        callback.onAddFailure(bodyJson.get(Const.MESSAGE_KEY).getAsString());
                    }
                }
            }
            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                callback.onAddFailure("服务器异常");
                Log.d(TAG, "onFailure: "+t);
            }
        });
    }

    @Override
    public void leaveMessagae(int gid, String uid, String con, @NonNull final LeaveMessageCallback callback) {
        Call<JsonObject> call = mIdleSaleClient.LeaveMessage(gid,uid,con);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject bodyJson = response.body();
                if (bodyJson != null) {
                    int res = bodyJson.get(Const.RES_KEY).getAsInt();
                    if (res == 1) {
                        callback.onLeaveMessageSuccess();
                        }
                    else {
                        callback.onLeaveMessageFailure(bodyJson.get(Const.MESSAGE_KEY).getAsString());
                    }
                }
            }
            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                callback.onLeaveMessageFailure("服务器异常");
                Log.d(TAG, "onFailure: "+t);
            }
        });
    }


    @Override
    public void fetchIdleSaleMessageList(int id, int pi, @NonNull final LoadIdleSaleMessageListCallback callback) {
        Call<JsonObject> call = mIdleSaleClient.getIdleSaleMessage(id, pi);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject bodyJson = response.body();
                if (bodyJson != null) {
                    int res = bodyJson.get(Const.RES_KEY).getAsInt();
                    if (res == 1) {
                        JsonArray resultJsons = bodyJson.get(Const.DATA_KEY).getAsJsonArray();
                        Gson gson = new GsonBuilder().setLenient().create();
                        List<LeaveMessageInfo> leaveMessageInfoList = new ArrayList<>();
                        for (int i = 0; i < resultJsons.size(); i++) {
                            JsonObject json = resultJsons.get(i).getAsJsonObject();
                            LeaveMessageInfo leaveMessageInfo = gson.fromJson(json, LeaveMessageInfo.class);
                            leaveMessageInfoList.add(leaveMessageInfo);
                        }
                        callback.onIdleSaleMessageListLoaded(leaveMessageInfoList);

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
}
