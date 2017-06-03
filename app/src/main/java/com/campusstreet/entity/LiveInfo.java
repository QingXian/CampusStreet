package com.campusstreet.entity;

import java.io.Serializable;

/**
 * Created by Orange on 2017/5/31.
 */

public class LiveInfo implements Serializable {
    private int id;
    private String username;
    private String userpic;
    private String con;
    private String images;
    private String pubtime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserpic() {
        return userpic;
    }

    public void setUserpic(String userpic) {
        this.userpic = userpic;
    }

    public String getCon() {
        return con;
    }

    public void setCon(String con) {
        this.con = con;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getPubtime() {
        return pubtime;
    }

    public void setPubtime(String pubtime) {
        this.pubtime = pubtime;
    }
}
