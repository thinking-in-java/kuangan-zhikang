package com.yihe.system.domain;

public class YHDiseaseAssessResolve {
private Integer id;

private String resultSerialNum;
private Integer diseaseId;
private String type;
private String name;
private String improve;
public Integer getId() {
	return id;
}
public void setId(Integer id) {
	this.id = id;
}
public String getResultSerialNum() {
	return resultSerialNum;
}
public void setResultSerialNum(String resultSerialNum) {
	this.resultSerialNum = resultSerialNum;
}
public Integer getDiseaseId() {
	return diseaseId;
}
public void setDiseaseId(Integer diseaseId) {
	this.diseaseId = diseaseId;
}
public String getType() {
	return type;
}
public void setType(String type) {
	this.type = type;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public  YHDiseaseAssessResolve(){}

public String getImprove() {
	return improve;
}
public void setImprove(String improve) {
	this.improve = improve;
}
public YHDiseaseAssessResolve(Integer id, String resultSerialNum, Integer diseaseId, String type, String name,
		String improve) {
	super();
	this.id = id;
	this.resultSerialNum = resultSerialNum;
	this.diseaseId = diseaseId;
	this.type = type;
	this.name = name;
	this.improve = improve;
}



}
