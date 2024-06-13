package com.yihe.system.domain;

import java.io.Serializable;
import java.util.Date;

public class YhExportCheck implements Serializable{

	private Integer id;
	private String user_code;
	private String user_check_answer;
	private String user_check_index;
	private String user_check_ts;
	private Date user_check_time;
	private String name;
	private Integer gender;
	private Date birth_date;
	private String Org_Name;
    private static final long serialVersionUID = 1L;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUser_code() {
		return user_code;
	}
	public void setUser_code(String user_code) {
		this.user_code = user_code;
	}
	public String getUser_check_answer() {
		return user_check_answer;
	}
	public void setUser_check_answer(String user_check_answer) {
		this.user_check_answer = user_check_answer;
	}
	public String getUser_check_index() {
		return user_check_index;
	}
	public void setUser_check_index(String user_check_index) {
		this.user_check_index = user_check_index;
	}
	public String getUser_check_ts() {
		return user_check_ts;
	}
	public void setUser_check_ts(String user_check_ts) {
		this.user_check_ts = user_check_ts;
	}
	public Date getUser_check_time() {
		return user_check_time;
	}
	public void setUser_check_time(Date user_check_time) {
		this.user_check_time = user_check_time;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getGender() {
		return gender;
	}
	public void setGender(Integer gender) {
		this.gender = gender;
	}
	
	public Date getBirth_date() {
		return birth_date;
	}
	public void setBirth_date(Date birth_date) {
		this.birth_date = birth_date;
	}
	public String getOrg_Name() {
		return Org_Name;
	}
	public void setOrg_Name(String org_Name) {
		Org_Name = org_Name;
	}
	@Override
	public String toString() {
		return "YhExportCheck [id=" + id + ", user_code=" + user_code + ", user_check_answer=" + user_check_answer
				+ ", user_check_index=" + user_check_index + ", user_check_ts=" + user_check_ts + ", user_check_time="
				+ user_check_time + ", name=" + name + ", gender=" + gender + ", birth_date=" + birth_date
				+ ", Org_Name=" + Org_Name + "]";
	}
	
}
