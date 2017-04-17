package com.campusstreet.model;

import android.content.Context;
import android.support.annotation.NonNull;

import java.util.Map;

/**
 * Created by Orange on 2017/4/17.
 */

public class SettingImpl implements  ISettingBiz {

    private final String TAG = this.getClass().getSimpleName();
    private static SettingImpl sSettingImpl;



    private SettingImpl(Context context) {
    }

    public static SettingImpl getInstance(Context context) {

        if (sSettingImpl == null) {
            sSettingImpl = new SettingImpl(context);
        }
        return sSettingImpl;
    }

    @Override
    public void userInfoRevise(Map<String, Object> params, @NonNull UserInfoReviseCallback callback) {

    }
}
