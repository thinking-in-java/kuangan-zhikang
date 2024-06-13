// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   GroupReportWaistline.java

package com.yihe.common.riskresult;
import com.yihe.common.utils.ParamType;
import com.yihe.common.utils.Path;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DatasetUtilities;

// Referenced classes of package cn.well.step.util.riskresult:
//			GroupReport

public class GroupReportWaistline
{

	private int man;
	private int woman;
	private int man30_normal;
	private int woman30_normal;
	private int man40_normal;
	private int woman40_normal;
	private int man45_normal;
	private int woman45_normal;
	private int man50_normal;
	private int woman50_normal;
	private int man55_normal;
	private int woman55_normal;
	private int man60_normal;
	private int woman60_normal;
	private int man70_normal;
	private int woman70_normal;
	private int man30_high_risk;
	private int woman30_high_risk;
	private int man40_high_risk;
	private int woman40_high_risk;
	private int man45_high_risk;
	private int woman45_high_risk;
	private int man50_high_risk;
	private int woman50_high_risk;
	private int man55_high_risk;
	private int woman55_high_risk;
	private int man60_high_risk;
	private int woman60_high_risk;
	private int man70_high_risk;
	private int woman70_high_risk;

	public GroupReportWaistline(int man, int woman)
	{
		this.man = man;
		this.woman = woman;
	}

	public float getNormal()
	{
		return ParamType.getFloNum(((getMan_normal_count() + getWoman_normal_count()) / (float)(man + woman)) * 100F, 1);
	}

	public float getHigh_risk()
	{
		return ParamType.getFloNum(((getMan_high_risk_count() + getWoman_high_risk_count()) / (float)(man + woman)) * 100F, 1);
	}

	public float getMan_normal()
	{
		return ParamType.getFloNum((getMan_normal_count() / (float)man) * 100F, 1);
	}

	public float getMan_high_risk()
	{
		return ParamType.getFloNum((getMan_high_risk_count() / (float)man) * 100F, 1);
	}

	public float getWoman_normal()
	{
		return ParamType.getFloNum((getWoman_normal_count() / (float)woman) * 100F, 1);
	}

	public float getWoman_high_risk()
	{
		return ParamType.getFloNum((getWoman_high_risk_count() / (float)woman) * 100F, 1);
	}

	public float getMan_normal_count()
	{
		return (float)(man30_normal + man40_normal + man45_normal + man50_normal + man55_normal + man60_normal + man70_normal);
	}

	public float getMan_high_risk_count()
	{
		return (float)(man30_high_risk + man40_high_risk + man45_high_risk + man50_high_risk + man55_high_risk + man60_high_risk + man70_high_risk);
	}

	public float getWoman_normal_count()
	{
		return (float)(woman30_normal + woman40_normal + woman45_normal + woman50_normal + woman55_normal + woman60_normal + woman70_normal);
	}

	public float getWoman_high_risk_count()
	{
		return (float)(woman30_high_risk + woman40_high_risk + woman45_high_risk + woman50_high_risk + woman55_high_risk + woman60_high_risk + woman70_high_risk);
	}

	public float count30()
	{
		float max = man30_normal + man30_high_risk + woman30_normal + woman30_high_risk;
		if (max == 0.0F)
			max = 1.0F;
		return max;
	}

	public float count40()
	{
		float max = man40_normal + man40_high_risk + woman40_normal + woman40_high_risk;
		if (max == 0.0F)
			max = 1.0F;
		return max;
	}

	public float count45()
	{
		float max = man45_normal + man45_high_risk + woman45_normal + woman45_high_risk;
		if (max == 0.0F)
			max = 1.0F;
		return max;
	}

	public float count50()
	{
		float max = man50_normal + man50_high_risk + woman50_normal + woman50_high_risk;
		if (max == 0.0F)
			max = 1.0F;
		return max;
	}

