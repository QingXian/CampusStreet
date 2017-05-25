package com.campusstreet.presenter;

import android.util.Log;
import android.widget.Toast;

import com.campusstreet.contract.IHomeContract;
import com.campusstreet.entity.BannerInfo;
import com.campusstreet.entity.HomeDynamicInfo;
import com.campusstreet.model.HomeImpl;
import com.campusstreet.model.IHomeBiz;

import java.util.List;

/**
 * Created by Orange on 2017/4/17.
 */

public class HomePresenter implements IHomeContract.Presenter {

    public static final String TAG = "HomePresenter";

    private HomeImpl mHomeImpl;
    private IHomeContract.View mView;


    public HomePresenter(HomeImpl homeImpl, IHomeContract.View view) {
        mHomeImpl = homeImpl;
        mView = view;

        mView.setPresenter(this);
    }


    @Override
    public void fetchBanner() {
        mHomeImpl.fetchBannerImage(new IHomeBiz.GetBannerCallback() {
            @Override
            public void onFetchSuccess(List<BannerInfo> bannerInfos) {
                mView.setBanner(bannerInfos);
            }

            @Override
            public void onFetchFailure(String errorMsg) {
              mView.showFetchBannerFail(errorMsg);
            }
        });
    }

    @Override
    public void fetchAdvertisement() {

    }

    @Override
    public void fetchdynamicList(String uid) {
        mView.setLoadingIndicator(true);
        mHomeImpl.getdynamicList(uid, new IHomeBiz.LoaddynamicListCallback() {
            @Override
            public void ondynamicListLoaded(List<HomeDynamicInfo> homeDynamicInfo) {
                mView.setdynamicList(homeDynamicInfo);
                mView.setLoadingIndicator(false);
            }

            @Override
            public void onDataNotAvailable(String errorMsg) {
                mView.showErrorMsg(errorMsg);
                mView.setLoadingIndicator(false);
            }
        });
    }

}
