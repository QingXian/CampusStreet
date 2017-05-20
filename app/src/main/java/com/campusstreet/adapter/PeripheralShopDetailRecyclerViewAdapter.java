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
import com.campusstreet.entity.PeripheralShopGoodInfo;
import com.campusstreet.entity.PeripheralShopGoodInfo;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Orange on 2017/5/20.
 */

public class PeripheralShopDetailRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {
    
    private List<PeripheralShopGoodInfo> mList;
    private static OnRecyclerViewItemClickListener mOnItemClickListener;
    private Context mContext;

    public PeripheralShopDetailRecyclerViewAdapter(Context context, List<PeripheralShopGoodInfo> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public void onClick(View view) {
        if (mOnItemClickListener != null) {
            // 注意这里使用getTag方法获取数据
            mOnItemClickListener.onItemClick(view, (PeripheralShopGoodInfo) view.getTag());
        }
    }


    public static interface OnRecyclerViewItemClickListener {

        void onItemClick(View view, PeripheralShopGoodInfo peripheralShopGoodInfo);

    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }


    public void replaceData(List<PeripheralShopGoodInfo> peripheralShopGoodInfos) {
        //Log.d(TAG, "replaceData: assistanceType <== " + assistanceType);
        mList = peripheralShopGoodInfos;
        // 调用以下方法更新后，会依次调用getItemViewType和onBindViewHolder方法
        notifyDataSetChanged();
    }

    public void addData(List<PeripheralShopGoodInfo> PeripheralShopGoodInfos) {
        mList.addAll(PeripheralShopGoodInfos);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_peripheral_shop_detail_recycler_view_item, parent, false);
        viewItem.setOnClickListener(this);
        return new RecyclerItemViewHolder(viewItem);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final RecyclerItemViewHolder viewHolder = (RecyclerItemViewHolder) holder;
        PeripheralShopGoodInfo peripheralShopGoodInfo = mList.get(position);
        if (peripheralShopGoodInfo != null) {
            Picasso.with(mContext)
                    .load(AppConfig.PIC_HOME_BANNER_SERVER_HOST + peripheralShopGoodInfo.getGoodsimage())
                    .fit()
                    .into(viewHolder.mIvImage);
            viewHolder.mTvTitle.setText(peripheralShopGoodInfo.getGoodsname());
            java.text.DecimalFormat   df   =new   java.text.DecimalFormat("#.00");
            viewHolder.mTvPrice.setText(df.format(peripheralShopGoodInfo.getGoodsmoney()));
            viewHolder.itemView.setTag(peripheralShopGoodInfo);
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


        @BindView(R.id.iv_image)
        ImageView mIvImage;
        @BindView(R.id.tv_title)
        TextView mTvTitle;
        @BindView(R.id.tv_price)
        TextView mTvPrice;

        private RecyclerItemViewHolder(View viewItem) {
            super(viewItem);
            ButterKnife.bind(this, viewItem);
        }
    }
}