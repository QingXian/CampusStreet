package com.campusstreet.entity;

import java.io.Serializable;

/**
 * 留言消息的实体类
 * Created by Orange on 2017/4/21.
 */

public class LeaveMessageInfo implements Serializable {

    /**
     * nick : 式微云雨
     * userpic : 3.jpg
     * con : 哪边的大门口
     * atime : 2017-04-14 11:16:07
     */

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
