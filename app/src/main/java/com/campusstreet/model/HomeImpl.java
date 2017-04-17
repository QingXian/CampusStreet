package com.campusstreet.model;

import android.content.Context;
import android.support.annotation.NonNull;

/**
 * Created by Orange on 2017/4/16.
 */

public class HomeImpl implements IHomeBiz{

    private final String TAG = this.getClass().getSimpleName();
    private static HomeImpl sHomeImple;



    private HomeImpl(Context context) {
    }

    public static HomeImpl getInstance(Context context) {

        if (sHomeImple == null) {
            sHomeImple = new HomeImpl(context);
        }
        return sHomeImple;
    }

    @Override
    public void fetchBannerImage(String picType, @NonNull GetBannerCallback callback) {
    }

    @Override
    public void fetchAdImage(String picType, @NonNull GetAdImageCallback callback) {

    }

    @Override
    public void getdynamicList(@NonNull LoaddynamicListCallback callback) {

    }
}