	public float count55()
	{
		float max = man55_normal + man55_high_risk + woman55_normal + woman55_high_risk;
		if (max == 0.0F)
			max = 1.0F;
		return max;
	}

	public float count60()
	{
		float max = man60_normal + man60_high_risk + woman60_normal + woman60_high_risk;
		if (max == 0.0F)
			max = 1.0F;
		return max;
	}

	public float count70()
	{
		float max = man70_normal + man70_high_risk + woman70_normal + woman70_high_risk;
		if (max == 0.0F)
			max = 1.0F;
		return max;
	}

	public float count_man_30()
	{
		float max = man30_normal + man30_high_risk;
		if (max == 0.0F)
			max = 1.0F;
		return max;
	}

	public float count_man_40()
	{
		float max = man40_normal + man40_high_risk;
		if (max == 0.0F)
			max = 1.0F;
		return max;
	}

	public float count_man_45()
	{
		float max = man45_normal + man45_high_risk;
		if (max == 0.0F)
			max = 1.0F;
		return max;
	}

	public float count_man_50()
	{
		float max = man50_normal + man50_high_risk;
		if (max == 0.0F)
			max = 1.0F;
		return max;
	}

	public float count_man_55()
	{
		float max = man55_normal + man55_high_risk;
		if (max == 0.0F)
			max = 1.0F;
		return max;
	}

	public float count_man_60()
	{
		float max = man60_normal + man60_high_risk;
		if (max == 0.0F)
			max = 1.0F;
		return max;
	}

	public float count_man_70()
	{
		float max = man70_normal + man70_high_risk;
		if (max == 0.0F)
			max = 1.0F;
		return max;
	}

	public float count_woman_30()
	{
		float max = woman30_normal + woman30_high_risk;
		if (max == 0.0F)
			max = 1.0F;
		return max;
	}

	public float count_woman_40()
	{
		float max = woman40_normal + woman40_high_risk;
		if (max == 0.0F)
			max = 1.0F;
		return max;
	}

	public float count_woman_45()
	{
		float max = woman45_normal + woman45_high_risk;
		if (max == 0.0F)
			max = 1.0F;
		return max;
	}

	public float count_woman_50()
	{
		float max = woman50_normal + woman50_high_risk;
		if (max == 0.0F)
			max = 1.0F;
		return max;
	}

	public float count_woman_55()
	{
		float max = woman55_normal + woman55_high_risk;
		if (max == 0.0F)
			max = 1.0F;
		return max;
	}

	public float count_woman_60()
	{
		float max = woman60_normal + woman60_high_risk;
		if (max == 0.0F)
			max = 1.0F;
		return max;
	}

	public float count_woman_70()
	{
		float max = woman70_normal + woman70_high_risk;
		if (max == 0.0F)
			max = 1.0F;
		return max;
	}

	public float getMan30_high_riskP()
	{
		return ParamType.getFloNum(((float)man30_high_risk / count_man_30()) * 100F, 1);
	}

	public float getWoman30_high_riskP()
	{
		return ParamType.getFloNum(((float)woman30_high_risk / count_woman_30()) * 100F, 1);
	}

	public float getMan40_high_riskP()
	{
		return ParamType.getFloNum(((float)man40_high_risk / count_man_40()) * 100F, 1);
	}

	public float getWoman40_high_riskP()
	{
		return ParamType.getFloNum(((float)woman40_high_risk / count_woman_40()) * 100F, 1);
	}

	public float getMan45_high_riskP()
	{
		return ParamType.getFloNum(((float)man45_high_risk / count_man_45()) * 100F, 1);
	}

	public float getWoman45_high_riskP()
	{
		return ParamType.getFloNum(((float)woman45_high_risk / count_woman_45()) * 100F, 1);
	}

	public float getMan50_high_riskP()
	{
		return ParamType.getFloNum(((float)man50_high_risk / count_man_50()) * 100F, 1);
	}

