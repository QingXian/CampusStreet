package com.campusstreet.entity;

import java.io.Serializable;

/**
 * Created by Orange on 2017/5/15.
 */

public class UserAssociationInfo implements Serializable {

    private int id;
    private int assnid;
    private String assnname;
    private String assnjob;
    private String islast;
    private String jointime;
    private String classimg;
    private String username;
    private String note;


    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getClassimg() {
        return classimg;
    }

    public void setClassimg(String classimg) {
        this.classimg = classimg;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getAssnid() {
        return assnid;
    }

    public void setAssnid(int assnid) {
        this.assnid = assnid;
    }

    public String getAssnname() {
        return assnname;
    }

    public void setAssnname(String assnname) {
        this.assnname = assnname;
    }

    public String getAssnjob() {
        return assnjob;
    }

    public void setAssnjob(String assnjob) {
        this.assnjob = assnjob;
    }

    public String getIslast() {
        return islast;
    }

    public void setIslast(String islast) {
        this.islast = islast;
    }

    public String getJointime() {
        return jointime;
    }

    public void setJointime(String jointime) {
        this.jointime = jointime;
    }
}
