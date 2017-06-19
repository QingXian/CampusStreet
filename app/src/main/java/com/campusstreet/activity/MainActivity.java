package com.campusstreet.activity;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.campusstreet.R;
import com.campusstreet.common.Const;
import com.campusstreet.entity.UserInfo;
import com.campusstreet.fragment.FindFragment;
import com.campusstreet.fragment.HomeFragment;
import com.campusstreet.fragment.MessageFragment;
import com.campusstreet.fragment.ReleasePopupWindow;
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

import java.lang.reflect.Method;
import java.security.cert.CertPathValidatorException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.campusstreet.R.id.view;
import static com.campusstreet.utils.PermissionsManage.PERMISSIONCODE;
import static com.campusstreet.utils.PermissionsManage.SD;
import static com.campusstreet.utils.PermissionsManage.SD1;

public class MainActivity extends BaseActivity implements ReleasePopupWindow.OnItemClickListener {

    private static final String TAG = "MainActivity";
    @BindView(R.id.et_search)
    EditText mEtSearch;
    @BindView(R.id.toolbar_home)
    Toolbar mToolbarHome;
    private HomeFragment mHomeFragment;
    private MessageFragment mMessageFragment;
    private FindFragment mFindFragment;
    private UserFragment mUserFragment;
    private Drawable mTop;
    private long mCurrentTimeMillis;

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
    private int mType;

    private ReleasePopupWindow mPop;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mTvHome.setTextColor(getResources().getColor(R.color.colorPrimary));
        mTvHome.setSelected(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PermissionsManage.checkPermission(this, SD1);
        }

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
        mType = getIntent().getIntExtra(Const.TYPE, 0);
        if (mType == 1) {
            mUserFragment = mUserFragment.newInstance(mUserInfo);
            setFragment(mUserFragment);
            clearSeleted();
            mIvToolbarRight.setVisibility(View.VISIBLE);
            mToolbarHome.setVisibility(View.GONE);
            mToolbar.setVisibility(View.VISIBLE);
            mIvToolbarRight.setImageResource(R.drawable.ic_setting);
            mToolbarTitle.setText(getString(R.string.bot_tv_user));
            mTvUser.setTextColor(getResources().getColor(R.color.colorPrimary));
            mTvUser.setSelected(true);
        } else {
            mHomeFragment = mHomeFragment.newInstance(mUserInfo);
            setFragment(mHomeFragment);
            mToolbarHome.setVisibility(View.VISIBLE);
            mToolbar.setVisibility(View.GONE);
            new HomePresenter(HomeImpl.getInstance(getApplicationContext()), mHomeFragment);
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean hasAllGranted = true;
        for (int i = 0; i < grantResults.length; ++i) {
            if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                hasAllGranted = false;
                //在用户已经拒绝授权的情况下，如果shouldShowRequestPermissionRationale返回false则
                // 可以推断出用户选择了“不在提示”选项，在这种情况下需要引导用户至设置页手动授权
                if (!ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[i])) {
                    //解释原因，并且引导用户至设置页手动授权
                    new AlertDialog.Builder(this)
                            .setTitle("权限异常")
                            .setMessage("缺少这些权限将会使应用无法使用")
                            .setPositiveButton("去授权", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //引导用户至设置页手动授权
                                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                    Uri uri = Uri.fromParts("package", getApplicationContext().getPackageName(), null);
                                    intent.setData(uri);
                                    startActivity(intent);
                                }
                            })
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //引导用户手动授权，权限请求失败
                                    PermissionsManage.checkPermission(MainActivity.this, SD1);
                                }
                            }).setOnCancelListener(new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialog) {
                            //引导用户手动授权，权限请求失败
                            PermissionsManage.checkPermission(MainActivity.this, SD1);
                        }
                    }).show();

                } else {
                    //权限请求失败，但未选中“不再提示”选项
                    showMessage("缺少这些权限将会使应用无法使用");
                    PermissionsManage.checkPermission(this, SD1);
                }
                break;
            }
        }
        if (hasAllGranted) {
            //权限请求成功
        }
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
                if (!mToolbarTitle.getText().equals(getString(R.string.bot_tv_home))) {
                    mToolbarTitle.setText(getString(R.string.bot_tv_home));
                    clearSeleted();
                    mTvHome.setTextColor(getResources().getColor(R.color.colorPrimary));
                    mTvHome.setSelected(true);
                    mHomeFragment = mHomeFragment.newInstance(mUserInfo);
                    setFragment(mHomeFragment);
                    mIvToolbarRight.setVisibility(View.GONE);
                    new HomePresenter(HomeImpl.getInstance(getApplicationContext()), mHomeFragment);
                    mToolbarHome.setVisibility(View.VISIBLE);
                    mToolbar.setVisibility(View.GONE);
                }
                break;
            case R.id.tv_notice:
                if (!mToolbarTitle.getText().equals(getString(R.string.bot_tv_message))) {
                    if (mUserInfo == null) {
                        showMessage("请您先登录");
                    } else {
                        mToolbarTitle.setText(getString(R.string.bot_tv_message));
                        clearSeleted();
                        mTvNotice.setTextColor(getResources().getColor(R.color.colorPrimary));
                        mTvNotice.setSelected(true);
                        mMessageFragment = mMessageFragment.newInstance(mUserInfo);
                        setFragment(mMessageFragment);
                        mIvToolbarRight.setVisibility(View.GONE);
                        mToolbarHome.setVisibility(View.GONE);
                        mToolbar.setVisibility(View.VISIBLE);
                        new MessagePresenter(MessageImpl.getInstance(getApplicationContext()), mMessageFragment);
                    }
                }
                break;
            case R.id.tv_find:
                if (!mToolbarTitle.getText().equals(getString(R.string.bot_tv_find))) {
                    mToolbarTitle.setText(getString(R.string.bot_tv_find));
                    clearSeleted();
                    mTvFind.setTextColor(getResources().getColor(R.color.colorPrimary));
                    mTvFind.setSelected(true);
                    mFindFragment = mFindFragment.newInstance(mUserInfo);
                    setFragment(mFindFragment);
                    mIvToolbarRight.setVisibility(View.GONE);
                    mToolbarHome.setVisibility(View.GONE);
                    mToolbar.setVisibility(View.VISIBLE);
                    new FindPresenter(FindImpl.getInstance(getApplicationContext()), mFindFragment);
                }
                break;
            case R.id.tv_user:
                if (!mToolbarTitle.getText().equals(getString(R.string.bot_tv_user))) {
                    mToolbarTitle.setText(getString(R.string.bot_tv_user));
                    clearSeleted();
                    mTvUser.setTextColor(getResources().getColor(R.color.colorPrimary));
                    mTvUser.setSelected(true);
                    mUserFragment = mUserFragment.newInstance(mUserInfo);
                    setFragment(mUserFragment);
                    mIvToolbarRight.setVisibility(View.VISIBLE);
                    mToolbarHome.setVisibility(View.GONE);
                    mToolbar.setVisibility(View.VISIBLE);
                    mIvToolbarRight.setImageResource(R.drawable.ic_setting);
                }
                break;
            case R.id.iv_release:
                showPopupWindow();
