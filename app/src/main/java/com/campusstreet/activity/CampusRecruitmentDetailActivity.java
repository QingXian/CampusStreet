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
import static com.campusstreet.common.Const.TYPE;
import static com.campusstreet.utils.DataUtil.getTimeRange;

/**
 * Created by Orange on 2017/4/7.
 */

public class CampusRecruitmentDetailActivity extends BaseActivity implements ICampusRecruitmentContract.View {
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
    @BindView(R.id.tv_money_tag)
    TextView mTvMoneyTag;
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
    RelativeLayout mLlCompanyDetail;
    @BindView(R.id.tv_position)
    TextView mTvPosition;
    @BindView(R.id.rl_company_info)
    RelativeLayout mRlCompanyInfo;
    @BindView(R.id.tv_duty)
    TextView mTvDuty;
    @BindView(R.id.tv_title_req)
    TextView mTvTitleReq;
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
    @BindView(R.id.rl_studywork_detail)
    RelativeLayout mRlStudyworkDetail;
    @BindView(R.id.tv_title_position)
    TextView mTvTitlePosition;
    @BindView(R.id.tv_title_worktime)
    TextView mTvTitleWorkTime;
    @BindView(R.id.tv_title_phone)
    TextView mTvTitlePhone;
    @BindView(R.id.tv_company_add)
    TextView mTvComAdd;
    @BindView(R.id.tv_title_com_phone)
    TextView mTvTitleComPhone;
    @BindView(R.id.tv_title_com_add)
    TextView mTvTitleComAdd;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.progress_bar_title)
    TextView mProgressBarTitle;
    @BindView(R.id.progress_bar_container)
    LinearLayout mProgressBarContainer;
    private RecruitInfo mRecruitInfo;
    private StudyWorkInfo mStudyWorkInfo;
    private int mId;
    private int mType;
    private ICampusRecruitmentContract.Presenter mPresenter;

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
        if (mType == 3) {
            mPresenter.fetchCampusRecruitmentDetail(mId);
        } else if (mType == 8) {
            mPresenter.fetchStudyWorkDetail(mId);
        } else {
            initView();
        }
    }

    private void initView() {
        if (mRecruitInfo != null) {
            showRecriutDetail();
            mTvTitle.setText(mRecruitInfo.getJobtitle());
            mTvComAdd.setText(mRecruitInfo.getJobplace());
            mTvEducation.setText(mRecruitInfo.getJobeduname());
            mTvWages.setText(mRecruitInfo.getJobmoney());
            if (mRecruitInfo.getJobmoney().equals("0"))
            {
                mTvWages.setText("面议");
                mTvMoneyTag.setVisibility(View.GONE);
            }
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
            mTvEducation.setText("招聘人数:" + mStudyWorkInfo.getJobperson());
            mTvWages.setText(mStudyWorkInfo.getJobmoney());
            if (mStudyWorkInfo.getJobmoney().equals("0"))
            {
                mTvWages.setText("面议");
                mTvMoneyTag.setVisibility(View.GONE);
            }
            mTvStudyworkType.setText(mStudyWorkInfo.getJobcom());
            mTvStudyworkRange.setText(mStudyWorkInfo.getJobtime());
            mTvStudyworkPhone.setText(mStudyWorkInfo.getPhone());
            mTvReq.setText(mStudyWorkInfo.getJobreq());
            String time = getTimeRange(mStudyWorkInfo.getPublishtime());
            mTvTime.setText(time);
            mTvDuty.setText(mStudyWorkInfo.getJobperson());
        }
    }

    private void showRecriutDetail() {
        mRlCompanyInfo.setVisibility(View.VISIBLE);
        mRlStudyworkDetail.setVisibility(View.GONE);

        mTvTitleReq.setVisibility(View.VISIBLE);
        mTvDutyHint.setVisibility(View.VISIBLE);
        mTvDuty.setVisibility(View.VISIBLE);
    }

    private void showStudyWorkDetail() {
        mRlCompanyInfo.setVisibility(View.GONE);
        mRlStudyworkDetail.setVisibility(View.VISIBLE);

        mTvTitleReq.setVisibility(View.GONE);
        mTvDutyHint.setVisibility(View.GONE);
        mTvDuty.setVisibility(View.GONE);
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
        mRecruitInfo = recruitInfo;
        showRecriutDetail();
        mTvTitle.setText(mRecruitInfo.getJobtitle());
        mTvComAdd.setText(mRecruitInfo.getJobplace());
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
        if (mRecruitInfo.getJobmoney().equals("0"))
        {
            mTvWages.setText("面议");
            mTvMoneyTag.setVisibility(View.GONE);
        }
    }

    @Override
    public void setStudyWorkDetail(StudyWorkInfo studyWorkInfo) {
        mStudyWorkInfo = studyWorkInfo;
        showStudyWorkDetail();
        mTvTitle.setText(mStudyWorkInfo.getTitle());
        mTvPlace.setText(mStudyWorkInfo.getJobplace());
        mTvEducation.setText("招聘人数:" + mStudyWorkInfo.getJobperson());
        mTvWages.setText(mStudyWorkInfo.getJobmoney());
        mTvStudyworkType.setText(mStudyWorkInfo.getJobcom());
        mTvStudyworkRange.setText(mStudyWorkInfo.getJobtime());
        mTvStudyworkPhone.setText(mStudyWorkInfo.getPhone());
        mTvReq.setText(mStudyWorkInfo.getJobreq());
        String time = getTimeRange(mRecruitInfo.getPublishtime());
        mTvTime.setText(time);
        mTvDuty.setText(mStudyWorkInfo.getJobperson());
        if (mStudyWorkInfo.getJobmoney().equals("0"))
        {
            mTvWages.setText("面议");
            mTvMoneyTag.setVisibility(View.GONE);
        }
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

}
