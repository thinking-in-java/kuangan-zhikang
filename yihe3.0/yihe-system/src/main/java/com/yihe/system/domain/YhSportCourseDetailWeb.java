package com.yihe.system.domain;

public class YhSportCourseDetailWeb {
    private Integer cdetailId;

    private Integer cdetailDay;

    private String cdetailName;

    private String cdetailUrl;

    private Integer courseId;

    private String viconIds;

    private Integer cdetailTotals;

    private Integer cdetailTotalkal;

    private Integer cdetailTotalduration;

    public Integer getCdetailId() {
        return cdetailId;
    }

    public void setCdetailId(Integer cdetailId) {
        this.cdetailId = cdetailId;
    }

    public Integer getCdetailDay() {
        return cdetailDay;
    }

    public void setCdetailDay(Integer cdetailDay) {
        this.cdetailDay = cdetailDay;
    }

    public String getCdetailName() {
        return cdetailName;
    }

    public void setCdetailName(String cdetailName) {
        this.cdetailName = cdetailName == null ? null : cdetailName.trim();
    }

    public String getCdetailUrl() {
        return cdetailUrl;
    }

    public void setCdetailUrl(String cdetailUrl) {
        this.cdetailUrl = cdetailUrl == null ? null : cdetailUrl.trim();
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public String getViconIds() {
        return viconIds;
    }

    public void setViconIds(String viconIds) {
        this.viconIds = viconIds == null ? null : viconIds.trim();
    }

    public Integer getCdetailTotals() {
        return cdetailTotals;
    }

    public void setCdetailTotals(Integer cdetailTotals) {
        this.cdetailTotals = cdetailTotals;
    }

    public Integer getCdetailTotalkal() {
        return cdetailTotalkal;
    }

    public void setCdetailTotalkal(Integer cdetailTotalkal) {
        this.cdetailTotalkal = cdetailTotalkal;
    }

    public Integer getCdetailTotalduration() {
        return cdetailTotalduration;
    }

    public void setCdetailTotalduration(Integer cdetailTotalduration) {
        this.cdetailTotalduration = cdetailTotalduration;
    }
}