package com.campusstreet.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.campusstreet.R;
import com.campusstreet.activity.ReleaseLiveActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Orange on 2017/4/1.
 */

public class FindFragment extends Fragment {

    @BindView(R.id.iv_user_head)
    ImageView mIvUserHead;
    @BindView(R.id.rl_release)
    RelativeLayout mRlRelease;
    @BindView(R.id.rl_content)
    RecyclerView mRlContent;
    @BindView(R.id.tv_department)
    TextView mTvDepartment;
    private Unbinder mUnbinder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_find, container, false);
        mUnbinder = ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    @OnClick(R.id.rl_release)
    public void onViewClicked() {
        Intent intent = new Intent(getActivity(), ReleaseLiveActivity.class);
        startActivity(intent);
    }
}
