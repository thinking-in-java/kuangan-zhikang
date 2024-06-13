package com.yihe.system.domain;

import java.util.List;
import java.util.Map;

/**
 * 信息概要封装实体类
 * @author Administrator
 *
 */
public class InformationSummary {
/**
 * 用户编号
 */
	private String user_code;
	
	private String user_name;
	
	private int age;
	
	private String birth_date;
	
	private int[] fooddataweek;//本周
	private int[] fooddatamonth;//本月
	private String[] fooddatamonthTime;
	private int[] fooddatayear;//本年
	
	private int[] sportdataweek;//本周
	private int[] sportdatamonth;//本月
	
	private int[] sportdatayear;//本年
	
	private int[] sleepdataweek;//本周
	private int[] sleepdatamonth;//本月
	
	private int[] sleepdatayear;//本年
	
	//是否干预
	private int interveneYesOrNo;
	//干预分组
	private List<YHDocGroup> groupList;
	
	List<String> goodList;
	List<String> nogoodList;
	
	
	private int day;
	private int daynum;
	
	
	  

	public List<String> getGoodList() {
		return goodList;
	}

	public void setGoodList(List<String> goodList) {
		this.goodList = goodList;
	}

	public List<String> getNogoodList() {
		return nogoodList;
	}

	public void setNogoodList(List<String> nogoodList) {
		this.nogoodList = nogoodList;
	}

	public int[] getSleepdataweek() {
		return sleepdataweek;
	}

	public void setSleepdataweek(int[] sleepdataweek) {
		this.sleepdataweek = sleepdataweek;
	}

	public int[] getSleepdatamonth() {
		return sleepdatamonth;
	}

	public void setSleepdatamonth(int[] sleepdatamonth) {
		this.sleepdatamonth = sleepdatamonth;
	}

	public int[] getSleepdatayear() {
		return sleepdatayear;
	}

	public void setSleepdatayear(int[] sleepdatayear) {
		this.sleepdatayear = sleepdatayear;
	}

	public String[] getFooddatamonthTime() {
		return fooddatamonthTime;
	}

	public void setFooddatamonthTime(String[] fooddatamonthTime) {
		this.fooddatamonthTime = fooddatamonthTime;
	}

	public int[] getFooddataweek() {
		return fooddataweek;
	}

	public void setFooddataweek(int[] fooddataweek) {
		this.fooddataweek = fooddataweek;
	}

	public int[] getFooddatamonth() {
		return fooddatamonth;
	}

	public void setFooddatamonth(int[] fooddatamonth) {
		this.fooddatamonth = fooddatamonth;
	}

	public int[] getFooddatayear() {
		return fooddatayear;
	}

	public void setFooddatayear(int[] fooddatayear) {
		this.fooddatayear = fooddatayear;
	}

	public int[] getSportdataweek() {
		return sportdataweek;
	}

	public void setSportdataweek(int[] sportdataweek) {
		this.sportdataweek = sportdataweek;
	}

	public int[] getSportdatamonth() {
		return sportdatamonth;
	}

	public void setSportdatamonth(int[] sportdatamonth) {
		this.sportdatamonth = sportdatamonth;
	}

	public int[] getSportdatayear() {
		return sportdatayear;
	}

