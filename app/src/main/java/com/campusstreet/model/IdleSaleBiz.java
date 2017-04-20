package com.campusstreet.model;

import android.support.annotation.NonNull;

import com.campusstreet.entity.IdleSaleInfo;

import java.util.List;

/**
 * Created by Orange on 2017/4/19.
 */

public interface IdleSaleBiz {

    //获取闲置商品列表
    void fetchIdleSaleList(int type,int pi,@NonNull LoadIdleSaleListCallback callback);

    //搜索商品列表
    void searchIdleSale(String picType, @NonNull searchIdleSaleCallback callback);

    void addGoods(@NonNull addGoodsCallback callback);

    void leaveMessagae(@NonNull LeaveMessageCallback callback);

    interface addGoodsCallback {

        void onAddSuccess();

        void onAddFailure(String errorMsg);
    }

    interface searchIdleSaleCallback {

        void onSearchSuccess();

        void onSearchFailure(String errorMsg);
    }


    interface LeaveMessageCallback {

        void onLeaveMessageSuccess();

        void onLeaveMessageFailure(String errorMsg);
    }

    interface LoadIdleSaleListCallback {

        // 加载所有IdleSale
        void onIdleSaleListLoaded(List<IdleSaleInfo> idleSaleInfoList);

        // 获取数据失败的回调
        void onDataNotAvailable(String errorMsg);
    }

}
