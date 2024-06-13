package com.yihe.system.domain;

import java.io.Serializable;

/** 
 *@author dyb 
 *@date 创建时间：2020年06月05日 下午4:56:34 
 *@version 1.0 
 */
public class YhDiseaseRiskFactorGroupsVOJO implements Serializable{
	
	// @Fields serialVersionUID : TODO
	private static final long serialVersionUID = 1L;
	private String name;
	private int max;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getMax() {
		return max;
	}
	public void setMax(int max) {
		this.max = max;
	}
	
	

}
