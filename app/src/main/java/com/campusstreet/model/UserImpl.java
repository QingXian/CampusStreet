package com.campusstreet.model;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.campusstreet.api.ServiceGenerator;
import com.campusstreet.api.UserClient;
import com.campusstreet.common.Const;
import com.campusstreet.entity.UserInfo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.campusstreet.common.Const.MULTIPART_FORM_DATA;

/**
 * Created by Orange on 2017/5/3.
 */

public class UserImpl implements IUserBiz {

    private final String TAG = this.getClass().getSimpleName();
    private static UserImpl sUserImpl;
    private UserClient mUserClient;


    private UserImpl(Context context) {
        mUserClient = ServiceGenerator.createService(context, UserClient.class);
    }


    public static UserImpl getInstance(Context context) {

        if (sUserImpl == null) {
            sUserImpl = new UserImpl(context);
        }
        return sUserImpl;
    }

    @Override
    public void onLogin(String phone, String password, @NonNull final onLoginCallback callback) {
        Call<JsonObject> call = mUserClient.login(phone, password);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject bodyJson = response.body();
                if (bodyJson != null) {
                    int res = bodyJson.get(Const.RES_KEY).getAsInt();
                    if (res == 1) {
                        JsonArray resultJsons = bodyJson.get(Const.EXT_KEY).getAsJsonArray();
                        Gson gson = new GsonBuilder().setLenient().create();
                        JsonObject json = resultJsons.get(0).getAsJsonObject();
                        UserInfo userInfo = gson.fromJson(json, UserInfo.class);
                        callback.onLoginSuccess(userInfo);
                    } else {
                        callback.onLoginFailure(bodyJson.get(Const.MESSAGE_KEY).getAsString());
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                callback.onLoginFailure("网络异常");
                Log.d(TAG, "onFailure: " + t);
            }
        });
    }

    @Override
    public void onResgister(UserInfo userInfo,String password,String code, @NonNull final onResgisterCallback callback) {
        Map<String, RequestBody> requestBodyMap = new HashMap<>();
        requestBodyMap.put("mobile", RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA), userInfo.getMobile()));
        requestBodyMap.put("mcode", RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA), code));
        requestBodyMap.put("pwd", RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA), password));
        requestBodyMap.put("nick", RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA), userInfo.getUsername()));
        requestBodyMap.put("major", RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA), userInfo.getMajor()));
        Call<JsonObject> call = mUserClient.resgister(requestBodyMap);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject bodyJson = response.body();
                if (bodyJson != null) {
                    int res = bodyJson.get(Const.RES_KEY).getAsInt();
                    if (res == 1) {
                        JsonArray resultJsons = bodyJson.get(Const.EXT_KEY).getAsJsonArray();
                        Gson gson = new GsonBuilder().setLenient().create();
                        JsonObject json = resultJsons.get(0).getAsJsonObject();
                        UserInfo userInfo = gson.fromJson(json, UserInfo.class);
                        callback.onResgisterSuccess(userInfo);
                    } else {
                        callback.onResgisterFailure(bodyJson.get(Const.MESSAGE_KEY).getAsString());
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                callback.onResgisterFailure("网络异常");
                Log.d(TAG, "onFailure: " + t);
            }
        });
    }

    @Override
    public void fetchCaptcha(String mt,String mc, String phone, @NonNull final GetCaptchaCallback callback) {
        Call<JsonObject> call = mUserClient.fetchCaptcha(mt,mc, phone);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject bodyJson = response.body();
                if (bodyJson != null) {
                    int res = bodyJson.get(Const.RES_KEY).getAsInt();
                    if (res == 1) {
                        callback.onFetchSuccess();
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
    public void getResgisterMc(String phone, @NonNull final GetResgisterMcCallback callback) {
        Call<JsonObject> call = mUserClient.getRegMc(phone);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject bodyJson = response.body();
                if (bodyJson != null) {
                    int res = bodyJson.get(Const.RES_KEY).getAsInt();
                    if (res == 1) {
                        String mc = bodyJson.get(Const.MESSAGE_KEY).getAsString();
                        callback.GetResgisterMcSuccess(mc);
                    } else {
                        callback.GetResgisterMcFailure(bodyJson.get(Const.MESSAGE_KEY).getAsString());
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                callback.GetResgisterMcFailure("网络异常");
                Log.d(TAG, "onFailure: " + t);
            }
        });
    }

    @Override
    public void getforgetPasswordrMc(String phone, @NonNull final GetRForgetPasswordMcCallback callback) {
        Call<JsonObject> call = mUserClient.getRegMc(phone);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject bodyJson = response.body();
                if (bodyJson != null) {
                    int res = bodyJson.get(Const.RES_KEY).getAsInt();
                    if (res == 1) {
                        String mc = bodyJson.get(Const.MESSAGE_KEY).getAsString();
                        callback.GetForgetPasswordMcSuccess(mc);
                    } else {
                        callback.GetForgetPasswordMcFailure(bodyJson.get(Const.MESSAGE_KEY).getAsString());
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                callback.GetForgetPasswordMcFailure("网络异常");
                Log.d(TAG, "onFailure: " + t);
            }
        });
    }

    @Override
    public void forgetPassword(Map<String, Object> params, @NonNull final ForgetPasswordCallback callback) {
        Call<JsonObject> call = mUserClient.forgetPassword(params);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject bodyJson = response.body();
                if (bodyJson != null) {
                    int res = bodyJson.get(Const.EXT_KEY).getAsInt();
                    if (res == 1) {
                        callback.onForgetPasswordSuccess();
                    } else {
                        callback.onForgetPasswordFailure(bodyJson.get(Const.MESSAGE_KEY).getAsString());
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                callback.onForgetPasswordFailure("网络异常");
                Log.d(TAG, "onFailure: " + t);
            }
        });
    }


}
