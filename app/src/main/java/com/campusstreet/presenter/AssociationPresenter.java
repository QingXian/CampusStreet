package com.campusstreet.presenter;

import com.campusstreet.contract.IAssociationContract;
import com.campusstreet.contract.IAssociationContract;
import com.campusstreet.entity.AssociationInfo;
import com.campusstreet.entity.AssociationNumInfo;
import com.campusstreet.entity.AssociationPostInfo;
import com.campusstreet.entity.AssociationPostMessageInfo;
import com.campusstreet.model.AssociationImpl;
import com.campusstreet.model.IAssociationBiz;
import com.campusstreet.model.IBuyZoneBiz;

import java.util.List;

/**
 * Created by Orange on 2017/5/7.
 */

public class AssociationPresenter implements IAssociationContract.Presenter {
    public static final String TAG = "AssociationPresenter";

    private AssociationImpl mAssociationImpl;
    private IAssociationContract.View mView;


    public AssociationPresenter(AssociationImpl AssociationImpl, IAssociationContract.View view) {
        mAssociationImpl = AssociationImpl;
        mView = view;

        mView.setPresenter(this);
    }

    @Override
    public void fetchAssociationList(int pi) {
        mView.setLoadingIndicator(true);
        mAssociationImpl.fetchAssociationList(pi, new IAssociationBiz.LoadAssociationListCallback() {
            @Override
            public void onAssociationListLoaded(List<AssociationInfo> associationInfos) {
                mView.setAssociationList(associationInfos);
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
    public void fetchAssociationPostList(int aid, int pi) {
        mView.setLoadingIndicator(true);
        mAssociationImpl.fetchAssociationPostList(aid, pi, new IAssociationBiz.LoadAssociationPostCallback() {
            @Override
            public void onAssociationPostLoaded(List<AssociationPostInfo> associationPostInfos) {
                mView.setAssociationPostList(associationPostInfos);
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
    public void onLeaveMessage(int pid, String uid, String con) {
        mView.setLoadingIndicator(true);
        mAssociationImpl.onLeaveMessage(pid, uid, con, new IAssociationBiz.onLeaveMessageCallback() {
            @Override
            public void onLeaveMessageSuccess() {
                mView.showSuccessfullyleaveMessage("留言成功");
                mView.setLoadingIndicator(false);
            }

            @Override
            public void onLeaveMessageFailure(String errorMsg) {
                mView.showErrorMsg(errorMsg + "留言失败");
                mView.setLoadingIndicator(false);
            }
        });
    }

    @Override
    public void onJoinAssociation(int aid, String uid, String con) {
        mView.setLoadingIndicator(true);
        mAssociationImpl.onJoinAssociation(aid, uid, con, new IAssociationBiz.onJoinAssociationCallback() {
            @Override
            public void onJoinAssociationSuccess() {
                mView.showSuccessfullyJoin("加入请求发送成功，等待审核");
                mView.setLoadingIndicator(false);
            }

            @Override
            public void onJoinAssociationFailure(String errorMsg) {
                mView.showErrorMsg(errorMsg);
                mView.setLoadingIndicator(true);
            }
        });
    }

    @Override
    public void onApplyJoinAssn(int pid, String uid, int st, String con) {

    }

    @Override
    public void fetchAssociationNumList(int pid, int pi, int ps) {
        mView.setLoadingIndicator(true);
        mAssociationImpl.fetchAssociationNumList(pid, pi, ps, new IAssociationBiz.LoadAssociationNumListCallback() {
            @Override
            public void onAssociationNumListLoaded(List<AssociationNumInfo> associationNumInfos) {
                mView.setAssociationNumList(associationNumInfos);
                mView.setLoadingIndicator(false);
            }

            @Override
            public void onDataNotAvailable(String errorMsg) {
                mView.setLoadingIndicator(false);
            }
        });
    }

    @Override
    public void addAssociationPost(int aid, String uid, String con, String title) {
        mView.setLoadingIndicator(true);
        mAssociationImpl.addAssociationPost(aid, uid, con, title, new IAssociationBiz.addAssociationPostCallback() {
            @Override
            public void onAddSuccess() {
                mView.setLoadingIndicator(false);
                mView.showSuccessfullyJoin("发布成功");
            }

            @Override
            public void onAddFailure(String errorMsg) {
                mView.showErrorMsg(errorMsg);
                mView.setLoadingIndicator(false);
            }
        });
    }

    @Override
    public void fetchAssociationPostMessageList(int pid, int pi) {
        mView.setLoadingIndicator(true);
        mAssociationImpl.fetchAssociationPostMessageList(pid, pi, new IAssociationBiz.LoadAssociationPostMessageCallback() {
            @Override
            public void onAssociationPostMessageLoaded(List<AssociationPostMessageInfo> associationPostMessageInfos) {
                mView.setLoadingIndicator(false);
                mView.setAssociationPostMessageList(associationPostMessageInfos);
            }

            @Override
            public void onDataNotAvailable(String errorMsg) {
                mView.showErrorMsg(errorMsg);
                mView.setLoadingIndicator(false);
            }
        });
    }

    @Override
    public void fetchAssociationPostDetail(int pid) {
        mAssociationImpl.fetchAssociationPostDetail(pid, new IAssociationBiz.LoadAssociationPostDetailCallback() {
            @Override
            public void onAssociationPostDetailLoaded(AssociationPostInfo associationPostInfo) {
                mView.setAssociationPostDetail(associationPostInfo);
            }

            @Override
            public void onDataNotAvailable(String errorMsg) {
                mView.showErrorMsg(errorMsg);
                mView.setLoadingIndicator(false);
            }
        });
    }
}
