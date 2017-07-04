package com.campusstreet.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.campusstreet.R;
import com.campusstreet.common.AppConfig;
import com.campusstreet.entity.IdleSaleInfo;
import com.campusstreet.entity.SeatsGroupInfo;
import com.campusstreet.entity.SeatsSingleInfo;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by develop2 on 2017/7/3.
 */

public class SeatsSelectRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private Context mContext;
    private List<SeatsGroupInfo> mList;
    private static OnRecyclerViewItemClickListener mOnItemClickListener;


    public static interface OnRecyclerViewItemClickListener {

        void onItemClick(View view, IdleSaleInfo idleSaleInfo);
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public void replaceData(List<SeatsGroupInfo> seatsGroupInfos) {
        //Log.d(TAG, "replaceData: assistanceType <== " + assistanceType);
        mList = seatsGroupInfos;
        // 调用以下方法更新后，会依次调用getItemViewType和onBindViewHolder方法
        notifyDataSetChanged();
    }
    public void addData(List<SeatsGroupInfo> idleSaleInfos) {
        mList.addAll(idleSaleInfos);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_seats_group_recycler_view_item, parent,false);
        viewItem.setOnClickListener(this);
        return new RecyclerItemViewHolder(viewItem);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final RecyclerItemViewHolder viewHolder = (RecyclerItemViewHolder) holder;
        SeatsGroupInfo seatsGroupInfo = mList.get(position);
        if (seatsGroupInfo != null) {

            for (int idx = 0;idx < 4;idx++)
            {
                if (seatsGroupInfo.getSeatsGroup().get(idx) != null)
                {
                    SeatsSingleInfo singleInfo = (SeatsSingleInfo)seatsGroupInfo.getSeatsGroup().get(idx);
                    if (idx == 0)
                        viewHolder.mBtnSeat1.setText(singleInfo.getSeatId());
                    else if(idx ==1)
                        viewHolder.mBtnSeat2.setText(singleInfo.getSeatId());
                    else if(idx ==2)
                        viewHolder.mBtnSeat3.setText(singleInfo.getSeatId());
                    else if(idx ==3)
                        viewHolder.mBtnSeat4.setText(singleInfo.getSeatId());

                }
            }

        }
    }

    @Override
    public int getItemCount() {
        if (mList!=null)
            return mList.size();
        else
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

        @BindView(R.id.btn_seat_1)
        Button mBtnSeat1;
        @BindView(R.id.btn_seat_2)
        Button mBtnSeat2;
        @BindView(R.id.btn_seat_3)
        Button mBtnSeat3;
        @BindView(R.id.btn_seat_4)
        Button mBtnSeat4;

        private RecyclerItemViewHolder(View viewItem) {
            super(viewItem);
            ButterKnife.bind(this, viewItem);
        }
    }


}
