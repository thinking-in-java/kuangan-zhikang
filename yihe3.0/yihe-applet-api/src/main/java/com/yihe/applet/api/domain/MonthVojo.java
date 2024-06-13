/**
 * 
 */
package com.yihe.applet.api.domain;

import java.io.Serializable;

/**
 * @author Administrator
 *
 */
public class MonthVojo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 月份
	 */
	private String months;

	/**
	 * 种类
	 */
	private String kind;
	
	/**
	 * 记录
	 */
	private String record;

	/**
	 * 记录天数
	 */
	private String number;

	/**
	 * 连续天数
	 */
	private String continuous;

	public String getMonths() {
		return months;
	}

	public void setMonths(String months) {
		this.months = months;
	}

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	public String getRecord() {
		return record;
	}

	public void setRecord(String record) {
		this.record = record;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getContinuous() {
		return continuous;
	}

	public void setContinuous(String continuous) {
		this.continuous = continuous;
	}
	
	
	
}
