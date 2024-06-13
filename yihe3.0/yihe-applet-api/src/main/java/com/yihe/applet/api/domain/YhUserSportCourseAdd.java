package com.yihe.applet.api.domain;

import java.util.Date;

public class YhUserSportCourseAdd {
    private Integer addId;

    private String userCode;

    private Integer courseId;

    private Date addTime;

    private String finishid;

    private Integer addType;

    private String standby;

    private String addNum;

    public Integer getAddId() {
        return addId;
    }

    public void setAddId(Integer addId) {
        this.addId = addId;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode == null ? null : userCode.trim();
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public String getFinishid() {
        return finishid;
    }

    public void setFinishid(String finishid) {
        this.finishid = finishid == null ? null : finishid.trim();
    }

    public Integer getAddType() {
        return addType;
    }

    public void setAddType(Integer addType) {
        this.addType = addType;
    }

    public String getStandby() {
        return standby;
    }

    public void setStandby(String standby) {
        this.standby = standby == null ? null : standby.trim();
    }

    public String getAddNum() {
        return addNum;
    }

    public void setAddNum(String addNum) {
        this.addNum = addNum == null ? null : addNum.trim();
    }
}