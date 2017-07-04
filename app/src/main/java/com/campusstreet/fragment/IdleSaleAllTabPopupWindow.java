package com.campusstreet.fragment;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;

import com.campusstreet.R;
import com.campusstreet.entity.UserInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by develop2 on 2017/7/3.
 */

public class IdleSaleAllTabPopupWindow extends PopupWindow implements GridView.OnItemClickListener{

    @BindView(R.id.btn_up)
    Button mBtnUp;
    @BindView(R.id.gv_all_tab)
    GridView mGvAllTab;



    private View mPopView;
    private Unbinder mUnbinder;
    private UserInfo mUserInfo;
    private IdleSaleAllTabPopupWindow.OnItemClickListener mListener;
    private Context mContext;

    public IdleSaleAllTabPopupWindow(Context context , ArrayList<Map<String,String>> tabList)
    {
        super(context);
        init(context,tabList);
    }

    private void init(Context context , ArrayList<Map<String,String>> tabList)
    {
        mContext = context;
        LayoutInflater inflater = LayoutInflater.from(context);
        //绑定布局
        mPopView = inflater.inflate(R.layout.popupwindow_idlesale_alltab, null);
        mUnbinder = ButterKnife.bind(this, mPopView);
        setPopupWindow();
        setGridView(tabList);
    }

    /**
     * 设置窗口的相关属性
     */
    private void setPopupWindow() {
        this.setContentView(mPopView);// 设置View
        this.setWidth(DrawerLayout.LayoutParams.MATCH_PARENT);// 设置弹出窗口的宽
        this.setHeight(DrawerLayout.LayoutParams.WRAP_CONTENT);// 设置弹出窗口的高
        this.setAnimationStyle(R.style.popwindow_up_anim_style);// 设置动画
        //实例化一个ColorDrawable颜色为透明
//        ColorDrawable dw = new ColorDrawable(0x00ffffff);
//        this.setBackgroundDrawable(dw);
        this.setFocusable(true);
        this.setOutsideTouchable(true);
    }

    private void setGridView(ArrayList<Map<String,String>> tabList)
    {
        String[] from = {"key_array"};
        int[] to = {R.id.tv_tab};
        SimpleAdapter adapter = new SimpleAdapter(mContext,tabList,R.layout.popupwindow_idlesale_tab_gridview_item,from,to);
        mGvAllTab.setAdapter(adapter);
        mGvAllTab.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mListener.setOnItemClick(view ,position);
        this.dismiss();
    }


    public interface OnItemClickListener {
        void setOnItemClick(View v,int position);
    }
    public void setOnItemClickListener(IdleSaleAllTabPopupWindow.OnItemClickListener listener) {
        this.mListener =  listener;
    }
    public void setUserInfo(UserInfo userInfo)
    {
        mUserInfo = userInfo;
    }


    @OnClick({R.id.btn_up})
    public void OnClick(View v)
    {
//        mListener.setOnItemClick(v);
        this.dismiss();
    }

}
