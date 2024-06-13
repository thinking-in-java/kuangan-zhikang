package com.yihe.applet.api.controller;

import java.util.List;

public class Yhhealthplan1 {
    private String day;
    private String num;
    private List<Yhhealthplan2> renwuList;

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public List<Yhhealthplan2> getRenwuList() {
        return renwuList;
    }

    public void setRenwuList(List<Yhhealthplan2> renwuList) {
        this.renwuList = renwuList;
    }
}
