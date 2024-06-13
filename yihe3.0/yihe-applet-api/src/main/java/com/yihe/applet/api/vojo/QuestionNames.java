package com.yihe.applet.api.vojo;

import java.io.Serializable;

/** 
 *@author lll 
 *@Email:2982564698@qq.com 
 *@phone:17608493351
 *@date 创建时间：2019年12月9日 下午4:56:34 
 *@version 1.0 
 */
public class QuestionNames implements Serializable{

	 // @Fields serialVersionUID : TODO
	private static final long serialVersionUID = 1L;

	private String answerid;
	 
	 private String answersName;
	 
	 private boolean isSelect;

	public String getAnswerid() {
		return answerid;
	}

	public void setAnswerid(String answerid) {
		this.answerid = answerid;
	}

	public String getAnswersName() {
		return answersName;
	}

	public void setAnswersName(String answersName) {
		this.answersName = answersName;
	}

	public boolean isSelect() {
		return isSelect;
	}

	public void setSelect(boolean isSelect) {
		this.isSelect = isSelect;
	}

	public QuestionNames() {
		super();
		// TODO Auto-generated constructor stub
	}

	public QuestionNames(String answerid, String answersName, boolean isSelect) {
		super();
		this.answerid = answerid;
		this.answersName = answersName;
		this.isSelect = isSelect;
	}
	 
	 
}
