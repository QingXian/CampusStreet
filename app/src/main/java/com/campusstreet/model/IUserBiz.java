package com.campusstreet.model;

import android.support.annotation.NonNull;

import com.campusstreet.entity.BuyZoneInfo;
import com.campusstreet.entity.LeaveMessageInfo;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by Orange on 2017/5/3.
 */

public interface IUserBiz {

    void onLogin(String phone, String password, @NonNull onLoginCallback callback);


    void onResgister(Map<String, Objects> params,@NonNull onResgisterCallback callback);


    void fetchCaptcha(String phone,@NonNull GetCaptchaCallback callback);


    void changePassword(String userId, String oldPassword, String newPassword,@NonNull ChangePasswordCallback callback);


    void forgetPassword(String phone, String inputCaptcha, String password,@NonNull ForgetPasswordCallback callback);


    interface onLoginCallback {

        void onLoginSuccess();

        void onLoginFailure(String errorMsg);
    }


    interface onResgisterCallback {

        void onResgisterSuccess();

        void onResgisterFailure(String errorMsg);
    }

    interface GetCaptchaCallback {

        void onFetchSuccess(List<BuyZoneInfo> buyZoneInfoList);

        void onFetchFailure(String errorMsg);
    }

    interface ForgetPasswordCallback {
        void onForgetPasswordSuccess();

        void onForgetPasswordFailure(String errorMsg);
    }

    interface ChangePasswordCallback {
        void onChangePasswordSuccess();

        void onChangePasswordFailure(String errorMsg);
    }
}
