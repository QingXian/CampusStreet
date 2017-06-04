package com.campusstreet.entity;

import java.io.Serializable;

/**
 * Created by Orange on 2017/6/4.
 */

public class LiveReplyInfo implements Serializable {

    private String nick;
    private String userpic;
    private String con;
    private String atime;

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
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

    public String getAtime() {
        return atime;
    }

    public void setAtime(String atime) {
        this.atime = atime;
    }
}
