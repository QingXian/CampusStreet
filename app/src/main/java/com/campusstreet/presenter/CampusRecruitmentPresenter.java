package com.campusstreet.presenter;

import com.campusstreet.contract.ICampusRecruitmentContract;
import com.campusstreet.contract.ICampusRecruitmentContract;
import com.campusstreet.model.CampusRecruitmentImpl;

/**
 * Created by Orange on 2017/4/18.
 */

public class CampusRecruitmentPresenter implements ICampusRecruitmentContract.Presenter {
    
    public static final String TAG = "CampusRecruitmentPresenter";

    private CampusRecruitmentImpl mCampusRecruitmentImpl;
    private ICampusRecruitmentContract.View mView;



    public CampusRecruitmentPresenter(CampusRecruitmentImpl CampusRecruitmentImpl, ICampusRecruitmentContract.View view) {
        mCampusRecruitmentImpl = CampusRecruitmentImpl;
        mView = view;

        mView.setPresenter(this);
    }

    @Override
    public void fetchCampusRecruitmentList(int type, int pi) {
    }

    @Override
    public void searchRecruit(String keyWord) {

    }
}
