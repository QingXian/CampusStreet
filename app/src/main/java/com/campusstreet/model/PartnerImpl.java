package com.campusstreet.model;

import android.content.Context;
import android.support.annotation.NonNull;

import com.campusstreet.api.PartnerClient;
import com.campusstreet.api.ServiceGenerator;

/**
 * Created by Orange on 2017/4/24.
 */

public class PartnerImpl implements IPartnerBiz  {
    
    private final String TAG = this.getClass().getSimpleName();
    private static PartnerImpl sPartnerImple;
    private PartnerClient mPartnerClient;


    private PartnerImpl(Context context) {
        mPartnerClient = ServiceGenerator.createService(context, PartnerClient.class);
    }

    public static PartnerImpl getInstance(Context context) {

        if (sPartnerImple == null) {
            sPartnerImple = new PartnerImpl(context);
        }
        return sPartnerImple;
    }

    @Override
    public void fetchPartnerList(int type, int pi, @NonNull LoadPartnerListCallback callback) {

    }

    @Override
    public void searchPartner(String picType, @NonNull searchPartnerCallback callback) {

    }

    @Override
    public void fetchPartnerCategories(String picType, @NonNull LoadPartnerCategoriesCallback callback) {

    }
}
