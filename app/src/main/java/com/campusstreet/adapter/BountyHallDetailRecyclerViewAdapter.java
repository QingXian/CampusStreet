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
import com.campusstreet.entity.JoinInfo;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.R.attr.start;
import static android.R.attr.type;

/**
 * Created by Orange on 2017/5/1.
 */

public class BountyHallDetailRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {


    private List<JoinInfo> mList;
    private static OnRecyclerViewItemClickListener mOnItemClickListener;
    private Context mContext;
    private int mType;
    private int mIsStart = 0;

    public BountyHallDetailRecyclerViewAdapter(Context context, List<JoinInfo> list,int type) {
        mContext = context;
        mList = list;
        mType = type;
    }


    public static interface OnRecyclerViewItemClickListener {

        void onItemClick(View view, JoinInfo JoinInfo,int type,int isStrat);
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }


    public void replaceData(List<JoinInfo> JoinInfos) {
        mList = JoinInfos;
        // 调用以下方法更新后，会依次调用getItemViewType和onBindViewHolder方法
        notifyDataSetChanged();
    }
    public void replaceType(int  type) {
      mType = type;
    }

    public void startTask(int  isStart) {
       mIsStart = isStart;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_bounty_hall_detail_recycler_view_item, parent, false);
        viewItem.setOnClickListener(this);
        return new RecyclerItemViewHolder(viewItem);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final RecyclerItemViewHolder viewHolder = (RecyclerItemViewHolder) holder;
        JoinInfo JoinInfo = mList.get(position);
        if (JoinInfo != null) {
            Picasso.with(mContext)
                    .load(AppConfig.AVATAR_SERVER_HOST + JoinInfo.getUserpic())
                    .fit()
                    .into(viewHolder.mIvHead);
            viewHolder.mTvName.setText(JoinInfo.getUsername());
            viewHolder.itemView.setTag(JoinInfo);
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
            mOnItemClickListener.onItemClick(v, (JoinInfo) v.getTag(),mType,mIsStart);
        }
    }

    static class RecyclerItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_head)
        CircleImageView mIvHead;
        @BindView(R.id.tv_name)
        TextView mTvName;

        private RecyclerItemViewHolder(View viewItem) {
            super(viewItem);
            ButterKnife.bind(this, viewItem);
        }
    }
}
