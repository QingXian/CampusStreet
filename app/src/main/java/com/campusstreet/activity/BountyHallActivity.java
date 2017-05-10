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
import com.campusstreet.entity.JoinInfo;
import com.campusstreet.entity.UserInfo;
import com.campusstreet.model.BountyHallImpl;
import com.campusstreet.presenter.BountyHallPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.R.id.message;

/**
 * Created by Orange on 2017/4/6.
 */

public class BountyHallActivity extends AppCompatActivity implements IBountyHallContract.View {
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.iv_toolbar_right)
    ImageView mIvToolbarRight;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.rv_content)
    RecyclerView mRvContent;
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
    private int mPostion = 1;
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

    }

    @Override
    protected void onStart() {
        mPresenter.fetchTaskList(mPostion, mPi, null);
        super.onStart();

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
    }

    private void initView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRvContent.setLayoutManager(linearLayoutManager);
        mAdapter = new BountyHallRecyclerViewAdapter(this, mBountyHallInfoList);
        mRvContent.setAdapter(mAdapter);
    }

    @OnClick(R.id.iv_toolbar_right)
    public void onClick() {
        if (mUserInfo != null) {
            Intent intent = new Intent(this, AddBountyHallActivity.class);
            intent.putExtra(Const.USERINFO_EXTRA, mUserInfo);
            startActivity(intent);
        } else {
            showMessage("您还未登录");
        }
    }

    @Override
    public void setPresenter(IBountyHallContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void setTaskList(List<BountyHallInfo> bountyHallInfos) {
        mRvContent.setVisibility(View.VISIBLE);
        mTvError.setVisibility(View.GONE);
        mAdapter.replaceData(bountyHallInfos);
        setLoadingIndicator(false);
    }

    @Override
    public void setUserTaskList(List<BountyHallInfo> bountyHallInfos) {

    }

    @Override
    public void setBountyHallCategories(String[] type) {
        mTitle = type;
        if (type != null) {
            for (int i = 0; i < type.length; i++) {
                mTabLayout.addTab(mTabLayout.newTab().setText(type[i]));
            }
            mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    mPresenter.fetchTaskList(tab.getPosition() + 1, 0, null);
                    mPostion = tab.getPosition() + 1;
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
    public void showSuccessfullpassJoinTask() {

    }

    @Override
    public void showSuccessfullJointask(String successMsg) {

    }

    @Override
    public void showSuccessfullStartTask() {

    }

    @Override
    public void showErrorMsg(String errorMsg) {
        mRvContent.setVisibility(View.GONE);
        mTvError.setText(errorMsg);
        mTvError.setVisibility(View.VISIBLE);
        setLoadingIndicator(false);
    }

    @Override
    public void showSuccessfullyPush(String succcessMsg) {

    }

    @Override
    public void showfetchBountyHallCategoriesFailMsg(String errorMsg) {
        showMessage(errorMsg);
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
