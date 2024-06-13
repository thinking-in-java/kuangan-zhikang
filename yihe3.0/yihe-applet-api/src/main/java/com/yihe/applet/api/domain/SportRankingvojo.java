/**
 * 
 */
package com.yihe.applet.api.domain;

import java.io.Serializable;

/**
 * @author dong
 *
 */
public class SportRankingvojo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String id;
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
	private int days;
	/**
	 * 百分比
	 */
	private String percentage;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public int getDays() {
		return days;
	}
	public void setDays(int days) {
		this.days = days;
	}
	public String getPercentage() {
		return percentage;
	}
	public void setPercentage(String percentage) {
		this.percentage = percentage;
	}
	
	

}
