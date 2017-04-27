package com.campusstreet.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.campusstreet.R;
import com.campusstreet.adapter.LeaveMessageRecycleViewAdapter;
import com.campusstreet.common.AppConfig;
import com.campusstreet.common.Const;
import com.campusstreet.contract.IBuyZoneContract;
import com.campusstreet.entity.BuyZoneInfo;
import com.campusstreet.entity.LeaveMessageInfo;
import com.campusstreet.model.BuyZoneImpl;
import com.campusstreet.presenter.BuyZonePresenter;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Orange on 2017/4/9.
 */

public class BuyZoneDetailActivity extends AppCompatActivity implements IBuyZoneContract.View {
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.iv_toolbar_right)
    ImageView mIvToolbarRight;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.iv_head)
    CircleImageView mIvHead;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.iv_privilege)
    ImageView mIvPrivilege;
    @BindView(R.id.tv_time)
    TextView mTvTime;
    @BindView(R.id.tv_time_hint)
    TextView mTvTimeHint;
    @BindView(R.id.tv_place_hint)
    TextView mTvPlaceHint;
    @BindView(R.id.tv_place)
    TextView mTvPlace;
    @BindView(R.id.tv_content)
    TextView mTvContent;
    @BindView(R.id.tv_expected_price_symbol)
    TextView mTvExpectedPriceSymbol;
    @BindView(R.id.tv_expected_price)
    TextView mTvExpectedPrice;
    @BindView(R.id.rl_buy_zone_content)
    RelativeLayout mRlBuyZoneContent;
    @BindView(R.id.tv_message_hint)
    TextView mTvMessageHint;
    @BindView(R.id.rv_content)
    RecyclerView mRvContent;
    @BindView(R.id.et_message)
    EditText mEtMessage;
    @BindView(R.id.btn_send_message)
    Button mBtnSendMessage;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.progress_bar_title)
    TextView mProgressBarTitle;
    @BindView(R.id.progress_bar_container)
    LinearLayout mProgressBarContainer;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_phone)
    TextView mTvPhone;
    @BindView(R.id.tv_qq)
    TextView mTvQq;
    @BindView(R.id.tv_qq_hint)
    TextView mTvQqHint;
    private IBuyZoneContract.Presenter mPresenter;
    private BuyZoneInfo mBuyZoneInfo;
    private int mPi = 0;
    private LeaveMessageRecycleViewAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_zone_detail);
        ButterKnife.bind(this);
        mToolbar.setTitle("");
        mToolbarTitle.setText(getString(R.string.act_detail));
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
        mBuyZoneInfo = (BuyZoneInfo) getIntent().getSerializableExtra(Const.BUYZONEIINFO_EXTRA);
        new BuyZonePresenter(BuyZoneImpl.getInstance(getApplicationContext()), this);
        initView();
        setLoadingIndicator(true);
        mPresenter.fetchBuyZoneMessageList(mBuyZoneInfo.getId(), mPi);
    }

    private void initView() {

        mTvExpectedPrice.setText(mBuyZoneInfo.getMoney());
        mTvName.setText(mBuyZoneInfo.getUsername());
        mTvTime.setText(mBuyZoneInfo.getPubtime());
        mTvTitle.setText(mBuyZoneInfo.getName());
        mTvContent.setText(mBuyZoneInfo.getCon());
        mTvPhone.setText(mBuyZoneInfo.getMoney());
        Picasso.with(this)
                .load(AppConfig.AVATAR_SERVER_HOST + mBuyZoneInfo.getUserpic())
                .fit()
                .into(mIvHead);
        if (mBuyZoneInfo.getQq() != null) {
            mTvQq.setVisibility(View.VISIBLE);
            mTvQq.setText(mBuyZoneInfo.getQq());
        }else{
            mTvQq.setVisibility(View.GONE);
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRvContent.setLayoutManager(linearLayoutManager);
        mAdapter = new LeaveMessageRecycleViewAdapter(this, null);
        mRvContent.setAdapter(mAdapter);
    }

    @OnClick(R.id.btn_send_message)
    public void onViewClicked() {
        if (TextUtils.isEmpty(mEtMessage.getText().toString().trim())) {
            showMessage("请填写留言内容");
            return;
        }
        mPresenter.leaveMessage("Mw==", mBuyZoneInfo.getId(), mEtMessage.getText().toString().trim());
        setLoadingIndicator(true);
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
        mAdapter.replaceData(BuyZoneMessageList);
    }

    @Override
    public void showErrorMsg(String errorMsg) {
        showMessage(errorMsg);
    }

    @Override
    public void showSuccessfullyPush(String succcessMsg) {
    }

    @Override
    public void showSuccessfullyleaveMessage(String succcessMsg) {
        showMessage(succcessMsg);
        mPresenter.fetchBuyZoneMessageList(mBuyZoneInfo.getId(), mPi);
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
        }
        mEtMessage.setText("");
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

    protected void showMessage(String msg) {
        if (this != null && !this.isFinishing()) {
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        }
    }
}
