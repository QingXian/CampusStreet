package com.campusstreet.presenter;

import android.util.Log;

import com.campusstreet.contract.IFindContract;
import com.campusstreet.contract.IFindContract;
import com.campusstreet.entity.LiveInfo;
import com.campusstreet.entity.LiveReplyInfo;
import com.campusstreet.model.FindImpl;
import com.campusstreet.model.IFindBiz;

import java.util.List;

/**
 * Created by Orange on 2017/4/17.
 */

public class FindPresenter implements IFindContract.Presenter {

    public static final String TAG = "FindPresenter";

    private FindImpl mFindImpl;
    private IFindContract.View mView;


    public FindPresenter(FindImpl findImpl, IFindContract.View view) {
        mFindImpl = findImpl;
        mView = view;

        mView.setPresenter(this);
    }

    @Override
    public void fetchFindList(int pi) {
        mFindImpl.fetchFindList(pi, new IFindBiz.LoadFindListCallback() {
            @Override
            public void onFindListLoaded(List<LiveInfo> liveInfo) {
                mView.setFindList(liveInfo);
                mView.setLoadingIndicator(false);
            }

            @Override
            public void onDataNotAvailable(String errorMsg) {
                mView.showErrorMsg(errorMsg);
                mView.setLoadingIndicator(false);
            }
        });
    }

    @Override
    public void fetchTopImge() {

    }

    @Override
    public void pushLive(LiveInfo liveInfo) {
        mView.setLoadingIndicator(true);
        mFindImpl.addLive(liveInfo, new IFindBiz.AddLiveCallback() {
            @Override
            public void onAddSuccess() {
                mView.showSuccessfullyPush("发布成功");
                mView.setLoadingIndicator(false);
            }

            @Override
            public void onAddFailure(String errorMsg) {
                mView.showErrorMsg(errorMsg);
                mView.setLoadingIndicator(false);
            }
        });
    }

    @Override
    public void fetchLiveReplyList(int did, int pi) {
        mFindImpl.fetchLiveReplyList(did, pi, new IFindBiz.LoadLiveReplyListCallback() {
            @Override
            public void onFindListLoaded(List<LiveReplyInfo> liveReplyInfos) {
                mView.setLiveReplyList(liveReplyInfos);
                mView.setLoadingIndicator(false);
            }

            @Override
            public void onDataNotAvailable(String errorMsg) {
                mView.showErrorMsg(errorMsg);
                mView.setLoadingIndicator(false);
            }
        });
    }

    @Override
    public void replyLive(String uid, int did, String con) {
        mView.setLoadingIndicator(true);
        mFindImpl.onReplyLive(uid, did, con, new IFindBiz.onReplyLiveCallback() {
            @Override
            public void onReplyLiveSuccess() {
                mView.showReplySuccess();
                mView.setLoadingIndicator(false);
            }

            @Override
            public void onReplyLiveFailure(String errorMsg) {
                mView.showOperationError(errorMsg);
                mView.setLoadingIndicator(false);
            }
        });
    }

    @Override
    public void deleteLive(String uid, int did) {
        mView.setLoadingIndicator(true);
        mFindImpl.onDeleteLive(uid, did, new IFindBiz.onDeleteLiveCallback() {
            @Override
            public void onDeleteLiveSuccess() {
                mView.showDeleteSuccess();
                mView.setLoadingIndicator(false);
            }

            @Override
            public void onDeleteLiveFailure(String errorMsg) {
                mView.showOperationError(errorMsg);
                mView.setLoadingIndicator(false);
            }
        });
    }

}
