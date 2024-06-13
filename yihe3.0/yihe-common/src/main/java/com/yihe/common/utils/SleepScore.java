package com.yihe.common.utils;

import java.io.Serializable;

/** 
 *@author lll 
 *@Email:2982564698@qq.com 
 *@phone:17608493351
 *@date 创建时间：2020年4月8日 上午10:58:36 
 *@version 1.0 
 */
public class SleepScore implements Serializable{
	
	private String sleepTitle;
	
	private String sleepContent;

	public String getSleepTitle() {
		return sleepTitle;
	}

	public void setSleepTitle(String sleepTitle) {
		this.sleepTitle = sleepTitle;
	}

	public String getSleepContent() {
		return sleepContent;
	}

	public void setSleepContent(String sleepContent) {
		this.sleepContent = sleepContent;
	}

	public SleepScore() {
		super();
	}

	public SleepScore(String sleepTitle, String sleepContent) {
		super();
		this.sleepTitle = sleepTitle;
		this.sleepContent = sleepContent;
	}

	

}
