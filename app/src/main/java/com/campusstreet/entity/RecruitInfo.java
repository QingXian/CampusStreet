package com.campusstreet.entity;

import java.io.Serializable;

/**
 * Created by Orange on 2017/5/3.
 */

public class RecruitInfo implements Serializable {

    /**
     * id : 9
     * comname : 龙岩万达肯德基招聘兼职
     * comphone : 18959007992
     * comtypename : 外资
     * comindustryname : 互联网/电子商务
     * jobtitle : 肯德基服务生
     * jobtypename : 全职
     * jobmoney : 10元/小时
     * jobexpname : 不限
     * jobeduname : 初中及以下
     * jobperson : 3人
     * publishtime : 2017/4/14 16:27:44
     * jobplace : 新罗
     * jobddes : 岗位为厨房产品制作和晚班打烊
     * jobreq : 学生兼职： 1.年满18周岁，每周提供5天左右的时间上班； 2.身体健康，没有做过重大手术，可以办理健康证； 3.自有交通工具往返餐厅； 4.能够接受倒班，多数晚班，时间为16：00-23：00; 5.有团队及服务意识,服从管理！ 社会兼职： 1.薪资14元/小时，一周可提供3-5天上班时间，上班时间为17：00-23：00 2.需要至少做满一年以上 3.岗位为厨房产品制作和晚班打烊 4.年龄18周岁以上，40周岁以下 5.无需缴纳五险一金，薪资月结2次 有意向者请直接电话联系
     */

    private String id;
    private String comname;
    private String comphone;
    private String comtypename;
    private String comindustryname;
    private String jobtitle;
    private String jobtypename;
    private String jobmoney;
    private String jobexpname;
    private String jobeduname;
    private String jobperson;
    private String publishtime;
    private String jobplace;
    private String jobddes;
    private String jobreq;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getComname() {
        return comname;
    }

    public void setComname(String comname) {
        this.comname = comname;
    }

    public String getComphone() {
        return comphone;
    }

    public void setComphone(String comphone) {
        this.comphone = comphone;
    }

    public String getComtypename() {
        return comtypename;
    }

    public void setComtypename(String comtypename) {
        this.comtypename = comtypename;
    }

    public String getComindustryname() {
        return comindustryname;
    }

    public void setComindustryname(String comindustryname) {
        this.comindustryname = comindustryname;
    }

    public String getJobtitle() {
        return jobtitle;
    }

    public void setJobtitle(String jobtitle) {
        this.jobtitle = jobtitle;
    }

    public String getJobtypename() {
        return jobtypename;
    }

    public void setJobtypename(String jobtypename) {
        this.jobtypename = jobtypename;
    }

    public String getJobmoney() {
        return jobmoney;
    }

    public void setJobmoney(String jobmoney) {
        this.jobmoney = jobmoney;
    }

    public String getJobexpname() {
        return jobexpname;
    }

    public void setJobexpname(String jobexpname) {
        this.jobexpname = jobexpname;
    }

    public String getJobeduname() {
        return jobeduname;
    }

    public void setJobeduname(String jobeduname) {
        this.jobeduname = jobeduname;
    }

    public String getJobperson() {
        return jobperson;
    }

    public void setJobperson(String jobperson) {
        this.jobperson = jobperson;
    }

    public String getPublishtime() {
        return publishtime;
    }

    public void setPublishtime(String publishtime) {
        this.publishtime = publishtime;
    }

    public String getJobplace() {
        return jobplace;
    }

    public void setJobplace(String jobplace) {
        this.jobplace = jobplace;
    }

    public String getJobddes() {
        return jobddes;
    }

    public void setJobddes(String jobddes) {
        this.jobddes = jobddes;
    }

    public String getJobreq() {
        return jobreq;
    }

    public void setJobreq(String jobreq) {
        this.jobreq = jobreq;
    }
}
