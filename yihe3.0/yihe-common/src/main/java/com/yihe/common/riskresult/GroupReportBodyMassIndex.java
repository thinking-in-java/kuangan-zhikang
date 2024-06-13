// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   GroupReportBodyMassIndex.java

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

public class GroupReportBodyMassIndex
{

	private int man;
	private int woman;
	private int count;
	public int man30_thin;
	public int woman30_thin;
	public int man40_thin;
	public int woman40_thin;
	public int man45_thin;
	public int woman45_thin;
	public int man50_thin;
	public int woman50_thin;
	public int man55_thin;
	public int woman55_thin;
	public int man60_thin;
	public int woman60_thin;
	public int man70_thin;
	public int woman70_thin;
	public int man30_normal;
	public int woman30_normal;
	public int man40_normal;
	public int woman40_normal;
	public int man45_normal;
	public int woman45_normal;
	public int man50_normal;
	public int woman50_normal;
	public int man55_normal;
	public int woman55_normal;
	public int man60_normal;
	public int woman60_normal;
	public int man70_normal;
	public int woman70_normal;
	public int man30_overweight;
	public int woman30_overweight;
	public int man40_overweight;
	public int woman40_overweight;
	public int man45_overweight;
	public int woman45_overweight;
	public int man50_overweight;
	public int woman50_overweight;
	public int man55_overweight;
	public int woman55_overweight;
	public int man60_overweight;
	public int woman60_overweight;
	public int man70_overweight;
	public int woman70_overweight;
	public int man30_obesity;
	public int woman30_obesity;
	public int man40_obesity;
	public int woman40_obesity;
	public int man45_obesity;
	public int woman45_obesity;
	public int man50_obesity;
	public int woman50_obesity;
	public int man55_obesity;
	public int woman55_obesity;
	public int man60_obesity;
	public int woman60_obesity;
	public int man70_obesity;
	public int woman70_obesity;
	private float manThinMax;
	private int manThin;
	private float manNormalMax;
	private int manNormal;
	private float manOverweightMax;
	private int manOverweight;
	private float manobesityMax;
	private int manobesity;
	private float womanThinMax;
	private int womanThin;
	private float womanNormalMax;
	private int womanNormal;
	private float womanOverweightMax;
	private int womanOverweight;
	private float womanobesityMax;
	private int womanobesity;
	private float allThinMax;
	private float allNormalMax;
	private float allOverweightMax;
	private float allobesityMax;

	public GroupReportBodyMassIndex(int man, int woman, int count)
	{
		this.man = man;
		this.woman = woman;
		this.count = count;
	}

	public float getThin()
	{
		return ParamType.getFloNum(((getMan_thin_count() + getWoman_thin_count()) / (float)(man + woman)) * 100F, 1);
	}

	public float getNormal()
	{
		return ParamType.getFloNum(((getMan_normal_count() + getWoman_normal_count()) / (float)(man + woman)) * 100F, 1);
	}

	public float getOverweight()
	{
		return ParamType.getFloNum(((getMan_overweight_count() + getWoman_overweight_count()) / (float)(man + woman)) * 100F, 1);
	}

	public float getObesity()
	{
		return ParamType.getFloNum(((getMan_obesity_count() + getWoman_obesity_count()) / (float)(man + woman)) * 100F, 1);
	}

	public float man_thin()
	{
		return ParamType.getFloNum((getMan_thin_count() / (float)man) * 100F, 1);
	}

	public float man_normal()
	{
		return ParamType.getFloNum((getMan_normal_count() / (float)man) * 100F, 1);
	}

	public float man_overweight()
	{
		return ParamType.getFloNum((getMan_overweight_count() / (float)man) * 100F, 1);
	}

	public float man_obesity()
	{
		return ParamType.getFloNum((getMan_obesity_count() / (float)man) * 100F, 1);
	}

	public float woman_thin()
	{
		return ParamType.getFloNum((getWoman_thin_count() / (float)woman) * 100F, 1);
	}

	public float woman_normal()
	{
		return ParamType.getFloNum((getWoman_normal_count() / (float)woman) * 100F, 1);
	}

	public float woman_overweight()
	{
		return ParamType.getFloNum((getWoman_overweight_count() / (float)woman) * 100F, 1);
	}

	public float woman_obesity()
	{
		return ParamType.getFloNum((getWoman_obesity_count() / (float)woman) * 100F, 1);
	}

