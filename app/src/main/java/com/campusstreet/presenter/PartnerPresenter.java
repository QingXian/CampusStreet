package com.campusstreet.presenter;

import com.campusstreet.contract.IPartnerContract;
import com.campusstreet.contract.IPartnerContract;
import com.campusstreet.model.PartnerImpl;

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
    public void fetchPartnerList(int type, int pi) {
        
    }

    @Override
    public void search(String keyWord) {

    }

    @Override
    public void fetchPartnerCategories() {

    }
}
