package com.campusstreet.presenter;

import android.util.Log;

import com.campusstreet.contract.IFindContract;
import com.campusstreet.contract.IFindContract;
import com.campusstreet.model.FindImpl;

/**
 * Created by Orange on 2017/4/17.
 */

public class FindPresenter implements IFindContract.Presenter {
    
    public static final String TAG = "FindPresenter";

    private FindImpl mFindImpl;
    private IFindContract.View mView;



    public FindPresenter(FindImpl homeImpl, IFindContract.View view) {
        mFindImpl = homeImpl;
        mView = view;

        mView.setPresenter(this);
    }
    @Override
    public void start() {
        Log.d(TAG, "start: 开始");
    }

    @Override
    public void fetchFindList() {

    }

    @Override
    public void fetchTopImge() {

    }

    @Override
    public void pushLive() {

    }
}
