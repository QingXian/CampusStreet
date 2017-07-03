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
import com.squareup.picasso.Request;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.campusstreet.common.Const.REQUEST_CODE;

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
    @BindView(R.id.tv_show)
    TextView mTvShow;

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

    @OnClick({R.id.tv_signed})
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case  R.id.tv_signed:
                startActivityForResult(new Intent(this, ScannerActivity.class), REQUEST_CODE);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK){
            mTvShow.setText(data.getStringExtra("text")); // 显示识别到的文字
//            mWebView.loadUrl(data.getStringExtra("text")); // 将识别的内容当作网址加载到WebView
        }
    }




}
