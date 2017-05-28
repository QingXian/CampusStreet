package com.campusstreet.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.campusstreet.R;
import com.campusstreet.common.Const;
import com.campusstreet.contract.ICampusRecruitmentContract;
import com.campusstreet.entity.RecruitInfo;
import com.campusstreet.entity.StudyWorkInfo;
import com.campusstreet.model.CampusRecruitmentImpl;
import com.campusstreet.presenter.CampusRecruitmentPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.campusstreet.common.Const.ID_EXTRA;
import static com.campusstreet.common.Const.TID_EXTRA;
import static com.campusstreet.common.Const.TYPE;
import static com.campusstreet.utils.DataUtil.getTimeRange;

/**
 * Created by Orange on 2017/4/7.
 */

public class CampusRecruitmentDetailActivity extends AppCompatActivity implements ICampusRecruitmentContract.View {
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
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.progress_bar_title)
    TextView mProgressBarTitle;
    @BindView(R.id.progress_bar_container)
    LinearLayout mProgressBarContainer;
    private RecruitInfo mRecruitInfo;
    private StudyWorkInfo mStudyWorkInfo;
    private ICampusRecruitmentContract.Presenter mPresenter;
    private int mId;
    private int mType;

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
        new CampusRecruitmentPresenter(CampusRecruitmentImpl.getInstance(getApplicationContext()), this);
        mRecruitInfo = (RecruitInfo) getIntent().getSerializableExtra(Const.RECRUITMENTNFO_EXTRA);
        mStudyWorkInfo = (StudyWorkInfo) getIntent().getSerializableExtra(Const.STUDYWORKINFO_EXTRA);
        mId = getIntent().getIntExtra(ID_EXTRA, 0);
        mType = getIntent().getIntExtra(TYPE, 0);
        if (mType == 1) {
            mPresenter.fetchCampusRecruitmentDetail(mId);
        }
        if (mType == 2) {
            mPresenter.fetchStudyWorkDetail(mId);
        } else {
            initView();
        }
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
            String time = getTimeRange(mRecruitInfo.getPublishtime());
            mTvTime.setText(time);
        } else {
            showStudyWorkDetail();
            mTvTitle.setText(mStudyWorkInfo.getTitle());
            mTvPlace.setText(mStudyWorkInfo.getJobplace());
            mTvWages.setText(mStudyWorkInfo.getJobmoney());
            mTvStudyworkType.setText(mStudyWorkInfo.getJobcom());
            mTvStudyworkRange.setText(mStudyWorkInfo.getJobtime());
            mTvStudyworkPhone.setText(mStudyWorkInfo.getPhone());
            mTvReq.setText(mStudyWorkInfo.getJobreq());
            String time = getTimeRange(mStudyWorkInfo.getPublishtime());
            mTvTime.setText(time);
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

    @Override
    public void setPresenter(ICampusRecruitmentContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void setCampusRecruitmentList(List<RecruitInfo> recruitInfos) {
    }

    @Override
    public void setStudyWorkList(List<StudyWorkInfo> studyWorkInfos) {

    }

    @Override
    public void showErrorMsg(String errorMsg) {
        showMessage(errorMsg);
    }

    @Override
    public void setCampusRecruitmentDetail(RecruitInfo recruitInfo) {
        showRecriutDetail();
        mTvTitle.setText(recruitInfo.getJobtitle());
        mTvPlace.setText(recruitInfo.getJobplace());
        mTvEducation.setText(recruitInfo.getJobeduname());
        mTvWages.setText(recruitInfo.getJobmoney());
        mTvCompanyName.setText(recruitInfo.getComname());
        mTvCompanyRange.setText(recruitInfo.getComindustryname());
        mTvCompanyType.setText(recruitInfo.getComtypename());
        mTvDuty.setText(recruitInfo.getJobddes());
        mTvReq.setText(recruitInfo.getJobreq());
        mTvCompanyPhone.setText(recruitInfo.getComphone());
        String time = getTimeRange(recruitInfo.getPublishtime());
        mTvTime.setText(time);
    }

    @Override
    public void setStudyWorkDetail(StudyWorkInfo studyWorkInfo) {
        showStudyWorkDetail();
        mTvTitle.setText(studyWorkInfo.getTitle());
        mTvPlace.setText(studyWorkInfo.getJobplace());
        mTvWages.setText(studyWorkInfo.getJobmoney());
        mTvStudyworkType.setText(studyWorkInfo.getJobcom());
        mTvStudyworkRange.setText(studyWorkInfo.getJobtime());
        mTvStudyworkPhone.setText(studyWorkInfo.getPhone());
        mTvReq.setText(studyWorkInfo.getJobreq());
        String time = getTimeRange(studyWorkInfo.getPublishtime());
        mTvTime.setText(time);
        mTvDuty.setText(studyWorkInfo.getJobperson());
        mTvDutyHint.setText("需要人数");
        showStudyWorkDetail();
    }

    @Override
    public void setLoadingIndicator(boolean active) {
        if (mProgressBarContainer != null) {
            if (active) {
                //设置滚动条可见
                mProgressBarContainer.setVisibility(View.VISIBLE);
                mProgressBarTitle.setText(R.string.loading_progress_bar_title);
            } else {
                if (mProgressBarContainer.getVisibility() == View.VISIBLE) {
                    mProgressBarContainer.setVisibility(View.GONE);
                }
            }
        }
    }

    protected void showMessage(String msg) {
        if (this != null && !this.isFinishing()) {
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        }
    }
}
