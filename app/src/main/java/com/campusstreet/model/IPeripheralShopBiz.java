package com.campusstreet.model;

import android.support.annotation.NonNull;

import com.campusstreet.entity.LeaveMessageInfo;
import com.campusstreet.entity.PeripheralShopGoodInfo;
import com.campusstreet.entity.PeripheralShopInfo;

import java.util.List;

/**
 * Created by Orange on 2017/4/24.
 */

public interface IPeripheralShopBiz {

    void fetchPeripheralShopList(int type,int pi,String key,@NonNull LoadPeripheralShopListCallback callback);

    void fetchPeripheralCategories(@NonNull LoadPeripheralCategoriesCallback callback);

    void fetchShopGoodList(int sid,int type,String key,int pi,@NonNull LoadShopCommodityListCallback callback);


    interface LoadPeripheralCategoriesCallback {

        void onPeripheralCategoriesLoad(String[] type);

        void onDataNotAvailable(String errorMsg);
    }


    interface LoadPeripheralShopListCallback {

        // 加载所有PeripheralShop
        void onPeripheralShopListLoaded(List<PeripheralShopInfo> peripheralShopInfos);

        // 获取数据失败的回调
        void onDataNotAvailable(String errorMsg);
    }
    interface LoadShopCommodityListCallback {

        // 加载所有PeripheralShopMessage
        void onShopCommodityListLoaded(List<PeripheralShopGoodInfo> peripheralShopGoodInfos);

        // 获取数据失败的回调
        void onDataNotAvailable(String errorMsg);
    }
}
