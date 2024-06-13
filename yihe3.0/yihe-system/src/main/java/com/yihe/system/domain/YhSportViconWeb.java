package com.yihe.system.domain;

public class YhSportViconWeb {
    private Integer viconId;

    private String viconType;

    private String viconVideo;

    private String viconName;

    private String viconImg;

    private String viconStrength;

    private String viconContent;

    private String viconExplainImg;

    private String viconMuscleImg;

    private Integer isDel;

    public Integer getViconId() {
        return viconId;
    }

    public void setViconId(Integer viconId) {
        this.viconId = viconId;
    }

    public String getViconType() {
        return viconType;
    }

    public void setViconType(String viconType) {
        this.viconType = viconType == null ? null : viconType.trim();
    }

    public String getViconVideo() {
        return viconVideo;
    }

    public void setViconVideo(String viconVideo) {
        this.viconVideo = viconVideo == null ? null : viconVideo.trim();
    }

    public String getViconName() {
        return viconName;
    }

    public void setViconName(String viconName) {
        this.viconName = viconName == null ? null : viconName.trim();
    }

    public String getViconImg() {
        return viconImg;
    }

    public void setViconImg(String viconImg) {
        this.viconImg = viconImg == null ? null : viconImg.trim();
    }

    public String getViconStrength() {
        return viconStrength;
    }

    public void setViconStrength(String viconStrength) {
        this.viconStrength = viconStrength == null ? null : viconStrength.trim();
    }

    public String getViconContent() {
        return viconContent;
    }

    public void setViconContent(String viconContent) {
        this.viconContent = viconContent == null ? null : viconContent.trim();
    }

    public String getViconExplainImg() {
        return viconExplainImg;
    }

    public void setViconExplainImg(String viconExplainImg) {
        this.viconExplainImg = viconExplainImg == null ? null : viconExplainImg.trim();
    }

    public String getViconMuscleImg() {
        return viconMuscleImg;
    }

    public void setViconMuscleImg(String viconMuscleImg) {
        this.viconMuscleImg = viconMuscleImg == null ? null : viconMuscleImg.trim();
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }
}