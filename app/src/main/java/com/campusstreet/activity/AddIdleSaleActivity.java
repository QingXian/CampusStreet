package com.campusstreet.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.campusstreet.R;
import com.campusstreet.common.Const;
import com.campusstreet.contract.IIdleSaleContract;
import com.campusstreet.entity.IdleSaleInfo;
import com.campusstreet.entity.LeaveMessageInfo;
import com.campusstreet.model.IdleSaleImpl;
import com.campusstreet.presenter.IdleSalePresenter;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.os.Build.ID;


/**
 * Created by Orange on 2017/4/8.
 */

public class AddIdleSaleActivity extends AppCompatActivity implements IIdleSaleContract.View {

    protected final String TAG = getClass().getSimpleName();
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
    @BindView(R.id.et_qq)
    EditText mEtQq;
    @BindView(R.id.tv_goods_type)
    TextView mTvGoodsType;
    @BindView(R.id.tv_mode)
    TextView mTvMode;
    @BindView(R.id.tv_trade_type)
    TextView mTvTradeType;
    @BindView(R.id.btn_goods_type)
    Button mBtnGoodsType;
    @BindView(R.id.btn_mode)
    Button mBtnMode;
    @BindView(R.id.btn_trade_type)
    Button mBtnTradeType;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.progress_bar_title)
    TextView mProgressBarTitle;
    @BindView(R.id.progress_bar_container)
    LinearLayout mProgressBarContainer;
    private IIdleSaleContract.Presenter mPresenter;
    public static final int REQUEST_UPDATE_AVATAR = 1;
    private int mIndex;

    private String[] mTitles;
    private String mTabType;

    private File mFile = null;

    private HashMap<String, String> mPushRange;
    private String mId;
    private String mName;
    private String mMoney;
    private String mTradeplace;
    private String mTradetype;
    private String mSelltype;
    private String mMobile;
    private String mQQ;
    private String mBewrite;
    private String mContent;

    private Intent mIntent;

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
        initView();
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        new IdleSalePresenter(IdleSaleImpl.getInstance(getApplicationContext()), this);
    }


    private void initView() {
    }


    @OnClick({R.id.iv_add_img, R.id.btn_release, R.id.btn_goods_type, R.id.btn_mode, R.id.btn_trade_type,R.id.tv_goods_type, R.id.tv_mode, R.id.tv_trade_type})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_add_img:
                mIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                mIntent.putExtra(ID, mId);
                startActivityForResult(mIntent, REQUEST_UPDATE_AVATAR);
                break;
            case R.id.btn_release:
                AddIdleSaleGoods();
                break;
            case R.id.btn_goods_type:
                new AlertDialog.Builder(this)
                        .setTitle("请选择商品类型")
//                        .setSingleChoiceItems(mTitles, mIndex, this)
                        .create()
                        .show();
                break;
            case R.id.btn_mode:
                new AlertDialog.Builder(this)
                        .setTitle("请选择讨价类型")
                        .setSingleChoiceItems(Const.SELLTYPE, mIndex, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                mSelltype = Const.SELLTYPE[i];
                                mTvMode.setText(Const.SELLTYPE[i]);
                                dialogInterface.dismiss();
                            }
                        })
                        .create()
                        .show();
                break;
            case R.id.btn_trade_type:
                new AlertDialog.Builder(this)
                        .setTitle("请选择交易方式")
                        .setSingleChoiceItems(Const.TRADETYPE, mIndex, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                mTradetype = Const.TRADETYPE[i];
                                mTvTradeType.setText(Const.TRADETYPE[i]);
                                dialogInterface.dismiss();
                            }
                        })
                        .create()
                        .show();
                break;
            case R.id.tv_goods_type:
                new AlertDialog.Builder(this)
                        .setTitle("请选择商品类型")
