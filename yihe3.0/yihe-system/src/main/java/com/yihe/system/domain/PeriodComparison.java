package com.yihe.system.domain;

public class PeriodComparison {
    private String select1;//选择几个机构的字符串
    private Integer sex; //性别
    private String orgCode; // 机构id
    private Integer ageStart; // 年龄
    private Integer ageEnd; // 年龄
    private String befortime;//干预前总时间
    private String endtime;//干预后总时间
    private String startTime;
    private String endTime;
    private String meddleStartTime; //干预后 数据
    private String meddleEndTime;
    private Boolean rick; // 危险统计图
    private Boolean disease; // 疾病统计图
    private String begorberthday;//查询多少年前
    private String endberthday;//查询多少年的
    private String noworgcode;
    private String doctorcode;

    public String getDoctorcode() {
        return doctorcode;
    }

    public void setDoctorcode(String doctorcode) {
        this.doctorcode = doctorcode;
    }

    public String getNoworgcode() {
        return noworgcode;
    }

    public void setNoworgcode(String noworgcode) {
        this.noworgcode = noworgcode;
    }

    public String getSelect1() {
        return select1;
    }

    public void setSelect1(String select1) {
        this.select1 = select1;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public Integer getAgeStart() {
        return ageStart;
    }

    public void setAgeStart(Integer ageStart) {
        this.ageStart = ageStart;
    }

    public Integer getAgeEnd() {
        return ageEnd;
    }

    public void setAgeEnd(Integer ageEnd) {
        this.ageEnd = ageEnd;
    }

    public String getBefortime() {
        return befortime;
    }

    public void setBefortime(String befortime) {
        this.befortime = befortime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getMeddleStartTime() {
        return meddleStartTime;
    }

    public void setMeddleStartTime(String meddleStartTime) {
        this.meddleStartTime = meddleStartTime;
    }

    public String getMeddleEndTime() {
        return meddleEndTime;
    }

    public void setMeddleEndTime(String meddleEndTime) {
        this.meddleEndTime = meddleEndTime;
    }

    public Boolean getRick() {
        return rick;
    }

    public void setRick(Boolean rick) {
        this.rick = rick;
    }

    public Boolean getDisease() {
        return disease;
    }

    public void setDisease(Boolean disease) {
        this.disease = disease;
    }

    public String getBegorberthday() {
        return begorberthday;
    }

    public void setBegorberthday(String begorberthday) {
        this.begorberthday = begorberthday;
    }

    public String getEndberthday() {
        return endberthday;
    }

    public void setEndberthday(String endberthday) {
        this.endberthday = endberthday;
    }
}
