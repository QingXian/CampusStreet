package com.campusstreet.model;

import android.support.annotation.NonNull;

/**
 * Created by Orange on 2017/4/16.
 */

public interface IHomeBiz {

    void fetchBannerImage(String picType, @NonNull GetBannerCallback callback);

    void fetchAdImage(String picType, @NonNull GetAdImageCallback callback);

    void getdynamicList(@NonNull LoaddynamicListCallback callback);

    interface GetBannerCallback {
        void onFetchSuccess();

        void onFetchFailure(String errorMsg);
    }

    interface GetAdImageCallback {
        void onFetchSuccess();

        void onFetchFailure(String errorMsg);
    }

    interface LoaddynamicListCallback {

        // 加载所有GoodDeeds
        void ondynamicListLoaded();

        // 获取数据失败的回调
        void onDataNotAvailable(String errorMsg);
    }

}