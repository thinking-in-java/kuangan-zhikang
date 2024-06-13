package com.yihe.applet.api.vojo;

import java.io.Serializable;

/** 
 *@author lll 
 *@Email:2982564698@qq.com 
 *@phone:17608493351
 *@date 创建时间：2019年12月9日 下午4:52:27 
 *@version 1.0 
 */
public class YhQuestionAssessTitleJson implements Serializable{
	
	// @Fields serialVersionUID : TODO
	private static final long serialVersionUID = 1L;
	private Integer questionIndex;
	private Integer questionZIndex;
	private String questionTitle;
	private String questionItemName;
	private String questionValue;
	private YhQuestionAssessTitleJson[] childrenYhQuestionAssessTitleJson;
	private Integer questionType;
	private Integer questionGender;
	private Integer questionParentId;
	private QuestionNames[] questionNames;
	public Integer getQuestionIndex() {
		return questionIndex;
	}
	public void setQuestionIndex(Integer questionIndex) {
		this.questionIndex = questionIndex;
	}
	public Integer getQuestionZIndex() {
		return questionZIndex;
	}
	public void setQuestionZIndex(Integer questionZIndex) {
		this.questionZIndex = questionZIndex;
	}
	public String getQuestionTitle() {
		return questionTitle;
	}
	public void setQuestionTitle(String questionTitle) {
		this.questionTitle = questionTitle;
	}
	public Integer getQuestionType() {
		return questionType;
	}
	public void setQuestionType(Integer questionType) {
		this.questionType = questionType;
	}
	public Integer getQuestionParentId() {
		return questionParentId;
	}
	public void setQuestionParentId(Integer questionParentId) {
		this.questionParentId = questionParentId;
	}
	public QuestionNames[] getQuestionNames() {
		return questionNames;
	}
	public void setQuestionNames(QuestionNames[] questionNames) {
		this.questionNames = questionNames;
	}
	
	public Integer getQuestionGender() {
		return questionGender;
	}
	public void setQuestionGender(Integer questionGender) {
		this.questionGender = questionGender;
	}
	
	
	public String getQuestionItemName() {
		return questionItemName;
	}
	public void setQuestionItemName(String questionItemName) {
		this.questionItemName = questionItemName;
	}
	public String getQuestionValue() {
		return questionValue;
	}
	public void setQuestionValue(String questionValue) {
		this.questionValue = questionValue;
	}
	
	public YhQuestionAssessTitleJson[] getChildrenYhQuestionAssessTitleJson() {
		return childrenYhQuestionAssessTitleJson;
	}
	public void setChildrenYhQuestionAssessTitleJson(YhQuestionAssessTitleJson[] childrenYhQuestionAssessTitleJson) {
		this.childrenYhQuestionAssessTitleJson = childrenYhQuestionAssessTitleJson;
	}
	public YhQuestionAssessTitleJson() {
		super();
		// TODO Auto-generated constructor stub
	}
	public YhQuestionAssessTitleJson(Integer questionIndex, Integer questionZIndex, String questionTitle,
			String questionItemName, String questionValue, YhQuestionAssessTitleJson[] childrenYhQuestionAssessTitleJson,
			Integer questionType, Integer questionGender, Integer questionParentId, QuestionNames[] questionNames) {
		super();
		this.questionIndex = questionIndex;
		this.questionZIndex = questionZIndex;
		this.questionTitle = questionTitle;
		this.questionItemName = questionItemName;
		this.questionValue = questionValue;
		this.childrenYhQuestionAssessTitleJson = childrenYhQuestionAssessTitleJson;
		this.questionType = questionType;
		this.questionGender = questionGender;
		this.questionParentId = questionParentId;
		this.questionNames = questionNames;
	}
	
	
	
	
}
