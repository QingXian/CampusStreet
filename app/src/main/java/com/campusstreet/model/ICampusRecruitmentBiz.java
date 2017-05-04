package com.campusstreet.model;

import android.support.annotation.NonNull;

import com.campusstreet.entity.RecruitInfo;
import com.campusstreet.entity.StudyWorkInfo;

import java.util.List;

/**
 * Created by Orange on 2017/4/18.
 */

public interface ICampusRecruitmentBiz {

    void fetchCampusRecruitmentList(String key,int pi,@NonNull LoadCampusRecruitmentListCallback callback);

    void fetchStudyWorkList(String key,int pi, @NonNull LoadStudyWorkListCallback callback);



    interface LoadStudyWorkListCallback {

        void onStudyWorkListLoaded(List<StudyWorkInfo> studyWorkInfos);

        void onDataNotAvailable(String errorMsg);
    }

    interface LoadCampusRecruitmentListCallback {

        void onCampusRecruitmentListLoaded(List<RecruitInfo> recruitInfos);

        void onDataNotAvailable(String errorMsg);
    }
}
