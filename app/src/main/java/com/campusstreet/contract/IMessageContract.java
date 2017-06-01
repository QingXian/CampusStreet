package com.campusstreet.contract;

import com.campusstreet.entity.MessageInfo;

import java.util.List;

/**
 * Created by Orange on 2017/4/15.
 */

public interface IMessageContract {

    interface Presenter extends BasePresenter {

        void fetchMessageList(String uid, int pi);

    }

    interface View extends BaseView<Presenter> {


        void setMessageList(List<MessageInfo> messageInfo);

        void showErrorMsg(String errorMsg);

        /**
         * 设置是否加载指示器
         *
         * @param active true表示显示，false不显示
         */
        void setLoadingIndicator(boolean active);


    }
}
