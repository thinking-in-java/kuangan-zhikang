package com.yihe.applet.api.domain;

import java.util.Date;

public class YhOrgTreeApp {
    private String orgCode;

    private String orgName;

    private String superOrgCode;

    private Integer isDel;

    private Date addDate;

    private Integer classNumber;

    private String userCode;

    private String docdorCode;

    private Integer orgTreeUploadId;

    private String orgCustomerCode;

    private String province;

    private String city;

    private String county;

    private String standby;

    private String orgNote;

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode == null ? null : orgCode.trim();
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName == null ? null : orgName.trim();
    }

    public String getSuperOrgCode() {
        return superOrgCode;
    }

    public void setSuperOrgCode(String superOrgCode) {
        this.superOrgCode = superOrgCode == null ? null : superOrgCode.trim();
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    public Date getAddDate() {
        return addDate;
    }

    public void setAddDate(Date addDate) {
        this.addDate = addDate;
    }

    public Integer getClassNumber() {
        return classNumber;
    }

    public void setClassNumber(Integer classNumber) {
        this.classNumber = classNumber;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode == null ? null : userCode.trim();
    }

    public String getDocdorCode() {
        return docdorCode;
    }

    public void setDocdorCode(String docdorCode) {
        this.docdorCode = docdorCode == null ? null : docdorCode.trim();
    }

    public Integer getOrgTreeUploadId() {
        return orgTreeUploadId;
    }

    public void setOrgTreeUploadId(Integer orgTreeUploadId) {
        this.orgTreeUploadId = orgTreeUploadId;
    }

    public String getOrgCustomerCode() {
        return orgCustomerCode;
    }

    public void setOrgCustomerCode(String orgCustomerCode) {
        this.orgCustomerCode = orgCustomerCode == null ? null : orgCustomerCode.trim();
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county == null ? null : county.trim();
    }

    public String getStandby() {
        return standby;
    }

    public void setStandby(String standby) {
        this.standby = standby == null ? null : standby.trim();
    }

    public String getOrgNote() {
        return orgNote;
    }

    public void setOrgNote(String orgNote) {
        this.orgNote = orgNote == null ? null : orgNote.trim();
    }
}