package com.campusstreet.presenter;

import com.campusstreet.contract.IBountyHallContract;
import com.campusstreet.contract.IBountyHallContract;
import com.campusstreet.entity.BountyHallInfo;
import com.campusstreet.entity.BountyHallInfo;
import com.campusstreet.entity.JoinInfo;
import com.campusstreet.model.BountyHallImpl;
import com.campusstreet.model.IBountyHallBiz;

import java.util.List;

/**
 * Created by Orange on 2017/4/28.
 */

public class BountyHallPresenter implements IBountyHallContract.Presenter {


    public static final String TAG = "BountyHallPresenter";

    private BountyHallImpl mBountyHallImpl;
    private IBountyHallContract.View mView;


    public BountyHallPresenter(BountyHallImpl BountyHallImpl, IBountyHallContract.View view) {
        mBountyHallImpl = BountyHallImpl;
        mView = view;

        mView.setPresenter(this);
    }

    @Override
    public void fetchBountyHallCategories() {
        mBountyHallImpl.fetchBountyHallCategories(new IBountyHallBiz.LoadBountyHallCategoriesCallback() {
            @Override
            public void onBountyHallCategoriesLoaded(String[] type) {
                mView.setBountyHallCategories(type);
            }

            @Override
            public void onDataNotAvailable(String errorMsg) {
                mView.showfetchBountyHallCategoriesFailMsg(errorMsg);
            }
        });
    }

    @Override
    public void fetchTaskList(int tp, int pi, String key) {
        mView.setLoadingIndicator(true);
        mBountyHallImpl.fetchTaskList(tp, pi, key, new IBountyHallBiz.LoadTaskListCallback() {
            @Override
            public void onTaskListLoaded(List<BountyHallInfo> bountyHallInfos) {
                mView.setLoadingIndicator(false);
                mView.setTaskList(bountyHallInfos);
            }

            @Override
            public void onDataNotAvailable(String errorMsg) {
                mView.setLoadingIndicator(false);
                mView.showErrorMsg(errorMsg);
            }
        });
    }

    @Override
    public void fetchjoinTaskList(int tid, int state, int pi) {
        mView.setLoadingIndicator(true);
        mBountyHallImpl.fetchjoinTaskList(tid, state, pi, new IBountyHallBiz.LoadJoinTaskListCallback() {
            @Override
            public void onJoinTaskListLoaded(List<JoinInfo> joinInfos) {
                mView.setLoadingIndicator(false);
                mView.setJoinTaskList(joinInfos);
            }

            @Override
            public void onDataNotAvailable(String errorMsg) {
                mView.setLoadingIndicator(false);
                mView.showErrorMsg(errorMsg);
            }
        });

    }

    @Override
    public void joinTask(JoinInfo joinInfo) {
        mView.setLoadingIndicator(true);
        mBountyHallImpl.onJoinTask(joinInfo, new IBountyHallBiz.onJoinTaskCallback() {
            @Override
            public void onJoinTaskSuccess() {
                mView.setLoadingIndicator(true);
                mView.showSuccessfullJointask("报名成功");
            }

            @Override
            public void onJoinTaskFailure(String errorMsg) {
                mView.setLoadingIndicator(false);
                mView.showErrorMsg(errorMsg);
            }
        });

    }

    @Override
    public void passJoinTask(String uid, int tid, int tpid) {
        mView.setLoadingIndicator(true);
        mBountyHallImpl.onPassJoinTask(uid, tid, tpid, new IBountyHallBiz.onPassJoinTaskCallback() {
            @Override
            public void onPassJoinTaskSuccess() {
                mView.setLoadingIndicator(false);
                mView.showSuccessfullpassJoinTask();
            }

            @Override
            public void onPassJoinTaskFailure(String errorMsg) {
                mView.setLoadingIndicator(false);
                mView.showErrorMsg(errorMsg);
            }
        });
    }

    @Override
    public void startTask(String uid, int tid) {
        mView.setLoadingIndicator(true);
        mBountyHallImpl.onStartTask(uid, tid, new IBountyHallBiz.onStartTaskCallback() {
            @Override
            public void onStartTaskSuccess() {
                mView.setLoadingIndicator(false);
                mView.showSuccessfullStartTask();
            }

            @Override
            public void onStartTaskFailure(String errorMsg) {
                mView.setLoadingIndicator(false);
                mView.showErrorMsg(errorMsg);
            }
        });
    }

    @Override
    public void addTask(BountyHallInfo bountyHallInfo) {
        mView.setLoadingIndicator(true);
        mBountyHallImpl.addTask(bountyHallInfo, new IBountyHallBiz.addTaskCallback() {
            @Override
            public void onAddSuccess() {
                mView.setLoadingIndicator(false);
                mView.showSuccessfullyPush("发布成功");
            }

            @Override
            public void onAddFailure(String errorMsg) {
                mView.setLoadingIndicator(false);
                mView.showErrorMsg(errorMsg);
            }
        });
    }

    @Override
    public void fetchUserTaskList(String uid, int tp, int pi, String key) {
        mView.setLoadingIndicator(true);
        mBountyHallImpl.fetchUserTaskList(uid, tp, key, pi, new IBountyHallBiz.LoadUserTaskListCallback() {
            @Override
            public void onUserTaskListLoaded(List<BountyHallInfo> bountyHallInfos) {
                mView.setLoadingIndicator(false);
                mView.setTaskList(bountyHallInfos);
            }

            @Override
            public void onDataNotAvailable(String errorMsg) {
                mView.setLoadingIndicator(false);
                mView.showErrorMsg(errorMsg);
            }
        });
    }
}
