package com.campusstreet.presenter;

import android.util.Log;

import com.campusstreet.contract.ISettingContract;
import com.campusstreet.contract.ISettingContract;
import com.campusstreet.model.SettingImpl;

/**
 * Created by Orange on 2017/4/18.
 */

public class SettingPresenter implements ISettingContract.Presenter {

    public static final String TAG = "SettingPresenter";

    private SettingImpl mSettingImpl;
    private ISettingContract.View mView;



    public SettingPresenter(SettingImpl settingImpl, ISettingContract.View view) {
        mSettingImpl = settingImpl;
        mView = view;

        mView.setPresenter(this);
    }

    @Override
    public void reviseHead() {

    }

    @Override
    public void reviseNickName() {
        Log.d(TAG, "reviseNickName: +++++++");
    }

    @Override
    public void reviseDepartment() {

    }
}
