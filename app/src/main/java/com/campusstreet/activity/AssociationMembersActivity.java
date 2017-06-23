package com.campusstreet.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.campusstreet.R;
import com.campusstreet.adapter.AssociationMembersRecyclerViewAdapter;
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
import de.hdodenhof.circleimageview.CircleImageView;

import static com.campusstreet.common.Const.USERINFO_EXTRA;

/**
 * Created by develop2 on 2017/6/23.
 */

public class AssociationMembersActivity extends BaseActivity implements IAssociationContract.View{
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
    private int mPi = 0;
    private UserInfo mUserInfo;
    private IAssociationContract.Presenter mPresenter;
    private AssociationInfo mAssociationInfo;
    private UserAssociationInfo mUserAssociationInfo;
    AssociationMembersRecyclerViewAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_association_members);
        ButterKnife.bind(this);
        mToolbar.setTitle("");
        mToolbarTitle.setText(getString(R.string.act_association_members_title));
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
        new AssociationPresenter(AssociationImpl.getInstance(getApplicationContext()), this);
        mUserInfo = (UserInfo) getIntent().getSerializableExtra(USERINFO_EXTRA);
        mAssociationInfo = (AssociationInfo) getIntent().getSerializableExtra(Const.ASSOCIATIONINFO_EXTRA);
        mUserAssociationInfo = (UserAssociationInfo) getIntent().getSerializableExtra(Const.USERASSOCIATIONINFO_EXTRA);
        initView();
        initEvent();
        mPresenter.fetchAssociationNumList(mUserAssociationInfo.getAssnid(),mPi,20);
        setLoadingIndicator(true);
    }

    private void initView()
    {
        mRvContent.setLinearLayout();
        mAdapter = new AssociationMembersRecyclerViewAdapter(this,null);
        mRvContent.setAdapter(mAdapter);
    }

    private void initEvent()
    {
        mRvContent.setOnPullLoadMoreListener(new PullLoadMoreRecyclerView.PullLoadMoreListener() {
            @Override
            public void onRefresh() {
                mPi = 0;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPresenter.fetchAssociationNumList(mUserAssociationInfo.getAssnid(),mPi,20);
                    }
                }, 1500);
            }

            @Override
            public void onLoadMore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPresenter.fetchAssociationNumList(mUserAssociationInfo.getAssnid(),mPi,20);
                    }
                }, 500);
            }
        });
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
        if (associationNumList != null && associationNumList.size() < 20) {
            mRvContent.setPushRefreshEnable(false);
        } else {
            mRvContent.setPushRefreshEnable(true);
        }
        if (mPi != 0) {
            if (associationNumList != null) {
                mAdapter.addData(associationNumList);
                mRvContent.setPullLoadMoreCompleted();
            }
        } else {
            mRvContent.setVisibility(View.VISIBLE);
            mTvError.setVisibility(View.GONE);
            mAdapter.replaceData(associationNumList);
            mRvContent.setPullLoadMoreCompleted();
            setLoadingIndicator(false);
        }
    }

    @Override
    public void setAssociationList(List<AssociationInfo> associationList) {

    }

    @Override
    public void setUserAssociationList(List<UserAssociationInfo> UserAssociationList) {

    }

    @Override
    public void showSuccessfullyJoin(String succcessMsg) {

    }

    @Override
    public void showSuccessfullyAddNotice(String succcessMsg) {

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
