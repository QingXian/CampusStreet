package com.campusstreet.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.campusstreet.R;
import com.campusstreet.entity.NewInfo;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Orange on 2017/4/24.
 */

public class CampusInformationRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements  View.OnClickListener {

    private Context mContext;
    private List<NewInfo> mList;
    private OnRecyclerViewItemClickListener mOnItemClickListener;


    public CampusInformationRecyclerViewAdapter(Context context, List<NewInfo> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public void onClick(View view) {
        if (mOnItemClickListener != null) {
            // 注意这里使用getTag方法获取数据
            mOnItemClickListener.onItemClick(view, (NewInfo) view.getTag());
        }
    }

    public static interface OnRecyclerViewItemClickListener {

        void onItemClick(View view, NewInfo newInfo);

    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }


    public void replaceData(List<NewInfo> NewInfos) {
        //Log.d(TAG, "replaceData: assistanceType <== " + assistanceType);
        mList = NewInfos;
        // 调用以下方法更新后，会依次调用getItemViewType和onBindViewHolder方法
        notifyDataSetChanged();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_campus_information_recycler_view_item, parent, false);
        viewItem.setOnClickListener(this);
        return new RecyclerItemViewHolder(viewItem);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final RecyclerItemViewHolder viewHolder = (RecyclerItemViewHolder) holder;
        NewInfo newInfo = mList.get(position);
        if (newInfo != null) {
            viewHolder.mTvTitle.setText(newInfo.getTitle());
            viewHolder.mTvContent.setText(newInfo.getSectitle());
            viewHolder.mTvDate.setText(newInfo.getPubtime());
            viewHolder.itemView.setTag(newInfo);
        }
    }

    @Override
    public int getItemCount() {
        if (mList != null) {
            return mList.size();
        } else
            return 0;
    }

    static class RecyclerItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_title)
        TextView mTvTitle;
        @BindView(R.id.tv_content)
        TextView mTvContent;
        @BindView(R.id.tv_date)
        TextView mTvDate;


        private RecyclerItemViewHolder(View viewItem) {
            super(viewItem);
            ButterKnife.bind(this, viewItem);
        }
    }
}
