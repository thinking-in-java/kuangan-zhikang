// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   GroupReportSmoke.java

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

public class GroupReportSmoke
{

	private int smoke_man;
	private int smoke_woman;
	private int smoke_no_man;
	private int smoke_no_woman;
	private int smoke_cessation_man;
	private int smoke_cessation_woman;
	private int smokeAll;
	private int smokeMan;
	private int smokeWoman;
	private int secondSmokeMan;
	private int secondSmokeWoman;
	private float secondSmoke_Allmax;
	private float secondSmoke_Manmax;
	private float secondSmoke_Womanmax;
	private float smoke_man_max;
	private float smoke_woman_max;
	private float smoke_no_man_max;
	private float smoke_no_woman_max;
	private float smoke_cessation_man_max;
	private float smoke_cessation_woman_max;
	private float smoke_all_max;
	private float smoke_all_no_max;
	private float smoke_all_cessation_max;

	public GroupReportSmoke()
	{
	}

	public int getSmoke_man()
	{
		return smoke_man;
	}

	public void setSmoke_man(int smoke_man)
	{
		this.smoke_man = smoke_man;
	}

	public int getSmoke_woman()
	{
		return smoke_woman;
	}

	public void setSmoke_woman(int smoke_woman)
	{
		this.smoke_woman = smoke_woman;
	}

	public int getSmoke_no_man()
	{
		return smoke_no_man;
	}

	public void setSmoke_no_man(int smoke_no_man)
	{
		this.smoke_no_man = smoke_no_man;
	}

	public int getSmoke_no_woman()
	{
		return smoke_no_woman;
	}

	public void setSmoke_no_woman(int smoke_no_woman)
	{
		this.smoke_no_woman = smoke_no_woman;
	}

	public int getSmoke_cessation_man()
	{
		return smoke_cessation_man;
	}

	public void setSmoke_cessation_man(int smoke_cessation_man)
	{
		this.smoke_cessation_man = smoke_cessation_man;
	}

	public int getSmoke_cessation_woman()
	{
		return smoke_cessation_woman;
	}

	public void setSmoke_cessation_woman(int smoke_cessation_woman)
	{
		this.smoke_cessation_woman = smoke_cessation_woman;
	}

	public int getSmokeAll()
	{
		return getSmoke_man() + getSmoke_cessation_man() + getSmoke_no_man() + getSmoke_woman() + getSmoke_cessation_woman() + getSmoke_no_woman();
	}

	public int getSmokeMan()
	{
		return getSmoke_man() + getSmoke_cessation_man() + getSmoke_no_man();
	}

	public int getSmokeWoman()
	{
		return getSmoke_woman() + getSmoke_cessation_woman() + getSmoke_no_woman();
	}

	public float getSmoke_all_cessation_max()
	{
		return ParamType.getFloNum(((float)(getSmoke_cessation_man() + getSmoke_cessation_woman()) * 100F) / (float)getSmokeAll(), 1);
	}

	public float getSmoke_all_max()
	{
		return ParamType.getFloNum(((float)(getSmoke_man() + getSmoke_woman()) * 100F) / (float)getSmokeAll(), 1);
	}

	public float getSmoke_all_no_max()
	{
		return ParamType.getFloNum(((float)(getSmoke_no_man() + getSmoke_no_woman()) * 100F) / (float)getSmokeAll(), 1);
	}

	public float getSmoke_cessation_man_max()
	{
		return ParamType.getFloNum(((float)getSmoke_cessation_man() * 100F) / (float)getSmokeMan(), 1);
	}

	public float getSmoke_cessation_woman_max()
	{
		return ParamType.getFloNum(((float)getSmoke_cessation_woman() * 100F) / (float)getSmokeWoman(), 1);
	}

	public float getSmoke_man_max()
	{
		return ParamType.getFloNum(((float)getSmoke_man() * 100F) / (float)getSmokeMan(), 1);
	}

