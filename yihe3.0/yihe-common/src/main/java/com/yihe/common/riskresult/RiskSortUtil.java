// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   RiskSortUtil.java
package com.yihe.common.riskresult;

public class RiskSortUtil
	implements Comparable
{

	private String name;
	private Float max;

	public RiskSortUtil()
	{
	}

	public int compareTo(RiskSortUtil obj)
	{
		return getMax().compareTo(obj.getMax());
	}

	public Float getMax()
	{
		return max;
	}

	public void setMax(float max)
	{
		this.max = Float.valueOf(max);
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public int compareTo(Object obj)
	{
		return compareTo((RiskSortUtil)obj);
	}
}
