package com.campusstreet.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.campusstreet.R;
import com.campusstreet.activity.AdActivity;
import com.campusstreet.activity.AssociationActivity;
import com.campusstreet.activity.BountyHallActivity;
import com.campusstreet.activity.BuyZoneActivity;
import com.campusstreet.activity.CampusInformationActivity;
import com.campusstreet.activity.CampusRecruitmentActivity;
import com.campusstreet.activity.IdleSaleActivity;
import com.campusstreet.activity.PartnerActivity;
import com.campusstreet.activity.PeripheraShopActivity;
import com.campusstreet.adapter.HomeFragmentRecyclerViewAdapter;
import com.campusstreet.common.AppConfig;
import com.campusstreet.common.Const;
import com.campusstreet.contract.IHomeContract;
import com.campusstreet.entity.BannerInfo;
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
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.R.id.list;
import static com.campusstreet.common.Const.BANNER_TITLE_EXTRA;
import static com.campusstreet.common.Const.BANNER_URL_EXTRA;

/**
 * Created by Orange on 2017/4/1.
 */


public class HomeFragment extends Fragment implements OnBannerListener, IHomeContract.View {

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
    private Unbinder mUnbinder;
    private HomeFragmentRecyclerViewAdapter mAdapter;
    private IHomeContract.Presenter mPresenter;
    private ArrayList<BannerInfo> mBannerInfos;
    private UserInfo mUserInfo;

    public static HomeFragment newInstance(UserInfo userInfo) {
        Bundle args = new Bundle();
        args.putSerializable(Const.USERINFO_EXTRA, userInfo);
        HomeFragment homeFragment = new HomeFragment();
        homeFragment.setArguments(args);
        return homeFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBannerInfos = new ArrayList<>();
        if (getArguments() != null) {
            mUserInfo = (UserInfo) getArguments().getSerializable(Const.USERINFO_EXTRA);
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
    }

    private void initData() {
        mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        // 设置图片加载器
        mBanner.setImageLoader(new PicassoImageLoader());
        // 设置banner动画效果
        mBanner.setBannerAnimation(Transformer.DepthPage);
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

        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < 10; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("name", "龙岩学院");
            map.put("head", R.drawable.bg_test);
            map.put("title", "震惊");
            map.put("content", "特大新闻");
            list.add(map);
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mRvContent.setLayoutManager(linearLayoutManager);
        mAdapter = new HomeFragmentRecyclerViewAdapter(getActivity(), list);
        mRvContent.addItemDecoration(new DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL));
        mRvContent.setNestedScrollingEnabled(false);
        mRvContent.setAdapter(mAdapter);
    }


    @OnClick({R.id.tv_campus_recruitment, R.id.tv_idle_sale, R.id.tv_buy_zone, R.id.tv_bounty_hall, R.id.tv_association, R.id.tv_campus_information, R.id.tv_partner, R.id.tv_peripheral_shop})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_campus_recruitment:
                Intent intent = new Intent(getActivity(), CampusRecruitmentActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_idle_sale:
                intent = new Intent(getActivity(), IdleSaleActivity.class);
                intent.putExtra(Const.USERINFO_EXTRA, mUserInfo);
                startActivity(intent);
                break;
            case R.id.tv_buy_zone:
                intent = new Intent(getActivity(), BuyZoneActivity.class);
                intent.putExtra(Const.USERINFO_EXTRA, mUserInfo);
                startActivity(intent);
                break;
            case R.id.tv_bounty_hall:
                intent = new Intent(getActivity(), BountyHallActivity.class);
                intent.putExtra(Const.USERINFO_EXTRA, mUserInfo);
                startActivity(intent);
                break;
            case R.id.tv_association:
                intent = new Intent(getActivity(), AssociationActivity.class);
                intent.putExtra(Const.USERINFO_EXTRA, mUserInfo);
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
    public void setdynamicList() {

    }

    @Override
    public void showErrorMsg(String errorMsg) {
        showMessage(errorMsg);
    }

    @Override
    public void setLoadingIndicator(boolean active) {

    }

    @Override
    public void clearCookie() {

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
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    protected void showMessage(String msg) {
        if (getActivity() != null && !getActivity().isFinishing()) {
            Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
        }
    }
}
