package com.yihe.applet.api.domain;

public class YhSmokingReport {
    private int smoking_report_id;
    private String user_code;
    private String smoking_report_desc;
    private int smoking_report_score;
    private String smoking_report_level;
    private String smoking_report_queselect;
    private String qtime;

    public int getSmoking_report_id() {
        return smoking_report_id;
    }

    public void setSmoking_report_id(int smoking_report_id) {
        this.smoking_report_id = smoking_report_id;
    }

    public String getUser_code() {
        return user_code;
    }

    public void setUser_code(String user_code) {
        this.user_code = user_code;
    }

    public String getSmoking_report_desc() {
        return smoking_report_desc;
    }

    public void setSmoking_report_desc(String smoking_report_desc) {
        this.smoking_report_desc = smoking_report_desc;
    }

    public int getSmoking_report_score() {
        return smoking_report_score;
    }

    public void setSmoking_report_score(int smoking_report_score) {
        this.smoking_report_score = smoking_report_score;
    }

    public String getSmoking_report_level() {
        return smoking_report_level;
    }

    public void setSmoking_report_level(String smoking_report_level) {
        this.smoking_report_level = smoking_report_level;
    }

    public String getSmoking_report_queselect() {
        return smoking_report_queselect;
    }

    public void setSmoking_report_queselect(String smoking_report_queselect) {
        this.smoking_report_queselect = smoking_report_queselect;
    }

    public String getQtime() {
        return qtime;
    }

    public void setQtime(String qtime) {
        this.qtime = qtime;
    }
}
