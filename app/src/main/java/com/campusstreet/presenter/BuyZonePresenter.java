package com.campusstreet.presenter;

import com.campusstreet.contract.IBuyZoneContract;
import com.campusstreet.contract.IBuyZoneContract;
import com.campusstreet.entity.BuyZoneInfo;
import com.campusstreet.entity.LeaveMessageInfo;
import com.campusstreet.model.BuyZoneImpl;
import com.campusstreet.model.IBuyZoneBiz;
import com.campusstreet.model.IIdleSaleBiz;

import java.util.List;

/**
 * Created by Orange on 2017/4/24.
 */

public class BuyZonePresenter implements IBuyZoneContract.Presenter {

    public static final String TAG = "BuyZonePresenter";

    private BuyZoneImpl mBuyZoneImpl;
    private IBuyZoneContract.View mView;


    public BuyZonePresenter(BuyZoneImpl BuyZoneImpl, IBuyZoneContract.View view) {
        mBuyZoneImpl = BuyZoneImpl;
        mView = view;

        mView.setPresenter(this);
    }

    @Override
    public void fetchBuyZoneList(int pi) {
        mBuyZoneImpl.fetchBuyZoneList(pi, new IBuyZoneBiz.LoadBuyZoneListCallback() {
            @Override
            public void onBuyZoneListLoaded(List<BuyZoneInfo> buyZoneInfoList) {
                mView.setBuyZone(buyZoneInfoList);
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
    public void fetchBuyZoneDetail(int gid) {
        mView.setLoadingIndicator(true);
        mBuyZoneImpl.fetchBuyZoneDetail(gid, new IBuyZoneBiz.LoadBuyZoneDetailCallback() {
            @Override
            public void onBuyZoneListLoaded(BuyZoneInfo buyZoneInfo) {
                mView.setBuyZoneDetail(buyZoneInfo);
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

    @Override
    public void fetchUserBuyZoneList(String uid, String key, int pi) {
        mBuyZoneImpl.fetchUserBuyZoneList(uid, key, pi, new IBuyZoneBiz.LoadUserBuyZoneListCallback() {
            @Override
            public void onUserBuyZoneListLoaded(List<BuyZoneInfo> buyZoneInfoList) {
                mView.setBuyZone(buyZoneInfoList);
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
    public void pushBuy(BuyZoneInfo buyZoneInfo) {
        mView.setLoadingIndicator(true);
        mBuyZoneImpl.addBuy(buyZoneInfo, new IBuyZoneBiz.addIdleGoodsCallback() {
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
        mBuyZoneImpl.leaveMessagae(gid, uid, con, new IBuyZoneBiz.LeaveMessageCallback() {
            @Override
            public void onLeaveMessageSuccess() {
//                mView.showSuccessfullyleaveMessage("留言成功");
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
    public void fetchBuyZoneMessageList(int id, int pi) {
        mBuyZoneImpl.fetchBuyZoneMessageList(id, pi, new IBuyZoneBiz.LoadBuyZoneMessageListCallback() {
            @Override
            public void onBuyZoneMessageListLoaded(List<LeaveMessageInfo> leaveMessageInfos) {
                mView.setBuyZoneMessageList(leaveMessageInfos);
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
