// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   GroupReportPhysicalActivity.java

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

public class GroupReportPhysicalActivity
{

	private int man30_high;
	private int woman30_high;
	private int man40_high;
	private int woman40_high;
	private int man45_high;
	private int woman45_high;
	private int man50_high;
	private int woman50_high;
	private int man55_high;
	private int woman55_high;
	private int man60_high;
	private int woman60_high;
	private int man70_high;
	private int woman70_high;
	private int man30_in;
	private int woman30_in;
	private int man40_in;
	private int woman40_in;
	private int man45_in;
	private int woman45_in;
	private int man50_in;
	private int woman50_in;
	private int man55_in;
	private int woman55_in;
	private int man60_in;
	private int woman60_in;
	private int man70_in;
	private int woman70_in;
	private int man30_low;
	private int woman30_low;
	private int man40_low;
	private int woman40_low;
	private int man45_low;
	private int woman45_low;
	private int man50_low;
	private int woman50_low;
	private int man55_low;
	private int woman55_low;
	private int man60_low;
	private int woman60_low;
	private int man70_low;
	private int woman70_low;
	private int all30_;
	private int all40_;
	private int all45_;
	private int all50_;
	private int all55_;
	private int all60_;
	private int all70_;
	private int all_man_30_;
	private int all_man_40_;
	private int all_man_45_;
	private int all_man_50_;
	private int all_man_55_;
	private int all_man_60_;
	private int all_man_70_;
	private int all_woman_30_;
	private int all_woman_40_;
	private int all_woman_45_;
	private int all_woman_50_;
	private int all_woman_55_;
	private int all_woman_60_;
	private int all_woman_70_;
	private int all_high;
	private int all_in;
	private int all_low;
	private int man_high;
	private int man_in;
	private int man_low;
	private int woman_high;
	private int woman_in;
	private int woman_low;
	private float man30_high_max;
	private float woman30_high_max;
	private float man40_high_max;
	private float woman40_high_max;
	private float man45_high_max;
	private float woman45_high_max;
	private float man50_high_max;
	private float woman50_high_max;
	private float man55_high_max;
	private float woman55_high_max;
	private float man60_high_max;
	private float woman60_high_max;
	private float man70_high_max;
	private float woman70_high_max;
	private float man30_in_max;
	private float woman30_in_max;
	private float man40_in_max;
	private float woman40_in_max;
	private float man45_in_max;
	private float woman45_in_max;
	private float man50_in_max;
	private float woman50_in_max;
	private float man55_in_max;
	private float woman55_in_max;
	private float man60_in_max;
	private float woman60_in_max;
	private float man70_in_max;
	private float woman70_in_max;
	private float man30_low_max;
	private float woman30_low_max;
	private float man40_low_max;
	private float woman40_low_max;
	private float man45_low_max;
	private float woman45_low_max;
	private float man50_low_max;
	private float woman50_low_max;
	private float man55_low_max;
	private float woman55_low_max;
	private float man60_low_max;
	private float woman60_low_max;
	private float man70_low_max;
	private float woman70_low_max;
	private float all30_high_max;
	private float all40_high_max;
	private float all45_high_max;
	private float all50_high_max;
	private float all55_high_max;
	private float all60_high_max;
	private float all70_high_max;
	private float all30_in_max;
	private float all40_in_max;
	private float all45_in_max;
	private float all50_in_max;
	private float all55_in_max;
	private float all60_in_max;
	private float all70_in_max;
	private float all30_low_max;
	private float all40_low_max;
	private float all45_low_max;
	private float all50_low_max;
	private float all55_low_max;
	private float all60_low_max;
	private float all70_low_max;
	private int allMount;
	private int manMount;
	private int womanMount;
	private float all_low_max;
	private float all_in_max;
	private float all_high_max;
	private float man_low_max;
	private float man_in_max;
	private float man_high_max;
	private float woman_low_max;
	private float woman_in_max;
	private float woman_high_max;

	public GroupReportPhysicalActivity()
	{
	}

	public int getMan30_high()
	{
		return man30_high;
	}

	public void setMan30_high(int man30_high)
	{
		this.man30_high = man30_high;
	}

