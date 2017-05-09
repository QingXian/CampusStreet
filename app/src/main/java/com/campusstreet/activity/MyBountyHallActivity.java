package com.campusstreet.activity;

import android.content.Intent;
import android.os.Bundle;
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

import com.campusstreet.R;
import com.campusstreet.adapter.BountyHallRecyclerViewAdapter;
import com.campusstreet.common.Const;
import com.campusstreet.contract.IBountyHallContract;
import com.campusstreet.entity.BountyHallInfo;
import com.campusstreet.entity.JoinInfo;
import com.campusstreet.entity.UserInfo;
import com.campusstreet.model.BountyHallImpl;
import com.campusstreet.presenter.BountyHallPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Orange on 2017/4/9.
 */

public class MyBountyHallActivity extends AppCompatActivity implements IBountyHallContract.View {
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.iv_toolbar_right)
    ImageView mIvToolbarRight;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
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
    private int mPi = 0;
    private UserInfo mUserInfo;
    private BountyHallRecyclerViewAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_bounty_hall);
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
        mIvToolbarRight.setVisibility(View.VISIBLE);
        mIvToolbarRight.setImageResource(R.drawable.ic_add);
        mUserInfo = (UserInfo) getIntent().getSerializableExtra(Const.USERINFO_EXTRA);
        new BountyHallPresenter(BountyHallImpl.getInstance(getApplicationContext()), this);
        initView();
        initEvent();
    }

    private void initEvent() {
        mAdapter.setOnItemClickListener(new BountyHallRecyclerViewAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, BountyHallInfo BountyHallInfo) {
                Intent intent = new Intent(MyBountyHallActivity.this, BountyHallDetailActivity.class);
                intent.putExtra(Const.BOUNTYHALLINFO_EXTRA, BountyHallInfo);
                intent.putExtra(Const.USERINFO_EXTRA, mUserInfo);
                startActivity(intent);
            }
        });
    }

    private void initView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRvContent.setLayoutManager(linearLayoutManager);
        mAdapter = new BountyHallRecyclerViewAdapter(this, null);
        mRvContent.setAdapter(mAdapter);
    }

    @Override
    protected void onStart() {
        mPresenter.fetchUserTaskList(mUserInfo.getUid(), 0, mPi, null);
        super.onStart();

    }

    @OnClick(R.id.iv_toolbar_right)
    public void onClick() {
        Intent intent = new Intent(this, AddBountyHallActivity.class);
        startActivity(intent);
    }

    @Override
    public void setPresenter(IBountyHallContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void setTaskList(List<BountyHallInfo> bountyHallInfos) {

    }

    @Override
    public void setUserTaskList(List<BountyHallInfo> bountyHallInfos) {

    }

    @Override
    public void setBountyHallCategories(String[] type) {

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
    }

    @Override
    public void showSuccessfullyPush(String successMsg) {

    }

    @Override
    public void showfetchBountyHallCategoriesFailMsg(String errorMsg) {

    }

    @Override
    public void setLoadingIndicator(boolean active) {

    }
}
