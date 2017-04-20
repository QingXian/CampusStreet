package com.campusstreet.contract;

import com.campusstreet.entity.IdleSaleInfo;

import java.util.List;

/**
 * Created by Orange on 2017/4/15.
 */

public interface IIdleSaleContract {

    interface Presenter extends BasePresenter {

        void fetchIdleSaleCategories();

        void fetchIdleSaleList(int type , int pi);

        void searchGoods(String keyWord);

        void pushGoods();

        void leaveMessage();




    }

    interface View extends BaseView<Presenter> {

        void setIdleSale(List<IdleSaleInfo> idleSaleInfoList);

        void setIdleSaleCategories();

        void showErrorMsg(String errorMsg);

        void showSuccessfullyPush(String succcessMsg);

        void showSuccessfullyleaveMessage(String succcessMsg);

        /**
         * 设置是否加载指示器
         *
         * @param active true表示显示，false不显示
         */
        void setLoadingIndicator(boolean active);

    }

}
