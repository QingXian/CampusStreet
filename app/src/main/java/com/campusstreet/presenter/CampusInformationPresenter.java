package com.campusstreet.presenter;

import com.campusstreet.contract.ICampusInformationContract;
import com.campusstreet.contract.ICampusInformationContract;
import com.campusstreet.model.CampusInformationImpl;
import com.campusstreet.model.ICampusInformationBiz;

/**
 * Created by Orange on 2017/4/24.
 */

public class CampusInformationPresenter implements ICampusInformationContract.Presenter {
    
    public static final String TAG = "CampusInformationPresenter";

    private CampusInformationImpl mCampusInformationImpl;
    private ICampusInformationContract.View mView;


    public CampusInformationPresenter(CampusInformationImpl CampusInformationImpl, ICampusInformationContract.View view) {
        mCampusInformationImpl = CampusInformationImpl;
        mView = view;

        mView.setPresenter(this);
    }
    @Override
    public void fetchCampusInformationList(int pi) {
        
    }
}
