package com.campusstreet.presenter;

import com.campusstreet.contract.IIdleSaleContract;
import com.campusstreet.contract.IIdleSaleContract;
import com.campusstreet.entity.IdleSaleInfo;
import com.campusstreet.entity.LeaveMessageInfo;
import com.campusstreet.model.IdleSaleBiz;
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

    }

    @Override
    public void fetchIdleSaleList(int type, int pi) {
        mIdleSaleImpl.fetchIdleSaleList(type, pi, new IdleSaleBiz.LoadIdleSaleListCallback() {
            @Override
            public void onIdleSaleListLoaded(List<IdleSaleInfo> idleSaleInfoList) {
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
    public void searchGoods(String keyWord) {

    }

    @Override
    public void pushGoods(IdleSaleInfo idleSaleInfo) {
        mView.setLoadingIndicator(true);
        mIdleSaleImpl.addIdleGoods(idleSaleInfo, new IdleSaleBiz.addIdleGoodsCallback() {
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
        new IdleSaleBiz.LeaveMessageCallback() {
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
        mIdleSaleImpl.fetchIdleSaleMessageList(id, pi, new IdleSaleBiz.LoadIdleSaleMessageListCallback() {
            @Override
            public void onIdleSaleMessageListLoaded(List<LeaveMessageInfo> leaveMessageInfos) {
                mView.setIdleSaleMessageList(leaveMessageInfos);
                mView.setLoadingIndicator(false);
            }

            @Override
            public void onDataNotAvailable(String errorMsg) {
                mView.setLoadingIndicator(false);
                mView.showErrorMsg(errorMsg+"获取留言信息失败");
            }
        });
    }
}
