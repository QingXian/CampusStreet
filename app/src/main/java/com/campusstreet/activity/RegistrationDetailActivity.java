package com.campusstreet.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
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
import com.campusstreet.entity.JoinInfo;
import com.campusstreet.model.BountyHallImpl;
import com.campusstreet.presenter.BountyHallPresenter;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.campusstreet.common.Const.ISSTRAT;
import static com.campusstreet.common.Const.TID_EXTRA;
import static com.campusstreet.common.Const.TYPE;

/**
 * Created by Orange on 2017/4/9.
 */

public class RegistrationDetailActivity extends AppCompatActivity implements IBountyHallContract.View {
    @BindView(R.id.iv_head)
    CircleImageView mIvHead;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_department)
    TextView mTvDepartment;
    @BindView(R.id.tv_time)
    TextView mTvTime;
    @BindView(R.id.tv_remuneration)
    TextView mTvRemuneration;
    @BindView(R.id.tv_place)
    TextView mTvPlace;
    @BindView(R.id.tv_remarks)
    TextView mTvRemarks;
    @BindView(R.id.btn_adopt)
    Button mBtnAdopt;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.iv_toolbar_right)
    ImageView mIvToolbarRight;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.progress_bar_title)
    TextView mProgressBarTitle;
    @BindView(R.id.progress_bar_container)
    LinearLayout mProgressBarContainer;
    private IBountyHallContract.Presenter mPresenter;
    private JoinInfo mJoinInfo;
    private int mType;
    private int mIsStart;
    private int mTid;

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
        mIsStart = getIntent().getIntExtra(ISSTRAT, 0);
        mType = getIntent().getIntExtra(TYPE, 1);
        mTid = getIntent().getIntExtra(TID_EXTRA, 0);
        new BountyHallPresenter(BountyHallImpl.getInstance(getApplicationContext()), this);
        initView();

    }

    private void initView() {
        mTvName.setText(mJoinInfo.getUsername());
        mTvTime.setText(mJoinInfo.getAddtime());
        mTvRemuneration.setText(mJoinInfo.getFee());
        mTvRemarks.setText(mJoinInfo.getSummary());
        Picasso.with(this)
                .load(AppConfig.AVATAR_SERVER_HOST + mJoinInfo.getUserpic())
                .fit()
                .into(mIvHead);
        if (mType == 1 || mIsStart == 1) {
            mBtnAdopt.setVisibility(View.GONE);
        } else {
            mBtnAdopt.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R.id.btn_adopt)
    public void onViewClicked() {
        mPresenter.passJoinTask("Mw==", mTid, mJoinInfo.getId());
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
    public void showSuccessfullpassJoinTask() {
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

    }

    @Override
    public void showSuccessfullyPush(String successMsg) {

    }

    @Override
    public void showfetchBountyHallCategoriesFailMsg(String errorMsg) {

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
