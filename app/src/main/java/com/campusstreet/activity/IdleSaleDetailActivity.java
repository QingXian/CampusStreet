package com.campusstreet.activity;

import android.content.Context;
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
import com.campusstreet.contract.IIdleSaleContract;
import com.campusstreet.entity.IdleSaleInfo;
import com.campusstreet.entity.LeaveMessageInfo;
import com.campusstreet.model.IdleSaleImpl;
import com.campusstreet.presenter.IdleSalePresenter;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Orange on 2017/4/7.
 */

public class IdleSaleDetailActivity extends AppCompatActivity implements IIdleSaleContract.View {
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.iv_photo)
    ImageView mIvPhoto;
    @BindView(R.id.tv_symbol)
    TextView mTvSymbol;
    @BindView(R.id.tv_price)
    TextView mTvPrice;
    @BindView(R.id.tv_time_hint)
    TextView mTvTimeHint;
    @BindView(R.id.tv_time)
    TextView mTvTime;
    @BindView(R.id.tv_type)
    TextView mTvType;
    @BindView(R.id.rl_commodity_content)
    RelativeLayout mRlCommodityContent;
    @BindView(R.id.iv_head)
    CircleImageView mIvHead;
    @BindView(R.id.tv_tradetype)
    TextView mTvTradetype;
    @BindView(R.id.tv_place)
    TextView mTvPlace;
    @BindView(R.id.tv_phone_hint)
    TextView mTvPhoneHint;
    @BindView(R.id.tv_phone)
    TextView mTvPhone;
    @BindView(R.id.rv_content)
    RecyclerView mRvContent;
    @BindView(R.id.et_message)
    EditText mEtMessage;
    @BindView(R.id.btn_send_message)
    Button mBtnSendMessage;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_degree)
    TextView mTvDegree;
    @BindView(R.id.tv_content)
    TextView mTvContent;
    @BindView(R.id.tv_qq)
    TextView mTvQq;
    @BindView(R.id.tv_qq_hint)
    TextView mTvQqHint;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.progress_bar_title)
    TextView mProgressBarTitle;
    @BindView(R.id.progress_bar_container)
    LinearLayout mProgressBarContainer;
    @BindView(R.id.tv_selltype)
    TextView mTvSelltype;
    private IIdleSaleContract.Presenter mPresenter;
    private LeaveMessageRecycleViewAdapter mAdapter;
    private IdleSaleInfo mIdleSaleInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_idle_sale_detail);
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
        mIdleSaleInfo = (IdleSaleInfo) getIntent().getSerializableExtra(Const.IDLESALEINFO_EXTRA);
        new IdleSalePresenter(IdleSaleImpl.getInstance(getApplicationContext()), this);
        initView();
        setLoadingIndicator(true);
        mPresenter.fetchIdleSaleMessageList(mIdleSaleInfo.getId(), 1);
    }

    private void initView() {
        Picasso.with(this)
                .load(AppConfig.PIC_SERVER_HOST + mIdleSaleInfo.getCoverimage())
                .placeholder(R.drawable.ic_base_picture)
                .fit()
                .error(R.drawable.ic_pic_error)
                .into(mIvPhoto);
        mToolbarTitle.setText(mIdleSaleInfo.getName());
        mTvPrice.setText(mIdleSaleInfo.getMoney());
        mTvName.setText(mIdleSaleInfo.getUsername());
        Picasso.with(this)
                .load(AppConfig.PIC_SERVER_HOST + mIdleSaleInfo.getUserpic())
                .fit()
                .into(mIvHead);
        mTvDegree.setText(mIdleSaleInfo.getBewrite());
        mTvContent.setText(mIdleSaleInfo.getContent());

        mTvSelltype.setText(mIdleSaleInfo.getSelltype());
        mTvTime.setText(mIdleSaleInfo.getGpublishtime());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRvContent.setLayoutManager(linearLayoutManager);
        mAdapter = new LeaveMessageRecycleViewAdapter(this, null);
        mRvContent.setAdapter(mAdapter);
    }


    @OnClick(R.id.btn_send_message)
    public void onClick() {
        if (TextUtils.isEmpty(mEtMessage.getText().toString().trim())) {
            showMessage("请填写留言内容");
            return;
        }
        mPresenter.leaveMessage("Mw==", mIdleSaleInfo.getId(), mEtMessage.getText().toString().trim());
        setLoadingIndicator(true);
    }

    @Override
    public void setPresenter(IIdleSaleContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void setIdleSale(List<IdleSaleInfo> idleSaleInfoList) {
        //忽略
    }

    @Override
    public void setIdleSaleCategories() {//忽略
    }

    @Override
    public void setIdleSaleMessageList(List<LeaveMessageInfo> idleSaleMessageList) {
        mAdapter.replaceData(idleSaleMessageList);
    }


    @Override
    public void showErrorMsg(String errorMsg) {
        Toast.makeText(this, errorMsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showSuccessfullyPush(String succcessMsg) {//忽略
    }

    @Override
    public void showSuccessfullyleaveMessage(String succcessMsg) {
        Toast.makeText(this, succcessMsg, Toast.LENGTH_SHORT).show();
        mPresenter.fetchIdleSaleMessageList(mIdleSaleInfo.getId(), 1);
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(),
                    0);
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
