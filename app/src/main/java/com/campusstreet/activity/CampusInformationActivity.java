package com.campusstreet.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.campusstreet.R;
import com.campusstreet.adapter.BountyHallRecyclerViewAdapter;
import com.campusstreet.adapter.CampusInformationRecyclerViewAdapter;
import com.campusstreet.common.Const;
import com.campusstreet.contract.ICampusInformationContract;
import com.campusstreet.entity.BountyHallInfo;
import com.campusstreet.entity.LeaveMessageInfo;
import com.campusstreet.entity.NewInfo;
import com.campusstreet.model.CampusInformationImpl;
import com.campusstreet.presenter.CampusInformationPresenter;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import java.security.Key;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.campusstreet.R.id.view;

/**
 * Created by Orange on 2017/4/6.
 */

public class CampusInformationActivity extends AppCompatActivity implements ICampusInformationContract.View {
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.iv_toolbar_right)
    ImageView mIvToolbarRight;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.rv_content)
    PullLoadMoreRecyclerView mRvContent;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.progress_bar_title)
    TextView mProgressBarTitle;
    @BindView(R.id.progress_bar_container)
    LinearLayout mProgressBarContainer;
    @BindView(R.id.tv_error)
    TextView mTvError;
    private ICampusInformationContract.Presenter mPresenter;
    private int mPi = 0;
    private CampusInformationRecyclerViewAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campus_information);
        ButterKnife.bind(this);
        mToolbar.setTitle("");
        mToolbarTitle.setText(getString(R.string.frag_home_campus_information));
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
        new CampusInformationPresenter(CampusInformationImpl.getInstance(getApplicationContext()), this);
        initView();
        initEvent();
    }

    private void initEvent() {
        mAdapter.setOnItemClickListener(new CampusInformationRecyclerViewAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, NewInfo newInfo) {
                Intent intent = new Intent(CampusInformationActivity.this, CampusInformationDetailActivity.class);
                intent.putExtra(Const.NEWINFO_EXTRA, newInfo);
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
                        mPresenter.fetchCampusInformationList(null,mPi);
                    }
                }, 1500);
            }

            @Override
            public void onLoadMore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPresenter.fetchCampusInformationList(null,++mPi);
                    }
                }, 500);
            }
        });
    }

    private void initView() {
        mRvContent.setLinearLayout();
        mAdapter = new CampusInformationRecyclerViewAdapter(this,null);
        mRvContent.setAdapter(mAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPi = 0;
        mPresenter.fetchCampusInformationList(null,mPi);
        setLoadingIndicator(true);

    }

    @Override
    public void setPresenter(ICampusInformationContract.Presenter presenter) {
        mPresenter = presenter;
    }


    @Override
    public void setCampusInformationList(List<NewInfo> newInfos) {
        if (newInfos != null && newInfos.size() < 20) {
            mRvContent.setPushRefreshEnable(false);
        } else {
            mRvContent.setPushRefreshEnable(true);
        }
        if (mPi != 0) {
            if (newInfos != null) {
                mAdapter.addData(newInfos);
                mRvContent.setPullLoadMoreCompleted();
            }
        } else {
            mRvContent.setVisibility(View.VISIBLE);
            mTvError.setVisibility(View.GONE);
            mAdapter.replaceData(newInfos);
            mRvContent.setPullLoadMoreCompleted();
            setLoadingIndicator(false);
        }
    }

    @Override
    public void setCampusInformationDetail(NewInfo newInfo) {//忽略
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
