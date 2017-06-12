package com.campusstreet.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.campusstreet.R;
import com.campusstreet.adapter.PartnerRecyclerViewAdapter;
import com.campusstreet.common.Const;
import com.campusstreet.contract.IPartnerContract;
import com.campusstreet.entity.CategoriesInfo;
import com.campusstreet.entity.PartnerInfo;
import com.campusstreet.model.PartnerImpl;
import com.campusstreet.presenter.PartnerPresenter;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.R.attr.type;
import static com.campusstreet.R.id.view;

/**
 * Created by Orange on 2017/4/6.
 */

public class PartnerActivity extends BaseActivity implements IPartnerContract.View {

    @BindView(R.id.et_search)
    EditText mEtSearch;
    @BindView(R.id.tv_toolbar_right)
    TextView mTvToolbarRight;
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
    private int mPi = 0;
    private int mPostion = 0;
    private String[] mTitle;
    private IPartnerContract.Presenter mPresenter;
    private PartnerRecyclerViewAdapter mAdapter;
    private int[] mPostions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partner);
        ButterKnife.bind(this);
        mToolbar.setTitle("");
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
        new PartnerPresenter(PartnerImpl.getInstance(getApplicationContext()), this);
        initView();
        initEvent();
        mPresenter.fetchPartnerCategories();
        if (!mEtSearch.getText().equals(""))
            mPresenter.fetchPartnerList(mEtSearch.getText().toString(), mPostion, mPi);
        else
            mPresenter.fetchPartnerList(null, mPostion, mPi);
        setLoadingIndicator(true);
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
                        if (!mEtSearch.getText().equals(""))
                            mPresenter.fetchPartnerList(mEtSearch.getText().toString(), mPostion, mPi);
                        else
                            mPresenter.fetchPartnerList(null, mPostion, mPi);
                    }
                }, 1500);
            }

            @Override
            public void onLoadMore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (!mEtSearch.getText().equals(""))
                            mPresenter.fetchPartnerList(mEtSearch.getText().toString(), mPostion, ++mPi);
                        else
                            mPresenter.fetchPartnerList(null, mPostion, ++mPi);
                    }
                }, 1500);
            }
        });

        mEtSearch.setOnKeyListener(new View.OnKeyListener() {

            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    mPi = 0;
                    if (!mEtSearch.getText().equals(""))
                        mPresenter.fetchPartnerList(mEtSearch.getText().toString(), mPostion, mPi);
                    else
                        mPresenter.fetchPartnerList(null, mPostion, mPi);
                    setLoadingIndicator(true);
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm != null) {
                        imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
                    }
                    return true;
                }
                return false;
            }
        });
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
    public void setPartnerCategories(List<CategoriesInfo> categories) {
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
                    mEtSearch.setText("");
                    mPresenter.fetchPartnerList(null, mPostions[tab.getPosition()], 0);
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
}
