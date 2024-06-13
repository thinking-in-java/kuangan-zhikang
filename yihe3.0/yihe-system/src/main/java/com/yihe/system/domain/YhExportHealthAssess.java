package com.yihe.system.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
//yh_disease_assess_total_report
public class YhExportHealthAssess implements Serializable{
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String result_serialNum;
	private String request_serialNum;
	private String user_code;
	private String qtime;
	private Integer gender;
	private Integer age;
	private String name;
	private String Org_Name;
	private List<YhExportRiskResult> yhexpotriskresult;
	private List<YhExportAssessReport> assessreport;
	private List<YhDiseaseRiskResult2> yhDiseaseRiskResult2s;

	public List<YhDiseaseRiskResult2> getYhDiseaseRiskResult2s() {
		return yhDiseaseRiskResult2s;
	}

	public void setYhDiseaseRiskResult2s(List<YhDiseaseRiskResult2> yhDiseaseRiskResult2s) {
		this.yhDiseaseRiskResult2s = yhDiseaseRiskResult2s;
	}

	public String getOrg_Name() {
		return Org_Name;
	}
	public void setOrg_Name(String org_Name) {
		Org_Name = org_Name;
	}
	public List<YhExportAssessReport> getAssessreport() {
		return assessreport;
	}
	public void setAssessreport(List<YhExportAssessReport> assessreport) {
		this.assessreport = assessreport;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getResult_serialNum() {
		return result_serialNum;
	}
	public void setResult_serialNum(String result_serialNum) {
		this.result_serialNum = result_serialNum;
	}
	public String getRequest_serialNum() {
		return request_serialNum;
	}
	public void setRequest_serialNum(String request_serialNum) {
		this.request_serialNum = request_serialNum;
	}
	public String getUser_code() {
		return user_code;
	}
	public void setUser_code(String user_code) {
		this.user_code = user_code;
	}
	
	public String getQtime() {
		return qtime;
	}
	public void setQtime(String qtime) {
		this.qtime = qtime;
	}
	public Integer getGender() {
		return gender;
	}
	public void setGender(Integer gender) {
		this.gender = gender;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<YhExportRiskResult> getYhexpotriskresult() {
		return yhexpotriskresult;
	}
	public void setYhexpotriskresult(List<YhExportRiskResult> yhexpotriskresult) {
		this.yhexpotriskresult = yhexpotriskresult;
	}
	@Override
	public String toString() {
		return "YhExportHealthAssess [id=" + id + ", result_serialNum=" + result_serialNum + ", request_serialNum="
				+ request_serialNum + ", user_code=" + user_code + ", qtime=" + qtime + ", gender=" + gender + ", age="
				+ age + ", name=" + name + ", yhexpotriskresult=" + yhexpotriskresult + ", assessreport=" + assessreport
				+ "]";
	}
	
}
