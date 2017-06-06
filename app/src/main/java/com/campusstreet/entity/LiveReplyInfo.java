package com.campusstreet.entity;

import java.io.Serializable;

/**
 * Created by Orange on 2017/6/4.
 */

public class LiveReplyInfo implements Serializable {

    private String username;
    private String userpic;
    private String con;
    private String addtime;

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
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

}
