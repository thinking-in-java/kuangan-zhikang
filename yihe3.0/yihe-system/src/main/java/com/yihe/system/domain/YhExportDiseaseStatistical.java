package com.yihe.system.domain;

import java.io.Serializable;

public class YhExportDiseaseStatistical implements Serializable{
	private static final long serialVersionUID = 1L;
	//评估人数
	private Integer peoplecount;
	//极高风险人数(冠心病)
	private Integer chd_hisVeryhighcount;
	//高风险人数
	private Integer chd_hishighcount;
	//中等风险人数
	private Integer chd_hismediumcount;
	//较低风险人数
	private Integer chd_histhelowercount;
	//低风险人数
	private Integer chd_hislowcount;
	
	//糖尿病
	private Integer diab_hisVeryhighcount;
	private Integer diab_hishighcount;
	private Integer diab_hismediumcount;
	private Integer diab_histhelowercount;
	private Integer diab_hislowcount;
	//高血压
	private Integer hbp_hisVeryhighcount;
	private Integer hbp_hishighcount;
	private Integer hbp_hismediumcount;
	private Integer hbp_histhelowercount;
	private Integer hbp_hislowcount;
	//脑卒中
	private Integer stroke_hisVeryhighcount;
	private Integer stroke_hishighcount;
	private Integer stroke_hismediumcount;
	private Integer stroke_histhelowercount;
	private Integer stroke_hislowcount;
	
	//肺癌
	private Integer lc_hisVeryhighcount;
	private Integer lc_hishighcount;
	private Integer lc_hismediumcount;
	private Integer lc_histhelowercount;
	private Integer lc_hislowcount;
	
	
	//前列腺癌
	private Integer pc_hisVeryhighcount;
	private Integer pc_hishighcount;
	private Integer pc_hismediumcount;
	private Integer pc_histhelowercount;
	private Integer pc_hislowcount;
	
	//骨质疏松
	private Integer op_hisVeryhighcount;
	private Integer op_hishighcount;
	private Integer op_hismediumcount;
	private Integer op_histhelowercount;
	private Integer op_hislowcount;

	//痛风
	private Integer pd_hisVeryhighcount;
	private Integer pd_hishighcount;
	private Integer pd_hismediumcount;
	private Integer pd_histhelowercount;
	private Integer pd_hislowcount;

