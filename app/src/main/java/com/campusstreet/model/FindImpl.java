package com.campusstreet.model;

import android.content.Context;
import android.support.annotation.NonNull;

/**
 * Created by Orange on 2017/4/16.
 */

public class FindImpl implements IFindBiz{

    private final String TAG = this.getClass().getSimpleName();
    private static FindImpl sFindImple;



    private FindImpl(Context context) {
    }

    public static FindImpl getInstance(Context context) {

        if (sFindImple == null) {
            sFindImple = new FindImpl(context);
        }
        return sFindImple;
    }

    @Override
    public void fetchFindList(@NonNull LoadFindListCallback callback) {

    }

    @Override
    public void fetchTopImage(String picType, @NonNull GetTopImageCallback callback) {

    }

    @Override
    public void addLive(@NonNull addLiveCallback callback) {

    }
}
