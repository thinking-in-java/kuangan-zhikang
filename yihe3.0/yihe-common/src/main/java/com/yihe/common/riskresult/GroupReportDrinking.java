// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   GroupReportDrinking.java

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

public class GroupReportDrinking
{

	private int drinking_high_man;
	private int drinking_high_woman;
	private int drinking_in_man;
	private int drinking_in_woman;
	private int drinking_low_man;
	private int drinking_low_woman;
	private int drinking_all;
	private int drinking_man;
	private int drinking_woman;
	private float drinking_high_man_max;
	private float drinking_high_woman_max;
	private float drinking_high_all_max;
	private float drinking_in_man_max;
	private float drinking_in_woman_max;
	private float drinking_in_all_max;
	private float drinking_low_man_max;
	private float drinking_low_woman_max;
	private float drinking_low_all_max;

	public GroupReportDrinking()
	{
	}

	public int getDrinking_high_man()
	{
		return drinking_high_man;
	}

	public void setDrinking_high_man(int drinking_high_man)
	{
		this.drinking_high_man = drinking_high_man;
	}

	public int getDrinking_high_woman()
	{
		return drinking_high_woman;
	}

	public void setDrinking_high_woman(int drinking_high_woman)
	{
		this.drinking_high_woman = drinking_high_woman;
	}

	public int getDrinking_in_man()
	{
		return drinking_in_man;
	}

	public void setDrinking_in_man(int drinking_in_man)
	{
		this.drinking_in_man = drinking_in_man;
	}

	public int getDrinking_in_woman()
	{
		return drinking_in_woman;
	}

	public void setDrinking_in_woman(int drinking_in_woman)
	{
		this.drinking_in_woman = drinking_in_woman;
	}

	public int getDrinking_low_man()
	{
		return drinking_low_man;
	}

	public void setDrinking_low_man(int drinking_low_man)
	{
		this.drinking_low_man = drinking_low_man;
	}

	public int getDrinking_low_woman()
	{
		return drinking_low_woman;
	}

	public void setDrinking_low_woman(int drinking_low_woman)
	{
		this.drinking_low_woman = drinking_low_woman;
	}

	public int getDrinking_all()
	{
		return getDrinking_high_man() + getDrinking_in_man() + getDrinking_low_man() + getDrinking_high_woman() + getDrinking_in_woman() + getDrinking_low_woman();
	}

	public float getDrinking_high_all_max()
	{
		return ParamType.getFloNum(((float)(getDrinking_high_man() + getDrinking_high_woman()) * 100F) / (float)getDrinking_all(), 1);
	}

	public float getDrinking_high_man_max()
	{
		return ParamType.getFloNum(((float)getDrinking_high_man() * 100F) / (float)getDrinking_man(), 1);
	}

	public float getDrinking_high_woman_max()
	{
		return ParamType.getFloNum(((float)getDrinking_high_woman() * 100F) / (float)getDrinking_woman(), 1);
	}

	public float getDrinking_in_all_max()
	{
		return ParamType.getFloNum(((float)(getDrinking_in_man() + getDrinking_in_woman()) * 100F) / (float)getDrinking_all(), 1);
	}

	public float getDrinking_in_man_max()
	{
		return ParamType.getFloNum(((float)getDrinking_in_man() * 100F) / (float)getDrinking_man(), 1);
	}

	public float getDrinking_in_woman_max()
	{
		return ParamType.getFloNum(((float)getDrinking_in_woman() * 100F) / (float)getDrinking_woman(), 1);
	}

	public float getDrinking_low_all_max()
	{
		return ParamType.getFloNum(((float)(getDrinking_low_man() + getDrinking_low_woman()) * 100F) / (float)getDrinking_all(), 1);
	}

	public float getDrinking_low_man_max()
	{
		return ParamType.getFloNum(((float)getDrinking_low_man() * 100F) / (float)getDrinking_man(), 1);
	}

	public float getDrinking_low_woman_max()
	{
		return ParamType.getFloNum(((float)getDrinking_low_woman() * 100F) / (float)getDrinking_woman(), 1);
	}

	public int getDrinking_man()
	{
		return getDrinking_high_man() + getDrinking_in_man() + getDrinking_low_man();
	}

	public int getDrinking_woman()
	{
		return getDrinking_high_woman() + getDrinking_in_woman() + getDrinking_low_woman();
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
		double valueHigh[] = {
			(double)(getDrinking_high_all_max() / 100F), (double)(getDrinking_high_man_max() / 100F), (double)(getDrinking_high_woman_max() / 100F)
		};
		double valueIn[] = {
			(double)(getDrinking_in_all_max() / 100F), (double)(getDrinking_in_man_max() / 100F), (double)(getDrinking_in_woman_max() / 100F)
		};
		double valueLow[] = {
			(double)(getDrinking_low_all_max() / 100F), (double)(getDrinking_low_man_max() / 100F), (double)(getDrinking_low_woman_max() / 100F)
		};
		String value1[] = {
			"��������", "��������", "������"
		};
		String value2[] = {
			"����", "��", "Ů"
		};
		double value[][] = {
			valueHigh, valueIn, valueLow
		};
		return GroupReport.getBarChart(DatasetUtilities.createCategoryDataset(value1, value2, value), new Color[] {
			Color.red, Color.yellow, Color.green
		}, "������������������Ա�ֲ���%��", 0.40000000000000002D);
	}
}
