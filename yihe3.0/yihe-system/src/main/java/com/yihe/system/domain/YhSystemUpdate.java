package com.yihe.system.domain;

import java.io.Serializable;
import java.util.Date;

public class YhSystemUpdate implements Serializable{
	 private Integer update_id;
	 private Date update_begintime;
	 private Date update_endtime;
	 private String update_title;
	 private String update_content;
	 private Integer state;
	 private String brgin;
	 private String end;
	 
	 @Override
	public String toString() {
		return "YhSystemUpdate [update_id=" + update_id + ", update_begintime=" + update_begintime + ", update_endtime="
				+ update_endtime + ", update_title=" + update_title + ", update_content=" + update_content + ", state="
				+ state + "]";
	}

	public Integer getUpdate_id() {
		return update_id;
	}

	public void setUpdate_id(Integer update_id) {
		this.update_id = update_id;
	}
	


	

	public String getBrgin() {
		return brgin;
	}

	public void setBrgin(String brgin) {
		this.brgin = brgin;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public Date getUpdate_begintime() {
		return update_begintime;
	}

	public void setUpdate_begintime(Date update_begintime) {
		this.update_begintime = update_begintime;
	}

	public Date getUpdate_endtime() {
		return update_endtime;
	}

	public void setUpdate_endtime(Date update_endtime) {
		this.update_endtime = update_endtime;
	}

	public String getUpdate_title() {
		return update_title;
	}

	public void setUpdate_title(String update_title) {
		this.update_title = update_title;
	}

	public String getUpdate_content() {
		return update_content;
	}

	public void setUpdate_content(String update_content) {
		this.update_content = update_content;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	private static final long serialVersionUID = 1L;
}
