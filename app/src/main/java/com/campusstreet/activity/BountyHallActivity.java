package com.campusstreet.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
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
import com.campusstreet.adapter.BuyZoneRecyclerViewAdapter;
import com.campusstreet.common.Const;
import com.campusstreet.contract.IBountyHallContract;
import com.campusstreet.entity.BountyHallInfo;
import com.campusstreet.entity.BuyZoneInfo;
import com.campusstreet.entity.CategoriesInfo;
import com.campusstreet.entity.JoinInfo;
import com.campusstreet.entity.UserInfo;
import com.campusstreet.entity.UserJoinTaskInfo;
import com.campusstreet.model.BountyHallImpl;
import com.campusstreet.presenter.BountyHallPresenter;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.R.attr.type;
import static android.R.id.message;
import static com.campusstreet.common.Const.REQUEST_CODE;

/**
 * Created by Orange on 2017/4/6.
 */

public class BountyHallActivity extends BaseActivity implements IBountyHallContract.View {
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
    @BindView(R.id.tv_error)
    TextView mTvError;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.progress_bar_title)
    TextView mProgressBarTitle;
    @BindView(R.id.progress_bar_container)
    LinearLayout mProgressBarContainer;
    private IBountyHallContract.Presenter mPresenter;
    private BountyHallRecyclerViewAdapter mAdapter;
    private String[] mTitle;
    private int mIndex;
    private List<BountyHallInfo> mBountyHallInfoList;
    private int mPi = 0;
    private int mPostion = 0;
    private int[] mPostions;
    private UserInfo mUserInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bounty_hall);
        ButterKnife.bind(this);
        mToolbar.setTitle("");
        mToolbarTitle.setText(getString(R.string.frag_home_bounty_hall));
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
        mIvToolbarRight.setImageResource(R.drawable.ic_add);
        new BountyHallPresenter(BountyHallImpl.getInstance(getApplicationContext()), this);
        mBountyHallInfoList = new ArrayList<>();
        initView();
        initEvent();
        mUserInfo = (UserInfo) getIntent().getSerializableExtra(Const.USERINFO_EXTRA);
        mPresenter.fetchBountyHallCategories();
        mPresenter.fetchTaskList(mPostion, mPi, null);
        setLoadingIndicator(true);

    }


    private void initEvent() {
        mAdapter.setOnItemClickListener(new BountyHallRecyclerViewAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, BountyHallInfo BountyHallInfo) {
                Intent intent = new Intent(BountyHallActivity.this, BountyHallDetailActivity.class);
                intent.putExtra(Const.BOUNTYHALLINFO_EXTRA, BountyHallInfo);
                intent.putExtra(Const.USERINFO_EXTRA, mUserInfo);
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
                        mPresenter.fetchTaskList(mPostion, mPi, null);
                    }
                }, 1500);
            }

            @Override
            public void onLoadMore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPresenter.fetchTaskList(mPostion, ++mPi, null);
                    }
                }, 500);
            }
        });
    }

    private void initView() {
        mRvContent.setLinearLayout();
        mAdapter = new BountyHallRecyclerViewAdapter(this, mBountyHallInfoList);
        mRvContent.setAdapter(mAdapter);
    }

    @OnClick(R.id.iv_toolbar_right)
    public void onClick() {
        if (mUserInfo != null) {
            Intent intent = new Intent(this, AddBountyHallActivity.class);
            intent.putExtra(Const.USERINFO_EXTRA, mUserInfo);
            startActivityForResult(intent,REQUEST_CODE);
        } else {
            showMessage("您还未登录");
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            mPi = 0;
            mPresenter.fetchTaskList(mPostion, mPi, null);
            setLoadingIndicator(true);
        }
    }

    @Override
    public void setPresenter(IBountyHallContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void setTaskList(List<BountyHallInfo> bountyHallInfos) {
        if (bountyHallInfos != null && bountyHallInfos.size() < 20) {
            mRvContent.setPushRefreshEnable(false);
        } else {
            mRvContent.setPushRefreshEnable(true);
        }
        if (mPi != 0) {
            if (bountyHallInfos != null) {
                mAdapter.addData(bountyHallInfos);
                mRvContent.setPullLoadMoreCompleted();
            }
        } else {
            mRvContent.setVisibility(View.VISIBLE);
            mTvError.setVisibility(View.GONE);
            mAdapter.replaceData(bountyHallInfos);
            mRvContent.setPullLoadMoreCompleted();
            setLoadingIndicator(false);
        }
    }

    @Override
    public void setUserJoinTaskList(List<UserJoinTaskInfo> userJoinTaskList) {

    }

    @Override
    public void setBountyHallCategories(List<CategoriesInfo> categories) {
        mTitle = new String[categories.size() + 1];
        mPostions = new int[categories.size() + 1];
        for (int i = 0; i < categories.size(); i++) {
            mTitle[i + 1] = categories.get(i).getName();
            mPostions[i + 1] = categories.get(i).getId();
        }
        if (mTitle != null) {
            mTabLayout.addTab(mTabLayout.newTab().setText("全部"));
            for (int i = 1; i < mTitle.length; i++) {
                mTabLayout.addTab(mTabLayout.newTab().setText(mTitle[i]));
            }
            mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    mPi = 0;
                    mPresenter.fetchTaskList(mPostions[tab.getPosition()], mPi, null);
                    mPostion = mPostions[tab.getPosition()];
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });
        }
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
    public void showSuccessfullyPush(String succcessMsg) {

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
