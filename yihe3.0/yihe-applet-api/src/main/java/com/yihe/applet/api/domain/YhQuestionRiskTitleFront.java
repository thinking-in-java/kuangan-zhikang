package com.yihe.applet.api.domain;

import java.io.Serializable;
import java.util.List;

/** 
 *@author lll 
 *@Email:2982564698@qq.com 
 *@phone:17608493351
 *@date 创建时间：2020年7月7日 下午4:56:39 
 *@version 1.0 
 */
public class YhQuestionRiskTitleFront implements Serializable{
	private static final long serialVersionUID = 1L;
    private int id;
    private String type;
    private String title;
    private String questionItemName;
	private String choose1;
    private String choose1Value;
    private String choose2;
    private String choose2Value;
    private String choose3;
    private String choose3Value;
    private String choose4;
    private String choose4Value;
    private String choose5;
    private String choose5Value;
    private String choose6;
    private String choose6Value;
    private String parentId;
    private int diseaseId;
    private int delFlag;
    private List<YhQuestionRiskTitleFront> childList;
    private int index;
    private int zIndex;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getQuestionItemName() {
		return questionItemName;
	}
	public void setQuestionItemName(String questionItemName) {
		this.questionItemName = questionItemName;
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
	public String getChoose5() {
		return choose5;
	}
	public void setChoose5(String choose5) {
		this.choose5 = choose5;
	}
	public String getChoose5Value() {
		return choose5Value;
	}
	public void setChoose5Value(String choose5Value) {
		this.choose5Value = choose5Value;
	}
	public String getChoose6() {
		return choose6;
	}
	public void setChoose6(String choose6) {
		this.choose6 = choose6;
	}
	public String getChoose6Value() {
		return choose6Value;
	}
	public void setChoose6Value(String choose6Value) {
		this.choose6Value = choose6Value;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public int getDiseaseId() {
		return diseaseId;
	}
	public void setDiseaseId(int diseaseId) {
		this.diseaseId = diseaseId;
	}
	public int getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(int delFlag) {
		this.delFlag = delFlag;
	}
	public List<YhQuestionRiskTitleFront> getChildList() {
		return childList;
	}
	public void setChildList(List<YhQuestionRiskTitleFront> childList) {
		this.childList = childList;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public int getzIndex() {
		return zIndex;
	}
	public void setzIndex(int zIndex) {
		this.zIndex = zIndex;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
