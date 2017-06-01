package com.campusstreet.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.campusstreet.R;
import com.campusstreet.entity.IdleSaleInfo;
import com.campusstreet.entity.MessageInfo;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.R.id.list;

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

//        Picasso.with(mContext).load(mList.get()).into(viewHolder.mIvHead);
//        viewHolder.mTvTitle.setText(mList.get().toString());
//        viewHolder.mTvContent.setText(mList.get().toString());
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

        @BindView(R.id.iv_head)
        CircleImageView mIvHead;
        @BindView(R.id.tv_title)
        TextView mTvTitle;
        @BindView(R.id.tv_content)
        TextView mTvContent;
        @BindView(R.id.tv_time)
        TextView mTvTime;

        private RecyclerItemViewHolder(View viewItem) {
            super(viewItem);
            ButterKnife.bind(this, viewItem);
        }
    }
}
