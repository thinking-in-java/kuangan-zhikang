package com.yihe.system.domain;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


public class newReg {
    private Integer id;

    private String name;

    private String userCode;

    private Integer sex;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birth;

    private String workUnit;

    private String department;

    private String rogCode;

    private String idCard;

    private String phone;

    private String bloodType;

    private String marriage;

    private String historyDrugAllergies;

    private String exposureHistory;

    private String disabilitySituation;

    private String kitchenExhaustFacilities;

    private String fuelType;

    private String drinkingWater;

    private String toilet;

    private String poultryLivestockPens;

    private String responsibleHealthManagerCode;

    private String archivist;

    private Date insertDate;

    private Integer age;

    private String nation;

    private String culture;

    private String v666;

    private String v777;

    private String v888;

    private String Times;

    private String beginTime;

    private String endTime;

    private String qtime;

    private String dfNames;

    private String skNames;

    private String dname;

    private String interventionTime;

    public String getV888() {
        return v888;
    }

    public void setV888(String v888) {
        this.v888 = v888;
    }

    public String getInterventionTime() {
        return interventionTime;
    }

    public void setInterventionTime(String interventionTime) {
        this.interventionTime = interventionTime;
    }

    public String getDname() {
        return dname;
    }

    public void setDname(String dname) {
        this.dname = dname;
    }

    public String getQtime() {
        return qtime;
    }

    public void setQtime(String qtime) {
        this.qtime = qtime;
    }

    public String getDfNames() {
        return dfNames;
    }

    public void setDfNames(String dfNames) {
        this.dfNames = dfNames;
    }

    public String getSkNames() {
        return skNames;
    }

    public void setSkNames(String skNames) {
        this.skNames = skNames;
    }

    public String getTimes() {
        return Times;
    }

    public void setTimes(String times) {
        Times = times;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getV666() {
        return v666;
    }

    public void setV666(String v666) {
        this.v666 = v666;
    }

    public String getV777() {
        return v777;
    }

    public void setV777(String v777) {
        this.v777 = v777;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getCulture() {
        return culture;
    }

    public void setCulture(String culture) {
        this.culture = culture;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode == null ? null : userCode.trim();
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public String getWorkUnit() {
        return workUnit;
    }

    public void setWorkUnit(String workUnit) {
        this.workUnit = workUnit == null ? null : workUnit.trim();
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department == null ? null : department.trim();
    }

    public String getRogCode() {
        return rogCode;
    }

    public void setRogCode(String rogCode) {
        this.rogCode = rogCode == null ? null : rogCode.trim();
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard == null ? null : idCard.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType == null ? null : bloodType.trim();
    }

    public String getMarriage() {
        return marriage;
    }

    public void setMarriage(String marriage) {
        this.marriage = marriage == null ? null : marriage.trim();
    }

    public String getHistoryDrugAllergies() {
        return historyDrugAllergies;
    }

    public void setHistoryDrugAllergies(String historyDrugAllergies) {
        this.historyDrugAllergies = historyDrugAllergies == null ? null : historyDrugAllergies.trim();
    }

    public String getExposureHistory() {
        return exposureHistory;
    }

    public void setExposureHistory(String exposureHistory) {
        this.exposureHistory = exposureHistory == null ? null : exposureHistory.trim();
    }

    public String getDisabilitySituation() {
        return disabilitySituation;
    }

    public void setDisabilitySituation(String disabilitySituation) {
        this.disabilitySituation = disabilitySituation == null ? null : disabilitySituation.trim();
    }

    public String getKitchenExhaustFacilities() {
        return kitchenExhaustFacilities;
    }

    public void setKitchenExhaustFacilities(String kitchenExhaustFacilities) {
        this.kitchenExhaustFacilities = kitchenExhaustFacilities == null ? null : kitchenExhaustFacilities.trim();
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType == null ? null : fuelType.trim();
    }

    public String getDrinkingWater() {
        return drinkingWater;
    }

    public void setDrinkingWater(String drinkingWater) {
        this.drinkingWater = drinkingWater == null ? null : drinkingWater.trim();
    }

    public String getToilet() {
        return toilet;
    }

    public void setToilet(String toilet) {
        this.toilet = toilet == null ? null : toilet.trim();
    }

    public String getPoultryLivestockPens() {
        return poultryLivestockPens;
    }

    public void setPoultryLivestockPens(String poultryLivestockPens) {
        this.poultryLivestockPens = poultryLivestockPens == null ? null : poultryLivestockPens.trim();
    }

    public String getResponsibleHealthManagerCode() {
        return responsibleHealthManagerCode;
    }

    public void setResponsibleHealthManagerCode(String responsibleHealthManagerCode) {
        this.responsibleHealthManagerCode = responsibleHealthManagerCode == null ? null : responsibleHealthManagerCode.trim();
    }

    public String getArchivist() {
        return archivist;
    }

    public void setArchivist(String archivist) {
        this.archivist = archivist == null ? null : archivist.trim();
    }

    public Date getInsertDate() {
        return insertDate;
    }

    public void setInsertDate(Date insertDate) {
        this.insertDate = insertDate;
    }
}