package com.campusstreet.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.campusstreet.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Orange on 2017/4/11.
 */

public class PeripheralShopDetailActivity extends AppCompatActivity {
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
    @BindView(R.id.tv_type)
    TextView mTvType;
    @BindView(R.id.ll_shop_content)
    LinearLayout mLlShopContent;
    @BindView(R.id.rl_commodity_content)
    RelativeLayout mRlCommodityContent;

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

    }
}
