package com.campusstreet.model;

import android.content.Context;
import android.support.annotation.NonNull;

import com.campusstreet.api.CampusInformationClient;
import com.campusstreet.api.ServiceGenerator;

/**
 * Created by Orange on 2017/4/24.
 */

public class CampusInformationImpl implements ICampusInformationBiz {
    
    private final String TAG = this.getClass().getSimpleName();
    private static CampusInformationImpl sCampusInformationImple;
    private CampusInformationClient mCampusInformationClient;


    private CampusInformationImpl(Context context) {
        mCampusInformationClient = ServiceGenerator.createService(context, CampusInformationClient.class);
    }

    public static CampusInformationImpl getInstance(Context context) {

        if (sCampusInformationImple == null) {
            sCampusInformationImple = new CampusInformationImpl(context);
        }
        return sCampusInformationImple;
    }
    
    @Override
    public void fetchCampusInformationList(@NonNull LoadCampusInformationListCallback callback) {
        
    }
}
