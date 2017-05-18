package com.campusstreet.presenter;

import com.campusstreet.contract.IPartnerContract;
import com.campusstreet.contract.IPartnerContract;
import com.campusstreet.entity.PartnerInfo;
import com.campusstreet.model.IPartnerBiz;
import com.campusstreet.model.PartnerImpl;

import java.util.List;

/**
 * Created by Orange on 2017/4/24.
 */

public class PartnerPresenter implements IPartnerContract.Presenter {
    
    public static final String TAG = "PartnerPresenter";

    private PartnerImpl mPartnerImpl;
    private IPartnerContract.View mView;


    public PartnerPresenter(PartnerImpl PartnerImpl, IPartnerContract.View view) {
        mPartnerImpl = PartnerImpl;
        mView = view;

        mView.setPresenter(this);
    }

    @Override
    public void fetchPartnerList(String key, int type, int pi) {
        mPartnerImpl.fetchPartnerList(key, type, pi, new IPartnerBiz.LoadPartnerListCallback() {
            @Override
            public void onPartnerListLoaded(List<PartnerInfo> partnerInfos) {
                mView.setPartnerList(partnerInfos);
                mView.setLoadingIndicator(false);
            }

            @Override
            public void onDataNotAvailable(String errorMsg) {
                mView.showErrorMsg(errorMsg);
                mView.setLoadingIndicator(false);
            }
        });
    }

    @Override
    public void fetchPartnerCategories() {
        mPartnerImpl.fetchPartnerCategories(new IPartnerBiz.LoadPartnerCategoriesCallback() {
            @Override
            public void onPartnerCategoriesLoaded(String[] type) {
                mView.setPartnerCategories(type);
                mView.setLoadingIndicator(true);
            }

            @Override
            public void onDataNotAvailable(String errorMsg) {
                mView.showErrorMsg(errorMsg);
                mView.setLoadingIndicator(false);
            }
        });
    }

    @Override
    public void fetchPartnerDetail(int pid) {
        mView.setLoadingIndicator(true);
        mPartnerImpl.fetchPartnerDetail(pid, new IPartnerBiz.LoadPartnerDetailCallback() {
            @Override
            public void onPartnerDetailLoaded(PartnerInfo partnerInfo) {
                mView.setPartnerDetail(partnerInfo);
                mView.setLoadingIndicator(false);
            }

            @Override
            public void onDataNotAvailable(String errorMsg) {
                mView.showErrorMsg(errorMsg);
                mView.setLoadingIndicator(false);
            }
        });
    }
}
