/**
 * 
 */
package com.yihe.applet.api.domain;

import java.io.Serializable;

/**
 * 今日能耗
 * @author dong
 *
 */
public class Recommendvojo1 implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 推荐能耗
	 */
	private String recommend;
	/**
	 * 消耗能耗
	 */
	private String consume;
	/**
	 * 今日记录
	 */
	private String record;
	/**
	 * 百分比
	 */
	private String percent;
	/**
	 * 步数
	 */
	private String step;
	
	/**
	 * 频率
	 */
	private String frequency;
	
	
	
	public String getFrequency() {
		return frequency;
	}
	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}
	public String getRecommend() {
		return recommend;
	}
	public void setRecommend(String recommend) {
		this.recommend = recommend;
	}
	public String getConsume() {
		return consume;
	}
	public void setConsume(String consume) {
		this.consume = consume;
	}
	public String getRecord() {
		return record;
	}
	public void setRecord(String record) {
		this.record = record;
	}
	public String getPercent() {
		return percent;
	}
	public void setPercent(String percent) {
		this.percent = percent;
	}
	public String getStep() {
		return step;
	}
	public void setStep(String step) {
		this.step = step;
	}
	
	
}
