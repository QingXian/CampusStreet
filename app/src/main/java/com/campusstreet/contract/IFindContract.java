package com.campusstreet.contract;

import com.campusstreet.entity.LiveInfo;
import com.campusstreet.entity.LiveReplyInfo;

import java.util.List;

/**
 * Created by Orange on 2017/4/15.
 */

public interface IFindContract {

    interface Presenter extends BasePresenter {

        void fetchFindList(int pi);

        void fetchTopImge();

        void pushLive(LiveInfo liveInfo);

        void fetchLiveReplyList(int did, int pi);

        void replyLive(String uid, int did, String con);

        void deleteLive(String uid, int did);

    }

    interface View extends BaseView<Presenter> {


        void setLiveReplyList(List<LiveReplyInfo> liveReplyList);

        void showDeleteSuccess();

        void showReplySuccess();

        void showOperationError(String errorMsg);

        void setFindList(List<LiveInfo> liveInfos);

        void setTopImge();

        void showErrorMsg(String errorMsg);

        void showSuccessfullyPush(String succcessMsg);

        /**
         * 设置是否加载指示器
         *
         * @param active true表示显示，false不显示
         */
        void setLoadingIndicator(boolean active);


    }
}