	public float getMan_thin_count()
	{
		return (float)(man30_thin + man40_thin + man45_thin + man50_thin + man55_thin + man60_thin + man70_thin);
	}

	public float getMan_normal_count()
	{
		return (float)(man30_normal + man40_normal + man45_normal + man50_normal + man55_normal + man60_normal + man70_normal);
	}

	public float getMan_overweight_count()
	{
		return (float)(man30_overweight + man40_overweight + man45_overweight + man50_overweight + man55_overweight + man60_overweight + man70_overweight);
	}

	public float getMan_obesity_count()
	{
		return (float)(man30_obesity + man40_obesity + man45_obesity + man50_obesity + man55_obesity + man60_obesity + man70_obesity);
	}

	public float getWoman_thin_count()
	{
		return (float)(woman30_thin + woman40_thin + woman45_thin + woman50_thin + woman55_thin + woman60_thin + woman70_thin);
	}

	public float getWoman_normal_count()
	{
		return (float)(woman30_normal + woman40_normal + woman45_normal + woman50_normal + woman55_normal + woman60_normal + woman70_normal);
	}

	public float getWoman_overweight_count()
	{
		return (float)(woman30_overweight + woman40_overweight + woman45_overweight + woman50_overweight + woman55_overweight + woman60_overweight + woman70_overweight);
	}

	public float getWoman_obesity_count()
	{
		return (float)(woman30_obesity + woman40_obesity + woman45_obesity + woman50_obesity + woman55_obesity + woman60_obesity + woman70_obesity);
	}

	private float count30()
	{
		float max = man30_thin + man30_normal + man30_overweight + man30_obesity + woman30_thin + woman30_normal + woman30_overweight + woman30_obesity;
		if (max == 0.0F)
			max = 1.0F;
		return max;
	}

	private float count40()
	{
		float max = man40_thin + man40_normal + man40_overweight + man40_obesity + woman40_thin + woman40_normal + woman40_overweight + woman40_obesity;
		if (max == 0.0F)
			max = 1.0F;
		return max;
	}

	private float count45()
	{
		float max = man45_thin + man45_normal + man45_overweight + man45_obesity + woman45_thin + woman45_normal + woman45_overweight + woman45_obesity;
		if (max == 0.0F)
			max = 1.0F;
		return max;
	}

	private float count50()
	{
		float max = man50_thin + man50_normal + man50_overweight + man50_obesity + woman50_thin + woman50_normal + woman50_overweight + woman50_obesity;
		if (max == 0.0F)
			max = 1.0F;
		return max;
	}

	private float count55()
	{
		float max = man55_thin + man55_normal + man55_overweight + man55_obesity + woman55_thin + woman55_normal + woman55_overweight + woman55_obesity;
		if (max == 0.0F)
			max = 1.0F;
		return max;
	}

	private float count60()
	{
		float max = man60_thin + man60_normal + man60_overweight + man60_obesity + woman60_thin + woman60_normal + woman60_overweight + woman60_obesity;
		if (max == 0.0F)
			max = 1.0F;
		return max;
	}

	private float count70()
	{
		float max = man70_thin + man70_normal + man70_overweight + man70_obesity + woman70_thin + woman70_normal + woman70_overweight + woman70_obesity;
		if (max == 0.0F)
			max = 1.0F;
		return max;
	}

	private float count_man_30()
	{
		float max = man30_thin + man30_normal + man30_overweight + man30_obesity;
		if (max == 0.0F)
			max = 1.0F;
		return max;
	}

	private float count_man_40()
	{
		float max = man40_thin + man40_normal + man40_overweight + man40_obesity;
		if (max == 0.0F)
			max = 1.0F;
		return max;
	}

	private float count_man_45()
	{
		float max = man45_thin + man45_normal + man45_overweight + man45_obesity;
		if (max == 0.0F)
			max = 1.0F;
		return max;
	}

	private float count_man_50()
	{
		float max = man50_thin + man50_normal + man50_overweight + man50_obesity;
		if (max == 0.0F)
			max = 1.0F;
		return max;
	}

	private float count_man_55()
	{
		float max = man55_thin + man55_normal + man55_overweight + man55_obesity;
		if (max == 0.0F)
			max = 1.0F;
		return max;
	}

	private float count_man_60()
	{
		float max = man60_thin + man60_normal + man60_overweight + man60_obesity;
		if (max == 0.0F)
			max = 1.0F;
		return max;
	}

