package com.yihe.system.domain;

import java.util.Date;

/**
 * 医生控制台封装实体类
 * @author Administrator
 *
 */
public class doctorConsoleVOJO {
	private String usercode;
	private int gender;
	private Date birthDate;
	private String regtime;
	private String regMonthTime;
	private int regnum;
	private int regMonthNum;
	
	private int ConsultationNum;//咨询数
	private int healthAlarmNum;//告警数
	
	private int useCardNum;//已使用注册卡数
	private int surplusCardNum;//剩余注册卡数
	private int manNum;//男人数
	private int womanNum;//女人数
	private int ageType1;//年龄25岁及以下
	private int ageType2;//年龄26-35
	private int ageType3;//年龄36-45
	private int ageType4;//年龄46-55岁以上
	private int ageType5;//年龄56-60岁以上
	private int ageType6;//年龄60岁以上
	
	//今日新增数据
	private int registerNum;//注册人数
	private int healthAssessmentNum;//健康评估人数
	private int healthMonitoringNum;//健康监测人数
	private int habitAssessmentNum;//习惯评估人数
	private int pressureAssessmentNum;//压力评估人数
	private int sleepAssessmentNum;//睡眠评估人数
	private int foodDiaryNum;//饮食日记人数
	private int sportDiaryNum;//运动日记人数
	private int habitDiaryNum;//习惯日记人数
	private int sleepDiaryNum;//运动日记人数
	private int healthPlanDiaryNum;//运动日记人数
	
	
	//数据趋势图数据
	private int[] regdataInfo;
	private String[] regtimeList;
	
	//评估人数
	private  int assessment;
	//未评估人数
	private  int notassessment;

	private int beillNum;
	private int notBeillNum;

	private int highRisk;

	public int getBeillNum() {
		return beillNum;
	}

	public void setBeillNum(int beillNum) {
		this.beillNum = beillNum;
	}

	public int getNotBeillNum() {
		return notBeillNum;
	}

	public void setNotBeillNum(int notBeillNum) {
		this.notBeillNum = notBeillNum;
	}

	public int getHighRisk() {
		return highRisk;
	}

	public void setHighRisk(int highRisk) {
		this.highRisk = highRisk;
	}

	public int getOtherRisk() {
		return otherRisk;
	}

	public void setOtherRisk(int otherRisk) {
		this.otherRisk = otherRisk;
	}

	private int otherRisk;


	public int getAssessment() {
		return assessment;
	}

	public void setAssessment(int assessment) {
		this.assessment = assessment;
	}

	public int getNotassessment() {
		return notassessment;
	}

	public void setNotassessment(int notassessment) {
		this.notassessment = notassessment;
	}

	public int getAgeType5() {
		return ageType5;
	}


	public void setAgeType5(int ageType5) {
		this.ageType5 = ageType5;
	}


	public int getAgeType6() {
		return ageType6;
	}


	public void setAgeType6(int ageType6) {
		this.ageType6 = ageType6;
	}


	public String getRegMonthTime() {
		return regMonthTime;
	}


	public void setRegMonthTime(String regMonthTime) {
		this.regMonthTime = regMonthTime;
	}


	public int getRegMonthNum() {
		return regMonthNum;
	}


	public void setRegMonthNum(int regMonthNum) {
		this.regMonthNum = regMonthNum;
	}


	public String getUsercode() {
		return usercode;
	}


	public void setUsercode(String usercode) {
		this.usercode = usercode;
	}


	public int getGender() {
		return gender;
	}


	public void setGender(int gender) {
		this.gender = gender;
	}


