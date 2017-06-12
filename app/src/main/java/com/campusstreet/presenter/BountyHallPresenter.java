package com.campusstreet.presenter;

import com.campusstreet.contract.IBountyHallContract;
import com.campusstreet.contract.IBountyHallContract;
import com.campusstreet.entity.BountyHallInfo;
import com.campusstreet.entity.BountyHallInfo;
import com.campusstreet.entity.CategoriesInfo;
import com.campusstreet.entity.JoinInfo;
import com.campusstreet.entity.UserJoinTaskInfo;
import com.campusstreet.model.BountyHallImpl;
import com.campusstreet.model.IBountyHallBiz;

import java.util.List;

import static com.campusstreet.common.Const.JOINNOTPASS;

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
            public void onBountyHallCategoriesLoaded(List<CategoriesInfo> categoriesInfos) {
                mView.setBountyHallCategories(categoriesInfos);
            }

            @Override
            public void onDataNotAvailable(String errorMsg) {
                mView.showErrorMsg(errorMsg);
            }
        });
    }

    @Override
    public void fetchTaskList(int tp, int pi, String key) {
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
    public void fetchTaskDetail(int tid) {
        mView.setLoadingIndicator(true);
        mBountyHallImpl.fetchTaskDetail(tid, new IBountyHallBiz.LoadTaskDetailCallback() {
            @Override
            public void onUserTaskDetailLoaded(BountyHallInfo bountyHallInfo) {
                mView.setLoadingIndicator(false);
                mView.setTaskDetail(bountyHallInfo);
            }

            @Override
            public void onDataNotAvailable(String errorMsg) {
                if (!errorMsg.equals("")) {
                    mView.showErrorMsg(errorMsg);
                }
                mView.setLoadingIndicator(false);
            }

        });
    }

    @Override
    public void fetchjoinTaskList(int tid, final int state, int pi) {
        mView.setLoadingIndicator(true);
        mBountyHallImpl.fetchjoinTaskList(tid, state, pi, new IBountyHallBiz.LoadJoinTaskListCallback() {
            @Override
            public void onJoinTaskListLoaded(List<JoinInfo> joinInfos) {
                if (state == JOINNOTPASS) {
                    mView.setLoadingIndicator(false);
                    mView.setJoinTaskList(joinInfos);
                } else {
                    mView.setLoadingIndicator(false);
                    mView.setPassTaskList(joinInfos);
                }
            }

            @Override
            public void onDataNotAvailable(String errorMsg) {
                mView.setLoadingIndicator(false);
                if (state == JOINNOTPASS) {
                    mView.showNoPassMsg();
                } else
                    mView.showNoPassMsg();
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
    public void passJoinTask(String uid, int tid, int tpid, int st) {
        mView.setLoadingIndicator(true);
        mBountyHallImpl.onPassJoinTask(uid, tid, tpid, st, new IBountyHallBiz.onPassJoinTaskCallback() {
            @Override
            public void onPassJoinTaskSuccess() {
                mView.setLoadingIndicator(false);
                mView.showSuccessfullPassJoinTask();
            }

            @Override
            public void onPassJoinTaskFailure(String errorMsg) {
                mView.setLoadingIndicator(false);
                mView.showNoPassMsg();
            }
        });
    }

    @Override
    public void startTask(String uid, int tid, String state) {
        mView.setLoadingIndicator(true);
        mBountyHallImpl.onStartTask(uid, tid, state, new IBountyHallBiz.onStartTaskCallback() {
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

    @Override
    public void publisherOpTask(String uid, int tpid, int taskid, int state) {
        mView.setLoadingIndicator(true);
        mBountyHallImpl.onPublisherOpTask(uid, tpid, taskid, state, new IBountyHallBiz.onPublisherOpTaskCallback() {
            @Override
            public void onPublisherOpTaskSuccess() {
                mView.setLoadingIndicator(false);
                mView.showSuccessfullPublisherOpTask();
            }

            @Override
            public void onPublisherOpTaskFailure(String errorMsg) {
                mView.setLoadingIndicator(false);
                mView.showErrorMsg(errorMsg);
            }
        });
    }

    @Override
    public void completeTask(String uid, int tpid, int taskid) {
        mView.setLoadingIndicator(true);
        mBountyHallImpl.onCompleteTask(uid, tpid, taskid, new IBountyHallBiz.onCompleteTaskCallback() {
            @Override
            public void onCompleteTaskSuccess() {
                mView.setLoadingIndicator(false);
                mView.showSuccessfullCompleteTask();
            }

            @Override
            public void onCompleteTaskFailure(String errorMsg) {
                mView.setLoadingIndicator(false);
                mView.showErrorMsg(errorMsg);
            }
        });
    }

    @Override
    public void giveUpTask(String uid, int tpid, int taskid) {
        mView.setLoadingIndicator(true);
        mBountyHallImpl.onGiveUpTask(uid, tpid, taskid, new IBountyHallBiz.onGiveUpTaskCallback() {
            @Override
            public void onGiveUpTaskSuccess() {
                mView.setLoadingIndicator(false);
                mView.showSuccessfullGiveUpTask();
            }

            @Override
            public void onGiveUpTaskFailure(String errorMsg) {
                mView.setLoadingIndicator(false);
                mView.showErrorMsg(errorMsg);
            }
        });
    }

    @Override
    public void fetchUserJoinTaskList(String uid, int pi) {
        mView.setLoadingIndicator(true);
        mBountyHallImpl.fetchUserJoinTaskList(uid, pi, new IBountyHallBiz.LoadUserJoinTaskListCallback() {
            @Override
            public void onUserJoinTaskListLoaded(List<UserJoinTaskInfo> userJoinTaskInfos) {
                mView.setLoadingIndicator(false);
                mView.setUserJoinTaskList(userJoinTaskInfos);
            }

            @Override
            public void onDataNotAvailable(String errorMsg) {
                mView.setLoadingIndicator(false);
                mView.showErrorMsg(errorMsg);
            }
        });
    }
}
