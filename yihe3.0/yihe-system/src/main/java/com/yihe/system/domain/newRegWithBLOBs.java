package com.yihe.system.domain;

import com.yihe.system.domain.newReg;

import java.util.List;

public class newRegWithBLOBs extends newReg {
    private String medicalHistory;

    private String surgicalHistory;

    private String medicalHistoryTrauma;

    private String bloodTransfusionHistory;

    private String familyHistory;

    private String geneticHistory;

    private int numberPhysicalExaminations;

    private String evaluationTime;

    private int interventionCount;

    private int begin;

    private int limit;

    private String doctor;

    private List<String> lists;

    private String v1;
    private String v2;
    private String v3;
    private String v4;

    public String getV1() {
        return v1;
    }

    public void setV1(String v1) {
        this.v1 = v1;
    }

    public String getV2() {
        return v2;
    }

    public void setV2(String v2) {
        this.v2 = v2;
    }

    public String getV3() {
        return v3;
    }

    public void setV3(String v3) {
        this.v3 = v3;
    }

    public String getV4() {
        return v4;
    }

    public void setV4(String v4) {
        this.v4 = v4;
    }

    public List<String> getLists() {
        return lists;
    }

    public void setLists(List<String> lists) {
        this.lists = lists;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public int getBegin() {
        return begin;
    }

    public void setBegin(int begin) {
        this.begin = begin;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getNumberPhysicalExaminations() {
        return numberPhysicalExaminations;
    }

    public void setNumberPhysicalExaminations(int numberPhysicalExaminations) {
        this.numberPhysicalExaminations = numberPhysicalExaminations;
    }

    public String getEvaluationTime() {
        return evaluationTime;
    }

    public void setEvaluationTime(String evaluationTime) {
        this.evaluationTime = evaluationTime;
    }

    public int getInterventionCount() {
        return interventionCount;
    }

    public void setInterventionCount(int interventionCount) {
        this.interventionCount = interventionCount;
    }

    public String getMedicalHistory() {
        return medicalHistory;
    }

    public void setMedicalHistory(String medicalHistory) {
        this.medicalHistory = medicalHistory == null ? null : medicalHistory.trim();
    }

    public String getSurgicalHistory() {
        return surgicalHistory;
    }

    public void setSurgicalHistory(String surgicalHistory) {
        this.surgicalHistory = surgicalHistory == null ? null : surgicalHistory.trim();
    }

    public String getMedicalHistoryTrauma() {
        return medicalHistoryTrauma;
    }

    public void setMedicalHistoryTrauma(String medicalHistoryTrauma) {
        this.medicalHistoryTrauma = medicalHistoryTrauma == null ? null : medicalHistoryTrauma.trim();
    }

    public String getBloodTransfusionHistory() {
        return bloodTransfusionHistory;
    }

    public void setBloodTransfusionHistory(String bloodTransfusionHistory) {
        this.bloodTransfusionHistory = bloodTransfusionHistory == null ? null : bloodTransfusionHistory.trim();
    }

    public String getFamilyHistory() {
        return familyHistory;
    }

    public void setFamilyHistory(String familyHistory) {
        this.familyHistory = familyHistory == null ? null : familyHistory.trim();
    }

    public String getGeneticHistory() {
        return geneticHistory;
    }

    public void setGeneticHistory(String geneticHistory) {
        this.geneticHistory = geneticHistory == null ? null : geneticHistory.trim();
    }
}