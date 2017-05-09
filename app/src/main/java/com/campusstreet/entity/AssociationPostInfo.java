package com.campusstreet.entity;

import java.io.Serializable;

/**
 * Created by Orange on 2017/5/6.
 */

public class AssociationPostInfo implements Serializable {

    /**
     * id : 92
     * userpic : 3.jpg
     * username : 式微云雨
     * addtime : 2017/4/15 17:09:01
     * title : 明天足球赛
     * viewnum : 15
     * replynum : 11
     */

    private int id;
    private String userpic;
    private String username;
    private String addtime;
    private String title;
    private String viewnum;
    private String replynum;
    private String con;

    public String getCon() {
        return con;
    }

    public void setCon(String con) {
        this.con = con;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserpic() {
        return userpic;
    }

    public void setUserpic(String userpic) {
        this.userpic = userpic;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getViewnum() {
        return viewnum;
    }

    public void setViewnum(String viewnum) {
        this.viewnum = viewnum;
    }

    public String getReplynum() {
        return replynum;
    }

    public void setReplynum(String replynum) {
        this.replynum = replynum;
    }
}
