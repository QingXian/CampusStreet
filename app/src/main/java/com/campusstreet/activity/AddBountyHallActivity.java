package com.campusstreet.activity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.campusstreet.R;
import com.campusstreet.common.Const;
import com.campusstreet.contract.IBountyHallContract;
import com.campusstreet.entity.BountyHallInfo;
import com.campusstreet.entity.JoinInfo;
import com.campusstreet.entity.UserInfo;
import com.campusstreet.model.BountyHallImpl;
import com.campusstreet.presenter.BountyHallPresenter;

import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Orange on 2017/4/10.
 */

public class AddBountyHallActivity extends AppCompatActivity implements IBountyHallContract.View {


    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.tv_toolbar_right)
    TextView mTvToolbarRight;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.et_title)
    EditText mEtTitle;
    @BindView(R.id.et_detail)
    EditText mEtDetail;
    @BindView(R.id.et_bounty)
    EditText mEtBounty;
    @BindView(R.id.et_num)
    EditText mEtNum;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.progress_bar_title)
    TextView mProgressBarTitle;
    @BindView(R.id.progress_bar_container)
    LinearLayout mProgressBarContainer;
    @BindView(R.id.et_key)
    EditText mEtKey;
    @BindView(R.id.et_phone)
    EditText mEtPhone;
    @BindView(R.id.et_linkman)
    EditText mEtLinkman;
    @BindView(R.id.tv_type)
    TextView mTvType;
    @BindView(R.id.btn_type)
    Button mBtnType;
    @BindView(R.id.tv_end_time)
    TextView mTvEndTime;
    @BindView(R.id.btn_end_time)
    Button mBtnEndTime;
    @BindView(R.id.rl_task_type)
    RelativeLayout mRlTaskType;
    @BindView(R.id.rl_task_end_time)
    RelativeLayout mRlTaskEndTime;
    private IBountyHallContract.Presenter mPresenter;
    private String[] mTitle;
    private String mType;
    private int mIndex;
    private String mEndTime;
    private UserInfo mUserInfo;
    private int mYear;
    private int mMonth;
    private int mDay;
    private int mIsUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bounty_hall_add);
        ButterKnife.bind(this);
        mToolbar.setTitle("");
        mToolbarTitle.setText(getString(R.string.act_bounty_hall_add_toolbar_title));
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
        new BountyHallPresenter(BountyHallImpl.getInstance(getApplicationContext()), this);
        mUserInfo = (UserInfo) getIntent().getSerializableExtra(Const.USERINFO_EXTRA);
        mIsUser = getIntent().getIntExtra(Const.TYPE, 0);
        mPresenter.fetchBountyHallCategories();
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
        mTitle = type;
    }

    @Override
    public void setJoinTaskList(List<JoinInfo> joinInfos) {

    }

    @Override
    public void setPassTaskList(List<JoinInfo> joinInfos) {

    }

    @Override
    public void showSuccessfullPassJoinTask() {

    }

    @Override
    public void showSuccessfullPublisherOpTask() {

    }

    @Override
    public void showSuccessfullCompleteTask() {

    }

    @Override
    public void showSuccessfullGiveUpTask() {

    }

    @Override
    public void showSuccessfullJointask(String successMsg) {

    }

    @Override
    public void showSuccessfullStartTask() {

    }

    @Override
    public void showErrorMsg(String errorMsg) {
        showMessage(errorMsg);
    }

    @Override
    public void showNoPassMsg() {

    }

    @Override
    public void showSuccessfullyPush(String succcessMsg) {
        showMessage(succcessMsg);
        if (mIsUser == 1) {
            Intent intent = new Intent(this, MyBountyHallActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, BountyHallActivity.class);
            startActivity(intent);
        }

        this.finish();
    }

    @Override
    public void setTaskDetail(BountyHallInfo bountyHallInfo) {

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

    @OnClick({R.id.rl_task_end_time, R.id.tv_toolbar_right, R.id.rl_task_type})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_task_type:
                new AlertDialog.Builder(this)
                        .setTitle("请选择类型")
                        .setSingleChoiceItems(mTitle, mIndex, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                mType = mTitle[i];
                                mIndex = i;
                                mTvType.setText(mTitle[i]);
                                dialogInterface.dismiss();
                            }
                        })
                        .create()
                        .show();
                break;
            case R.id.rl_task_end_time:
                Calendar c = Calendar.getInstance();
                new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        mTvEndTime.setText(String.format("%d-%d-%d", year, monthOfYear + 1, dayOfMonth));
                        mEndTime = mTvEndTime.getText().toString();
                        mYear = year;
                        mMonth = monthOfYear;
                        mDay = dayOfMonth;
                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();

                break;
            case R.id.tv_toolbar_right:
                AddTask();
                break;
        }
    }

    private void AddTask() {
        if (!mTvType.getText().toString().trim().equals(mType)) {
            showMessage("请选择类型");
            return;
        }
        if (TextUtils.isEmpty(mEtTitle.getText().toString().trim())) {
            showMessage("请填写标题");
            return;
        }
        try {
            if (Integer.valueOf(mEtBounty.getText().toString().trim()) < 0) {
                showMessage("请填写正确的价格");
                return;
            }

        } catch (NumberFormatException e) {
            showMessage("价格格式不正确");
            return;
        }
        if (TextUtils.isEmpty(mEtDetail.getText().toString().trim())) {
            showMessage("请填写详细内容");
            return;
        }
        if (TextUtils.isEmpty(mEtKey.getText().toString().trim())) {
            showMessage("请填写关键字");
            return;
        }
        if (TextUtils.isEmpty(mEtPhone.getText().toString().trim())) {
            showMessage("请填写联系方式");
            return;
        }
        if (TextUtils.isEmpty(mEtLinkman.getText().toString().trim())) {
            showMessage("请填写联系人");
            return;
        }
        try {
            if (Integer.valueOf(mEtNum.getText().toString().trim()) < 0) {
                showMessage("请填写大于0的人数");
                return;
            }

        } catch (NumberFormatException e) {
            showMessage("人数不正确");
            return;
        }
        if (!mTvEndTime.getText().toString().trim().equals(mEndTime)) {
            showMessage("请选择截止日期");
            return;
        }
        Calendar c = Calendar.getInstance();
        if (mYear < c.get(Calendar.YEAR)) {
            showMessage("请选择正确的日期");
            return;
        } else if (mMonth < c.get(Calendar.MONTH)) {
            showMessage("请选择正确的日期");
            return;
        } else if (mDay < c.get(Calendar.DAY_OF_MONTH)) {
            showMessage("请选择正确的日期");
            return;
        }


        BountyHallInfo bountyHallInfo = new BountyHallInfo();
        bountyHallInfo.setTitle(mEtTitle.getText().toString());
        bountyHallInfo.setFee(mEtBounty.getText().toString());
        bountyHallInfo.setCon(mEtDetail.getText().toString());
        bountyHallInfo.setKey(mEtKey.getText().toString());
        bountyHallInfo.setType(String.valueOf(mIndex + 1));
        bountyHallInfo.setMobile(mEtPhone.getText().toString());
        bountyHallInfo.setLinkman(mEtLinkman.getText().toString());
        bountyHallInfo.setPerson(Integer.valueOf(mEtNum.getText().toString()));
//        bountyHallInfo.setUid("Mw==");
        bountyHallInfo.setUid(mUserInfo.getUid());
        bountyHallInfo.setEndtime(mEndTime);
        mPresenter.addTask(bountyHallInfo);
    }

}
