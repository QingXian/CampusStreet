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
import com.campusstreet.entity.BountyHallInfo;
import com.campusstreet.entity.IdleSaleInfo;
import com.campusstreet.entity.JoinInfo;
import com.campusstreet.entity.RecruitInfo;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.R.id.list;

/**
 * Created by Orange on 2017/4/18.
 */

public class CampusRecruitmentRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {


    private Context mContext;
    private List<RecruitInfo> mList;
    private OnRecyclerViewItemClickListener mOnItemClickListener;


    public CampusRecruitmentRecyclerViewAdapter(Context context, List<RecruitInfo> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public void onClick(View view) {
        if (mOnItemClickListener != null) {
            // 注意这里使用getTag方法获取数据
            mOnItemClickListener.onItemClick(view, (RecruitInfo) view.getTag());
        }
    }

    public static interface OnRecyclerViewItemClickListener {

        void onItemClick(View view, RecruitInfo recruitInfo);

    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }


    public void addData(List<RecruitInfo> recruitInfos) {
        mList.addAll(recruitInfos);
        notifyDataSetChanged();
    }

    public void replaceData(List<RecruitInfo> recruitInfos) {
        //Log.d(TAG, "replaceData: assistanceType <== " + assistanceType);
        mList = recruitInfos;
        // 调用以下方法更新后，会依次调用getItemViewType和onBindViewHolder方法
        notifyDataSetChanged();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_campus_recruitment_recycler_view_item, parent, false);
        viewItem.setOnClickListener(this);
        return new RecyclerItemViewHolder(viewItem);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final RecyclerItemViewHolder viewHolder = (RecyclerItemViewHolder) holder;
        RecruitInfo recruiInfo = mList.get(position);
        if (recruiInfo!=null){
//            Picasso.with(mContext)
//                    .load(AppConfig.AVATAR_SERVER_HOST + recruiInfo.get)
//                    .fit()
//                    .into(viewHolder.mIvHead);
            viewHolder.mTvTitle.setText(recruiInfo.getJobtitle());
            viewHolder.mTvCompany.setText(recruiInfo.getComtypename());
            viewHolder.mTvReleaseTime.setText(recruiInfo.getPublishtime());
            viewHolder.mTvPlace.setText(recruiInfo.getJobplace());
            viewHolder.mTvRequirement.setText(recruiInfo.getJobeduname());
            viewHolder.mTvWages.setText(recruiInfo.getJobmoney());
            viewHolder.itemView.setTag(recruiInfo);
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
        ImageView mIvHead;
        @BindView(R.id.tv_title)
        TextView mTvTitle;
        @BindView(R.id.tv_company)
        TextView mTvCompany;
        @BindView(R.id.tv_release_time)
        TextView mTvReleaseTime;
        @BindView(R.id.tv_place)
        TextView mTvPlace;
        @BindView(R.id.tv_requirement)
        TextView mTvRequirement;
        @BindView(R.id.tv_wages)
        TextView mTvWages;

        private RecyclerItemViewHolder(View viewItem) {
            super(viewItem);
            ButterKnife.bind(this, viewItem);
        }
    }
}
