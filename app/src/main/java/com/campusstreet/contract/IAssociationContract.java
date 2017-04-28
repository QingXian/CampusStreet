package com.campusstreet.contract;

import com.campusstreet.entity.IdleSaleInfo;
import com.campusstreet.entity.LeaveMessageInfo;

import java.util.List;

/**
 * Created by Orange on 2017/4/24.
 */

public interface IAssociationContract {

    interface Presenter extends BasePresenter {

        void fetchAssociationCategories();

        void fetchAssociationList(int type , int pi);

        void fetchMyAssociationList(int type , int pi);

        void searchGoods(String keyWord);

        void pushGoods(IdleSaleInfo idleSaleInfo);

        void leaveMessage(String uid,int gid,String con);

        void fetchIdleSaleMessageList(int id,int pi);




    }

    interface View extends BaseView<Presenter> {

        void setIdleSale(List<IdleSaleInfo> idleSaleInfoList);

        void setIdleSaleCategories();

        void setIdleSaleMessageList(List<LeaveMessageInfo> idleSaleMessageList);

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
