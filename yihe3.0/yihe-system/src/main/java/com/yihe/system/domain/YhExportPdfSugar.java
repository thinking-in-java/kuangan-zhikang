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

public class YhExportPdfSugar  implements Serializable{
	private static final long serialVersionUID = 1L;
	private Integer mancountnormal;
	private Integer manounthighside;
	private Integer manounthigh;
	private Integer womancountnormal;
	private Integer womanounthighside;
	private Integer womanounthigh;	
	private Integer age30mancountnormal;
	private Integer age30manounthighside;
	private Integer age30manounthigh;
	private Integer age30womancountnormal;
	private Integer age30womanounthighside;
	private Integer age30womanounthigh;
	private Integer age30_40mancountnormal;
	private Integer age30_40manounthighside;
	private Integer age30_40manounthigh;
	private Integer age30_40womancountnormal;
	private Integer age30_40womanounthighside;
	private Integer age30_40womanounthigh;	
	private Integer age40_45mancountnormal;
	private Integer age40_45manounthighside;
	private Integer age40_45manounthigh;
	private Integer age40_45womancountnormal;
	private Integer age40_45womanounthighside;
	private Integer age40_45womanounthigh;	
	private Integer age45_50mancountnormal;
	private Integer age45_50manounthighside;
	private Integer age45_50manounthigh;
	private Integer age45_50womancountnormal;
	private Integer age45_50womanounthighside;
	private Integer age45_50womanounthigh;	
	private Integer age50_55mancountnormal;
	private Integer age50_55manounthighside;
	private Integer age50_55manounthigh;
	private Integer age50_55womancountnormal;
	private Integer age50_55womanounthighside;
	private Integer age50_55womanounthigh;	
	private Integer age55_60mancountnormal;
	private Integer age55_60manounthighside;
	private Integer age55_60manounthigh;
	private Integer age55_60womancountnormal;
	private Integer age55_60womanounthighside;
	private Integer age55_60womanounthigh;	
	private Integer age60mancountnormal;
	private Integer age60manounthighside;
	private Integer age60manounthigh;
	private Integer age60womancountnormal;
	private Integer age60womanounthighside;
	private Integer age60womanounthigh;
	public Integer getMancountnormal() {
		return mancountnormal;
	}
	public void setMancountnormal(Integer mancountnormal) {
		this.mancountnormal = mancountnormal;
	}
	public Integer getManounthighside() {
		return manounthighside;
	}
	public void setManounthighside(Integer manounthighside) {
		this.manounthighside = manounthighside;
	}
	public Integer getManounthigh() {
		return manounthigh;
	}
	public void setManounthigh(Integer manounthigh) {
		this.manounthigh = manounthigh;
	}
	public Integer getWomancountnormal() {
		return womancountnormal;
	}
	public void setWomancountnormal(Integer womancountnormal) {
		this.womancountnormal = womancountnormal;
	}
	public Integer getWomanounthighside() {
		return womanounthighside;
	}
	public void setWomanounthighside(Integer womanounthighside) {
		this.womanounthighside = womanounthighside;
	}
	public Integer getWomanounthigh() {
		return womanounthigh;
	}
	public void setWomanounthigh(Integer womanounthigh) {
		this.womanounthigh = womanounthigh;
	}
	public Integer getAge30mancountnormal() {
		return age30mancountnormal;
	}
	public void setAge30mancountnormal(Integer age30mancountnormal) {
		this.age30mancountnormal = age30mancountnormal;
	}
	public Integer getAge30manounthighside() {
		return age30manounthighside;
	}
	public void setAge30manounthighside(Integer age30manounthighside) {
		this.age30manounthighside = age30manounthighside;
	}
	public Integer getAge30manounthigh() {
		return age30manounthigh;
	}
	public void setAge30manounthigh(Integer age30manounthigh) {
		this.age30manounthigh = age30manounthigh;
	}
	public Integer getAge30womancountnormal() {
		return age30womancountnormal;
	}
	public void setAge30womancountnormal(Integer age30womancountnormal) {
		this.age30womancountnormal = age30womancountnormal;
	}
	public Integer getAge30womanounthighside() {
		return age30womanounthighside;
	}
	public void setAge30womanounthighside(Integer age30womanounthighside) {
		this.age30womanounthighside = age30womanounthighside;
	}
	public Integer getAge30womanounthigh() {
		return age30womanounthigh;
	}
	public void setAge30womanounthigh(Integer age30womanounthigh) {
		this.age30womanounthigh = age30womanounthigh;
	}
	public Integer getAge30_40mancountnormal() {
		return age30_40mancountnormal;
	}
	public void setAge30_40mancountnormal(Integer age30_40mancountnormal) {
		this.age30_40mancountnormal = age30_40mancountnormal;
	}
	public Integer getAge30_40manounthighside() {
		return age30_40manounthighside;
	}
	public void setAge30_40manounthighside(Integer age30_40manounthighside) {
		this.age30_40manounthighside = age30_40manounthighside;
	}
	public Integer getAge30_40manounthigh() {
		return age30_40manounthigh;
	}
	public void setAge30_40manounthigh(Integer age30_40manounthigh) {
		this.age30_40manounthigh = age30_40manounthigh;
	}
	public Integer getAge30_40womancountnormal() {
		return age30_40womancountnormal;
	}
	public void setAge30_40womancountnormal(Integer age30_40womancountnormal) {
		this.age30_40womancountnormal = age30_40womancountnormal;
	}
	public Integer getAge30_40womanounthighside() {
		return age30_40womanounthighside;
	}
	public void setAge30_40womanounthighside(Integer age30_40womanounthighside) {
		this.age30_40womanounthighside = age30_40womanounthighside;
	}
	public Integer getAge30_40womanounthigh() {
		return age30_40womanounthigh;
	}
	public void setAge30_40womanounthigh(Integer age30_40womanounthigh) {
		this.age30_40womanounthigh = age30_40womanounthigh;
	}
	public Integer getAge40_45mancountnormal() {
		return age40_45mancountnormal;
	}
	public void setAge40_45mancountnormal(Integer age40_45mancountnormal) {
		this.age40_45mancountnormal = age40_45mancountnormal;
	}
	public Integer getAge40_45manounthighside() {
		return age40_45manounthighside;
	}
	public void setAge40_45manounthighside(Integer age40_45manounthighside) {
		this.age40_45manounthighside = age40_45manounthighside;
	}
	public Integer getAge40_45manounthigh() {
		return age40_45manounthigh;
	}
	public void setAge40_45manounthigh(Integer age40_45manounthigh) {
		this.age40_45manounthigh = age40_45manounthigh;
	}
	public Integer getAge40_45womancountnormal() {
		return age40_45womancountnormal;
	}
	public void setAge40_45womancountnormal(Integer age40_45womancountnormal) {
		this.age40_45womancountnormal = age40_45womancountnormal;
	}
	public Integer getAge40_45womanounthighside() {
		return age40_45womanounthighside;
	}
	public void setAge40_45womanounthighside(Integer age40_45womanounthighside) {
		this.age40_45womanounthighside = age40_45womanounthighside;
	}
	public Integer getAge40_45womanounthigh() {
		return age40_45womanounthigh;
	}
	public void setAge40_45womanounthigh(Integer age40_45womanounthigh) {
		this.age40_45womanounthigh = age40_45womanounthigh;
	}
	public Integer getAge45_50mancountnormal() {
		return age45_50mancountnormal;
	}
	public void setAge45_50mancountnormal(Integer age45_50mancountnormal) {
		this.age45_50mancountnormal = age45_50mancountnormal;
	}
	public Integer getAge45_50manounthighside() {
		return age45_50manounthighside;
	}
	public void setAge45_50manounthighside(Integer age45_50manounthighside) {
		this.age45_50manounthighside = age45_50manounthighside;
	}
	public Integer getAge45_50manounthigh() {
		return age45_50manounthigh;
	}
	public void setAge45_50manounthigh(Integer age45_50manounthigh) {
		this.age45_50manounthigh = age45_50manounthigh;
	}
	public Integer getAge45_50womancountnormal() {
		return age45_50womancountnormal;
	}
	public void setAge45_50womancountnormal(Integer age45_50womancountnormal) {
		this.age45_50womancountnormal = age45_50womancountnormal;
	}
	public Integer getAge45_50womanounthighside() {
		return age45_50womanounthighside;
	}
	public void setAge45_50womanounthighside(Integer age45_50womanounthighside) {
		this.age45_50womanounthighside = age45_50womanounthighside;
	}
	public Integer getAge45_50womanounthigh() {
		return age45_50womanounthigh;
	}
	public void setAge45_50womanounthigh(Integer age45_50womanounthigh) {
		this.age45_50womanounthigh = age45_50womanounthigh;
	}
	public Integer getAge50_55mancountnormal() {
		return age50_55mancountnormal;
	}
	public void setAge50_55mancountnormal(Integer age50_55mancountnormal) {
		this.age50_55mancountnormal = age50_55mancountnormal;
	}
	public Integer getAge50_55manounthighside() {
		return age50_55manounthighside;
	}
	public void setAge50_55manounthighside(Integer age50_55manounthighside) {
		this.age50_55manounthighside = age50_55manounthighside;
	}
	public Integer getAge50_55manounthigh() {
		return age50_55manounthigh;
	}
	public void setAge50_55manounthigh(Integer age50_55manounthigh) {
		this.age50_55manounthigh = age50_55manounthigh;
	}
	public Integer getAge50_55womancountnormal() {
		return age50_55womancountnormal;
	}
	public void setAge50_55womancountnormal(Integer age50_55womancountnormal) {
		this.age50_55womancountnormal = age50_55womancountnormal;
	}
	public Integer getAge50_55womanounthighside() {
		return age50_55womanounthighside;
	}
	public void setAge50_55womanounthighside(Integer age50_55womanounthighside) {
		this.age50_55womanounthighside = age50_55womanounthighside;
	}
	public Integer getAge50_55womanounthigh() {
		return age50_55womanounthigh;
	}
	public void setAge50_55womanounthigh(Integer age50_55womanounthigh) {
		this.age50_55womanounthigh = age50_55womanounthigh;
	}
	public Integer getAge55_60mancountnormal() {
		return age55_60mancountnormal;
	}
	public void setAge55_60mancountnormal(Integer age55_60mancountnormal) {
		this.age55_60mancountnormal = age55_60mancountnormal;
	}
	public Integer getAge55_60manounthighside() {
		return age55_60manounthighside;
	}
	public void setAge55_60manounthighside(Integer age55_60manounthighside) {
		this.age55_60manounthighside = age55_60manounthighside;
	}
	public Integer getAge55_60manounthigh() {
		return age55_60manounthigh;
	}
	public void setAge55_60manounthigh(Integer age55_60manounthigh) {
		this.age55_60manounthigh = age55_60manounthigh;
	}
	public Integer getAge55_60womancountnormal() {
		return age55_60womancountnormal;
	}
	public void setAge55_60womancountnormal(Integer age55_60womancountnormal) {
		this.age55_60womancountnormal = age55_60womancountnormal;
	}
	public Integer getAge55_60womanounthighside() {
		return age55_60womanounthighside;
	}
	public void setAge55_60womanounthighside(Integer age55_60womanounthighside) {
		this.age55_60womanounthighside = age55_60womanounthighside;
	}
	public Integer getAge55_60womanounthigh() {
		return age55_60womanounthigh;
	}
	public void setAge55_60womanounthigh(Integer age55_60womanounthigh) {
		this.age55_60womanounthigh = age55_60womanounthigh;
	}
	public Integer getAge60mancountnormal() {
		return age60mancountnormal;
	}
	public void setAge60mancountnormal(Integer age60mancountnormal) {
		this.age60mancountnormal = age60mancountnormal;
	}
	public Integer getAge60manounthighside() {
		return age60manounthighside;
	}
	public void setAge60manounthighside(Integer age60manounthighside) {
		this.age60manounthighside = age60manounthighside;
	}
	public Integer getAge60manounthigh() {
		return age60manounthigh;
	}
	public void setAge60manounthigh(Integer age60manounthigh) {
		this.age60manounthigh = age60manounthigh;
	}
	public Integer getAge60womancountnormal() {
		return age60womancountnormal;
	}
	public void setAge60womancountnormal(Integer age60womancountnormal) {
		this.age60womancountnormal = age60womancountnormal;
	}
	public Integer getAge60womanounthighside() {
		return age60womanounthighside;
	}
	public void setAge60womanounthighside(Integer age60womanounthighside) {
		this.age60womanounthighside = age60womanounthighside;
	}
	public Integer getAge60womanounthigh() {
		return age60womanounthigh;
	}
	public void setAge60womanounthigh(Integer age60womanounthigh) {
		this.age60womanounthigh = age60womanounthigh;
	}
	@Override
	public String toString() {
		return "YhExportPdfSugar [mancountnormal=" + mancountnormal + ", manounthighside=" + manounthighside
				+ ", manounthigh=" + manounthigh + ", womancountnormal=" + womancountnormal + ", womanounthighside="
				+ womanounthighside + ", womanounthigh=" + womanounthigh + ", age30mancountnormal="
				+ age30mancountnormal + ", age30manounthighside=" + age30manounthighside + ", age30manounthigh="
				+ age30manounthigh + ", age30womancountnormal=" + age30womancountnormal + ", age30womanounthighside="
				+ age30womanounthighside + ", age30womanounthigh=" + age30womanounthigh + ", age30_40mancountnormal="
				+ age30_40mancountnormal + ", age30_40manounthighside=" + age30_40manounthighside
				+ ", age30_40manounthigh=" + age30_40manounthigh + ", age30_40womancountnormal="
				+ age30_40womancountnormal + ", age30_40womanounthighside=" + age30_40womanounthighside
				+ ", age30_40womanounthigh=" + age30_40womanounthigh + ", age40_45mancountnormal="
				+ age40_45mancountnormal + ", age40_45manounthighside=" + age40_45manounthighside
				+ ", age40_45manounthigh=" + age40_45manounthigh + ", age40_45womancountnormal="
				+ age40_45womancountnormal + ", age40_45womanounthighside=" + age40_45womanounthighside
				+ ", age40_45womanounthigh=" + age40_45womanounthigh + ", age45_50mancountnormal="
				+ age45_50mancountnormal + ", age45_50manounthighside=" + age45_50manounthighside
				+ ", age45_50manounthigh=" + age45_50manounthigh + ", age45_50womancountnormal="
				+ age45_50womancountnormal + ", age45_50womanounthighside=" + age45_50womanounthighside
				+ ", age45_50womanounthigh=" + age45_50womanounthigh + ", age50_55mancountnormal="
				+ age50_55mancountnormal + ", age50_55manounthighside=" + age50_55manounthighside
				+ ", age50_55manounthigh=" + age50_55manounthigh + ", age50_55womancountnormal="
				+ age50_55womancountnormal + ", age50_55womanounthighside=" + age50_55womanounthighside
				+ ", age50_55womanounthigh=" + age50_55womanounthigh + ", age55_60mancountnormal="
				+ age55_60mancountnormal + ", age55_60manounthighside=" + age55_60manounthighside
				+ ", age55_60manounthigh=" + age55_60manounthigh + ", age55_60womancountnormal="
				+ age55_60womancountnormal + ", age55_60womanounthighside=" + age55_60womanounthighside
				+ ", age55_60womanounthigh=" + age55_60womanounthigh + ", age60mancountnormal=" + age60mancountnormal
				+ ", age60manounthighside=" + age60manounthighside + ", age60manounthigh=" + age60manounthigh
				+ ", age60womancountnormal=" + age60womancountnormal + ", age60womanounthighside="
				+ age60womanounthighside + ", age60womanounthigh=" + age60womanounthigh + "]";
	}
	
