// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   GroupReportRiskComplication.java

package com.yihe.common.riskresult;
import com.yihe.common.utils.Path;
import java.io.File;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

// Referenced classes of package cn.well.step.util.riskresult:
//			GroupReport

public class GroupReportRiskComplication
{

	private int noRisk;
	private int oneRisk;
	private int twoRisk;
	private int threeRisk;
	private int fourRisk;
	private int fiveRisk;
	private float noRisk_max;
	private float oneRisk_max;
	private float twoRisk_max;
	private float threeRisk_max;
	private float fourRisk_max;
	private float fiveRisk_max;
	private int all;
	
	public GroupReportRiskComplication()
	{
	}

	public int getFiveRisk()
	{
		return fiveRisk;
	}

	public void setFiveRisk(int fiveRisk)
	{
		this.fiveRisk = fiveRisk;
	}

	public int getFourRisk()
	{
		return fourRisk;
	}

	public void setFourRisk(int fourRisk)
	{
		this.fourRisk = fourRisk;
	}

	public int getNoRisk()
	{
		return noRisk;
	}

	public void setNoRisk(int noRisk)
	{
		this.noRisk = noRisk;
	}

	public int getOneRisk()
	{
		return oneRisk;
	}

	public void setOneRisk(int oneRisk)
	{
		this.oneRisk = oneRisk;
	}

	public int getThreeRisk()
	{
		return threeRisk;
	}

	public void setThreeRisk(int threeRisk)
	{
		this.threeRisk = threeRisk;
	}

	public int getTwoRisk()
	{
		return twoRisk;
	}

	public void setTwoRisk(int twoRisk)
	{
		this.twoRisk = twoRisk;
	}

	public String getJFreeChartPath()
	{
		JFreeChart jFreeChar = getJFreeChart();
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

	public JFreeChart getJFreeChart()
	{
		DefaultPieDataset defaultpiedataset = new DefaultPieDataset();
		defaultpiedataset.setValue("无", getNoRisk());
		defaultpiedataset.setValue("一项", getOneRisk());
		defaultpiedataset.setValue("两项", getTwoRisk());
		defaultpiedataset.setValue("三项", getThreeRisk());
		defaultpiedataset.setValue("四项", getFourRisk());
		defaultpiedataset.setValue("五项及以上", getFiveRisk());
		return GroupReport.getPieChart(defaultpiedataset, "危险因素聚集情况");
	}
	public int getAll()
	{
		return getNoRisk() + getOneRisk() + getTwoRisk() + getThreeRisk() + getFourRisk() + getFiveRisk();
	}

	public float getNoRisk_max()
	{
		return noRisk_max;
	}

	public void setNoRisk_max(float noRisk_max)
	{
		this.noRisk_max = noRisk_max;
	}

	public float getOneRisk_max()
	{
		return oneRisk_max;
	}

	public void setOneRisk_max(float oneRisk_max)
	{
		this.oneRisk_max = oneRisk_max;
	}

	public float getTwoRisk_max()
	{
		return twoRisk_max;
	}

	public void setTwoRisk_max(float twoRisk_max)
	{
		this.twoRisk_max = twoRisk_max;
	}

	public float getThreeRisk_max()
	{
		return threeRisk_max;
	}

	public void setThreeRisk_max(float threeRisk_max)
	{
		this.threeRisk_max = threeRisk_max;
	}

	public float getFourRisk_max()
	{
		return fourRisk_max;
	}

	public void setFourRisk_max(float fourRisk_max)
	{
		this.fourRisk_max = fourRisk_max;
	}

	public float getFiveRisk_max()
	{
		return fiveRisk_max;
	}

	public void setFiveRisk_max(float fiveRisk_max)
	{
		this.fiveRisk_max = fiveRisk_max;
	}
}
