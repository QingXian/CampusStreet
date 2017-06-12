package com.campusstreet.presenter;

import com.campusstreet.contract.ICampusRecruitmentContract;
import com.campusstreet.contract.ICampusRecruitmentContract;
import com.campusstreet.entity.NewInfo;
import com.campusstreet.entity.RecruitInfo;
import com.campusstreet.entity.StudyWorkInfo;
import com.campusstreet.model.CampusRecruitmentImpl;
import com.campusstreet.model.ICampusInformationBiz;
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
        mCampusRecruitmentImpl.fetchCampusRecruitmentList(key, pi, new ICampusRecruitmentBiz.LoadCampusRecruitmentListCallback() {
            @Override
            public void onCampusRecruitmentListLoaded(List<RecruitInfo> recruitInfos) {
                mView.setCampusRecruitmentList(recruitInfos);
            }

            @Override
            public void onDataNotAvailable(String errorMsg) {
                mView.showErrorMsg(errorMsg);
            }
        });
    }

    @Override
    public void fetchStudyWorkList(String key, int pi) {
        mCampusRecruitmentImpl.fetchStudyWorkList(key, pi, new ICampusRecruitmentBiz.LoadStudyWorkListCallback() {
            @Override
            public void onStudyWorkListLoaded(List<StudyWorkInfo> studyWorkInfos) {
                mView.setStudyWorkList(studyWorkInfos);
            }

            @Override
            public void onDataNotAvailable(String errorMsg) {
                mView.showErrorMsg(errorMsg);
            }
        });
    }

    @Override
    public void fetchCampusRecruitmentDetail(int swid) {
        mView.setLoadingIndicator(true);
        mCampusRecruitmentImpl.fetchCampusRecruitmentDetail(swid, new ICampusRecruitmentBiz.LoadCampusRecruitmentDetailCallback() {
            @Override
            public void onCampusRecruitmentDetailLoaded(RecruitInfo recruitInfo) {
                mView.setLoadingIndicator(false);
                mView.setCampusRecruitmentDetail(recruitInfo);
            }

            @Override
            public void onDataNotAvailable(String errorMsg) {
                mView.setLoadingIndicator(false);
                mView.showErrorMsg(errorMsg);
            }
        });
    }

    @Override
    public void fetchStudyWorkDetail(int rid) {
        mView.setLoadingIndicator(true);
        mCampusRecruitmentImpl.fetchStudyWorkDetail(rid, new ICampusRecruitmentBiz.LoadStudyWorkDetailCallback() {
            @Override
            public void onStudyWorkDetailLoaded(StudyWorkInfo studyWorkInfo) {
                mView.setLoadingIndicator(false);
                mView.setStudyWorkDetail(studyWorkInfo);
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
