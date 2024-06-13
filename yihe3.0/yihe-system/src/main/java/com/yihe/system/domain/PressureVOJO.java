package com.yihe.system.domain;

/**
 * 压力评估管理封装实体
 * 
 * @author Administrator
 *
 */
public class PressureVOJO {
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
	 * 评估分数
	 */
	private String evaluateScore;
	
	/**
	 * 评估次数
	 */
	private String assessmentNum;
	/**
	 * 最终评估时间
	 */
	private String assessmentNewTime;
	/**
	 * A型性格
	 */
	private String type1;
	/**
	 * PSTRI压力
	 */
	private String type2;
	
	/**
	 * 心理健康
	 */
	private String type3;
	 
	/**
	 * 评估内容
	 */
	private String assessmentContent;
	/**
	 * 评估结果
	 */
	private String assessmentResults;

	/**
	 * 评估时间
	 */
	private String assessmentTime;
	/**
	 * 评估类型
	 */
	private String assessmentType;
	
	private Integer id;
	
	
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAssessmentType() {
		return assessmentType;
	}

	public void setAssessmentType(String assessmentType) {
		this.assessmentType = assessmentType;
	}

	public String getEvaluateScore() {
		return evaluateScore;
	}

	public void setEvaluateScore(String evaluateScore) {
		this.evaluateScore = evaluateScore;
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

	public String getAssessmentNum() {
		return assessmentNum;
	}

	public void setAssessmentNum(String assessmentNum) {
		this.assessmentNum = assessmentNum;
	}

	public String getAssessmentNewTime() {
		return assessmentNewTime;
	}

	public void setAssessmentNewTime(String assessmentNewTime) {
		this.assessmentNewTime = assessmentNewTime;
	}

	public String getType1() {
		return type1;
	}

	public void setType1(String type1) {
		this.type1 = type1;
	}

	public String getType2() {
		return type2;
	}

	public void setType2(String type2) {
		this.type2 = type2;
	}

	public String getType3() {
		return type3;
	}

	public void setType3(String type3) {
		this.type3 = type3;
	}

	public String getAssessmentContent() {
		return assessmentContent;
	}

	public void setAssessmentContent(String assessmentContent) {
		this.assessmentContent = assessmentContent;
	}

	public String getAssessmentResults() {
		return assessmentResults;
	}

	public void setAssessmentResults(String assessmentResults) {
		this.assessmentResults = assessmentResults;
	}

	public String getAssessmentTime() {
		return assessmentTime;
	}

	public void setAssessmentTime(String assessmentTime) {
		this.assessmentTime = assessmentTime;
	}
	
	
	
	
}
