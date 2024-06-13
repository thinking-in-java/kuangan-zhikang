package com.yihe.system.domain;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import com.ibm.icu.math.BigDecimal;
import com.yihe.common.riskresult.GroupReport;
import com.yihe.common.utils.Path;

import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DatasetUtilities;


public class YhExportPdfFood implements Serializable{
	private static final long serialVersionUID = 1L;
	private Integer manfood_purine;
	private Integer manFruit;
	private Integer manmilk;
	private Integer womanfood_purine;
	private Integer womanFruit;
	private Integer womanmilk;
	private Integer mancount;
	private Integer womancount;
	private Integer allcount;
	public Integer getManfood_purine() {
		return manfood_purine;
	}
	public void setManfood_purine(Integer manfood_purine) {
		this.manfood_purine = manfood_purine;
	}
	public Integer getManFruit() {
		return manFruit;
	}
	public void setManFruit(Integer manFruit) {
		this.manFruit = manFruit;
	}
	public Integer getManmilk() {
		return manmilk;
	}
	public void setManmilk(Integer manmilk) {
		this.manmilk = manmilk;
	}
	public Integer getWomanfood_purine() {
		return womanfood_purine;
	}
	public void setWomanfood_purine(Integer womanfood_purine) {
		this.womanfood_purine = womanfood_purine;
	}
	public Integer getWomanFruit() {
		return womanFruit;
	}
	public void setWomanFruit(Integer womanFruit) {
		this.womanFruit = womanFruit;
	}
	public Integer getWomanmilk() {
		return womanmilk;
	}
	public void setWomanmilk(Integer womanmilk) {
		this.womanmilk = womanmilk;
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
	public Integer getAllcount() {
		return allcount;
	}
	public void setAllcount(Integer allcount) {
		this.allcount = allcount;
	}
	@Override
	public String toString() {
		return "YhExportPdfFood [manfood_purine=" + manfood_purine + ", manFruit=" + manFruit + ", manmilk=" + manmilk
				+ ", womanfood_purine=" + womanfood_purine + ", womanFruit=" + womanFruit + ", womanmilk=" + womanmilk
				+ ", mancount=" + mancount + ", womancount=" + womancount + ", allcount=" + allcount + "]";
	}
	public String getJFreeChartPath(YhExportPdfFood food)
	 {
	  JFreeChart jFreeChart = getJFreeChart(food);
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

	 public JFreeChart getJFreeChart(YhExportPdfFood food)
	 {
	  double valueHigh[] = {
			  persent(food.getManfood_purine()+food.getWomanfood_purine(), food.getMancount()+food.getWomancount()), persent(food.getManfood_purine(), food.getMancount()), persent(food.getWomanfood_purine(), food.getWomancount())
	  };
	  double valueIn[] = {
	   persent(food.getManFruit()+food.getWomanFruit(), food.getAllcount()),persent(food.getManFruit(), food.getMancount()),persent(food.getWomanFruit(),  food.getWomancount())
	  };
	  double valueLow[] = {
	   persent(food.getManmilk()+food.getWomanmilk(),  food.getMancount()+food.getWomancount()), persent(food.getManmilk(), food.getMancount()), persent(food.getWomanmilk(),  food.getMancount())
	  };
	  String value1[] = {
	   "高嘌呤饮食", "蔬菜水果摄入不足", "牛奶摄入不足"
	  };
	  String value2[] = {
	   "总体", "男", "女"
	  };
	  double value[][] = {
	   valueHigh, valueIn, valueLow
	  };
	  return GroupReport.getBarChart(DatasetUtilities.createCategoryDataset(value1, value2, value), new Color[] {
	   Color.red, Color.yellow, Color.green
	  }, "膳食习惯危险因素按性别分布（%）", 0.40000000000000002D);
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
