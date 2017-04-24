package com.campusstreet.model;

import android.support.annotation.NonNull;

import com.campusstreet.entity.LeaveMessageInfo;

import java.util.List;

/**
 * Created by Orange on 2017/4/24.
 */

public interface IPartnerBiz {

    void fetchPartnerList(int type,int pi,@NonNull LoadPartnerListCallback callback);

    void searchPartner(String picType, @NonNull searchPartnerCallback callback);

    void fetchPartnerCategories(String picType, @NonNull LoadPartnerCategoriesCallback callback);


    interface searchPartnerCallback {

        void onSearchSuccess();

        void onSearchFailure(String errorMsg);
    }



    interface LoadPartnerListCallback {

        // 加载所有Partner
        void onPartnerListLoaded();

        // 获取数据失败的回调
        void onDataNotAvailable(String errorMsg);
    }
    
    interface LoadPartnerCategoriesCallback {

        void onPartnerCategoriesLoaded();

        void onDataNotAvailable(String errorMsg);
    }
}
