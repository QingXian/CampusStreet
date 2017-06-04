package com.campusstreet.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.campusstreet.R;
import com.campusstreet.adapter.LeaveMessageRecycleViewAdapter;
import com.campusstreet.adapter.LiveDetailRecyclerViewAdapter;
import com.campusstreet.common.AppConfig;
import com.campusstreet.common.Const;
import com.campusstreet.contract.IFindContract;
import com.campusstreet.entity.LiveInfo;
import com.campusstreet.entity.LiveReplyInfo;
import com.campusstreet.entity.UserInfo;
import com.campusstreet.model.FindImpl;
import com.campusstreet.model.IdleSaleImpl;
import com.campusstreet.presenter.FindPresenter;
import com.campusstreet.presenter.IdleSalePresenter;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.campusstreet.utils.DataUtil.getTimeRange;

/**
 * Created by Orange on 2017/6/4.
 */

public class LiveDetailActivity extends AppCompatActivity implements IFindContract.View {

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
    @BindView(R.id.tv_content)
    TextView mTvContent;
    @BindView(R.id.iv_image1)
    ImageView mIvImage1;
    @BindView(R.id.iv_image2)
    ImageView mIvImage2;
    @BindView(R.id.iv_image3)
    ImageView mIvImage3;
    @BindView(R.id.image_content_ll)
    LinearLayout mImageContentLl;
    @BindView(R.id.tv_delete)
    TextView mTvDelete;
    @BindView(R.id.rv_content)
    RecyclerView mRvContent;
    @BindView(R.id.scrollView)
    NestedScrollView mScrollView;
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
    private LinearLayoutManager mLinearLayoutManager;
    private IFindContract.Presenter mPresenter;
    private LiveDetailRecyclerViewAdapter mAdapter;
    private UserInfo mUserInfo;
    private LiveInfo mLiveInfo;
    private int mImageNum = 0;
    private String[] mImages;
    private int mPi;
    private boolean mIsLoading;
    private boolean mIsNeedLoadMore = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_detail);
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
        mUserInfo = (UserInfo) getIntent().getSerializableExtra(Const.USERINFO_EXTRA);
        mLiveInfo = (LiveInfo) getIntent().getSerializableExtra(Const.LIVEINFO_EXTRA);
        new FindPresenter(FindImpl.getInstance(getApplicationContext()), this);
        initView();
        initEvent();
        mPresenter.fetchLiveReplyList(mLiveInfo.getId(), mPi);
    }

    private void initEvent() {
        mScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                View contentView = mScrollView.getChildAt(0);
                if (mIsNeedLoadMore && !mIsLoading && contentView.getMeasuredHeight() <= mScrollView.getScrollY() + mScrollView.getHeight()) {
                    mPresenter.fetchLiveReplyList(mLiveInfo.getId(), mPi);
                    mIsLoading = true;
                }

            }

        });
    }

    private void initView() {
        if (mUserInfo != null && mUserInfo.getUid().equals(mLiveInfo.getUid())) {
            mTvDelete.setVisibility(View.VISIBLE);
        } else {
            mTvDelete.setVisibility(View.GONE);
        }
        mTvName.setText(mLiveInfo.getUsername());
        mTvContent.setText(mLiveInfo.getCon());
        String time = getTimeRange(mLiveInfo.getPubtime());
        mTvTime.setText(time);
        Picasso.with(this)
                .load(AppConfig.AVATAR_SERVER_HOST + mLiveInfo.getUserpic())
                .fit()
                .into(mIvHead);
        initImage(mLiveInfo.getImages());
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRvContent.setLayoutManager(mLinearLayoutManager);
        mAdapter = new LiveDetailRecyclerViewAdapter(this, null);
        mRvContent.setAdapter(mAdapter);
        mRvContent.setNestedScrollingEnabled(false);
    }

    private void initImage(String images) {
        if (images != null) {
            for (int i = 0; i <= images.length() - 1; i++) {
                String getstr = images.substring(i, i + 1);
                if (getstr.equals(",")) {
                    mImageNum++;
                }
            }
            if (mImageNum == 0) {
                mIvImage1.setVisibility(View.VISIBLE);
                mIvImage2.setVisibility(View.GONE);
                mIvImage3.setVisibility(View.GONE);
                Picasso.with(this)
                        .load(AppConfig.PIC_LIVE_SERVER_HOST + images)
                        .fit()
                        .into(mIvHead);

            } else if (mImageNum == 1) {
                mImages = images.split(",");
                mIvImage1.setVisibility(View.VISIBLE);
                mIvImage2.setVisibility(View.VISIBLE);
                mIvImage3.setVisibility(View.GONE);
                Picasso.with(this)
                        .load(AppConfig.PIC_LIVE_SERVER_HOST + mImages[0])
                        .fit()
                        .into(mIvHead);
                Picasso.with(this)
                        .load(AppConfig.PIC_LIVE_SERVER_HOST + mImages[1])
                        .fit()
                        .into(mIvHead);
            } else if (mImageNum == 2) {
                mImages = images.split(",");
                mIvImage1.setVisibility(View.VISIBLE);
                mIvImage2.setVisibility(View.VISIBLE);
                mIvImage3.setVisibility(View.VISIBLE);
                Picasso.with(this)
                        .load(AppConfig.PIC_LIVE_SERVER_HOST + mImages[0])
                        .fit()
                        .into(mIvHead);
                Picasso.with(this)
                        .load(AppConfig.PIC_LIVE_SERVER_HOST + mImages[1])
                        .fit()
                        .into(mIvHead);
                Picasso.with(this)
                        .load(AppConfig.PIC_LIVE_SERVER_HOST + mImages[2])
                        .fit()
                        .into(mIvHead);
            }
        }
    }

    @Override
    public void setPresenter(IFindContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void setLiveReplyList(List<LiveReplyInfo> liveReplyList) {
        if (liveReplyList != null && liveReplyList.size() < 20) {
            mIsNeedLoadMore = false;
        } else {
            mIsNeedLoadMore = true;
        }
        if (mPi != 0) {
            if (liveReplyList != null) {
                mAdapter.addData(liveReplyList);
                mIsLoading = false;
            }
        } else {
            mAdapter.replaceData(liveReplyList);
            setLoadingIndicator(false);
        }
    }

    @Override
    public void showDeleteSuccess() {
        showMessage("已删除");
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        this.finish();
    }

    @Override
    public void showReplySuccess() {
        showMessage("回复成功");
        mPi = 0;
        setLoadingIndicator(true);
        mPresenter.fetchLiveReplyList(mLiveInfo.getId(), mPi);
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
        }
        mEtMessage.setText("");
        mScrollView.smoothScrollTo(0, 0);
    }

    @Override
    public void showOperationError(String errorMsg) {
        showMessage(errorMsg);
    }

    @Override
    public void setFindList(List<LiveInfo> liveInfos) {

    }

    @Override
    public void setTopImge() {

    }

    @Override
    public void showErrorMsg(String errorMsg) {
        if (mPi == 0) {
            showMessage(errorMsg);
        } else {
            showMessage("没有数据了");
            mIsLoading = false;
        }
    }

    @Override
    public void showSuccessfullyPush(String succcessMsg) {

    }

    @OnClick({R.id.tv_delete, R.id.btn_send_message})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_delete:
                showAlertDialog("确认删除");
                break;
            case R.id.btn_send_message:
                if (mUserInfo != null) {
                    mPresenter.replyLive(mUserInfo.getUid(), mLiveInfo.getId(), mEtMessage.getText().toString().trim());
                    setLoadingIndicator(true);
                } else {
                    showMessage("您还未登录");
                }
                break;
        }
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

    private void showAlertDialog(String text) {
        new AlertDialog.Builder(this).setTitle(text)
                .setPositiveButton("同意", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        mPresenter.deleteLive(mUserInfo.getUid(), mLiveInfo.getId());
                    }
                })
                .setNegativeButton("拒绝", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                })
                .show();
    }
}
