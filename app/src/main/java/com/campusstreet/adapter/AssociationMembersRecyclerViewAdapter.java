package com.campusstreet.adapter;

/**
 * Created by develop2 on 2017/6/23.
 */
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.campusstreet.R;
import com.campusstreet.entity.AssociationNumInfo;
import com.campusstreet.entity.NewInfo;
import com.campusstreet.entity.RecruitInfo;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.campusstreet.common.AppConfig.AVATAR_SERVER_HOST;
import static com.campusstreet.utils.DataUtil.getTimeRange;

public class AssociationMembersRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    private Context mContext;
    private List<AssociationNumInfo> mList;
    private OnRecyclerViewItemClickListener mOnItemClickListener;


    public AssociationMembersRecyclerViewAdapter(Context context, List<AssociationNumInfo> list) {
        mContext = context;
        mList = list;
    }

    public static interface OnRecyclerViewItemClickListener {

        void onItemClick(View view, NewInfo newInfo);

    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public void addData(List<AssociationNumInfo> newInfos) {
        mList.addAll(newInfos);
        notifyDataSetChanged();
    }

    public void replaceData(List<AssociationNumInfo> newInfos) {
        //Log.d(TAG, "replaceData: assistanceType <== " + assistanceType);
        mList = newInfos;
        // 调用以下方法更新后，会依次调用getItemViewType和onBindViewHolder方法
        notifyDataSetChanged();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_association_mem_recycler_item, parent, false);
        return new RecyclerItemViewHolder(viewItem);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final RecyclerItemViewHolder viewHolder = (RecyclerItemViewHolder) holder;
        AssociationNumInfo newInfo = mList.get(position);
        if (newInfo != null) {
            Picasso.with(mContext)
                    .load(AVATAR_SERVER_HOST + newInfo.getUserpic())
                    .fit()
                    .error(R.drawable.ic_head_test)
                    .into(viewHolder.mIvHead);
            viewHolder.mTvName.setText(newInfo.getUsername());
            viewHolder.mTvDepartment.setText(newInfo.getMajor());
            if (newInfo.getAssnjob() == 1)
            {
                viewHolder.mIvMasterTag.setVisibility(View.VISIBLE);
                viewHolder.mIvDeputyMasterTag.setVisibility(View.GONE);
            }
            else if (newInfo.getAssnjob() == 2)
            {
                viewHolder.mIvMasterTag.setVisibility(View.GONE);
                viewHolder.mIvDeputyMasterTag.setVisibility(View.VISIBLE);
            }
            else
            {
                viewHolder.mIvMasterTag.setVisibility(View.GONE);
                viewHolder.mIvDeputyMasterTag.setVisibility(View.GONE);
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

        @BindView(R.id.iv_master_tag)
        ImageView mIvMasterTag;
        @BindView(R.id.iv_deputy_master_tag)
        ImageView mIvDeputyMasterTag;
        @BindView(R.id.iv_head)
        CircleImageView mIvHead;
        @BindView(R.id.tv_name)
        TextView mTvName;
        @BindView(R.id.tv_department)
        TextView mTvDepartment;


        private RecyclerItemViewHolder(View viewItem) {
            super(viewItem);
            ButterKnife.bind(this, viewItem);
        }
    }
}
