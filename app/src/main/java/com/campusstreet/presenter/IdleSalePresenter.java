package com.campusstreet.presenter;

import com.campusstreet.contract.IIdleSaleContract;
import com.campusstreet.entity.CategoriesInfo;
import com.campusstreet.entity.IdleSaleInfo;
import com.campusstreet.entity.LeaveMessageInfo;
import com.campusstreet.model.IBountyHallBiz;
import com.campusstreet.model.IIdleSaleBiz;
import com.campusstreet.model.IdleSaleImpl;

import java.util.List;

import static android.R.attr.id;
import static android.R.attr.type;

/**
 * Created by Orange on 2017/4/19.
 */

public class IdleSalePresenter implements IIdleSaleContract.Presenter {

    public static final String TAG = "IdleSalePresenter";

    private IdleSaleImpl mIdleSaleImpl;
    private IIdleSaleContract.View mView;


    public IdleSalePresenter(IdleSaleImpl IdleSaleImpl, IIdleSaleContract.View view) {
        mIdleSaleImpl = IdleSaleImpl;
        mView = view;

        mView.setPresenter(this);
    }


    @Override
    public void fetchIdleSaleCategories() {
        mIdleSaleImpl.fetchIdleSaleCategories(new IIdleSaleBiz.LoadIdleSaleCategoriesCallback() {
            @Override
            public void onIdleSaleCategoriesLoaded(List<CategoriesInfo> categoriesInfos) {
                mView.setIdleSaleCategories(categoriesInfos);
            }

            @Override
            public void onDataNotAvailable(String errorMsg) {
                mView.showErrorMsg(errorMsg);
            }
        });
    }

    @Override
    public void fetchIdleSaleList(int type, int pi) {
        mIdleSaleImpl.fetchIdleSaleList(type, pi, new IIdleSaleBiz.LoadIdleSaleListCallback() {
            @Override
            public void onIdleSaleListLoaded(List<IdleSaleInfo> idleSaleInfoList) {
                mView.setIdleSale(idleSaleInfoList);
            }

            @Override
            public void onDataNotAvailable(String errorMsg) {
                mView.setLoadingIndicator(false);
                mView.showErrorMsg(errorMsg);
            }
        });
    }

    @Override
    public void fetchUserIdleSaleList(String uid, String key, int pi) {
        mIdleSaleImpl.fetchUserIdleSaleList(uid, key, pi, new IIdleSaleBiz.LoadUserIdleSaleListCallback() {
            @Override
            public void onUserIdleSaleListLoaded(List<IdleSaleInfo> idleSaleInfoList) {
                mView.setIdleSale(idleSaleInfoList);
                mView.setLoadingIndicator(false);
            }

            @Override
            public void onDataNotAvailable(String errorMsg) {
                mView.setLoadingIndicator(false);
                mView.showErrorMsg(errorMsg);
            }
        });
    }


    @Override
    public void pushGoods(IdleSaleInfo idleSaleInfo) {
        mView.setLoadingIndicator(true);
        mIdleSaleImpl.addIdleGoods(idleSaleInfo, new IIdleSaleBiz.addIdleGoodsCallback() {
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
    public void leaveMessage(String uid, int gid, String con) {
        mIdleSaleImpl.leaveMessagae(gid, uid, con,
                new IIdleSaleBiz.LeaveMessageCallback() {
                    @Override
                    public void onLeaveMessageSuccess() {
                        mView.showSuccessfullyleaveMessage("留言成功");
                        mView.setLoadingIndicator(false);
                    }

                    @Override
                    public void onLeaveMessageFailure(String errorMsg) {
                        mView.showErrorMsg(errorMsg);
                        mView.setLoadingIndicator(false);
                    }
                });
    }


    @Override
    public void fetchIdleSaleMessageList(int id, int pi) {
        mIdleSaleImpl.fetchIdleSaleMessageList(id, pi, new IIdleSaleBiz.LoadIdleSaleMessageListCallback() {
            @Override
            public void onIdleSaleMessageListLoaded(List<LeaveMessageInfo> leaveMessageInfos) {
                mView.setIdleSaleMessageList(leaveMessageInfos);
                mView.setLoadingIndicator(false);
            }

            @Override
            public void onDataNotAvailable(String errorMsg) {
                mView.setLoadingIndicator(false);
                if (!errorMsg.equals("")) {
                    mView.showErrorMsg(errorMsg);
                }
            }
        });
    }

    @Override
    public void fetchIdleSaleDetail(int gid) {
        mView.setLoadingIndicator(true);
        mIdleSaleImpl.fetchIdleSaleDetail(gid, new IIdleSaleBiz.LoadIdleSaleDetailCallback() {
            @Override
            public void onIdleSaleDetailLoaded(IdleSaleInfo idleSaleInfo) {
                mView.setIdleSaleDetail(idleSaleInfo);
                mView.setLoadingIndicator(false);
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
}
