package com.campusstreet.presenter;

import com.campusstreet.contract.IBuyZoneContract;
import com.campusstreet.contract.IBuyZoneContract;
import com.campusstreet.model.BuyZoneImpl;

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
    public void fetchBuyZoneList(int type, int pi) {
        
    }

    @Override
    public void pushBuy() {

    }

    @Override
    public void leaveMessage(String uid, int gid, String con) {

    }

    @Override
    public void fetchBuyZoneMessageList(int id, int pi) {

    }
}
