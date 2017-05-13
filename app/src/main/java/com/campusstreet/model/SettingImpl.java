package com.campusstreet.model;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.campusstreet.api.IdleSaleClient;
import com.campusstreet.api.ModifyInfoClient;
import com.campusstreet.api.ServiceGenerator;
import com.campusstreet.api.UserClient;
import com.campusstreet.common.Const;
import com.campusstreet.entity.IdleSaleInfo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Orange on 2017/4/17.
 */

public class SettingImpl implements  ISettingBiz {

    private final String TAG = this.getClass().getSimpleName();
    private static SettingImpl sSettingImpl;
    private ModifyInfoClient mModifyInfoClient;
    private UserClient mUserClient;


    private SettingImpl(Context context) {
        mModifyInfoClient = ServiceGenerator.createService(context, ModifyInfoClient.class);
        mUserClient = ServiceGenerator.createService(context,UserClient.class);
    }


    public static SettingImpl getInstance(Context context) {

        if (sSettingImpl == null) {
            sSettingImpl = new SettingImpl(context);
        }
        return sSettingImpl;
    }

    @Override
    public void userInfoRevise(Map<String, Object> params, @NonNull final UserInfoReviseCallback callback) {
        Call<JsonObject> call = mModifyInfoClient.ModifyInfo(params);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject bodyJson = response.body();
                if (bodyJson != null) {
                    int res = bodyJson.get(Const.RES_KEY).getAsInt();
                    if (res == 1) {
                        callback.onUserInfoReviseSuccess();
                    }
                    else {
                        callback.onUserInfoReviseFailure(bodyJson.get(Const.MESSAGE_KEY).getAsString());
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                callback.onUserInfoReviseFailure("网络异常");
                Log.d(TAG, "onFailure: "+t);
            }
        });
    }

    @Override
    public void avatarRevise(Map<String, Object> params, @NonNull final AvatarReviseCallback callback) {
        MultipartBody.Part part;
        if (params.get("a") != null) {
            part = ((MultipartBody.Part) params.get("a"));
            Map<String, RequestBody> requestBodyMap = new HashMap<>();
            requestBodyMap.put("uid", RequestBody.create(MediaType.parse("multipart/form-data"),
                    params.get("uid").toString()));
            Call<JsonObject> call = mModifyInfoClient.ModifyAvatar(requestBodyMap, part);
            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    JsonObject bodyJson = response.body();
                    if (bodyJson != null) {
                        int res = bodyJson.get(Const.RES_KEY).getAsInt();
                        if (res == 1) {
                            callback.onAvatarReviseSuccess();
                        } else {
                            callback.onAvatarReviseFailure(bodyJson.get(Const.MESSAGE_KEY).getAsString());
                        }
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    callback.onAvatarReviseFailure("网络异常");
                    Log.d(TAG, "onFailure: " + t);
                }
            });
        }
    }
    @Override
    public void changePassword(String userId, String oldPassword, String newPassword, @NonNull final ChangePasswordCallback callback) {
        Map<String, Object> params = new HashMap<>();
        params.put("uid", userId);
        params.put("oldpwd", oldPassword);
        params.put("newpwd", newPassword);
        Call<JsonObject> call = mUserClient.changePassword(params);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject bodyJson = response.body();
                if (bodyJson != null) {
                    int res = bodyJson.get(Const.EXT_KEY).getAsInt();
                    if (res == 1) {
                        callback.onChangePasswordSuccess();
                    } else {
                        callback.onChangePasswordFailure(bodyJson.get(Const.MESSAGE_KEY).getAsString());
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                callback.onChangePasswordFailure("网络异常");
                Log.d(TAG, "onFailure: " + t);
            }
        });
    }
}
