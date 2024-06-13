/**
 * 
 */
package com.yihe.applet.api.domain;

import java.io.Serializable;

/**
 * 问卷子类
 * @author dong
 *
 */
public class Sonquestion implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int scoreid;
	private String score;
	private String topic;
	private boolean select;
	
	public int getScoreid() {
		return scoreid;
	}
	public void setScoreid(int scoreid) {
		this.scoreid = scoreid;
	}
	public boolean isSelect() {
		return select;
	}
	public void setSelect(boolean select) {
		this.select = select;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public String getTopic() {
		return topic;
	}
	public void setTopic(String topic) {
		this.topic = topic;
	}
	
	
}
