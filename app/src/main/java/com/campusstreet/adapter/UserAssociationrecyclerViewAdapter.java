package com.campusstreet.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.campusstreet.R;
import com.campusstreet.common.AppConfig;
import com.campusstreet.entity.UserAssociationInfo;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Orange on 2017/5/16.
 */

public class UserAssociationrecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {


    private List<UserAssociationInfo> mList;
    private static OnRecyclerViewItemClickListener mOnItemClickListener;
    private Context mContext;

    public UserAssociationrecyclerViewAdapter(Context context, List<UserAssociationInfo> list) {
        mContext = context;
        mList = list;
    }


    public static interface OnRecyclerViewItemClickListener {

        void onItemClick(View view, UserAssociationInfo userAssociationInfo);

    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }


    public void replaceData(List<UserAssociationInfo> userAssociationInfos) {
        //Log.d(TAG, "replaceData: assistanceType <== " + assistanceType);
        mList = userAssociationInfos;
        // 调用以下方法更新后，会依次调用getItemViewType和onBindViewHolder方法
        notifyDataSetChanged();
    }

    public void addData(List<UserAssociationInfo> userAssociationInfos) {
        mList.addAll(userAssociationInfos);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_association_user_recycler_view, parent, false);
        viewItem.setOnClickListener(this);
        return new RecyclerItemViewHolder(viewItem);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final RecyclerItemViewHolder viewHolder = (RecyclerItemViewHolder) holder;
        UserAssociationInfo userAssociationInfo = mList.get(position);
        if (userAssociationInfo != null) {
            Picasso.with(mContext)
                    .load(AppConfig.PIC_ASSOCIATION_SERVER_HOST + userAssociationInfo.getClassimg())
                    .fit()
                    .into(viewHolder.mIvHead);
            viewHolder.mTvName.setText(userAssociationInfo.getAssnname());
            viewHolder.mTvAssociationJoinTime.setText(userAssociationInfo.getJointime());
            viewHolder.itemView.setTag(userAssociationInfo);
        }
    }

    @Override
    public int getItemCount() {
        if (mList != null) {
            return mList.size();
        } else
            return 0;
    }


    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            // 注意这里使用getTag方法获取数据
            mOnItemClickListener.onItemClick(v, (UserAssociationInfo) v.getTag());
        }
    }

    static class RecyclerItemViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.iv_head)
        ImageView mIvHead;
        @BindView(R.id.tv_name)
        TextView mTvName;
        @BindView(R.id.tv_association_join_time)
        TextView mTvAssociationJoinTime;

        private RecyclerItemViewHolder(View viewItem) {
            super(viewItem);
            ButterKnife.bind(this, viewItem);
        }
    }
}
