package com.campusstreet.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.campusstreet.R;
import com.campusstreet.common.AppConfig;
import com.campusstreet.entity.AssociationInfo;
import com.campusstreet.entity.AssociationPostMessageInfo;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Orange on 2017/5/8.
 */

public class PostDetailRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private Context mContext;
    private List<AssociationPostMessageInfo> mList;

    public PostDetailRecyclerViewAdapter(Context context, List<AssociationPostMessageInfo> list) {
        mContext = context;
        mList = list;
    }


    public void replaceData(List<AssociationPostMessageInfo> postDetailInfos) {
        mList = postDetailInfos;
        // 调用以下方法更新后，会依次调用getItemViewType和onBindViewHolder方法
        notifyDataSetChanged();
    }
    public void addData(List<AssociationPostMessageInfo> postDetailInfos) {
        mList.addAll(postDetailInfos);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_recycler_view_item, parent, false);
        return new RecyclerItemViewHolder(viewItem);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final RecyclerItemViewHolder viewHolder = (RecyclerItemViewHolder) holder;
        AssociationPostMessageInfo associationPostMessageInfo = mList.get(position);
        if (associationPostMessageInfo != null) {
            Picasso.with(mContext)
                    .load(AppConfig.AVATAR_SERVER_HOST + associationPostMessageInfo.getUserpic())
                    .fit()
                    .into(viewHolder.mIvHead);
            viewHolder.mTvName.setText(associationPostMessageInfo.getUsername());
            viewHolder.mTvContent.setText(associationPostMessageInfo.getCon());
            viewHolder.mTvTime.setText(associationPostMessageInfo.getPubtime());
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
        @BindView(R.id.tv_name)
        TextView mTvName;
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
