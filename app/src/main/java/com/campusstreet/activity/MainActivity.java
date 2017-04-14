package com.campusstreet.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.campusstreet.R;
import com.campusstreet.fragment.FindFragment;
import com.campusstreet.fragment.HomeFragment;
import com.campusstreet.fragment.MessageFragment;
import com.campusstreet.fragment.UserFragment;
import com.youth.banner.Banner;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private HomeFragment mHomeFragment;
    private MessageFragment mMessageFragment;
    private FindFragment mFindFragment;
    private UserFragment mUserFragment;

    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.main_content)
    FrameLayout mMainContent;
    @BindView(R.id.iv_toolbar_right)
    ImageView mIvToolbarRight;
    @BindView(R.id.tv_home)
    TextView mTvHome;
    @BindView(R.id.tv_notice)
    TextView mTvNotice;
    @BindView(R.id.tv_find)
    TextView mTvFind;
    @BindView(R.id.tv_user)
    TextView mTvUser;
    @BindView(R.id.iv_release)
    ImageView mIvRelease;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mHomeFragment = new HomeFragment();
        setFragment(mHomeFragment);

    }

    @OnClick({R.id.iv_toolbar_right, R.id.tv_home, R.id.tv_notice, R.id.tv_find, R.id.tv_user, R.id.iv_release})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_toolbar_right:
                if (mToolbarTitle.getText().equals(getString(R.string.bot_tv_user))){
                    Intent intent = new Intent(this,UserSettingActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.tv_home:
                mToolbarTitle.setText(getString(R.string.bot_tv_home));
                setFragment(mHomeFragment);
                mIvToolbarRight.setVisibility(View.GONE);
                break;
            case R.id.tv_notice:
                mToolbarTitle.setText(getString(R.string.bot_tv_message));
                mMessageFragment = new MessageFragment();
                setFragment(mMessageFragment);
                mIvToolbarRight.setVisibility(View.GONE);
                break;
            case R.id.tv_find:
                mToolbarTitle.setText(getString(R.string.bot_tv_find));
                mFindFragment = new FindFragment();
                setFragment(mFindFragment);
                mIvToolbarRight.setVisibility(View.GONE);
                break;
            case R.id.tv_user:
                mToolbarTitle.setText(getString(R.string.bot_tv_user));
                mUserFragment = new UserFragment();
                setFragment(mUserFragment);
                mIvToolbarRight.setVisibility(View.VISIBLE);
                mIvToolbarRight.setImageResource(R.drawable.ic_setting);
                break;
            case R.id.iv_release:
                break;
        }
    }
    private void setFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.main_content,fragment).commit();
    }
}
