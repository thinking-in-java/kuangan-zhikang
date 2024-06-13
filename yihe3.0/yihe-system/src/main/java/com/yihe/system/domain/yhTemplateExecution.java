package com.yihe.system.domain;

import java.util.Date;

public class yhTemplateExecution {
    private Integer executionId;

    private String userCode;

    private String groupId;

    private Integer planCount;

    private String lastPlanTime;

    public Integer getExecutionId() {
        return executionId;
    }

    public void setExecutionId(Integer executionId) {
        this.executionId = executionId;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode == null ? null : userCode.trim();
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId == null ? null : groupId.trim();
    }

    public Integer getPlanCount() {
        return planCount;
    }

    public void setPlanCount(Integer planCount) {
        this.planCount = planCount;
    }

    public String getLastPlanTime() {
        return lastPlanTime;
    }

    public void setLastPlanTime(String lastPlanTime) {
        this.lastPlanTime = lastPlanTime;
    }
}