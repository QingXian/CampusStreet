package com.campusstreet.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.campusstreet.R;
import com.campusstreet.activity.BountyHallDetailActivity;
import com.campusstreet.activity.BuyZoneDetailActivity;
import com.campusstreet.activity.CampusInformationDetailActivity;
import com.campusstreet.activity.CampusRecruitmentActivity;
import com.campusstreet.activity.CampusRecruitmentDetailActivity;
import com.campusstreet.activity.IdleSaleDetailActivity;
import com.campusstreet.activity.PartnerDetailActivity;
import com.campusstreet.activity.PostDetailActivity;
import com.campusstreet.adapter.MessageFragmentRecyclerViewAdapter;
import com.campusstreet.api.AssociationClient;
import com.campusstreet.api.ServiceGenerator;
import com.campusstreet.common.Const;
import com.campusstreet.contract.IMessageContract;
import com.campusstreet.entity.MessageInfo;
import com.campusstreet.entity.UserInfo;
import com.campusstreet.model.AssociationImpl;
import com.google.gson.JsonObject;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.campusstreet.common.Const.ID_EXTRA;
import static com.campusstreet.common.Const.TYPE;

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
    private AssociationClient mAssociationClient;
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
            mPresenter.fetchMessageList(mUserInfo.getUid(), mPi);
            setLoadingIndicator(true);
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_message, container, false);
        mUnbinder = ButterKnife.bind(this, root);

        mRvContent.setLinearLayout();
        mAdapter = new MessageFragmentRecyclerViewAdapter(getActivity(), null);
        mRvContent.setAdapter(mAdapter);
        initView();
        initEvent();
        return root;
    }

    private void initEvent() {
        mAdapter.setOnItemClickListener(new MessageFragmentRecyclerViewAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, MessageInfo messageInfo) {
                mPresenter.readMessage(mUserInfo.getUid(), messageInfo.getId());
                switch (messageInfo.getTypecode()) {
                    case "join_assn":
                        showAlertDialog(messageInfo);
                        break;
                    case "ewu_reply":
                        Intent intent = new Intent(getActivity(), IdleSaleDetailActivity.class);
                        intent.putExtra(ID_EXTRA, messageInfo.getMainid());
                        startActivity(intent);
                        break;
                    case "wish_reply":
                        intent = new Intent(getActivity(), BuyZoneDetailActivity.class);
                        intent.putExtra(ID_EXTRA, messageInfo.getMainid());
                        startActivity(intent);
                        break;
                    case "task_join":
                        intent = new Intent(getActivity(), BountyHallDetailActivity.class);
                        intent.putExtra(ID_EXTRA, messageInfo.getMainid());
                        startActivity(intent);
                        break;
                    case "task_accept":
                        intent = new Intent(getActivity(), BountyHallDetailActivity.class);
                        intent.putExtra(ID_EXTRA, messageInfo.getMainid());
                        startActivity(intent);
                        break;
                    case "task_execute":
                        intent = new Intent(getActivity(), BountyHallDetailActivity.class);
                        intent.putExtra(ID_EXTRA, messageInfo.getMainid());
                        startActivity(intent);
                        break;
                    case "task_done":
                        intent = new Intent(getActivity(), BountyHallDetailActivity.class);
                        intent.putExtra(ID_EXTRA, messageInfo.getMainid());
                        startActivity(intent);
                        break;
                    case "task_success":
                        intent = new Intent(getActivity(), BountyHallDetailActivity.class);
                        intent.putExtra(ID_EXTRA, messageInfo.getMainid());
                        startActivity(intent);
                        break;
                    case "recruit_push":
                        intent = new Intent(getActivity(), CampusRecruitmentDetailActivity.class);
                        intent.putExtra(ID_EXTRA, messageInfo.getMainid());
                        intent.putExtra(TYPE, 3);
                        startActivity(intent);
                        break;
                }
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
    public void showReadMessageSuccess() {

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

    private void showAlertDialog(final MessageInfo messageInfo) {
        new AlertDialog.Builder(getActivity()).setTitle(messageInfo.getUsername() + "请求加入社团")
                .setMessage("备注：" + messageInfo.getCon())
                .setPositiveButton("同意", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        mAssociationClient = ServiceGenerator.createService(getActivity(), AssociationClient.class);
                        Call<JsonObject> call = mAssociationClient.applyJoinAssn(messageInfo.getMainid(), mUserInfo.getUid(), 1, "");
                        call.enqueue(new Callback<JsonObject>() {
                            @Override
                            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                JsonObject bodyJson = response.body();
                                if (bodyJson != null) {
                                    int res = bodyJson.get(Const.RES_KEY).getAsInt();
                                    if (res == 1) {
                                        showMessage("已同意");
                                    } else {
                                        showMessage(bodyJson.get(Const.MESSAGE_KEY).getAsString());
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<JsonObject> call, Throwable t) {
                                showMessage("网络异常");
                            }
                        });

                    }
                })
                .setNegativeButton("拒绝", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mAssociationClient = ServiceGenerator.createService(getActivity(), AssociationClient.class);
                        Call<JsonObject> call = mAssociationClient.applyJoinAssn(messageInfo.getMainid(), mUserInfo.getUid(), -1, "");
                        call.enqueue(new Callback<JsonObject>() {
                            @Override
                            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                JsonObject bodyJson = response.body();
                                if (bodyJson != null) {
                                    int res = bodyJson.get(Const.RES_KEY).getAsInt();
                                    if (res == 1) {
                                        showMessage("已拒绝");
                                    } else {
                                        showMessage(bodyJson.get(Const.MESSAGE_KEY).getAsString());
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<JsonObject> call, Throwable t) {
                                showMessage("网络异常");
                            }
                        });

                    }
                })
                .show();
    }
}
