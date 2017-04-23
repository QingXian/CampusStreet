package com.campusstreet.model;

import android.support.annotation.NonNull;

import com.campusstreet.entity.IdleSaleInfo;
import com.campusstreet.entity.LeaveMessageInfo;

import java.util.List;

/**
 * Created by Orange on 2017/4/19.
 */

public interface IdleSaleBiz {

    //获取闲置商品列表
    void fetchIdleSaleList(int type,int pi,@NonNull LoadIdleSaleListCallback callback);

    //搜索商品列表
    void searchIdleSale(String picType, @NonNull searchIdleSaleCallback callback);

    void addIdleGoods(IdleSaleInfo idleSaleInfo ,@NonNull addIdleGoodsCallback callback);

    void leaveMessagae(int gid ,String uid ,String con,@NonNull LeaveMessageCallback callback);

    void fetchIdleSaleMessageList(int id,int pi,@NonNull LoadIdleSaleMessageListCallback callback);

    interface addIdleGoodsCallback {

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
    interface LoadIdleSaleMessageListCallback {

        // 加载所有IdleSaleMessage
        void onIdleSaleMessageListLoaded(List<LeaveMessageInfo> leaveMessageInfos);

        // 获取数据失败的回调
        void onDataNotAvailable(String errorMsg);
    }

}
