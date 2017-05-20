package com.campusstreet.presenter;

import com.campusstreet.contract.IIdleSaleContract;
import com.campusstreet.entity.IdleSaleInfo;
import com.campusstreet.entity.LeaveMessageInfo;
import com.campusstreet.model.IBountyHallBiz;
import com.campusstreet.model.IIdleSaleBiz;
import com.campusstreet.model.IdleSaleImpl;

import java.util.List;

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
           public void onIdleSaleCategoriesLoaded(String[] type) {
               mView.setIdleSaleCategories(type);
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
        mIdleSaleImpl.leaveMessagae(gid, uid,con,
        new IIdleSaleBiz.LeaveMessageCallback() {
            @Override
            public void onLeaveMessageSuccess() {
                mView.showSuccessfullyleaveMessage("留言成功");
                mView.setLoadingIndicator(false);
            }

            @Override
            public void onLeaveMessageFailure(String errorMsg) {
                mView.showErrorMsg(errorMsg+"留言失败");
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
                mView.showErrorMsg(errorMsg);
            }
        });
    }
}
