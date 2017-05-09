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
import com.campusstreet.adapter.PostDetailRecyclerViewAdapter;
import com.campusstreet.common.Const;
import com.campusstreet.contract.IAssociationContract;
import com.campusstreet.entity.AssociationInfo;
import com.campusstreet.entity.AssociationNumInfo;
import com.campusstreet.entity.AssociationPostInfo;
import com.campusstreet.entity.AssociationPostMessageInfo;
import com.campusstreet.entity.UserInfo;
import com.campusstreet.model.AssociationImpl;
import com.campusstreet.presenter.AssociationPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Orange on 2017/4/10.
 */

public class PostDetailActivity extends AppCompatActivity implements IAssociationContract.View {
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
    @BindView(R.id.rl_publisher_content)
    RelativeLayout mRlPublisherContent;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_content)
    TextView mTvContent;
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
    private IAssociationContract.Presenter mPresenter;
    private AssociationPostInfo mAssociationPostInfo;
    private int mPi = 0;
    private PostDetailRecyclerViewAdapter mAdapter;
    private UserInfo mUserInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);
        ButterKnife.bind(this);
        mToolbar.setTitle("");
        mToolbarTitle.setText(getString(R.string.act_post_detail_toolbar_title));
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
        new AssociationPresenter(AssociationImpl.getInstance(getApplicationContext()), this);
        mAssociationPostInfo = (AssociationPostInfo) getIntent().getSerializableExtra(Const.ASSOCIATIONPOSTINFO_EXTRA);
        mUserInfo = (UserInfo) getIntent().getSerializableExtra(Const.USERINFO_EXTRA);
        mPresenter.fetchAssociationPostDetail(mAssociationPostInfo.getId());
        mPresenter.fetchAssociationPostMessageList(mAssociationPostInfo.getId(), mPi);
        initView();
    }

    private void initView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRvContent.setLayoutManager(linearLayoutManager);
        mAdapter = new PostDetailRecyclerViewAdapter(this, null);
        mRvContent.setAdapter(mAdapter);
    }

    @Override
    public void setPresenter(IAssociationContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void setAssociationPostMessageList(List<AssociationPostMessageInfo> associationPostMessageList) {
        mAdapter.replaceData(associationPostMessageList);
    }

    @Override
    public void setAssociationPostList(List<AssociationPostInfo> associationPostList) {

    }

    @Override
    public void setAssociationNumList(List<AssociationNumInfo> associationNumList) {

    }

    @Override
    public void setAssociationList(List<AssociationInfo> associationList) {
    }

    @Override
    public void showSuccessfullyJoin(String succcessMsg) {

    }

    @Override
    public void showSuccessfullyApplyJoin(String succcessMsg) {

    }

    @Override
    public void showSuccessfullyPushPost(String succcessMsg) {

    }

    @Override
    public void setAssociationPostDetail(AssociationPostInfo associationPostInfo) {
        mTvContent.setText(associationPostInfo.getCon());
    }

    @Override
    public void showErrorMsg(String errorMsg) {
        showMessage(errorMsg);
    }

    @Override
    public void showSuccessfullyleaveMessage(String succcessMsg) {
        showMessage(succcessMsg);
        mPresenter.fetchAssociationPostMessageList(mAssociationPostInfo.getId(), mPi);
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
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

    @OnClick(R.id.btn_send_message)
    public void onViewClicked() {
        if (TextUtils.isEmpty(mEtMessage.getText().toString().trim())) {
            showMessage("请填写留言内容");
            return;
        }
        if (mUserInfo != null) {
            mPresenter.onLeaveMessage(mAssociationPostInfo.getId(), mUserInfo.getUid(), mEtMessage.getText().toString().trim());
            setLoadingIndicator(true);
        } else {
            showMessage("您还未登录");
        }
    }
}
