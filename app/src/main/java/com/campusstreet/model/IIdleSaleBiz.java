package com.campusstreet.model;

import android.support.annotation.NonNull;

import com.campusstreet.entity.CategoriesInfo;
import com.campusstreet.entity.IdleSaleInfo;
import com.campusstreet.entity.LeaveMessageInfo;

import java.util.List;

import static android.R.attr.type;

/**
 * Created by Orange on 2017/4/19.
 */

public interface IIdleSaleBiz {

    //获取闲置商品列表
    void fetchIdleSaleList(int type, int pi, @NonNull LoadIdleSaleListCallback callback);

    void fetchUserIdleSaleList(String uid, String key, int pi, @NonNull LoadUserIdleSaleListCallback callback);

    void fetchIdleSaleCategories(@NonNull LoadIdleSaleCategoriesCallback callback);

    void addIdleGoods(IdleSaleInfo idleSaleInfo, @NonNull addIdleGoodsCallback callback);

    void leaveMessagae(int gid, String uid, String con, @NonNull LeaveMessageCallback callback);

    void fetchIdleSaleMessageList(int id, int pi, @NonNull LoadIdleSaleMessageListCallback callback);

    void fetchIdleSaleDetail(int gid, @NonNull LoadIdleSaleDetailCallback callback);

    interface addIdleGoodsCallback {

        void onAddSuccess();

        void onAddFailure(String errorMsg);
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

    interface LoadIdleSaleDetailCallback {

        // 加载所有IdleSale
        void onIdleSaleDetailLoaded(IdleSaleInfo idleSaleInfo);

        // 获取数据失败的回调
        void onDataNotAvailable(String errorMsg);
    }

    interface LoadUserIdleSaleListCallback {

        // 加载所有IdleSale
        void onUserIdleSaleListLoaded(List<IdleSaleInfo> idleSaleInfoList);

        // 获取数据失败的回调
        void onDataNotAvailable(String errorMsg);
    }

    interface LoadIdleSaleCategoriesCallback {

        void onIdleSaleCategoriesLoaded(List<CategoriesInfo> categoriesInfos);

        void onDataNotAvailable(String errorMsg);
    }

    interface LoadIdleSaleMessageListCallback {

        // 加载所有IdleSaleMessage
        void onIdleSaleMessageListLoaded(List<LeaveMessageInfo> leaveMessageInfos);

        // 获取数据失败的回调
        void onDataNotAvailable(String errorMsg);
    }

}
