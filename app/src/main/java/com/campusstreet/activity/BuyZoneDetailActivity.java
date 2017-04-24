package com.campusstreet.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.campusstreet.R;
import com.campusstreet.contract.IBuyZoneContract;
import com.campusstreet.entity.LeaveMessageInfo;
import com.campusstreet.model.BuyZoneImpl;
import com.campusstreet.presenter.BuyZonePresenter;

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
    private IBuyZoneContract.Presenter mPresenter;

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
        new BuyZonePresenter(BuyZoneImpl.getInstance(getApplicationContext()), this);

    }

    @OnClick(R.id.btn_send_message)
    public void onViewClicked() {
    }

    @Override
    public void setPresenter(IBuyZoneContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void setBuyZone() {

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
    }

    @Override
    public void showSuccessfullyleaveMessage(String succcessMsg) {
        showMessage(succcessMsg);
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
