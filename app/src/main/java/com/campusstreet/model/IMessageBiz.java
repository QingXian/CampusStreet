package com.campusstreet.model;

import android.support.annotation.NonNull;

/**
 * Created by Orange on 2017/4/16.
 */

public interface IMessageBiz {

    void getMessageList(@NonNull LoadMessageListCallback callback);

    interface LoadMessageListCallback {

        // 加载所有GoodDeeds
        void onMessageListLoaded();

        // 获取数据失败的回调
        void onDataNotAvailable(String errorMsg);
    }
}
