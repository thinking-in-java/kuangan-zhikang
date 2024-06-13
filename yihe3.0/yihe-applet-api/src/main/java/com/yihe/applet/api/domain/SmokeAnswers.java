package com.yihe.applet.api.domain;

import org.exolab.castor.types.DateTime;

public class SmokeAnswers {
    //问卷答案的分数
    private Integer answers1;
    private Integer answers2;
    private Integer answers3;
    private Integer answers4;
    private Integer answers5;
    private Integer answers6;
    //评估时间
    private String qtime;
    private String user_code;
    //报告描述
    private String smoking_report_desc;
    //评估分数
    private Integer smoking_report_score;
    //报告等级
    private String smoking_report_level;
    //问卷答案
    private String smoking_report_queselect;

    public Integer getAnswers1() {
        return answers1;
    }

    public void setAnswers1(Integer answers1) {
        this.answers1 = answers1;
    }

    public Integer getAnswers2() {
        return answers2;
    }

    public void setAnswers2(Integer answers2) {
        this.answers2 = answers2;
    }

    public Integer getAnswers3() {
        return answers3;
    }

    public void setAnswers3(Integer answers3) {
        this.answers3 = answers3;
    }

    public Integer getAnswers4() {
        return answers4;
    }

    public void setAnswers4(Integer answers4) {
        this.answers4 = answers4;
    }

    public Integer getAnswers5() {
        return answers5;
    }

    public void setAnswers5(Integer answers5) {
        this.answers5 = answers5;
    }

    public Integer getAnswers6() {
        return answers6;
    }

    public void setAnswers6(Integer answers6) {
        this.answers6 = answers6;
    }

    public String getQtime() {
        return qtime;
    }

    public void setQtime(String qtime) {
        this.qtime = qtime;
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

    public Integer getSmoking_report_score() {
        return smoking_report_score;
    }

    public void setSmoking_report_score(Integer smoking_report_score) {
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
}
