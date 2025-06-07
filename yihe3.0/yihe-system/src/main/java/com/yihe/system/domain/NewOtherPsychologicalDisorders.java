package com.yihe.system.domain;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class NewOtherPsychologicalDisorders {
    private Integer id;

    private String name;

    private Integer regId;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date serviceDate;

    private String serviceMode;

    private String symptom;

    private String symptom1;

    private String bloodPressure;

    private String bloodPressure1;

    private String weight;

    private String weight1;

    private String bodyMassIndex;

    private String bodyMassIndex1;

    private String heartRate;

    private String other;

    private String dailySmokingVolume;

    private String dailySmokingVolume1;

    private String dailyAlcoholConsumption;

    private String dailyAlcoholConsumption1;

    private String motion;

    private String motion1;

    private String motion2;

    private String motion3;

    private String dietarySituation;

    private String psychologicalAdjustment;

    private String complianceBehavior;

    private String supplementaryExamination;

    private String medicationCompliance;

    private String adverseDrugReactions;

    private String thisServiceClassification;

    private String reason;

    private String institutionDepartment;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date nextServiceDate;

    private String healthManagementSpecialist;

    private String medicationSituation;

    private Date insertTime;

    private String dietarySituation1;

    private String healthGuidanceOpinions;

    private String adverseDrugReactions1;

    private Integer begin;

    private Integer limit;

    private String dname;

    private String workUnit;

    public String getWorkUnit() {
        return workUnit;
    }

    public void setWorkUnit(String workUnit) {
        this.workUnit = workUnit;
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

    public String getDname() {
        return dname;
    }

    public void setDname(String dname) {
        this.dname = dname;
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

    public Integer getRegId() {
        return regId;
    }

    public void setRegId(Integer regId) {
        this.regId = regId;
    }

    public Date getServiceDate() {
        return serviceDate;
    }

    public void setServiceDate(Date serviceDate) {
        this.serviceDate = serviceDate;
    }

    public String getServiceMode() {
        return serviceMode;
    }

    public void setServiceMode(String serviceMode) {
        this.serviceMode = serviceMode == null ? null : serviceMode.trim();
    }

    public String getSymptom() {
        return symptom;
    }

    public void setSymptom(String symptom) {
        this.symptom = symptom == null ? null : symptom.trim();
    }

    public String getSymptom1() {
        return symptom1;
    }

    public void setSymptom1(String symptom1) {
        this.symptom1 = symptom1 == null ? null : symptom1.trim();
    }

    public String getBloodPressure() {
        return bloodPressure;
    }

    public void setBloodPressure(String bloodPressure) {
        this.bloodPressure = bloodPressure == null ? null : bloodPressure.trim();
    }

    public String getBloodPressure1() {
        return bloodPressure1;
    }

    public void setBloodPressure1(String bloodPressure1) {
        this.bloodPressure1 = bloodPressure1 == null ? null : bloodPressure1.trim();
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight == null ? null : weight.trim();
    }

    public String getWeight1() {
        return weight1;
    }

    public void setWeight1(String weight1) {
        this.weight1 = weight1 == null ? null : weight1.trim();
    }

    public String getBodyMassIndex() {
        return bodyMassIndex;
    }

    public void setBodyMassIndex(String bodyMassIndex) {
        this.bodyMassIndex = bodyMassIndex == null ? null : bodyMassIndex.trim();
    }

    public String getBodyMassIndex1() {
        return bodyMassIndex1;
    }

    public void setBodyMassIndex1(String bodyMassIndex1) {
        this.bodyMassIndex1 = bodyMassIndex1 == null ? null : bodyMassIndex1.trim();
    }

    public String getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(String heartRate) {
        this.heartRate = heartRate == null ? null : heartRate.trim();
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other == null ? null : other.trim();
    }

    public String getDailySmokingVolume() {
        return dailySmokingVolume;
    }

    public void setDailySmokingVolume(String dailySmokingVolume) {
        this.dailySmokingVolume = dailySmokingVolume == null ? null : dailySmokingVolume.trim();
    }

    public String getDailySmokingVolume1() {
        return dailySmokingVolume1;
    }

    public void setDailySmokingVolume1(String dailySmokingVolume1) {
        this.dailySmokingVolume1 = dailySmokingVolume1 == null ? null : dailySmokingVolume1.trim();
    }

    public String getDailyAlcoholConsumption() {
        return dailyAlcoholConsumption;
    }

    public void setDailyAlcoholConsumption(String dailyAlcoholConsumption) {
        this.dailyAlcoholConsumption = dailyAlcoholConsumption == null ? null : dailyAlcoholConsumption.trim();
    }

    public String getDailyAlcoholConsumption1() {
        return dailyAlcoholConsumption1;
    }

    public void setDailyAlcoholConsumption1(String dailyAlcoholConsumption1) {
        this.dailyAlcoholConsumption1 = dailyAlcoholConsumption1 == null ? null : dailyAlcoholConsumption1.trim();
    }

    public String getMotion() {
        return motion;
    }

    public void setMotion(String motion) {
        this.motion = motion == null ? null : motion.trim();
    }

    public String getMotion1() {
        return motion1;
    }

    public void setMotion1(String motion1) {
        this.motion1 = motion1 == null ? null : motion1.trim();
    }

    public String getMotion2() {
        return motion2;
    }

    public void setMotion2(String motion2) {
        this.motion2 = motion2 == null ? null : motion2.trim();
    }

    public String getMotion3() {
        return motion3;
    }

    public void setMotion3(String motion3) {
        this.motion3 = motion3 == null ? null : motion3.trim();
    }

    public String getDietarySituation() {
        return dietarySituation;
    }

    public void setDietarySituation(String dietarySituation) {
        this.dietarySituation = dietarySituation == null ? null : dietarySituation.trim();
    }

    public String getPsychologicalAdjustment() {
        return psychologicalAdjustment;
    }

    public void setPsychologicalAdjustment(String psychologicalAdjustment) {
        this.psychologicalAdjustment = psychologicalAdjustment == null ? null : psychologicalAdjustment.trim();
    }

    public String getComplianceBehavior() {
        return complianceBehavior;
    }

    public void setComplianceBehavior(String complianceBehavior) {
        this.complianceBehavior = complianceBehavior == null ? null : complianceBehavior.trim();
    }

    public String getSupplementaryExamination() {
        return supplementaryExamination;
    }

    public void setSupplementaryExamination(String supplementaryExamination) {
        this.supplementaryExamination = supplementaryExamination == null ? null : supplementaryExamination.trim();
    }

    public String getMedicationCompliance() {
        return medicationCompliance;
    }

    public void setMedicationCompliance(String medicationCompliance) {
        this.medicationCompliance = medicationCompliance == null ? null : medicationCompliance.trim();
    }

    public String getAdverseDrugReactions() {
        return adverseDrugReactions;
    }

    public void setAdverseDrugReactions(String adverseDrugReactions) {
        this.adverseDrugReactions = adverseDrugReactions == null ? null : adverseDrugReactions.trim();
    }

    public String getThisServiceClassification() {
        return thisServiceClassification;
    }

    public void setThisServiceClassification(String thisServiceClassification) {
        this.thisServiceClassification = thisServiceClassification == null ? null : thisServiceClassification.trim();
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason == null ? null : reason.trim();
    }

    public String getInstitutionDepartment() {
        return institutionDepartment;
    }

    public void setInstitutionDepartment(String institutionDepartment) {
        this.institutionDepartment = institutionDepartment == null ? null : institutionDepartment.trim();
    }

    public Date getNextServiceDate() {
        return nextServiceDate;
    }

    public void setNextServiceDate(Date nextServiceDate) {
        this.nextServiceDate = nextServiceDate;
    }

    public String getHealthManagementSpecialist() {
        return healthManagementSpecialist;
    }

    public void setHealthManagementSpecialist(String healthManagementSpecialist) {
        this.healthManagementSpecialist = healthManagementSpecialist == null ? null : healthManagementSpecialist.trim();
    }

    public String getMedicationSituation() {
        return medicationSituation;
    }

    public void setMedicationSituation(String medicationSituation) {
        this.medicationSituation = medicationSituation == null ? null : medicationSituation.trim();
    }

    public Date getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }

    public String getDietarySituation1() {
        return dietarySituation1;
    }

    public void setDietarySituation1(String dietarySituation1) {
        this.dietarySituation1 = dietarySituation1 == null ? null : dietarySituation1.trim();
    }

    public String getHealthGuidanceOpinions() {
        return healthGuidanceOpinions;
    }

    public void setHealthGuidanceOpinions(String healthGuidanceOpinions) {
        this.healthGuidanceOpinions = healthGuidanceOpinions == null ? null : healthGuidanceOpinions.trim();
    }

    public String getAdverseDrugReactions1() {
        return adverseDrugReactions1;
    }

    public void setAdverseDrugReactions1(String adverseDrugReactions1) {
        this.adverseDrugReactions1 = adverseDrugReactions1 == null ? null : adverseDrugReactions1.trim();
    }
}