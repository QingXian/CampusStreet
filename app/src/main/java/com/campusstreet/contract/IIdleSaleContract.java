package com.campusstreet.contract;

import com.campusstreet.entity.IdleSaleInfo;
import com.campusstreet.entity.LeaveMessageInfo;

import java.util.List;

import static android.R.attr.type;

/**
 * Created by Orange on 2017/4/15.
 */

public interface IIdleSaleContract {

    interface Presenter extends BasePresenter {

        void fetchIdleSaleCategories();

        void fetchIdleSaleList(int type, int pi);

        void fetchUserIdleSaleList(String uid, String key, int pi);

        void pushGoods(IdleSaleInfo idleSaleInfo);

        void leaveMessage(String uid, int gid, String con);

        void fetchIdleSaleMessageList(int id, int pi);


    }

    interface View extends BaseView<Presenter> {

        void setIdleSale(List<IdleSaleInfo> idleSaleInfoList);

        void setIdleSaleCategories(String[] type);

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
