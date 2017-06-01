package com.campusstreet.contract;

import com.campusstreet.entity.LiveInfo;

import java.util.List;

/**
 * Created by Orange on 2017/4/15.
 */

public interface IFindContract {

    interface Presenter extends BasePresenter {

        void fetchFindList(int pi);

        void fetchTopImge();

        void pushLive(LiveInfo liveInfo);

    }

    interface View extends BaseView<Presenter> {


        void setFindList(List<LiveInfo> liveInfos);

        void setTopImge();

        void showErrorMsg(String errorMsg);

        void showSuccessfullyPush(String succcessMsg);

        /**
         * 设置是否加载指示器
         *
         * @param active true表示显示，false不显示
         */
        void setLoadingIndicator(boolean active);


    }
}
