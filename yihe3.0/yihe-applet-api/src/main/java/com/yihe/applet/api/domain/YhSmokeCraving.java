package com.yihe.applet.api.domain;

public class YhSmokeCraving {
    private Integer craving_id;
    private String craving_Time;
    private String usercode;
    private Integer dayyanying;
    private Integer allyanying;

    public Integer getCraving_id() {
        return craving_id;
    }

    public void setCraving_id(Integer craving_id) {
        this.craving_id = craving_id;
    }

    public String getCraving_Time() {
        return craving_Time;
    }

    public void setCraving_Time(String craving_Time) {
        this.craving_Time = craving_Time;
    }

    public String getUsercode() {
        return usercode;
    }

    public void setUsercode(String usercode) {
        this.usercode = usercode;
    }

    public Integer getDayyanying() {
        return dayyanying;
    }

    public void setDayyanying(Integer dayyanying) {
        this.dayyanying = dayyanying;
    }

    public Integer getAllyanying() {
        return allyanying;
    }

    public void setAllyanying(Integer allyanying) {
        this.allyanying = allyanying;
    }
}