	public int getWoman30_high()
	{
		return woman30_high;
	}

	public void setWoman30_high(int woman30_high)
	{
		this.woman30_high = woman30_high;
	}

	public int getMan40_high()
	{
		return man40_high;
	}

	public void setMan40_high(int man40_high)
	{
		this.man40_high = man40_high;
	}

	public int getWoman40_high()
	{
		return woman40_high;
	}

	public void setWoman40_high(int woman40_high)
	{
		this.woman40_high = woman40_high;
	}

	public int getMan45_high()
	{
		return man45_high;
	}

	public void setMan45_high(int man45_high)
	{
		this.man45_high = man45_high;
	}

	public int getWoman45_high()
	{
		return woman45_high;
	}

	public void setWoman45_high(int woman45_high)
	{
		this.woman45_high = woman45_high;
	}

	public int getMan50_high()
	{
		return man50_high;
	}

	public void setMan50_high(int man50_high)
	{
		this.man50_high = man50_high;
	}

	public int getWoman50_high()
	{
		return woman50_high;
	}

	public void setWoman50_high(int woman50_high)
	{
		this.woman50_high = woman50_high;
	}

	public int getMan55_high()
	{
		return man55_high;
	}

	public void setMan55_high(int man55_high)
	{
		this.man55_high = man55_high;
	}

	public int getWoman55_high()
	{
		return woman55_high;
	}

	public void setWoman55_high(int woman55_high)
	{
		this.woman55_high = woman55_high;
	}

	public int getMan60_high()
	{
		return man60_high;
	}

	public void setMan60_high(int man60_high)
	{
		this.man60_high = man60_high;
	}

	public int getWoman60_high()
	{
		return woman60_high;
	}

	public void setWoman60_high(int woman60_high)
	{
		this.woman60_high = woman60_high;
	}

	public int getMan70_high()
	{
		return man70_high;
	}

	public void setMan70_high(int man70_high)
	{
		this.man70_high = man70_high;
	}

	public int getWoman70_high()
	{
		return woman70_high;
	}

	public void setWoman70_high(int woman70_high)
	{
		this.woman70_high = woman70_high;
	}

	public int getMan30_in()
	{
		return man30_in;
	}

	public void setMan30_in(int man30_in)
	{
		this.man30_in = man30_in;
	}

	public int getWoman30_in()
	{
		return woman30_in;
	}

	public void setWoman30_in(int woman30_in)
	{
		this.woman30_in = woman30_in;
	}

	public int getMan40_in()
	{
		return man40_in;
	}

	public void setMan40_in(int man40_in)
	{
		this.man40_in = man40_in;
	}

	public int getWoman40_in()
	{
		return woman40_in;
	}

	public void setWoman40_in(int woman40_in)
	{
		this.woman40_in = woman40_in;
	}

	public int getMan45_in()
	{
		return man45_in;
	}

	public void setMan45_in(int man45_in)
	{
		this.man45_in = man45_in;
	}

	public int getWoman45_in()
	{
		return woman45_in;
	}

	public void setWoman45_in(int woman45_in)
	{
		this.woman45_in = woman45_in;
	}

	public int getMan50_in()
	{
		return man50_in;
	}

	public void setMan50_in(int man50_in)
	{
		this.man50_in = man50_in;
	}

	public int getWoman50_in()
	{
		return woman50_in;
	}

	public void setWoman50_in(int woman50_in)
	{
		this.woman50_in = woman50_in;
	}

	public int getMan55_in()
	{
		return man55_in;
	}

	public void setMan55_in(int man55_in)
	{
		this.man55_in = man55_in;
	}

	public int getWoman55_in()
	{
		return woman55_in;
	}

	public void setWoman55_in(int woman55_in)
	{
		this.woman55_in = woman55_in;
	}

	public int getMan60_in()
	{
		return man60_in;
	}

	public void setMan60_in(int man60_in)
	{
		this.man60_in = man60_in;
	}

	public int getWoman60_in()
	{
		return woman60_in;
	}

	public void setWoman60_in(int woman60_in)
	{
		this.woman60_in = woman60_in;
	}

