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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.campusstreet.R;
import com.campusstreet.common.Const;
import com.campusstreet.contract.ISettingContract;
import com.campusstreet.entity.UserInfo;
import com.campusstreet.model.SettingImpl;
import com.campusstreet.presenter.SettingPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Orange on 2017/4/6.
 */

public class ModifyDepartmentActivity extends AppCompatActivity implements ISettingContract.View {
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.iv_toolbar_right)
    ImageView mIvToolbarRight;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.btn_save)
    Button mBtnSave;
    ISettingContract.Presenter mPresenter;
    @BindView(R.id.tv_department)
    TextView mTvDepartment;
    @BindView(R.id.btn_department)
    Button mBtnDepartment;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.progress_bar_title)
    TextView mProgressBarTitle;
    @BindView(R.id.progress_bar_container)
    LinearLayout mProgressBarContainer;
    private int mIndex;
    private String mDepartment;
    private UserInfo mUserInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_department);
        ButterKnife.bind(this);
        mToolbar.setTitle("");
        mToolbarTitle.setText(getString(R.string.act_modify_department_toolbar_title));
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
        mTvDepartment.setText(mUserInfo.getMajor());
    }

    @OnClick({R.id.btn_save, R.id.tv_department, R.id.btn_department})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_save:
                modifyInfo();
                break;
            case R.id.tv_department:
                new AlertDialog.Builder(this)
                        .setTitle("请选择院系")
                        .setSingleChoiceItems(Const.DEPARTMENT, mIndex, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                mDepartment = Const.DEPARTMENT[i];
                                mTvDepartment.setText(Const.DEPARTMENT[i]);
                                dialogInterface.dismiss();
                            }
                        })
                        .create()
                        .show();
                break;
            case R.id.btn_department:
                new AlertDialog.Builder(this)
                        .setTitle("请选择院系")
                        .setSingleChoiceItems(Const.DEPARTMENT, mIndex, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                mDepartment = Const.DEPARTMENT[i];
                                mTvDepartment.setText(Const.DEPARTMENT[i]);
                                dialogInterface.dismiss();
                            }
                        })
                        .create()
                        .show();
                break;
        }
    }

    private void modifyInfo() {
        if (!mTvDepartment.getText().toString().trim().equals(mDepartment)) {
            showMessage("请选择院系");
            return;
        }
        mPresenter.reviseDepartment(mUserInfo.getUid(), mDepartment);
    }

    @Override
    public void setPresenter(ISettingContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showSuccessMsg(String successMsg) {
        showMessage(successMsg);
        mUserInfo.setUsername(mTvDepartment.getText().toString());
        Intent data = new Intent(this, MainActivity.class);
        data.putExtra(Const.USERINFO_EXTRA, mUserInfo);
        startActivity(data);
    }

    @Override
    public void showErrorMsg(String errorMsg) {
        showMessage(errorMsg);
    }

    @Override
    public void showSuccessfullyChangePassword() {

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

    protected void showMessage(String msg) {
        if (this != null && !this.isFinishing()) {
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        }
    }
}
