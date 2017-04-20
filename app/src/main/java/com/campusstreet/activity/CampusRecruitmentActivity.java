package com.campusstreet.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.campusstreet.R;
import com.campusstreet.contract.ICampusRecruitmentContract;
import com.campusstreet.model.CampusRecruitmentImpl;
import com.campusstreet.presenter.CampusRecruitmentPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Orange on 2017/4/6.
 */

public class CampusRecruitmentActivity extends AppCompatActivity implements ICampusRecruitmentContract.View {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.rv_content)
    RecyclerView mRvContent;
    @BindView(R.id.et_search)
    EditText mEtSearch;
    @BindView(R.id.tv_toolbar_right)
    TextView mTvToolbarRight;
    ICampusRecruitmentContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campus_recruitment);
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
        initView();
        new CampusRecruitmentPresenter(CampusRecruitmentImpl.getInstance(getApplicationContext()),this);
    }

    private void initView() {
    }

    @OnClick(R.id.tv_toolbar_right)
    public void onClick() {
    }

    @Override
    public void setPresenter(ICampusRecruitmentContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void setCampusRecruitment() {

    }

    @Override
    public void showErrorMsg(String errorMsg) {

    }

    @Override
    public void setLoadingIndicator(boolean active) {

    }
}
