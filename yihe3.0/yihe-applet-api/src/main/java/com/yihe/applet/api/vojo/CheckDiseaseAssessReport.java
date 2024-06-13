package com.yihe.applet.api.vojo;

import java.io.Serializable;

import com.yihe.applet.api.domain.YhDiseaseAssessTotalReportApp;
import com.yihe.system.domain.YHDiseaseAssessResolve;
import com.yihe.system.domain.YhDiseaseAssessReport;
import com.yihe.system.domain.YhDiseaseRiskFactorGroups;


/** 
 *@author lll 
 *@Email:2982564698@qq.com 
 *@phone:17608493351
 *@date 创建时间：2019年12月17日 下午4:44:27 
 *@version 1.0 
 */
public class CheckDiseaseAssessReport implements Serializable{
	
	

	private YhDiseaseAssessReport yhDiseaseAssessReport;
	
	private String tmep;
	private String bimStatus;
	private String bimStep;
	private String time;
	private YHDiseaseAssessResolve[] yhDiseaseAssessResolve;
	
	private YhDiseaseRiskFactorGroups[] yhDiseaseRiskFactorMoreGroups;
	
	private YhDiseaseRiskFactorGroups[] yhDiseaseRiskFactorGroups;
	
	private YhDiseaseAssessTotalReportApp yhDiseaseAssessTotalReport;

	public YhDiseaseAssessReport getYhDiseaseAssessReport() {
		return yhDiseaseAssessReport;
	}

	public void setYhDiseaseAssessReport(YhDiseaseAssessReport yhDiseaseAssessReport) {
		this.yhDiseaseAssessReport = yhDiseaseAssessReport;
	}

	public YHDiseaseAssessResolve[] getYhDiseaseAssessResolve() {
		return yhDiseaseAssessResolve;
	}

	public void setYhDiseaseAssessResolve(YHDiseaseAssessResolve[] yhDiseaseAssessResolve) {
		this.yhDiseaseAssessResolve = yhDiseaseAssessResolve;
	}

	public YhDiseaseRiskFactorGroups[] getYhDiseaseRiskFactorGroups() {
		return yhDiseaseRiskFactorGroups;
	}

	public void setYhDiseaseRiskFactorGroups(YhDiseaseRiskFactorGroups[] yhDiseaseRiskFactorGroups) {
		this.yhDiseaseRiskFactorGroups = yhDiseaseRiskFactorGroups;
	}

	public YhDiseaseAssessTotalReportApp getYhDiseaseAssessTotalReport() {
		return yhDiseaseAssessTotalReport;
	}

	public void setYhDiseaseAssessTotalReport(YhDiseaseAssessTotalReportApp yhDiseaseAssessTotalReport) {
		this.yhDiseaseAssessTotalReport = yhDiseaseAssessTotalReport;
	}

	public String getTmep() {
		return tmep;
	}

	public void setTmep(String tmep) {
		this.tmep = tmep;
	}

	public String getBimStatus() {
		return bimStatus;
	}

	public void setBimStatus(String bimStatus) {
		this.bimStatus = bimStatus;
	}

	public String getBimStep() {
		return bimStep;
	}

	public void setBimStep(String bimStep) {
		this.bimStep = bimStep;
	}

	public YhDiseaseRiskFactorGroups[] getYhDiseaseRiskFactorMoreGroups() {
		return yhDiseaseRiskFactorMoreGroups;
	}

	public void setYhDiseaseRiskFactorMoreGroups(YhDiseaseRiskFactorGroups[] yhDiseaseRiskFactorMoreGroups) {
		this.yhDiseaseRiskFactorMoreGroups = yhDiseaseRiskFactorMoreGroups;
	}

	
	
	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public CheckDiseaseAssessReport(YhDiseaseAssessReport yhDiseaseAssessReport, String tmep, String bimStatus,
			String bimStep, String time, YHDiseaseAssessResolve[] yhDiseaseAssessResolve,
			YhDiseaseRiskFactorGroups[] yhDiseaseRiskFactorMoreGroups,
			YhDiseaseRiskFactorGroups[] yhDiseaseRiskFactorGroups,
			YhDiseaseAssessTotalReportApp yhDiseaseAssessTotalReport) {
		super();
		this.yhDiseaseAssessReport = yhDiseaseAssessReport;
		this.tmep = tmep;
		this.bimStatus = bimStatus;
		this.bimStep = bimStep;
		this.time = time;
		this.yhDiseaseAssessResolve = yhDiseaseAssessResolve;
		this.yhDiseaseRiskFactorMoreGroups = yhDiseaseRiskFactorMoreGroups;
		this.yhDiseaseRiskFactorGroups = yhDiseaseRiskFactorGroups;
		this.yhDiseaseAssessTotalReport = yhDiseaseAssessTotalReport;
	}

	public CheckDiseaseAssessReport() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	

	

	
	
	
	
	
}
