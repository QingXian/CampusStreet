package com.campusstreet.entity;

import java.io.Serializable;

/**
 * Created by Orange on 2017/6/7.
 */

public class UserJoinTaskInfo implements Serializable {

    /**
     * id : 7
     * taskid : 21
     * tpname : 其它
     * fee : 123.0000
     * addtime : 2017/6/7 19:21:23
     * title : 测试测试
     * username : test
     * userpic :
     * taskuser : 12345
     * taskuserpic : 20161181903.jpg
     * finishtime :
     * summary : wewew
     * state : 3
     */

    private int id;
    private int taskid;
    private String tpname;
    private String fee;
    private String addtime;
    private String title;
    private String username;
    private String userpic;
    private String taskuser;
    private String taskuserpic;
    private String finishtime;
    private String summary;
    private String state;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTaskid() {
        return taskid;
    }

    public void setTaskid(int taskid) {
        this.taskid = taskid;
    }

    public String getTpname() {
        return tpname;
    }

    public void setTpname(String tpname) {
        this.tpname = tpname;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getTaskuser() {
        return taskuser;
    }

    public void setTaskuser(String taskuser) {
        this.taskuser = taskuser;
    }

    public String getTaskuserpic() {
        return taskuserpic;
    }

    public void setTaskuserpic(String taskuserpic) {
        this.taskuserpic = taskuserpic;
    }

    public String getFinishtime() {
        return finishtime;
    }

    public void setFinishtime(String finishtime) {
        this.finishtime = finishtime;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
