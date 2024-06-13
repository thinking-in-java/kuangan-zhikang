package com.yihe.system.domain;

public class BasicUser {
    private String xiangmu;
    private String[] xaingmu;
    private String times;
    private String yonghu;
    private String orgcode;
    private Integer table;
    private String begantime;
    private String endtime;

    public String getYonghu() {
        return yonghu;
    }

    public String[] getXaingmu() {
        return xaingmu;
    }

    public String getXiangmu() {
        return xiangmu;
    }

    public void setXiangmu(String xiangmu) {
        this.xiangmu = xiangmu;
    }

    public void setXaingmu(String[] xaingmu) {
        this.xaingmu = xaingmu;
    }

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }

    public void setYonghu(String yonghu) {
        this.yonghu = yonghu;
    }


    public String getOrgcode() {
        return orgcode;
    }

    public void setOrgcode(String orgcode) {
        this.orgcode = orgcode;
    }

    public Integer getTable() {
        return table;
    }

    public void setTable(Integer table) {
        this.table = table;
    }

    public String getBegantime() {
        return begantime;
    }

    public void setBegantime(String begantime) {
        this.begantime = begantime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }
}
