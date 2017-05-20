package com.campusstreet.contract;

import com.campusstreet.entity.LeaveMessageInfo;
import com.campusstreet.entity.PeripheralShopGoodInfo;
import com.campusstreet.entity.PeripheralShopInfo;

import java.util.List;

/**
 * Created by Orange on 2017/4/24.
 */

public interface IPeripheralShopContract {

    interface Presenter extends BasePresenter {

        void fetchPeriPheralShopList(int type, String key, int pi);

        void fetchShopCommodityList(int sid, int type, String key, int pi);

        void fetchPeriPheralShopCategories();


    }

    interface View extends BaseView<Presenter> {

        void setPeriPheralShop(List<PeripheralShopInfo> peripheralShopInfos);

        void setShopCommodityList(List<PeripheralShopGoodInfo> peripheralShopGoodInfos);

        void setPeriPheralShopCategories(String[] type);

        void showErrorMsg(String errorMsg);

        /**
         * 设置是否加载指示器
         *
         * @param active true表示显示，false不显示
         */
        void setLoadingIndicator(boolean active);

    }
}
