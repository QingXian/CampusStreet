package com.campusstreet.presenter;

import com.campusstreet.contract.ICampusInformationContract;
import com.campusstreet.contract.ICampusInformationContract;
import com.campusstreet.entity.NewInfo;
import com.campusstreet.model.CampusInformationImpl;
import com.campusstreet.model.ICampusInformationBiz;

import java.util.List;

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
    public void fetchCampusInformationList(String key, int pi) {
        mCampusInformationImpl.fetchCampusInformationList(key, pi, new ICampusInformationBiz.LoadCampusInformationListCallback() {
            @Override
            public void onCampusInformationListLoaded(List<NewInfo> newInfos) {
                mView.setLoadingIndicator(false);
                mView.setCampusInformationList(newInfos);
            }

            @Override
            public void onDataNotAvailable(String errorMsg) {
                mView.setLoadingIndicator(false);
                mView.showErrorMsg(errorMsg);
            }
        });
    }

    @Override
    public void fetchCampusInformationDetail(int nid) {
        mView.setLoadingIndicator(true);
        mCampusInformationImpl.fetchCampusInformationDetail(nid, new ICampusInformationBiz.LoadCampusInformationDetailCallback() {
            @Override
            public void onCampusInformationDetailLoaded(NewInfo newInfo) {
                mView.setLoadingIndicator(false);
                mView.setCampusInformationDetail(newInfo);
            }

            @Override
            public void onDataNotAvailable(String errorMsg) {
                if (!errorMsg.equals("")) {
                    mView.showErrorMsg(errorMsg);
                }
                mView.setLoadingIndicator(false);
            }
        });
    }
}
