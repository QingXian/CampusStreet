package com.campusstreet.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.campusstreet.R;
import com.campusstreet.entity.RecruitInfo;
import com.campusstreet.entity.StudyWorkInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.campusstreet.utils.DataUtil.getTimeRange;

/**
 * Created by Orange on 2017/5/3.
 */

public class CampusStudyWorkRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {


    private Context mContext;
    private List<StudyWorkInfo> mList;
    private OnRecyclerViewItemClickListener mOnItemClickListener;


    public CampusStudyWorkRecyclerViewAdapter(Context context, List<StudyWorkInfo> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public void onClick(View view) {
        if (mOnItemClickListener != null) {
            // 注意这里使用getTag方法获取数据
            mOnItemClickListener.onItemClick(view, (StudyWorkInfo) view.getTag());
        }
    }

    public static interface OnRecyclerViewItemClickListener {

        void onItemClick(View view, StudyWorkInfo StudyWorkInfo);

    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }


    public void addData(List<StudyWorkInfo> studyWorkInfos) {
        mList.addAll(studyWorkInfos);
        notifyDataSetChanged();
    }

    public void replaceData(List<StudyWorkInfo> studyWorkInfos) {
        //Log.d(TAG, "replaceData: assistanceType <== " + assistanceType);
        mList = studyWorkInfos;
        // 调用以下方法更新后，会依次调用getItemViewType和onBindViewHolder方法
        notifyDataSetChanged();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_campus_recruitment_recycler_view_item, parent, false);
        viewItem.setOnClickListener(this);
        return new RecyclerItemViewHolder(viewItem);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final RecyclerItemViewHolder viewHolder = (RecyclerItemViewHolder) holder;
        StudyWorkInfo studyWorkInfo = mList.get(position);
        viewHolder.mIvHead.setVisibility(View.GONE);
        viewHolder.mTvRequirement.setVisibility(View.GONE);
        if (studyWorkInfo != null) {
            viewHolder.mTvTitle.setText(studyWorkInfo.getTitle());
            viewHolder.mTvCompany.setText(studyWorkInfo.getJobcom());
            String time = getTimeRange(studyWorkInfo.getPublishtime());
            viewHolder.mTvReleaseTime.setText(time);
            viewHolder.mTvPlace.setText(studyWorkInfo.getJobplace());
            viewHolder.mTvWages.setText(studyWorkInfo.getJobmoney());
            if (studyWorkInfo.getJobmoney().equals("0"))
            {
                viewHolder.mTvWages.setText("面议");
            }
            viewHolder.itemView.setTag(studyWorkInfo);
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

        @BindView(R.id.iv_head)
        ImageView mIvHead;
        @BindView(R.id.tv_title)
        TextView mTvTitle;
        @BindView(R.id.tv_company)
        TextView mTvCompany;
        @BindView(R.id.tv_release_time)
        TextView mTvReleaseTime;
        @BindView(R.id.tv_place)
        TextView mTvPlace;
        @BindView(R.id.tv_requirement)
        TextView mTvRequirement;
        @BindView(R.id.tv_wages)
        TextView mTvWages;

        private RecyclerItemViewHolder(View viewItem) {
            super(viewItem);
            ButterKnife.bind(this, viewItem);
        }
    }
}
