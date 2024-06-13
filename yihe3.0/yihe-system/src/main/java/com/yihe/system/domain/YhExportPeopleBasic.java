package com.yihe.system.domain;

import java.io.Serializable;

public class YhExportPeopleBasic implements Serializable {
	private static final long serialVersionUID = 1L;
	public Integer age30Man;
	public Integer age30_40Man;
	public Integer age40_45Man;
	public Integer age45_50Man;
	public Integer age50_55Man;
	public Integer age55_60Man;
	public Integer age60Man;
	public Integer age30Woman;
	public Integer age30_40Woman;
	public Integer age40_45Woman;
	public Integer age45_50Woman;
	public Integer age50_55Woman;
	public Integer age55_60Woman;
	public Integer age60Woman;
	private Integer mancount;
	private Integer womancount;
	private Integer Peoplecount;
	private String Begintime;
	private String Endtime;
	private String statements;
	
	public String getStatements() {
		return statements;
	}

	public void setStatements(String statements) {
		this.statements = statements;
	}

	// 糖尿病
	private Integer mandiab_hiscount;
	// 心血管病
	private Integer mancard_hiscount;
	// 房颤
	private Integer manaf_hiscount;
	// 高血压
	private Integer manhbp_hiscount;
	// 冠心病史
	private Integer manchd_hiscount;
	// 肺气肿
	private Integer manemphy_hiscount;
	// 慢性支气管炎
	private Integer manbronch_hiscount;
	// 前列腺炎或肥大
	private Integer manprostate_diseasecount;
	// 脑卒中
	private Integer manstroke_hiscount;
	// 糖尿病
	private Integer womandiab_hiscount;
	// 心血管病
	private Integer womancard_hiscount;
	// 房颤
	private Integer womanaf_hiscount;
	// 高血压
	private Integer womanhbp_hiscount;
	// 冠心病史
	private Integer womanchd_hiscount;
	// 肺气肿
	private Integer womanemphy_hiscount;
	// 慢性支气管炎
	private Integer womanbronch_hiscount;
	// 前列腺炎或肥大
	private Integer womanprostate_diseasecount;
	// 脑卒中
	private Integer womanstroke_hiscount;

	public Integer getAge30Man() {
		return age30Man;
	}

	public void setAge30Man(Integer age30Man) {
		this.age30Man = age30Man;
	}

	public Integer getAge30_40Man() {
		return age30_40Man;
	}

	public void setAge30_40Man(Integer age30_40Man) {
		this.age30_40Man = age30_40Man;
	}

	public Integer getAge40_45Man() {
		return age40_45Man;
	}

	public void setAge40_45Man(Integer age40_45Man) {
		this.age40_45Man = age40_45Man;
	}

	public Integer getAge45_50Man() {
		return age45_50Man;
	}

	public void setAge45_50Man(Integer age45_50Man) {
		this.age45_50Man = age45_50Man;
	}

	public Integer getAge50_55Man() {
		return age50_55Man;
	}

	public void setAge50_55Man(Integer age50_55Man) {
		this.age50_55Man = age50_55Man;
	}

	public Integer getAge55_60Man() {
		return age55_60Man;
	}

	public void setAge55_60Man(Integer age55_60Man) {
		this.age55_60Man = age55_60Man;
	}

	public Integer getAge60Man() {
		return age60Man;
	}

	public void setAge60Man(Integer age60Man) {
		this.age60Man = age60Man;
	}

	public Integer getAge30Woman() {
		return age30Woman;
	}

	public void setAge30Woman(Integer age30Woman) {
		this.age30Woman = age30Woman;
	}

	public Integer getAge30_40Woman() {
		return age30_40Woman;
	}

	public void setAge30_40Woman(Integer age30_40Woman) {
		this.age30_40Woman = age30_40Woman;
	}

	public Integer getAge40_45Woman() {
		return age40_45Woman;
	}