	public int getMan70_in()
	{
		return man70_in;
	}

	public void setMan70_in(int man70_in)
	{
		this.man70_in = man70_in;
	}

	public int getWoman70_in()
	{
		return woman70_in;
	}

	public void setWoman70_in(int woman70_in)
	{
		this.woman70_in = woman70_in;
	}

	public int getMan30_low()
	{
		return man30_low;
	}

	public void setMan30_low(int man30_low)
	{
		this.man30_low = man30_low;
	}

	public int getWoman30_low()
	{
		return woman30_low;
	}

	public void setWoman30_low(int woman30_low)
	{
		this.woman30_low = woman30_low;
	}

	public int getMan40_low()
	{
		return man40_low;
	}

	public void setMan40_low(int man40_low)
	{
		this.man40_low = man40_low;
	}

	public int getWoman40_low()
	{
		return woman40_low;
	}

	public void setWoman40_low(int woman40_low)
	{
		this.woman40_low = woman40_low;
	}

	public int getMan45_low()
	{
		return man45_low;
	}

	public void setMan45_low(int man45_low)
	{
		this.man45_low = man45_low;
	}

	public int getWoman45_low()
	{
		return woman45_low;
	}

	public void setWoman45_low(int woman45_low)
	{
		this.woman45_low = woman45_low;
	}

	public int getMan50_low()
	{
		return man50_low;
	}

	public void setMan50_low(int man50_low)
	{
		this.man50_low = man50_low;
	}

	public int getWoman50_low()
	{
		return woman50_low;
	}

	public void setWoman50_low(int woman50_low)
	{
		this.woman50_low = woman50_low;
	}

	public int getMan55_low()
	{
		return man55_low;
	}

	public void setMan55_low(int man55_low)
	{
		this.man55_low = man55_low;
	}

	public int getWoman55_low()
	{
		return woman55_low;
	}

	public void setWoman55_low(int woman55_low)
	{
		this.woman55_low = woman55_low;
	}

	public int getMan60_low()
	{
		return man60_low;
	}

	public void setMan60_low(int man60_low)
	{
		this.man60_low = man60_low;
	}

	public int getWoman60_low()
	{
		return woman60_low;
	}

	public void setWoman60_low(int woman60_low)
	{
		this.woman60_low = woman60_low;
	}

	public int getMan70_low()
	{
		return man70_low;
	}

	public void setMan70_low(int man70_low)
	{
		this.man70_low = man70_low;
	}

	public int getWoman70_low()
	{
		return woman70_low;
	}

	public void setWoman70_low(int woman70_low)
	{
		this.woman70_low = woman70_low;
	}

	public int getAll30_()
	{
		return getMan30_high() + getMan30_in() + getMan30_low() + getWoman30_high() + getWoman30_in() + getWoman30_low();
	}

	public void setAll30_(int all30_)
	{
		this.all30_ = all30_;
	}

	public int getAll40_()
	{
		return getMan40_high() + getMan40_in() + getMan40_low() + getWoman40_high() + getWoman40_in() + getWoman40_low();
	}

	public void setAll40_(int all40_)
	{
		this.all40_ = all40_;
	}

	public int getAll45_()
	{
		return getMan45_high() + getMan45_in() + getMan45_low() + getWoman45_high() + getWoman45_in() + getWoman45_low();
	}

	public void setAll45_(int all45_)
	{
		this.all45_ = all45_;
	}

	public int getAll50_()
	{
		return getMan50_high() + getMan50_in() + getMan50_low() + getWoman50_high() + getWoman50_in() + getWoman50_low();
	}

	public void setAll50_(int all50_)
	{
		this.all50_ = all50_;
	}

	public int getAll55_()
	{
		return getMan55_high() + getMan55_in() + getMan55_low() + getWoman55_high() + getWoman55_in() + getWoman55_low();
	}

	public void setAll55_(int all55_)
	{
		this.all55_ = all55_;
	}

	public int getAll60_()
	{
		return getMan60_high() + getMan60_in() + getMan60_low() + getWoman60_high() + getWoman60_in() + getWoman60_low();
	}

