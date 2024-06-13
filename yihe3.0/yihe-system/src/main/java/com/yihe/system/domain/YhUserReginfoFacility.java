package com.yihe.system.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public class YhUserReginfoFacility implements Serializable {
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




   

   
	

}