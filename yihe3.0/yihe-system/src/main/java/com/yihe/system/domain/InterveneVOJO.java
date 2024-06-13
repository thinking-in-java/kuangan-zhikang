package com.yihe.system.domain;

/**
 * 健康干预数据表格封装实体
 * 
 * @author Administrator
 *
 */
public class InterveneVOJO {
	
	/**
	 * 是否干预 1干预 0为干预
	 */
	private Integer evaluateFlag;
	/**
	 * 用户编号
	 */
	private String usercode;
	/**
	 * 账号
	 */
	private String username;

	/**
	 * 姓名
	 */
	private String name;
	
	/**
	 * 性别
	 */
	private String sex;
	
	/**
	 * 健康监测
	 */
	
	private String monitorTime;
	
	/**
	 * 健康评估
	 */
	private String assess;
	/**
	 * 饮食日记
	 */
	private String foodTime;
	/**
	 * 运动日记
	 */
	private String sportTime;
	/**
	 * 干预时间
	 */
	private String evaTime;
	/**
	 * 干预周期
	 */
	private String interveneTime;
	
	/**
	 * 分组
	 */
	private String grouping;
	/**
	 * 分组信息
	 */
	private String groupInfo;
	//最新睡眠日记时间
	private String sleepNewTime;
	
	/**
	 * jihua 0为保留原有干预计划 1为清除原有干预计划
	 * @return
	 */
	private String jihua;
	
	private String consult;
	
	
	
	
	public String getConsult() {
		return consult;
	}

	public void setConsult(String consult) {
		this.consult = consult;
	}

	public String getJihua() {
		return jihua;
	}

	public void setJihua(String jihua) {
		this.jihua = jihua;
	}

	public String getSleepNewTime() {
		return sleepNewTime;
	}

	public void setSleepNewTime(String sleepNewTime) {
		this.sleepNewTime = sleepNewTime;
	}

	public String getGroupInfo() {
		return groupInfo;
	}

	public void setGroupInfo(String groupInfo) {
		this.groupInfo = groupInfo;
	}

	/**
	 * 分组字段信息
	 * @return
	 */
	private String groupingStr;
	
	
	/**
	 * bmiTime
	 * 体重数据新增时间
	 * @return
	 */
	private String bmiTime;
	
	/**
	 * 血压数据新增时间
	 * @return
	 */
	
	private String bloodPreTime;
	
	/**
	 * 血糖数据新增时间
	 * @return
	 */
	
	private String bsTime;
	
	

	public Integer getEvaluateFlag() {
		return evaluateFlag;
	}

	public void setEvaluateFlag(Integer evaluateFlag) {
		this.evaluateFlag = evaluateFlag;
	}

	public String getBmiTime() {
		return bmiTime;
	}

	public void setBmiTime(String bmiTime) {
		this.bmiTime = bmiTime;
	}

	public String getBloodPreTime() {
		return bloodPreTime;
	}

	public void setBloodPreTime(String bloodPreTime) {
		this.bloodPreTime = bloodPreTime;
	}

	public String getBsTime() {
		return bsTime;
	}

	public void setBsTime(String bsTime) {
		this.bsTime = bsTime;
	}

	public String getEvaTime() {
		return evaTime;
	}

	public void setEvaTime(String evaTime) {
		this.evaTime = evaTime;
	}

	public String getGroupingStr() {
		return groupingStr;
	}

	public void setGroupingStr(String groupingStr) {
		this.groupingStr = groupingStr;
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

	public String getMonitorTime() {
		return monitorTime;
	}

	public void setMonitorTime(String monitorTime) {
		this.monitorTime = monitorTime;
	}

	public String getAssess() {
		return assess;
	}

	public void setAssess(String assess) {
		this.assess = assess;
	}

	public String getFoodTime() {
		return foodTime;
	}

	public void setFoodTime(String foodTime) {
		this.foodTime = foodTime;
	}

	public String getSportTime() {
		return sportTime;
	}

	public void setSportTime(String sportTime) {
		this.sportTime = sportTime;
	}

	public String getInterveneTime() {
		return interveneTime;
	}

	public void setInterveneTime(String interveneTime) {
		this.interveneTime = interveneTime;
	}

	public String getGrouping() {
		return grouping;
	}

	public void setGrouping(String grouping) {
		this.grouping = grouping;
	}

	@Override
	public String toString() {
		return "InterveneVOJO [evaluateFlag=" + evaluateFlag + ", usercode=" + usercode + ", username=" + username
				+ ", name=" + name + ", sex=" + sex + ", monitorTime=" + monitorTime + ", assess=" + assess
				+ ", foodTime=" + foodTime + ", sportTime=" + sportTime + ", evaTime=" + evaTime + ", interveneTime="
				+ interveneTime + ", grouping=" + grouping + ", groupInfo=" + groupInfo + ", sleepNewTime="
				+ sleepNewTime + ", groupingStr=" + groupingStr + ", bmiTime=" + bmiTime + ", bloodPreTime="
				+ bloodPreTime + ", bsTime=" + bsTime + "]";
	}
	
	
	
}
