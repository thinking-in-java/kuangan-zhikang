package com.yihe.system.domain;

import java.io.Serializable;
import java.util.List;

public class YhManageTotalTask implements Serializable{
	private static final long serialVersionUID = 1L;
	private Integer allwattingcount;
	private Integer allbegincount;
	private Integer allclosecount;
	private Integer allfinishicount;
	private Integer tid;
	private String user_code;
	private String doctor_code;
	private String name;
	private String mobile;
	private String doctorname;
	private List<YhManageTask> managetask;
	private String phone;

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getDoctorname() {
		return doctorname;
	}

	public void setDoctorname(String doctorname) {
		this.doctorname = doctorname;
	}

	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public List<YhManageTask> getManagetask() {
		return managetask;
	}
	public void setManagetask(List<YhManageTask> managetask) {
		this.managetask = managetask;
	}
	public Integer getAllwattingcount() {
		return allwattingcount;
	}
	public void setAllwattingcount(Integer allwattingcount) {
		this.allwattingcount = allwattingcount;
	}
	public Integer getAllbegincount() {
		return allbegincount;
	}
	public void setAllbegincount(Integer allbegincount) {
		this.allbegincount = allbegincount;
	}
	public Integer getAllclosecount() {
		return allclosecount;
	}
	public void setAllclosecount(Integer allclosecount) {
		this.allclosecount = allclosecount;
	}
	public Integer getAllfinishicount() {
		return allfinishicount;
	}
	public void setAllfinishicount(Integer allfinishicount) {
		this.allfinishicount = allfinishicount;
	}
	public Integer getTid() {
		return tid;
	}
	public void setTid(Integer tid) {
		this.tid = tid;
	}
	public String getUser_code() {
		return user_code;
	}
	public void setUser_code(String user_code) {
		this.user_code = user_code;
	}
	public String getDoctor_code() {
		return doctor_code;
	}
	public void setDoctor_code(String doctor_code) {
		this.doctor_code = doctor_code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "YhManageTotalTask [allwattingcount=" + allwattingcount + ", allbegincount=" + allbegincount
				+ ", allclosecount=" + allclosecount + ", allfinishicount=" + allfinishicount + ", tid=" + tid
				+ ", user_code=" + user_code + ", doctor_code=" + doctor_code + ", name=" + name + "]"+ ", phone=" + phone + "]";
	}

}
