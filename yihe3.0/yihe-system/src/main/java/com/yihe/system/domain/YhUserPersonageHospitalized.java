package com.yihe.system.domain;

public class YhUserPersonageHospitalized {
    private Integer id;

    private String userCode;

    private String timedeta;

    private String cause;

    private String dischargetime;

    private String yiyuanname;

    private String disease;

    private String hospitalizedtype;

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

    public String getTimedeta() {
        return timedeta;
    }

    public void setTimedeta(String timedeta) {
        this.timedeta = timedeta == null ? null : timedeta.trim();
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause == null ? null : cause.trim();
    }

    public String getDischargetime() {
        return dischargetime;
    }

    public void setDischargetime(String dischargetime) {
        this.dischargetime = dischargetime == null ? null : dischargetime.trim();
    }

    public String getYiyuanname() {
        return yiyuanname;
    }

    public void setYiyuanname(String yiyuanname) {
        this.yiyuanname = yiyuanname == null ? null : yiyuanname.trim();
    }

    public String getDisease() {
        return disease;
    }

    public void setDisease(String disease) {
        this.disease = disease == null ? null : disease.trim();
    }

    public String getHospitalizedtype() {
        return hospitalizedtype;
    }

    public void setHospitalizedtype(String hospitalizedtype) {
        this.hospitalizedtype = hospitalizedtype == null ? null : hospitalizedtype.trim();
    }
}