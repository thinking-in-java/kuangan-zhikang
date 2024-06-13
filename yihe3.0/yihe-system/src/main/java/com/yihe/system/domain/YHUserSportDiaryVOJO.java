package com.yihe.system.domain;

import java.util.Date;

public class YHUserSportDiaryVOJO {
	private String usercode;
	private String username;
	private String name;
	private String sex;
	private String diaryTime;// 运动日记日期
	private String number;// 运动日记日期
	private String stepNumber;// 步数
	private String stepNumberCal;// 步数热量
	private String classSport;// 课程训练
	private String otherSport;// 其他运动
	private String recommendSport;// 推荐运动
	private String Whether;// 是否达标
	private String diaryDetails;// 日记内容
    private String diaryBoutique;// 精品课程日记内容

    private String diaryRecommend;// 推荐运动日记内容
	private Date birthDate;// 生日
	private Double Tenergy;// 生日
	private Double Uweight;

	
	
	@Override
	public String toString() {
		return "YHUserSportDiaryVOJO [usercode=" + usercode + ", username=" + username + ", name=" + name + ", sex="
				+ sex + ", diaryTime=" + diaryTime + ", number=" + number + ", stepNumber=" + stepNumber
				+ ", stepNumberCal=" + stepNumberCal + ", classSport=" + classSport + ", otherSport=" + otherSport
				+ ", Whether=" + Whether + ", diaryDetails=" + diaryDetails + ", birthDate=" + birthDate + ", Tenergy="
				+ Tenergy + "]";
	}

	
	public Double getUweight() {
		return Uweight;
	}



	public void setUweight(Double uweight) {
		Uweight = uweight;
	}



	public String getDiaryBoutique() {
		return diaryBoutique;
	}




	public void setDiaryBoutique(String diaryBoutique) {
		this.diaryBoutique = diaryBoutique;
	}




	public String getDiaryRecommend() {
		return diaryRecommend;
	}




	public void setDiaryRecommend(String diaryRecommend) {
		this.diaryRecommend = diaryRecommend;
	}




	public String getRecommendSport() {
		return recommendSport;
	}

	public void setRecommendSport(String recommendSport) {
		this.recommendSport = recommendSport;
	}

	public Double getTenergy() {
		return Tenergy;
	}

	public void setTenergy(Double tenergy) {
		Tenergy = tenergy;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
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

	public String getDiaryTime() {
		return diaryTime;
	}

	public void setDiaryTime(String diaryTime) {
		this.diaryTime = diaryTime;
	}

	public String getStepNumber() {
		return stepNumber;
	}

	public void setStepNumber(String stepNumber) {
		this.stepNumber = stepNumber;
	}

	public String getStepNumberCal() {
		return stepNumberCal;
	}

	public void setStepNumberCal(String stepNumberCal) {
		this.stepNumberCal = stepNumberCal;
	}

	public String getClassSport() {
		return classSport;
	}

	public void setClassSport(String classSport) {
		this.classSport = classSport;
	}

	public String getOtherSport() {
		return otherSport;
	}

	public void setOtherSport(String otherSport) {
		this.otherSport = otherSport;
	}

	public String getWhether() {
		return Whether;
	}

	public void setWhether(String whether) {
		Whether = whether;
	}

	public String getDiaryDetails() {
		return diaryDetails;
	}

	public void setDiaryDetails(String diaryDetails) {
		this.diaryDetails = diaryDetails;
	}

}
