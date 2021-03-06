package com.campusstreet.model;

import android.support.annotation.NonNull;

import com.campusstreet.entity.AssociationInfo;
import com.campusstreet.entity.AssociationNumInfo;
import com.campusstreet.entity.AssociationPostInfo;
import com.campusstreet.entity.AssociationPostMessageInfo;
import com.campusstreet.entity.BountyHallInfo;
import com.campusstreet.entity.JoinInfo;
import com.campusstreet.entity.UserAssociationInfo;

import java.util.List;

import retrofit2.http.Field;

/**
 * Created by Orange on 2017/5/6.
 */

public interface IAssociationBiz {

    //获取社团列表数据
    void fetchAssociationList(int pi, @NonNull LoadAssociationListCallback callback);

    //获取社团下帖子列表数据
    void fetchAssociationPostList(int aid, int pi, @NonNull LoadAssociationPostCallback callback);

    //留言请求
    void onLeaveMessage(int pid, String uid, String con, @NonNull onLeaveMessageCallback callback);

    //加入社团
    void onJoinAssociation(int aid, String uid, String con, @NonNull onJoinAssociationCallback callback);

    //同意加入社团
    void onApplyJoinAssn(int pid, String uid, int st, String con, @NonNull onApplyJoinAssnCallback callback);


    void fetchAssociationNumList(int aid, int pi, int ps, @NonNull LoadAssociationNumListCallback callback);

    //发布帖子
    void addAssociationPost(int aid, String uid, String con, String title, @NonNull addAssociationPostCallback callback);

    //获取社团下帖子的留言列表数据
    void fetchAssociationPostMessageList(int pid, int pi, @NonNull LoadAssociationPostMessageCallback callback);

    void fetchAssociationPostDetail(int pid, @NonNull LoadAssociationPostDetailCallback callback);

    void fetchUserAssociationList(int pi, String uid, @NonNull LoadUserAssociationListCallback callback);

    void onAddAssociationNotice(int aid, String uid, String con, @NonNull addAssociationNoticeCallback callback);

    interface addAssociationPostCallback {

        void onAddSuccess();

        void onAddFailure(String errorMsg);
    }

    interface addAssociationNoticeCallback {

        void onAddSuccess();

        void onAddFailure(String errorMsg);
    }

    interface onJoinAssociationCallback {

        void onJoinAssociationSuccess();

        void onJoinAssociationFailure(String errorMsg);
    }

    interface onLeaveMessageCallback {

        void onLeaveMessageSuccess();

        void onLeaveMessageFailure(String errorMsg);
    }

    interface onApplyJoinAssnCallback {

        void onApplyJoinAssnSuccess();

        void onApplyJoinAssnFailure(String errorMsg);
    }

    interface LoadAssociationListCallback {

        void onAssociationListLoaded(List<AssociationInfo> associationInfos);

        void onDataNotAvailable(String errorMsg);
    }

    interface LoadUserAssociationListCallback {

        void onUserAssociationListLoaded(List<UserAssociationInfo> userAssociationInfos);

        void onDataNotAvailable(String errorMsg);
    }


    interface LoadAssociationPostCallback {

        void onAssociationPostLoaded(List<AssociationPostInfo> associationPostInfos);

        void onDataNotAvailable(String errorMsg);
    }

    interface LoadAssociationPostMessageCallback {


        void onAssociationPostMessageLoaded(List<AssociationPostMessageInfo> associationPostMessageInfos);


        void onDataNotAvailable(String errorMsg);
    }

    interface LoadAssociationNumListCallback {


        void onAssociationNumListLoaded(List<AssociationNumInfo> associationNumInfos);


        void onDataNotAvailable(String errorMsg);
    }

    interface LoadAssociationPostDetailCallback {


        void onAssociationPostDetailLoaded(AssociationPostInfo associationPostInfo);


        void onDataNotAvailable(String errorMsg);
    }
}
