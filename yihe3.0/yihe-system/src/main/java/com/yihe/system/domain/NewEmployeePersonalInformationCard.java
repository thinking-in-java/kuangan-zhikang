package com.yihe.system.domain;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

public class NewEmployeePersonalInformationCard {
    private Integer id;

    private String userCode;

    private String name;

    private String sex;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birth;

    private String nameUsedBefore;

    private String maritalStatus;

    private String degreeEducation;

    private String nativePlace;

    private String myPhoneNumber;

    private String hobby;

    private String idNumber;

    private String occupationalHistoryExposureHistoryToOccupationalHazards;

    private String textJson;

    private String menstrualReproductiveHistory;

    private String testingResultsOccupationalHazardsInWorkplace;

    private String previousOccupationalHealthExaminationResults;

    private String sign;

    private String noticeOccupationalHealthExaminationResults;

    private String occupationalHealthExamination1;

    private String occupationalHealthExamination2;

    private String occupationalHealthExamination3;

    private String occupationalHealthExamination4;

    private String examinationResults;

    private String examinationResultsSign;

    private String examinationResultsFile;

    private String regardingMaterials;

    private String notes;

    private String other;

    private String photo;

    private Integer begin;

    private Integer limit;

    private String time;

    private Integer regId;

    private String v888;

    private String department;

    private Integer[] ids;

    private String team;

    private String orgCode;

    private String times;

    private String startTime;

    private String endTime;

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
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

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getTeam() {
        return team;
    }


    public void setTeam(String team) {
        this.team = team;
    }

    public Integer[] getIds() {
        return ids;
    }

    public void setIds(Integer[] ids) {
        this.ids = ids;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getV888() {
        return v888;
    }

    public void setV888(String v888) {
        this.v888 = v888;
    }

    public Integer getRegId() {
        return regId;
    }

    public void setRegId(Integer regId) {
        this.regId = regId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode == null ? null : userCode.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public String getNameUsedBefore() {
        return nameUsedBefore;
    }

    public void setNameUsedBefore(String nameUsedBefore) {
        this.nameUsedBefore = nameUsedBefore == null ? null : nameUsedBefore.trim();
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus == null ? null : maritalStatus.trim();
    }

    public String getDegreeEducation() {
        return degreeEducation;
    }

    public void setDegreeEducation(String degreeEducation) {
        this.degreeEducation = degreeEducation == null ? null : degreeEducation.trim();
    }

    public String getNativePlace() {
        return nativePlace;
    }

    public void setNativePlace(String nativePlace) {
        this.nativePlace = nativePlace == null ? null : nativePlace.trim();
    }

    public String getMyPhoneNumber() {
        return myPhoneNumber;
    }

    public void setMyPhoneNumber(String myPhoneNumber) {
        this.myPhoneNumber = myPhoneNumber;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby == null ? null : hobby.trim();
    }



    public String getOccupationalHistoryExposureHistoryToOccupationalHazards() {
        return occupationalHistoryExposureHistoryToOccupationalHazards;
    }

    public void setOccupationalHistoryExposureHistoryToOccupationalHazards(String occupationalHistoryExposureHistoryToOccupationalHazards) {
        this.occupationalHistoryExposureHistoryToOccupationalHazards = occupationalHistoryExposureHistoryToOccupationalHazards == null ? null : occupationalHistoryExposureHistoryToOccupationalHazards.trim();
    }

    public String getTextJson() {
        return textJson;
    }

    public void setTextJson(String textJson) {
        this.textJson = textJson == null ? null : textJson.trim();
    }

    public String getMenstrualReproductiveHistory() {
        return menstrualReproductiveHistory;
    }

    public void setMenstrualReproductiveHistory(String menstrualReproductiveHistory) {
        this.menstrualReproductiveHistory = menstrualReproductiveHistory == null ? null : menstrualReproductiveHistory.trim();
    }

    public String getTestingResultsOccupationalHazardsInWorkplace() {
        return testingResultsOccupationalHazardsInWorkplace;
    }

    public void setTestingResultsOccupationalHazardsInWorkplace(String testingResultsOccupationalHazardsInWorkplace) {
        this.testingResultsOccupationalHazardsInWorkplace = testingResultsOccupationalHazardsInWorkplace == null ? null : testingResultsOccupationalHazardsInWorkplace.trim();
    }

    public String getPreviousOccupationalHealthExaminationResults() {
        return previousOccupationalHealthExaminationResults;
    }

    public void setPreviousOccupationalHealthExaminationResults(String previousOccupationalHealthExaminationResults) {
        this.previousOccupationalHealthExaminationResults = previousOccupationalHealthExaminationResults == null ? null : previousOccupationalHealthExaminationResults.trim();
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign == null ? null : sign.trim();
    }

    public String getNoticeOccupationalHealthExaminationResults() {
        return noticeOccupationalHealthExaminationResults;
    }

    public void setNoticeOccupationalHealthExaminationResults(String noticeOccupationalHealthExaminationResults) {
        this.noticeOccupationalHealthExaminationResults = noticeOccupationalHealthExaminationResults == null ? null : noticeOccupationalHealthExaminationResults.trim();
    }

    public String getOccupationalHealthExamination1() {
        return occupationalHealthExamination1;
    }

    public void setOccupationalHealthExamination1(String occupationalHealthExamination1) {
        this.occupationalHealthExamination1 = occupationalHealthExamination1 == null ? null : occupationalHealthExamination1.trim();
    }

    public String getOccupationalHealthExamination2() {
        return occupationalHealthExamination2;
    }

    public void setOccupationalHealthExamination2(String occupationalHealthExamination2) {
        this.occupationalHealthExamination2 = occupationalHealthExamination2 == null ? null : occupationalHealthExamination2.trim();
    }

    public String getOccupationalHealthExamination3() {
        return occupationalHealthExamination3;
    }

    public void setOccupationalHealthExamination3(String occupationalHealthExamination3) {
        this.occupationalHealthExamination3 = occupationalHealthExamination3 == null ? null : occupationalHealthExamination3.trim();
    }

    public String getOccupationalHealthExamination4() {
        return occupationalHealthExamination4;
    }

    public void setOccupationalHealthExamination4(String occupationalHealthExamination4) {
        this.occupationalHealthExamination4 = occupationalHealthExamination4 == null ? null : occupationalHealthExamination4.trim();
    }

    public String getExaminationResults() {
        return examinationResults;
    }

    public void setExaminationResults(String examinationResults) {
        this.examinationResults = examinationResults == null ? null : examinationResults.trim();
    }

    public String getExaminationResultsSign() {
        return examinationResultsSign;
    }

    public void setExaminationResultsSign(String examinationResultsSign) {
        this.examinationResultsSign = examinationResultsSign == null ? null : examinationResultsSign.trim();
    }

    public String getExaminationResultsFile() {
        return examinationResultsFile;
    }

    public void setExaminationResultsFile(String examinationResultsFile) {
        this.examinationResultsFile = examinationResultsFile == null ? null : examinationResultsFile.trim();
    }

    public String getRegardingMaterials() {
        return regardingMaterials;
    }

    public void setRegardingMaterials(String regardingMaterials) {
        this.regardingMaterials = regardingMaterials == null ? null : regardingMaterials.trim();
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes == null ? null : notes.trim();
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other == null ? null : other.trim();
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo == null ? null : photo.trim();
    }
}