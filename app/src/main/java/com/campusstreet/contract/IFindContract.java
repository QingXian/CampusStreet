package com.campusstreet.contract;

/**
 * Created by Orange on 2017/4/15.
 */

public interface IFindContract {

    interface Presenter extends BasePresenter {

        void fetchFindList();

        void fetchTopImge();

        void pushLive();

    }

    interface View extends BaseView<Presenter> {


        void setFindList();

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