//                if (mUserInfo != null) {
//                    Intent intent = new Intent(this, ReleaseLiveActivity.class);
//                    intent.putExtra(Const.USERINFO_EXTRA, mUserInfo);
//                    startActivity(intent);
//                } else {
//                    showMessage("请您先登录");
//                }
                break;
        }
    }

    private void showPopupWindow() {
        mPop = new ReleasePopupWindow(this);
        int dis_y = mIvRelease.getHeight() + 5;
        if (checkDeviceHasNavigationBar()) {
            dis_y = mIvRelease.getHeight() * 2 + 10;
        }
        mPop.showAtLocation(this.findViewById(R.id.bottom_navigation), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, dis_y);
        mPop.setUserInfo(mUserInfo);
        mPop.setOnItemClickListener(this);

    }

    public boolean checkDeviceHasNavigationBar() {
        boolean hasNavigationBar = false;
        Resources rs = getResources();
        int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
        if (id > 0) {
            hasNavigationBar = rs.getBoolean(id);
        }
        try {
            Class<?> systemPropertiesClass = Class.forName("android.os.SystemProperties");
            Method m = systemPropertiesClass.getMethod("get", String.class);
            String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
            if ("1".equals(navBarOverride)) {
                hasNavigationBar = false;
            } else if ("0".equals(navBarOverride)) {
                hasNavigationBar = true;
            }
        } catch (Exception e) {

        }
        return hasNavigationBar;
    }

    @Override
    public void setOnItemClick(View v) {
        switch (v.getId()) {
            case R.id.btn_release_bounty: {
                if (mUserInfo != null) {
                    Intent intent = new Intent(this, AddBountyHallActivity.class);
                    intent.putExtra(Const.USERINFO_EXTRA, mUserInfo);
                    startActivity(intent);
                } else {
                    showMessage("您还未登录");
                }
            }
            break;
            case R.id.btn_release_buy: {
                if (mUserInfo != null) {
                    Intent intent = new Intent(this, AddBuyZoneActivity.class);
                    intent.putExtra(Const.USERINFO_EXTRA, mUserInfo);
                    startActivity(intent);
                } else {
                    showMessage("您还未登录");
                }
            }
            break;
            case R.id.btn_release_idl: {
                if (mUserInfo != null) {
                    Intent intent = new Intent(this, AddIdleSaleActivity.class);
                    intent.putExtra(Const.USERINFO_EXTRA, mUserInfo);
                    startActivity(intent);
                } else {
                    showMessage("您还未登录");
                }
            }
            break;
            case R.id.btn_release_live: {
                if (mUserInfo != null) {
                    Intent intent = new Intent(this, ReleaseLiveActivity.class);
                    intent.putExtra(Const.USERINFO_EXTRA, mUserInfo);
                    startActivity(intent);
                } else {
                    showMessage("请您先登录");
                }
            }
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
    protected void onRestart() {
        super.onRestart();
        PermissionsManage.checkPermission(this, SD1);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Const.mIsLogout) {
            String userStr = PreferencesUtil.getDefaultPreferences(this, Const.PREF_USER)
                    .getString(Const.PREF_USERINFO_KEY, null);
            if (userStr == null) {
                mUserInfo = null;
            }
            mUserFragment = mUserFragment.newInstance(mUserInfo);
            setFragment(mUserFragment);
            Const.mIsLogout = false;
            clearSeleted();
            mIvToolbarRight.setVisibility(View.VISIBLE);
            mToolbarHome.setVisibility(View.GONE);
            mToolbar.setVisibility(View.VISIBLE);
            mIvToolbarRight.setImageResource(R.drawable.ic_setting);
            mToolbarTitle.setText(getString(R.string.bot_tv_user));
            mTvUser.setTextColor(getResources().getColor(R.color.colorPrimary));
            mTvUser.setSelected(true);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            long downTime = System.currentTimeMillis();
            if (downTime - mCurrentTimeMillis > 2000) {
                Toast.makeText(this, "再按一次返回键将退出应用", Toast.LENGTH_SHORT).show();
                mCurrentTimeMillis = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
