package com.yihe.system.domain;

import java.io.Serializable;
import java.util.List;
public class ReviceDataFromPlatemp implements Serializable{
	
	private String userName;
	
	private String phone;
	
	private String sex;
	
	private List<ConList> conlist;
	

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public List<ConList> getConlist() {
		return conlist;
	}

	public void setConlist(List<ConList> conlist) {
		this.conlist = conlist;
	}
	
	
	
}
