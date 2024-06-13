package com.yihe.system.domain;

import java.util.Date;

public class YhScoreEventCategoryWeb {
    private Integer scoreeventId;

    private String eventName;

    private Integer score;

    private String eventType;

    private Integer type;

    private Integer continuousStatus;

    private Integer putaway;

    private Date updateDate;

    private String creator;

    private String standby;

    private String standby2;

    public Integer getScoreeventId() {
        return scoreeventId;
    }

    public void setScoreeventId(Integer scoreeventId) {
        this.scoreeventId = scoreeventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName == null ? null : eventName.trim();
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType == null ? null : eventType.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getContinuousStatus() {
        return continuousStatus;
    }

    public void setContinuousStatus(Integer continuousStatus) {
        this.continuousStatus = continuousStatus;
    }

    public Integer getPutaway() {
        return putaway;
    }

    public void setPutaway(Integer putaway) {
        this.putaway = putaway;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    public String getStandby() {
        return standby;
    }

    public void setStandby(String standby) {
        this.standby = standby == null ? null : standby.trim();
    }

    public String getStandby2() {
        return standby2;
    }

    public void setStandby2(String standby2) {
        this.standby2 = standby2 == null ? null : standby2.trim();
    }
}