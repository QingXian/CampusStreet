package com.campusstreet.entity;

import java.io.File;
import java.io.Serializable;
import java.util.Set;

/**
 * Created by Orange on 2017/5/31.
 */

public class LiveInfo implements Serializable {
    private String uid;
    private int id;
    private String username;
    private String userpic;
    private String con;
    private String images;
    private String pubtime;
    private Set<File> mFiles;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

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

    public Set<File> getFiles() {
        return mFiles;
    }

    public void setFiles(Set<File> files) {
        mFiles = files;
    }
}
