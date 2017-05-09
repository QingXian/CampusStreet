package com.campusstreet.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.campusstreet.R;
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

import static com.campusstreet.common.Const.USERINFO_EXTRA;

/**
 * Created by Orange on 2017/4/10.
 */

public class AddPostActivity extends AppCompatActivity implements IAssociationContract.View {
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
    @BindView(R.id.iv_add_img)
    ImageView mIvAddImg;
    @BindView(R.id.iv_image1)
    ImageView mIvImage1;
    @BindView(R.id.iv_image2)
    ImageView mIvImage2;
    @BindView(R.id.iv_image3)
    ImageView mIvImage3;
    @BindView(R.id.image_content_ll)
    LinearLayout mImageContentLl;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.progress_bar_title)
    TextView mProgressBarTitle;
    @BindView(R.id.progress_bar_container)
    LinearLayout mProgressBarContainer;
    private UserInfo mUserInfo;
    private IAssociationContract.Presenter mPresenter;
    private AssociationInfo mAssociationInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_add);
        ButterKnife.bind(this);
        mToolbar.setTitle("");
        mToolbarTitle.setText(R.string.act_post_add_toolbar_title);
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        new AssociationPresenter(AssociationImpl.getInstance(getApplicationContext()), this);
        mUserInfo = (UserInfo) getIntent().getSerializableExtra(USERINFO_EXTRA);
        mAssociationInfo = (AssociationInfo) getIntent().getSerializableExtra(Const.ASSOCIATIONINFO_EXTRA);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    @OnClick({R.id.tv_toolbar_right, R.id.iv_add_img})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_toolbar_right:
                if (TextUtils.isEmpty(mEtTitle.getText().toString().trim())) {
                    showMessage("请填写标题");
                    return;
                }
                if (TextUtils.isEmpty(mEtDetail.getText().toString().trim())) {
                    showMessage("请填写内容");
                    return;
                }
                mPresenter.addAssociationPost(mAssociationInfo.getId(), mUserInfo.getUid(), mEtTitle.getText().toString(), mEtDetail.getText().toString());
                break;
            case R.id.iv_add_img:
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
        showMessage(succcessMsg);
        Intent intent = new Intent(this, AssociationDetailActivity.class);
        startActivity(intent);
        this.finish();
    }

    @Override
    public void setAssociationPostDetail(AssociationPostInfo associationPostInfo) {

    }

    @Override
    public void showErrorMsg(String errorMsg) {

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
}
