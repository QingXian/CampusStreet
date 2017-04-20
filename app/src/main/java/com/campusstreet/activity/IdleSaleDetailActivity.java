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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.campusstreet.R;
import com.campusstreet.common.AppConfig;
import com.campusstreet.common.Const;
import com.campusstreet.entity.IdleSaleInfo;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Orange on 2017/4/7.
 */

public class IdleSaleDetailActivity extends AppCompatActivity {
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
    @BindView(R.id.tv_introduce)
    TextView mTvIntroduce;
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
        initView();
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
        mTvIntroduce.setText(mIdleSaleInfo.getSelltype());
        mTvTime.setText(mIdleSaleInfo.getGpublishtime());
    }


    @OnClick(R.id.btn_send_message)
    public void onClick() {
    }
}
