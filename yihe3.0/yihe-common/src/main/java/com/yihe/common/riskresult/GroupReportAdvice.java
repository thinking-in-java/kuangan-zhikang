// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   GroupReportAdvice.java

package com.yihe.common.riskresult;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DatasetUtilities;

import com.yihe.common.utils.ParamType;
import com.yihe.common.utils.Path;

// Referenced classes of package cn.well.step.util.riskresult:
//			GroupReportCreatData, GroupReportBean, RiskSortUtil, GroupReport

public class GroupReportAdvice
{

	private int all;
	private int cereal;
	private int fruits_vegetables;
	private int pressure;
	private int lipid;
	private int weight;
	private int smoke;
	private int sport;
	private int bloodSugar;
	private int waist;
	private int meat;
	private int drink;
	private int riskAll;
	private float riskAllMax;
	private List riskList;

	/*public GroupReportAdvice(GroupReportCreatData groupReportCreatData)
	{
		all = groupReportCreatData.getGroupReportBean().getCount();
	}*/

	public int getBloodSugar()
	{
		return bloodSugar;
	}

	public void setBloodSugar(int bloodSugar)
	{
		this.bloodSugar = bloodSugar;
	}

	public int getCereal()
	{
		return cereal;
	}

	public void setCereal(int cereal)
	{
		this.cereal = cereal;
	}

	public int getDrink()
	{
		return drink;
	}

	public void setDrink(int drink)
	{
		this.drink = drink;
	}

	public int getFruits_vegetables()
	{
		return fruits_vegetables;
	}

	public void setFruits_vegetables(int fruits_vegetables)
	{
		this.fruits_vegetables = fruits_vegetables;
	}

	public int getLipid()
	{
		return lipid;
	}

	public void setLipid(int lipid)
	{
		this.lipid = lipid;
	}

	public int getMeat()
	{
		return meat;
	}

	public void setMeat(int meat)
	{
		this.meat = meat;
	}

	public int getPressure()
	{
		return pressure;
	}

	public void setPressure(int pressure)
	{
		this.pressure = pressure;
	}

	public int getSmoke()
	{
		return smoke;
	}

	public void setSmoke(int smoke)
	{
		this.smoke = smoke;
	}

	public int getSport()
	{
		return sport;
	}

	public void setSport(int sport)
	{
		this.sport = sport;
	}

	public int getWaist()
	{
		return waist;
	}

	public void setWaist(int waist)
	{
		this.waist = waist;
	}

	public int getWeight()
	{
		return weight;
	}

	public void setWeight(int weight)
	{
		this.weight = weight;
	}

