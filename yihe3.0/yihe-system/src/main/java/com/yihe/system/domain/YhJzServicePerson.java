package com.yihe.system.domain;

public class YhJzServicePerson {
    private Integer id;

    private String userCode;

    private Integer gender;

    private String endServiceTime;

    private String phone;

    private Integer age;

    private String startServiceTime;

    private Integer serviceState;

    private String serviceWaitingTime;
    //服务内容
    private String serviceContent;

    private Integer serviceType;

    private String serviceRecord;

    private Integer isrede;

    private Integer returnvisit;

    private Double longitude;

    private Double dimension;

    private String insertTime;

    private String acceptsituation;

    private Integer childnodes;

    private Integer childtag;

    private Integer satisfaction;
    //服务类型
    private String serviceSubtitle;
    private String select;
    //当前时间
    private String nowtime;
    //计划服务日期
    private String datetime;
    //服务期限
    private String lintime;

    private Integer totaltype;

    private String finishi_doctor;

    public String getFinishi_doctor() {
        return finishi_doctor;
    }

    public void setFinishi_doctor(String finishi_doctor) {
        this.finishi_doctor = finishi_doctor;
    }

    public Integer getTotaltype() {
        return totaltype;
    }

    public void setTotaltype(Integer totaltype) {
        this.totaltype = totaltype;
    }

    public String getNowtime() {
        return nowtime;
    }

    public void setNowtime(String nowtime) {
        this.nowtime = nowtime;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getLintime() {
        return lintime;
    }

    public void setLintime(String lintime) {
        this.lintime = lintime;
    }

    public String getEndServiceTime() {
        return endServiceTime;
    }

    public void setEndServiceTime(String endServiceTime) {
        this.endServiceTime = endServiceTime;
    }

    public String getStartServiceTime() {
        return startServiceTime;
    }

    public void setStartServiceTime(String startServiceTime) {
        this.startServiceTime = startServiceTime;
    }

    public String getServiceWaitingTime() {
        return serviceWaitingTime;
    }

    public void setServiceWaitingTime(String serviceWaitingTime) {
        this.serviceWaitingTime = serviceWaitingTime;
    }

    public String getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(String insertTime) {
        this.insertTime = insertTime;
    }

    public String getSelect() {
        return select;
    }

    public void setSelect(String select) {
        this.select = select;
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

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }



    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }



    public Integer getServiceState() {
        return serviceState;
    }

    public void setServiceState(Integer serviceState) {
        this.serviceState = serviceState;
    }



    public String getServiceContent() {
        return serviceContent;
    }

    public void setServiceContent(String serviceContent) {
        this.serviceContent = serviceContent == null ? null : serviceContent.trim();
    }

    public Integer getServiceType() {
        return serviceType;
    }

    public void setServiceType(Integer serviceType) {
        this.serviceType = serviceType;
    }

    public String getServiceRecord() {
        return serviceRecord;
    }

    public void setServiceRecord(String serviceRecord) {
        this.serviceRecord = serviceRecord == null ? null : serviceRecord.trim();
    }

    public Integer getIsrede() {
        return isrede;
    }

    public void setIsrede(Integer isrede) {
        this.isrede = isrede;
    }

    public Integer getReturnvisit() {
        return returnvisit;
    }

    public void setReturnvisit(Integer returnvisit) {
        this.returnvisit = returnvisit;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getDimension() {
        return dimension;
    }

    public void setDimension(Double dimension) {
        this.dimension = dimension;
    }



    public String getAcceptsituation() {
        return acceptsituation;
    }

    public void setAcceptsituation(String acceptsituation) {
        this.acceptsituation = acceptsituation == null ? null : acceptsituation.trim();
    }

    public Integer getChildnodes() {
        return childnodes;
    }

    public void setChildnodes(Integer childnodes) {
        this.childnodes = childnodes;
    }

    public Integer getChildtag() {
        return childtag;
    }

    public void setChildtag(Integer childtag) {
        this.childtag = childtag;
    }

    public Integer getSatisfaction() {
        return satisfaction;
    }

    public void setSatisfaction(Integer satisfaction) {
        this.satisfaction = satisfaction;
    }

    public String getServiceSubtitle() {
        return serviceSubtitle;
    }

    public void setServiceSubtitle(String serviceSubtitle) {
        this.serviceSubtitle = serviceSubtitle == null ? null : serviceSubtitle.trim();
    }
}