package com.campusstreet.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.campusstreet.R;
import com.campusstreet.activity.AdActivity;
import com.campusstreet.activity.AssociationActivity;
import com.campusstreet.activity.BountyHallActivity;
import com.campusstreet.activity.BountyHallDetailActivity;
import com.campusstreet.activity.BuyZoneActivity;
import com.campusstreet.activity.BuyZoneDetailActivity;
import com.campusstreet.activity.CampusInformationActivity;
import com.campusstreet.activity.CampusInformationDetailActivity;
import com.campusstreet.activity.CampusRecruitmentActivity;
import com.campusstreet.activity.CampusRecruitmentDetailActivity;
import com.campusstreet.activity.IdleSaleActivity;
import com.campusstreet.activity.IdleSaleDetailActivity;
import com.campusstreet.activity.PartnerActivity;
import com.campusstreet.activity.PartnerDetailActivity;
import com.campusstreet.activity.PeripheraShopActivity;
import com.campusstreet.activity.PostDetailActivity;
import com.campusstreet.activity.SeatsActivity;
import com.campusstreet.adapter.HomeFragmentRecyclerViewAdapter;
import com.campusstreet.adapter.IdleSaleRecyclerViewAdapter;
import com.campusstreet.common.AppConfig;
import com.campusstreet.common.Const;
import com.campusstreet.contract.IHomeContract;
import com.campusstreet.entity.BannerInfo;
import com.campusstreet.entity.HomeDynamicInfo;
import com.campusstreet.entity.IdleSaleInfo;
import com.campusstreet.entity.UserInfo;
import com.squareup.picasso.Picasso;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.campusstreet.common.Const.BANNER_TITLE_EXTRA;
import static com.campusstreet.common.Const.BANNER_URL_EXTRA;
import static com.campusstreet.common.Const.ID_EXTRA;
import static com.campusstreet.common.Const.TYPE;
import static com.campusstreet.common.Const.USERINFO_EXTRA;

/**
 * Created by Orange on 2017/4/1.
 */


public class HomeFragment extends Fragment implements OnBannerListener, IHomeContract.View {

