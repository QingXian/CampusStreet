package com.campusstreet.entity;

import java.io.Serializable;

/**
 * Created by Orange on 2017/5/18.
 */

public class PartnerInfo implements Serializable {

    /**
     * name : 德国一品牌进口奶制品代理销售！
     * img :
     * organizer : 式微云雨
     * typename : 创业项目
     * state : 1
     * publishtime : 2017/1/14 11:31:14
     * sketch : 大型奶制品公司新品牌！
     * summary : 德国一大型公司一液态奶亲新品牌！该公司有大型牧场，加工厂！产品质量是欧洲前列！价优！
     * needperson : 营销合伙人,资源合伙人,投融资合伙人
     * cnstage : 还没准备好
     * mobile : 13635283686
     */

    private String name;
    private String img;
    private String organizer;
    private String typename;
    private String state;
    private String publishtime;
    private String sketch;
    private String summary;
    private String needperson;
    private String cnstage;
    private String mobile;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getOrganizer() {
        return organizer;
    }

    public void setOrganizer(String organizer) {
        this.organizer = organizer;
    }

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPublishtime() {
        return publishtime;
    }

    public void setPublishtime(String publishtime) {
        this.publishtime = publishtime;
    }

    public String getSketch() {
        return sketch;
    }

    public void setSketch(String sketch) {
        this.sketch = sketch;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getNeedperson() {
        return needperson;
    }

    public void setNeedperson(String needperson) {
        this.needperson = needperson;
    }

    public String getCnstage() {
        return cnstage;
    }

    public void setCnstage(String cnstage) {
        this.cnstage = cnstage;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
