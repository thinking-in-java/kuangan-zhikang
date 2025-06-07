package com.yihe.system.domain;

import java.util.Date;
import java.util.List;

public class NewListChronicDiseasePatients {
    private Integer id;

    private String jsonData;

    private Date isnertTime;

    private Integer begin;

    private Integer limit;

    private String lists;

    private String name;

    private String doctor;

    private String workUnit;


    public String getWorkUnit() {
        return workUnit;
    }

    public void setWorkUnit(String workUnit) {
        this.workUnit = workUnit;
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

    private List<String> listSelect;

    public String getLists() {
        return lists;
    }

    public void setLists(String lists) {
        this.lists = lists;
    }

    public List<String> getListSelect() {
        return listSelect;
    }

    public void setListSelect(List<String> listSelect) {
        this.listSelect = listSelect;
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

    public String getJsonData() {
        return jsonData;
    }

    public void setJsonData(String jsonData) {
        this.jsonData = jsonData == null ? null : jsonData.trim();
    }

    public Date getIsnertTime() {
        return isnertTime;
    }

    public void setIsnertTime(Date isnertTime) {
        this.isnertTime = isnertTime;
    }
}