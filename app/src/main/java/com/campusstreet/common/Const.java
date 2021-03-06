package com.campusstreet.common;

import java.lang.reflect.Type;

import static android.R.attr.id;

/**
 * Created by dengfengdecao on 16/10/27.
 */

public class Const {

    /**
     * PREFERENCE
     */
    public static final String PREF_USER = "user";  // 用户信息文件
    public static final String PREF_COOKIES = "cookies";    // cookie存储文件
    public static final String PREF_CONFIG = "config";  // 应用配置文件
    public static final String PREF_COOKIES_KEY = "cookie";
    public static final String PREF_USERNAME_KEY = "username";
    public static final String PREF_USERINFO_KEY = "userinfo";
    public static final String MULTIPART_FORM_DATA = "multipart/form-data";
    public static final String PREF_WEIXIN_INFO_KEY = "userwxinfo";
    /**
     * API结果KEY
     */
    public static final String RES_KEY = "res";
    public static final String MESSAGE_KEY = "msg";
    public static final String DATA_KEY = "data";
    public static final String TOTAL_KEY = "total";
    public static final String EXT_KEY = "ext";

    /**
     * 实体类
     */
    public static final String IDLESALEINFO_EXTRA = "idlesaleinfo_extra";
    public static final String BUYZONEIINFO_EXTRA = "buyzoneinfo_extra";
    public static final String BOUNTYHALLINFO_EXTRA = "bountyhallinfo_extra";
    public static final String USERJOINTASKINFO_EXTRA = "userjointaskinfo_extra";
    public static final String JOINNFO_EXTRA = "joininfo_extra";
    public static final String RECRUITMENTNFO_EXTRA = "recruitmentinfo_extra";
    public static final String STUDYWORKINFO_EXTRA = "studyworkinfo_extra";
    public static final String NEWINFO_EXTRA = "newinfo_extra";
    public static final String USERINFO_EXTRA = "userinfo_extra";
    public static final String ASSOCIATIONINFO_EXTRA = "associationinfo_extra";
    public static final String USERASSOCIATIONINFO_EXTRA = "userassociationinfo_extra";
    public static final String ASSOCIATIONPOSTINFO_EXTRA = "associationpostinfo_extra";
    public static final String PARTNERINFO_EXTRA = "partnerinfo_extra";
    public static final String PERIPHERASHOPINFO_EXTRA = "peripherashoinfo_extra";
    public static final String LIVEINFO_EXTRA = "liveinfo_extra";

    /**
     * 数据源
     */
    public static final String[] TRADETYPE = {"见面交易"};
    public static final String[] SELLTYPE = {"一口价", "可小刀"};
    public static final String[] DEPARTMENT = {"信息院", "物机院", "化材院", "经管院", "教科院", "生科院"};
    public static final String[] SEX = {"男", "女"};
    public static final String[] SEATS_VENUE = {"图书馆一", "图书馆二", "图书馆三", "图书馆四", "图书馆五", "图书馆六"};

    /**
     * 判断标识
     */
    //类型判断标识
    public static final String TYPE = "type";
    //是否已经开始赏金任务
    public static final String ISSTRAT = "isStrat";
    //赏金大厅未通过报名
    public static final int JOINNOTPASS = 0;
    //赏金大厅通过报名
    public static final int JOINPASS = 1;
    public static final int STARTTASK = 1;
    public static boolean mIsLogout = false;
    public static final String TID_EXTRA = "tid";
    public static final String ID_EXTRA = "id";
    public static final String BANNER_TITLE_EXTRA = "banner_title_extra";
    public static final String BANNER_URL_EXTRA = "banner_url_extra";
    public static final String PHONE = "phone";
    public static final String CAPTCHA = "captcha";
    public static final int REQUEST_CODE = 1;
}