	private float count_man_70()
	{
		float max = man70_thin + man70_normal + man70_overweight + man70_obesity;
		if (max == 0.0F)
			max = 1.0F;
		return max;
	}

	private float count_woman_30()
	{
		float max = woman30_thin + woman30_normal + woman30_overweight + woman30_obesity;
		if (max == 0.0F)
			max = 1.0F;
		return max;
	}

	private float count_woman_40()
	{
		float max = woman40_thin + woman40_normal + woman40_overweight + woman40_obesity;
		if (max == 0.0F)
			max = 1.0F;
		return max;
	}

	private float count_woman_45()
	{
		float max = woman45_thin + woman45_normal + woman45_overweight + woman45_obesity;
		if (max == 0.0F)
			max = 1.0F;
		return max;
	}

	private float count_woman_50()
	{
		float max = woman50_thin + woman50_normal + woman50_overweight + woman50_obesity;
		if (max == 0.0F)
			max = 1.0F;
		return max;
	}

	private float count_woman_55()
	{
		float max = woman55_thin + woman55_normal + woman55_overweight + woman55_obesity;
		if (max == 0.0F)
			max = 1.0F;
		return max;
	}

	private float count_woman_60()
	{
		float max = woman60_thin + woman60_normal + woman60_overweight + woman60_obesity;
		if (max == 0.0F)
			max = 1.0F;
		return max;
	}

	private float count_woman_70()
	{
		float max = woman70_thin + woman70_normal + woman70_overweight + woman70_obesity;
		if (max == 0.0F)
			max = 1.0F;
		return max;
	}

	public float getMan30_thinP()
	{
		return ParamType.getFloNum(((float)man30_thin / count_man_30()) * 100F, 1);
	}

	public float getWoman30_thinP()
	{
		return ParamType.getFloNum(((float)woman30_thin / count_woman_30()) * 100F, 1);
	}

	public float getMan40_thinP()
	{
		return ParamType.getFloNum(((float)man40_thin / count_man_40()) * 100F, 1);
	}

	public float getWoman40_thinP()
	{
		return ParamType.getFloNum(((float)woman40_thin / count_woman_40()) * 100F, 1);
	}

	public float getMan45_thinP()
	{
		return ParamType.getFloNum(((float)man45_thin / count_man_45()) * 100F, 1);
	}

	public float getWoman45_thinP()
	{
		return ParamType.getFloNum(((float)woman45_thin / count_woman_45()) * 100F, 1);
	}

	public float getMan50_thinP()
	{
		return ParamType.getFloNum(((float)man50_thin / count_man_50()) * 100F, 1);
	}

	public float getWoman50_thinP()
	{
		return ParamType.getFloNum(((float)woman50_thin / count_woman_50()) * 100F, 1);
	}

	public float getMan55_thinP()
	{
		return ParamType.getFloNum(((float)man55_thin / count_man_55()) * 100F, 1);
	}

	public float getWoman55_thinP()
	{
		return ParamType.getFloNum(((float)woman55_thin / count_woman_55()) * 100F, 1);
	}

	public float getMan60_thinP()
	{
		return ParamType.getFloNum(((float)man60_thin / count_man_60()) * 100F, 1);
	}

	public float getWoman60_thinP()
	{
		return ParamType.getFloNum(((float)woman60_thin / count_woman_60()) * 100F, 1);
	}

	public float getMan70_thinP()
	{
		return ParamType.getFloNum(((float)man70_thin / count_man_70()) * 100F, 1);
	}