	public void setAge40_45Woman(Integer age40_45Woman) {
		this.age40_45Woman = age40_45Woman;
	}

	public Integer getAge45_50Woman() {
		return age45_50Woman;
	}

	public void setAge45_50Woman(Integer age45_50Woman) {
		this.age45_50Woman = age45_50Woman;
	}

	public Integer getAge50_55Woman() {
		return age50_55Woman;
	}

	public void setAge50_55Woman(Integer age50_55Woman) {
		this.age50_55Woman = age50_55Woman;
	}

	public Integer getAge55_60Woman() {
		return age55_60Woman;
	}

	public void setAge55_60Woman(Integer age55_60Woman) {
		this.age55_60Woman = age55_60Woman;
	}

	public Integer getAge60Woman() {
		return age60Woman;
	}

	public void setAge60Woman(Integer age60Woman) {
		this.age60Woman = age60Woman;
	}

	public Integer getMancount() {
		return mancount;
	}

	public void setMancount(Integer mancount) {
		this.mancount = mancount;
	}

	public Integer getWomancount() {
		return womancount;
	}

	public void setWomancount(Integer womancount) {
		this.womancount = womancount;
	}

	public Integer getPeoplecount() {
		return Peoplecount;
	}

	public void setPeoplecount(Integer peoplecount) {
		Peoplecount = peoplecount;
	}

	public String getBegintime() {
		return Begintime;
	}

	public void setBegintime(String begintime) {
		Begintime = begintime;
	}

	public String getEndtime() {
		return Endtime;
	}

	public void setEndtime(String endtime) {
		Endtime = endtime;
	}

	public Integer getMandiab_hiscount() {
		return mandiab_hiscount;
	}

	public void setMandiab_hiscount(Integer mandiab_hiscount) {
		this.mandiab_hiscount = mandiab_hiscount;
	}

	public Integer getMancard_hiscount() {
		return mancard_hiscount;
	}

	public void setMancard_hiscount(Integer mancard_hiscount) {
		this.mancard_hiscount = mancard_hiscount;
	}

	public Integer getManaf_hiscount() {
		return manaf_hiscount;
	}

	public void setManaf_hiscount(Integer manaf_hiscount) {
		this.manaf_hiscount = manaf_hiscount;
	}

	public Integer getManhbp_hiscount() {
		return manhbp_hiscount;
	}

	public void setManhbp_hiscount(Integer manhbp_hiscount) {
		this.manhbp_hiscount = manhbp_hiscount;
	}

	public Integer getManchd_hiscount() {
		return manchd_hiscount;
	}

	public void setManchd_hiscount(Integer manchd_hiscount) {
		this.manchd_hiscount = manchd_hiscount;
	}

	public Integer getManemphy_hiscount() {
		return manemphy_hiscount;
	}

	public void setManemphy_hiscount(Integer manemphy_hiscount) {
		this.manemphy_hiscount = manemphy_hiscount;
	}

	public Integer getManbronch_hiscount() {
		return manbronch_hiscount;
	}

	public void setManbronch_hiscount(Integer manbronch_hiscount) {
		this.manbronch_hiscount = manbronch_hiscount;
	}

	public Integer getManprostate_diseasecount() {
		return manprostate_diseasecount;
	}

	public void setManprostate_diseasecount(Integer manprostate_diseasecount) {
		this.manprostate_diseasecount = manprostate_diseasecount;
	}

	public Integer getManstroke_hiscount() {
		return manstroke_hiscount;
	}

	public void setManstroke_hiscount(Integer manstroke_hiscount) {
		this.manstroke_hiscount = manstroke_hiscount;
	}

	public Integer getWomandiab_hiscount() {
		return womandiab_hiscount;
	}

	public void setWomandiab_hiscount(Integer womandiab_hiscount) {
		this.womandiab_hiscount = womandiab_hiscount;
	}

	public Integer getWomancard_hiscount() {
		return womancard_hiscount;
	}

