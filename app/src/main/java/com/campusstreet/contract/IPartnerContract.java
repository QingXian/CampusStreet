package com.campusstreet.contract;

import com.campusstreet.entity.LeaveMessageInfo;

import java.util.List;

/**
 * Created by Orange on 2017/4/24.
 */

public interface IPartnerContract {
    
    interface Presenter extends BasePresenter {

        void fetchPartnerList(int type, int pi);

        void search(String keyWord);

        void fetchPartnerCategories();

    }
    interface View extends BaseView<Presenter> {

        void setPartner();

        void setPartnerCategories();

        void showErrorMsg(String errorMsg);

        /**
         * 设置是否加载指示器
         *
         * @param active true表示显示，false不显示
         */
        void setLoadingIndicator(boolean active);
    }
}
