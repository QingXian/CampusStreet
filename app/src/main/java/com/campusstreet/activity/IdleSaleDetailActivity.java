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
import com.campusstreet.adapter.LeaveMessageRecycleViewAdapter;
import com.campusstreet.common.AppConfig;
import com.campusstreet.common.Const;
import com.campusstreet.contract.IIdleSaleContract;
import com.campusstreet.entity.CategoriesInfo;
import com.campusstreet.entity.IdleSaleInfo;
import com.campusstreet.entity.LeaveMessageInfo;
import com.campusstreet.entity.UserInfo;
import com.campusstreet.model.IdleSaleImpl;
import com.campusstreet.presenter.IdleSalePresenter;
import com.squareup.picasso.Picasso;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.campusstreet.common.Const.ID_EXTRA;
import static com.campusstreet.utils.DataUtil.getTimeRange;

/**
 * Created by Orange on 2017/4/7.
 */

public class IdleSaleDetailActivity extends AppCompatActivity implements IIdleSaleContract.View {
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.iv_photo)
    Banner mIvPhoto;
    @BindView(R.id.tv_symbol)
    TextView mTvSymbol;
    @BindView(R.id.tv_price)
    TextView mTvPrice;
    @BindView(R.id.tv_time_hint)
    TextView mTvTimeHint;
    @BindView(R.id.tv_time)
    TextView mTvTime;
    @BindView(R.id.tv_selltype)
    TextView mTvSellType;
    @BindView(R.id.rl_commodity_content)
    RelativeLayout mRlCommodityContent;
    @BindView(R.id.iv_head)
    CircleImageView mIvHead;
    @BindView(R.id.tv_tradetype)
    TextView mTvTradetype;
    @BindView(R.id.tv_place)
    TextView mTvPlace;
    @BindView(R.id.tv_phone_hint)
    TextView mTvPhoneHint;
    @BindView(R.id.tv_phone)
    TextView mTvPhone;
    @BindView(R.id.rv_content)
    RecyclerView mRvContent;
    @BindView(R.id.et_message)
    EditText mEtMessage;
    @BindView(R.id.btn_send_message)
    Button mBtnSendMessage;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_degree)
    TextView mTvDegree;
    @BindView(R.id.tv_content)
    TextView mTvContent;
    @BindView(R.id.tv_qq)
    TextView mTvQq;
    @BindView(R.id.tv_qq_hint)
    TextView mTvQqHint;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.progress_bar_title)
    TextView mProgressBarTitle;
    @BindView(R.id.progress_bar_container)
    LinearLayout mProgressBarContainer;
    @BindView(R.id.view)
    View mView;
    @BindView(R.id.scrollView)
    NestedScrollView mScrollView;
    private LinearLayoutManager mLinearLayoutManager;
    private IIdleSaleContract.Presenter mPresenter;
    private LeaveMessageRecycleViewAdapter mAdapter;
    private IdleSaleInfo mIdleSaleInfo;
    private int mImageNum = 0;
    private List<String> mImageList = new ArrayList<>();
    private String[] mImages;
    private int mPi = 0;
    private UserInfo mUserInfo;
    private boolean mIsLoading;
    private int mLastVisibleItemPosition;
    private int mItemCount;
    private boolean mIsNeedLoadMore = true;
    private int mId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_idle_sale_detail);
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
        mIdleSaleInfo = (IdleSaleInfo) getIntent().getSerializableExtra(Const.IDLESALEINFO_EXTRA);
        new IdleSalePresenter(IdleSaleImpl.getInstance(getApplicationContext()), this);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRvContent.setLayoutManager(mLinearLayoutManager);
        mAdapter = new LeaveMessageRecycleViewAdapter(this, null);
        mRvContent.setAdapter(mAdapter);
        mRvContent.setNestedScrollingEnabled(false);
        if (mIdleSaleInfo == null) {
            mId = getIntent().getIntExtra(ID_EXTRA, 0);
            mPresenter.fetchIdleSaleDetail(mId);
        } else {
            initView();
            mPresenter.fetchIdleSaleMessageList(mIdleSaleInfo.getId(), mPi);
        }
        initEvent();
        setLoadingIndicator(true);
        mUserInfo = (UserInfo) getIntent().getSerializableExtra(Const.USERINFO_EXTRA);
    }

    private void initEvent() {
        mScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                View contentView = mScrollView.getChildAt(0);
                if (mIsNeedLoadMore && !mIsLoading && contentView.getMeasuredHeight() <= mScrollView.getScrollY() + mScrollView.getHeight()) {
                    mPresenter.fetchIdleSaleMessageList(mIdleSaleInfo.getId(), ++mPi);
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
//                    mPresenter.fetchIdleSaleMessageList(mIdleSaleInfo.getId(), ++mPi);
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

    private void initView() {

        mToolbarTitle.setText(mIdleSaleInfo.getName());
        mTvPrice.setText(mIdleSaleInfo.getMoney());
        mTvName.setText(mIdleSaleInfo.getUsername());
        Picasso.with(this)
                .load(AppConfig.AVATAR_SERVER_HOST + mIdleSaleInfo.getUserpic())
                .fit()
                .error(R.drawable.ic_head_test)
                .into(mIvHead);
        mTvDegree.setText(mIdleSaleInfo.getBewrite());
        mTvContent.setText(mIdleSaleInfo.getContent());

        if (mIdleSaleInfo.getSelltype().equals(0)) {
            mTvSellType.setText("一口价");
        } else if (mIdleSaleInfo.getSelltype().equals(1)) {
            mTvSellType.setText("可小刀");
        }
        String time = getTimeRange(mIdleSaleInfo.getGpublishtime());
        mTvTime.setText(time);
        if (mIdleSaleInfo.getTradetype().equals(0)) {
            mTvTradetype.setText("见面交易");
        }
        mTvPlace.setText(mIdleSaleInfo.getTradeplace());
        mTvPhone.setText(mIdleSaleInfo.getMobile());
        mTvQq.setText(mIdleSaleInfo.getQq());
        initImage(mIdleSaleInfo.getImages());
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
                mImageList.add(images);
                setImage(mImageList);
            } else if (mImageNum == 1) {
                mImages = images.split(",");
                mImageList.add(mImages[0]);
                mImageList.add(mImages[1]);
                setImage(mImageList);
            } else if (mImageNum == 2) {
                mImages = images.split(",");
                mImageList.add(mImages[0]);
                mImageList.add(mImages[1]);
                mImageList.add(mImages[2]);
                setImage(mImageList);
            }
        }
    }

    private void setImage(List<String> images) {
        mIvPhoto.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        // 设置图片加载器
        mIvPhoto.setImageLoader(new PicassoImageLoader());
        // 设置banner动画效果
        mIvPhoto.setBannerAnimation(Transformer.Default);
        // 设置标题集合（当banner样式有显示title时）
        // mBanner.setBannerTitles(Arrays.asList(mTitles));
        // 设置指示器位置（当banner模式中有指示器时）
        mIvPhoto.setIndicatorGravity(BannerConfig.RIGHT);
        mIvPhoto.setImages(images);
        mIvPhoto.start();
    }


    @OnClick(R.id.btn_send_message)
    public void onClick() {
        if (TextUtils.isEmpty(mEtMessage.getText().toString().trim())) {
            showMessage("请填写留言内容");
            return;
        }
        if (mUserInfo != null) {
            mPresenter.leaveMessage(mUserInfo.getUid(), mIdleSaleInfo.getId(), mEtMessage.getText().toString().trim());
            setLoadingIndicator(true);
        } else {
            showMessage("您还未登录");
        }
    }

    @Override
    public void setPresenter(IIdleSaleContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void setIdleSale(List<IdleSaleInfo> idleSaleInfoList) {
        //忽略
    }

    @Override
    public void setIdleSaleCategories(List<CategoriesInfo> categories) {

    }

    @Override
    public void setIdleSaleMessageList(List<LeaveMessageInfo> idleSaleMessageList) {
        if (idleSaleMessageList != null && idleSaleMessageList.size() < 20) {
            mIsNeedLoadMore = false;
        } else {
            mIsNeedLoadMore = true;
        }
        if (mPi != 0) {
            if (idleSaleMessageList != null) {
                mAdapter.addData(idleSaleMessageList);
                mIsLoading = false;
            }
        } else {
            mAdapter.replaceData(idleSaleMessageList);
            setLoadingIndicator(false);
        }
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
    public void showSuccessfullyPush(String succcessMsg) {//忽略
    }

    @Override
    public void showSuccessfullyleaveMessage(String succcessMsg) {
        showMessage(succcessMsg);
        mPi = 0;
        setLoadingIndicator(true);
        mPresenter.fetchIdleSaleMessageList(mIdleSaleInfo.getId(), mPi);
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
        }
        mEtMessage.setText("");
        mScrollView.smoothScrollTo(0, 0);
    }

    @Override
    public void setIdleSaleDetail(IdleSaleInfo idleSaleInfo) {
        mToolbarTitle.setText(idleSaleInfo.getName());
        mTvPrice.setText(idleSaleInfo.getMoney());
        mTvName.setText(idleSaleInfo.getUsername());
        Picasso.with(this)
                .load(AppConfig.AVATAR_SERVER_HOST + idleSaleInfo.getUserpic())
                .fit()
                .error(R.drawable.ic_head_test)
                .into(mIvHead);
        mTvDegree.setText(idleSaleInfo.getBewrite());
        mTvContent.setText(idleSaleInfo.getContent());

        if (idleSaleInfo.getSelltype().equals(0)) {
            mTvSellType.setText("一口价");
        } else if (idleSaleInfo.getSelltype().equals(1)) {
            mTvSellType.setText("可小刀");
        }
        String time = getTimeRange(idleSaleInfo.getGpublishtime());
        mTvTime.setText(time);
        if (idleSaleInfo.getTradetype().equals(0)) {
            mTvTradetype.setText("见面交易");
        }
        mTvPlace.setText(idleSaleInfo.getTradeplace());
        mTvPhone.setText(idleSaleInfo.getMobile());
        mTvQq.setText(idleSaleInfo.getQq());
        initImage(idleSaleInfo.getImages());
        mPresenter.fetchIdleSaleMessageList(idleSaleInfo.getId(), mPi);
        mIdleSaleInfo = idleSaleInfo;
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

    private class PicassoImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Picasso.with(context)
                    .load(AppConfig.PIC_EWU_SERVER_HOST + path)
                    .placeholder(R.drawable.ic_base_picture)
                    .error(R.drawable.ic_pic_error)
                    .fit()
                    .into(imageView);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        mIvPhoto.isAutoPlay(false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mImageList.clear();
    }
}