	public void setSportdatayear(int[] sportdatayear) {
		this.sportdatayear = sportdatayear;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public int getDaynum() {
		return daynum;
	}

	public void setDaynum(int daynum) {
		this.daynum = daynum;
	}
 
	/**
	 * 健康评估时间
	 * @return
	 */
	private String  assessTime;
	//数据监测最新时间
	private String  monitorTime;
	//习惯评估最新时间
	private String habitTime;
	//压力最新评估
	private String pressureTime;
	//档案更新时间
	private String archivesTime;
	//最新睡眠评估时间
	private String sleepNewTime;
	//注册时间
	private String registerTime;
	
	//评估高风险信息
	private String gaofxInfo;
	//评估中等风险信息
	private String zhongfxInfo;
	//患病信息
	private String huanInfo;
	
	
	//风险评估列表
	private List<YhDiseaseAssessReport> assessMap;
	
	//健康评估次数
	private int assesscount;
	
	//习惯评估次数
	private int habitcount;
	
	//健康计划种类条数
	private int planTypeCount;
	//健康计划日记天数条数
	private int planDairyCount;
	
	//睡眠评估次数
	private int sleepcount;
	//压力评估次数
	private int pressurecount;
	
	//膳食日记次数
	private int foodDiarycount;
	
	//运动日记次数
	private int sportDiarycount;
	//习惯日记次数
	private int habitDiarycount;
	
	//睡眠日记天数
	private int sleepDiarycount;
	
	//课堂已阅次数
	private int classcount;
	
	//评论和回复条数
	
	private int commentcount;
	
	//记录膳食日记食物条数
	private int foodTypeCount;
	
	//记录运动日记运动类型条数
	private int sportTypeCount;
	
	
	private String consult;
	
	
	
	
	
	
	public String getConsult() {
		return consult;
	}

	public void setConsult(String consult) {
		this.consult = consult;
	}

	public int getInterveneYesOrNo() {
		return interveneYesOrNo;
	}

	public void setInterveneYesOrNo(int interveneYesOrNo) {
		this.interveneYesOrNo = interveneYesOrNo;
	}

	public List<YHDocGroup> getGroupList() {
		return groupList;
	}

	public void setGroupList(List<YHDocGroup> groupList) {
		this.groupList = groupList;
	}

	public String getGaofxInfo() {
		return gaofxInfo;
	}

	public void setGaofxInfo(String gaofxInfo) {
		this.gaofxInfo = gaofxInfo;
	}

	public String getZhongfxInfo() {
		return zhongfxInfo;
	}

	public void setZhongfxInfo(String zhongfxInfo) {
		this.zhongfxInfo = zhongfxInfo;
	}

	public String getHuanInfo() {
		return huanInfo;
	}

	public void setHuanInfo(String huanInfo) {
		this.huanInfo = huanInfo;
	}

	public int getPlanTypeCount() {
		return planTypeCount;
	}

	public void setPlanTypeCount(int planTypeCount) {
		this.planTypeCount = planTypeCount;
	}

	public int getPlanDairyCount() {
		return planDairyCount;
	}

	public void setPlanDairyCount(int planDairyCount) {
		this.planDairyCount = planDairyCount;
	}

	public String getSleepNewTime() {
		return sleepNewTime;
	}

	public void setSleepNewTime(String sleepNewTime) {
		this.sleepNewTime = sleepNewTime;
	}

	public int getAssesscount() {
		return assesscount;
	}

	public void setAssesscount(int assesscount) {
		this.assesscount = assesscount;
	}

	public int getHabitcount() {
		return habitcount;
	}

	public void setHabitcount(int habitcount) {
		this.habitcount = habitcount;
	}

	public int getSleepcount() {
		return sleepcount;
	}

	public void setSleepcount(int sleepcount) {
		this.sleepcount = sleepcount;
	}

	public int getPressurecount() {
		return pressurecount;
	}

	public void setPressurecount(int pressurecount) {
		this.pressurecount = pressurecount;
	}

	public int getFoodDiarycount() {
		return foodDiarycount;
	}

	public void setFoodDiarycount(int foodDiarycount) {
		this.foodDiarycount = foodDiarycount;
	}

	public int getSportDiarycount() {
		return sportDiarycount;
	}

	public void setSportDiarycount(int sportDiarycount) {
		this.sportDiarycount = sportDiarycount;
	}

	public int getHabitDiarycount() {
		return habitDiarycount;
	}

	public void setHabitDiarycount(int habitDiarycount) {
		this.habitDiarycount = habitDiarycount;
	}

	public int getSleepDiarycount() {
		return sleepDiarycount;
	}

	public void setSleepDiarycount(int sleepDiarycount) {
		this.sleepDiarycount = sleepDiarycount;
	}

	public int getClasscount() {
		return classcount;
	}

	public void setClasscount(int classcount) {
		this.classcount = classcount;
	}

	public int getCommentcount() {
		return commentcount;
	}

	public void setCommentcount(int commentcount) {
		this.commentcount = commentcount;
	}

	public int getFoodTypeCount() {
		return foodTypeCount;
	}

	public void setFoodTypeCount(int foodTypeCount) {
		this.foodTypeCount = foodTypeCount;
	}

	public int getSportTypeCount() {
		return sportTypeCount;
	}

	public void setSportTypeCount(int sportTypeCount) {
		this.sportTypeCount = sportTypeCount;
	}

	public List<YhDiseaseAssessReport> getAssessMap() {
		return assessMap;
	}

	public void setAssessMap(List<YhDiseaseAssessReport> assessMap) {
		this.assessMap = assessMap;
	}

	public String getAssessTime() {
		return assessTime;
	}

	public void setAssessTime(String assessTime) {
		this.assessTime = assessTime;
	}

	public String getMonitorTime() {
		return monitorTime;
	}

	public void setMonitorTime(String monitorTime) {
		this.monitorTime = monitorTime;
	}

	public String getHabitTime() {
		return habitTime;
	}

	public void setHabitTime(String habitTime) {
		this.habitTime = habitTime;
	}

	public String getPressureTime() {
		return pressureTime;
	}

	public void setPressureTime(String pressureTime) {
		this.pressureTime = pressureTime;
	}

	public String getArchivesTime() {
		return archivesTime;
	}

	public void setArchivesTime(String archivesTime) {
		this.archivesTime = archivesTime;
	}

	public String getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(String registerTime) {
		this.registerTime = registerTime;
	}

	public String getUser_code() {
		return user_code;
	}

	public void setUser_code(String user_code) {
		this.user_code = user_code;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getBirth_date() {
		return birth_date;
	}

	public void setBirth_date(String birth_date) {
		this.birth_date = birth_date;
	}

	@Override
	public String toString() {
		return "InformationSummary [user_code=" + user_code + ", user_name=" + user_name + ", age=" + age
				+ ", birth_date=" + birth_date + ", assessTime=" + assessTime + ", monitorTime=" + monitorTime
				+ ", habitTime=" + habitTime + ", pressureTime=" + pressureTime + ", archivesTime=" + archivesTime
				+ ", registerTime=" + registerTime + ", assessMap=" + assessMap + "]";
	}
	
	
	
	
	
	
	
	
	
}
