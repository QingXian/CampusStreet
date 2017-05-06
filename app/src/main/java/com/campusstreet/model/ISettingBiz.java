package com.campusstreet.model;

import android.support.annotation.NonNull;

import java.util.Map;

import okhttp3.MultipartBody;

/**
 * Created by Orange on 2017/4/17.
 */

public interface ISettingBiz {

    void userInfoRevise(Map<String, Object> params, @NonNull UserInfoReviseCallback callback);

    void avatarRevise(Map<String, Object> params, @NonNull AvatarReviseCallback callback);

    void changePassword(String userId, String oldPassword, String newPassword, @NonNull ChangePasswordCallback callback);


    interface UserInfoReviseCallback {
        void onUserInfoReviseSuccess();

        void onUserInfoReviseFailure(String errorMsg);
    }

    interface AvatarReviseCallback {
        void onAvatarReviseSuccess();

        void onAvatarReviseFailure(String errorMsg);

    }

    interface ChangePasswordCallback {
        void onChangePasswordSuccess();

        void onChangePasswordFailure(String errorMsg);
    }
}
