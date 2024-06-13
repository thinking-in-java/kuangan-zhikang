package com.yihe.system.domain;

import java.io.Serializable;
import java.util.Date;

public class HealthAssessmentVOJO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 用户编号
	 */
	private String usercode;
	
	/**
	 * 用户账户
	 */
	private String username;
	
	/**
	 * 用户姓名
	 */
	private String name;
	
	/**
	 * 性别
	 */
	
	private String sex;
	
	/**
	 * 评估次数
	 */
	
	private Integer assessmentNum;
	
	/**
	 * 高风险数
	 */
	private Integer highRiskNum;
	
	/**
	 * 高风险疾病
	 */
	private String highRiskInfo;
	
	
	/**
	 * 较高风险数
	 */
	private Integer HigherRiskNum;
	
	/**
	 * 较高风险疾病
	 */
	private String HigherRiskInfo;
	
	/**
	 * 中等风险Medium risk
	 */
	private Integer mediumRiskNum;
	
	/**
	 * 中等风险疾病
	 */
	private String mediumRiskInfo;
	
	/**
	 * 较低风险Lower risk
	 */
	private Integer lowerRiskNum;
	/**
	 * 较低风险疾病
	 */
	private String lowerRisInfo;
	
	/**
	 * 低风险Low risk
	 */
	private Integer lowRiskNum;
	
	/**
	 * 低风险疾病
	 */
	private String lowRiskInfo;
	
	
	/**
	 * 患病或者异常数
	 */
	private Integer abnormalNum;
	

	
	/**
	 * 患病或者异常疾病
	 */
	private String abnormalInfo;
	
	/**
	 * 最新评估时间
	 */
	private String newestAssessTime;
	
	private String qtime;
	
	
	 private String requestSerialNum;
	 
	 private String riskLevel;
	 
	 private String diseaseName;
	 
	 
	
	
 

	public String getRiskLevel() {
		return riskLevel;
	}

	public void setRiskLevel(String riskLevel) {
		this.riskLevel = riskLevel;
	}

	public String getDiseaseName() {
		return diseaseName;
	}

	public void setDiseaseName(String diseaseName) {
		this.diseaseName = diseaseName;
	}

	public String getRequestSerialNum() {
		return requestSerialNum;
	}

	public void setRequestSerialNum(String requestSerialNum) {
		this.requestSerialNum = requestSerialNum;
	}

	public String getQtime() {
		return qtime;
	}

	public void setQtime(String qtime) {
		this.qtime = qtime;
	}

	public String getNewestAssessTime() {
		return newestAssessTime;
	}

	public void setNewestAssessTime(String newestAssessTime) {
		this.newestAssessTime = newestAssessTime;
	}

	/**
	 * 干预
	 */
	private Integer intervene;
	
	
	
 

	public String getHighRiskInfo() {
		return highRiskInfo;
	}

	public void setHighRiskInfo(String highRiskInfo) {
		this.highRiskInfo = highRiskInfo;
	}

	public String getHigherRiskInfo() {
		return HigherRiskInfo;
	}

	public void setHigherRiskInfo(String higherRiskInfo) {
		HigherRiskInfo = higherRiskInfo;
	}

	public String getMediumRiskInfo() {
		return mediumRiskInfo;
	}

	public void setMediumRiskInfo(String mediumRiskInfo) {
		this.mediumRiskInfo = mediumRiskInfo;
	}

	public String getLowerRisInfo() {
		return lowerRisInfo;
	}

	public void setLowerRisInfo(String lowerRisInfo) {
		this.lowerRisInfo = lowerRisInfo;
	}

	public String getLowRiskInfo() {
		return lowRiskInfo;
	}

	public void setLowRiskInfo(String lowRiskInfo) {
		this.lowRiskInfo = lowRiskInfo;
	}

	public String getAbnormalInfo() {
		return abnormalInfo;
	}

	public void setAbnormalInfo(String abnormalInfo) {
		this.abnormalInfo = abnormalInfo;
	}

	public Integer getAbnormalNum() {
		return abnormalNum;
	}

	public void setAbnormalNum(Integer abnormalNum) {
		this.abnormalNum = abnormalNum;
	}

 

	public String getUsercode() {
		return usercode;
	}

	public void setUsercode(String usercode) {
		this.usercode = usercode;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Integer getAssessmentNum() {
		return assessmentNum;
	}

	public void setAssessmentNum(Integer assessmentNum) {
		this.assessmentNum = assessmentNum;
	}

	public Integer getHighRiskNum() {
		return highRiskNum;
	}

	public void setHighRiskNum(Integer highRiskNum) {
		this.highRiskNum = highRiskNum;
	}

	public Integer getHigherRiskNum() {
		return HigherRiskNum;
	}

	public void setHigherRiskNum(Integer higherRiskNum) {
		HigherRiskNum = higherRiskNum;
	}

	public Integer getMediumRiskNum() {
		return mediumRiskNum;
	}

	public void setMediumRiskNum(Integer mediumRiskNum) {
		this.mediumRiskNum = mediumRiskNum;
	}

	public Integer getLowerRiskNum() {
		return lowerRiskNum;
	}

	public void setLowerRiskNum(Integer lowerRiskNum) {
		this.lowerRiskNum = lowerRiskNum;
	}

	public Integer getLowRiskNum() {
		return lowRiskNum;
	}

	public void setLowRiskNum(Integer lowRiskNum) {
		this.lowRiskNum = lowRiskNum;
	}

	public Integer getIntervene() {
		return intervene;
	}

	public void setIntervene(Integer intervene) {
		this.intervene = intervene;
	}
	
	
	
}