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

    }

    @OnClick(R.id.rl_company_info)
    public void onClick() {
    }
}
