// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   GroupReportBasic.java
package com.yihe.common.riskresult;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DatasetUtilities;

import com.yihe.common.utils.ParamType;
import com.yihe.common.utils.Path;

// Referenced classes of package cn.well.step.util.riskresult:
//			GroupReport

public class GroupReportBasic
{

	public int count;
	public Date beginDate;
	public Date endDate;
	private String syear;
	private String smonth;
	private String sday;
	private String eyear;
	private String emonth;
	private String eday;
	public int heartDiseaseCount;
	public int diabetesCount;
	public int lungCancerCount;
	public int hypertensionCount;
	public int diabetesCountShadiness;
	public int hypertensionCountShadiness;
	public int age30Man;
	public int age30_40Man;
	public int age40_45Man;
	public int age45_50Man;
	public int age50_55Man;
	public int age55_60Man;
	public int age60Man;
	public int age30Woman;
	public int age30_40Woman;
	public int age40_45Woman;
	public int age45_50Woman;
	public int age50_55Woman;
	public int age55_60Woman;
	public int age60Woman;
	private int manNo;
	private int womanNo;

	public GroupReportBasic(int count)
	{
		syear = "--";
		smonth = "--";
		sday = "--";
		eyear = "--";
		emonth = "--";
		eday = "--";
		this.count = count;
	}

	public int getAgeManMax()
	{
		return age30Man + age30_40Man + age40_45Man + age45_50Man + age50_55Man + age55_60Man + age60Man;
	}

	public int getAgeWomanMax()
	{
		return age30Woman + age30_40Woman + age40_45Woman + age45_50Woman + age50_55Woman + age55_60Woman + age60Woman;
	}

	public float getAgeManMaxP()
	{
		return ParamType.getFloNum(((float)getAgeManMax() * 100F) / (float)getCount(), 1);
	}

	public float getAgeWomanMaxP()
	{
		return ParamType.getFloNum(((float)getAgeWomanMax() * 100F) / (float)getCount(), 1);
	}

	public int getAge30Max()
	{
		return age30Man + age30Woman;
	}

	public int getAge30_40Max()
	{
		return age30_40Man + age30_40Woman;
	}

	public int getAge40_45Max()
	{
		return age40_45Man + age40_45Woman;
	}

	public int getAge45_50Max()
	{
		return age45_50Man + age45_50Woman;
	}

	public int getAge50_55Max()
	{
		return age50_55Man + age50_55Woman;
	}

	public int getAge55_60Max()
	{
		return age55_60Man + age55_60Woman;
	}

	public int getAge60Max()
	{
		return age60Man + age60Woman;
	}

	public float getAge30MaxP()
	{
		return ParamType.getFloNum(((float)(age30Man + age30Woman) * 100F) / (float)getCount(), 1);
	}

	public float getAge30_40MaxP()
	{
		return ParamType.getFloNum(((float)(age30_40Man + age30_40Woman) * 100F) / (float)getCount(), 1);
	}

	public float getAge40_45MaxP()
	{
		return ParamType.getFloNum(((float)(age40_45Man + age40_45Woman) * 100F) / (float)getCount(), 1);
	}

	public float getAge45_50MaxP()
	{
		return ParamType.getFloNum(((float)(age45_50Man + age45_50Woman) * 100F) / (float)getCount(), 1);
	}

	public float getAge50_55MaxP()
	{
		return ParamType.getFloNum(((float)(age50_55Man + age50_55Woman) * 100F) / (float)getCount(), 1);
	}

	public float getAge55_60MaxP()
	{
		return ParamType.getFloNum(((float)(age55_60Man + age55_60Woman) * 100F) / (float)getCount(), 1);
	}

	public float getAge60MaxP()
	{
		return ParamType.getFloNum(((float)(age60Man + age60Woman) * 100F) / (float)getCount(), 1);
	}

	public int getCount()
	{
		return count;
	}

	public Date getBeginDate()
	{
		return beginDate;
	}

	public void setBeginDate(Date beginDate)
	{
		this.beginDate = beginDate;
	}

	public Date getEndDate()
	{
		return endDate;
	}

	public void setEndDate(Date endDate)
	{
		this.endDate = endDate;
	}

	public int getHeartDiseaseCount()
	{
		return heartDiseaseCount;
	}

	public void setHeartDiseaseCount(int heartDiseaseCount)
	{
		this.heartDiseaseCount = heartDiseaseCount;
	}

	public int getDiabetesCount()
	{
		return diabetesCount;
	}

	public void setDiabetesCount(int diabetesCount)
	{
		this.diabetesCount = diabetesCount;
	}

