package com.yihe.system.domain;

import java.io.Serializable;

public class YhJzServiceBodydataSwitch implements Serializable{
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String name;
	private Integer first_switch;
	private Integer every_switch;
	private Integer service_date;
	private String service_content;
	private Integer long_time_no_switch;
	private Integer long_time_no_intervaltime;
	private Integer returnvisit;

	public Integer getLong_time_no_switch() {
		return long_time_no_switch;
	}

	public void setLong_time_no_switch(Integer long_time_no_switch) {
		this.long_time_no_switch = long_time_no_switch;
	}

	public Integer getLong_time_no_intervaltime() {
		return long_time_no_intervaltime;
	}

	public void setLong_time_no_intervaltime(Integer long_time_no_intervaltime) {
		this.long_time_no_intervaltime = long_time_no_intervaltime;
	}

	public Integer getReturnvisit() {
		return returnvisit;
	}
	public void setReturnvisit(Integer returnvisit) {
		this.returnvisit = returnvisit;
	}
	public Integer getService_date() {
		return service_date;
	}
	public void setService_date(Integer service_date) {
		this.service_date = service_date;
	}
	public String getService_content() {
		return service_content;
	}
	public void setService_content(String service_content) {
		this.service_content = service_content;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getFirst_switch() {
		return first_switch;
	}
	public void setFirst_switch(Integer first_switch) {
		this.first_switch = first_switch;
	}
	public Integer getEvery_switch() {
		return every_switch;
	}
	public void setEvery_switch(Integer every_switch) {
		this.every_switch = every_switch;
	}
	
	@Override
	public String toString() {
		return "YhJzServiceBodydataSwitch [id=" + id + ", name=" + name + ", first_switch=" + first_switch
				+ ", every_switch=" + every_switch + "]";
	}
	
}
