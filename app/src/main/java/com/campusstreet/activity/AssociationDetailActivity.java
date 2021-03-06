package com.campusstreet.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.campusstreet.R;
import com.campusstreet.adapter.AssociationDetailRecyclerViewAdapter;
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

import static android.R.id.input;
import static com.campusstreet.R.id.editText;
import static com.campusstreet.common.Const.REQUEST_CODE;
import static com.campusstreet.common.Const.USERASSOCIATIONINFO_EXTRA;
import static com.campusstreet.common.Const.USERINFO_EXTRA;

/**
 * Created by Orange on 2017/4/10.
 */

public class AssociationDetailActivity extends BaseActivity implements IAssociationContract.View {
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
    @BindView(R.id.rv_content)
    RecyclerView mRvContent;
    @BindView(R.id.tv_introduce)
    TextView mTvIntroduce;
    @BindView(R.id.btn_join)
    Button mBtnJoin;
    @BindView(R.id.rl_association_content)
    RelativeLayout mRlAssociationContent;
    @BindView(R.id.iv_notice_icon)
    ImageView mIvNoticeIcon;
    @BindView(R.id.rl_notice)
    RelativeLayout mRlNotice;
    @BindView(R.id.fab_add_task)
    FloatingActionButton mFabAddTask;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.progress_bar_title)
    TextView mProgressBarTitle;
    @BindView(R.id.progress_bar_container)
    LinearLayout mProgressBarContainer;
    @BindView(R.id.iv_association_content_bg)
    ImageView mIvAssociationContentBg;
    @BindView(R.id.scrollView)
    NestedScrollView mScrollView;
    private IAssociationContract.Presenter mPresenter;
    private int mPi = 0;
    private AssociationInfo mAssociationInfo;
    private UserAssociationInfo mUserAssociationInfo;
    private AssociationDetailRecyclerViewAdapter mAdapter;
    private UserInfo mUserInfo;
    private boolean mIsLoading;
    private boolean mIsNeedLoadMore = true;
    private String mNotice;
    PopupWindow mPopWin;
    View mPopView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_association_detail);
        ButterKnife.bind(this);
        mToolbar.setTitle("");
        mToolbarTitle.setText(getString(R.string.act_association_detail_toolbar_title));
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
        mIvToolbarRight.setVisibility(View.GONE);
        mIvToolbarRight.setImageResource(R.drawable.ic_more);
        new AssociationPresenter(AssociationImpl.getInstance(getApplicationContext()), this);
        mUserAssociationInfo = (UserAssociationInfo) getIntent().getSerializableExtra(Const.USERASSOCIATIONINFO_EXTRA);
        mAssociationInfo = (AssociationInfo) getIntent().getSerializableExtra(Const.ASSOCIATIONINFO_EXTRA);
        mUserInfo = (UserInfo) getIntent().getSerializableExtra(USERINFO_EXTRA);
        initView();
        initEvent();
        if (mAssociationInfo != null) {
            mPresenter.fetchAssociationNumList(mAssociationInfo.getId(), mPi, 1000);
        } else {
            mPresenter.fetchAssociationNumList(mUserAssociationInfo.getAssnid(), mPi, 1000);
        }
        if (mAssociationInfo != null) {
            mPresenter.fetchAssociationPostList(mAssociationInfo.getId(), mPi);
        } else {
            mPresenter.fetchAssociationPostList(mUserAssociationInfo.getAssnid(), mPi);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            mPi = 0;
            if (mAssociationInfo != null) {
                mPresenter.fetchAssociationPostList(mAssociationInfo.getId(), mPi);
            } else {
                mPresenter.fetchAssociationPostList(mUserAssociationInfo.getAssnid(), mPi);
            }
            setLoadingIndicator(true);
        }
    }

    private void initEvent() {
        mScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                View contentView = mScrollView.getChildAt(0);
                if (mIsNeedLoadMore && !mIsLoading && contentView.getMeasuredHeight() <= mScrollView.getScrollY() + mScrollView.getHeight()) {
                    if (mAssociationInfo != null) {
                        mPresenter.fetchAssociationPostList(mAssociationInfo.getId(), ++mPi);
                    } else {
                        mPresenter.fetchAssociationPostList(mUserAssociationInfo.getAssnid(), ++mPi);
                    }
                    mIsLoading = true;
                }

            }

        });
        mAdapter.setOnItemClickListener(new AssociationDetailRecyclerViewAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, AssociationPostInfo associationPostInfo) {
                Intent intent = new Intent(AssociationDetailActivity.this, PostDetailActivity.class);
                intent.putExtra(Const.ASSOCIATIONPOSTINFO_EXTRA, associationPostInfo);
                intent.putExtra(USERINFO_EXTRA, mUserInfo);
                startActivity(intent);
            }
        });
    }

    private void initView() {
        mBtnJoin.setVisibility(View.VISIBLE);
        mFabAddTask.setVisibility(View.GONE);
        if (mAssociationInfo != null) {
            mTvName.setText(mAssociationInfo.getName());
            mTvIntroduce.setText(mAssociationInfo.getNote());
            Picasso.with(this)
                    .load(AppConfig.PIC_ASSOCIATION_SERVER_HOST + mAssociationInfo.getClassimg())
                    .fit()
                    .into(mIvHead);
            if (mUserInfo == null) {
                mBtnJoin.setVisibility(View.GONE);
                mFabAddTask.setVisibility(View.GONE);
            } else {
                mBtnJoin.setVisibility(View.VISIBLE);
                mFabAddTask.setVisibility(View.GONE);
            }
        } else if (mUserAssociationInfo != null) {
            mTvName.setText(mUserAssociationInfo.getAssnname());
            mTvIntroduce.setText(mUserAssociationInfo.getNote());
            Picasso.with(this)
                    .load(AppConfig.PIC_ASSOCIATION_SERVER_HOST + mUserAssociationInfo.getClassimg())
                    .fit()
                    .into(mIvHead);
            mBtnJoin.setVisibility(View.GONE);
            mFabAddTask.setVisibility(View.VISIBLE);
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRvContent.setLayoutManager(linearLayoutManager);
        mAdapter = new AssociationDetailRecyclerViewAdapter(this, null);
        mRvContent.setAdapter(mAdapter);
        mRvContent.setNestedScrollingEnabled(false);
    }


    @OnClick({R.id.iv_toolbar_right, R.id.btn_join, R.id.rl_notice, R.id.fab_add_task})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_toolbar_right:
//                showAlertDialog("发布公告");
                showPopWin(mIvToolbarRight);
                break;
            case R.id.btn_join:
                if (mUserInfo != null) {
                    showAlertDialog("备注");
                } else {
                    showMessage("您还未登录");
                }
                break;
            case R.id.rl_notice:
                Intent intent = new Intent(this, NoticeActivity.class);
                startActivity(intent);
                break;
            case R.id.fab_add_task:
                if (mUserInfo != null) {
                    intent = new Intent(this, AddPostActivity.class);
                    intent.putExtra(USERINFO_EXTRA, mUserInfo);
                    intent.putExtra(USERASSOCIATIONINFO_EXTRA, mUserAssociationInfo);
                    intent.putExtra(Const.ASSOCIATIONINFO_EXTRA, mAssociationInfo);
                    startActivityForResult(intent,REQUEST_CODE);
                } else {
                    showMessage("您还未登录");
                }
                break;
        }
    }

    @Override
    public void setPresenter(IAssociationContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void setAssociationPostMessageList(List<AssociationPostMessageInfo> associationPostMessageList) {

    }

    @Override
    public void setAssociationPostList(List<AssociationPostInfo> associationPostList) {
        if (associationPostList != null && associationPostList.size() < 20) {
            mIsNeedLoadMore = false;
        } else {
            mIsNeedLoadMore = true;
        }
        if (mPi != 0) {
            if (associationPostList != null) {
                mAdapter.addData(associationPostList);
                mIsLoading = false;
            }
        } else {
            mAdapter.replaceData(associationPostList);
            setLoadingIndicator(false);
        }
    }

    @Override
    public void setAssociationNumList(List<AssociationNumInfo> associationNumList) {
        for (AssociationNumInfo associationNumInfo :
                associationNumList) {
            if (mUserInfo != null) {
                if (associationNumInfo.getUid().equals(mUserInfo.getUid())) {
                    if (associationNumInfo.getAssnjob() == 1 || associationNumInfo.getAssnjob() == 2) {
                        mIvToolbarRight.setVisibility(View.VISIBLE);
                    } else {
                        mIvToolbarRight.setVisibility(View.GONE);
                    }
                    mBtnJoin.setVisibility(View.GONE);
                    mFabAddTask.setVisibility(View.VISIBLE);
                    break;
                }
            }
        }
    }

    @Override
    public void setAssociationList(List<AssociationInfo> associationList) {

    }

    @Override
    public void setUserAssociationList(List<UserAssociationInfo> UserAssociationList) {

    }

    @Override
    public void showSuccessfullyJoin(String succcessMsg) {
        showMessage(succcessMsg);
    }

    @Override
    public void showSuccessfullyAddNotice(String succcessMsg) {
        showMessage(succcessMsg);
        mTvIntroduce.setText(mNotice);
    }

    @Override
    public void showSuccessfullyApplyJoin(String succcessMsg) {

    }

    @Override
    public void showSuccessfullyPushPost(String succcessMsg) {

    }

    @Override
    public void setAssociationPostDetail(AssociationPostInfo associationPostInfo) {
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

    private void showAlertDialog(String text) {
        if (text.equals("备注")) {
            final EditText et = new EditText(this);
            et.setFilters(new InputFilter[]{new InputFilter.LengthFilter(30)});
            new AlertDialog.Builder(this).setTitle(text)
                    .setView(et)
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            String input = et.getText().toString();
                            if (!input.equals("")) {
                                mPresenter.onJoinAssociation(mAssociationInfo.getId(), mUserInfo.getUid(), input);
                            } else {
                                showErrorMsg("备注信息不能为空");
                            }
                        }
                    })
                    .setNegativeButton("取消", null)
                    .show();
        } else {
            final EditText et = new EditText(this);
            et.setFilters(new InputFilter[]{new InputFilter.LengthFilter(50)});
            new AlertDialog.Builder(this).setTitle(text)
                    .setView(et)
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            mNotice = et.getText().toString();
                            if (!mNotice.equals("")) {
                                if (mAssociationInfo != null) {
                                    mPresenter.addAssociationNotice(mAssociationInfo.getId(), mUserInfo.getUid(), mNotice);
                                }else{
                                    mPresenter.addAssociationNotice(mUserAssociationInfo.getAssnid(), mUserInfo.getUid(), mNotice);
                                }

                            } else {
                                showErrorMsg("公告不能为空");
                            }
                        }
                    })
                    .setNegativeButton("取消", null)
                    .show();
        }
    }

    private void showPopWin(View view)
    {
        if (mPopWin == null)
        {
            LayoutInflater inflater =(LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //绑定布局
            mPopView = inflater.inflate(R.layout.popupwindow_association, null);
            Button noticeBtn = (Button)mPopView.findViewById(R.id.btn_release_notice);
            noticeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mPopWin != null)
                    {
                        showAlertDialog("发布公告");
                        mPopWin.dismiss();
                    }
                }
            });

            Button memberBtn = (Button)mPopView.findViewById(R.id.btn_association_memebers);
            memberBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mPopWin != null)
                    {
                        Intent intent = new Intent(AssociationDetailActivity.this, AssociationMembersActivity.class);
                        intent.putExtra(USERINFO_EXTRA, mUserInfo);
                        intent.putExtra(USERASSOCIATIONINFO_EXTRA, mUserAssociationInfo);
                        intent.putExtra(Const.ASSOCIATIONINFO_EXTRA, mAssociationInfo);
                        startActivity(intent);
                        mPopWin.dismiss();
                    }
                }
            });

            mPopWin = new PopupWindow(mPopView,200,200);
        }
        mPopWin.setFocusable(true);
        mPopWin.setOutsideTouchable(true);
        // 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
        mPopWin.setBackgroundDrawable(new BitmapDrawable());

        int xPos = -mPopWin.getWidth() / 2
                + mIvToolbarRight.getWidth() / 2;

        mPopWin.showAsDropDown(view, xPos, 4);

    }


}
