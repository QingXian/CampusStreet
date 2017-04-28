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
        mView.setLoadingIndicator(true);
        mBountyHallImpl.fetchBountyHallCategories(new IBountyHallBiz.LoadBountyHallCategoriesCallback() {
            @Override
            public void onBountyHallCategoriesLoaded(String[] type) {
                mView.setLoadingIndicator(false);
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
                mView.showErrorMsg(errorMsg);
            }
        });
    }

    @Override
    public void fetchjoinTaskList(int tid, int state, int pi) {

    }

    @Override
    public void joinTask(JoinInfo joinInfo) {

    }

    @Override
    public void passJoinTask(String uid, int tid, int tpid) {

    }

    @Override
    public void startTask(String uid, int tid) {

    }

    @Override
    public void addTask(BountyHallInfo bountyHallInfo) {

    }

    @Override
    public void fetchUserTaskList(String uid, int tp, int pi, String key) {

    }
}
