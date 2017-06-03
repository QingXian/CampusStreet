package com.campusstreet.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.campusstreet.adapter.FindFragmentRecyclerViewAdapter;
import com.campusstreet.adapter.MessageFragmentRecyclerViewAdapter;
import com.campusstreet.contract.IFindContract;
import com.campusstreet.entity.LiveInfo;
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
    @BindView(R.id.rv_content)
    PullLoadMoreRecyclerView mRvContent;

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
        mRvContent.setOnPullLoadMoreListener(new PullLoadMoreRecyclerView.PullLoadMoreListener() {
            @Override
            public void onRefresh() {
            }

            @Override
            public void onLoadMore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPresenter.fetchFindList(++mPi);
                    }
                }, 500);
            }
        });
    }

    private void initView() {
        mRvContent.setLinearLayout();
        mAdapter = new FindFragmentRecyclerViewAdapter(getActivity(), null);
        mRvContent.addItemDecoration(new DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL));
        mRvContent.setAdapter(mAdapter);
        mRvContent.setPushRefreshEnable(false);
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
    public void setFindList(List<LiveInfo> liveInfos) {
        if (liveInfos != null && liveInfos.size() < 20) {
            mRvContent.setPushRefreshEnable(false);
        } else {
            mRvContent.setPushRefreshEnable(true);
        }
        if (mPi != 0) {
            if (liveInfos != null) {
                mAdapter.addData(liveInfos);
                mRvContent.setPullLoadMoreCompleted();
            }
        } else {
            mRvContent.setVisibility(View.VISIBLE);
            mTvError.setVisibility(View.GONE);
            mAdapter.replaceData(liveInfos);
            mRvContent.setPullLoadMoreCompleted();
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
        }
        setLoadingIndicator(false);
        mRvContent.setPullLoadMoreCompleted();
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
