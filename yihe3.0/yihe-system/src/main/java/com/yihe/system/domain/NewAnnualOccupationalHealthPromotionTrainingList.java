package com.yihe.system.domain;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class NewAnnualOccupationalHealthPromotionTrainingList {
    private Integer id;

    private String orgCode;

    private String staffingPersonnel;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date preparationTime;

    private String fileNames;

    private String archiveNumber;

    private String archiveYear;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date archiveTime;

    private Date insertTime;

    private String file1;

    private Integer begin;

    private Integer limit;

    private String name;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode == null ? null : orgCode.trim();
    }

    public String getStaffingPersonnel() {
        return staffingPersonnel;
    }

    public void setStaffingPersonnel(String staffingPersonnel) {
        this.staffingPersonnel = staffingPersonnel == null ? null : staffingPersonnel.trim();
    }

    public Date getPreparationTime() {
        return preparationTime;
    }

    public void setPreparationTime(Date preparationTime) {
        this.preparationTime = preparationTime;
    }

    public String getFileNames() {
        return fileNames;
    }

    public void setFileNames(String fileNames) {
        this.fileNames = fileNames == null ? null : fileNames.trim();
    }

    public String getArchiveNumber() {
        return archiveNumber;
    }

    public void setArchiveNumber(String archiveNumber) {
        this.archiveNumber = archiveNumber == null ? null : archiveNumber.trim();
    }

    public String getArchiveYear() {
        return archiveYear;
    }

    public void setArchiveYear(String archiveYear) {
        this.archiveYear = archiveYear == null ? null : archiveYear.trim();
    }

    public Date getArchiveTime() {
        return archiveTime;
    }

    public void setArchiveTime(Date archiveTime) {
        this.archiveTime = archiveTime;
    }

    public Date getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }

    public String getFile1() {
        return file1;
    }

    public void setFile1(String file1) {
        this.file1 = file1 == null ? null : file1.trim();
    }
}