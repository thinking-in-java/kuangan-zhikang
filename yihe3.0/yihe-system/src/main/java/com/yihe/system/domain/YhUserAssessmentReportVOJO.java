package com.yihe.system.domain;

public class YhUserAssessmentReportVOJO {
	//高血压
	//血糖
	private String bs;
	//血压
	private String bloodPressure;
	private String dbp;
	private String sbp;
	//腰围
	private String theWaist;
	//BMI
	private String bmi;
	//总胆固醇
	private String tc;
	//甘油三酯
	private String tg;
	//高密度脂蛋白
	private String hdlc;
	//低密度脂蛋白
	private String ldlc;
	//膳食结构
	private String b4;
	//体力活动
	private String sport;
	//吸烟
	private String smoke;
	//饮酒
	private String foodDrink;
	
	private String zhibiao;//指标
	
	private String mubiao;//理想目标
	
	private String dangqian;//当前数据
	
	//血尿酸
	private String bua;
	private String t_score;
	
	//血肌酐
	private String serumCreatinine;
	
	private String name;
	private String sex;
	private String age;
	private String unit;
	private String pinggutime;
	
	//指标项
	private String indicators;
	//趋势
	private String trend;
	//本次
	private String thisTime;
	//本次是否超标（标红）
	private String thisTimeColor;
	//上次
	private String lastTime;
	//上次是否超标（标红）
	private String lastTimeColor;
	//参考值
	private String referenceValue;
	
	//类型
	private String type;
	//危险因素
	private String riskFactors;
	//建议
	private String proposal;
	
	//评价年龄
	private double evaluateAge;
	private String evaluateStr;
	//理想年龄
	private double idealAge;
	private String idealStr;
	
	//延迟寿命
	private double extendAge;
	private String extend;
	
	private double evaluatepxNum;
	private double idealpxNum;
	
	
	
	
	
	
	public double getEvaluatepxNum() {
		return evaluatepxNum;
	}
	public void setEvaluatepxNum(double evaluatepxNum) {
		this.evaluatepxNum = evaluatepxNum;
	}
	public double getIdealpxNum() {
		return idealpxNum;
	}
	public void setIdealpxNum(double idealpxNum) {
		this.idealpxNum = idealpxNum;
	}
	public double getEvaluateAge() {
		return evaluateAge;
	}
	public void setEvaluateAge(double evaluateAge) {
		this.evaluateAge = evaluateAge;
	}
	public String getEvaluateStr() {
		return evaluateStr;
	}
	public void setEvaluateStr(String evaluateStr) {
		this.evaluateStr = evaluateStr;
	}
	public double getIdealAge() {
		return idealAge;
	}
	public void setIdealAge(double idealAge) {
		this.idealAge = idealAge;
	}
	public String getIdealStr() {
		return idealStr;
	}
	public void setIdealStr(String idealStr) {
		this.idealStr = idealStr;
	}
	public double getExtendAge() {
		return extendAge;
	}
	public void setExtendAge(double extendAge) {
		this.extendAge = extendAge;
	}
	public String getExtend() {
		return extend;
	}
	public void setExtend(String extend) {
		this.extend = extend;
	}
	 
	public String getT_score() {
		return t_score;
	}
	public void setT_score(String t_score) {
		this.t_score = t_score;
	}
	public String getDbp() {
		return dbp;
	}
	public void setDbp(String dbp) {
		this.dbp = dbp;
	}
	public String getSbp() {
		return sbp;
	}
	public void setSbp(String sbp) {
		this.sbp = sbp;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getRiskFactors() {
		return riskFactors;
	}
	public void setRiskFactors(String riskFactors) {
		this.riskFactors = riskFactors;
	}
	public String getProposal() {
		return proposal;
	}
	public void setProposal(String proposal) {
		this.proposal = proposal;
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
	public String getIndicators() {
		return indicators;
	}
	public void setIndicators(String indicators) {
		this.indicators = indicators;
	}
	public String getTrend() {
		return trend;
	}
	public void setTrend(String trend) {
		this.trend = trend;
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
	public String getReferenceValue() {
		return referenceValue;
	}
	public void setReferenceValue(String referenceValue) {
		this.referenceValue = referenceValue;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getPinggutime() {
		return pinggutime;
	}
	public void setPinggutime(String pinggutime) {
		this.pinggutime = pinggutime;
	}
	public String getSerumCreatinine() {
		return serumCreatinine;
	}
	public void setSerumCreatinine(String serumCreatinine) {
		this.serumCreatinine = serumCreatinine;
	}
	public String getBua() {
		return bua;
	}
	public void setBua(String bua) {
		this.bua = bua;
	}
	public String getZhibiao() {
		return zhibiao;
	}
	public void setZhibiao(String zhibiao) {
		this.zhibiao = zhibiao;
	}
 
	public String getMubiao() {
		return mubiao;
	}
	public void setMubiao(String mubiao) {
		this.mubiao = mubiao;
	}
	public String getDangqian() {
		return dangqian;
	}
	public void setDangqian(String dangqian) {
		this.dangqian = dangqian;
	}
	public String getBs() {
		return bs;
	}
	public void setBs(String bs) {
		this.bs = bs;
	}
	public String getBloodPressure() {
		return bloodPressure;
	}
	public void setBloodPressure(String bloodPressure) {
		this.bloodPressure = bloodPressure;
	}
	public String getTheWaist() {
		return theWaist;
	}
	public void setTheWaist(String theWaist) {
		this.theWaist = theWaist;
	}
	public String getBmi() {
		return bmi;
	}
	public void setBmi(String bmi) {
		this.bmi = bmi;
	}
	public String getTc() {
		return tc;
	}
	public void setTc(String tc) {
		this.tc = tc;
	}
	public String getTg() {
		return tg;
	}
	public void setTg(String tg) {
		this.tg = tg;
	}
	public String getHdlc() {
		return hdlc;
	}
	public void setHdlc(String hdlc) {
		this.hdlc = hdlc;
	}
	public String getLdlc() {
		return ldlc;
	}
	public void setLdlc(String ldlc) {
		this.ldlc = ldlc;
	}
	public String getB4() {
		return b4;
	}
	public void setB4(String b4) {
		this.b4 = b4;
	}
	public String getSport() {
		return sport;
	}
	public void setSport(String sport) {
		this.sport = sport;
	}
	public String getSmoke() {
		return smoke;
	}
	public void setSmoke(String smoke) {
		this.smoke = smoke;
	}
	public String getFoodDrink() {
		return foodDrink;
	}
	public void setFoodDrink(String foodDrink) {
		this.foodDrink = foodDrink;
	}
	
	
}
