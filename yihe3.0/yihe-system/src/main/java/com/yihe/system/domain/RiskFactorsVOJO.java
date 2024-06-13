package com.yihe.system.domain;

public class RiskFactorsVOJO {
//危险因素
	private String riskFactors;
	
	//本次评估时间
	private String thisTime;
	
	private String thisTimeColor;
	
	//上次评估
	private String lastTime;
	
	private String lastTimeColor;
	
	//变化情况
	private String changes;
	
	//理想水平
	private String idealLevel;
	
	private String immediately;
	
	private String proceed;
	private String maintain;
	private int thisScore;
	private Integer lastScore;


	public RiskFactorsVOJO() {
	}

	public RiskFactorsVOJO(String riskFactors,String thisTime, String thisTimeColor, String lastTime, String lastTimeColor, String changes, String idealLevel) {
		this.riskFactors = riskFactors;
		this.thisTime = thisTime;
		this.thisTimeColor = thisTimeColor;
		this.lastTime = lastTime;
		this.lastTimeColor = lastTimeColor;
		this.changes = changes;
		this.idealLevel = idealLevel;
	}

	public String getImmediately() {
		return immediately;
	}

	public void setImmediately(String immediately) {
		this.immediately = immediately;
	}

	public String getProceed() {
		return proceed;
	}

	public void setProceed(String proceed) {
		this.proceed = proceed;
	}

	public String getMaintain() {
		return maintain;
	}

	public void setMaintain(String maintain) {
		this.maintain = maintain;
	}

	public int getThisScore() {
		return thisScore;
	}

	public void setThisScore(int thisScore) {
		this.thisScore = thisScore;
	}

	public Integer getLastScore() {
		return lastScore;
	}

	public void setLastScore(Integer lastScore) {
		this.lastScore = lastScore;
	}

	public String getThisTimeColor() {
		return thisTimeColor;
	}

	public void setThisTimeColor(String thisTimeColor) {
		this.thisTimeColor = thisTimeColor;
	}

	public String getLastTimeColor() {
		return lastTimeColor;
	}

	public void setLastTimeColor(String lastTimeColor) {
		this.lastTimeColor = lastTimeColor;
	}

	public String getRiskFactors() {
		return riskFactors;
	}

	public void setRiskFactors(String riskFactors) {
		this.riskFactors = riskFactors;
	}

	public String getThisTime() {
		return thisTime;
	}

	public void setThisTime(String thisTime) {
		this.thisTime = thisTime;
	}

	public String getLastTime() {
		return lastTime;
	}

	public void setLastTime(String lastTime) {
		this.lastTime = lastTime;
	}

	public String getChanges() {
		return changes;
	}

	public void setChanges(String changes) {
		this.changes = changes;
	}

	public String getIdealLevel() {
		return idealLevel;
	}

	public void setIdealLevel(String idealLevel) {
		this.idealLevel = idealLevel;
	}
	
	
	
}
