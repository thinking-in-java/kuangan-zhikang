/**
 * 
 */
package com.yihe.applet.api.domain;

import java.io.Serializable;

/**
 * @author Administrator
 *
 */
public class StatisticsMapVojo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 时间
	 */
	private String time;
	
	/**
	 * 数量
	 */
	private String quantity;
	/**
	 * 运动图片
	 */
	private String img;
	/**
	 * 运动名称
	 */
	private String name;
	/**
	 * 运动天数
	 */
	private String days;
	/**
	 * 百分比
	 */
	private String percentage;
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDays() {
		return days;
	}
	public void setDays(String days) {
		this.days = days;
	}
	public String getPercentage() {
		return percentage;
	}
	public void setPercentage(String percentage) {
		this.percentage = percentage;
	}
	
	
}
