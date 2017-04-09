package com.campusstreet.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.campusstreet.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Orange on 2017/4/8.
 */

public class AddIdleSaleActivity extends AppCompatActivity {

    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.iv_toolbar_right)
    ImageView mIvToolbarRight;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.et_title)
    EditText mEtTitle;
    @BindView(R.id.et_price)
    EditText mEtPrice;
    @BindView(R.id.et_place)
    EditText mEtPlace;
    @BindView(R.id.spinner_mode)
    Spinner mSpinnerMode;
    @BindView(R.id.spinner_type)
    Spinner mSpinnerType;
    @BindView(R.id.et_degree)
    EditText mEtDegree;
    @BindView(R.id.et_phone)
    EditText mEtPhone;
    @BindView(R.id.et_describe)
    EditText mEtDescribe;
    @BindView(R.id.iv_image1)
    ImageView mIvImage1;
    @BindView(R.id.iv_image2)
    ImageView mIvImage2;
    @BindView(R.id.iv_image3)
    ImageView mIvImage3;
    @BindView(R.id.image_content_ll)
    LinearLayout mImageContentLl;
    @BindView(R.id.btn_release)
    Button mBtnRelease;
    @BindView(R.id.iv_add_img)
    ImageView mIvAddImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_idle_sale_add);
        ButterKnife.bind(this);
        mToolbar.setTitle("");
        mToolbarTitle.setText(getString(R.string.act_idle_sale_add_toolbar_title));
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

    }


    @OnClick({R.id.iv_add_img, R.id.btn_release})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_add_img:
                break;
            case R.id.btn_release:
                break;
        }
    }
}
