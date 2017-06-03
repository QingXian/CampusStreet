package com.campusstreet.model;

import android.support.annotation.NonNull;

import com.campusstreet.entity.CategoriesInfo;
import com.campusstreet.entity.LeaveMessageInfo;
import com.campusstreet.entity.PartnerInfo;

import java.util.List;

import static android.R.attr.type;

/**
 * Created by Orange on 2017/4/24.
 */

public interface IPartnerBiz {

    void fetchPartnerList(String key, int type, int pi, @NonNull LoadPartnerListCallback callback);

    void fetchPartnerCategories(@NonNull LoadPartnerCategoriesCallback callback);

    void fetchPartnerDetail(int pid, @NonNull LoadPartnerDetailCallback callback);


    interface LoadPartnerDetailCallback {

        void onPartnerDetailLoaded(PartnerInfo partnerInfo);

        // 获取数据失败的回调
        void onDataNotAvailable(String errorMsg);
    }

    interface LoadPartnerListCallback {

        // 加载所有Partner
        void onPartnerListLoaded(List<PartnerInfo> partnerInfos);

        // 获取数据失败的回调
        void onDataNotAvailable(String errorMsg);
    }

    interface LoadPartnerCategoriesCallback {

        void onPartnerCategoriesLoaded(List<CategoriesInfo> categoriesInfos);

        void onDataNotAvailable(String errorMsg);
    }
}
