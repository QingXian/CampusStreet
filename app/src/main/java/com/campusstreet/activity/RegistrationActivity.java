package com.campusstreet.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.R.attr.data;
import static com.campusstreet.common.Const.TID_EXTRA;

/**
 * Created by Orange on 2017/5/2.
 */

public class RegistrationActivity extends AppCompatActivity implements IBountyHallContract.View {
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.et_bounty)
    EditText mEtBounty;
    @BindView(R.id.et_phone)
    EditText mEtPhone;
    @BindView(R.id.et_detail)
    EditText mEtDetail;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.progress_bar_title)
    TextView mProgressBarTitle;
    @BindView(R.id.progress_bar_container)
    LinearLayout mProgressBarContainer;
    @BindView(R.id.btn_join)
    Button mBtnJoin;
    private IBountyHallContract.Presenter mPresenter;
    private int mTid;
    private UserInfo mUserInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        ButterKnife.bind(this);
        mToolbar.setTitle("");
        mToolbarTitle.setText(getString(R.string.act_registration_toolbar_title));
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
        mTid = getIntent().getIntExtra(TID_EXTRA, 0);
        new BountyHallPresenter(BountyHallImpl.getInstance(getApplicationContext()), this);
        mUserInfo = (UserInfo) getIntent().getSerializableExtra(Const.USERINFO_EXTRA);
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
        showMessage(successMsg);
        Intent intent = new Intent(this, BountyHallDetailActivity.class);
        this.setResult(Activity.RESULT_OK, intent);
        this.finish();
        startActivity(intent);
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

    @OnClick(R.id.btn_join)
    public void onViewClicked() {
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

        if (TextUtils.isEmpty(mEtPhone.getText().toString().trim())) {
            showMessage("请填写联系方式");
            return;
        }
        if (mEtPhone.getText().toString().trim().length() != 11) {
            showMessage("请正确的联系方式");
            return;
        }

        JoinInfo joinInfo = new JoinInfo();
        joinInfo.setFee(mEtBounty.getText().toString());
        joinInfo.setSummary(mEtDetail.getText().toString());
        joinInfo.setPhone(mEtPhone.getText().toString());
        joinInfo.setUid(mUserInfo.getUid());
        joinInfo.setId(mTid);
        mPresenter.joinTask(joinInfo);
    }
}
