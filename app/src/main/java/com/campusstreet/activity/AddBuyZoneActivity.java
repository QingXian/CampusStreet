package com.campusstreet.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.campusstreet.R;
import com.campusstreet.common.Const;
import com.campusstreet.contract.IBuyZoneContract;
import com.campusstreet.entity.BuyZoneInfo;
import com.campusstreet.entity.IdleSaleInfo;
import com.campusstreet.entity.LeaveMessageInfo;
import com.campusstreet.entity.UserInfo;
import com.campusstreet.model.BuyZoneImpl;
import com.campusstreet.presenter.BuyZonePresenter;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Orange on 2017/4/9.
 */

public class AddBuyZoneActivity extends AppCompatActivity implements IBuyZoneContract.View {
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.iv_toolbar_right)
    ImageView mIvToolbarRight;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.et_title)
    EditText mEtTitle;
    @BindView(R.id.et_price)
    EditText mEtPrice;
    @BindView(R.id.et_phone)
    EditText mEtPhone;
    @BindView(R.id.et_describe)
    EditText mEtDescribe;
    @BindView(R.id.btn_release)
    Button mBtnRelease;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.progress_bar_title)
    TextView mProgressBarTitle;
    @BindView(R.id.progress_bar_container)
    LinearLayout mProgressBarContainer;
    @BindView(R.id.et_qq)
    EditText mEtQq;
    private IBuyZoneContract.Presenter mPresenter;
    private UserInfo mUserInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_zone_add);
        ButterKnife.bind(this);
        mToolbar.setTitle("");
        mToolbarTitle.setText(getString(R.string.act_buy_zone_add_toolbar_title));
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
        new BuyZonePresenter(BuyZoneImpl.getInstance(getApplicationContext()), this);
    }

    @OnClick(R.id.btn_release)
    public void onViewClicked() {
      AddBuy();
    }

    private void AddBuy() {
        if (TextUtils.isEmpty(mEtTitle.getText().toString().trim())) {
            showMessage("请填写标题");
            return;
        }
        try {
            if (Integer.valueOf(mEtPrice.getText().toString().trim()) < 0) {
                showMessage("请填写大于0的价格");
                return;
            }

        } catch (NumberFormatException e) {
            showMessage("价格格式不正确");
            return;
        }
        if (TextUtils.isEmpty(mEtPhone.getText().toString().trim())) {
            showMessage("请填写联系方式");
            return;
        }
        if (TextUtils.isEmpty(mEtDescribe.getText().toString().trim())) {
            showMessage("请填写详细介绍");
            return;
        }

        BuyZoneInfo buyZoneinfo = new BuyZoneInfo();
        buyZoneinfo.setName(mEtTitle.getText().toString());
        buyZoneinfo.setMoney(mEtPrice.getText().toString());
        buyZoneinfo.setCon(mEtDescribe.getText().toString());
        buyZoneinfo.setMobile(mEtPhone.getText().toString());
        if (!TextUtils.isEmpty(mEtQq.getText().toString().trim())) {
            buyZoneinfo.setQq(mEtQq.getText().toString());
        }
        buyZoneinfo.setUid(mUserInfo.getUid());
        mPresenter.pushBuy(buyZoneinfo);
    }

    @Override
    public void setPresenter(IBuyZoneContract.Presenter presenter) {
        mPresenter = presenter;
    }


    @Override
    public void setBuyZone(List<BuyZoneInfo> buyZoneInfoList) {

    }

    @Override
    public void setBuyZoneMessageList(List<LeaveMessageInfo> BuyZoneMessageList) {

    }

    @Override
    public void showErrorMsg(String errorMsg) {
        showMessage(errorMsg);
    }

    @Override
    public void showSuccessfullyPush(String succcessMsg) {
        showMessage(succcessMsg);
        Intent intent = new Intent(this, BuyZoneActivity.class);
        startActivity(intent);
        this.finish();
    }

    @Override
    public void showSuccessfullyleaveMessage(String succcessMsg) {//忽略
    }

    @Override
    public void setLoadingIndicator(boolean active) {
        if (mProgressBarContainer != null) {
            if (active) {
                //设置滚动条可见
                mProgressBarContainer.setVisibility(View.VISIBLE);
                mProgressBarTitle.setText(R.string.pushing_progress_bar_title);
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