	public void setAll60_(int all60_)
	{
		this.all60_ = all60_;
	}

	public int getAll70_()
	{
		return getMan70_high() + getMan70_in() + getMan70_low() + getWoman70_high() + getWoman70_in() + getWoman70_low();
	}

	public void setAll70_(int all70_)
	{
		this.all70_ = all70_;
	}

	public float getMan30_high_max()
	{
		return ParamType.getFloNum(((float)getMan30_high() * 100F) / (float)getAll_man_30_(), 1);
	}

	public float getMan30_in_max()
	{
		return ParamType.getFloNum(((float)getMan30_in() * 100F) / (float)getAll_man_30_(), 1);
	}

	public float getMan30_low_max()
	{
		return ParamType.getFloNum(((float)getMan30_low() * 100F) / (float)getAll_man_30_(), 1);
	}

	public float getMan40_high_max()
	{
		return ParamType.getFloNum(((float)getMan40_high() * 100F) / (float)getAll_man_40_(), 1);
	}

	public float getMan40_in_max()
	{
		return ParamType.getFloNum(((float)getMan40_in() * 100F) / (float)getAll_man_40_(), 1);
	}

	public float getMan40_low_max()
	{
		return ParamType.getFloNum(((float)getMan40_low() * 100F) / (float)getAll_man_40_(), 1);
	}

	public float getMan45_high_max()
	{
		return ParamType.getFloNum(((float)getMan45_high() * 100F) / (float)getAll_man_45_(), 1);
	}

	public float getMan45_in_max()
	{
		return ParamType.getFloNum(((float)getMan45_in() * 100F) / (float)getAll_man_45_(), 1);
	}

	public float getMan45_low_max()
	{
		return ParamType.getFloNum(((float)getMan45_low() * 100F) / (float)getAll_man_45_(), 1);
	}

	public float getMan50_high_max()
	{
		return ParamType.getFloNum(((float)getMan50_high() * 100F) / (float)getAll_man_50_(), 1);
	}

	public float getMan50_in_max()
	{
		return ParamType.getFloNum(((float)getMan50_in() * 100F) / (float)getAll_man_50_(), 1);
	}

	public float getMan50_low_max()
	{
		return ParamType.getFloNum(((float)getMan50_low() * 100F) / (float)getAll_man_50_(), 1);
	}

	public float getMan55_high_max()
	{
		return ParamType.getFloNum(((float)getMan55_high() * 100F) / (float)getAll_man_55_(), 1);
	}

	public float getMan55_in_max()
	{
		return ParamType.getFloNum(((float)getMan55_in() * 100F) / (float)getAll_man_55_(), 1);
	}

	public float getMan55_low_max()
	{
		return ParamType.getFloNum(((float)getMan55_low() * 100F) / (float)getAll_man_55_(), 1);
	}

	public float getMan60_high_max()
	{
		return ParamType.getFloNum(((float)getMan60_high() * 100F) / (float)getAll_man_60_(), 1);
	}

	public float getMan60_in_max()
	{
		return ParamType.getFloNum(((float)getMan60_in() * 100F) / (float)getAll_man_60_(), 1);
	}

	public float getMan60_low_max()
	{
		return ParamType.getFloNum(((float)getMan60_low() * 100F) / (float)getAll_man_60_(), 1);
	}

	public float getMan70_high_max()
	{
		return ParamType.getFloNum(((float)getMan70_high() * 100F) / (float)getAll_man_70_(), 1);
	}

	public float getMan70_in_max()
	{
		return ParamType.getFloNum(((float)getMan70_in() * 100F) / (float)getAll_man_70_(), 1);
	}

	public float getMan70_low_max()
	{
		return ParamType.getFloNum(((float)getMan70_low() * 100F) / (float)getAll_man_70_(), 1);
	}

	public float getWoman30_high_max()
	{
		return ParamType.getFloNum(((float)getWoman30_high() * 100F) / (float)getAll_woman_30_(), 1);
	}

	public float getWoman30_in_max()
	{
		return ParamType.getFloNum(((float)getWoman30_in() * 100F) / (float)getAll_woman_30_(), 1);
	}

