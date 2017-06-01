package com.campusstreet.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.campusstreet.R;
import com.campusstreet.entity.IdleSaleInfo;
import com.campusstreet.entity.LiveInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Orange on 2017/4/17.
 */

public class FindFragmentRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {


    private Context mContext;
    private List<LiveInfo> mList;
    private static OnRecyclerViewItemClickListener mOnItemClickListener;


    public FindFragmentRecyclerViewAdapter(Context context, List<LiveInfo> list) {
        mContext = context;
        mList = list;
    }

    public void replaceData(List<LiveInfo> liveInfos) {
        mList = liveInfos;
        // 调用以下方法更新后，会依次调用getItemViewType和onBindViewHolder方法
        notifyDataSetChanged();
    }

    public void addData(List<LiveInfo> liveInfos) {
        mList.addAll(liveInfos);
        notifyDataSetChanged();
    }

    public static interface OnRecyclerViewItemClickListener {

        void onItemClick(View view, LiveInfo liveInfo);

    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_find_recycler_view_item, parent, false);
        viewItem.setOnClickListener(this);
        return new RecyclerItemViewHolder(viewItem);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final RecyclerItemViewHolder viewHolder = (RecyclerItemViewHolder) holder;
        LiveInfo liveInfo = mList.get(position);
        if (liveInfo != null) {
//            Picasso.with(mContext).load((Integer) list.get("head")).into(viewHolder.mIvHead);
//            viewHolder.mTvContent.setText(list.get("content").toString());
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
            mOnItemClickListener.onItemClick(v, (LiveInfo) v.getTag());
        }
    }


    static class RecyclerItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_head)
        CircleImageView mIvHead;
        @BindView(R.id.tv_time)
        TextView mTvTime;
        @BindView(R.id.tv_content)
        TextView mTvContent;
        @BindView(R.id.iv_image1)
        ImageView mIvImage1;
        @BindView(R.id.iv_image2)
        ImageView mIvImage2;
        @BindView(R.id.iv_image3)
        ImageView mIvImage3;
        @BindView(R.id.image_content_ll)
        LinearLayout mImageContentLl;

        private RecyclerItemViewHolder(View viewItem) {
            super(viewItem);
            ButterKnife.bind(this, viewItem);
        }
    }
}
