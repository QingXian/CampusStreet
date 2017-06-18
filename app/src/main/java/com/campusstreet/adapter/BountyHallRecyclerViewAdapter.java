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
import com.campusstreet.entity.RecruitInfo;
import com.squareup.picasso.Picasso;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.campusstreet.utils.DataUtil.formateDateWithoutSecond;
import static com.campusstreet.utils.DataUtil.getTimeRange;

/**
 * Created by Orange on 2017/4/28.
 */

public class BountyHallRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {


    private List<BountyHallInfo> mList;
    private static OnRecyclerViewItemClickListener mOnItemClickListener;
    private Context mContext;

    public BountyHallRecyclerViewAdapter(Context context, List<BountyHallInfo> list) {
        mContext = context;
        mList = list;
    }


    public static interface OnRecyclerViewItemClickListener {

        void onItemClick(View view, BountyHallInfo BountyHallInfo);

    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }


    public void addData(List<BountyHallInfo> bountyHallInfos) {
        mList.addAll(bountyHallInfos);
        notifyDataSetChanged();
    }

    public void replaceData(List<BountyHallInfo> bountyHallInfos) {
        //Log.d(TAG, "replaceData: assistanceType <== " + assistanceType);
        mList = bountyHallInfos;
        // 调用以下方法更新后，会依次调用getItemViewType和onBindViewHolder方法
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_bounty_hall_recycler_view_item, parent, false);
        viewItem.setOnClickListener(this);
        return new RecyclerItemViewHolder(viewItem);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final RecyclerItemViewHolder viewHolder = (RecyclerItemViewHolder) holder;
        BountyHallInfo bountyHallInfo = mList.get(position);
        if (bountyHallInfo != null) {
            Picasso.with(mContext)
                    .load(AppConfig.AVATAR_SERVER_HOST + bountyHallInfo.getUserpic())
                    .fit()
                    .error(R.drawable.ic_head_test)
                    .into(viewHolder.mIvHead);

            viewHolder.mTvName.setText(bountyHallInfo.getUsername());
            String time = getTimeRange(bountyHallInfo.getPubtime());
            viewHolder.mTvTime.setText(time);
            String con = bountyHallInfo.getCon();
            viewHolder.mTvContent.setText(con);
            viewHolder.mTvTitle.setText(bountyHallInfo.getTitle());
            viewHolder.mTvNeedNum.setText(String.valueOf(bountyHallInfo.getPerson()));
            viewHolder.mTvSignUpNum.setText(String.valueOf(bountyHallInfo.getSperson()));
            viewHolder.mTvSelectedNum.setText(String.valueOf(bountyHallInfo.getTperson()));
            String newDate = formateDateWithoutSecond(bountyHallInfo.getEndtime());
            viewHolder.mTvSurplusTime.setText(newDate+ mContext.getString(R.string.act_bounty_halll_item_end_hint));
            viewHolder.itemView.setTag(bountyHallInfo);
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
            mOnItemClickListener.onItemClick(v, (BountyHallInfo) v.getTag());
        }
    }

    static class RecyclerItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_head)
        CircleImageView mIvHead;
        @BindView(R.id.tv_name)
        TextView mTvName;
        @BindView(R.id.iv_privilege)
        ImageView mIvPrivilege;
        @BindView(R.id.tv_time)
        TextView mTvTime;
        @BindView(R.id.tv_title)
        TextView mTvTitle;
        @BindView(R.id.tv_content)
        TextView mTvContent;
        @BindView(R.id.tv_need_num)
        TextView mTvNeedNum;
        @BindView(R.id.tv_sign_up_num)
        TextView mTvSignUpNum;
        @BindView(R.id.tv_selected)
        TextView mTvSelected;
        @BindView(R.id.ll_operation_content)
        LinearLayout mLlOperationContent;
        @BindView(R.id.tv_surplus_time)
        TextView mTvSurplusTime;
        @BindView(R.id.tv_selected_num)
        TextView mTvSelectedNum;

        private RecyclerItemViewHolder(View viewItem) {
            super(viewItem);
            ButterKnife.bind(this, viewItem);
        }
    }
}
