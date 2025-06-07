package com.yihe.system.domain;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class NewCoronaryHeartDiseasePatients {
    private Integer id;

    private String name;

    private Integer regId;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date serviceDate;

    private String serviceMode;

    private String symptom;

    private String symptom1;

    private String height;

    private String bodyMassIndex;

    private String weight;

    private String bloodPressure;

    private String heartRate;

    private String hdlc;

    private String fastingBloodGlucose;

    private String cholesterol;

    private String triglyceride;

    private String electrocardiogramExamination;

    private String exerciseLoadTest;

    private String myocardialEnzyme;

    private String coronaryAngiography;

    private String dailySmokingVolume;

    private String dailyAlcoholConsumption;

    private String motion;

    private String motion1;

    private String motion2;

    private String motion3;

    private String medicationCompliance;

    private String specialTreatment;

    private String thisServiceClassification;

    private String nonPharmacologicalTreatmentMeasures;

    private String nonPharmacologicalTreatmentMeasures1;

    private String medicationSituation;

    private String medicationSituation1;

    private String medicationSituation2;

    private String medicationSituation3;

    private String medicationSituation4;

    private String medicationSituation5;

    private String reason;

    private String institutionDepartment;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date nextServiceDate;

    private String healthManagementSpecialist;

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

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height == null ? null : height.trim();
    }

    public String getBodyMassIndex() {
        return bodyMassIndex;
    }

    public void setBodyMassIndex(String bodyMassIndex) {
        this.bodyMassIndex = bodyMassIndex == null ? null : bodyMassIndex.trim();
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight == null ? null : weight.trim();
    }

    public String getBloodPressure() {
        return bloodPressure;
    }

    public void setBloodPressure(String bloodPressure) {
        this.bloodPressure = bloodPressure == null ? null : bloodPressure.trim();
    }

    public String getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(String heartRate) {
        this.heartRate = heartRate == null ? null : heartRate.trim();
    }

    public String getHdlc() {
        return hdlc;
    }

    public void setHdlc(String hdlc) {
        this.hdlc = hdlc == null ? null : hdlc.trim();
    }

    public String getFastingBloodGlucose() {
        return fastingBloodGlucose;
    }

    public void setFastingBloodGlucose(String fastingBloodGlucose) {
        this.fastingBloodGlucose = fastingBloodGlucose == null ? null : fastingBloodGlucose.trim();
    }

    public String getCholesterol() {
        return cholesterol;
    }

    public void setCholesterol(String cholesterol) {
        this.cholesterol = cholesterol == null ? null : cholesterol.trim();
    }

    public String getTriglyceride() {
        return triglyceride;
    }

    public void setTriglyceride(String triglyceride) {
        this.triglyceride = triglyceride == null ? null : triglyceride.trim();
    }

    public String getElectrocardiogramExamination() {
        return electrocardiogramExamination;
    }

    public void setElectrocardiogramExamination(String electrocardiogramExamination) {
        this.electrocardiogramExamination = electrocardiogramExamination == null ? null : electrocardiogramExamination.trim();
    }

    public String getExerciseLoadTest() {
        return exerciseLoadTest;
    }

    public void setExerciseLoadTest(String exerciseLoadTest) {
        this.exerciseLoadTest = exerciseLoadTest == null ? null : exerciseLoadTest.trim();
    }

    public String getMyocardialEnzyme() {
        return myocardialEnzyme;
    }

    public void setMyocardialEnzyme(String myocardialEnzyme) {
        this.myocardialEnzyme = myocardialEnzyme == null ? null : myocardialEnzyme.trim();
    }

    public String getCoronaryAngiography() {
        return coronaryAngiography;
    }

    public void setCoronaryAngiography(String coronaryAngiography) {
        this.coronaryAngiography = coronaryAngiography == null ? null : coronaryAngiography.trim();
    }

    public String getDailySmokingVolume() {
        return dailySmokingVolume;
    }

    public void setDailySmokingVolume(String dailySmokingVolume) {
        this.dailySmokingVolume = dailySmokingVolume == null ? null : dailySmokingVolume.trim();
    }

    public String getDailyAlcoholConsumption() {
        return dailyAlcoholConsumption;
    }

    public void setDailyAlcoholConsumption(String dailyAlcoholConsumption) {
        this.dailyAlcoholConsumption = dailyAlcoholConsumption == null ? null : dailyAlcoholConsumption.trim();
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

    public String getMedicationCompliance() {
        return medicationCompliance;
    }

    public void setMedicationCompliance(String medicationCompliance) {
        this.medicationCompliance = medicationCompliance == null ? null : medicationCompliance.trim();
    }

    public String getSpecialTreatment() {
        return specialTreatment;
    }

    public void setSpecialTreatment(String specialTreatment) {
        this.specialTreatment = specialTreatment == null ? null : specialTreatment.trim();
    }

    public String getThisServiceClassification() {
        return thisServiceClassification;
    }

    public void setThisServiceClassification(String thisServiceClassification) {
        this.thisServiceClassification = thisServiceClassification == null ? null : thisServiceClassification.trim();
    }

    public String getNonPharmacologicalTreatmentMeasures() {
        return nonPharmacologicalTreatmentMeasures;
    }

    public void setNonPharmacologicalTreatmentMeasures(String nonPharmacologicalTreatmentMeasures) {
        this.nonPharmacologicalTreatmentMeasures = nonPharmacologicalTreatmentMeasures == null ? null : nonPharmacologicalTreatmentMeasures.trim();
    }

    public String getNonPharmacologicalTreatmentMeasures1() {
        return nonPharmacologicalTreatmentMeasures1;
    }

    public void setNonPharmacologicalTreatmentMeasures1(String nonPharmacologicalTreatmentMeasures1) {
        this.nonPharmacologicalTreatmentMeasures1 = nonPharmacologicalTreatmentMeasures1 == null ? null : nonPharmacologicalTreatmentMeasures1.trim();
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

    public Date getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }
}