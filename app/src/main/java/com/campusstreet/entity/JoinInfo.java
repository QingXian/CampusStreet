package com.campusstreet.entity;

import java.io.Serializable;

/**
 * 赏金大厅报名信息实体类
 * Created by Orange on 2017/4/27.
 */

public class JoinInfo implements Serializable {

    /**
     * username : 式微云雨
     * userpic : 3.jpg
     * id : 1
     * state : 0
     * fee : 1.0000
     * addtime : 2017/3/21 11:40:33
     * finishtime : 2017/3/24 14:00:00
     * phone : 18959075323
     * summary : 12312
     */

    private String username;
    private String userpic;
    private int id;
    private int state;
    private String fee;
    private String addtime;
    private String finishtime;
    private String phone;
    private String summary;
    private String uid;

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public String getFinishtime() {
        return finishtime;
    }

    public void setFinishtime(String finishtime) {
        this.finishtime = finishtime;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
