package com.yihe.applet.api.domain;

import java.util.List;

public class YhChineseAnwser4 {
    private String name;
    private String tizhi;
    private String state;
    private List<YhChineseAnwser5> zong;
    private List<YhChineseAnwser5> zhong;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTizhi() {
        return tizhi;
    }

    public void setTizhi(String tizhi) {
        this.tizhi = tizhi;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List<YhChineseAnwser5> getZong() {
        return zong;
    }

    public void setZong(List<YhChineseAnwser5> zong) {
        this.zong = zong;
    }

    public List<YhChineseAnwser5> getZhong() {
        return zhong;
    }

    public void setZhong(List<YhChineseAnwser5> zhong) {
        this.zhong = zhong;
    }
}
