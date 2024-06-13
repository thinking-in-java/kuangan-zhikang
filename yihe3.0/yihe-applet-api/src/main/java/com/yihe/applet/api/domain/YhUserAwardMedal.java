package com.yihe.applet.api.domain;

public class YhUserAwardMedal {
    private Integer id;

    private String img;

    private Integer grade;

    private String name;

    private String userCode;

    private String habId;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode == null ? null : userCode.trim();
    }

    public String getHabId() {
        return habId;
    }

    public void setHabId(String habId) {
        this.habId = habId == null ? null : habId.trim();
    }
}