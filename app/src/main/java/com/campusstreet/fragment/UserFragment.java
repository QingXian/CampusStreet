package com.campusstreet.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.campusstreet.R;
import com.campusstreet.activity.MyBountyHallActivity;

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
    @BindView(R.id.tv_detection_update)
    TextView mTvDetectionUpdate;
    @BindView(R.id.tv_my_idle)
    TextView mTvMyIdle;
    @BindView(R.id.tv_my_club)
    TextView mTvMyClub;
    @BindView(R.id.tv_about_us)
    TextView mTvAboutUs;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_user, container, false);
        ButterKnife.bind(this, root);
        return root;
    }


    @OnClick({R.id.tv_recharge, R.id.tv_bounty_mission, R.id.tv_detection_update, R.id.tv_my_idle, R.id.tv_my_club})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_recharge:
                break;
            case R.id.tv_bounty_mission:
                Intent intent = new Intent(getActivity(), MyBountyHallActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_detection_update:
                break;
            case R.id.tv_my_idle:
                break;
            case R.id.tv_my_club:
                break;
        }
    }
}