//                        .setSingleChoiceItems(mTitles, mIndex, this)
                        .create()
                        .show();
                break;
            case R.id.tv_mode:
                new AlertDialog.Builder(this)
                        .setTitle("请选择讨价类型")
                        .setSingleChoiceItems(Const.SELLTYPE, mIndex, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                mSelltype = Const.SELLTYPE[i];
                                mTvMode.setText(Const.SELLTYPE[i]);
                                dialogInterface.dismiss();
                            }
                        })
                        .create()
                        .show();
                break;
            case R.id.tv_trade_type:
                new AlertDialog.Builder(this)
                        .setTitle("请选择交易方式")
                        .setSingleChoiceItems(Const.TRADETYPE, mIndex, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                mTradetype = Const.TRADETYPE[i];
                                mTvTradeType.setText(Const.TRADETYPE[i]);
                                dialogInterface.dismiss();
                            }
                        })
                        .create()
                        .show();
                break;
        }
    }

    /**
     * 添加闲置商品信息
     */
    private void AddIdleSaleGoods() {
        if (!mTvTradeType.getText().toString().trim().equals(mTradetype)) {
            showMessage("请选择交易方式");
            return;
        }
        if (!mTvMode.getText().toString().trim().equals(mSelltype)) {
            showMessage("请选择讨价类型");
            return;
        }
//        if (!mTvTradeType.getText().toString().trim().equals(mTradetype)) {
//            showMessage("请选择求助类别");
//            return;
//        }
        if (TextUtils.isEmpty(mEtTitle.getText().toString().trim())) {
            showMessage("请填写标题");
            return;
        }
        try {
            if (Integer.valueOf(mEtPrice.getText().toString().trim()) < 0) {
                showMessage("请填写大于0的价格");
                return;
            }

        } catch (NumberFormatException e) {
            showMessage("价格格式不正确");
            return;
        }
        if (TextUtils.isEmpty(mEtPlace.getText().toString().trim())) {
            showMessage("请填写交易地点");
            return;
        }
        if (TextUtils.isEmpty(mEtPhone.getText().toString().trim())) {
            showMessage("请填写联系方式");
            return;
        }
        if (TextUtils.isEmpty(mEtQq.getText().toString().trim())) {
            showMessage("请填写QQ");
            return;
        }
        if (TextUtils.isEmpty(mEtDegree.getText().toString().trim())) {
            showMessage("请填写新旧程度");
            return;
        }
        if (TextUtils.isEmpty(mEtDescribe.getText().toString().trim())) {
            showMessage("请填写详细介绍");
            return;
        }
        if (mFile == null) {
            showMessage("请选择一张图片");
            return;
        }
        IdleSaleInfo idlSaleInfo = new IdleSaleInfo();
        idlSaleInfo.setName(mEtTitle.getText().toString());
        idlSaleInfo.setMoney(mEtPrice.getText().toString());
        idlSaleInfo.setBewrite(mEtDegree.getText().toString());
        idlSaleInfo.setContent(mEtDescribe.getText().toString());
        idlSaleInfo.setGoodstype("电脑");
        idlSaleInfo.setTradetype(mTradetype);
        idlSaleInfo.setSelltype(mSelltype);
        idlSaleInfo.setTradeplace(mEtPlace.getText().toString());
        idlSaleInfo.setMobile(mEtPhone.getText().toString());
        idlSaleInfo.setQq(mEtQq.getText().toString());
        idlSaleInfo.setUid("Mw==");
        idlSaleInfo.setImage(mFile);
        mPresenter.pushGoods(idlSaleInfo);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_UPDATE_AVATAR && resultCode == RESULT_OK) {
            addImage(data);
        }
    }

    private void addImage(Intent data) {
        String path = null;
        Uri uri = data.getData();
        Cursor cursor = null;
        try {
            cursor = this.getContentResolver().query(uri, null, null, null, null);
            int column_index = 0;
            if (cursor != null) {
                column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();
                path = cursor.getString(column_index);
                mFile = new File(path);
                Picasso.with(this)
                        .load(mFile)
                        .fit()
                        .into(mIvImage1);
            }
        } catch (Exception e) {
            Log.e(TAG, "fetchAvatar: 选择图片异常", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    @Override
    public void setPresenter(IIdleSaleContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void setIdleSale(List<IdleSaleInfo> idleSaleInfoList) {
        //忽略
    }

    @Override
    public void setIdleSaleCategories() {
        //忽略
    }

    @Override
    public void setIdleSaleMessageList(List<LeaveMessageInfo> idleSaleMessageList) {//忽略
    }


    @Override
    public void showErrorMsg(String errorMsg) {
        Toast.makeText(this, errorMsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showSuccessfullyPush(String succcessMsg) {
        Toast.makeText(this, succcessMsg, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, IdleSaleActivity.class);
        startActivity(intent);
    }

    @Override
    public void showSuccessfullyleaveMessage(String succcessMsg) {
        //忽略
    }

    @Override
    public void setLoadingIndicator(boolean active) {
        if (mProgressBarContainer != null) {
            if (active) {
                //设置滚动条可见
                mProgressBarContainer.setVisibility(View.VISIBLE);
                mProgressBarTitle.setText(R.string.pushing_progress_bar_title);
            } else {
                if (mProgressBarContainer.getVisibility() == View.VISIBLE) {
                    mProgressBarContainer.setVisibility(View.GONE);
                }
            }
        }
    }

    protected void showMessage(String msg) {
        if (this != null && !this.isFinishing()) {
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        }
    }
}
