package com.campusstreet.model;

import android.support.annotation.NonNull;

import com.campusstreet.entity.BountyHallInfo;
import com.campusstreet.entity.CategoriesInfo;
import com.campusstreet.entity.IdleSaleInfo;
import com.campusstreet.entity.JoinInfo;
import com.campusstreet.entity.LeaveMessageInfo;
import com.campusstreet.entity.UserJoinTaskInfo;

import java.util.List;

import retrofit2.http.Field;

/**
 * Created by Orange on 2017/4/27.
 */

public interface IBountyHallBiz {

    //获取赏金大厅列表数据
    void fetchTaskList(int type, int pi, String key, @NonNull LoadTaskListCallback callback);

    //获取赏金大厅分类
    void fetchBountyHallCategories(@NonNull LoadBountyHallCategoriesCallback callback);

    //获取报名数据
    void fetchjoinTaskList(int tp, int state, int pi, @NonNull LoadJoinTaskListCallback callback);

    void fetchTaskDetail(int tid, @NonNull LoadTaskDetailCallback callback);

    //报名请求
    void onJoinTask(JoinInfo joinInfo, @NonNull onJoinTaskCallback callback);

    //通过报名请求
    void onPassJoinTask(String uid, int tid, int tpid, int st, @NonNull onPassJoinTaskCallback callback);

    void onPublisherOpTask(String uid, int tpid, int taskid, int state, @NonNull onPublisherOpTaskCallback callback);

    void onCompleteTask(String uid, int tpid, int taskid, @NonNull onCompleteTaskCallback callback);

    void onGiveUpTask(String uid, int tpid, int taskid, @NonNull onGiveUpTaskCallback callback);

    //确认开始服务
    void onStartTask(String uid, int tid, String state, @NonNull onStartTaskCallback callback);

    //发布赏金任务
    void addTask(BountyHallInfo bountyHallInfo, @NonNull addTaskCallback callback);

    //获取我的赏金任务
    void fetchUserTaskList(String uid, int tp, String key, int pi, LoadUserTaskListCallback callback);

    void fetchUserJoinTaskList(String uid,int pi, @NonNull LoadUserJoinTaskListCallback callback);

    interface addTaskCallback {

        void onAddSuccess();

        void onAddFailure(String errorMsg);
    }

    interface onJoinTaskCallback {

        void onJoinTaskSuccess();

        void onJoinTaskFailure(String errorMsg);
    }

    interface onPassJoinTaskCallback {

        void onPassJoinTaskSuccess();

        void onPassJoinTaskFailure(String errorMsg);
    }

    interface onStartTaskCallback {

        void onStartTaskSuccess();

        void onStartTaskFailure(String errorMsg);
    }

    interface onPublisherOpTaskCallback {

        void onPublisherOpTaskSuccess();

        void onPublisherOpTaskFailure(String errorMsg);
    }

    interface onCompleteTaskCallback {

        void onCompleteTaskSuccess();

        void onCompleteTaskFailure(String errorMsg);
    }

    interface onGiveUpTaskCallback {

        void onGiveUpTaskSuccess();

        void onGiveUpTaskFailure(String errorMsg);
    }

    interface LoadBountyHallCategoriesCallback {

        void onBountyHallCategoriesLoaded(List<CategoriesInfo> categoriesInfos);

        void onDataNotAvailable(String errorMsg);
    }


    interface LoadUserTaskListCallback {

        void onUserTaskListLoaded(List<BountyHallInfo> bountyHallInfos);

        void onDataNotAvailable(String errorMsg);
    }

    interface LoadUserJoinTaskListCallback {

        void onUserJoinTaskListLoaded(List<UserJoinTaskInfo> userJoinTaskInfos);

        void onDataNotAvailable(String errorMsg);
    }

    interface LoadTaskDetailCallback {

        void onUserTaskDetailLoaded(BountyHallInfo bountyHallInfo);

        void onDataNotAvailable(String errorMsg);
    }

    interface LoadTaskListCallback {

        void onTaskListLoaded(List<BountyHallInfo> bountyHallInfos);


        void onDataNotAvailable(String errorMsg);
    }

    interface LoadJoinTaskListCallback {


        void onJoinTaskListLoaded(List<JoinInfo> joinInfos);


        void onDataNotAvailable(String errorMsg);
    }
}
