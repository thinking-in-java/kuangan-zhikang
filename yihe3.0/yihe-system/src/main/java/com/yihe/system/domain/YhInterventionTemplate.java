package com.yihe.system.domain;

public class YhInterventionTemplate {
    private Integer interventionId;

    private String sourceType;

    private Integer templateDiseases;

    private String templateRisk;

    private String templateName;

    private String insertTime;

    private Integer planCount;

    private Integer applySex;

    private Integer applyAgeMin;

    private Integer applyAgeMax;

    private Float applyBimMin;

    private Float applyBimMax;

    private String souceName;

    private Integer souceIsdelete;

    private String operation;

    private String templateDescribe;

    private String disease_name;

    private  int page;

    private int limit;

    private int start;

    private String Nowusercode;
    private String Roleid;

    private String usercode;

    public String getUsercode() {
        return usercode;
    }

    public void setUsercode(String usercode) {
        this.usercode = usercode;
    }

    public String getRoleid() {
        return Roleid;
    }

    public void setRoleid(String roleid) {
        Roleid = roleid;
    }

    public String getNowusercode() {
        return Nowusercode;
    }

    public void setNowusercode(String nowusercode) {
        Nowusercode = nowusercode;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public String getDisease_name() {
        return disease_name;
    }

    public void setDisease_name(String disease_name) {
        this.disease_name = disease_name;
    }

    public Integer getInterventionId() {
        return interventionId;
    }

    public void setInterventionId(Integer interventionId) {
        this.interventionId = interventionId;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public Integer getTemplateDiseases() {
        return templateDiseases;
    }

    public void setTemplateDiseases(Integer templateDiseases) {
        this.templateDiseases = templateDiseases;
    }

    public String getTemplateRisk() {
        return templateRisk;
    }

    public void setTemplateRisk(String templateRisk) {
        this.templateRisk = templateRisk;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName == null ? null : templateName.trim();
    }

    public String getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(String insertTime) {
        this.insertTime = insertTime;
    }

    public Integer getPlanCount() {
        return planCount;
    }

    public void setPlanCount(Integer planCount) {
        this.planCount = planCount;
    }

    public Integer getApplySex() {
        return applySex;
    }

    public void setApplySex(Integer applySex) {
        this.applySex = applySex;
    }

    public Integer getApplyAgeMin() {
        return applyAgeMin;
    }

    public void setApplyAgeMin(Integer applyAgeMin) {
        this.applyAgeMin = applyAgeMin;
    }

    public Integer getApplyAgeMax() {
        return applyAgeMax;
    }

    public void setApplyAgeMax(Integer applyAgeMax) {
        this.applyAgeMax = applyAgeMax;
    }

    public Float getApplyBimMin() {
        return applyBimMin;
    }

    public void setApplyBimMin(Float applyBimMin) {
        this.applyBimMin = applyBimMin;
    }

    public Float getApplyBimMax() {
        return applyBimMax;
    }

    public void setApplyBimMax(Float applyBimMax) {
        this.applyBimMax = applyBimMax;
    }

    public String getSouceName() {
        return souceName;
    }

    public void setSouceName(String souceName) {
        this.souceName = souceName == null ? null : souceName.trim();
    }

    public Integer getSouceIsdelete() {
        return souceIsdelete;
    }

    public void setSouceIsdelete(Integer souceIsdelete) {
        this.souceIsdelete = souceIsdelete;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation == null ? null : operation.trim();
    }

    public String getTemplateDescribe() {
        return templateDescribe;
    }

    public void setTemplateDescribe(String templateDescribe) {
        this.templateDescribe = templateDescribe == null ? null : templateDescribe.trim();
    }
}