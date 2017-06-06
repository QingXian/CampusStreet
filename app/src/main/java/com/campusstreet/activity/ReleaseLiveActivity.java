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
import com.campusstreet.contract.IFindContract;
import com.campusstreet.entity.IdleSaleInfo;
import com.campusstreet.entity.LiveInfo;
import com.campusstreet.entity.LiveReplyInfo;
import com.campusstreet.entity.UserInfo;
import com.campusstreet.model.FindImpl;
import com.campusstreet.presenter.FindPresenter;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.iwf.photopicker.PhotoPickerActivity;
import me.iwf.photopicker.utils.PhotoPickerIntent;

import static com.campusstreet.activity.AddIdleSaleActivity.REQUEST_CODE;

/**
 * Created by Orange on 2017/4/11.
 */

public class ReleaseLiveActivity extends AppCompatActivity implements IFindContract.View {

    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.iv_toolbar_right)
    TextView mIvToolbarRight;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
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
    @BindView(R.id.tv_content)
    EditText mTvContent;
    private IFindContract.Presenter mPresenter;
    private UserInfo mUserInfo;
    private Set<File> mFiles;
    private ArrayList<String> mImages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_release_live);
        ButterKnife.bind(this);
        mToolbar.setTitle("");
        mToolbarTitle.setText(getString(R.string.act_release_live_toolbar_title));
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
        new FindPresenter(FindImpl.getInstance(getApplicationContext()), this);
        initView();
    }

    private void initView() {
        mIvImage1.setVisibility(View.INVISIBLE);
        mIvImage2.setVisibility(View.INVISIBLE);
        mIvImage3.setVisibility(View.INVISIBLE);
    }

    @Override
    public void setPresenter(IFindContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void setLiveReplyList(List<LiveReplyInfo> liveReplyList) {

    }

    @Override
    public void showDeleteSuccess() {

    }

    @Override
    public void showReplySuccess() {

    }

    @Override
    public void showOperationError(String errorMsg) {

    }

    @Override
    public void setFindList(List<LiveInfo> liveInfos) {

    }

    @Override
    public void setTopImge() {

    }

    @Override
    public void showErrorMsg(String errorMsg) {
        showMessage(errorMsg);
    }

    @Override
    public void showSuccessfullyPush(String succcessMsg) {
        showMessage(succcessMsg);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        this.finish();
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


    @OnClick({R.id.iv_toolbar_right, R.id.iv_add_img})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_toolbar_right:
                releseLive();
                break;
            case R.id.iv_add_img:
                PhotoPickerIntent intent = new PhotoPickerIntent(this);
                intent.setPhotoCount(3);
                intent.setShowCamera(false);
                startActivityForResult(intent, REQUEST_CODE);
                break;
        }
    }

    private void releseLive() {
        if (TextUtils.isEmpty(mTvContent.getText().toString().trim())) {
            showMessage("请填写内容");
            return;
        }
//        if (mImages == null) {
//            showMessage("请选择一张图片");
//            return;
//        }
        if (mImages != null) {
            for (int i = 0; i < mImages.size(); i++) {
                mFiles.add(new File(mImages.get(i)));
            }
        }
        LiveInfo liveinfo = new LiveInfo();
        liveinfo.setUid(mUserInfo.getUid());
        liveinfo.setCon(mTvContent.getText().toString());
        liveinfo.setFiles(mFiles);
        mPresenter.pushLive(liveinfo);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            if (data != null) {
                mImages = data.getStringArrayListExtra(PhotoPickerActivity.KEY_SELECTED_PHOTOS);
                mFiles = new HashSet<>(3);
                if (mImages.size() == 1) {
                    mIvImage1.setVisibility(View.VISIBLE);
                    Picasso.with(this).load(new File(mImages.get(0))).into(mIvImage1);
                    if (mIvImage2.getVisibility() == View.VISIBLE) {
                        mIvImage2.setVisibility(View.INVISIBLE);
                    }
                    if (mIvImage3.getVisibility() == View.VISIBLE) {
                        mIvImage3.setVisibility(View.INVISIBLE);
                    }
                } else if (mImages.size() == 2) {
                    mIvImage1.setVisibility(View.VISIBLE);
                    mIvImage2.setVisibility(View.VISIBLE);
                    Picasso.with(this).load(new File(mImages.get(0))).into(mIvImage1);
                    Picasso.with(this).load(new File(mImages.get(1))).into(mIvImage2);
                    if (mIvImage3.getVisibility() == View.VISIBLE) {
                        mIvImage3.setVisibility(View.INVISIBLE);
                    }
                } else if (mImages.size() == 3) {
                    mIvImage1.setVisibility(View.VISIBLE);
                    mIvImage2.setVisibility(View.VISIBLE);
                    mIvImage3.setVisibility(View.VISIBLE);
                    Picasso.with(this).load(new File(mImages.get(0))).into(mIvImage1);
                    Picasso.with(this).load(new File(mImages.get(1))).into(mIvImage2);
                    Picasso.with(this).load(new File(mImages.get(2))).into(mIvImage3);
                }
            }
        }
    }
}
