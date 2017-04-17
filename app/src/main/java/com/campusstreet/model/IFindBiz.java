package com.campusstreet.model;

import android.support.annotation.NonNull;

/**
 * Created by Orange on 2017/4/16.
 */

public interface IFindBiz {

    void fetchFindList(@NonNull LoadFindListCallback callback);

    void fetchTopImage(String picType, @NonNull GetTopImageCallback callback);

    void addLive(@NonNull addLiveCallback callback);

    interface addLiveCallback {

        void onAddSuccess();

        void onAddFailure(String errorMsg);
    }

    interface GetTopImageCallback {
        void onFetchSuccess();

        void onFetchFailure(String errorMsg);
    }

    interface LoadFindListCallback {

        // 加载所有GoodDeeds
        void ondyFindListLoaded();

        // 获取数据失败的回调
        void onDataNotAvailable(String errorMsg);
    }
}
