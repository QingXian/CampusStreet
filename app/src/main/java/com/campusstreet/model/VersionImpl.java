package com.campusstreet.model;

import android.content.Context;
import android.support.annotation.NonNull;

import com.campusstreet.api.ServiceGenerator;
import com.campusstreet.api.VersionClient;
import com.campusstreet.common.Const;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Orange on 2017/6/13.
 */

public class VersionImpl implements IVersionBiz {
    private VersionClient mVersionClient;
    private String mLocalVersion;

    public VersionImpl(Context context, String localVersion) {
        mVersionClient = ServiceGenerator.createService(context, VersionClient.class);
        mLocalVersion = localVersion;
    }

    @Override
    public void getVersion(@NonNull final LoadVersionCallback callback) {
        Call<JsonObject> call = mVersionClient.getVersion();
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject bodyJson = response.body();
                if (bodyJson != null) {
                    int res = bodyJson.get(Const.RES_KEY).getAsInt();
                    if (res == 1) {
                        String result = bodyJson.get(Const.MESSAGE_KEY).getAsString();
                        String[] resultarray = result.split("\\|");
                        if (!mLocalVersion.equals(resultarray[0])) {
                            callback.onVersionLoad(resultarray[1]);
                        } else {
                            callback.onDataNotAvailable();

                        }
                    } else {
                        callback.onDataNotAvailable();
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                callback.onDataNotAvailable();
            }
        });
    }
}
