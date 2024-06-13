package com.yihe.applet.api.domain;

public class YhWxInformationApp {
    private Integer wxId;

    private String gzAppid;

    private String gzAppsecret;

    private String xcAppid;

    private String xcAppsecret;

    private String boardId;

    private String variate1;

    private String variate2;

    private String variate3;

    private String variate4;

    private String variate5;

    private String wxType;

    private String mbName;
    
    private String infoType;
    
    

    

	public String getInfoType() {
		return infoType;
	}

	public void setInfoType(String infoType) {
		this.infoType = infoType;
	}

	public Integer getWxId() {
        return wxId;
    }

    public void setWxId(Integer wxId) {
        this.wxId = wxId;
    }

    public String getGzAppid() {
        return gzAppid;
    }

    public void setGzAppid(String gzAppid) {
        this.gzAppid = gzAppid == null ? null : gzAppid.trim();
    }

    public String getGzAppsecret() {
        return gzAppsecret;
    }

    public void setGzAppsecret(String gzAppsecret) {
        this.gzAppsecret = gzAppsecret == null ? null : gzAppsecret.trim();
    }

    public String getXcAppid() {
        return xcAppid;
    }

    public void setXcAppid(String xcAppid) {
        this.xcAppid = xcAppid == null ? null : xcAppid.trim();
    }

    public String getXcAppsecret() {
        return xcAppsecret;
    }

    public void setXcAppsecret(String xcAppsecret) {
        this.xcAppsecret = xcAppsecret == null ? null : xcAppsecret.trim();
    }

    public String getBoardId() {
        return boardId;
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId == null ? null : boardId.trim();
    }

    public String getVariate1() {
        return variate1;
    }

    public void setVariate1(String variate1) {
        this.variate1 = variate1 == null ? null : variate1.trim();
    }

    public String getVariate2() {
        return variate2;
    }

    public void setVariate2(String variate2) {
        this.variate2 = variate2 == null ? null : variate2.trim();
    }

    public String getVariate3() {
        return variate3;
    }

    public void setVariate3(String variate3) {
        this.variate3 = variate3 == null ? null : variate3.trim();
    }

    public String getVariate4() {
        return variate4;
    }

    public void setVariate4(String variate4) {
        this.variate4 = variate4 == null ? null : variate4.trim();
    }

    public String getVariate5() {
        return variate5;
    }

    public void setVariate5(String variate5) {
        this.variate5 = variate5 == null ? null : variate5.trim();
    }

    public String getWxType() {
        return wxType;
    }

    public void setWxType(String wxType) {
        this.wxType = wxType == null ? null : wxType.trim();
    }

    public String getMbName() {
        return mbName;
    }

    public void setMbName(String mbName) {
        this.mbName = mbName == null ? null : mbName.trim();
    }
}