	//痛风
	private Integer bc_hisVeryhighcount;
	private Integer bc_hishighcount;
	private Integer bc_hismediumcount;
	private Integer bc_histhelowercount;
	private Integer bc_hislowcount;
	public Integer getPeoplecount() {
		return peoplecount;
	}
	public void setPeoplecount(Integer peoplecount) {
		this.peoplecount = peoplecount;
	}
	public Integer getChd_hisVeryhighcount() {
		return chd_hisVeryhighcount;
	}
	public void setChd_hisVeryhighcount(Integer chd_hisVeryhighcount) {
		this.chd_hisVeryhighcount = chd_hisVeryhighcount;
	}
	public Integer getChd_hishighcount() {
		return chd_hishighcount;
	}
	public void setChd_hishighcount(Integer chd_hishighcount) {
		this.chd_hishighcount = chd_hishighcount;
	}
	public Integer getChd_hismediumcount() {
		return chd_hismediumcount;
	}
	public void setChd_hismediumcount(Integer chd_hismediumcount) {
		this.chd_hismediumcount = chd_hismediumcount;
	}
	public Integer getChd_histhelowercount() {
		return chd_histhelowercount;
	}
	public void setChd_histhelowercount(Integer chd_histhelowercount) {
		this.chd_histhelowercount = chd_histhelowercount;
	}
	public Integer getChd_hislowcount() {
		return chd_hislowcount;
	}
	public void setChd_hislowcount(Integer chd_hislowcount) {
		this.chd_hislowcount = chd_hislowcount;
	}
	public Integer getDiab_hisVeryhighcount() {
		return diab_hisVeryhighcount;
	}
	public void setDiab_hisVeryhighcount(Integer diab_hisVeryhighcount) {
		this.diab_hisVeryhighcount = diab_hisVeryhighcount;
	}
	public Integer getDiab_hishighcount() {
		return diab_hishighcount;
	}
	public void setDiab_hishighcount(Integer diab_hishighcount) {
		this.diab_hishighcount = diab_hishighcount;
	}
	public Integer getDiab_hismediumcount() {
		return diab_hismediumcount;
	}
	public void setDiab_hismediumcount(Integer diab_hismediumcount) {
		this.diab_hismediumcount = diab_hismediumcount;
	}
	public Integer getDiab_histhelowercount() {
		return diab_histhelowercount;
	}
	public void setDiab_histhelowercount(Integer diab_histhelowercount) {
		this.diab_histhelowercount = diab_histhelowercount;
	}
	public Integer getDiab_hislowcount() {
		return diab_hislowcount;
	}
	public void setDiab_hislowcount(Integer diab_hislowcount) {
		this.diab_hislowcount = diab_hislowcount;
	}
	public Integer getHbp_hisVeryhighcount() {
		return hbp_hisVeryhighcount;
	}
	public void setHbp_hisVeryhighcount(Integer hbp_hisVeryhighcount) {
		this.hbp_hisVeryhighcount = hbp_hisVeryhighcount;
	}
	public Integer getHbp_hishighcount() {
		return hbp_hishighcount;
	}
	public void setHbp_hishighcount(Integer hbp_hishighcount) {
		this.hbp_hishighcount = hbp_hishighcount;
	}
	public Integer getHbp_hismediumcount() {
		return hbp_hismediumcount;
	}
	public void setHbp_hismediumcount(Integer hbp_hismediumcount) {
		this.hbp_hismediumcount = hbp_hismediumcount;
	}
	public Integer getHbp_histhelowercount() {
		return hbp_histhelowercount;
	}
	public void setHbp_histhelowercount(Integer hbp_histhelowercount) {
		this.hbp_histhelowercount = hbp_histhelowercount;
	}
	public Integer getHbp_hislowcount() {
		return hbp_hislowcount;
	}
	public void setHbp_hislowcount(Integer hbp_hislowcount) {
		this.hbp_hislowcount = hbp_hislowcount;
	}
	public Integer getStroke_hisVeryhighcount() {
		return stroke_hisVeryhighcount;
	}
	public void setStroke_hisVeryhighcount(Integer stroke_hisVeryhighcount) {
		this.stroke_hisVeryhighcount = stroke_hisVeryhighcount;
	}
	public Integer getStroke_hishighcount() {
		return stroke_hishighcount;
	}
	public void setStroke_hishighcount(Integer stroke_hishighcount) {
		this.stroke_hishighcount = stroke_hishighcount;
	}
	public Integer getStroke_hismediumcount() {
		return stroke_hismediumcount;
	}
	public void setStroke_hismediumcount(Integer stroke_hismediumcount) {
		this.stroke_hismediumcount = stroke_hismediumcount;
	}
	public Integer getStroke_histhelowercount() {
		return stroke_histhelowercount;
	}
	public void setStroke_histhelowercount(Integer stroke_histhelowercount) {
		this.stroke_histhelowercount = stroke_histhelowercount;
	}
	public Integer getStroke_hislowcount() {
		return stroke_hislowcount;
	}
	public void setStroke_hislowcount(Integer stroke_hislowcount) {
		this.stroke_hislowcount = stroke_hislowcount;
	}
	public Integer getLc_hisVeryhighcount() {
		return lc_hisVeryhighcount;
	}
	public void setLc_hisVeryhighcount(Integer lc_hisVeryhighcount) {
		this.lc_hisVeryhighcount = lc_hisVeryhighcount;
	}
	public Integer getLc_hishighcount() {
		return lc_hishighcount;
	}
	public void setLc_hishighcount(Integer lc_hishighcount) {
		this.lc_hishighcount = lc_hishighcount;
	}
	public Integer getLc_hismediumcount() {
		return lc_hismediumcount;
	}
	public void setLc_hismediumcount(Integer lc_hismediumcount) {
		this.lc_hismediumcount = lc_hismediumcount;
	}
	public Integer getLc_histhelowercount() {
		return lc_histhelowercount;
	}
	public void setLc_histhelowercount(Integer lc_histhelowercount) {
		this.lc_histhelowercount = lc_histhelowercount;
	}
	public Integer getLc_hislowcount() {
		return lc_hislowcount;
	}
	public void setLc_hislowcount(Integer lc_hislowcount) {
		this.lc_hislowcount = lc_hislowcount;
	}
	public Integer getPc_hisVeryhighcount() {
		return pc_hisVeryhighcount;
	}
	public void setPc_hisVeryhighcount(Integer pc_hisVeryhighcount) {
		this.pc_hisVeryhighcount = pc_hisVeryhighcount;
	}
	public Integer getPc_hishighcount() {
		return pc_hishighcount;
	}
	public void setPc_hishighcount(Integer pc_hishighcount) {
		this.pc_hishighcount = pc_hishighcount;
	}
	public Integer getPc_hismediumcount() {
		return pc_hismediumcount;
	}
	public void setPc_hismediumcount(Integer pc_hismediumcount) {
		this.pc_hismediumcount = pc_hismediumcount;
	}
	public Integer getPc_histhelowercount() {
		return pc_histhelowercount;
	}
	public void setPc_histhelowercount(Integer pc_histhelowercount) {
		this.pc_histhelowercount = pc_histhelowercount;
	}
	public Integer getPc_hislowcount() {
		return pc_hislowcount;
	}
	public void setPc_hislowcount(Integer pc_hislowcount) {
		this.pc_hislowcount = pc_hislowcount;
	}
	public Integer getOp_hisVeryhighcount() {
		return op_hisVeryhighcount;
	}
	public void setOp_hisVeryhighcount(Integer op_hisVeryhighcount) {
		this.op_hisVeryhighcount = op_hisVeryhighcount;
	}
	public Integer getOp_hishighcount() {
		return op_hishighcount;
	}
	public void setOp_hishighcount(Integer op_hishighcount) {
		this.op_hishighcount = op_hishighcount;
	}
	public Integer getOp_hismediumcount() {
		return op_hismediumcount;
	}
	public void setOp_hismediumcount(Integer op_hismediumcount) {
		this.op_hismediumcount = op_hismediumcount;
	}
	public Integer getOp_histhelowercount() {
		return op_histhelowercount;
	}
	public void setOp_histhelowercount(Integer op_histhelowercount) {
		this.op_histhelowercount = op_histhelowercount;
	}
	public Integer getOp_hislowcount() {
		return op_hislowcount;
	}
	public void setOp_hislowcount(Integer op_hislowcount) {
		this.op_hislowcount = op_hislowcount;
	}

