package com.campusstreet.entity;

import java.io.Serializable;

/**
 * Created by Orange on 2017/4/27.
 */

public class BuyZoneInfo implements Serializable {

    /**
     * id : 10
     * name : 求几盆盆栽
     * money : 5
     * con : 想给阳台添加一点生气，个人偏爱肉肉的植物。希望有人推荐，福利是可以介绍宿舍的单身妹子呦。
     * pubtime : 2017/3/6 17:08:31
     * username : H&amp;M
     * mobile : 15059099554
     * qq :
     * userpic : 6.jpg
     */

    private int id;
    private String name;
    private String money;
    private String con;
    private String pubtime;
    private String username;
    private String mobile;
    private String qq;
    private String userpic;
    private String uid;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getCon() {
        return con;
    }

    public void setCon(String con) {
        this.con = con;
    }

    public String getPubtime() {
        return pubtime;
    }

    public void setPubtime(String pubtime) {
        this.pubtime = pubtime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
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
}
