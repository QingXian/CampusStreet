package com.campusstreet.model;

import android.content.Context;
import android.support.annotation.NonNull;

/**
 * Created by Orange on 2017/4/18.
 */

public class CampusRecruitmentImpl implements ICampusRecruitmentBiz{

    private final String TAG = this.getClass().getSimpleName();
    private static CampusRecruitmentImpl sCampusRecruitmentImple;



    private CampusRecruitmentImpl(Context context) {
    }

    public static CampusRecruitmentImpl getInstance(Context context) {

        if (sCampusRecruitmentImple == null) {
            sCampusRecruitmentImple = new CampusRecruitmentImpl(context);
        }
        return sCampusRecruitmentImple;
    }
    @Override
    public void fetchCampusRecruitmentList(@NonNull LoadCampusRecruitmentListCallback callback) {

    }

    @Override
    public void searchCampusRecruitment(String picType, @NonNull searchCampusRecruitmentCallback callback) {

    }
}
