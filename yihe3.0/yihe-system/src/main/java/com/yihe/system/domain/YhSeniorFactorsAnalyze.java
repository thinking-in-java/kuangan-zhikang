package com.yihe.system.domain;

import java.io.Serializable;

public class YhSeniorFactorsAnalyze implements Serializable{
	private static final long serialVersionUID = 1L;
	private Double HDL;
	private Double HDL_max;
	private Double HDL_min;
	private Double LDL;
	private Double LDL_max;
	private Double LDL_min;
	private Integer chooseyinsu;
	private Integer feiai;
	private Integer feiaishi;
	private Double ganyou;
	private Double ganyou_max;
	private Double ganyou_min;
	private Integer gaoxueya;
	private Integer gaoxueyashi;
	private Integer guanxin;
	private Double kongfu;
	private Double kongfu_max;
	private Double kongfu_min;
	private String select;
	private String select1;
	private Integer sex;
	private Double shousuo;
	private Double shousuo_max;
	private Double shousuo_min;
	private Double shuzhang;
	private Double shuzhang_max;
	private Double shuzhang_min;
	private Integer sui_max;
	private Integer sui_min;
	private Integer tangniao;
	private Integer tangniaoshi;
	private Integer tili;
	private String times;
	private Double tizhong;
	private Double tizhong_max;
	private Double tizhong_min;
	private Integer xiyan;
	private Integer xuezhi;
	private Double yaowei;
	private Double yaowei_max;
	private Double yaowei_min;
	private Integer yinjiu;
	private Double zongdan;
	private Double zongdan_max;
	private Double zongdan_min;
	private String nowselect;
	private String begintime;
	private String endtime;
	
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
	public String getNowselect() {
		return nowselect;
	}
	public void setNowselect(String nowselect) {
		this.nowselect = nowselect;
	}
	public Double getHDL() {
		return HDL;
	}
	public void setHDL(Double hDL) {
		HDL = hDL;
	}
	public Double getHDL_max() {
		return HDL_max;
	}
	public void setHDL_max(Double hDL_max) {
		HDL_max = hDL_max;
	}
	public Double getHDL_min() {
		return HDL_min;
	}
	public void setHDL_min(Double hDL_min) {
		HDL_min = hDL_min;
	}
	public Double getLDL() {
		return LDL;
	}
	public void setLDL(Double lDL) {
		LDL = lDL;
	}
	public Double getLDL_max() {
		return LDL_max;
	}
	public void setLDL_max(Double lDL_max) {
		LDL_max = lDL_max;
	}
	public Double getLDL_min() {
		return LDL_min;
	}
	public void setLDL_min(Double lDL_min) {
		LDL_min = lDL_min;
	}
	public Integer getChooseyinsu() {
		return chooseyinsu;
	}
	public void setChooseyinsu(Integer chooseyinsu) {
		this.chooseyinsu = chooseyinsu;
	}
	public Integer getFeiai() {
		return feiai;
	}
	public void setFeiai(Integer feiai) {
		this.feiai = feiai;
	}
	public Integer getFeiaishi() {
		return feiaishi;
	}
	public void setFeiaishi(Integer feiaishi) {
		this.feiaishi = feiaishi;
	}
	public Double getGanyou() {
		return ganyou;
	}
	public void setGanyou(Double ganyou) {
		this.ganyou = ganyou;
	}
	public Double getGanyou_max() {
		return ganyou_max;
	}
	public void setGanyou_max(Double ganyou_max) {
		this.ganyou_max = ganyou_max;
	}
	public Double getGanyou_min() {
		return ganyou_min;
	}
	public void setGanyou_min(Double ganyou_min) {
		this.ganyou_min = ganyou_min;
	}
	public Integer getGaoxueya() {
		return gaoxueya;
	}
	public void setGaoxueya(Integer gaoxueya) {
		this.gaoxueya = gaoxueya;
	}
	public Integer getGaoxueyashi() {
		return gaoxueyashi;
	}
	public void setGaoxueyashi(Integer gaoxueyashi) {
		this.gaoxueyashi = gaoxueyashi;
	}
	public Integer getGuanxin() {
		return guanxin;
	}
	public void setGuanxin(Integer guanxin) {
		this.guanxin = guanxin;
	}
	public Double getKongfu() {
		return kongfu;
	}
	public void setKongfu(Double kongfu) {
		this.kongfu = kongfu;
	}
	public Double getKongfu_max() {
		return kongfu_max;
	}
	public void setKongfu_max(Double kongfu_max) {
		this.kongfu_max = kongfu_max;
	}
	public Double getKongfu_min() {
		return kongfu_min;
	}
	public void setKongfu_min(Double kongfu_min) {
		this.kongfu_min = kongfu_min;
	}
	public String getSelect() {
		return select;
	}
	public void setSelect(String select) {
		this.select = select;
	}
	public String getSelect1() {
		return select1;
	}
	public void setSelect1(String select1) {
		this.select1 = select1;
	}
	public Integer getSex() {
		return sex;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	public Double getShousuo() {
		return shousuo;
	}
	public void setShousuo(Double shousuo) {
		this.shousuo = shousuo;
	}
	public Double getShousuo_max() {
		return shousuo_max;
	}
	public void setShousuo_max(Double shousuo_max) {
		this.shousuo_max = shousuo_max;
	}
	public Double getShousuo_min() {
		return shousuo_min;
	}
	public void setShousuo_min(Double shousuo_min) {
		this.shousuo_min = shousuo_min;
	}
	public Double getShuzhang() {
		return shuzhang;
	}
	public void setShuzhang(Double shuzhang) {
		this.shuzhang = shuzhang;
	}
	public Double getShuzhang_max() {
		return shuzhang_max;
	}
	public void setShuzhang_max(Double shuzhang_max) {
		this.shuzhang_max = shuzhang_max;
	}
	public Double getShuzhang_min() {
		return shuzhang_min;
	}
	public void setShuzhang_min(Double shuzhang_min) {
		this.shuzhang_min = shuzhang_min;
	}
	public Integer getSui_max() {
		return sui_max;
	}
	public void setSui_max(Integer sui_max) {
		this.sui_max = sui_max;
	}
	public Integer getSui_min() {
		return sui_min;
	}
	public void setSui_min(Integer sui_min) {
		this.sui_min = sui_min;
	}
	public Integer getTangniao() {
		return tangniao;
	}
	public void setTangniao(Integer tangniao) {
		this.tangniao = tangniao;
	}
	public Integer getTangniaoshi() {
		return tangniaoshi;
	}
	public void setTangniaoshi(Integer tangniaoshi) {
		this.tangniaoshi = tangniaoshi;
	}
	public Integer getTili() {
		return tili;
	}
	public void setTili(Integer tili) {
		this.tili = tili;
	}
	public String getTimes() {
		return times;
	}
	public void setTimes(String times) {
		this.times = times;
	}
	public Double getTizhong() {
		return tizhong;
	}
	public void setTizhong(Double tizhong) {
		this.tizhong = tizhong;
	}
	public Double getTizhong_max() {
		return tizhong_max;
	}
	public void setTizhong_max(Double tizhong_max) {
		this.tizhong_max = tizhong_max;
	}
	public Double getTizhong_min() {
		return tizhong_min;
	}
	public void setTizhong_min(Double tizhong_min) {
		this.tizhong_min = tizhong_min;
	}
	public Integer getXiyan() {
		return xiyan;
	}
	public void setXiyan(Integer xiyan) {
		this.xiyan = xiyan;
	}
	public Integer getXuezhi() {
		return xuezhi;
	}
	public void setXuezhi(Integer xuezhi) {
		this.xuezhi = xuezhi;
	}
	public Double getYaowei() {
		return yaowei;
	}
	public void setYaowei(Double yaowei) {
		this.yaowei = yaowei;
	}
	public Double getYaowei_max() {
		return yaowei_max;
	}
	public void setYaowei_max(Double yaowei_max) {
		this.yaowei_max = yaowei_max;
	}
	public Double getYaowei_min() {
		return yaowei_min;
	}
	public void setYaowei_min(Double yaowei_min) {
		this.yaowei_min = yaowei_min;
	}
	public Integer getYinjiu() {
		return yinjiu;
	}
	public void setYinjiu(Integer yinjiu) {
		this.yinjiu = yinjiu;
	}
	public Double getZongdan() {
		return zongdan;
	}
	public void setZongdan(Double zongdan) {
		this.zongdan = zongdan;
	}
	public Double getZongdan_max() {
		return zongdan_max;
	}
	public void setZongdan_max(Double zongdan_max) {
		this.zongdan_max = zongdan_max;
	}
	public Double getZongdan_min() {
		return zongdan_min;
	}
	public void setZongdan_min(Double zongdan_min) {
		this.zongdan_min = zongdan_min;
	}
	@Override
	public String toString() {
		return "YhSeniorFactorsAnalyze [HDL=" + HDL + ", HDL_max=" + HDL_max + ", HDL_min=" + HDL_min + ", LDL=" + LDL
				+ ", LDL_max=" + LDL_max + ", LDL_min=" + LDL_min + ", chooseyinsu=" + chooseyinsu + ", feiai=" + feiai
				+ ", feiaishi=" + feiaishi + ", ganyou=" + ganyou + ", ganyou_max=" + ganyou_max + ", ganyou_min="
				+ ganyou_min + ", gaoxueya=" + gaoxueya + ", gaoxueyashi=" + gaoxueyashi + ", guanxin=" + guanxin
				+ ", kongfu=" + kongfu + ", kongfu_max=" + kongfu_max + ", kongfu_min=" + kongfu_min + ", select="
				+ select + ", select1=" + select1 + ", sex=" + sex + ", shousuo=" + shousuo + ", shousuo_max="
				+ shousuo_max + ", shousuo_min=" + shousuo_min + ", shuzhang=" + shuzhang + ", shuzhang_max="
				+ shuzhang_max + ", shuzhang_min=" + shuzhang_min + ", sui_max=" + sui_max + ", sui_min=" + sui_min
				+ ", tangniao=" + tangniao + ", tangniaoshi=" + tangniaoshi + ", tili=" + tili + ", times=" + times
				+ ", tizhong=" + tizhong + ", tizhong_max=" + tizhong_max + ", tizhong_min=" + tizhong_min + ", xiyan="
				+ xiyan + ", xuezhi=" + xuezhi + ", yaowei=" + yaowei + ", yaowei_max=" + yaowei_max + ", yaowei_min="
				+ yaowei_min + ", yinjiu=" + yinjiu + ", zongdan=" + zongdan + ", zongdan_max=" + zongdan_max
				+ ", zongdan_min=" + zongdan_min + "]";
	}
	
}