	public String getJFreeChartPath()
	{
		JFreeChart jFreeChart = getJFreeChart();

		String path = (new StringBuilder(String.valueOf(Path.PASSING))).append(System.currentTimeMillis()).append(".jpg").toString();
		File fos_jpg = null;
		try
		{
			fos_jpg = new File(path);

			if (!fos_jpg.exists()) {
				fos_jpg.mkdirs();
			}
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
		double valueCereal[] = {
			(double)(((RiskSortUtil)getRiskList().get(0)).getMax().floatValue() / 100F), (double)(((RiskSortUtil)getRiskList().get(1)).getMax().floatValue() / 100F), (double)(((RiskSortUtil)getRiskList().get(2)).getMax().floatValue() / 100F), (double)(((RiskSortUtil)getRiskList().get(3)).getMax().floatValue() / 100F), (double)(((RiskSortUtil)getRiskList().get(4)).getMax().floatValue() / 100F), (double)(((RiskSortUtil)getRiskList().get(5)).getMax().floatValue() / 100F), (double)(((RiskSortUtil)getRiskList().get(6)).getMax().floatValue() / 100F), (double)(((RiskSortUtil)getRiskList().get(7)).getMax().floatValue() / 100F), (double)(((RiskSortUtil)getRiskList().get(8)).getMax().floatValue() / 100F), (double)(((RiskSortUtil)getRiskList().get(9)).getMax().floatValue() / 100F), 
			(double)(((RiskSortUtil)getRiskList().get(10)).getMax().floatValue() / 100F)
		};
		String value2[] = {
			((RiskSortUtil)getRiskList().get(0)).getName(), ((RiskSortUtil)getRiskList().get(1)).getName(), ((RiskSortUtil)getRiskList().get(2)).getName(), ((RiskSortUtil)getRiskList().get(3)).getName(), ((RiskSortUtil)getRiskList().get(4)).getName(), ((RiskSortUtil)getRiskList().get(5)).getName(), ((RiskSortUtil)getRiskList().get(6)).getName(), ((RiskSortUtil)getRiskList().get(7)).getName(), ((RiskSortUtil)getRiskList().get(8)).getName(), ((RiskSortUtil)getRiskList().get(9)).getName(), 
			((RiskSortUtil)getRiskList().get(10)).getName()
		};
		String value1[] = {
			"百分比"
		};
		double value[][] = {
			valueCereal
		};
		return GroupReport.getBarChartHorizontal(DatasetUtilities.createCategoryDataset(value1, value2, value), new java.awt.Color[] {
			new java.awt.Color(102, 153, 204)
		}, "健康危险因素的详细分布（%）");
	}

	public List getRiskList()
	{
		List temp = new ArrayList();
		RiskSortUtil cerealList = new RiskSortUtil();
		cerealList.setName("谷类摄入过多");
		cerealList.setMax(ParamType.getFloNum(((float)getCereal() * 100F) / (float)all, 1));
		temp.add(cerealList);
		RiskSortUtil fruits_vegetablesList = new RiskSortUtil();
		fruits_vegetablesList.setName("蔬菜水果摄入不足");
		fruits_vegetablesList.setMax(ParamType.getFloNum(((float)getFruits_vegetables() * 100F) / (float)all, 1));
		temp.add(fruits_vegetablesList);
		RiskSortUtil pressureList = new RiskSortUtil();
		pressureList.setName("血压高");
		pressureList.setMax(ParamType.getFloNum(((float)getPressure() * 100F) / (float)all, 1));
		temp.add(pressureList);
		RiskSortUtil lipidList = new RiskSortUtil();
		lipidList.setName("血脂异常");
		lipidList.setMax(ParamType.getFloNum(((float)getLipid() * 100F) / (float)all, 1));
		temp.add(lipidList);
		RiskSortUtil weightList = new RiskSortUtil();
		weightList.setName("超重肥胖");
		weightList.setMax(ParamType.getFloNum(((float)getWeight() * 100F) / (float)all, 1));
		temp.add(weightList);
		RiskSortUtil smokeList = new RiskSortUtil();
		smokeList.setName("吸烟");
		smokeList.setMax(ParamType.getFloNum(((float)getSmoke() * 100F) / (float)all, 1));
		temp.add(smokeList);
		RiskSortUtil sportList = new RiskSortUtil();
		sportList.setName("缺乏运动");
		sportList.setMax(ParamType.getFloNum(((float)getSport() * 100F) / (float)all, 1));
		temp.add(sportList);
		RiskSortUtil bloodSugarList = new RiskSortUtil();
		bloodSugarList.setName("血糖高");
		bloodSugarList.setMax(ParamType.getFloNum(((float)getBloodSugar() * 100F) / (float)all, 1));
		temp.add(bloodSugarList);
		RiskSortUtil waistList = new RiskSortUtil();
		waistList.setName("腰围过大");
		waistList.setMax(ParamType.getFloNum(((float)getWaist() * 100F) / (float)all, 1));
		temp.add(waistList);
		RiskSortUtil meatList = new RiskSortUtil();
		meatList.setName("肉类摄入过多");
		meatList.setMax(ParamType.getFloNum(((float)getMeat() * 100F) / (float)all, 1));
		temp.add(meatList);
		RiskSortUtil drinkList = new RiskSortUtil();
		drinkList.setName("过量饮酒");
		drinkList.setMax(ParamType.getFloNum(((float)getDrink() * 100F) / (float)all, 1));
		temp.add(drinkList);
		Collections.sort(temp);
		return temp;
	}

	public void setRiskList(List riskList)
	{
		this.riskList = riskList;
	}

	public int getRiskAll()
	{
		return riskAll;
	}

	public void setRiskAll(int riskAll)
	{
		this.riskAll = riskAll;
	}

	public float getRiskAllMax()
	{
		return ParamType.getFloNum(((float)getRiskAll() * 100F) / (float)all, 1);
	}

	public void setRiskAllMax(float riskAllMax)
	{
		this.riskAllMax = riskAllMax;
	}
}

