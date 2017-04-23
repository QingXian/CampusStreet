package com.campusstreet.entity;

import java.io.File;
import java.io.Serializable;

/**
 * IdleSale实体类
 * Created by Orange on 2017/4/19.
 */

public class IdleSaleInfo implements Serializable {

    /**
     * id : 53
     * money : 1,399.00
     * userpic :
     * gmoney : 1399.0000
     * content :
     * pubtime : 3天前
     * gpublishtime : 2017/4/15 1:06:00
     * name : oppor9s
     * bewrite : 全新
     * selltype : 0
     * username : 小黄人
     * coverimage : 1017415010600_c.jpg
     * vn : 0
     */

    private int id;
    private String money;
    private String userpic;
    private String gmoney;
    private String content;
    private String pubtime;
    private String gpublishtime;
    private String name;
    private String bewrite;
    private String selltype;
    private String username;
    private String coverimage;
    private String vn;
    private String goodstype;
    private String tradeplace;
    private String tradetype;
    private String mobile;
    private String qq;
    private String uid;
    private File image; // 本地图片文件

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }


    public String getGoodstype() {
        return goodstype;
    }

    public void setGoodstype(String goodstype) {
        this.goodstype = goodstype;
    }

    public String getTradeplace() {
        return tradeplace;
    }

    public void setTradeplace(String tradeplace) {
        this.tradeplace = tradeplace;
    }

    public String getTradetype() {
        return tradetype;
    }

    public void setTradetype(String tradetype) {
        this.tradetype = tradetype;
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

    public File getImage() {
        return image;
    }

    public void setImage(File image) {
        this.image = image;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getUserpic() {
        return userpic;
    }

    public void setUserpic(String userpic) {
        this.userpic = userpic;
    }

    public String getGmoney() {
        return gmoney;
    }

    public void setGmoney(String gmoney) {
        this.gmoney = gmoney;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPubtime() {
        return pubtime;
    }

    public void setPubtime(String pubtime) {
        this.pubtime = pubtime;
    }

    public String getGpublishtime() {
        return gpublishtime;
    }

    public void setGpublishtime(String gpublishtime) {
        this.gpublishtime = gpublishtime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBewrite() {
        return bewrite;
    }

    public void setBewrite(String bewrite) {
        this.bewrite = bewrite;
    }

    public String getSelltype() {
        return selltype;
    }

    public void setSelltype(String selltype) {
        this.selltype = selltype;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCoverimage() {
        return coverimage;
    }

    public void setCoverimage(String coverimage) {
        this.coverimage = coverimage;
    }

    public String getVn() {
        return vn;
    }

    public void setVn(String vn) {
        this.vn = vn;
    }
}
