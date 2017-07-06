package com.campusstreet.activity;

/**
 * Created by develop2 on 2017/7/3.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.campusstreet.R;
import com.campusstreet.adapter.SeatsSelectRecyclerViewAdapter;
import com.campusstreet.common.Const;
import com.campusstreet.entity.IdleSaleInfo;
import com.campusstreet.entity.SeatsGroupInfo;
import com.campusstreet.entity.SeatsSingleInfo;
import com.campusstreet.entity.UserInfo;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.campusstreet.common.Const.REQUEST_CODE;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.campusstreet.R;
import com.campusstreet.common.AppConfig;
import com.campusstreet.common.Const;
import com.campusstreet.entity.UserInfo;
import com.campusstreet.utils.PreferencesUtil;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Request;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.campusstreet.common.Const.REQUEST_CODE;

/**
 * Created by develop2 on 2017/6/22.
 */

public class SeatsSelectActivity extends BaseActivity implements SeatsSelectRecyclerViewAdapter.OnRecyclerViewItemClickListener{

    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.iv_toolbar_right)
    ImageView mIvToolbarRight;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tv_venue)
    TextView mTvVenue;
    @BindView(R.id.btn_venue)
    Button mBtnVennue;
    @BindView(R.id.rv_content)
    PullLoadMoreRecyclerView mRvContent;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.progress_bar_title)
    TextView mProgressBarTitle;

    private int mIndex;
    private String mCurVenue;
    private UserInfo mUserInfo;
    SeatsSelectRecyclerViewAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seats_select);
        ButterKnife.bind(this);
        mToolbar.setTitle("");
        mToolbarTitle.setText("座位选择");
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
        mUserInfo = (UserInfo) getIntent().getSerializableExtra(Const.USERINFO_EXTRA);
        initView();
    }

    private void initView()
    {
        List<SeatsGroupInfo> seatsGroupInfoList = new ArrayList<>();
        for (int i=0 ;i<8;i++)
        {
            SeatsGroupInfo groupInfo = new SeatsGroupInfo();
            List<SeatsSingleInfo> singleList = new ArrayList<>();
            for (int jj =1; jj<=4;jj++)
            {
                SeatsSingleInfo singleInfo = new SeatsSingleInfo();
                int seatid = i*4+jj;
                singleInfo.setSeatId(Integer.toString(seatid));
                singleInfo.setSeatState("0");
                singleList.add(singleInfo);
            }
            groupInfo.setSeatsGroup(singleList);
            seatsGroupInfoList.add(groupInfo);
        }
        mRvContent.setGridLayout(2);
        mAdapter = new SeatsSelectRecyclerViewAdapter(this,seatsGroupInfoList);
        mRvContent.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(this);

    }

    @OnClick({R.id.tv_venue ,R.id.btn_venue})
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case  R.id.tv_venue:
                new AlertDialog.Builder(this)
                        .setTitle("请选择场馆")
                        .setSingleChoiceItems(Const.SEATS_VENUE, mIndex, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                mCurVenue = Const.SEATS_VENUE[i];
                                mTvVenue.setText(Const.SEATS_VENUE[i]);
                                dialogInterface.dismiss();
                            }
                        })
                        .create()
                        .show();
                break;
            case R.id.btn_venue:
                new AlertDialog.Builder(this)
                        .setTitle("请选择场馆")
                        .setSingleChoiceItems(Const.SEATS_VENUE, mIndex, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                mCurVenue = Const.SEATS_VENUE[i];
                                mTvVenue.setText(Const.SEATS_VENUE[i]);
                                dialogInterface.dismiss();
                            }
                        })
                        .create()
                        .show();
                break;
            default:
                break;
        }
    }


    @Override
    public void onItemClick(View view, SeatsSingleInfo singleInfo) {
        Log.i("xxxxxxxx", "onItemClick: ");
        Intent intent = new Intent(this,SeatsSelectTimeActivity.class);
        startActivity(intent);
    }
}