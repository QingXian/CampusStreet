package com.campusstreet.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.campusstreet.R;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Orange on 2017/4/17.
 */

public class MessageFragmentRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<Map<String, Object>> mList;


    public MessageFragmentRecyclerViewAdapter(Context context, List<Map<String, Object>> list) {
        mContext = context;
        mList = list;
    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecyclerItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_message_recycler_view_item,null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final RecyclerItemViewHolder viewHolder = (RecyclerItemViewHolder) holder;
        Map<String, Object> list = mList.get(position);
        Picasso.with(mContext).load((Integer) list.get("head")).into(viewHolder.mIvHead);
        viewHolder.mTvTitle.setText(list.get("title").toString());
        viewHolder.mTvContent.setText(list.get("content").toString());
    }

    @Override
    public int getItemCount() {
        return mList.size();
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
