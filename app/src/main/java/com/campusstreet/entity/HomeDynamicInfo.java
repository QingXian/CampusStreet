package com.campusstreet.entity;

import java.io.Serializable;

/**
 * Created by Orange on 2017/5/25.
 */

public class HomeDynamicInfo implements Serializable {

    /**
     * type : 1
     * tname : 闲置出售·最新回复
     * time : 2017-05-22 09:38
     * tid : 60
     * mainid : 60
     * title : yygg
     * img :
     * con : @xie: wo yao liuyan
     */

    private String type;
    private String tname;
    private String time;
    private String tid;
    private String mainid;
    private String title;
    private String img;
    private String con;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getMainid() {
        return mainid;
    }

    public void setMainid(String mainid) {
        this.mainid = mainid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getCon() {
        return con;
    }

    public void setCon(String con) {
        this.con = con;
    }
}
