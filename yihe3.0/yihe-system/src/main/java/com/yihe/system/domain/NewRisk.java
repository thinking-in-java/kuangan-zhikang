package com.yihe.system.domain;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class NewRisk {
    private Integer id;

    private String gender;

    private String riskFrequency;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date riskTime;

    private String highrisk;

    private String beIll;

    private Date inserTime;

    private Integer begin;

    private Integer limit;

    private String name;

    private String doctor;

    private String userCode;

    private String workUnit;

    public String getWorkUnit() {
        return workUnit;
    }

    public void setWorkUnit(String workUnit) {
        this.workUnit = workUnit;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender == null ? null : gender.trim();
    }

    public String getRiskFrequency() {
        return riskFrequency;
    }

    public void setRiskFrequency(String riskFrequency) {
        this.riskFrequency = riskFrequency == null ? null : riskFrequency.trim();
    }

    public Date getRiskTime() {
        return riskTime;
    }

    public void setRiskTime(Date riskTime) {
        this.riskTime = riskTime;
    }

    public String getHighrisk() {
        return highrisk;
    }

    public void setHighrisk(String highrisk) {
        this.highrisk = highrisk == null ? null : highrisk.trim();
    }

    public String getBeIll() {
        return beIll;
    }

    public void setBeIll(String beIll) {
        this.beIll = beIll == null ? null : beIll.trim();
    }

    public Date getInserTime() {
        return inserTime;
    }

    public void setInserTime(Date inserTime) {
        this.inserTime = inserTime;
    }
}