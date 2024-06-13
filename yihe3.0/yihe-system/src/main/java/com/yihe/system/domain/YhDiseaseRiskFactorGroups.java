package com.yihe.system.domain;

import java.io.Serializable;

/** 
 *@author dyb 
 *@date 创建时间：2020年06月05日 下午4:56:34 
 *@version 1.0 
 */
public class YhDiseaseRiskFactorGroups implements Serializable{
	
	// @Fields serialVersionUID : TODO
	private static final long serialVersionUID = 1L;
	private Integer diseaseId;
	private String name;
	private Integer dangqian;
	private Integer lixiang;

	private String diseaseRiskFactorName;
	
	private Integer diseaseRiskFactorBadge;
	
	private Integer diseaseRiskZIndex;
	
	
	private String diseaseRiskFactorGroup;
	
	private Integer diseaseRiskFactorSegment;
	
	private QuestionNames[] questionNames;
	
	private String diseaseRiskFactorColor;
	
	

	public Integer getDiseaseId() {
		return diseaseId;
	}

	public void setDiseaseId(Integer diseaseId) {
		this.diseaseId = diseaseId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getDangqian() {
		return dangqian;
	}

	public void setDangqian(Integer dangqian) {
		this.dangqian = dangqian;
	}

	public Integer getLixiang() {
		return lixiang;
	}

	public void setLixiang(Integer lixiang) {
		this.lixiang = lixiang;
	}

	public String getDiseaseRiskFactorName() {
		return diseaseRiskFactorName;
	}

	public void setDiseaseRiskFactorName(String diseaseRiskFactorName) {
		this.diseaseRiskFactorName = diseaseRiskFactorName;
	}

	public Integer getDiseaseRiskFactorBadge() {
		return diseaseRiskFactorBadge;
	}

	public void setDiseaseRiskFactorBadge(Integer diseaseRiskFactorBadge) {
		this.diseaseRiskFactorBadge = diseaseRiskFactorBadge;
	}

	public String getDiseaseRiskFactorGroup() {
		return diseaseRiskFactorGroup;
	}

	public void setDiseaseRiskFactorGroup(String diseaseRiskFactorGroup) {
		this.diseaseRiskFactorGroup = diseaseRiskFactorGroup;
	}

	public Integer getDiseaseRiskFactorSegment() {
		return diseaseRiskFactorSegment;
	}

	public void setDiseaseRiskFactorSegment(Integer diseaseRiskFactorSegment) {
		this.diseaseRiskFactorSegment = diseaseRiskFactorSegment;
	}

	
	
	public Integer getDiseaseRiskZIndex() {
		return diseaseRiskZIndex;
	}

	public void setDiseaseRiskZIndex(Integer diseaseRiskZIndex) {
		this.diseaseRiskZIndex = diseaseRiskZIndex;
	}

	public QuestionNames[] getQuestionNames() {
		return questionNames;
	}

	public void setQuestionNames(QuestionNames[] questionNames) {
		this.questionNames = questionNames;
	}

	public String getDiseaseRiskFactorColor() {
		return diseaseRiskFactorColor;
	}

	public void setDiseaseRiskFactorColor(String diseaseRiskFactorColor) {
		this.diseaseRiskFactorColor = diseaseRiskFactorColor;
	}

	public YhDiseaseRiskFactorGroups() {
		super();
	}


	public YhDiseaseRiskFactorGroups(String diseaseRiskFactorName, Integer diseaseRiskFactorBadge,
			Integer diseaseRiskZIndex, String diseaseRiskFactorGroup, Integer diseaseRiskFactorSegment,
			QuestionNames[] questionNames, String diseaseRiskFactorColor) {
		super();
		this.diseaseRiskFactorName = diseaseRiskFactorName;
		this.diseaseRiskFactorBadge = diseaseRiskFactorBadge;
		this.diseaseRiskZIndex = diseaseRiskZIndex;
		this.diseaseRiskFactorGroup = diseaseRiskFactorGroup;
		this.diseaseRiskFactorSegment = diseaseRiskFactorSegment;
		this.questionNames = questionNames;
		this.diseaseRiskFactorColor = diseaseRiskFactorColor;
	}



	
	

}
