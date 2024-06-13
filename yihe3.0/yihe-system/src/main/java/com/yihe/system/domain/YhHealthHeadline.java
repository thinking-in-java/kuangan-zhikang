package com.yihe.system.domain;

public class YhHealthHeadline {
    private Integer id;

    private String heTitle;

    private String heContent;

    private String heImg;

    private String heTime;

    private String heAuthor;

    private Integer heCount;

    private String standby;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHeTitle() {
        return heTitle;
    }

    public void setHeTitle(String heTitle) {
        this.heTitle = heTitle == null ? null : heTitle.trim();
    }

    public String getHeContent() {
        return heContent;
    }

    public void setHeContent(String heContent) {
        this.heContent = heContent == null ? null : heContent.trim();
    }

    public String getHeImg() {
        return heImg;
    }

    public void setHeImg(String heImg) {
        this.heImg = heImg == null ? null : heImg.trim();
    }

    public String getHeTime() {
        return heTime;
    }

    public void setHeTime(String heTime) {
        this.heTime = heTime == null ? null : heTime.trim();
    }

    public String getHeAuthor() {
        return heAuthor;
    }

    public void setHeAuthor(String heAuthor) {
        this.heAuthor = heAuthor == null ? null : heAuthor.trim();
    }

    public Integer getHeCount() {
        return heCount;
    }

    public void setHeCount(Integer heCount) {
        this.heCount = heCount;
    }

    public String getStandby() {
        return standby;
    }

    public void setStandby(String standby) {
        this.standby = standby == null ? null : standby.trim();
    }
}