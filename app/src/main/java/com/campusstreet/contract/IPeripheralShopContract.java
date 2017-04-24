package com.campusstreet.contract;

import com.campusstreet.entity.LeaveMessageInfo;

import java.util.List;

/**
 * Created by Orange on 2017/4/24.
 */

public interface IPeripheralShopContract {
    
    interface Presenter extends BasePresenter {

        void fetchPeriPheralShopList(int type , int pi);

        void searchShop(String keyWord);

        void fetchShopCommodityList(int id,int pi);




    }

    interface View extends BaseView<Presenter> {

        void setPeriPheralShop();

        void setShopCommodityList();

        void showErrorMsg(String errorMsg);

        /**
         * 设置是否加载指示器
         *
         * @param active true表示显示，false不显示
         */
        void setLoadingIndicator(boolean active);

    }
}
