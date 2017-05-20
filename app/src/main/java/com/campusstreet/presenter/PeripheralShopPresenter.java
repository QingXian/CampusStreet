package com.campusstreet.presenter;

import com.campusstreet.contract.IPeripheralShopContract;
import com.campusstreet.entity.PeripheralShopGoodInfo;
import com.campusstreet.entity.PeripheralShopInfo;
import com.campusstreet.model.IPeripheralShopBiz;
import com.campusstreet.model.PeripheralShopImpl;

import java.util.List;

/**
 * Created by Orange on 2017/4/24.
 */

public class PeripheralShopPresenter implements IPeripheralShopContract.Presenter {
    
    public static final String TAG = "PeripheralShopPresenter";

    private PeripheralShopImpl mPeripheralShopImpl;
    private IPeripheralShopContract.View mView;


    public PeripheralShopPresenter(PeripheralShopImpl PeripheralShopImpl, IPeripheralShopContract.View view) {
        mPeripheralShopImpl = PeripheralShopImpl;
        mView = view;

        mView.setPresenter(this);
    }

    @Override
    public void fetchPeriPheralShopList(int type, String key, int pi) {
        mPeripheralShopImpl.fetchPeripheralShopList(type,pi,key, new IPeripheralShopBiz.LoadPeripheralShopListCallback() {
            @Override
            public void onPeripheralShopListLoaded(List<PeripheralShopInfo> peripheralShopInfos) {
                mView.setPeriPheralShop(peripheralShopInfos);
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
    public void fetchShopCommodityList(int sid, int type, String key, int pi) {
        mPeripheralShopImpl.fetchShopGoodList(sid, type, key, pi, new IPeripheralShopBiz.LoadShopCommodityListCallback() {
            @Override
            public void onShopCommodityListLoaded(List<PeripheralShopGoodInfo> peripheralShopGoodInfos) {
                mView.setShopCommodityList(peripheralShopGoodInfos);
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
    public void fetchPeriPheralShopCategories() {
        mPeripheralShopImpl.fetchPeripheralCategories(new IPeripheralShopBiz.LoadPeripheralCategoriesCallback() {
            @Override
            public void onPeripheralCategoriesLoad(String[] type) {
                mView.setPeriPheralShopCategories(type);
                mView.setLoadingIndicator(false);
            }

            @Override
            public void onDataNotAvailable(String errorMsg) {
                mView.showErrorMsg(errorMsg);
            }
        });
    }
}