	public float getSmoke_no_man_max()
	{
		return ParamType.getFloNum(((float)getSmoke_no_man() * 100F) / (float)getSmokeMan(), 1);
	}

	public float getSmoke_no_woman_max()
	{
		return ParamType.getFloNum(((float)getSmoke_no_woman() * 100F) / (float)getSmokeWoman(), 1);
	}

	public float getSmoke_woman_max()
	{
		return ParamType.getFloNum(((float)getSmoke_woman() * 100F) / (float)getSmokeWoman(), 1);
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
			(double)(getSmoke_all_max() / 100F), (double)(getSmoke_man_max() / 100F), (double)(getSmoke_woman_max() / 100F)
		};
		double valueIn[] = {
			(double)(getSmoke_all_no_max() / 100F), (double)(getSmoke_no_man_max() / 100F), (double)(getSmoke_no_woman_max() / 100F)
		};
		double valueLow[] = {
			(double)(getSmoke_all_cessation_max() / 100F), (double)(getSmoke_cessation_man_max() / 100F), (double)(getSmoke_cessation_woman_max() / 100F)
		};
		String value1[] = {
			"����", "������", "����"
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

	public int getSecondSmokeMan()
	{
		return secondSmokeMan;
	}

	public void setSecondSmokeMan(int secondSmokeMan)
	{
		this.secondSmokeMan = secondSmokeMan;
	}

	public int getSecondSmokeWoman()
	{
		return secondSmokeWoman;
	}

	public void setSecondSmokeWoman(int secondSmokeWoman)
	{
		this.secondSmokeWoman = secondSmokeWoman;
	}

	public float getSecondSmoke_Allmax()
	{
		return secondSmoke_Allmax;
	}

	public void setSecondSmoke_Allmax(float secondSmoke_Allmax)
	{
		this.secondSmoke_Allmax = secondSmoke_Allmax;
	}

	public float getSecondSmoke_Manmax()
	{
		return secondSmoke_Manmax;
	}

	public void setSecondSmoke_Manmax(float secondSmoke_Manmax)
	{
		this.secondSmoke_Manmax = secondSmoke_Manmax;
	}

	public float getSecondSmoke_Womanmax()
	{
		return secondSmoke_Womanmax;
	}

	public void setSecondSmoke_Womanmax(float secondSmoke_Womanmax)
	{
		this.secondSmoke_Womanmax = secondSmoke_Womanmax;
	}

	public void setSmokeAll(int smokeAll)
	{
		this.smokeAll = smokeAll;
	}

	public void setSmokeMan(int smokeMan)
	{
		this.smokeMan = smokeMan;
	}

	public void setSmokeWoman(int smokeWoman)
	{
		this.smokeWoman = smokeWoman;
	}

	public void setSmoke_man_max(float smoke_man_max)
	{
		this.smoke_man_max = smoke_man_max;
	}

	public void setSmoke_woman_max(float smoke_woman_max)
	{
		this.smoke_woman_max = smoke_woman_max;
	}

	public void setSmoke_no_man_max(float smoke_no_man_max)
	{
		this.smoke_no_man_max = smoke_no_man_max;
	}

	public void setSmoke_no_woman_max(float smoke_no_woman_max)
	{
		this.smoke_no_woman_max = smoke_no_woman_max;
	}

	public void setSmoke_cessation_man_max(float smoke_cessation_man_max)
	{
		this.smoke_cessation_man_max = smoke_cessation_man_max;
	}

	public void setSmoke_cessation_woman_max(float smoke_cessation_woman_max)
	{
		this.smoke_cessation_woman_max = smoke_cessation_woman_max;
	}

	public void setSmoke_all_max(float smoke_all_max)
	{
		this.smoke_all_max = smoke_all_max;
	}

	public void setSmoke_all_no_max(float smoke_all_no_max)
	{
		this.smoke_all_no_max = smoke_all_no_max;
	}

	public void setSmoke_all_cessation_max(float smoke_all_cessation_max)
	{
		this.smoke_all_cessation_max = smoke_all_cessation_max;
	}
}