    private Toast mToast;
    @BindView(R.id.banner)
    Banner mBanner;
    @BindView(R.id.tv_campus_recruitment)
    TextView mTvCampusRecruitment;
    @BindView(R.id.tv_idle_sale)
    TextView mTvIdleSale;
    @BindView(R.id.tv_buy_zone)
    TextView mTvBuyZone;
    @BindView(R.id.tv_bounty_hall)
    TextView mTvBountyHall;
    @BindView(R.id.tv_association)
    TextView mTvAssociation;
    @BindView(R.id.tv_campus_information)
    TextView mTvCampusInformation;
    @BindView(R.id.tv_partner)
    TextView mTvPartner;
    @BindView(R.id.tv_peripheral_shop)
    TextView mTvPeripheralShop;
    @BindView(R.id.imageView)
    ImageView mImageView;
    @BindView(R.id.rv_content)
    RecyclerView mRvContent;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.progress_bar_title)
    TextView mProgressBarTitle;
    @BindView(R.id.progress_bar_container)
    LinearLayout mProgressBarContainer;
    @BindView(R.id.tv_order_seats)
    TextView mTvOrderSeats;
    private Unbinder mUnbinder;
    private HomeFragmentRecyclerViewAdapter mAdapter;
    private IHomeContract.Presenter mPresenter;
    private ArrayList<BannerInfo> mBannerInfos;
    private UserInfo mUserInfo;

    public static HomeFragment newInstance(UserInfo userInfo) {
        Bundle args = new Bundle();
        args.putSerializable(USERINFO_EXTRA, userInfo);
        HomeFragment homeFragment = new HomeFragment();
        homeFragment.setArguments(args);
        return homeFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBannerInfos = new ArrayList<>();
        if (getArguments() != null) {
            mUserInfo = (UserInfo) getArguments().getSerializable(USERINFO_EXTRA);
        }
        if (mUserInfo != null) {
            mPresenter.fetchdynamicList(mUserInfo.getUid());
        } else {
            mPresenter.fetchdynamicList(null);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        mUnbinder = ButterKnife.bind(this, root);
        initData();
        initEvent();
        return root;
    }

    private void initEvent() {
        mBanner.setOnBannerListener(this);
        mAdapter.setOnItemClickListener(new HomeFragmentRecyclerViewAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, HomeDynamicInfo homeDynamicInfo) {
                switch (homeDynamicInfo.getType()) {
                    case 1:
                        Intent intent = new Intent(getActivity(), IdleSaleDetailActivity.class);
                        intent.putExtra(USERINFO_EXTRA, mUserInfo);
                        intent.putExtra(ID_EXTRA, homeDynamicInfo.getMainid());
                        startActivity(intent);
                        break;
                    case 2:
                        intent = new Intent(getActivity(), BuyZoneDetailActivity.class);
                        intent.putExtra(USERINFO_EXTRA, mUserInfo);
                        intent.putExtra(ID_EXTRA, homeDynamicInfo.getMainid());
                        startActivity(intent);
                        break;
                    case 3:
                        intent = new Intent(getActivity(), CampusRecruitmentDetailActivity.class);
                        intent.putExtra(TYPE, homeDynamicInfo.getType());
                        intent.putExtra(ID_EXTRA, homeDynamicInfo.getMainid());
                        startActivity(intent);
                        break;
                    case 4:
                        intent = new Intent(getActivity(), BountyHallDetailActivity.class);
                        intent.putExtra(USERINFO_EXTRA, mUserInfo);
                        intent.putExtra(ID_EXTRA, homeDynamicInfo.getMainid());
                        startActivity(intent);
                        break;
                    case 5:
                        intent = new Intent(getActivity(), PostDetailActivity.class);
                        intent.putExtra(USERINFO_EXTRA, mUserInfo);
                        intent.putExtra(ID_EXTRA, homeDynamicInfo.getMainid());
                        startActivity(intent);
                        break;
                    case 6:
                        intent = new Intent(getActivity(), CampusInformationDetailActivity.class);
                        intent.putExtra(ID_EXTRA, homeDynamicInfo.getMainid());
                        startActivity(intent);
                        break;
                    case 7:
                        intent = new Intent(getActivity(), PartnerDetailActivity.class);
                        intent.putExtra(ID_EXTRA, homeDynamicInfo.getMainid());
                        startActivity(intent);
                        break;
                    case 8:
                        intent = new Intent(getActivity(), CampusRecruitmentDetailActivity.class);
                        intent.putExtra(TYPE, homeDynamicInfo.getType());
                        intent.putExtra(ID_EXTRA, homeDynamicInfo.getMainid());
                        startActivity(intent);
                        break;
                }
            }
        });
    }

    private void initData() {
        mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        // 设置图片加载器
        mBanner.setImageLoader(new PicassoImageLoader());
        // 设置banner动画效果
        mBanner.setBannerAnimation(Transformer.Default);
        // 设置标题集合（当banner样式有显示title时）
        // mBanner.setBannerTitles(Arrays.asList(mTitles));
        // 设置指示器位置（当banner模式中有指示器时）
        mBanner.setIndicatorGravity(BannerConfig.RIGHT);
        if (mBannerInfos == null || mBannerInfos.size() < 1) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    if (mPresenter != null)
                        mPresenter.fetchBanner();
                }
            }).start();
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mRvContent.setLayoutManager(linearLayoutManager);
        mAdapter = new HomeFragmentRecyclerViewAdapter(getActivity(), null);
        mRvContent.setAdapter(mAdapter);
        mRvContent.setNestedScrollingEnabled(false);
    }


    @OnClick({R.id.tv_campus_recruitment, R.id.tv_idle_sale, R.id.tv_buy_zone, R.id.tv_bounty_hall,
            R.id.tv_association, R.id.tv_campus_information, R.id.tv_partner, R.id.tv_peripheral_shop, R.id.tv_order_seats})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_campus_recruitment:
                Intent intent = new Intent(getActivity(), CampusRecruitmentActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_idle_sale:
                intent = new Intent(getActivity(), IdleSaleActivity.class);
                intent.putExtra(USERINFO_EXTRA, mUserInfo);
                startActivity(intent);
                break;
            case R.id.tv_buy_zone:
                intent = new Intent(getActivity(), BuyZoneActivity.class);
                intent.putExtra(USERINFO_EXTRA, mUserInfo);
                startActivity(intent);
                break;
            case R.id.tv_bounty_hall:
                intent = new Intent(getActivity(), BountyHallActivity.class);
                intent.putExtra(USERINFO_EXTRA, mUserInfo);
                startActivity(intent);
                break;
            case R.id.tv_association:
                intent = new Intent(getActivity(), AssociationActivity.class);
                intent.putExtra(USERINFO_EXTRA, mUserInfo);
                startActivity(intent);
                break;
            case R.id.tv_campus_information:
                intent = new Intent(getActivity(), CampusInformationActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_partner:
                intent = new Intent(getActivity(), PartnerActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_peripheral_shop:
                intent = new Intent(getActivity(), PeripheraShopActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_order_seats:
                intent = new Intent(getActivity(), SeatsActivity.class);
                intent.putExtra(USERINFO_EXTRA, mUserInfo);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    @Override
    public void OnBannerClick(int position) {
        if (mBannerInfos != null || mBannerInfos.size() > 1) {
            Intent intent = new Intent(getActivity(), AdActivity.class);
            intent.putExtra(BANNER_TITLE_EXTRA, mBannerInfos.get(position).getTitle());
            intent.putExtra(BANNER_URL_EXTRA, mBannerInfos.get(position).getUrl());
            startActivity(intent);
        }
    }

    @Override
    public void setPresenter(IHomeContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void setUserInfo() {

    }

    @Override
    public void setBanner(List<BannerInfo> bannerInfos) {
        mBannerInfos = (ArrayList<BannerInfo>) bannerInfos;
        ArrayList<String> imagesUrl = new ArrayList<>();
        for (BannerInfo bannerInfo :
                bannerInfos) {
            imagesUrl.add(bannerInfo.getImage());
        }
        if (mBanner != null) {
            mBanner.setImages(imagesUrl);
            mBanner.start();
        }
    }

    @Override
    public void setAdvertisement() {

    }

    @Override
    public void showFetchBannerFail(String errorMsg) {
        showMessage(errorMsg);
    }

    @Override
    public void setdynamicList(List<HomeDynamicInfo> homeDynamicInfos) {
        if (mRvContent != null) {
            mRvContent.setVisibility(View.VISIBLE);
            mAdapter.replaceData(homeDynamicInfos);
            setLoadingIndicator(false);
        }
    }

    @Override
    public void showErrorMsg(String errorMsg) {
        showMessage(errorMsg);
    }

    @Override
    public void setLoadingIndicator(boolean active) {

    }


    private class PicassoImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Picasso.with(context)
                    .load(AppConfig.PIC_HOME_BANNER_SERVER_HOST + path)
                    .placeholder(R.drawable.ic_base_picture)
                    .error(R.drawable.ic_pic_error)
                    .fit()
                    .into(imageView);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mBanner.isAutoPlay(true);

    }

    @Override
    public void onStop() {
        super.onStop();
        mBanner.isAutoPlay(false);
        cancelToast();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    public void showMessage(String text) {
        if (mToast == null) {
            mToast = Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(text);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.show();
    }

    public void cancelToast() {
        if (mToast != null) {
            mToast.cancel();
        }
    }
}
