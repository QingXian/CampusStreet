package com.campusstreet.presenter;

import com.campusstreet.contract.IPeripheralShopContract;
import com.campusstreet.model.PeripheralShopImpl;

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
    public void fetchPeriPheralShopList(int type, int pi) {

    }

    @Override
    public void searchShop(String keyWord) {

    }

    @Override
    public void fetchShopCommodityList(int id, int pi) {

    }
}
