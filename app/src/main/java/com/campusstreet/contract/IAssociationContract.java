package com.campusstreet.contract;

import android.support.annotation.NonNull;

import com.campusstreet.entity.AssociationInfo;
import com.campusstreet.entity.AssociationNumInfo;
import com.campusstreet.entity.AssociationPostInfo;
import com.campusstreet.entity.AssociationPostMessageInfo;
import com.campusstreet.entity.IdleSaleInfo;
import com.campusstreet.entity.LeaveMessageInfo;
import com.campusstreet.entity.NewInfo;
import com.campusstreet.entity.UserAssociationInfo;

import java.util.List;

/**
 * Created by Orange on 2017/4/24.
 */

public interface IAssociationContract {

    interface Presenter extends BasePresenter {

        void fetchAssociationList(int pi);

        //获取社团下帖子列表数据
        void fetchAssociationPostList(int aid, int pi);

        //留言请求
        void onLeaveMessage(int pid, String uid, String con);

        //加入社团
        void onJoinAssociation(int aid, String uid ,String con);

        //同意加入社团
        void onApplyJoinAssn(int pid, String uid ,int st,String con);

        void fetchAssociationNumList(int aid ,int pi,int ps);

        //发布帖子
        void addAssociationPost(int aid,String uid ,String con,String title);

        void addAssociationNotice(int aid,String uid ,String con);

        //获取社团下帖子的留言列表数据
        void fetchAssociationPostMessageList(int pid, int pi);

        //获取社团下帖子的留言列表数据

        void fetchAssociationPostDetail(int pid);

        void fetchUserAssociationList(int pi,String uid);

    }

    interface View extends BaseView<Presenter> {

        void setAssociationPostMessageList(List<AssociationPostMessageInfo> associationPostMessageList);

        void setAssociationPostList(List<AssociationPostInfo> associationPostList);

        void setAssociationNumList(List<AssociationNumInfo> associationNumList);

        void setAssociationList(List<AssociationInfo> associationList);

        void setUserAssociationList(List<UserAssociationInfo> UserAssociationList);

        void showSuccessfullyJoin(String succcessMsg);

        void showSuccessfullyAddNotice(String succcessMsg);

        void showSuccessfullyApplyJoin(String succcessMsg);

        void showSuccessfullyPushPost(String succcessMsg);

        void setAssociationPostDetail(AssociationPostInfo associationPostInfo);

        void showErrorMsg(String errorMsg);

        void showSuccessfullyleaveMessage(String succcessMsg);

        /**
         * 设置是否加载指示器
         *
         * @param active true表示显示，false不显示
         */
        void setLoadingIndicator(boolean active);

    }
}
