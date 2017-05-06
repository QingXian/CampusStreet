package com.campusstreet.contract;


import com.campusstreet.entity.UserInfo;

import java.util.Map;

/**
 * Created by Orange on 2017/5/3.
 */

public interface IRegisterContract {
    interface Presenter extends BasePresenter {

        void onResgister(Map<String, Object> params);


        void fetchCaptcha(String phone);


    }

    interface View extends BaseView<Presenter> {

        void showSuccessfullyresgister(UserInfo userInfo);

        void fetchCaptchaSuccessfull();

        void showErrorMsg(String errorMsg);

        /**
         * 设置是否加载指示器
         *
         * @param active true表示显示，false不显示
         */
        void setLoadingIndicator(boolean active);

    }
}
