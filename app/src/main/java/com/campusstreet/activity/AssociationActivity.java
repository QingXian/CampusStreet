package com.campusstreet.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.campusstreet.R;
import com.campusstreet.adapter.AssociationRecyclerViewAdapter;
import com.campusstreet.adapter.UserAssociationrecyclerViewAdapter;
import com.campusstreet.common.Const;
import com.campusstreet.contract.IAssociationContract;
import com.campusstreet.entity.AssociationInfo;
import com.campusstreet.entity.AssociationNumInfo;
import com.campusstreet.entity.AssociationPostInfo;
import com.campusstreet.entity.AssociationPostMessageInfo;
import com.campusstreet.entity.UserAssociationInfo;
import com.campusstreet.entity.UserInfo;
import com.campusstreet.model.AssociationImpl;
import com.campusstreet.presenter.AssociationPresenter;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Orange on 2017/4/6.
 */

public class AssociationActivity extends BaseActivity implements IAssociationContract.View {

    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.iv_toolbar_right)
    ImageView mIvToolbarRight;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.tab_layout_sub)
    TabLayout mTabLayoutSub;
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
    private IAssociationContract.Presenter mPresenter;
    private int mPi = 0;
    private AssociationRecyclerViewAdapter mAdapter;
    private UserAssociationrecyclerViewAdapter mUserAssociationAdapter;
    private UserInfo mUserInfo;
    private int mPostion;
    private int mType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_association);
        ButterKnife.bind(this);
        mToolbar.setTitle("");
        mToolbarTitle.setText(getString(R.string.frag_home_association));
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
        new AssociationPresenter(AssociationImpl.getInstance(getApplicationContext()), this);
        mUserInfo = (UserInfo) getIntent().getSerializableExtra(Const.USERINFO_EXTRA);
        mType = getIntent().getIntExtra(Const.TYPE, 0);
        initView();
        initEvent();
        if (mPostion == 0) {
            mPresenter.fetchAssociationList(mPi);
        } else {
            if (mUserInfo != null) {
                mPresenter.fetchUserAssociationList(mPi, mUserInfo.getUid());
            } else {
                showErrorMsg("您还未登录");
            }
        }
        setLoadingIndicator(true);
    }

    private void initEvent() {
        mAdapter.setOnItemClickListener(new AssociationRecyclerViewAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, AssociationInfo AssociationInfo) {
                Intent intent = new Intent(AssociationActivity.this, AssociationDetailActivity.class);
                intent.putExtra(Const.ASSOCIATIONINFO_EXTRA, AssociationInfo);
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
                        mPresenter.fetchAssociationList(mPi);
                    }
                }, 1500);
            }

            @Override
            public void onLoadMore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPresenter.fetchAssociationList(++mPi);
                    }
                }, 500);
            }
        });
        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getText().equals("推荐社团")) {
                    mPi = 0;
                    mPresenter.fetchAssociationList(mPi);
                    mPostion = tab.getPosition();
                } else {
                    if (mUserInfo != null) {
                        mPi = 0;
                        mPostion = tab.getPosition();
                        mPresenter.fetchUserAssociationList(mPi, mUserInfo.getUid());
                    } else {
                        showErrorMsg("您还未登录");
                    }
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        mUserAssociationAdapter.setOnItemClickListener(new UserAssociationrecyclerViewAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, UserAssociationInfo userAssociationInfo) {
                Intent intent = new Intent(AssociationActivity.this, AssociationDetailActivity.class);
                intent.putExtra(Const.USERASSOCIATIONINFO_EXTRA, userAssociationInfo);
                intent.putExtra(Const.USERINFO_EXTRA, mUserInfo);
                startActivity(intent);
            }
        });
    }

    private void initView() {
        mTabLayout.addTab(mTabLayout.newTab().setText("推荐社团"));
        mTabLayout.addTab(mTabLayout.newTab().setText("我的社团"));
        if (mType == 1) {
            mPostion = 1;
            mTabLayout.getTabAt(mPostion).select();
        }
        mRvContent.setLinearLayout();
        mAdapter = new AssociationRecyclerViewAdapter(this, null);
        mUserAssociationAdapter = new UserAssociationrecyclerViewAdapter(this, null);
        mRvContent.setAdapter(mAdapter);
    }

    @Override
    public void setPresenter(IAssociationContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void setAssociationPostMessageList(List<AssociationPostMessageInfo> associationPostMessageList) {

    }

    @Override
    public void setAssociationPostList(List<AssociationPostInfo> associationPostList) {

    }

    @Override
    public void setAssociationNumList(List<AssociationNumInfo> associationNumList) {

    }

    @Override
    public void setAssociationList(List<AssociationInfo> associationList) {
        if (associationList != null && associationList.size() < 20) {
            mRvContent.setPushRefreshEnable(false);
        } else {
            mRvContent.setPushRefreshEnable(true);
        }
        if (mPi != 0) {
            if (associationList != null) {
                mRvContent.setAdapter(mAdapter);
                mAdapter.addData(associationList);
                mRvContent.setPullLoadMoreCompleted();
            }
        } else {
            mRvContent.setVisibility(View.VISIBLE);
            mTvError.setVisibility(View.GONE);
            mRvContent.setAdapter(mAdapter);
            mAdapter.replaceData(associationList);
            mRvContent.setPullLoadMoreCompleted();
            setLoadingIndicator(false);
        }
    }

    @Override
    public void setUserAssociationList(List<UserAssociationInfo> userAssociationList) {
        if (userAssociationList != null && userAssociationList.size() < 20) {
            mRvContent.setPushRefreshEnable(false);
        } else {
            mRvContent.setPushRefreshEnable(true);
        }
        if (mPi != 0) {
            if (userAssociationList != null) {
                mRvContent.setAdapter(mUserAssociationAdapter);
                mUserAssociationAdapter.addData(userAssociationList);
                mRvContent.setPullLoadMoreCompleted();
            }
        } else {
            mRvContent.setVisibility(View.VISIBLE);
            mTvError.setVisibility(View.GONE);
            mRvContent.setAdapter(mUserAssociationAdapter);
            mUserAssociationAdapter.replaceData(userAssociationList);
            mRvContent.setPullLoadMoreCompleted();
            setLoadingIndicator(false);
        }
    }

    @Override
    public void showSuccessfullyJoin(String succcessMsg) {

    }

    @Override
    public void showSuccessfullyApplyJoin(String succcessMsg) {

    }

    @Override
    public void showSuccessfullyPushPost(String succcessMsg) {

    }

    @Override
    public void setAssociationPostDetail(AssociationPostInfo associationPostInfo) {

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
