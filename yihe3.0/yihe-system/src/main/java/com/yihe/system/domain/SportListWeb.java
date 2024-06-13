/**
 * 
 */
package com.yihe.system.domain;

import java.util.List;

/**
 * @author Administrator
 *
 */
public class SportListWeb {
	
	private List<SportDiaryDetailsWeb> details;
	private List<SportDiaryDetailsWeb> boutique;
	private List<SportDiaryDetailsWeb> recomSport;
	public List<SportDiaryDetailsWeb> getDetails() {
		return details;
	}
	public void setDetails(List<SportDiaryDetailsWeb> details) {
		this.details = details;
	}
	public List<SportDiaryDetailsWeb> getBoutique() {
		return boutique;
	}
	public void setBoutique(List<SportDiaryDetailsWeb> boutique) {
		this.boutique = boutique;
	}
	public List<SportDiaryDetailsWeb> getRecomSport() {
		return recomSport;
	}
	public void setRecomSport(List<SportDiaryDetailsWeb> recomSport) {
		this.recomSport = recomSport;
	}
	
	
	
	
}
