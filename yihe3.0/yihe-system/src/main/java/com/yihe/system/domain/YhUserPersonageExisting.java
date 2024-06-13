package com.yihe.system.domain;

public class YhUserPersonageExisting {
    private Integer id;

    private String userCode;

    private String bloodvessel;

    private String kidney;

    private String heart;

    private String blooddisease;

    private String ocular;

    private String rests;

    private String nervoussystem;

    private Integer pid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode == null ? null : userCode.trim();
    }

    public String getBloodvessel() {
        return bloodvessel;
    }

    public void setBloodvessel(String bloodvessel) {
        this.bloodvessel = bloodvessel == null ? null : bloodvessel.trim();
    }

    public String getKidney() {
        return kidney;
    }

    public void setKidney(String kidney) {
        this.kidney = kidney == null ? null : kidney.trim();
    }

    public String getHeart() {
        return heart;
    }

    public void setHeart(String heart) {
        this.heart = heart == null ? null : heart.trim();
    }

    public String getBlooddisease() {
        return blooddisease;
    }

    public void setBlooddisease(String blooddisease) {
        this.blooddisease = blooddisease == null ? null : blooddisease.trim();
    }

    public String getOcular() {
        return ocular;
    }

    public void setOcular(String ocular) {
        this.ocular = ocular == null ? null : ocular.trim();
    }

    public String getRests() {
        return rests;
    }

    public void setRests(String rests) {
        this.rests = rests == null ? null : rests.trim();
    }

    public String getNervoussystem() {
        return nervoussystem;
    }

    public void setNervoussystem(String nervoussystem) {
        this.nervoussystem = nervoussystem == null ? null : nervoussystem.trim();
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }
}