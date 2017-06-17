package com.campusstreet.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.campusstreet.R;
import com.campusstreet.adapter.IdleSaleRecyclerViewAdapter;
import com.campusstreet.common.Const;
import com.campusstreet.contract.IIdleSaleContract;
import com.campusstreet.entity.CategoriesInfo;
import com.campusstreet.entity.IdleSaleInfo;
import com.campusstreet.entity.LeaveMessageInfo;
import com.campusstreet.entity.UserInfo;
import com.campusstreet.model.IdleSaleImpl;
import com.campusstreet.presenter.IdleSalePresenter;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.R.attr.type;
import static com.campusstreet.common.Const.REQUEST_CODE;

/**
 * Created by Orange on 2017/4/6.
 */

public class IdleSaleActivity extends BaseActivity implements IIdleSaleContract.View {
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
    @BindView(R.id.btn_add)
    Button mBtnAdd;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.progress_bar_title)
    TextView mProgressBarTitle;
    @BindView(R.id.progress_bar_container)
    LinearLayout mProgressBarContainer;
    @BindView(R.id.tv_error)
    TextView mTvError;
    private IIdleSaleContract.Presenter mPresenter;
    private IdleSaleRecyclerViewAdapter mAdapter;
    private int mPi = 0;
    private int mPostion = 0;
    private int[] mPostions;
    private String[] mTitle;
    private UserInfo mUserInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_idle_sale);
        ButterKnife.bind(this);
        mToolbar.setTitle("");
        mToolbarTitle.setText(getString(R.string.frag_home_idle_sale));
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
        mBtnAdd.setVisibility(View.GONE);
        mIvToolbarRight.setVisibility(View.VISIBLE);
        mIvToolbarRight.setImageResource(R.drawable.ic_add);
        new IdleSalePresenter(IdleSaleImpl.getInstance(getApplicationContext()), this);
        mUserInfo = (UserInfo) getIntent().getSerializableExtra(Const.USERINFO_EXTRA);
        initView();
        initEvent();
        mPresenter.fetchIdleSaleCategories();
        mPresenter.fetchIdleSaleList(mPostion, mPi);
        setLoadingIndicator(true);
    }


    private void initEvent() {
        mAdapter.setOnItemClickListener(new IdleSaleRecyclerViewAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, IdleSaleInfo idleSaleInfo) {
                Intent intent = new Intent(IdleSaleActivity.this, IdleSaleDetailActivity.class);
                intent.putExtra(Const.IDLESALEINFO_EXTRA, idleSaleInfo);
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
                        mPresenter.fetchIdleSaleList(mPostion, mPi);
                    }
                }, 1500);
            }

            @Override
            public void onLoadMore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPresenter.fetchIdleSaleList(mPostion, ++mPi);
                    }
                }, 500);
            }
        });
    }


    private void initView() {
        mRvContent.setGridLayout(2);
        mAdapter = new IdleSaleRecyclerViewAdapter(this, null);
        mRvContent.setAdapter(mAdapter);
    }


    @OnClick({R.id.iv_toolbar_right, R.id.btn_add})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_toolbar_right:
                if (mUserInfo != null) {
                    Intent intent = new Intent(this, AddIdleSaleActivity.class);
                    intent.putExtra(Const.USERINFO_EXTRA, mUserInfo);
                    startActivityForResult(intent, REQUEST_CODE);
                } else {
                    showMessage("您还未登录");
                }
                break;
            case R.id.btn_add:
                break;
        }
    }

    @Override
    public void setPresenter(IIdleSaleContract.Presenter presenter) {
        mPresenter = presenter;
    }


    @Override
    public void setIdleSale(List<IdleSaleInfo> idleSaleInfoList) {
        if (idleSaleInfoList != null && idleSaleInfoList.size() < 20) {
            mRvContent.setPushRefreshEnable(false);
        } else {
            mRvContent.setPushRefreshEnable(true);
        }
        if (mPi != 0) {
            if (idleSaleInfoList != null) {
                mAdapter.addData(idleSaleInfoList);
                mRvContent.setPullLoadMoreCompleted();
            }
        } else {
            mRvContent.setVisibility(View.VISIBLE);
            mTvError.setVisibility(View.GONE);
            mAdapter.replaceData(idleSaleInfoList);
            mRvContent.setPullLoadMoreCompleted();
            setLoadingIndicator(false);
        }
    }

    @Override
    public void setIdleSaleCategories(List<CategoriesInfo> categories) {
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
                    mPresenter.fetchIdleSaleList(mPostions[tab.getPosition()], mPi);
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
    public void setIdleSaleMessageList(List<LeaveMessageInfo> idleSaleMessageList) {

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
    public void showSuccessfullyPush(String succcessMsg) {

    }

    @Override
    public void showSuccessfullyleaveMessage(String succcessMsg) {

    }

    @Override
    public void setIdleSaleDetail(IdleSaleInfo idleSaleInfo) {

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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            mPi = 0;
            mPresenter.fetchIdleSaleList(mPostion, mPi);
            setLoadingIndicator(true);
        }
    }
}
