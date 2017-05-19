package com.campusstreet.entity;

import java.io.Serializable;

/**
 * Created by Orange on 2017/5/6.
 */

public class AssociationNumInfo implements Serializable {


    /**
     * id : 3
     * uid : Mw==
     * userpic : 3.jpg
     * username : 式微云雨
     * assnjob : 1
     * jointime : 2017/4/15 15:35:46
     */

    private String id;
    private String uid;
    private String userpic;
    private String username;
    private String assnjob;
    private String jointime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
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

    public String getAssnjob() {
        return assnjob;
    }

    public void setAssnjob(String assnjob) {
        this.assnjob = assnjob;
    }

    public String getJointime() {
        return jointime;
    }

    public void setJointime(String jointime) {
        this.jointime = jointime;
    }
}
