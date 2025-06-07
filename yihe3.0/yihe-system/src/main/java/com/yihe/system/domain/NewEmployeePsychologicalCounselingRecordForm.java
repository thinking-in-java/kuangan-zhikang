package com.yihe.system.domain;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class NewEmployeePsychologicalCounselingRecordForm {
    private Integer id;

    private String customerAffiliation;

    private String customerContact;

    private Integer regId;

    private String name;

    private String sex;

    private String age;

    private String company;

    private String departments;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date serviceDate;

    private String chiefComplaint;

    private String objectiveObservation;

    private String assessment;

    private String plan;

    private String importantInteraction;

    private String familySocialBackground;

    private String resourcesStrength;

    private String consultationEffectivenessFeedback;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date nextVisitDate;

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

    public String getCustomerAffiliation() {
        return customerAffiliation;
    }

    public void setCustomerAffiliation(String customerAffiliation) {
        this.customerAffiliation = customerAffiliation == null ? null : customerAffiliation.trim();
    }

    public String getCustomerContact() {
        return customerContact;
    }

    public void setCustomerContact(String customerContact) {
        this.customerContact = customerContact == null ? null : customerContact.trim();
    }

    public Integer getRegId() {
        return regId;
    }

    public void setRegId(Integer regId) {
        this.regId = regId;
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

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age == null ? null : age.trim();
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company == null ? null : company.trim();
    }

    public String getDepartments() {
        return departments;
    }

    public void setDepartments(String departments) {
        this.departments = departments == null ? null : departments.trim();
    }

    public Date getServiceDate() {
        return serviceDate;
    }

    public void setServiceDate(Date serviceDate) {
        this.serviceDate = serviceDate;
    }

    public String getChiefComplaint() {
        return chiefComplaint;
    }

    public void setChiefComplaint(String chiefComplaint) {
        this.chiefComplaint = chiefComplaint == null ? null : chiefComplaint.trim();
    }

    public String getObjectiveObservation() {
        return objectiveObservation;
    }

    public void setObjectiveObservation(String objectiveObservation) {
        this.objectiveObservation = objectiveObservation == null ? null : objectiveObservation.trim();
    }

    public String getAssessment() {
        return assessment;
    }

    public void setAssessment(String assessment) {
        this.assessment = assessment == null ? null : assessment.trim();
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan == null ? null : plan.trim();
    }

    public String getImportantInteraction() {
        return importantInteraction;
    }

    public void setImportantInteraction(String importantInteraction) {
        this.importantInteraction = importantInteraction == null ? null : importantInteraction.trim();
    }

    public String getFamilySocialBackground() {
        return familySocialBackground;
    }

    public void setFamilySocialBackground(String familySocialBackground) {
        this.familySocialBackground = familySocialBackground == null ? null : familySocialBackground.trim();
    }

    public String getResourcesStrength() {
        return resourcesStrength;
    }

    public void setResourcesStrength(String resourcesStrength) {
        this.resourcesStrength = resourcesStrength == null ? null : resourcesStrength.trim();
    }

    public String getConsultationEffectivenessFeedback() {
        return consultationEffectivenessFeedback;
    }

    public void setConsultationEffectivenessFeedback(String consultationEffectivenessFeedback) {
        this.consultationEffectivenessFeedback = consultationEffectivenessFeedback == null ? null : consultationEffectivenessFeedback.trim();
    }

    public Date getNextVisitDate() {
        return nextVisitDate;
    }

    public void setNextVisitDate(Date nextVisitDate) {
        this.nextVisitDate = nextVisitDate;
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