package com.campusstreet.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.campusstreet.R;
import com.campusstreet.adapter.BountyHallDetailRecyclerViewAdapter;
import com.campusstreet.common.AppConfig;
import com.campusstreet.common.Const;
import com.campusstreet.contract.IBountyHallContract;
import com.campusstreet.entity.BountyHallInfo;
import com.campusstreet.entity.JoinInfo;
import com.campusstreet.entity.UserInfo;
import com.campusstreet.model.BountyHallImpl;
import com.campusstreet.presenter.BountyHallPresenter;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.campusstreet.common.Const.BOUNTYHALLINFO_EXTRA;
import static com.campusstreet.common.Const.ISSTRAT;
import static com.campusstreet.common.Const.JOINNOTPASS;
import static com.campusstreet.common.Const.JOINPASS;
import static com.campusstreet.common.Const.STARTTASK;
import static com.campusstreet.common.Const.TID_EXTRA;
import static com.campusstreet.common.Const.TYPE;
import static com.campusstreet.common.Const.USERINFO_EXTRA;

/**
 * Created by Orange on 2017/4/9.
 */

public class BountyHallDetailActivity extends AppCompatActivity implements IBountyHallContract.View {


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
    @BindView(R.id.iv_sex)
    ImageView mIvSex;
    @BindView(R.id.tv_real_name_authentication)
    TextView mTvRealNameAuthentication;
    @BindView(R.id.tv_task_num)
    TextView mTvTaskNum;
    @BindView(R.id.textView3)
    TextView mTextView3;
    @BindView(R.id.tv_skill_num)
    TextView mTvSkillNum;
    @BindView(R.id.tv_help_num)
    TextView mTvHelpNum;
    @BindView(R.id.tv_position)
    TextView mTvPosition;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_price)
    TextView mTvPrice;
    @BindView(R.id.tv_people_num)
    TextView mTvPeopleNum;
    @BindView(R.id.tv_time)
    TextView mTvTime;
    @BindView(R.id.tv_completion_time)
    TextView mTvCompletionTime;
    @BindView(R.id.tv_contact_people)
    TextView mTvContactPeople;
    @BindView(R.id.tv_describe)
    TextView mTvDescribe;
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.rv_content)
    RecyclerView mRvContent;
    @BindView(R.id.btn_entel)
    Button mBtnEntel;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.progress_bar_title)
    TextView mProgressBarTitle;
    @BindView(R.id.progress_bar_container)
    LinearLayout mProgressBarContainer;
    @BindView(R.id.tv_phone)
    TextView mTvPhone;
    private BountyHallInfo mBountyHallInfo;
    private IBountyHallContract.Presenter mPresenter;
    private BountyHallDetailRecyclerViewAdapter mAdapter;
    private int mPi = 0;
    private UserInfo mUserInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bounty_hall_detail);
        ButterKnife.bind(this);
        mToolbar.setTitle("");
        mToolbarTitle.setText(getString(R.string.act_bounty_hall_detail_toolbar_title));
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
        mBountyHallInfo = (BountyHallInfo) getIntent().getSerializableExtra(Const.BOUNTYHALLINFO_EXTRA);
        mUserInfo = (UserInfo) getIntent().getSerializableExtra(Const.USERINFO_EXTRA);
        new BountyHallPresenter(BountyHallImpl.getInstance(getApplicationContext()), this);
        initView();
        initEvent();

    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.fetchjoinTaskList(mBountyHallInfo.getId(), JOINNOTPASS, mPi);
        mPresenter.fetchjoinTaskList(mBountyHallInfo.getId(), JOINPASS, mPi);
    }

    private void initEvent() {
        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getText().equals("报名申请")) {
                    //获取报名的列表数据
                    mPresenter.fetchjoinTaskList(mBountyHallInfo.getId(), JOINNOTPASS, mPi);
                    mAdapter.replaceType(JOINNOTPASS);
                } else {
                    //获取通过报名的列表数据
                    mPresenter.fetchjoinTaskList(mBountyHallInfo.getId(), JOINPASS, mPi);
                    mAdapter.replaceType(JOINPASS);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        mAdapter.setOnItemClickListener(new BountyHallDetailRecyclerViewAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, JoinInfo JoinInfo, int type) {
                Intent intent = new Intent(BountyHallDetailActivity.this, RegistrationDetailActivity.class);
                intent.putExtra(Const.JOINNFO_EXTRA, JoinInfo);
                intent.putExtra(TYPE, type);
                intent.putExtra(BOUNTYHALLINFO_EXTRA, mBountyHallInfo);
                intent.putExtra(USERINFO_EXTRA, mUserInfo);
                startActivity(intent);
            }

        });
    }

    private void initView() {
        if (mBountyHallInfo.getFee().equals(0)) {
            mTvPrice.setText("面议");
        } else {
            double price = Double.parseDouble(mBountyHallInfo.getFee());
            DecimalFormat df = new DecimalFormat("0.00");
            String strprice = df.format(price);
            mTvPrice.setText(strprice);
        }
        mTvName.setText(mBountyHallInfo.getUsername());
        mTvTime.setText(mBountyHallInfo.getPubtime());
        mTvTitle.setText(mBountyHallInfo.getTitle());
        mTvPeopleNum.setText(String.valueOf(mBountyHallInfo.getSperson()));
        mTvCompletionTime.setText(mBountyHallInfo.getEndtime());
        mTvContactPeople.setText(mBountyHallInfo.getLinkman());
        mTvDescribe.setText(mBountyHallInfo.getCon());
        mTvPhone.setText(mBountyHallInfo.getMobile());
        Picasso.with(this)
                .load(AppConfig.AVATAR_SERVER_HOST + mBountyHallInfo.getUserpic())
                .fit()
                .into(mIvHead);
        mTabLayout.addTab(mTabLayout.newTab().setText("报名申请"));
        mTabLayout.addTab(mTabLayout.newTab().setText("已选择名单"));
        if (mUserInfo == null) {
            mBtnEntel.setVisibility(View.GONE);
            mTabLayout.setVisibility(View.GONE);
            mRvContent.setVisibility(View.GONE);
        } else {
            if (mUserInfo.getUid().equals(mBountyHallInfo.getUid())) {
                mTabLayout.setVisibility(View.VISIBLE);
                mRvContent.setVisibility(View.VISIBLE);
                if (mBountyHallInfo.getState() == 3) {
                    mBtnEntel.setVisibility(View.VISIBLE);
                    mBtnEntel.setText("完成任务");
                } else if (mBountyHallInfo.getState() == 1) {
                    mBtnEntel.setVisibility(View.GONE);
                } else if (mBountyHallInfo.getState() == 2) {
                    mBtnEntel.setVisibility(View.VISIBLE);
                    mBtnEntel.setEnabled(false);
                    mBtnEntel.setText("任务关闭");
                    mBtnEntel.setBackgroundColor(Color.parseColor("#f7f7f7"));
                } else if (mBountyHallInfo.getState() == 5) {
                    mBtnEntel.setVisibility(View.VISIBLE);
                    mBtnEntel.setEnabled(false);
                    mBtnEntel.setText("任务完成");
                    mBtnEntel.setBackgroundColor(Color.parseColor("#f7f7f7"));
                }
            } else {
                mTabLayout.setVisibility(View.GONE);
                mRvContent.setVisibility(View.GONE);
                if (mBountyHallInfo.getState() == 1) {
                    mBtnEntel.setVisibility(View.VISIBLE);
                    mBtnEntel.setText("报名");
                } else if (mBountyHallInfo.getState() == 3) {
                    mBtnEntel.setVisibility(View.VISIBLE);
                    mBtnEntel.setEnabled(false);
                    mBtnEntel.setText("任务进行中");
                    mBtnEntel.setBackgroundColor(Color.parseColor("#f7f7f7"));
                } else if (mBountyHallInfo.getState() == 2) {
                    mBtnEntel.setVisibility(View.VISIBLE);
                    mBtnEntel.setEnabled(false);
                    mBtnEntel.setText("任务关闭");
                    mBtnEntel.setBackgroundColor(Color.parseColor("#f7f7f7"));
                } else if (mBountyHallInfo.getState() == 5) {
                    mBtnEntel.setVisibility(View.VISIBLE);
                    mBtnEntel.setEnabled(false);
                    mBtnEntel.setText("任务完成");
                    mBtnEntel.setBackgroundColor(Color.parseColor("#f7f7f7"));
                }
            }
        }
        LinearLayoutManager gridLayoutManager = new LinearLayoutManager(this);
        mRvContent.setLayoutManager(gridLayoutManager);
        mAdapter = new BountyHallDetailRecyclerViewAdapter(this, null, 0);
        mRvContent.setAdapter(mAdapter);
    }


    @Override
    public void setPresenter(IBountyHallContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void setTaskList(List<BountyHallInfo> bountyHallInfos) {

    }

    @Override
    public void setUserTaskList(List<BountyHallInfo> bountyHallInfos) {

    }

    @Override
    public void setBountyHallCategories(String[] type) {

    }

    @Override
    public void setJoinTaskList(List<JoinInfo> joinInfos) {
        for (JoinInfo joinInfo :
                joinInfos) {
            if (mUserInfo != null) {
                if (joinInfo.getUid().equals(mUserInfo.getUid())) {
                    mBtnEntel.setText("已报名");
                    mBtnEntel.setBackgroundColor(Color.parseColor("#f7f7f7"));
                    mBtnEntel.setEnabled(false);
                }
            }
        }
        mAdapter.replaceData(joinInfos);

    }

    @Override
    public void showSuccessfullpassJoinTask() {

    }

    @Override
    public void showSuccessfullJointask(String successMsg) {

    }

    @Override
    public void showSuccessfullStartTask() {
    }

    @Override
    public void showErrorMsg(String errorMsg) {
        if (mUserInfo.getUid().equals(mBountyHallInfo.getUid())) {
            showMessage(errorMsg);
        }
    }

    @Override
    public void showNoPassMsg() {
        mBtnEntel.setVisibility(View.GONE);
    }

    @Override
    public void showSuccessfullyPush(String succcessMsg) {

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

    @OnClick(R.id.btn_entel)
    public void onViewClicked() {
        if (mBtnEntel.getText().toString().equals("报名")) {
            Intent intent = new Intent(this, RegistrationActivity.class);
            intent.putExtra(TID_EXTRA, mBountyHallInfo.getId());
            intent.putExtra(USERINFO_EXTRA, mUserInfo);
            startActivityForResult(intent, 1);
        } else {
            mPresenter.startTask(mUserInfo.getUid(), mBountyHallInfo.getId());
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            mBtnEntel.setText("已报名");
            mBtnEntel.setBackgroundColor(Color.parseColor("#f7f7f7"));
            mBtnEntel.setEnabled(false);
        }
    }
}
