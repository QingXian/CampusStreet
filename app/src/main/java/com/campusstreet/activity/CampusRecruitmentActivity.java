package com.campusstreet.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.campusstreet.R;
import com.campusstreet.adapter.BountyHallDetailRecyclerViewAdapter;
import com.campusstreet.adapter.CampusRecruitmentRecyclerViewAdapter;
import com.campusstreet.adapter.CampusStudyWorkRecyclerViewAdapter;
import com.campusstreet.common.Const;
import com.campusstreet.contract.ICampusRecruitmentContract;
import com.campusstreet.entity.JoinInfo;
import com.campusstreet.entity.RecruitInfo;
import com.campusstreet.entity.StudyWorkInfo;
import com.campusstreet.model.CampusRecruitmentImpl;
import com.campusstreet.presenter.CampusRecruitmentPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.R.attr.type;
import static com.campusstreet.R.id.view;
import static com.campusstreet.common.Const.ISSTRAT;
import static com.campusstreet.common.Const.TID_EXTRA;

/**
 * Created by Orange on 2017/4/6.
 */

public class CampusRecruitmentActivity extends AppCompatActivity implements ICampusRecruitmentContract.View {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.rv_content)
    RecyclerView mRvContent;
    @BindView(R.id.et_search)
    EditText mEtSearch;
    @BindView(R.id.tv_toolbar_right)
    TextView mTvToolbarRight;
    ICampusRecruitmentContract.Presenter mPresenter;
    @BindView(R.id.tv_error)
    TextView mTvError;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.progress_bar_title)
    TextView mProgressBarTitle;
    @BindView(R.id.progress_bar_container)
    LinearLayout mProgressBarContainer;
    private int mPi = 0;
    private CampusRecruitmentRecyclerViewAdapter mRecruitAdapter;
    private CampusStudyWorkRecyclerViewAdapter mStudyWorkAdapter;
    private int mPostion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campus_recruitment);
        ButterKnife.bind(this);
        mToolbar.setTitle("");
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
        initView();
        initEvent();
        new CampusRecruitmentPresenter(CampusRecruitmentImpl.getInstance(getApplicationContext()), this);
    }

    private void initEvent() {
        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getText().equals("普通招聘")) {

                    mPresenter.fetchCampusRecruitmentList(null, mPi);
                    mPostion = tab.getPosition();
                } else {
                    mPostion = tab.getPosition();
                    mPresenter.fetchStudyWorkList(null, mPi);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        mRecruitAdapter.setOnItemClickListener(new CampusRecruitmentRecyclerViewAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, RecruitInfo recruitInfo) {
                Intent intent = new Intent(CampusRecruitmentActivity.this, CampusRecruitmentDetailActivity.class);
                intent.putExtra(Const.RECRUITMENTNFO_EXTRA, recruitInfo);
                startActivity(intent);
            }
        });
        mStudyWorkAdapter.setOnItemClickListener(new CampusStudyWorkRecyclerViewAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, StudyWorkInfo studyWorkInfo) {
                Intent intent = new Intent(CampusRecruitmentActivity.this, CampusRecruitmentDetailActivity.class);
                intent.putExtra(Const.STUDYWORKINFO_EXTRA, studyWorkInfo);
                startActivity(intent);
            }
        });
    }

    private void initView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRvContent.setLayoutManager(linearLayoutManager);
        mRecruitAdapter = new CampusRecruitmentRecyclerViewAdapter(this, null);
        mStudyWorkAdapter = new CampusStudyWorkRecyclerViewAdapter(this, null);
        mRvContent.setAdapter(mRecruitAdapter);
        mTabLayout.addTab(mTabLayout.newTab().setText("普通招聘"));
        mTabLayout.addTab(mTabLayout.newTab().setText("勤工俭学/兼职"));
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mPostion == 0) {
            mPresenter.fetchCampusRecruitmentList(null, mPi);
        } else
            mPresenter.fetchStudyWorkList(null, mPi);

    }

    @OnClick(R.id.tv_toolbar_right)
    public void onClick() {
    }

    @Override
    public void setPresenter(ICampusRecruitmentContract.Presenter presenter) {
        mPresenter = presenter;
    }


    @Override
    public void setCampusRecruitmentList(List<RecruitInfo> recruitInfos) {
        mRvContent.setVisibility(View.VISIBLE);
        mTvError.setVisibility(View.GONE);
        mRecruitAdapter.replaceData(recruitInfos);
    }

    @Override
    public void setStudyWorkList(List<StudyWorkInfo> studyWorkInfos) {
        mRvContent.setVisibility(View.VISIBLE);
        mTvError.setVisibility(View.GONE);
        mRvContent.setAdapter(mStudyWorkAdapter);
        mStudyWorkAdapter.replaceData(studyWorkInfos);
    }

    @Override
    public void showErrorMsg(String errorMsg) {
        mRvContent.setVisibility(View.GONE);
        mTvError.setText(errorMsg);
        mTvError.setVisibility(View.VISIBLE);
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
