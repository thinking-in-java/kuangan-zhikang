package com.yihe.system.domain;
/**
 * 健康计划实体封装类
 * @author Administrator
 *
 */
public class HealthPlanVOJO {
	private String id;
private String usercode;
private String username;
private String sex;
private String name;
private Integer age;
private int planNum;
private String planStrTime;
private String planendTime;
private String planName;
private String planRunDay;
private String planState;
private String plandetailId;//该计划当日需要完成计划1;2;3;4;13
private int courseseq;//当日完成计划个数
private int planUnique;//计划次数编号
private int planDay;//计划第几天
private int planId;
private int completedNum;//已完成
private Integer completeNum;//全部需要完成

private  String[] timeInfo;
private  String[] data1;
private  String[] data2;

 


public Integer getAge() {
	return age;
}
public void setAge(Integer age) {
	this.age = age;
}
public String[] getTimeInfo() {
	return timeInfo;
}
public void setTimeInfo(String[] timeInfo) {
	this.timeInfo = timeInfo;
}
public String[] getData1() {
	return data1;
}
public void setData1(String[] data1) {
	this.data1 = data1;
}
public String[] getData2() {
	return data2;
}
public void setData2(String[] data2) {
	this.data2 = data2;
}
public String getPlanendTime() {
	return planendTime;
}
public void setPlanendTime(String planendTime) {
	this.planendTime = planendTime;
}
public int getCompletedNum() {
	return completedNum;
}
public void setCompletedNum(int completedNum) {
	this.completedNum = completedNum;
}
public Integer getCompleteNum() {
	return completeNum;
}
public void setCompleteNum(Integer completeNum) {
	this.completeNum = completeNum;
}
public String getId() {
	return id;
}
public void setId(String id) {
	this.id = id;
}
public String getPlandetailId() {
	return plandetailId;
}
public void setPlandetailId(String plandetailId) {
	this.plandetailId = plandetailId;
}
public int getCourseseq() {
	return courseseq;
}
public void setCourseseq(int courseseq) {
	this.courseseq = courseseq;
}
public int getPlanUnique() {
	return planUnique;
}
public void setPlanUnique(int planUnique) {
	this.planUnique = planUnique;
}
public int getPlanDay() {
	return planDay;
}
public void setPlanDay(int planDay) {
	this.planDay = planDay;
}
public int getPlanId() {
	return planId;
}
public void setPlanId(int planId) {
	this.planId = planId;
}
public String getPlanState() {
	return planState;
}
public void setPlanState(String planState) {
	this.planState = planState;
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
public String getSex() {
	return sex;
}
public void setSex(String sex) {
	this.sex = sex;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public int getPlanNum() {
	return planNum;
}
public void setPlanNum(int planNum) {
	this.planNum = planNum;
}
public String getPlanStrTime() {
	return planStrTime;
}
public void setPlanStrTime(String planStrTime) {
	this.planStrTime = planStrTime;
}
public String getPlanName() {
	return planName;
}
public void setPlanName(String planName) {
	this.planName = planName;
}
public String getPlanRunDay() {
	return planRunDay;
}
public void setPlanRunDay(String planRunDay) {
	this.planRunDay = planRunDay;
}



}
