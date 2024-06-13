package com.yihe.system.domain;

public class JiaiWatchInfo {
    private String terminalid;

    private Integer id;

    private Integer gpsflag;

    private String positinterval;

    private String sosname1;

    private String mobile1;

    private String sosname2;

    private String mobile2;

    private String sosname3;

    private String mobile3;

    private String heartinterval;

    private String bloodpressureinterval;

    private String temperaturepressureinterval;

    public String getTerminalid() {
        return terminalid;
    }

    public void setTerminalid(String terminalid) {
        this.terminalid = terminalid == null ? null : terminalid.trim();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGpsflag() {
        return gpsflag;
    }

    public void setGpsflag(Integer gpsflag) {
        this.gpsflag = gpsflag;
    }

    public String getPositinterval() {
        return positinterval;
    }

    public void setPositinterval(String positinterval) {
        this.positinterval = positinterval == null ? null : positinterval.trim();
    }

    public String getSosname1() {
        return sosname1;
    }

    public void setSosname1(String sosname1) {
        this.sosname1 = sosname1 == null ? null : sosname1.trim();
    }

    public String getMobile1() {
        return mobile1;
    }

    public void setMobile1(String mobile1) {
        this.mobile1 = mobile1 == null ? null : mobile1.trim();
    }

    public String getSosname2() {
        return sosname2;
    }

    public void setSosname2(String sosname2) {
        this.sosname2 = sosname2 == null ? null : sosname2.trim();
    }

    public String getMobile2() {
        return mobile2;
    }

    public void setMobile2(String mobile2) {
        this.mobile2 = mobile2 == null ? null : mobile2.trim();
    }

    public String getSosname3() {
        return sosname3;
    }

    public void setSosname3(String sosname3) {
        this.sosname3 = sosname3 == null ? null : sosname3.trim();
    }

    public String getMobile3() {
        return mobile3;
    }

    public void setMobile3(String mobile3) {
        this.mobile3 = mobile3 == null ? null : mobile3.trim();
    }

    public String getHeartinterval() {
        return heartinterval;
    }

    public void setHeartinterval(String heartinterval) {
        this.heartinterval = heartinterval == null ? null : heartinterval.trim();
    }

    public String getBloodpressureinterval() {
        return bloodpressureinterval;
    }

    public void setBloodpressureinterval(String bloodpressureinterval) {
        this.bloodpressureinterval = bloodpressureinterval == null ? null : bloodpressureinterval.trim();
    }

    public String getTemperaturepressureinterval() {
        return temperaturepressureinterval;
    }

    public void setTemperaturepressureinterval(String temperaturepressureinterval) {
        this.temperaturepressureinterval = temperaturepressureinterval == null ? null : temperaturepressureinterval.trim();
    }
}