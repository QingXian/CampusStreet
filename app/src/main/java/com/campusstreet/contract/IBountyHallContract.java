package com.campusstreet.contract;

import com.campusstreet.entity.BountyHallInfo;
import com.campusstreet.entity.JoinInfo;

import java.util.List;

/**
 * Created by Orange on 2017/4/27.
 */

public interface IBountyHallContract {
    interface Presenter extends BasePresenter {

        //获取赏金大厅分类
        void fetchBountyHallCategories();

        //获取赏金大厅列表数据
        void fetchTaskList(int tp, int pi, String key);

        //获取报名数据
        void fetchjoinTaskList(int tid, int state, int pi);

        //报名请求
        void joinTask(JoinInfo joinInfo);

        //通过报名请求
        void passJoinTask(String uid, int tid, int tpid);

        //确认开始服务
        void startTask(String uid, int tid);

        //发布赏金任务
        void addTask(BountyHallInfo bountyHallInfo);

        //获取我的赏金任务
        void fetchUserTaskList(String uid, int tp, int pi,String key);


    }

    interface View extends BaseView<Presenter> {

        void setTaskList(List<BountyHallInfo> bountyHallInfos);

        void setUserTaskList(List<BountyHallInfo> bountyHallInfos);

        void setBountyHallCategories(String[] type);

        void setJoinTaskList(List<JoinInfo> joinInfos);

        //报名通过成功
        void showSuccessfullpassJoinTask();

        //报名成功
        void showSuccessfullJointask(String successMsg);

        //服务开始成功
        void showSuccessfullStartTask();

        void showErrorMsg(String errorMsg);

        //赏金任务发布成功
        void showSuccessfullyPush(String successMsg);

        void showfetchBountyHallCategoriesFailMsg(String errorMsg);


        /**
         * 设置是否加载指示器
         *
         * @param active true表示显示，false不显示
         */
        void setLoadingIndicator(boolean active);

    }
}