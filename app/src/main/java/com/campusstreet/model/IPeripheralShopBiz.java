package com.campusstreet.model;

import android.support.annotation.NonNull;

import com.campusstreet.entity.LeaveMessageInfo;

import java.util.List;

/**
 * Created by Orange on 2017/4/24.
 */

public interface IPeripheralShopBiz {

    void fetchPeripheralShopList(int type,int pi,@NonNull LoadPeripheralShopListCallback callback);

    void searchPeripheralShop(String picType, @NonNull searchPeripheralShopCallback callback);

    void fetchShopCommodityList(int id,int pi,@NonNull LoadShopCommodityListCallback callback);


    interface searchPeripheralShopCallback {

        void onSearchSuccess();

        void onSearchFailure(String errorMsg);
    }


    interface LoadPeripheralShopListCallback {

        // 加载所有PeripheralShop
        void onPeripheralShopListLoaded();

        // 获取数据失败的回调
        void onDataNotAvailable(String errorMsg);
    }
    interface LoadShopCommodityListCallback {

        // 加载所有PeripheralShopMessage
        void onShopCommodityListLoaded();

        // 获取数据失败的回调
        void onDataNotAvailable(String errorMsg);
    }
}
