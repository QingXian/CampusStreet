package com.campusstreet.contract;

import com.campusstreet.entity.BannerInfo;

import java.util.List;

/**
 * Created by Orange on 2017/4/15.
 */

public interface IHomeContract {

    interface Presenter extends BasePresenter {

        void fetchBanner();

        void fetchAdvertisement();

        void fetchdynamicList();

    }

    interface View extends BaseView<Presenter> {

        void setUserInfo();

        void setBanner(List<BannerInfo> bannerInfos);

        void setAdvertisement();

        void setdynamicList  ();

        void showErrorMsg(String errorMsg);

        /**
         * 设置是否加载指示器
         *
         * @param active true表示显示，false不显示
         */
        void setLoadingIndicator(boolean active);

        void clearCookie();


    }
}