	public Integer getPd_hisVeryhighcount() {
		return pd_hisVeryhighcount;
	}

	public void setPd_hisVeryhighcount(Integer pd_hisVeryhighcount) {
		this.pd_hisVeryhighcount = pd_hisVeryhighcount;
	}

	public Integer getPd_hishighcount() {
		return pd_hishighcount;
	}

	public void setPd_hishighcount(Integer pd_hishighcount) {
		this.pd_hishighcount = pd_hishighcount;
	}

	public Integer getPd_hismediumcount() {
		return pd_hismediumcount;
	}

	public void setPd_hismediumcount(Integer pd_hismediumcount) {
		this.pd_hismediumcount = pd_hismediumcount;
	}

	public Integer getPd_histhelowercount() {
		return pd_histhelowercount;
	}

	public void setPd_histhelowercount(Integer pd_histhelowercount) {
		this.pd_histhelowercount = pd_histhelowercount;
	}

	public Integer getPd_hislowcount() {
		return pd_hislowcount;
	}

	public void setPd_hislowcount(Integer pd_hislowcount) {
		this.pd_hislowcount = pd_hislowcount;
	}

	public Integer getBc_hisVeryhighcount() {
		return bc_hisVeryhighcount;
	}

	public void setBc_hisVeryhighcount(Integer bc_hisVeryhighcount) {
		this.bc_hisVeryhighcount = bc_hisVeryhighcount;
	}

