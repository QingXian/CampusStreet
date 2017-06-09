package com.campusstreet.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Orange on 2017/4/6.
 */

public class CampusRecruitmentActivity extends BaseActivity implements ICampusRecruitmentContract.View {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.rv_content)
    PullLoadMoreRecyclerView mRvContent;
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
    List<RecruitInfo> mRecruitInfos;
    List<StudyWorkInfo> mStudyWorkInfos;

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
                    mPi = 0;
                    mEtSearch.setText("");
                    mPresenter.fetchCampusRecruitmentList(null, mPi);
                    mPostion = tab.getPosition();
                } else {
                    mPi = 0;
                    mEtSearch.setText("");
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
        mRvContent.setOnPullLoadMoreListener(new PullLoadMoreRecyclerView.PullLoadMoreListener() {
            @Override
            public void onRefresh() {
                mPi = 0;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (mPostion == 0) {
                            if (!mEtSearch.getText().equals(""))
                                mPresenter.fetchCampusRecruitmentList(mEtSearch.getText().toString(), mPi);
                            else
                                mPresenter.fetchCampusRecruitmentList(null, mPi);
                        } else {
                            if (!mEtSearch.getText().equals(""))
                                mPresenter.fetchStudyWorkList(mEtSearch.getText().toString(), mPi);
                            else
                                mPresenter.fetchStudyWorkList(null, mPi);
                        }
                    }
                }, 1500);
            }

            @Override
            public void onLoadMore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (mPostion == 0) {
                            if (!mEtSearch.getText().equals(""))
                                mPresenter.fetchCampusRecruitmentList(mEtSearch.getText().toString(), ++mPi);
                            else
                                mPresenter.fetchCampusRecruitmentList(null, ++mPi);
                        } else {
                            if (!mEtSearch.getText().equals(""))
                                mPresenter.fetchStudyWorkList(mEtSearch.getText().toString(), ++mPi);
                            else
                                mPresenter.fetchStudyWorkList(null, ++mPi);
                        }

                    }
                }, 1500);
            }
        });
        mEtSearch.setOnKeyListener(new View.OnKeyListener() {

            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    mPi = 0;
                    if (mPostion == 0) {
                        if (!mEtSearch.getText().equals(""))
                            mPresenter.fetchCampusRecruitmentList(mEtSearch.getText().toString(), mPi);
                        else
                            mPresenter.fetchCampusRecruitmentList(null, mPi);
                    } else {
                        if (!mEtSearch.getText().equals(""))
                            mPresenter.fetchStudyWorkList(mEtSearch.getText().toString(), mPi);
                        else
                            mPresenter.fetchStudyWorkList(null, mPi);
                    }
                    setLoadingIndicator(true);
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm != null) {
                        imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
                    }
                    return true;
                }
                return false;
            }
        });
    }

    private void initView() {
        mRvContent.setLinearLayout();
        mRecruitAdapter = new CampusRecruitmentRecyclerViewAdapter(this, null);
        mStudyWorkAdapter = new CampusStudyWorkRecyclerViewAdapter(this, null);
        mRvContent.setAdapter(mRecruitAdapter);
        mTabLayout.addTab(mTabLayout.newTab().setText("普通招聘"));
        mTabLayout.addTab(mTabLayout.newTab().setText("勤工俭学/兼职"));
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPi = 0;
        setLoadingIndicator(true);
        if (mPostion == 0) {
            if (!mEtSearch.getText().equals(""))
                mPresenter.fetchCampusRecruitmentList(mEtSearch.getText().toString(), mPi);
            else
                mPresenter.fetchCampusRecruitmentList(null, mPi);
        } else {
            if (!mEtSearch.getText().equals(""))
                mPresenter.fetchStudyWorkList(mEtSearch.getText().toString(), mPi);
            else
                mPresenter.fetchStudyWorkList(null, mPi);
        }
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
        if (recruitInfos != null && recruitInfos.size() < 20) {
            mRvContent.setPushRefreshEnable(false);
        } else {
            mRvContent.setPushRefreshEnable(true);
        }
        if (mPi != 0) {
            if (recruitInfos != null) {
                mRvContent.setAdapter(mRecruitAdapter);
                mRecruitAdapter.addData(recruitInfos);
                mRvContent.setPullLoadMoreCompleted();
            }
        } else {
            mRvContent.setVisibility(View.VISIBLE);
            mTvError.setVisibility(View.GONE);
            mRvContent.setAdapter(mRecruitAdapter);
            mRecruitAdapter.replaceData(recruitInfos);
            mRvContent.setPullLoadMoreCompleted();
            setLoadingIndicator(false);
        }
    }

    @Override
    public void setStudyWorkList(List<StudyWorkInfo> studyWorkInfos) {
        if (studyWorkInfos != null && studyWorkInfos.size() < 20) {
            mRvContent.setPushRefreshEnable(false);
        } else {
            mRvContent.setPushRefreshEnable(true);
        }
        if (mPi != 0) {
            if (studyWorkInfos != null) {
                mRvContent.setAdapter(mStudyWorkAdapter);
                mStudyWorkAdapter.addData(studyWorkInfos);
                mRvContent.setPullLoadMoreCompleted();
            }
        } else {
            mRvContent.setVisibility(View.VISIBLE);
            mTvError.setVisibility(View.GONE);
            mRvContent.setAdapter(mStudyWorkAdapter);
            mStudyWorkAdapter.replaceData(studyWorkInfos);
            mRvContent.setPullLoadMoreCompleted();
            setLoadingIndicator(false);
        }

    }

    @Override
    public void showErrorMsg(String errorMsg) {
        if (mPi == 0) {
            mRvContent.setVisibility(View.GONE);
            mTvError.setText(errorMsg);
            mTvError.setVisibility(View.VISIBLE);
        } else {
            showMessage("没有数据了");
        }
        setLoadingIndicator(false);
        mRvContent.setPullLoadMoreCompleted();
    }

    @Override
    public void setCampusRecruitmentDetail(RecruitInfo recruitInfo) {

    }

    @Override
    public void setStudyWorkDetail(StudyWorkInfo studyWorkInfo) {

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
