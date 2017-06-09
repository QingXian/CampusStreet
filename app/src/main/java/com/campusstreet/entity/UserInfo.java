package com.campusstreet.entity;

import java.io.Serializable;

/**
 * Created by Orange on 2017/5/3.
 */

public class UserInfo implements Serializable{

    /**
     * uid : Mw==
     * mobile : 13635283686
     * username : 式微云雨
     * sex : 2
     * major : 物机院
     * qq : 38537357
     * userpic:
     */

    private String uid;
    private String mobile;
    private String username;
    private String sex;
    private String major;
    private String qq;
    private String userpic;
    private String point;


    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public String getUserpic() {
        return userpic;
    }

    public void setUserpic(String userpic) {
        this.userpic = userpic;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }
}
