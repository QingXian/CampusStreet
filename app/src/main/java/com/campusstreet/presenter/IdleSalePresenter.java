package com.campusstreet.presenter;

import com.campusstreet.contract.IIdleSaleContract;
import com.campusstreet.contract.IIdleSaleContract;
import com.campusstreet.entity.IdleSaleInfo;
import com.campusstreet.model.IdleSaleBiz;
import com.campusstreet.model.IdleSaleImpl;

import java.util.List;

/**
 * Created by Orange on 2017/4/19.
 */

public class IdleSalePresenter implements IIdleSaleContract.Presenter {

    public static final String TAG = "IdleSalePresenter";

    private IdleSaleImpl mIdleSaleImpl;
    private IIdleSaleContract.View mView;


    public IdleSalePresenter(IdleSaleImpl IdleSaleImpl, IIdleSaleContract.View view) {
        mIdleSaleImpl = IdleSaleImpl;
        mView = view;

        mView.setPresenter(this);
    }


    @Override
    public void fetchIdleSaleCategories() {

    }

    @Override
    public void fetchIdleSaleList(int type, int pi) {
        mIdleSaleImpl.fetchIdleSaleList(type, pi, new IdleSaleBiz.LoadIdleSaleListCallback() {
            @Override
            public void onIdleSaleListLoaded(List<IdleSaleInfo> idleSaleInfoList) {
                mView.setIdleSale(idleSaleInfoList);
                mView.setLoadingIndicator(false);
            }

            @Override
            public void onDataNotAvailable(String errorMsg) {
                mView.setLoadingIndicator(false);
                mView.showErrorMsg(errorMsg);
            }
        });
    }


    @Override
    public void searchGoods(String keyWord) {

    }

    @Override
    public void pushGoods() {

    }

    @Override
    public void leaveMessage() {

    }
}
