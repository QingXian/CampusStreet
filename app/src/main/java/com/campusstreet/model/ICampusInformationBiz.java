package com.campusstreet.model;

import android.support.annotation.NonNull;

/**
 * Created by Orange on 2017/4/24.
 */

public interface ICampusInformationBiz {

    void fetchCampusInformationList(@NonNull LoadCampusInformationListCallback callback);

    interface LoadCampusInformationListCallback {

        // 加载所有GoodDeeds
        void onCampusInformationListLoaded();

        // 获取数据失败的回调
        void onDataNotAvailable(String errorMsg);
    }
}
