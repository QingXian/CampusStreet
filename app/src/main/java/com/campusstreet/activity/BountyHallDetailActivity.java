package com.campusstreet.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.campusstreet.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Orange on 2017/4/9.
 */

public class BountyHallDetailActivity extends AppCompatActivity {


    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.iv_toolbar_right)
    ImageView mIvToolbarRight;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.iv_head)
    CircleImageView mIvHead;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.iv_sex)
    ImageView mIvSex;
    @BindView(R.id.tv_real_name_authentication)
    TextView mTvRealNameAuthentication;
    @BindView(R.id.tv_task_num)
    TextView mTvTaskNum;
    @BindView(R.id.textView3)
    TextView mTextView3;
    @BindView(R.id.tv_skill_num)
    TextView mTvSkillNum;
    @BindView(R.id.tv_help_num)
    TextView mTvHelpNum;
    @BindView(R.id.tv_position)
    TextView mTvPosition;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_price)
    TextView mTvPrice;
    @BindView(R.id.tv_people_num)
    TextView mTvPeopleNum;
    @BindView(R.id.tv_day)
    TextView mTvDay;
    @BindView(R.id.tv_time)
    TextView mTvTime;
    @BindView(R.id.tv_remaining_time)
    TextView mTvRemainingTime;
    @BindView(R.id.tv_completion_time)
    TextView mTvCompletionTime;
    @BindView(R.id.tv_contact_people)
    TextView mTvContactPeople;
    @BindView(R.id.tv_contact_place)
    TextView mTvContactPlace;
    @BindView(R.id.tv_describe)
    TextView mTvDescribe;
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.rl_content)
    RecyclerView mRlContent;
    @BindView(R.id.btn_entel)
    Button mBtnEntel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bounty_hall_detail);
        ButterKnife.bind(this);
        mToolbar.setTitle("");
        mToolbarTitle.setText(getString(R.string.act_bounty_hall_detail_toolbar_title));
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mIvToolbarRight.setVisibility(View.VISIBLE);
        mIvToolbarRight.setImageResource(R.drawable.ic_add);

    }


    @OnClick(R.id.btn_entel)
    public void onViewClicked() {
    }
}
