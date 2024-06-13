/**
 * 
 */
package com.yihe.system.domain;

import java.util.List;

/**
 * 运动套餐详情
 * @author dong
 *
 */
public class SportmealVojo {
	private String name;
	private String apply;
	private String describe;
	private List<CampaignVojo> vojo;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getApply() {
		return apply;
	}
	public void setApply(String apply) {
		this.apply = apply;
	}
	public String getDescribe() {
		return describe;
	}
	public void setDescribe(String describe) {
		this.describe = describe;
	}
	public List<CampaignVojo> getVojo() {
		return vojo;
	}
	public void setVojo(List<CampaignVojo> vojo) {
		this.vojo = vojo;
	}

	
	
	
	
	

}
