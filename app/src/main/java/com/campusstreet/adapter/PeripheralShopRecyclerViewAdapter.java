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
import com.campusstreet.entity.PartnerInfo;
import com.campusstreet.entity.PeripheralShopInfo;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Orange on 2017/4/24.
 */

public class PeripheralShopRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private List<PeripheralShopInfo> mList;
    private static OnRecyclerViewItemClickListener mOnItemClickListener;
    private Context mContext;

    public PeripheralShopRecyclerViewAdapter(Context context, List<PeripheralShopInfo> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public void onClick(View view) {
        if (mOnItemClickListener != null) {
            // 注意这里使用getTag方法获取数据
            mOnItemClickListener.onItemClick(view, (PeripheralShopInfo) view.getTag());
        }
    }


    public static interface OnRecyclerViewItemClickListener {

        void onItemClick(View view, PeripheralShopInfo peripheralShopInfo);

    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }


    public void replaceData(List<PeripheralShopInfo> PeriPheralShopInfos) {
        //Log.d(TAG, "replaceData: assistanceType <== " + assistanceType);
        mList = PeriPheralShopInfos;
        // 调用以下方法更新后，会依次调用getItemViewType和onBindViewHolder方法
        notifyDataSetChanged();
    }

    public void addData(List<PeripheralShopInfo> peripheralShopInfos) {
        mList.addAll(peripheralShopInfos);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_peripheral_shop_recycler_view_item, parent, false);
        viewItem.setOnClickListener(this);
        return new RecyclerItemViewHolder(viewItem);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final RecyclerItemViewHolder viewHolder = (RecyclerItemViewHolder) holder;
        PeripheralShopInfo peripheralShopInfo = mList.get(position);
        if (peripheralShopInfo != null) {
            Picasso.with(mContext)
                    .load(AppConfig.PIC_HOME_BANNER_SERVER_HOST + peripheralShopInfo.getImg())
                    .fit()
                    .into(viewHolder.mIvPhoto);
            viewHolder.mTvName.setText(peripheralShopInfo.getName());
            viewHolder.mTvGrade.setText(String.valueOf(peripheralShopInfo.getPoint()));
            viewHolder.mTvBusinessHours.setText(peripheralShopInfo.getBhour());
            viewHolder.mTvCategory.setText(peripheralShopInfo.getTypename());
            if (Math.floor(peripheralShopInfo.getPoint()) == 0) {
                viewHolder.mIvStar1.setImageResource(R.drawable.ic_star);
                viewHolder.mIvStar2.setImageResource(R.drawable.ic_star);
                viewHolder.mIvStar3.setImageResource(R.drawable.ic_star);
                viewHolder.mIvStar4.setImageResource(R.drawable.ic_star);
                viewHolder.mIvStar5.setImageResource(R.drawable.ic_star);
            } else if (Math.floor(peripheralShopInfo.getPoint()) == 1) {
                viewHolder.mIvStar1.setImageResource(R.drawable.ic_star_fill);
                viewHolder.mIvStar2.setImageResource(R.drawable.ic_star);
                viewHolder.mIvStar3.setImageResource(R.drawable.ic_star);
                viewHolder.mIvStar4.setImageResource(R.drawable.ic_star);
                viewHolder.mIvStar5.setImageResource(R.drawable.ic_star);
            } else if (Math.floor(peripheralShopInfo.getPoint()) == 2) {
                viewHolder.mIvStar1.setImageResource(R.drawable.ic_star_fill);
                viewHolder.mIvStar2.setImageResource(R.drawable.ic_star_fill);
                viewHolder.mIvStar3.setImageResource(R.drawable.ic_star);
                viewHolder.mIvStar4.setImageResource(R.drawable.ic_star);
                viewHolder.mIvStar5.setImageResource(R.drawable.ic_star);
            } else if (Math.floor(peripheralShopInfo.getPoint()) == 3) {
                viewHolder.mIvStar1.setImageResource(R.drawable.ic_star_fill);
                viewHolder.mIvStar2.setImageResource(R.drawable.ic_star_fill);
                viewHolder.mIvStar3.setImageResource(R.drawable.ic_star_fill);
                viewHolder.mIvStar4.setImageResource(R.drawable.ic_star);
                viewHolder.mIvStar5.setImageResource(R.drawable.ic_star);
            } else if (Math.floor(peripheralShopInfo.getPoint()) == 4) {
                viewHolder.mIvStar1.setImageResource(R.drawable.ic_star_fill);
                viewHolder.mIvStar2.setImageResource(R.drawable.ic_star_fill);
                viewHolder.mIvStar3.setImageResource(R.drawable.ic_star_fill);
                viewHolder.mIvStar4.setImageResource(R.drawable.ic_star_fill);
                viewHolder.mIvStar5.setImageResource(R.drawable.ic_star);
            } else if (Math.floor(peripheralShopInfo.getPoint()) == 5) {
                viewHolder.mIvStar1.setImageResource(R.drawable.ic_star_fill);
                viewHolder.mIvStar2.setImageResource(R.drawable.ic_star_fill);
                viewHolder.mIvStar3.setImageResource(R.drawable.ic_star_fill);
                viewHolder.mIvStar4.setImageResource(R.drawable.ic_star_fill);
                viewHolder.mIvStar5.setImageResource(R.drawable.ic_star_fill);
            }
            viewHolder.itemView.setTag(peripheralShopInfo);
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


        @BindView(R.id.iv_photo)
        ImageView mIvPhoto;
        @BindView(R.id.tv_name)
        TextView mTvName;
        @BindView(R.id.iv_star1)
        ImageView mIvStar1;
        @BindView(R.id.iv_star2)
        ImageView mIvStar2;
        @BindView(R.id.iv_star3)
        ImageView mIvStar3;
        @BindView(R.id.iv_star4)
        ImageView mIvStar4;
        @BindView(R.id.iv_star5)
        ImageView mIvStar5;
        @BindView(R.id.tv_grade)
        TextView mTvGrade;
        @BindView(R.id.ll_grade_content)
        LinearLayout mLlGradeContent;
        @BindView(R.id.tv_business_hours)
        TextView mTvBusinessHours;
        @BindView(R.id.tv_category)
        TextView mTvCategory;
        @BindView(R.id.tv_introduce)
        TextView mTvIntroduce;

        private RecyclerItemViewHolder(View viewItem) {
            super(viewItem);
            ButterKnife.bind(this, viewItem);
        }
    }
}
