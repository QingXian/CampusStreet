package com.campusstreet.contract;

import okhttp3.MultipartBody;

/**
 * Created by Orange on 2017/4/15.
 */

public interface ISettingContract {

    interface Presenter extends BasePresenter {

        void reviseHead(String uid, MultipartBody.Part avatar);

        void reviseNickName(String uid,String nickName);

        void reviseDepartment(String uid,String department);

        void changePassword(String userId, String oldPassword, String newPassword);

    }

    interface View extends BaseView<Presenter> {


        void showSuccessMsg(String successMsg);

        void showErrorMsg(String errorMsg);

        void showSuccessfullyChangePassword();

        /**
         * 设置是否加载指示器
         *
         * @param active true表示显示，false不显示
         */
        void setLoadingIndicator(boolean active);


    }
}