	public float getWoman30_low_max()
	{
		return ParamType.getFloNum(((float)getWoman30_low() * 100F) / (float)getAll_woman_30_(), 1);
	}

	public float getWoman40_high_max()
	{
		return ParamType.getFloNum(((float)getWoman40_high() * 100F) / (float)getAll_woman_40_(), 1);
	}

	public float getWoman40_in_max()
	{
		return ParamType.getFloNum(((float)getWoman40_in() * 100F) / (float)getAll_woman_40_(), 1);
	}

	public float getWoman40_low_max()
	{
		return ParamType.getFloNum(((float)getWoman40_low() * 100F) / (float)getAll_woman_40_(), 1);
	}

	public float getWoman45_high_max()
	{
		return ParamType.getFloNum(((float)getWoman45_high() * 100F) / (float)getAll_woman_45_(), 1);
	}

	public float getWoman45_in_max()
	{
		return ParamType.getFloNum(((float)getWoman45_in() * 100F) / (float)getAll_woman_45_(), 1);
	}

	public float getWoman45_low_max()
	{
		return ParamType.getFloNum(((float)getWoman45_low() * 100F) / (float)getAll_woman_45_(), 1);
	}

	public float getWoman50_high_max()
	{
		return ParamType.getFloNum(((float)getWoman50_high() * 100F) / (float)getAll_woman_50_(), 1);
	}

	public float getWoman50_in_max()
	{
		return ParamType.getFloNum(((float)getWoman50_in() * 100F) / (float)getAll_woman_50_(), 1);
	}

	public float getWoman50_low_max()
	{
		return ParamType.getFloNum(((float)getWoman50_low() * 100F) / (float)getAll_woman_50_(), 1);
	}

	public float getWoman55_high_max()
	{
		return ParamType.getFloNum(((float)getWoman55_high() * 100F) / (float)getAll_woman_55_(), 1);
	}

	public float getWoman55_in_max()
	{
		return ParamType.getFloNum(((float)getWoman55_in() * 100F) / (float)getAll_woman_55_(), 1);
	}

	public float getWoman55_low_max()
	{
		return ParamType.getFloNum(((float)getWoman55_low() * 100F) / (float)getAll_woman_55_(), 1);
	}

	public float getWoman60_high_max()
	{
		return ParamType.getFloNum(((float)getWoman60_high() * 100F) / (float)getAll_woman_60_(), 1);
	}

	public float getWoman60_in_max()
	{
		return ParamType.getFloNum(((float)getWoman60_in() * 100F) / (float)getAll_woman_60_(), 1);
	}

	public float getWoman60_low_max()
	{
		return ParamType.getFloNum(((float)getWoman60_low() * 100F) / (float)getAll_woman_60_(), 1);
	}

	public float getWoman70_high_max()
	{
		return ParamType.getFloNum(((float)getWoman70_high() * 100F) / (float)getAll_woman_70_(), 1);
	}

	public float getWoman70_in_max()
	{
		return ParamType.getFloNum(((float)getWoman70_in() * 100F) / (float)getAll_woman_70_(), 1);
	}

	public float getWoman70_low_max()
	{
		return ParamType.getFloNum(((float)getWoman70_low() * 100F) / (float)getAll_woman_70_(), 1);
	}

	public float getAll30_high_max()
	{
		return ParamType.getFloNum(((float)(getWoman30_high() + getMan30_high()) * 100F) / (float)getAll30_(), 1);
	}

	public float getAll30_in_max()
	{
		return ParamType.getFloNum(((float)(getWoman30_in() + getMan30_in()) * 100F) / (float)getAll30_(), 1);
	}

	public float getAll30_low_max()
	{
		return ParamType.getFloNum(((float)(getWoman30_low() + getMan30_low()) * 100F) / (float)getAll30_(), 1);
	}

	public float getAll40_high_max()
	{
		return ParamType.getFloNum(((float)(getWoman40_high() + getMan40_high()) * 100F) / (float)getAll40_(), 1);
	}

	public float getAll40_in_max()
	{
		return ParamType.getFloNum(((float)(getWoman40_in() + getMan40_in()) * 100F) / (float)getAll40_(), 1);
	}