	public void setWomancard_hiscount(Integer womancard_hiscount) {
		this.womancard_hiscount = womancard_hiscount;
	}

	public Integer getWomanaf_hiscount() {
		return womanaf_hiscount;
	}

	public void setWomanaf_hiscount(Integer womanaf_hiscount) {
		this.womanaf_hiscount = womanaf_hiscount;
	}

	public Integer getWomanhbp_hiscount() {
		return womanhbp_hiscount;
	}

	public void setWomanhbp_hiscount(Integer womanhbp_hiscount) {
		this.womanhbp_hiscount = womanhbp_hiscount;
	}

	public Integer getWomanchd_hiscount() {
		return womanchd_hiscount;
	}

	public void setWomanchd_hiscount(Integer womanchd_hiscount) {
		this.womanchd_hiscount = womanchd_hiscount;
	}

	public Integer getWomanemphy_hiscount() {
		return womanemphy_hiscount;
	}

	public void setWomanemphy_hiscount(Integer womanemphy_hiscount) {
		this.womanemphy_hiscount = womanemphy_hiscount;
	}

	public Integer getWomanbronch_hiscount() {
		return womanbronch_hiscount;
	}

	public void setWomanbronch_hiscount(Integer womanbronch_hiscount) {
		this.womanbronch_hiscount = womanbronch_hiscount;
	}

	public Integer getWomanprostate_diseasecount() {
		return womanprostate_diseasecount;
	}

	public void setWomanprostate_diseasecount(Integer womanprostate_diseasecount) {
		this.womanprostate_diseasecount = womanprostate_diseasecount;
	}

	public Integer getWomanstroke_hiscount() {
		return womanstroke_hiscount;
	}

	public void setWomanstroke_hiscount(Integer womanstroke_hiscount) {
		this.womanstroke_hiscount = womanstroke_hiscount;
	}

	@Override
	public String toString() {
		return "YhExportPeopleBasic [age30Man=" + age30Man + ", age30_40Man=" + age30_40Man + ", age40_45Man="
				+ age40_45Man + ", age45_50Man=" + age45_50Man + ", age50_55Man=" + age50_55Man + ", age55_60Man="
				+ age55_60Man + ", age60Man=" + age60Man + ", age30Woman=" + age30Woman + ", age30_40Woman="
				+ age30_40Woman + ", age40_45Woman=" + age40_45Woman + ", age45_50Woman=" + age45_50Woman
				+ ", age50_55Woman=" + age50_55Woman + ", age55_60Woman=" + age55_60Woman + ", age60Woman=" + age60Woman
				+ ", mancount=" + mancount + ", womancount=" + womancount + ", Peoplecount=" + Peoplecount
				+ ", Begintime=" + Begintime + ", Endtime=" + Endtime + ", mandiab_hiscount=" + mandiab_hiscount
				+ ", mancard_hiscount=" + mancard_hiscount + ", manaf_hiscount=" + manaf_hiscount + ", manhbp_hiscount="
				+ manhbp_hiscount + ", manchd_hiscount=" + manchd_hiscount + ", manemphy_hiscount=" + manemphy_hiscount
				+ ", manbronch_hiscount=" + manbronch_hiscount + ", manprostate_diseasecount="
				+ manprostate_diseasecount + ", manstroke_hiscount=" + manstroke_hiscount + ", womandiab_hiscount="
				+ womandiab_hiscount + ", womancard_hiscount=" + womancard_hiscount + ", womanaf_hiscount="
				+ womanaf_hiscount + ", womanhbp_hiscount=" + womanhbp_hiscount + ", womanchd_hiscount="
				+ womanchd_hiscount + ", womanemphy_hiscount=" + womanemphy_hiscount + ", womanbronch_hiscount="
				+ womanbronch_hiscount + ", womanprostate_diseasecount=" + womanprostate_diseasecount
				+ ", womanstroke_hiscount=" + womanstroke_hiscount + "]";
	}

	

}
