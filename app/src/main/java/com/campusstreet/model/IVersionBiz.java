package com.campusstreet.model;

import android.support.annotation.NonNull;

/**
 * Created by Orange on 2017/6/13.
 */

public interface IVersionBiz {

    void getVersion(@NonNull LoadVersionCallback callback);

    interface LoadVersionCallback {

        void onVersionLoad(String appUrl);

        void onDataNotAvailable();
    }
}
