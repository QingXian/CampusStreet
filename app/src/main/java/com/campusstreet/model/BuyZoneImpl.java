package com.campusstreet.model;

import android.content.Context;
import android.support.annotation.NonNull;

import com.campusstreet.api.BuyZoneClient;
import com.campusstreet.api.ServiceGenerator;

/**
 * Created by Orange on 2017/4/24.
 */

public class BuyZoneImpl implements IBuyZoneBiz {

    private final String TAG = this.getClass().getSimpleName();
    private static BuyZoneImpl sBuyZoneImple;
    private BuyZoneClient mBuyZoneClient;


    private BuyZoneImpl(Context context) {
        mBuyZoneClient = ServiceGenerator.createService(context, BuyZoneClient.class);
    }

    public static BuyZoneImpl getInstance(Context context) {

        if (sBuyZoneImple == null) {
            sBuyZoneImple = new BuyZoneImpl(context);
        }
        return sBuyZoneImple;
    }
    
    @Override
    public void fetchBuyZoneList(int type, int pi, @NonNull LoadBuyZoneListCallback callback) {
        
    }

    @Override
    public void addBuy(@NonNull addIdleGoodsCallback callback) {

    }

    @Override
    public void leaveMessagae(int gid, String uid, String con, @NonNull LeaveMessageCallback callback) {

    }

    @Override
    public void fetchBuyZoneMessageList(int id, int pi, @NonNull LoadBuyZoneMessageListCallback callback) {

    }
}
