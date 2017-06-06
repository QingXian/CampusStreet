package com.campusstreet.model;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.campusstreet.api.BountyHallClient;
import com.campusstreet.api.LiveClient;
import com.campusstreet.api.ServiceGenerator;
import com.campusstreet.common.Const;
import com.campusstreet.entity.BannerInfo;
import com.campusstreet.entity.HomeDynamicInfo;
import com.campusstreet.entity.LiveInfo;
import com.campusstreet.entity.LiveReplyInfo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.campusstreet.common.Const.MULTIPART_FORM_DATA;

/**
 * Created by Orange on 2017/4/16.
 */

public class FindImpl implements IFindBiz {

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
        Call<JsonObject> call = mLiveClient.getLive(pi);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject bodyJson = response.body();
                if (bodyJson != null) {
                    int res = bodyJson.get(Const.RES_KEY).getAsInt();
                    if (res == 1) {
                        if (bodyJson.get(Const.TOTAL_KEY).getAsInt() != 0) {
                            JsonArray resultJsons = bodyJson.get(Const.DATA_KEY).getAsJsonArray();
                            Gson gson = new GsonBuilder().setLenient().create();
                            List<LiveInfo> liveInfos = new ArrayList<>();
                            for (int i = 0; i < resultJsons.size(); i++) {
                                JsonObject json = resultJsons.get(i).getAsJsonObject();
                                LiveInfo liveInfo = gson.fromJson(json, LiveInfo.class);
                                liveInfos.add(liveInfo);
                            }
                            callback.onFindListLoaded(liveInfos);
                        } else {
                            callback.onDataNotAvailable("暂时没有数据");
                        }

                    } else {
                        callback.onDataNotAvailable(bodyJson.get(Const.MESSAGE_KEY).getAsString());
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                callback.onDataNotAvailable("网络异常");
                Log.d(TAG, "onFailure: " + t);
            }
        });
    }

    @Override
    public void fetchTopImage(String picType, @NonNull GetTopImageCallback callback) {

    }

    @Override
    public void addLive(LiveInfo liveInfo, @NonNull final AddLiveCallback callback) {
        Map<String, RequestBody> requestBodyMap = new HashMap<>();
        MultipartBody.Part[] files = new MultipartBody.Part[0];
        if (liveInfo.getFiles() != null && liveInfo.getFiles().size() > 0) {
            files = new MultipartBody.Part[liveInfo.getFiles().size()];
            RequestBody requestFile;
            Iterator<File> iterator = liveInfo.getFiles().iterator();
            int i = 0;
            while (iterator.hasNext()) {
                File file = iterator.next();
                // 创建 RequestBody，用于封装 请求RequestBody
                requestFile = RequestBody.create(MediaType.parse("application/otcet-stream"), file);
                //  MultipartBody.Part is used to send also the actual file name
                files[i] = MultipartBody.Part.createFormData("images", file.getName(), requestFile);

                i++;
            }
        }


        //添加请求参数
        requestBodyMap.put("uid", RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA), String.valueOf(liveInfo.getUid())));
        requestBodyMap.put("con", RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA),
                liveInfo.getCon()));
        Call<JsonObject> call = mLiveClient.pushLive(requestBodyMap, files);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject bodyJson = response.body();
                if (bodyJson != null) {
                    int res = bodyJson.get(Const.RES_KEY).getAsInt();
                    if (res == 1) {
                        callback.onAddSuccess();

                    } else {
                        callback.onAddFailure(bodyJson.get(Const.MESSAGE_KEY).getAsString());
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                callback.onAddFailure("网络异常");
                Log.d(TAG, "onFailure: " + t);
            }
        });
    }

    @Override
    public void fetchLiveReplyList(int did, int pi, @NonNull final LoadLiveReplyListCallback callback) {
        Call<JsonObject> call = mLiveClient.getLivereply(did,pi);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject bodyJson = response.body();
                if (bodyJson != null) {
                    int res = bodyJson.get(Const.RES_KEY).getAsInt();
                    if (res == 1) {
                        if (bodyJson.get(Const.TOTAL_KEY).getAsInt() != 0) {
                            JsonArray resultJsons = bodyJson.get(Const.DATA_KEY).getAsJsonArray();
                            Gson gson = new GsonBuilder().setLenient().create();
                            List<LiveReplyInfo> liveReplyInfos = new ArrayList<>();
                            for (int i = 0; i < resultJsons.size(); i++) {
                                JsonObject json = resultJsons.get(i).getAsJsonObject();
                                LiveReplyInfo liveReplyInfo = gson.fromJson(json, LiveReplyInfo.class);
                                liveReplyInfos.add(liveReplyInfo);
                            }
                            callback.onFindListLoaded(liveReplyInfos);
                        } else {
                            callback.onDataNotAvailable("暂时没有人留言");
                        }

                    } else {
                        callback.onDataNotAvailable(bodyJson.get(Const.MESSAGE_KEY).getAsString());
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                callback.onDataNotAvailable("网络异常");
                Log.d(TAG, "onFailure: " + t);
            }
        });
    }

    @Override
    public void onReplyLive(String uid, int did, String con, @NonNull final onReplyLiveCallback callback) {
        Call<JsonObject> call = mLiveClient.replyLive(uid, did, con);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject bodyJson = response.body();
                if (bodyJson != null) {
                    int res = bodyJson.get(Const.RES_KEY).getAsInt();
                    if (res == 1) {
                        callback.onReplyLiveSuccess();
                    } else {
                        callback.onReplyLiveFailure(bodyJson.get(Const.MESSAGE_KEY).getAsString());
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                callback.onReplyLiveFailure("网络异常");
                Log.d(TAG, "onFailure: " + t);
            }
        });
    }

    @Override
    public void onDeleteLive(String uid, int did, @NonNull final onDeleteLiveCallback callback) {
        Call<JsonObject> call = mLiveClient.deleteLive(uid, did);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject bodyJson = response.body();
                if (bodyJson != null) {
                    int res = bodyJson.get(Const.RES_KEY).getAsInt();
                    if (res == 1) {
                        callback.onDeleteLiveSuccess();
                    } else {
                        callback.onDeleteLiveFailure(bodyJson.get(Const.MESSAGE_KEY).getAsString());
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                callback.onDeleteLiveFailure("网络异常");
                Log.d(TAG, "onFailure: " + t);
            }
        });
    }
}
