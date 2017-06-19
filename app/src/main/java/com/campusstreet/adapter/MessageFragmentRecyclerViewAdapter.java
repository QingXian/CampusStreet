package com.campusstreet.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.campusstreet.R;
import com.campusstreet.entity.MessageInfo;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Orange on 2017/4/17.
 */

public class MessageFragmentRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private Context mContext;
    private List<MessageInfo> mList;
    private static OnRecyclerViewItemClickListener mOnItemClickListener;


    public MessageFragmentRecyclerViewAdapter(Context context, List<MessageInfo> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            // 注意这里使用getTag方法获取数据
            mOnItemClickListener.onItemClick(v, (MessageInfo) v.getTag());
        }
    }

    public static interface OnRecyclerViewItemClickListener {

        void onItemClick(View view, MessageInfo messageInfo);

    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }


    public void replaceData(List<MessageInfo> messageInfos) {
        //Log.d(TAG, "replaceData: assistanceType <== " + assistanceType);
        mList = messageInfos;
        // 调用以下方法更新后，会依次调用getItemViewType和onBindViewHolder方法
        notifyDataSetChanged();
    }

    public void addData(List<MessageInfo> messageInfos) {
        mList.addAll(messageInfos);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_message_recycler_view_item, parent, false);
        viewItem.setOnClickListener(this);
        return new RecyclerItemViewHolder(viewItem);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final RecyclerItemViewHolder viewHolder = (RecyclerItemViewHolder) holder;
        MessageInfo messageInfo = mList.get(position);
        if (messageInfo != null) {
            viewHolder.mTvTitle.setText(messageInfo.getTitle());
            viewHolder.mTvContent.setText(messageInfo.getCon());
            viewHolder.mTvTime.setText(messageInfo.getTime());
            viewHolder.itemView.setTag(messageInfo);
            if (messageInfo.getIsread()) {
                viewHolder.mTvIsread.setVisibility(View.GONE);
            } else {
                viewHolder.mTvIsread.setVisibility(View.VISIBLE);
            }
            switch (messageInfo.getTypecode()) {
                case "ewu_reply":
                    viewHolder.mIvHead.setImageResource(R.drawable.ic_idle_sale);
                    break;
                case "wish_reply":
                    viewHolder.mIvHead.setImageResource(R.drawable.ic_buy_zone);
                    break;
                case "task_join":
                    viewHolder.mIvHead.setImageResource(R.drawable.ic_bounty_hall);
                    break;
                case "task_accept":
                    viewHolder.mIvHead.setImageResource(R.drawable.ic_bounty_hall);
                    break;
                case "join_assn":
                    viewHolder.mIvHead.setImageResource(R.drawable.ic_association);
                    break;
                case "assn_accept":
                    viewHolder.mIvHead.setImageResource(R.drawable.ic_association);
                    break;
                case "assn_deny":
                    viewHolder.mIvHead.setImageResource(R.drawable.ic_association);
                    break;
                case "task_execute":
                    viewHolder.mIvHead.setImageResource(R.drawable.ic_bounty_hall);
                    break;
                case "task_done":
                    viewHolder.mIvHead.setImageResource(R.drawable.ic_bounty_hall);
                    break;
                case "task_success":
                    viewHolder.mIvHead.setImageResource(R.drawable.ic_bounty_hall);
                    break;
                case "recruit_push":
                    viewHolder.mIvHead.setImageResource(R.drawable.ic_campus_recruitment);
                    break;
            }
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
        @BindView(R.id.tv_time)
        TextView mTvTime;
        @BindView(R.id.tv_isread)
        TextView mTvIsread;
        @BindView(R.id.iv_head)
        CircleImageView mIvHead;

        private RecyclerItemViewHolder(View viewItem) {
            super(viewItem);
            ButterKnife.bind(this, viewItem);
        }
    }
}