	public String getJFreeChartPath(YhExportPdfSugar sugar)
	{
		JFreeChart jFreeChart = getJFreeChart(sugar);
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

	public JFreeChart getJFreeChart(YhExportPdfSugar sugar)
	{
		double valueLow[] = {
				persent(sugar.getMancountnormal()+sugar.getWomancountnormal(), sugar.getMancountnormal()+sugar.getManounthigh()+sugar.getManounthighside()+sugar.getWomancountnormal()+sugar.getWomanounthigh()+sugar.getWomanounthighside()), persent(sugar.getMancountnormal(), sugar.getMancountnormal()+sugar.getManounthigh()+sugar.getManounthighside()), persent(sugar.getWomancountnormal(), sugar.getWomancountnormal()+sugar.getWomanounthigh()+sugar.getWomanounthighside())
		};
		double valueIn[] = {
				persent(sugar.getManounthighside()+sugar.getWomanounthighside(), sugar.getMancountnormal()+sugar.getManounthigh()+sugar.getManounthighside()+sugar.getWomancountnormal()+sugar.getWomanounthigh()+sugar.getWomanounthighside()), persent(sugar.getManounthighside(), sugar.getMancountnormal()+sugar.getManounthigh()+sugar.getManounthighside()), persent(sugar.getWomanounthighside(), sugar.getWomancountnormal()+sugar.getWomanounthigh()+sugar.getWomanounthighside())
		};
		double valueHigh[] = {
				persent(sugar.getManounthigh()+sugar.getWomanounthigh(), sugar.getMancountnormal()+sugar.getManounthigh()+sugar.getManounthighside()+sugar.getWomancountnormal()+sugar.getWomanounthigh()+sugar.getWomanounthighside()), persent(sugar.getManounthigh(), sugar.getMancountnormal()+sugar.getManounthigh()+sugar.getManounthighside()), persent(sugar.getWomanounthigh(), sugar.getWomancountnormal()+sugar.getWomanounthigh()+sugar.getWomanounthighside())
		};
		String value1[] = {
				   "正常血糖", "糖尿病前期", "可疑糖尿病"
				  };
				  String value2[] = {
				   "总体", "男", "女"
				  };
				  double value[][] = {
				   valueLow, valueIn, valueHigh
				  };
				  return GroupReport.getBarChart(DatasetUtilities.createCategoryDataset(value1, value2, value), new Color[] {
				   Color.green, Color.yellow, Color.red
				  }, "评估对象血糖检测结果按性别分布（%）", 0.40000000000000002D);
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
