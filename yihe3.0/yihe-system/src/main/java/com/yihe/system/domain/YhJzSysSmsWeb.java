package com.yihe.system.domain;

import java.util.Date;

public class YhJzSysSmsWeb {
    private Long smsId;

    private String templateId;

    private String smsname;

    private String signname;

    private String accesskey;

    private String accesskeysecret;

    private Integer smscodeNum;

    private Date smsCreate;

    private Date smsModified;

    private String templateType;

    private String templateText;
    
    private String templateGenre;

    private Integer type;

    
    
    public String getTemplateGenre() {
		return templateGenre;
	}

	public void setTemplateGenre(String templateGenre) {
		this.templateGenre = templateGenre;
	}

	public Long getSmsId() {
        return smsId;
    }

    public void setSmsId(Long smsId) {
        this.smsId = smsId;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId == null ? null : templateId.trim();
    }

    public String getSmsname() {
        return smsname;
    }

    public void setSmsname(String smsname) {
        this.smsname = smsname == null ? null : smsname.trim();
    }

    public String getSignname() {
        return signname;
    }

    public void setSignname(String signname) {
        this.signname = signname == null ? null : signname.trim();
    }

    public String getAccesskey() {
        return accesskey;
    }

    public void setAccesskey(String accesskey) {
        this.accesskey = accesskey == null ? null : accesskey.trim();
    }

    public String getAccesskeysecret() {
        return accesskeysecret;
    }

    public void setAccesskeysecret(String accesskeysecret) {
        this.accesskeysecret = accesskeysecret == null ? null : accesskeysecret.trim();
    }

    public Integer getSmscodeNum() {
        return smscodeNum;
    }

    public void setSmscodeNum(Integer smscodeNum) {
        this.smscodeNum = smscodeNum;
    }

    public Date getSmsCreate() {
        return smsCreate;
    }

    public void setSmsCreate(Date smsCreate) {
        this.smsCreate = smsCreate;
    }

    public Date getSmsModified() {
        return smsModified;
    }

    public void setSmsModified(Date smsModified) {
        this.smsModified = smsModified;
    }

    public String getTemplateType() {
        return templateType;
    }

    public void setTemplateType(String templateType) {
        this.templateType = templateType == null ? null : templateType.trim();
    }

    public String getTemplateText() {
        return templateText;
    }

    public void setTemplateText(String templateText) {
        this.templateText = templateText == null ? null : templateText.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}