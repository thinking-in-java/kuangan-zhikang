package com.yihe.applet.api.domain;

public class YhFoodSrecipeApiNew {
    private Long id;

    private Integer fType;

    private Integer eLevel;

    private Integer isDel;

    private String diseaseid;

    private Integer oneself;

    private Integer status;

    private String fsDescribe;

    private String userCode;

    private String creationtime;

    private String fElements;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getfType() {
        return fType;
    }

    public void setfType(Integer fType) {
        this.fType = fType;
    }

    public Integer geteLevel() {
        return eLevel;
    }

    public void seteLevel(Integer eLevel) {
        this.eLevel = eLevel;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    public String getDiseaseid() {
        return diseaseid;
    }

    public void setDiseaseid(String diseaseid) {
        this.diseaseid = diseaseid;
    }

    public Integer getOneself() {
        return oneself;
    }

    public void setOneself(Integer oneself) {
        this.oneself = oneself;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getFsDescribe() {
        return fsDescribe;
    }

    public void setFsDescribe(String fsDescribe) {
        this.fsDescribe = fsDescribe == null ? null : fsDescribe.trim();
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode == null ? null : userCode.trim();
    }

    public String getCreationtime() {
        return creationtime;
    }

    public void setCreationtime(String creationtime) {
        this.creationtime = creationtime == null ? null : creationtime.trim();
    }

    public String getfElements() {
        return fElements;
    }

    public void setfElements(String fElements) {
        this.fElements = fElements == null ? null : fElements.trim();
    }
}