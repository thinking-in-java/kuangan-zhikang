package com.yihe.system.domain;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.data.general.DefaultPieDataset;

import com.ibm.icu.math.BigDecimal;
import com.yihe.common.riskresult.GroupReport;
import com.yihe.common.riskresult.RiskSortUtil;
import com.yihe.common.utils.Path;

public class YhExportPdfFactors implements Serializable{
	private static final long serialVersionUID = 1L;
	private Integer nofactors;
	private Integer onefactors;
	private Integer twofactors;
	private Integer threefactors;
	private Integer fourfactors;
	private Integer fivefactors;
	private Integer allpeople;
	private Integer drinkcount;
	private Integer FBGcount;
	private Integer bloodpressurecount;
	private Integer waistcount;
	private Integer BMIcount;
	private Integer smokecount;
	private Integer sportcount;
	private Integer foodcount;
	private Integer BFcount;
	public Integer getNofactors() {
		return nofactors;
	}
	public void setNofactors(Integer nofactors) {
		this.nofactors = nofactors;
	}
	public Integer getOnefactors() {
		return onefactors;
	}
	public void setOnefactors(Integer onefactors) {
		this.onefactors = onefactors;
	}
	public Integer getTwofactors() {
		return twofactors;
	}
	public void setTwofactors(Integer twofactors) {
		this.twofactors = twofactors;
	}
	public Integer getThreefactors() {
		return threefactors;
	}
	public void setThreefactors(Integer threefactors) {
		this.threefactors = threefactors;
	}
	public Integer getFourfactors() {
		return fourfactors;
	}
	public void setFourfactors(Integer fourfactors) {
		this.fourfactors = fourfactors;
	}
	public Integer getFivefactors() {
		return fivefactors;
	}
	public void setFivefactors(Integer fivefactors) {
		this.fivefactors = fivefactors;
	}
	public Integer getAllpeople() {
		return allpeople;
	}
	public void setAllpeople(Integer allpeople) {
		this.allpeople = allpeople;
	}
	public Integer getDrinkcount() {
		return drinkcount;
	}
	public void setDrinkcount(Integer drinkcount) {
		this.drinkcount = drinkcount;
	}
	public Integer getFBGcount() {
		return FBGcount;
	}
	public void setFBGcount(Integer fBGcount) {
		FBGcount = fBGcount;
	}
	public Integer getBloodpressurecount() {
		return bloodpressurecount;
	}
	public void setBloodpressurecount(Integer bloodpressurecount) {
		this.bloodpressurecount = bloodpressurecount;
	}
	public Integer getWaistcount() {
		return waistcount;
	}
	public void setWaistcount(Integer waistcount) {
		this.waistcount = waistcount;
	}
	public Integer getBMIcount() {
		return BMIcount;
	}
	public void setBMIcount(Integer bMIcount) {
		BMIcount = bMIcount;
	}
	public Integer getSmokecount() {
		return smokecount;
	}
	public void setSmokecount(Integer smokecount) {
		this.smokecount = smokecount;
	}
	public Integer getSportcount() {
		return sportcount;
	}
	public void setSportcount(Integer sportcount) {
		this.sportcount = sportcount;
	}
	public Integer getFoodcount() {
		return foodcount;
	}
	public void setFoodcount(Integer foodcount) {
		this.foodcount = foodcount;
	}
	public Integer getBFcount() {
		return BFcount;
	}
	public void setBFcount(Integer bFcount) {
		BFcount = bFcount;
	}
	@Override
	public String toString() {
		return "YhExportPdfFactors [nofactors=" + nofactors + ", onefactors=" + onefactors + ", twofactors="
				+ twofactors + ", threefactors=" + threefactors + ", fourfactors=" + fourfactors + ", fivefactors="
				+ fivefactors + ", allpeople=" + allpeople + ", drinkcount=" + drinkcount + ", FBGcount=" + FBGcount
				+ ", bloodpressurecount=" + bloodpressurecount + ", waistcount=" + waistcount + ", BMIcount=" + BMIcount
				+ ", smokecount=" + smokecount + ", sportcount=" + sportcount + ", foodcount=" + foodcount
				+ ", BFcount=" + BFcount + "]";
	}
	public String getJFreeChartPath(YhExportPdfFactors pdffactors)
	{
		JFreeChart jFreeChar = getJFreeChart(pdffactors);
		String path = (new StringBuilder(String.valueOf(Path.PASSING))).append(System.currentTimeMillis()).append(".jpg").toString();
		File fos_jpg = null;
		try
		{
			fos_jpg = new File(path);
			ChartUtilities.saveChartAsJPEG(fos_jpg, jFreeChar, 680, 300);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return path;
	}

	public JFreeChart getJFreeChart(YhExportPdfFactors pdffactors)
	{
		DefaultPieDataset defaultpiedataset = new DefaultPieDataset();
		defaultpiedataset.setValue("无", pdffactors.getNofactors());
		defaultpiedataset.setValue("一项", pdffactors.getOnefactors());
		defaultpiedataset.setValue("两项", pdffactors.getTwofactors());
		defaultpiedataset.setValue("三项", pdffactors.getThreefactors());
		defaultpiedataset.setValue("四项", pdffactors.getFourfactors());
		defaultpiedataset.setValue("五项及以上", pdffactors.getFivefactors());
		return GroupReport.getPieChart(defaultpiedataset, "危险因素聚集情况");
	}
	public String getJFreeChartPath2(YhExportPdfFactors pdffactors)
	{
		JFreeChart jFreeChart = getJFreeChart2(pdffactors);
		String path = (new StringBuilder(String.valueOf(Path.PASSING))).append(System.currentTimeMillis()).append(".jpg").toString();
		File fos_jpg = null;
		try
		{
			fos_jpg = new File(path);
			ChartUtilities.saveChartAsJPEG(fos_jpg, jFreeChart, 680, 300);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return path;
	}

	public JFreeChart getJFreeChart2(YhExportPdfFactors pdffactors)
	{
		double valueCereal[] = {
			(double)(((RiskSortUtil)getRiskList(pdffactors).get(0)).getMax().floatValue() / 100F), (double)(((RiskSortUtil)getRiskList(pdffactors).get(1)).getMax().floatValue() / 100F), (double)(((RiskSortUtil)getRiskList(pdffactors).get(2)).getMax().floatValue() / 100F), (double)(((RiskSortUtil)getRiskList(pdffactors).get(3)).getMax().floatValue() / 100F), (double)(((RiskSortUtil)getRiskList(pdffactors).get(4)).getMax().floatValue() / 100F), (double)(((RiskSortUtil)getRiskList(pdffactors).get(5)).getMax().floatValue() / 100F), (double)(((RiskSortUtil)getRiskList(pdffactors).get(6)).getMax().floatValue() / 100F), (double)(((RiskSortUtil)getRiskList(pdffactors).get(7)).getMax().floatValue() / 100F), (double)(((RiskSortUtil)getRiskList(pdffactors).get(8)).getMax().floatValue() / 100F) 

		};
		String value2[] = {
			((RiskSortUtil)getRiskList(pdffactors).get(0)).getName(), ((RiskSortUtil)getRiskList(pdffactors).get(1)).getName(), ((RiskSortUtil)getRiskList(pdffactors).get(2)).getName(), ((RiskSortUtil)getRiskList(pdffactors).get(3)).getName(), ((RiskSortUtil)getRiskList(pdffactors).get(4)).getName(), ((RiskSortUtil)getRiskList(pdffactors).get(5)).getName(), ((RiskSortUtil)getRiskList(pdffactors).get(6)).getName(), ((RiskSortUtil)getRiskList(pdffactors).get(7)).getName(), ((RiskSortUtil)getRiskList(pdffactors).get(8)).getName()

		};
		String value1[] = {
			"百分比"
		};
		double value[][] = {
			valueCereal
		};
		return GroupReport.getBarChartHorizontal(DatasetUtilities.createCategoryDataset(value1, value2, value), new java.awt.Color[] {
			new java.awt.Color(102, 153, 204)
		}, "健康危险因素的详细分布（%）");
	}

	public List getRiskList(YhExportPdfFactors pdffactors)
	{
		List temp = new ArrayList();
		RiskSortUtil fruits_vegetablesList = new RiskSortUtil();
		fruits_vegetablesList.setName("蔬菜水果摄入不足");
		fruits_vegetablesList.setMax(persent(pdffactors.getFoodcount(), pdffactors.getAllpeople()));
		temp.add(fruits_vegetablesList);
		RiskSortUtil pressureList = new RiskSortUtil();
		pressureList.setName("血压高");
		pressureList.setMax(persent(pdffactors.getBloodpressurecount(), pdffactors.getAllpeople()));
		temp.add(pressureList);
		RiskSortUtil lipidList = new RiskSortUtil();
		lipidList.setName("血脂异常");
		lipidList.setMax(persent(pdffactors.getBFcount(), pdffactors.getAllpeople()));
		temp.add(lipidList);
		RiskSortUtil weightList = new RiskSortUtil();
		weightList.setName("超重肥胖");
		weightList.setMax(persent(pdffactors.getBMIcount(), pdffactors.getAllpeople()));
		temp.add(weightList);
		RiskSortUtil smokeList = new RiskSortUtil();
		smokeList.setName("吸烟");
		smokeList.setMax(persent(pdffactors.getSmokecount(), pdffactors.getAllpeople()));
		temp.add(smokeList);
		RiskSortUtil sportList = new RiskSortUtil();
		sportList.setName("缺乏运动");
		sportList.setMax(persent(pdffactors.getSportcount(), pdffactors.getAllpeople()));
		temp.add(sportList);
		RiskSortUtil bloodSugarList = new RiskSortUtil();
		bloodSugarList.setName("血糖高");
		bloodSugarList.setMax(persent(pdffactors.getFBGcount(), pdffactors.getAllpeople()));
		temp.add(bloodSugarList);
		RiskSortUtil waistList = new RiskSortUtil();
		waistList.setName("腰围过大");
		waistList.setMax(persent(pdffactors.getWaistcount(), pdffactors.getAllpeople()));
		temp.add(waistList);
		RiskSortUtil drinkList = new RiskSortUtil();
		drinkList.setName("过量饮酒");
		drinkList.setMax(persent(pdffactors.getDrinkcount(), pdffactors.getAllpeople()));
		temp.add(drinkList);
		Collections.sort(temp);
		return temp;
	}
	public  float persent(Integer beichu,Integer chu) {
		if(chu!=0) {
			BigDecimal bg = new BigDecimal((double)beichu/chu);
			double f1 = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			return (float)f1*100;
		}else {
			return 0;
		}
		
	}
}
