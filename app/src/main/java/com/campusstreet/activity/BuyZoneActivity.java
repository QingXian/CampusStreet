package com.campusstreet.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.campusstreet.R;
import com.campusstreet.adapter.BuyZoneRecyclerViewAdapter;
import com.campusstreet.adapter.IdleSaleRecyclerViewAdapter;
import com.campusstreet.common.Const;
import com.campusstreet.contract.IBuyZoneContract;
import com.campusstreet.entity.BuyZoneInfo;
import com.campusstreet.entity.IdleSaleInfo;
import com.campusstreet.entity.LeaveMessageInfo;
import com.campusstreet.model.BuyZoneImpl;
import com.campusstreet.model.IdleSaleImpl;
import com.campusstreet.presenter.BuyZonePresenter;
import com.campusstreet.presenter.IdleSalePresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.campusstreet.R.id.view;

/**
 * Created by Orange on 2017/4/6.
 */

public class BuyZoneActivity extends AppCompatActivity implements IBuyZoneContract.View {
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.iv_toolbar_right)
    ImageView mIvToolbarRight;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.rv_content)
    RecyclerView mRvContent;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.progress_bar_title)
    TextView mProgressBarTitle;
    @BindView(R.id.progress_bar_container)
    LinearLayout mProgressBarContainer;
    @BindView(R.id.tv_error)
    TextView mTvError;
    private IBuyZoneContract.Presenter mPresenter;
    private BuyZoneRecyclerViewAdapter mAdapter;
    private int mPi = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_zone);
        ButterKnife.bind(this);
        mToolbar.setTitle("");
        mToolbarTitle.setText(getString(R.string.frag_home_buy_zone));
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
        new BuyZonePresenter(BuyZoneImpl.getInstance(getApplicationContext()), this);
        initView();
        initEvent();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.fetchBuyZoneList(mPi);
    }

    private void initView() {
        LinearLayoutManager gridLayoutManager = new LinearLayoutManager(this);
        mRvContent.setLayoutManager(gridLayoutManager);
        mAdapter = new BuyZoneRecyclerViewAdapter(this,null);
        mRvContent.setAdapter(mAdapter);
    }

    private void initEvent() {
        mAdapter.setOnItemClickListener(new BuyZoneRecyclerViewAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view,BuyZoneInfo buyZoneInfo) {
                Intent intent = new Intent(BuyZoneActivity.this, BuyZoneDetailActivity.class);
                intent.putExtra(Const.BUYZONEIINFO_EXTRA,buyZoneInfo);
                startActivity(intent);
            }
        });
    }

    @OnClick(R.id.iv_toolbar_right)
    public void onClick() {
        Intent intent = new Intent(this, AddBuyZoneActivity.class);
        startActivity(intent);
    }

    @Override
    public void setPresenter(IBuyZoneContract.Presenter presenter) {
        mPresenter = presenter;
    }


    @Override
    public void setBuyZone(List<BuyZoneInfo> buyZoneInfoList) {
        mRvContent.setVisibility(View.VISIBLE);
        mTvError.setVisibility(View.GONE);
        mAdapter.replaceData(buyZoneInfoList);
    }

    @Override
    public void setBuyZoneMessageList(List<LeaveMessageInfo> BuyZoneMessageList) {

    }

    @Override
    public void showErrorMsg(String errorMsg) {
        mRvContent.setVisibility(View.GONE);
        mTvError.setText(errorMsg);
        mTvError.setVisibility(View.VISIBLE);
    }

    @Override
    public void showSuccessfullyPush(String succcessMsg) {

    }

    @Override
    public void showSuccessfullyleaveMessage(String succcessMsg) {

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
