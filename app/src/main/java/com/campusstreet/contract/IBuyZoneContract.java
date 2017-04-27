package com.campusstreet.contract;

import com.campusstreet.entity.BuyZoneInfo;
import com.campusstreet.entity.LeaveMessageInfo;

import java.util.List;

/**
 * Created by Orange on 2017/4/24.
 */

public interface IBuyZoneContract {

    interface Presenter extends BasePresenter {

    void fetchBuyZoneList(int pi);

    void pushBuy(BuyZoneInfo buyZoneInfo);

    void leaveMessage(String uid, int gid, String con);

    void fetchBuyZoneMessageList(int id, int pi);

}
interface View extends BaseView<Presenter> {

    void setBuyZone(List<BuyZoneInfo> buyZoneInfoList);

    void setBuyZoneMessageList(List<LeaveMessageInfo> BuyZoneMessageList);

    void showErrorMsg(String errorMsg);

    void showSuccessfullyPush(String succcessMsg);

    void showSuccessfullyleaveMessage(String succcessMsg);

    /**
     * 设置是否加载指示器
     *
     * @param active true表示显示，false不显示
     */
    void setLoadingIndicator(boolean active);
}
}