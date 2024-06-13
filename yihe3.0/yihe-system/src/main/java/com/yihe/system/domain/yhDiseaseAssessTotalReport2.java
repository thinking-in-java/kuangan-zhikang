package com.yihe.system.domain;

import java.util.Date;
import java.util.List;

public class yhDiseaseAssessTotalReport2 {
    private Integer id;

    private String requestSerialnum;

    private String userCode;

    private String qtime;

    private Integer gender;

    private Integer age;

    private String userName;

    private Integer times;

    private String dfNames;

    private String classify;
    private  String orgname;
    private List<YhDiseaseRiskResult2> yhDiseaseRiskResult2s;
    private List<YhExportAssessReport> yhExportAssessReports;

    public String getOrgname() {
        return orgname;
    }

    public void setOrgname(String orgname) {
        this.orgname = orgname;
    }

    public List<YhExportAssessReport> getYhExportAssessReports() {
        return yhExportAssessReports;
    }

    public void setYhExportAssessReports(List<YhExportAssessReport> yhExportAssessReports) {
        this.yhExportAssessReports = yhExportAssessReports;
    }

    public List<YhDiseaseRiskResult2> getYhDiseaseRiskResult2s() {
        return yhDiseaseRiskResult2s;
    }

    public void setYhDiseaseRiskResult2s(List<YhDiseaseRiskResult2> yhDiseaseRiskResult2s) {
        this.yhDiseaseRiskResult2s = yhDiseaseRiskResult2s;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRequestSerialnum() {
        return requestSerialnum;
    }

    public void setRequestSerialnum(String requestSerialnum) {
        this.requestSerialnum = requestSerialnum == null ? null : requestSerialnum.trim();
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode == null ? null : userCode.trim();
    }

    public String getQtime() {
        return qtime;
    }

    public void setQtime(String qtime) {
        this.qtime = qtime;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public Integer getTimes() {
        return times;
    }

    public void setTimes(Integer times) {
        this.times = times;
    }

    public String getDfNames() {
        return dfNames;
    }

    public void setDfNames(String dfNames) {
        this.dfNames = dfNames == null ? null : dfNames.trim();
    }

    public String getClassify() {
        return classify;
    }

    public void setClassify(String classify) {
        this.classify = classify == null ? null : classify.trim();
    }
}