package com.campusstreet.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.campusstreet.R;
import com.campusstreet.adapter.CampusInformationRecyclerViewAdapter;
import com.campusstreet.adapter.CampusRecruitmentRecyclerViewAdapter;
import com.campusstreet.adapter.CampusStudyWorkRecyclerViewAdapter;
import com.campusstreet.adapter.PartnerRecyclerViewAdapter;
import com.campusstreet.common.Const;
import com.campusstreet.contract.IPartnerContract;
import com.campusstreet.entity.NewInfo;
import com.campusstreet.entity.PartnerInfo;
import com.campusstreet.model.PartnerImpl;
import com.campusstreet.presenter.PartnerPresenter;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Orange on 2017/4/6.
 */

public class PartnerActivity extends AppCompatActivity implements IPartnerContract.View {
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.iv_toolbar_right)
    ImageView mIvToolbarRight;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
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
    private int mPi = 0;
    private IPartnerContract.Presenter mPresenter;
    private PartnerRecyclerViewAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partner);
        ButterKnife.bind(this);
        mToolbar.setTitle("");
        mToolbarTitle.setText(getString(R.string.frag_home_partner));
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
        mIvToolbarRight.setVisibility(View.VISIBLE);
        mIvToolbarRight.setImageResource(R.drawable.ic_search);
        new PartnerPresenter(PartnerImpl.getInstance(getApplicationContext()), this);
        initView();
        initEvent();
    }

    private void initEvent() {
        mAdapter.setOnItemClickListener(new PartnerRecyclerViewAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, PartnerInfo partnerInfo) {
                Intent intent = new Intent(PartnerActivity.this, PartnerDetailActivity.class);
                intent.putExtra(Const.PARTNERINFO_EXTRA, partnerInfo);
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
                        mPresenter.fetchPartnerList(null, 0, mPi);
                    }
                }, 1500);
            }

            @Override
            public void onLoadMore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPresenter.fetchPartnerList(null, 0, ++mPi);
                    }
                }, 500);
            }
        });
    }

    @OnClick(R.id.iv_toolbar_right)
    public void onClick() {
    }


    private void initView() {
        mRvContent.setLinearLayout();
        mAdapter = new PartnerRecyclerViewAdapter(this, null);
        mRvContent.setAdapter(mAdapter);
    }

    @Override
    public void setPresenter(IPartnerContract.Presenter presenter) {
        mPresenter = presenter;
    }


    @Override
    public void setPartnerCategories(String[] type) {
    }

    @Override
    public void setPartnerList(List<PartnerInfo> partnerList) {
        if (partnerList != null && partnerList.size() < 20) {
            mRvContent.setPushRefreshEnable(false);
        } else {
            mRvContent.setPushRefreshEnable(true);
        }
        if (mPi != 0) {
            if (partnerList != null) {
                mAdapter.addData(partnerList);
                mRvContent.setPullLoadMoreCompleted();
            }
        } else {
            mRvContent.setVisibility(View.VISIBLE);
            mTvError.setVisibility(View.GONE);
            mAdapter.replaceData(partnerList);
            mRvContent.setPullLoadMoreCompleted();
            setLoadingIndicator(false);
        }
    }

    @Override
    public void setPartnerDetail(PartnerInfo partnerInfo) {

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

    @Override
    protected void onStart() {
        super.onStart();
        mPi = 0;
        mPresenter.fetchPartnerList(null, 0, mPi);
        setLoadingIndicator(true);

    }

    protected void showMessage(String msg) {
        if (this != null && !this.isFinishing()) {
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        }
    }
}
