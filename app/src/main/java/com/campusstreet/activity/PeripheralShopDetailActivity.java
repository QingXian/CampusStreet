package com.campusstreet.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.campusstreet.R;
import com.campusstreet.adapter.PeripheralShopDetailRecyclerViewAdapter;
import com.campusstreet.common.AppConfig;
import com.campusstreet.common.Const;
import com.campusstreet.contract.IPeripheralShopContract;
import com.campusstreet.entity.CategoriesInfo;
import com.campusstreet.entity.PeripheralShopGoodInfo;
import com.campusstreet.entity.PeripheralShopInfo;
import com.campusstreet.model.PeripheralShopImpl;
import com.campusstreet.presenter.PeripheralShopPresenter;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Orange on 2017/4/11.
 */

public class PeripheralShopDetailActivity extends BaseActivity implements IPeripheralShopContract.View {
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.iv_toolbar_right)
    ImageView mIvToolbarRight;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.iv_photo)
    ImageView mIvPhoto;
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.tv_phone)
    TextView mTvPhone;
    @BindView(R.id.tv_position)
    TextView mTvPosition;
    @BindView(R.id.iv_star1)
    ImageView mIvStar1;
    @BindView(R.id.iv_star2)
    ImageView mIvStar2;
    @BindView(R.id.iv_star3)
    ImageView mIvStar3;
    @BindView(R.id.iv_star4)
    ImageView mIvStar4;
    @BindView(R.id.iv_star5)
    ImageView mIvStar5;
    @BindView(R.id.tv_grade)
    TextView mTvGrade;
    @BindView(R.id.ll_grade_content)
    LinearLayout mLlGradeContent;
    @BindView(R.id.tv_time)
    TextView mTvTime;
    @BindView(R.id.ll_shop_content)
    LinearLayout mLlShopContent;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.progress_bar_title)
    TextView mProgressBarTitle;
    @BindView(R.id.progress_bar_container)
    LinearLayout mProgressBarContainer;
    @BindView(R.id.rv_content)
    RecyclerView mRvContent;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.scrollView)
    NestedScrollView mScrollView;
    private int mPi = 0;
    private IPeripheralShopContract.Presenter mPresenter;
    private PeripheralShopInfo mPeripheralShopInfo;
    private PeripheralShopDetailRecyclerViewAdapter mAdapter;
    private boolean mIsLoading;
    private boolean mIsNeedLoadMore = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peripheral_shop_detail);
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
        mPeripheralShopInfo = (PeripheralShopInfo) getIntent().getSerializableExtra(Const.PERIPHERASHOPINFO_EXTRA);
        new PeripheralShopPresenter(PeripheralShopImpl.getInstance(getApplicationContext()), this);
        initView();
        initEvent();
    }


    private void initView() {
        mLlShopContent.setVisibility(View.VISIBLE);
        Picasso.with(this)
                .load(AppConfig.PIC_HOME_BANNER_SERVER_HOST + mPeripheralShopInfo.getImg())
                .fit()
                .into(mIvPhoto);
        mTvName.setText(mPeripheralShopInfo.getName());
        mTvGrade.setText(String.valueOf(mPeripheralShopInfo.getPoint()));
        mTvPosition.setText("地址：" + mPeripheralShopInfo.getAddress());
        mTvTime.setText("营业时间：" + mPeripheralShopInfo.getBhour());
        if (Math.floor(mPeripheralShopInfo.getPoint()) == 0) {
            mIvStar1.setImageResource(R.drawable.ic_star);
            mIvStar2.setImageResource(R.drawable.ic_star);
            mIvStar3.setImageResource(R.drawable.ic_star);
            mIvStar4.setImageResource(R.drawable.ic_star);
            mIvStar5.setImageResource(R.drawable.ic_star);
        } else if (Math.floor(mPeripheralShopInfo.getPoint()) == 1) {
            mIvStar1.setImageResource(R.drawable.ic_star_fill);
            mIvStar2.setImageResource(R.drawable.ic_star);
            mIvStar3.setImageResource(R.drawable.ic_star);
            mIvStar4.setImageResource(R.drawable.ic_star);
            mIvStar5.setImageResource(R.drawable.ic_star);
        } else if (Math.floor(mPeripheralShopInfo.getPoint()) == 2) {
            mIvStar1.setImageResource(R.drawable.ic_star_fill);
            mIvStar2.setImageResource(R.drawable.ic_star_fill);
            mIvStar3.setImageResource(R.drawable.ic_star);
            mIvStar4.setImageResource(R.drawable.ic_star);
            mIvStar5.setImageResource(R.drawable.ic_star);
        } else if (Math.floor(mPeripheralShopInfo.getPoint()) == 3) {
            mIvStar1.setImageResource(R.drawable.ic_star_fill);
            mIvStar2.setImageResource(R.drawable.ic_star_fill);
            mIvStar3.setImageResource(R.drawable.ic_star_fill);
            mIvStar4.setImageResource(R.drawable.ic_star);
            mIvStar5.setImageResource(R.drawable.ic_star);
        } else if (Math.floor(mPeripheralShopInfo.getPoint()) == 4) {
            mIvStar1.setImageResource(R.drawable.ic_star_fill);
            mIvStar2.setImageResource(R.drawable.ic_star_fill);
            mIvStar3.setImageResource(R.drawable.ic_star_fill);
            mIvStar4.setImageResource(R.drawable.ic_star_fill);
            mIvStar5.setImageResource(R.drawable.ic_star);
        } else if (Math.floor(mPeripheralShopInfo.getPoint()) == 5) {
            mIvStar1.setImageResource(R.drawable.ic_star_fill);
            mIvStar2.setImageResource(R.drawable.ic_star_fill);
            mIvStar3.setImageResource(R.drawable.ic_star_fill);
            mIvStar4.setImageResource(R.drawable.ic_star_fill);
            mIvStar5.setImageResource(R.drawable.ic_star_fill);
        }
        mTabLayout.addTab(mTabLayout.newTab().setText("店铺"));
        mTabLayout.addTab(mTabLayout.newTab().setText("商品"));
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        mRvContent.setLayoutManager(gridLayoutManager);
        mAdapter = new PeripheralShopDetailRecyclerViewAdapter(this, null);
        mRvContent.setAdapter(mAdapter);
    }

    private void initEvent() {
        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getText().equals("商品")) {
                    mPi = 0;
                    mPresenter.fetchShopCommodityList(mPeripheralShopInfo.getId(),0, null, mPi);
                    mLlShopContent.setVisibility(View.GONE);
                    mRvContent.setVisibility(View.VISIBLE);
                    setLoadingIndicator(true);
                } else {
                    mLlShopContent.setVisibility(View.VISIBLE);
                    mRvContent.setVisibility(View.GONE);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        mScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                View contentView = mScrollView.getChildAt(0);
                if (mIsNeedLoadMore && !mIsLoading && contentView.getMeasuredHeight() <= mScrollView.getScrollY() + mScrollView.getHeight()) {
                    mPresenter.fetchShopCommodityList(mPeripheralShopInfo.getId(),0, null, ++mPi);
                    mIsLoading = true;
                }

            }

        });
    }

    @Override
    public void setPresenter(IPeripheralShopContract.Presenter presenter) {
        mPresenter = presenter;
    }


    @Override
    public void setPeriPheralShop(List<PeripheralShopInfo> mPeripheralShopInfos) {

    }

    @Override
    public void setShopCommodityList(List<PeripheralShopGoodInfo> peripheralShopGoodInfos) {
        if (peripheralShopGoodInfos != null && peripheralShopGoodInfos.size() < 20) {
            mIsNeedLoadMore = false;
        } else {
            mIsNeedLoadMore = true;
        }
        if (mPi != 0) {
            if (peripheralShopGoodInfos != null) {
                mAdapter.addData(peripheralShopGoodInfos);
                mIsLoading = false;
            }
        } else {
            mAdapter.replaceData(peripheralShopGoodInfos);
            setLoadingIndicator(false);
        }
    }

    @Override
    public void setPeriPheralShopCategories(List<CategoriesInfo> categories) {

    }


    @Override
    public void showErrorMsg(String errorMsg) {
        if (mPi == 0) {
            showMessage(errorMsg);
        } else {
            showMessage("没有数据了");
            mIsLoading = false;
        }
        setLoadingIndicator(false);
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
