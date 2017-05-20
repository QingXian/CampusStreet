package com.campusstreet.entity;

import java.io.Serializable;

/**
 * Created by Orange on 2017/5/18.
 */

public class PeripheralShopGoodInfo implements Serializable {

    /**
     * id : 4
     * shopid : 32
     * goodsname : 汉堡王
     * goodsimage : sg/i2/131259871160390000.jpg
     * goodsmoney : 50.0000
     */

    private String id;
    private String shopid;
    private String goodsname;
    private String goodsimage;
    private double goodsmoney;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getShopid() {
        return shopid;
    }

    public void setShopid(String shopid) {
        this.shopid = shopid;
    }

    public String getGoodsname() {
        return goodsname;
    }

    public void setGoodsname(String goodsname) {
        this.goodsname = goodsname;
    }

    public String getGoodsimage() {
        return goodsimage;
    }

    public void setGoodsimage(String goodsimage) {
        this.goodsimage = goodsimage;
    }

    public double getGoodsmoney() {
        return goodsmoney;
    }

    public void setGoodsmoney(double goodsmoney) {
        this.goodsmoney = goodsmoney;
    }
}