	public float getAll40_low_max()
	{
		return ParamType.getFloNum(((float)(getWoman40_low() + getMan40_low()) * 100F) / (float)getAll40_(), 1);
	}

	public float getAll45_high_max()
	{
		return ParamType.getFloNum(((float)(getWoman45_high() + getMan45_high()) * 100F) / (float)getAll45_(), 1);
	}

	public float getAll45_in_max()
	{
		return ParamType.getFloNum(((float)(getWoman45_in() + getMan45_in()) * 100F) / (float)getAll45_(), 1);
	}

	public float getAll45_low_max()
	{
		return ParamType.getFloNum(((float)(getWoman45_low() + getMan45_low()) * 100F) / (float)getAll45_(), 1);
	}

	public float getAll50_high_max()
	{
		return ParamType.getFloNum(((float)(getWoman50_high() + getMan50_high()) * 100F) / (float)getAll50_(), 1);
	}

	public float getAll50_in_max()
	{
		return ParamType.getFloNum(((float)(getWoman50_in() + getMan50_in()) * 100F) / (float)getAll50_(), 1);
	}

	public float getAll50_low_max()
	{
		return ParamType.getFloNum(((float)(getWoman50_low() + getMan50_low()) * 100F) / (float)getAll50_(), 1);
	}

	public float getAll55_high_max()
	{
		return ParamType.getFloNum(((float)(getWoman55_high() + getMan55_high()) * 100F) / (float)getAll55_(), 1);
	}

	public float getAll55_in_max()
	{
		return ParamType.getFloNum(((float)(getWoman55_in() + getMan55_in()) * 100F) / (float)getAll55_(), 1);
	}

	public float getAll55_low_max()
	{
		return ParamType.getFloNum(((float)(getWoman55_low() + getMan55_low()) * 100F) / (float)getAll55_(), 1);
	}

	public float getAll60_high_max()
	{
		return ParamType.getFloNum(((float)(getWoman60_high() + getMan60_high()) * 100F) / (float)getAll60_(), 1);
	}

	public float getAll60_in_max()
	{
		return ParamType.getFloNum(((float)(getWoman60_in() + getMan60_in()) * 100F) / (float)getAll60_(), 1);
	}

	public float getAll60_low_max()
	{
		return ParamType.getFloNum(((float)(getWoman60_low() + getMan60_low()) * 100F) / (float)getAll60_(), 1);
	}

	public float getAll70_high_max()
	{
		return ParamType.getFloNum(((float)(getWoman70_high() + getMan70_high()) * 100F) / (float)getAll70_(), 1);
	}

	public float getAll70_in_max()
	{
		return ParamType.getFloNum(((float)(getWoman70_in() + getMan70_in()) * 100F) / (float)getAll70_(), 1);
	}

	public float getAll70_low_max()
	{
		return ParamType.getFloNum(((float)(getWoman70_low() + getMan70_low()) * 100F) / (float)getAll70_(), 1);
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
		double valueLow[] = {
			(double)(getAll_low_max() / 100F), (double)(getMan_low_max() / 100F), (double)(getWoman_low_max() / 100F)
		};
		double valueIn[] = {
			(double)(getAll_in_max() / 100F), (double)(getMan_in_max() / 100F), (double)(getWoman_in_max() / 100F)
		};
		double valueHigh[] = {
			(double)(getAll_high_max() / 100F), (double)(getMan_high_max() / 100F), (double)(getWoman_high_max() / 100F)
		};
		String value1[] = {
			"����", "�е�", "���"
		};
		String value2[] = {
			"����", "��", "Ů"
		};
		double value[][] = {
			valueHigh, valueIn, valueLow
		};
		return GroupReport.getBarChart(DatasetUtilities.createCategoryDataset(value1, value2, value), new Color[] {
			Color.red, Color.yellow, Color.green
		}, "�������������ˮƽ���Ա�ֲ���%��", 0.40000000000000002D);
	}

	public int getAll_man_30_()
	{
		return getMan30_high() + getMan30_in() + getMan30_low();
	}

