package com.campusstreet.contract;

import com.campusstreet.entity.CategoriesInfo;
import com.campusstreet.entity.LeaveMessageInfo;
import com.campusstreet.entity.NewInfo;
import com.campusstreet.entity.PartnerInfo;

import java.util.List;

import static android.R.attr.type;

/**
 * Created by Orange on 2017/4/24.
 */

public interface IPartnerContract {
    
    interface Presenter extends BasePresenter {

        void fetchPartnerList(String key,int type, int pi);

        void fetchPartnerCategories();

        void fetchPartnerDetail(int pid);

    }
    interface View extends BaseView<Presenter> {


        void setPartnerCategories(List<CategoriesInfo> categories);

        void setPartnerList(List<PartnerInfo> partnerList);

        void setPartnerDetail(PartnerInfo partnerInfo);

        void showErrorMsg(String errorMsg);

        /**
         * 设置是否加载指示器
         *
         * @param active true表示显示，false不显示
         */
        void setLoadingIndicator(boolean active);
    }
}
