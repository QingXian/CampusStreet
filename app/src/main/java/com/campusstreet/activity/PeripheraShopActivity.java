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
import com.campusstreet.adapter.PeripheralShopRecyclerViewAdapter;
import com.campusstreet.common.Const;
import com.campusstreet.contract.IPeripheralShopContract;
import com.campusstreet.entity.PeripheralShopGoodInfo;
import com.campusstreet.entity.PeripheralShopInfo;
import com.campusstreet.model.PeripheralShopImpl;
import com.campusstreet.presenter.PeripheralShopPresenter;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.campusstreet.R.id.view;

/**
 * Created by Orange on 2017/4/6.
 */

public class PeripheraShopActivity extends AppCompatActivity implements IPeripheralShopContract.View {
    @BindView(R.id.et_search)
    EditText mEtSearch;
    @BindView(R.id.tv_toolbar_right)
    TextView mTvToolbarRight;
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
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    private IPeripheralShopContract.Presenter mPresenter;
    private PeripheralShopRecyclerViewAdapter mAdapter;
    private int mPi = 0;
    private int mPostion = 0;
    private String[] mTitle;
    private PeripheralShopInfo mPeripheralShopInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peripheral_shop);
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
        new PeripheralShopPresenter(PeripheralShopImpl.getInstance(getApplicationContext()), this);
        initView();
        initEvent();
        mPresenter.fetchPeriPheralShopCategories();
    }

    @Override
    protected void onStart() {
        mPi = 0;
        super.onStart();
        if (!mEtSearch.getText().equals(""))
            mPresenter.fetchPeriPheralShopList(mPostion, mEtSearch.getText().toString(), mPi);
        else
            mPresenter.fetchPeriPheralShopList(mPostion, null, mPi);
        setLoadingIndicator(true);
    }


    private void initView() {
        mRvContent.setLinearLayout();
        mAdapter = new PeripheralShopRecyclerViewAdapter(this, null);
        mRvContent.setAdapter(mAdapter);
    }

    private void initEvent() {
        mAdapter.setOnItemClickListener(new PeripheralShopRecyclerViewAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, PeripheralShopInfo peripheralShopInfo) {
                Intent intent = new Intent(PeripheraShopActivity.this, PeripheralShopDetailActivity.class);
                intent.putExtra(Const.PERIPHERASHOPINFO_EXTRA, peripheralShopInfo);
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
                            mPresenter.fetchPeriPheralShopList(mPostion, mEtSearch.getText().toString(), mPi);
                        else
                            mPresenter.fetchPeriPheralShopList(mPostion, null, mPi);
                    }
                }, 1500);
            }

            @Override
            public void onLoadMore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (!mEtSearch.getText().equals(""))
                            mPresenter.fetchPeriPheralShopList(mPostion, mEtSearch.getText().toString(), ++mPi);
                        else
                            mPresenter.fetchPeriPheralShopList(mPostion, null, ++mPi);
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
                        mPresenter.fetchPeriPheralShopList(mPostion, mEtSearch.getText().toString(), mPi);
                    else
                        mPresenter.fetchPeriPheralShopList(mPostion, null, mPi);
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


    @Override
    public void setPresenter(IPeripheralShopContract.Presenter presenter) {
        mPresenter = presenter;
    }


    @Override
    public void setPeriPheralShop(List<PeripheralShopInfo> peripheralShopInfos) {
        if (peripheralShopInfos != null && peripheralShopInfos.size() < 20) {
            mRvContent.setPushRefreshEnable(false);
        } else {
            mRvContent.setPushRefreshEnable(true);
        }
        if (mPi != 0) {
            if (peripheralShopInfos != null) {
                mAdapter.addData(peripheralShopInfos);
                mRvContent.setPullLoadMoreCompleted();
            }
        } else {
            mRvContent.setVisibility(View.VISIBLE);
            mTvError.setVisibility(View.GONE);
            mAdapter.replaceData(peripheralShopInfos);
            mRvContent.setPullLoadMoreCompleted();
            setLoadingIndicator(false);
        }
    }

    @Override
    public void setShopCommodityList(List<PeripheralShopGoodInfo> peripheralShopGoodInfos) {

    }

    @Override
    public void setPeriPheralShopCategories(String[] type) {
        mTitle = type;
        if (type != null) {
            mTabLayout.addTab(mTabLayout.newTab().setText("全部"));
            for (int i = 0; i < type.length; i++) {
                mTabLayout.addTab(mTabLayout.newTab().setText(type[i]));
            }
            mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    mPi = 0;
                    mEtSearch.setText("");
                    if (tab.getText().equals("全部")) {
                        mPresenter.fetchPeriPheralShopList(0, null, mPi);
                        mPostion = 0;
                    } else {
                        mPresenter.fetchPeriPheralShopList(tab.getPosition() + 1, null, mPi);
                        mPostion = tab.getPosition() + 1;
                    }
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

    protected void showMessage(String msg) {
        if (this != null && !this.isFinishing()) {
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        }
    }
}
