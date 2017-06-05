package com.campusstreet.activity;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.campusstreet.R;
import com.campusstreet.common.Const;
import com.campusstreet.contract.ICampusInformationContract;
import com.campusstreet.entity.LeaveMessageInfo;
import com.campusstreet.entity.NewInfo;
import com.campusstreet.entity.RecruitInfo;
import com.campusstreet.model.CampusInformationImpl;
import com.campusstreet.presenter.CampusInformationPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.campusstreet.common.Const.ID_EXTRA;
import static com.campusstreet.utils.DataUtil.getTimeRange;

/**
 * Created by Orange on 2017/5/4.
 */

public class CampusInformationDetailActivity extends AppCompatActivity implements ICampusInformationContract.View {

    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.iv_toolbar_right)
    ImageView mIvToolbarRight;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_time)
    TextView mTvTime;
    @BindView(R.id.tv_content)
    TextView mTvContent;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.progress_bar_title)
    TextView mProgressBarTitle;
    @BindView(R.id.progress_bar_container)
    LinearLayout mProgressBarContainer;
    private ICampusInformationContract.Presenter mPresenter;
    private NewInfo mNewInfo;
    private int mId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campus_information_detail);
        ButterKnife.bind(this);
        mToolbar.setTitle("");
        mToolbarTitle.setText(getString(R.string.frag_home_campus_information));
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
        new CampusInformationPresenter(CampusInformationImpl.getInstance(getApplicationContext()), this);
        mNewInfo = (NewInfo) getIntent().getSerializableExtra(Const.NEWINFO_EXTRA);
        if (mNewInfo == null) {
            mId = getIntent().getIntExtra(ID_EXTRA, 0);
            mPresenter.fetchCampusInformationDetail(mId);
        } else {
            mPresenter.fetchCampusInformationDetail(mNewInfo.getId());
        }
    }


    @Override
    public void setPresenter(ICampusInformationContract.Presenter presenter) {
        mPresenter = presenter;
    }


    @Override
    public void setCampusInformationList(List<NewInfo> newInfos) {

    }

    @Override
    public void setCampusInformationDetail(NewInfo newInfo) {
        mNewInfo = newInfo;
        mTvTitle.setText(mNewInfo.getTitle());
        String time = getTimeRange(mNewInfo.getPubtime());
        mTvTime.setText(time);

//        String html="<font color='red'>样式一</font> <br>";
        CharSequence charSequence = Html.fromHtml(mNewInfo.getSummary());
        mTvContent.setText(charSequence);

    }

    @Override
    public void showErrorMsg(String errorMsg) {
        showMessage(errorMsg);
        mTvContent.setText("没有可显示内容");
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
