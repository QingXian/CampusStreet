package com.campusstreet.contract;

import com.campusstreet.entity.BannerInfo;
import com.campusstreet.entity.HomeDynamicInfo;

import java.util.List;

/**
 * Created by Orange on 2017/4/15.
 */

public interface IHomeContract {

    interface Presenter extends BasePresenter {

        void fetchBanner();

        void fetchAdvertisement();

        void fetchdynamicList(String uid);

    }

    interface View extends BaseView<Presenter> {

        void setUserInfo();

        void setBanner(List<BannerInfo> bannerInfos);

        void setAdvertisement();

        void showFetchBannerFail(String errorMsg);

        void setdynamicList  (List<HomeDynamicInfo> homeDynamicInfos);

        void showErrorMsg(String errorMsg);

        /**
         * 设置是否加载指示器
         *
         * @param active true表示显示，false不显示
         */
        void setLoadingIndicator(boolean active);


    }
}
