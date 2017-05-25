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
import com.campusstreet.common.AppConfig;
import com.campusstreet.entity.AssociationInfo;
import com.campusstreet.entity.HomeDynamicInfo;
import com.campusstreet.entity.IdleSaleInfo;
import com.campusstreet.entity.RecruitInfo;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.R.attr.path;
import static android.R.id.home;
import static android.R.id.list;

/**
 * Created by Orange on 2017/4/14.
 */

public class HomeFragmentRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private Context mContext;
    private List<HomeDynamicInfo> mList;
    private static HomeFragmentRecyclerViewAdapter.OnRecyclerViewItemClickListener mOnItemClickListener;


    public HomeFragmentRecyclerViewAdapter(Context context, List<HomeDynamicInfo> list) {
        mContext = context;
        mList = list;
    }

    public void replaceData(List<HomeDynamicInfo> homeDynamicInfos) {
        mList = homeDynamicInfos;
        // 调用以下方法更新后，会依次调用getItemViewType和onBindViewHolder方法
        notifyDataSetChanged();
    }

    public static interface OnRecyclerViewItemClickListener {

        void onItemClick(View view, HomeDynamicInfo homeDynamicInfo);

    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_home_recycler_view_item, parent, false);
        viewItem.setOnClickListener(this);
        return new RecyclerItemViewHolder(viewItem);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final RecyclerItemViewHolder viewHolder = (RecyclerItemViewHolder) holder;
        HomeDynamicInfo homeDynamicInfo = mList.get(position);
        if (homeDynamicInfo != null) {
            Picasso.with(mContext)
                    .load(AppConfig.PIC_EWU_SERVER_HOST + homeDynamicInfo.getImg())
                    .placeholder(R.drawable.ic_base_picture)
                    .fit()
                    .error(R.drawable.ic_pic_error)
                    .into(viewHolder.mIvImage1);
            viewHolder.mTvName.setText(homeDynamicInfo.getTname());
            viewHolder.mTvTitle.setText(homeDynamicInfo.getTitle());
            viewHolder.mTvContent.setText(homeDynamicInfo.getCon());
            viewHolder.itemView.setTag(homeDynamicInfo);
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
    public void onClick(View view) {
        if (mOnItemClickListener != null) {
            // 注意这里使用getTag方法获取数据
            mOnItemClickListener.onItemClick(view, (HomeDynamicInfo) view.getTag());
        }
    }


    static class RecyclerItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_name)
        TextView mTvName;
        @BindView(R.id.tv_title)
        TextView mTvTitle;
        @BindView(R.id.tv_content)
        TextView mTvContent;
        @BindView(R.id.iv_image1)
        ImageView mIvImage1;

        private RecyclerItemViewHolder(View viewItem) {
            super(viewItem);
            ButterKnife.bind(this, viewItem);
        }
    }
}
