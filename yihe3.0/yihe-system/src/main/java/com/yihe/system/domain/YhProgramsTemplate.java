package com.yihe.system.domain;

import java.util.List;

public class YhProgramsTemplate {
    private Integer programsId;

    private Integer interventionId;

    private Integer programsWeeks;

    private Integer programsDays;

    private String serviceWay;

    private String planContent;

    private String contentPrompt;

    private Integer remark;

    private  int page;

    private int limit;

    private int start;
    private String plan_count;
    private String usercode;
    private String LAY_CHECKE;
    private Boolean LAY_CHECKED;
    private Integer temporary_id;
    private String[] ids;
    private String time;
    public Integer getTemporary_id() {
        return temporary_id;
    }

    public void setTemporary_id(Integer temporary_id) {
        this.temporary_id = temporary_id;
    }



    private List<Integer> templatid;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String[] getIds() {
        return ids;
    }

    public void setIds(String[] ids) {
        this.ids = ids;
    }

    public String getLAY_CHECKE() {
        return LAY_CHECKE;
    }

    public void setLAY_CHECKE(String LAY_CHECKE) {
        this.LAY_CHECKE = LAY_CHECKE;
    }

    public Boolean getLAY_CHECKED() {
        return LAY_CHECKED;
    }

    public void setLAY_CHECKED(Boolean LAY_CHECKED) {
        this.LAY_CHECKED = LAY_CHECKED;
    }

    public List<Integer> getTemplatid() {
        return templatid;
    }

    public void setTemplatid(List<Integer> templatid) {
        this.templatid = templatid;
    }

    public String getUsercode() {
        return usercode;
    }

    public void setUsercode(String usercode) {
        this.usercode = usercode;
    }

    public String getPlan_count() {
        return plan_count;
    }

    public void setPlan_count(String plan_count) {
        this.plan_count = plan_count;
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

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public Integer getProgramsId() {
        return programsId;
    }

    public void setProgramsId(Integer programsId) {
        this.programsId = programsId;
    }

    public Integer getInterventionId() {
        return interventionId;
    }

    public void setInterventionId(Integer interventionId) {
        this.interventionId = interventionId;
    }

    public Integer getProgramsWeeks() {
        return programsWeeks;
    }

    public void setProgramsWeeks(Integer programsWeeks) {
        this.programsWeeks = programsWeeks;
    }

    public Integer getProgramsDays() {
        return programsDays;
    }

    public void setProgramsDays(Integer programsDays) {
        this.programsDays = programsDays;
    }

    public String getServiceWay() {
        return serviceWay;
    }

    public void setServiceWay(String serviceWay) {
        this.serviceWay = serviceWay == null ? null : serviceWay.trim();
    }

    public String getPlanContent() {
        return planContent;
    }

    public void setPlanContent(String planContent) {
        this.planContent = planContent == null ? null : planContent.trim();
    }

    public String getContentPrompt() {
        return contentPrompt;
    }

    public void setContentPrompt(String contentPrompt) {
        this.contentPrompt = contentPrompt == null ? null : contentPrompt.trim();
    }

    public Integer getRemark() {
        return remark;
    }

    public void setRemark(Integer remark) {
        this.remark = remark;
    }
}