	public float getWoman50_high_riskP()
	{
		return ParamType.getFloNum(((float)woman50_high_risk / count_woman_50()) * 100F, 1);
	}

	public float getMan55_high_riskP()
	{
		return ParamType.getFloNum(((float)man55_high_risk / count_man_55()) * 100F, 1);
	}

	public float getWoman55_high_riskP()
	{
		return ParamType.getFloNum(((float)woman55_high_risk / count_woman_55()) * 100F, 1);
	}

	public float getMan60_high_riskP()
	{
		return ParamType.getFloNum(((float)man60_high_risk / count_man_60()) * 100F, 1);
	}

	public float getWoman60_high_riskP()
	{
		return ParamType.getFloNum(((float)woman60_high_risk / count_woman_60()) * 100F, 1);
	}

	public float getMan70_high_riskP()
	{
		return ParamType.getFloNum(((float)man70_high_risk / count_man_70()) * 100F, 1);
	}

	public float getWoman70_high_riskP()
	{
		return ParamType.getFloNum(((float)woman70_high_risk / count_woman_70()) * 100F, 1);
	}

	public float getMan30_normalP()
	{
		return ParamType.getFloNum(((float)man30_normal / count_man_30()) * 100F, 1);
	}

	public float getWoman30_normalP()
	{
		return ParamType.getFloNum(((float)woman30_normal / count_woman_30()) * 100F, 1);
	}

	public float getMan40_normalP()
	{
		return ParamType.getFloNum(((float)man40_normal / count_man_40()) * 100F, 1);
	}

	public float getWoman40_normalP()
	{
		return ParamType.getFloNum(((float)woman40_normal / count_woman_40()) * 100F, 1);
	}

	public float getMan45_normalP()
	{
		return ParamType.getFloNum(((float)man45_normal / count_man_45()) * 100F, 1);
	}

	public float getWoman45_normalP()
	{
		return ParamType.getFloNum(((float)woman45_normal / count_woman_45()) * 100F, 1);
	}

	public float getMan50_normalP()
	{
		return ParamType.getFloNum(((float)man50_normal / count_man_50()) * 100F, 1);
	}

	public float getWoman50_normalP()
	{
		return ParamType.getFloNum(((float)woman50_normal / count_woman_50()) * 100F, 1);
	}

	public float getMan55_normalP()
	{
		return ParamType.getFloNum(((float)man55_normal / count_man_55()) * 100F, 1);
	}

	public float getWoman55_normalP()
	{
		return ParamType.getFloNum(((float)woman55_normal / count_woman_55()) * 100F, 1);
	}

	public float getMan60_normalP()
	{
		return ParamType.getFloNum(((float)man60_normal / count_man_60()) * 100F, 1);
	}

	public float getWoman60_normalP()
	{
		return ParamType.getFloNum(((float)woman60_normal / count_woman_60()) * 100F, 1);
	}

	public float getMan70_normalP()
	{
		return ParamType.getFloNum(((float)man70_normal / count_man_70()) * 100F, 1);
	}

	public float getWoman70_normalP()
	{
		return ParamType.getFloNum(((float)woman70_normal / count_woman_70()) * 100F, 1);
	}

	public int getMan30_normal()
	{
		return man30_normal;
	}

	public void setMan30_normal(int man30_normal)
	{
		this.man30_normal = man30_normal;
	}

	public int getWoman30_normal()
	{
		return woman30_normal;
	}

	public void setWoman30_normal(int woman30_normal)
	{
		this.woman30_normal = woman30_normal;
	}

	public int getMan40_normal()
	{
		return man40_normal;
	}

	public void setMan40_normal(int man40_normal)
	{
		this.man40_normal = man40_normal;
	}

	public int getWoman40_normal()
	{
		return woman40_normal;
	}

	public void setWoman40_normal(int woman40_normal)
	{
		this.woman40_normal = woman40_normal;
	}

