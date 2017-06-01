package com.campusstreet.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.campusstreet.R;
import com.campusstreet.adapter.MessageFragmentRecyclerViewAdapter;
import com.campusstreet.common.Const;
import com.campusstreet.contract.IMessageContract;
import com.campusstreet.entity.MessageInfo;
import com.campusstreet.entity.UserInfo;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Orange on 2017/4/1.
 */

public class MessageFragment extends Fragment implements IMessageContract.View {

    @BindView(R.id.rv_content)
    PullLoadMoreRecyclerView mRvContent;
    @BindView(R.id.tv_error)
    TextView mTvError;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.progress_bar_title)
    TextView mProgressBarTitle;
    @BindView(R.id.progress_bar_container)
    LinearLayout mProgressBarContainer;
    private IMessageContract.Presenter mPresenter;
    private MessageFragmentRecyclerViewAdapter mAdapter;
    private Unbinder mUnbinder;
    private int mPi;
    private UserInfo mUserInfo;

    public static MessageFragment newInstance(UserInfo userInfo) {
        Bundle args = new Bundle();
        args.putSerializable(Const.USERINFO_EXTRA, userInfo);
        MessageFragment messageFragment = new MessageFragment();
        messageFragment.setArguments(args);
        return messageFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUserInfo = (UserInfo) getArguments().getSerializable(Const.USERINFO_EXTRA);
        }
        if (mUserInfo != null) {
            mPi = 0;
//            mPresenter.fetchMessageList(mUserInfo.getUid(), mPi);
//            setLoadingIndicator(true);
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_message, container, false);
        mUnbinder = ButterKnife.bind(this, root);

        mRvContent.setLinearLayout();
        mAdapter = new MessageFragmentRecyclerViewAdapter(getActivity(), null);
        mRvContent.addItemDecoration(new DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL));
        mRvContent.setAdapter(mAdapter);
        initView();
        initEvent();
        return root;
    }

    private void initEvent() {
        mAdapter.setOnItemClickListener(new MessageFragmentRecyclerViewAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, MessageInfo messageInfo) {
//                switch (messageInfo.getType()) {
//                    case 1:
//                        Intent intent = new Intent(getActivity(), IdleSaleDetailActivity.class);
//                        intent.putExtra(ID_EXTRA, messageInfo.getMainid());
//                        startActivity(intent);
//                        break;
//                    case 2:
//                        intent = new Intent(getActivity(), BuyZoneDetailActivity.class);
//                        intent.putExtra(ID_EXTRA, messageInfo.getMainid());
//                        startActivity(intent);
//                        break;
//                    case 3:
//                        intent = new Intent(getActivity(), CampusRecruitmentActivity.class);
//                        intent.putExtra(TYPE, messageInfo.getType());
//                        intent.putExtra(ID_EXTRA, messageInfo.getMainid());
//                        startActivity(intent);
//                        break;
//                    case 4:
//                        intent = new Intent(getActivity(), BountyHallDetailActivity.class);
//                        intent.putExtra(ID_EXTRA, messageInfo.getMainid());
//                        startActivity(intent);
//                        break;
//                    case 5:
//                        intent = new Intent(getActivity(), PostDetailActivity.class);
//                        intent.putExtra(ID_EXTRA, messageInfo.getMainid());
//                        startActivity(intent);
//                        break;
//                    case 6:
//                        intent = new Intent(getActivity(), CampusInformationDetailActivity.class);
//                        intent.putExtra(ID_EXTRA, messageInfo.getMainid());
//                        startActivity(intent);
//                        break;
//                    case 7:
//                        intent = new Intent(getActivity(), PartnerDetailActivity.class);
//                        intent.putExtra(ID_EXTRA, messageInfo.getMainid());
//                        startActivity(intent);
//                        break;
//                    case 8:
//                        intent = new Intent(getActivity(), CampusRecruitmentDetailActivity.class);
//                        intent.putExtra(TYPE, messageInfo.getType());
//                        intent.putExtra(ID_EXTRA, messageInfo.getMainid());
//                        startActivity(intent);
//                        break;
            }
        });
        mRvContent.setOnPullLoadMoreListener(new PullLoadMoreRecyclerView.PullLoadMoreListener() {
            @Override
            public void onRefresh() {
                mPi = 0;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPresenter.fetchMessageList(mUserInfo.getUid(), mPi);
                    }
                }, 1500);
            }

            @Override
            public void onLoadMore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPresenter.fetchMessageList(mUserInfo.getUid(), ++mPi);
                    }
                }, 500);
            }
        });
    }

    private void initView() {
        mRvContent.setLinearLayout();
        mAdapter = new MessageFragmentRecyclerViewAdapter(getActivity(), null);
        mRvContent.addItemDecoration(new DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL));
        mRvContent.setAdapter(mAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    @Override
    public void setMessageList(List<MessageInfo> messageList) {
        if (messageList != null && messageList.size() < 20) {
            mRvContent.setPushRefreshEnable(false);
        } else {
            mRvContent.setPushRefreshEnable(true);
        }
        if (mPi != 0) {
            if (messageList != null) {
                mAdapter.addData(messageList);
                mRvContent.setPullLoadMoreCompleted();
            }
        } else {
            mRvContent.setVisibility(View.VISIBLE);
            mTvError.setVisibility(View.GONE);
            mAdapter.replaceData(messageList);
            mRvContent.setPullLoadMoreCompleted();
            setLoadingIndicator(false);
        }
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
        if (this != null && !getActivity().isFinishing()) {
            Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void setPresenter(IMessageContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
