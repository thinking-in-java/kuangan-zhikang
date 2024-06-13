package com.yihe.system.domain;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DatasetUtilities;

import com.ibm.icu.math.BigDecimal;
import com.yihe.common.riskresult.GroupReport;
import com.yihe.common.utils.Path;

public class YhExportPdfBloodFat implements Serializable{
	private static final long serialVersionUID = 1L;
	// 总胆固醇
	private Integer mancholesterolcount;
	// 高密度脂蛋白胆固醇
	private Integer manhdlccount;
	// 低密度脂蛋白胆固醇
	private Integer manldlccount;
	// 甘油三酯
	private Integer manTGcount;
	// 高血脂
	private Integer manBFcount;
	// 总胆固醇
	private Integer womancholesterolcount;
	// 高密度脂蛋白胆固醇
	private Integer womanhdlccount;
	// 低密度脂蛋白胆固醇
	private Integer womanldlccount;
	// 甘油三酯
	private Integer womanTGcount;
	// 高血脂
	private Integer womanBFcount;
	//总人数
	private Integer allcount;
	//人数（男）
	private Integer mancount;
	//人数（女）
	private Integer womancount;;
	
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
	public Integer getAllcount() {
		return allcount;
	}
	public void setAllcount(Integer allcount) {
		this.allcount = allcount;
	}
	public Integer getMancholesterolcount() {
		return mancholesterolcount;
	}
	public void setMancholesterolcount(Integer mancholesterolcount) {
		this.mancholesterolcount = mancholesterolcount;
	}
	public Integer getManhdlccount() {
		return manhdlccount;
	}
	public void setManhdlccount(Integer manhdlccount) {
		this.manhdlccount = manhdlccount;
	}
	public Integer getManldlccount() {
		return manldlccount;
	}
	public void setManldlccount(Integer manldlccount) {
		this.manldlccount = manldlccount;
	}
	public Integer getManTGcount() {
		return manTGcount;
	}
	public void setManTGcount(Integer manTGcount) {
		this.manTGcount = manTGcount;
	}
	public Integer getManBFcount() {
		return manBFcount;
	}
	public void setManBFcount(Integer manBFcount) {
		this.manBFcount = manBFcount;
	}
	public Integer getWomancholesterolcount() {
		return womancholesterolcount;
	}
	public void setWomancholesterolcount(Integer womancholesterolcount) {
		this.womancholesterolcount = womancholesterolcount;
	}
	public Integer getWomanhdlccount() {
		return womanhdlccount;
	}
	public void setWomanhdlccount(Integer womanhdlccount) {
		this.womanhdlccount = womanhdlccount;
	}
	public Integer getWomanldlccount() {
		return womanldlccount;
	}
	public void setWomanldlccount(Integer womanldlccount) {
		this.womanldlccount = womanldlccount;
	}
	public Integer getWomanTGcount() {
		return womanTGcount;
	}
	public void setWomanTGcount(Integer womanTGcount) {
		this.womanTGcount = womanTGcount;
	}
	public Integer getWomanBFcount() {
		return womanBFcount;
	}
	public void setWomanBFcount(Integer womanBFcount) {
		this.womanBFcount = womanBFcount;
	}
	@Override
	public String toString() {
		return "YhExportPdfBloodFat [mancholesterolcount=" + mancholesterolcount + ", manhdlccount=" + manhdlccount
				+ ", manldlccount=" + manldlccount + ", manTGcount=" + manTGcount + ", manBFcount=" + manBFcount
				+ ", womancholesterolcount=" + womancholesterolcount + ", womanhdlccount=" + womanhdlccount
				+ ", womanldlccount=" + womanldlccount + ", womanTGcount=" + womanTGcount + ", womanBFcount="
				+ womanBFcount + "]";
	}
	
	public String getJFreeChartPath(YhExportPdfBloodFat bloodfat)
	{
		JFreeChart jFreeChart = getJFreeChart(bloodfat);
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

	public JFreeChart getJFreeChart(YhExportPdfBloodFat bloodfat)
	{
		double valueHigh[] = {
				persent(bloodfat.getMancholesterolcount()+bloodfat.getWomancholesterolcount(), bloodfat.getAllcount()),persent(bloodfat.getMancholesterolcount(), bloodfat.getMancount()), persent(bloodfat.getWomancholesterolcount(), bloodfat.getWomancount())
		};
		double valueIn[] = {
			persent(bloodfat.getManTGcount()+bloodfat.getWomanTGcount(), bloodfat.getAllcount()), persent(bloodfat.getManTGcount(), bloodfat.getMancount()), persent(bloodfat.getWomanTGcount(), bloodfat.getWomancount())
		};
		double valueLow[] = {
			persent(bloodfat.getWomanhdlccount()+bloodfat.getManhdlccount(), bloodfat.getAllcount()), persent(bloodfat.getManhdlccount(), bloodfat.getMancount()), persent(bloodfat.getWomanhdlccount(), bloodfat.getWomancount())
		};
		double value4[] = {
			persent(bloodfat.getManldlccount()+bloodfat.getWomanldlccount(), bloodfat.getAllcount()), persent(bloodfat.getManldlccount(), bloodfat.getMancount()), persent(bloodfat.getWomanldlccount(), bloodfat.getWomancount())
		};
		String value1[] = {
			"高总胆固醇", "高甘油三酯", "低HDL胆固醇", "高LDL胆固醇"
		};
		String value2[] = {
			"总体", "男", "女"
		};
		double value[][] = {
			valueHigh, valueIn, valueLow, value4
		};
		return GroupReport.getBarChart(DatasetUtilities.createCategoryDataset(value1, value2, value), new Color[] {
			Color.red, Color.yellow, Color.green, Color.blue
		}, "评估对象血脂检测结果按性别分布（%）", 0.29999999999999999D);
	}
	public double persent(Integer beichu,Integer chu) {
		if(chu!=0) {
			BigDecimal bg = new BigDecimal((double)beichu/chu);
			double f1 = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			return f1;
		}else {
			return 0;
		}
		
	}
	
}
