package com.campusstreet.model;

import android.support.annotation.NonNull;

import com.campusstreet.entity.BannerInfo;
import com.campusstreet.entity.HomeDynamicInfo;

import java.util.List;

/**
 * Created by Orange on 2017/4/16.
 */

public interface IHomeBiz {

    void fetchBannerImage(@NonNull GetBannerCallback callback);

    void fetchAdImage(String picType, @NonNull GetAdImageCallback callback);

    void getdynamicList(String uid,@NonNull LoaddynamicListCallback callback);

    interface GetBannerCallback {
        void onFetchSuccess(List<BannerInfo> bannerInfos);

        void onFetchFailure(String errorMsg);
    }

    interface GetAdImageCallback {
        void onFetchSuccess();

        void onFetchFailure(String errorMsg);
    }

    interface LoaddynamicListCallback {

        // 加载所有GoodDeeds
        void ondynamicListLoaded(List<HomeDynamicInfo> homeDynamicInfo);

        // 获取数据失败的回调
        void onDataNotAvailable(String errorMsg);
    }

}
