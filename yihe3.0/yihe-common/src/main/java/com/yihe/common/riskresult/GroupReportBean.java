// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   GroupReportBean.java

package com.yihe.common.riskresult;


import java.util.List;

import com.yihe.common.utils.ParamType;

// Referenced classes of package cn.well.step.util.riskresult:
//			GroupReportBasic, GroupReportLifestyle, GroupReportHealthRisks, GroupReportBodyMassIndex, 
//			GroupReportWaistline, GroupReportBloodPressure, GroupReportBloodSugar, GroupReportLipid, 
//			GroupReportMealHabit, GroupReportSmoke, GroupReportDrinking, GroupReportPhysicalActivity, 
//			GroupReportRiskComplication, GroupReportAdvice

public class GroupReportBean
{

	private int count;
	private int evCount;
	private int man;
	private int woman;
	private GroupReportBasic groupReportBasic;
	private GroupReportHealthRisks groupReportHealthRisks;
	private GroupReportBodyMassIndex groupReportBodyMassIndex;
	private GroupReportWaistline groupReportWaistline;
	private GroupReportBloodPressure groupReportBloodPressure;
	private GroupReportBloodSugar groupReportBloodSugar;
	private GroupReportSmoke groupReportSmoke;
	private GroupReportDrinking groupReportDrinking;
	private GroupReportPhysicalActivity groupReportPhysicalActivity;
	private GroupReportRiskComplication groupReportRiskComplication;
	private GroupReportAdvice groupReportAdvice;
	private float evCountMax;
	private String firstMonth;
	private String lastMonth;
	private List disList;
	private String descriptions;

	public GroupReportBean()
	{
		descriptions = "";
	}

	public int getEvCount()
	{
		return evCount;
	}

	public void setEvCount(int evCount)
	{
		this.evCount = evCount;
	}

	public int getCount()
	{
		return count;
	}

	public void setCount(int count)
	{
		this.count = count;
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

	public GroupReportBasic getGroupReportBasic()
	{
		return groupReportBasic;
	}

	public void setGroupReportBasic(GroupReportBasic groupReportBasic)
	{
		this.groupReportBasic = groupReportBasic;
	}

	

	public GroupReportHealthRisks getGroupReportHealthRisks()
	{
		return groupReportHealthRisks;
	}

	public void setGroupReportHealthRisks(GroupReportHealthRisks groupReportHealthRisks)
	{
		this.groupReportHealthRisks = groupReportHealthRisks;
	}

	public GroupReportBodyMassIndex getGroupReportBodyMassIndex()
	{
		return groupReportBodyMassIndex;
	}

	public void setGroupReportBodyMassIndex(GroupReportBodyMassIndex groupReportBodyMassIndex)
	{
		this.groupReportBodyMassIndex = groupReportBodyMassIndex;
	}

	public GroupReportWaistline getGroupReportWaistline()
	{
		return groupReportWaistline;
	}

	public void setGroupReportWaistline(GroupReportWaistline groupReportWaistline)
	{
		this.groupReportWaistline = groupReportWaistline;
	}

	public GroupReportBloodPressure getGroupReportBloodPressure()
	{
		return groupReportBloodPressure;
	}

	public void setGroupReportBloodPressure(GroupReportBloodPressure groupReportBloodPressure)
	{
		this.groupReportBloodPressure = groupReportBloodPressure;
	}

	public GroupReportBloodSugar getGroupReportBloodSugar()
	{
		return groupReportBloodSugar;
	}

	public void setGroupReportBloodSugar(GroupReportBloodSugar groupReportBloodSugar)
	{
		this.groupReportBloodSugar = groupReportBloodSugar;
	}

	

	public GroupReportSmoke getGroupReportSmoke()
	{
		return groupReportSmoke;
	}

	public void setGroupReportSmoke(GroupReportSmoke groupReportSmoke)
	{
		this.groupReportSmoke = groupReportSmoke;
	}

	public GroupReportDrinking getGroupReportDrinking()
	{
		return groupReportDrinking;
	}

	public void setGroupReportDrinking(GroupReportDrinking groupReportDrinking)
	{
		this.groupReportDrinking = groupReportDrinking;
	}

	public GroupReportPhysicalActivity getGroupReportPhysicalActivity()
	{
		return groupReportPhysicalActivity;
	}

	public void setGroupReportPhysicalActivity(GroupReportPhysicalActivity groupReportPhysicalActivity)
	{
		this.groupReportPhysicalActivity = groupReportPhysicalActivity;
	}

	public GroupReportRiskComplication getGroupReportRiskComplication()
	{
		return groupReportRiskComplication;
	}

	public void setGroupReportRiskComplication(GroupReportRiskComplication groupReportRiskComplication)
	{
		this.groupReportRiskComplication = groupReportRiskComplication;
	}

	public GroupReportAdvice getGroupReportAdvice()
	{
		return groupReportAdvice;
	}

	public void setGroupReportAdvice(GroupReportAdvice groupReportAdvice)
	{
		this.groupReportAdvice = groupReportAdvice;
	}

	public float getEvCountMax()
	{
		return ParamType.getFloNum(((float)getEvCount() * 100F) / (float)getCount(), 1);
	}

	public void setEvCountMax(float evCountMax)
	{
		this.evCountMax = evCountMax;
	}

	public String getFirstMonth()
	{
		return firstMonth;
	}

	public void setFirstMonth(String firstMonth)
	{
		this.firstMonth = firstMonth;
	}

	public String getLastMonth()
	{
		return lastMonth;
	}

	public void setLastMonth(String lastMonth)
	{
		this.lastMonth = lastMonth;
	}

	public List getDisList()
	{
		return disList;
	}

	public void setDisList(List disList)
	{
		this.disList = disList;
	}

	public String getDescriptions()
	{
		return descriptions;
	}

	public void setDescriptions(String descriptions)
	{
		this.descriptions = descriptions;
	}
}
