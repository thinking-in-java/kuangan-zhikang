package com.yihe.system.domain;

public class YHUserRenalfunction {
    private Integer renalid;

    private String creatinine;

    private String ureaNitrogen;

    private String uricAcid;

    private Integer userId;

    private String inerttime;

    private String dataSource;

    private Integer isDel;

    public Integer getRenalid() {
        return renalid;
    }

    public void setRenalid(Integer renalid) {
        this.renalid = renalid;
    }

    public String getCreatinine() {
        return creatinine;
    }

    public void setCreatinine(String creatinine) {
        this.creatinine = creatinine == null ? null : creatinine.trim();
    }

    public String getUreaNitrogen() {
        return ureaNitrogen;
    }

    public void setUreaNitrogen(String ureaNitrogen) {
        this.ureaNitrogen = ureaNitrogen == null ? null : ureaNitrogen.trim();
    }

    public String getUricAcid() {
        return uricAcid;
    }

    public void setUricAcid(String uricAcid) {
        this.uricAcid = uricAcid == null ? null : uricAcid.trim();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getInerttime() {
        return inerttime;
    }

    public void setInerttime(String inerttime) {
        this.inerttime = inerttime == null ? null : inerttime.trim();
    }

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource == null ? null : dataSource.trim();
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }
}