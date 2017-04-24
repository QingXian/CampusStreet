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


    interface UserInfoReviseCallback {
        void onUserInfoReviseSuccess();

        void onUserInfoReviseFailure(String errorMsg);
    }

    interface AvatarReviseCallback {
        void onAvatarReviseSuccess();

        void onAvatarReviseFailure(String errorMsg);
    }
}
