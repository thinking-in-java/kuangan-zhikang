package com.yihe.applet.api.domain;

public class YhSportCategoryApp {
    private Integer id;

    private String cateName;

    private Integer cateTimes;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCateName() {
        return cateName;
    }

    public void setCateName(String cateName) {
        this.cateName = cateName == null ? null : cateName.trim();
    }

    public Integer getCateTimes() {
        return cateTimes;
    }

    public void setCateTimes(Integer cateTimes) {
        this.cateTimes = cateTimes;
    }
}