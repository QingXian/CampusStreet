package com.campusstreet.activity;

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

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by develop2 on 2017/6/22.
 */

public class SeatsActivity extends BaseActivity {

    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.iv_toolbar_right)
    ImageView mIvToolbarRight;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tv_signed)
    TextView mTvSigned;
    @BindView(R.id.tv_release_seat)
    TextView mTvReleaseSeat;
    @BindView(R.id.tv_continue_seat)
    TextView mTvContinueSeat;
    @BindView(R.id.tv_pause_leave)
    TextView mTvPauseLeave;
    @BindView(R.id.tv_order_seat)
    TextView mTvOrderSeat;

    private UserInfo mUserInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seats);
        ButterKnife.bind(this);
        mToolbar.setTitle("");
        mToolbarTitle.setText("座位预约");
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




}
