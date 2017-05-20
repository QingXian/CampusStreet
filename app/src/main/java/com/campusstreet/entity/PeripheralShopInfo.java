package com.campusstreet.entity;

import java.io.Serializable;

/**
 * Created by Orange on 2017/5/18.
 */

public class PeripheralShopInfo implements Serializable {

    /**
     * typename : 其它
     * img : shop/i2/131254877578900000.jpg
     * address : 新罗区龙岩学院瑞善格物食堂一楼
     * bhour : 07:00-24:00
     * phone : 18859619016
     * name : 小意思蛋糕坊
     * point : 4.7
     * lng : 117.033002
     * lat : 25.033518
     */

    private int id;
    private String typename;
    private String img;
    private String address;
    private String bhour;
    private String phone;
    private String name;
    private double point;
    private String lng;
    private String lat;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBhour() {
        return bhour;
    }

    public void setBhour(String bhour) {
        this.bhour = bhour;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPoint() {
        return point;
    }

    public void setPoint(double point) {
        this.point = point;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }
}