	public Integer getBc_hishighcount() {
		return bc_hishighcount;
	}

	public void setBc_hishighcount(Integer bc_hishighcount) {
		this.bc_hishighcount = bc_hishighcount;
	}

	public Integer getBc_hismediumcount() {
		return bc_hismediumcount;
	}

	public void setBc_hismediumcount(Integer bc_hismediumcount) {
		this.bc_hismediumcount = bc_hismediumcount;
	}

	public Integer getBc_histhelowercount() {
		return bc_histhelowercount;
	}

	public void setBc_histhelowercount(Integer bc_histhelowercount) {
		this.bc_histhelowercount = bc_histhelowercount;
	}

	public Integer getBc_hislowcount() {
		return bc_hislowcount;
	}

	public void setBc_hislowcount(Integer bc_hislowcount) {
		this.bc_hislowcount = bc_hislowcount;
	}

	@Override
	public String toString() {
		return "YhExportDiseaseStatistical [peoplecount=" + peoplecount + ", chd_hisVeryhighcount="
				+ chd_hisVeryhighcount + ", chd_hishighcount=" + chd_hishighcount + ", chd_hismediumcount="
				+ chd_hismediumcount + ", chd_histhelowercount=" + chd_histhelowercount + ", chd_hislowcount="
				+ chd_hislowcount + ", diab_hisVeryhighcount=" + diab_hisVeryhighcount + ", diab_hishighcount="
				+ diab_hishighcount + ", diab_hismediumcount=" + diab_hismediumcount + ", diab_histhelowercount="
				+ diab_histhelowercount + ", diab_hislowcount=" + diab_hislowcount + ", hbp_hisVeryhighcount="
				+ hbp_hisVeryhighcount + ", hbp_hishighcount=" + hbp_hishighcount + ", hbp_hismediumcount="
				+ hbp_hismediumcount + ", hbp_histhelowercount=" + hbp_histhelowercount + ", hbp_hislowcount="
				+ hbp_hislowcount + ", stroke_hisVeryhighcount=" + stroke_hisVeryhighcount + ", stroke_hishighcount="
				+ stroke_hishighcount + ", stroke_hismediumcount=" + stroke_hismediumcount
				+ ", stroke_histhelowercount=" + stroke_histhelowercount + ", stroke_hislowcount=" + stroke_hislowcount
				+ ", lc_hisVeryhighcount=" + lc_hisVeryhighcount + ", lc_hishighcount=" + lc_hishighcount
				+ ", lc_hismediumcount=" + lc_hismediumcount + ", lc_histhelowercount=" + lc_histhelowercount
				+ ", lc_hislowcount=" + lc_hislowcount + ", pc_hisVeryhighcount=" + pc_hisVeryhighcount
				+ ", pc_hishighcount=" + pc_hishighcount + ", pc_hismediumcount=" + pc_hismediumcount
				+ ", pc_histhelowercount=" + pc_histhelowercount + ", pc_hislowcount=" + pc_hislowcount
				+ ", op_hisVeryhighcount=" + op_hisVeryhighcount + ", op_hishighcount=" + op_hishighcount
				+ ", op_hismediumcount=" + op_hismediumcount + ", op_histhelowercount=" + op_histhelowercount
				+ ", op_hislowcount=" + op_hislowcount + "]";
	}
	
	
}
