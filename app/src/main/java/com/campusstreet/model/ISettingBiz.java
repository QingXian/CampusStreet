package com.campusstreet.model;

import android.support.annotation.NonNull;

import java.util.Map;

/**
 * Created by Orange on 2017/4/17.
 */

public interface ISettingBiz {

    void userInfoRevise(Map<String, Object> params, @NonNull UserInfoReviseCallback callback);


    interface UserInfoReviseCallback {
        void onUserInfoReviseSuccess();

        void onUserInfoReviseFailure(String errorMsg);
    }
}
