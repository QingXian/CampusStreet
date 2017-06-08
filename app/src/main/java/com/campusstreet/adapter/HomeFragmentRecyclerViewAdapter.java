package com.campusstreet.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.campusstreet.utils.DataUtil;
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
            if (!homeDynamicInfo.getImg().equals("")) {
                Picasso.with(mContext)
                        .load(AppConfig.PIC_EWU_SERVER_HOST + homeDynamicInfo.getImg())
                        .placeholder(R.drawable.ic_base_picture)
                        .fit()
                        .error(R.drawable.ic_pic_error)
                        .into(viewHolder.mIvImage1);
                viewHolder.mIvImage1.setVisibility(View.VISIBLE);
                viewHolder.mTvContent.setVisibility(View.GONE);
            } else {
                viewHolder.mIvImage1.setVisibility(View.GONE);
                viewHolder.mTvContent.setVisibility(View.VISIBLE);
                viewHolder.mTvContent.setText(homeDynamicInfo.getCon());
                if (homeDynamicInfo.getType() == 6) //校园资讯不显示内容
                {
                    viewHolder.mTvContent.setVisibility(View.GONE);
                }
            }

            viewHolder.mTvName.setText(homeDynamicInfo.getTname());
            viewHolder.mTvTime.setText(DataUtil.formatDateTime(homeDynamicInfo.getTime()));
            viewHolder.mTvTitle.setText(homeDynamicInfo.getTitle());
            viewHolder.itemView.setTag(homeDynamicInfo);
            switch (homeDynamicInfo.getType())
            {
                case 1:
                    viewHolder.mIvHeadIcon.setImageResource(R.drawable.ic_idle_sale);
                    break;
                case 2:
                    viewHolder.mIvHeadIcon.setImageResource(R.drawable.ic_buy_zone);
                    break;
                case 3:
                    viewHolder.mIvHeadIcon.setImageResource(R.drawable.ic_campus_recruitment);
                    break;
                case 4:
                    viewHolder.mIvHeadIcon.setImageResource(R.drawable.ic_bounty_hall);
                    break;
                case 5:
                    viewHolder.mIvHeadIcon.setImageResource(R.drawable.ic_association);
                    break;
                case 6:
                    viewHolder.mIvHeadIcon.setImageResource(R.drawable.ic_campus_information);
                    break;
                case 7:
                    viewHolder.mIvHeadIcon.setImageResource(R.drawable.ic_partner);
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

    @Override
    public void onClick(View view) {
        if (mOnItemClickListener != null) {
            // 注意这里使用getTag方法获取数据
            mOnItemClickListener.onItemClick(view, (HomeDynamicInfo) view.getTag());
        }
    }


    static class RecyclerItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_head_icon)
        ImageView mIvHeadIcon;
        @BindView(R.id.tv_name)
        TextView mTvName;
        @BindView(R.id.tv_time)
        TextView mTvTime;
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
