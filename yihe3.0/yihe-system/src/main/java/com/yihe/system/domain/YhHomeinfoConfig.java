package com.yihe.system.domain;

public class YhHomeinfoConfig {
    private Integer id;

    private Integer infoType;

    private String infoImg;

    private String infoTitle;

    private String infoTime;

    private Integer infoStatus;

    private Integer infoSource;

    private Integer infoContent;
    
    private Integer infoTip;
    
    

    public Integer getInfoTip() {
		return infoTip;
	}

	public void setInfoTip(Integer infoTip) {
		this.infoTip = infoTip;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getInfoType() {
        return infoType;
    }

    public void setInfoType(Integer infoType) {
        this.infoType = infoType;
    }

    public String getInfoImg() {
        return infoImg;
    }

    public void setInfoImg(String infoImg) {
        this.infoImg = infoImg == null ? null : infoImg.trim();
    }

    public String getInfoTitle() {
        return infoTitle;
    }

    public void setInfoTitle(String infoTitle) {
        this.infoTitle = infoTitle == null ? null : infoTitle.trim();
    }

    public String getInfoTime() {
        return infoTime;
    }

    public void setInfoTime(String infoTime) {
        this.infoTime = infoTime == null ? null : infoTime.trim();
    }

    public Integer getInfoStatus() {
        return infoStatus;
    }

    public void setInfoStatus(Integer infoStatus) {
        this.infoStatus = infoStatus;
    }

    public Integer getInfoSource() {
        return infoSource;
    }

    public void setInfoSource(Integer infoSource) {
        this.infoSource = infoSource;
    }

    public Integer getInfoContent() {
        return infoContent;
    }

    public void setInfoContent(Integer infoContent) {
        this.infoContent = infoContent;
    }
}