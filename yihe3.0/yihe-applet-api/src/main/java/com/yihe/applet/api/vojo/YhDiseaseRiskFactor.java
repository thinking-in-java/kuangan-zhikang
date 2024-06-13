package com.yihe.applet.api.vojo;

import java.io.Serializable;
import java.util.List;

import com.yihe.applet.api.domain.YhDiseaseCategoryApp;


/** 
 *十大评估疾病危险等级超标数据
 *@author lll 
 *@Email:2982564698@qq.com 
 *@phone:17608493351
 *@date 创建时间：2019年12月25日 上午11:08:41 
 *@version 1.0 
 */
public class YhDiseaseRiskFactor implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private List<YhDiseaseCategoryApp> yhDiseaseCategory;
	
	private  List<YhDiseaseRiskFactors> yhDiseaseRiskFactors;

	public List<YhDiseaseCategoryApp> getYhDiseaseCategory() {
		return yhDiseaseCategory;
	}

	public void setYhDiseaseCategory(List<YhDiseaseCategoryApp> yhDiseaseCategory) {
		this.yhDiseaseCategory = yhDiseaseCategory;
	}

	public List<YhDiseaseRiskFactors> getYhDiseaseRiskFactors() {
		return yhDiseaseRiskFactors;
	}

	public void setYhDiseaseRiskFactors(List<YhDiseaseRiskFactors> yhDiseaseRiskFactors) {
		this.yhDiseaseRiskFactors = yhDiseaseRiskFactors;
	}
	
	
	public YhDiseaseRiskFactor(List<YhDiseaseCategoryApp> yhDiseaseCategory,
			List<YhDiseaseRiskFactors> yhDiseaseRiskFactors) {
		super();
		this.yhDiseaseCategory = yhDiseaseCategory;
		this.yhDiseaseRiskFactors = yhDiseaseRiskFactors;
	}

	public YhDiseaseRiskFactor() {
		super();
	}

	
	
	
	  
	

}
