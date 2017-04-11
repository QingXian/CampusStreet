package com.campusstreet.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.campusstreet.R;
import com.campusstreet.activity.AssociationActivity;
import com.campusstreet.activity.BountyHallActivity;
import com.campusstreet.activity.BuyZoneActivity;
import com.campusstreet.activity.CampusInformationActivity;
import com.campusstreet.activity.CampusRecruitmentActivity;
import com.campusstreet.activity.IdleSaleActivity;
import com.campusstreet.activity.PartnerActivity;
import com.campusstreet.activity.PeripheraShopActivity;
import com.squareup.picasso.Picasso;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Orange on 2017/4/1.
 */


public class HomeFragment extends Fragment implements OnBannerListener {

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
    @BindView(R.id.rl_content)
    RecyclerView mRlContent;
    private List<Integer> testList = new ArrayList<>();
    private Unbinder mUnbinder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        testList.add(R.drawable.bg_test);
        testList.add(R.drawable.bg_test);
        testList.add(R.drawable.bg_test);
        testList.add(R.drawable.bg_test);
        mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        // 设置图片加载器
        mBanner.setImageLoader(new PicassoImageLoader());
        // 设置banner动画效果
        mBanner.setBannerAnimation(Transformer.DepthPage);
        // 设置标题集合（当banner样式有显示title时）
        // mBanner.setBannerTitles(Arrays.asList(mTitles));
        // 设置指示器位置（当banner模式中有指示器时）
        mBanner.setIndicatorGravity(BannerConfig.RIGHT);
        mBanner.setImages(testList);
        mBanner.start();
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
                startActivity(intent);
                break;
            case R.id.tv_buy_zone:
                intent = new Intent(getActivity(), BuyZoneActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_bounty_hall:
                intent = new Intent(getActivity(), BountyHallActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_association:
                intent = new Intent(getActivity(), AssociationActivity.class);
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
        Log.d("HomeFragment", "OnBannerClick: "+position);

    }

    private class PicassoImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Picasso.with(context).load((Integer) path).into(imageView);
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
}
