/**
 * 
 */
package com.yihe.applet.api.domain;

import java.util.List;

/**
 * @author Administrator
 *
 */
public class SportListVojo {
	
	private List<SportDiaryDetails> details;
	private List<SportDiaryDetails> boutique;
	private List<SportDiaryDetails> recomSport;
	public List<SportDiaryDetails> getDetails() {
		return details;
	}
	public void setDetails(List<SportDiaryDetails> details) {
		this.details = details;
	}
	public List<SportDiaryDetails> getBoutique() {
		return boutique;
	}
	public void setBoutique(List<SportDiaryDetails> boutique) {
		this.boutique = boutique;
	}
	public List<SportDiaryDetails> getRecomSport() {
		return recomSport;
	}
	public void setRecomSport(List<SportDiaryDetails> recomSport) {
		this.recomSport = recomSport;
	}
	
	
	
	
}
