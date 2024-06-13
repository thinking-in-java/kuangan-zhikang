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

public class YhExportPdfDrink implements Serializable{
	private static final long serialVersionUID = 1L;
	private Integer drinking_high_man;
	private Integer drinking_high_woman;
	private Integer drinking_in_man;
	private Integer drinking_in_woman;
	private Integer drinking_low_man;
	private Integer drinking_low_woman;
	private Integer drinking_all;
	private Integer drinking_man;
	private Integer drinking_woman;
	private Integer allpeoplecount;
	
	public Integer getAllpeoplecount() {
		return allpeoplecount;
	}
	public void setAllpeoplecount(Integer allpeoplecount) {
		this.allpeoplecount = allpeoplecount;
	}
	public Integer getDrinking_high_man() {
		return drinking_high_man;
	}
	public void setDrinking_high_man(Integer drinking_high_man) {
		this.drinking_high_man = drinking_high_man;
	}
	public Integer getDrinking_high_woman() {
		return drinking_high_woman;
	}
	public void setDrinking_high_woman(Integer drinking_high_woman) {
		this.drinking_high_woman = drinking_high_woman;
	}
	public Integer getDrinking_in_man() {
		return drinking_in_man;
	}
	public void setDrinking_in_man(Integer drinking_in_man) {
		this.drinking_in_man = drinking_in_man;
	}
	public Integer getDrinking_in_woman() {
		return drinking_in_woman;
	}
	public void setDrinking_in_woman(Integer drinking_in_woman) {
		this.drinking_in_woman = drinking_in_woman;
	}
	public Integer getDrinking_low_man() {
		return drinking_low_man;
	}
	public void setDrinking_low_man(Integer drinking_low_man) {
		this.drinking_low_man = drinking_low_man;
	}
	public Integer getDrinking_low_woman() {
		return drinking_low_woman;
	}
	public void setDrinking_low_woman(Integer drinking_low_woman) {
		this.drinking_low_woman = drinking_low_woman;
	}
	public Integer getDrinking_all() {
		return drinking_all;
	}
	public void setDrinking_all(Integer drinking_all) {
		this.drinking_all = drinking_all;
	}
	public Integer getDrinking_man() {
		return drinking_man;
	}
	public void setDrinking_man(Integer drinking_man) {
		this.drinking_man = drinking_man;
	}
	public Integer getDrinking_woman() {
		return drinking_woman;
	}
	public void setDrinking_woman(Integer drinking_woman) {
		this.drinking_woman = drinking_woman;
	}
	@Override
	public String toString() {
		return "YhExportPdfDrink [drinking_high_man=" + drinking_high_man + ", drinking_high_woman="
				+ drinking_high_woman + ", drinking_in_man=" + drinking_in_man + ", drinking_in_woman="
				+ drinking_in_woman + ", drinking_low_man=" + drinking_low_man + ", drinking_low_woman="
				+ drinking_low_woman + ", drinking_all=" + drinking_all + ", drinking_man=" + drinking_man
				+ ", drinking_woman=" + drinking_woman + "]";
	}
	public String getJFreeChartPath(YhExportPdfDrink drrink)
	{
		JFreeChart jFreeChart = getJFreeChart(drrink);
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

	public JFreeChart getJFreeChart(YhExportPdfDrink drrink)
	{
		double valueHigh[] = {
				persent(drrink.getDrinking_high_man()+drrink.getDrinking_high_woman(), drrink.getAllpeoplecount()), persent(drrink.getDrinking_high_man(), drrink.getDrinking_man()), persent(drrink.getDrinking_high_woman(), drrink.getDrinking_woman())
		};
		double valueIn[] = {
				persent(drrink.getDrinking_in_man()+drrink.getDrinking_in_woman(), drrink.getAllpeoplecount()), persent(drrink.getDrinking_in_man(), drrink.getDrinking_man()), persent(drrink.getDrinking_in_woman(), drrink.getDrinking_woman())
		};
		double valueLow[] = {
				persent(drrink.getDrinking_low_man()+drrink.getDrinking_low_woman(), drrink.getAllpeoplecount()), persent(drrink.getDrinking_low_man(), drrink.getDrinking_man()), persent(drrink.getDrinking_low_woman(), drrink.getDrinking_woman())
		};
		String value1[] = {
				   "过量饮酒", "适量饮酒", "不饮酒"
				  };
				  String value2[] = {
				   "总体", "男", "女"
				  };
				  double value[][] = {
				   valueHigh, valueIn, valueLow
				  };
				  return GroupReport.getBarChart(DatasetUtilities.createCategoryDataset(value1, value2, value), new Color[] {
				   Color.red, Color.yellow, Color.green
				  }, "评估对象饮酒情况按性别分布（%）", 0.40000000000000002D);
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
