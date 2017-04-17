package com.campusstreet.presenter;

import android.util.Log;

import com.campusstreet.contract.IHomeContract;
import com.campusstreet.model.HomeImpl;

/**
 * Created by Orange on 2017/4/17.
 */

public class HomePresenter implements IHomeContract.Presenter {

    public static final String TAG = "HomePresenter";

    private HomeImpl mHomeImpl;
    private IHomeContract.View mView;



    public HomePresenter(HomeImpl homeImpl, IHomeContract.View view) {
        mHomeImpl = homeImpl;
        mView = view;

        mView.setPresenter(this);
    }


    @Override
    public void start() {
        Log.d(TAG, "start: 开始");
    }

    @Override
    public void fetchBanner() {

    }

    @Override
    public void fetchAdvertisement() {

    }

    @Override
    public void fetchdynamicList() {

    }
}
