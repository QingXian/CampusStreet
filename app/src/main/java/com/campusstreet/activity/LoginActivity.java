package com.campusstreet.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.campusstreet.R;
import com.campusstreet.common.Const;
import com.campusstreet.contract.ILoginContract;
import com.campusstreet.entity.BountyHallInfo;
import com.campusstreet.entity.UserInfo;
import com.campusstreet.model.UserImpl;
import com.campusstreet.presenter.LoginPresenter;
import com.campusstreet.utils.PreferencesUtil;
import com.google.gson.GsonBuilder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static java.security.AccessController.getContext;

/**
 * Created by Orange on 2017/4/26.
 */

public class LoginActivity extends AppCompatActivity implements ILoginContract.View {

    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.tv_register)
    TextView mTvRegister;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.et_login_password)
    TextInputEditText mEtLoginPassword;
    @BindView(R.id.tv_forget_password)
    TextView mTvForgetPassword;
    @BindView(R.id.btn_login)
    Button mBtnLogin;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.progress_bar_title)
    TextView mProgressBarTitle;
    @BindView(R.id.progress_bar_container)
    LinearLayout mProgressBarContainer;
    @BindView(R.id.et_login_username)
    AutoCompleteTextView mEtLoginUsername;
    private ILoginContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
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
        new LoginPresenter(UserImpl.getInstance(getApplicationContext()), this);
    }

    @OnClick({R.id.tv_register, R.id.tv_forget_password, R.id.btn_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_register:
                Intent intent = new Intent(this, RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_forget_password:
                intent = new Intent(this, ForgetPasswordActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_login:
                mPresenter.onLogin(mEtLoginUsername.getText().toString(), mEtLoginPassword.getText().toString());
                break;
        }
    }

    @Override
    public void setPresenter(ILoginContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showSuccessfullyLogin(UserInfo userInfo) {
        showMessage("登陆成功");
        String userinfo = new GsonBuilder().create().toJson(userInfo, UserInfo.class);
        PreferencesUtil.getDefaultPreferences(this, Const.PREF_USER)
                .edit()
                .putString(Const.PREF_USERINFO_KEY, userinfo)
                .apply();

        // 设置返回的结果数据
        Intent data = new Intent(this, MainActivity.class);
        data.putExtra(Const.USERINFO_EXTRA, userInfo);
        data.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(data);
        this.finish();
    }

    @Override
    public void fetchCaptchaSuccessfull() {

    }

    @Override
    public void showSuccessfullyForgetPasswrod() {

    }

    @Override
    public void showErrorMsg(String errorMsg) {
        showMessage(errorMsg);
    }

    @Override
    public void setLoadingIndicator(boolean active) {
        if (mProgressBarContainer != null) {
            if (active) {
                //设置滚动条可见
                mProgressBarContainer.setVisibility(View.VISIBLE);
                mProgressBarTitle.setText(R.string.Login_progress_bar_title);
            } else {
                if (mProgressBarContainer.getVisibility() == View.VISIBLE) {
                    mProgressBarContainer.setVisibility(View.GONE);
                }
            }
        }
    }

    protected void showMessage(String msg) {
        if (this != null && !this.isFinishing()) {
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        }
    }
}