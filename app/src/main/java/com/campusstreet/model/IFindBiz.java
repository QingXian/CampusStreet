package com.campusstreet.model;

import android.support.annotation.NonNull;

import com.campusstreet.entity.LiveInfo;

import java.util.List;

/**
 * Created by Orange on 2017/4/16.
 */

public interface IFindBiz {

    void fetchFindList(int pi,@NonNull LoadFindListCallback callback);

    void fetchTopImage(String picType, @NonNull GetTopImageCallback callback);

    void addLive(LiveInfo liveInfo,@NonNull AddLiveCallback callback);

    interface AddLiveCallback {

        void onAddSuccess();

        void onAddFailure(String errorMsg);
    }

    interface GetTopImageCallback {
        void onFetchSuccess();

        void onFetchFailure(String errorMsg);
    }

    interface LoadFindListCallback {

        // 加载所有GoodDeeds
        void onFindListLoaded(List<LiveInfo> liveInfo);

        // 获取数据失败的回调
        void onDataNotAvailable(String errorMsg);
    }
}
