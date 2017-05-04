package com.campusstreet.model;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.campusstreet.api.ModifyInfoClient;
import com.campusstreet.api.ServiceGenerator;
import com.campusstreet.api.UserClient;
import com.campusstreet.common.Const;
import com.google.gson.JsonObject;

import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }

    @Override
    public void onResgister(Map<String, Objects> params, @NonNull onResgisterCallback callback) {

    }

    @Override
    public void fetchCaptcha(String phone, @NonNull GetCaptchaCallback callback) {

    }

    @Override
    public void changePassword(String userId, String oldPassword, String newPassword, @NonNull ChangePasswordCallback callback) {

    }

    @Override
    public void forgetPassword(String phone, String inputCaptcha, String password, @NonNull ForgetPasswordCallback callback) {

    }
}
