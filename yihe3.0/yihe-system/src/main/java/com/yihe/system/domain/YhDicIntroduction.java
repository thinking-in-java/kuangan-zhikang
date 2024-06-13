package com.yihe.system.domain;

import java.io.Serializable;

public class YhDicIntroduction implements Serializable{
	private int code;
	private String name;
	private String explain;
	private String information;
	private int type;
	private static final long serialVersionUID = 1L;
	
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getExplain() {
		return explain;
	}
	public void setExplain(String explain) {
		this.explain = explain;
	}
	public String getInformation() {
		return information;
	}
	public void setInformation(String information) {
		this.information = information;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	@Override
	public String toString() {
		return "YhDicIntroduction [code=" + code + ", name=" + name + ", explain=" + explain + ", information="
				+ information + ", type=" + type + "]";
	}
	
}
