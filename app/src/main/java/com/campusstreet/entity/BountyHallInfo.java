package com.campusstreet.entity;

import java.io.Serializable;

/**
 * 赏金大厅的实体类
 * Created by Orange on 2017/4/27.
 */

public class BountyHallInfo implements Serializable {

    /**
     * id : 5
     * tpname : 其它
     * attachs :
     * mobile : 13635283686
     * person : 1
     * fee : 0.0000
     * sperson : 0
     * tperson : 0
     * pubtime : 2017/3/24 9:33:05
     * title : 宿舍大扫除
     * username : 式微云雨
     * mobile1 : 13635283686
     * userpic : 3.jpg
     * endtime : 2017/3/24 15:00:00
     */

    private Integer id;
    private String tpname;
    private String attachs;
    private String mobile;
    private int person;
    private String fee;
    private int sperson;
    private int tperson;
    private String pubtime;
    private String title;
    private String username;
    private String mobile1;
    private String userpic;
    private String endtime;
    private String uid;
    private String type;
    private String linkman;
    private String con;
    private String key;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTpname() {
        return tpname;
    }

    public void setTpname(String tpname) {
        this.tpname = tpname;
    }

    public String getAttachs() {
        return attachs;
    }

    public void setAttachs(String attachs) {
        this.attachs = attachs;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getPerson() {
        return person;
    }

    public void setPerson(int person) {
        this.person = person;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public int getSperson() {
        return sperson;
    }

    public void setSperson(int sperson) {
        this.sperson = sperson;
    }

    public int getTperson() {
        return tperson;
    }

    public void setTperson(int tperson) {
        this.tperson = tperson;
    }

    public String getPubtime() {
        return pubtime;
    }

    public void setPubtime(String pubtime) {
        this.pubtime = pubtime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMobile1() {
        return mobile1;
    }

    public void setMobile1(String mobile1) {
        this.mobile1 = mobile1;
    }

    public String getUserpic() {
        return userpic;
    }

    public void setUserpic(String userpic) {
        this.userpic = userpic;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLinkman() {
        return linkman;
    }

    public void setLinkman(String linkman) {
        this.linkman = linkman;
    }


    public String getCon() {
        return con;
    }

    public void setCon(String con) {
        this.con = con;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
