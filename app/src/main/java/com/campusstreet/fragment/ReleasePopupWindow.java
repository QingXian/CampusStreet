package com.campusstreet.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.campusstreet.R;
import com.campusstreet.activity.ReleaseLiveActivity;
import com.campusstreet.common.Const;
import com.campusstreet.entity.UserInfo;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by develop2 on 2017/6/6.
 */

public class ReleasePopupWindow extends PopupWindow implements View.OnClickListener{

    @BindView(R.id.btn_release_idl)
    Button mBtnReleaseIdl;
    @BindView(R.id.btn_release_buy)
    Button mBtnReleaseBuy;
    @BindView(R.id.btn_release_bounty)
    Button mBtnReleaseBounty;
    @BindView(R.id.btn_release_live)
    Button mBtnReleaseLive;


    private View mPopView;
    private Unbinder mUnbinder;
    private UserInfo mUserInfo;
    private OnItemClickListener mListener;

    public ReleasePopupWindow(Context context)
    {
        super(context);
        init(context);
    }

    private void init(Context context)
    {
        // TODO Auto-generated method stub
        LayoutInflater inflater = LayoutInflater.from(context);
        //绑定布局
        mPopView = inflater.inflate(R.layout.popupwindow_release, null);
        mUnbinder = ButterKnife.bind(this, mPopView);
        setPopupWindow();
    }

    /**
     * 设置窗口的相关属性
     */
    private void setPopupWindow() {
        this.setContentView(mPopView);// 设置View
        this.setWidth(DrawerLayout.LayoutParams.MATCH_PARENT);// 设置弹出窗口的宽
        this.setHeight(126);// 设置弹出窗口的高
        this.setAnimationStyle(R.style.mypopwindow_anim_style);// 设置动画
        //实例化一个ColorDrawable颜色为透明
        ColorDrawable dw = new ColorDrawable(0x00000000);
        this.setBackgroundDrawable(dw);
        // TODO: 2016/5/17 设置可以获取焦点
        this.setFocusable(true);
        // TODO: 2016/5/17 设置可以触摸弹出框以外的区域
        this.setOutsideTouchable(true);
    }



    public interface OnItemClickListener {
        void setOnItemClick(View v);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener =  listener;
    }
    public void setUserInfo(UserInfo userInfo)
    {
        mUserInfo = userInfo;
    }


    @OnClick({R.id.btn_release_idl, R.id.btn_release_buy, R.id.btn_release_bounty, R.id.btn_release_live})
    public void OnClick(View v)
    {
        mListener.setOnItemClick(v);
        this.dismiss();
    }

    @Override
    public void onClick(View v) {
    }
}
