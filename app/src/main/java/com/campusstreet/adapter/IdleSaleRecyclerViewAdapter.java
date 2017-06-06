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
import com.campusstreet.entity.IdleSaleInfo;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.R.id.content;
import static android.R.id.list;
import static java.lang.System.load;

/**
 * Created by Orange on 2017/4/20.
 */

public class IdleSaleRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {


    private Context mContext;
    private List<IdleSaleInfo> mList;
    private static OnRecyclerViewItemClickListener mOnItemClickListener;

    public IdleSaleRecyclerViewAdapter(Context context, List<IdleSaleInfo> list) {
        mContext = context;
        mList = list;
    }


    public static interface OnRecyclerViewItemClickListener {

        void onItemClick(View view, IdleSaleInfo idleSaleInfo);

    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }


    public void replaceData(List<IdleSaleInfo> idleSaleInfos) {
        //Log.d(TAG, "replaceData: assistanceType <== " + assistanceType);
        mList = idleSaleInfos;
        // 调用以下方法更新后，会依次调用getItemViewType和onBindViewHolder方法
        notifyDataSetChanged();
    }
    public void addData(List<IdleSaleInfo> idleSaleInfos) {
        mList.addAll(idleSaleInfos);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_idle_sale_recycler_view_item, parent,false);
        viewItem.setOnClickListener(this);
        return new RecyclerItemViewHolder(viewItem);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final RecyclerItemViewHolder viewHolder = (RecyclerItemViewHolder) holder;
        IdleSaleInfo idleSaleInfo = mList.get(position);
        if (idleSaleInfo != null){
            Picasso.with(mContext)
                    .load(AppConfig.PIC_EWU_SERVER_HOST+idleSaleInfo.getCoverimage())
                    .placeholder(R.drawable.ic_base_picture)
                    .fit()
                    .error(R.drawable.ic_pic_error)
                    .into(viewHolder.mIvImage);
            viewHolder.mTvTitle.setText(idleSaleInfo.getName());
            viewHolder.mTvPrice.setText(idleSaleInfo.getMoney());
            viewHolder.mTvPlace.setText(idleSaleInfo.getTradeplace());
            viewHolder.itemView.setTag(idleSaleInfo);
        }
    }

    @Override
    public int getItemCount() {
        if (mList != null){
            return mList.size();
        }else
            return 0;
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            // 注意这里使用getTag方法获取数据
            mOnItemClickListener.onItemClick(v, (IdleSaleInfo) v.getTag());
        }
    }


    static class RecyclerItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_image)
        ImageView mIvImage;
        @BindView(R.id.tv_title)
        TextView mTvTitle;
        @BindView(R.id.tv_price)
        TextView mTvPrice;
        @BindView(R.id.tv_place)
        TextView mTvPlace;

        private RecyclerItemViewHolder(View viewItem) {
            super(viewItem);
            ButterKnife.bind(this, viewItem);
        }
    }
}
