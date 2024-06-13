package com.yihe.applet.api.domain;

import java.io.Serializable;

/**
 * @version: shayu3.0
 * @Description: 睡眠评估题目数据
 * @author: lhh
 * @date: 2020年4月15日 上午11:45:52
 */
public class YhSleepQuestionApp implements Serializable{
	private static final long serialVersionUID = 1L;
	private Integer questionId;
	private Integer questionType;
	private String questionTitle;
	private String choose1;
	private String choose1Value;
	private String choose2;
	private String choose2Value;
	private String choose3;
	private String choose3Value;
	private String choose4;
	private String choose4Value;
	public Integer getQuestionId() {
		return questionId;
	}
	public void setQuestionId(Integer questionId) {
		this.questionId = questionId;
	}
	public Integer getQuestionType() {
		return questionType;
	}
	public void setQuestionType(Integer questionType) {
		this.questionType = questionType;
	}
	public String getQuestionTitle() {
		return questionTitle;
	}
	public void setQuestionTitle(String questionTitle) {
		this.questionTitle = questionTitle;
	}
	public String getChoose1() {
		return choose1;
	}
	public void setChoose1(String choose1) {
		this.choose1 = choose1;
	}
	public String getChoose1Value() {
		return choose1Value;
	}
	public void setChoose1Value(String choose1Value) {
		this.choose1Value = choose1Value;
	}
	public String getChoose2() {
		return choose2;
	}
	public void setChoose2(String choose2) {
		this.choose2 = choose2;
	}
	public String getChoose2Value() {
		return choose2Value;
	}
	public void setChoose2Value(String choose2Value) {
		this.choose2Value = choose2Value;
	}
	public String getChoose3() {
		return choose3;
	}
	public void setChoose3(String choose3) {
		this.choose3 = choose3;
	}
	public String getChoose3Value() {
		return choose3Value;
	}
	public void setChoose3Value(String choose3Value) {
		this.choose3Value = choose3Value;
	}
	public String getChoose4() {
		return choose4;
	}
	public void setChoose4(String choose4) {
		this.choose4 = choose4;
	}
	public String getChoose4Value() {
		return choose4Value;
	}
	public void setChoose4Value(String choose4Value) {
		this.choose4Value = choose4Value;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
