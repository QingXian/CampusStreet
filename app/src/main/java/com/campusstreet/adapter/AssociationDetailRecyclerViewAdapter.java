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
import com.campusstreet.entity.AssociationInfo;
import com.campusstreet.entity.AssociationPostInfo;
import com.campusstreet.entity.AssociationPostInfo;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.campusstreet.common.AppConfig.AVATAR_SERVER_HOST;
import static com.campusstreet.utils.DataUtil.getTimeRange;

/**
 * Created by Orange on 2017/5/8.
 */

public class AssociationDetailRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {


    private List<AssociationPostInfo> mList;
    private static OnRecyclerViewItemClickListener mOnItemClickListener;
    private Context mContext;

    public AssociationDetailRecyclerViewAdapter(Context context, List<AssociationPostInfo> list) {
        mContext = context;
        mList = list;
    }


    public static interface OnRecyclerViewItemClickListener {

        void onItemClick(View view, AssociationPostInfo associationPostInfo);

    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public void addData(List<AssociationPostInfo> associationPostInfos) {
        mList.addAll(associationPostInfos);
        notifyDataSetChanged();
    }

    public void replaceData(List<AssociationPostInfo> associationPostInfos) {
        //Log.d(TAG, "replaceData: assistanceType <== " + assistanceType);
        mList = associationPostInfos;
        // 调用以下方法更新后，会依次调用getItemViewType和onBindViewHolder方法
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_association_detail_recycler_view_item, parent, false);
        viewItem.setOnClickListener(this);
        return new RecyclerItemViewHolder(viewItem);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final RecyclerItemViewHolder viewHolder = (RecyclerItemViewHolder) holder;
        AssociationPostInfo associationPostInfo = mList.get(position);
        if (associationPostInfo != null) {
            Picasso.with(mContext)
                    .load(AVATAR_SERVER_HOST + associationPostInfo.getUserpic())
                    .fit()
                    .error(R.drawable.ic_head_test)
                    .into(viewHolder.mIvHead);
            viewHolder.mTvTitle.setText(associationPostInfo.getTitle());
            viewHolder.mTvName.setText(associationPostInfo.getUsername());
            String time = getTimeRange(associationPostInfo.getAddtime());
            viewHolder.mTvTime.setText(time);
            viewHolder.mTvReadingTimes.setText(associationPostInfo.getViewnum());
            viewHolder.mTvMessage.setText(associationPostInfo.getReplynum());
            viewHolder.itemView.setTag(associationPostInfo);
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
            mOnItemClickListener.onItemClick(v, (AssociationPostInfo) v.getTag());
        }
    }

    static class RecyclerItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_head)
        CircleImageView mIvHead;
        @BindView(R.id.tv_title)
        TextView mTvTitle;
        @BindView(R.id.tv_name)
        TextView mTvName;
        @BindView(R.id.tv_time)
        TextView mTvTime;
        @BindView(R.id.tv_reading_times)
        TextView mTvReadingTimes;
        @BindView(R.id.tv_message)
        TextView mTvMessage;

        private RecyclerItemViewHolder(View viewItem) {
            super(viewItem);
            ButterKnife.bind(this, viewItem);
        }
    }
}
