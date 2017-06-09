package com.campusstreet.activity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.campusstreet.R;
import com.campusstreet.common.AppConfig;
import com.campusstreet.common.Const;
import com.campusstreet.contract.ISettingContract;
import com.campusstreet.entity.UserInfo;
import com.campusstreet.model.SettingImpl;
import com.campusstreet.presenter.SettingPresenter;
import com.squareup.picasso.Picasso;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by Orange on 2017/4/6.
 */

public class ModifyHeadActivity extends BaseActivity implements ISettingContract.View {

    protected final String TAG = getClass().getSimpleName();
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.tv_toolbar_right)
    TextView mTvToolbarRight;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.iv_head)
    CircleImageView mIvHead;
    @BindView(R.id.btn_choice_head)
    Button mBtnChoiceHead;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.progress_bar_title)
    TextView mProgressBarTitle;
    @BindView(R.id.progress_bar_container)
    LinearLayout mProgressBarContainer;
    private ISettingContract.Presenter mPresenter;

    private File mFile = null;

    public static final int REQUEST_UPDATE_AVATAR = 1;
    private UserInfo mUserInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_head);
        ButterKnife.bind(this);
        mToolbar.setTitle("");
        mToolbarTitle.setText(getString(R.string.act_modify_head_toolbar_title));
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
        new SettingPresenter(SettingImpl.getInstance(getApplicationContext()), this);
        mUserInfo = (UserInfo) getIntent().getSerializableExtra(Const.USERINFO_EXTRA);
        Picasso.with(this)
                .load(AppConfig.AVATAR_SERVER_HOST + mUserInfo.getUserpic())
                .fit()
                .into(mIvHead);
    }

    @OnClick({R.id.tv_toolbar_right, R.id.btn_choice_head})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_toolbar_right:
                modifyHead();
                break;
            case R.id.btn_choice_head:
                Intent mIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(mIntent, REQUEST_UPDATE_AVATAR);
                break;
        }
    }

    private void modifyHead() {

        RequestBody body = null;
        if (mFile != null) {
            body = RequestBody.create(MediaType.parse("application/otcet-stream"), mFile);
            mPresenter.reviseHead(mUserInfo.getUid(), MultipartBody.Part.createFormData("avatar", mFile.getName(), body));
        }
    }

    @Override
    public void setPresenter(ISettingContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showSuccessMsg(String successMsg) {
        showMessage("修改头像成功");
        Intent data = new Intent(this, MainActivity.class);
        mUserInfo.setUserpic(successMsg);
        data.putExtra(Const.USERINFO_EXTRA, mUserInfo);
        startActivity(data);
    }

    @Override
    public void showErrorMsg(String errorMsg) {
        showMessage(errorMsg);
    }

    @Override
    public void showSuccessfullyChangePassword() {

    }

    @Override
    public void setLoadingIndicator(boolean active) {
        if (mProgressBarContainer != null) {
            if (active) {
                //设置滚动条可见
                mProgressBarContainer.setVisibility(View.VISIBLE);
                mProgressBarTitle.setText(R.string.Modifying_progress_bar_title);
            } else {
                if (mProgressBarContainer.getVisibility() == View.VISIBLE) {
                    mProgressBarContainer.setVisibility(View.GONE);
                }
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_UPDATE_AVATAR && resultCode == RESULT_OK) {
            fetchImage(data);
        }
    }

    private void fetchImage(Intent data) {
        String path = null;
        Uri uri = data.getData();
        Cursor cursor = null;
        try {
            cursor = this.getContentResolver().query(uri, null, null, null, null);
            int column_index = 0;
            if (cursor != null) {
                column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();
                path = cursor.getString(column_index);
                mFile = new File(path);
                mIvHead.setImageURI(data.getData());

            }
        } catch (Exception e) {
            Log.e(TAG, "fetchAvatar: 选择图片异常", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }
}
