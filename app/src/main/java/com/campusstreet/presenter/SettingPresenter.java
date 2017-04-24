package com.campusstreet.presenter;

import android.util.Log;

import com.campusstreet.contract.ISettingContract;
import com.campusstreet.contract.ISettingContract;
import com.campusstreet.model.ISettingBiz;
import com.campusstreet.model.SettingImpl;

import java.util.HashMap;
import java.util.Map;

import okhttp3.MultipartBody;

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
    public void reviseHead(String uid, MultipartBody.Part avatar) {
        Map<String, Object> params = new HashMap<>();
        params.put("uid", uid);
        params.put("a",avatar);
        mView.setLoadingIndicator(true);
        mSettingImpl.avatarRevise(params, new ISettingBiz.AvatarReviseCallback() {
            @Override
            public void onAvatarReviseSuccess() {
                mView.showSuccessMsg("更改头像成功");
                mView.setLoadingIndicator(false);
            }

            @Override
            public void onAvatarReviseFailure(String errorMsg) {
                mView.showErrorMsg(errorMsg);
                mView.setLoadingIndicator(false);
            }
        });
    }

    @Override
    public void reviseNickName(String uid, String nickName) {
        Map<String, Object> params = new HashMap<>();
        params.put("uid", uid);
        params.put("nick",nickName);
        mView.setLoadingIndicator(true);
        mSettingImpl.userInfoRevise(params, new ISettingBiz.UserInfoReviseCallback() {
            @Override
            public void onUserInfoReviseSuccess() {
                mView.showSuccessMsg("更改昵称成功");
                mView.setLoadingIndicator(false);
            }

            @Override
            public void onUserInfoReviseFailure(String errorMsg) {
                mView.showErrorMsg(errorMsg);
                mView.setLoadingIndicator(false);
            }
        });

    }

    @Override
    public void reviseDepartment(String uid, String department) {
        Map<String, Object> params = new HashMap<>();
            params.put("uid", uid);
            params.put("major",department);
            mView.setLoadingIndicator(true);
        mSettingImpl.userInfoRevise(params, new ISettingBiz.UserInfoReviseCallback() {
            @Override
            public void onUserInfoReviseSuccess() {
                mView.showSuccessMsg("更改院系成功");
                mView.setLoadingIndicator(false);
            }

            @Override
            public void onUserInfoReviseFailure(String errorMsg) {
                mView.showErrorMsg(errorMsg);
                mView.setLoadingIndicator(false);
            }
        });
    }
}
