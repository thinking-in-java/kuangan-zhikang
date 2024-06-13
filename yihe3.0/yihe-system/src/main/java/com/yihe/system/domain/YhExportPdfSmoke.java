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

public class YhExportPdfSmoke implements Serializable{
	private static final long serialVersionUID = 1L;
	private Integer smoke_man;
	private Integer smoke_woman;
	private Integer smoke_no_man;
	private Integer smoke_no_woman;
	private Integer allpeople;
	private Integer secondSmokeMan;
	private Integer secondSmokeWoman;
	private Integer man;
	private Integer woman;
	
	public Integer getMan() {
		return man;
	}
	public void setMan(Integer man) {
		this.man = man;
	}
	public Integer getWoman() {
		return woman;
	}
	public void setWoman(Integer woman) {
		this.woman = woman;
	}
	public Integer getSmoke_man() {
		return smoke_man;
	}
	public void setSmoke_man(Integer smoke_man) {
		this.smoke_man = smoke_man;
	}
	public Integer getSmoke_woman() {
		return smoke_woman;
	}
	public void setSmoke_woman(Integer smoke_woman) {
		this.smoke_woman = smoke_woman;
	}
	public Integer getSmoke_no_man() {
		return smoke_no_man;
	}
	public void setSmoke_no_man(Integer smoke_no_man) {
		this.smoke_no_man = smoke_no_man;
	}
	public Integer getSmoke_no_woman() {
		return smoke_no_woman;
	}
	public void setSmoke_no_woman(Integer smoke_no_woman) {
		this.smoke_no_woman = smoke_no_woman;
	}
	public Integer getAllpeople() {
		return allpeople;
	}
	public void setAllpeople(Integer allpeople) {
		this.allpeople = allpeople;
	}
	public Integer getSecondSmokeMan() {
		return secondSmokeMan;
	}
	public void setSecondSmokeMan(Integer secondSmokeMan) {
		this.secondSmokeMan = secondSmokeMan;
	}
	public Integer getSecondSmokeWoman() {
		return secondSmokeWoman;
	}
	public void setSecondSmokeWoman(Integer secondSmokeWoman) {
		this.secondSmokeWoman = secondSmokeWoman;
	}
	@Override
	public String toString() {
		return "YhExportPdfSmoke [smoke_man=" + smoke_man + ", smoke_woman=" + smoke_woman + ", smoke_no_man="
				+ smoke_no_man + ", smoke_no_woman=" + smoke_no_woman + ", allpeople=" + allpeople + ", secondSmokeMan="
				+ secondSmokeMan + ", secondSmokeWoman=" + secondSmokeWoman + "]";
	}
	public String getJFreeChartPath(YhExportPdfSmoke smoke)
	{
		JFreeChart jFreeChart = getJFreeChart(smoke);
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

	public JFreeChart getJFreeChart(YhExportPdfSmoke smoke)
	{
		double valueHigh[] = {
				persent(smoke.getSmoke_man()+smoke.getSmoke_woman(), smoke.getMan()+smoke.getWoman()), persent(smoke.getSmoke_man(), smoke.getMan()), persent(smoke.getSmoke_woman(), smoke.getWoman())
		};
		double valueIn[] = {
			persent(smoke.getSmoke_no_man()+smoke.getSmoke_no_woman(), smoke.getWoman()+smoke.getMan()), persent(smoke.getSmoke_no_man(), smoke.getMan()), persent(smoke.getSmoke_no_woman(),  smoke.getWoman())
		};
		String value1[] = {
			"吸烟", "不吸烟"
		};
		String value2[] = {
			"总体", "男", "女"
		};
		double value[][] = {
			valueHigh, valueIn
		};
		return GroupReport.getBarChart(DatasetUtilities.createCategoryDataset(value1, value2, value), new Color[] {
			Color.red, Color.yellow, Color.green
		},"评估对象吸烟情况按性别分布",0.40000000000000002D);
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
