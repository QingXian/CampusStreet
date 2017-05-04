package com.campusstreet.model;

import android.support.annotation.NonNull;

import com.campusstreet.entity.NewInfo;

import java.util.List;

/**
 * Created by Orange on 2017/4/24.
 */

public interface ICampusInformationBiz {

    void fetchCampusInformationList(String key, int pi, @NonNull LoadCampusInformationListCallback callback);

    void fetchCampusInformationDetail(int nid, @NonNull LoadCampusInformationDetailCallback callback);

    interface LoadCampusInformationListCallback {

        // 加载所有GoodDeeds
        void onCampusInformationListLoaded(List<NewInfo> newInfos);

        // 获取数据失败的回调
        void onDataNotAvailable(String errorMsg);
    }

    interface LoadCampusInformationDetailCallback {

        // 加载所有GoodDeeds
        void onCampusInformationDetailLoaded(NewInfo newInfo);

        // 获取数据失败的回调
        void onDataNotAvailable(String errorMsg);
    }
}
