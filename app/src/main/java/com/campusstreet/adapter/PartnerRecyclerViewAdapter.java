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
import com.campusstreet.entity.NewInfo;
import com.campusstreet.entity.PartnerInfo;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Orange on 2017/4/24.
 */

public class PartnerRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements  View.OnClickListener {


    private List<PartnerInfo> mList;
    private static OnRecyclerViewItemClickListener mOnItemClickListener;
    private Context mContext;

    public PartnerRecyclerViewAdapter(Context context, List<PartnerInfo> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public void onClick(View view) {
        if (mOnItemClickListener != null) {
            // 注意这里使用getTag方法获取数据
            mOnItemClickListener.onItemClick(view, (PartnerInfo) view.getTag());
        }
    }


    public static interface OnRecyclerViewItemClickListener {

        void onItemClick(View view,PartnerInfo partnerInfo);

    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public void addData(List<PartnerInfo> partnerInfos) {
        mList.addAll(partnerInfos);
        notifyDataSetChanged();
    }


    public void replaceData(List<PartnerInfo> partnerInfos) {
        //Log.d(TAG, "replaceData: assistanceType <== " + assistanceType);
        mList = partnerInfos;
        // 调用以下方法更新后，会依次调用getItemViewType和onBindViewHolder方法
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_partner_recycler_view_item, parent, false);
        viewItem.setOnClickListener(this);
        return new RecyclerItemViewHolder(viewItem);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final RecyclerItemViewHolder viewHolder = (RecyclerItemViewHolder) holder;
        PartnerInfo partnerInfo = mList.get(position);
        if (partnerInfo != null){
            Picasso.with(mContext)
                    .load(AppConfig.PIC_HOME_BANNER_SERVER_HOST + partnerInfo.getImg())
                    .error(R.drawable.ic_head_test)
                    .fit()
                    .error(R.drawable.ic_head_test)
                    .into(viewHolder.mIvHead);
            viewHolder.mTvTitle.setText(partnerInfo.getName());
            viewHolder.mTvContent.setText(partnerInfo.getSketch());
            viewHolder.mTvName.setText(partnerInfo.getOrganizer());
            viewHolder.itemView.setTag(partnerInfo);
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
        @BindView(R.id.tv_name)
        TextView mTvName;

        private RecyclerItemViewHolder(View viewItem) {
            super(viewItem);
            ButterKnife.bind(this, viewItem);
        }
    }
}
