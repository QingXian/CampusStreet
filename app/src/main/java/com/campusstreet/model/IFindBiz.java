package com.campusstreet.model;

import android.support.annotation.NonNull;

import com.campusstreet.entity.LiveInfo;
import com.campusstreet.entity.LiveReplyInfo;

import java.util.List;

/**
 * Created by Orange on 2017/4/16.
 */

public interface IFindBiz {

    void fetchFindList(int pi, @NonNull LoadFindListCallback callback);

    void fetchTopImage(String picType, @NonNull GetTopImageCallback callback);

    void addLive(LiveInfo liveInfo, @NonNull AddLiveCallback callback);

    void fetchLiveReplyList(int did, int pi, @NonNull LoadLiveReplyListCallback callback);

    void onReplyLive(String uid, int did, String con, @NonNull onReplyLiveCallback callback);

    void onDeleteLive(String uid, int did, @NonNull onDeleteLiveCallback callback);


    interface onReplyLiveCallback {

        void onReplyLiveSuccess();

        void onReplyLiveFailure(String errorMsg);
    }

    interface onDeleteLiveCallback {

        void onDeleteLiveSuccess();

        void onDeleteLiveFailure(String errorMsg);
    }


    interface LoadLiveReplyListCallback {

        void onFindListLoaded(List<LiveReplyInfo> liveReplyInfos);

        // 获取数据失败的回调
        void onDataNotAvailable(String errorMsg);
    }

    interface AddLiveCallback {

        void onAddSuccess();

        void onAddFailure(String errorMsg);
    }

    interface GetTopImageCallback {

        void onFetchSuccess();

        void onFetchFailure(String errorMsg);
    }

    interface LoadFindListCallback {

        void onFindListLoaded(List<LiveInfo> liveInfo);

        // 获取数据失败的回调
        void onDataNotAvailable(String errorMsg);
    }
}
