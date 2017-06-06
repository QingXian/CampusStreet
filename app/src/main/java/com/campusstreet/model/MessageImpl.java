package com.campusstreet.model;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.campusstreet.api.BountyHallClient;
import com.campusstreet.api.MessageClient;
import com.campusstreet.api.ServiceGenerator;
import com.campusstreet.common.Const;
import com.campusstreet.entity.AssociationPostInfo;
import com.campusstreet.entity.MessageInfo;
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

public class MessageImpl implements IMessageBiz {

    private final String TAG = this.getClass().getSimpleName();
    private static MessageImpl sMessageImpl;
    private MessageClient mMessageClient;


    private MessageImpl(Context context) {
        mMessageClient = ServiceGenerator.createService(context, MessageClient.class);
    }


    public static MessageImpl getInstance(Context context) {

        if (sMessageImpl == null) {
            sMessageImpl = new MessageImpl(context);
        }
        return sMessageImpl;
    }


    @Override
    public void getMessageList(String uid, int pi, @NonNull final LoadMessageListCallback callback) {
        Call<JsonObject> call = mMessageClient.getMessage(uid, pi);
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
                            List<MessageInfo> messageInfos = new ArrayList<>();
                            for (int i = 0; i < resultJsons.size(); i++) {
                                JsonObject json = resultJsons.get(i).getAsJsonObject();
                                MessageInfo messageInfo = gson.fromJson(json, MessageInfo.class);
                                messageInfos.add(messageInfo);
                            }
                            callback.onMessageListLoaded(messageInfos);
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
    public void onReadMessage(String uid, int smsids, @NonNull final onReadMessageCallback callback) {
        Call<JsonObject> call = mMessageClient.readMessage(uid,smsids);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject bodyJson = response.body();
                if (bodyJson != null) {
                    int res = bodyJson.get(Const.RES_KEY).getAsInt();
                    if (res == 1) {

                        callback.onReadMessageSuccess();
                    } else {
                        callback.onReadMessageFailure(bodyJson.get(Const.MESSAGE_KEY).getAsString());
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                callback.onReadMessageFailure("网络异常");
                Log.d(TAG, "onFailure: " + t);
            }
        });
    }
}
