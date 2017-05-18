package com.campusstreet.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.campusstreet.R;
import com.campusstreet.common.AppConfig;
import com.campusstreet.common.Const;
import com.campusstreet.contract.IPartnerContract;
import com.campusstreet.entity.PartnerInfo;
import com.campusstreet.model.PartnerImpl;
import com.campusstreet.presenter.PartnerPresenter;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Orange on 2017/4/10.
 */

public class PartnerDetailActivity extends AppCompatActivity implements IPartnerContract.View {
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.iv_toolbar_right)
    ImageView mIvToolbarRight;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tv_entry_name)
    TextView mTvEntryName;
    @BindView(R.id.tv_profile)
    TextView mTvProfile;
    @BindView(R.id.tv_company_name)
    TextView mTvCompanyName;
    @BindView(R.id.tv_type)
    TextView mTvType;
    @BindView(R.id.tv_phone)
    TextView mTvPhone;
    @BindView(R.id.tv_place)
    TextView mTvPlace;
    @BindView(R.id.tv_detail)
    TextView mTvDetail;
    @BindView(R.id.iv_pic)
    ImageView mIvPic;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.progress_bar_title)
    TextView mProgressBarTitle;
    @BindView(R.id.progress_bar_container)
    LinearLayout mProgressBarContainer;
    private IPartnerContract.Presenter mPresenter;
    private PartnerInfo mPartnerInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partner_detail);
        ButterKnife.bind(this);
        mToolbar.setTitle("");
        mToolbarTitle.setText(getString(R.string.act_partner_detail_toolbar_title));
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
        new PartnerPresenter(PartnerImpl.getInstance(getApplicationContext()), this);
        mPartnerInfo = (PartnerInfo) getIntent().getSerializableExtra(Const.PARTNERINFO_EXTRA);
//        mPresenter.fetchPartnerDetail();
        initView();
    }

    private void initView() {
        Picasso.with(this)
                .load(AppConfig.PIC_HOME_BANNER_SERVER_HOST + mPartnerInfo.getImg())
                .fit()
                .into(mIvPic);
        mTvEntryName.setText(mPartnerInfo.getName());
        mTvProfile.setText(mPartnerInfo.getSketch());
        mTvCompanyName.setText(mPartnerInfo.getOrganizer());

    }

    @Override
    public void setPartnerCategories(String[] type) {

    }

    @Override
    public void setPartnerList(List<PartnerInfo> partnerList) {

    }

    @Override
    public void setPartnerDetail(PartnerInfo partnerInfo) {
        mTvType.setText(partnerInfo.getNeedperson());
        mTvPhone.setText(partnerInfo.getMobile());
        mTvDetail.setText(partnerInfo.getSummary());
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
                mProgressBarTitle.setText(R.string.loading_progress_bar_title);
            } else {
                if (mProgressBarContainer.getVisibility() == View.VISIBLE) {
                    mProgressBarContainer.setVisibility(View.GONE);
                }
            }
        }
    }

    @Override
    public void setPresenter(IPartnerContract.Presenter presenter) {
        mPresenter = presenter;
    }

    protected void showMessage(String msg) {
        if (this != null && !this.isFinishing()) {
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        }
    }
}
