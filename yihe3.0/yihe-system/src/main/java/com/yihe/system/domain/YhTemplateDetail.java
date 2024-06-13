package com.yihe.system.domain;

import java.util.Date;

public class YhTemplateDetail {
    private Integer detailsId;

    private Date executeDate;
    private String ecdate;
    private String var_count;


    private Integer serviceWay;

    private String planCount;

    private String countPrompt;

    private String userCode;
    private String counts;

    //分页
    private Integer limit;
    private Integer page;
    private Integer start;

    //长时间
    private  String times;
    //开始结束时间
    private String begintime;
    private String endtime;
    private  String servicenam;
    private String[] deleteids;

    //个人信息
    private String gender;
    private String phone;
    private String watting_time;
    private String insert_time;
    private Integer service_state;
    private Integer service_type;
    private String service_recode;
    private String service_subtitle;
    private Integer count;
    private Integer totaltype;

    public Integer getTotaltype() {
        return totaltype;
    }

    public void setTotaltype(Integer totaltype) {
        this.totaltype = totaltype;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getVar_count() {
        return var_count;
    }

    public void setVar_count(String var_count) {
        this.var_count = var_count;
    }

    public String getEcdate() {
        return ecdate;
    }

    public void setEcdate(String ecdate) {
        this.ecdate = ecdate;
    }

    public String getCounts() {
        return counts;
    }

    public void setCounts(String counts) {
        this.counts = counts;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWatting_time() {
        return watting_time;
    }

    public void setWatting_time(String watting_time) {
        this.watting_time = watting_time;
    }

    public String getInsert_time() {
        return insert_time;
    }

    public void setInsert_time(String insert_time) {
        this.insert_time = insert_time;
    }

    public Integer getService_state() {
        return service_state;
    }

    public void setService_state(Integer service_state) {
        this.service_state = service_state;
    }

    public Integer getService_type() {
        return service_type;
    }

    public void setService_type(Integer service_type) {
        this.service_type = service_type;
    }

    public String getService_recode() {
        return service_recode;
    }

    public void setService_recode(String service_recode) {
        this.service_recode = service_recode;
    }

    public String getService_subtitle() {
        return service_subtitle;
    }

    public void setService_subtitle(String service_subtitle) {
        this.service_subtitle = service_subtitle;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String[] getDeleteids() {
        return deleteids;
    }

    public void setDeleteids(String[] deleteids) {
        this.deleteids = deleteids;
    }

    public String getServicenam() {
        return servicenam;
    }

    public void setServicenam(String servicenam) {
        this.servicenam = servicenam;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }

    public String getBegintime() {
        return begintime;
    }

    public void setBegintime(String begintime) {
        this.begintime = begintime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public Integer getDetailsId() {
        return detailsId;
    }

    public void setDetailsId(Integer detailsId) {
        this.detailsId = detailsId;
    }

    public Date getExecuteDate() {
        return executeDate;
    }

    public void setExecuteDate(Date executeDate) {
        this.executeDate = executeDate;
    }

    public Integer getServiceWay() {
        return serviceWay;
    }

    public void setServiceWay(Integer serviceWay) {
        this.serviceWay = serviceWay;
    }

    public String getPlanCount() {
        return planCount;
    }

    public void setPlanCount(String planCount) {
        this.planCount = planCount == null ? null : planCount.trim();
    }

    public String getCountPrompt() {
        return countPrompt;
    }

    public void setCountPrompt(String countPrompt) {
        this.countPrompt = countPrompt == null ? null : countPrompt.trim();
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode == null ? null : userCode.trim();
    }
}