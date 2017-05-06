package com.campusstreet.contract;

import com.campusstreet.entity.BountyHallInfo;
import com.campusstreet.entity.UserInfo;

import java.util.List;

/**
 * Created by Orange on 2017/5/3.
 */

public interface ILoginContract {


    interface Presenter extends BasePresenter {

        void onLogin(String phone, String password);

        void forgetPassword(String phone, String inputCaptcha, String password,String passwordAgain);

        void fetchCaptcha(String phone);

    }

    interface View extends BaseView<Presenter> {

        void showSuccessfullyLogin(UserInfo userInfo);

        void fetchCaptchaSuccessfull();

        void showSuccessfullyForgetPasswrod();


        void showErrorMsg(String errorMsg);

        /**
         * 设置是否加载指示器
         *
         * @param active true表示显示，false不显示
         */
        void setLoadingIndicator(boolean active);

    }
}
