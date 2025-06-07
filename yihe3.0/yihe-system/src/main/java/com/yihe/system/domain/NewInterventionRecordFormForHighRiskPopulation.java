package com.yihe.system.domain;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class NewInterventionRecordFormForHighRiskPopulation {
    private Integer id;

    private String name;

    private Integer regId;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date followupDate;

    private String followUpMethod;

    private String disease;

    private String disease1;

    private String bloodPressure;

    private String weight;

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

    private String dailyAlcoholConsumption2;

    private String dailyAlcoholConsumption3;

    private String psychologicalAdjustment;

    private String complianceBehavior;

    private String auxiliaryDetection;

    private String medicationComplianceNature;

    private String adverseDrugReactions;

    private String adverseDrugReactions1;

    private String thisServiceClassification;

    private String medicationSituation;

    private String medicationSituation1;

    private String medicationSituation2;

    private String medicationSituation3;

    private String medicationSituation4;

    private String medicationSituation5;

    private String medicationSituation6;

    private String medicationSituation7;

    private String medicationSituation8;

    private String medicationSituation9;

    private String medicationSituation10;

    private String medicationSituation11;

    private String reason;

    private String institutionDepartment;

    private String healthGuidanceOpinions;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date nextFollowupDate;

    private String psychologicalCounselor;

    private Date insertTime;

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

    public String getDname() {
        return dname;
    }

    public void setDname(String dname) {
        this.dname = dname;
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

    public Date getFollowupDate() {
        return followupDate;
    }

    public void setFollowupDate(Date followupDate) {
        this.followupDate = followupDate;
    }

    public String getFollowUpMethod() {
        return followUpMethod;
    }

    public void setFollowUpMethod(String followUpMethod) {
        this.followUpMethod = followUpMethod == null ? null : followUpMethod.trim();
    }

    public String getDisease() {
        return disease;
    }

    public void setDisease(String disease) {
        this.disease = disease == null ? null : disease.trim();
    }

    public String getDisease1() {
        return disease1;
    }

    public void setDisease1(String disease1) {
        this.disease1 = disease1 == null ? null : disease1.trim();
    }

    public String getBloodPressure() {
        return bloodPressure;
    }

    public void setBloodPressure(String bloodPressure) {
        this.bloodPressure = bloodPressure == null ? null : bloodPressure.trim();
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight == null ? null : weight.trim();
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

    public String getDailyAlcoholConsumption2() {
        return dailyAlcoholConsumption2;
    }

    public void setDailyAlcoholConsumption2(String dailyAlcoholConsumption2) {
        this.dailyAlcoholConsumption2 = dailyAlcoholConsumption2 == null ? null : dailyAlcoholConsumption2.trim();
    }

    public String getDailyAlcoholConsumption3() {
        return dailyAlcoholConsumption3;
    }

    public void setDailyAlcoholConsumption3(String dailyAlcoholConsumption3) {
        this.dailyAlcoholConsumption3 = dailyAlcoholConsumption3 == null ? null : dailyAlcoholConsumption3.trim();
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

    public String getAuxiliaryDetection() {
        return auxiliaryDetection;
    }

    public void setAuxiliaryDetection(String auxiliaryDetection) {
        this.auxiliaryDetection = auxiliaryDetection == null ? null : auxiliaryDetection.trim();
    }

    public String getMedicationComplianceNature() {
        return medicationComplianceNature;
    }

    public void setMedicationComplianceNature(String medicationComplianceNature) {
        this.medicationComplianceNature = medicationComplianceNature == null ? null : medicationComplianceNature.trim();
    }

    public String getAdverseDrugReactions() {
        return adverseDrugReactions;
    }

    public void setAdverseDrugReactions(String adverseDrugReactions) {
        this.adverseDrugReactions = adverseDrugReactions == null ? null : adverseDrugReactions.trim();
    }

    public String getAdverseDrugReactions1() {
        return adverseDrugReactions1;
    }

    public void setAdverseDrugReactions1(String adverseDrugReactions1) {
        this.adverseDrugReactions1 = adverseDrugReactions1 == null ? null : adverseDrugReactions1.trim();
    }

    public String getThisServiceClassification() {
        return thisServiceClassification;
    }

    public void setThisServiceClassification(String thisServiceClassification) {
        this.thisServiceClassification = thisServiceClassification == null ? null : thisServiceClassification.trim();
    }

    public String getMedicationSituation() {
        return medicationSituation;
    }

    public void setMedicationSituation(String medicationSituation) {
        this.medicationSituation = medicationSituation == null ? null : medicationSituation.trim();
    }

    public String getMedicationSituation1() {
        return medicationSituation1;
    }

    public void setMedicationSituation1(String medicationSituation1) {
        this.medicationSituation1 = medicationSituation1 == null ? null : medicationSituation1.trim();
    }

    public String getMedicationSituation2() {
        return medicationSituation2;
    }

    public void setMedicationSituation2(String medicationSituation2) {
        this.medicationSituation2 = medicationSituation2 == null ? null : medicationSituation2.trim();
    }

    public String getMedicationSituation3() {
        return medicationSituation3;
    }

    public void setMedicationSituation3(String medicationSituation3) {
        this.medicationSituation3 = medicationSituation3 == null ? null : medicationSituation3.trim();
    }

    public String getMedicationSituation4() {
        return medicationSituation4;
    }

    public void setMedicationSituation4(String medicationSituation4) {
        this.medicationSituation4 = medicationSituation4 == null ? null : medicationSituation4.trim();
    }

    public String getMedicationSituation5() {
        return medicationSituation5;
    }

    public void setMedicationSituation5(String medicationSituation5) {
        this.medicationSituation5 = medicationSituation5 == null ? null : medicationSituation5.trim();
    }

    public String getMedicationSituation6() {
        return medicationSituation6;
    }

    public void setMedicationSituation6(String medicationSituation6) {
        this.medicationSituation6 = medicationSituation6 == null ? null : medicationSituation6.trim();
    }

    public String getMedicationSituation7() {
        return medicationSituation7;
    }

    public void setMedicationSituation7(String medicationSituation7) {
        this.medicationSituation7 = medicationSituation7 == null ? null : medicationSituation7.trim();
    }

    public String getMedicationSituation8() {
        return medicationSituation8;
    }

    public void setMedicationSituation8(String medicationSituation8) {
        this.medicationSituation8 = medicationSituation8 == null ? null : medicationSituation8.trim();
    }

    public String getMedicationSituation9() {
        return medicationSituation9;
    }

    public void setMedicationSituation9(String medicationSituation9) {
        this.medicationSituation9 = medicationSituation9 == null ? null : medicationSituation9.trim();
    }

    public String getMedicationSituation10() {
        return medicationSituation10;
    }

    public void setMedicationSituation10(String medicationSituation10) {
        this.medicationSituation10 = medicationSituation10 == null ? null : medicationSituation10.trim();
    }

    public String getMedicationSituation11() {
        return medicationSituation11;
    }

    public void setMedicationSituation11(String medicationSituation11) {
        this.medicationSituation11 = medicationSituation11 == null ? null : medicationSituation11.trim();
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

    public String getHealthGuidanceOpinions() {
        return healthGuidanceOpinions;
    }

    public void setHealthGuidanceOpinions(String healthGuidanceOpinions) {
        this.healthGuidanceOpinions = healthGuidanceOpinions == null ? null : healthGuidanceOpinions.trim();
    }

    public Date getNextFollowupDate() {
        return nextFollowupDate;
    }

    public void setNextFollowupDate(Date nextFollowupDate) {
        this.nextFollowupDate = nextFollowupDate;
    }

    public String getPsychologicalCounselor() {
        return psychologicalCounselor;
    }

    public void setPsychologicalCounselor(String psychologicalCounselor) {
        this.psychologicalCounselor = psychologicalCounselor == null ? null : psychologicalCounselor.trim();
    }

    public Date getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }
}