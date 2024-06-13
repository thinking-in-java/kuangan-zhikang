package com.yihe.applet.api.domain;

public class YhAwardMedal {
    private Integer id;

    private String img;

    private Integer grade;

    private String habId;

    private String meMane;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img == null ? null : img.trim();
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public String getHabId() {
        return habId;
    }

    public void setHabId(String habId) {
        this.habId = habId == null ? null : habId.trim();
    }

    public String getMeMane() {
        return meMane;
    }

    public void setMeMane(String meMane) {
        this.meMane = meMane == null ? null : meMane.trim();
    }
}