package com.campusstreet.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
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
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.campusstreet.R;
import com.campusstreet.common.Const;
import com.campusstreet.contract.IIdleSaleContract;
import com.campusstreet.entity.CategoriesInfo;
import com.campusstreet.entity.IdleSaleInfo;
import com.campusstreet.entity.LeaveMessageInfo;
import com.campusstreet.entity.UserInfo;
import com.campusstreet.model.IdleSaleImpl;
import com.campusstreet.presenter.IdleSalePresenter;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.iwf.photopicker.PhotoPickerActivity;
import me.iwf.photopicker.utils.PhotoPickerIntent;

import static android.R.attr.data;
import static android.R.attr.path;
import static android.os.Build.ID;
import static android.os.Build.TYPE;


/**
 * Created by Orange on 2017/4/8.
 */

public class AddIdleSaleActivity extends BaseActivity implements IIdleSaleContract.View {

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
    RelativeLayout mImageContentLl;
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
    public static final int REQUEST_CODE = 1;

    private String[] mTitle;
    private int[] mPostions;
    private int mPostion;
    private String mType;
    private int mIsUser;
    private int mIndex;
    private Set<File> mFiles;
    private ArrayList<String> mImages;
    private String mTradetype;
    private String mSelltype;
    private UserInfo mUserInfo;

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
        mUserInfo = (UserInfo) getIntent().getSerializableExtra(Const.USERINFO_EXTRA);
        mIsUser = getIntent().getIntExtra(Const.TYPE, 0);
        mPresenter.fetchIdleSaleCategories();
    }


    private void initView() {
        mIvImage1.setVisibility(View.INVISIBLE);
        mIvImage2.setVisibility(View.INVISIBLE);
        mIvImage3.setVisibility(View.INVISIBLE);
    }


    @OnClick({R.id.iv_add_img, R.id.btn_release, R.id.btn_goods_type, R.id.btn_mode, R.id.btn_trade_type, R.id.tv_goods_type, R.id.tv_mode, R.id.tv_trade_type})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_add_img:
                PhotoPickerIntent intent = new PhotoPickerIntent(this);
                intent.setPhotoCount(3);
                intent.setShowCamera(false);
                startActivityForResult(intent, REQUEST_CODE);
                break;
            case R.id.btn_release:
                AddIdleSaleGoods();
                break;
            case R.id.btn_goods_type:
                new AlertDialog.Builder(this)
                        .setTitle("请选择商品类型")
                        .setIcon(R.drawable.ic_idle_sale)
                        .setSingleChoiceItems(mTitle, mPostion, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                mType = mTitle[i];
                                mPostion = i;
                                mIndex = mPostions[i];
                                mTvGoodsType.setText(mTitle[i]);
                                dialogInterface.dismiss();
                            }
                        })
                        .create()
                        .show();
                break;
            case R.id.btn_mode:
                new AlertDialog.Builder(this)
                        .setTitle("请选择交易方式")
                        .setIcon(R.drawable.ic_idle_sale)
                        .setSingleChoiceItems(Const.TRADETYPE, mPostion, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                mTradetype = Const.TRADETYPE[i];
                                mPostion = i;
                                mTvMode.setText(Const.TRADETYPE[i]);
                                dialogInterface.dismiss();
                            }
                        })
                        .create()
                        .show();
                break;
            case R.id.btn_trade_type:
                new AlertDialog.Builder(this)
                        .setTitle("请选择讨价类型")
                        .setIcon(R.drawable.ic_idle_sale)
                        .setSingleChoiceItems(Const.SELLTYPE, mPostion, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                mSelltype = Const.SELLTYPE[i];
                                mPostion = i;
                                mTvTradeType.setText(Const.SELLTYPE[i]);
                                dialogInterface.dismiss();
                            }
                        })
                        .create()
                        .show();
                break;

            case R.id.tv_goods_type:
                new AlertDialog.Builder(this)
                        .setTitle("请选择商品类型")
                        .setIcon(R.drawable.ic_idle_sale)
                        .setSingleChoiceItems(mTitle, mPostion, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                mType = mTitle[i];
                                mPostion = i;
                                mIndex = mPostions[i];
                                mTvGoodsType.setText(mTitle[i]);
                                dialogInterface.dismiss();
                            }
                        })
                        .create()
                        .show();
                break;
            case R.id.tv_mode:
                new AlertDialog.Builder(this)
                        .setTitle("请选择交易方式")
                        .setIcon(R.drawable.ic_idle_sale)
                        .setSingleChoiceItems(Const.TRADETYPE, mPostion, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                mTradetype = Const.TRADETYPE[i];
                                mPostion = i;
                                mTvMode.setText(Const.TRADETYPE[i]);
                                dialogInterface.dismiss();
                            }
                        })
                        .create()
                        .show();
                break;
            case R.id.tv_trade_type:
                new AlertDialog.Builder(this)
                        .setTitle("请选择讨价类型")
                        .setIcon(R.drawable.ic_idle_sale)
                        .setSingleChoiceItems(Const.SELLTYPE, mPostion, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                mSelltype = Const.SELLTYPE[i];
                                mPostion = i;
                                mTvTradeType.setText(Const.SELLTYPE[i]);
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
        if (!mTvMode.getText().toString().trim().equals(mTradetype)) {
            showMessage("请选择交易方式");
            return;
        }
        if (!mTvTradeType.getText().toString().trim().equals(mSelltype)) {
            showMessage("请选择讨价类型");
            return;
        }
        if (!mTvGoodsType.getText().toString().trim().equals(mType)) {
            showMessage("请选择商品类别");
            return;
        }
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
        if (mImages == null) {
            showMessage("请选择一张图片");
            return;
        }
        for (int i = 0; i < mImages.size(); i++) {
            mFiles.add(new File(mImages.get(i)));
        }
        IdleSaleInfo idlSaleInfo = new IdleSaleInfo();
        idlSaleInfo.setName(mEtTitle.getText().toString());
        idlSaleInfo.setMoney(mEtPrice.getText().toString());
        idlSaleInfo.setBewrite(mEtDegree.getText().toString());
        idlSaleInfo.setContent(mEtDescribe.getText().toString());
        idlSaleInfo.setGoodstype(String.valueOf(mIndex));
        idlSaleInfo.setTradetype(mTradetype);
        if (mSelltype.equals("一口价"))
            idlSaleInfo.setSelltype(0);
        else
            idlSaleInfo.setSelltype(1);
        idlSaleInfo.setTradeplace(mEtPlace.getText().toString());
        idlSaleInfo.setMobile(mEtPhone.getText().toString());
        idlSaleInfo.setQq(mEtQq.getText().toString());
        idlSaleInfo.setUid(mUserInfo.getUid());
        idlSaleInfo.setFiles(mFiles);
        mPresenter.pushGoods(idlSaleInfo);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            if (data != null) {
                mImages = data.getStringArrayListExtra(PhotoPickerActivity.KEY_SELECTED_PHOTOS);
                mFiles = new HashSet<>(3);
                if (mImages.size() == 1) {
                    mIvImage1.setVisibility(View.VISIBLE);
                    Picasso.with(this).load(new File(mImages.get(0))).into(mIvImage1);
                    if (mIvImage2.getVisibility() == View.VISIBLE) {
                        mIvImage2.setVisibility(View.INVISIBLE);
                    }
                    if (mIvImage3.getVisibility() == View.VISIBLE) {
                        mIvImage3.setVisibility(View.INVISIBLE);
                    }

                    float x = mIvImage2.getX();
                    float y = mIvImage2.getY();
                    mIvAddImg.setX(x);
                    mIvAddImg.setY(y);
                } else if (mImages.size() == 2) {
                    mIvImage1.setVisibility(View.VISIBLE);
                    mIvImage2.setVisibility(View.VISIBLE);
                    Picasso.with(this).load(new File(mImages.get(0))).into(mIvImage1);
                    Picasso.with(this).load(new File(mImages.get(1))).into(mIvImage2);
                    if (mIvImage3.getVisibility() == View.VISIBLE) {
                        mIvImage3.setVisibility(View.INVISIBLE);
                    }
                    float x = mIvImage3.getX();
                    float y = mIvImage3.getY();
                    mIvAddImg.setX(x);
                    mIvAddImg.setY(y);
                } else if (mImages.size() == 3) {
                    mIvImage1.setVisibility(View.VISIBLE);
                    mIvImage2.setVisibility(View.VISIBLE);
                    mIvImage3.setVisibility(View.VISIBLE);
                    Picasso.with(this).load(new File(mImages.get(0))).into(mIvImage1);
                    Picasso.with(this).load(new File(mImages.get(1))).into(mIvImage2);
                    Picasso.with(this).load(new File(mImages.get(2))).into(mIvImage3);
                    mIvAddImg.setVisibility(View.GONE);
                }
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
    public void setIdleSaleCategories(List<CategoriesInfo> categories) {
        mTitle = new String[categories.size()];
        mPostions = new int[categories.size()];
        for (int i = 0; i < categories.size(); i++) {
            mTitle[i] = categories.get(i).getName();
            mPostions[i] = categories.get(i).getId();
        }
    }


    @Override
    public void setIdleSaleMessageList(List<LeaveMessageInfo> idleSaleMessageList) {//忽略
    }


    @Override
    public void showErrorMsg(String errorMsg) {
        showMessage(errorMsg);
    }

    @Override
    public void showSuccessfullyPush(String succcessMsg) {
        showMessage(succcessMsg);
        if (mIsUser == 1) {
            Intent intent = new Intent(this, MyIdleSaleActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, IdleSaleActivity.class);
            startActivity(intent);
        }

        this.finish();
    }

    @Override
    public void showSuccessfullyleaveMessage(String succcessMsg) {
        //忽略
    }

    @Override
    public void setIdleSaleDetail(IdleSaleInfo idleSaleInfo) {

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

}
