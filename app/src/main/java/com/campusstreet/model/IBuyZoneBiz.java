package com.campusstreet.model;

import android.support.annotation.NonNull;

import com.campusstreet.entity.BuyZoneInfo;
import com.campusstreet.entity.LeaveMessageInfo;

import java.util.List;

/**
 * Created by Orange on 2017/4/24.
 */

public interface IBuyZoneBiz {

    //获取求购列表
    void fetchBuyZoneList(int pi, @NonNull LoadBuyZoneListCallback callback);

    void fetchUserBuyZoneList(String uid, String key, int pi, @NonNull LoadUserBuyZoneListCallback callback);

    void fetchBuyZoneDetail(int gid, @NonNull LoadBuyZoneDetailCallback callback);

    void addBuy(BuyZoneInfo buyZoneInfo, @NonNull addIdleGoodsCallback callback);

    void leaveMessagae(int gid, String uid, String con, @NonNull LeaveMessageCallback callback);

    void fetchBuyZoneMessageList(int id, int pi, @NonNull LoadBuyZoneMessageListCallback callback);

    interface addIdleGoodsCallback {

        void onAddSuccess();

        void onAddFailure(String errorMsg);
    }


    interface LeaveMessageCallback {

        void onLeaveMessageSuccess();

        void onLeaveMessageFailure(String errorMsg);
    }

    interface LoadBuyZoneListCallback {

        // 加载所有BuyZone
        void onBuyZoneListLoaded(List<BuyZoneInfo> buyZoneInfoList);

        // 获取数据失败的回调
        void onDataNotAvailable(String errorMsg);
    }

    interface LoadBuyZoneDetailCallback {


        void onBuyZoneListLoaded(BuyZoneInfo buyZoneInfo);

        // 获取数据失败的回调
        void onDataNotAvailable(String errorMsg);
    }

    interface LoadUserBuyZoneListCallback {

        // 加载所有BuyZone
        void onUserBuyZoneListLoaded(List<BuyZoneInfo> buyZoneInfoList);

        // 获取数据失败的回调
        void onDataNotAvailable(String errorMsg);
    }

    interface LoadBuyZoneMessageListCallback {

        // 加载所有BuyZoneMessage
        void onBuyZoneMessageListLoaded(List<LeaveMessageInfo> leaveMessageInfos);

        // 获取数据失败的回调
        void onDataNotAvailable(String errorMsg);
    }
}
