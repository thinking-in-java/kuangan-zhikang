package com.yihe.applet.api.domain;

import java.util.Date;

public class YhUserShippingAddressApp {
    private Integer siteId;

    private String userCode;

    private String consigneeName;

    private String consigneePhone;

    private String site;

    private String detailedSite;

    private Integer whetherDefault;

    private String creator;

    private String standby;

    private Date addDate;

    private Date updateDate;

    public Integer getSiteId() {
        return siteId;
    }

    public void setSiteId(Integer siteId) {
        this.siteId = siteId;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode == null ? null : userCode.trim();
    }

    public String getConsigneeName() {
        return consigneeName;
    }

    public void setConsigneeName(String consigneeName) {
        this.consigneeName = consigneeName == null ? null : consigneeName.trim();
    }

    public String getConsigneePhone() {
        return consigneePhone;
    }

    public void setConsigneePhone(String consigneePhone) {
        this.consigneePhone = consigneePhone == null ? null : consigneePhone.trim();
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site == null ? null : site.trim();
    }

    public String getDetailedSite() {
        return detailedSite;
    }

    public void setDetailedSite(String detailedSite) {
        this.detailedSite = detailedSite == null ? null : detailedSite.trim();
    }

    public Integer getWhetherDefault() {
        return whetherDefault;
    }

    public void setWhetherDefault(Integer whetherDefault) {
        this.whetherDefault = whetherDefault;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    public String getStandby() {
        return standby;
    }

    public void setStandby(String standby) {
        this.standby = standby == null ? null : standby.trim();
    }

    public Date getAddDate() {
        return addDate;
    }

    public void setAddDate(Date addDate) {
        this.addDate = addDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}