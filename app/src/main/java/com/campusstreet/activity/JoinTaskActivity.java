package com.campusstreet.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.campusstreet.R;
import com.campusstreet.adapter.BountyHallRecyclerViewAdapter;
import com.campusstreet.adapter.UserJoinTaskRecyclerViewAdapter;
import com.campusstreet.common.Const;
import com.campusstreet.contract.IBountyHallContract;
import com.campusstreet.entity.BountyHallInfo;
import com.campusstreet.entity.CategoriesInfo;
import com.campusstreet.entity.JoinInfo;
import com.campusstreet.entity.UserInfo;
import com.campusstreet.entity.UserJoinTaskInfo;
import com.campusstreet.model.BountyHallImpl;
import com.campusstreet.presenter.BountyHallPresenter;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.campusstreet.R.id.view;

/**
 * Created by Orange on 2017/6/7.
 */

public class JoinTaskActivity extends BaseActivity implements IBountyHallContract.View {
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.iv_toolbar_right)
    ImageView mIvToolbarRight;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.rv_content)
    PullLoadMoreRecyclerView mRvContent;
    @BindView(R.id.tv_error)
    TextView mTvError;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.progress_bar_title)
    TextView mProgressBarTitle;
    @BindView(R.id.progress_bar_container)
    LinearLayout mProgressBarContainer;
    private IBountyHallContract.Presenter mPresenter;
    private int mPi = 0;
    private UserInfo mUserInfo;
    private UserJoinTaskRecyclerViewAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_task);
        ButterKnife.bind(this);
        mToolbar.setTitle("");
        mToolbarTitle.setText(R.string.act_my_bounty_hall_toolbar_title);
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
        mIvToolbarRight.setVisibility(View.GONE);
        mUserInfo = (UserInfo) getIntent().getSerializableExtra(Const.USERINFO_EXTRA);
        new BountyHallPresenter(BountyHallImpl.getInstance(getApplicationContext()), this);
        initView();
        initEvent();
        mPresenter.fetchUserJoinTaskList(mUserInfo.getUid(), mPi);
        setLoadingIndicator(true);
    }

    private void initEvent() {
        mRvContent.setOnPullLoadMoreListener(new PullLoadMoreRecyclerView.PullLoadMoreListener() {
            @Override
            public void onRefresh() {
                mPi = 0;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPresenter.fetchUserTaskList(mUserInfo.getUid(), 0, mPi, null);
                    }
                }, 1500);
            }

            @Override
            public void onLoadMore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPresenter.fetchUserTaskList(mUserInfo.getUid(), 0, ++mPi, null);
                    }
                }, 500);
            }
        });
        mAdapter.setOnItemClickListener(new UserJoinTaskRecyclerViewAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, UserJoinTaskInfo userJoinTaskInfo) {
                Intent intent = new Intent(JoinTaskActivity.this, BountyHallDetailActivity.class);
                intent.putExtra(Const.USERJOINTASKINFO_EXTRA, userJoinTaskInfo);
                intent.putExtra(Const.USERINFO_EXTRA, mUserInfo);
                startActivity(intent);
            }
        });
    }

    private void initView() {
        mRvContent.setLinearLayout();
        mAdapter = new UserJoinTaskRecyclerViewAdapter(this, null);
        mRvContent.setAdapter(mAdapter);
    }


    @Override
    public void setPresenter(IBountyHallContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void setTaskList(List<BountyHallInfo> userJoinTaskList) {
    }

    @Override
    public void setUserJoinTaskList(List<UserJoinTaskInfo> userJoinTaskList) {
        if (userJoinTaskList != null && userJoinTaskList.size() < 20) {
            mRvContent.setPushRefreshEnable(false);
        } else {
            mRvContent.setPushRefreshEnable(true);
        }
        if (mPi != 0) {
            if (userJoinTaskList != null) {
                mAdapter.addData(userJoinTaskList);
                mRvContent.setPullLoadMoreCompleted();
            }
        } else {
            mRvContent.setVisibility(View.VISIBLE);
            mTvError.setVisibility(View.GONE);
            mAdapter.replaceData(userJoinTaskList);
            mRvContent.setPullLoadMoreCompleted();
            setLoadingIndicator(false);
        }
    }

    @Override
    public void setBountyHallCategories(List<CategoriesInfo> categories) {

    }


    @Override
    public void setJoinTaskList(List<JoinInfo> joinInfos) {

    }

    @Override
    public void setPassTaskList(List<JoinInfo> joinInfos) {

    }

    @Override
    public void showSuccessfullPassJoinTask() {

    }

    @Override
    public void showSuccessfullPublisherOpTask() {

    }

    @Override
    public void showSuccessfullCompleteTask() {

    }

    @Override
    public void showSuccessfullGiveUpTask() {

    }


    @Override
    public void showSuccessfullJointask(String successMsg) {

    }

    @Override
    public void showSuccessfullStartTask() {

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
    public void showNoPassMsg() {

    }

    @Override
    public void showSuccessfullyPush(String successMsg) {

    }

    @Override
    public void setTaskDetail(BountyHallInfo bountyHallInfo) {

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
