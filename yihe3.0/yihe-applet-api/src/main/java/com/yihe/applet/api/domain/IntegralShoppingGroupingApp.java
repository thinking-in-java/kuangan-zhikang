package com.yihe.applet.api.domain;

import java.util.Date;

public class IntegralShoppingGroupingApp {
    private Integer groupingId;

    private String name;

    private String parentId;

    private Integer sortId;

    private Date addTime;

    private String orgCode;

    public Integer getGroupingId() {
        return groupingId;
    }

    public void setGroupingId(Integer groupingId) {
        this.groupingId = groupingId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId == null ? null : parentId.trim();
    }

    public Integer getSortId() {
        return sortId;
    }

    public void setSortId(Integer sortId) {
        this.sortId = sortId;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode == null ? null : orgCode.trim();
    }

    public IntegralShoppingGroupingApp(Integer groupingId, String name, String orgCode) {
        this.groupingId = groupingId;
        this.name = name;
        this.orgCode = orgCode;
    }

    public IntegralShoppingGroupingApp() {
    }
}