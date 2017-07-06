package com.campusstreet.activity;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.os.Bundle;
import android.renderscript.Long2;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.campusstreet.R;
import com.campusstreet.adapter.SeatsSelectRecyclerViewAdapter;
import com.campusstreet.common.Const;
import com.campusstreet.entity.UserInfo;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by develop2 on 2017/7/5.
 */

public class SeatsSelectTimeActivity extends BaseActivity {

    List<View> startList;
    List<View> durationList;

    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.tv_toolbar_right)
    TextView mTvToolbarRight;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.progress_bar_title)
    TextView mProgressBarTitle;

    @BindView(R.id.tv_time_8)
    TextView mTvTime8;
    @BindView(R.id.tv_time_14)
    TextView mTvTime14;
    @BindView(R.id.tv_time_20)
    TextView mTvTime20;
    @BindView(R.id.tv_long_1)
    TextView mTvLong1;
    @BindView(R.id.tv_long_2)
    TextView mTvLong2;
    @BindView(R.id.tv_long_3)
    TextView mTvLong3;

    private int mIndex;
    private String mCurVenue;
    private UserInfo mUserInfo;
    SeatsSelectRecyclerViewAdapter mAdapter;
    private String mStartTime;
    private int mDuration=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seats_select_time);
        ButterKnife.bind(this);
        mToolbar.setTitle("");
        mToolbarTitle.setText("时间选择");
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
        mUserInfo = (UserInfo) getIntent().getSerializableExtra(Const.USERINFO_EXTRA);
        initEvent();
    }
    private void initEvent()
    {
        startList = new ArrayList<>();
        startList.add(mTvTime8);
        startList.add(mTvTime14);
        startList.add(mTvTime20);

        durationList = new ArrayList<>();
        durationList.add(mTvLong1);
        durationList.add(mTvLong2);
        durationList.add(mTvLong3);
    }

    @OnClick({R.id.tv_time_8 ,R.id.tv_time_14, R.id.tv_time_20 ,R.id.tv_long_1, R.id.tv_long_2, R.id.tv_long_3, R.id.tv_toolbar_right})
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.tv_time_8:
                mStartTime = "08/00/00";
                resetSelectedStart(R.id.tv_time_8);
                break;
            case R.id.tv_time_14:
                mStartTime = "14/00/00";
                resetSelectedStart(R.id.tv_time_14);
                break;
            case R.id.tv_time_20:
                mStartTime = "20/00/00";
                resetSelectedStart(R.id.tv_time_20);
                break;
            case R.id.tv_long_1:
                mDuration = 1;
                resetSelectedDuration(R.id.tv_long_1);
                break;
            case R.id.tv_long_2:
                mDuration = 2;
                resetSelectedDuration(R.id.tv_long_2);
                break;
            case R.id.tv_long_3:
                mDuration = 3;
                resetSelectedDuration(R.id.tv_long_3);
                break;
            case R.id.tv_toolbar_right: {
                if (mStartTime == null)
                {
                    Toast.makeText(this,"请选择开始时间",Toast.LENGTH_SHORT).show();
                    break;
                }
                if (mDuration == 0)
                {
                    Toast.makeText(this,"请选择时长",Toast.LENGTH_SHORT).show();
                    break;
                }
                this.finish();
            }
                break;
            default:
                break;

        }
    }

    private void resetSelectedStart(int selectId)
    {
        for (int idx=0; idx<startList.size(); idx++)
        {
            TextView textView =(TextView)startList.get(idx);
            textView.setBackgroundResource(R.drawable.bg_act_bounty_hall_real_name_authentication);
            if (textView.getId() == selectId)
            {
                textView.setBackgroundResource(R.drawable.bg_act_selected_btn);
            }
        }
    }
    private void resetSelectedDuration(int selectId)
    {
        for (int idx=0; idx<durationList.size(); idx++)
        {
            TextView textView =(TextView)durationList.get(idx);
            textView.setBackgroundResource(R.drawable.bg_act_bounty_hall_real_name_authentication);
            if (textView.getId() == selectId)
            {
                textView.setBackgroundResource(R.drawable.bg_act_selected_btn);
            }
        }
    }



}
