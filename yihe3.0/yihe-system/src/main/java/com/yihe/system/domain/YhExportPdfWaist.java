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

public class YhExportPdfWaist  implements Serializable{
		private static final long serialVersionUID = 1L;
		private Integer mancountnormal;
		private Integer mancounthigh;
		private Integer womancountnormal;
		private Integer womancounthigh;
		private Integer age30mancountnormal;
		private Integer age30mancounthigh;
		private Integer age30womancountnormal;
		private Integer age30womancounthigh;
		private Integer age30_40mancountnormal;
		private Integer age30_40mancounthigh;
		private Integer age30_40womancountnormal;
		private Integer age30_40womancounthigh;
		private Integer age40_45mancountnormal;
		private Integer age40_45mancounthigh;
		private Integer age40_45womancountnormal;
		private Integer age40_45womancounthigh;
		private Integer age45_50mancountnormal;
		private Integer age45_50mancounthigh;
		private Integer age45_50womancountnormal;
		private Integer age45_50womancounthigh;
		private Integer age50_55mancountnormal;
		private Integer age50_55mancounthigh;
		private Integer age50_55womancountnormal;
		private Integer age50_55womancounthigh;
		private Integer age55_60mancountnormal;
		private Integer age55_60mancounthigh;
		private Integer age55_60womancountnormal;
		private Integer age55_60womancounthigh;
		private Integer age60mancountnormal;
		private Integer age60mancounthigh;
		private Integer age60womancountnormal;
		private Integer age60womancounthigh;
		public Integer getMancountnormal() {
			return mancountnormal;
		}
		public void setMancountnormal(Integer mancountnormal) {
			this.mancountnormal = mancountnormal;
		}
		public Integer getMancounthigh() {
			return mancounthigh;
		}
		public void setMancounthigh(Integer mancounthigh) {
			this.mancounthigh = mancounthigh;
		}
		public Integer getWomancountnormal() {
			return womancountnormal;
		}
		public void setWomancountnormal(Integer womancountnormal) {
			this.womancountnormal = womancountnormal;
		}
		public Integer getWomancounthigh() {
			return womancounthigh;
		}
		public void setWomancounthigh(Integer womancounthigh) {
			this.womancounthigh = womancounthigh;
		}
		public Integer getAge30mancountnormal() {
			return age30mancountnormal;
		}
		public void setAge30mancountnormal(Integer age30mancountnormal) {
			this.age30mancountnormal = age30mancountnormal;
		}
		public Integer getAge30mancounthigh() {
			return age30mancounthigh;
		}
		public void setAge30mancounthigh(Integer age30mancounthigh) {
			this.age30mancounthigh = age30mancounthigh;
		}
		public Integer getAge30womancountnormal() {
			return age30womancountnormal;
		}
		public void setAge30womancountnormal(Integer age30womancountnormal) {
			this.age30womancountnormal = age30womancountnormal;
		}
		public Integer getAge30womancounthigh() {
			return age30womancounthigh;
		}
		public void setAge30womancounthigh(Integer age30womancounthigh) {
			this.age30womancounthigh = age30womancounthigh;
		}
		public Integer getAge30_40mancountnormal() {
			return age30_40mancountnormal;
		}
		public void setAge30_40mancountnormal(Integer age30_40mancountnormal) {
			this.age30_40mancountnormal = age30_40mancountnormal;
		}
		public Integer getAge30_40mancounthigh() {
			return age30_40mancounthigh;
		}
		public void setAge30_40mancounthigh(Integer age30_40mancounthigh) {
			this.age30_40mancounthigh = age30_40mancounthigh;
		}
		public Integer getAge30_40womancountnormal() {
			return age30_40womancountnormal;
		}
		public void setAge30_40womancountnormal(Integer age30_40womancountnormal) {
			this.age30_40womancountnormal = age30_40womancountnormal;
		}
		public Integer getAge30_40womancounthigh() {
			return age30_40womancounthigh;
		}
		public void setAge30_40womancounthigh(Integer age30_40womancounthigh) {
			this.age30_40womancounthigh = age30_40womancounthigh;
		}
		public Integer getAge40_45mancountnormal() {
			return age40_45mancountnormal;
		}
		public void setAge40_45mancountnormal(Integer age40_45mancountnormal) {
			this.age40_45mancountnormal = age40_45mancountnormal;
		}
		public Integer getAge40_45mancounthigh() {
			return age40_45mancounthigh;
		}
		public void setAge40_45mancounthigh(Integer age40_45mancounthigh) {
			this.age40_45mancounthigh = age40_45mancounthigh;
		}
		public Integer getAge40_45womancountnormal() {
			return age40_45womancountnormal;
		}
		public void setAge40_45womancountnormal(Integer age40_45womancountnormal) {
			this.age40_45womancountnormal = age40_45womancountnormal;
		}
		public Integer getAge40_45womancounthigh() {
			return age40_45womancounthigh;
		}
		public void setAge40_45womancounthigh(Integer age40_45womancounthigh) {
			this.age40_45womancounthigh = age40_45womancounthigh;
		}
		public Integer getAge45_50mancountnormal() {
			return age45_50mancountnormal;
		}
		public void setAge45_50mancountnormal(Integer age45_50mancountnormal) {
			this.age45_50mancountnormal = age45_50mancountnormal;
		}
		public Integer getAge45_50mancounthigh() {
			return age45_50mancounthigh;
		}
		public void setAge45_50mancounthigh(Integer age45_50mancounthigh) {
			this.age45_50mancounthigh = age45_50mancounthigh;
		}
		public Integer getAge45_50womancountnormal() {
			return age45_50womancountnormal;
		}
		public void setAge45_50womancountnormal(Integer age45_50womancountnormal) {
			this.age45_50womancountnormal = age45_50womancountnormal;
		}
		public Integer getAge45_50womancounthigh() {
			return age45_50womancounthigh;
		}
		public void setAge45_50womancounthigh(Integer age45_50womancounthigh) {
			this.age45_50womancounthigh = age45_50womancounthigh;
		}
		public Integer getAge50_55mancountnormal() {
			return age50_55mancountnormal;
		}
		public void setAge50_55mancountnormal(Integer age50_55mancountnormal) {
			this.age50_55mancountnormal = age50_55mancountnormal;
		}
		public Integer getAge50_55mancounthigh() {
			return age50_55mancounthigh;
		}
		public void setAge50_55mancounthigh(Integer age50_55mancounthigh) {
			this.age50_55mancounthigh = age50_55mancounthigh;
		}
		public Integer getAge50_55womancountnormal() {
			return age50_55womancountnormal;
		}
		public void setAge50_55womancountnormal(Integer age50_55womancountnormal) {
			this.age50_55womancountnormal = age50_55womancountnormal;
		}
		public Integer getAge50_55womancounthigh() {
			return age50_55womancounthigh;
		}
		public void setAge50_55womancounthigh(Integer age50_55womancounthigh) {
			this.age50_55womancounthigh = age50_55womancounthigh;
		}
		public Integer getAge55_60mancountnormal() {
			return age55_60mancountnormal;
		}
		public void setAge55_60mancountnormal(Integer age55_60mancountnormal) {
			this.age55_60mancountnormal = age55_60mancountnormal;
		}
		public Integer getAge55_60mancounthigh() {
			return age55_60mancounthigh;
		}
		public void setAge55_60mancounthigh(Integer age55_60mancounthigh) {
			this.age55_60mancounthigh = age55_60mancounthigh;
		}
		public Integer getAge55_60womancountnormal() {
			return age55_60womancountnormal;
		}
		public void setAge55_60womancountnormal(Integer age55_60womancountnormal) {
			this.age55_60womancountnormal = age55_60womancountnormal;
		}
		public Integer getAge55_60womancounthigh() {
			return age55_60womancounthigh;
		}
		public void setAge55_60womancounthigh(Integer age55_60womancounthigh) {
			this.age55_60womancounthigh = age55_60womancounthigh;
		}
		public Integer getAge60mancountnormal() {
			return age60mancountnormal;
		}
		public void setAge60mancountnormal(Integer age60mancountnormal) {
			this.age60mancountnormal = age60mancountnormal;
		}
		public Integer getAge60mancounthigh() {
			return age60mancounthigh;
		}
		public void setAge60mancounthigh(Integer age60mancounthigh) {
			this.age60mancounthigh = age60mancounthigh;
		}
		public Integer getAge60womancountnormal() {
			return age60womancountnormal;
		}
		public void setAge60womancountnormal(Integer age60womancountnormal) {
			this.age60womancountnormal = age60womancountnormal;
		}
		public Integer getAge60womancounthigh() {
			return age60womancounthigh;
		}
		public void setAge60womancounthigh(Integer age60womancounthigh) {
			this.age60womancounthigh = age60womancounthigh;
		}
		@Override
		public String toString() {
			return "YhExportPdfWaist [mancountnormal=" + mancountnormal + ", mancounthigh=" + mancounthigh
					+ ", womancountnormal=" + womancountnormal + ", womancounthigh=" + womancounthigh
					+ ", age30mancountnormal=" + age30mancountnormal + ", age30mancounthigh=" + age30mancounthigh
					+ ", age30womancountnormal=" + age30womancountnormal + ", age30womancounthigh="
					+ age30womancounthigh + ", age30_40mancountnormal=" + age30_40mancountnormal
					+ ", age30_40mancounthigh=" + age30_40mancounthigh + ", age30_40womancountnormal="
					+ age30_40womancountnormal + ", age30_40womancounthigh=" + age30_40womancounthigh
					+ ", age40_45mancountnormal=" + age40_45mancountnormal + ", age40_45mancounthigh="
					+ age40_45mancounthigh + ", age40_45womancountnormal=" + age40_45womancountnormal
					+ ", age40_45womancounthigh=" + age40_45womancounthigh + ", age45_50mancountnormal="
					+ age45_50mancountnormal + ", age45_50mancounthigh=" + age45_50mancounthigh
					+ ", age45_50womancountnormal=" + age45_50womancountnormal + ", age45_50womancounthigh="
					+ age45_50womancounthigh + ", age50_55mancountnormal=" + age50_55mancountnormal
					+ ", age50_55mancounthigh=" + age50_55mancounthigh + ", age50_55womancountnormal="
					+ age50_55womancountnormal + ", age50_55womancounthigh=" + age50_55womancounthigh
					+ ", age55_60mancountnormal=" + age55_60mancountnormal + ", age55_60mancounthigh="
					+ age55_60mancounthigh + ", age55_60womancountnormal=" + age55_60womancountnormal
					+ ", age55_60womancounthigh=" + age55_60womancounthigh + ", age60mancountnormal="
					+ age60mancountnormal + ", age60mancounthigh=" + age60mancounthigh + ", age60womancountnormal="
					+ age60womancountnormal + ", age60womancounthigh=" + age60womancounthigh + "]";
		}
		public String getJFreeChartPath(YhExportPdfWaist pdfwaist)
		{
			JFreeChart jFreeChart = getJFreeChart(pdfwaist);
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


		public JFreeChart getJFreeChart(YhExportPdfWaist pdfwaist)
		{
			double value_normal[] = {
				(persent(pdfwaist.getMancountnormal()+pdfwaist.getWomancountnormal(), pdfwaist.getMancounthigh()+pdfwaist.getMancountnormal()+pdfwaist.getWomancounthigh()+pdfwaist.getWomancountnormal())), persent(pdfwaist.getMancountnormal(),pdfwaist.getMancounthigh()+pdfwaist.getMancountnormal()), persent(pdfwaist.getWomancountnormal(), pdfwaist.getWomancounthigh()+pdfwaist.getWomancountnormal())
			};
			double value_high_risk[] = {
					(persent(pdfwaist.getMancounthigh()+pdfwaist.getWomancounthigh(), pdfwaist.getMancounthigh()+pdfwaist.getMancountnormal()+pdfwaist.getWomancounthigh()+pdfwaist.getWomancountnormal())), persent(pdfwaist.getMancounthigh(),pdfwaist.getMancounthigh()+pdfwaist.getMancountnormal()), persent(pdfwaist.getWomancounthigh(), pdfwaist.getWomancounthigh()+pdfwaist.getWomancountnormal())
			};
			String value1[] = {
				"正常", "高危"
			};
			String value2[] = {
				"总体", "男", "女"
			};
			double value[][] = {
				value_normal, value_high_risk
			};
			return GroupReport.getBarChart(DatasetUtilities.createCategoryDataset(value1, value2, value), new Color[] {
				Color.GREEN, Color.red
			}, "评估对象腰围按性别分布（%）", 0.5D);
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
