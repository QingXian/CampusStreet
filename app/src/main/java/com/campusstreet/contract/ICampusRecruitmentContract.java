package com.campusstreet.contract;

/**
 * Created by Orange on 2017/4/15.
 */

public interface ICampusRecruitmentContract {

    interface Presenter extends BasePresenter {

        void fetchCampusRecruitmentList(String type);

        void searchRecruit(String keyWord);


    }

    interface View extends BaseView<Presenter> {


        void setCampusRecruitment();

        void showErrorMsg(String errorMsg);

        /**
         * 设置是否加载指示器
         *
         * @param active true表示显示，false不显示
         */
        void setLoadingIndicator(boolean active);

    }
}
