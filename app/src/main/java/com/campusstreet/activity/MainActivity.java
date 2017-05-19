package com.campusstreet.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.campusstreet.R;
import com.campusstreet.common.Const;
import com.campusstreet.entity.UserInfo;
import com.campusstreet.fragment.FindFragment;
import com.campusstreet.fragment.HomeFragment;
import com.campusstreet.fragment.MessageFragment;
import com.campusstreet.fragment.UserFragment;
import com.campusstreet.model.FindImpl;
import com.campusstreet.model.HomeImpl;
import com.campusstreet.model.MessageImpl;
import com.campusstreet.presenter.FindPresenter;
import com.campusstreet.presenter.HomePresenter;
import com.campusstreet.presenter.MessagePresenter;
import com.campusstreet.utils.PermissionsManage;
import com.campusstreet.utils.PreferencesUtil;
import com.google.gson.GsonBuilder;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    @BindView(R.id.scrollview)
    NestedScrollView mScrollview;
    @BindView(R.id.et_search)
    EditText mEtSearch;
    @BindView(R.id.toolbar_home)
    Toolbar mToolbarHome;
    private HomeFragment mHomeFragment;
    private MessageFragment mMessageFragment;
    private FindFragment mFindFragment;
    private UserFragment mUserFragment;
    private Drawable mTop;

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
    PermissionsManage mPermissionsManage;
    private UserInfo mUserInfo;
    private boolean mIsLogined = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mTvHome.setTextColor(getResources().getColor(R.color.colorPrimary));
        mTvHome.setSelected(true);
        PermissionsManage.verifyStoragePermissions(this);
        // 从登录成功后获取的用户信息
        if (getIntent() != null) {
            mUserInfo = (UserInfo) getIntent().getSerializableExtra(Const.USERINFO_EXTRA);
            if (mUserInfo != null) {
                Log.d(TAG, "登录成功获取的用户: mUserInfo <== " + mUserInfo.getUid());
                mIsLogined = true;
            }
        }
        //从持久化文件中获取的用户信息
        if (!mIsLogined && mUserInfo == null) {
            String userStr = PreferencesUtil.getDefaultPreferences(this, Const.PREF_USER)
                    .getString(Const.PREF_USERINFO_KEY, null);
            if (userStr != null) {
                mUserInfo = new GsonBuilder().setLenient().create().fromJson(userStr, UserInfo.class);
            }
        }
//        mHomeFragment = mHomeFragment.newInstance(mUserInfo);
//        setFragment(mHomeFragment);
        mHomeFragment = mHomeFragment.newInstance(mUserInfo);
        setFragment(mHomeFragment);
        mScrollview.smoothScrollTo(0, 0);
        mToolbarHome.setVisibility(View.VISIBLE);
        mToolbar.setVisibility(View.GONE);
        new HomePresenter(HomeImpl.getInstance(getApplicationContext()), mHomeFragment);
    }

    @OnClick({R.id.iv_toolbar_right, R.id.tv_home, R.id.tv_notice, R.id.tv_find, R.id.tv_user, R.id.iv_release})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_toolbar_right:
                if (mToolbarTitle.getText().equals(getString(R.string.bot_tv_user)) && mUserInfo != null) {
                    Intent intent = new Intent(this, UserSettingActivity.class);
                    intent.putExtra(Const.USERINFO_EXTRA, mUserInfo);
                    startActivity(intent);
                } else {
                    showMessage("请您先登录");
                }
                break;
            case R.id.tv_home:
                mToolbarTitle.setText(getString(R.string.bot_tv_home));
                clearSeleted();
                mTvHome.setTextColor(getResources().getColor(R.color.colorPrimary));
                mTvHome.setSelected(true);
                setFragment(mHomeFragment);
                mScrollview.smoothScrollTo(0, 0);
                mIvToolbarRight.setVisibility(View.GONE);
                new HomePresenter(HomeImpl.getInstance(getApplicationContext()), mHomeFragment);
                mToolbarHome.setVisibility(View.VISIBLE);
                mToolbar.setVisibility(View.GONE);
                break;
            case R.id.tv_notice:
                mToolbarTitle.setText(getString(R.string.bot_tv_message));
                clearSeleted();
                mTvNotice.setTextColor(getResources().getColor(R.color.colorPrimary));
                mTvNotice.setSelected(true);
                mMessageFragment = new MessageFragment();
                setFragment(mMessageFragment);
                mScrollview.smoothScrollTo(0, 0);
                mIvToolbarRight.setVisibility(View.GONE);
                mToolbarHome.setVisibility(View.GONE);
                mToolbar.setVisibility(View.VISIBLE);
                new MessagePresenter(MessageImpl.getInstance(getApplicationContext()), mMessageFragment);
                break;
            case R.id.tv_find:
                mToolbarTitle.setText(getString(R.string.bot_tv_find));
                clearSeleted();
                mTvFind.setTextColor(getResources().getColor(R.color.colorPrimary));
                mTvFind.setSelected(true);
                mFindFragment = new FindFragment();
                setFragment(mFindFragment);
                mScrollview.smoothScrollTo(0, 0);
                mIvToolbarRight.setVisibility(View.GONE);
                mToolbarHome.setVisibility(View.GONE);
                mToolbar.setVisibility(View.VISIBLE);
                new FindPresenter(FindImpl.getInstance(getApplicationContext()), mFindFragment);
                break;
            case R.id.tv_user:
                mToolbarTitle.setText(getString(R.string.bot_tv_user));
                clearSeleted();
                mTvUser.setTextColor(getResources().getColor(R.color.colorPrimary));
                mTvUser.setSelected(true);
                mUserFragment = mUserFragment.newInstance(mUserInfo);
                setFragment(mUserFragment);
                mScrollview.smoothScrollTo(0, 0);
                mIvToolbarRight.setVisibility(View.VISIBLE);
                mToolbarHome.setVisibility(View.GONE);
                mToolbar.setVisibility(View.VISIBLE);
                mIvToolbarRight.setImageResource(R.drawable.ic_setting);
                break;
            case R.id.iv_release:
                break;
        }
    }

    private void setFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.main_content, fragment).commit();
    }

    public void clearSeleted() {
        mTvHome.setTextColor(getResources().getColor(R.color.tv_color_defult));
        mTvNotice.setTextColor(getResources().getColor(R.color.tv_color_defult));
        mTvFind.setTextColor(getResources().getColor(R.color.tv_color_defult));
        mTvUser.setTextColor(getResources().getColor(R.color.tv_color_defult));
        mTvHome.setSelected(false);
        mTvNotice.setSelected(false);
        mTvFind.setSelected(false);
        mTvUser.setSelected(false);
    }

    @Override
    protected void onStart() {
        super.onStart();
//        mHomeFragment = mHomeFragment.newInstance(mUserInfo);
//        setFragment(mHomeFragment);
    }

    protected void showMessage(String msg) {
        if (this != null && !this.isFinishing()) {
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        }
    }
}