	public int getMan45_normal()
	{
		return man45_normal;
	}

	public void setMan45_normal(int man45_normal)
	{
		this.man45_normal = man45_normal;
	}

	public int getWoman45_normal()
	{
		return woman45_normal;
	}

	public void setWoman45_normal(int woman45_normal)
	{
		this.woman45_normal = woman45_normal;
	}

	public int getMan50_normal()
	{
		return man50_normal;
	}

	public void setMan50_normal(int man50_normal)
	{
		this.man50_normal = man50_normal;
	}

	public int getWoman50_normal()
	{
		return woman50_normal;
	}

	public void setWoman50_normal(int woman50_normal)
	{
		this.woman50_normal = woman50_normal;
	}

	public int getMan55_normal()
	{
		return man55_normal;
	}

	public void setMan55_normal(int man55_normal)
	{
		this.man55_normal = man55_normal;
	}

	public int getWoman55_normal()
	{
		return woman55_normal;
	}

	public void setWoman55_normal(int woman55_normal)
	{
		this.woman55_normal = woman55_normal;
	}

	public int getMan60_normal()
	{
		return man60_normal;
	}

	public void setMan60_normal(int man60_normal)
	{
		this.man60_normal = man60_normal;
	}

	public int getWoman60_normal()
	{
		return woman60_normal;
	}

	public void setWoman60_normal(int woman60_normal)
	{
		this.woman60_normal = woman60_normal;
	}

	public int getMan70_normal()
	{
		return man70_normal;
	}

	public void setMan70_normal(int man70_normal)
	{
		this.man70_normal = man70_normal;
	}

	public int getWoman70_normal()
	{
		return woman70_normal;
	}

	public void setWoman70_normal(int woman70_normal)
	{
		this.woman70_normal = woman70_normal;
	}

	public int getMan30_high_risk()
	{
		return man30_high_risk;
	}

	public void setMan30_high_risk(int man30_high_risk)
	{
		this.man30_high_risk = man30_high_risk;
	}

	public int getWoman30_high_risk()
	{
		return woman30_high_risk;
	}

	public void setWoman30_high_risk(int woman30_high_risk)
	{
		this.woman30_high_risk = woman30_high_risk;
	}

	public int getMan40_high_risk()
	{
		return man40_high_risk;
	}

	public void setMan40_high_risk(int man40_high_risk)
	{
		this.man40_high_risk = man40_high_risk;
	}

	public int getWoman40_high_risk()
	{
		return woman40_high_risk;
	}

	public void setWoman40_high_risk(int woman40_high_risk)
	{
		this.woman40_high_risk = woman40_high_risk;
	}

	public int getMan45_high_risk()
	{
		return man45_high_risk;
	}

	public void setMan45_high_risk(int man45_high_risk)
	{
		this.man45_high_risk = man45_high_risk;
	}

	public int getWoman45_high_risk()
	{
		return woman45_high_risk;
	}

	public void setWoman45_high_risk(int woman45_high_risk)
	{
		this.woman45_high_risk = woman45_high_risk;
	}

	public int getMan50_high_risk()
	{
		return man50_high_risk;
	}

	public void setMan50_high_risk(int man50_high_risk)
	{
		this.man50_high_risk = man50_high_risk;
	}

	public int getWoman50_high_risk()
	{
		return woman50_high_risk;
	}

	public void setWoman50_high_risk(int woman50_high_risk)
	{
		this.woman50_high_risk = woman50_high_risk;
	}

	public int getMan55_high_risk()
	{
		return man55_high_risk;
	}

	public void setMan55_high_risk(int man55_high_risk)
	{
		this.man55_high_risk = man55_high_risk;
	}

	public int getWoman55_high_risk()
	{
		return woman55_high_risk;
	}

	public void setWoman55_high_risk(int woman55_high_risk)
	{
		this.woman55_high_risk = woman55_high_risk;
	}

