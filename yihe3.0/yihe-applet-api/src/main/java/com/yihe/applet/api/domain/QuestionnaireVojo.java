package com.yihe.applet.api.domain;

import java.io.Serializable;
import java.util.List;

/**
 * 问卷vojo
 * @author dong
 *
 */
public class QuestionnaireVojo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String title;
	private int zIndex;
	private List<Sonquestion> sonquestion;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getzIndex() {
		return zIndex;
	}
	public void setzIndex(int zIndex) {
		this.zIndex = zIndex;
	}
	public List<Sonquestion> getSonquestion() {
		return sonquestion;
	}
	public void setSonquestion(List<Sonquestion> sonquestion) {
		this.sonquestion = sonquestion;
	}
	
}