	public float getWoman70_thinP()
	{
		return ParamType.getFloNum(((float)woman70_thin / count_woman_70()) * 100F, 1);
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

	public float getMan30_overweightP()
	{
		return ParamType.getFloNum(((float)man30_overweight / count_man_30()) * 100F, 1);
	}

	public float getWoman30_overweightP()
	{
		return ParamType.getFloNum(((float)woman30_overweight / count_woman_30()) * 100F, 1);
	}

	public float getMan40_overweightP()
	{
		return ParamType.getFloNum(((float)man40_overweight / count_man_40()) * 100F, 1);
	}

	public float getWoman40_overweightP()
	{
		return ParamType.getFloNum(((float)woman40_overweight / count_woman_40()) * 100F, 1);
	}

	public float getMan45_overweightP()
	{
		return ParamType.getFloNum(((float)man45_overweight / count_man_45()) * 100F, 1);
	}

	public float getWoman45_overweightP()
	{
		return ParamType.getFloNum(((float)woman45_overweight / count_woman_45()) * 100F, 1);
	}

	public float getMan50_overweightP()
	{
		return ParamType.getFloNum(((float)man50_overweight / count_man_50()) * 100F, 1);
	}

	public float getWoman50_overweightP()
	{
		return ParamType.getFloNum(((float)woman50_overweight / count_woman_50()) * 100F, 1);
	}

	public float getMan55_overweightP()
	{
		return ParamType.getFloNum(((float)man55_overweight / count_man_55()) * 100F, 1);
	}

	public float getWoman55_overweightP()
	{
		return ParamType.getFloNum(((float)woman55_overweight / count_woman_55()) * 100F, 1);
	}

	public float getMan60_overweightP()
	{
		return ParamType.getFloNum(((float)man60_overweight / count_man_60()) * 100F, 1);
	}

	public float getWoman60_overweightP()
	{
		return ParamType.getFloNum(((float)woman60_overweight / count_woman_60()) * 100F, 1);
	}

	public float getMan70_overweightP()
	{
		return ParamType.getFloNum(((float)man70_overweight / count_man_70()) * 100F, 1);
	}

	public float getWoman70_overweightP()
	{
		return ParamType.getFloNum(((float)woman70_overweight / count_woman_70()) * 100F, 1);
	}

	public float getMan30_obesityP()
	{
		return ParamType.getFloNum(((float)man30_obesity / count_man_30()) * 100F, 1);
	}

	public float getWoman30_obesityP()
	{
		return ParamType.getFloNum(((float)woman30_obesity / count_woman_30()) * 100F, 1);
	}

	public float getMan40_obesityP()
	{
		return ParamType.getFloNum(((float)man40_obesity / count_man_40()) * 100F, 1);
	}

	public float getWoman40_obesityP()
	{
		return ParamType.getFloNum(((float)woman40_obesity / count_woman_40()) * 100F, 1);
	}

	public float getMan45_obesityP()
	{
		return ParamType.getFloNum(((float)man45_obesity / count_man_45()) * 100F, 1);
	}

	public float getWoman45_obesityP()
	{
		return ParamType.getFloNum(((float)woman45_obesity / count_woman_45()) * 100F, 1);
	}

	public float getMan50_obesityP()
	{
		return ParamType.getFloNum(((float)man50_obesity / count_man_50()) * 100F, 1);
	}

	public float getWoman50_obesityP()
	{
		return ParamType.getFloNum(((float)woman50_obesity / count_woman_50()) * 100F, 1);
	}

	public float getMan55_obesityP()
	{
		return ParamType.getFloNum(((float)man55_obesity / count_man_55()) * 100F, 1);
	}

	public float getWoman55_obesityP()
	{
		return ParamType.getFloNum(((float)woman55_obesity / count_woman_55()) * 100F, 1);
	}

	public float getMan60_obesityP()
	{
		return ParamType.getFloNum(((float)man60_obesity / count_man_60()) * 100F, 1);
	}

	public float getWoman60_obesityP()
	{
		return ParamType.getFloNum(((float)woman60_obesity / count_woman_60()) * 100F, 1);
	}

	public float getMan70_obesityP()
	{
		return ParamType.getFloNum(((float)man70_obesity / count_man_70()) * 100F, 1);
	}

	public float getWoman70_obesityP()
	{
		return ParamType.getFloNum(((float)woman70_obesity / count_woman_70()) * 100F, 1);
	}

	public float getCount30_thinP()
	{
		return ParamType.getFloNum(((float)(man30_thin + woman30_thin) / count30()) * 100F, 1);
	}

	public float getCount40_thinP()
	{
		return ParamType.getFloNum(((float)(man40_thin + woman40_thin) / count40()) * 100F, 1);
	}

	public float getCount45_thinP()
	{
		return ParamType.getFloNum(((float)(man45_thin + woman45_thin) / count45()) * 100F, 1);
	}

	public float getCount50_thinP()
	{
		return ParamType.getFloNum(((float)(man50_thin + woman50_thin) / count50()) * 100F, 1);
	}

	public float getCount55_thinP()
	{
		return ParamType.getFloNum(((float)(man55_thin + woman55_thin) / count55()) * 100F, 1);
	}

	public float getCount60_thinP()
	{
		return ParamType.getFloNum(((float)(man60_thin + woman60_thin) / count60()) * 100F, 1);
	}

	public float getCount70_thinP()
	{
		return ParamType.getFloNum(((float)(man70_thin + woman70_thin) / count70()) * 100F, 1);
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

	public float getCount30_overweightP()
	{
		return ParamType.getFloNum(((float)(man30_overweight + woman30_overweight) / count30()) * 100F, 1);
	}

	public float getCount40_overweightP()
	{
		return ParamType.getFloNum(((float)(man40_overweight + woman40_overweight) / count40()) * 100F, 1);
	}

	public float getCount45_overweightP()
	{
		return ParamType.getFloNum(((float)(man45_overweight + woman45_overweight) / count45()) * 100F, 1);
	}

	public float getCount50_overweightP()
	{
		return ParamType.getFloNum(((float)(man50_overweight + woman50_overweight) / count50()) * 100F, 1);
	}

	public float getCount55_overweightP()
	{
		return ParamType.getFloNum(((float)(man55_overweight + woman55_overweight) / count55()) * 100F, 1);
	}

	public float getCount60_overweightP()
	{
		return ParamType.getFloNum(((float)(man60_overweight + woman60_overweight) / count60()) * 100F, 1);
	}

	public float getCount70_overweightP()
	{
		return ParamType.getFloNum(((float)(man70_overweight + woman70_overweight) / count70()) * 100F, 1);
	}

	public float getCount30_obesityP()
	{
		return ParamType.getFloNum(((float)(man30_obesity + woman30_obesity) / count30()) * 100F, 1);
	}

	public float getCount40_obesityP()
	{
		return ParamType.getFloNum(((float)(man40_obesity + woman40_obesity) / count40()) * 100F, 1);
	}

	public float getCount45_obesityP()
	{
		return ParamType.getFloNum(((float)(man45_obesity + woman45_obesity) / count45()) * 100F, 1);
	}

	public float getCount50_obesityP()
	{
		return ParamType.getFloNum(((float)(man50_obesity + woman50_obesity) / count50()) * 100F, 1);
	}

	public float getCount55_obesityP()
	{
		return ParamType.getFloNum(((float)(man55_obesity + woman55_obesity) / count55()) * 100F, 1);
	}

	public float getCount60_obesityP()
	{
		return ParamType.getFloNum(((float)(man60_obesity + woman60_obesity) / count60()) * 100F, 1);
	}

	public float getCount70_obesityP()
	{
		return ParamType.getFloNum(((float)(man70_obesity + woman70_obesity) / count70()) * 100F, 1);
	}

	public int getMan30_thin()
	{
		return man30_thin;
	}

	public int getWoman30_thin()
	{
		return woman30_thin;
	}

	public int getMan40_thin()
	{
		return man40_thin;
	}

	public int getWoman40_thin()
	{
		return woman40_thin;
	}

	public int getMan45_thin()
	{
		return man45_thin;
	}

	public int getWoman45_thin()
	{
		return woman45_thin;
	}

	public int getMan50_thin()
	{
		return man50_thin;
	}

	public int getWoman50_thin()
	{
		return woman50_thin;
	}

	public int getMan55_thin()
	{
		return man55_thin;
	}

	public int getWoman55_thin()
	{
		return woman55_thin;
	}

	public int getMan60_thin()
	{
		return man60_thin;
	}

	public int getWoman60_thin()
	{
		return woman60_thin;
	}

	public int getMan70_thin()
	{
		return man70_thin;
	}

	public int getWoman70_thin()
	{
		return woman70_thin;
	}

	public int getMan30_normal()
	{
		return man30_normal;
	}

	public int getWoman30_normal()
	{
		return woman30_normal;
	}

	public int getMan40_normal()
	{
		return man40_normal;
	}

	public int getWoman40_normal()
	{
		return woman40_normal;
	}

	public int getMan45_normal()
	{
		return man45_normal;
	}

	public int getWoman45_normal()
	{
		return woman45_normal;
	}

	public int getMan50_normal()
	{
		return man50_normal;
	}

	public int getWoman50_normal()
	{
		return woman50_normal;
	}

	public int getMan55_normal()
	{
		return man55_normal;
	}

	public int getWoman55_normal()
	{
		return woman55_normal;
	}

	public int getMan60_normal()
	{
		return man60_normal;
	}

	public int getWoman60_normal()
	{
		return woman60_normal;
	}

	public int getMan70_normal()
	{
		return man70_normal;
	}

	public int getWoman70_normal()
	{
		return woman70_normal;
	}

	public int getMan30_overweight()
	{
		return man30_overweight;
	}

	public int getWoman30_overweight()
	{
		return woman30_overweight;
	}

	public int getMan40_overweight()
	{
		return man40_overweight;
	}

	public int getWoman40_overweight()
	{
		return woman40_overweight;
	}

	public int getMan45_overweight()
	{
		return man45_overweight;
	}

	public int getWoman45_overweight()
	{
		return woman45_overweight;
	}

	public int getMan50_overweight()
	{
		return man50_overweight;
	}

	public int getWoman50_overweight()
	{
		return woman50_overweight;
	}

	public int getMan55_overweight()
	{
		return man55_overweight;
	}

	public int getWoman55_overweight()
	{
		return woman55_overweight;
	}

	public int getMan60_overweight()
	{
		return man60_overweight;
	}

	public int getWoman60_overweight()
	{
		return woman60_overweight;
	}

	public int getMan70_overweight()
	{
		return man70_overweight;
	}

	public int getWoman70_overweight()
	{
		return woman70_overweight;
	}

	public int getMan30_obesity()
	{
		return man30_obesity;
	}

	public int getWoman30_obesity()
	{
		return woman30_obesity;
	}

	public int getMan40_obesity()
	{
		return man40_obesity;
	}

	public int getWoman40_obesity()
	{
		return woman40_obesity;
	}

	public int getMan45_obesity()
	{
		return man45_obesity;
	}

	public int getWoman45_obesity()
	{
		return woman45_obesity;
	}

	public int getMan50_obesity()
	{
		return man50_obesity;
	}

	public int getWoman50_obesity()
	{
		return woman50_obesity;
	}

	public int getMan55_obesity()
	{
		return man55_obesity;
	}

	public int getWoman55_obesity()
	{
		return woman55_obesity;
	}

	public int getMan60_obesity()
	{
		return man60_obesity;
	}

	public int getWoman60_obesity()
	{
		return woman60_obesity;
	}

	public int getMan70_obesity()
	{
		return man70_obesity;
	}

	public int getWoman70_obesity()
	{
		return woman70_obesity;
	}

	public void setMan30_thin(int man30_thin)
	{
		this.man30_thin = man30_thin;
	}

	public void setWoman30_thin(int woman30_thin)
	{
		this.woman30_thin = woman30_thin;
	}

	public void setMan40_thin(int man40_thin)
	{
		this.man40_thin = man40_thin;
	}

	public void setWoman40_thin(int woman40_thin)
	{
		this.woman40_thin = woman40_thin;
	}

	public void setMan45_thin(int man45_thin)
	{
		this.man45_thin = man45_thin;
	}

	public void setWoman45_thin(int woman45_thin)
	{
		this.woman45_thin = woman45_thin;
	}

	public void setMan50_thin(int man50_thin)
	{
		this.man50_thin = man50_thin;
	}

	public void setWoman50_thin(int woman50_thin)
	{
		this.woman50_thin = woman50_thin;
	}

	public void setMan55_thin(int man55_thin)
	{
		this.man55_thin = man55_thin;
	}

	public void setWoman55_thin(int woman55_thin)
	{
		this.woman55_thin = woman55_thin;
	}

	public void setMan60_thin(int man60_thin)
	{
		this.man60_thin = man60_thin;
	}

	public void setWoman60_thin(int woman60_thin)
	{
		this.woman60_thin = woman60_thin;
	}

	public void setMan70_thin(int man70_thin)
	{
		this.man70_thin = man70_thin;
	}

	public void setWoman70_thin(int woman70_thin)
	{
		this.woman70_thin = woman70_thin;
	}

	public void setMan30_normal(int man30_normal)
	{
		this.man30_normal = man30_normal;
	}

	public void setWoman30_normal(int woman30_normal)
	{
		this.woman30_normal = woman30_normal;
	}

	public void setMan40_normal(int man40_normal)
	{
		this.man40_normal = man40_normal;
	}

	public void setWoman40_normal(int woman40_normal)
	{
		this.woman40_normal = woman40_normal;
	}

	public void setMan45_normal(int man45_normal)
	{
		this.man45_normal = man45_normal;
	}

	public void setWoman45_normal(int woman45_normal)
	{
		this.woman45_normal = woman45_normal;
	}

	public void setMan50_normal(int man50_normal)
	{
		this.man50_normal = man50_normal;
	}

	public void setWoman50_normal(int woman50_normal)
	{
		this.woman50_normal = woman50_normal;
	}

	public void setMan55_normal(int man55_normal)
	{
		this.man55_normal = man55_normal;
	}

	public void setWoman55_normal(int woman55_normal)
	{
		this.woman55_normal = woman55_normal;
	}

	public void setMan60_normal(int man60_normal)
	{
		this.man60_normal = man60_normal;
	}

	public void setWoman60_normal(int woman60_normal)
	{
		this.woman60_normal = woman60_normal;
	}

	public void setMan70_normal(int man70_normal)
	{
		this.man70_normal = man70_normal;
	}

	public void setWoman70_normal(int woman70_normal)
	{
		this.woman70_normal = woman70_normal;
	}

	public void setMan30_overweight(int man30_overweight)
	{
		this.man30_overweight = man30_overweight;
	}

	public void setWoman30_overweight(int woman30_overweight)
	{
		this.woman30_overweight = woman30_overweight;
	}

	public void setMan40_overweight(int man40_overweight)
	{
		this.man40_overweight = man40_overweight;
	}

	public void setWoman40_overweight(int woman40_overweight)
	{
		this.woman40_overweight = woman40_overweight;
	}

	public void setMan45_overweight(int man45_overweight)
	{
		this.man45_overweight = man45_overweight;
	}

	public void setWoman45_overweight(int woman45_overweight)
	{
		this.woman45_overweight = woman45_overweight;
	}

	public void setMan50_overweight(int man50_overweight)
	{
		this.man50_overweight = man50_overweight;
	}

	public void setWoman50_overweight(int woman50_overweight)
	{
		this.woman50_overweight = woman50_overweight;
	}

	public void setMan55_overweight(int man55_overweight)
	{
		this.man55_overweight = man55_overweight;
	}

	public void setWoman55_overweight(int woman55_overweight)
	{
		this.woman55_overweight = woman55_overweight;
	}

	public void setMan60_overweight(int man60_overweight)
	{
		this.man60_overweight = man60_overweight;
	}

	public void setWoman60_overweight(int woman60_overweight)
	{
		this.woman60_overweight = woman60_overweight;
	}

	public void setMan70_overweight(int man70_overweight)
	{
		this.man70_overweight = man70_overweight;
	}

	public void setWoman70_overweight(int woman70_overweight)
	{
		this.woman70_overweight = woman70_overweight;
	}

	public void setMan30_obesity(int man30_obesity)
	{
		this.man30_obesity = man30_obesity;
	}

	public void setWoman30_obesity(int woman30_obesity)
	{
		this.woman30_obesity = woman30_obesity;
	}

	public void setMan40_obesity(int man40_obesity)
	{
		this.man40_obesity = man40_obesity;
	}

	public void setWoman40_obesity(int woman40_obesity)
	{
		this.woman40_obesity = woman40_obesity;
	}

	public void setMan45_obesity(int man45_obesity)
	{
		this.man45_obesity = man45_obesity;
	}

	public void setWoman45_obesity(int woman45_obesity)
	{
		this.woman45_obesity = woman45_obesity;
	}

	public void setMan50_obesity(int man50_obesity)
	{
		this.man50_obesity = man50_obesity;
	}

	public void setWoman50_obesity(int woman50_obesity)
	{
		this.woman50_obesity = woman50_obesity;
	}

	public void setMan55_obesity(int man55_obesity)
	{
		this.man55_obesity = man55_obesity;
	}

	public void setWoman55_obesity(int woman55_obesity)
	{
		this.woman55_obesity = woman55_obesity;
	}

	public void setMan60_obesity(int man60_obesity)
	{
		this.man60_obesity = man60_obesity;
	}

	public void setWoman60_obesity(int woman60_obesity)
	{
		this.woman60_obesity = woman60_obesity;
	}

	public void setMan70_obesity(int man70_obesity)
	{
		this.man70_obesity = man70_obesity;
	}

	public void setWoman70_obesity(int woman70_obesity)
	{
		this.woman70_obesity = woman70_obesity;
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
		double value_thin[] = {
			(double)(getThin() / 100F), (double)(man_thin() / 100F), (double)(woman_thin() / 100F)
		};
		double value_normal[] = {
			(double)(getNormal() / 100F), (double)(man_normal() / 100F), (double)(woman_normal() / 100F)
		};
		double value_overweight[] = {
			(double)(getOverweight() / 100F), (double)(man_overweight() / 100F), (double)(woman_overweight() / 100F)
		};
		double value_obesity[] = {
			(double)(getObesity() / 100F), (double)(man_obesity() / 100F), (double)(woman_obesity() / 100F)
		};
		String value1[] = {
			"����", "����", "����", "����"
		};
		String value2[] = {
			"����", "��", "Ů"
		};
		double value[][] = {
			value_thin, value_normal, value_overweight, value_obesity
		};
		return GroupReport.getBarChart(DatasetUtilities.createCategoryDataset(value1, value2, value), new Color[] {
			Color.YELLOW, Color.GREEN, new Color(253, 95, 77), Color.RED
		}, "�����������ݡ����ؼ����ְ��Ա�ֲ���%��", 0.29999999999999999D);
	}

	public int getCount()
	{
		return count;
	}

	public void setCount(int count)
	{
		this.count = count;
	}

	public float getAllNormalMax()
	{
		return ParamType.getFloNum(((getMan_normal_count() + getWoman_normal_count()) / (float)getCount()) * 100F, 1);
	}

	public float getAllobesityMax()
	{
		return ParamType.getFloNum(((getMan_obesity_count() + getWoman_obesity_count()) / (float)getCount()) * 100F, 1);
	}

	public float getAllOverweightMax()
	{
		return ParamType.getFloNum(((getMan_overweight_count() + getWoman_overweight_count()) / (float)getCount()) * 100F, 1);
	}

	public float getAllThinMax()
	{
		return ParamType.getFloNum(((getMan_thin_count() + getWoman_thin_count()) / (float)getCount()) * 100F, 1);
	}

	public float getManNormalMax()
	{
		return ParamType.getFloNum((getMan_normal_count() / (float)man) * 100F, 1);
	}

	public float getManobesityMax()
	{
		return ParamType.getFloNum((getMan_obesity_count() / (float)man) * 100F, 1);
	}

	public float getManOverweightMax()
	{
		return ParamType.getFloNum((getMan_overweight_count() / (float)man) * 100F, 1);
	}

	public float getManThinMax()
	{
		return ParamType.getFloNum((getMan_thin_count() / (float)man) * 100F, 1);
	}

	public float getWomanNormalMax()
	{
		return ParamType.getFloNum((getWoman_normal_count() / (float)woman) * 100F, 1);
	}

	public float getWomanobesityMax()
	{
		return ParamType.getFloNum((getWoman_obesity_count() / (float)woman) * 100F, 1);
	}

	public float getWomanOverweightMax()
	{
		return ParamType.getFloNum((getWoman_overweight_count() / (float)woman) * 100F, 1);
	}

	public float getWomanThinMax()
	{
		return ParamType.getFloNum((getWoman_thin_count() / (float)woman) * 100F, 1);
	}

	public int getManThin()
	{
		return getMan30_thin() + getMan40_thin() + getMan45_thin() + getMan50_thin() + getMan55_thin() + getMan60_thin() + getMan70_thin();
	}

	public int getManNormal()
	{
		return getMan30_normal() + getMan40_normal() + getMan45_normal() + getMan50_normal() + getMan55_normal() + getMan60_normal() + getMan70_normal();
	}

	public int getManOverweight()
	{
		return getMan30_overweight() + getMan40_overweight() + getMan45_overweight() + getMan50_overweight() + getMan55_overweight() + getMan60_overweight() + getMan70_overweight();
	}

	public int getManobesity()
	{
		return getMan30_obesity() + getMan40_obesity() + getMan45_obesity() + getMan50_obesity() + getMan55_obesity() + getMan60_obesity() + getMan70_obesity();
	}

	public int getWomanThin()
	{
		return getWoman30_thin() + getWoman40_thin() + getWoman45_thin() + getWoman50_thin() + getWoman55_thin() + getWoman60_thin() + getWoman70_thin();
	}

	public int getWomanNormal()
	{
		return getWoman30_normal() + getWoman40_normal() + getWoman45_normal() + getWoman50_normal() + getWoman55_normal() + getWoman60_normal() + getWoman70_normal();
	}

	public int getWomanOverweight()
	{
		return getWoman30_overweight() + getWoman40_overweight() + getWoman45_overweight() + getWoman50_overweight() + getWoman55_overweight() + getWoman60_overweight() + getWoman70_overweight();
	}

	public int getWomanobesity()
	{
		return getWoman30_obesity() + getWoman40_obesity() + getWoman45_obesity() + getWoman50_obesity() + getWoman55_obesity() + getWoman60_obesity() + getWoman70_obesity();
	}
}
