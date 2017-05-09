package com.campusstreet.model;

import android.support.annotation.NonNull;

import com.campusstreet.entity.BuyZoneInfo;
import com.campusstreet.entity.LeaveMessageInfo;
import com.campusstreet.entity.UserInfo;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by Orange on 2017/5/3.
 */

public interface IUserBiz {

    void onLogin(String phone, String password, @NonNull onLoginCallback callback);


    void onResgister(Map<String, Object> params, @NonNull onResgisterCallback callback);


    void fetchCaptcha(String mt,String mc,String phone,@NonNull GetCaptchaCallback callback);

    void getResgisterMc(String phone ,@NonNull GetResgisterMcCallback callback);

    void getforgetPasswordrMc(String phone ,@NonNull GetRForgetPasswordMcCallback callback);


    void forgetPassword (Map<String, Object> params, @NonNull ForgetPasswordCallback callback);


    interface onLoginCallback {

        void onLoginSuccess(UserInfo userInfo);

        void onLoginFailure(String errorMsg);
    }


    interface onResgisterCallback {

        void onResgisterSuccess(UserInfo userInfo);

        void onResgisterFailure(String errorMsg);
    }

    interface GetCaptchaCallback {

        void onFetchSuccess();

        void onFetchFailure(String errorMsg);
    }
    interface GetResgisterMcCallback {

        void GetResgisterMcSuccess(String mc);

        void GetResgisterMcFailure(String errorMsg);
    }
    interface GetRForgetPasswordMcCallback {

        void GetForgetPasswordMcSuccess(String mc);

        void GetForgetPasswordMcFailure(String errorMsg);
    }

    interface ForgetPasswordCallback {

        void onForgetPasswordSuccess();

        void onForgetPasswordFailure(String errorMsg);
    }
}
