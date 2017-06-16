package com.campusstreet.activity;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Handler;

import com.campusstreet.R;
import com.campusstreet.common.Const;
import com.campusstreet.contract.ICampusInformationContract;
import com.campusstreet.entity.LeaveMessageInfo;
import com.campusstreet.entity.NewInfo;
import com.campusstreet.entity.RecruitInfo;
import com.campusstreet.model.CampusInformationImpl;
import com.campusstreet.presenter.CampusInformationPresenter;
import com.campusstreet.utils.htmlEscapeUtil;

import java.net.URL;
import java.util.List;
import java.util.logging.LogRecord;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.internal.framed.FrameReader;

import static com.campusstreet.common.Const.ID_EXTRA;
import static com.campusstreet.utils.DataUtil.formatDateToChinese;
import static com.campusstreet.utils.DataUtil.getTimeRange;

/**
 * Created by Orange on 2017/5/4.
 */

public class CampusInformationDetailActivity extends BaseActivity implements ICampusInformationContract.View {

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

    private Handler handler = new Handler() ;

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

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void setCampusInformationDetail(NewInfo newInfo) {
        mNewInfo = newInfo;
        mTvTitle.setText(mNewInfo.getTitle());
        String time = formatDateToChinese(mNewInfo.getPubtime());
        mTvTime.setText(time);
        
        final String summary = htmlEscapeUtil.htmlReplace(mNewInfo.getSummary());
//        Log.i("sssssss", summary);

        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                Spanned text = null;
                if (Build.VERSION.SDK_INT >= 24 )
                {
                    text = Html.fromHtml(summary,0,imgGetter,null);
                }
                else
                    text = Html.fromHtml(summary,imgGetter,null);

                final Spanned htmlText = text;
                handler.post(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        mTvContent.setText(htmlText);
                    }
                });
            }
        }).start();
    }

    private Html.ImageGetter imgGetter = new Html.ImageGetter() //格式语句不一定相同，只要进行网络加载图片即可
    {
        public Drawable getDrawable(String source)
        {
            Drawable drawable = null;
            try
            {
                drawable = Drawable.createFromStream(new URL(source).openStream(), "");//加载网络图片资源核心语句
                DisplayMetrics dm = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(dm);

                //图片高宽处理
                int height = drawable.getIntrinsicHeight();
                int width = drawable.getIntrinsicWidth();
                if(width < 100){
                    drawable.setBounds(0, 0, height, width);
                }else{
                    double scale = (dm.widthPixels - 40)/width;
                    width = dm.widthPixels-40;
                    height = (int)scale*height;
                    drawable.setBounds(0, 0, width, height);
                }
            }
            catch (Exception e)
            {
                return new Drawable()
                {
                    @Override
                    public void draw(@NonNull Canvas canvas) {}
                    @Override
                    public void setAlpha(@IntRange(from = 0, to = 255) int alpha) {}
                    @Override
                    public void setColorFilter(@Nullable ColorFilter colorFilter) {}
                    @Override
                    public int getOpacity() {
                        return 0;
                    }
                };
            }
            return drawable;
        }
    };

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

}
