package com.campusstreet.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
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
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Orange on 2017/4/6.
 */

public class UserSettingActivity extends AppCompatActivity {
    public static boolean mIsLogout = false;

    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.iv_toolbar_right)
    ImageView mIvToolbarRight;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.rl_head)
    RelativeLayout mRlHead;
    @BindView(R.id.rl_name)
    RelativeLayout mRlName;
    @BindView(R.id.rl_department)
    RelativeLayout mRlDepartment;
    @BindView(R.id.rl_password)
    RelativeLayout mRlPassword;
    @BindView(R.id.iv_head)
    CircleImageView mIvHead;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_department)
    TextView mTvDepartment;
    @BindView(R.id.rl_logout)
    RelativeLayout mRlLogout;
    private UserInfo mUserInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_setting);
        ButterKnife.bind(this);
        mToolbar.setTitle("");
        mToolbarTitle.setText(getString(R.string.act_user_setting_toolbar_title));
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
        initView();

    }

    private void initView() {
        Picasso.with(this)
                .load(AppConfig.AVATAR_SERVER_HOST + mUserInfo.getUserpic())
                .fit()
                .error(R.drawable.ic_head_test)
                .into(mIvHead);
        mTvDepartment.setText(mUserInfo.getMajor());
        mTvName.setText(mUserInfo.getUsername());
    }

    @OnClick({R.id.rl_head, R.id.rl_name, R.id.rl_department, R.id.rl_password, R.id.rl_logout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_head:
                Intent intent = new Intent(this, ModifyHeadActivity.class);
                intent.putExtra(Const.USERINFO_EXTRA, mUserInfo);
                startActivity(intent);
                break;
            case R.id.rl_name:
                intent = new Intent(this, ModifyNickNameActivity.class);
                intent.putExtra(Const.USERINFO_EXTRA, mUserInfo);
                startActivity(intent);
                break;
            case R.id.rl_department:
                intent = new Intent(this, ModifyDepartmentActivity.class);
                intent.putExtra(Const.USERINFO_EXTRA, mUserInfo);
                startActivity(intent);
                break;
            case R.id.rl_password:
                intent = new Intent(this, ModifyPasswordActivity.class);
                intent.putExtra(Const.USERINFO_EXTRA, mUserInfo);
                startActivity(intent);
                break;
            case R.id.rl_logout:
                showAlertDialog("您确认要退出登录");
                break;
        }
    }

    private void showAlertDialog(String text) {
        final EditText et = new EditText(this);
        new AlertDialog.Builder(this).setTitle(text)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        PreferencesUtil.getDefaultPreferences(UserSettingActivity.this, Const.PREF_USER)
                                .edit()
                                .putString(Const.PREF_USERINFO_KEY, null)
                                .apply();
                        mIsLogout = true;
                        Intent intent = new Intent(UserSettingActivity.this, LoginActivity.class);
                        startActivity(intent);
                        UserSettingActivity.this.finish();
                    }
                })
                .setNegativeButton("取消", null)
                .show();
    }

}
