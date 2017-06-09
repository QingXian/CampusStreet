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
import android.util.Log;
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
import com.campusstreet.entity.CategoriesInfo;
import com.campusstreet.entity.JoinInfo;
import com.campusstreet.entity.UserInfo;
import com.campusstreet.entity.UserJoinTaskInfo;
import com.campusstreet.model.BountyHallImpl;
import com.campusstreet.presenter.BountyHallPresenter;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.R.attr.data;
import static com.campusstreet.R.id.view;
import static com.campusstreet.common.Const.BOUNTYHALLINFO_EXTRA;
import static com.campusstreet.common.Const.ID_EXTRA;
import static com.campusstreet.common.Const.JOINNOTPASS;
import static com.campusstreet.common.Const.JOINPASS;
import static com.campusstreet.common.Const.TID_EXTRA;
import static com.campusstreet.common.Const.TYPE;
import static com.campusstreet.common.Const.USERINFO_EXTRA;
import static com.campusstreet.utils.DataUtil.getTimeRange;

/**
 * Created by Orange on 2017/4/9.
 */

public class BountyHallDetailActivity extends BaseActivity implements IBountyHallContract.View {


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
    @BindView(R.id.btn_giveup)
    Button mBtnGiveup;
    private BountyHallInfo mBountyHallInfo;
    private IBountyHallContract.Presenter mPresenter;
    private BountyHallDetailRecyclerViewAdapter mAdapter;
    private int mPi = 0;
    private UserInfo mUserInfo;
    private JoinInfo mJoinInfo;
    private UserJoinTaskInfo mUserJoinTaskInfo;
    private boolean mIsFrist = true;
    private boolean mIsHavePass = false;
    private int mId;
    private int mPostion;

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
        mUserJoinTaskInfo = (UserJoinTaskInfo) getIntent().getSerializableExtra(Const.USERJOINTASKINFO_EXTRA);
        mUserInfo = (UserInfo) getIntent().getSerializableExtra(Const.USERINFO_EXTRA);
        new BountyHallPresenter(BountyHallImpl.getInstance(getApplicationContext()), this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRvContent.setLayoutManager(linearLayoutManager);
        mAdapter = new BountyHallDetailRecyclerViewAdapter(this, null, 0);
        mRvContent.setAdapter(mAdapter);
        if (mBountyHallInfo == null) {
            if (mUserJoinTaskInfo != null) {
                mPresenter.fetchTaskDetail(mUserJoinTaskInfo.getTaskid());
            } else {
                mId = getIntent().getIntExtra(ID_EXTRA, 0);
                mPresenter.fetchTaskDetail(mId);
            }
        } else {
            initView(mBountyHallInfo);
        }
        initEvent();

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mIsFrist) {
            if (mBountyHallInfo != null) {
                mPresenter.fetchjoinTaskList(mBountyHallInfo.getId(), JOINNOTPASS, mPi);
                mPresenter.fetchjoinTaskList(mBountyHallInfo.getId(), JOINPASS, mPi);
            }
        } else {
            mPresenter.fetchjoinTaskList(mBountyHallInfo.getId(), mPostion, mPi);
        }
    }

    private void initEvent() {
        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getText().equals("报名申请")) {
                    //获取报名的列表数据
                    mIsFrist = false;
                    mPresenter.fetchjoinTaskList(mBountyHallInfo.getId(), JOINNOTPASS, mPi);
                    mAdapter.replaceType(JOINNOTPASS);
                } else {
                    //获取通过报名的列表数据
                    mIsFrist = false;
                    mPresenter.fetchjoinTaskList(mBountyHallInfo.getId(), JOINPASS, mPi);
                    mAdapter.replaceType(JOINPASS);
                }
                mPostion = tab.getPosition();
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

    private void initView(BountyHallInfo bountyHallInfo) {
        if (bountyHallInfo.getFee().equals(0)) {
            mTvPrice.setText("面议");
        } else {
            double price = Double.parseDouble(bountyHallInfo.getFee());
            DecimalFormat df = new DecimalFormat("0.0");
            String strprice = df.format(price);
            mTvPrice.setText(strprice);
        }
        mTvName.setText(bountyHallInfo.getUsername());
        String time = getTimeRange(bountyHallInfo.getPubtime());
        mTvTime.setText(time);
        mTvTitle.setText(bountyHallInfo.getTitle());
        mTvPeopleNum.setText(String.valueOf(bountyHallInfo.getSperson()));
        mTvCompletionTime.setText(bountyHallInfo.getEndtime());
        mTvContactPeople.setText(bountyHallInfo.getLinkman());
        mTvDescribe.setText(bountyHallInfo.getCon());
        mTvPhone.setText(bountyHallInfo.getMobile());
        Picasso.with(this)
                .load(AppConfig.AVATAR_SERVER_HOST + bountyHallInfo.getUserpic())
                .fit()
                .into(mIvHead);
        mTabLayout.addTab(mTabLayout.newTab().setText("报名申请"));
        mTabLayout.addTab(mTabLayout.newTab().setText("已选择名单"));
        if (mUserInfo == null) {
            mBtnGiveup.setVisibility(View.GONE);
            mBtnEntel.setVisibility(View.GONE);
            mTabLayout.setVisibility(View.GONE);
            mRvContent.setVisibility(View.GONE);
        } else {
            if (mUserInfo.getUid().equals(bountyHallInfo.getUid()) || bountyHallInfo.getUid() == null) {
                mBtnGiveup.setVisibility(View.GONE);
                mTabLayout.setVisibility(View.VISIBLE);
                mRvContent.setVisibility(View.VISIBLE);
                if (bountyHallInfo.getState() == 3) {
                    mBtnEntel.setVisibility(View.VISIBLE);
                    mBtnEntel.setEnabled(false);
                    mBtnEntel.setText("任务关闭");
                    mBtnEntel.setBackgroundColor(Color.parseColor("#f7f7f7"));
                } else if (bountyHallInfo.getState() == 1) {
                    mBtnEntel.setVisibility(View.VISIBLE);
                    mBtnEntel.setText("开始任务");
                } else if (bountyHallInfo.getState() == 2) {
                    mBtnEntel.setVisibility(View.VISIBLE);
                    mBtnEntel.setEnabled(false);
                    mBtnEntel.setText("任务进行中");
                    mBtnEntel.setBackgroundColor(Color.parseColor("#f7f7f7"));
                } else if (bountyHallInfo.getState() == 5) {
                    mBtnEntel.setVisibility(View.VISIBLE);
                    mBtnEntel.setEnabled(false);
                    mBtnEntel.setText("任务结束");
                    mBtnEntel.setBackgroundColor(Color.parseColor("#f7f7f7"));
                }
            } else {
                mTabLayout.setVisibility(View.GONE);
                mRvContent.setVisibility(View.GONE);
                if (bountyHallInfo.getState() == 1) {
                    mBtnEntel.setVisibility(View.VISIBLE);
                    mBtnEntel.setText("报名");
                    mBtnGiveup.setVisibility(View.GONE);
                } else if (bountyHallInfo.getState() == 2) {
                    mBtnEntel.setVisibility(View.VISIBLE);
                    mBtnEntel.setEnabled(false);
                    mBtnEntel.setText("任务进行中");
                    mBtnGiveup.setVisibility(View.GONE);
                    mBtnEntel.setBackgroundColor(Color.parseColor("#f7f7f7"));
                } else if (bountyHallInfo.getState() == 3) {
                    mBtnEntel.setVisibility(View.VISIBLE);
                    mBtnEntel.setEnabled(false);
                    mBtnEntel.setText("任务关闭");
                    mBtnGiveup.setVisibility(View.GONE);
                    mBtnEntel.setBackgroundColor(Color.parseColor("#f7f7f7"));
                } else if (bountyHallInfo.getState() == 5) {
                    mBtnEntel.setVisibility(View.VISIBLE);
                    mBtnEntel.setEnabled(false);
                    mBtnEntel.setText("任务结束");
                    mBtnGiveup.setVisibility(View.GONE);
                    mBtnEntel.setBackgroundColor(Color.parseColor("#f7f7f7"));
                }
            }
        }
    }


    @Override
    public void setPresenter(IBountyHallContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void setTaskList(List<BountyHallInfo> bountyHallInfos) {

    }

    @Override
    public void setUserJoinTaskList(List<UserJoinTaskInfo> userJoinTaskList) {

    }

    @Override
    public void setBountyHallCategories(List<CategoriesInfo> categories) {

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
                    break;
                }
            }
        }
        mAdapter.replaceData(joinInfos);

    }

    @Override
    public void setPassTaskList(List<JoinInfo> joinInfos) {
        mIsHavePass = true;
        for (JoinInfo joinInfo :
                joinInfos) {
            if (mUserInfo != null) {
                if (joinInfo.getUid().equals(mUserInfo.getUid())) {
                    if (joinInfo.getState() == 3) {
                        mBtnGiveup.setVisibility(View.VISIBLE);
                        mBtnEntel.setText("提交完成");
                        mBtnEntel.setEnabled(true);
                        mBtnEntel.setBackgroundColor(Color.parseColor("#ffffff"));
                    } else if (joinInfo.getState() == 5) {
                        mBtnGiveup.setVisibility(View.GONE);
                        mBtnEntel.setText("已经提交等待验收");
                        mBtnEntel.setBackgroundColor(Color.parseColor("#f7f7f7"));
                        mBtnEntel.setEnabled(false);
                    } else if (joinInfo.getState() == 4) {
                        mBtnGiveup.setVisibility(View.GONE);
                        mBtnEntel.setText("已经放弃等待同意");
                        mBtnEntel.setBackgroundColor(Color.parseColor("#f7f7f7"));
                        mBtnEntel.setEnabled(false);
                    } else {
                        mBtnGiveup.setVisibility(View.GONE);
                        mBtnEntel.setText("任务结束");
                        mBtnEntel.setBackgroundColor(Color.parseColor("#f7f7f7"));
                        mBtnEntel.setEnabled(false);
                    }
                    mJoinInfo = joinInfo;
                    break;
                } else {
                    mBtnGiveup.setVisibility(View.GONE);
                }
            }
        }
        if (!mIsFrist) {
            mAdapter.replaceData(joinInfos);
        }
    }

    @Override
    public void showSuccessfullPassJoinTask() {

    }

    @Override
    public void showSuccessfullPublisherOpTask() {

    }

    @Override
    public void showSuccessfullCompleteTask() {
        showMessage("已发起完成，等待确认");
        mPresenter.fetchjoinTaskList(mBountyHallInfo.getId(), JOINPASS, mPi);
        setLoadingIndicator(true);
    }

    @Override
    public void showSuccessfullGiveUpTask() {
        showMessage("已发起放弃，等待确认");
        mPresenter.fetchjoinTaskList(mBountyHallInfo.getId(), JOINPASS, mPi);
        setLoadingIndicator(true);
    }

    @Override
    public void showSuccessfullJointask(String successMsg) {

    }

    @Override
    public void showSuccessfullStartTask() {
        mBtnEntel.setVisibility(View.VISIBLE);
        mBtnEntel.setEnabled(false);
        mBtnEntel.setText("任务进行中");
        mBtnEntel.setBackgroundColor(Color.parseColor("#f7f7f7"));
        mBountyHallInfo.setState(2);
    }

    @Override
    public void showErrorMsg(String errorMsg) {
        showMessage(errorMsg);
    }

    @Override
    public void showNoPassMsg() {
        mIsHavePass = false;
        if (mUserInfo != null) {
            if (mUserInfo.getUid().equals(mBountyHallInfo.getUid()) || mBountyHallInfo.getUid() == null) {
                if (mBountyHallInfo.getState() == 1) {
                    //                    showMessage("没有通过报名的数据");
                }
            }
        }
        mAdapter.replaceData(null);
    }

    @Override
    public void showSuccessfullyPush(String succcessMsg) {

    }

    @Override
    public void setTaskDetail(BountyHallInfo bountyHallInfo) {
        mBountyHallInfo = bountyHallInfo;
        mPresenter.fetchjoinTaskList(mBountyHallInfo.getId(), JOINNOTPASS, mPi);
        mPresenter.fetchjoinTaskList(mBountyHallInfo.getId(), JOINPASS, mPi);
        initView(mBountyHallInfo);
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

    @OnClick({R.id.btn_entel, R.id.btn_giveup})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_entel:
                if (mBtnEntel.getText().toString().equals("报名")) {
                    Calendar c1 = Calendar.getInstance();
                    Calendar c2 = Calendar.getInstance();
                    SimpleDateFormat CurrentTime = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                    Date curDate = new Date(System.currentTimeMillis());
                    String dataStrNew = CurrentTime.format(curDate);
                    try {
                        c1.setTime(CurrentTime.parse(mBountyHallInfo.getEndtime()));
                        c2.setTime(CurrentTime.parse(dataStrNew));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    int result = c1.compareTo(c2);
                    if (result == 0) {
                        showMessage("任务已经过期");
                    } else if (result < 0) {
                        showMessage("任务已经过期");
                    } else {
                        Intent intent = new Intent(this, RegistrationActivity.class);
                        intent.putExtra(TID_EXTRA, mBountyHallInfo.getId());
                        intent.putExtra(USERINFO_EXTRA, mUserInfo);
                        startActivityForResult(intent, 1);
                    }
                } else if (mBtnEntel.getText().toString().equals("开始任务")) {
                    //这里005表示开始任务
                    if (mIsHavePass) {
                        mPresenter.startTask(mUserInfo.getUid(), mBountyHallInfo.getId(), "005");
                    } else {
                        showMessage("没有通过的,无法开始");
                    }
                } else if (mBtnEntel.getText().toString().equals("提交完成")) {
                    if (mJoinInfo != null)
                        mPresenter.completeTask(mUserInfo.getUid(), mJoinInfo.getId(),mBountyHallInfo.getId());
                }
                break;
            case R.id.btn_giveup:
                if (mJoinInfo != null) {
                    mPresenter.giveUpTask(mUserInfo.getUid(), mJoinInfo.getId(), mBountyHallInfo.getId());
                }
                break;
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
