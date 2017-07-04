package com.campusstreet.activity;

/**
 * Created by develop2 on 2017/7/3.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.campusstreet.R;
import com.campusstreet.common.Const;
import com.campusstreet.entity.UserInfo;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.campusstreet.common.Const.REQUEST_CODE;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.campusstreet.R;
import com.campusstreet.common.AppConfig;
import com.campusstreet.common.Const;
import com.campusstreet.entity.UserInfo;
import com.campusstreet.utils.PreferencesUtil;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Request;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.campusstreet.common.Const.REQUEST_CODE;

/**
 * Created by develop2 on 2017/6/22.
 */

public class SeatsSelectActivity extends BaseActivity {

    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.iv_toolbar_right)
    ImageView mIvToolbarRight;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tv_venue)
    TextView mTvVenue;
    @BindView(R.id.btn_venue)
    Button mBtnVennue;
    @BindView(R.id.rv_content)
    PullLoadMoreRecyclerView mRvContent;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.progress_bar_title)
    TextView mProgressBarTitle;


    private UserInfo mUserInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seats_select);
        ButterKnife.bind(this);
        mToolbar.setTitle("");
        mToolbarTitle.setText("座位选择");
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

    }

    private void initView()
    {
        mRvContent.setGridLayout(2);
    }

    @OnClick({R.id.tv_signed})
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case  R.id.tv_signed:
                break;
        }
    }




}