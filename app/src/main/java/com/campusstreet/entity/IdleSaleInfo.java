package com.campusstreet.entity;

import java.io.File;
import java.io.Serializable;
import java.util.Set;

/**
 * IdleSale实体类
 * Created by Orange on 2017/4/19.
 */

public class IdleSaleInfo implements Serializable {

    /**
     * id : 58
     * money : 12.00
     * userpic : 3.jpg
     * gmoney : 12.0000
     * content : 46646454
     * pubtime : 1天前
     * gpublishtime : 2017/4/24 11:40:32
     * name : 测试5
     * bewrite : 9.9
     * selltype : 0
     * username : 式微云雨
     * coverimage : 3174241140320_c.jpg
     * vn : 0
     * mobile : 13635283686
     * qq : 830825546
     * tradeplace : 哦哦
     * tradetype : 0
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
    private String mobile;
    private String qq;
    private String tradeplace;
    private String tradetype;
    private String images;
    private String uid;
    private Set<File> mFiles;

    public Set<File> getFiles() {
        return mFiles;
    }

    public void setFiles(Set<File> files) {
        mFiles = files;
    }

    private String goodstype;

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

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
}