	public int getMan60_high_risk()
	{
		return man60_high_risk;
	}

	public void setMan60_high_risk(int man60_high_risk)
	{
		this.man60_high_risk = man60_high_risk;
	}

	public int getWoman60_high_risk()
	{
		return woman60_high_risk;
	}

	public void setWoman60_high_risk(int woman60_high_risk)
	{
		this.woman60_high_risk = woman60_high_risk;
	}

	public int getMan70_high_risk()
	{
		return man70_high_risk;
	}

	public void setMan70_high_risk(int man70_high_risk)
	{
		this.man70_high_risk = man70_high_risk;
	}

	public int getWoman70_high_risk()
	{
		return woman70_high_risk;
	}

	public void setWoman70_high_risk(int woman70_high_risk)
	{
		this.woman70_high_risk = woman70_high_risk;
	}

	public float getCount30_normalP()
	{
		return ParamType.getFloNum(((float)(man30_normal + woman30_normal) / count30()) * 100F, 1);
	}

	public float getCount40_normalP()
	{
		return ParamType.getFloNum(((float)(man40_normal + woman40_normal) / count40()) * 100F, 1);
	}

	public float getCount45_normalP()
	{
		return ParamType.getFloNum(((float)(man45_normal + woman45_normal) / count45()) * 100F, 1);
	}

	public float getCount50_normalP()
	{
		return ParamType.getFloNum(((float)(man50_normal + woman50_normal) / count50()) * 100F, 1);
	}

	public float getCount55_normalP()
	{
		return ParamType.getFloNum(((float)(man55_normal + woman55_normal) / count55()) * 100F, 1);
	}

	public float getCount60_normalP()
	{
		return ParamType.getFloNum(((float)(man60_normal + woman60_normal) / count60()) * 100F, 1);
	}

	public float getCount70_normalP()
	{
		return ParamType.getFloNum(((float)(man70_normal + woman70_normal) / count70()) * 100F, 1);
	}

	public float getCount30_high_riskP()
	{
		return ParamType.getFloNum(((float)(man30_high_risk + woman30_high_risk) / count30()) * 100F, 1);
	}

	public float getCount40_high_riskP()
	{
		return ParamType.getFloNum(((float)(man40_high_risk + woman40_high_risk) / count40()) * 100F, 1);
	}

	public float getCount45_high_riskP()
	{
		return ParamType.getFloNum(((float)(man45_high_risk + woman45_high_risk) / count45()) * 100F, 1);
	}

	public float getCount50_high_riskP()
	{
		return ParamType.getFloNum(((float)(man50_high_risk + woman50_high_risk) / count50()) * 100F, 1);
	}

	public float getCount55_high_riskP()
	{
		return ParamType.getFloNum(((float)(man55_high_risk + woman55_high_risk) / count55()) * 100F, 1);
	}

	public float getCount60_high_riskP()
	{
		return ParamType.getFloNum(((float)(man60_high_risk + woman60_high_risk) / count60()) * 100F, 1);
	}

	public float getCount70_high_riskP()
	{
		return ParamType.getFloNum(((float)(man70_high_risk + woman70_high_risk) / count70()) * 100F, 1);
	}

	public String getJFreeChartPath()
	{
		JFreeChart jFreeChart = getJFreeChart();
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


	public JFreeChart getJFreeChart()
	{
		double value_normal[] = {
			(double)(getNormal() / 100F), (double)(getMan_normal() / 100F), (double)(getWoman_normal() / 100F)
		};
		double value_high_risk[] = {
			(double)(getHigh_risk() / 100F), (double)(getMan_high_risk() / 100F), (double)(getWoman_high_risk() / 100F)
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

	public int getMan()
	{
		return man;
	}

	public void setMan(int man)
	{
		this.man = man;
	}

	public int getWoman()
	{
		return woman;
	}

	public void setWoman(int woman)
	{
		this.woman = woman;
	}
}