	public int getLungCancerCount()
	{
		return lungCancerCount;
	}

	public void setLungCancerCount(int lungCancerCount)
	{
		this.lungCancerCount = lungCancerCount;
	}

	public int getHypertensionCount()
	{
		return hypertensionCount;
	}

	public void setHypertensionCount(int hypertensionCount)
	{
		this.hypertensionCount = hypertensionCount;
	}

	public int getAge30Man()
	{
		return age30Man;
	}

	public float getAge30ManP()
	{
		return ParamType.getFloNum(((float)age30Man * 100F) / (float)getManNo(), 1);
	}

	public void setAge30Man(int age30Man)
	{
		this.age30Man = age30Man;
	}

	public int getAge30_40Man()
	{
		return age30_40Man;
	}

	public float getAge30_40ManP()
	{
		return ParamType.getFloNum(((float)age30_40Man * 100F) / (float)getManNo(), 1);
	}

	public void setAge30_40Man(int age30_40Man)
	{
		this.age30_40Man = age30_40Man;
	}

	public int getAge40_45Man()
	{
		return age40_45Man;
	}

	public float getAge40_45ManP()
	{
		return ParamType.getFloNum(((float)age40_45Man * 100F) / (float)getManNo(), 1);
	}

	public void setAge40_45Man(int age40_45Man)
	{
		this.age40_45Man = age40_45Man;
	}

	public int getAge45_50Man()
	{
		return age45_50Man;
	}

	public float getAge45_50ManP()
	{
		return ParamType.getFloNum(((float)age45_50Man * 100F) / (float)getManNo(), 1);
	}

	public void setAge45_50Man(int age45_50Man)
	{
		this.age45_50Man = age45_50Man;
	}

	public int getAge50_55Man()
	{
		return age50_55Man;
	}

	public float getAge50_55ManP()
	{
		return ParamType.getFloNum(((float)age50_55Man * 100F) / (float)getManNo(), 1);
	}

	public void setAge50_55Man(int age50_55Man)
	{
		this.age50_55Man = age50_55Man;
	}

	public int getAge55_60Man()
	{
		return age55_60Man;
	}

	public float getAge55_60ManP()
	{
		return ParamType.getFloNum(((float)age55_60Man * 100F) / (float)getManNo(), 1);
	}

	public void setAge55_60Man(int age55_60Man)
	{
		this.age55_60Man = age55_60Man;
	}

	public int getAge60Man()
	{
		return age60Man;
	}

	public float getAge60ManP()
	{
		return ParamType.getFloNum(((float)age60Man * 100F) / (float)getManNo(), 1);
	}

	public void setAge60Man(int age60Man)
	{
		this.age60Man = age60Man;
	}

	public int getAge30Woman()
	{
		return age30Woman;
	}

	public float getAge30WomanP()
	{
		return ParamType.getFloNum(((float)age30Woman * 100F) / (float)getWomanNo(), 1);
	}

	public void setAge30Woman(int age30Woman)
	{
		this.age30Woman = age30Woman;
	}

	public int getAge30_40Woman()
	{
		return age30_40Woman;
	}

	public float getAge30_40WomanP()
	{
		return ParamType.getFloNum(((float)age30_40Woman * 100F) / (float)getWomanNo(), 1);
	}

	public void setAge30_40Woman(int age30_40Woman)
	{
		this.age30_40Woman = age30_40Woman;
	}

	public int getAge40_45Woman()
	{
		return age40_45Woman;
	}

	public float getAge40_45WomanP()
	{
		return ParamType.getFloNum(((float)age40_45Woman * 100F) / (float)getWomanNo(), 1);
	}

	public void setAge40_45Woman(int age40_45Woman)
	{
		this.age40_45Woman = age40_45Woman;
	}

	public int getAge45_50Woman()
	{
		return age45_50Woman;
	}

	public float getAge45_50WomanP()
	{
		return ParamType.getFloNum(((float)age45_50Woman * 100F) / (float)getWomanNo(), 1);
	}

	public void setAge45_50Woman(int age45_50Woman)
	{
		this.age45_50Woman = age45_50Woman;
	}

	public int getAge50_55Woman()
	{
		return age50_55Woman;
	}

	public float getAge50_55WomanP()
	{
		return ParamType.getFloNum(((float)age50_55Woman * 100F) / (float)getWomanNo(), 1);
	}

	public void setAge50_55Woman(int age50_55Woman)
	{
		this.age50_55Woman = age50_55Woman;
	}

	public int getAge55_60Woman()
	{
		return age55_60Woman;
	}

