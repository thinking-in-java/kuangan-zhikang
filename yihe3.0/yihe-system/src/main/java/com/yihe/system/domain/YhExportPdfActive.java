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

public class YhExportPdfActive implements Serializable{
	private static final long serialVersionUID = 1L;
	//男充足，女充足，男不足，女不足
	private Integer Age30mansufficient;
	private Integer Age30womansufficient;
	private Integer Age30mannosufficient;
	private Integer Age30womannosufficient;
	private Integer Age30_40mansufficient;
	private Integer Age30_40womansufficient;
	private Integer Age30_40mannosufficient;
	private Integer Age30_40womannosufficient;
	private Integer Age40_45mansufficient;
	private Integer Age40_45womansufficient;
	private Integer Age40_45mannosufficient;
	private Integer Age40_45womannosufficient;
	private Integer Age45_50mansufficient;
	private Integer Age45_50womansufficient;
	private Integer Age45_50mannosufficient;
	private Integer Age45_50womannosufficient;
	private Integer Age50_55mansufficient;
	private Integer Age50_55womansufficient;
	private Integer Age50_55mannosufficient;
	private Integer Age50_55womannosufficient;
	private Integer Age55_60mansufficient;
	private Integer Age55_60womansufficient;
	private Integer Age55_60mannosufficient;
	private Integer Age55_60womannosufficient;
	private Integer Age60mansufficient;
	private Integer Age60womansufficient;
	private Integer Age60mannosufficient;
	private Integer Age60womannosufficient;
	private Integer mansufficient;
	private Integer mannosufficient;
	private Integer womansufficient;
	private Integer womannosufficient;
	private Integer allcount;
	
