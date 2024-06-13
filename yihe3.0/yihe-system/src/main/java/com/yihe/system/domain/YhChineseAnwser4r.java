package com.yihe.system.domain;

import java.util.List;

public class YhChineseAnwser4r {
    private String name;
    private String tizhi;
    private String state;
    private List<YhChineseAnwser5r> zong;
    private List<YhChineseAnwser5r> zhong;

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

    public List<YhChineseAnwser5r> getZong() {
        return zong;
    }

    public void setZong(List<YhChineseAnwser5r> zong) {
        this.zong = zong;
    }

    public List<YhChineseAnwser5r> getZhong() {
        return zhong;
    }

    public void setZhong(List<YhChineseAnwser5r> zhong) {
        this.zhong = zhong;
    }
}
