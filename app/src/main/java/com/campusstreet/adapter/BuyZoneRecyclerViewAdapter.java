package com.campusstreet.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.campusstreet.R;
import com.campusstreet.common.AppConfig;
import com.campusstreet.entity.BuyZoneInfo;
import com.campusstreet.entity.IdleSaleInfo;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.campusstreet.utils.DataUtil.getTimeRange;

/**
 * Created by Orange on 2017/4/24.
 */

public class BuyZoneRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {


    private List<BuyZoneInfo> mList;
    private static OnRecyclerViewItemClickListener mOnItemClickListener;
    private Context mContext;

    public BuyZoneRecyclerViewAdapter(Context context, List<BuyZoneInfo> list) {
        mContext = context;
        mList = list;
    }


    public static interface OnRecyclerViewItemClickListener {

        void onItemClick(View view, BuyZoneInfo buyZoneInfo);

    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }


    public void addData(List<BuyZoneInfo> buyZoneInfos) {
        mList.addAll(buyZoneInfos);
        notifyDataSetChanged();
    }

    public void replaceData(List<BuyZoneInfo> buyZoneInfos) {
        //Log.d(TAG, "replaceData: assistanceType <== " + assistanceType);
        mList = buyZoneInfos;
        // 调用以下方法更新后，会依次调用getItemViewType和onBindViewHolder方法
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_buy_zone_recycler_view, parent, false);
        viewItem.setOnClickListener(this);
        return new RecyclerItemViewHolder(viewItem);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final RecyclerItemViewHolder viewHolder = (RecyclerItemViewHolder) holder;
        BuyZoneInfo buyZoneInfo = mList.get(position);
        if (buyZoneInfo != null) {
            Picasso.with(mContext)
                    .load(AppConfig.AVATAR_SERVER_HOST + buyZoneInfo.getUserpic())
                    .fit()
                    .error(R.drawable.ic_head_test)
                    .into(viewHolder.mIvHead);
            viewHolder.mTvName.setText(buyZoneInfo.getUsername());
            String time = getTimeRange(buyZoneInfo.getPubtime());
            viewHolder.mTvTime.setText(time);
            viewHolder.mTvContent.setText(buyZoneInfo.getCon());
            viewHolder.mTvExpectedPrice.setText(buyZoneInfo.getMoney());
            viewHolder.mTvTitle.setText(buyZoneInfo.getName());
            viewHolder.itemView.setTag(buyZoneInfo);
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
            mOnItemClickListener.onItemClick(v, (BuyZoneInfo) v.getTag());
        }
    }

    static class RecyclerItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_head)
        CircleImageView mIvHead;
        @BindView(R.id.tv_name)
        TextView mTvName;
        @BindView(R.id.tv_time)
        TextView mTvTime;
        @BindView(R.id.tv_content)
        TextView mTvContent;
        @BindView(R.id.tv_position)
        TextView mTvPosition;
        @BindView(R.id.tv_message_num)
        TextView mTvMessageNum;
        @BindView(R.id.tv_expected_price)
        TextView mTvExpectedPrice;
        @BindView(R.id.tv_title)
        TextView mTvTitle;

        private RecyclerItemViewHolder(View viewItem) {
            super(viewItem);
            ButterKnife.bind(this, viewItem);
        }
    }
}
