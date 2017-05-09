package com.campusstreet.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.campusstreet.R;
import com.campusstreet.adapter.AssociationDetailRecyclerViewAdapter;
import com.campusstreet.adapter.AssociationRecyclerViewAdapter;
import com.campusstreet.common.AppConfig;
import com.campusstreet.common.Const;
import com.campusstreet.contract.IAssociationContract;
import com.campusstreet.entity.AssociationInfo;
import com.campusstreet.entity.AssociationNumInfo;
import com.campusstreet.entity.AssociationPostInfo;
import com.campusstreet.entity.AssociationPostMessageInfo;
import com.campusstreet.entity.JoinInfo;
import com.campusstreet.entity.UserInfo;
import com.campusstreet.model.AssociationImpl;
import com.campusstreet.presenter.AssociationPresenter;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.campusstreet.R.id.view;
import static com.campusstreet.common.Const.USERINFO_EXTRA;

/**
 * Created by Orange on 2017/4/10.
 */

public class AssociationDetailActivity extends AppCompatActivity implements IAssociationContract.View {
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
    private IAssociationContract.Presenter mPresenter;
    private int mPi = 0;
    private AssociationInfo mAssociationInfo;
    private AssociationDetailRecyclerViewAdapter mAdapter;
    private UserInfo mUserInfo;

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
        mIvToolbarRight.setVisibility(View.VISIBLE);
        mIvToolbarRight.setImageResource(R.drawable.ic_more);
        new AssociationPresenter(AssociationImpl.getInstance(getApplicationContext()), this);
        mAssociationInfo = (AssociationInfo) getIntent().getSerializableExtra(Const.ASSOCIATIONINFO_EXTRA);
        mUserInfo = (UserInfo) getIntent().getSerializableExtra(USERINFO_EXTRA);
        mPresenter.fetchAssociationNumList(mAssociationInfo.getId(), mPi, 1000);
        initView();
        initEvent();
    }

    private void initEvent() {
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
        mTvName.setText(mAssociationInfo.getName());
        mTvIntroduce.setText(mAssociationInfo.getNote());
        Picasso.with(this)
                .load(AppConfig.PIC_ASSOCIATION_SERVER_HOST + mAssociationInfo.getClassimg())
                .fit()
                .into(mIvHead);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRvContent.setLayoutManager(linearLayoutManager);
        mAdapter = new AssociationDetailRecyclerViewAdapter(this, null);
        mRvContent.setAdapter(mAdapter);
        if (mUserInfo == null) {
            mBtnJoin.setVisibility(View.GONE);
            mFabAddTask.setVisibility(View.GONE);
        } else {
            mBtnJoin.setVisibility(View.VISIBLE);
            mFabAddTask.setVisibility(View.VISIBLE);
        }
    }


    protected void onStart() {
        mPresenter.fetchAssociationPostList(mAssociationInfo.getId(), mPi);
        super.onStart();

    }

    @OnClick({R.id.iv_toolbar_right, R.id.btn_join, R.id.rl_notice, R.id.fab_add_task})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_toolbar_right:
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
                    intent.putExtra(Const.ASSOCIATIONINFO_EXTRA, mAssociationInfo);
                    startActivity(intent);
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
        mAdapter.replaceData(associationPostList);
    }

    @Override
    public void setAssociationNumList(List<AssociationNumInfo> associationNumList) {
        for (AssociationNumInfo associationNumInfo :
                associationNumList) {
            if (mUserInfo != null) {
                if (associationNumInfo.getId() == mUserInfo.getUid()) {
                    mBtnJoin.setVisibility(View.GONE);
                    mFabAddTask.setVisibility(View.VISIBLE);
                } else {
                    mBtnJoin.setVisibility(View.VISIBLE);
                    mFabAddTask.setVisibility(View.GONE);
                }
            } else {
                mBtnJoin.setVisibility(View.VISIBLE);
                mFabAddTask.setVisibility(View.GONE);
            }
        }
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
    }

    @Override
    public void showErrorMsg(String errorMsg) {
        showMessage(errorMsg);
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

    protected void showMessage(String msg) {
        if (this != null && !this.isFinishing()) {
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        }
    }

    private void showAlertDialog(String text) {
        final EditText et = new EditText(this);
        new AlertDialog.Builder(this).setTitle(text)
                .setView(et)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String input = et.getText().toString();
                        mPresenter.onJoinAssociation(mAssociationInfo.getId(), mUserInfo.getUid(), input);
                    }
                })
                .setNegativeButton("取消", null)
                .show();
    }

}
