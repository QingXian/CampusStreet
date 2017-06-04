package com.campusstreet.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
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
import com.campusstreet.common.AppConfig;
import com.campusstreet.common.Const;
import com.campusstreet.contract.IAssociationContract;
import com.campusstreet.entity.AssociationInfo;
import com.campusstreet.entity.AssociationNumInfo;
import com.campusstreet.entity.AssociationPostInfo;
import com.campusstreet.entity.AssociationPostMessageInfo;
import com.campusstreet.entity.UserAssociationInfo;
import com.campusstreet.entity.UserInfo;
import com.campusstreet.model.AssociationImpl;
import com.campusstreet.presenter.AssociationPresenter;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.campusstreet.common.Const.ID_EXTRA;
import static com.campusstreet.utils.DataUtil.getTimeRange;

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
    @BindView(R.id.scrollView)
    NestedScrollView mScrollView;
    private LinearLayoutManager mLinearLayoutManager;
    private IAssociationContract.Presenter mPresenter;
    private AssociationPostInfo mAssociationPostInfo;
    private int mPi = 0;
    private PostDetailRecyclerViewAdapter mAdapter;
    private UserInfo mUserInfo;
    private boolean mIsLoading;
    private boolean mIsNeedLoadMore = true;
    private int mId;

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
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRvContent.setLayoutManager(mLinearLayoutManager);
        mAdapter = new PostDetailRecyclerViewAdapter(this, null);
        mRvContent.setAdapter(mAdapter);
        mRvContent.setNestedScrollingEnabled(false);
        mAssociationPostInfo = (AssociationPostInfo) getIntent().getSerializableExtra(Const.ASSOCIATIONPOSTINFO_EXTRA);
        mUserInfo = (UserInfo) getIntent().getSerializableExtra(Const.USERINFO_EXTRA);
        mPresenter.fetchAssociationPostDetail(mAssociationPostInfo.getId());
        if (mAssociationPostInfo == null) {
            mId = getIntent().getIntExtra(ID_EXTRA, 0);
            mPresenter.fetchAssociationPostDetail(mId);
            mPresenter.fetchAssociationPostMessageList(mId, mPi);
        } else {
            initView(mAssociationPostInfo);
            mPresenter.fetchAssociationPostMessageList(mAssociationPostInfo.getId(), mPi);
        }
        setLoadingIndicator(true);
        initEvent();
    }

    private void initView(AssociationPostInfo associationPostInfo) {
        mTvName.setText(associationPostInfo.getUsername());
        Log.d("initView: ", "initView: " + associationPostInfo.getAddtime());
        String time = getTimeRange(associationPostInfo.getAddtime());
        Picasso.with(this)
                .load(AppConfig.AVATAR_SERVER_HOST + associationPostInfo.getUserpic())
                .fit()
                .error(R.drawable.ic_head_test)
                .into(mIvHead);
        mTvTime.setText(time);
        mTvTitle.setText(associationPostInfo.getTitle());
        mTvContent.setText(associationPostInfo.getCon());
    }

    private void initEvent() {
        mScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                View contentView = mScrollView.getChildAt(0);
                if (mIsNeedLoadMore && !mIsLoading && contentView.getMeasuredHeight() <= mScrollView.getScrollY() + mScrollView.getHeight()) {
                    mPresenter.fetchAssociationPostMessageList(mAssociationPostInfo.getId(), ++mPi);
                    mIsLoading = true;
                }

            }

        });
//        mRvContent.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                // 如果当前滑动状态为空闲并且总数小于最后一个可见项+阈值，则加载更多
//                if (mIsNeedLoadMore && !mIsLoading && RecyclerView.SCROLL_STATE_IDLE == newState &&
//                        mItemCount < mLastVisibleItemPosition + Const.RECYCLER_VIEW_VISIBLE_THRESHOLD) {
//                    mPresenter.fetchAssociationPostMessageList(mAssociationPostInfo.getId(), ++mPi);
//                    mIsLoading = true;
//                }
//
//            }
//
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                mLastVisibleItemPosition = mLinearLayoutManager.findLastVisibleItemPosition();
//                mItemCount = mLinearLayoutManager.getItemCount();
//            }
//        });
    }

    @Override
    public void setPresenter(IAssociationContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void setAssociationPostMessageList(List<AssociationPostMessageInfo> associationPostMessageList) {
        if (associationPostMessageList != null && associationPostMessageList.size() < 20) {
            mIsNeedLoadMore = false;
        } else {
            mIsNeedLoadMore = true;
        }
        if (mPi != 0) {
            if (associationPostMessageList != null) {
                mAdapter.addData(associationPostMessageList);
                mIsLoading = false;
            }
        } else {
            mAdapter.replaceData(associationPostMessageList);
            setLoadingIndicator(false);
        }
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
    public void setUserAssociationList(List<UserAssociationInfo> UserAssociationList) {

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
        mAssociationPostInfo = associationPostInfo;
        initView(mAssociationPostInfo);
    }

    @Override
    public void showErrorMsg(String errorMsg) {
        if (mPi == 0) {
            showMessage(errorMsg);
        } else {
            showMessage("没有数据了");
            mIsLoading = false;
        }
        setLoadingIndicator(false);
    }

    @Override
    public void showSuccessfullyleaveMessage(String succcessMsg) {
        showMessage(succcessMsg);
        mPi = 0;
        setLoadingIndicator(true);
        mPresenter.fetchAssociationPostMessageList(mAssociationPostInfo.getId(), mPi);
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
        }
        mEtMessage.setText("");
        mScrollView.smoothScrollTo(0, 0);
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
