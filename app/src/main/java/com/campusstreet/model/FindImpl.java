package com.campusstreet.model;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.campusstreet.api.BountyHallClient;
import com.campusstreet.api.LiveClient;
import com.campusstreet.api.ServiceGenerator;
import com.campusstreet.common.Const;
import com.campusstreet.entity.HomeDynamicInfo;
import com.campusstreet.entity.LiveInfo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Orange on 2017/4/16.
 */

public class FindImpl implements IFindBiz{

    private final String TAG = this.getClass().getSimpleName();
    private static FindImpl sFindImple;
    private LiveClient mLiveClient;



    private FindImpl(Context context) {
        mLiveClient = ServiceGenerator.createService(context, LiveClient.class);
    }

    public static FindImpl getInstance(Context context) {

        if (sFindImple == null) {
            sFindImple = new FindImpl(context);
        }
        return sFindImple;
    }


    @Override
    public void fetchFindList(int pi, @NonNull final LoadFindListCallback callback) {

    }

    @Override
    public void fetchTopImage(String picType, @NonNull GetTopImageCallback callback) {

    }

    @Override
    public void addLive(LiveInfo liveInfo, @NonNull AddLiveCallback callback) {

    }
}
