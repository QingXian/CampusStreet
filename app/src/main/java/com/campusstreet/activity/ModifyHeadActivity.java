package com.campusstreet.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.campusstreet.R;
import com.campusstreet.contract.ISettingContract;
import com.campusstreet.model.SettingImpl;
import com.campusstreet.presenter.SettingPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Orange on 2017/4/6.
 */

public class ModifyHeadActivity extends AppCompatActivity implements ISettingContract.View{
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.tv_toolbar_right)
    TextView mTvToolbarRight;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.iv_head)
    CircleImageView mIvHead;
    @BindView(R.id.btn_choice_head)
    Button mBtnChoiceHead;
    private ISettingContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_head);
        ButterKnife.bind(this);
        mToolbar.setTitle("");
        mToolbarTitle.setText(getString(R.string.act_modify_head_toolbar_title));
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
        new SettingPresenter(SettingImpl.getInstance(getApplicationContext()),this);
    }

    @OnClick({R.id.tv_toolbar_right, R.id.btn_choice_head})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_toolbar_right:
                break;
            case R.id.btn_choice_head:
                break;
        }
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

    }

    @Override
    public void setLoadingIndicator(boolean active) {

    }
}
