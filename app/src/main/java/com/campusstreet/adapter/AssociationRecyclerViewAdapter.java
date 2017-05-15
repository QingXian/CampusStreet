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
import com.campusstreet.entity.AssociationPostInfo;
import com.campusstreet.entity.JoinInfo;
import com.campusstreet.entity.RecruitInfo;
import com.squareup.picasso.Picasso;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Orange on 2017/5/7.
 */

public class AssociationRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {


    private List<AssociationInfo> mList;
    private static OnRecyclerViewItemClickListener mOnItemClickListener;
    private Context mContext;

    public AssociationRecyclerViewAdapter(Context context, List<AssociationInfo> list) {
        mContext = context;
        mList = list;
    }


    public static interface OnRecyclerViewItemClickListener {

        void onItemClick(View view, AssociationInfo AssociationInfo);

    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }


    public void replaceData(List<AssociationInfo> associationInfos) {
        //Log.d(TAG, "replaceData: assistanceType <== " + assistanceType);
        mList = associationInfos;
        // 调用以下方法更新后，会依次调用getItemViewType和onBindViewHolder方法
        notifyDataSetChanged();
    }
    public void addData(List<AssociationInfo> associationInfos) {
        mList.addAll(associationInfos);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_association_recycler_view_item, parent, false);
        viewItem.setOnClickListener(this);
        return new RecyclerItemViewHolder(viewItem);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final RecyclerItemViewHolder viewHolder = (RecyclerItemViewHolder) holder;
        AssociationInfo associationInfo = mList.get(position);
        if (associationInfo != null) {
            Picasso.with(mContext)
                    .load(AppConfig.PIC_ASSOCIATION_SERVER_HOST+ associationInfo.getClassimg())
                    .fit()
                    .into(viewHolder.mIvHead);
            viewHolder.mTvName.setText(associationInfo.getName());
            viewHolder.mTvAssociationNum.setText(associationInfo.getFollownum());
            viewHolder.itemView.setTag(associationInfo);
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
            mOnItemClickListener.onItemClick(v, (AssociationInfo) v.getTag());
        }
    }

    static class RecyclerItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_head)
        ImageView mIvHead;
        @BindView(R.id.tv_name)
        TextView mTvName;
        @BindView(R.id.tv_association_num)
        TextView mTvAssociationNum;

        private RecyclerItemViewHolder(View viewItem) {
            super(viewItem);
            ButterKnife.bind(this, viewItem);
        }
    }
}