	public int getAll_man_40_()
	{
		return getMan40_high() + getMan40_in() + getMan40_low();
	}

	public int getAll_man_45_()
	{
		return getMan45_high() + getMan45_in() + getMan45_low();
	}

	public int getAll_man_50_()
	{
		return getMan50_high() + getMan50_in() + getMan50_low();
	}

	public int getAll_man_55_()
	{
		return getMan55_high() + getMan55_in() + getMan55_low();
	}

	public int getAll_man_60_()
	{
		return getMan60_high() + getMan60_in() + getMan60_low();
	}

	public int getAll_man_70_()
	{
		return getMan70_high() + getMan70_in() + getMan70_low();
	}

	public int getAll_woman_30_()
	{
		return getWoman30_high() + getWoman30_in() + getWoman30_low();
	}

	public int getAll_woman_40_()
	{
		return getWoman40_high() + getWoman40_in() + getWoman40_low();
	}

	public int getAll_woman_45_()
	{
		return getWoman45_high() + getWoman45_in() + getWoman45_low();
	}

	public int getAll_woman_50_()
	{
		return getWoman50_high() + getWoman50_in() + getWoman50_low();
	}

	public int getAll_woman_55_()
	{
		return getWoman55_high() + getWoman55_in() + getWoman55_low();
	}

	public int getAll_woman_60_()
	{
		return getWoman60_high() + getWoman60_in() + getWoman60_low();
	}

	public int getAll_woman_70_()
	{
		return getWoman70_high() + getWoman70_in() + getWoman70_low();
	}

	public int getAllMount()
	{
		return getAll30_() + getAll40_() + getAll45_() + getAll50_() + getAll55_() + getAll60_() + getAll70_();
	}

	public int getManMount()
	{
		return getAll_man_30_() + getAll_man_40_() + getAll_man_45_() + getAll_man_50_() + getAll_man_55_() + getAll_man_60_() + getAll_man_70_();
	}

	public int getWomanMount()
	{
		return getAll_woman_30_() + getAll_woman_40_() + getAll_woman_45_() + getAll_woman_50_() + getAll_woman_55_() + getAll_woman_60_() + getAll_woman_70_();
	}

	public float getAll_high_max()
	{
		return ParamType.getFloNum(((float)(getMan30_high() + getMan40_high() + getMan45_high() + getMan50_high() + getMan55_high() + getMan60_high() + getMan70_high() + getWoman30_high() + getWoman40_high() + getWoman45_high() + getWoman50_high() + getWoman55_high() + getWoman60_high() + getWoman70_high()) * 100F) / (float)getAllMount(), 1);
	}

	public float getAll_in_max()
	{
		return ParamType.getFloNum(((float)(getMan30_in() + getMan40_in() + getMan45_in() + getMan50_in() + getMan55_in() + getMan60_in() + getMan70_in() + getWoman30_in() + getWoman40_in() + getWoman45_in() + getWoman50_in() + getWoman55_in() + getWoman60_in() + getWoman70_in()) * 100F) / (float)getAllMount(), 1);
	}

	public float getAll_low_max()
	{
		return ParamType.getFloNum(((float)(getMan30_low() + getMan40_low() + getMan45_low() + getMan50_low() + getMan55_low() + getMan60_low() + getMan70_low() + getWoman30_low() + getWoman40_low() + getWoman45_low() + getWoman50_low() + getWoman55_low() + getWoman60_low() + getWoman70_low()) * 100F) / (float)getAllMount(), 1);
	}

	public float getMan_high_max()
	{
		return ParamType.getFloNum(((float)(getMan30_high() + getMan40_high() + getMan45_high() + getMan50_high() + getMan55_high() + getMan60_high() + getMan70_high()) * 100F) / (float)getManMount(), 1);
	}

	public float getMan_in_max()
	{
		return ParamType.getFloNum(((float)(getMan30_in() + getMan40_in() + getMan45_in() + getMan50_in() + getMan55_in() + getMan60_in() + getMan70_in()) * 100F) / (float)getManMount(), 1);
	}

