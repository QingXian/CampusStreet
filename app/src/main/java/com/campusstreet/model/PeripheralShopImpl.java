package com.campusstreet.model;

import android.content.Context;
import android.support.annotation.NonNull;

import com.campusstreet.api.PeripheralShopClient;
import com.campusstreet.api.ServiceGenerator;

/**
 * Created by Orange on 2017/4/24.
 */

public class PeripheralShopImpl implements IPeripheralShopBiz {
    
    private final String TAG = this.getClass().getSimpleName();
    private static PeripheralShopImpl sPeripheralShopImple;
    private PeripheralShopClient mPeripheralShopClient;


    private PeripheralShopImpl(Context context) {
        mPeripheralShopClient = ServiceGenerator.createService(context, PeripheralShopClient.class);
    }

    public static PeripheralShopImpl getInstance(Context context) {

        if (sPeripheralShopImple == null) {
            sPeripheralShopImple = new PeripheralShopImpl(context);
        }
        return sPeripheralShopImple;
    }
    @Override
    public void fetchPeripheralShopList(int type, int pi, @NonNull LoadPeripheralShopListCallback callback) {
        
    }

    @Override
    public void searchPeripheralShop(String picType, @NonNull searchPeripheralShopCallback callback) {

    }

    @Override
    public void fetchShopCommodityList(int id, int pi, @NonNull LoadShopCommodityListCallback callback) {

    }
}
