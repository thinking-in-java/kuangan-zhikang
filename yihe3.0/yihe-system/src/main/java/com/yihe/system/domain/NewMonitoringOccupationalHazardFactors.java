package com.yihe.system.domain;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class NewMonitoringOccupationalHazardFactors {
    private Integer id;

    private String orgCode;

    private String stage;

    private String staffingPersonnel;

    private String entryName;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date preparationTime;

    private String fileNames;

    private String archiveNumber;

    private String archiveYear;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date archiveTime;

    private Date insertTime;

    private String file1;

    private String file2;

    private String file3;

    private String file4;

    private String file5;

    private String file6;

    private String file7;

    private String file8;

    private String file9;

    private Integer begin;

    private Integer limit;

    private String name;

    public String getFile2() {
        return file2;
    }

    public void setFile2(String file2) {
        this.file2 = file2;
    }

    public String getFile3() {
        return file3;
    }

    public void setFile3(String file3) {
        this.file3 = file3;
    }

    public String getFile4() {
        return file4;
    }

    public void setFile4(String file4) {
        this.file4 = file4;
    }

    public String getFile5() {
        return file5;
    }

    public void setFile5(String file5) {
        this.file5 = file5;
    }

    public String getFile6() {
        return file6;
    }

    public void setFile6(String file6) {
        this.file6 = file6;
    }

    public String getFile7() {
        return file7;
    }

    public void setFile7(String file7) {
        this.file7 = file7;
    }

    public String getFile8() {
        return file8;
    }

    public void setFile8(String file8) {
        this.file8 = file8;
    }

    public String getFile9() {
        return file9;
    }

    public void setFile9(String file9) {
        this.file9 = file9;
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

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage == null ? null : stage.trim();
    }

    public String getStaffingPersonnel() {
        return staffingPersonnel;
    }

    public void setStaffingPersonnel(String staffingPersonnel) {
        this.staffingPersonnel = staffingPersonnel == null ? null : staffingPersonnel.trim();
    }

    public String getEntryName() {
        return entryName;
    }

    public void setEntryName(String entryName) {
        this.entryName = entryName == null ? null : entryName.trim();
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