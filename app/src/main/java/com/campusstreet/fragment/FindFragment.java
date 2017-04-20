package com.campusstreet.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.campusstreet.R;
import com.campusstreet.activity.ReleaseLiveActivity;
import com.campusstreet.adapter.FindFragmentRecyclerViewAdapter;
import com.campusstreet.adapter.MessageFragmentRecyclerViewAdapter;
import com.campusstreet.contract.IFindContract;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Orange on 2017/4/1.
 */

public class FindFragment extends Fragment implements IFindContract.View{

    @BindView(R.id.iv_user_head)
    ImageView mIvUserHead;
    @BindView(R.id.rl_release)
    RelativeLayout mRlRelease;
    @BindView(R.id.rv_content)
    RecyclerView mRvContent;
    @BindView(R.id.tv_department)
    TextView mTvDepartment;
    private Unbinder mUnbinder;
    private IFindContract.Presenter mPresenter;
    private FindFragmentRecyclerViewAdapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_find, container, false);
        mUnbinder = ButterKnife.bind(this, root);

        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < 10; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("head", R.drawable.bg_test);
            map.put("title", "震惊");
            map.put("content", "特大新闻");
            list.add(map);
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mRvContent.setLayoutManager(linearLayoutManager);
        mAdapter = new FindFragmentRecyclerViewAdapter(getActivity(), list);
        mRvContent.setNestedScrollingEnabled(false);
        mRvContent.addItemDecoration(new DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL));
        mRvContent.setAdapter(mAdapter);
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

    @Override
    public void setPresenter(IFindContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void setFindList() {

    }

    @Override
    public void setTopImge() {

    }

    @Override
    public void showErrorMsg(String errorMsg) {

    }

    @Override
    public void showSuccessfullyPush(String succcessMsg) {

    }

    @Override
    public void setLoadingIndicator(boolean active) {

    }
}
