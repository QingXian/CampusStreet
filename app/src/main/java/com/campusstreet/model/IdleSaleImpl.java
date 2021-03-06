package com.campusstreet.model;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.campusstreet.api.IdleSaleClient;
import com.campusstreet.api.ServiceGenerator;
import com.campusstreet.common.Const;
import com.campusstreet.entity.CategoriesInfo;
import com.campusstreet.entity.IdleSaleInfo;
import com.campusstreet.entity.LeaveMessageInfo;
import com.campusstreet.entity.StudyWorkInfo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

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

import static android.R.attr.type;
import static com.campusstreet.common.Const.MULTIPART_FORM_DATA;

/**
 * Created by Orange on 2017/4/19.
 */
public class IdleSaleImpl implements IIdleSaleBiz {


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
                        if (bodyJson.get(Const.TOTAL_KEY).getAsInt() != 0) {
                            JsonArray resultJsons = bodyJson.get(Const.DATA_KEY).getAsJsonArray();
                            Gson gson = new GsonBuilder().setLenient().create();
                            List<IdleSaleInfo> IdleSaleInfoList = new ArrayList<>();
                            for (int i = 0; i < resultJsons.size(); i++) {
                                JsonObject json = resultJsons.get(i).getAsJsonObject();
                                IdleSaleInfo idleSaleInfo = gson.fromJson(json, IdleSaleInfo.class);
                                IdleSaleInfoList.add(idleSaleInfo);
                            }
                            callback.onIdleSaleListLoaded(IdleSaleInfoList);
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
    public void fetchUserIdleSaleList(String uid, String key, int pi, @NonNull final LoadUserIdleSaleListCallback callback) {
        Call<JsonObject> call = mIdleSaleClient.getUserIdleSale(uid, key, pi);
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
                            List<IdleSaleInfo> IdleSaleInfoList = new ArrayList<>();
                            for (int i = 0; i < resultJsons.size(); i++) {
                                JsonObject json = resultJsons.get(i).getAsJsonObject();
                                IdleSaleInfo idleSaleInfo = gson.fromJson(json, IdleSaleInfo.class);
                                IdleSaleInfoList.add(idleSaleInfo);
                            }
                            callback.onUserIdleSaleListLoaded(IdleSaleInfoList);
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
    public void fetchIdleSaleCategories(@NonNull final LoadIdleSaleCategoriesCallback callback) {
        Call<JsonObject> call = mIdleSaleClient.getIdleSaleType();
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
                            callback.onIdleSaleCategoriesLoaded(categoriesInfos);
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
    public void addIdleGoods(IdleSaleInfo idleSaleInfo, @NonNull final addIdleGoodsCallback callback) {
        Map<String, RequestBody> requestBodyMap = new HashMap<>();
        MultipartBody.Part[] files = new MultipartBody.Part[0];
        if (idleSaleInfo.getFiles() != null && idleSaleInfo.getFiles().size() > 0) {
            files = new MultipartBody.Part[idleSaleInfo.getFiles().size()];
            RequestBody requestFile;
            Iterator<File> iterator = idleSaleInfo.getFiles().iterator();
            int i = 0;
            while (iterator.hasNext()) {
                File file = iterator.next();
                // 创建 RequestBody，用于封装 请求RequestBody
                requestFile = RequestBody.create(MediaType.parse("application/otcet-stream"), file);
                //  MultipartBody.Part is used to send also the actual file name
                files[i] = MultipartBody.Part.createFormData("images", file.getName(), requestFile);

                i++;
            }
        }


        //添加请求参数
        requestBodyMap.put("uid", RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA), String.valueOf(idleSaleInfo.getUid())));
        requestBodyMap.put("name", RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA),
                idleSaleInfo.getName()));
        requestBodyMap.put("gmoney", RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA), idleSaleInfo.getMoney()));
        requestBodyMap.put("goodstype", RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA), idleSaleInfo.getGoodstype()));
        requestBodyMap.put("tradeplace", RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA), idleSaleInfo.getTradeplace()));
        requestBodyMap.put("tradetype", RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA), idleSaleInfo.getTradetype()));
        requestBodyMap.put("selltype", RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA), String.valueOf(idleSaleInfo.getSelltype())));
        requestBodyMap.put("mobile", RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA), idleSaleInfo.getMobile()));
        requestBodyMap.put("qq", RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA), idleSaleInfo.getQq()));
        requestBodyMap.put("bewrite", RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA), idleSaleInfo.getBewrite()));
        requestBodyMap.put("gcontent", RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA), idleSaleInfo.getContent()));
        Call<JsonObject> call = mIdleSaleClient.pushIdlegoods(requestBodyMap, files);
//        Call<JsonObject> call = mIdleSaleClient.pushIdlegoods(requestBodyMap);
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
    public void leaveMessagae(int gid, String uid, String con, @NonNull final LeaveMessageCallback callback) {
        Call<JsonObject> call = mIdleSaleClient.LeaveMessage(gid, uid, con);
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
    public void fetchIdleSaleMessageList(int id, int pi, @NonNull final LoadIdleSaleMessageListCallback callback) {
        Call<JsonObject> call = mIdleSaleClient.getIdleSaleMessage(id, pi);
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
                            callback.onIdleSaleMessageListLoaded(leaveMessageInfoList);
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

    @Override
    public void fetchIdleSaleDetail(int gid, @NonNull final LoadIdleSaleDetailCallback callback) {
        Call<JsonObject> call = mIdleSaleClient.getIdleSaleDetail(gid);
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
                            IdleSaleInfo idleSaleInfo = gson.fromJson(resultJsons.get(0).getAsJsonObject(), IdleSaleInfo.class);
                            callback.onIdleSaleDetailLoaded(idleSaleInfo);
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
