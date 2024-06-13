package com.yihe.applet.api.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * @version: shayu3.0
 * @Description: 睡眠评估结果
 * @author: lhh
 * @date: 2020年4月15日 上午11:54:58
 */
public class YhSleepReportApp implements Serializable{
	private static final long serialVersionUID = 1L;
	private Integer reportId;
	private String userCode;
	private String reportDesc;
	private String reportLevel;
	private Integer reportScore;
	private String reportQueselect;
	private Date qtime;
	public Integer getReportId() {
		return reportId;
	}
	public void setReportId(Integer reportId) {
		this.reportId = reportId;
	}
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public String getReportDesc() {
		return reportDesc;
	}
	public void setReportDesc(String reportDesc) {
		this.reportDesc = reportDesc;
	}
	public String getReportLevel() {
		return reportLevel;
	}
	public void setReportLevel(String reportLevel) {
		this.reportLevel = reportLevel;
	}
	public Integer getReportScore() {
		return reportScore;
	}
	public void setReportScore(Integer reportScore) {
		this.reportScore = reportScore;
	}
	public String getReportQueselect() {
		return reportQueselect;
	}
	public void setReportQueselect(String reportQueselect) {
		this.reportQueselect = reportQueselect;
	}
	public Date getQtime() {
		return qtime;
	}
	public void setQtime(Date qtime) {
		this.qtime = qtime;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
