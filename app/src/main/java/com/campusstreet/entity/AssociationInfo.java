package com.campusstreet.entity;

/**
 * Created by Orange on 2017/5/6.
 */

public class AssociationInfo {

    /**
     * id : 10
     * name : 团委学生会
     * classimg : studen.png
     * note :
     * follownum : 0
     * memnum : 0
     * username :
     */

    private String id;
    private String name;
    private String classimg;
    private String note;
    private String follownum;
    private String memnum;
    private String username;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassimg() {
        return classimg;
    }

    public void setClassimg(String classimg) {
        this.classimg = classimg;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getFollownum() {
        return follownum;
    }

    public void setFollownum(String follownum) {
        this.follownum = follownum;
    }

    public String getMemnum() {
        return memnum;
    }

    public void setMemnum(String memnum) {
        this.memnum = memnum;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
