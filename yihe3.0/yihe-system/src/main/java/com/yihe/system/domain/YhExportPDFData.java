package com.yihe.system.domain;

import java.io.Serializable;

public class YhExportPDFData implements Serializable{
	private static final long serialVersionUID = 1L;
	private String guanli;
	private String people;
	private String dis;
	private String bmi;
	private String yaowei;
	private String xueya;
	private String xuetang;
	private String xuezhi;
	private String yinshi;
	private String xiyan;
	private String yinjiu;
	private String tili;
	private String weixian;
	private String nowselect;
	private String begintime;
	private String endtime;
	private String select;
	private String times;
	
	public String getSelect() {
		return select;
	}
	public void setSelect(String select) {
		this.select = select;
	}
	public String getTimes() {
		return times;
	}
	public void setTimes(String times) {
		this.times = times;
	}
	public String getNowselect() {
		return nowselect;
	}
	public void setNowselect(String nowselect) {
		this.nowselect = nowselect;
	}
	public String getBegintime() {
		return begintime;
	}
	public void setBegintime(String begintime) {
		this.begintime = begintime;
	}
	public String getEndtime() {
		return endtime;
	}
	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}
	public String getGuanli() {
		return guanli;
	}
	public void setGuanli(String guanli) {
		this.guanli = guanli;
	}
	public String getPeople() {
		return people;
	}
	public void setPeople(String people) {
		this.people = people;
	}
	public String getDis() {
		return dis;
	}
	public void setDis(String dis) {
		this.dis = dis;
	}
	public String getBmi() {
		return bmi;
	}
	public void setBmi(String bmi) {
		this.bmi = bmi;
	}
	public String getYaowei() {
		return yaowei;
	}
	public void setYaowei(String yaowei) {
		this.yaowei = yaowei;
	}
	public String getXueya() {
		return xueya;
	}
	public void setXueya(String xueya) {
		this.xueya = xueya;
	}
	public String getXuetang() {
		return xuetang;
	}
	public void setXuetang(String xuetang) {
		this.xuetang = xuetang;
	}
	public String getXuezhi() {
		return xuezhi;
	}
	public void setXuezhi(String xuezhi) {
		this.xuezhi = xuezhi;
	}
	public String getYinshi() {
		return yinshi;
	}
	public void setYinshi(String yinshi) {
		this.yinshi = yinshi;
	}
	public String getXiyan() {
		return xiyan;
	}
	public void setXiyan(String xiyan) {
		this.xiyan = xiyan;
	}
	public String getYinjiu() {
		return yinjiu;
	}
	public void setYinjiu(String yinjiu) {
		this.yinjiu = yinjiu;
	}
	public String getTili() {
		return tili;
	}
	public void setTili(String tili) {
		this.tili = tili;
	}
	public String getWeixian() {
		return weixian;
	}
	public void setWeixian(String weixian) {
		this.weixian = weixian;
	}
	@Override
	public String toString() {
		return "YhExportPDFData [guanli=" + guanli + ", people=" + people + ", dis=" + dis + ", bmi=" + bmi
				+ ", yaowei=" + yaowei + ", xueya=" + xueya + ", xuetang=" + xuetang + ", xuezhi=" + xuezhi
				+ ", yinshi=" + yinshi + ", xiyan=" + xiyan + ", yinjiu=" + yinjiu + ", tili=" + tili + ", weixian="
				+ weixian + ", nowselect=" + nowselect + ", begintime=" + begintime + ", endtime=" + endtime
				+ ", select=" + select + ", times=" + times + "]";
	}
	
}
