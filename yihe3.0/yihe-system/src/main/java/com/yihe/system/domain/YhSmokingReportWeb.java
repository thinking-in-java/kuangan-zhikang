package com.yihe.system.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yihe.common.annotation.Excel;
import com.yihe.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 尼古丁评估结果对象 yh_smoking_report
 *
 * @author dengyongbo
 * @date 2022-05-24
 */
public class YhSmokingReportWeb extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * $column.columnComment
     */
    private Long smokingReportId;

    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String userCode;

    /**
     * 报告描述
     */
    @Excel(name = "报告描述")
    private String smokingReportDesc;

    /**
     * 评估分数
     */
    @Excel(name = "评估分数")
    private Integer smokingReportScore;

    /**
     * 报告等级
     */
    @Excel(name = "报告等级")
    private String smokingReportLevel;

    /**
     * 问卷答案
     */
    @Excel(name = "问卷答案")
    private String smokingReportQueselect;

    /**
     * 评估时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "评估时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date qtime;


    private String usercode;
    private String name;
    private String mobile;
    private Integer num;

    private String strTime;
    private String endTime;
    private String orgId;
    private String doctor;
    private Integer page;
    private Integer limit;


    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public String getStrTime() {
        return strTime;
    }

    public void setStrTime(String strTime) {
        this.strTime = strTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getUsercode() {
        return usercode;
    }

    public void setUsercode(String usercode) {
        this.usercode = usercode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Long getSmokingReportId() {
        return smokingReportId;
    }

    public void setSmokingReportId(Long smokingReportId) {
        this.smokingReportId = smokingReportId;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getSmokingReportDesc() {
        return smokingReportDesc;
    }

    public void setSmokingReportDesc(String smokingReportDesc) {
        this.smokingReportDesc = smokingReportDesc;
    }

    public Integer getSmokingReportScore() {
        return smokingReportScore;
    }

    public void setSmokingReportScore(Integer smokingReportScore) {
        this.smokingReportScore = smokingReportScore;
    }

    public String getSmokingReportLevel() {
        return smokingReportLevel;
    }

    public void setSmokingReportLevel(String smokingReportLevel) {
        this.smokingReportLevel = smokingReportLevel;
    }

    public String getSmokingReportQueselect() {
        return smokingReportQueselect;
    }

    public void setSmokingReportQueselect(String smokingReportQueselect) {
        this.smokingReportQueselect = smokingReportQueselect;
    }

    public Date getQtime() {
        return qtime;
    }

    public void setQtime(Date qtime) {
        this.qtime = qtime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("smokingReportId", getSmokingReportId())
                .append("userCode", getUserCode())
                .append("smokingReportDesc", getSmokingReportDesc())
                .append("smokingReportScore", getSmokingReportScore())
                .append("smokingReportLevel", getSmokingReportLevel())
                .append("smokingReportQueselect", getSmokingReportQueselect())
                .append("qtime", getQtime())
                .toString();
    }
}
