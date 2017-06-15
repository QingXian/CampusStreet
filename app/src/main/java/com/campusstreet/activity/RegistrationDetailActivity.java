package com.campusstreet.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.campusstreet.R;
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
import com.campusstreet.utils.PreferencesUtil;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.campusstreet.common.Const.TYPE;

/**
 * Created by Orange on 2017/4/9.
 */

public class RegistrationDetailActivity extends BaseActivity implements IBountyHallContract.View {
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
    @BindView(R.id.tv_time)
    TextView mTvTime;
    @BindView(R.id.tv_remuneration)
    TextView mTvRemuneration;
    @BindView(R.id.tv_remarks)
    TextView mTvRemarks;
    @BindView(R.id.btn_adopt)
    Button mBtnAdopt;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.progress_bar_title)
    TextView mProgressBarTitle;
    @BindView(R.id.progress_bar_container)
    LinearLayout mProgressBarContainer;
    @BindView(R.id.tv_phone)
    TextView mTvPhone;
    @BindView(R.id.tv_state)
    TextView mTvState;
    private IBountyHallContract.Presenter mPresenter;
    private JoinInfo mJoinInfo;
    private int mType;
    private UserInfo mUserInfo;
    private BountyHallInfo mBountyHallInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_detail);
        ButterKnife.bind(this);
        mToolbar.setTitle("");
        mToolbarTitle.setText(getString(R.string.act_registration_detail_toolbar_title));
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
        mJoinInfo = (JoinInfo) getIntent().getSerializableExtra(Const.JOINNFO_EXTRA);
        mType = getIntent().getIntExtra(TYPE, 1);
        mBountyHallInfo = (BountyHallInfo) getIntent().getSerializableExtra(Const.BOUNTYHALLINFO_EXTRA);
        mUserInfo = (UserInfo) getIntent().getSerializableExtra(Const.USERINFO_EXTRA);
        new BountyHallPresenter(BountyHallImpl.getInstance(getApplicationContext()), this);
        initView();

    }

    private void initView() {
        mTvName.setText(mJoinInfo.getUsername());
        mTvTime.setText(mJoinInfo.getAddtime());
        mTvPhone.setText(mJoinInfo.getPhone());
        double price = Double.parseDouble(mJoinInfo.getFee());
        DecimalFormat df = new DecimalFormat("0.0");
        String strprice = df.format(price);
        mTvRemuneration.setText(strprice);
        mTvRemarks.setText(mJoinInfo.getSummary());
        Picasso.with(this)
                .load(AppConfig.AVATAR_SERVER_HOST + mJoinInfo.getUserpic())
                .fit()
                .error(R.drawable.ic_head_test)
                .into(mIvHead);
        if (mType == 0) {
            if (mBountyHallInfo.getState() == 1) {
                mTvState.setVisibility(View.GONE);
                mBtnAdopt.setVisibility(View.VISIBLE);
                mBtnAdopt.setText("通过");
            } else {
                mBtnAdopt.setVisibility(View.GONE);
            }
        } else {
            if (mJoinInfo.getState() == 3) {
                mBtnAdopt.setVisibility(View.GONE);
                mTvState.setVisibility(View.VISIBLE);
                mTvState.setText("进行中");
            } else if (mJoinInfo.getState() == 4) {
                mBtnAdopt.setVisibility(View.VISIBLE);
                mBtnAdopt.setText("是否同意放弃");
                mTvState.setVisibility(View.VISIBLE);
                mTvState.setText("对方发起放弃");
            } else if (mJoinInfo.getState() == 5) {
                mBtnAdopt.setVisibility(View.VISIBLE);
                mBtnAdopt.setText("是否同意完成");
                mTvState.setVisibility(View.VISIBLE);
                mTvState.setText("对方发起完成");
            } else if (mJoinInfo.getState() == 9) {
                mBtnAdopt.setVisibility(View.GONE);
                mTvState.setVisibility(View.VISIBLE);
                mTvState.setText("已放弃");
            } else if (mJoinInfo.getState() == 10) {
                mBtnAdopt.setVisibility(View.GONE);
                mTvState.setVisibility(View.VISIBLE);
                mTvState.setText("已完成");
            } else {
                mBtnAdopt.setVisibility(View.GONE);
                mTvState.setVisibility(View.GONE);
            }
        }
    }

    @OnClick(R.id.btn_adopt)
    public void onViewClicked() {
        if (mBtnAdopt.getText().equals("通过")) {
            //状态1代表选中
            mPresenter.passJoinTask(mUserInfo.getUid(), mBountyHallInfo.getId(), mJoinInfo.getId(), 1);
        } else if (mBtnAdopt.getText().equals("是否同意放弃")) {
            showAlertDialog("对方发起放弃");
        } else if (mBtnAdopt.getText().equals("是否同意完成")) {
            showAlertDialog("对方发起完成");
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

    }

    @Override
    public void setPassTaskList(List<JoinInfo> joinInfos) {

    }

    @Override
    public void showSuccessfullPublisherOpTask() {
        showMessage("操作成功");
        Intent intent = new Intent(this, BountyHallDetailActivity.class);
        startActivity(intent);
        this.finish();
    }

    @Override
    public void showSuccessfullCompleteTask() {

    }

    @Override
    public void showSuccessfullGiveUpTask() {

    }

    @Override
    public void showSuccessfullPassJoinTask() {
        Intent intent = new Intent(this, BountyHallDetailActivity.class);
        startActivity(intent);
        this.finish();
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
        showMessage("通过失败");
    }

    @Override
    public void showSuccessfullyPush(String successMsg) {

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
                mProgressBarTitle.setText(R.string.operating_progress_bar_title);
            } else {
                if (mProgressBarContainer.getVisibility() == View.VISIBLE) {
                    mProgressBarContainer.setVisibility(View.GONE);
                }
            }
        }
    }

    private void showAlertDialog(String text) {
        new AlertDialog.Builder(this).setTitle(text)
                .setPositiveButton("同意", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //1代表同意
                        mPresenter.publisherOpTask(mUserInfo.getUid(), mJoinInfo.getId(), mBountyHallInfo.getId(), "001");
                    }
                })
                .setNegativeButton("拒绝", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //2代表拒绝
                        mPresenter.publisherOpTask(mUserInfo.getUid(), mJoinInfo.getId(), mBountyHallInfo.getId(), "002");
                    }
                })
                .show();
    }
}
