package com.campusstreet.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.campusstreet.R;
import com.campusstreet.common.Const;
import com.campusstreet.contract.ISettingContract;
import com.campusstreet.entity.UserInfo;
import com.campusstreet.model.SettingImpl;
import com.campusstreet.presenter.SettingPresenter;
import com.campusstreet.utils.PreferencesUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Orange on 2017/5/6.
 */

public class ModifyPasswordActivity extends BaseActivity implements ISettingContract.View {
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.iv_toolbar_right)
    ImageView mIvToolbarRight;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.et_old_password)
    EditText mEtOldPassword;
    @BindView(R.id.et_new_password)
    EditText mEtNewPassword;
    @BindView(R.id.et_new_password_again)
    EditText mEtNewPasswordAgain;
    @BindView(R.id.btn_save)
    Button mBtnSave;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.progress_bar_title)
    TextView mProgressBarTitle;
    @BindView(R.id.progress_bar_container)
    LinearLayout mProgressBarContainer;
    private ISettingContract.Presenter mPresenter;
    private UserInfo mUserInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_password);
        ButterKnife.bind(this);
        mToolbar.setTitle("");
        mToolbarTitle.setText(getString(R.string.act_modify_password_toolbar_title));
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
        new SettingPresenter(SettingImpl.getInstance(getApplicationContext()), this);
        mUserInfo = (UserInfo) getIntent().getSerializableExtra(Const.USERINFO_EXTRA);
    }

    @Override
    public void setPresenter(ISettingContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showSuccessMsg(String successMsg) {

    }

    @Override
    public void showErrorMsg(String errorMsg) {
        showMessage(errorMsg);
    }

    @Override
    public void showSuccessfullyChangePassword() {
        showMessage("更改密码成功");
        PreferencesUtil.getDefaultPreferences(ModifyPasswordActivity.this, Const.PREF_USER)
                .edit()
                .putString(Const.PREF_USERINFO_KEY, null)
                .apply();
        Const.mIsLogout = true;
        Intent intent = new Intent(ModifyPasswordActivity.this, LoginActivity.class);
        startActivity(intent);
        ModifyPasswordActivity.this.finish();
    }

    @Override
    public void setLoadingIndicator(boolean active) {
        if (mProgressBarContainer != null) {
            if (active) {
                //设置滚动条可见
                mProgressBarContainer.setVisibility(View.VISIBLE);
                mProgressBarTitle.setText(R.string.Modifying_progress_bar_title);
            } else {
                if (mProgressBarContainer.getVisibility() == View.VISIBLE) {
                    mProgressBarContainer.setVisibility(View.GONE);
                }
            }
        }
    }

    @OnClick(R.id.btn_save)
    public void onViewClicked() {
        if (TextUtils.isEmpty(mEtOldPassword.getText().toString().trim())) {
            showMessage("请输入旧密码");
            return;
        }
        if (TextUtils.isEmpty(mEtNewPassword.getText().toString().trim())) {
            showMessage("请输入新密码");
            return;
        }
        if (!mEtNewPassword.getText().toString().trim().equals(mEtNewPasswordAgain.getText().toString().trim())) {
            showMessage("两次密码不一致");
            return;
        }
        mPresenter.changePassword(mUserInfo.getUid(), mEtOldPassword.getText().toString(), mEtNewPassword.getText().toString());
    }
}
