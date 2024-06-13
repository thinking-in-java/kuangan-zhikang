package com.yihe.applet.api.domain;

public class YhUserPersonageNonimmuneApp {
    private Integer id;

    private String userCode;

    private String immunename;

    private String organization;

    private String timedate;

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

    public String getImmunename() {
        return immunename;
    }

    public void setImmunename(String immunename) {
        this.immunename = immunename == null ? null : immunename.trim();
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization == null ? null : organization.trim();
    }

    public String getTimedate() {
        return timedate;
    }

    public void setTimedate(String timedate) {
        this.timedate = timedate == null ? null : timedate.trim();
    }
}