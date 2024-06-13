package com.yihe.system.domain;

public class TheBasicUse {
    //已经使用了有多少人
    private Integer usecount;
    //总共多少人
    private Integer allcount;
    //使用次数
    private Integer times;
    //总时间
    private String alltime;
    //机构
    private String noworg;
    //用户
    private String userCode;
    //开始时间
    private String bigantime;
    //结束时间
    private String endtime;

    public String getBigantime() {
        return bigantime;
    }

    public void setBigantime(String bigantime) {
        this.bigantime = bigantime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public Integer getUsecount() {
        return usecount;
    }

    public void setUsecount(Integer usecount) {
        this.usecount = usecount;
    }

    public Integer getAllcount() {
        return allcount;
    }

    public void setAllcount(Integer allcount) {
        this.allcount = allcount;
    }

    public Integer getTimes() {
        return times;
    }

    public void setTimes(Integer times) {
        this.times = times;
    }

    public String getAlltime() {
        return alltime;
    }

    public void setAlltime(String alltime) {
        this.alltime = alltime;
    }

    public String getNoworg() {
        return noworg;
    }

    public void setNoworg(String noworg) {
        this.noworg = noworg;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }
}
