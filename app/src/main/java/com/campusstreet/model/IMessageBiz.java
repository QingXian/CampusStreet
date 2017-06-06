package com.campusstreet.model;

import android.support.annotation.NonNull;

import com.campusstreet.entity.MessageInfo;

import java.util.List;

/**
 * Created by Orange on 2017/4/16.
 */

public interface IMessageBiz {

    void getMessageList(String uid, int pi, @NonNull LoadMessageListCallback callback);

    void onReadMessage(String uid,int smsids,@NonNull onReadMessageCallback callback);

    interface LoadMessageListCallback {

        // 加载所有GoodDeeds
        void onMessageListLoaded(List<MessageInfo> messageInfos);

        // 获取数据失败的回调
        void onDataNotAvailable(String errorMsg);
    }
    interface onReadMessageCallback {

        void onReadMessageSuccess();

        void onReadMessageFailure(String errorMsg);
    }
}
