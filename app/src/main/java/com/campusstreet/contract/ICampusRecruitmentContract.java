package com.campusstreet.contract;

import com.campusstreet.entity.RecruitInfo;
import com.campusstreet.entity.StudyWorkInfo;

import java.util.List;

/**
 * Created by Orange on 2017/4/15.
 */

public interface ICampusRecruitmentContract {

    interface Presenter extends BasePresenter {

        void fetchCampusRecruitmentList(String key,int pi);

        void fetchStudyWorkList(String key,int pi);

    }

    interface View extends BaseView<Presenter> {


        void setCampusRecruitmentList(List<RecruitInfo> recruitInfos);

        void setStudyWorkList(List<StudyWorkInfo> studyWorkInfos);

        void showErrorMsg(String errorMsg);

        /**
         * 设置是否加载指示器
         *
         * @param active true表示显示，false不显示
         */
        void setLoadingIndicator(boolean active);

    }
}
