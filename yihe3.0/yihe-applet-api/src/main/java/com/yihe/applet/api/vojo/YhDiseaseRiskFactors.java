package com.yihe.applet.api.vojo;

import java.io.Serializable;

/** 
 *@author lll 
 *@Email:2982564698@qq.com 
 *@phone:17608493351
 *@date 创建时间：2019年12月25日 上午11:14:01 
 *@version 1.0 
 */
public class YhDiseaseRiskFactors implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private String riskColor;
	
	private String riskBadge;
	
	private String riskName;

	public String getRiskColor() {
		return riskColor;
	}

	public void setRiskColor(String riskColor) {
		this.riskColor = riskColor;
	}

	public String getRiskBadge() {
		return riskBadge;
	}

	public void setRiskBadge(String riskBadge) {
		this.riskBadge = riskBadge;
	}

	public String getRiskName() {
		return riskName;
	}

	public void setRiskName(String riskName) {
		this.riskName = riskName;
	}

	public YhDiseaseRiskFactors() {
		super();
	}

	public YhDiseaseRiskFactors(String riskColor, String riskBadge, String riskName) {
		super();
		this.riskColor = riskColor;
		this.riskBadge = riskBadge;
		this.riskName = riskName;
	}
	
	

}
