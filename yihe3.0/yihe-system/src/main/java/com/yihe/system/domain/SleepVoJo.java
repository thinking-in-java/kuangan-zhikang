/**
 * 
 */
package com.yihe.system.domain;

import java.util.List;

/**
 * @author dong
 *
 */
public class SleepVoJo {
	private String headline;
	private List<SubtitleVoJO> subtitle;
	
	
	public List<SubtitleVoJO> getSubtitle() {
		return subtitle;
	}
	public void setSubtitle(List<SubtitleVoJO> subtitle) {
		this.subtitle = subtitle;
	}
	public String getHeadline() {
		return headline;
	}
	public void setHeadline(String headline) {
		this.headline = headline;
	}
	
	
	
	
}
