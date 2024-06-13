package com.yihe.system.domain;

import java.io.Serializable;
import java.util.Date;

public class YhDiseaseAssessReportVOJO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 请求序列号
	 */
	
	private String requestNum;
	
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
	
	/**
	 * 总记录条数
	 * @return
	 */
	private Integer count;
	/**
	 * 每页显示的条数
	 */
	private Integer limit;
	
	/**
	 * 分组信息
	 * @return
	 */
	private String flagDgroup;
	
	/**
	 * 评估次数
	 * @return
	 */
	private Integer times;
	
	/**
	 * 评估时间
	 * @return
	 */
	private String qtime;
	
	/**
	 * 高风险项 ;号分割
	 * @return
	 */
	private String bfnames;
	
	//患或异常疾病
	private String sknames;
	//中危疾病
	private String ldnames;
	//低危疾病
	private String lrnames;
	
	private float bmi;
	
	
	
	
	
 

	public float getBmi() {
		return bmi;
	}

	public void setBmi(float bmi) {
		this.bmi = bmi;
	}

	public String getFlagDgroup() {
		return flagDgroup;
	}

	public void setFlagDgroup(String flagDgroup) {
		this.flagDgroup = flagDgroup;
	}

	public Integer getTimes() {
		return times;
	}

	public void setTimes(Integer times) {
		this.times = times;
	}

	public String getQtime() {
		return qtime;
	}

	public void setQtime(String qtime) {
		this.qtime = qtime;
	}

	public String getBfnames() {
		return bfnames;
	}

	public void setBfnames(String bfnames) {
		this.bfnames = bfnames;
	}

	public String getSknames() {
		return sknames;
	}

	public void setSknames(String sknames) {
		this.sknames = sknames;
	}

	public String getLdnames() {
		return ldnames;
	}

	public void setLdnames(String ldnames) {
		this.ldnames = ldnames;
	}

	public String getLrnames() {
		return lrnames;
	}

	public void setLrnames(String lrnames) {
		this.lrnames = lrnames;
	}

	public String getRequestNum() {
		return requestNum;
	}

	public void setRequestNum(String requestNum) {
		this.requestNum = requestNum;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
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