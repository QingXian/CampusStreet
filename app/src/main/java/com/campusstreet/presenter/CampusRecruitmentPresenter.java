package com.campusstreet.presenter;

import com.campusstreet.contract.ICampusRecruitmentContract;
import com.campusstreet.contract.ICampusRecruitmentContract;
import com.campusstreet.entity.RecruitInfo;
import com.campusstreet.entity.StudyWorkInfo;
import com.campusstreet.model.CampusRecruitmentImpl;
import com.campusstreet.model.ICampusRecruitmentBiz;

import java.util.List;

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
    public void fetchCampusRecruitmentList(String key, int pi) {
        mView.setLoadingIndicator(true);
        mCampusRecruitmentImpl.fetchCampusRecruitmentList(key, pi, new ICampusRecruitmentBiz.LoadCampusRecruitmentListCallback() {
            @Override
            public void onCampusRecruitmentListLoaded(List<RecruitInfo> recruitInfos) {
                mView.setCampusRecruitmentList(recruitInfos);
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
    public void fetchStudyWorkList(String key, int pi) {
        mView.setLoadingIndicator(true);
        mCampusRecruitmentImpl.fetchStudyWorkList(key, pi, new ICampusRecruitmentBiz.LoadStudyWorkListCallback() {
            @Override
            public void onStudyWorkListLoaded(List<StudyWorkInfo> studyWorkInfos) {
                mView.setStudyWorkList(studyWorkInfos);
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
