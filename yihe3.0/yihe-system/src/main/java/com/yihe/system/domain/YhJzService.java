package com.yihe.system.domain;

import java.io.Serializable;

public class YhJzService implements Serializable{
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String service_name;
	private Integer service_date;
	private String service_content;
	private String service_describe;
	private Integer isopen;
	private Integer return_id;
	
	public Integer getReturn_id() {
		return return_id;
	}
	public void setReturn_id(Integer return_id) {
		this.return_id = return_id;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getService_name() {
		return service_name;
	}
	public void setService_name(String service_name) {
		this.service_name = service_name;
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
	public String getService_describe() {
		return service_describe;
	}
	public void setService_describe(String service_describe) {
		this.service_describe = service_describe;
	}
	public Integer getIsopen() {
		return isopen;
	}
	public void setIsopen(Integer isopen) {
		this.isopen = isopen;
	}
	@Override
	public String toString() {
		return "YhJzService [id=" + id + ", service_name=" + service_name + ", service_date=" + service_date
				+ ", service_content=" + service_content + ", service_describe=" + service_describe + ", isopen="
				+ isopen + "]";
	}
	
}
