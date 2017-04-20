package com.campusstreet.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.campusstreet.R;
import com.campusstreet.adapter.HomeFragmentRecyclerViewAdapter;
import com.campusstreet.adapter.MessageFragmentRecyclerViewAdapter;
import com.campusstreet.contract.IMessageContract;
import com.campusstreet.presenter.MessagePresenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Orange on 2017/4/1.
 */

public class MessageFragment extends Fragment implements IMessageContract.View {
    @BindView(R.id.rv_content)
    RecyclerView mRvContent;

    private IMessageContract.Presenter mPresenter;
    private MessageFragmentRecyclerViewAdapter mAdapter;
    private Unbinder mUnbinder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_message, container, false);
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
        mAdapter = new MessageFragmentRecyclerViewAdapter(getActivity(), list);
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

    @Override
    public void setMessageList() {

    }

    @Override
    public void showErrorMsg(String errorMsg) {

    }

    @Override
    public void setLoadingIndicator(boolean active) {

    }

    @Override
    public void setPresenter(IMessageContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
