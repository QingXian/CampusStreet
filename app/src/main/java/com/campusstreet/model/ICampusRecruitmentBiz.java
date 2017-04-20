package com.campusstreet.model;

import android.support.annotation.NonNull;

/**
 * Created by Orange on 2017/4/18.
 */

public interface ICampusRecruitmentBiz {

    void fetchCampusRecruitmentList(@NonNull LoadCampusRecruitmentListCallback callback);

    void searchCampusRecruitment(String picType, @NonNull searchCampusRecruitmentCallback callback);



    interface searchCampusRecruitmentCallback {
        void onSearchSuccess();

        void onSearchFailure(String errorMsg);
    }

    interface LoadCampusRecruitmentListCallback {

        // 加载所有GoodDeeds
        void onCampusRecruitmentListLoaded();

        // 获取数据失败的回调
        void onDataNotAvailable(String errorMsg);
    }
}
