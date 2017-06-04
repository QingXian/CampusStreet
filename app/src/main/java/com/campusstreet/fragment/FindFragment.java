package com.campusstreet.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.campusstreet.R;
import com.campusstreet.activity.LiveDetailActivity;
import com.campusstreet.activity.PeripheraShopActivity;
import com.campusstreet.activity.PeripheralShopDetailActivity;
import com.campusstreet.adapter.FindFragmentRecyclerViewAdapter;
import com.campusstreet.adapter.LeaveMessageRecycleViewAdapter;
import com.campusstreet.common.Const;
import com.campusstreet.contract.IFindContract;
import com.campusstreet.entity.LiveInfo;
import com.campusstreet.entity.LiveReplyInfo;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Orange on 2017/4/1.
 */

public class FindFragment extends Fragment implements IFindContract.View {

    @BindView(R.id.iv_top_ad)
    ImageView mIvTopAd;
    @BindView(R.id.tv_error)
    TextView mTvError;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.progress_bar_title)
    TextView mProgressBarTitle;
    @BindView(R.id.progress_bar_container)
    LinearLayout mProgressBarContainer;
    @BindView(R.id.scrollview)
    NestedScrollView mScrollView;
    @BindView(R.id.rv_content)
    RecyclerView mRvContent;
    private boolean mIsLoading;
    private boolean mIsNeedLoadMore = true;

    private Unbinder mUnbinder;
    private IFindContract.Presenter mPresenter;
    private FindFragmentRecyclerViewAdapter mAdapter;
    private int mPi;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPi = 0;
        mPresenter.fetchFindList(mPi);
        setLoadingIndicator(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_find, container, false);
        mUnbinder = ButterKnife.bind(this, root);
        initView();
        initEvent();
        return root;
    }

    private void initEvent() {
        mScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                View contentView = mScrollView.getChildAt(0);
                if (mIsNeedLoadMore && !mIsLoading && contentView.getMeasuredHeight() <= mScrollView.getScrollY() + mScrollView.getHeight()) {
                    mPresenter.fetchFindList(mPi++);
                    mIsLoading = true;
                }

            }

        });
        mAdapter.setOnItemClickListener(new FindFragmentRecyclerViewAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, LiveInfo liveInfo) {
                Intent intent = new Intent(getActivity(), LiveDetailActivity.class);
                intent.putExtra(Const.LIVEINFO_EXTRA, liveInfo);
                startActivity(intent);
            }
        });
    }

    private void initView() {
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mRvContent.setLayoutManager(mLinearLayoutManager);
        mAdapter = new FindFragmentRecyclerViewAdapter(getActivity(), null);
        mRvContent.setAdapter(mAdapter);
        mRvContent.setNestedScrollingEnabled(false);
        mRvContent.addItemDecoration(new DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL));
        mRvContent.setNestedScrollingEnabled(false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
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
        if (liveInfos != null && liveInfos.size() < 20) {
            mIsNeedLoadMore = false;
        } else {
            mIsNeedLoadMore = true;
        }
        if (mPi != 0) {
            if (liveInfos != null) {
                mAdapter.addData(liveInfos);
                mIsLoading = false;
            }
        } else {
            mRvContent.setVisibility(View.VISIBLE);
            mTvError.setVisibility(View.GONE);
            mAdapter.replaceData(liveInfos);
            setLoadingIndicator(false);
        }
    }

    @Override
    public void setTopImge() {

    }

    @Override
    public void showErrorMsg(String errorMsg) {
        if (mPi == 0) {
            mRvContent.setVisibility(View.GONE);
            mTvError.setText(errorMsg);
            mTvError.setVisibility(View.VISIBLE);
        } else {
            showMessage("没有数据了");
            mIsLoading = false;
        }
        setLoadingIndicator(false);
    }

    @Override
    public void showSuccessfullyPush(String succcessMsg) {

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
        if (getActivity() != null && !getActivity().isFinishing()) {
            Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
        }
    }
}
