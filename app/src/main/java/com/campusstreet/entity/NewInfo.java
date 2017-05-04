package com.campusstreet.entity;

import java.io.Serializable;

/**
 * Created by Orange on 2017/5/4.
 */

public class NewInfo implements Serializable {

    /**
     * id : 15
     * title : 龙岩学院组织在职党员协助市区各个社区开展创城入户宣传活动
     * sectitle : 龙岩学院组织在职党员协助市区各个社区开展创城入户宣传活动
     * coverimage :
     * pubtime : 2017/1/3 10:04:06
     */

    private int id;
    private String title;
    private String sectitle;
    private String coverimage;
    private String pubtime;
    private String summary;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSectitle() {
        return sectitle;
    }

    public void setSectitle(String sectitle) {
        this.sectitle = sectitle;
    }

    public String getCoverimage() {
        return coverimage;
    }

    public void setCoverimage(String coverimage) {
        this.coverimage = coverimage;
    }

    public String getPubtime() {
        return pubtime;
    }

    public void setPubtime(String pubtime) {
        this.pubtime = pubtime;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}
