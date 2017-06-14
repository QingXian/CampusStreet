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
import com.campusstreet.entity.UserJoinTaskInfo;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Orange on 2017/6/7.
 */

public class UserJoinTaskRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {


    private List<UserJoinTaskInfo> mList;
    private static OnRecyclerViewItemClickListener mOnItemClickListener;
    private Context mContext;

    public UserJoinTaskRecyclerViewAdapter(Context context, List<UserJoinTaskInfo> list) {
        mContext = context;
        mList = list;
    }


    public static interface OnRecyclerViewItemClickListener {

        void onItemClick(View view, UserJoinTaskInfo UserJoinTaskInfo);

    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }


    public void addData(List<UserJoinTaskInfo> UserJoinTaskInfos) {
        mList.addAll(UserJoinTaskInfos);
        notifyDataSetChanged();
    }

    public void replaceData(List<UserJoinTaskInfo> UserJoinTaskInfos) {
        //Log.d(TAG, "replaceData: assistanceType <== " + assistanceType);
        mList = UserJoinTaskInfos;
        // 调用以下方法更新后，会依次调用getItemViewType和onBindViewHolder方法
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_user_join_task_recycler_view_item, parent, false);
        viewItem.setOnClickListener(this);
        return new RecyclerItemViewHolder(viewItem);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final RecyclerItemViewHolder viewHolder = (RecyclerItemViewHolder) holder;
        UserJoinTaskInfo userJoinTaskInfo = mList.get(position);
        if (userJoinTaskInfo != null) {
            Picasso.with(mContext)
                    .load(AppConfig.AVATAR_SERVER_HOST + userJoinTaskInfo.getTaskuserpic())
                    .fit()
                    .error(R.drawable.ic_head_test)
                    .into(viewHolder.mIvHead);
            viewHolder.mTvName.setText(userJoinTaskInfo.getTaskuser());
            viewHolder.mTvContent.setText(userJoinTaskInfo.getSummary());
            viewHolder.mTvTitle.setText(userJoinTaskInfo.getTitle());
            viewHolder.itemView.setTag(userJoinTaskInfo);
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
            mOnItemClickListener.onItemClick(v, (UserJoinTaskInfo) v.getTag());
        }
    }

    static class RecyclerItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_head)
        CircleImageView mIvHead;
        @BindView(R.id.tv_name)
        TextView mTvName;
        @BindView(R.id.tv_title)
        TextView mTvTitle;
        @BindView(R.id.tv_content)
        TextView mTvContent;

        private RecyclerItemViewHolder(View viewItem) {
            super(viewItem);
            ButterKnife.bind(this, viewItem);
        }
    }
}
