package com.yihe.system.domain;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class NewPsychologicalAssessmentReport {
    private Integer id;

    private String regId;

    private String name;

    private String phone;

    private Integer numberEvaluations;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date latestEvaluationDate;

    private String evaluationResults;

    private Date insertTime;

    private Integer begin;

    private Integer limit;

    private String healthManagementSpecialist;

    public String getHealthManagementSpecialist() {
        return healthManagementSpecialist;
    }

    public void setHealthManagementSpecialist(String healthManagementSpecialist) {
        this.healthManagementSpecialist = healthManagementSpecialist;
    }

    public Integer getBegin() {
        return begin;
    }

    public void setBegin(Integer begin) {
        this.begin = begin;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRegId() {
        return regId;
    }

    public void setRegId(String regId) {
        this.regId = regId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public Integer getNumberEvaluations() {
        return numberEvaluations;
    }

    public void setNumberEvaluations(Integer numberEvaluations) {
        this.numberEvaluations = numberEvaluations;
    }

    public Date getLatestEvaluationDate() {
        return latestEvaluationDate;
    }

    public void setLatestEvaluationDate(Date latestEvaluationDate) {
        this.latestEvaluationDate = latestEvaluationDate;
    }

    public String getEvaluationResults() {
        return evaluationResults;
    }

    public void setEvaluationResults(String evaluationResults) {
        this.evaluationResults = evaluationResults == null ? null : evaluationResults.trim();
    }

    public Date getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }
}