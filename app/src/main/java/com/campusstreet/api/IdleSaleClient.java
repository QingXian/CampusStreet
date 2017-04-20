package com.campusstreet.api;


import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Orange on 2017/4/19.
 */

public interface IdleSaleClient {



    @POST("get_ewu_goods")
    Call<JsonObject> getIdleSale(@Query("type") Integer type, @Query("pi") Integer pi);
}
