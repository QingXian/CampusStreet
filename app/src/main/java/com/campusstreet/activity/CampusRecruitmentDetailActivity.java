package com.campusstreet.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.campusstreet.R;
import com.campusstreet.common.Const;
import com.campusstreet.entity.RecruitInfo;
import com.campusstreet.entity.StudyWorkInfo;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Orange on 2017/4/7.
 */

public class CampusRecruitmentDetailActivity extends AppCompatActivity {
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.iv_toolbar_right)
    ImageView mIvToolbarRight;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_wages)
    TextView mTvWages;
    @BindView(R.id.tv_feedback)
    TextView mTvFeedback;
    @BindView(R.id.tv_place)
    TextView mTvPlace;
    @BindView(R.id.tv_education)
    TextView mTvEducation;
    @BindView(R.id.iv_head)
    ImageView mIvHead;
    @BindView(R.id.tv_company_name)
    TextView mTvCompanyName;
    @BindView(R.id.tv_company_range)
    TextView mTvCompanyRange;
    @BindView(R.id.tv_company_num)
    TextView mTvCompanyNum;
    @BindView(R.id.tv_company_type)

            
    TextView mTvCompanyType;
    @BindView(R.id.ll_company_detail)
    LinearLayout mLlCompanyDetail;
    @BindView(R.id.tv_position)
    TextView mTvPosition;
    @BindView(R.id.rl_company_info)
    RelativeLayout mRlCompanyInfo;
    @BindView(R.id.tv_duty)
    TextView mTvDuty;
    @BindView(R.id.tv_req)
    TextView mTvReq;
    @BindView(R.id.tv_company_phone)
    TextView mTvCompanyPhone;
    @BindView(R.id.tv_time)
    TextView mTvTime;
    @BindView(R.id.tv_duty_hint)
    TextView mTvDutyHint;
    @BindView(R.id.tv_studywork_range)
    TextView mTvStudyworkRange;
    @BindView(R.id.tv_studywork_type)
    TextView mTvStudyworkType;
    @BindView(R.id.tv_studywork_phone)
    TextView mTvStudyworkPhone;
    @BindView(R.id.ll_studywork_detail)
    LinearLayout mLlStudyworkDetail;
    private RecruitInfo mRecruitInfo;
    private StudyWorkInfo mStudyWorkInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campus_recruitment_detail);
        ButterKnife.bind(this);
        mToolbar.setTitle("");
        mToolbarTitle.setText(getString(R.string.act_detail));
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mRecruitInfo = (RecruitInfo) getIntent().getSerializableExtra(Const.RECRUITMENTNFO_EXTRA);
        mStudyWorkInfo = (StudyWorkInfo) getIntent().getSerializableExtra(Const.STUDYWORKINFO_EXTRA);
        initView();
    }

    private void initView() {
        if (mRecruitInfo != null) {
            showRecriutDetail();
            mTvTitle.setText(mRecruitInfo.getJobtitle());
            mTvPlace.setText(mRecruitInfo.getJobplace());
            mTvEducation.setText(mRecruitInfo.getJobeduname());
            mTvWages.setText(mRecruitInfo.getJobmoney());
            mTvCompanyName.setText(mRecruitInfo.getComname());
            mTvCompanyRange.setText(mRecruitInfo.getComindustryname());
            mTvCompanyType.setText(mRecruitInfo.getComtypename());
            mTvDuty.setText(mRecruitInfo.getJobddes());
            mTvReq.setText(mRecruitInfo.getJobreq());
            mTvCompanyPhone.setText(mRecruitInfo.getComphone());
            mTvTime.setText(mRecruitInfo.getPublishtime());
        } else {
            showStudyWorkDetail();
            mTvTitle.setText(mStudyWorkInfo.getTitle());
            mTvPlace.setText(mStudyWorkInfo.getJobplace());
            mTvWages.setText(mStudyWorkInfo.getJobmoney());
            mTvStudyworkType.setText(mStudyWorkInfo.getJobcom());
            mTvStudyworkRange.setText(mStudyWorkInfo.getJobtime());
            mTvStudyworkPhone.setText(mStudyWorkInfo.getPhone());
            mTvReq.setText(mStudyWorkInfo.getJobreq());
            mTvTime.setText(mStudyWorkInfo.getPublishtime());
            mTvDuty.setText(mStudyWorkInfo.getJobperson());
            mTvDutyHint.setText("需要人数");
            showStudyWorkDetail();
        }
    }

    private void showRecriutDetail() {
        mTvEducation.setVisibility(View.VISIBLE);
        mRlCompanyInfo.setVisibility(View.VISIBLE);
        mLlStudyworkDetail.setVisibility(View.GONE);
    }

    private void showStudyWorkDetail() {
        mTvEducation.setVisibility(View.GONE);
        mRlCompanyInfo.setVisibility(View.GONE);
        mLlStudyworkDetail.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.rl_company_info)
    public void onClick() {
    }
}
