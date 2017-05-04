package com.campusstreet.contract;

import android.content.Intent;

import com.campusstreet.entity.BountyHallInfo;
import com.campusstreet.entity.JoinInfo;
import com.campusstreet.entity.UserInfo;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by Orange on 2017/5/3.
 */

public interface IRegisterrContract {
    interface Presenter extends BasePresenter {

        void onResgister(Map<String, Objects> params);


        void fetchCaptcha(String phone);


    }

    interface View extends BaseView<Presenter> {

        void showSuccessfullyresgister(UserInfo userInfo);

        void fetchCaptchaSuccessfull(List<BountyHallInfo> bountyHallInfos);

        void showErrorMsg(String errorMsg);

        /**
         * 设置是否加载指示器
         *
         * @param active true表示显示，false不显示
         */
        void setLoadingIndicator(boolean active);

    }
}