	public float getAge55_60WomanP()
	{
		return ParamType.getFloNum(((float)age55_60Woman * 100F) / (float)getWomanNo(), 1);
	}

	public void setAge55_60Woman(int age55_60Woman)
	{
		this.age55_60Woman = age55_60Woman;
	}

	public int getAge60Woman()
	{
		return age60Woman;
	}

	public float getAge60WomanP()
	{
		return ParamType.getFloNum(((float)age60Woman * 100F) / (float)getWomanNo(), 1);
	}

	public void setAge60Woman(int age60Woman)
	{
		this.age60Woman = age60Woman;
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
		double value30[] = {
			(double)(getAge30MaxP() / 100F), (double)(getAge30ManP() / 100F), (double)(getAge30WomanP() / 100F)
		};
		double value30_40[] = {
			(double)(getAge30_40MaxP() / 100F), (double)(getAge30_40ManP() / 100F), (double)(getAge30_40WomanP() / 100F)
		};
		double value40_45[] = {
			(double)(getAge40_45MaxP() / 100F), (double)(getAge40_45ManP() / 100F), (double)(getAge40_45WomanP() / 100F)
		};
		double value45_50[] = {
			(double)(getAge45_50MaxP() / 100F), (double)(getAge45_50ManP() / 100F), (double)(getAge45_50WomanP() / 100F)
		};
		double value50_55[] = {
			(double)(getAge50_55MaxP() / 100F), (double)(getAge50_55ManP() / 100F), (double)(getAge50_55WomanP() / 100F)
		};
		double value55_60[] = {
			(double)(getAge55_60MaxP() / 100F), (double)(getAge55_60ManP() / 100F), (double)(getAge55_60WomanP() / 100F)
		};
		double value60[] = {
			(double)(getAge60MaxP() / 100F), (double)(getAge60ManP() / 100F), (double)(getAge60WomanP() / 100F)
		};
		String value1[] = {
			"<30", "30-40", "40-45", "45-50", "50-55", "55-60", "≥60"
		};
		String value2[] = {
			"总体", "男", "女"
		};
		double value[][] = {
			value30, value30_40, value40_45, value45_50, value50_55, value55_60, value60
		};
	
		return GroupReport.getBarChart(DatasetUtilities.createCategoryDataset(value1, value2, value), new Color[] {
			new Color(134, 194, 222), new Color(9, 117, 12), new Color(122, 16, 122), new Color(228, 50, 228), new Color(160, 249, 153), new Color(219, 71, 0), new Color(11, 66, 210)
		}, "评估对象按年龄及性别分布（%）", 0.10000000000000001D);
	}

	public int getDiabetesCountShadiness()
	{
		return diabetesCountShadiness;
	}

	public void setDiabetesCountShadiness(int diabetesCountShadiness)
	{
		this.diabetesCountShadiness = diabetesCountShadiness;
	}

	public int getHypertensionCountShadiness()
	{
		return hypertensionCountShadiness;
	}

	public void setHypertensionCountShadiness(int hypertensionCountShadiness)
	{
		this.hypertensionCountShadiness = hypertensionCountShadiness;
	}

	public int getManNo()
	{
		return getAge30_40Man() + getAge30Man() + getAge40_45Man() + getAge45_50Man() + getAge50_55Man() + getAge55_60Man() + getAge60Man();
	}

	public void setManNo(int manNo)
	{
		this.manNo = manNo;
	}

	public int getWomanNo()
	{
		return getAge30_40Woman() + getAge30Woman() + getAge40_45Woman() + getAge45_50Woman() + getAge50_55Woman() + getAge55_60Woman() + getAge60Woman();
	}

	public void setWomanNo(int womanNo)
	{
		this.womanNo = womanNo;
	}

	public String getEday()
	{
		return eday;
	}

	public void setEday(String eday)
	{
		this.eday = eday;
	}

	public String getEmonth()
	{
		return emonth;
	}

	public void setEmonth(String emonth)
	{
		this.emonth = emonth;
	}

	public String getEyear()
	{
		return eyear;
	}

	public void setEyear(String eyear)
	{
		this.eyear = eyear;
	}

	public String getSday()
	{
		return sday;
	}

	public void setSday(String sday)
	{
		this.sday = sday;
	}

	public String getSmonth()
	{
		return smonth;
	}

	public void setSmonth(String smonth)
	{
		this.smonth = smonth;
	}

	public String getSyear()
	{
		return syear;
	}

	public void setSyear(String syear)
	{
		this.syear = syear;
	}
}
