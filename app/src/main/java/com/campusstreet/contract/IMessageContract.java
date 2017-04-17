package com.campusstreet.contract;

/**
 * Created by Orange on 2017/4/15.
 */

public interface IMessageContract {

    interface Presenter extends BasePresenter {

        void fetchMessageList();

    }

    interface View extends BaseView<Presenter> {


        void setMessageList();

        void showErrorMsg(String errorMsg);

        /**
         * 设置是否加载指示器
         *
         * @param active true表示显示，false不显示
         */
        void setLoadingIndicator(boolean active);


    }
}