	public float getMan_low_max()
	{
		return ParamType.getFloNum(((float)(getMan30_low() + getMan40_low() + getMan45_low() + getMan50_low() + getMan55_low() + getMan60_low() + getMan70_low()) * 100F) / (float)getManMount(), 1);
	}

	public float getWoman_high_max()
	{
		return ParamType.getFloNum(((float)(getWoman30_high() + getWoman40_high() + getWoman45_high() + getWoman50_high() + getWoman55_high() + getWoman60_high() + getWoman70_high()) * 100F) / (float)getWomanMount(), 1);
	}

	public float getWoman_in_max()
	{
		return ParamType.getFloNum(((float)(getWoman30_in() + getWoman40_in() + getWoman45_in() + getWoman50_in() + getWoman55_in() + getWoman60_in() + getWoman70_in()) * 100F) / (float)getWomanMount(), 1);
	}

	public float getWoman_low_max()
	{
		return ParamType.getFloNum(((float)(getWoman30_low() + getWoman40_low() + getWoman45_low() + getWoman50_low() + getWoman55_low() + getWoman60_low() + getWoman70_low()) * 100F) / (float)getWomanMount(), 1);
	}

	public int getAll_high()
	{
		return getMan30_high() + getMan40_high() + getMan45_high() + getMan50_high() + getMan55_high() + getMan60_high() + getMan70_high() + getWoman30_high() + getWoman40_high() + getWoman45_high() + getWoman50_high() + getWoman55_high() + getWoman60_high() + getWoman70_high();
	}

	public void setAll_high(int all_high)
	{
		this.all_high = all_high;
	}

	public int getAll_in()
	{
		return getMan30_in() + getMan40_in() + getMan45_in() + getMan50_in() + getMan55_in() + getMan60_in() + getMan70_in() + getWoman30_in() + getWoman40_in() + getWoman45_in() + getWoman50_in() + getWoman55_in() + getWoman60_in() + getWoman70_in();
	}

	public void setAll_in(int all_in)
	{
		this.all_in = all_in;
	}

	public int getAll_low()
	{
		return getMan30_low() + getMan40_low() + getMan45_low() + getMan50_low() + getMan55_low() + getMan60_low() + getMan70_low() + getWoman30_low() + getWoman40_low() + getWoman45_low() + getWoman50_low() + getWoman55_low() + getWoman60_low() + getWoman70_low();
	}

	public void setAll_low(int all_low)
	{
		this.all_low = all_low;
	}

	public int getMan_high()
	{
		return getMan30_high() + getMan40_high() + getMan45_high() + getMan50_high() + getMan55_high() + getMan60_high() + getMan70_high();
	}

	public void setMan_high(int man_high)
	{
		this.man_high = man_high;
	}

	public int getMan_in()
	{
		return getMan30_in() + getMan40_in() + getMan45_in() + getMan50_in() + getMan55_in() + getMan60_in() + getMan70_in();
	}

	public void setMan_in(int man_in)
	{
		this.man_in = man_in;
	}

	public int getMan_low()
	{
		return getMan30_low() + getMan40_low() + getMan45_low() + getMan50_low() + getMan55_low() + getMan60_low() + getMan70_low();
	}

	public void setMan_low(int man_low)
	{
		this.man_low = man_low;
	}

	public int getWoman_high()
	{
		return getWoman30_high() + getWoman40_high() + getWoman45_high() + getWoman50_high() + getWoman55_high() + getWoman60_high() + getWoman70_high();
	}

	public void setWoman_high(int woman_high)
	{
		this.woman_high = woman_high;
	}

	public int getWoman_in()
	{
		return getWoman30_in() + getWoman40_in() + getWoman45_in() + getWoman50_in() + getWoman55_in() + getWoman60_in() + getWoman70_in();
	}

	public void setWoman_in(int woman_in)
	{
		this.woman_in = woman_in;
	}

	public int getWoman_low()
	{
		return getWoman30_low() + getWoman40_low() + getWoman45_low() + getWoman50_low() + getWoman55_low() + getWoman60_low() + getWoman70_low();
	}

	public void setWoman_low(int woman_low)
	{
		this.woman_low = woman_low;
	}
}