	public Date getBirthDate() {
		return birthDate;
	}


	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}


	public String getRegtime() {
		return regtime;
	}


	public void setRegtime(String regtime) {
		this.regtime = regtime;
	}


	public int getRegnum() {
		return regnum;
	}


	public void setRegnum(int regnum) {
		this.regnum = regnum;
	}


	public int getConsultationNum() {
		return ConsultationNum;
	}


	public void setConsultationNum(int consultationNum) {
		ConsultationNum = consultationNum;
	}


	public int getHealthAlarmNum() {
		return healthAlarmNum;
	}


	public void setHealthAlarmNum(int healthAlarmNum) {
		this.healthAlarmNum = healthAlarmNum;
	}


	public int getUseCardNum() {
		return useCardNum;
	}


	public void setUseCardNum(int useCardNum) {
		this.useCardNum = useCardNum;
	}


	public int getSurplusCardNum() {
		return surplusCardNum;
	}


	public void setSurplusCardNum(int surplusCardNum) {
		this.surplusCardNum = surplusCardNum;
	}


	public int getManNum() {
		return manNum;
	}


	public void setManNum(int manNum) {
		this.manNum = manNum;
	}


	public int getWomanNum() {
		return womanNum;
	}


	public void setWomanNum(int womanNum) {
		this.womanNum = womanNum;
	}


	public int getAgeType1() {
		return ageType1;
	}


	public void setAgeType1(int ageType1) {
		this.ageType1 = ageType1;
	}


	public int getAgeType2() {
		return ageType2;
	}


	public void setAgeType2(int ageType2) {
		this.ageType2 = ageType2;
	}


	public int getAgeType3() {
		return ageType3;
	}


	public void setAgeType3(int ageType3) {
		this.ageType3 = ageType3;
	}


	public int getAgeType4() {
		return ageType4;
	}


	public void setAgeType4(int ageType4) {
		this.ageType4 = ageType4;
	}


	public int getRegisterNum() {
		return registerNum;
	}


	public void setRegisterNum(int registerNum) {
		this.registerNum = registerNum;
	}


	public int getHealthAssessmentNum() {
		return healthAssessmentNum;
	}


	public void setHealthAssessmentNum(int healthAssessmentNum) {
		this.healthAssessmentNum = healthAssessmentNum;
	}


	public int getHealthMonitoringNum() {
		return healthMonitoringNum;
	}


	public void setHealthMonitoringNum(int healthMonitoringNum) {
		this.healthMonitoringNum = healthMonitoringNum;
	}


	public int getHabitAssessmentNum() {
		return habitAssessmentNum;
	}


	public void setHabitAssessmentNum(int habitAssessmentNum) {
		this.habitAssessmentNum = habitAssessmentNum;
	}


	public int getPressureAssessmentNum() {
		return pressureAssessmentNum;
	}


	public void setPressureAssessmentNum(int pressureAssessmentNum) {
		this.pressureAssessmentNum = pressureAssessmentNum;
	}


	public int getSleepAssessmentNum() {
		return sleepAssessmentNum;
	}


	public void setSleepAssessmentNum(int sleepAssessmentNum) {
		this.sleepAssessmentNum = sleepAssessmentNum;
	}


	public int getFoodDiaryNum() {
		return foodDiaryNum;
	}


	public void setFoodDiaryNum(int foodDiaryNum) {
		this.foodDiaryNum = foodDiaryNum;
	}


	public int getSportDiaryNum() {
		return sportDiaryNum;
	}


	public void setSportDiaryNum(int sportDiaryNum) {
		this.sportDiaryNum = sportDiaryNum;
	}


	public int getHabitDiaryNum() {
		return habitDiaryNum;
	}


	public void setHabitDiaryNum(int habitDiaryNum) {
		this.habitDiaryNum = habitDiaryNum;
	}


	public int getSleepDiaryNum() {
		return sleepDiaryNum;
	}


	public void setSleepDiaryNum(int sleepDiaryNum) {
		this.sleepDiaryNum = sleepDiaryNum;
	}


	public int getHealthPlanDiaryNum() {
		return healthPlanDiaryNum;
	}


	public void setHealthPlanDiaryNum(int healthPlanDiaryNum) {
		this.healthPlanDiaryNum = healthPlanDiaryNum;
	}


	public int[] getRegdataInfo() {
		return regdataInfo;
	}


	public void setRegdataInfo(int[] regdataInfo) {
		this.regdataInfo = regdataInfo;
	}


	public String[] getRegtimeList() {
		return regtimeList;
	}


	public void setRegtimeList(String[] regtimeList) {
		this.regtimeList = regtimeList;
	}

 
	
	
	
 }
