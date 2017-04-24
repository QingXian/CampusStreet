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

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Orange on 2017/4/24.
 */

public class PeripheralShopRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<String> mList;
    private static OnRecyclerViewItemClickListener mOnItemClickListener;
    private Context mContext;

    public PeripheralShopRecyclerViewAdapter(Context context, List<String> list) {
        mContext = context;
        mList = list;
    }


    public static interface OnRecyclerViewItemClickListener {

        void onItemClick(View view);

    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }


    public void replaceData(List<String> PeriPheralShopInfos) {
        //Log.d(TAG, "replaceData: assistanceType <== " + assistanceType);
        mList = PeriPheralShopInfos;
        // 调用以下方法更新后，会依次调用getItemViewType和onBindViewHolder方法
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_peripheral_shop_recycler_view_item, parent, false);
        return new RecyclerItemViewHolder(viewItem);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final RecyclerItemViewHolder viewHolder = (RecyclerItemViewHolder) holder;

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
