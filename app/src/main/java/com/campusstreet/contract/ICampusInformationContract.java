package com.campusstreet.contract;

import com.campusstreet.entity.LeaveMessageInfo;

import java.util.List;

/**
 * Created by Orange on 2017/4/24.
 */

public interface ICampusInformationContract {

    interface Presenter extends BasePresenter {

        void fetchCampusInformationList(int pi);


    }
    interface View extends BaseView<Presenter> {

        void setCampusInformationList(List<LeaveMessageInfo> BuyZoneMessageList);

        void showErrorMsg(String errorMsg);

        /**
         * 设置是否加载指示器
         *
         * @param active true表示显示，false不显示
         */
        void setLoadingIndicator(boolean active);
    }
}
