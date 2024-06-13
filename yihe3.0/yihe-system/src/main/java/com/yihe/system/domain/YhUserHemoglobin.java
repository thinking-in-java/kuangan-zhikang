package com.yihe.system.domain;

import java.util.Date;

public class YhUserHemoglobin {
    private Integer id;

    private Float heHb;

    private String heTyep;

    private Date heData;

    private String timeStr;

    private String userCode;

    private String inserttime;

    private  String tiwen;

    private Boolean openNo;

    private String jiedu;

    public String getTimeStr() {
        return timeStr;
    }

    public void setTimeStr(String timeStr) {
        this.timeStr = timeStr;
    }

    public String getJiedu() {
        return jiedu;
    }

    public void setJiedu(String jiedu) {
        this.jiedu = jiedu;
    }

    public String getTiwen() {
        return tiwen;
    }

    public void setTiwen(String tiwen) {
        this.tiwen = tiwen;
    }

    public Boolean getOpenNo() {
        return openNo;
    }

    public void setOpenNo(Boolean openNo) {
        this.openNo = openNo;
    }

    public String getInserttime() {
        return inserttime;
    }

    public void setInserttime(String inserttime) {
        this.inserttime = inserttime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Float getHeHb() {
        return heHb;
    }

    public void setHeHb(Float heHb) {
        this.heHb = heHb;
    }

    public String getHeTyep() {
        return heTyep;
    }

    public void setHeTyep(String heTyep) {
        this.heTyep = heTyep == null ? null : heTyep.trim();
    }

    public Date getHeData() {
        return heData;
    }

    public void setHeData(Date heData) {
        this.heData = heData;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode == null ? null : userCode.trim();
    }
}