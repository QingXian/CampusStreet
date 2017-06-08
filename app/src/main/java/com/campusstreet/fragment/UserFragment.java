package com.campusstreet.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.campusstreet.R;
import com.campusstreet.activity.AssociationActivity;
import com.campusstreet.activity.JoinTaskActivity;
import com.campusstreet.activity.LoginActivity;
import com.campusstreet.activity.MyBountyHallActivity;
import com.campusstreet.activity.MyBuyZoneActivity;
import com.campusstreet.activity.MyIdleSaleActivity;
import com.campusstreet.activity.PayActivity;
import com.campusstreet.common.AppConfig;
import com.campusstreet.common.Const;
import com.campusstreet.entity.UserInfo;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Orange on 2017/4/1.
 */

public class UserFragment extends Fragment {
    @BindView(R.id.iv_head)
    CircleImageView mIvHead;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_department)
    TextView mTvDepartment;
    @BindView(R.id.tv_balance)
    TextView mTvBalance;
    @BindView(R.id.tv_recharge)
    TextView mTvRecharge;
    @BindView(R.id.tv_bounty_mission)
    TextView mTvBountyMission;
    @BindView(R.id.tv_my_idle)
    TextView mTvMyIdle;
    @BindView(R.id.tv_my_club)
    TextView mTvMyClub;
    @BindView(R.id.tv_about_us)
    TextView mTvAboutUs;
    @BindView(R.id.rl_user_info)
    RelativeLayout mRlUserInfo;
    @BindView(R.id.tv_login)
    TextView mTvLogin;
    @BindView(R.id.tv_user_idle)
    TextView mTvUserIdle;
    @BindView(R.id.tv_join_task)
    TextView mTvJoinTask;
    private Unbinder mUnbinder;
    private UserInfo mUserInfo;

    public static UserFragment newInstance(UserInfo userInfo) {
        Bundle args = new Bundle();
        args.putSerializable(Const.USERINFO_EXTRA, userInfo);
        UserFragment userFragment = new UserFragment();
        userFragment.setArguments(args);
        return userFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUserInfo = (UserInfo) getArguments().getSerializable(Const.USERINFO_EXTRA);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_user, container, false);
        mUnbinder = ButterKnife.bind(this, root);
        if (mUserInfo != null) {
            mRlUserInfo.setVisibility(View.VISIBLE);
            mTvLogin.setVisibility(View.GONE);
            initView();
        } else {
            mRlUserInfo.setVisibility(View.GONE);
            mTvLogin.setVisibility(View.VISIBLE);
        }
        return root;
    }

    private void initView() {
        Picasso.with(getActivity())
                .load(AppConfig.AVATAR_SERVER_HOST + mUserInfo.getUserpic())
                .fit()
                .error(R.drawable.ic_head_test)
                .into(mIvHead);
        mTvDepartment.setText(mUserInfo.getMajor());
        mTvName.setText(mUserInfo.getUsername());
    }


    @OnClick({R.id.tv_recharge, R.id.tv_bounty_mission, R.id.tv_user_idle, R.id.tv_my_idle, R.id.tv_my_club, R.id.tv_login, R.id.tv_join_task})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_recharge:
                if (mUserInfo != null) {
                    Intent intent = new Intent(getActivity(), PayActivity.class);
                    intent.putExtra(Const.USERINFO_EXTRA, mUserInfo);
                    startActivity(intent);
                } else {
                    showMessage("请先登录");
                }
                break;
            case R.id.tv_bounty_mission:
                if (mUserInfo != null) {
                    Intent intent = new Intent(getActivity(), MyBountyHallActivity.class);
                    intent.putExtra(Const.USERINFO_EXTRA, mUserInfo);
                    startActivity(intent);
                } else {
                    showMessage("请先登录");
                }
                break;
            case R.id.tv_user_idle:
                if (mUserInfo != null) {
                    Intent intent = new Intent(getActivity(), MyIdleSaleActivity.class);
                    intent.putExtra(Const.USERINFO_EXTRA, mUserInfo);
                    startActivity(intent);
                } else {
                    showMessage("请先登录");
                }
                break;
            case R.id.tv_my_idle:
                if (mUserInfo != null) {
                    Intent intent = new Intent(getActivity(), MyBuyZoneActivity.class);
                    intent.putExtra(Const.USERINFO_EXTRA, mUserInfo);
                    startActivity(intent);
                } else {
                    showMessage("请先登录");
                }
                break;
            case R.id.tv_my_club:
                if (mUserInfo != null) {
                    Intent intent = new Intent(getActivity(), AssociationActivity.class);
                    intent.putExtra(Const.USERINFO_EXTRA, mUserInfo);
                    intent.putExtra(Const.TYPE, 1);
                    startActivity(intent);
                } else {
                    showMessage("请先登录");
                }
                break;
            case R.id.tv_login:
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                intent.putExtra(Const.USERINFO_EXTRA, mUserInfo);
                startActivity(intent);
                break;
            case R.id.tv_join_task:
                if (mUserInfo != null) {
                    intent = new Intent(getActivity(), JoinTaskActivity.class);
                    intent.putExtra(Const.USERINFO_EXTRA, mUserInfo);
                    intent.putExtra(Const.TYPE, 1);
                    startActivity(intent);
                } else {
                    showMessage("请先登录");
                }
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    protected void showMessage(String msg) {
        if (getActivity() != null && !getActivity().isFinishing()) {
            Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
        }
    }

}
