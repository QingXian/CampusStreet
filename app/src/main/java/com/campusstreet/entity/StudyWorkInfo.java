package com.campusstreet.entity;

import java.io.Serializable;

/**
 * Created by Orange on 2017/5/3.
 */

public class StudyWorkInfo implements Serializable {

    /**
     * id : 7
     * jobcom : 私人
     * title : 初一数学家教
     * jobmoney : 30一小时
     * jobplace : 美域中央（私聊）
     * jobtime : 早上9点到12点
     * jobperson : 1
     * jobreq : 龙岩学院在校本科生，最好是数学师范生。有家教经验。
     * phone : 15059099554
     * publishtime : 2017/1/19 15:01:20
     */

    private String id;
    private String jobcom;
    private String title;
    private String jobmoney;
    private String jobplace;
    private String jobtime;
    private String jobperson;
    private String jobreq;
    private String phone;
    private String publishtime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJobcom() {
        return jobcom;
    }

    public void setJobcom(String jobcom) {
        this.jobcom = jobcom;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getJobmoney() {
        return jobmoney;
    }

    public void setJobmoney(String jobmoney) {
        this.jobmoney = jobmoney;
    }

    public String getJobplace() {
        return jobplace;
    }

    public void setJobplace(String jobplace) {
        this.jobplace = jobplace;
    }

    public String getJobtime() {
        return jobtime;
    }

    public void setJobtime(String jobtime) {
        this.jobtime = jobtime;
    }

    public String getJobperson() {
        return jobperson;
    }

    public void setJobperson(String jobperson) {
        this.jobperson = jobperson;
    }

    public String getJobreq() {
        return jobreq;
    }

    public void setJobreq(String jobreq) {
        this.jobreq = jobreq;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPublishtime() {
        return publishtime;
    }

    public void setPublishtime(String publishtime) {
        this.publishtime = publishtime;
    }
}