	public Integer getAllcount() {
		return allcount;
	}
	public void setAllcount(Integer allcount) {
		this.allcount = allcount;
	}
	public Integer getAge30mansufficient() {
		return Age30mansufficient;
	}
	public void setAge30mansufficient(Integer age30mansufficient) {
		Age30mansufficient = age30mansufficient;
	}
	public Integer getAge30womansufficient() {
		return Age30womansufficient;
	}
	public void setAge30womansufficient(Integer age30womansufficient) {
		Age30womansufficient = age30womansufficient;
	}
	public Integer getAge30mannosufficient() {
		return Age30mannosufficient;
	}
	public void setAge30mannosufficient(Integer age30mannosufficient) {
		Age30mannosufficient = age30mannosufficient;
	}
	public Integer getAge30womannosufficient() {
		return Age30womannosufficient;
	}
	public void setAge30womannosufficient(Integer age30womannosufficient) {
		Age30womannosufficient = age30womannosufficient;
	}
	public Integer getAge30_40mansufficient() {
		return Age30_40mansufficient;
	}
	public void setAge30_40mansufficient(Integer age30_40mansufficient) {
		Age30_40mansufficient = age30_40mansufficient;
	}
	public Integer getAge30_40womansufficient() {
		return Age30_40womansufficient;
	}
	public void setAge30_40womansufficient(Integer age30_40womansufficient) {
		Age30_40womansufficient = age30_40womansufficient;
	}
	public Integer getAge30_40mannosufficient() {
		return Age30_40mannosufficient;
	}
	public void setAge30_40mannosufficient(Integer age30_40mannosufficient) {
		Age30_40mannosufficient = age30_40mannosufficient;
	}
	public Integer getAge30_40womannosufficient() {
		return Age30_40womannosufficient;
	}
	public void setAge30_40womannosufficient(Integer age30_40womannosufficient) {
		Age30_40womannosufficient = age30_40womannosufficient;
	}
	public Integer getAge40_45mansufficient() {
		return Age40_45mansufficient;
	}
	public void setAge40_45mansufficient(Integer age40_45mansufficient) {
		Age40_45mansufficient = age40_45mansufficient;
	}
	public Integer getAge40_45womansufficient() {
		return Age40_45womansufficient;
	}
	public void setAge40_45womansufficient(Integer age40_45womansufficient) {
		Age40_45womansufficient = age40_45womansufficient;
	}
	public Integer getAge40_45mannosufficient() {
		return Age40_45mannosufficient;
	}
	public void setAge40_45mannosufficient(Integer age40_45mannosufficient) {
		Age40_45mannosufficient = age40_45mannosufficient;
	}
	public Integer getAge40_45womannosufficient() {
		return Age40_45womannosufficient;
	}
	public void setAge40_45womannosufficient(Integer age40_45womannosufficient) {
		Age40_45womannosufficient = age40_45womannosufficient;
	}
	public Integer getAge45_50mansufficient() {
		return Age45_50mansufficient;
	}
	public void setAge45_50mansufficient(Integer age45_50mansufficient) {
		Age45_50mansufficient = age45_50mansufficient;
	}
	public Integer getAge45_50womansufficient() {
		return Age45_50womansufficient;
	}
	public void setAge45_50womansufficient(Integer age45_50womansufficient) {
		Age45_50womansufficient = age45_50womansufficient;
	}
	public Integer getAge45_50mannosufficient() {
		return Age45_50mannosufficient;
	}
	public void setAge45_50mannosufficient(Integer age45_50mannosufficient) {
		Age45_50mannosufficient = age45_50mannosufficient;
	}
	public Integer getAge45_50womannosufficient() {
		return Age45_50womannosufficient;
	}
	public void setAge45_50womannosufficient(Integer age45_50womannosufficient) {
		Age45_50womannosufficient = age45_50womannosufficient;
	}
	public Integer getAge50_55mansufficient() {
		return Age50_55mansufficient;
	}
	public void setAge50_55mansufficient(Integer age50_55mansufficient) {
		Age50_55mansufficient = age50_55mansufficient;
	}
	public Integer getAge50_55womansufficient() {
		return Age50_55womansufficient;
	}
	public void setAge50_55womansufficient(Integer age50_55womansufficient) {
		Age50_55womansufficient = age50_55womansufficient;
	}
	public Integer getAge50_55mannosufficient() {
		return Age50_55mannosufficient;
	}
	public void setAge50_55mannosufficient(Integer age50_55mannosufficient) {
		Age50_55mannosufficient = age50_55mannosufficient;
	}
	public Integer getAge50_55womannosufficient() {
		return Age50_55womannosufficient;
	}
	public void setAge50_55womannosufficient(Integer age50_55womannosufficient) {
		Age50_55womannosufficient = age50_55womannosufficient;
	}
	public Integer getAge55_60mansufficient() {
		return Age55_60mansufficient;
	}
	public void setAge55_60mansufficient(Integer age55_60mansufficient) {
		Age55_60mansufficient = age55_60mansufficient;
	}
	public Integer getAge55_60womansufficient() {
		return Age55_60womansufficient;
	}
	public void setAge55_60womansufficient(Integer age55_60womansufficient) {
		Age55_60womansufficient = age55_60womansufficient;
	}
	public Integer getAge55_60mannosufficient() {
		return Age55_60mannosufficient;
	}
	public void setAge55_60mannosufficient(Integer age55_60mannosufficient) {
		Age55_60mannosufficient = age55_60mannosufficient;
	}
	public Integer getAge55_60womannosufficient() {
		return Age55_60womannosufficient;
	}
	public void setAge55_60womannosufficient(Integer age55_60womannosufficient) {
		Age55_60womannosufficient = age55_60womannosufficient;
	}
	public Integer getAge60mansufficient() {
		return Age60mansufficient;
	}
	public void setAge60mansufficient(Integer age60mansufficient) {
		Age60mansufficient = age60mansufficient;
	}
	public Integer getAge60womansufficient() {
		return Age60womansufficient;
	}
	public void setAge60womansufficient(Integer age60womansufficient) {
		Age60womansufficient = age60womansufficient;
	}
	public Integer getAge60mannosufficient() {
		return Age60mannosufficient;
	}
	public void setAge60mannosufficient(Integer age60mannosufficient) {
		Age60mannosufficient = age60mannosufficient;
	}
	public Integer getAge60womannosufficient() {
		return Age60womannosufficient;
	}
	public void setAge60womannosufficient(Integer age60womannosufficient) {
		Age60womannosufficient = age60womannosufficient;
	}
	public Integer getMansufficient() {
		return mansufficient;
	}
	public void setMansufficient(Integer mansufficient) {
		this.mansufficient = mansufficient;
	}
	public Integer getMannosufficient() {
		return mannosufficient;
	}
	public void setMannosufficient(Integer mannosufficient) {
		this.mannosufficient = mannosufficient;
	}
	public Integer getWomansufficient() {
		return womansufficient;
	}
	public void setWomansufficient(Integer womansufficient) {
		this.womansufficient = womansufficient;
	}
	public Integer getWomannosufficient() {
		return womannosufficient;
	}
	public void setWomannosufficient(Integer womannosufficient) {
		this.womannosufficient = womannosufficient;
	}
	@Override
	public String toString() {
		return "YhExportPdfActive [Age30mansufficient=" + Age30mansufficient + ", Age30womansufficient="
				+ Age30womansufficient + ", Age30mannosufficient=" + Age30mannosufficient + ", Age30womannosufficient="
				+ Age30womannosufficient + ", Age30_40mansufficient=" + Age30_40mansufficient
				+ ", Age30_40womansufficient=" + Age30_40womansufficient + ", Age30_40mannosufficient="
				+ Age30_40mannosufficient + ", Age30_40womannosufficient=" + Age30_40womannosufficient
				+ ", Age40_45mansufficient=" + Age40_45mansufficient + ", Age40_45womansufficient="
				+ Age40_45womansufficient + ", Age40_45mannosufficient=" + Age40_45mannosufficient
				+ ", Age40_45womannosufficient=" + Age40_45womannosufficient + ", Age45_50mansufficient="
				+ Age45_50mansufficient + ", Age45_50womansufficient=" + Age45_50womansufficient
				+ ", Age45_50mannosufficient=" + Age45_50mannosufficient + ", Age45_50womannosufficient="
				+ Age45_50womannosufficient + ", Age50_55mansufficient=" + Age50_55mansufficient
				+ ", Age50_55womansufficient=" + Age50_55womansufficient + ", Age50_55mannosufficient="
				+ Age50_55mannosufficient + ", Age50_55womannosufficient=" + Age50_55womannosufficient
				+ ", Age55_60mansufficient=" + Age55_60mansufficient + ", Age55_60womansufficient="
				+ Age55_60womansufficient + ", Age55_60mannosufficient=" + Age55_60mannosufficient
				+ ", Age55_60womannosufficient=" + Age55_60womannosufficient + ", Age60mansufficient="
				+ Age60mansufficient + ", Age60womansufficient=" + Age60womansufficient + ", Age60mannosufficient="
				+ Age60mannosufficient + ", Age60womannosufficient=" + Age60womannosufficient + ", mansufficient="
				+ mansufficient + ", mannosufficient=" + mannosufficient + ", womansufficient=" + womansufficient
				+ ", womannosufficient=" + womannosufficient + "]";
	}
	public String getJFreeChartPath(YhExportPdfActive active)
	{
		JFreeChart jFreeChart = getJFreeChart(active);
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

	public JFreeChart getJFreeChart(YhExportPdfActive active)
	{
		double valueLow[] = {
			persent(active.getMannosufficient()+active.getWomannosufficient(), active.getMannosufficient()+active.getMansufficient()+active.getWomannosufficient()+active.getWomansufficient()), persent(active.getMannosufficient(), active.getMannosufficient()+active.getMansufficient()), persent(active.getWomannosufficient(), active.getWomannosufficient()+active.getWomansufficient())
		};
		double valueHigh[] = {
			persent(active.getMansufficient()+active.getWomansufficient(), active.getMannosufficient()+active.getMansufficient()+active.getWomannosufficient()+active.getWomansufficient()), persent(active.getMansufficient(), active.getMannosufficient()+active.getMansufficient()), persent(active.getWomansufficient(), active.getWomannosufficient()+active.getWomansufficient())
		};
		String value1[] = {
				   "不足", "充分"
				  };
				  String value2[] = {
				   "总体", "男", "女"
				  };
				  double value[][] = {
						  valueLow,valueHigh
				  };
				  return GroupReport.getBarChart(DatasetUtilities.createCategoryDataset(value1, value2, value), new Color[] {
				   Color.red, Color.yellow, Color.green
				  }, "评估对象体力活动水平按性别分布（%）", 0.40000000000000002D);
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
